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

import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.Dom4jUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 * @author Peter Shin
 */
public class SPDXBuilder {

	public static void main(String[] args) throws IOException {
		String xmls = null;

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in))) {

			xmls = bufferedReader.readLine();
		}

		new SPDXBuilder(StringUtil.split(xmls), args[0]);
	}

	public SPDXBuilder(String[] xmls, String fileName) {
		try {
			System.setProperty("line.separator", StringPool.NEW_LINE);

			String content = Dom4jUtil.toString(_getDocument(xmls, fileName));
			String prefix = fileName.substring(0, fileName.lastIndexOf('.'));

			Files.write(
				Paths.get(prefix + "-complete.rdf"),
				content.getBytes(StandardCharsets.UTF_8));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Element _createPackageElement(Node libraryNode) {
		Element packageElement = DocumentHelper.createElement(_PACKAGE_QNAME);

		packageElement.addAttribute("rdf:about", "spdx");

		Element nameElement = packageElement.addElement(_NAME_QNAME);

		Node projectNameNode = libraryNode.selectSingleNode("project-name");

		nameElement.addText(projectNameNode.getText());

		Element versionInfoElement = packageElement.addElement(
			_VERSION_INFO_QNAME);

		Node versionNode = libraryNode.selectSingleNode("version");

		versionInfoElement.addText(versionNode.getText());

		Node projectURLNode = libraryNode.selectSingleNode("project-url");

		if (projectURLNode != null) {
			Element downloadLocationElement = packageElement.addElement(
				_DOWNLOAD_LOCATION_QNAME);

			downloadLocationElement.addText(projectURLNode.getText());
		}

		List<Node> licenseNameNodes = libraryNode.selectNodes(
			"./licenses/license/license-name");

		if (!licenseNameNodes.isEmpty()) {
			Element licenseConcludedElement = packageElement.addElement(
				_LICENSE_CONCLUDED_QNAME);

			Node licenseNameNode = licenseNameNodes.get(0);

			licenseConcludedElement.addText(licenseNameNode.getText());
		}

		return packageElement;
	}

	@SuppressWarnings("unchecked")
	private Document _getDocument(String[] xmls, String fileName)
		throws Exception {

		Map<String, Element> packageElementMap = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		Document document = saxReader.read(new File(fileName));

		Element rootElement = document.getRootElement();

		Element documentElement = rootElement.element(_SPDX_DOCUMENT_QNAME);

		List<Element> packageElements = documentElement.elements(
			_PACKAGE_QNAME);

		for (Element packageElement : packageElements) {
			String name = packageElement.elementText(_NAME_QNAME);
			String versionInfo = packageElement.elementText(
				_VERSION_INFO_QNAME);

			List<Element> fileElements = packageElement.elements(_FILE_QNAME);

			for (Element fileElement : fileElements) {
				String text = fileElement.elementText(_FILE_NAME_QNAME);

				String baseDirName = text.substring(0, text.indexOf('/') + 1);

				if (!baseDirName.endsWith("portal/") ||
					!baseDirName.endsWith("portal-ee/")) {

					continue;
				}

				Element fileNameElement = fileElement.element(_FILE_NAME_QNAME);

				fileNameElement.setText(text.substring(text.indexOf('/') + 1));
			}

			rootElement.remove(packageElement);

			packageElementMap.put(name + ':' + versionInfo, packageElement);
		}

		for (String xml : xmls) {
			Document xmlDocument = saxReader.read(new File(xml));

			List<Node> fileNameNodes = xmlDocument.selectNodes("//file-name");

			for (Node fileNameNode : fileNameNodes) {
				Node libraryNode = fileNameNode.getParent();

				Node projectNameNode = libraryNode.selectSingleNode(
					"project-name");
				Node versionNode = libraryNode.selectSingleNode("version");

				Element packageElement = packageElementMap.get(
					projectNameNode.getText() + ':' + versionNode.getText());

				if (packageElement == null) {
					packageElement = _createPackageElement(libraryNode);
				}

				Element fileElement = packageElement.addElement(_FILE_QNAME);

				Element fileNameElement = fileElement.addElement(
					_FILE_NAME_QNAME);

				fileNameElement.addText(fileNameNode.getText());

				packageElementMap.put(
					projectNameNode.getText() + ':' + versionNode.getText(),
					packageElement);
			}
		}

		for (Element packageElement : packageElementMap.values()) {
			documentElement.add(packageElement.detach());
		}

		return document;
	}

	private static final QName _DOWNLOAD_LOCATION_QNAME = new QName(
		"downloadLocation", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:downloadLocation");

	private static final QName _FILE_NAME_QNAME = new QName(
		"fileName", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:fileName");

	private static final QName _FILE_QNAME = new QName(
		"File", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:File");

	private static final QName _LICENSE_CONCLUDED_QNAME = new QName(
		"licenseConcluded", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:licenseConcluded");

	private static final QName _NAME_QNAME = new QName(
		"name", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:name");

	private static final QName _PACKAGE_QNAME = new QName(
		"Package", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:Package");

	private static final QName _SPDX_DOCUMENT_QNAME = new QName(
		"SpdxDocument", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:SpdxDocument");

	private static final QName _VERSION_INFO_QNAME = new QName(
		"versionInfo", new Namespace("spdx", "http://spdx.org/rdf/terms#"),
		"spdx:versionInfo");

}