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

import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerValidation {

	public static void validate() throws Exception {
		_validateTestName();

		String[] filePathsArray = PoshiRunnerContext.getFilePathsArray();

		for (String filePath : filePathsArray) {
			filePath = _BASE_DIR + "/" + filePath;

			if (OSDetector.isWindows()) {
				filePath = filePath.replace("/", "\\");
			}

			String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
				filePath);

			if (classType.equals("function")) {
				Element element = PoshiRunnerContext.getFunctionRootElement(
					className);

				_validateFunctionFile(element, filePath);
			}
			else if (classType.equals("macro")) {
				Element element = PoshiRunnerContext.getMacroRootElement(
					className);

				_validateMacroFile(element, filePath);
			}
			else if (classType.equals("path")) {
				Element element = PoshiRunnerContext.getPathRootElement(
					className);

				_validatePathFile(element, filePath);
			}
			else if (classType.equals("testcase")) {
				Element element = PoshiRunnerContext.getTestCaseRootElement(
					className);

				_validateTestCaseFile(element, filePath);
			}
		}
	}

	private static void _parseElements(Element element, String filePath)
		throws Exception {

		List<Element> childElements = element.elements();

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if", "property",
			"take-screenshot", "task", "var", "while");

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (!possibleElementNames.contains(elementName)) {
				throw new Exception(
					"Invalid " + elementName + " element\n" + filePath + ":" +
						childElement.attributeValue("line-number"));
			}

			if (elementName.equals("description") ||
				elementName.equals("echo") || elementName.equals("fail")) {

				_validateMessageElement(childElement, filePath);
			}
			else if (elementName.equals("execute")) {
				_validateExecuteElement(childElement, filePath);
			}
			else if (elementName.equals("for")) {
				_validateForElement(childElement, filePath);
			}
			else if (elementName.equals("if")) {
				_validateIfElement(childElement, filePath);
			}
			else if (elementName.equals("take-screenshot")) {
				_validateTakeScreenshotElement(childElement, filePath);
			}
			else if (elementName.equals("task")) {
				_validateTaskElement(childElement, filePath);
			}
			else if (elementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateClassCommandName(
			Element element, String classCommandName, String classType,
			String filePath)
		throws Exception {

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		if (!PoshiRunnerContext.isRootElement(classType + "#" + className)) {
			throw new Exception(
				"Invalid " + classType + " class " + className + "\n" +
					filePath + ":" + element.attributeValue("line-number"));
		}

		String commandElementKey = classType + "#" + classCommandName;

		if (!PoshiRunnerContext.isCommandElement(commandElementKey)) {
			throw new Exception(
				"Invalid " + classType + " command " + classCommandName + "\n" +
					filePath + ":" + element.attributeValue("line-number"));
		}
	}

	private static void _validateCommandElement(
			Element element, String filePath)
		throws Exception {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "name", "summary", "summary-ignore");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
		_validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);
	}

	private static void _validateConditionElement(
			Element element, String filePath)
		throws Exception {

		String elementName = element.getName();

		if (elementName.equals("and")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
		else if (elementName.equals("condition")) {
			if (Validator.isNotNull(element.attributeValue("function")) &&
				Validator.isNotNull(element.attributeValue("selenium"))) {

				throw new Exception(
					"There cannot be both function and selenium conditions\n" +
						filePath + ":" + element.attributeValue("line-number"));
			}

			if (Validator.isNotNull(element.attributeValue("function"))) {
				_validateRequiredAttributeNames(
					element, Arrays.asList("locator1"), filePath);

				List<String> possibleAttributeNames = Arrays.asList(
					"function", "line-number", "locator1", "value1");

				_validatePossibleAttributeNames(
					element, possibleAttributeNames, filePath);
			}
			else if (Validator.isNotNull(element.attributeValue("selenium"))) {
				List<String> possibleAttributeNames = Arrays.asList(
					"argument1", "argument2", "line-number", "selenium");

				_validatePossibleAttributeNames(
					element, possibleAttributeNames, filePath);
			}
			else {
				throw new Exception(
					"Invalid condition\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}

			_validateHasNoChildElements(element, filePath);
		}
		else if (elementName.equals("contains")) {
			List<String> attributeNames = Arrays.asList(
				"line-number", "string", "substring");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("equals")) {
			List<String> attributeNames = Arrays.asList(
				"arg1", "arg2", "line-number");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("isset")) {
			List<String> attributeNames = Arrays.asList("line-number", "var");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("not")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
		else if (elementName.equals("or")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
	}

	private static void _validateDefinitionElement(
			Element element, String filePath)
		throws Exception {

		String elementName = element.getName();

		if (!StringUtils.equals(elementName, "definition")) {
			throw new Exception(
				"Invalid " + elementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"default", "line-number", "summary", "summary-ignore");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
			_validateRequiredAttributeNames(
				element, Arrays.asList("default"), filePath);
		}
		else if (classType.equals("macro")) {
			_validateHasNoAttributes(element, filePath);
		}
		else if (classType.equals("testcase")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"component-name", "extends", "ignore", "ignore-command-names",
				"line-number");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
			_validateRequiredAttributeNames(
				element, Arrays.asList("component-name"), filePath);
		}
	}

	private static void _validateExecuteElement(
			Element element, String filePath)
		throws Exception {

		if (Validator.isNotNull(element.attributeValue("function"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"function", "line-number", "locator1", "locator2", "value1",
				"value2");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateFunctionContext(element, filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("macro"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro", filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("macro-desktop"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro-desktop", "macro-mobile");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro-desktop", filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("macro-mobile"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro-desktop", "macro-mobile");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro-mobile", filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("selenium"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"argument1", "argument2", "line-number", "selenium");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("test-case"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "test-case");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else {
			throw new Exception(
				"Invalid attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			if (Validator.isNotNull(element.attributeValue("function")) ||
				Validator.isNotNull(element.attributeValue("macro")) ||
				Validator.isNotNull(element.attributeValue("macro-desktop")) ||
				Validator.isNotNull(element.attributeValue("macro-mobile"))) {

				for (Element childElement : childElements) {
					if (StringUtils.equals(childElement.getName(), "var")) {
						_validateVarElement(childElement, filePath);

						continue;
					}
					else {
						throw new Exception(
							"Invalid child element\n" + filePath + ":" +
								element.attributeValue("line-number"));
					}
				}
			}
			else {
				throw new Exception(
					"Invalid child element\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateForElement(Element element, String filePath)
		throws Exception {

		List<String> attributeNames = Arrays.asList(
			"line-number", "list", "param");

		_validateHasChildElements(element, filePath);
		_validatePossibleAttributeNames(element, attributeNames, filePath);
		_validateRequiredAttributeNames(element, attributeNames, filePath);

		_parseElements(element, filePath);
	}

	private static void _validateFunctionContext(
			Element element, String filePath)
		throws Exception {

		String function = element.attributeValue("function");

		_validateClassCommandName(element, function, "function", filePath);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(function);

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < locatorCount; i++) {
			String locator = element.attributeValue("locator" + (i + 1));

			if (locator != null) {
				Matcher matcher = _pattern.matcher(locator);

				if (!locator.contains("#") || matcher.find()) {
					continue;
				}

				String pathName =
					PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
						locator);

				if (!PoshiRunnerContext.isRootElement("path#" + pathName)) {
					throw new Exception(
						"Invalid path name " + pathName + "\n" + filePath +
							":" + element.attributeValue("line-number"));
				}

				if (!PoshiRunnerContext.isPathLocator(locator)) {
					throw new Exception(
						"Invalid path locator " + locator + "\n" + filePath +
							":" + element.attributeValue("line-number"));
				}
			}
		}
	}

	private static void _validateFunctionFile(Element element, String filePath)
		throws Exception {

		_validateDefinitionElement(element, filePath);
		_validateHasChildElements(element, filePath);

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!childElementName.equals("command")) {
				throw new Exception(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			_validateCommandElement(childElement, filePath);
			_validateHasChildElements(childElement, filePath);

			_parseElements(childElement, filePath);
		}
	}

	private static void _validateHasChildElements(
			Element element, String filePath)
		throws Exception {

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new Exception(
				"Missing child elements\n " + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validateHasNoAttributes(
			Element element, String filePath)
		throws Exception {

		List<Attribute> attributes = element.attributes();

		if (attributes.size() > 1) {
			Attribute attribute = attributes.get(1);

			throw new Exception(
				"Invalid " + attribute.getName() + " attribute\n" + filePath +
					":" + element.attributeValue("line-number"));
		}
	}

	private static void _validateHasNoChildElements(
			Element element, String filePath)
		throws Exception {

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			throw new Exception(
				"Invalid child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validateIfElement(Element element, String filePath)
		throws Exception {

		_validateHasChildElements(element, filePath);
		_validateHasNoAttributes(element, filePath);

		String fileName = filePath.substring(filePath.lastIndexOf(".") + 1);

		List<Element> childElements = element.elements();

		if (fileName.equals("function")) {
			Element firstChildElement = childElements.get(0);

			String firstChildElementName = firstChildElement.getName();

			if (!StringUtils.equals(firstChildElementName, "condition") &&
				!StringUtils.equals(firstChildElementName, "contains")) {

				throw new Exception(
					"Missing (condition|contains) child element\n" +
						filePath + ":" +
						firstChildElement.attributeValue("line-number"));
			}
		}

		List<Element> thenElements = element.elements("then");

		if (thenElements.size() > 1) {
			throw new Exception(
				"Too many then elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		_validateHasChildElements(thenElements.get(0), filePath);

		List<Element> elseElements = element.elements("else");

		if (elseElements.size() > 1) {
			throw new Exception(
				"Too many else elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		int i = 0;

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (conditionTags.contains(childElementName) && (i == 0)) {
				_validateConditionElement(childElement, filePath);
			}
			else if (childElementName.equals("else")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("elseif")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_validateIfElement(childElement, filePath);
			}
			else if (childElementName.equals("then")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else {
				throw new Exception(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			i++;
		}
	}

	private static void _validateMacroContext(
			Element element, String macroType, String filePath)
		throws Exception {

		_validateClassCommandName(
			element, element.attributeValue(macroType), "macro", filePath);
	}

	private static void _validateMacroFile(Element element, String filePath)
		throws Exception {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new Exception(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				throw new Exception(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			if (childElementName.equals("command")) {
				_validateCommandElement(childElement, filePath);
				_validateHasChildElements(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateMessageElement(
			Element element, String filePath)
		throws Exception {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "message");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);

		if ((element.attributeValue("message") == null) &&
			Validator.isNull(element.getText())) {

			throw new Exception(
				"Missing message attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validatePathFile(Element element, String filePath)
		throws Exception {

		String rootElementName = element.getName();

		if (!StringUtils.equals(rootElementName, "html")) {
			throw new Exception(
				"Invalid " + rootElementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		_validateHasChildElements(element, filePath);
	}

	private static void _validatePossibleAttributeNames(
			Element element, List<String> possibleAttributeNames,
			String filePath)
		throws Exception {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributeNames.contains(attributeName)) {
				throw new Exception(
					"Invalid " + attributeName + " attribute\n" + filePath +
						":" + element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateRequiredAttributeNames(
			Element element, List<String> requiredAttributeNames,
			String filePath)
		throws Exception {

		for (String requiredAttributeName : requiredAttributeNames) {
			if (element.attributeValue(requiredAttributeName) == null) {
				throw new Exception(
					"Missing " + requiredAttributeName + " attribute\n" +
						filePath + ":" + element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateTakeScreenshotElement(
			Element element, String filePath)
		throws Exception {

		_validateHasNoAttributes(element, filePath);
		_validateHasNoChildElements(element, filePath);
	}

	private static void _validateTaskElement(Element element, String filePath)
		throws Exception {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "macro-summary", "summary");

		_validateHasChildElements(element, filePath);
		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);

		if (Validator.isNull(element.attributeValue("macro-summary")) &&
			Validator.isNull(element.attributeValue("summary"))) {

			throw new Exception(
				"Missing (macro-summary|summary) attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		_parseElements(element, filePath);
	}

	private static void _validateTestCaseFile(Element element, String filePath)
		throws Exception {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.isEmpty() &&
			Validator.isNull(element.attributeValue("extends"))) {

			throw new Exception(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> possibleTagElementNames = Arrays.asList(
			"command", "property", "set-up", "tear-down", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				throw new Exception(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			if (childElementName.equals("command")) {
				if (Validator.isNull(childElement.attributeValue("name"))) {
					throw new Exception(
						"Missing name attribute\n" + filePath + ":" +
							childElement.attributeValue("line-number"));
				}

				List<String> possibleAttributeNames = Arrays.asList(
					"description", "known-issues", "line-number", "name",
					"priority");

				_validateHasChildElements(childElement, filePath);
				_validatePossibleAttributeNames(
					childElement, possibleAttributeNames, filePath);
				_validateRequiredAttributeNames(
					childElement, Arrays.asList("name"), filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("property")) {
				List<String> attributeNames = Arrays.asList(
					"line-number", "name", "value");

				_validatePossibleAttributeNames(
					childElement, attributeNames, filePath);
				_validateRequiredAttributeNames(
					childElement, attributeNames, filePath);
			}
			else if (childElementName.equals("set-up") ||
					 childElementName.equals("tear-down")) {

				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateTestName() throws Exception {
		String testName = PropsValues.TEST_NAME;

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(testName);

		if (!PoshiRunnerContext.isRootElement("test-case#" + className)) {
			throw new Exception("Invalid test case class " + className);
		}

		if (testName.contains("#")) {
			String commandElementKey = "test-case#" + testName;

			if (!PoshiRunnerContext.isCommandElement(commandElementKey)) {
				String commandName =
					PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
						testName);

				throw new Exception("Invalid test case command " + commandName);
			}
		}
	}

	private static void _validateVarElement(Element element, String filePath)
		throws Exception {

		_validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<Attribute> attributes = element.attributes();

		if (attributes.size() <= 2) {
			if (Validator.isNull(element.getText())) {
				throw new Exception(
					"Missing value attribute\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}

		List<String> possibleAttributeNames = Arrays.asList(
			"attribute", "group", "input", "line-number", "locator", "method",
			"name", "pattern", "property-value", "value");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
	}

	private static final String _BASE_DIR =
		PoshiRunnerGetterUtil.getCanonicalPath(PropsValues.TEST_BASE_DIR_NAME);

	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");

}