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

import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerContext {

	public static Element getActionCommandElement(String classCommandName) {
		return _commandElements.get("action#" + classCommandName);
	}

	public static Element getActionRootElement(String className) {
		return _rootElements.get("action#" + className);
	}

	public static Element getFunctionCommandElement(String classCommandName) {
		return _commandElements.get("function#" + classCommandName);
	}

	public static Element getFunctionRootElement(String className) {
		return _rootElements.get("function#" + className);
	}

	public static Element getMacroCommandElement(String classCommandName) {
		return _commandElements.get("macro#" + classCommandName);
	}

	public static Element getMacroRootElement(String className) {
		return _rootElements.get("macro#" + className);
	}

	public static String getPathLocator(String pathLocatorKey) {
		return _pathLocators.get(pathLocatorKey);
	}

	public static int getSeleniumParameterCount(String commandName) {
		return _seleniumParameterCounts.get(commandName);
	}

	public static Element getTestcaseCommandElement(String classCommandName) {
		return _commandElements.get("testcase#" + classCommandName);
	}

	public static Element getTestcaseRootElement(String className) {
		return _rootElements.get("testcase#" + className);
	}

	private static void _readPoshiFiles() throws Exception {
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
			filePath = _BASE_DIR + "/" + filePath;

			String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
				filePath);

			if (classType.equals("action") || classType.equals("function") ||
				classType.equals("macro") || classType.equals("testcase")) {

				Element element = PoshiRunnerGetterUtil.getRootElementFromFilePath(
					filePath);

				_rootElements.put(classType + "#" + className, element);

				if (element.element("set-up") != null) {
					Element setUpElement = element.element("set-up");

					String classCommandName = className + "#set-up";

					_commandElements.put(
						classType + "#" + classCommandName, setUpElement);
				}

				if (element.element("tear-down") != null) {
					Element tearDownElement = element.element("tear-down");

					String classCommandName = className + "#tear-down";

					_commandElements.put(
						classType + "#" + classCommandName, tearDownElement);
				}

				List<Element> commandElements = element.elements("command");

				for (Element commandElement : commandElements) {
					String classCommandName =
						className + "#" + commandElement.attributeValue("name");

					_commandElements.put(
						classType + "#" + classCommandName, commandElement);
				}
			}
			else if (classType.equals("path")) {
				Element rootElement =
					PoshiRunnerGetterUtil.getRootElementFromFilePath(filePath);

				Element bodyElement = rootElement.element("body");

				Element tableElement = bodyElement.element("table");

				Element tBodyElement = tableElement.element("tbody");

				List<Element> trElements = tBodyElement.elements("tr");

				for (Element trElement : trElements) {
					List<Element> tdElements = trElement.elements("td");

					Element locatorKeyElement = tdElements.get(0);
					Element locatorElement = tdElements.get(1);

					_pathLocators.put(
						className + "#" + locatorKeyElement.getText(),
						locatorElement.getText());
				}
			}
		}
	}

	private static void _readSeleniumFiles() throws Exception {
		String[] fileNames = {
			"LiferaySelenium.java", "WebDriverToSeleniumBridge.java"
		};

		for (String fileName : fileNames) {
			String content = FileUtil.read(
				"src/com/liferay/poshi/runner/selenium/" + fileName);

			Matcher matcher = _pattern.matcher(content);

			while (matcher.find()) {
				String methodSignature = matcher.group();

				int x = methodSignature.indexOf(" ", 7);
				int y = methodSignature.indexOf("(");

				String commandName = methodSignature.substring(x + 1, y);

				int parameterCount = 0;

				int z = methodSignature.indexOf(")");

				String parameters = methodSignature.substring(y + 1, z);

				if (!parameters.equals("")) {
					parameterCount = StringUtil.count(parameters, ",") + 1;
				}

				_seleniumParameterCounts.put(commandName, parameterCount);
			}
		}

		_seleniumParameterCounts.put("open", 1);
	}

	private static final String _BASE_DIR = PoshiRunnerGetterUtil.getCanonicalPath(
		"../../../portal-web/test/functional/com/liferay/portalweb/");

	private static final Map<String, Element> _commandElements =
		new HashMap<>();
	private static final Map<String, String> _pathLocators = new HashMap<>();
	private static final Pattern _pattern = Pattern.compile(
		"public [a-z]* [A-Za-z0-9_]*\\(.*?\\)");
	private static final Map<String, Element> _rootElements = new HashMap<>();
	private static final Map<String, Integer> _seleniumParameterCounts =
		new HashMap<>();

	static {
		try {
			_readPoshiFiles();
			_readSeleniumFiles();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}