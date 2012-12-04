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

		sb.append("<html>\n");

		sb.append("<head>\n");
		sb.append("</head>\n");

		sb.append("<body>\n");

		Element rootElement = getRootElement(fileName);

		sb.append("<h1>");
		sb.append(rootElement.attributeValue("name"));
		sb.append("</h1>\n");

		sb.append("<p>");
		sb.append(rootElement.attributeValue("description"));
		sb.append("</p>\n");

		sb.append(getSetup(rootElement));

		sb.append(getSteps(rootElement));

		sb.append(getTeardown(rootElement));

		sb.append("</body>\n");

		sb.append("</html>");

		writeFile(testFileName, sb.toString(), false);
	}

	protected String getSetup(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("setup");

		sb.append("<h2>Setup</h2>\n");

		sb.append("<ol>\n");

		sb.append("<li>Log into the portal as <strong>test@liferay.com</strong> with the password <strong>test</strong>.</li>\n");

		sb.append(getCommands(runBlock));

		sb.append("</ol>\n");

		return sb.toString();
	}

	protected String getSteps(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("steps");

		sb.append("<h2>Steps</h2>\n");

		sb.append("<ol>\n");

		sb.append(getCommands(runBlock));

		sb.append("</ol>\n");

		return sb.toString();
	}

	protected String getTeardown(Element rootElement) throws Exception {
		StringBundler sb = new StringBundler();

		Element runBlock = rootElement.element("teardown");

		sb.append("<h2>Teardown</h2>\n");

		sb.append("<ol>\n");

		sb.append(getCommands(runBlock));

		sb.append("<li>Log out of the portal.</li>\n");

		sb.append("</ol>\n");

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
				String actionName = command.attributeValue("name");
				String actionPath = command.attributeValue("path");
				String actionValue = command.attributeValue("value");

				String pathKey = actionName + "__" + actionPath;
				String pageNameKey = actionName + "__PAGE_NAME";

				String pageName = pathsMap.get(pageNameKey)[2];

				if (pathsMap.containsKey(pathKey)) {
					String[] actionArray = pathsMap.get(pathKey);
					String[] baseActionArray = baseActionsMap.get(actionCommand);

					String finalString = baseActionArray[1];

					finalString = StringUtil.replace(
						finalString, "${PATH.PAGE_NAME}", pageName);

					finalString = StringUtil.replace(
						finalString, "${PATH.DESCRIPTION}", actionArray[2]);

					finalString = StringUtil.replace(
						finalString, "${PATH.VALUE}", actionValue);

					sb.append(finalString);
				}
				else {
					String[] baseActionArray = baseActionsMap.get(actionCommand);

					String finalString = baseActionArray[1];

					finalString = StringUtil.replace(
						finalString, "${PATH.PAGE_NAME}", pageName);

					finalString = StringUtil.replace(
						finalString, "${PATH.VALUE}", actionValue);

					sb.append(finalString);
				}
			}
			else if (commandName.equals("macro")) {
				String macroCommand = command.attributeValue("command");
				String macroName = command.attributeValue("name");

				String macroKey = macroName + "__" + macroCommand;

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
				}
			}

			sb.append("</li>\n");
		}

		return sb.toString();
	}

	private Map<String, String[]> getBaseActionsMap() throws Exception {
		Map<String, String[]> hashMap = new HashMap<String, String[]>();

		String baseActionsXML =
			"com/liferay/portalweb/blocks/base/actions/Base.actions";

		Element rootElement = getRootElement(baseActionsXML);

		List<Element> actionDefs = rootElement.elements("actiondef");

		for (Element actionDef : actionDefs) {
			String actionName = actionDef.attributeValue("name");
			String actionDescription = actionDef.attributeValue("description");

			String[] actionValue = {
				actionName, actionDescription
			};

			hashMap.put(actionName, actionValue);
		}

		return hashMap;
	}

	private Set<String> getMacroFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.macros"
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
		Map<String, String[]> hashMap = new HashMap<String, String[]>();

		Set<String> macroFileNames = getMacroFileNames();

		for (String macroFileName : macroFileNames) {
			int x = macroFileName.lastIndexOf(StringPool.SLASH);
			int y = macroFileName.indexOf(CharPool.PERIOD);

			String macroName = macroFileName.substring(x + 1, y);

			Element rootElement = getRootElement(macroFileName);

			List<Element> macroDefs = rootElement.elements("macrodef");

			for (Element macroDef : macroDefs) {
				String name = macroDef.attributeValue("name");
				String description = macroDef.attributeValue("description");

				String macroKey = macroName + "__" + name;

				String[] macroValue = { macroKey, description };

				hashMap.put(macroKey, macroValue);
			}
		}

		return hashMap;
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
		Map<String, String[]> hashMap = new HashMap<String, String[]>();

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

				hashMap.put(pathKey, pathValue);
			}
		}

		return hashMap;
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

	private Map<String, String[]> baseActionsMap;
	private Map<String, String[]> macrosMap;
	private Map<String, String[]> pathsMap;

}