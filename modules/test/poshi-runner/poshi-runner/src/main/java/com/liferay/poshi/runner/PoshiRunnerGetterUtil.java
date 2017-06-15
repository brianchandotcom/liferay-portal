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

import com.liferay.poshi.runner.elements.PoshiElementFactory;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.ExternalMethod;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerGetterUtil {

	public static List<Element> getAllChildElements(
		Element element, String elementName) {

		List<Element> allChildElements = new ArrayList<>();

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			return allChildElements;
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals(elementName)) {
				allChildElements.add(childElement);
			}

			allChildElements.addAll(
				getAllChildElements(childElement, elementName));
		}

		return allChildElements;
	}

	public static String getCanonicalPath(String dir) {
		try {
			File file = new File(dir);

			return file.getCanonicalPath();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return dir;
	}

	public static String getClassCommandName(
		String className, String commandName) {

		StringBuilder sb = new StringBuilder(3);

		sb.append(className);
		sb.append("#");
		sb.append(commandName);

		return sb.toString();
	}

	public static String getClassNameFromClassCommandName(
		String classCommandName) {

		if (classCommandName.contains("#")) {
			int x = classCommandName.indexOf("#");

			return classCommandName.substring(0, x);
		}

		return classCommandName;
	}

	public static String getClassNameFromFilePath(String filePath) {
		int x = filePath.lastIndexOf("/");
		int y = filePath.lastIndexOf(".");

		if ((x == -1) && OSDetector.isWindows()) {
			x = filePath.lastIndexOf("\\");
		}

		return filePath.substring(x + 1, y);
	}

	public static String getClassTypeFromFileExtension(String fileExtension) {
		String classType = fileExtension;

		if (fileExtension.equals("testcase")) {
			classType = "test-case";
		}

		return classType;
	}

	public static String getClassTypeFromFilePath(String filePath) {
		String fileExtension = getFileExtensionFromFilePath(filePath);

		return getClassTypeFromFileExtension(fileExtension);
	}

	public static String getCommandNameFromClassCommandName(
		String classCommandName) {

		int x = classCommandName.indexOf("#");
		int y = classCommandName.indexOf("(");

		if (y != -1) {
			return classCommandName.substring(x + 1, y);
		}

		return classCommandName.substring(x + 1);
	}

	public static String getExtendedTestCaseName() {
		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			getClassNameFromClassCommandName(PropsValues.TEST_NAME));

		return getExtendedTestCaseName(rootElement);
	}

	public static String getExtendedTestCaseName(Element rootElement) {
		return rootElement.attributeValue("extends");
	}

	public static String getExtendedTestCaseName(String filePath) {
		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			getClassNameFromFilePath(filePath));

		return getExtendedTestCaseName(rootElement);
	}

	public static String getFileExtensionFromClassType(String classType) {
		String fileExtension = classType;

		if (fileExtension.equals("test-case")) {
			fileExtension = "testcase";
		}

		return fileExtension;
	}

	public static String getFileExtensionFromFilePath(String filePath) {
		int x = filePath.lastIndexOf(".");

		return filePath.substring(x + 1);
	}

	public static String getFileNameFromClassKey(String classKey) {
		int x = classKey.indexOf("#");
		int y = classKey.length();

		String classType = classKey.substring(0, x);
		String className = classKey.substring(x + 1, y);

		return className + "." + getFileExtensionFromClassType(classType);
	}

	public static String getFileNameFromFilePath(String filePath) {
		String className = getClassNameFromFilePath(filePath);
		String fileExtension = getFileExtensionFromFilePath(filePath);

		return className + "." + fileExtension;
	}

	public static Object getMethodReturnValue(
			List<String> args, String className, String methodName,
			Object object)
		throws Exception {

		Object[] parameters = new Object[args.size()];

		for (int i = 0; i < args.size(); i++) {
			String arg = args.get(i);

			Matcher matcher = _variablePattern.matcher(arg);

			Object parameter = null;

			if (matcher.matches()) {
				parameter = PoshiRunnerVariablesUtil.getValueFromCommandMap(
					matcher.group(1));
			}
			else {
				parameter = PoshiRunnerVariablesUtil.replaceCommandVars(arg);
			}

			if (className.endsWith("MathUtil") &&
				(parameter instanceof String)) {

				parameter = Integer.parseInt((String)parameter);
			}

			parameters[i] = parameter;
		}

		Object returnObject = null;

		if (object != null) {
			returnObject = ExternalMethod.execute(
				methodName, object, parameters);
		}
		else {
			returnObject = ExternalMethod.execute(
				className, methodName, parameters);
		}

		return returnObject;
	}

	public static String getProjectDirName() {
		return getCanonicalPath(PropsValues.PROJECT_DIR);
	}

	public static Element getRootElementFromFilePath(String filePath)
		throws Exception {

		String fileContent = FileUtil.read(filePath);

		if (!fileContent.contains("<definition") &&
			filePath.endsWith(".testcase")) {

			Element element = PoshiElementFactory.newPoshiElementFromFile(
				filePath);

			fileContent = Dom4JUtil.format(element);
		}

		boolean cdata = false;
		int lineNumber = 1;
		StringBuilder sb = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(
			new StringReader(fileContent));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			Matcher matcher = _tagPattern.matcher(line);

			if (line.contains("<![CDATA[") || cdata) {
				if (line.contains("]]>")) {
					cdata = false;
				}
				else {
					cdata = true;
				}

				if (line.contains("<![CDATA[") && matcher.find()) {
					for (String reservedTag : _reservedTags) {
						if (line.contains("<" + reservedTag)) {
							line = StringUtil.replace(
								line, matcher.group(),
								matcher.group() + " line-number=\"" +
									lineNumber + "\"");

							break;
						}
					}
				}
			}
			else if (matcher.find()) {
				boolean tagIsReservedTag = false;

				for (String reservedTag : _reservedTags) {
					if (line.contains("<" + reservedTag)) {
						line = StringUtil.replace(
							line, matcher.group(),
							matcher.group() + " line-number=\"" + lineNumber +
								"\"");

						tagIsReservedTag = true;

						break;
					}
				}

				if (!tagIsReservedTag) {
					int x = line.indexOf("<");

					int y = line.indexOf(" ", x);

					if (y == -1) {
						y = line.indexOf(">");

						if (y == -1) {
							y = line.indexOf(">");
						}
					}

					String tagName = line.substring(x + 1, y);

					throw new Exception(
						"Invalid \"" + tagName + "\" tag\n" + filePath + ":" +
							lineNumber);
				}
			}

			sb.append(line);

			lineNumber++;
		}

		String content = sb.toString();

		InputStream inputStream = new ByteArrayInputStream(
			content.getBytes("UTF-8"));

		SAXReader saxReader = new SAXReader();

		Document document = null;

		try {
			document = saxReader.read(inputStream);
		}
		catch (DocumentException de) {
			throw new Exception(
				de.getMessage() + "\nInvalid syntax in " + filePath, de);
		}

		Element rootElement = document.getRootElement();

		return rootElement;
	}

	public static Object getVarMethodValue(String classCommandName)
		throws Exception {

		int x = classCommandName.indexOf("(");
		int y = classCommandName.lastIndexOf(")");

		String className = getClassNameFromClassCommandName(classCommandName);
		String commandName = getCommandNameFromClassCommandName(
			classCommandName);

		List<String> args = new ArrayList<>();

		if ((x + 1) < y) {
			String parameterString = classCommandName.substring(x + 1, y);

			Matcher parameterMatcher = _parameterPattern.matcher(
				parameterString);

			while (parameterMatcher.find()) {
				String parameterValue = parameterMatcher.group();

				if (parameterValue.startsWith("'") &&
					parameterValue.endsWith("'")) {

					parameterValue = parameterValue.substring(
						1, parameterValue.length() - 1);
				}
				else if (parameterValue.contains("#")) {
					parameterValue = PoshiRunnerContext.getPathLocator(
						parameterValue);
				}

				if (parameterValue.contains("\'")) {
					parameterValue = parameterValue.replaceAll("\\\\'", "'");
				}

				args.add(parameterValue);
			}
		}

		Object returnObject = null;

		if (className.equals("selenium")) {
			Object object = SeleniumUtil.getSelenium();

			returnObject = getMethodReturnValue(
				args, className, commandName, object);
		}
		else {
			className = "com.liferay.poshi.runner.util." + className;

			returnObject = getMethodReturnValue(
				args, className, commandName, null);
		}

		return returnObject;
	}

	private static final Pattern _parameterPattern = Pattern.compile(
		"('([^'\\\\]|\\\\.)*'|[^',\\s]+)");
	private static final List<String> _reservedTags = Arrays.asList(
		new String[] {
			"and", "arg", "body", "case", "command", "condition", "contains",
			"default", "definition", "description", "echo", "else", "elseif",
			"equals", "execute", "fail", "for", "if", "head", "html", "isset",
			"not", "off", "on", "or", "property", "return", "set-up", "table",
			"take-screenshot", "task", "tbody", "td", "tear-down", "thead",
			"then", "title", "toggle", "tr", "var", "while"
		});
	private static final Pattern _tagPattern = Pattern.compile("<[a-z\\-]+");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\$\\{([^}]*)\\}");

}