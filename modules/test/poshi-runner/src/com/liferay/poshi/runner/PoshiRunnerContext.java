/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.poshi.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.DirectoryScanner;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerContext {

	public static Element getActionCommandElement(String classCommandName) {
		return _commandElementMap.get("action#" + classCommandName);
	}

	public static Element getActionRootElement(String className) {
		return _rootElementMap.get("action#" + className);
	}

	public static Element getFunctionCommandElement(String classCommandName) {
		return _commandElementMap.get("function#" + classCommandName);
	}

	public static Element getFunctionRootElement(String className) {
		return _rootElementMap.get("function#" + className);
	}

	public static Element getMacroCommandElement(String classCommandName) {
		return _commandElementMap.get("macro#" + classCommandName);
	}

	public static Element getMacroRootElement(String className) {
		return _rootElementMap.get("macro#" + className);
	}

	public static String getPathLocator(String pathLocatorKey) {
		return _pathLocatorMap.get(pathLocatorKey);
	}

	public static Element getTestcaseCommandElement(String classCommandName) {
		return _commandElementMap.get("testcase#" + classCommandName);
	}

	public static Element getTestcaseRootElement(String className) {
		return _rootElementMap.get("testcase#" + className);
	}

	private static void _initFileMaps() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_BASE_DIR);
		directoryScanner.setIncludes(
			new String[] {
				"**\\*.action", "**\\*.function", "**\\*.macro", "**\\*.path",
				"**\\*.testcase"
			});

		directoryScanner.scan();

		String[] filePaths = directoryScanner.getIncludedFiles();

		for (String filePath : filePaths) {
			String className = PoshiRunnerUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiRunnerUtil.getClassTypeFromFilePath(
				filePath);

			if (classType.equals("action") || classType.equals("function") ||
				classType.equals("macro") || classType.equals("testcase")) {

				Element element = PoshiRunnerUtil.getRootElementFromFilePath(
					_BASE_DIR + "/" + filePath);

				_rootElementMap.put(classType + "#" + className, element);

				if (element.element("set-up") != null) {
					Element setUpElement = element.element("set-up");

					String classCommandName = className + "#set-up";

					_commandElementMap.put(
						classType + "#" + classCommandName, setUpElement);
				}

				if (element.element("tear-down") != null) {
					Element tearDownElement = element.element("tear-down");

					String classCommandName = className + "#tear-down";

					_commandElementMap.put(
						classType + "#" + classCommandName, tearDownElement);
				}

				List<Element> commandElements = element.elements("command");

				for (Element commandElement : commandElements) {
					String classCommandName =
						className + "#" + commandElement.attributeValue("name");

					_commandElementMap.put(
						classType + "#" + classCommandName, commandElement);
				}
			}
			else if (classType.equals("path")) {
				Element rootElement =
					PoshiRunnerUtil.getRootElementFromFilePath(
						_BASE_DIR + "/" + filePath);

				Element bodyElement = rootElement.element("body");

				Element tableElement = bodyElement.element("table");

				Element tBodyElement = tableElement.element("tbody");

				List<Element> trElements = tBodyElement.elements("tr");

				for (Element trElement : trElements) {
					List<Element> tdElements = trElement.elements("td");

					Element locatorKeyElement = tdElements.get(0);
					Element locatorElement = tdElements.get(1);

					_pathLocatorMap.put(
						className + "#" + locatorKeyElement.getText(),
						locatorElement.getText());
				}
			}
		}
	}

	private static final String _BASE_DIR = PoshiRunnerUtil.getCanonicalPath(
		"../../../portal-web/test/functional/com/liferay/portalweb/");

	private static final Map<String, Element> _commandElementMap =
		new HashMap<String, Element>();
	private static final Map<String, String> _pathLocatorMap =
		new HashMap<String, String>();
	private static final Map<String, Element> _rootElementMap =
		new HashMap<String, Element>();

	static {
		try {
			_initFileMaps();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}