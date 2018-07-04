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
import com.liferay.poshi.runner.util.HtmlUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class XMLLoggerHandler {

	public static void generateXMLLog(String namespacedClassCommandName)
		throws PoshiRunnerLoggerException {

		try {
			_xmlLogLoggerElement = new LoggerElement("xmlLogContainer");

			_xmlLogLoggerElement.setClassName("xml-log-container");
			_xmlLogLoggerElement.setName("ul");

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
					_getLoggerElementFromElement(setUpElement));

				PoshiRunnerStackTraceUtil.emptyStackTrace();
			}

			PoshiRunnerStackTraceUtil.startStackTrace(
				namespacedClassCommandName, "test-case");

			String classCommandName =
				PoshiRunnerGetterUtil.
					getClassCommandNameFromNamespacedClassCommandName(
						namespacedClassCommandName);

			childContainerLoggerElement.addChildLoggerElement(
				_getLoggerElementFromElement(
					PoshiRunnerContext.getTestCaseCommandElement(
						classCommandName, namespace)));

			PoshiRunnerStackTraceUtil.emptyStackTrace();

			Element tearDownElement =
				PoshiRunnerContext.getTestCaseCommandElement(
					className + "#tear-down", namespace);

			if (tearDownElement != null) {
				PoshiRunnerStackTraceUtil.startStackTrace(
					namespace + "." + className + "#tear-down", "test-case");

				childContainerLoggerElement.addChildLoggerElement(
					_getLoggerElementFromElement(tearDownElement));

				PoshiRunnerStackTraceUtil.emptyStackTrace();
			}

			headerLoggerElement.addChildLoggerElement(
				childContainerLoggerElement);

			_xmlLogLoggerElement.addChildLoggerElement(headerLoggerElement);
		}
		catch (Throwable t) {
			throw new PoshiRunnerLoggerException(t.getMessage(), t);
		}
	}

	public static LoggerElement getXMLLoggerElement(String stackTrace) {
		return _loggerElements.get(stackTrace);
	}

	public static String getXMLLogText() {
		return _xmlLogLoggerElement.toString();
	}

	public static void updateStatus(Element element, String status) {
		PoshiRunnerStackTraceUtil.setCurrentElement(element);

		String stackTrace = PoshiRunnerStackTraceUtil.getSimpleStackTrace();

		if (stackTrace.contains(".function")) {
			return;
		}

		LoggerElement loggerElement = getXMLLoggerElement(stackTrace);

		loggerElement.setAttribute("data-status01", status);
	}

	private static LoggerElement _getBtnContainerLoggerElement(
		Element element) {

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(
			_getLineNumberItemText(element.attributeValue("line-number")));

		List<Element> childElements = element.elements();

		if ((!childElements.isEmpty() && !_isExecutingFunction(element) &&
			 !_isExecutingGroovyScript(element) &&
			 !_isExecutingMethod(element)) ||
			_isExecutingMacro(element) || _isExecutingTestCase(element)) {

			sb.append(_getBtnItemText("btn-collapse"));
		}

		if (!childElements.isEmpty() &&
			(_isExecutingFunction(element) ||
			 _isExecutingGroovyScript(element) || _isExecutingMacro(element) ||
			 _isExecutingTestCase(element) || _isExecutingMethod(element))) {

			sb.append(_getBtnItemText("btn-var"));
		}

		btnContainerLoggerElement.setText(sb.toString());

		return btnContainerLoggerElement;
	}

	private static String _getBtnItemText(String className) {
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

	private static LoggerElement _getChildContainerLoggerElement()
		throws Exception {

		return _getChildContainerLoggerElement(null, null);
	}

	private static LoggerElement _getChildContainerLoggerElement(
			Element element)
		throws Exception {

		return _getChildContainerLoggerElement(element, null);
	}

	private static LoggerElement _getChildContainerLoggerElement(
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
					_getVarLoggerElement(rootVarElement));
			}
		}

		if (element != null) {
			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				String childElementName = childElement.getName();

				if (childElementName.equals("description") ||
					childElementName.equals("echo")) {

					loggerElement.addChildLoggerElement(
						_getEchoLoggerElement(childElement));
				}
				else if (childElementName.equals("execute")) {
					if (childElement.attributeValue("function") != null) {
						loggerElement.addChildLoggerElement(
							_getFunctionExecuteLoggerElement(childElement));
					}
					else if (childElement.attributeValue("groovy-script") !=
								null) {

						loggerElement.addChildLoggerElement(
							_getGroovyScriptLoggerElement(childElement));
					}
					else if (childElement.attributeValue("macro") != null) {
						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro"));
					}
					else if (Validator.isNotNull(
								childElement.attributeValue("macro-desktop")) &&
							 !PropsValues.MOBILE_BROWSER) {

						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro-desktop"));
					}
					else if (Validator.isNotNull(
								childElement.attributeValue("macro-mobile")) &&
							 PropsValues.MOBILE_BROWSER) {

						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro-mobile"));
					}
					else if (childElement.attributeValue("method") != null) {
						loggerElement.addChildLoggerElement(
							_getMethodExecuteLoggerElement(childElement));
					}
					else if (childElement.attributeValue("test-case") != null) {
						loggerElement.addChildLoggerElement(
							_getTestCaseExecuteLoggerElement(childElement));
					}
				}
				else if (childElementName.equals("fail")) {
					loggerElement.addChildLoggerElement(
						_getFailLoggerElement(childElement));
				}
				else if (childElementName.equals("for") ||
						 childElementName.equals("task")) {

					loggerElement.addChildLoggerElement(
						_getForLoggerElement(childElement));
				}
				else if (childElementName.equals("if")) {
					loggerElement.addChildLoggerElement(
						_getIfLoggerElement(childElement));
				}
				else if (childElementName.equals("return")) {
					loggerElement.addChildLoggerElement(
						_getReturnLoggerElement(childElement));
				}
				else if (childElementName.equals("toggle")) {
					loggerElement.addChildLoggerElement(
						_getToggleLoggerElement(childElement));
				}
				else if (childElementName.equals("var")) {
					loggerElement.addChildLoggerElement(
						_getVarLoggerElement(childElement));
				}
				else if (childElementName.equals("while")) {
					loggerElement.addChildLoggerElement(
						_getWhileLoggerElement(childElement));
				}
			}
		}

		return loggerElement;
	}

	private static LoggerElement _getClosingLineContainerLoggerElement(
		Element element) {

		LoggerElement closingLineContainerLoggerElement = new LoggerElement();

		closingLineContainerLoggerElement.setClassName("line-container");
		closingLineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;/"));
		sb.append(_getLineItemText("action-type", element.getName()));
		sb.append(_getLineItemText("misc", "&gt;"));

		closingLineContainerLoggerElement.setText(sb.toString());

		return closingLineContainerLoggerElement;
	}

	private static LoggerElement _getConditionalLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = null;

		if (_isExecutingFunction(element)) {
			loggerElement = _getLineGroupLoggerElement(
				"conditional-function", element);
		}
		else {
			loggerElement = _getLineGroupLoggerElement("conditional", element);
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			LoggerElement childContainerLoggerElement =
				_getChildContainerLoggerElement();

			for (Element childElement : childElements) {
				childContainerLoggerElement.addChildLoggerElement(
					_getConditionalLoggerElement(childElement));
			}

			loggerElement.addChildLoggerElement(childContainerLoggerElement);
			loggerElement.addChildLoggerElement(
				_getClosingLineContainerLoggerElement(element));
		}

		return loggerElement;
	}

	private static LoggerElement _getEchoLoggerElement(Element element) {
		return _getLineGroupLoggerElement("echo", element);
	}

	private static LoggerElement _getFailLoggerElement(Element element) {
		return _getLineGroupLoggerElement(element);
	}

	private static LoggerElement _getForLoggerElement(Element element)
		throws Exception {

		return _getLoggerElementFromElement(element);
	}

	private static LoggerElement _getFunctionExecuteLoggerElement(
		Element element) {

		return _getLineGroupLoggerElement("function", element);
	}

	private static LoggerElement _getGroovyScriptLoggerElement(
		Element element) {

		return _getLineGroupLoggerElement("groovy-script", element);
	}

	private static LoggerElement _getIfChildContainerLoggerElement(
			Element element)
		throws Exception {

		LoggerElement loggerElement = _getChildContainerLoggerElement();

		List<Element> childElements = element.elements();

		Element conditionElement = childElements.get(0);

		loggerElement.addChildLoggerElement(
			_getConditionalLoggerElement(conditionElement));

		Element thenElement = element.element("then");

		loggerElement.addChildLoggerElement(
			_getLoggerElementFromElement(thenElement));

		List<Element> elseIfElements = element.elements("elseif");

		for (Element elseIfElement : elseIfElements) {
			loggerElement.addChildLoggerElement(
				_getIfLoggerElement(elseIfElement));
		}

		Element elseElement = element.element("else");

		if (elseElement != null) {
			loggerElement.addChildLoggerElement(
				_getLoggerElementFromElement(elseElement));
		}

		return loggerElement;
	}

	private static LoggerElement _getIfLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"conditional", element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(
		Element element) {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		if (element.attributeValue("macro") != null) {
			lineContainerLoggerElement.setAttribute(
				"onmouseout", "macroHover(this, false)");
			lineContainerLoggerElement.setAttribute(
				"onmouseover", "macroHover(this, true)");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;"));
		sb.append(_getLineItemText("action-type", element.getName()));

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("line-number")) {
				continue;
			}

			sb.append(_getLineItemText("tag-type", attributeName));
			sb.append(_getLineItemText("misc", "="));
			sb.append(_getLineItemText("misc quote", "\""));
			sb.append(_getLineItemText("name", attribute.getValue()));
			sb.append(_getLineItemText("misc quote", "\""));
		}

		List<Element> elements = element.elements();

		String innerText = element.getText();

		innerText = innerText.trim();

		if (elements.isEmpty() && Validator.isNull(innerText)) {
			sb.append(_getLineItemText("misc", "/&gt;"));
		}
		else {
			sb.append(_getLineItemText("misc", "&gt;"));
		}

		if (Validator.isNotNull(innerText)) {
			sb.append(_getLineItemText("name", HtmlUtil.escape(innerText)));
			sb.append(_getLineItemText("misc", "&lt;/"));
			sb.append(_getLineItemText("action-type", element.getName()));
			sb.append(_getLineItemText("misc", "&gt;"));
		}

		lineContainerLoggerElement.setText(sb.toString());

		String elementName = element.getName();

		if (elementName.equals("execute") && !elements.isEmpty()) {
			lineContainerLoggerElement.addChildLoggerElement(
				_getParameterContainerLoggerElement(element));
		}

		return lineContainerLoggerElement;
	}

	private static LoggerElement _getLineGroupLoggerElement(Element element) {
		return _getLineGroupLoggerElement(null, element);
	}

	private static LoggerElement _getLineGroupLoggerElement(
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
			_getBtnContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		_loggerElements.put(
			PoshiRunnerStackTraceUtil.getSimpleStackTrace(), loggerElement);

		return loggerElement;
	}

	private static String _getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	private static LoggerElement _getLineNumberItem(String lineNumber) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-number");
		loggerElement.setID(null);
		loggerElement.setName("div");
		loggerElement.setText(lineNumber);

		return loggerElement;
	}

	private static String _getLineNumberItemText(String lineNumber) {
		LoggerElement loggerElement = _getLineNumberItem(lineNumber);

		return loggerElement.toString();
	}

	private static LoggerElement _getLoggerElementFromElement(Element element)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			_getChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getMacroCommandLoggerElement(
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

		return _getChildContainerLoggerElement(commandElement, rootElement);
	}

	private static LoggerElement _getMacroExecuteLoggerElement(
			Element executeElement, String macroType)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"macro", executeElement);

		String classCommandName = executeElement.attributeValue(macroType);

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		loggerElement.addChildLoggerElement(
			_getMacroCommandLoggerElement(classCommandName));

		PoshiRunnerStackTraceUtil.popStackTrace();

		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(executeElement));

		return loggerElement;
	}

	private static LoggerElement _getMethodExecuteLoggerElement(
			Element executeElement)
		throws Exception {

		return _getLineGroupLoggerElement("method", executeElement);
	}

	private static LoggerElement _getParameterContainerLoggerElement(
		Element element) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("data-btnlinkid", "var-" + _btnLinkVarId);
		loggerElement.setClassName(
			"child-container collapse parameter-container");
		loggerElement.setID(null);
		loggerElement.setName("div");

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			loggerElement.addChildLoggerElement(
				_getLineNumberItem(childElement.attributeValue("line-number")));
			loggerElement.addChildLoggerElement(
				_getLineContainerLoggerElement(childElement));
		}

		return loggerElement;
	}

	private static LoggerElement _getReturnLoggerElement(Element element) {
		return _getLineGroupLoggerElement("return", element);
	}

	private static LoggerElement _getTestCaseCommandLoggerElement(
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

		return _getChildContainerLoggerElement(commandElement, rootElement);
	}

	private static LoggerElement _getTestCaseExecuteLoggerElement(
			Element executeElement)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"test-case", executeElement);

		String namespacedClassCommandName = executeElement.attributeValue(
			"test-case");

		PoshiRunnerStackTraceUtil.pushStackTrace(executeElement);

		loggerElement.addChildLoggerElement(
			_getTestCaseCommandLoggerElement(namespacedClassCommandName));

		PoshiRunnerStackTraceUtil.popStackTrace();

		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(executeElement));

		return loggerElement;
	}

	private static LoggerElement _getToggleChildContainerLoggerElement(
			Element element)
		throws Exception {

		LoggerElement loggerElement = _getChildContainerLoggerElement();

		String toggleName = element.attributeValue("name");

		if (PoshiRunnerContext.isTestToggle(toggleName)) {
			Element onElement = element.element("on");

			if (onElement != null) {
				loggerElement.addChildLoggerElement(
					_getLoggerElementFromElement(onElement));
			}
		}
		else {
			Element onElement = element.element("off");

			if (onElement != null) {
				loggerElement.addChildLoggerElement(
					_getLoggerElementFromElement(onElement));
			}
		}

		return loggerElement;
	}

	private static LoggerElement _getToggleLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"conditional", element);

		loggerElement.addChildLoggerElement(
			_getToggleChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getVarLoggerElement(Element element) {
		return _getLineGroupLoggerElement("var", element);
	}

	private static LoggerElement _getWhileLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = _getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static boolean _isExecutingFunction(Element element) {
		if (element.attributeValue("function") != null) {
			return true;
		}

		return false;
	}

	private static boolean _isExecutingGroovyScript(Element element) {
		if (element.attributeValue("groovy-script") != null) {
			return true;
		}

		return false;
	}

	private static boolean _isExecutingMacro(Element element) {
		if ((element.attributeValue("macro") != null) ||
			(element.attributeValue("macro-desktop") != null) ||
			(element.attributeValue("macro-mobile") != null)) {

			return true;
		}

		return false;
	}

	private static boolean _isExecutingMethod(Element element) {
		if (element.attributeValue("method") != null) {
			return true;
		}

		return false;
	}

	private static boolean _isExecutingTestCase(Element element) {
		if (element.attributeValue("test-case") != null) {
			return true;
		}

		return false;
	}

	private static int _btnLinkCollapseId;
	private static int _btnLinkVarId;
	private static final Map<String, LoggerElement> _loggerElements =
		new HashMap<>();
	private static LoggerElement _xmlLogLoggerElement;

}