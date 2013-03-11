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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

	public Set<String> getChildElementNameSet(Element element) {
		return getChildElementNameSet(new TreeSet<String>(), element);
	}

	public Set<String> getChildElementNameSet(
		Set<String> nameSet, Element element) {

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return nameSet;
		}
		else {
			for (Element childElement : childElements) {
				String childElementName = childElement.attributeValue("name");

				if (!(childElementName == null)) {
					int x = childElementName.lastIndexOf(StringPool.POUND);

					if (!(x == -1)) {
						nameSet.add(childElementName.substring(0, x));
					}
				}

				getChildElementNameSet(nameSet, childElement);
			}
		}

		return nameSet;
	}

	public String getClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getClassName(fileName, classSuffix);
	}

	public String getClassName(String fileName, String classSuffix) {
		return
			getPackageName(fileName) + "." +
				getSimpleClassName(fileName, classSuffix);
	}

	public String getClassSuffix(String fileName) {
		int x = fileName.indexOf(CharPool.PERIOD);

		return StringUtil.upperCaseFirstLetter(fileName.substring(x + 1));
	}

	public String getJavaFileName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getJavaFileName(fileName, classSuffix);
	}

	public String getJavaFileName(String fileName, String classSuffix) {
		return
			getPackagePath(fileName) + "/" +
				getSimpleClassName(fileName, classSuffix) + ".java";
	}

	public String getName(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		return fileName.substring(x + 1, y);
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

	public String getPackageName(String fileName) {
		String packagePath = getPackagePath(fileName);

		return StringUtil.replace(
			packagePath, StringPool.SLASH, StringPool.PERIOD);
	}

	public String getPackagePath(String fileName) {
		int x = fileName.lastIndexOf(StringPool.SLASH);

		return fileName.substring(0, x);
	}

	public String getReturnType(String name) {
		if (name.startsWith("Is")) {
			return "boolean";
		}

		return "void";
	}

	public Element getRootElement(String fileName) throws Exception {
		String content = getNormalizedContent(fileName);

		Document document = SAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		validateDocument(fileName, rootElement);

		return rootElement;
	}

	public String getSimpleClassName(String fileName) {
		String classSuffix = getClassSuffix(fileName);

		return getSimpleClassName(fileName, classSuffix);
	}

	public String getSimpleClassName(String fileName, String classSuffix) {
		return getName(fileName) + classSuffix;
	}

	public int getTargetCount(Element rootElement) {
		String xml = rootElement.asXML();

		for (int i = 1;;) {
			if (xml.contains("${target" + i + "}")) {
				i++;

				continue;
			}

			return i;
		}
	}

	public String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	public String readFile(String fileName) throws Exception {
		return FileUtil.read(getBaseDir() + "/" + fileName);
	}

	public void writeFile(String fileName, String content, boolean format)
		throws Exception {

		File file = new File(getBaseDir() + "-generated/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	protected void validateDocument(String fileName, Element rootElement)
		throws Exception {

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String shortFileName = fileName.substring(x + 1, y);

		if (fileName.endsWith(".path")) {
			Element headElement = rootElement.element("head");

			Element titleElement = headElement.element("title");

			String title = titleElement.getText();

			if ((title == null) || !shortFileName.equals(title)) {
				System.out.println(fileName + " has an <title>");
			}

			Element bodyElement = rootElement.element("body");

			Element tableElement = bodyElement.element("table");

			Element theadElement = tableElement.element("thead");

			Element trElement = theadElement.element("tr");

			Element tdElement = trElement.element("td");

			String tdText = tdElement.getText();

			if ((tdText == null) || !shortFileName.equals(tdText)) {
				System.out.println(fileName + " has an invalid <td>");
			}
		}
		else {
			String name = rootElement.attributeValue("name");

			if ((name == null) || !name.equals(shortFileName)) {
				System.out.println(fileName + " has an invalid name=\"\"");
			}
		}
	}

	private String _baseDir;

}