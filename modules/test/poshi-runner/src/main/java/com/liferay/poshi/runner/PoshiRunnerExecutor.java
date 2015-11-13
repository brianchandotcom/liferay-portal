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

import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.logger.CommandLoggerHandler;
import com.liferay.poshi.runner.logger.SummaryLoggerHandler;
import com.liferay.poshi.runner.logger.XMLLoggerHandler;
import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.PropsUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.RegexUtil;
import com.liferay.poshi.runner.util.Validator;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PoshiRunnerExecutor {

	public static boolean evaluateConditionalElement(Element element)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		XMLLoggerHandler.updateStatus(element, "pending");

		boolean conditionalValue = false;

		String elementName = element.getName();

		if (elementName.equals("and")) {
			List<Element> andElements = element.elements();

			conditionalValue = true;

			for (Element andElement : andElements) {
				if (conditionalValue) {
					conditionalValue = evaluateConditionalElement(andElement);
				}

				if (!conditionalValue) {
					break;
				}
			}
		}
		else if (elementName.equals("condition")) {
			if (element.attributeValue("function") != null) {
				runFunctionExecuteElement(element);

				conditionalValue = (boolean)_returnObject;
			}
			else if (element.attributeValue("selenium") != null) {
				runSeleniumElement(element);

				conditionalValue = (boolean)_returnObject;
			}
		}
		else if (elementName.equals("contains")) {
			String string = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("string"));
			String substring = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("substring"));

			if (string.contains(substring)) {
				conditionalValue = true;
			}
		}
		else if (elementName.equals("equals")) {
			String arg1 = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("arg1"));
			String arg2 = PoshiRunnerVariablesUtil.replaceCommandVars(
				element.attributeValue("arg2"));

			if (arg1.equals(arg2)) {
				conditionalValue = true;
			}
		}
		else if (elementName.equals("isset")) {
			if (PoshiRunnerVariablesUtil.containsKeyInCommandMap(
					element.attributeValue("var"))) {

				conditionalValue = true;
			}
		}
		else if (elementName.equals("or")) {
			List<Element> orElements = element.elements();

			for (Element orElement : orElements) {
				if (!conditionalValue) {
					conditionalValue = evaluateConditionalElement(orElement);
				}

				if (conditionalValue) {
					break;
				}
			}
		}
		else if (elementName.equals("not")) {
			List<Element> notElements = element.elements();

			Element notElement = notElements.get(0);

			conditionalValue = !evaluateConditionalElement(notElement);
		}

		if (conditionalValue) {
			XMLLoggerHandler.updateStatus(element, "pass");
		}
		else {
			XMLLoggerHandler.updateStatus(element, "conditional-fail");
		}

		return conditionalValue;
	}

	public static void parseElement(Element element) throws Exception {
		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals("echo") ||
				childElementName.equals("description")) {

				runEchoElement(childElement);
			}
			else if (childElementName.equals("execute")) {
				if (childElement.attributeValue("function") != null) {
					runFunctionExecuteElement(childElement);
				}
				else if (childElement.attributeValue("macro") != null) {
					runMacroExecuteElement(childElement, "macro");
				}
				else if ((childElement.attributeValue(
							"macro-desktop") != null) &&
						 !PropsValues.MOBILE_BROWSER) {

					runMacroExecuteElement(childElement, "macro-desktop");
				}
				else if ((childElement.attributeValue(
							"macro-mobile") != null) &&
						 PropsValues.MOBILE_BROWSER) {

					runMacroExecuteElement(childElement, "macro-mobile");
				}
				else if (childElement.attributeValue("selenium") != null) {
					runSeleniumElement(childElement);
				}
				else if (childElement.attributeValue("test-case") != null) {
					runTestCaseExecuteElement(childElement);
				}
			}
			else if (childElementName.equals("if")) {
				runIfElement(childElement);
			}
			else if (childElementName.equals("fail")) {
				runFailElement(childElement);
			}
			else if (childElementName.equals("for")) {
				runForElement(childElement);
			}
			else if (childElementName.equals("return")) {
				runReturnElement(childElement);
			}
			else if (childElementName.equals("task")) {
				runTaskElement(childElement);
			}
			else if (childElementName.equals("var")) {
				runVarElement(childElement, true, true);
			}
			else if (childElementName.equals("while")) {
				runWhileElement(childElement);
			}
		}
	}

	public static void runEchoElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		CommandLoggerHandler.logMessage(element);

		String message = element.attributeValue("message");

		if (message == null) {
			message = element.getText();
		}

		System.out.println(
			PoshiRunnerVariablesUtil.replaceCommandVars(message));
	}

	public static void runFailElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		CommandLoggerHandler.logMessage(element);

		String message = element.attributeValue("message");

		XMLLoggerHandler.updateStatus(element, "fail");

		if (Validator.isNotNull(message)) {
			throw new Exception(
				PoshiRunnerVariablesUtil.replaceCommandVars(message));
		}

		throw new Exception();
	}

	public static void runForElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		XMLLoggerHandler.updateStatus(element, "pending");

		String list = PoshiRunnerVariablesUtil.replaceCommandVars(
			element.attributeValue("list"));

		String[] paramValues = list.split(",");

		String paramName = PoshiRunnerVariablesUtil.replaceCommandVars(
			element.attributeValue("param"));

		for (String paramValue : paramValues) {
			PoshiRunnerVariablesUtil.putIntoCommandMap(paramName, paramValue);

			parseElement(element);
		}

		XMLLoggerHandler.updateStatus(element, "pass");
	}

	public static void runFunctionCommandElement(
			String classCommandName, Element commandElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(commandElement);

		PoshiRunnerVariablesUtil.pushCommandMap();

		try {
			parseElement(commandElement);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			PoshiRunnerVariablesUtil.popCommandMap();
		}
	}

	public static void runFunctionExecuteElement(Element executeElement)
		throws Exception {

		if (_functionExecuteElement == null) {
			_functionExecuteElement = executeElement;
		}

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runVarElement(executeVarElement, false, false);
		}

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		String classCommandName = executeElement.attributeValue("function");

		String className = classCommandName;

		if (classCommandName.contains("#")) {
			className = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);
		}

		Exception exception = null;

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < locatorCount; i++) {
			String locator = executeElement.attributeValue("locator" + (i + 1));

			if (locator == null) {
				locator = PoshiRunnerVariablesUtil.getValueFromCommandMap(
					"locator" + (i + 1));
			}

			if (locator != null) {
				Matcher matcher = _locatorKeyPattern.matcher(locator);

				if (matcher.find() && !locator.contains("/")) {
					String pathClassName =
						PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
							locator);

					String locatorKey =
						PoshiRunnerVariablesUtil.replaceCommandVars(
							PoshiRunnerGetterUtil.
								getCommandNameFromClassCommandName(locator));

					PoshiRunnerVariablesUtil.putIntoExecuteMap(
						"locator-key" + (i + 1), locatorKey);

					try {
						locator = PoshiRunnerContext.getPathLocator(
							pathClassName + "#" + locatorKey);
					}
					catch (Exception e) {
						exception = e;
					}

					locator = PoshiRunnerVariablesUtil.replaceExecuteVars(
						locator);
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

		SummaryLoggerHandler.startSummary(executeElement);

		CommandLoggerHandler.startCommand(executeElement);

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		Element commandElement = PoshiRunnerContext.getFunctionCommandElement(
			classCommandName);

		try {
			if (exception != null) {
				throw exception;
			}

			runFunctionCommandElement(classCommandName, commandElement);
		}
		catch (Throwable t) {
			String warningMessage = _getWarningFromThrowable(t);

			if (warningMessage != null) {
				_functionWarningMessage = warningMessage;
			}
			else {
				PoshiRunnerStackTraceUtil.popStackTrace();

				if (_functionExecuteElement == executeElement) {
					PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

					SummaryLoggerHandler.failSummary(
						_functionExecuteElement, t.getMessage());

					CommandLoggerHandler.failCommand(_functionExecuteElement);

					_functionExecuteElement = null;
					_functionWarningMessage = null;
				}

				throw t;
			}
		}

		PoshiRunnerStackTraceUtil.popStackTrace();

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		if (_functionExecuteElement == executeElement) {
			if (_functionWarningMessage != null) {
				SummaryLoggerHandler.warnSummary(
					_functionExecuteElement, _functionWarningMessage);

				CommandLoggerHandler.warnCommand(_functionExecuteElement);
			}
			else {
				SummaryLoggerHandler.passSummary(executeElement);

				CommandLoggerHandler.passCommand(executeElement);
			}

			_functionExecuteElement = null;
			_functionWarningMessage = null;
		}
	}

	public static void runIfElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		XMLLoggerHandler.updateStatus(element, "pending");

		List<Element> ifChildElements = element.elements();

		Element ifConditionElement = ifChildElements.get(0);

		boolean condition = evaluateConditionalElement(ifConditionElement);
		boolean conditionRun = false;

		if (condition) {
			conditionRun = true;

			Element ifThenElement = element.element("then");

			PoshiRunnerStackTraceUtil.setCurrentElement(ifThenElement);

			XMLLoggerHandler.updateStatus(ifThenElement, "pending");

			parseElement(ifThenElement);

			XMLLoggerHandler.updateStatus(ifThenElement, "pass");
		}
		else if (element.element("elseif") != null) {
			List<Element> elseIfElements = element.elements("elseif");

			for (Element elseIfElement : elseIfElements) {
				PoshiRunnerStackTraceUtil.setCurrentElement(elseIfElement);

				XMLLoggerHandler.updateStatus(elseIfElement, "pending");

				List<Element> elseIfChildElements = elseIfElement.elements();

				Element elseIfConditionElement = elseIfChildElements.get(0);

				condition = evaluateConditionalElement(elseIfConditionElement);

				if (condition) {
					conditionRun = true;

					Element elseIfThenElement = elseIfElement.element("then");

					PoshiRunnerStackTraceUtil.setCurrentElement(
						elseIfThenElement);

					XMLLoggerHandler.updateStatus(elseIfThenElement, "pending");

					parseElement(elseIfThenElement);

					XMLLoggerHandler.updateStatus(elseIfThenElement, "pass");
					XMLLoggerHandler.updateStatus(elseIfElement, "pass");

					break;
				}
				else {
					XMLLoggerHandler.updateStatus(
						elseIfElement, "conditional-fail");
				}
			}
		}

		if ((element.element("else") != null) && !conditionRun) {
			conditionRun = true;

			Element elseElement = element.element("else");

			PoshiRunnerStackTraceUtil.setCurrentElement(elseElement);

			XMLLoggerHandler.updateStatus(elseElement, "pending");

			parseElement(elseElement);

			XMLLoggerHandler.updateStatus(elseElement, "pass");
		}

		if (conditionRun) {
			XMLLoggerHandler.updateStatus(element, "pass");
		}
		else {
			XMLLoggerHandler.updateStatus(element, "conditional-fail");
		}
	}

	public static void runMacroCommandElement(
			String classCommandName, Element commandElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(commandElement);

		PoshiRunnerVariablesUtil.pushCommandMap();

		parseElement(commandElement);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runMacroExecuteElement(
			Element executeElement, String macroType)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		XMLLoggerHandler.updateStatus(executeElement, "pending");

		String classCommandName = executeElement.attributeValue(macroType);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		Element rootElement = PoshiRunnerContext.getMacroRootElement(className);

		List<Element> rootVarElements = rootElement.elements("var");

		for (Element rootVarElement : rootVarElements) {
			runVarElement(rootVarElement, false, true);
		}

		PoshiRunnerStackTraceUtil.popStackTrace();

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runVarElement(executeVarElement, false, false);
		}

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		SummaryLoggerHandler.startSummary(executeElement);

		Element commandElement = PoshiRunnerContext.getMacroCommandElement(
			classCommandName);

		try {
			runMacroCommandElement(classCommandName, commandElement);
		}
		catch (Exception e) {
			SummaryLoggerHandler.failSummary(executeElement, e.getMessage());

			throw e;
		}

		SummaryLoggerHandler.passSummary(executeElement);

		PoshiRunnerStackTraceUtil.popStackTrace();

		XMLLoggerHandler.updateStatus(executeElement, "pass");
	}

	public static void runReturnElement(Element returnElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(returnElement);

		if (returnElement.attributeValue("value") != null) {
			String returnValue = returnElement.attributeValue("value");

			returnValue = PoshiRunnerVariablesUtil.replaceCommandVars(
				returnValue);

			String returnVariable = returnElement.attributeValue("name"); 

			PoshiRunnerVariablesUtil.putIntoCommandMap(
				returnVariable, returnValue);
		}

		XMLLoggerHandler.updateStatus(returnElement, "pass");
	}

	public static void runSeleniumElement(Element executeElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		List<String> arguments = new ArrayList<>();
		List<Class<?>> parameterClasses = new ArrayList<>();

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
						selenium.equals("assertHTMLSourceTextNotPresent") ||
						selenium.equals("assertHTMLSourceTextPresent") ||
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

					if (selenium.equals("clickAt")) {
						argument = "";
					}
				}
				else if (i == 2) {
					if (selenium.equals("assertCssValue")) {
						argument =
							PoshiRunnerVariablesUtil.getValueFromCommandMap(
								"value1");
					}
					else {
						argument =
							PoshiRunnerVariablesUtil.getValueFromCommandMap(
								"locator2");
					}
				}
			}
			else {
				argument = PoshiRunnerVariablesUtil.replaceCommandVars(
					argument);
			}

			arguments.add(argument);

			parameterClasses.add(String.class);
		}

		CommandLoggerHandler.logSeleniumCommand(executeElement, arguments);

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		Class<?> clazz = liferaySelenium.getClass();

		try {
			Method method = clazz.getMethod(
				selenium,
				parameterClasses.toArray(new Class[parameterClasses.size()]));

			_returnObject = method.invoke(
				liferaySelenium,
				(Object[])arguments.toArray(new String[arguments.size()]));
		}
		catch (Exception e) {
			Throwable throwable = e.getCause();

			throw new Exception(throwable.getMessage(), e);
		}
	}

	public static void runTaskElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		XMLLoggerHandler.updateStatus(element, "pending");

		try {
			SummaryLoggerHandler.startSummary(element);

			parseElement(element);
		}
		catch (Exception e) {
			SummaryLoggerHandler.failSummary(element, e.getMessage());

			throw e;
		}

		SummaryLoggerHandler.passSummary(element);

		XMLLoggerHandler.updateStatus(element, "pass");
	}

	public static void runTestCaseCommandElement(Element commandElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(commandElement);

		PoshiRunnerVariablesUtil.pushCommandMap();

		parseElement(commandElement);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public static void runTestCaseExecuteElement(Element executeElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		XMLLoggerHandler.updateStatus(executeElement, "pending");

		String classCommandName = executeElement.attributeValue("test-case");

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		if (className.equals("super")) {
			className = PoshiRunnerGetterUtil.getExtendedTestCaseName();

			classCommandName = classCommandName.replaceFirst(
				"super", className);
		}

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			className);

		List<Element> rootVarElements = rootElement.elements("var");

		for (Element rootVarElement : rootVarElements) {
			runVarElement(rootVarElement, false, true);
		}

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName);

		runTestCaseCommandElement(commandElement);

		PoshiRunnerStackTraceUtil.popStackTrace();

		XMLLoggerHandler.updateStatus(executeElement, "pass");
	}

	public static void runVarElement(
			Element element, boolean commandVar, boolean updateLoggerStatus)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		if (updateLoggerStatus) {
			XMLLoggerHandler.updateStatus(element, "pending");
		}

		String varName = element.attributeValue("name");
		String varValue = element.attributeValue("value");

		if (varValue == null) {
			if (element.attributeValue("attribute") != null) {
				LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

				String attribute = element.attributeValue("attribute");
				String locator = element.attributeValue("locator");

				varValue = liferaySelenium.getAttribute(
					locator + "@" + attribute);
			}
			else if ((element.attributeValue("group") != null) &&
					 (element.attributeValue("input") != null) &&
					 (element.attributeValue("pattern") != null)) {

				varValue = RegexUtil.replace(
					PoshiRunnerVariablesUtil.replaceCommandVars(
						element.attributeValue("input")),
					element.attributeValue("pattern"),
					element.attributeValue("group"));
			}
			else if (element.attributeValue("locator") != null) {
				String locator = element.attributeValue("locator");

				if (locator.contains("#")) {
					locator = PoshiRunnerContext.getPathLocator(locator);
				}

				LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

				locator = PoshiRunnerVariablesUtil.replaceCommandVars(locator);

				try {
					if (locator.contains("/input")) {
						varValue = liferaySelenium.getElementValue(locator);
					}
					else {
						varValue = liferaySelenium.getElementText(locator);
					}
				}
				catch (Exception e) {
					XMLLoggerHandler.updateStatus(element, "fail");

					throw e;
				}
			}
			else if (element.attributeValue("method") != null) {
				String classCommandName =
					PoshiRunnerVariablesUtil.replaceCommandVars(
						element.attributeValue("method"));

				if (classCommandName.startsWith("TestPropsUtil")) {
					classCommandName = classCommandName.replace(
						"TestPropsUtil", "PropsUtil");
				}

				try {
					varValue = PoshiRunnerGetterUtil.getVarMethodValue(
						classCommandName);
				}
				catch (Exception e) {
					XMLLoggerHandler.updateStatus(element, "fail");

					Throwable throwable = e.getCause();

					throw new Exception(throwable.getMessage(), e);
				}
			}
			else if (element.attributeValue("property-value") != null) {
				varValue = PropsUtil.get(
					element.attributeValue("property-value"));

				if (varValue == null) {
					varValue = "";
				}
			}
			else {
				varValue = element.getText();
			}
		}
		else {
			Matcher matcher = _variableMethodPattern.matcher(varValue);

			if (matcher.find()) {
				String method = matcher.group(2);
				String variable = matcher.group(1);

				if (method.equals("length()")) {
					if (PoshiRunnerVariablesUtil.containsKeyInCommandMap(
							variable)) {

						variable =
							PoshiRunnerVariablesUtil.getValueFromCommandMap(
								variable);
					}
					else {
						throw new Exception("No such variable " + variable);
					}

					varValue = String.valueOf(variable.length());
				}
				else {
					throw new Exception("No such method " + method);
				}
			}
		}

		String replacedVarValue = PoshiRunnerVariablesUtil.replaceCommandVars(
			varValue);

		Matcher matcher = _variablePattern.matcher(replacedVarValue);

		if (matcher.matches() && replacedVarValue.equals(varValue)) {
			if (updateLoggerStatus) {
				XMLLoggerHandler.updateStatus(element, "pass");
			}

			return;
		}

		if (commandVar) {
			PoshiRunnerVariablesUtil.putIntoCommandMap(
				varName, replacedVarValue);
		}
		else {
			PoshiRunnerVariablesUtil.putIntoExecuteMap(
				varName, replacedVarValue);
		}

		if (updateLoggerStatus) {
			XMLLoggerHandler.updateStatus(element, "pass");
		}
	}

	public static void runWhileElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		XMLLoggerHandler.updateStatus(element, "pending");

		int maxIterations = 15;

		if (element.attributeValue("max-iterations") != null) {
			maxIterations = GetterUtil.getInteger(
				element.attributeValue("max-iterations"));
		}

		List<Element> whileChildElements = element.elements();

		Element conditionElement = whileChildElements.get(0);
		Element thenElement = element.element("then");

		boolean conditionRun = false;

		for (int i = 0; i < maxIterations; i++) {
			if (!evaluateConditionalElement(conditionElement)) {
				break;
			}

			conditionRun = true;

			PoshiRunnerStackTraceUtil.setCurrentElement(thenElement);

			XMLLoggerHandler.updateStatus(thenElement, "pending");

			parseElement(thenElement);

			XMLLoggerHandler.updateStatus(thenElement, "pass");
		}

		if (conditionRun) {
			XMLLoggerHandler.updateStatus(element, "pass");
		}
		else {
			XMLLoggerHandler.updateStatus(element, "conditional-fail");
		}
	}

	private static String _getWarningFromThrowable(Throwable throwable) {
		Class clazz = PoshiRunnerWarningException.class;

		String classCanonicalName = clazz.getCanonicalName();

		String throwableString = throwable.toString();

		if (throwableString.contains(classCanonicalName)) {
			return throwable.getMessage();
		}

		Throwable cause = throwable.getCause();

		if (cause != null) {
			return _getWarningFromThrowable(cause);
		}

		return null;
	}

	private static Element _functionExecuteElement;
	private static String _functionWarningMessage;
	private static final Pattern _locatorKeyPattern = Pattern.compile(
		"\\S#\\S");
	private static Object _returnObject;
	private static final Pattern _variableMethodPattern = Pattern.compile(
		"\\$\\{([\\S]*)\\?([\\S]*)\\}");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\$\\{([^}]*)\\}");

}