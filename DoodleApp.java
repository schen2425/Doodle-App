import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;

/**
 * This is a drawing application for drawing simple pictures.
 * @author Steven Chen
 * @version 1.0
 */
public class DoodleApp extends Application {
    private Label coord;
    private Label numShapes;
    private int shapes = 0;
    private Label curColor;
    private Canvas canvas = new Canvas(650, 450);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Label drawWidthLabel;
    private Label eraseRadiusLabel;
    private Label shapeSizeLabel;

    private String color = "black";


    private void updateCoord(double x, double y) {
        coord.setText("(" + x + "," + y + ")");
    }

    private void updateNumShapes(int shape) {
        numShapes.setText("Number of shapes: " + shape);
    }

    private void updateCurColor() {
        curColor.setText("Color: " + color);
    }
    private Double x1 = null;
    private Double y1 = null;

    private void updateDrawWidthLabel(double size) {
        String size1 = String.format("Draw Radius: %.2f", size);
        drawWidthLabel.setText(size1);

    }
    private void updateEraseRadiusLabel(double radius) {
        String radius1 = String.format("Erase Radius: %.2f", radius);
        eraseRadiusLabel.setText(radius1);
    }
    private void updateShapeSizeLabel(double size) {
        String size1 = String.format("Shape Size: %.2f", size);
        shapeSizeLabel.setText(size1);
    }

    /**
     * This launches the application and contains the code to define actions.
     * @param primaryStage This is the stage shown to user.
     */
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        VBox sidePane = new VBox();
        sidePane.setStyle("-fx-background-color: #DCDCDC;");
        RadioButton rb1 = new RadioButton("Draw");
        RadioButton rb2  = new RadioButton("Erase");
        RadioButton rb3 = new RadioButton("Circle");
        RadioButton rb4 = new RadioButton("Rectangle");
        final ToggleGroup options = new ToggleGroup();
        rb1.setToggleGroup(options);
        rb1.setSelected(true);
        rb2.setToggleGroup(options);
        rb3.setToggleGroup(options);
        rb4.setToggleGroup(options);
        Slider strokeSize = new Slider(1, 5, 2);
        strokeSize.setShowTickMarks(true);
        strokeSize.setShowTickLabels(true);
        strokeSize.setMajorTickUnit(1.0f);
        strokeSize.setBlockIncrement(0.5f);
        Label colorLabel = new Label("Color (Press Enter):");
        Label widthLabel = new Label("Draw Radius:");
        Slider eraseSize = new Slider(5, 15, 10);
        eraseSize.setShowTickLabels(true);
        eraseSize.setShowTickMarks(true);
        eraseSize.setMajorTickUnit(2.5);
        eraseSize.setBlockIncrement(1.0);
        Label eraseLabel = new Label("Erase Radius:");
        Label shapeLabel = new Label("Shape Size:");
        Slider shapeSize = new Slider(5, 25, 15);
        shapeSize.setShowTickLabels(true);
        shapeSize.setShowTickMarks(true);
        shapeSize.setBlockIncrement(2.0);
        shapeSize.setMajorTickUnit(5);

        TextField colorSelect = new TextField();
        colorSelect.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    try {
                        Color.valueOf(colorSelect.getText());
                        color = colorSelect.getText();
                        updateCurColor();
                    } catch (Exception ex) {
                        Alert alert = new Alert(AlertType.ERROR, "Invalid color entered!!",
                                ButtonType.OK);
                        alert.show();
                    }
                }
            }
        });

        strokeSize.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateDrawWidthLabel(strokeSize.getValue());
            }
        });
        shapeSize.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateShapeSizeLabel(shapeSize.getValue());
            }
        });

        eraseSize.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateEraseRadiusLabel(eraseSize.getValue());
            }
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button clear = new Button("Clear Canvas");
        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, 650, 450);
                shapes = 0;
                updateNumShapes(shapes);
            }
        });

        sidePane.getChildren().addAll(rb1, rb2, rb3, rb4, colorLabel, colorSelect,
                widthLabel, strokeSize, eraseLabel, eraseSize, shapeLabel, shapeSize, spacer, clear);
        sidePane.setSpacing(10.0);
        sidePane.setAlignment(Pos.TOP_LEFT);

        HBox bottom = new HBox();
        bottom.setStyle("-fx-background-color: #DCDCDC;");
        coord = new Label("(x, y)");
        coord.setMinWidth(87);
        numShapes = new Label("Number of shape: 0");
        curColor = new Label("Color: Black");
        drawWidthLabel = new Label("Draw Radius: 2.00");
        eraseRadiusLabel = new Label("Erase Radius: 10.00");
        shapeSizeLabel = new Label("Shape Size: 15.00");
        bottom.getChildren().addAll(coord, numShapes, curColor, drawWidthLabel,
                eraseRadiusLabel, shapeSizeLabel);
        bottom.setSpacing(20.0);
        bottom.setAlignment(Pos.CENTER_LEFT);

        Pane center = new Pane();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 650, 450);
        gc.setLineWidth(strokeSize.getValue() * 2);
        gc.setLineCap(StrokeLineCap.ROUND);
        attachHandlers(canvas, rb1, rb2, strokeSize, eraseSize);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (rb3.isSelected()) {
                    gc.setFill(Color.valueOf(color));
                    gc.fillOval((e.getX() - shapeSize.getValue()), (e.getY() - shapeSize.getValue()),
                            shapeSize.getValue() * 2, shapeSize.getValue() * 2);
                    shapes += 1;
                    updateNumShapes(shapes);
                } else if (rb1.isSelected()) {
                    gc.setLineWidth(strokeSize.getValue() * 2);
                    gc.setStroke(Color.valueOf(color));
                    gc.strokeLine(e.getX(), e.getY(), e.getX(), e.getY());
                } else if (rb2.isSelected()) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval((e.getX() - eraseSize.getValue()), (e.getY() - eraseSize.getValue()),
                            eraseSize.getValue() * 2, eraseSize.getValue() * 2);
                } else if (rb4.isSelected()) {
                    gc.setFill(Color.valueOf(color));
                    double size = shapeSize.getValue() - 4.5;
                    gc.fillRect(e.getX() - shapeSize.getValue(), e.getY() - (size),
                            shapeSize.getValue() * 2, (size * 2));
                    shapes += 1;
                    updateNumShapes(shapes);
                }
            }
        });
        center.getChildren().add(canvas);
        borderPane.setLeft(sidePane);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Doodle App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void attachHandlers(Canvas canvas1, RadioButton rb1, RadioButton rb2,
                                Slider strokeSize, Slider eraseSize) {
        canvas1.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateCoord(e.getX(), e.getY());
            }
        });
        canvas1.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateCoord(e.getX(), e.getY());
            }
        });
        canvas1.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (rb1.isSelected()) {
                    gc.setLineWidth(strokeSize.getValue() * 2);
                    if ((x1 == null && y1 == null)) {
                        x1 = e.getX();
                        y1 = e.getY();
                    }
                    gc.setStroke(Color.valueOf(color));
                    gc.strokeLine(x1, y1, e.getX(), e.getY());
                    x1 = e.getX();
                    y1 = e.getY();
                } else if (rb2.isSelected()) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval((e.getX() - eraseSize.getValue()), (e.getY() - eraseSize.getValue()),
                            eraseSize.getValue() * 2, eraseSize.getValue() * 2);
                }
            }
        });
        canvas1.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                x1 = null;
                y1 = null;
            }
        });
        canvas1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                updateCoord(0, 0);
            }
        });
    }
}
