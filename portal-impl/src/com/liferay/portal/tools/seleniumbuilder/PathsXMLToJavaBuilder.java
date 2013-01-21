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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PathsXMLToJavaBuilder extends BaseXMLToJavaBuilder {

	public PathsXMLToJavaBuilder(Map<String, Object> context) throws Exception {
		super(context);

		_basedir = (String)context.get("basedir");
		_seleniumDataUtil = new SeleniumDataUtil(context);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);
	}

	public void generatePaths(String fileName) throws Exception {
		if (fileName.length() > 161) {
			System.out.println(
				"Exceeds 177 characters: portal-web/test/" + fileName);
		}

		if (!FileUtil.exists(_basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String pathsFilePath = fileName.substring(0, x);
		String pathsSimpleClassName = fileName.substring(x + 1, y) + "Paths";

		String pathsFileName =
			pathsFilePath + "/" + pathsSimpleClassName + ".java";
		String pathsPackagePath = StringUtil.replace(
			pathsFilePath, StringPool.SLASH, StringPool.PERIOD);

		Element rootElement = _seleniumFileUtil.getRootElementByFileName(
			fileName);

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(pathsPackagePath);
		sb.append(";\n");

		sb.append("import java.util.HashMap;\n");
		sb.append("import java.util.Map;\n");

		sb.append(processBlockImports(rootElement));

		sb.append("public class ");
		sb.append(pathsSimpleClassName);
		sb.append(" {");

		sb.append("public static Map<String, String> getPaths() {");
		sb.append("Map<String, String> paths = new HashMap<String, String>();");
		sb.append(_processBlockImportPaths(rootElement));
		sb.append(_processTargets(rootElement));
		sb.append("return paths;");
		sb.append("}");

		sb.append("}");

		_seleniumFileUtil.writeFile(pathsFileName, sb.toString(), true);
	}

	protected String processBlockImports(Element block) throws Exception {
		StringBundler sb = new StringBundler();

		Set<String> classNameSet = _seleniumDataUtil.getPathsClassNameSet(
			block);

		for (String className : classNameSet) {
			sb.append("import ");
			sb.append(className);
			sb.append(";\n");
		}

		return sb.toString();
	}

	private String _processBlockImportPaths(Element element) throws Exception {
		StringBundler sb = new StringBundler();

		Element bodyElement = element.element("body");
		Element tableElement = bodyElement.element("table");
		Element tbodyElement = tableElement.element("tbody");

		List<Element> targetList = tbodyElement.elements("tr");

		for (Element target : targetList) {
			List<Element> paramList = target.elements("td");

			String targetID = paramList.get(0).getText();
			String targetLocator = paramList.get(1).getText();

			if (targetID.equals("IMPORT_PATH_OBJECT")) {
				sb.append("paths.putAll(");
				sb.append(targetLocator);
				sb.append("Paths.getPaths());");
			}
		}

		return sb.toString();
	}

	private String _processTargets(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element bodyElement = rootElement.element("body");
		Element tableElement = bodyElement.element("table");
		Element tbodyElement = tableElement.element("tbody");

		List<Element> targetList = tbodyElement.elements("tr");

		sb.append("paths.put(\"TOP\", \"relative=top\");");

		for (Element target : targetList) {
			List<Element> paramList = target.elements("td");

			String targetID = paramList.get(0).getText();
			String targetLocator = paramList.get(1).getText();

			if (!targetID.equals("") &&
				!targetID.equals("IMPORT_PATH_OBJECT")) {

				sb.append("paths.put(\"");
				sb.append(targetID);
				sb.append("\", \"");
				sb.append(targetLocator);
				sb.append("\");");
			}
		}

		return sb.toString();
	}

	private String _basedir;
	private SeleniumDataUtil _seleniumDataUtil;
	private SeleniumFileUtil _seleniumFileUtil;

}