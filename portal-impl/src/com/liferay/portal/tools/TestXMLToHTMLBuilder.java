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

	protected void getDependencies(String testFilePath) throws Exception {
		String sourcePath = basedir + "/com/liferay/portalweb/blocks/styles/";

		String destinationPath = basedir + "/" + testFilePath + "/";

		if (!FileUtil.exists(destinationPath + "style.css")) {
			FileUtil.copyFile(sourcePath + "style.css",
				destinationPath + "style.css");
		}

		if (!FileUtil.exists(destinationPath + "jquery.js")) {
			FileUtil.copyFile(sourcePath + "jquery.js",
				destinationPath + "jquery.js");
		}

		if (!FileUtil.exists(destinationPath + "scripts.js")) {
			FileUtil.copyFile(sourcePath + "scripts.js",
				destinationPath + "scripts.js");
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

		getDependencies(testFilePath);

		StringBundler sb = new StringBundler();

		Element rootElement = getRootElement(fileName);

		sb.append("<html>\n");

		sb.append("<head>\n");
		sb.append("<title>Test Outline:");

		sb.append(rootElement.attributeValue("name"));
		sb.append("</title>\n");
		sb.append("<script type='text/javascript' src='jquery.js'></script>");
		sb.append("<script type='text/javascript' src='scripts.js'></script>");
		sb.append("<link rel='stylesheet' href='style.css'>");
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

	protected String getCommands(Element runBlock) {
		StringBundler sb = new StringBundler();

		List<Element> commands = runBlock.elements();

		for (Element command : commands) {
			String commandName = command.getName();

			sb.append("<li>");

			if (commandName.equals("action")) {
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
			}
			else if (commandName.equals("macro")) {
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

					String finalString = macroArray[1];

					List<Element> params = command.elements("param");

					for (Element param : params) {
						String paramName = param.attributeValue("name");
						String paramValue = param.attributeValue("value");

						finalString = StringUtil.replace(
							finalString, "${" + paramName + "}", paramValue);
					}

					sb.append(finalString);

					sb.append("<div class='expand-macro-steps'>\n");
					sb.append("<a href='#'>Expand Macro Steps</a>\n");
					sb.append("<div class='macro-steps'>\n");
					sb.append("<ol>\n");
					sb.append(steps);
					sb.append("\n</ol>\n");
					sb.append("</div>\n");
					sb.append("</div>\n");
				}
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
					pathsName, key, value
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