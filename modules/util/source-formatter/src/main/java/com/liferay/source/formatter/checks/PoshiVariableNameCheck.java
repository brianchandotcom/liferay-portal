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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiNodeFactory;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class PoshiVariableNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException, PoshiScriptParserException {

		if (fileName.endsWith(".path")) {
			Matcher matcher = _variableReferencePattern.matcher(content);

			while (matcher.find()) {
				_checkVariableName(fileName, "", "", matcher.group(2));
			}

			return content;
		}

		if (SourceUtil.isXML(content)) {
			return content;
		}

		File file = new File(fileName);

		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				FileUtil.getURL(file));

		String poshiElementSyntax = Dom4JUtil.format(poshiElement);

		Document document = SourceUtil.readXML(poshiElementSyntax);

		_parsePoshiElements(
			fileName, StringPool.BLANK, document.getRootElement());

		return content;
	}

	private void _checkVariableName(
		String fileName, String commandName, String executeName,
		String variableName) {

		if (Validator.isNull(variableName)) {
			return;
		}

		String message = commandName;

		if (Validator.isNotNull(executeName)) {
			message = message + "#" + executeName;
		}

		char firstChar = variableName.charAt(0);

		if (!Character.isLowerCase(firstChar)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Variable '", variableName, "' in '", message,
					"' should start with a lowercase letter"));

			return;
		}

		String expectedVariableName = _getExpectedVariableName(variableName);

		if (!variableName.equals(expectedVariableName)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Rename variable '", variableName, "' to '",
					expectedVariableName, "' in '", message));

			return;
		}

		String[] words = StringUtil.split(variableName, StringPool.UNDERLINE);

		for (String word : words) {
			if (!word.matches(_CAMEL_CASE_PATTERN)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Variable '", variableName, "' in '", message,
						"' must match camelCase pattern '", _CAMEL_CASE_PATTERN,
						"'"));

				return;
			}
		}
	}

	private String _getExpectedVariableName(String variableName) {
		for (String[] array : _ALL_CAPS_STRINGS) {
			String s = array[1];

			int x = -1;

			while (true) {
				x = variableName.indexOf(s, x + 1);

				if (x == -1) {
					break;
				}

				int y = x + s.length();

				if ((y != variableName.length()) &&
					!Character.isUpperCase(variableName.charAt(y))) {

					continue;
				}

				return variableName.substring(0, x) + array[0] +
					variableName.substring(y);
			}
		}

		return variableName;
	}

	private void _parsePoshiElements(
		String fileName, String commandName, Element parentElement) {

		List<Element> elements = parentElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				commandName = element.attributeValue("name");
			}
			else if (elementName.equals("return") ||
					 elementName.equals("var")) {

				Element variableParentElement = element.getParent();

				String variableParentElementName =
					variableParentElement.getName();

				String executeName = "";

				if (variableParentElementName.equals("execute")) {
					String functionName = variableParentElement.attributeValue(
						"function");

					if (Validator.isNotNull(functionName)) {
						executeName = functionName;
					}

					String macroName = variableParentElement.attributeValue(
						"macro");

					if (Validator.isNotNull(macroName)) {
						executeName = macroName;
					}

					String className = variableParentElement.attributeValue(
						"class");
					String methodName = variableParentElement.attributeValue(
						"method");

					if (Objects.equals(className, methodName)) {
						executeName = className;
					}
					else {
						executeName = className + "." + methodName;
					}
				}

				_checkVariableName(
					fileName, commandName, executeName,
					element.attributeValue("name"));
			}

			_parsePoshiElements(fileName, commandName, element);
		}
	}

	private static final String[][] _ALL_CAPS_STRINGS = {{"URL", "Url"}};

	private static final String _CAMEL_CASE_PATTERN = "([a-z0-9]+([A-Z])?)+";

	private static final Pattern _variableReferencePattern = Pattern.compile(
		"(\\$\\{)([a-zA-Z0-9_]+?)(\\})");

}