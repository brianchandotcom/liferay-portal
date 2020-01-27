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

package com.liferay.gradle.plugins.target.platform.internal.util;

import com.liferay.gradle.util.Validator;

import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Gregory Amerson
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

	public static Document newDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-external-dtd",
			false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.newDocument();
	}

	public static String toString(Document document)
		throws TransformerException {

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		transformer.transform(
			new DOMSource(document), new StreamResult(byteArrayOutputStream));

		return byteArrayOutputStream.toString();
	}

}