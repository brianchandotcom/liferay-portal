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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProvider;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Brian Wing Shun Chan
 */
public class XSLTBuilder {

	public static void main(String[] args) {
		if (args.length == 3) {
			new XSLTBuilder(StringUtil.split(args[0]), args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public XSLTBuilder(String[] xmls, String xsl, String html) {
		try {
			System.setProperty("line.separator", StringPool.NEW_LINE);

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer(
				new StreamSource(xsl));

			transformer.transform(
				_combineAndSortXMLs(xmls),
				new StreamResult(new FileOutputStream(html)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Source _combineAndSortXMLs(String[] xmls) throws Exception {
		SecureXMLFactoryProvider secureXMLFactoryProvider =
			new SecureXMLFactoryProviderImpl();

		DocumentBuilderFactory documentBuilderFactory =
			secureXMLFactoryProvider.newDocumentBuilderFactory();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Map<String, Node> nodeMap = new TreeMap<>();

		for (String xml : xmls) {
			Document document = documentBuilder.parse(new File(xml));

			NodeList nodeList = document.getElementsByTagName("file-name");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				nodeMap.put(node.getTextContent(), node.getParentNode());
			}
		}

		Document document = documentBuilder.newDocument();

		Element versionsElement = document.createElement("versions");

		document.appendChild(versionsElement);

		Element versionElement = document.createElement("version");

		versionsElement.appendChild(versionElement);

		Element librariesElement = document.createElement("libraries");

		versionElement.appendChild(librariesElement);

		for (Node node : nodeMap.values()) {
			librariesElement.appendChild(document.importNode(node, true));
		}

		return new DOMSource(document);
	}

}