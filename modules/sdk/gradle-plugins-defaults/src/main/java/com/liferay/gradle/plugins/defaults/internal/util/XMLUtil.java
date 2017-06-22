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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.util.Validator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Andrea Di Giorgi
 */
public class XMLUtil {

	public static Element appendElement(
		Document document, Node parentNode, String name) {

		Element element = document.createElement(name);

		parentNode.appendChild(element);

		return element;
	}

	public static Element appendElement(
		Document document, Node parentNode, String name, String text) {

		if (Validator.isNull(text)) {
			return null;
		}

		Element element = appendElement(document, parentNode, name);

		element.setTextContent(text);

		return element;
	}

	public static Element getChildElement(Element parentElement, String name) {
		if (parentElement == null) {
			return null;
		}

		Node node = parentElement.getFirstChild();

		do {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;

				if (name.equals(element.getTagName())) {
					return element;
				}
			}
		}
		while ((node = node.getNextSibling()) != null);

		return null;
	}

	public static String getChildElementText(
		Element parentElement, String name) {

		Element element = getChildElement(parentElement, name);

		if (element == null) {
			return null;
		}

		return element.getTextContent();
	}

	public static DocumentBuilder getDocumentBuilder() throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-external-dtd",
			false);

		return documentBuilderFactory.newDocumentBuilder();
	}

	public static void write(Document document, File file) throws Exception {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		transformer.transform(new DOMSource(document), new StreamResult(file));
	}

}