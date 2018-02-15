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

import com.liferay.poshi.runner.elements.PoshiElement;
import com.liferay.poshi.runner.elements.PoshiNode;
import com.liferay.poshi.runner.elements.PoshiNodeFactory;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.ExternalMethod;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.net.URL;

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

	public static String getClassCommandNameFromNamespacedClassCommandName(
		String namespacedClassCommandName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassCommandName);

		if (matcher.find()) {
			String className = matcher.group("className");

			String commandName = matcher.group("commandName");

			if (Validator.isNotNull(commandName)) {
				return className + "#" + commandName;
			}

			return className;
		}

		throw new RuntimeException(
			"Unable to find class name and/or command name in " +
				namespacedClassCommandName);
	}

	public static String getClassNameFromFilePath(String filePath) {
		int x = filePath.lastIndexOf("/");
		int y = filePath.lastIndexOf(".");

		if ((x == -1) && OSDetector.isWindows()) {
			x = filePath.lastIndexOf("\\");
		}

		return filePath.substring(x + 1, y);
	}

	public static String getClassNameFromNamespacedClassCommandName(
		String namespacedClassCommandName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassCommandName);

		if (matcher.find()) {
			return matcher.group("className");
		}

		throw new RuntimeException(
			"Unable to find class name in " + namespacedClassCommandName);
	}

	public static String getClassNameFromNamespacedClassName(
		String namespacedClassName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassName);

		if (matcher.find()) {
			return matcher.group("className");
		}

		throw new RuntimeException(
			"Unable to find class name in " + namespacedClassName);
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

	public static String getCommandNameFromNamespacedClassCommandName(
		String namespacedClassCommandName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassCommandName);

		if (matcher.find()) {
			String commandName = matcher.group("commandName");

			if (Validator.isNull(commandName)) {
				return namespacedClassCommandName;
			}

			return commandName;
		}

		throw new RuntimeException(
			"Unable to find command name in " + namespacedClassCommandName);
	}

	public static String getExtendedTestCaseName() {
		String testName = PropsValues.TEST_NAME;

		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			getClassNameFromNamespacedClassCommandName(testName),
			getNamespaceFromNamespacedClassCommandName(testName));

		return getExtendedTestCaseName(rootElement);
	}

	public static String getExtendedTestCaseName(Element rootElement) {
		return rootElement.attributeValue("extends");
	}

	public static String getExtendedTestCaseName(String filePath) {
		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			getClassNameFromFilePath(filePath),
			PoshiRunnerContext.getNamespaceFromFilePath(filePath));

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

	public static String getNamespacedClassNameFromNamespacedClassCommandName(
		String namespacedClassCommandName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassCommandName);

		if (matcher.find()) {
			String namespace = matcher.group("namespace");

			if (Validator.isNull(namespace)) {
				namespace = PoshiRunnerContext.getDefaultNamespace();
			}

			String className = matcher.group("className");

			return namespace + "." + className;
		}

		throw new RuntimeException(
			"Unable to find class name in " + namespacedClassCommandName);
	}

	public static String getNamespaceFromNamespacedClassCommandName(
		String namespacedClassCommandName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassCommandName);

		if (matcher.find()) {
			String namespace = matcher.group("namespace");

			if (Validator.isNull(namespace)) {
				namespace = PoshiRunnerContext.getDefaultNamespace();
			}

			return namespace;
		}

		throw new RuntimeException(
			"Unable to find namespace in " + namespacedClassCommandName);
	}

	public static String getNamespaceFromNamespacedClassName(
		String namespacedClassName) {

		Matcher matcher = _namespacedClassCommandNamePattern.matcher(
			namespacedClassName);

		if (matcher.find()) {
			String namespace = matcher.group("namespace");

			if (Validator.isNull(namespace)) {
				namespace = PoshiRunnerContext.getDefaultNamespace();
			}

			return namespace;
		}

		throw new RuntimeException(
			"Unable to find namespace in " + namespacedClassName);
	}

	public static String getProjectDirName() {
		return getCanonicalPath(PropsValues.PROJECT_DIR);
	}

	public static Element getRootElementFromURL(URL url) throws Exception {
		String fileContent = FileUtil.read(url);
		String filePath = url.getFile();

		if (!fileContent.contains("<definition") &&
			filePath.endsWith(".testcase")) {

			PoshiNode<?, ?> poshiNode = PoshiNodeFactory.newPoshiNodeFromFile(
				filePath);

			if (poshiNode instanceof PoshiElement) {
				fileContent = Dom4JUtil.format((PoshiElement)poshiNode);
			}
		}

		BufferedReader bufferedReader = new BufferedReader(
			new StringReader(fileContent));

		boolean cdata = false;
		String line = null;
		int lineNumber = 1;
		StringBuilder sb = new StringBuilder();

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

	public static Object getVarMethodValue(String expression, String namespace)
		throws Exception {

		List<String> args = new ArrayList<>();

		int x = expression.indexOf("(");
		int y = expression.lastIndexOf(")");

		if ((x + 1) < y) {
			String parameterString = expression.substring(x + 1, y);

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
						parameterValue, namespace);
				}

				if (parameterValue.contains("\'")) {
					parameterValue = parameterValue.replaceAll("\\\\'", "'");
				}

				args.add(parameterValue);
			}
		}

		y = expression.indexOf("#");

		String className = expression.substring(0, y);
		String methodName = expression.substring(y + 1, x);

		Object returnObject = null;

		if (className.equals("selenium")) {
			Object object = SeleniumUtil.getSelenium();

			returnObject = getMethodReturnValue(
				args, className, methodName, object);
		}
		else {
			className = "com.liferay.poshi.runner.util." + className;

			returnObject = getMethodReturnValue(
				args, className, methodName, null);
		}

		return returnObject;
	}

	private static final Pattern _namespacedClassCommandNamePattern =
		Pattern.compile(
			"((?<namespace>\\w+)\\.)?(?<className>\\w+)(\\#(?<commandName>" +
				"(\\w+(\\-\\w+)*|\\$\\{\\w+\\}|\\w+)*))?");
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