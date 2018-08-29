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

package com.liferay.gradle.plugins.tlddoc.builder.internal.util;

import java.io.File;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Peter Shin
 */
public class TLDUtil {

	public static void scanDTDAndXSD(
			File tldFile, BiConsumer<String, File> dtdConsumer,
			BiConsumer<String, File> xsdConsumer)
		throws Exception {

		String fileName = tldFile.getName();

		if (!fileName.endsWith(".tld")) {
			return;
		}

		Document document = _getDocument(tldFile);

		if (document == null) {
			return;
		}

		_scanDTD(document, dtdConsumer);
		_scanXSD(document, xsdConsumer);
	}

	private static Document _getDocument(File file) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(_LOAD_EXTERNAL_DTD, false);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.parse(file);
	}

	private static String _getFileName(String s) {
		if (s == null) {
			return null;
		}

		String trimmedString = s.trim();

		int index = trimmedString.lastIndexOf('/');

		if (index == -1) {
			return null;
		}

		return trimmedString.substring(index + 1);
	}

	private static void _populateSchemaProperties(
			BiConsumer<String, File> xsdConsumer, File definitionFile)
		throws Exception {

		Queue<File> definitionFiles = new LinkedList<>();

		definitionFiles.add(definitionFile);

		while ((definitionFile = definitionFiles.poll()) != null) {
			Document document = _getDocument(definitionFile);

			if (document == null) {
				continue;
			}

			NodeList importNodeList = document.getElementsByTagName(
				"xsd:import");

			for (int i = 0; i < importNodeList.getLength(); i++) {
				Node importNode = importNodeList.item(i);

				NamedNodeMap namedNodeMap = importNode.getAttributes();

				Node namespaceNode = namedNodeMap.getNamedItem("namespace");

				if (namespaceNode == null) {
					continue;
				}

				Node schemLocationNode = namedNodeMap.getNamedItem(
					"schemaLocation");

				if (schemLocationNode == null) {
					continue;
				}

				String namespace = namespaceNode.getNodeValue();
				String schemaLocation = schemLocationNode.getNodeValue();

				if ((namespace == null) || (schemaLocation == null)) {
					continue;
				}

				String fileName = _getFileName(schemaLocation);

				if (fileName == null) {
					continue;
				}

				File curDefinitionFile = _portalDefinitions.get(fileName);

				if (curDefinitionFile == null) {
					continue;
				}

				xsdConsumer.accept(namespace, curDefinitionFile);

				definitionFiles.add(curDefinitionFile);
			}

			NodeList includeNodeList = document.getElementsByTagName(
				"xsd:include");

			for (int i = 0; i < includeNodeList.getLength(); i++) {
				Node taglibNode = includeNodeList.item(i);

				NamedNodeMap namedNodeMap = taglibNode.getAttributes();

				Node schemLocationNode = namedNodeMap.getNamedItem(
					"schemaLocation");

				if (schemLocationNode == null) {
					continue;
				}

				String schemaLocation = schemLocationNode.getNodeValue();

				if (schemaLocation == null) {
					continue;
				}

				File curDefinitionFile = _portalDefinitions.get(schemaLocation);

				if (curDefinitionFile == null) {
					continue;
				}

				definitionFiles.add(curDefinitionFile);
			}
		}
	}

	private static void _scanDTD(
		Document document, BiConsumer<String, File> dtdConsumer) {

		DocumentType documentType = document.getDoctype();

		if (documentType == null) {
			return;
		}

		String publicId = documentType.getPublicId();

		if (publicId == null) {
			return;
		}

		String definitionFileName = _getFileName(documentType.getSystemId());

		if (definitionFileName == null) {
			return;
		}

		File definitionFile = _portalDefinitions.get(definitionFileName);

		if (definitionFile == null) {
			return;
		}

		dtdConsumer.accept(publicId, definitionFile);
	}

	private static void _scanXSD(
			Document document, BiConsumer<String, File> xsdConsumer)
		throws Exception {

		Node taglibNode = document.getDocumentElement();

		NamedNodeMap namedNodeMap = taglibNode.getAttributes();

		Node schemLocationNode = namedNodeMap.getNamedItem(
			"xsi:schemaLocation");

		if (schemLocationNode == null) {
			return;
		}

		String schemLocation = schemLocationNode.getNodeValue();

		if (schemLocation == null) {
			return;
		}

		String[] values = schemLocation.split("\\s+");

		if (values.length != 2) {
			return;
		}

		String definitionFileName = _getFileName(values[1]);

		if (definitionFileName == null) {
			return;
		}

		File definitionFile = _portalDefinitions.get(definitionFileName);

		if (definitionFile == null) {
			return;
		}

		xsdConsumer.accept(values[0].trim(), definitionFile);

		_populateSchemaProperties(xsdConsumer, definitionFile);
	}

	private static final String _LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final Map<String, File> _portalDefinitions = new HashMap<>();

	static {
		String gradleUserHome = System.getProperty("gradle.user.home");

		if (gradleUserHome != null) {
			File definitionsDir = new File(gradleUserHome, "/../definitions");

			if (definitionsDir.exists()) {
				for (File definitionFile : definitionsDir.listFiles()) {
					String definitionFileName = definitionFile.getName();

					_portalDefinitions.put(definitionFileName, definitionFile);
				}
			}
		}
	}

}