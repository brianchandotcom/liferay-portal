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

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.runner.PoshiRunnerContext;
import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.PoshiRunnerStackTraceUtil;
import com.liferay.poshi.runner.exception.PoshiRunnerLoggerException;
import com.liferay.poshi.runner.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

/**
 * @author Leslie Wong
 */
public abstract class SyntaxLogger {

	public SyntaxLogger() {
	}

	public SyntaxLogger(String namespacedClassCommandName) throws Exception {
		generateSyntaxLog(namespacedClassCommandName);
	}

	public void generateSyntaxLog(String namespacedClassCommandName)
		throws PoshiRunnerLoggerException {

		try {
			_syntaxLogLoggerElement = new LoggerElement("syntaxLogContainer");

			_syntaxLogLoggerElement.setClassName("syntax-log-container");
			_syntaxLogLoggerElement.setName("ul");

			LoggerElement headerLoggerElement = new LoggerElement();

			headerLoggerElement.setClassName("header");
			headerLoggerElement.setName("li");

			LoggerElement lineContainerLoggerElement = new LoggerElement();

			lineContainerLoggerElement.setClassName("line-container");
			lineContainerLoggerElement.setID(null);
			lineContainerLoggerElement.setName("div");

			LoggerElement lineLoggerElement = new LoggerElement();

			lineLoggerElement.setClassName("test-case-command");
			lineLoggerElement.setID(null);
			lineLoggerElement.setName("h3");
			lineLoggerElement.setText(namespacedClassCommandName);

			lineContainerLoggerElement.addChildLoggerElement(lineLoggerElement);

			headerLoggerElement.addChildLoggerElement(
				lineContainerLoggerElement);

			LoggerElement childContainerLoggerElement = new LoggerElement();

			childContainerLoggerElement.setClassName("child-container");
			childContainerLoggerElement.setID(null);
			childContainerLoggerElement.setName("ul");

			String className =
				PoshiRunnerGetterUtil.
					getClassNameFromNamespacedClassCommandName(
						namespacedClassCommandName);
			String namespace =
				PoshiRunnerGetterUtil.
					getNamespaceFromNamespacedClassCommandName(
						namespacedClassCommandName);

			Element setUpElement = PoshiRunnerContext.getTestCaseCommandElement(
				className + "#set-up", namespace);

			if (setUpElement != null) {
				PoshiRunnerStackTraceUtil.startStackTrace(
					namespace + "." + className + "#set-up", "test-case");

				childContainerLoggerElement.addChildLoggerElement(
					getLoggerElementFromElement(setUpElement));

				PoshiRunnerStackTraceUtil.emptyStackTrace();
			}

			PoshiRunnerStackTraceUtil.startStackTrace(
				namespacedClassCommandName, "test-case");

			String classCommandName =
				PoshiRunnerGetterUtil.
					getClassCommandNameFromNamespacedClassCommandName(
						namespacedClassCommandName);

			Element testCaseElement =
				PoshiRunnerContext.getTestCaseCommandElement(
					classCommandName, namespace);

			childContainerLoggerElement.addChildLoggerElement(
				getLoggerElementFromElement(testCaseElement));

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			Element tearDownElement =
				PoshiRunnerContext.getTestCaseCommandElement(
					className + "#tear-down", namespace);

			if (tearDownElement != null) {
				PoshiRunnerStackTraceUtil.startStackTrace(
					namespace + "." + className + "#tear-down", "test-case");

				childContainerLoggerElement.addChildLoggerElement(
					getLoggerElementFromElement(tearDownElement));

				PoshiRunnerStackTraceUtil.emptyStackTrace();
			}

			headerLoggerElement.addChildLoggerElement(
				childContainerLoggerElement);

			_syntaxLogLoggerElement.addChildLoggerElement(headerLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	public LoggerElement getSyntaxLoggerElement(String stackTrace) {
		return _loggerElements.get(stackTrace);
	}

	public String getSyntaxLogText() {
		return _syntaxLogLoggerElement.toString();
	}

	public abstract void updateStatus(Element element, String status);

	protected LoggerElement getBtnContainerLoggerElement(Element element) {
		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(
			getLineNumberItemText(
				PoshiRunnerGetterUtil.getLineNumber(element)));

		if (isExecuteChildElementLogged(element)) {
			sb.append(getBtnItemText("btn-collapse"));
		}

		btnContainerLoggerElement.setText(sb.toString());

		return btnContainerLoggerElement;
	}

	protected final String getBtnItemText(String className) {
		LoggerElement loggerElement = new LoggerElement();

		if (className.equals("btn-collapse")) {
			loggerElement.setAttribute(
				"data-btnlinkid", "collapse-" + _btnLinkCollapseId);
		}
		else if (className.equals("btn-var")) {
			loggerElement.setAttribute(
				"data-btnlinkid", "var-" + _btnLinkVarId);
		}

		loggerElement.setClassName("btn " + className);
		loggerElement.setID(null);
		loggerElement.setName("button");

		return loggerElement.toString();
	}

	protected int getBtnLinkVarId() {
		return _btnLinkVarId;
	}

	protected LoggerElement getChildContainerLoggerElement() throws Exception {
		return getChildContainerLoggerElement(null, null);
	}

	protected LoggerElement getChildContainerLoggerElement(Element element)
		throws Exception {

		return getChildContainerLoggerElement(element, null);
	}

	protected LoggerElement getChildContainerLoggerElement(
			Element element, Element rootElement)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-btnlinkid", "collapse-" + _btnLinkCollapseId);

		loggerElement.setClassName("child-container collapse collapsible");
		loggerElement.setName("ul");

		if (rootElement != null) {
			List<Element> rootVarElements = rootElement.elements("var");

			for (Element rootVarElement : rootVarElements) {
				loggerElement.addChildLoggerElement(
					getVarLoggerElement(rootVarElement));
			}
		}

		if (element != null) {
			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				String childElementName = childElement.getName();

				if (childElementName.equals("description") ||
					childElementName.equals("echo")) {

					loggerElement.addChildLoggerElement(
						getEchoLoggerElement(childElement));
				}
				else if (childElementName.equals("execute")) {
					if (childElement.attributeValue("function") != null) {
						loggerElement.addChildLoggerElement(
							getFunctionExecuteLoggerElement(childElement));
					}
					else if (childElement.attributeValue("groovy-script") !=
								null) {

						loggerElement.addChildLoggerElement(
							getGroovyScriptLoggerElement(childElement));
					}
					else if (childElement.attributeValue("macro") != null) {
						loggerElement.addChildLoggerElement(
							getMacroExecuteLoggerElement(
								childElement, "macro"));
					}
					else if (childElement.attributeValue("method") != null) {
						loggerElement.addChildLoggerElement(
							getMethodExecuteLoggerElement(childElement));
					}
					else if (childElement.attributeValue("test-case") != null) {
						loggerElement.addChildLoggerElement(
							getTestCaseExecuteLoggerElement(childElement));
					}
				}
				else if (childElementName.equals("fail")) {
					loggerElement.addChildLoggerElement(
						getFailLoggerElement(childElement));
				}
				else if (childElementName.equals("for") ||
						 childElementName.equals("task")) {

					loggerElement.addChildLoggerElement(
						getForLoggerElement(childElement));
				}
				else if (childElementName.equals("if")) {
					loggerElement.addChildLoggerElement(
						getIfLoggerElement(childElement));
				}
				else if (childElementName.equals("return")) {
					loggerElement.addChildLoggerElement(
						getReturnLoggerElement(childElement));
				}
				else if (childElementName.equals("take-screenshot")) {
					loggerElement.addChildLoggerElement(
						getTakeScreenshotLoggerElement(childElement));
				}
				else if (childElementName.equals("var")) {
					loggerElement.addChildLoggerElement(
						getVarLoggerElement(childElement));
				}
				else if (childElementName.equals("while")) {
					loggerElement.addChildLoggerElement(
						getWhileLoggerElement(childElement));
				}
			}
		}

		return loggerElement;
	}

	protected LoggerElement getEchoLoggerElement(Element element) {
		return getLineGroupLoggerElement("echo", element);
	}

	protected LoggerElement getFailLoggerElement(Element element) {
		return getLineGroupLoggerElement(element);
	}

	protected LoggerElement getForLoggerElement(Element element)
		throws Exception {

		return getLoggerElementFromElement(element);
	}

	protected LoggerElement getFunctionExecuteLoggerElement(Element element) {
		return getLineGroupLoggerElement("function", element);
	}

	protected LoggerElement getGroovyScriptLoggerElement(Element element) {
		return getLineGroupLoggerElement("groovy-script", element);
	}

	protected abstract LoggerElement getIfLoggerElement(Element element)
		throws Exception;

	protected abstract LoggerElement getLineContainerLoggerElement(
		Element element);

	protected LoggerElement getLineGroupLoggerElement(Element element) {
		return getLineGroupLoggerElement(null, element);
	}

	protected LoggerElement getLineGroupLoggerElement(
		String className, Element element) {

		_btnLinkCollapseId++;
		_btnLinkVarId++;

		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-group");
		loggerElement.setName("li");

		if (Validator.isNotNull(className)) {
			loggerElement.addClassName(className);
		}

		loggerElement.addChildLoggerElement(
			getBtnContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			getLineContainerLoggerElement(element));

		_loggerElements.put(
			PoshiRunnerStackTraceUtil.getSimpleStackTrace(), loggerElement);

		return loggerElement;
	}

	protected String getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	protected LoggerElement getLineNumberItem(int lineNumber) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-number");
		loggerElement.setID(null);
		loggerElement.setName("div");
		loggerElement.setText(String.valueOf(lineNumber));

		return loggerElement;
	}

	protected final String getLineNumberItemText(int lineNumber) {
		LoggerElement loggerElement = getLineNumberItem(lineNumber);

		return loggerElement.toString();
	}

	protected abstract LoggerElement getLoggerElementFromElement(
			Element element)
		throws Exception;

	protected LoggerElement getMacroExecuteLoggerElement(
			Element executeElement, String macroType)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(
			"macro", executeElement);

		String classCommandName = executeElement.attributeValue(macroType);

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		loggerElement.addChildLoggerElement(
			_getMacroCommandLoggerElement(classCommandName));

		PoshiRunnerStackTraceUtil.popStackTrace();

		return loggerElement;
	}

	protected LoggerElement getMethodExecuteLoggerElement(
			Element executeElement)
		throws Exception {

		return getLineGroupLoggerElement("method", executeElement);
	}

	protected LoggerElement getReturnLoggerElement(Element element) {
		return getLineGroupLoggerElement("return", element);
	}

	protected LoggerElement getTakeScreenshotLoggerElement(Element element) {
		return getLineGroupLoggerElement("take-screenshot", element);
	}

	protected LoggerElement getTestCaseCommandLoggerElement(
			String namespacedClassCommandName)
		throws Exception {

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			namespacedClassCommandName,
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName));

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element rootElement = PoshiRunnerContext.getTestCaseRootElement(
			className,
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName));

		return getChildContainerLoggerElement(commandElement, rootElement);
	}

	protected LoggerElement getTestCaseExecuteLoggerElement(
			Element executeElement)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(
			"test-case", executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"test-case");

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		loggerElement.addChildLoggerElement(
			getTestCaseCommandLoggerElement(namespacedClassCommandName));

		PoshiRunnerStackTraceUtil.popStackTrace();

		return loggerElement;
	}

	protected LoggerElement getVarLoggerElement(Element element) {
		return getLineGroupLoggerElement("var", element);
	}

	protected abstract LoggerElement getWhileLoggerElement(Element element)
		throws Exception;

	protected boolean isExecuteChildElementLogged(Element element) {
		List<Element> childElements = element.elements();

		if ((!childElements.isEmpty() && !isExecutingFunction(element) &&
			 !isExecutingGroovyScript(element) &&
			 !isExecutingMethod(element)) ||
			isExecutingMacro(element) || isExecutingTestCase(element)) {

			return true;
		}

		return false;
	}

	protected boolean isExecuting(Element element) {
		if (isExecutingFunction(element) || isExecutingGroovyScript(element) ||
			isExecutingMacro(element) || isExecutingMethod(element) ||
			isExecutingTestCase(element)) {

			return true;
		}

		return false;
	}

	protected boolean isExecutingFunction(Element element) {
		if (element.attributeValue("function") != null) {
			return true;
		}

		return false;
	}

	protected boolean isExecutingGroovyScript(Element element) {
		if (element.attributeValue("groovy-script") != null) {
			return true;
		}

		return false;
	}

	protected boolean isExecutingMacro(Element element) {
		if (element.attributeValue("macro") != null) {
			return true;
		}

		return false;
	}

	protected boolean isExecutingMethod(Element element) {
		if (element.attributeValue("method") != null) {
			return true;
		}

		return false;
	}

	protected boolean isExecutingTestCase(Element element) {
		if (element.attributeValue("test-case") != null) {
			return true;
		}

		return false;
	}

	protected void updateElementStatus(Element element, String status) {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		String stackTrace = PoshiRunnerStackTraceUtil.getSimpleStackTrace();

		if (stackTrace.contains(".function")) {
			return;
		}

		LoggerElement loggerElement = getSyntaxLoggerElement(stackTrace);

		loggerElement.setAttribute("data-status01", status);
	}

	private LoggerElement _getMacroCommandLoggerElement(
			String namespacedClassCommandName)
		throws Exception {

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);
		String namespace = PoshiRunnerStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		Element commandElement = PoshiRunnerContext.getMacroCommandElement(
			classCommandName, namespace);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element rootElement = PoshiRunnerContext.getMacroRootElement(
			className, namespace);

		return getChildContainerLoggerElement(commandElement, rootElement);
	}

	private int _btnLinkCollapseId;
	private int _btnLinkVarId;
	private final Map<String, LoggerElement> _loggerElements = new HashMap<>();
	private LoggerElement _syntaxLogLoggerElement;

}