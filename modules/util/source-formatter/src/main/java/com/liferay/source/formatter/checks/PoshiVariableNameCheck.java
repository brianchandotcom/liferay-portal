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

			// TODO Auto SF Start

			return _fixVariableNameInPath(content);

			// TODO Auto SF End

			/*Matcher matcher = _referenceVariablePattern.matcher(content);

			while (matcher.find()) {
				_checkVariableName(fileName, "", "" , matcher.group(2));
			}

			return content;*/

		}

		if (SourceUtil.isXML(content)) {
			return content;
		}

		File file = new File(fileName);

		PoshiElement poshiElement =
			(PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
				FileUtil.getURL(file));

		String poshiElementSyntax = Dom4JUtil.format(poshiElement);

		// TODO Auto SF Start

		poshiElementSyntax = _fixVariableNameInPoshiScript(
			file, poshiElement, poshiElementSyntax.trim(),
			_variableReferencePattern);

		poshiElementSyntax = _fixVariableNameInPoshiScript(
			file, poshiElement, poshiElementSyntax.trim(),
			_variableDefinitionPattern);

		// TODO Auto SF End

		Document document = SourceUtil.readXML(poshiElementSyntax);

		_parsePoshiElements(
			fileName, StringPool.BLANK, document.getRootElement());

		return poshiElement.toPoshiScript();
	}

	private void _checkVariableName(
		String fileName, String commandName, String executeName,
		String variableName) {

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

		String fixedVariableName = variableName;

		String[] words = StringUtil.split(
			fixedVariableName, StringPool.UNDERLINE);

		for (String word : words) {
			if (!word.matches(_CAMEL_CASE_PATTERN)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Variable '", variableName, "' in '", message,
						"' must match camelCase pattern '", _CAMEL_CASE_PATTERN,
						"'"));
			}
		}
	}

	private String _fixVariableNameInPath(String content) {
		Matcher matcher1 = _variableReferencePattern.matcher(content);

		StringBuffer sb1 = new StringBuffer();

		while (matcher1.find()) {
			String newVar = matcher1.group(2);

			if (newVar.startsWith("OSGi")) {
				newVar = StringUtil.replace(newVar, "OSGi", "osgi");
			}

			if (newVar.matches("[A-Z]+")) {
				newVar = StringUtil.toLowerCase(newVar);
			}

			Pattern pattern = Pattern.compile("([A-Z])([A-Z]+)([A-Z][a-z]|$)");

			Matcher matcher2 = pattern.matcher(newVar);

			StringBuffer sb2 = new StringBuffer();

			while (matcher2.find()) {
				matcher2.appendReplacement(
					sb2,
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase() + matcher2.group(3));
			}

			matcher2.appendTail(sb2);

			newVar = sb2.toString();

			pattern = Pattern.compile("(_)([A-Z])");

			matcher2 = pattern.matcher(newVar);

			while (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase());
			}

			pattern = Pattern.compile("^([A-Z])([a-z])(.*)$");

			matcher2 = pattern.matcher(newVar);

			if (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(
						1
					).toLowerCase() + matcher2.group(2) + matcher2.group(3));
			}

			pattern = Pattern.compile("([A-Z])([A-Z]+)(\\d)");

			matcher2 = pattern.matcher(newVar);

			if (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase() + matcher2.group(3));
			}

			matcher1.appendReplacement(sb1, "\\$\\{" + newVar + "\\}");
		}

		matcher1.appendTail(sb1);

		return sb1.toString();
	}

	private String _fixVariableNameInPoshiScript(
			File file, PoshiElement poshiElement, String poshiElementSyntax,
			Pattern pattern)
		throws IOException, PoshiScriptParserException {

		Matcher matcher1 = pattern.matcher(poshiElementSyntax);

		StringBuffer sb1 = new StringBuffer();

		while (matcher1.find()) {
			String newVar = matcher1.group(2);

			if (newVar.startsWith("OSGi")) {
				newVar = StringUtil.replace(newVar, "OSGi", "osgi");
			}

			if (newVar.matches("[A-Z]+")) {
				newVar = StringUtil.toLowerCase(newVar);
			}

			Pattern pattern1 = Pattern.compile("([A-Z])([A-Z]+)([A-Z][a-z]|$)");

			Matcher matcher2 = pattern1.matcher(newVar);

			StringBuffer sb2 = new StringBuffer();

			while (matcher2.find()) {
				matcher2.appendReplacement(
					sb2,
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase() + matcher2.group(3));
			}

			matcher2.appendTail(sb2);

			newVar = sb2.toString();

			pattern1 = Pattern.compile("(_)([A-Z])");

			matcher2 = pattern1.matcher(newVar);

			while (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase());
			}

			pattern1 = Pattern.compile("^([A-Z])([a-z])(.*)$");

			matcher2 = pattern1.matcher(newVar);

			if (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(
						1
					).toLowerCase() + matcher2.group(2) + matcher2.group(3));
			}

			pattern1 = Pattern.compile("([A-Z])([A-Z]+)(\\d)");

			matcher2 = pattern1.matcher(newVar);

			if (matcher2.find()) {
				newVar = newVar.replaceFirst(
					matcher2.group(),
					matcher2.group(1) +
						matcher2.group(
							2
						).toLowerCase() + matcher2.group(3));
			}

			String p = pattern.toString();

			if (p.startsWith("(\\$\\{)")) {
				matcher1.appendReplacement(sb1, "\\$\\{" + newVar + "\\}");
			}
			else {
				matcher1.appendReplacement(
					sb1, matcher1.group(1) + newVar + matcher1.group(3));
			}
		}

		matcher1.appendTail(sb1);

		FileUtil.write(file, sb1.toString());

		poshiElement = (PoshiElement)PoshiNodeFactory.newPoshiNodeFromFile(
			FileUtil.getURL(file));

		FileUtil.write(file, poshiElement.toPoshiScript());

		return sb1.toString();
	}

	private void _parsePoshiElements(
		String fileName, String commandName, Element parentElement) {

		List<Element> elements = parentElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("command")) {
				commandName = element.attributeValue("name");
			}
			else if (elementName.equals("var")) {
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

	private static final String _CAMEL_CASE_PATTERN = "([a-z0-9]+([A-Z])?)+";

	private static final Pattern _variableDefinitionPattern = Pattern.compile(
		"((?:<var name|<isset var|for param)=\")(.+?)(\")");
	private static final Pattern _variableReferencePattern = Pattern.compile(
		"(\\$\\{)([a-zA-Z0-9_]+?)(\\})");

}