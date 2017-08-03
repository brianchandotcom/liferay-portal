/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sharepoint.connector.schema;

import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.connector.SharepointRuntimeException;

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
public class XMLHelper {

	public Element getElement(AnyContentType anyContentType) {
		try {
			return anyContentType.get_any()[0].getAsDOM();
		}
		catch (Exception e) {
			throw new SharepointRuntimeException(
				"Unable to parse response from the Sharepoint server", e);
		}
	}

	public Element getElement(String nodeName, org.w3c.dom.Node w3CNode) {
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

	public Element toElement(Node node) {
		return _toElement(node.toXmlString());
	}

	public String toString(Element element) {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = null;

		try {
			transformer = transformerFactory.newTransformer();
		}
		catch (TransformerConfigurationException tce) {
			throw new RuntimeException(tce);
		}

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		StringWriter stringWriter = new StringWriter();

		try {
			transformer.transform(
				new DOMSource(element), new StreamResult(stringWriter));
		}
		catch (TransformerException te) {
			throw new RuntimeException(te);
		}

		StringBuffer stringBuffer = stringWriter.getBuffer();

		return stringBuffer.toString();
	}

	private Element _toElement(String xml) {
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
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		catch (ParserConfigurationException pce) {
			throw new RuntimeException(pce);
		}
		catch (SAXException saxe) {
			throw new RuntimeException(saxe);
		}
	}

}