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
import com.liferay.poshi.runner.selenium.LiferaySeleniumHelper;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.util.List;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class CommandLoggerHandler {

	public static void failCommand(Element element) throws Exception {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;

		_failLineGroupLoggerElement(_lineGroupLoggerElement);
	}

	public static String getCommandLogText() {
		return _commandLogLoggerElement.toString();
	}

	public static void logClassCommandName(String classCommandName) {
		LoggerElement dividerLineLoggerElement = _getDividerLineLoggerElement(
			classCommandName);

		_commandLogLoggerElement.addChildLoggerElement(
			dividerLineLoggerElement);
	}

	public static void logSeleniumCommand(
		Element element, List<String> arguments) {

		LoggerElement loggerElement = _lineGroupLoggerElement.loggerElement(
			"ul");

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

		_takeScreenshot("before", _errorLinkId);

		_commandElement = element;

		_lineGroupLoggerElement = _getLineGroupLoggerElement(element);

		_commandLogLoggerElement.addChildLoggerElement(_lineGroupLoggerElement);
	}

	public static void startRunning() throws Exception {
		_xmlLogLoggerElement.addClassName("running");
	}

	public static void stopRunning() throws Exception {
		_commandLogLoggerElement.addClassName("collapse");

		_xmlLogLoggerElement.removeClassName("running");
	}

	private static void _failLineGroupLoggerElement(
			LoggerElement lineGroupLoggerElement)
		throws Exception {

		lineGroupLoggerElement.addClassName("failed");

		lineGroupLoggerElement.addChildLoggerElement(
			_getErrorContainerLoggerElement());

		LoggerElement childContainerLoggerElement =
			lineGroupLoggerElement.loggerElement("ul");

		List<LoggerElement> runLineLoggerElements =
			childContainerLoggerElement.loggerElements("li");

		LoggerElement runLineLoggerElement = runLineLoggerElements.get(
			runLineLoggerElements.size() - 1);

		runLineLoggerElement.addClassName("error-line");
	}

	private static LoggerElement _getButtonLoggerElement(int btnLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("data-btnlinkid", "command-" + btnLinkId);
		loggerElement.setClassName("btn expand-toggle");

		return loggerElement;
	}

	private static LoggerElement _getCauseHeaderLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("cause-header");
		loggerElement.setName("h4");
		loggerElement.setText("Cause:");

		return loggerElement;
	}

	private static LoggerElement _getCauseLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("cause");

		loggerElement.addChildLoggerElement(_getCauseHeaderLoggerElement());

		loggerElement.addChildLoggerElement(
			SummaryLoggerHandler.getCauseBodyLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getChildContainerLoggerElement(
		int btnLinkId) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("data-btnlinkid", "command-" + btnLinkId);
		loggerElement.setClassName("child-container collapse");
		loggerElement.setName("ul");

		return loggerElement;
	}

	private static LoggerElement _getConsoleLoggerElement(int errorLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-errorlinkid", "console-" + errorLinkId);
		loggerElement.setClassName("console errorPanel toggle");

		loggerElement.addChildLoggerElement(_getConsoleLogLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getConsoleLogLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("console-log");

		loggerElement.addChildLoggerElement(_getStepsLoggerElement());

		loggerElement.addChildLoggerElement(_getCauseLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getDividerLineLoggerElement(
		String classCommandName) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("divider-line");
		loggerElement.setText(classCommandName);

		return loggerElement;
	}

	private static LoggerElement _getErrorContainerLoggerElement()
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("error-container hidden");

		loggerElement.addChildLoggerElement(
			_getConsoleLoggerElement(_errorLinkId));

		loggerElement.addChildLoggerElement(
			_getScreenshotsLoggerElement(_errorLinkId));

		_errorLinkId++;

		return loggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-container");
		loggerElement.setText(_getLineContainerText(element));

		return loggerElement;
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

	private static LoggerElement _getLineGroupLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-group linkable");
		loggerElement.setName("li");

		loggerElement.addChildLoggerElement(
			_getButtonLoggerElement(_btnLinkId));

		loggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		loggerElement.addChildLoggerElement(
			_getChildContainerLoggerElement(_btnLinkId));

		_btnLinkId++;

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

	private static LoggerElement _getScreenshotContainerLoggerElement(
		String screenshotName, int errorLinkId) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(screenshotName + " screenshot-container");

		loggerElement.addChildLoggerElement(
			_getScreenshotLoggerElement(screenshotName, errorLinkId));

		loggerElement.addChildLoggerElement(
			_getScreenshotSpanLoggerElement(
				StringUtil.upperCaseFirstLetter(screenshotName)));

		return loggerElement;
	}

	private static LoggerElement _getScreenshotLoggerElement(
		String screenshotName, int errorLinkId) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("alt", screenshotName + errorLinkId);
		loggerElement.setAttribute(
			"src", "screenshot/" + screenshotName + errorLinkId + ".jpg");
		loggerElement.setName("img");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsLoggerElement(int errorLinkId)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-errorlinkid", "screenshots-" + errorLinkId);
		loggerElement.setClassName("errorPanel screenshots toggle");

		loggerElement.addChildLoggerElement(
			_getScreenshotContainerLoggerElement("before", errorLinkId));

		_takeScreenshot("after", errorLinkId);

		loggerElement.addChildLoggerElement(
			_getScreenshotContainerLoggerElement("after", errorLinkId));

		return loggerElement;
	}

	private static LoggerElement _getScreenshotSpanLoggerElement(
		String screenshotName) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setName("span");
		loggerElement.setText(StringUtil.upperCaseFirstLetter(screenshotName));

		return loggerElement;
	}

	private static LoggerElement _getStepsHeaderLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("steps-header");
		loggerElement.setName("h4");
		loggerElement.setText("Steps:");

		return loggerElement;
	}

	private static LoggerElement _getStepsLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("steps");

		loggerElement.addChildLoggerElement(_getStepsHeaderLoggerElement());

		loggerElement.addChildLoggerElement(
			SummaryLoggerHandler.getMajorStepsLoggerElement());

		return loggerElement;
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

	private static void _takeScreenshot(String screenshotName, int errorLinkId)
		throws Exception {

		String testClassCommandName =
			PoshiRunnerContext.getTestCaseCommandName();

		testClassCommandName = StringUtil.replace(
			testClassCommandName, "#", "_");

		LiferaySeleniumHelper.captureScreen(
			PoshiRunnerGetterUtil.getCanonicalPath(".") + "/test-results/" +
				testClassCommandName + "/screenshot/" + screenshotName +
					errorLinkId + ".jpg");
	}

	private static int _btnLinkId;
	private static Element _commandElement;
	private static final LoggerElement _commandLogLoggerElement =
		new LoggerElement("commandLog");
	private static int _errorLinkId;
	private static LoggerElement _lineGroupLoggerElement;
	private static final LoggerElement _xmlLogLoggerElement = new LoggerElement(
		"xml-log");

	static {
		_commandLogLoggerElement.setAttribute("data-logid", "01");
		_commandLogLoggerElement.setClassName("command-log");
		_commandLogLoggerElement.setName("ul");
	}

}