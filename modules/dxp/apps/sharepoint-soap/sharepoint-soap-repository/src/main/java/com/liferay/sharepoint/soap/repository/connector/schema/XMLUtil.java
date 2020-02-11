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

package com.liferay.sharepoint.soap.repository.connector.schema;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.StringWriter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public static List<org.w3c.dom.Node> toNodes(
		Document document, Node... nodes) {

		return Stream.of(
			nodes
		).map(
			Node::toXmlString
		).map(
			XMLUtil::_toNode
		).map(
			node -> document.importNode(node, true)
		).collect(
			Collectors.toList()
		);
	}

	public static String toString(org.w3c.dom.Node node) {
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