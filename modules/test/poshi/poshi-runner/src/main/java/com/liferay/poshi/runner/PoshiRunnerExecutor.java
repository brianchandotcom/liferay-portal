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

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.PoshiStackTraceUtil;
import com.liferay.poshi.core.PoshiVariablesUtil;
import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.logger.PoshiLogger;
import com.liferay.poshi.runner.logger.SummaryLogger;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.TableUtil;
import com.liferay.poshi.runner.var.type.BaseTable;
import com.liferay.poshi.runner.var.type.TableFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.openqa.selenium.StaleElementReferenceException;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PoshiRunnerExecutor {

	public PoshiRunnerExecutor(PoshiLogger poshiLogger) {
		_poshiLogger = poshiLogger;
	}

	public boolean evaluateConditionalElement(Element element)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(element);

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
			String string = PoshiVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("string"));
			String substring = PoshiVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("substring"));

			if (string.contains(substring)) {
				conditionalValue = true;
			}
		}
		else if (elementName.equals("equals")) {
			String arg1 = PoshiVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("arg1"));

			String arg2 = PoshiVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("arg2"));

			if (arg1.equals(arg2)) {
				conditionalValue = true;
			}
		}
		else if (elementName.equals("isset")) {
			if (PoshiVariablesUtil.containsKeyInCommandMap(
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
			_poshiLogger.updateStatus(element, "pass");
		}
		else {
			_poshiLogger.updateStatus(element, "conditional-fail");
		}

		return conditionalValue;
	}

	public void parseElement(Element element) throws Exception {
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
				else if (childElement.attributeValue("selenium") != null) {
					runSeleniumElement(childElement);
				}
				else if (childElement.attributeValue("test-case") != null) {
					runTestCaseExecuteElement(childElement);
				}
				else if (childElement.attributeValue("method") != null) {
					runMethodExecuteElement(childElement);
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
			else if (childElementName.equals("take-screenshot")) {
				runTakeScreenshotElement(childElement);
			}
			else if (childElementName.equals("task")) {
				runTaskElement(childElement);
			}
			else if (childElementName.equals("var")) {
				runCommandVarElement(childElement, true);
			}
			else if (childElementName.equals("while")) {
				runWhileElement(childElement);
			}
		}
	}

	public void runCommandVarElement(
			Element element, boolean updateLoggerStatus)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(element);

		Object varValue = null;

		try {
			varValue = _getVarValue(element);
		}
		catch (Exception exception) {
			if (updateLoggerStatus) {
				_poshiLogger.updateStatus(element, "fail");
			}

			throw exception;
		}

		if (varValue instanceof String) {
			varValue = PoshiVariablesUtil.replaceCommandVars((String)varValue);

			if (varValue instanceof String) {
				Matcher matcher = _variablePattern.matcher((String)varValue);

				if (matcher.matches()) {
					if (updateLoggerStatus) {
						_poshiLogger.updateStatus(element, "pass");
					}

					return;
				}
			}
		}

		String varName = element.attributeValue("name");

		PoshiVariablesUtil.putIntoCommandMap(varName, varValue);

		String currentFilePath = PoshiStackTraceUtil.getCurrentFilePath();

		if (currentFilePath.contains(".macro") ||
			currentFilePath.contains(".testcase")) {

			String staticValue = element.attributeValue("static");

			if ((staticValue != null) && staticValue.equals("true")) {
				PoshiVariablesUtil.putIntoStaticMap(varName, varValue);
			}
		}

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runEchoElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		_poshiLogger.logMessage(element);

		String message = element.attributeValue("message");

		if (message == null) {
			message = element.getText();
		}

		System.out.println(PoshiVariablesUtil.replaceCommandVars(message));
	}

	public void runExecuteVarElement(
			Element element, boolean updateLoggerStatus)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(element);

		String varName = element.attributeValue("name");

		if (PoshiVariablesUtil.containsKeyInStaticMap(varName)) {
			if (updateLoggerStatus) {
				_poshiLogger.updateStatus(element, "fail");
			}

			throw new Exception(
				"Unable to set var '" + varName +
					"' as parameter of function. It is already set in the " +
						"static context.");
		}

		Object varValue = null;

		try {
			varValue = _getVarValue(element);
		}
		catch (Exception exception) {
			if (updateLoggerStatus) {
				_poshiLogger.updateStatus(element, "fail");
			}

			throw exception;
		}

		if (varValue instanceof String) {
			varValue = PoshiVariablesUtil.replaceExecuteVars((String)varValue);

			varValue = PoshiVariablesUtil.replaceCommandVars((String)varValue);

			if (varValue instanceof String) {
				Matcher matcher = _variablePattern.matcher((String)varValue);

				if (matcher.matches() && varValue.equals(varValue)) {
					if (updateLoggerStatus) {
						_poshiLogger.updateStatus(element, "pass");
					}

					return;
				}
			}
		}

		PoshiVariablesUtil.putIntoExecuteMap(varName, varValue);

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runFailElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		_poshiLogger.logMessage(element);

		String message = element.attributeValue("message");

		_poshiLogger.updateStatus(element, "fail");

		if (Validator.isNotNull(message)) {
			throw new Exception(
				PoshiVariablesUtil.getReplacedCommandVarsString(message));
		}

		throw new Exception();
	}

	public void runForElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		String paramName = PoshiVariablesUtil.getReplacedCommandVarsString(
			element.attributeValue("param"));

		if (element.attributeValue("list") != null) {
			String list = PoshiVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("list"));

			String[] paramValues = list.split(",");

			for (String paramValue : paramValues) {
				PoshiVariablesUtil.putIntoCommandMap(paramName, paramValue);

				parseElement(element);
			}
		}
		else if (element.attributeValue("table") != null) {
			BaseTable<?> table =
				(BaseTable<?>)PoshiVariablesUtil.replaceCommandVars(
					element.attributeValue("table"));

			Iterator<?> iterator = table.iterator();

			while (iterator.hasNext()) {
				PoshiVariablesUtil.putIntoCommandMap(
					paramName, iterator.next());

				parseElement(element);
			}
		}

		_poshiLogger.updateStatus(element, "pass");
	}

	public void runFunctionCommandElement(Element commandElement)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(commandElement);

		PoshiVariablesUtil.pushCommandMap();

		try {
			parseElement(commandElement);
		}
		catch (Exception exception) {
			throw exception;
		}
		finally {
			PoshiVariablesUtil.popCommandMap();
		}
	}

	public void runFunctionExecuteElement(Element executeElement)
		throws Exception {

		if (_functionExecuteElement == null) {
			_functionExecuteElement = executeElement;
		}

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runExecuteVarElement(executeVarElement, false);
		}

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"function");

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Exception exception = null;

		for (int i = 1; i <= PoshiContext.getFunctionMaxArgumentCount(); i++) {
			String locator = executeElement.attributeValue("locator" + i);

			if (locator == null) {
				locator = PoshiVariablesUtil.getStringFromCommandMap(
					"locator" + i);
			}

			if (locator != null) {
				Matcher matcher = _locatorKeyPattern.matcher(locator);

				if (matcher.find() && !locator.contains("/")) {
					String pathClassName =
						PoshiVariablesUtil.getReplacedCommandVarsString(
							PoshiGetterUtil.
								getClassNameFromNamespacedClassCommandName(
									locator));

					String locatorKey =
						PoshiVariablesUtil.getReplacedCommandVarsString(
							PoshiGetterUtil.
								getCommandNameFromNamespacedClassCommandName(
									locator));

					PoshiVariablesUtil.putIntoExecuteMap(
						"locator-key" + i, locatorKey);

					locator = PoshiContext.getPathLocator(
						pathClassName + "#" + locatorKey,
						PoshiGetterUtil.
							getNamespaceFromNamespacedClassCommandName(
								locator));

					if (locator == null) {
						exception = new Exception(
							"No such locator key " + pathClassName + "#" +
								locatorKey);
					}

					locator = (String)PoshiVariablesUtil.replaceExecuteVars(
						locator);
				}

				PoshiVariablesUtil.putIntoExecuteMap("locator" + i, locator);
			}

			String value = executeElement.attributeValue("value" + i);

			if (value == null) {
				value = PoshiVariablesUtil.getStringFromCommandMap("value" + i);
			}

			if (value != null) {
				PoshiVariablesUtil.putIntoExecuteMap("value" + i, value);
			}
		}

		if (_functionExecuteElement == executeElement) {
			SummaryLogger.startSummary(_functionExecuteElement);
		}

		_poshiLogger.startCommand(executeElement);

		PoshiStackTraceUtil.pushStackTrace(executeElement);

		Element commandElement = PoshiContext.getFunctionCommandElement(
			classCommandName,
			PoshiStackTraceUtil.getCurrentNamespace(
				namespacedClassCommandName));

		try {
			if (exception != null) {
				throw exception;
			}

			runFunctionCommandElement(commandElement);
		}
		catch (Throwable throwable) {
			String warningMessage = _getWarningFromThrowable(throwable);

			if (warningMessage != null) {
				_functionWarningMessage = warningMessage;
			}
			else {
				PoshiStackTraceUtil.popStackTrace();

				if (_functionExecuteElement == executeElement) {
					PoshiStackTraceUtil.setCurrentElement(executeElement);

					SummaryLogger.failSummary(
						_functionExecuteElement, throwable.getMessage(),
						_poshiLogger.getDetailsLinkId());

					_poshiLogger.failCommand(_functionExecuteElement);

					_functionExecuteElement = null;
					_functionWarningMessage = null;
				}

				throw throwable;
			}
		}

		PoshiStackTraceUtil.popStackTrace();

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		if (_functionExecuteElement == executeElement) {
			if (_functionWarningMessage != null) {
				SummaryLogger.warnSummary(
					_functionExecuteElement, _functionWarningMessage);

				_poshiLogger.warnCommand(_functionExecuteElement);
			}
			else {
				SummaryLogger.passSummary(executeElement);

				_poshiLogger.passCommand(executeElement);
			}

			_functionExecuteElement = null;
			_functionWarningMessage = null;
		}
	}

	public void runIfElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		List<Element> ifChildElements = element.elements();

		Element ifConditionElement = ifChildElements.get(0);

		boolean condition = evaluateConditionalElement(ifConditionElement);

		if (condition) {
			Element ifThenElement = element.element("then");

			PoshiStackTraceUtil.setCurrentElement(ifThenElement);

			parseElement(ifThenElement);

			_poshiLogger.updateStatus(ifThenElement, "pass");

			_poshiLogger.updateStatus(element, "pass");

			return;
		}

		_poshiLogger.updateStatus(element, "conditional-fail");

		if (element.element("elseif") != null) {
			List<Element> elseIfElements = element.elements("elseif");

			for (Element elseIfElement : elseIfElements) {
				PoshiStackTraceUtil.setCurrentElement(elseIfElement);

				List<Element> elseIfChildElements = elseIfElement.elements();

				Element elseIfConditionElement = elseIfChildElements.get(0);

				condition = evaluateConditionalElement(elseIfConditionElement);

				if (condition) {
					Element elseIfThenElement = elseIfElement.element("then");

					PoshiStackTraceUtil.setCurrentElement(elseIfThenElement);

					parseElement(elseIfThenElement);

					_poshiLogger.updateStatus(elseIfThenElement, "pass");

					_poshiLogger.updateStatus(elseIfElement, "pass");

					return;
				}

				_poshiLogger.updateStatus(elseIfElement, "conditional-fail");
			}
		}

		if (element.element("else") != null) {
			Element elseElement = element.element("else");

			PoshiStackTraceUtil.setCurrentElement(elseElement);

			parseElement(elseElement);

			_poshiLogger.updateStatus(elseElement, "pass");
		}
	}

	public void runMacroCommandElement(
			Element commandElement, String namespacedClassCommandName)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(commandElement);

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				classCommandName);

		String namespace = PoshiStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		List<Element> rootVarElements = PoshiContext.getRootVarElements(
			"macro", className, namespace);

		for (Element rootVarElement : rootVarElements) {
			runRootVarElement(rootVarElement, true);
		}

		PoshiVariablesUtil.pushCommandMap();

		parseElement(commandElement);

		PoshiVariablesUtil.popCommandMap();
	}

	public void runMacroExecuteElement(Element executeElement, String macroType)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			macroType);

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runExecuteVarElement(executeVarElement, false);
		}

		PoshiStackTraceUtil.pushStackTrace(executeElement);

		String namespace = PoshiStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		SummaryLogger.startSummary(executeElement);

		Element commandElement = PoshiContext.getMacroCommandElement(
			classCommandName, namespace);

		try {
			runMacroCommandElement(commandElement, namespacedClassCommandName);

			Element returnElement = executeElement.element("return");

			if (returnElement != null) {
				if (_macroReturnValue == null) {
					throw new RuntimeException(
						"No value was returned from macro command '" +
							namespacedClassCommandName + "'");
				}

				String returnName = returnElement.attributeValue("name");

				if (PoshiVariablesUtil.containsKeyInStaticMap(returnName)) {
					PoshiVariablesUtil.putIntoStaticMap(
						returnName, _macroReturnValue);
				}

				PoshiVariablesUtil.putIntoCommandMap(
					returnName, _macroReturnValue);

				_macroReturnValue = null;
			}
		}
		catch (Exception exception) {
			SummaryLogger.failSummary(
				executeElement, exception.getMessage(),
				_poshiLogger.getDetailsLinkId());

			throw exception;
		}

		SummaryLogger.passSummary(executeElement);

		PoshiStackTraceUtil.popStackTrace();

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runMethodExecuteElement(Element executeElement)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		List<String> args = new ArrayList<>();

		List<Element> argElements = executeElement.elements("arg");

		for (Element argElement : argElements) {
			args.add(argElement.attributeValue("value"));
		}

		String className = executeElement.attributeValue("class");
		String methodName = executeElement.attributeValue("method");

		try {
			Object returnValue = PoshiGetterUtil.getMethodReturnValue(
				args, className, methodName, null);

			Element returnElement = executeElement.element("return");

			if (returnElement != null) {
				PoshiVariablesUtil.putIntoCommandMap(
					returnElement.attributeValue("name"), returnValue);
			}

			_poshiLogger.logExternalMethodCommand(
				executeElement, args, returnValue);
		}
		catch (Throwable throwable) {
			_poshiLogger.updateStatus(executeElement, "fail");

			throw throwable;
		}

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runReturnElement(Element returnElement) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(returnElement);

		if (returnElement.attributeValue("value") != null) {
			String returnValue = returnElement.attributeValue("value");

			_macroReturnValue = PoshiVariablesUtil.replaceCommandVars(
				returnValue);
		}

		_poshiLogger.updateStatus(returnElement, "pass");
	}

	public void runRootVarElement(Element element, boolean updateLoggerStatus)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(element);

		Object varValue = null;

		try {
			varValue = _getVarValue(element);
		}
		catch (Exception exception) {
			if (updateLoggerStatus) {
				_poshiLogger.updateStatus(element, "fail");
			}

			throw exception;
		}

		if (varValue instanceof String) {
			varValue = PoshiVariablesUtil.replaceExecuteVars((String)varValue);

			varValue = PoshiVariablesUtil.replaceStaticVars((String)varValue);

			if (varValue instanceof String) {
				Matcher matcher = _variablePattern.matcher((String)varValue);

				if (matcher.matches() && varValue.equals(varValue)) {
					if (updateLoggerStatus) {
						_poshiLogger.updateStatus(element, "pass");
					}

					return;
				}
			}
		}

		String varName = element.attributeValue("name");

		if (!PoshiVariablesUtil.containsKeyInExecuteMap(varName)) {
			PoshiVariablesUtil.putIntoExecuteMap(varName, varValue);
		}

		String currentFilePath = PoshiStackTraceUtil.getCurrentFilePath();

		if (currentFilePath.contains(".testcase")) {
			String staticValue = element.attributeValue("static");

			if ((staticValue != null) && staticValue.equals("true") &&
				!PoshiVariablesUtil.containsKeyInStaticMap(varName)) {

				PoshiVariablesUtil.putIntoStaticMap(varName, varValue);
			}
		}

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runSeleniumElement(Element executeElement) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(executeElement);

		List<String> arguments = new ArrayList<>();
		List<Class<?>> parameterClasses = new ArrayList<>();

		String selenium = executeElement.attributeValue("selenium");

		int parameterCount = PoshiContext.getSeleniumParameterCount(selenium);

		for (int i = 0; i < parameterCount; i++) {
			String argument = executeElement.attributeValue(
				"argument" + (i + 1));

			if (argument == null) {
				if (i == 0) {
					if (_isJavaScriptMethod(selenium) ||
						selenium.equals("assertAlertText") ||
						selenium.equals("assertConfirmation") ||
						selenium.equals("assertConsoleTextNotPresent") ||
						selenium.equals("assertConsoleTextPresent") ||
						selenium.equals("assertHTMLSourceTextNotPresent") ||
						selenium.equals("assertHTMLSourceTextPresent") ||
						selenium.equals("assertLocation") ||
						selenium.equals("assertNotLocation") ||
						selenium.equals("assertPartialConfirmation") ||
						selenium.equals("assertPartialLocation") ||
						selenium.equals("assertTextNotPresent") ||
						selenium.equals("assertTextPresent") ||
						selenium.equals("isConsoleTextNotPresent") ||
						selenium.equals("isConsoleTextPresent") ||
						selenium.equals("scrollBy") ||
						selenium.equals("typeAlert") ||
						selenium.equals("waitForConfirmation") ||
						selenium.equals("waitForConsoleTextNotPresent") ||
						selenium.equals("waitForConsoleTextPresent") ||
						selenium.equals("waitForTextNotPresent") ||
						selenium.equals("waitForTextPresent")) {

						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"value1");
					}
					else {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"locator1");
					}
				}
				else if (i == 1) {
					argument = PoshiVariablesUtil.getStringFromCommandMap(
						"value1");

					if (selenium.equals("clickAt")) {
						argument = "";
					}
					else if (_isJavaScriptMethod(selenium)) {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"value2");
					}
				}
				else if (i == 2) {
					if (selenium.equals("assertCssValue")) {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"value1");
					}
					else if (selenium.equals("ocularAssertElementImage")) {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"value2");
					}
					else if (_isJavaScriptMethod(selenium)) {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"value3");
					}
					else {
						argument = PoshiVariablesUtil.getStringFromCommandMap(
							"locator2");
					}
				}
			}
			else {
				argument = PoshiVariablesUtil.getReplacedCommandVarsString(
					argument);
			}

			arguments.add(argument);

			parameterClasses.add(String.class);
		}

		_poshiLogger.logSeleniumCommand(executeElement, arguments);

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		Class<?> clazz = liferaySelenium.getClass();

		Method method = clazz.getMethod(
			selenium, parameterClasses.toArray(new Class<?>[0]));

		_returnObject = invokeLiferaySeleniumMethod(
			method, arguments.toArray(new String[0]));
	}

	public void runTakeScreenshotElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		_poshiLogger.takeScreenshotCommand(element);
	}

	public void runTaskElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

		try {
			SummaryLogger.startSummary(element);

			parseElement(element);
		}
		catch (Exception exception) {
			SummaryLogger.failSummary(
				element, exception.getMessage(),
				_poshiLogger.getDetailsLinkId());

			throw exception;
		}

		SummaryLogger.passSummary(element);

		_poshiLogger.updateStatus(element, "pass");
	}

	public void runTestCaseCommandElement(
			Element element, String namespacedClassCommandName)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(element);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		List<Element> rootVarElements = PoshiContext.getRootVarElements(
			"test-case", className, namespace);

		for (Element rootVarElement : rootVarElements) {
			runRootVarElement(rootVarElement, false);
		}

		PoshiVariablesUtil.pushCommandMap();

		parseElement(element);

		PoshiVariablesUtil.popCommandMap();
	}

	public void runTestCaseExecuteElement(Element executeElement)
		throws Exception {

		PoshiStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"test-case");

		PoshiStackTraceUtil.pushStackTrace(executeElement);

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element commandElement = PoshiContext.getTestCaseCommandElement(
			namespacedClassCommandName, namespace);

		runTestCaseCommandElement(commandElement, namespacedClassCommandName);

		PoshiStackTraceUtil.popStackTrace();

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runWhileElement(Element element) throws Exception {
		PoshiStackTraceUtil.setCurrentElement(element);

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

			PoshiStackTraceUtil.setCurrentElement(thenElement);

			parseElement(thenElement);

			_poshiLogger.updateStatus(thenElement, "pass");
		}

		if (conditionRun) {
			_poshiLogger.updateStatus(element, "pass");
		}
		else {
			_poshiLogger.updateStatus(element, "conditional-fail");
		}
	}

	protected static Object getVarMethodValue(
			String expression, String defaultNamespace)
		throws Exception {

		List<String> args = new ArrayList<>();

		int x = expression.indexOf("(");
		int y = expression.lastIndexOf(")");

		if ((x + 1) < y) {
			String parameterString = expression.substring(x + 1, y);

			Matcher parameterMatcher = _parameterPattern.matcher(
				parameterString);

			while (parameterMatcher.find()) {
				String parameterValue = parameterMatcher.group();

				if (parameterValue.startsWith("'") &&
					parameterValue.endsWith("'")) {

					parameterValue = parameterValue.substring(
						1, parameterValue.length() - 1);
				}

				Matcher matcher = _locatorKeyPattern.matcher(parameterValue);

				if (matcher.matches()) {
					String namespace = matcher.group("namespace");

					if (namespace == null) {
						parameterValue = PoshiContext.getPathLocator(
							parameterValue, defaultNamespace);
					}
					else {
						parameterValue = PoshiContext.getPathLocator(
							parameterValue, namespace);
					}
				}

				if (parameterValue.contains("\'")) {
					parameterValue = parameterValue.replaceAll("\\\\'", "'");
				}

				args.add(parameterValue);
			}
		}

		y = expression.indexOf("#");

		String className = expression.substring(0, y);
		String methodName = expression.substring(y + 1, x);

		Object object = null;

		if (className.equals("selenium")) {
			object = SeleniumUtil.getSelenium();
		}

		return PoshiGetterUtil.getMethodReturnValue(
			args, className, methodName, object);
	}

	protected Object callWithTimeout(
			Callable<?> callable, String description, long timeoutSeconds)
		throws Exception {

		ExecutorService executorService = Executors.newSingleThreadExecutor();

		Future<?> future = executorService.submit(callable);

		executorService.shutdown();

		try {
			return future.get(timeoutSeconds, TimeUnit.SECONDS);
		}
		catch (ExecutionException executionException) {
			if (PropsValues.DEBUG_STACKTRACE) {
				throw executionException;
			}

			Throwable throwable = executionException.getCause();

			if (throwable instanceof Error) {
				throw (Error)throwable;
			}

			throw (Exception)throwable;
		}
		catch (InterruptedException | TimeoutException exception) {
			future.cancel(true);

			if (exception instanceof TimeoutException) {
				System.out.println(
					"Timed out after " + timeoutSeconds +
						" seconds while executing " + description);
			}

			throw new Exception(
				"An error occurred while executing " + description, exception);
		}
	}

	protected Object invokeLiferaySeleniumMethod(Method method, Object... args)
		throws Exception {

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		String methodName = method.getName();

		Callable<Object> task = new Callable<Object>() {

			public Object call() throws Exception {
				try {
					return method.invoke(liferaySelenium, args);
				}
				catch (InvocationTargetException invocationTargetException) {
					Throwable throwable = invocationTargetException.getCause();

					if (throwable instanceof StaleElementReferenceException) {
						StringBuilder sb = new StringBuilder();

						sb.append("\nElement turned stale while running ");
						sb.append(methodName);
						sb.append(". Retrying in ");
						sb.append(PropsValues.TEST_RETRY_COMMAND_WAIT_TIME);
						sb.append("seconds.");

						System.out.println(sb.toString());

						try {
							return method.invoke(liferaySelenium, args);
						}
						catch (Exception exception) {
							throwable = exception.getCause();

							if (PropsValues.DEBUG_STACKTRACE) {
								throw new Exception(
									throwable.getMessage(), exception);
							}

							if (throwable instanceof Error) {
								throw (Error)throwable;
							}

							throw (Exception)throwable;
						}
					}
					else {
						if (PropsValues.DEBUG_STACKTRACE) {
							throw new Exception(
								throwable.getMessage(),
								invocationTargetException);
						}

						if (throwable instanceof Error) {
							throw (Error)throwable;
						}

						throw (Exception)throwable;
					}
				}
			}

		};

		Long timeout = Long.valueOf(PropsValues.TIMEOUT_EXPLICIT_WAIT) + 60L;

		if (methodName.equals("antCommand") | methodName.equals("pause")) {
			timeout = 3600L;
		}

		return callWithTimeout(task, methodName, timeout);
	}

	private Object _getVarValue(Element element) throws Exception {
		Object varValue = element.attributeValue("value");

		if (varValue == null) {
			if (element.attributeValue("method") != null) {
				String methodName = element.attributeValue("method");

				try {
					varValue = getVarMethodValue(
						methodName, PoshiStackTraceUtil.getCurrentNamespace());
				}
				catch (Exception exception) {
					Throwable throwable = exception.getCause();

					if ((throwable != null) &&
						(throwable.getMessage() != null)) {

						throw new Exception(throwable.getMessage(), exception);
					}

					throw exception;
				}
			}
			else if (element.attributeValue("type") != null) {
				String varType = element.attributeValue("type");

				if (varType.equals("Table")) {
					varValue = TableUtil.getRawDataListFromString(
						element.getText());
				}
				else if ((varType.equals("HashesTable") ||
						  varType.equals("RawTable") ||
						  varType.equals("RowsHashTable")) &&
						 (element.attributeValue("from") != null)) {

					Object varFrom = PoshiVariablesUtil.replaceCommandVars(
						element.attributeValue("from"));

					if (!(varFrom instanceof List)) {
						StringBuilder sb = new StringBuilder();

						sb.append("Variable '");
						sb.append((String)varFrom);
						sb.append("' is not an instance of type 'List'");

						throw new IllegalArgumentException(sb.toString());
					}

					varValue = TableFactory.newTable(
						(List<List<String>>)varFrom, varType);
				}
			}
			else if (element.attributeValue("from") != null) {
				Object varFrom = PoshiVariablesUtil.replaceCommandVars(
					element.attributeValue("from"));

				if (element.attributeValue("hash") != null) {
					LinkedHashMap<?, ?> varFromMap =
						(LinkedHashMap<?, ?>)varFrom;

					varValue = varFromMap.get(element.attributeValue("hash"));
				}
				else if (element.attributeValue("index") != null) {
					List<?> varFromList = (List<?>)varFrom;

					varValue = varFromList.get(
						GetterUtil.getInteger(element.attributeValue("index")));
				}
			}
			else {
				varValue = element.getText();
			}
		}

		return varValue;
	}

	private String _getWarningFromThrowable(Throwable throwable) {
		Class<?> clazz = PoshiRunnerWarningException.class;

		String classCanonicalName = clazz.getCanonicalName();

		String throwableString = throwable.toString();

		if (throwableString.contains(classCanonicalName)) {
			return throwable.getMessage();
		}

		Throwable causeThrowable = throwable.getCause();

		if (causeThrowable != null) {
			return _getWarningFromThrowable(causeThrowable);
		}

		return null;
	}

	private boolean _isJavaScriptMethod(String methodName) {
		if (methodName.equals("assertJavaScript") ||
			methodName.equals("executeJavaScript") ||
			methodName.equals("getJavaScriptResult") ||
			methodName.equals("waitForJavaScript") ||
			methodName.equals("waitForJavaScriptNoError") ||
			methodName.equals("verifyJavaScript")) {

			return true;
		}

		return false;
	}

	private static final Pattern _locatorKeyPattern = Pattern.compile(
		"((?<namespace>[\\w]+)\\.)?(\\w+)#(\\$\\{\\w+\\}|[A-Z0-9_]+)");
	private static final Pattern _parameterPattern = Pattern.compile(
		"('([^'\\\\]|\\\\.)*'|[^',\\s]+)");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\$\\{([^}]*)\\}");

	private Element _functionExecuteElement;
	private String _functionWarningMessage;
	private Object _macroReturnValue;
	private final PoshiLogger _poshiLogger;
	private Object _returnObject;

}