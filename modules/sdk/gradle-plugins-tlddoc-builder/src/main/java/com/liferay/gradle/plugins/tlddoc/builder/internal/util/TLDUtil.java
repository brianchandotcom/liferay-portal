/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.tlddoc.builder.internal.util;

import java.io.File;

import java.util.Collections;
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

		Map<String, File> definitionFiles = _getDefinitionFiles(tldFile);
		Document document = _getDocument(tldFile);

		_scanDTD(document, definitionFiles, dtdConsumer);
		_scanXSD(document, definitionFiles, xsdConsumer);
	}

	private static Map<String, File> _getDefinitionFiles(File tldFile) {
		if (!_portalDefinitions.isEmpty()) {
			return _portalDefinitions;
		}

		File tldDir = tldFile.getParentFile();

		if (tldDir == null) {
			return Collections.emptyMap();
		}

		File definitionFilesDir = new File(tldDir, "definitions");

		if (!definitionFilesDir.exists()) {
			return Collections.emptyMap();
		}

		Map<String, File> definitionFiles = new HashMap<>();

		for (File definitionFile : definitionFilesDir.listFiles()) {
			String definitionFileName = definitionFile.getName();

			definitionFiles.put(definitionFileName, definitionFile);
		}

		return definitionFiles;
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
		Document document, Map<String, File> definitionFiles,
		BiConsumer<String, File> dtdConsumer) {

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

		File dtdFile = definitionFiles.get(definitionFileName);

		if (dtdFile == null) {
			return;
		}

		dtdConsumer.accept(publicId, dtdFile);
	}

	private static Map<String, File> _scanNestedXSD(
			File xsdFile, Map<String, File> definitionFiles)
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

				File curDefinitionFile = definitionFiles.get(
					_getFileName(schemaLocation));

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

				File curDefinitionFile = definitionFiles.get(
					_getFileName(schemaLocation));

				if (curDefinitionFile == null) {
					continue;
				}

				xsdFiles.add(curDefinitionFile);
			}
		}

		return nestedXSDs;
	}

	private static void _scanXSD(
			Document document, Map<String, File> definitionFiles,
			BiConsumer<String, File> xsdConsumer)
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

		if ((values.length % 2) != 0) {
			return;
		}

		for (int i = 0; i < values.length; i += 2) {
			String definitionFileName = _getFileName(values[i + 1]);

			File xsdFile = definitionFiles.get(definitionFileName);

			if (xsdFile == null) {
				return;
			}

			xsdConsumer.accept(values[i], xsdFile);

			Map<String, File> nestedXSDFiles = _nestedXSDCache.computeIfAbsent(
				xsdFile,
				keyXSDFile -> {
					try {
						return _scanNestedXSD(keyXSDFile, definitionFiles);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				});

			for (Map.Entry<String, File> entry : nestedXSDFiles.entrySet()) {
				xsdConsumer.accept(entry.getKey(), entry.getValue());
			}
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
					File definitionFilesDir = new File(
						gradleUserHome, "/../definitions");

					if (definitionFilesDir.exists()) {
						for (File definitionFile :
								definitionFilesDir.listFiles()) {

							String definitionFileName =
								definitionFile.getName();

							put(definitionFileName, definitionFile);
						}
					}
				}
			}
		};

}