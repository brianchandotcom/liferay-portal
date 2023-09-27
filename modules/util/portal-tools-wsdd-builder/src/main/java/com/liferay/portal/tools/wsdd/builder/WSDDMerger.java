/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.wsdd.builder;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.wsdd.builder.util.Java2WsddTask;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.xml.sax.SAXException;

/**
 * @author Brian Wing Shun Chan
 */
public class WSDDMerger {

	public static void main(String[] args) {
		new WSDDMerger(args[0], args[1]);
	}

	public static void merge(String source, String destination)
		throws DocumentException, IOException, SAXException {

		// Source

		File sourceFile = new File(source);

		SAXReader saxReader = _getSAXReader();

		Document document = saxReader.read(sourceFile);

		Element rootElement = document.getRootElement();

		List<Element> sourceServiceElements = rootElement.elements("service");

		if (sourceServiceElements.isEmpty()) {
			return;
		}

		// Destination

		File destinationFile = new File(destination);

		document = saxReader.read(destinationFile);

		String oldContent = Java2WsddTask.documentToString(document);

		rootElement = document.getRootElement();

		Map<String, Element> servicesMap = new TreeMap<>();

		List<Element> serviceElements = rootElement.elements("service");

		for (Element serviceElement : serviceElements) {
			String name = serviceElement.attributeValue("name");

			servicesMap.put(name, serviceElement);

			serviceElement.detach();
		}

		for (Element serviceElement : sourceServiceElements) {
			String name = serviceElement.attributeValue("name");

			servicesMap.put(name, serviceElement);

			serviceElement.detach();
		}

		for (Map.Entry<String, Element> entry : servicesMap.entrySet()) {
			Element serviceElement = entry.getValue();

			rootElement.add(serviceElement);
		}

		String content = Java2WsddTask.documentToString(document);

		if (!content.equals(oldContent)) {
			content = StringUtil.replace(content, "\"/>", "\" />");

			Files.write(
				destinationFile.toPath(), content.getBytes(StringPool.UTF8));
		}
	}

	public WSDDMerger(String source, String destination) {
		try {
			merge(source, destination);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private static SAXReader _getSAXReader() throws SAXException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		saxReader.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", true);
		saxReader.setFeature(
			"http://xml.org/sax/features/external-general-entities", false);
		saxReader.setFeature(
			"http://xml.org/sax/features/external-parameter-entities", false);

		return saxReader;
	}

}