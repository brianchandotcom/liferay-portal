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
public class FunctionsXMLToJavaBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new FunctionsXMLToJavaBuilder(args);
	}

	public FunctionsXMLToJavaBuilder(String[] args) throws Exception {
		super(args);

		Set<String> fileNames = getFileNames();

		getSeleniumMethods();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			generateFunctions(fileName);
		}
	}

	protected void generateFunctions(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String functionsFilePath = fileName.substring(0, x);
		String functionsName = fileName.substring(x + 1, y) + "Functions";

		String functionsFileName =
			functionsFilePath + "/" + functionsName + ".java";
		String functionsPackagePath = StringUtil.replace(
			functionsFilePath, StringPool.SLASH, StringPool.PERIOD);
		String functionsXMLFileName =
			functionsFilePath + "/" + fileName.substring(x + 1, y) +
				".functions";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(functionsPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("public class ");
		sb.append(functionsName);
		sb.append(" extends BaseFunctions {\n\n");

		sb.append("public ");
		sb.append(functionsName);
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("}\n");

		Element rootElement = getRootElement(functionsXMLFileName);

		sb.append(getFunctionDefs(rootElement, functionsName));

		sb.append("}");

		writeFile(functionsFileName, sb.toString(), true);
	}

	protected String getCommands(Element runBlock, String functionsName) {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		for (Element command : commands) {
			String commandName = command.getName();

			if (commandName.equals("else")) {
				sb.append(getCommandElse(command));
			}
			else if (commandName.equals("functions")) {
				sb.append(getObjectDeclaration(functionsName));
				sb.append(getCommandFunctions(command, functionsName));
			}
			else if (commandName.equals("selenium")) {
				sb.append(getCommandSelenium(command));
			}
			else if (commandName.equals("if")) {
				sb.append(getCommandIf(command));
			}
		}

		return sb.toString();
	}

	private String getCommandElse(Element command) {
		StringBundler sb = new StringBundler();

		sb.append("else {");
		sb.append(getCommands(command));
		sb.append("}");

		return sb.toString();
	}

	private String getCommandFunctions(Element command, String functionsName) {
		StringBundler sb = new StringBundler();

		String functionCommandName = command.attributeValue("command");

		sb.append(lowerCaseFirstLetter(functionsName));
		sb.append(StringPool.PERIOD);
		sb.append(functionCommandName);
		sb.append("(param1, param2);\n");

		return sb.toString();
	}

	private String getCommandIf(Element command) {
		StringBundler sb = new StringBundler();

		sb.append("if (");
		sb.append(getCommandSeleniumConditional(command));
		sb.append(") {");
		sb.append(getCommands(command));
		sb.append("}");

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

		String seleniumTargetName = command.attributeValue("target");

		if (!(seleniumTargetName == null)) {
			sb.append("\"");
			sb.append(seleniumTargetName);
			sb.append("\"");
		}
		else if (seleniumCommandName.equals("assertConfirmation") ||
				 seleniumCommandName.equals("assertTextNotPresent") ||
				 seleniumCommandName.equals("assertTextPresent") ||
				 seleniumCommandName.equals("waitForConfirmation") ||
				 seleniumCommandName.equals("waitForTextNotPresent") ||
				 seleniumCommandName.equals("waitForTextPresent")) {

			sb.append("param2");
		}
		else if (numParams > 0) {
			sb.append("param1");
		}

		String seleniumValueName = command.attributeValue("value");

		if (!seleniumCommandName.equals("open")) {
			if (!(seleniumValueName == null)) {
				sb.append(", \"");
				sb.append(seleniumValueName);
				sb.append("\"");
			}
			else if (numParams > 1) {
				sb.append(", param2");
			}
		}

		sb.append(");\n");

		return sb.toString();
	}

	private String getCommandSeleniumConditional(Element command) {
		StringBundler sb = new StringBundler();

		String seleniumCommandName = command.attributeValue("command");

		if (seleniumCommandName.contains("Not")) {
			sb.append("!");

			seleniumCommandName = StringUtil.replace(
				seleniumCommandName, "Not", "");
		}

		int numParams = 0;

		if (_seleniumMethods.containsKey(seleniumCommandName)) {
			numParams = _seleniumMethods.get(seleniumCommandName);
		}

		sb.append("selenium.");
		sb.append(seleniumCommandName);
		sb.append("(");

		String seleniumTargetName = command.attributeValue("target");

		if (!(seleniumTargetName == null)) {
			sb.append("\"");
			sb.append(seleniumTargetName);
			sb.append("\"");
		}
		else if (numParams > 0) {
			sb.append("param1");
		}

		String seleniumValueName = command.attributeValue("value");

		if (!seleniumCommandName.equals("open")) {
			if (!(seleniumValueName == null)) {
				sb.append(", \"");
				sb.append(seleniumValueName);
				sb.append("\"");
			}
			else if (numParams > 1) {
				sb.append(", param2");
			}
		}

		sb.append(")");

		return sb.toString();
	}

	private Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\base\\functions\\*.functions"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private String getFunctionDefs(Element rootElement, String functionsName)
		throws Exception {

		StringBundler sb = new StringBundler();

		List<Element> functionDefs = rootElement.elements("functiondef");

		for (Element functionDef : functionDefs) {
			String functionDefName = functionDef.attributeValue("name");

			sb.append("public void ");
			sb.append(functionDefName);
			sb.append("(String param1, String param2) throws Exception {\n");

			sb.append(getCommands(functionDef, functionsName));

			sb.append("}\n");
		}

		return sb.toString();
	}

	private String getObjectDeclaration(String functionsName) {

		StringBundler sb = new StringBundler();

		sb.append(functionsName);
		sb.append(" ");
		sb.append(lowerCaseFirstLetter(functionsName));
		sb.append(" = new ");
		sb.append(functionsName);
		sb.append("(selenium);\n");

		sb.append("\n");

		return sb.toString();
	}

	private void getSeleniumFileMethods(String file) throws Exception {
		String content = getNormalizedContent(file);

		Pattern pattern = Pattern.compile(
			"public (boolean|String|void) [A-Za-z0-9_]*\\(.*?\\)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodDec = matcher.group();

			int x = methodDec.indexOf("(");
			int y = methodDec.indexOf(")");

			String name = null;

			if (methodDec.startsWith("public boolean")) {
				name = methodDec.substring(15, x);
			}
			else if (methodDec.startsWith("public String")) {
				name = methodDec.substring(14, x);
			}
			else if (methodDec.startsWith("public void")) {
				name = methodDec.substring(12, x);
			}

			String params = methodDec.substring(x + 1, y);

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
	}

	private void getSeleniumMethods() throws Exception {
		getSeleniumFileMethods(
			"com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java");
		getSeleniumFileMethods(
			"com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java");
	}

	private static Map<String, Integer> _seleniumMethods =
		new HashMap<String, Integer>();

}