/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.StringWriter;

import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import org.w3c.dom.Document;

/**
 * @author Iván Zaera
 */
public final class XMLUtil {

	public static org.w3c.dom.Node getNode(
		String nodeName, org.w3c.dom.Node w3CNode) {

		for (org.w3c.dom.Node childW3CNode = w3CNode.getFirstChild();
			 childW3CNode != null;
			 childW3CNode = childW3CNode.getNextSibling()) {

			String localName = childW3CNode.getLocalName();

			if ((localName != null) &&
				StringUtil.equalsIgnoreCase(localName, nodeName)) {

				return childW3CNode;
			}
		}

		return null;
	}

	public static org.w3c.dom.Node toNode(Document document, Node node) {
		return document.importNode(_toNode(node.toXmlString()), true);
	}

	public static List<org.w3c.dom.Node> toNodes(
		Document document, Node... nodes) {

		return TransformUtil.transformToList(
			nodes, node -> toNode(document, node));
	}

	public static String toString(org.w3c.dom.Node node) {
		TransformerFactory transformerFactory =
			SecureXMLFactoryProviderUtil.newTransformerFactory();

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
				new DOMSource(node), new StreamResult(stringWriter));
		}
		catch (TransformerException transformerException) {
			throw new RuntimeException(transformerException);
		}

		StringBuffer stringBuffer = stringWriter.getBuffer();

		return stringBuffer.toString();
	}

	private static org.w3c.dom.Node _toNode(String xml) {
		try {
			XmlObject xmlObject = XmlObject.Factory.parse(xml);

			org.w3c.dom.Node node = xmlObject.getDomNode();

			return node.getFirstChild();
		}
		catch (XmlException xmlException) {
			throw new RuntimeException(xmlException);
		}
	}

}