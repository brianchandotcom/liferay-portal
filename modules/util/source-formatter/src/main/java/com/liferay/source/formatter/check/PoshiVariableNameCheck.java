/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiNodeFactory;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class PoshiVariableNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException, PoshiScriptParserException {

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

		if (document == null) {
			return content;
		}

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

		String allCapsName = _getAllCapsName(variableName);

		if (!variableName.equals(allCapsName)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Rename variable '", variableName, "' to '", allCapsName,
					"' in '", message));

			return;
		}

		String expectedName = _getExpectedName(variableName);

		if (!variableName.equals(expectedName)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Rename variable '", variableName, "' to '", expectedName,
					"' in '", message));
		}
	}

	private String _getAllCapsName(String variableName) {
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

	private String _getExpectedName(String variableName) {
		String[] words = StringUtil.split(variableName, StringPool.UNDERLINE);

		String expectedName = StringPool.BLANK;

		for (String word : words) {
			char firstChar = word.charAt(0);

			if (!Character.isUpperCase(firstChar)) {
				expectedName = expectedName + word + StringPool.UNDERLINE;

				continue;
			}

			char secondChar = word.charAt(1);

			if (!Character.isUpperCase(secondChar)) {
				expectedName = StringBundler.concat(
					expectedName, Character.toLowerCase(firstChar),
					word.substring(1), StringPool.UNDERLINE);

				continue;
			}

			expectedName = expectedName + word + StringPool.UNDERLINE;
		}

		return expectedName.substring(0, expectedName.length() - 1);
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

	private static final String[][] _ALL_CAPS_STRINGS = {
		{"DDL", "Ddl"}, {"DDM", "Ddm"}, {"DL", "Dl"}, {"PK", "Pk"},
		{"URL", "Url"}
	};

	private static final Pattern _variableReferencePattern = Pattern.compile(
		"(\\$\\{)([a-zA-Z0-9_]+?)(\\})");

}