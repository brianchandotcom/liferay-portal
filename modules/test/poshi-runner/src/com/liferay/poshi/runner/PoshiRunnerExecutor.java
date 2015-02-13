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

	public static boolean evaluateConditionalElement(Element element)
		throws Exception {

		String elementName = element.getName();

		if (elementName.equals("and")) {
			List<Element> andElements = element.elements();

			for (Element andElement : andElements) {
				if (!evaluateConditionalElement(andElement)) {
					return false;
				}
			}

			return true;
		}
		else if (elementName.equals("contains")) {
			String string = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("string"));
			String substring = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("substring"));

			if (string.contains(substring)) {
				return true;
			}

			return false;
		}
		else if (elementName.equals("equals")) {
			String arg1 = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("arg1"));
			String arg2 = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("arg2"));

			if (arg1.equals(arg2)) {
				return true;
			}

			return false;
		}
		else if (elementName.equals("isset")) {
			if (PoshiRunnerVariablesUtil.containsKeyInCommandMap(
					element.attributeValue("var"))) {

				return true;
			}

			return false;
		}
		else if (elementName.equals("or")) {
			List<Element> orElements = element.elements();

			for (Element orElement : orElements) {
				if (evaluateConditionalElement(orElement)) {
					return true;
				}
			}

			return false;
		}
		else if (elementName.equals("not")) {
			List<Element> notElements = element.elements();

			Element notElement = notElements.get(0);

			return !evaluateConditionalElement(notElement);
		}

		return false;
	}

	public static void parseElement(Element element) throws Exception {
		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals("echo") ||
				childElementName.equals("description")) {

				String message = childElement.attributeValue("message");

				System.out.println(
					PoshiRunnerVariablesUtil.replaceCommandVars(message));
			}
			else if (childElementName.equals("execute")) {
				if (childElement.attributeValue("action") != null) {
					runActionElement(childElement);
				}
				else if (childElement.attributeValue("function") != null) {
					runFunctionElement(childElement);
				}
				else if (childElement.attributeValue("macro") != null) {
					runMacroElement(childElement);
				}
				else if (childElement.attributeValue("selenium") != null) {
					runSeleniumElement(childElement);
				}
			}
			else if (childElementName.equals("if")) {
				runIfElement(childElement);
			}
			else if (childElementName.equals("fail")) {
				String message = childElement.attributeValue("message");

				if (message != null) {
					throw new Exception(
						PoshiRunnerVariablesUtil.replaceCommandVars(message));
				}

				throw new Exception();
			}
			else if (childElementName.equals("for")) {
				runForElement(childElement);
			}
			else if (childElementName.equals("var")) {
				runVarElement(childElement);
			}
		}
	}

	public static void runActionElement(Element executeElement)
		throws Exception {

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			String name = executeVarElement.attributeValue("name");
			String value = executeVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		String actionClassCommandName = executeElement.attributeValue("action");

		int locatorCount = PoshiRunnerContext.getActionLocatorCount(
			actionClassCommandName);

		for (int i = 0; i < locatorCount; i++) {
			String locator = executeElement.attributeValue("locator" + (i + 1));
			String locatorKey = executeElement.attributeValue(
				"locator-key" + (i + 1));
			String value = executeElement.attributeValue("value" + (i + 1));

			if (locator != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"locator" + (i + 1), locator);
			}
			else if (locatorKey != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"locator-key" + (i + 1), locatorKey);

				String pathClassName =
					PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
						actionClassCommandName);

				locator = PoshiRunnerContext.getPathLocator(
					pathClassName + "#" + locatorKey);

				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"locator" + (i + 1), locator);
			}

			if (value != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"value" + (i + 1), value);
			}
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		List<Element> caseElements = PoshiRunnerContext.getActionCaseElements(
			actionClassCommandName);

		runCaseElements(caseElements, locatorCount);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runCaseElements(
			List<Element> caseElements, int locatorCount)
		throws Exception {

		for (Element caseElement : caseElements) {
			String elementName = caseElement.getName();

			if (elementName.equals("case")) {
				String attributeName = null;
				String expected = null;

				String[] arguments =
					new String[]{"locator", "locator-key", "value"};

				for (int i = 0; i < locatorCount; i++) {
					for (String argument : arguments) {
						attributeName = argument + (i + 1);

						expected = caseElement.attributeValue(attributeName);

						if (expected != null) {
							break;
						}
					}
				}

				String actual = PoshiRunnerVariablesUtil.getValueFromCommandMap(
					attributeName);

				if (actual == null) {
					continue;
				}

				String comparator = caseElement.attributeValue("comparator");

				if (comparator == null) {
					comparator = "equals";
				}

				if ((comparator.equals("contains") &&
					 actual.contains(expected)) ||
					(comparator.equals("endsWith") &&
					 actual.endsWith(expected)) ||
					(comparator.equals("equals") && actual.equals(expected)) ||
					(comparator.equals("startsWith") &&
					 actual.startsWith(expected))) {

					parseElement(caseElement);

					break;
				}
			}
			else if (elementName.equals("default")) {
				parseElement(caseElement);

				break;
			}
		}
	}

	public static void runForElement(Element element) throws Exception {
		String list = PoshiRunnerVariablesUtil.replaceCommandVars(
			element.attributeValue("list"));

		String[] paramValues = list.split(",");

		String paramName = PoshiRunnerVariablesUtil.replaceCommandVars(
			element.attributeValue("param"));

		for (String paramValue : paramValues) {
			PoshiRunnerVariablesUtil.putIntoCommandMap(paramName, paramValue);

			parseElement(element);
		}
	}

	public static void runFunctionElement(Element executeElement)
		throws Exception {

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			String name = executeVarElement.attributeValue("name");
			String value = executeVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		String classCommandName = executeElement.attributeValue("function");

		String className = classCommandName;

		if (classCommandName.contains("#")) {
			className = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);
		}

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < locatorCount; i++) {
			String locator = executeElement.attributeValue("locator" + (i + 1));

			if (locator == null) {
				locator = PoshiRunnerVariablesUtil.getValueFromCommandMap(
					"locator" + (i + 1));
			}

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

			String value = executeElement.attributeValue("value" + (i + 1));

			if (value == null) {
				value = PoshiRunnerVariablesUtil.getValueFromCommandMap(
					"value" + (i + 1));
			}

			if (value != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"value" + (i + 1), value);
			}
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element commandElement = PoshiRunnerContext.getFunctionCommandElement(
			classCommandName);

		parseElement(commandElement);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runIfElement(Element element) throws Exception {
		List<Element> ifChildElements = element.elements();

		Element ifConditionElement = ifChildElements.get(0);

		boolean condition = evaluateConditionalElement(ifConditionElement);

		if (condition) {
			Element ifThenElement = element.element("then");

			parseElement(ifThenElement);
		}
		else if (element.element("elseif") != null) {
			List<Element> elseIfElements = element.elements("elseif");

			for (Element elseIfElement : elseIfElements) {
				List<Element> elseIfChildElements = elseIfElement.elements();

				Element elseIfConditionElement = elseIfChildElements.get(0);

				condition = evaluateConditionalElement(elseIfConditionElement);

				if (condition) {
					Element elseIfThenElement = elseIfElement.element("then");

					parseElement(elseIfThenElement);

					break;
				}
			}
		}

		if ((element.element("else") != null) && !condition) {
			Element elseElement = element.element("else");

			parseElement(elseElement);
		}
	}

	public static void runMacroElement(Element executeElement)
		throws Exception {

		String classCommandName = executeElement.attributeValue("macro");

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		Element rootElement = PoshiRunnerContext.getMacroRootElement(className);

		List<Element> rootVarElements = rootElement.elements("var");

		for (Element rootVarElement : rootVarElements) {
			String name = rootVarElement.attributeValue("name");
			String value = rootVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			String name = executeVarElement.attributeValue("name");
			String value = executeVarElement.attributeValue("value");

			PoshiRunnerVariablesUtil.putIntoExecuteMap(name, value);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		Element commandElement = PoshiRunnerContext.getMacroCommandElement(
			classCommandName);

		parseElement(commandElement);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runSeleniumElement(Element executeElement)
		throws Exception {

		List<String> arguments = new ArrayList<>();
		List<Class> parameterClasses = new ArrayList<>();

		String selenium = executeElement.attributeValue("selenium");

		int parameterCount = PoshiRunnerContext.getSeleniumParameterCount(
			selenium);

		for (int i = 0; i < parameterCount; i++) {
			String argument = executeElement.attributeValue(
				"argument" + (i + 1));

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

	public static void runVarElement(Element element) throws Exception {
		String varName = element.attributeValue("name");
		String varValue = element.attributeValue("value");

		if (varValue == null) {
			varValue = element.elementText("var");
		}

		varValue = PoshiRunnerVariablesUtil.replaceCommandVars(varValue);

		PoshiRunnerVariablesUtil.putIntoCommandMap(varName, varValue);
	}

	private static final LiferaySelenium _liferaySelenium =
		SeleniumUtil.getSelenium();

}