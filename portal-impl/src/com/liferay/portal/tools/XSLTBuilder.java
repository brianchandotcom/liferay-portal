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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 */
public class XSLTBuilder {

	public static void main(String[] args) throws IOException {
		if (args.length == 2) {
			String xmls = null;

			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(System.in))) {

				xmls = bufferedReader.readLine();
			}

			new XSLTBuilder(StringUtil.split(xmls), args[0], args[1]);
		}
		else if (args.length == 3) {
			new XSLTBuilder(StringUtil.split(args[0]), args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public XSLTBuilder(String xml, String xsl, String html) {
		this(new String[] {xml}, xsl, html);
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

	private DocumentSource _combineAndSortXMLs(String[] xmls) throws Exception {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		Map<String, Element> elementMap = new TreeMap<>();

		for (String xml : xmls) {
			Document document = saxReader.read(new File(xml));

			List<Node> nodes = document.selectNodes("//file-name");

			for (Node node : nodes) {
				elementMap.put(node.getText(), node.getParent());
			}
		}

		Document document = DocumentHelper.createDocument();

		Element versionsElement = document.addElement("versions");

		Element versionElement = versionsElement.addElement("version");

		Element librariesElement = versionElement.addElement("libraries");

		for (Element element : elementMap.values()) {
			librariesElement.add(element.detach());
		}

		return new DocumentSource(document);
	}

}