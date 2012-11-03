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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.InitUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class UnitsXMLToJavaBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new UnitsXMLToJavaBuilder(args);
	}

	public UnitsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			getSeleniumMethods();
			generateUnits(fileName);
		}
	}

	protected String getCommands(Element runBlock) {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		String runBlockName = runBlock.getName();

		for (Element command : commands) {
			String commandName = command.getName();

			if (commandName.equals("selenium")) {
				sb.append(getCommandSelenium(command));
			}
		}

		return sb.toString();
	}

	private String getCommandSelenium(Element command) {
		StringBundler sb = new StringBundler();

		String seleniumCommandName = command.attributeValue("command");

		int numParams = 0;

		if (_seleniumMethods.containsKey(seleniumCommandName)) {
			numParams = _seleniumMethods.get(seleniumCommandName);
		}

		sb.append("selenium.");
		sb.append(seleniumCommandName);
		sb.append("(");

		if (numParams > 0) {
			sb.append("params[0]");
		}

		if (numParams > 1) {
			sb.append(", params[1]");
		}

		sb.append(");\n");

		return sb.toString();
	}

	private Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\base\\units\\*.units"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	protected void getSeleniumMethods() throws Exception {
		String seleniumWrapperFileName =
			"com/liferay/portalweb/portal/util/liferayselenium/SeleniumWrapper.java";

		String fileContents = readFile(seleniumWrapperFileName);

		Pattern pattern = Pattern.compile("\\spublic\\svoid\\s(.*)\\{");

		Matcher matcher = pattern.matcher(fileContents);

		while (matcher.find()) {
			String methodDec = matcher.group();

			int leftParen = methodDec.indexOf("(");
			int rightParen = methodDec.indexOf(")");

			String name = methodDec.substring(13, leftParen);
			String params = methodDec.substring(leftParen + 1, rightParen);
			String[] paramsArray = params.split(",");

			int numParams;

			if (params.equals("")) {
				numParams = 0;
			}
			else {
				numParams = paramsArray.length;
			}

			_seleniumMethods.put(name, numParams);
		}

		seleniumWrapperFileName =
			"com/liferay/portalweb/portal/util/liferayselenium/LiferaySeleniumHelper.java";

		fileContents = readFile(seleniumWrapperFileName);

		pattern = Pattern.compile(
			"public\\sstatic\\svoid(.*)\\{" +
			"|public\\sstatic\\svoid(.*)\n(.*)\\{" +
			"|public\\sstatic\\svoid(.*)\n(.*)\n(.*)\\{" +
			"|public\\sstatic\\svoid(.*)\n(.*)\n(.*)\n(.*)\\{");

		matcher = pattern.matcher(fileContents);

		while (matcher.find()) {
			String methodDec = matcher.group();
			System.out.println(methodDec);

			int leftParen = methodDec.indexOf("(");
			int rightParen = methodDec.indexOf(")");

			String name = methodDec.substring(19, leftParen);
			String params = methodDec.substring(leftParen + 1, rightParen);
			String[] paramsArray = params.split(",");

			int numParams = paramsArray.length - 1;

			_seleniumMethods.put(name, numParams);
			System.out.println(name);
		}

		_seleniumMethods.put("clickAndWait", 1);
		_seleniumMethods.put("clickAtAndWait", 2);
	}

	protected String getUnitDefs(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		List<Element> unitDefs = rootElement.elements("unitdef");

		for (Element unitDef : unitDefs) {
			String unitDefName = unitDef.attributeValue("name");

			sb.append("public void ");
			sb.append(unitDefName);
			sb.append("(String param1, String param2) throws Exception {\n");

			sb.append(getCommands(unitDef));

			sb.append("}\n");
		}

		return sb.toString();
	}

	protected void generateUnits(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String unitsName = fileName.substring(x + 1, y) + "Units";

		String unitsFilePath = fileName.substring(0, x);

		String unitsPackagePath = StringUtil.replace(
			unitsFilePath, StringPool.SLASH, StringPool.PERIOD);

		String unitsFileName = unitsFilePath + "/" + unitsName + ".java";

		String unitsXMLFileName =
			unitsFilePath + "/" + fileName.substring(x + 1, y) + ".units";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(unitsPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("public class ");
		sb.append(unitsName);
		sb.append(" extends BaseActionUnits {\n\n");

		sb.append("public ");
		sb.append(unitsName);
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("}\n");

		System.out.println("unitsXMLFileName");
		System.out.println(unitsXMLFileName);

		Element rootElement = getRootElement(unitsXMLFileName);

		sb.append(getUnitDefs(rootElement));

		sb.append("}");

		writeFile(unitsFileName, sb.toString(), true);
	}

	private static Map<String, Integer> _seleniumMethods = new HashMap<String, Integer>();

}