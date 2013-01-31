/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderFileUtil {

	public SeleniumBuilderFileUtil(String baseDir) {
		_baseDir = baseDir;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		if (content != null) {
			content = content.trim();
			content = StringUtil.replace(content, "\n", "");
			content = StringUtil.replace(content, "\r\n", "");
			content = StringUtil.replace(content, "\t", " ");
			content = content.replaceAll(" +", " ");
		}

		return content;
	}

	public Element getRootElementByFileName(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		_isValidName(fileName, rootElement);

		return rootElement;
	}

	public String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	public String readFile(String fileName) throws Exception {
		return FileUtil.read(getBaseDir() + "/" + fileName);
	}

	public void writeFile(
		String fileName, String content, boolean format) throws Exception {

		File file = new File(getBaseDir() + "-generated/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	private void _isValidName(String fileName, Element rootElement)
		throws Exception {

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String fileObjectName = fileName.substring(x + 1, y);

		if (fileName.endsWith(".path")) {
			Element head = rootElement.element("head");
			Element title = head.element("title");

			String titleText = title.getText();

			Element body = rootElement.element("body");
			Element table = body.element("table");
			Element thead = table.element("thead");
			Element tr = thead.element("tr");
			Element td = tr.element("td");

			String tdText = td.getText();

			if ((titleText == null) || (tdText == null) ||
				!fileObjectName.equals(titleText) ||
				!fileObjectName.equals(tdText)) {

				System.out.println(fileName + " has an invalid name");
			}
		}
		else if (fileName.endsWith(".testcase") ||
				 fileName.endsWith(".testsuite")) {

			String elementObjectName = rootElement.attributeValue("name");

			if ((elementObjectName == null) ||
				!elementObjectName.equals(fileObjectName)) {

				System.out.println(fileName + " has an invalid name");
			}
		}
		else {
			String elementObjectName = rootElement.attributeValue("object");

			if ((elementObjectName == null) ||
				!elementObjectName.equals(fileObjectName)) {

				System.out.println(fileName + " has an invalid name");
			}
		}
	}

	private String _baseDir;

}