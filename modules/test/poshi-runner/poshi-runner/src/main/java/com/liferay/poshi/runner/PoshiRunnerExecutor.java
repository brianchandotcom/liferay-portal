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
import com.liferay.poshi.runner.logger.PoshiLogger;
import com.liferay.poshi.runner.logger.SummaryLogger;
import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.LiferaySeleniumHelper;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.TableUtil;
import com.liferay.poshi.runner.util.Validator;
import com.liferay.poshi.runner.var.type.BaseTable;
import com.liferay.poshi.runner.var.type.TableFactory;

import groovy.lang.Binding;

import groovy.util.GroovyScriptEngine;

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

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

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
			String string =
				PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
					element.attributeValue("string"));
			String substring =
				PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
					element.attributeValue("substring"));

			if (string.contains(substring)) {
				conditionalValue = true;
			}
		}
		else if (elementName.equals("equals")) {
			String arg1 = PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("arg1"));

			String arg2 = PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
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
				else if (childElement.attributeValue("groovy-script") != null) {
					runGroovyScriptElement(childElement);
				}
				else if (childElement.attributeValue("macro") != null) {
					runMacroExecuteElement(childElement, "macro");
				}
				else if ((childElement.attributeValue("macro-desktop") !=
							null) &&
						 !PropsValues.MOBILE_BROWSER) {

					runMacroExecuteElement(childElement, "macro-desktop");
				}
				else if ((childElement.attributeValue("macro-mobile") !=
							null) &&
						 PropsValues.MOBILE_BROWSER) {

					runMacroExecuteElement(childElement, "macro-mobile");
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

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

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
			varValue = PoshiRunnerVariablesUtil.replaceCommandVars(
				(String)varValue);

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

		PoshiRunnerVariablesUtil.putIntoCommandMap(varName, varValue);

		String currentFilePath = PoshiRunnerStackTraceUtil.getCurrentFilePath();

		if (currentFilePath.contains(".macro") ||
			currentFilePath.contains(".testcase")) {

			String staticValue = element.attributeValue("static");

			if ((staticValue != null) && staticValue.equals("true")) {
				PoshiRunnerVariablesUtil.putIntoStaticMap(varName, varValue);
			}
		}

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runEchoElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		_poshiLogger.logMessage(element);

		String message = element.attributeValue("message");

		if (message == null) {
			message = element.getText();
		}

		System.out.println(
			PoshiRunnerVariablesUtil.replaceCommandVars(message));
	}

	public void runExecuteVarElement(
			Element element, boolean updateLoggerStatus)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		String varName = element.attributeValue("name");

		if (PoshiRunnerVariablesUtil.containsKeyInStaticMap(varName)) {
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
			varValue = PoshiRunnerVariablesUtil.replaceExecuteVars(
				(String)varValue);

			varValue = PoshiRunnerVariablesUtil.replaceCommandVars(
				(String)varValue);

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

		PoshiRunnerVariablesUtil.putIntoExecuteMap(varName, varValue);

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runFailElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		_poshiLogger.logMessage(element);

		String message = element.attributeValue("message");

		_poshiLogger.updateStatus(element, "fail");

		if (Validator.isNotNull(message)) {
			throw new Exception(
				PoshiRunnerVariablesUtil.getReplacedCommandVarsString(message));
		}

		throw new Exception();
	}

	public void runForElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		String paramName =
			PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("param"));

		if (element.attributeValue("list") != null) {
			String list = PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
				element.attributeValue("list"));

			String[] paramValues = list.split(",");

			for (String paramValue : paramValues) {
				PoshiRunnerVariablesUtil.putIntoCommandMap(
					paramName, paramValue);

				parseElement(element);
			}
		}
		else if (element.attributeValue("table") != null) {
			BaseTable<?> table =
				(BaseTable<?>)PoshiRunnerVariablesUtil.replaceCommandVars(
					element.attributeValue("table"));

			Iterator<?> iter = table.iterator();

			while (iter.hasNext()) {
				PoshiRunnerVariablesUtil.putIntoCommandMap(
					paramName, iter.next());

				parseElement(element);
			}
		}

		_poshiLogger.updateStatus(element, "pass");
	}

	public void runFunctionCommandElement(Element commandElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(commandElement);

		PoshiRunnerVariablesUtil.pushCommandMap();

		try {
			parseElement(commandElement);
		}
		catch (Exception exception) {
			throw exception;
		}
		finally {
			PoshiRunnerVariablesUtil.popCommandMap();
		}
	}

	public void runFunctionExecuteElement(Element executeElement)
		throws Exception {

		if (_functionExecuteElement == null) {
			_functionExecuteElement = executeElement;
		}

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runExecuteVarElement(executeVarElement, false);
		}

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"function");

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				classCommandName);

		Exception exception = null;

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className,
			PoshiRunnerStackTraceUtil.getCurrentNamespace(
				namespacedClassCommandName));

		for (int i = 1; i <= locatorCount; i++) {
			String locator = executeElement.attributeValue("locator" + i);

			if (locator == null) {
				locator = PoshiRunnerVariablesUtil.getStringFromCommandMap(
					"locator" + i);
			}

			if (locator != null) {
				Matcher matcher = _locatorKeyPattern.matcher(locator);

				if (matcher.find() && !locator.contains("/")) {
					String pathClassName =
						PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
							PoshiRunnerGetterUtil.
								getClassNameFromNamespacedClassCommandName(
									locator));

					String locatorKey =
						PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
							PoshiRunnerGetterUtil.
								getCommandNameFromNamespacedClassCommandName(
									locator));

					PoshiRunnerVariablesUtil.putIntoExecuteMap(
						"locator-key" + i, locatorKey);

					locator = PoshiRunnerContext.getPathLocator(
						pathClassName + "#" + locatorKey,
						PoshiRunnerGetterUtil.
							getNamespaceFromNamespacedClassCommandName(
								locator));

					if (locator == null) {
						exception = new Exception(
							"No such locator key " + pathClassName + "#" +
								locatorKey);
					}

					locator =
						(String)PoshiRunnerVariablesUtil.replaceExecuteVars(
							locator);
				}

				PoshiRunnerVariablesUtil.putIntoExecuteMap(
					"locator" + i, locator);
			}

			String value = executeElement.attributeValue("value" + i);

			if (value == null) {
				value = PoshiRunnerVariablesUtil.getStringFromCommandMap(
					"value" + i);
			}

			if (value != null) {
				PoshiRunnerVariablesUtil.putIntoExecuteMap("value" + i, value);
			}
		}

		if (_functionExecuteElement == executeElement) {
			SummaryLogger.startSummary(_functionExecuteElement);
		}

		_poshiLogger.startCommand(executeElement);

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		Element commandElement = PoshiRunnerContext.getFunctionCommandElement(
			classCommandName,
			PoshiRunnerStackTraceUtil.getCurrentNamespace(
				namespacedClassCommandName));

		try {
			if (exception != null) {
				throw exception;
			}

			runFunctionCommandElement(commandElement);
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

					SummaryLogger.failSummary(
						_functionExecuteElement, t.getMessage(),
						_poshiLogger.getDetailsLinkId());

					_poshiLogger.failCommand(_functionExecuteElement);

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

	public void runGroovyScriptElement(Element executeElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		List<Element> executeArgElements = executeElement.elements("arg");

		Binding binding = new Binding();

		if (!executeArgElements.isEmpty()) {
			List<String> arguments = new ArrayList<>();

			for (Element executeArgElement : executeArgElements) {
				arguments.add(
					PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
						executeArgElement.attributeValue("value")));
			}

			binding.setVariable("args", arguments.toArray(new String[0]));
		}

		String status = "fail";

		try {
			String fileName =
				PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
					executeElement.attributeValue("groovy-script"));

			String fileSeparator = FileUtil.getSeparator();

			GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine(
				LiferaySeleniumHelper.getSourceDirFilePath(
					fileSeparator + PropsValues.TEST_DEPENDENCIES_DIR_NAME +
						fileSeparator + fileName));

			Object result = groovyScriptEngine.run(fileName, binding);

			String returnVariable = executeElement.attributeValue("return");

			if (returnVariable != null) {
				PoshiRunnerVariablesUtil.putIntoCommandMap(
					returnVariable, result.toString());
			}

			status = "pass";
		}
		finally {
			_poshiLogger.updateStatus(executeElement, status);
		}
	}

	public void runIfElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		List<Element> ifChildElements = element.elements();

		Element ifConditionElement = ifChildElements.get(0);

		boolean condition = evaluateConditionalElement(ifConditionElement);

		if (condition) {
			Element ifThenElement = element.element("then");

			PoshiRunnerStackTraceUtil.setCurrentElement(ifThenElement);

			parseElement(ifThenElement);

			_poshiLogger.updateStatus(ifThenElement, "pass");

			_poshiLogger.updateStatus(element, "pass");

			return;
		}

		_poshiLogger.updateStatus(element, "conditional-fail");

		if (element.element("elseif") != null) {
			List<Element> elseIfElements = element.elements("elseif");

			for (Element elseIfElement : elseIfElements) {
				PoshiRunnerStackTraceUtil.setCurrentElement(elseIfElement);

				List<Element> elseIfChildElements = elseIfElement.elements();

				Element elseIfConditionElement = elseIfChildElements.get(0);

				condition = evaluateConditionalElement(elseIfConditionElement);

				if (condition) {
					Element elseIfThenElement = elseIfElement.element("then");

					PoshiRunnerStackTraceUtil.setCurrentElement(
						elseIfThenElement);

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

			PoshiRunnerStackTraceUtil.setCurrentElement(elseElement);

			parseElement(elseElement);

			_poshiLogger.updateStatus(elseElement, "pass");
		}
	}

	public void runMacroCommandElement(
			Element commandElement, String namespacedClassCommandName)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(commandElement);

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				classCommandName);

		String namespace = PoshiRunnerStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		List<Element> rootVarElements = PoshiRunnerContext.getRootVarElements(
			"macro", className, namespace);

		for (Element rootVarElement : rootVarElements) {
			runRootVarElement(rootVarElement, true);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		parseElement(commandElement);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public void runMacroExecuteElement(Element executeElement, String macroType)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			macroType);

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

		List<Element> executeVarElements = executeElement.elements("var");

		for (Element executeVarElement : executeVarElements) {
			runExecuteVarElement(executeVarElement, false);
		}

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		String namespace = PoshiRunnerStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		SummaryLogger.startSummary(executeElement);

		Element commandElement = PoshiRunnerContext.getMacroCommandElement(
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

				if (PoshiRunnerVariablesUtil.containsKeyInStaticMap(
						returnName)) {

					PoshiRunnerVariablesUtil.putIntoStaticMap(
						returnName, _macroReturnValue);
				}

				PoshiRunnerVariablesUtil.putIntoCommandMap(
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

		PoshiRunnerStackTraceUtil.popStackTrace();

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runMethodExecuteElement(Element executeElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		List<String> args = new ArrayList<>();

		List<Element> argElements = executeElement.elements("arg");

		for (Element argElement : argElements) {
			args.add(argElement.attributeValue("value"));
		}

		String className = executeElement.attributeValue("class");
		String methodName = executeElement.attributeValue("method");

		try {
			Object returnValue = PoshiRunnerGetterUtil.getMethodReturnValue(
				args, className, methodName, null);

			Element returnElement = executeElement.element("return");

			if (returnElement != null) {
				PoshiRunnerVariablesUtil.putIntoCommandMap(
					returnElement.attributeValue("name"), returnValue);
			}

			_poshiLogger.logExternalMethodCommand(
				executeElement, args, returnValue);
		}
		catch (Throwable t) {
			_poshiLogger.updateStatus(executeElement, "fail");

			throw t;
		}

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runReturnElement(Element returnElement) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(returnElement);

		if (returnElement.attributeValue("value") != null) {
			String returnValue = returnElement.attributeValue("value");

			_macroReturnValue = PoshiRunnerVariablesUtil.replaceCommandVars(
				returnValue);
		}

		_poshiLogger.updateStatus(returnElement, "pass");
	}

	public void runRootVarElement(Element element, boolean updateLoggerStatus)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

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
			varValue = PoshiRunnerVariablesUtil.replaceExecuteVars(
				(String)varValue);

			varValue = PoshiRunnerVariablesUtil.replaceStaticVars(
				(String)varValue);

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

		if (!PoshiRunnerVariablesUtil.containsKeyInExecuteMap(varName)) {
			PoshiRunnerVariablesUtil.putIntoExecuteMap(varName, varValue);
		}

		String currentFilePath = PoshiRunnerStackTraceUtil.getCurrentFilePath();

		if (currentFilePath.contains(".testcase")) {
			String staticValue = element.attributeValue("static");

			if ((staticValue != null) && staticValue.equals("true") &&
				!PoshiRunnerVariablesUtil.containsKeyInStaticMap(varName)) {

				PoshiRunnerVariablesUtil.putIntoStaticMap(varName, varValue);
			}
		}

		if (updateLoggerStatus) {
			_poshiLogger.updateStatus(element, "pass");
		}
	}

	public void runSeleniumElement(Element executeElement) throws Exception {
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
						selenium.equals("waitForConfirmation") ||
						selenium.equals("waitForConsoleTextNotPresent") ||
						selenium.equals("waitForConsoleTextPresent") ||
						selenium.equals("waitForTextNotPresent") ||
						selenium.equals("waitForTextPresent")) {

						argument =
							PoshiRunnerVariablesUtil.getStringFromCommandMap(
								"value1");
					}
					else {
						argument =
							PoshiRunnerVariablesUtil.getStringFromCommandMap(
								"locator1");
					}
				}
				else if (i == 1) {
					argument = PoshiRunnerVariablesUtil.getStringFromCommandMap(
						"value1");

					if (selenium.equals("clickAt")) {
						argument = "";
					}
				}
				else if (i == 2) {
					if (selenium.equals("assertCssValue")) {
						argument =
							PoshiRunnerVariablesUtil.getStringFromCommandMap(
								"value1");
					}
					else {
						argument =
							PoshiRunnerVariablesUtil.getStringFromCommandMap(
								"locator2");
					}
				}
			}
			else {
				argument =
					PoshiRunnerVariablesUtil.getReplacedCommandVarsString(
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
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		_poshiLogger.takeScreenshotCommand(element);
	}

	public void runTaskElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

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

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String namespace =
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		List<Element> rootVarElements = PoshiRunnerContext.getRootVarElements(
			"test-case", className, namespace);

		for (Element rootVarElement : rootVarElements) {
			runRootVarElement(rootVarElement, false);
		}

		PoshiRunnerVariablesUtil.pushCommandMap();

		parseElement(element);

		PoshiRunnerVariablesUtil.popCommandMap();
	}

	public void runTestCaseExecuteElement(Element executeElement)
		throws Exception {

		PoshiRunnerStackTraceUtil.setCurrentElement(executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"test-case");

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		String namespace =
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			namespacedClassCommandName, namespace);

		runTestCaseCommandElement(commandElement, namespacedClassCommandName);

		PoshiRunnerStackTraceUtil.popStackTrace();

		_poshiLogger.updateStatus(executeElement, "pass");
	}

	public void runWhileElement(Element element) throws Exception {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

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
					varValue = PoshiRunnerGetterUtil.getVarMethodValue(
						methodName,
						PoshiRunnerStackTraceUtil.getCurrentNamespace());
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

					Object varFrom =
						PoshiRunnerVariablesUtil.replaceCommandVars(
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
				Object varFrom = PoshiRunnerVariablesUtil.replaceCommandVars(
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

		Throwable cause = throwable.getCause();

		if (cause != null) {
			return _getWarningFromThrowable(cause);
		}

		return null;
	}

	private static final Pattern _locatorKeyPattern = Pattern.compile(
		"\\S#\\S");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\$\\{([^}]*)\\}");

	private Element _functionExecuteElement;
	private String _functionWarningMessage;
	private Object _macroReturnValue;
	private final PoshiLogger _poshiLogger;
	private Object _returnObject;

}