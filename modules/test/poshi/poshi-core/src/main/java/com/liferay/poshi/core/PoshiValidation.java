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

package com.liferay.poshi.core;

import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.elements.PoshiElementException;
import com.liferay.poshi.core.script.PoshiScriptParserUtil;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiValidation {

	public static void clearExceptions() {
		_exceptions.clear();
	}

	public static Set<Exception> getExceptions() {
		return _exceptions;
	}

	public static void main(String[] args) throws Exception {
		PoshiContext.readFiles();

		validate();
	}

	public static void validate() throws Exception {
		System.out.println("Start poshi validation.");

		long start = System.currentTimeMillis();

		for (String filePath : PoshiContext.getFilePaths()) {
			if (OSDetector.isWindows()) {
				filePath = StringUtil.replace(filePath, "/", "\\");
			}

			String className = PoshiGetterUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiGetterUtil.getClassTypeFromFilePath(
				filePath);
			String namespace = PoshiContext.getNamespaceFromFilePath(filePath);

			if (classType.equals("function")) {
				Element element = PoshiContext.getFunctionRootElement(
					className, namespace);

				validateFunctionFile((PoshiElement)element);
			}
			else if (classType.equals("macro")) {
				Element element = PoshiContext.getMacroRootElement(
					className, namespace);

				validateMacroFile((PoshiElement)element);
			}
			else if (classType.equals("path")) {
				Element element = PoshiContext.getPathRootElement(
					className, namespace);

				validatePathFile(element, filePath);
			}
			else if (classType.equals("test-case")) {
				Element element = PoshiContext.getTestCaseRootElement(
					className, namespace);

				validateTestCaseFile((PoshiElement)element);
			}
		}

		if (!_exceptions.isEmpty()) {
			_throwExceptions();
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println("Completed poshi validation in " + duration + "ms.");
	}

	public static void validate(String testName) throws Exception {
		validateTestName(testName);

		validate();
	}

	protected static String getPrimaryAttributeName(
		PoshiElement element, List<String> primaryAttributeNames) {

		return getPrimaryAttributeName(element, null, primaryAttributeNames);
	}

	protected static String getPrimaryAttributeName(
		PoshiElement element, List<String> multiplePrimaryAttributeNames,
		List<String> primaryAttributeNames) {

		validateHasPrimaryAttributeName(
			element, multiplePrimaryAttributeNames, primaryAttributeNames);

		for (String primaryAttributeName : primaryAttributeNames) {
			if (Validator.isNotNull(
					element.attributeValue(primaryAttributeName))) {

				return primaryAttributeName;
			}
		}

		return null;
	}

	protected static void parseElements(PoshiElement element) {
		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if", "property",
			"return", "take-screenshot", "task", "var", "while");

		String filePath = _getFilePath(element);

		if (Validator.isNotNull(filePath) && filePath.endsWith(".function")) {
			possibleElementNames = Arrays.asList("execute", "if", "var");
		}

		for (PoshiElement childPoshiElement : childPoshiElements) {
			String elementName = childPoshiElement.getName();

			if (!possibleElementNames.contains(elementName)) {
				_exceptions.add(
					new PoshiElementException(
						childPoshiElement, "Invalid ", elementName,
						" element"));
			}

			if (elementName.equals("description") ||
				elementName.equals("echo") || elementName.equals("fail")) {

				validateMessageElement(childPoshiElement);
			}
			else if (elementName.equals("execute")) {
				validateExecuteElement(childPoshiElement);
			}
			else if (elementName.equals("for")) {
				validateForElement(childPoshiElement);
			}
			else if (elementName.equals("if")) {
				validateIfElement(childPoshiElement);
			}
			else if (elementName.equals("property")) {
				validatePropertyElement(childPoshiElement);
			}
			else if (elementName.equals("return")) {
				validateCommandReturnElement(childPoshiElement);
			}
			else if (elementName.equals("take-screenshot")) {
				validateTakeScreenshotElement(childPoshiElement);
			}
			else if (elementName.equals("task")) {
				validateTaskElement(childPoshiElement);
			}
			else if (elementName.equals("var")) {
				validateVarElement(childPoshiElement);
			}
			else if (elementName.equals("while")) {
				validateWhileElement(childPoshiElement);
			}
		}
	}

	protected static void validateArgElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		List<String> attributes = Arrays.asList("line-number", "value");

		validatePossibleAttributeNames(element, attributes);
		validateRequiredAttributeNames(element, attributes, filePath);
	}

	protected static void validateCommandElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "name", "prose", "return", "summary",
			"summary-ignore");

		validatePossibleAttributeNames(element, possibleAttributeNames);

		validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<PoshiElement> returnElements = element.toPoshiElements(
			PoshiGetterUtil.getAllChildElements(element, "return"));

		List<PoshiElement> commandReturnPoshiElements = new ArrayList<>();

		for (PoshiElement returnElement : returnElements) {
			Element parentElement = returnElement.getParent();

			if (!Objects.equals(parentElement.getName(), "execute")) {
				commandReturnPoshiElements.add(returnElement);
			}
		}

		String returnName = element.attributeValue("return");

		if (Validator.isNull(returnName)) {
			for (PoshiElement commandReturnPoshiElement :
					commandReturnPoshiElements) {

				String returnVariableName =
					commandReturnPoshiElement.attributeValue("name");
				String returnVariableValue =
					commandReturnPoshiElement.attributeValue("value");

				if (Validator.isNotNull(returnVariableName) &&
					Validator.isNotNull(returnVariableValue)) {

					_exceptions.add(
						new PoshiElementException(
							commandReturnPoshiElement,
							"No return variables were stated in command ",
							"declaration, but found return name-value ",
							"mapping"));
				}
			}
		}
		else {
			if (commandReturnPoshiElements.isEmpty()) {
				_exceptions.add(
					new PoshiElementException(
						element,
						"Return variable was stated, but no returns were ",
						"found"));
			}
			else {
				for (PoshiElement commandReturnPoshiElement :
						commandReturnPoshiElements) {

					String returnVariableName =
						commandReturnPoshiElement.attributeValue("name");

					if (Validator.isNull(returnVariableName)) {
						_exceptions.add(
							new PoshiElementException(
								commandReturnPoshiElement,
								"Return variable was stated as '", returnName,
								"', but no 'name' attribute was found"));

						continue;
					}

					if (returnName.equals(returnVariableName)) {
						continue;
					}

					_exceptions.add(
						new PoshiElementException(
							commandReturnPoshiElement, "'", returnVariableName,
							"' not listed as a return variable"));
				}
			}
		}
	}

	protected static void validateCommandReturnElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(
			element, Arrays.asList("line-number", "name", "value"));
		validateRequiredAttributeNames(
			element, Arrays.asList("line-number", "value"), filePath);
	}

	protected static void validateConditionElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		String elementName = element.getName();

		if (elementName.equals("and") || elementName.equals("or")) {
			validateHasChildElements(element, filePath);
			validateHasNoAttributes(element);

			List<PoshiElement> childPoshiElements = element.toPoshiElements(
				element.elements());

			if (childPoshiElements.size() < 2) {
				_exceptions.add(
					new PoshiElementException(
						element, "Too few child elements"));
			}

			for (PoshiElement childPoshiElement : childPoshiElements) {
				validateConditionElement(childPoshiElement);
			}
		}
		else if (elementName.equals("condition")) {
			List<String> primaryAttributeNames = Arrays.asList(
				"function", "selenium");

			String primaryAttributeName = getPrimaryAttributeName(
				element, primaryAttributeNames);

			if (Validator.isNull(primaryAttributeName)) {
				return;
			}

			if (primaryAttributeName.equals("function")) {
				validateRequiredAttributeNames(
					element, Arrays.asList("locator1"), filePath);

				List<String> possibleAttributeNames = Arrays.asList(
					"function", "line-number", "locator1", "value1");

				validatePossibleAttributeNames(element, possibleAttributeNames);
			}
			else if (primaryAttributeName.equals("selenium")) {
				List<String> possibleAttributeNames = Arrays.asList(
					"argument1", "argument2", "line-number", "selenium");

				validatePossibleAttributeNames(element, possibleAttributeNames);
			}

			List<PoshiElement> varPoshiElements = element.toPoshiElements(
				element.elements("var"));

			for (PoshiElement varPoshiElement : varPoshiElements) {
				validateVarElement(varPoshiElement);
			}
		}
		else if (elementName.equals("contains")) {
			List<String> attributeNames = Arrays.asList(
				"line-number", "string", "substring");

			validateHasNoChildElements(element);
			validatePossibleAttributeNames(element, attributeNames);
			validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("equals")) {
			List<String> attributeNames = Arrays.asList(
				"arg1", "arg2", "line-number");

			validateHasNoChildElements(element);
			validatePossibleAttributeNames(element, attributeNames);
			validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("isset")) {
			List<String> attributeNames = Arrays.asList("line-number", "var");

			validateHasNoChildElements(element);
			validatePossibleAttributeNames(element, attributeNames);
			validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("not")) {
			validateHasChildElements(element, filePath);
			validateHasNoAttributes(element);
			validateNumberOfChildElements(element, 1, filePath);

			List<PoshiElement> childPoshiElements = element.toPoshiElements(
				element.elements());

			validateConditionElement(childPoshiElements.get(0));
		}
	}

	protected static void validateDefinitionElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		String elementName = element.getName();

		if (!Objects.equals(elementName, "definition")) {
			_exceptions.add(
				new PoshiElementException(
					element, "Root element name must be definition"));
		}

		String classType = PoshiGetterUtil.getClassTypeFromFilePath(filePath);

		if (classType.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"default", "line-number", "override", "summary",
				"summary-ignore");

			validatePossibleAttributeNames(element, possibleAttributeNames);

			validateRequiredAttributeNames(
				element, Arrays.asList("default"), filePath);
		}
		else if (classType.equals("macro")) {
			validateHasNoAttributes(element);
		}
		else if (classType.equals("testcase")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"extends", "ignore", "ignore-command-names", "line-number");

			validatePossibleAttributeNames(element, possibleAttributeNames);
		}
	}

	protected static void validateElementName(
		PoshiElement element, List<String> possibleElementNames) {

		if (!possibleElementNames.contains(element.getName())) {
			_exceptions.add(
				new PoshiElementException(
					element, "Missing ", possibleElementNames, " element"));
		}
	}

	protected static void validateElseElement(PoshiElement element) {
		List<PoshiElement> elsePoshiElements = element.toPoshiElements(
			element.elements("else"));

		if (elsePoshiElements.size() > 1) {
			_exceptions.add(
				new PoshiElementException(element, "Too many else elements"));
		}

		if (!elsePoshiElements.isEmpty()) {
			PoshiElement elseElement = elsePoshiElements.get(0);

			parseElements(elseElement);
		}
	}

	protected static void validateElseIfElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);

		validateHasNoAttributes(element);
		validateNumberOfChildElements(element, 2, filePath);
		validateThenElement(element);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		PoshiElement conditionElement = childPoshiElements.get(0);

		String conditionElementName = conditionElement.getName();

		if (conditionTags.contains(conditionElementName)) {
			validateConditionElement(conditionElement);
		}
		else {
			_exceptions.add(
				new PoshiElementException(
					element, "Invalid ", conditionElementName, " element"));
		}

		PoshiElement thenElement = (PoshiElement)element.element("then");

		validateHasChildElements(thenElement, filePath);
		validateHasNoAttributes(thenElement);

		parseElements(thenElement);
	}

	protected static void validateExecuteElement(PoshiElement element) {
		List<String> primaryAttributeNames = Arrays.asList(
			"function", "macro", "method", "selenium", "test-case");

		String filePath = _getFilePath(element);

		if (filePath.endsWith(".function")) {
			primaryAttributeNames = Arrays.asList("function", "selenium");
		}
		else if (filePath.endsWith(".macro")) {
			primaryAttributeNames = Arrays.asList(
				"function", "macro", "method");
		}
		else if (filePath.endsWith(".testcase")) {
			primaryAttributeNames = Arrays.asList(
				"function", "macro", "method", "test-case");
		}

		String primaryAttributeName = getPrimaryAttributeName(
			element, primaryAttributeNames);

		if (primaryAttributeName == null) {
			return;
		}

		if (primaryAttributeName.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"function", "line-number", "locator1", "locator2", "value1",
				"value2", "value3");

			validatePossibleAttributeNames(element, possibleAttributeNames);

			validateFunctionContext(element);
		}
		else if (primaryAttributeName.equals("macro")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro");

			validatePossibleAttributeNames(element, possibleAttributeNames);

			validateMacroContext(element, "macro");
		}
		else if (primaryAttributeName.equals("method")) {
			validateMethodExecuteElement(element);
		}
		else if (primaryAttributeName.equals("selenium")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"argument1", "argument2", "argument3", "line-number",
				"selenium");

			validatePossibleAttributeNames(element, possibleAttributeNames);
		}
		else if (primaryAttributeName.equals("test-case")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "test-case");

			validatePossibleAttributeNames(element, possibleAttributeNames);

			validateTestCaseContext(element);
		}

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		if (!childPoshiElements.isEmpty()) {
			primaryAttributeNames = Arrays.asList(
				"function", "macro", "method", "selenium", "test-case");

			validateHasPrimaryAttributeName(element, primaryAttributeNames);

			List<String> possibleChildElementNames = Arrays.asList(
				"arg", "prose", "return", "var");

			for (PoshiElement childPoshiElement : childPoshiElements) {
				String childElementName = childPoshiElement.getName();

				if (!possibleChildElementNames.contains(childElementName)) {
					_exceptions.add(
						new PoshiElementException(
							childPoshiElement, "Invalid child element"));
				}
			}

			List<PoshiElement> argPoshiElements = element.toPoshiElements(
				element.elements("arg"));

			for (PoshiElement argPoshiElement : argPoshiElements) {
				validateArgElement(argPoshiElement);
			}

			List<PoshiElement> returnElements = element.toPoshiElements(
				element.elements("return"));

			if ((returnElements.size() > 1) &&
				primaryAttributeName.equals("macro")) {

				_exceptions.add(
					new PoshiElementException(
						element, "Only 1 child element 'return' is allowed"));
			}

			PoshiElement returnElement = (PoshiElement)element.element(
				"return");

			if (returnElement != null) {
				if (primaryAttributeName.equals("macro")) {
					validateExecuteReturnMacroElement(returnElement);
				}
				else if (primaryAttributeName.equals("method")) {
					validateExecuteReturnMethodElement(returnElement);
				}
			}

			List<PoshiElement> varPoshiElements = element.toPoshiElements(
				element.elements("var"));
			List<String> varNames = new ArrayList<>();

			for (PoshiElement varPoshiElement : varPoshiElements) {
				validateVarElement(varPoshiElement);

				String varName = varPoshiElement.attributeValue("name");

				if (varNames.contains(varName)) {
					_exceptions.add(
						new PoshiElementException(
							element, "Duplicate variable name: " + varName));
				}

				varNames.add(varName);
			}
		}
	}

	protected static void validateExecuteReturnMacroElement(
		PoshiElement element) {

		List<String> attributeNames = Arrays.asList("line-number", "name");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, attributeNames);
		validateRequiredAttributeNames(
			element, attributeNames, _getFilePath(element));
	}

	protected static void validateExecuteReturnMethodElement(
		PoshiElement element) {

		List<String> attributeNames = Arrays.asList("line-number", "name");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, attributeNames);
		validateRequiredAttributeNames(
			element, attributeNames, _getFilePath(element));
	}

	protected static void validateForElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "list", "param", "table");

		validatePossibleAttributeNames(element, possibleAttributeNames);

		List<String> requiredAttributeNames = Arrays.asList(
			"line-number", "param");

		validateRequiredAttributeNames(
			element, requiredAttributeNames, filePath);

		parseElements(element);
	}

	protected static void validateFunctionContext(PoshiElement element) {
		String filePath = _getFilePath(element);

		String function = element.attributeValue("function");

		validateNamespacedClassCommandName(element, function, "function");

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				function);

		String namespace = PoshiContext.getNamespaceFromFilePath(filePath);

		int locatorCount = PoshiContext.getFunctionLocatorCount(
			className, namespace);

		for (int i = 0; i < locatorCount; i++) {
			String locator = element.attributeValue("locator" + (i + 1));

			if (locator != null) {
				Matcher matcher = _pattern.matcher(locator);

				if (locator.startsWith("css=") || !locator.contains("#") ||
					matcher.find()) {

					continue;
				}

				String pathName =
					PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
						locator);

				String defaultNamespace = PoshiContext.getDefaultNamespace();

				if (!PoshiContext.isRootElement("path", pathName, namespace) &&
					!PoshiContext.isRootElement(
						"path", pathName, defaultNamespace)) {

					_exceptions.add(
						new PoshiElementException(
							element, "Invalid path name ", pathName));
				}
				else if (!PoshiContext.isPathLocator(locator, namespace) &&
						 !PoshiContext.isPathLocator(
							 locator, defaultNamespace)) {

					_exceptions.add(
						new PoshiElementException(
							element, "Invalid path locator ", locator));
				}
			}
		}
	}

	protected static void validateFunctionFile(PoshiElement element) {
		validateDefinitionElement(element);

		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);
		validateRequiredChildElementNames(
			element, Arrays.asList("command"), filePath);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		for (PoshiElement childPoshiElement : childPoshiElements) {
			validateCommandElement(childPoshiElement);
			validateHasChildElements(childPoshiElement, filePath);

			parseElements(childPoshiElement);
		}
	}

	protected static void validateHasChildElements(
		Element element, String filePath) {

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new PoshiElementException(
						element, "Missing child elements"));

				return;
			}

			_exceptions.add(
				new ValidationException(
					element, "Missing child elements", filePath));
		}
	}

	protected static void validateHasMultiplePrimaryAttributeNames(
		PoshiElement element, List<String> attributeNames,
		List<String> multiplePrimaryAttributeNames) {

		if (!multiplePrimaryAttributeNames.equals(attributeNames)) {
			_exceptions.add(
				new PoshiElementException(element, "Too many attributes"));
		}
	}

	protected static void validateHasNoAttributes(PoshiElement element) {
		List<Attribute> attributes = element.attributes();

		if (!attributes.isEmpty()) {
			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (attributeName.equals("line-number")) {
					continue;
				}

				_exceptions.add(
					new PoshiElementException(
						element, "Invalid ", attributeName, " attribute"));
			}
		}
	}

	protected static void validateHasNoChildElements(PoshiElement element) {
		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		if (!childPoshiElements.isEmpty()) {
			_exceptions.add(
				new PoshiElementException(element, "Invalid child elements"));
		}
	}

	protected static void validateHasPrimaryAttributeName(
		PoshiElement element, List<String> primaryAttributeNames) {

		validateHasPrimaryAttributeName(element, null, primaryAttributeNames);
	}

	protected static void validateHasPrimaryAttributeName(
		PoshiElement element, List<String> multiplePrimaryAttributeNames,
		List<String> primaryAttributeNames) {

		List<String> attributeNames = new ArrayList<>();

		for (String primaryAttributeName : primaryAttributeNames) {
			if (Validator.isNotNull(
					element.attributeValue(primaryAttributeName))) {

				attributeNames.add(primaryAttributeName);
			}
		}

		if (attributeNames.isEmpty()) {
			_exceptions.add(
				new PoshiElementException(
					element, "Invalid or missing attribute"));
		}
		else if (attributeNames.size() > 1) {
			if (multiplePrimaryAttributeNames == null) {
				_exceptions.add(
					new PoshiElementException(element, "Too many attributes"));
			}
			else {
				validateHasMultiplePrimaryAttributeNames(
					element, attributeNames, multiplePrimaryAttributeNames);
			}
		}
	}

	protected static void validateHasRequiredPropertyElements(
		PoshiElement element) {

		List<String> requiredPropertyNames = new ArrayList<>(
			PoshiContext.getRequiredPoshiPropertyNames());

		List<PoshiElement> propertyPoshiElements = element.toPoshiElements(
			element.elements("property"));

		for (PoshiElement propertyPoshiElement : propertyPoshiElements) {
			validatePropertyElement(propertyPoshiElement);

			String propertyName = propertyPoshiElement.attributeValue("name");

			if (requiredPropertyNames.contains(propertyName)) {
				requiredPropertyNames.remove(propertyName);
			}
		}

		if (requiredPropertyNames.isEmpty()) {
			return;
		}

		String filePath = _getFilePath(element);

		String namespace = PoshiContext.getNamespaceFromFilePath(filePath);

		String className = PoshiGetterUtil.getClassNameFromFilePath(filePath);

		String commandName = element.attributeValue("name");

		String namespacedClassCommandName =
			namespace + "." + className + "#" + commandName;

		Properties properties =
			PoshiContext.getNamespacedClassCommandNameProperties(
				namespacedClassCommandName);

		for (String requiredPropertyName : requiredPropertyNames) {
			if (!properties.containsKey(requiredPropertyName)) {
				_exceptions.add(
					new PoshiElementException(
						element,
						className + "#" + commandName +
							" is missing required properties ",
						requiredPropertyNames.toString()));
			}
		}
	}

	protected static void validateIfElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);

		validateHasNoAttributes(element);

		String fileName = filePath.substring(filePath.lastIndexOf(".") + 1);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		if (fileName.equals("function")) {
			conditionTags = Arrays.asList(
				"and", "condition", "contains", "not", "or");
		}

		validateElseElement(element);
		validateThenElement(element);

		for (int i = 0; i < childPoshiElements.size(); i++) {
			PoshiElement childPoshiElement = childPoshiElements.get(i);

			String childElementName = childPoshiElement.getName();

			if (i == 0) {
				if (conditionTags.contains(childElementName)) {
					validateConditionElement(childPoshiElement);
				}
				else {
					_exceptions.add(
						new PoshiElementException(
							element,
							"Missing or invalid if condition element"));
				}
			}
			else if (childElementName.equals("else")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement);
			}
			else if (childElementName.equals("elseif")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				validateElseIfElement(childPoshiElement);
			}
			else if (childElementName.equals("then")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement);
			}
			else {
				_exceptions.add(
					new PoshiElementException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}
		}
	}

	protected static void validateMacroCommandName(PoshiElement element) {
		String attributeName = element.attributeValue("name");

		if (attributeName.contains("Url")) {
			_exceptions.add(
				new PoshiElementException(
					element, "Invalid macro command name: ", attributeName,
					". Use 'URL' instead of 'Url'"));
		}
	}

	protected static void validateMacroContext(
		PoshiElement element, String macroType) {

		validateNamespacedClassCommandName(
			element, element.attributeValue(macroType), "macro");
	}

	protected static void validateMacroFile(PoshiElement element) {
		validateDefinitionElement(element);

		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);
		validateRequiredChildElementName(element, "command", filePath);

		List<PoshiElement> childElements = element.toPoshiElements(
			element.elements());

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (PoshiElement childPoshiElement : childElements) {
			String childElementName = childPoshiElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				_exceptions.add(
					new PoshiElementException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}

			if (childElementName.equals("command")) {
				validateCommandElement(childPoshiElement);
				validateHasChildElements(childPoshiElement, filePath);
				validateMacroCommandName(childPoshiElement);

				parseElements(childPoshiElement);
			}
			else if (childElementName.equals("var")) {
				validateVarElement(childPoshiElement);
			}
		}
	}

	protected static void validateMessageElement(PoshiElement element) {
		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "message");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, possibleAttributeNames);

		if ((element.attributeValue("message") == null) &&
			Validator.isNull(element.getText())) {

			_exceptions.add(
				new PoshiElementException(
					element, "Missing message attribute"));
		}
	}

	protected static void validateMethodExecuteElement(PoshiElement element) {
		String className = element.attributeValue("class");

		Class<?> clazz = null;

		String fullClassName = null;

		if (className.matches("[\\w]*")) {
			for (String packageName : UTIL_PACKAGE_NAMES) {
				try {
					clazz = Class.forName(packageName + "." + className);

					fullClassName = packageName + "." + className;

					break;
				}
				catch (Exception exception) {
				}
			}
		}
		else {
			try {
				clazz = Class.forName(className);

				fullClassName = className;
			}
			catch (Exception exception) {
			}
		}

		if (fullClassName == null) {
			_exceptions.add(
				new PoshiElementException(
					element, "Unable to find class ", className));

			return;
		}

		try {
			validateUtilityClassName(element, fullClassName);
		}
		catch (PoshiElementException poshiElementException) {
			_exceptions.add(poshiElementException);

			return;
		}

		String methodName = element.attributeValue("method");

		List<Method> possibleMethods = new ArrayList<>();

		List<Method> completeMethods = Arrays.asList(clazz.getMethods());

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		for (Method possibleMethod : completeMethods) {
			String possibleMethodName = possibleMethod.getName();

			Class<?>[] methodParameterTypes =
				possibleMethod.getParameterTypes();

			if (methodName.equals(possibleMethodName) &&
				(methodParameterTypes.length == childPoshiElements.size())) {

				possibleMethods.add(possibleMethod);
			}
		}

		if (possibleMethods.isEmpty()) {
			_exceptions.add(
				new PoshiElementException(
					element, "Unable to find method ", fullClassName, "#",
					methodName));
		}
	}

	protected static void validateNamespacedClassCommandName(
		PoshiElement element, String namespacedClassCommandName,
		String classType) {

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String defaultNamespace = PoshiContext.getDefaultNamespace();

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		if (namespace.equals(defaultNamespace)) {
			namespace = PoshiContext.getNamespaceFromFilePath(
				_getFilePath(element));
		}

		if (!PoshiContext.isRootElement(classType, className, namespace) &&
			!PoshiContext.isRootElement(
				classType, className, defaultNamespace)) {

			_exceptions.add(
				new PoshiElementException(
					element, "Invalid ", classType, " class ", className));
		}

		if (!PoshiContext.isCommandElement(
				classType, classCommandName, namespace) &&
			!PoshiContext.isCommandElement(
				classType, classCommandName, defaultNamespace)) {

			_exceptions.add(
				new PoshiElementException(
					element, "Invalid ", classType, " command ",
					namespacedClassCommandName));
		}
	}

	protected static void validateNumberOfChildElements(
		Element element, int number, String filePath) {

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new PoshiElementException(
						element, "Missing child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Missing child elements", filePath));
		}
		else if (childElements.size() > number) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new PoshiElementException(
						element, "Too many child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Too many child elements", filePath));
		}
		else if (childElements.size() < number) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new PoshiElementException(
						element, "Too few child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Too few child elements", filePath));
		}
	}

	protected static void validateOffElement(PoshiElement element) {
		List<PoshiElement> offPoshiElements = element.toPoshiElements(
			element.elements("off"));

		if (offPoshiElements.size() > 1) {
			_exceptions.add(
				new ValidationException(element, "Too many off elements"));
		}

		if (!offPoshiElements.isEmpty()) {
			PoshiElement offElement = offPoshiElements.get(0);

			validateHasChildElements(offElement, _getFilePath(element));
			validateHasNoAttributes(offElement);

			parseElements(offElement);
		}
	}

	protected static void validateOnElement(PoshiElement element) {
		List<PoshiElement> onPoshiElements = element.toPoshiElements(
			element.elements("off"));

		if (onPoshiElements.size() > 1) {
			_exceptions.add(
				new PoshiElementException(element, "Too many off elements"));
		}

		if (!onPoshiElements.isEmpty()) {
			PoshiElement onElement = onPoshiElements.get(0);

			validateHasChildElements(onElement, _getFilePath(element));
			validateHasNoAttributes(onElement);

			parseElements(onElement);
		}
	}

	protected static void validatePathFile(Element element, String filePath) {
		String className = PoshiGetterUtil.getClassNameFromFilePath(filePath);

		String rootElementName = element.getName();

		if (!Objects.equals(rootElementName, "html")) {
			_exceptions.add(
				new ValidationException(
					element, "Invalid ", rootElementName, " element\n",
					filePath));
		}

		validateHasChildElements(element, filePath);
		validateNumberOfChildElements(element, 2, filePath);
		validateRequiredChildElementNames(
			element, Arrays.asList("body", "head"), filePath);

		Element bodyElement = element.element("body");

		validateHasChildElements(bodyElement, filePath);
		validateNumberOfChildElements(bodyElement, 1, filePath);
		validateRequiredChildElementName(bodyElement, "table", filePath);

		Element tableElement = bodyElement.element("table");

		List<String> requiredTableAttributeNames = Arrays.asList(
			"border", "cellpadding", "cellspacing", "line-number");

		validateHasChildElements(tableElement, filePath);
		validateNumberOfChildElements(tableElement, 2, filePath);
		validateRequiredAttributeNames(
			tableElement, requiredTableAttributeNames, filePath);
		validateRequiredChildElementNames(
			tableElement, Arrays.asList("tbody", "thead"), filePath);

		Element tBodyElement = tableElement.element("tbody");

		List<Element> trElements = tBodyElement.elements();

		if (trElements != null) {
			for (Element trElement : trElements) {
				validateHasChildElements(trElement, filePath);
				validateNumberOfChildElements(trElement, 3, filePath);
				validateRequiredChildElementName(trElement, "td", filePath);

				List<Element> tdElements = trElement.elements();

				Element locatorElement = tdElements.get(1);

				String locator = locatorElement.getText();

				Element locatorKeyElement = tdElements.get(0);

				String locatorKey = locatorKeyElement.getText();

				if (Validator.isNull(locator) != Validator.isNull(locatorKey)) {
					_exceptions.add(
						new ValidationException(
							trElement, "Missing locator\n", filePath));
				}

				if (locatorKey.equals("EXTEND_ACTION_PATH")) {
					String namespace = PoshiContext.getNamespaceFromFilePath(
						filePath);

					Element pathRootElement = PoshiContext.getPathRootElement(
						locator, namespace);

					if (pathRootElement == null) {
						_exceptions.add(
							new ValidationException(
								trElement, "Nonexistent parent path file\n",
								filePath));
					}
				}
			}
		}

		Element theadElement = tableElement.element("thead");

		validateHasChildElements(theadElement, filePath);
		validateNumberOfChildElements(theadElement, 1, filePath);
		validateRequiredChildElementName(theadElement, "tr", filePath);

		Element trElement = theadElement.element("tr");

		validateHasChildElements(trElement, filePath);
		validateNumberOfChildElements(trElement, 1, filePath);
		validateRequiredChildElementName(trElement, "td", filePath);

		Element tdElement = trElement.element("td");

		validateRequiredAttributeNames(
			tdElement, Arrays.asList("colspan", "rowspan"), filePath);

		String theadClassName = tdElement.getText();

		if (Validator.isNull(theadClassName)) {
			_exceptions.add(
				new ValidationException(
					trElement, "Missing thead class name\n", filePath));
		}
		else if (!Objects.equals(theadClassName, className)) {
			_exceptions.add(
				new ValidationException(
					trElement, "Thead class name does not match file name\n",
					filePath));
		}

		Element headElement = element.element("head");

		validateHasChildElements(headElement, filePath);
		validateNumberOfChildElements(headElement, 1, filePath);
		validateRequiredChildElementName(headElement, "title", filePath);

		Element titleElement = headElement.element("title");

		if (!Objects.equals(titleElement.getText(), className)) {
			_exceptions.add(
				new ValidationException(
					titleElement, "File name and title are different\n",
					filePath));
		}
	}

	protected static void validatePossibleAttributeNames(
		PoshiElement element, List<String> possibleAttributeNames) {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributeNames.contains(attributeName)) {
				_exceptions.add(
					new PoshiElementException(
						element, "Invalid ", attributeName, " attribute"));
			}
		}
	}

	protected static void validatePossiblePropertyValues(
		PoshiElement propertyElement) {

		String propertyName = propertyElement.attributeValue("name");

		String testCaseAvailablePropertyValues = PropsUtil.get(
			"test.case.available.property.values[" + propertyName + "]");

		if (Validator.isNotNull(testCaseAvailablePropertyValues)) {
			List<String> possiblePropertyValues = Arrays.asList(
				StringUtil.split(testCaseAvailablePropertyValues));

			List<String> propertyValues = Arrays.asList(
				StringUtil.split(propertyElement.attributeValue("value")));

			for (String propertyValue : propertyValues) {
				if (!possiblePropertyValues.contains(propertyValue.trim())) {
					_exceptions.add(
						new PoshiElementException(
							propertyElement, "Invalid property value '",
							propertyValue.trim(), "' for property name '",
							propertyName.trim()));
				}
			}
		}
	}

	protected static void validatePropertyElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		List<String> attributeNames = Arrays.asList(
			"line-number", "name", "value");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, attributeNames);
		validateRequiredAttributeNames(element, attributeNames, filePath);
		validatePossiblePropertyValues(element);
	}

	protected static void validateRequiredAttributeNames(
		Element element, List<String> requiredAttributeNames, String filePath) {

		for (String requiredAttributeName : requiredAttributeNames) {
			if (requiredAttributeName.equals("line-number") &&
				(element instanceof PoshiElement)) {

				continue;
			}

			if (element.attributeValue(requiredAttributeName) == null) {
				if (element instanceof PoshiElement) {
					_exceptions.add(
						new PoshiElementException(
							element, "Missing ", requiredAttributeName,
							" attribute"));
				}

				_exceptions.add(
					new ValidationException(
						element, "Missing ", requiredAttributeName,
						" attribute", filePath));
			}
		}
	}

	protected static void validateRequiredChildElementName(
		Element element, String requiredElementName, String filePath) {

		boolean found = false;

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			if (Objects.equals(childElement.getName(), requiredElementName)) {
				found = true;

				break;
			}
		}

		if (!found) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new PoshiElementException(
						element, "Missing required ", requiredElementName,
						" child element"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Missing required ", requiredElementName,
					" child element", filePath));
		}
	}

	protected static void validateRequiredChildElementNames(
		Element element, List<String> requiredElementNames, String filePath) {

		for (String requiredElementName : requiredElementNames) {
			validateRequiredChildElementName(
				element, requiredElementName, filePath);
		}
	}

	protected static void validateSeleniumMethodAttributeValue(
		PoshiElement element, String methodAttributeValue) {

		Matcher seleniumGetterMethodMatcher =
			_seleniumGetterMethodPattern.matcher(methodAttributeValue);

		seleniumGetterMethodMatcher.find();

		String seleniumMethodName = seleniumGetterMethodMatcher.group(
			"methodName");

		if (seleniumMethodName.equals("getCurrentUrl")) {
			return;
		}

		int seleniumParameterCount = PoshiContext.getSeleniumParameterCount(
			seleniumMethodName);

		List<String> methodParameterValues =
			PoshiScriptParserUtil.getMethodParameterValues(
				seleniumGetterMethodMatcher.group("methodParameters"));

		if (methodParameterValues.size() != seleniumParameterCount) {
			_exceptions.add(
				new PoshiElementException(
					element, "Expected ", seleniumParameterCount,
					" parameter(s) for method \"", seleniumMethodName,
					"\" but found ", seleniumParameterCount));
		}

		for (String methodParameterValue : methodParameterValues) {
			Matcher invalidMethodParameterMatcher =
				_invalidMethodParameterPattern.matcher(methodParameterValue);

			if (invalidMethodParameterMatcher.find()) {
				String invalidSyntax = invalidMethodParameterMatcher.group(
					"invalidSyntax");

				_exceptions.add(
					new PoshiElementException(
						element, "Invalid parameter syntax \"", invalidSyntax,
						"\"\n"));
			}
		}
	}

	protected static void validateTakeScreenshotElement(PoshiElement element) {
		validateHasNoAttributes(element);
		validateHasNoChildElements(element);
	}

	protected static void validateTaskElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "macro-summary", "summary");

		validateHasChildElements(element, filePath);
		validatePossibleAttributeNames(element, possibleAttributeNames);

		List<String> primaryAttributeNames = Arrays.asList(
			"macro-summary", "summary");

		validateHasPrimaryAttributeName(element, primaryAttributeNames);

		parseElements(element);
	}

	protected static void validateTestCaseContext(PoshiElement element) {
		String filePath = _getFilePath(element);

		String namespace = PoshiContext.getNamespaceFromFilePath(filePath);

		String testName = element.attributeValue("test-case");

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				testName);

		if (className.equals("super")) {
			className = PoshiGetterUtil.getExtendedTestCaseName(filePath);
		}

		String commandName =
			PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
				testName);

		validateTestName(namespace + "." + className + "#" + commandName);
	}

	protected static void validateTestCaseFile(PoshiElement element) {
		validateDefinitionElement(element);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		String filePath = _getFilePath(element);

		if (Validator.isNull(element.attributeValue("extends"))) {
			validateHasChildElements(element, filePath);
			validateRequiredChildElementName(element, "command", filePath);
		}

		List<String> possibleTagElementNames = Arrays.asList(
			"command", "property", "set-up", "tear-down", "var");

		List<String> propertyNames = new ArrayList<>();

		for (PoshiElement childPoshiElement : childPoshiElements) {
			String childElementName = childPoshiElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				_exceptions.add(
					new PoshiElementException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}

			if (childElementName.equals("command")) {
				List<String> possibleAttributeNames = Arrays.asList(
					"annotations", "description", "ignore", "known-issues",
					"line-number", "name", "priority");

				validateHasChildElements(childPoshiElement, filePath);
				validateHasRequiredPropertyElements(childPoshiElement);
				validatePossibleAttributeNames(
					childPoshiElement, possibleAttributeNames);
				validateRequiredAttributeNames(
					childPoshiElement, Arrays.asList("name"), filePath);

				parseElements(childPoshiElement);
			}
			else if (childElementName.equals("property")) {
				validatePropertyElement(childPoshiElement);

				String propertyName = childPoshiElement.attributeValue("name");

				if (!propertyNames.contains(propertyName)) {
					propertyNames.add(propertyName);
				}
				else {
					_exceptions.add(
						new PoshiElementException(
							childPoshiElement, "Duplicate property name ",
							propertyName));
				}
			}
			else if (childElementName.equals("set-up") ||
					 childElementName.equals("tear-down")) {

				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement);
			}
			else if (childElementName.equals("var")) {
				validateVarElement(childPoshiElement);
			}
		}
	}

	protected static void validateTestName(String testName) {
		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				testName);

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				testName);

		if (!PoshiContext.isRootElement("test-case", className, namespace)) {
			_exceptions.add(
				new PoshiElementException(
					"Invalid test case class " + namespace + "." + className));
		}
		else if (testName.contains("#")) {
			String classCommandName =
				PoshiGetterUtil.
					getClassCommandNameFromNamespacedClassCommandName(testName);

			if (!PoshiContext.isCommandElement(
					"test-case", classCommandName, namespace)) {

				String commandName =
					PoshiGetterUtil.
						getCommandNameFromNamespacedClassCommandName(testName);

				_exceptions.add(
					new PoshiElementException(
						"Invalid test case command " + commandName));
			}
		}
	}

	protected static void validateThenElement(PoshiElement element) {
		List<PoshiElement> thenPoshiElements = element.toPoshiElements(
			element.elements("then"));

		if (thenPoshiElements.isEmpty()) {
			_exceptions.add(
				new PoshiElementException(element, "Missing then element"));
		}
		else if (thenPoshiElements.size() > 1) {
			_exceptions.add(
				new PoshiElementException(element, "Too many then elements"));
		}
	}

	protected static void validateUtilityClassName(
			Element element, String className)
		throws PoshiElementException {

		if (!className.startsWith("selenium")) {
			if (!className.contains(".")) {
				try {
					className = PoshiGetterUtil.getUtilityClassName(className);
				}
				catch (IllegalArgumentException illegalArgumentException) {
					throw new PoshiElementException(
						element, illegalArgumentException.getMessage());
				}
			}

			if (!PoshiGetterUtil.isValidUtilityClass(className)) {
				throw new PoshiElementException(
					element, className, " is an invalid utility class");
			}
		}
	}

	protected static void validateVarElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasNoChildElements(element);
		validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<Attribute> attributes = element.attributes();

		int minimumAttributeSize = 1;

		if ((attributes.size() <= minimumAttributeSize) &&
			Validator.isNull(element.getText())) {

			_exceptions.add(
				new PoshiElementException(element, "Missing value attribute"));
		}

		List<String> possibleAttributeNames = new ArrayList<>();

		Collections.addAll(
			possibleAttributeNames,
			new String[] {
				"from", "hash", "index", "line-number", "method", "name",
				"type", "value"
			});

		if (filePath.contains(".macro")) {
			possibleAttributeNames.add("static");
		}

		Element parentElement = element.getParent();

		if (parentElement != null) {
			String parentElementName = parentElement.getName();

			if (filePath.contains(".testcase") &&
				parentElementName.equals("definition")) {

				possibleAttributeNames.add("static");
			}
		}

		validatePossibleAttributeNames(element, possibleAttributeNames);

		if (Validator.isNotNull(element.attributeValue("method"))) {
			String methodAttributeValue = element.attributeValue("method");

			int x = methodAttributeValue.indexOf("#");

			String className = methodAttributeValue.substring(0, x);

			if (className.equals("selenium")) {
				validateSeleniumMethodAttributeValue(
					element, methodAttributeValue);
			}

			try {
				validateUtilityClassName(element, className);
			}
			catch (PoshiElementException poshiElementException) {
				_exceptions.add(poshiElementException);
			}

			int expectedAttributeCount = 0;

			if (Validator.isNotNull(element.attributeValue("name"))) {
				expectedAttributeCount++;
			}

			if (PoshiGetterUtil.getLineNumber(element) != -1) {
				expectedAttributeCount++;
			}

			if (Validator.isNotNull(element.attributeValue("static"))) {
				expectedAttributeCount++;
			}

			if (attributes.size() < expectedAttributeCount) {
				_exceptions.add(
					new PoshiElementException(element, "Too few attributes"));
			}

			if (attributes.size() > expectedAttributeCount) {
				_exceptions.add(
					new PoshiElementException(element, "Too many attributes"));
			}
		}
	}

	protected static void validateWhileElement(PoshiElement element) {
		String filePath = _getFilePath(element);

		validateHasChildElements(element, filePath);

		validatePossibleAttributeNames(
			element, Arrays.asList("line-number", "max-iterations"));
		validateThenElement(element);

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		for (int i = 0; i < childPoshiElements.size(); i++) {
			PoshiElement childPoshiElement = childPoshiElements.get(i);

			String childElementName = childPoshiElement.getName();

			if (i == 0) {
				if (conditionTags.contains(childElementName)) {
					validateConditionElement(childPoshiElement);
				}
				else {
					_exceptions.add(
						new PoshiElementException(
							element, "Missing while condition element"));
				}
			}
			else if (childElementName.equals("then")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement);
			}
			else {
				_exceptions.add(
					new PoshiElementException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}
		}
	}

	protected static final String[] UTIL_PACKAGE_NAMES = {
		"com.liferay.poshi.core.util", "com.liferay.poshi.runner.util"
	};

	private static String _getFilePath(PoshiElement poshiElement) {
		URL filePathURL = poshiElement.getFilePathURL();

		return filePathURL.getPath();
	}

	private static void _throwExceptions() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n");
		sb.append(_exceptions.size());
		sb.append(" errors in POSHI\n\n");

		for (Exception exception : _exceptions) {
			sb.append(exception.getMessage());
			sb.append("\n\n");
		}

		System.out.println(sb.toString());

		throw new Exception();
	}

	private static final Set<Exception> _exceptions = new HashSet<>();
	private static final Pattern _invalidMethodParameterPattern =
		Pattern.compile("(?<invalidSyntax>(?:locator|value)[1-3]?[\\s]*=)");
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");
	private static final Pattern _seleniumGetterMethodPattern = Pattern.compile(
		"^selenium#(?<methodName>get[A-z]+)" +
			"(?:\\((?<methodParameters>.*|)\\))?$");

	private static class ValidationException extends Exception {

		public ValidationException(Element element, Object... messageParts) {
			super(
				PoshiElementException.join(
					PoshiElementException.join(messageParts), ":",
					PoshiGetterUtil.getLineNumber(element)));
		}

		public ValidationException(String... messageParts) {
			super(PoshiElementException.join((Object[])messageParts));
		}

	}

}