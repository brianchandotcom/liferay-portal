/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.schema;

import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.soap.connector.SharepointRuntimeException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.encoding.AnyContentType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Iván Zaera
 */
public final class XMLUtil {

	public static Element getElement(AnyContentType anyContentType) {
		try {
			return anyContentType.get_any()[0].getAsDOM();
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to parse response from the Sharepoint server",
				exception);
		}
	}

	public static Element getElement(
		String nodeName, org.w3c.dom.Node w3CNode) {

		for (org.w3c.dom.Node childW3CNode = w3CNode.getFirstChild();
			 childW3CNode != null;
			 childW3CNode = childW3CNode.getNextSibling()) {

			String localName = childW3CNode.getLocalName();

			if ((localName != null) &&
				StringUtil.equalsIgnoreCase(localName, nodeName)) {

				return (Element)childW3CNode;
			}
		}

		return null;
	}

	public static Element toElement(Node node) {
		return _toElement(node.toXmlString());
	}

	public static String toString(Element element) {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = null;

		try {
			transformer = transformerFactory.newTransformer();
		}
		catch (TransformerConfigurationException
					transformerConfigurationException) {

			throw new RuntimeException(transformerConfigurationException);
		}

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		StringWriter stringWriter = new StringWriter();

		try {
			transformer.transform(
				new DOMSource(element), new StreamResult(stringWriter));
		}
		catch (TransformerException transformerException) {
			throw new RuntimeException(transformerException);
		}

		StringBuffer stringBuffer = stringWriter.getBuffer();

		return stringBuffer.toString();
	}

	private static Element _toElement(String xml) {
		try {
			DocumentBuilderFactory documentBuilderFactory =
				SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			StringReader stringReader = new StringReader(xml);

			InputSource inputSource = new InputSource(stringReader);

			Document document = documentBuilder.parse(inputSource);

			return document.getDocumentElement();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		catch (ParserConfigurationException parserConfigurationException) {
			throw new RuntimeException(parserConfigurationException);
		}
		catch (SAXException saxException) {
			throw new RuntimeException(saxException);
		}
	}

}