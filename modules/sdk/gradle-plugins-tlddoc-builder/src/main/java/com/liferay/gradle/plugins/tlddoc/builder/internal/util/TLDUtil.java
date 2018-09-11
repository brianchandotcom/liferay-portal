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
import java.util.concurrent.ConcurrentHashMap;
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
		int index = s.lastIndexOf('/');

		if (index == -1) {
			return s;
		}

		String fileName = s.substring(index + 1);

		return fileName.trim();
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

		String systemId = documentType.getSystemId();

		if (systemId == null) {
			return;
		}

		String definitionFileName = _getFileName(systemId);

		File dtdFile = _portalDefinitions.get(definitionFileName);

		if (dtdFile == null) {
			return;
		}

		dtdConsumer.accept(publicId, dtdFile);
	}

	private static Map<String, File> _scanNestedXSD(File xsdFile)
		throws Exception {

		Map<String, File> nestedXSDs = new HashMap<>();

		Queue<File> xsdFiles = new LinkedList<>();

		xsdFiles.add(xsdFile);

		File curXSDFile = null;

		while ((curXSDFile = xsdFiles.poll()) != null) {
			Document document = _getDocument(curXSDFile);

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

				File curDefinitionFile = _portalDefinitions.get(fileName);

				if (curDefinitionFile == null) {
					continue;
				}

				nestedXSDs.put(namespace, curDefinitionFile);

				xsdFiles.add(curDefinitionFile);
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

				String fileName = _getFileName(schemaLocation);

				File curDefinitionFile = _portalDefinitions.get(fileName);

				if (curDefinitionFile == null) {
					continue;
				}

				xsdFiles.add(curDefinitionFile);
			}
		}

		return nestedXSDs;
	}

	private static void _scanXSD(
			Document document, BiConsumer<String, File> xsdConsumer)
		throws Exception {

		Node rootNode = document.getDocumentElement();

		NamedNodeMap namedNodeMap = rootNode.getAttributes();

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

		File xsdFile = _portalDefinitions.get(definitionFileName);

		if (xsdFile == null) {
			return;
		}

		xsdConsumer.accept(values[0], xsdFile);

		Map<String, File> nestedXSDFiles = _nestedXSDCache.computeIfAbsent(
			xsdFile,
			keyXSDFile -> {
				try {
					return _scanNestedXSD(keyXSDFile);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

		for (Map.Entry<String, File> entry : nestedXSDFiles.entrySet()) {
			xsdConsumer.accept(entry.getKey(), entry.getValue());
		}
	}

	private static final String _LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final Map<File, Map<String, File>> _nestedXSDCache =
		new ConcurrentHashMap<>();

	private static final Map<String, File> _portalDefinitions =
		new HashMap<String, File>() {
			{
				String gradleUserHome = System.getProperty("gradle.user.home");

				if (gradleUserHome != null) {
					File definitionsDir = new File(
						gradleUserHome, "/../definitions");

					if (definitionsDir.exists()) {
						for (File definitionFile : definitionsDir.listFiles()) {
							String definitionFileName =
								definitionFile.getName();

							put(definitionFileName, definitionFile);
						}
					}
				}
			}
		};

}