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

package com.liferay.tld.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.xml.SAXReaderFactory;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 */
public class TLDFormatter {

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDirName = GetterUtil.getString(
			arguments.get("tld.base.dir"), _BASE_DIR_NAME);
		boolean plugin = GetterUtil.getBoolean(
			arguments.get("tld.plugin"), true);

		try {
			new TLDFormatter(baseDirName, plugin);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TLDFormatter(String baseDirName, boolean plugin) throws Exception {
		_plugin = plugin;

		Files.walkFileTree(
			Paths.get(baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = file.getFileName();
					String fileName = fileNamePath.toString();

					if (!fileName.endsWith(".tld") ||
						(!_plugin &&
						 fileName.equals("liferay-portlet-ext.tld"))) {

						return FileVisitResult.CONTINUE;
					}

					try {
						_formatTLD(file);
					}
					catch (IOException ioe) {
						throw ioe;
					}
					catch (Exception e) {
						throw new IOException(e);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _formatTLD(Path file) throws Exception {
		String content = new String(
			Files.readAllBytes(file), StandardCharsets.UTF_8);

		if (!_plugin) {
			content = StringUtil.replace(
				content, "xml/ns/j2ee/web-jsptaglibrary_2_0.xsd",
				"dtd/web-jsptaglibrary_1_2.dtd");
		}

		SAXReader saxReader = _getSAXReader();

		Document document = saxReader.read(new UnsyncStringReader(content));

		Element root = document.getRootElement();

		_sortElements(root, "tag", "name");

		List<Element> tagEls = root.elements("tag");

		for (Element tagEl : tagEls) {
			_sortElements(tagEl, "attribute", "name");

			Element dynamicAttributesEl = tagEl.element("dynamic-attributes");

			if (dynamicAttributesEl != null) {
				dynamicAttributesEl.detach();

				tagEl.add(dynamicAttributesEl);
			}
		}

		String newContent = XMLFormatter.toString(document);

		int x = newContent.indexOf("<tlib-version");
		int y = newContent.indexOf("</taglib>");

		newContent = newContent.substring(x, y);

		x = content.indexOf("<tlib-version");
		y = content.indexOf("</taglib>");

		newContent =
			content.substring(0, x) + newContent + content.substring(y);

		if (!content.equals(newContent)) {
			Files.write(file, newContent.getBytes(StandardCharsets.UTF_8));

			System.out.println(file);
		}
	}

	private SAXReader _getSAXReader() {
		return SAXReaderFactory.getSAXReader(null, false, false);
	}

	private void _sortElements(
		Element parentElement, String name, String sortBy) {

		Map<String, Element> map = new TreeMap<>();

		List<Element> elements = parentElement.elements(name);

		for (Element element : elements) {
			map.put(element.elementText(sortBy), element);

			element.detach();
		}

		for (Map.Entry<String, Element> entry : map.entrySet()) {
			Element element = entry.getValue();

			parentElement.add(element);
		}
	}

	private static final String _BASE_DIR_NAME = "./";

	private final boolean _plugin;

}