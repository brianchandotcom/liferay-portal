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

				validateFunctionFile((PoshiElement)element, filePath);
			}
			else if (classType.equals("macro")) {
				Element element = PoshiContext.getMacroRootElement(
					className, namespace);

				validateMacroFile((PoshiElement)element, filePath);
			}
			else if (classType.equals("path")) {
				Element element = PoshiContext.getPathRootElement(
					className, namespace);

				validatePathFile(element, filePath);
			}
			else if (classType.equals("test-case")) {
				Element element = PoshiContext.getTestCaseRootElement(
					className, namespace);

				validateTestCaseFile((PoshiElement)element, filePath);
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
		Element element, List<String> primaryAttributeNames) {

		return getPrimaryAttributeName(element, null, primaryAttributeNames);
	}

	protected static String getPrimaryAttributeName(
		Element element, List<String> multiplePrimaryAttributeNames,
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

	protected static void parseElements(PoshiElement element, String filePath) {
		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if", "property",
			"return", "take-screenshot", "task", "var", "while");

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
				validateExecuteElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("for")) {
				validateForElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("if")) {
				validateIfElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("property")) {
				validatePropertyElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("return")) {
				validateCommandReturnElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("take-screenshot")) {
				validateTakeScreenshotElement(childPoshiElement);
			}
			else if (elementName.equals("task")) {
				validateTaskElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("var")) {
				validateVarElement(childPoshiElement, filePath);
			}
			else if (elementName.equals("while")) {
				validateWhileElement(childPoshiElement, filePath);
			}
		}
	}

	protected static void validateArgElement(Element element, String filePath) {
		List<String> attributes = Arrays.asList("line-number", "value");

		validatePossibleAttributeNames(element, attributes);
		validateRequiredAttributeNames(element, attributes, filePath);
	}

	protected static void validateCommandElement(
		Element element, String filePath) {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "name", "prose", "return", "summary",
			"summary-ignore");

		validatePossibleAttributeNames(element, possibleAttributeNames);

		validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<Element> returnElements = PoshiGetterUtil.getAllChildElements(
			element, "return");

		List<Element> commandReturnElements = new ArrayList<>();

		for (Element returnElement : returnElements) {
			Element parentElement = returnElement.getParent();

			if (!Objects.equals(parentElement.getName(), "execute")) {
				commandReturnElements.add(returnElement);
			}
		}

		String returnName = element.attributeValue("return");

		if (Validator.isNull(returnName)) {
			for (Element commandReturnElement : commandReturnElements) {
				String returnVariableName = commandReturnElement.attributeValue(
					"name");
				String returnVariableValue =
					commandReturnElement.attributeValue("value");

				if (Validator.isNotNull(returnVariableName) &&
					Validator.isNotNull(returnVariableValue)) {

					_exceptions.add(
						new ValidationException(
							commandReturnElement,
							"No return variables were stated in command ",
							"declaration, but found return name-value ",
							"mapping"));
				}
			}
		}
		else {
			if (commandReturnElements.isEmpty()) {
				_exceptions.add(
					new ValidationException(
						element,
						"Return variable was stated, but no returns were ",
						"found"));
			}
			else {
				for (Element commandReturnElement : commandReturnElements) {
					String returnVariableName =
						commandReturnElement.attributeValue("name");

					if (Validator.isNull(returnVariableName)) {
						_exceptions.add(
							new ValidationException(
								commandReturnElement,
								"Return variable was stated as '", returnName,
								"', but no 'name' attribute was found"));

						continue;
					}

					if (returnName.equals(returnVariableName)) {
						continue;
					}

					_exceptions.add(
						new ValidationException(
							commandReturnElement, "'", returnVariableName,
							"' not listed as a return variable"));
				}
			}
		}
	}

	protected static void validateCommandReturnElement(
		Element element, String filePath) {

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(
			element, Arrays.asList("line-number", "name", "value"));
		validateRequiredAttributeNames(
			element, Arrays.asList("line-number", "value"), filePath);
	}

	protected static void validateConditionElement(
		Element element, String filePath) {

		String elementName = element.getName();

		if (elementName.equals("and") || elementName.equals("or")) {
			validateHasChildElements(element, filePath);
			validateHasNoAttributes(element);

			List<Element> childElements = element.elements();

			if (childElements.size() < 2) {
				_exceptions.add(
					new ValidationException(element, "Too few child elements"));
			}

			for (Element childElement : childElements) {
				validateConditionElement(childElement, filePath);
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

			List<Element> varElements = element.elements("var");

			for (Element varElement : varElements) {
				validateVarElement(varElement, filePath);
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

			List<Element> childElements = element.elements();

			validateConditionElement(childElements.get(0), filePath);
		}
	}

	protected static void validateDefinitionElement(
		Element element, String filePath) {

		String elementName = element.getName();

		if (!Objects.equals(elementName, "definition")) {
			_exceptions.add(
				new ValidationException(
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
		Element element, List<String> possibleElementNames) {

		if (!possibleElementNames.contains(element.getName())) {
			_exceptions.add(
				new ValidationException(
					element, "Missing ", possibleElementNames, " element"));
		}
	}

	protected static void validateElseElement(
		PoshiElement element, String filePath) {

		List<PoshiElement> elsePoshiElements = element.toPoshiElements(
			element.elements("else"));

		if (elsePoshiElements.size() > 1) {
			_exceptions.add(
				new ValidationException(element, "Too many else elements"));
		}

		if (!elsePoshiElements.isEmpty()) {
			PoshiElement elseElement = elsePoshiElements.get(0);

			parseElements(elseElement, filePath);
		}
	}

	protected static void validateElseIfElement(
		PoshiElement element, String filePath) {

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
			validateConditionElement(conditionElement, filePath);
		}
		else {
			_exceptions.add(
				new ValidationException(
					element, "Invalid ", conditionElementName, " element"));
		}

		PoshiElement thenElement = (PoshiElement)element.element("then");

		validateHasChildElements(thenElement, filePath);
		validateHasNoAttributes(thenElement);

		parseElements(thenElement, filePath);
	}

	protected static void validateExecuteElement(
		Element element, String filePath) {

		List<String> primaryAttributeNames = Arrays.asList(
			"function", "macro", "method", "selenium", "test-case");

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

			validateFunctionContext(element, filePath);
		}
		else if (primaryAttributeName.equals("macro")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro");

			validatePossibleAttributeNames(element, possibleAttributeNames);

			validateMacroContext(element, "macro", filePath);
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

			validateTestCaseContext(element, filePath);
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			primaryAttributeNames = Arrays.asList(
				"function", "macro", "method", "selenium", "test-case");

			validateHasPrimaryAttributeName(element, primaryAttributeNames);

			List<String> possibleChildElementNames = Arrays.asList(
				"arg", "prose", "return", "var");

			for (Element childElement : childElements) {
				String childElementName = childElement.getName();

				if (!possibleChildElementNames.contains(childElementName)) {
					_exceptions.add(
						new ValidationException(
							childElement, "Invalid child element"));
				}
			}

			List<Element> argElements = element.elements("arg");

			for (Element argElement : argElements) {
				validateArgElement(argElement, filePath);
			}

			List<Element> returnElements = element.elements("return");

			if ((returnElements.size() > 1) &&
				primaryAttributeName.equals("macro")) {

				_exceptions.add(
					new ValidationException(
						element, "Only 1 child element 'return' is allowed"));
			}

			Element returnElement = element.element("return");

			if (returnElement != null) {
				if (primaryAttributeName.equals("macro")) {
					validateExecuteReturnMacroElement(returnElement, filePath);
				}
				else if (primaryAttributeName.equals("method")) {
					validateExecuteReturnMethodElement(returnElement, filePath);
				}
			}

			List<Element> varElements = element.elements("var");
			List<String> varNames = new ArrayList<>();

			for (Element varElement : varElements) {
				validateVarElement(varElement, filePath);

				String varName = varElement.attributeValue("name");

				if (varNames.contains(varName)) {
					_exceptions.add(
						new ValidationException(
							element, "Duplicate variable name: " + varName));
				}

				varNames.add(varName);
			}
		}
	}

	protected static void validateExecuteReturnMacroElement(
		Element element, String filePath) {

		List<String> attributeNames = Arrays.asList("line-number", "name");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, attributeNames);
		validateRequiredAttributeNames(element, attributeNames, filePath);
	}

	protected static void validateExecuteReturnMethodElement(
		Element element, String filePath) {

		List<String> attributeNames = Arrays.asList("line-number", "name");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, attributeNames);
		validateRequiredAttributeNames(element, attributeNames, filePath);
	}

	protected static void validateForElement(
		PoshiElement element, String filePath) {

		validateHasChildElements(element, filePath);

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "list", "param", "table");

		validatePossibleAttributeNames(element, possibleAttributeNames);

		List<String> requiredAttributeNames = Arrays.asList(
			"line-number", "param");

		validateRequiredAttributeNames(
			element, requiredAttributeNames, filePath);

		parseElements(element, filePath);
	}

	protected static void validateFunctionContext(
		Element element, String filePath) {

		String function = element.attributeValue("function");

		validateNamespacedClassCommandName(
			element, function, "function", filePath);

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
						new ValidationException(
							element, "Invalid path name ", pathName));
				}
				else if (!PoshiContext.isPathLocator(locator, namespace) &&
						 !PoshiContext.isPathLocator(
							 locator, defaultNamespace)) {

					_exceptions.add(
						new ValidationException(
							element, "Invalid path locator ", locator));
				}
			}
		}
	}

	protected static void validateFunctionFile(
		PoshiElement element, String filePath) {

		validateDefinitionElement(element, filePath);
		validateHasChildElements(element, filePath);
		validateRequiredChildElementNames(
			element, Arrays.asList("command"), filePath);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

		for (PoshiElement childPoshiElement : childPoshiElements) {
			validateCommandElement(childPoshiElement, filePath);
			validateHasChildElements(childPoshiElement, filePath);

			parseElements(childPoshiElement, filePath);
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
		Element element, List<String> attributeNames,
		List<String> multiplePrimaryAttributeNames) {

		if (!multiplePrimaryAttributeNames.equals(attributeNames)) {
			_exceptions.add(
				new ValidationException(element, "Too many attributes"));
		}
	}

	protected static void validateHasNoAttributes(Element element) {
		List<Attribute> attributes = element.attributes();

		if (!attributes.isEmpty()) {
			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();

				if (attributeName.equals("line-number")) {
					continue;
				}

				_exceptions.add(
					new ValidationException(
						element, "Invalid ", attributeName, " attribute"));
			}
		}
	}

	protected static void validateHasNoChildElements(Element element) {
		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			_exceptions.add(
				new ValidationException(element, "Invalid child elements"));
		}
	}

	protected static void validateHasPrimaryAttributeName(
		Element element, List<String> primaryAttributeNames) {

		validateHasPrimaryAttributeName(element, null, primaryAttributeNames);
	}

	protected static void validateHasPrimaryAttributeName(
		Element element, List<String> multiplePrimaryAttributeNames,
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
				new ValidationException(
					element, "Invalid or missing attribute"));
		}
		else if (attributeNames.size() > 1) {
			if (multiplePrimaryAttributeNames == null) {
				_exceptions.add(
					new ValidationException(element, "Too many attributes"));
			}
			else {
				validateHasMultiplePrimaryAttributeNames(
					element, attributeNames, multiplePrimaryAttributeNames);
			}
		}
	}

	protected static void validateHasRequiredPropertyElements(
		Element element, String filePath) {

		List<String> requiredPropertyNames = new ArrayList<>(
			PoshiContext.getRequiredPoshiPropertyNames());

		List<Element> propertyElements = element.elements("property");

		for (Element propertyElement : propertyElements) {
			validatePropertyElement(propertyElement, filePath);

			String propertyName = propertyElement.attributeValue("name");

			if (requiredPropertyNames.contains(propertyName)) {
				requiredPropertyNames.remove(propertyName);
			}
		}

		if (requiredPropertyNames.isEmpty()) {
			return;
		}

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
					new ValidationException(
						element,
						className + "#" + commandName +
							" is missing required properties ",
						requiredPropertyNames.toString()));
			}
		}
	}

	protected static void validateIfElement(
		PoshiElement element, String filePath) {

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

		validateElseElement(element, filePath);
		validateThenElement(element);

		for (int i = 0; i < childPoshiElements.size(); i++) {
			PoshiElement childPoshiElement = childPoshiElements.get(i);

			String childElementName = childPoshiElement.getName();

			if (i == 0) {
				if (conditionTags.contains(childElementName)) {
					validateConditionElement(childPoshiElement, filePath);
				}
				else {
					_exceptions.add(
						new ValidationException(
							element,
							"Missing or invalid if condition element"));
				}
			}
			else if (childElementName.equals("else")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement, filePath);
			}
			else if (childElementName.equals("elseif")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				validateElseIfElement(childPoshiElement, filePath);
			}
			else if (childElementName.equals("then")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement, filePath);
			}
			else {
				_exceptions.add(
					new ValidationException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}
		}
	}

	protected static void validateMacroCommandName(Element element) {
		String attributeName = element.attributeValue("name");

		if (attributeName.contains("Url")) {
			_exceptions.add(
				new ValidationException(
					element, "Invalid macro command name: ", attributeName,
					". Use 'URL' instead of 'Url'"));
		}
	}

	protected static void validateMacroContext(
		Element element, String macroType, String filePath) {

		validateNamespacedClassCommandName(
			element, element.attributeValue(macroType), "macro", filePath);
	}

	protected static void validateMacroFile(
		PoshiElement element, String filePath) {

		validateDefinitionElement(element, filePath);
		validateHasChildElements(element, filePath);
		validateRequiredChildElementName(element, "command", filePath);

		List<PoshiElement> childElements = element.toPoshiElements(
			element.elements());

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (PoshiElement childPoshiElement : childElements) {
			String childElementName = childPoshiElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				_exceptions.add(
					new ValidationException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}

			if (childElementName.equals("command")) {
				validateCommandElement(childPoshiElement, filePath);
				validateHasChildElements(childPoshiElement, filePath);
				validateMacroCommandName(childPoshiElement);

				parseElements(childPoshiElement, filePath);
			}
			else if (childElementName.equals("var")) {
				validateVarElement(childPoshiElement, filePath);
			}
		}
	}

	protected static void validateMessageElement(Element element) {
		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "message");

		validateHasNoChildElements(element);
		validatePossibleAttributeNames(element, possibleAttributeNames);

		if ((element.attributeValue("message") == null) &&
			Validator.isNull(element.getText())) {

			_exceptions.add(
				new ValidationException(element, "Missing message attribute"));
		}
	}

	protected static void validateMethodExecuteElement(Element element) {
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
				new ValidationException(
					element, "Unable to find class ", className));

			return;
		}

		try {
			validateUtilityClassName(element, fullClassName);
		}
		catch (ValidationException validationException) {
			_exceptions.add(validationException);

			return;
		}

		String methodName = element.attributeValue("method");

		List<Method> possibleMethods = new ArrayList<>();

		List<Method> completeMethods = Arrays.asList(clazz.getMethods());

		List<Element> childElements = element.elements();

		for (Method possibleMethod : completeMethods) {
			String possibleMethodName = possibleMethod.getName();

			Class<?>[] methodParameterTypes =
				possibleMethod.getParameterTypes();

			if (methodName.equals(possibleMethodName) &&
				(methodParameterTypes.length == childElements.size())) {

				possibleMethods.add(possibleMethod);
			}
		}

		if (possibleMethods.isEmpty()) {
			_exceptions.add(
				new ValidationException(
					element, "Unable to find method ", fullClassName, "#",
					methodName));
		}
	}

	protected static void validateNamespacedClassCommandName(
		Element element, String namespacedClassCommandName, String classType,
		String filePath) {

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
			namespace = PoshiContext.getNamespaceFromFilePath(filePath);
		}

		if (!PoshiContext.isRootElement(classType, className, namespace) &&
			!PoshiContext.isRootElement(
				classType, className, defaultNamespace)) {

			_exceptions.add(
				new ValidationException(
					element, "Invalid ", classType, " class ", className));
		}

		if (!PoshiContext.isCommandElement(
				classType, classCommandName, namespace) &&
			!PoshiContext.isCommandElement(
				classType, classCommandName, defaultNamespace)) {

			_exceptions.add(
				new ValidationException(
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
					new ValidationException(element, "Missing child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Missing child elements", filePath));
		}
		else if (childElements.size() > number) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new ValidationException(
						element, "Too many child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Too many child elements", filePath));
		}
		else if (childElements.size() < number) {
			if (element instanceof PoshiElement) {
				_exceptions.add(
					new ValidationException(element, "Too few child elements"));
			}

			_exceptions.add(
				new ValidationException(
					element, "Too few child elements", filePath));
		}
	}

	protected static void validateOffElement(
		PoshiElement element, String filePath) {

		List<PoshiElement> offPoshiElements = element.toPoshiElements(
			element.elements("off"));

		if (offPoshiElements.size() > 1) {
			_exceptions.add(
				new ValidationException(element, "Too many off elements"));
		}

		if (!offPoshiElements.isEmpty()) {
			PoshiElement offElement = offPoshiElements.get(0);

			validateHasChildElements(offElement, filePath);
			validateHasNoAttributes(offElement);

			parseElements(offElement, filePath);
		}
	}

	protected static void validateOnElement(
		PoshiElement element, String filePath) {

		List<PoshiElement> onPoshiElements = element.toPoshiElements(
			element.elements("off"));

		if (onPoshiElements.size() > 1) {
			_exceptions.add(
				new ValidationException(element, "Too many off elements"));
		}

		if (!onPoshiElements.isEmpty()) {
			PoshiElement onElement = onPoshiElements.get(0);

			validateHasChildElements(onElement, filePath);
			validateHasNoAttributes(onElement);

			parseElements(onElement, filePath);
		}
	}

	protected static void validatePathFile(Element element, String filePath) {
		String className = PoshiGetterUtil.getClassNameFromFilePath(filePath);

		String rootElementName = element.getName();

		if (!Objects.equals(rootElementName, "html")) {
			_exceptions.add(
				new ValidationException(
					element, "Invalid ", rootElementName, " element"));
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
						new ValidationException(trElement, "Missing locator"));
				}

				if (locatorKey.equals("EXTEND_ACTION_PATH")) {
					String namespace = PoshiContext.getNamespaceFromFilePath(
						filePath);

					Element pathRootElement = PoshiContext.getPathRootElement(
						locator, namespace);

					if (pathRootElement == null) {
						_exceptions.add(
							new ValidationException(
								trElement, "Nonexistent parent path file"));
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
				new ValidationException(trElement, "Missing thead class name"));
		}
		else if (!Objects.equals(theadClassName, className)) {
			_exceptions.add(
				new ValidationException(
					trElement, "Thead class name does not match file name"));
		}

		Element headElement = element.element("head");

		validateHasChildElements(headElement, filePath);
		validateNumberOfChildElements(headElement, 1, filePath);
		validateRequiredChildElementName(headElement, "title", filePath);

		Element titleElement = headElement.element("title");

		if (!Objects.equals(titleElement.getText(), className)) {
			_exceptions.add(
				new ValidationException(
					titleElement, "File name and title are different"));
		}
	}

	protected static void validatePossibleAttributeNames(
		Element element, List<String> possibleAttributeNames) {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributeNames.contains(attributeName)) {
				_exceptions.add(
					new ValidationException(
						element, "Invalid ", attributeName, " attribute"));
			}
		}
	}

	protected static void validatePossiblePropertyValues(
		Element propertyElement) {

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
						new ValidationException(
							propertyElement, "Invalid property value '",
							propertyValue.trim(), "' for property name '",
							propertyName.trim()));
				}
			}
		}
	}

	protected static void validatePropertyElement(
		Element element, String filePath) {

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
					new ValidationException(
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
		Element element, String methodAttributeValue, String filePath) {

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
				new ValidationException(
					element, "Expected ", seleniumParameterCount,
					" parameter(s) for method \"", seleniumMethodName,
					"\" but found ", seleniumParameterCount, "\n", filePath));
		}

		for (String methodParameterValue : methodParameterValues) {
			Matcher invalidMethodParameterMatcher =
				_invalidMethodParameterPattern.matcher(methodParameterValue);

			if (invalidMethodParameterMatcher.find()) {
				String invalidSyntax = invalidMethodParameterMatcher.group(
					"invalidSyntax");

				_exceptions.add(
					new ValidationException(
						element, "Invalid parameter syntax \"", invalidSyntax,
						"\"\n", filePath));
			}
		}
	}

	protected static void validateTakeScreenshotElement(Element element) {
		validateHasNoAttributes(element);
		validateHasNoChildElements(element);
	}

	protected static void validateTaskElement(
		PoshiElement element, String filePath) {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "macro-summary", "summary");

		validateHasChildElements(element, filePath);
		validatePossibleAttributeNames(element, possibleAttributeNames);

		List<String> primaryAttributeNames = Arrays.asList(
			"macro-summary", "summary");

		validateHasPrimaryAttributeName(element, primaryAttributeNames);

		parseElements(element, filePath);
	}

	protected static void validateTestCaseContext(
		Element element, String filePath) {

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

	protected static void validateTestCaseFile(
		PoshiElement element, String filePath) {

		validateDefinitionElement(element, filePath);

		List<PoshiElement> childPoshiElements = element.toPoshiElements(
			element.elements());

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
					new ValidationException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}

			if (childElementName.equals("command")) {
				List<String> possibleAttributeNames = Arrays.asList(
					"annotations", "description", "ignore", "known-issues",
					"line-number", "name", "priority");

				validateHasChildElements(childPoshiElement, filePath);
				validateHasRequiredPropertyElements(
					childPoshiElement, filePath);
				validatePossibleAttributeNames(
					childPoshiElement, possibleAttributeNames);
				validateRequiredAttributeNames(
					childPoshiElement, Arrays.asList("name"), filePath);

				parseElements(childPoshiElement, filePath);
			}
			else if (childElementName.equals("property")) {
				validatePropertyElement(childPoshiElement, filePath);

				String propertyName = childPoshiElement.attributeValue("name");

				if (!propertyNames.contains(propertyName)) {
					propertyNames.add(propertyName);
				}
				else {
					_exceptions.add(
						new ValidationException(
							childPoshiElement, "Duplicate property name ",
							propertyName));
				}
			}
			else if (childElementName.equals("set-up") ||
					 childElementName.equals("tear-down")) {

				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement, filePath);
			}
			else if (childElementName.equals("var")) {
				validateVarElement(childPoshiElement, filePath);
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
				new ValidationException(
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
					new ValidationException(
						"Invalid test case command " + commandName));
			}
		}
	}

	protected static void validateThenElement(Element element) {
		List<Element> thenElements = element.elements("then");

		if (thenElements.isEmpty()) {
			_exceptions.add(
				new ValidationException(element, "Missing then element"));
		}
		else if (thenElements.size() > 1) {
			_exceptions.add(
				new ValidationException(element, "Too many then elements"));
		}
	}

	protected static void validateUtilityClassName(
			Element element, String className)
		throws ValidationException {

		if (!className.startsWith("selenium")) {
			if (!className.contains(".")) {
				try {
					className = PoshiGetterUtil.getUtilityClassName(className);
				}
				catch (IllegalArgumentException illegalArgumentException) {
					throw new ValidationException(
						element, illegalArgumentException.getMessage());
				}
			}

			if (!PoshiGetterUtil.isValidUtilityClass(className)) {
				throw new ValidationException(
					element, className, " is an invalid utility class");
			}
		}
	}

	protected static void validateVarElement(Element element, String filePath) {
		validateHasNoChildElements(element);
		validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<Attribute> attributes = element.attributes();

		int minimumAttributeSize = 1;

		if ((attributes.size() <= minimumAttributeSize) &&
			Validator.isNull(element.getText())) {

			_exceptions.add(
				new ValidationException(element, "Missing value attribute"));
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
					element, methodAttributeValue, filePath);
			}

			try {
				validateUtilityClassName(element, className);
			}
			catch (ValidationException validationException) {
				_exceptions.add(validationException);
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
					new ValidationException(element, "Too few attributes"));
			}

			if (attributes.size() > expectedAttributeCount) {
				_exceptions.add(
					new ValidationException(element, "Too many attributes"));
			}
		}
	}

	protected static void validateWhileElement(
		PoshiElement element, String filePath) {

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
					validateConditionElement(childPoshiElement, filePath);
				}
				else {
					_exceptions.add(
						new ValidationException(
							element, "Missing while condition element"));
				}
			}
			else if (childElementName.equals("then")) {
				validateHasChildElements(childPoshiElement, filePath);
				validateHasNoAttributes(childPoshiElement);

				parseElements(childPoshiElement, filePath);
			}
			else {
				_exceptions.add(
					new ValidationException(
						childPoshiElement, "Invalid ", childElementName,
						" element"));
			}
		}
	}

	protected static final String[] UTIL_PACKAGE_NAMES = {
		"com.liferay.poshi.core.util", "com.liferay.poshi.runner.util"
	};

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