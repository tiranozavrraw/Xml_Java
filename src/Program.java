import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws ParserConfigurationException {
        Program program = new Program();
        program.run(args);
    }

    private void run(String[] args) throws ParserConfigurationException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        try {
            Document document = documentBuilder.parse("C:\\test\\input2.xml");
            Node root = document.getDocumentElement();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//Trade");
            NodeList result = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            System.out.println();
            for (int i = 0; i < result.getLength(); i++){
                var trade = result.item(i);
                var idNode = document.createElement("ExchangeTradeId");
                var id = document.createTextNode((Integer.toString(i)));
                idNode.appendChild(id);
                trade.appendChild(idNode);
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // send DOM to file
            tr.transform(new DOMSource(document),
                    new StreamResult(new FileOutputStream("C:\\test\\output10.xml")));

        } catch (SAXException | XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }


    }


}