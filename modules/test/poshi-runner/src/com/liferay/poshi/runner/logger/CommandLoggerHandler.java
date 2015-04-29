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
import com.liferay.poshi.runner.PoshiRunnerVariablesUtil;
import com.liferay.poshi.runner.util.Validator;

import java.util.List;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class CommandLoggerHandler {

	public static void failCommand(Element element) {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;
	}

	public static String getCommandLogText() {
		return _commandLogLoggerElement.toString();
	}

	public static void logClassCommandName(String classCommandName) {
		LoggerElement dividerLineLoggerElement = new LoggerElement();

		dividerLineLoggerElement.setClassName("divider-line");
		dividerLineLoggerElement.setText(classCommandName);

		_commandLogLoggerElement.addChildLoggerElement(
			dividerLineLoggerElement);
	}

	public static void logSeleniumCommand(
		Element element, List<String> arguments) {

		LoggerElement loggerElement = _commandLoggerElement.loggerElement("ul");

		loggerElement.addChildLoggerElement(
			_getRunLineLoggerElement(element, arguments));
	}

	public static void passCommand(Element element) {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;
	}

	public static void startCommand(Element element) throws Exception {
		if (!_isCommand(element)) {
			return;
		}

		_commandElement = element;

		_commandLoggerElement = _getCommandLoggerElement(element);

		_commandLogLoggerElement.addChildLoggerElement(_commandLoggerElement);
	}

	private static LoggerElement _getButtonLoggerElement(int btnLinkId) {
		LoggerElement buttonLoggerElement = new LoggerElement();

		buttonLoggerElement.setAttribute(
			"data-btnlinkid", "command-" + btnLinkId);
		buttonLoggerElement.setClassName("btn expand-toggle");

		return buttonLoggerElement;
	}

	private static LoggerElement _getChildContainerLoggerElement(
		int btnLinkId) {

		LoggerElement childContainerLoggerElement = new LoggerElement();

		childContainerLoggerElement.setAttribute(
			"data-btnlinkid", "command-" + btnLinkId);
		childContainerLoggerElement.setClassName("child-container collapse");
		childContainerLoggerElement.setName("ul");

		return childContainerLoggerElement;
	}

	private static LoggerElement _getCommandLoggerElement(Element element)
		throws Exception {

		LoggerElement commandLoggerElement = new LoggerElement();

		commandLoggerElement.setClassName("line-group linkable");
		commandLoggerElement.setName("li");

		commandLoggerElement.addChildLoggerElement(
			_getButtonLoggerElement(_btnLinkId));

		commandLoggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		commandLoggerElement.addChildLoggerElement(
			_getChildContainerLoggerElement(_btnLinkId));

		_btnLinkId++;

		return commandLoggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(Element element)
		throws Exception {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setText(_getLineContainerText(element));

		return lineContainerLoggerElement;
	}

	private static String _getLineContainerText(Element element)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "Running "));

		String classCommandName = element.attributeValue("function");

		sb.append(_getLineItemText("command-name", classCommandName));

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		int functionLocatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < functionLocatorCount; i++) {
			String locatorKey = "locator" + (i + 1);

			if (PoshiRunnerVariablesUtil.containsKeyInExecuteMap(locatorKey)) {
				sb.append(_getLineItemText("misc", " with "));
				sb.append(_getLineItemText("param-type", locatorKey));
				sb.append(_getLineItemText("misc", "&nbsp;"));

				String paramValue =
					PoshiRunnerVariablesUtil.getValueFromExecuteMap(locatorKey);

				sb.append(_getLineItemText("param-value", paramValue));
			}

			String valueKey = "value" + (i + 1);

			if (PoshiRunnerVariablesUtil.containsKeyInExecuteMap(valueKey)) {
				sb.append(_getLineItemText("misc", " with "));
				sb.append(_getLineItemText("param-type", valueKey));
				sb.append(_getLineItemText("misc", "&nbsp;"));

				String paramValue =
					PoshiRunnerVariablesUtil.getValueFromExecuteMap(valueKey);

				sb.append(_getLineItemText("param-value", paramValue));
			}
		}

		return sb.toString();
	}

	private static String _getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	private static LoggerElement _getRunLineLoggerElement(
		Element element, List<String> arguments) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("run-line");
		loggerElement.setName("li");
		loggerElement.setText(_getRunLineText(element, arguments));

		return loggerElement;
	}

	private static String _getRunLineText(
		Element element, List<String> arguments) {

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "Running "));
		sb.append(
			_getLineItemText(
				"command-name", element.attributeValue("selenium")));

		if (!arguments.isEmpty()) {
			sb.append(_getLineItemText("misc", " with parameters"));

			for (String argument : arguments) {
				sb.append(_getLineItemText("misc", "&nbsp;"));
				sb.append(_getLineItemText("param-value", argument));
			}
		}

		return sb.toString();
	}

	private static boolean _isCommand(Element element) {
		if (!Validator.equals(element.getName(), "execute")) {
			return false;
		}

		if (Validator.isNull(element.attributeValue("function"))) {
			return false;
		}

		if (_commandElement != null) {
			return false;
		}

		return true;
	}

	private static boolean _isCurrentCommand(Element element) {
		return element.equals(_commandElement);
	}

	private static int _btnLinkId;
	private static Element _commandElement;
	private static LoggerElement _commandLoggerElement;
	private static final LoggerElement _commandLogLoggerElement =
		new LoggerElement("commandLog");

	static {
		_commandLogLoggerElement.setAttribute("data-logid", "01");
		_commandLogLoggerElement.setClassName("command-log");
		_commandLogLoggerElement.setName("ul");
	}

}