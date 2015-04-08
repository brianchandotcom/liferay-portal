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

import com.liferay.poshi.runner.util.Validator;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerValidation {

	public static void validate(Element element, String filePath)
		throws PoshiRunnerException {

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("function")) {
			_validateFunctionFile(element, filePath);
		}
		else if (classType.equals("macro")) {
			_validateMacroFile(element, filePath);
		}
		else if (classType.equals("path")) {
			_validatePathFile(element, filePath);
		}
		else if (classType.equals("testcase")) {
			_validateTestcaseFile(element, filePath);
		}
	}

	private static void _parseElements(Element element, String filePath)
		throws PoshiRunnerException {

		List<Element> childElements = element.elements();

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if",
			"take-screenshot", "task", "var", "while");

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (!possibleElementNames.contains(elementName)) {
				throw new PoshiRunnerException(
					"Invalid " + elementName + " element\n" + filePath + ":" +
						childElement.attributeValue("line-number"));
			}
		}
	}

	private static void _validateAttributes(
			Element element, List<String> possibleAttributes, String filePath)
		throws PoshiRunnerException {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributes.contains(attributeName)) {
				throw new PoshiRunnerException(
					"Invalid " + attributeName + " attribute\n" + filePath +
						":" + element.attributeValue("line-number"));
			}

			if (!attributeName.startsWith("value") &&
				Validator.isNull(attribute.getValue())) {

				throw new PoshiRunnerException(
					"Missing " + attributeName + " attribute value\n" +
						filePath + ":" + element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateDefinitionElement(
			Element element, String filePath)
		throws PoshiRunnerException {

		String elementName = element.getName();

		if (!StringUtils.equals(elementName, "definition")) {
			throw new PoshiRunnerException(
				"Invalid " + elementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (Validator.isNull(attribute.getValue())) {
				throw new PoshiRunnerException(
					"Missing " + attributeName + " attribute value\n" +
						filePath + ":" + element.attributeValue("line-number"));
			}

			if (attributeName.equals("line-number")) {
				continue;
			}

			if (classType.equals("function")) {
				if (attributeName.equals("default") ||
					attributeName.equals("summary")) {

					continue;
				}
			}

			if (classType.equals("macro")) {
				if (attributeName.equals("extends")) {
					continue;
				}
			}

			if (classType.equals("testcase")) {
				if (attributeName.equals("component-name") ||
					attributeName.equals("extends") ||
					attributeName.equals("ignore") ||
					attributeName.equals("ignore-command-names")) {

					continue;
				}
			}

			throw new PoshiRunnerException(
				"Invalid " + attributeName + " attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validateFunctionFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);
	}

	private static void _validateMacroFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.isEmpty() &&
			(element.attributeValue("extends") == null)) {

			throw new PoshiRunnerException(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				throw new PoshiRunnerException(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			if (childElementName.equals("command")) {
				if (Validator.isNull(childElement.attributeValue("name"))) {
					throw new PoshiRunnerException(
						"Missing name attribute\n" + filePath + ":" +
							childElement.attributeValue("line-number"));
				}

				List<String> possibleAttributes = Arrays.asList(
					"line-number", "name", "priority", "summary",
					"summary-ignore");

				_validateAttributes(childElement, possibleAttributes, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validatePathFile(Element element, String filePath)
		throws PoshiRunnerException {

		String rootElementName = element.getName();

		if (!StringUtils.equals(rootElementName, "html")) {
			throw new PoshiRunnerException(
				"Invalid " + rootElementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new PoshiRunnerException(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validateTestcaseFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);
	}

	private static void _validateVarElement(Element element, String filePath)
		throws PoshiRunnerException {

		if (Validator.isNull(element.attributeValue("name"))) {
			throw new PoshiRunnerException(
				"Missing name attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Attribute> attributes = element.attributes();

		if (attributes.size() <= 2) {
			if (Validator.isNull(element.getText())) {
				throw new PoshiRunnerException(
					"Missing value attribute\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}

		List<String> possibleAttributes = Arrays.asList(
			"attribute", "group", "line-number", "locator", "method", "name",
			"pattern", "value");

		_validateAttributes(element, possibleAttributes, filePath);
	}

}