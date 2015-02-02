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

import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.SeleniumUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerExecutor {

	public static void parseElement(Element element) throws Exception {
		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals("execute")) {
				if (childElement.attributeValue("function") != null) {
					runFunctionElement(childElement);
				}
				else if (childElement.attributeValue("selenium") != null) {
					runSeleniumElement(childElement);
				}
			}
		}
	}

	public static void runFunctionElement(Element element) throws Exception {
		String classCommandName = element.attributeValue("function");

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < locatorCount; i++) {
			String locator = element.attributeValue("locator" + (i + 1));
			String value = element.attributeValue("value" + (i + 1));

			if (locator != null) {
				if (locator.contains("#")) {
					String pathClassName =
						PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
							locator);

					String locatorKey =
						PoshiRunnerGetterUtil.
							getCommandNameFromClassCommandName(locator);

					locator = PoshiRunnerContext.getPathLocator(
						pathClassName + "#" + locatorKey);
				}

				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"locator" + (i + 1), locator);
			}

			if (value != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"value" + (i + 1), value);
			}
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element function = PoshiRunnerContext.getFunctionCommandElement(
			classCommandName);

		parseElement(function);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runSeleniumElement(Element element) throws Exception {
		List<String> arguments = new ArrayList<>();
		List<Class> parameterClasses = new ArrayList<>();

		String selenium = element.attributeValue("selenium");

		int parameterCount = PoshiRunnerContext.getSeleniumParameterCount(
			selenium);

		for (int i = 0; i < parameterCount; i++) {
			String argument = element.attributeValue("argument" + (i + 1));

			if (argument == null) {
				if (i == 0) {
					if (selenium.equals("assertConfirmation") ||
						selenium.equals("assertConsoleTextNotPresent") ||
						selenium.equals("assertConsoleTextPresent") ||
						selenium.equals("assertLocation") ||
						selenium.equals("assertTextNotPresent") ||
						selenium.equals("assertTextPresent") ||
						selenium.equals("waitForConfirmation") ||
						selenium.equals("waitForTextNotPresent") ||
						selenium.equals("waitForTextPresent")) {

						argument =
							PoshiRunnerVariablesUtil.getValueFromCommandMap(
								"value1");
					}
					else {
						argument =
							PoshiRunnerVariablesUtil.getValueFromCommandMap(
								"locator1");
					}
				}
				else if (i == 1) {
					argument = PoshiRunnerVariablesUtil.getValueFromCommandMap(
						"value1");
				}
				else if (i == 2) {
					argument = PoshiRunnerVariablesUtil.getValueFromCommandMap(
						"locator2");
				}
			}

			arguments.add(argument);

			parameterClasses.add(String.class);
		}

		Class clazz = _liferaySelenium.getClass();

		Method method = clazz.getMethod(
			selenium,
			parameterClasses.toArray(new Class[parameterClasses.size()]));

		method.invoke(
			_liferaySelenium, arguments.toArray(new String[arguments.size()]));
	}

	private static final LiferaySelenium _liferaySelenium =
		SeleniumUtil.getSelenium();

}