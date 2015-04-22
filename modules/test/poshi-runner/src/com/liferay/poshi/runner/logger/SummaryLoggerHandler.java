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
import com.liferay.poshi.runner.PoshiRunnerVariablesUtil;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class SummaryLoggerHandler {

	public static void failSummary(Element element) {
		failSummary(element, null);
	}

	public static void failSummary(Element element, String message) {
		if (_isCurrentMajorStep(element)) {
			_failStepLoggerElement(_majorStepLoggerElement);

			_majorStepLoggerElement.addChildLoggerElement(
				_minorStepsLoggerElement);

			if (Validator.isNotNull(message)) {
				LoggerElement errorLoggerElement = new LoggerElement();

				errorLoggerElement.setText("ERROR: " + message);

				_causeBodyLoggerElement.addChildLoggerElement(
					errorLoggerElement);
			}

			_stopMajorStep();
		}

		if (_isCurrentMinorStep(element)) {
			_failStepLoggerElement(_minorStepLoggerElement);

			_stopMinorStep();
		}
	}

	public static void passSummary(Element element) {
		if (_isCurrentMajorStep(element)) {
			_passStepLoggerElement(_majorStepLoggerElement);

			_stopMajorStep();
		}

		if (_isCurrentMinorStep(element)) {
			_passStepLoggerElement(_minorStepLoggerElement);

			_stopMinorStep();
		}
	}

	public static void startSummary(Element element) throws Exception {
		if (_isMajorStep(element)) {
			_startMajorStep(element);

			_majorStepLoggerElement = _getStepLoggerElement(element);

			_majorStepsLoggerElement.addChildLoggerElement(
				_majorStepLoggerElement);

			_minorStepsLoggerElement = new LoggerElement();

			_minorStepsLoggerElement.setName("ul");
		}

		if (_isMinorStep(element)) {
			_startMinorStep(element);

			_minorStepLoggerElement = _getStepLoggerElement(element);

			_minorStepsLoggerElement.addChildLoggerElement(
				_minorStepLoggerElement);
		}
	}

	private static void _failStepLoggerElement(
		LoggerElement stepLoggerElement) {

		LoggerElement lineContainerLoggerElement =
			stepLoggerElement.loggerElement("div");

		lineContainerLoggerElement.addChildLoggerElement(
			_getStatusLoggerElement("FAILED"));
		lineContainerLoggerElement.setName("strong");
	}

	private static LoggerElement _getStatusLoggerElement(String status) {
		LoggerElement statusLoggerElement = new LoggerElement();

		statusLoggerElement.setName("span");
		statusLoggerElement.setText(" --> " + status);

		return statusLoggerElement;
	}

	private static LoggerElement _getStepLoggerElement(Element element)
		throws Exception {

		LoggerElement stepLoggerElement = new LoggerElement();

		stepLoggerElement.setName("li");

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setText(_getSummary(element));

		stepLoggerElement.addChildLoggerElement(lineContainerLoggerElement);

		return stepLoggerElement;
	}

	private static String _getSummary(Element element) throws Exception {
		String summary = null;

		if (element.attributeValue("summary") != null) {
			summary = element.attributeValue("summary");
		}

		if (summary == null) {
			if (element.attributeValue("action") != null) {
				summary = PoshiRunnerContext.getActionCommandSummary(
					element.attributeValue("action"));
			}
			else if (element.attributeValue("action-summary") != null) {
				summary = PoshiRunnerContext.getActionCommandSummary(
					element.attributeValue("action-summary"));
			}
			else if (element.attributeValue("function") != null) {
				summary = PoshiRunnerContext.getFunctionCommandSummary(
					element.attributeValue("function"));
			}
			else if (element.attributeValue("function-summary") != null) {
				summary = PoshiRunnerContext.getFunctionCommandSummary(
					element.attributeValue("function-summary"));
			}
			else if (element.attributeValue("macro") != null) {
				summary = PoshiRunnerContext.getMacroCommandSummary(
					element.attributeValue("macro"));
			}
			else if (element.attributeValue("macro-summary") != null) {
				summary = PoshiRunnerContext.getMacroCommandSummary(
					element.attributeValue("macro-summary"));
			}
		}

		if (summary != null) {
			return _replaceCommandVars(summary, element);
		}

		return null;
	}

	private static boolean _isCurrentMajorStep(Element element) {
		if (element == _majorStepElement) {
			return true;
		}

		return false;
	}

	private static boolean _isCurrentMinorStep(Element element) {
		if (element == _minorStepElement) {
			return true;
		}

		return false;
	}

	private static boolean _isMajorStep(Element element) throws Exception {
		String summary = _getSummary(element);

		if (summary == null) {
			return false;
		}

		if (!Validator.equals(element.getName(), "execute") &&
			!Validator.equals(element.getName(), "task")) {

			return false;
		}

		if (Validator.isNull(element.attributeValue("function")) &&
			Validator.isNull(element.attributeValue("function-summary")) &&
			Validator.isNull(element.attributeValue("macro")) &&
			Validator.isNull(element.attributeValue("macro-summary")) &&
			Validator.isNull(element.attributeValue("summary"))) {

			return false;
		}

		if (_majorStepElement != null) {
			return false;
		}

		return true;
	}

	private static boolean _isMinorStep(Element element) throws Exception {
		String summary = _getSummary(element);

		if (summary == null) {
			return false;
		}

		if (!Validator.equals(element.getName(), "execute")) {
			return false;
		}

		if (Validator.isNull(element.attributeValue("function"))) {
			return false;
		}

		if (_minorStepElement != null) {
			return false;
		}

		if (Validator.isNotNull(_majorStepElement.attributeValue("function"))) {
			return false;
		}

		return true;
	}

	private static void _passStepLoggerElement(
		LoggerElement stepLoggerElement) {

		LoggerElement lineContainerLoggerElement =
			stepLoggerElement.loggerElement("div");

		lineContainerLoggerElement.addChildLoggerElement(
			_getStatusLoggerElement("PASSED"));
	}

	private static String _replaceCommandVars(String token, Element element)
		throws Exception {

		Matcher matcher = _pattern.matcher(token);

		while (matcher.find() &&
			   PoshiRunnerVariablesUtil.containsKeyInExecuteMap(
				   matcher.group(1))) {

			String varName = matcher.group(1);

			String varValue = PoshiRunnerVariablesUtil.getValueFromExecuteMap(
				varName);

			if ((element.attributeValue("function") != null) &&
				varName.startsWith("locator")) {

				varName = StringUtil.replace(varName, "locator", "locator-key");

				String locatorKey =
					PoshiRunnerVariablesUtil.getValueFromExecuteMap(varName);

				if (Validator.isNotNull(locatorKey)) {
					StringBuilder sb = new StringBuilder();

					sb.append("<em title=\"");
					sb.append(varValue);
					sb.append("\">");
					sb.append(locatorKey);
					sb.append("</em>");

					varValue = sb.toString();
				}
			}

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	private static void _startMajorStep(Element element) {
		_majorStepElement = element;
	}

	private static void _startMinorStep(Element element) {
		_minorStepElement = element;
	}

	private static void _stopMajorStep() {
		_majorStepElement = null;
		_majorStepLoggerElement = null;
		_minorStepElement = null;
		_minorStepLoggerElement = null;
		_minorStepsLoggerElement = null;
	}

	private static void _stopMinorStep() {
		_minorStepElement = null;
		_minorStepLoggerElement = null;
	}

	private static final LoggerElement _causeBodyLoggerElement =
		new LoggerElement("cause-body");
	private static Element _majorStepElement = null;
	private static LoggerElement _majorStepLoggerElement = null;
	private static final LoggerElement _majorStepsLoggerElement =
		new LoggerElement("major-steps");
	private static Element _minorStepElement;
	private static LoggerElement _minorStepLoggerElement;
	private static LoggerElement _minorStepsLoggerElement;
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");

}