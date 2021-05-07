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

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.PoshiStackTraceUtil;
import com.liferay.poshi.core.PoshiVariablesUtil;
import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.exception.PoshiRunnerLoggerException;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.HtmlUtil;

import java.util.List;
import java.util.Objects;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public final class CommandLogger {

	public CommandLogger() {
		_commandLogLoggerElement = new LoggerElement("commandLog");

		_commandLogLoggerElement.setAttribute("data-logid", "01");
		_commandLogLoggerElement.setClassName("collapse command-log");
		_commandLogLoggerElement.setName("ul");
	}

	public void failCommand(Element element, SyntaxLogger syntaxLogger)
		throws PoshiRunnerLoggerException {

		if (!_isCurrentCommand(element)) {
			return;
		}

		try {
			_commandElement = null;

			_failLineGroupLoggerElement(lineGroupLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	public String getCommandLogText() {
		return _commandLogLoggerElement.toString();
	}

	public int getDetailsLinkId() {
		return _detailsLinkId - 1;
	}

	public void logExternalMethodCommand(
			Element element, List<String> arguments, Object returnValue,
			SyntaxLogger syntaxLogger)
		throws Exception {

		lineGroupLoggerElement = new LoggerElement();

		lineGroupLoggerElement.setClassName("line-group linkable");
		lineGroupLoggerElement.setName("li");
		lineGroupLoggerElement.addChildLoggerElement(
			_getExternalMethodLineLoggerElement(
				element, arguments, returnValue));

		_commandLogLoggerElement.addChildLoggerElement(lineGroupLoggerElement);

		LoggerElement scriptLoggerElement = syntaxLogger.getSyntaxLoggerElement(
			PoshiStackTraceUtil.getSimpleStackTrace());

		_linkLoggerElements(scriptLoggerElement);
	}

	public void logMessage(Element element, SyntaxLogger syntaxLogger)
		throws PoshiRunnerLoggerException {

		try {
			lineGroupLoggerElement = _getMessageGroupLoggerElement(element);

			_commandLogLoggerElement.addChildLoggerElement(
				lineGroupLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	public void logNamespacedClassCommandName(
		String namespacedClassCommandName) {

		LoggerElement dividerLineLoggerElement = _getDividerLineLoggerElement(
			namespacedClassCommandName);

		_commandLogLoggerElement.addChildLoggerElement(
			dividerLineLoggerElement);
	}

	public void logSeleniumCommand(Element element, List<String> arguments) {
		LoggerElement loggerElement = lineGroupLoggerElement.loggerElement(
			"ul");

		loggerElement.addChildLoggerElement(
			_getRunLineLoggerElement(element, arguments));
	}

	public void passCommand(Element element, SyntaxLogger syntaxLogger) {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;
	}

	public void startCommand(Element element, SyntaxLogger syntaxLogger)
		throws PoshiRunnerLoggerException {

		if (!_isCommand(element)) {
			return;
		}

		try {
			_takeScreenshot("before", _detailsLinkId);

			_commandElement = element;

			lineGroupLoggerElement = _getLineGroupLoggerElement(element);

			_commandLogLoggerElement.addChildLoggerElement(
				lineGroupLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	public void takeScreenshotCommand(
			Element element, SyntaxLogger syntaxLogger)
		throws PoshiRunnerLoggerException {

		try {
			_takeScreenshot("screenshot", _detailsLinkId);

			_commandElement = element;

			lineGroupLoggerElement = _getMessageGroupLoggerElement(element);

			_commandLogLoggerElement.addChildLoggerElement(
				lineGroupLoggerElement);

			_commandElement = null;

			_screenshotLineGroupLoggerElement(lineGroupLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	public void warnCommand(Element element, SyntaxLogger syntaxLogger)
		throws PoshiRunnerLoggerException {

		if (!_isCurrentCommand(element)) {
			return;
		}

		try {
			_commandElement = null;

			_warningLineGroupLoggerElement(lineGroupLoggerElement);
		}
		catch (Throwable throwable) {
			throw new PoshiRunnerLoggerException(
				throwable.getMessage(), throwable);
		}
	}

	protected LoggerElement lineGroupLoggerElement;

	private void _failLineGroupLoggerElement(
			LoggerElement lineGroupLoggerElement)
		throws Exception {

		lineGroupLoggerElement.addClassName("failed");

		lineGroupLoggerElement.addChildLoggerElement(
			_getErrorDetailsContainerLoggerElement());

		LoggerElement childContainerLoggerElement =
			lineGroupLoggerElement.loggerElement("ul");

		List<LoggerElement> runLineLoggerElements =
			childContainerLoggerElement.loggerElements("li");

		if (!runLineLoggerElements.isEmpty()) {
			LoggerElement runLineLoggerElement = runLineLoggerElements.get(
				runLineLoggerElements.size() - 1);

			runLineLoggerElement.addClassName("error-line");
		}
	}

	private LoggerElement _getBasicConsoleLoggerElement(int detailsLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-detailslinkid", "console-" + detailsLinkId);
		loggerElement.setClassName("console detailsPanel toggle");

		return loggerElement;
	}

	private LoggerElement _getBasicScreenshotsLoggerElement(int detailsLinkId)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-detailslinkid", "screenshots-" + detailsLinkId);
		loggerElement.setClassName("detailsPanel screenshots toggle");

		loggerElement.addChildLoggerElement(
			_getScreenshotContainerLoggerElement("screenshot", detailsLinkId));

		return loggerElement;
	}

	private LoggerElement _getButtonLoggerElement(int btnLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("data-btnlinkid", "command-" + btnLinkId);
		loggerElement.setClassName("btn expand-toggle");

		return loggerElement;
	}

	private LoggerElement _getChildContainerLoggerElement(int btnLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("data-btnlinkid", "command-" + btnLinkId);
		loggerElement.setClassName("child-container collapse");
		loggerElement.setName("ul");

		return loggerElement;
	}

	private LoggerElement _getConsoleLoggerElement(int detailsLinkId) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-detailslinkid", "console-" + detailsLinkId);
		loggerElement.setClassName("console detailsPanel toggle");

		loggerElement.addChildLoggerElement(
			SummaryLogger.getSummarySnapshotLoggerElement());

		return loggerElement;
	}

	private LoggerElement _getDividerLineLoggerElement(
		String classCommandName) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("divider-line");
		loggerElement.setText(classCommandName);

		return loggerElement;
	}

	private LoggerElement _getErrorDetailsContainerLoggerElement()
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("details-container hidden");

		loggerElement.addChildLoggerElement(
			_getConsoleLoggerElement(_detailsLinkId));

		loggerElement.addChildLoggerElement(
			_getScreenshotsLoggerElement(_detailsLinkId));

		_detailsLinkId++;

		return loggerElement;
	}

	private LoggerElement _getExternalMethodLineLoggerElement(
			Element element, List<String> arguments, Object returnValue)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-container");
		loggerElement.setText(
			_getExternalMethodLineText(element, arguments, returnValue));

		return loggerElement;
	}

	private String _getExternalMethodLineText(
			Element element, List<String> arguments, Object returnValue)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "Running "));
		sb.append(
			_getLineItemText("command-name", element.attributeValue("method")));

		if (!arguments.isEmpty()) {
			sb.append(_getLineItemText("misc", " with parameters"));

			for (String argument : arguments) {
				sb.append(
					_getLineItemText(
						"param-value", HtmlUtil.escape("Arg: " + argument)));
			}
		}

		if (returnValue != null) {
			returnValue = "Return: " + returnValue.toString();

			sb.append(
				_getLineItemText(
					"param-value", HtmlUtil.escape(returnValue.toString())));
		}

		return sb.toString();
	}

	private LoggerElement _getLineContainerLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-container");
		loggerElement.setText(_getLineContainerText(element));

		return loggerElement;
	}

	private String _getLineContainerText(Element element) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "Running "));

		String namespacedClassCommandName = element.attributeValue("function");

		sb.append(_getLineItemText("command-name", namespacedClassCommandName));

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				classCommandName);

		String namespace = PoshiStackTraceUtil.getCurrentNamespace(
			namespacedClassCommandName);

		int functionLocatorCount = PoshiContext.getFunctionLocatorCount(
			className, namespace);

		for (int i = 0; i < functionLocatorCount; i++) {
			String locatorKey = "locator" + (i + 1);

			if (PoshiVariablesUtil.containsKeyInExecuteMap(locatorKey)) {
				sb.append(_getLineItemText("misc", " with "));
				sb.append(_getLineItemText("param-type", locatorKey));

				String paramValue = PoshiVariablesUtil.getStringFromExecuteMap(
					locatorKey);

				sb.append(
					_getLineItemText(
						"param-value", HtmlUtil.escape(paramValue)));
			}

			String valueKey = "value" + (i + 1);

			if (PoshiVariablesUtil.containsKeyInExecuteMap(valueKey)) {
				sb.append(_getLineItemText("misc", " with "));
				sb.append(_getLineItemText("param-type", valueKey));

				String paramValue = PoshiVariablesUtil.getStringFromExecuteMap(
					valueKey);

				sb.append(
					_getLineItemText(
						"param-value", HtmlUtil.escape(paramValue)));
			}
		}

		return sb.toString();
	}

	private LoggerElement _getLineGroupLoggerElement(Element element)
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

	private String _getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	private LoggerElement _getMessageContainerLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-container");

		String elementName = element.getName();

		if (elementName.equals("take-screenshot")) {
			loggerElement.setText("Taking screenshot");
		}
		else {
			loggerElement.setText(_getMessageContainerText(element));
		}

		return loggerElement;
	}

	private String _getMessageContainerText(Element element) throws Exception {
		String message = element.attributeValue("message");

		if (message == null) {
			message = element.getText();
		}

		return PoshiVariablesUtil.getReplacedCommandVarsString(message);
	}

	private LoggerElement _getMessageGroupLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		String className = "line-group linkable";

		if (_isFail(element)) {
			className = className + " failed";
		}

		loggerElement.setClassName(className);

		loggerElement.setName("li");

		loggerElement.addChildLoggerElement(
			_getMessageContainerLoggerElement(element));

		return loggerElement;
	}

	private LoggerElement _getRunLineLoggerElement(
		Element element, List<String> arguments) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("run-line");
		loggerElement.setName("li");
		loggerElement.setText(_getRunLineText(element, arguments));

		return loggerElement;
	}

	private String _getRunLineText(Element element, List<String> arguments) {
		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "Running "));
		sb.append(
			_getLineItemText(
				"command-name", element.attributeValue("selenium")));

		if (!arguments.isEmpty()) {
			sb.append(_getLineItemText("misc", " with parameters"));

			for (String argument : arguments) {
				sb.append(
					_getLineItemText("param-value", HtmlUtil.escape(argument)));
			}
		}

		return sb.toString();
	}

	private LoggerElement _getScreenshotContainerLoggerElement(
		String screenshotName, int detailsLinkId) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(screenshotName + " screenshot-container");

		loggerElement.addChildLoggerElement(
			_getScreenshotLoggerElement(screenshotName, detailsLinkId));

		loggerElement.addChildLoggerElement(
			_getScreenshotSpanLoggerElement(
				StringUtil.upperCaseFirstLetter(screenshotName)));

		return loggerElement;
	}

	private LoggerElement _getScreenshotDetailsContainerLoggerElement()
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.addChildLoggerElement(
			_getBasicConsoleLoggerElement(_detailsLinkId));

		loggerElement.addChildLoggerElement(
			_getBasicScreenshotsLoggerElement(_detailsLinkId));

		_detailsLinkId++;

		return loggerElement;
	}

	private LoggerElement _getScreenshotLoggerElement(
		String screenshotName, int detailsLinkId) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("alt", screenshotName + detailsLinkId);
		loggerElement.setAttribute(
			"src", "screenshots/" + screenshotName + detailsLinkId + ".jpg");
		loggerElement.setName("img");

		return loggerElement;
	}

	private LoggerElement _getScreenshotsLoggerElement(int detailsLinkId)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-detailslinkid", "screenshots-" + detailsLinkId);
		loggerElement.setClassName("detailsPanel screenshots toggle");

		loggerElement.addChildLoggerElement(
			_getScreenshotContainerLoggerElement("before", detailsLinkId));

		_takeScreenshot("after", detailsLinkId);

		loggerElement.addChildLoggerElement(
			_getScreenshotContainerLoggerElement("after", detailsLinkId));

		return loggerElement;
	}

	private LoggerElement _getScreenshotSpanLoggerElement(
		String screenshotName) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setName("span");
		loggerElement.setText(StringUtil.upperCaseFirstLetter(screenshotName));

		return loggerElement;
	}

	private boolean _isCommand(Element element) {
		if (!Objects.equals(element.getName(), "condition") &&
			!Objects.equals(element.getName(), "execute")) {

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

	private boolean _isCurrentCommand(Element element) {
		return element.equals(_commandElement);
	}

	private boolean _isFail(Element element) {
		return Objects.equals(
			StringUtil.toLowerCase(element.getName()), "fail");
	}

	private void _linkLoggerElements(LoggerElement scriptLoggerElement) {
		String functionLinkID = scriptLoggerElement.getAttributeValue(
			"data-functionlinkid");

		if (functionLinkID != null) {
			_functionLinkId = GetterUtil.getInteger(
				functionLinkID.substring(15));
		}

		scriptLoggerElement.setAttribute(
			"data-functionlinkid", "functionLinkId-" + _functionLinkId);

		lineGroupLoggerElement.setAttribute(
			"data-functionlinkid", "functionLinkId-" + _functionLinkId);

		_functionLinkId++;
	}

	private void _screenshotLineGroupLoggerElement(
			LoggerElement lineGroupLoggerElement)
		throws Exception {

		lineGroupLoggerElement.addClassName("screenshot");

		lineGroupLoggerElement.addChildLoggerElement(
			_getScreenshotDetailsContainerLoggerElement());
	}

	private void _takeScreenshot(String screenshotName, int detailsLinkId)
		throws Exception {

		String testClassCommandName =
			PoshiContext.getTestCaseNamespacedClassCommandName();

		testClassCommandName = StringUtil.replace(
			testClassCommandName, "#", "_");

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		liferaySelenium.saveScreenshot(
			FileUtil.getCanonicalPath(".") + "/test-results/" +
				testClassCommandName + "/screenshots/" + screenshotName +
					detailsLinkId + ".jpg");
	}

	private void _warningLineGroupLoggerElement(
			LoggerElement lineGroupLoggerElement)
		throws Exception {

		lineGroupLoggerElement.addClassName("warning");

		lineGroupLoggerElement.addChildLoggerElement(
			_getErrorDetailsContainerLoggerElement());

		LoggerElement childContainerLoggerElement =
			lineGroupLoggerElement.loggerElement("ul");

		List<LoggerElement> runLineLoggerElements =
			childContainerLoggerElement.loggerElements("li");

		if (!runLineLoggerElements.isEmpty()) {
			LoggerElement runLineLoggerElement = runLineLoggerElements.get(
				runLineLoggerElements.size() - 1);

			runLineLoggerElement.addClassName("warning-line");
		}
	}

	private int _btnLinkId;
	private Element _commandElement;
	private final LoggerElement _commandLogLoggerElement;
	private int _detailsLinkId;
	private int _functionLinkId;

}