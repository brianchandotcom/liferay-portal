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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.InitUtil;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class PathsXMLToJavaBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new PathsXMLToJavaBuilder(args);
	}

	public PathsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = _getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			generatePaths(fileName);
		}
	}

	protected void generatePaths(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String pathsFilePath = fileName.substring(0, x);
		String pathsName = fileName.substring(x + 1, y) + "Paths";

		String pathsFileName = pathsFilePath + "/" + pathsName + ".java";
		String pathsPackagePath = StringUtil.replace(
			pathsFilePath, StringPool.SLASH, StringPool.PERIOD);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(pathsPackagePath);
		sb.append(";\n");

		sb.append("import java.util.HashMap;\n");
		sb.append("import java.util.Map;\n");

		sb.append("public class ");
		sb.append(pathsName);
		sb.append(" {");

		sb.append("public static Map<String, String[]> getPaths() {");
		sb.append("return _paths;");
		sb.append("}");

		Element rootElement = getRootElement(fileName);

		sb.append(getPaths(rootElement));

		sb.append("}");

		writeFile(pathsFileName, sb.toString(), true);
	}

	protected String getPaths(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element bodyElement = rootElement.element("body");
		Element tableElement = bodyElement.element("table");
		Element tbodyElement = tableElement.element("tbody");

		List<Element> elementList = tbodyElement.elements("tr");

		for (Element element : elementList) {
			List<Element> paramList = element.elements("td");

			String key = paramList.get(0).getText();
			String locator = paramList.get(1).getText();
			String value = paramList.get(2).getText();

			if (!key.equals("")) {
				sb.append("private static String[] _");
				sb.append(key);
				sb.append(" = {");
				sb.append("\"" + locator + "\",");
				sb.append("\"" + value + "\"");
				sb.append("};");
			}
		}

		sb.append("private static Map<String, String[]> _paths = ");
		sb.append("new HashMap<String, String[]>();");

		sb.append("static {");

		for (Element element : elementList) {
			List<Element> paramList = element.elements("td");

			String key = paramList.get(0).getText();

			if (!key.equals("")) {
				sb.append("_paths.put(\"");
				sb.append(key);
				sb.append("\", _");
				sb.append(key);
				sb.append(");");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private Set<String> _getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\*.paths"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

}