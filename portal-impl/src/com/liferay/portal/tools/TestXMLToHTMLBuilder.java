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

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class TestXMLToHTMLBuilder extends SeleniumXMLToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new TestXMLToHTMLBuilder(args);
	}

	public TestXMLToHTMLBuilder(String[] args) throws Exception {
		super(args);

		baseActionsMap = getBaseActionsMap();
		macrosMap = getMacrosMap();
		pathsMap = getPathsMap();

		Set<String> testFileNames = getTestFileNames();

		for (String testFileName : testFileNames) {
			if (testFileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + testFileName);
			}

			generateTest(testFileName);
		}
	}

	protected void generateTest(String fileName) throws Exception {
		if (!FileUtil.exists(basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testName = fileName.substring(x + 1, y) + "Test";
		String testFilePath = fileName.substring(0, x) + "/html";

		String testFileName = testFilePath + "/" + testName + ".html";

		StringBundler sb = new StringBundler();

		Element rootElement = getRootElement(fileName);

		sb.append("<html>\n");

		sb.append("<head>\n");
		sb.append("<title>Test Outline:");

		sb.append(rootElement.attributeValue("name"));
		sb.append("</title>\n");

		sb.append("<script type='text/javascript' src='");
		sb.append(getDependenciesPath(fileName));
		sb.append("jquery.js'></script>");

		sb.append("<script type='text/javascript' src='");
		sb.append(getDependenciesPath(fileName));
		sb.append("scripts.js'></script>");

		sb.append("<link rel='stylesheet' href='");
		sb.append(getDependenciesPath(fileName));
		sb.append("style.css'>");
		sb.append("</head>\n");

		sb.append("<body>\n");

		sb.append("<div id='test-outline'>\n");

		sb.append("<div class='test-name'>\n");
		sb.append(rootElement.attributeValue("name"));
		sb.append("</div>\n");

		sb.append("<div class='block-head'>\n");
		sb.append("Test Description");
		sb.append("</div>\n");

		sb.append("<div class='description-content'>\n");
		sb.append(rootElement.attributeValue("description"));
		sb.append("</div>\n");

		sb.append(getSetup(rootElement));

		sb.append(getSteps(rootElement));

		sb.append(getTeardown(rootElement));

		sb.append("</div>\n");

		sb.append("</body>\n");

		sb.append("</html>");

		writeFile(testFileName, sb.toString(), false);
	}

	protected String getSetup(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("setup");

		sb.append("<div class='block-head'>Setup</div>\n");

		sb.append("<div class='test-block'>\n");

		sb.append("<ol>\n");

		sb.append("<li>Log into the portal as ");
		sb.append("<strong>test@liferay.com</strong> ");
		sb.append("with the password <strong>test</strong>.</li>\n");

		sb.append(getCommands(runBlock));

		sb.append("</ol>\n");

		sb.append("</div>\n");

		return sb.toString();
	}

	protected String getDependenciesPath(String localPath) throws Exception {
		String stylesPath = "com/liferay/portalweb/blocks/styles";

		int x = localPath.indexOf("test");

		String pathFromPortalWeb = localPath.substring(x);

		String[] split = pathFromPortalWeb.split("/");

		int numDirectories = split.length;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < numDirectories; i++) {
			sb.append("../");
		}

		sb.append("blocks/styles/");

		return sb.toString();
	}

	protected String getSteps(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("steps");

		sb.append("<div class='block-head'>Steps</div>\n");

		sb.append("<div class='test-block'>\n");

		sb.append("<ol>\n");

		sb.append(getCommands(runBlock));

		sb.append("</ol>\n");

		sb.append("</div>\n");

		return sb.toString();
	}

	protected String getTeardown(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("teardown");

		sb.append("<div class='block-head'>Teardown</div>\n");

		sb.append("<div class='test-block'>\n");

		sb.append("<ol>\n");

		sb.append(getCommands(runBlock));

		sb.append("<li>Log out of the portal.</li>\n");

		sb.append("</ol>\n");

		sb.append("</div>\n");

		return sb.toString();
	}

	protected String getActionCommand(Element command) {
		StringBundler sb = new StringBundler();

		String actionCommand = command.attributeValue("command");
		String actionLocator = command.attributeValue("locator");
		String actionFileName = command.attributeValue("name");
		String actionPath = command.attributeValue("path");
		String actionValue = command.attributeValue("value");

		String pathKey = actionFileName + "__" + actionPath;
		String pageNameKey = actionFileName + "__PAGE_NAME";

		String pageName = pathsMap.get(pageNameKey)[2];

		if (pathsMap.containsKey(pathKey)) {
			String[] actionArray = pathsMap.get(pathKey);

			String finalString = baseActionsMap.get(actionCommand);

			finalString = StringUtil.replace(
				finalString, "${PATH.PAGE_NAME}", pageName);

			finalString = StringUtil.replace(
				finalString, "${PATH.DESCRIPTION}", actionArray[2]);

			finalString = StringUtil.replace(
				finalString, "${PATH.VALUE}", actionValue);

			sb.append(finalString);
		}
		else {
			String finalString = baseActionsMap.get(actionCommand);

			finalString = StringUtil.replace(
				finalString, "${PATH.PAGE_NAME}", pageName);

			finalString = StringUtil.replace(
				finalString, "${PATH.VALUE}", actionValue);

			sb.append(finalString);
		}

		return sb.toString();
	}

	protected String getWhileCommand(Element command) {
		StringBundler sb = new StringBundler();

		String fileName = command.attributeValue("name");
		String elementPathName = command.attributeValue("path");

		String steps = getCommands(command);

		String pathKey = fileName + "__" + elementPathName;
		String pageNameKey = fileName + "__PAGE_NAME";

		String pageName = pathsMap.get(pageNameKey)[2];

		String finalString = "In the ";
		finalString += "<strong>${PATH.PAGE_NAME}</strong>, ";
		finalString += "while <strong>${PATH.DESCRIPTION}</strong>";
		finalString += " is present, ";
		finalString += "perform the following steps:";

		if (pathsMap.containsKey(pathKey)) {
			String[] actionArray = pathsMap.get(pathKey);

			finalString = StringUtil.replace(
				finalString, "${PATH.PAGE_NAME}", pageName);

			finalString = StringUtil.replace(
				finalString, "${PATH.DESCRIPTION}", actionArray[2]);

			sb.append(finalString);
		}
		else {
			finalString = StringUtil.replace(
				finalString, "${PATH.PAGE_NAME}", pageName);

			sb.append(finalString);
		}

		sb.append("<ol>\n");

		sb.append(dereferenceParams(command, steps));

		sb.append("\n</ol>\n");
		
		return sb.toString();
	}

	protected String getBlockFilePath(String fileName) throws Exception {
		String blockFilePath = "";

		for (String key : macrosMap.keySet()) {
			String[] values = macrosMap.get(key);

			if (values[2].equals(fileName)) {
				blockFilePath = values[3];
			}
		}

		for (String key : pathsMap.keySet()) {
			String[] values = pathsMap.get(key);

			if (values[0].equals(fileName)) {
				int x = values[3].indexOf(CharPool.PERIOD);

				blockFilePath = values[3].substring(0, x) + ".actions";
			}
		}

		return blockFilePath;
	}

	protected String getMacroCommand(Element command) {
		StringBundler sb = new StringBundler();

		String macroCommand = command.attributeValue("command");
		String macroFileName = command.attributeValue("name");

		String steps = "";

		try {
			steps = getMacroSteps(macroFileName, macroCommand);
		} catch (Exception e ) {
			steps = "MACRO FILE NOT FOUND";
		}

		String macroKey = macroFileName + "__" + macroCommand;

		if (macrosMap.containsKey(macroKey)) {
			String[] macroArray = macrosMap.get(macroKey);

			sb.append(dereferenceParams(command, macroArray[1]));

			sb.append("<div class='expand-macro-steps'>\n");
			sb.append("<a href='#'>Expand Macro Steps</a>\n");
			sb.append("<div class='macro-steps'>\n");
			sb.append("<ol>\n");

			sb.append(dereferenceParams(command, steps));

			sb.append("\n</ol>\n");
			sb.append("</div>\n");
			sb.append("</div>\n");
		}

		return sb.toString();
	}

	protected String dereferenceParams(Element commandElement, String command) {
		String finalString = command;

		List<Element> params = commandElement.elements("param");

		for (Element param : params) {
			String paramName = param.attributeValue("name");
			String paramValue = param.attributeValue("value");

			finalString = StringUtil.replace(
				finalString, "${" + paramName + "}", paramValue);
		}

		return finalString;
	}

	protected String getCommands(Element runBlock) {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		for (Element command : commands) {
			String commandName = command.getName();

			sb.append("<li>");

			if (commandName.equals("action")) {
				sb.append(getActionCommand(command));
			}
			else if (commandName.equals("macro")) {
				sb.append(getMacroCommand(command));
			}
			else if (commandName.equals("while")) {
				sb.append(getWhileCommand(command));
			}

			sb.append("</li>\n");
		}

		return sb.toString();
	}

	private Map<String, String> getBaseActionsMap() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();

		String baseActionsXML =
			"com/liferay/portalweb/blocks/base/actions/Base.actions";

		Element rootElement = getRootElement(baseActionsXML);

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionName = actionDef.attributeValue("name");
			String actionDescription = actionDef.attributeValue("description");

			hashMap.put(actionName, actionDescription);
		}

		return hashMap;
	}

	private Set<String> getMacroFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\blocks\\**\\*.macros"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Map<String, String[]> getMacrosMap() throws Exception {
		Map<String, String[]> macrosMap = new HashMap<String, String[]>();

		Set<String> macroFilePaths = getMacroFileNames();

		for (String macroFilePath : macroFilePaths) {
			int x = macroFilePath.lastIndexOf(StringPool.SLASH);
			int y = macroFilePath.indexOf(CharPool.PERIOD);

			String macroFileName = macroFilePath.substring(x + 1, y);

			Element rootElement = getRootElement(macroFilePath);

			List<Element> macroDefs = rootElement.elements("macrodef");

			for (Element macroDef : macroDefs) {
				String name = macroDef.attributeValue("name");
				String description = macroDef.attributeValue("description");

				String macroKey = macroFileName + "__" + name;

				String[] macroValue =
					{ name, description, macroFileName, macroFilePath };

				macrosMap.put(macroKey, macroValue);
			}
		}

		return macrosMap;
	}

	protected String getMacrosFilePath(String macroFileName) throws Exception {
		String macroFilePath = "";

		for (String key : macrosMap.keySet()) {
			String[] value = macrosMap.get(key);

			if (value[2].equals(macroFileName)) {
				macroFilePath = value[3];
			}
		}

		return macroFilePath;
	}

	protected String getMacroSteps(String macroFileName, String macroCommand)
		throws Exception {

		String macrosFilePath = getMacrosFilePath(macroFileName);

		Element rootElement = getRootElement(macrosFilePath);

		List<Element> macros = rootElement.elements();
		String steps = "";

		for (Element macro : macros) {
			if (macro.attributeValue("name").equals(macroCommand)) {
				steps = getCommands(macro);
			}
		}

		return steps;
	}

	private Set<String> getPathFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.paths"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Map<String, String[]> getPathsMap() throws Exception {
		Map<String, String[]> pathsMap = new HashMap<String, String[]>();

		Set<String> pathFileNames = getPathFileNames();

		for (String pathFileName : pathFileNames) {
			int x = pathFileName.lastIndexOf(StringPool.SLASH);
			int y = pathFileName.indexOf(CharPool.PERIOD);

			String pathsName = pathFileName.substring(x + 1, y);

			Element rootElement = getRootElement(pathFileName);
			Element bodyElement = rootElement.element("body");
			Element tableElement = bodyElement.element("table");
			Element tbodyElement = tableElement.element("tbody");

			List<Element> elementList = tbodyElement.elements("tr");

			for (Element element : elementList) {
				List<Element> paramList = element.elements("td");

				String key = paramList.get(0).getText();
				String locator = paramList.get(1).getText();
				String value = paramList.get(2).getText();

				String pathKey = pathsName + "__" + key;
				String[] pathValue = {
					pathsName, key, value, pathFileName
				};

				pathsMap.put(pathKey, pathValue);
			}
		}

		return pathsMap;
	}

	private Set<String> getTestFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.test"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Map<String, String> baseActionsMap;
	private Map<String, String[]> macrosMap;
	private Map<String, String[]> pathsMap;

}