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

import com.liferay.portal.kernel.util.Validator;

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

		if (classType.equals("path")) {
			_validatePathFile(element, filePath);
		}
		else {
			_validateRootElement(element, filePath);
		}
	}

	private static void _validatePathFile(Element element, String filePath)
		throws PoshiRunnerException {

		String rootElementName = element.getName();

		if (!StringUtils.equals(rootElementName, "html")) {
			throw new PoshiRunnerException(
				"\nBUILD FAILED: Invalid " + rootElementName + " element\n" +
					filePath + ":" + element.attributeValue("line-number"));
		}

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new PoshiRunnerException(
				"\nBUILD FAILED: Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validateRootElement(Element element, String filePath)
		throws PoshiRunnerException {

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);
		String elementName = element.getName();

		if (!StringUtils.equals(elementName, "definition")) {
			throw new PoshiRunnerException(
				"\nBUILD FAILED: Invalid " +
					elementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			if (Validator.isNull(attribute.getValue())) {
				throw new PoshiRunnerException(
					"\nBUILD FAILED: Missing attribute value\n" + filePath +
						":" + element.attributeValue("line-number"));
			}

			String attributeName = attribute.getName();

			if ((attributeName.equals("component-name") ||
				 attributeName.equals("ignore") ||
				 attributeName.equals("ignore-command-names")) &&
				classType.equals("testcase")) {

				continue;
			}
			else if (attributeName.equals("extends") &&
					 (classType.equals("macro") ||
					  classType.equals("testcase"))) {

				continue;
			}
			else if (attributeName.equals("default") &&
					 classType.equals("function")) {

				continue;
			}
			else if (attributeName.equals("line-number")) {
				continue;
			}
			else {
				throw new PoshiRunnerException(
					"\nBUILD FAILED: Invalid \"" + attributeName +
						"\" attribute\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}
	}

}