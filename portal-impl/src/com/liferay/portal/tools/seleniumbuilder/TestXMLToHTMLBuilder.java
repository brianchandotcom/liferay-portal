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

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class TestXMLToHTMLBuilder extends SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new TestXMLToHTMLBuilder(args);
	}

	public TestXMLToHTMLBuilder(String[] args) throws Exception {
		super(args);

		_baseActionsMap = _getBaseActionsMap();
		_macrosMap = _getMacrosMap();
		_pathsMap = _getPathsMap();

		for (String fileName : fileNameSetTests) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			generateTest(fileName);
		}

		generateAPIFile();
	}

	protected void createTestEntry(
			String testName, String testFilePath, String description,
			Element rootElement)
		throws Exception {

		String[] testData = new String[4];

		_allTests.put(testName, testData);

		_allTests.get(testName)[0] = description;

		_allTests.get(testName)[1] = testFilePath;

		_allTests.get(testName)[2] = getObjectsReferenced(rootElement);

		_allTests.get(testName)[3] = getOutlineLink(testFilePath, testName);

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

	protected void generateAPIFile() throws Exception {

		String root = "com/liferay/portalweb/blocks/styles/templates/";

		StringBundler sb = new StringBundler();

		String headerTemplate = readFile(root + "testapi/head.html");

		sb.append(headerTemplate);

		for (String key : _allTests.keySet()) {
			String blockTemplate = readFile(root + "testapi/block.html");

			String testName = key;
			String description = _allTests.get(testName)[0];
			String testFilePath = _allTests.get(testName)[1];
			String objectsReferenced = _allTests.get(testName)[2];
			String outlineLink = _allTests.get(testName)[3];

			blockTemplate = blockTemplate.replace("${testname}", testName);

			blockTemplate = blockTemplate.replace(
				"${description}", description);

			blockTemplate = blockTemplate.replace(
				"${outlinelink}", outlineLink);

			int x = testFilePath.indexOf("tests");

			String location = testFilePath.substring(x + 6);

			String[] dirs = location.split("/");

			StringBundler locationFinal = new StringBundler();

			int tabs = 1;

			for (String dir : dirs) {
				for (int j = 0; j < tabs; j++) {
					locationFinal.append("&rarr; ");
				}

				locationFinal.append("/" + dir);

				locationFinal.append("<br>");

				tabs++;
			}

			blockTemplate = blockTemplate.replace(
				"${location}", locationFinal.toString());

			blockTemplate = blockTemplate.replace(
				"${pageobjects}", objectsReferenced);

			sb.append(blockTemplate);

			sb.append("</body>\n");
			sb.append("</html>\n");
		}

		String testAPIPath = "com/liferay/portalweb/tests/TestAPI.html";

		writeFile(testAPIPath, sb.toString(), false);
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

		String description = rootElement.attributeValue("description");

		if (description == null) {
			description = "Description Pending";
		}

		createTestEntry(testName, testFilePath, description, rootElement);

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
		sb.append(description);
		sb.append("</div>\n");

		sb.append(getSetup(rootElement));

		sb.append(getSteps(rootElement));

		sb.append(getTeardown(rootElement));

		sb.append("</div>\n");

		sb.append("</body>\n");

		sb.append("</html>");

		writeFile(testFileName, sb.toString(), false);
	}

	protected String getActionCommand(Element command) {
		StringBundler sb = new StringBundler();

		String actionFileName = command.attributeValue("object");
		String actionCommand = command.attributeValue("command");
		String actionPath = command.attributeValue("path");
		String actionLocator = command.attributeValue("locator");
		String actionValue = command.attributeValue("value");

		String finalString = _baseActionsMap.get(actionCommand);

		String pageNameKey = actionFileName + "__PAGE_NAME";
		String pageName = _pathsMap.get(pageNameKey)[2];

		if (actionLocator == null) {
			String pathKey = actionFileName + "__" + actionPath;

			if (_pathsMap.containsKey(pathKey)) {
				String[] actionArray = _pathsMap.get(pathKey);

				finalString = StringUtil.replace(
					finalString, "${PATH.PAGE_NAME}", pageName);

				finalString = StringUtil.replace(
					finalString, "${PATH.DESCRIPTION}", actionArray[2]);

				finalString = StringUtil.replace(
					finalString, "${PATH.VALUE}", actionValue);
			}
			else {
				finalString = StringUtil.replace(
					finalString, "${PATH.PAGE_NAME}", pageName);

				finalString = StringUtil.replace(
					finalString, "${PATH.VALUE}", actionValue);
			}
		}
		else {
			finalString = StringUtil.replace(
				finalString, "${PATH.PAGE_NAME}", pageName);

			finalString = StringUtil.replace(
				finalString, "${PATH.VALUE}", actionValue);

			StringBundler elementDescription = new StringBundler();

			elementDescription.append("element with xpath ");
			elementDescription.append("<span class='xpath'>");
			elementDescription.append(actionLocator + "</span>");

			finalString = StringUtil.replace(
				finalString, "${PATH.DESCRIPTION}",
				elementDescription.toString());
		}

		sb.append(finalString);

		return sb.toString();
	}

	protected String getBlockFilePath(String fileName) throws Exception {
		String blockFilePath = "";

		for (String key : _macrosMap.keySet()) {
			String[] values = _macrosMap.get(key);

			if (values[2].equals(fileName)) {
				blockFilePath = values[3];
			}
		}

		for (String key : _pathsMap.keySet()) {
			String[] values = _pathsMap.get(key);

			if (values[0].equals(fileName)) {
				int x = values[3].indexOf(CharPool.PERIOD);

				blockFilePath = values[3].substring(0, x) + ".actions";
			}
		}

		return blockFilePath;
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
			else if (commandName.equals("if")) {
				sb.append(getIfCommand(command));
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

	protected String getIfCommand(Element command) {
		StringBundler sb = new StringBundler();

		String fileName = command.attributeValue("object");
		String elementPathName = command.attributeValue("path");

		String steps = getCommands(command);

		String pathKey = fileName + "__" + elementPathName;
		String pageNameKey = fileName + "__PAGE_NAME";

		String pageName = _pathsMap.get(pageNameKey)[2];

		String finalString = "In the ";
		finalString += "<strong>${PATH.PAGE_NAME}</strong>, ";
		finalString += "if <strong>${PATH.DESCRIPTION}</strong>";
		finalString += " is present, ";
		finalString += "perform the following steps:";

		if (_pathsMap.containsKey(pathKey)) {
			String[] actionArray = _pathsMap.get(pathKey);

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

	protected String getMacroCommand(Element command) {
		StringBundler sb = new StringBundler();

		String macroCommand = command.attributeValue("command");
		String macroFileName = command.attributeValue("object");

		String steps = "";

		try {
			steps = getMacroSteps(macroFileName, macroCommand);
		} catch (Exception e ) {
			steps = "MACRO FILE NOT FOUND";
		}

		String macroKey = macroFileName + "__" + macroCommand;

		if (_macrosMap.containsKey(macroKey)) {
			String[] macroArray = _macrosMap.get(macroKey);

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

	protected String getMacrosFilePath(String macroFileName) throws Exception {
		String macroFilePath = "";

		for (String key : _macrosMap.keySet()) {
			String[] value = _macrosMap.get(key);

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
			if (macro.attributeValue("command").equals(macroCommand)) {
				steps = getCommands(macro);
			}
		}

		return steps;
	}

	protected String getObjectsReferenced(Element rootElement)
		throws Exception {

		List<Element> sections = rootElement.elements();

		Set<String> objects = new TreeSet<String>();

		for (Element section : sections) {
			Set<String> newSet = new TreeSet<String>();

			objects.addAll(getAllObjects(section, newSet));
		}

		StringBundler sb = new StringBundler();

		for (String object : objects) {
			sb.append("<li>");
			sb.append(object);
			sb.append("</li>\n");
		}

		return sb.toString();
	}

	protected String getOutlineLink(String testFilePath, String testName) {
		int x = testFilePath.lastIndexOf("tests");

		String path = testFilePath.substring(x + 6);

		return path + "/" + testName + ".html";
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

	protected String getWhileCommand(Element command) {
		StringBundler sb = new StringBundler();

		String fileName = command.attributeValue("object");
		String elementPathName = command.attributeValue("path");

		String steps = getCommands(command);

		String pathKey = fileName + "__" + elementPathName;
		String pageNameKey = fileName + "__PAGE_NAME";

		String pageName = _pathsMap.get(pageNameKey)[2];

		String finalString = "In the ";
		finalString += "<strong>${PATH.PAGE_NAME}</strong>, ";
		finalString += "while <strong>${PATH.DESCRIPTION}</strong>";
		finalString += " is present, ";
		finalString += "perform the following steps:";

		if (_pathsMap.containsKey(pathKey)) {
			String[] actionArray = _pathsMap.get(pathKey);

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

	private Map<String, String> _getBaseActionsMap() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();

		String baseActionsXML =
			"com/liferay/portalweb/blocks/base/actions/Base.actions";

		Element rootElement = getRootElement(baseActionsXML);

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionCommand = actionDef.attributeValue("command");
			String actionDescription = actionDef.attributeValue("description");

			hashMap.put(actionCommand, actionDescription);
		}

		return hashMap;
	}

	private Set<String> _getMacroFileNames() throws Exception {
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

	private Map<String, String[]> _getMacrosMap() throws Exception {
		Map<String, String[]> hashMap = new HashMap<String, String[]>();

		Set<String> macroFilePaths = _getMacroFileNames();

		for (String macroFilePath : macroFilePaths) {
			int x = macroFilePath.lastIndexOf(StringPool.SLASH);
			int y = macroFilePath.indexOf(CharPool.PERIOD);

			String macroFileName = macroFilePath.substring(x + 1, y);

			Element rootElement = getRootElement(macroFilePath);

			List<Element> macroDefs = rootElement.elements("macrodef");

			for (Element macroDef : macroDefs) {
				String command = macroDef.attributeValue("command");
				String description = macroDef.attributeValue("description");

				String macroKey = macroFileName + "__" + command;

				String[] macroValue =
					{ command, description, macroFileName, macroFilePath };

				hashMap.put(macroKey, macroValue);
			}
		}

		return hashMap;
	}

	private Set<String> _getPathFileNames() throws Exception {
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

	private Map<String, String[]> _getPathsMap() throws Exception {
		Map<String, String[]> hashMap = new HashMap<String, String[]>();

		Set<String> pathFileNames = _getPathFileNames();

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

				hashMap.put(pathKey, pathValue);
			}
		}

		return hashMap;
	}

	private Map<String, String[]> _allTests = new HashMap<String, String[]>();
	private Map<String, String> _baseActionsMap;
	private Map<String, String[]> _macrosMap;
	private Map<String, String[]> _pathsMap;

}