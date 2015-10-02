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
import com.liferay.poshi.runner.util.HtmlUtil;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class SummaryLoggerHandler {

	public static void failSummary(Element element, String message) {
		if (_isCurrentMajorStep(element)) {
			_causeBodyLoggerElement.setText(HtmlUtil.escape(message));

			_failStepLoggerElement(_majorStepLoggerElement);

			_summaryLogLoggerElement.addChildLoggerElement(
				_getScreenshotsLoggerElement());

			_stopMajorStep();
		}

		if (_isCurrentMinorStep(element)) {
			_causeBodyLoggerElement.setText(HtmlUtil.escape(message));

			_failStepLoggerElement(_minorStepLoggerElement);

			_stopMinorStep();
		}
	}

	public static String getSummary() throws Exception {
		String summaryHTMLContent = _readResource(
			"META-INF/resources/html/summary.html");

		_summaryContentWrapperLoggerElement.addChildLoggerElement(
			_getSummaryContentLoggerElement());

		summaryHTMLContent = StringUtil.replace(
			summaryHTMLContent, "<div id=\"summaryContentContainer\" />",
			_summaryContentContainerLoggerElement.toString());

		LoggerElement summaryTestDescriptionLoggerElement =
			_getSummaryTestDescriptionLoggerElement();

		summaryHTMLContent = StringUtil.replace(
			summaryHTMLContent, "<p id=\"summaryTestDescription\" />",
			summaryTestDescriptionLoggerElement.toString());

		LoggerElement summaryTestNameLoggerElement =
			_getSummaryTestNameLoggerElement();

		summaryHTMLContent = StringUtil.replace(
			summaryHTMLContent, "<h3 id=\"summaryTestName\" />",
			summaryTestNameLoggerElement.toString());

		summaryHTMLContent = StringUtil.replace(
			summaryHTMLContent, "<ul id=\"summaryTitleContainer\" />",
			_summaryTitleContainerLoggerElement.toString());

		return summaryHTMLContent;
	}

	public static LoggerElement getSummarySnapshotLoggerElement() {
		LoggerElement summaryLogLoggerElement = _summaryLogLoggerElement.copy();

		List<LoggerElement> loggerElements =
			summaryLogLoggerElement.loggerElements("div");

		for (LoggerElement loggerElement : loggerElements) {
			String className = loggerElement.getClassName();

			if (className.equals("steps")) {
				_reduceStepsLoggerElement(loggerElement);
			}
			else if (className.equals("screenshots")) {
				summaryLogLoggerElement.removeChildLoggerElement(loggerElement);
			}
		}

		return summaryLogLoggerElement;
	}

	public static void passSummary(Element element) {
		if (_isCurrentMajorStep(element)) {
			if (_containsMinorStepWarning) {
				_warnStepLoggerElement(_majorStepLoggerElement);

				_containsMinorStepWarning = false;
			}
			else {
				_passStepLoggerElement(_majorStepLoggerElement);
			}

			_stopMajorStep();
		}

		if (_isCurrentMinorStep(element)) {
			_passStepLoggerElement(_minorStepLoggerElement);

			_stopMinorStep();
		}
	}

	public static void startMajorSteps() throws Exception {
		_causeBodyLoggerElement = _getCauseBodyLoggerElement();
		_majorStepsLoggerElement = _getMajorStepsLoggerElement();
		_summaryLogLoggerElement = _getSummaryLogLoggerElement();
	}

	public static void startSummary(Element element) throws Exception {
		if (_isMajorStep(element)) {
			_startMajorStep(element);

			_majorStepLoggerElement = _getMajorStepLoggerElement(element);

			_majorStepsLoggerElement.addChildLoggerElement(
				_majorStepLoggerElement);

			_minorStepsLoggerElement = _getMinorStepsLoggerElement();

			_majorStepLoggerElement.addChildLoggerElement(
				_minorStepsLoggerElement);
		}

		if (_isMinorStep(element)) {
			_startMinorStep(element);

			_minorStepLoggerElement = _getMinorStepLoggerElement(element);

			_minorStepsLoggerElement.addChildLoggerElement(
				_minorStepLoggerElement);
		}
	}

	public static void warnSummary(Element element, String message) {
		if (_isCurrentMajorStep(element)) {
			_causeBodyLoggerElement.setText(message);

			_warnStepLoggerElement(_majorStepLoggerElement);

			_stopMajorStep();
		}

		if (_isCurrentMinorStep(element)) {
			_causeBodyLoggerElement.setText(message);

			_warnStepLoggerElement(_minorStepLoggerElement);

			_containsMinorStepWarning = true;

			_warningCount++;

			_summaryContentContainerLoggerElement.addChildLoggerElement(
				_getSummaryContentLoggerElement());

			_summaryTitleContainerLoggerElement.addChildLoggerElement(
				_getSummaryTitleLoggerElement("WARNING #" + _warningCount));

			_stopMinorStep();
		}
	}

	private static void _failStepLoggerElement(
		LoggerElement stepLoggerElement) {

		stepLoggerElement.addClassName("summary-failure");

		LoggerElement lineContainerLoggerElement =
			stepLoggerElement.loggerElement("div");

		if (lineContainerLoggerElement == null) {
			return;
		}

		lineContainerLoggerElement.addChildLoggerElement(
			_getStatusLoggerElement("FAILED"));
		lineContainerLoggerElement.setName("strong");
	}

	private static LoggerElement _getButtonLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("btn header");
		loggerElement.setName("button");
		loggerElement.setText("+");

		return loggerElement;
	}

	private static LoggerElement _getCauseBodyLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("cause-body");
		loggerElement.setName("pre");

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
		loggerElement.addChildLoggerElement(_causeBodyLoggerElement);

		return loggerElement;
	}

	private static LoggerElement _getMajorStepLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("major-step");
		loggerElement.setName("li");

		loggerElement.addChildLoggerElement(_getButtonLoggerElement());

		loggerElement.addChildLoggerElement(
			_getStepDescriptionLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getMajorStepsLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("major-steps");
		loggerElement.setName("ul");

		return loggerElement;
	}

	private static LoggerElement _getMinorStepLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("minor-step");
		loggerElement.setName("li");

		loggerElement.addChildLoggerElement(
			_getStepDescriptionLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getMinorStepsLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("content minor-steps");
		loggerElement.setName("ul");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsAfterHeaderLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setText("After Failure:");
		loggerElement.setName("h5");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsAfterLinkLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		StringBuilder sb = new StringBuilder();

		sb.append("screenshots/after");
		sb.append(CommandLoggerHandler.getErrorLinkId());
		sb.append(".jpg");

		loggerElement.setAttribute("href", sb.toString());

		loggerElement.setAttribute("title", "After Failure");
		loggerElement.setName("a");

		loggerElement.addChildLoggerElement(
			_getScreenshotsAfterThumbnailLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsAfterThumbnailLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("alt", "After Failure");

		StringBuilder sb = new StringBuilder();

		sb.append("screenshots/after");
		sb.append(CommandLoggerHandler.getErrorLinkId());
		sb.append(".jpg");

		loggerElement.setAttribute("src", sb.toString());

		loggerElement.setClassName("screenshots-thumbnail");
		loggerElement.setName("img");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsBeforeHeaderLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setText("Before Failure:");
		loggerElement.setName("h5");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsBeforeLinkLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		StringBuilder sb = new StringBuilder();

		sb.append("screenshots/before");
		sb.append(CommandLoggerHandler.getErrorLinkId());
		sb.append(".jpg");

		loggerElement.setAttribute("href", sb.toString());

		loggerElement.setAttribute("title", "Before Failure");
		loggerElement.setName("a");

		loggerElement.addChildLoggerElement(
			_getScreenshotsBeforeThumbnailLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsBeforeThumbnailLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("alt", "Before Failure");

		StringBuilder sb = new StringBuilder();

		sb.append("screenshots/before");
		sb.append(CommandLoggerHandler.getErrorLinkId());
		sb.append(".jpg");

		loggerElement.setAttribute("src", sb.toString());

		loggerElement.setClassName("screenshots-thumbnail");
		loggerElement.setName("img");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsHeaderLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("screenshots-header");
		loggerElement.setName("h4");
		loggerElement.setText("Screenshots:");

		return loggerElement;
	}

	private static LoggerElement _getScreenshotsLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("screenshots");

		loggerElement.addChildLoggerElement(
			_getScreenshotsHeaderLoggerElement());

		loggerElement.addChildLoggerElement(
			_getScreenshotsBeforeHeaderLoggerElement());
		loggerElement.addChildLoggerElement(
			_getScreenshotsBeforeLinkLoggerElement());

		loggerElement.addChildLoggerElement(
			_getScreenshotsAfterHeaderLoggerElement());
		loggerElement.addChildLoggerElement(
			_getScreenshotsAfterLinkLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getStatusLoggerElement(String status) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("status");
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(" --> " + status);

		return loggerElement;
	}

	private static LoggerElement _getStepDescriptionLoggerElement(
			Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("step-description");
		loggerElement.setText(_getSummary(element));

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
		loggerElement.addChildLoggerElement(_majorStepsLoggerElement);

		return loggerElement;
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
			summary = HtmlUtil.escape(
				PoshiRunnerVariablesUtil.replaceCommandVars(summary));

			return _replaceExecuteVars(summary, element);
		}

		return null;
	}

	private static LoggerElement _getSummaryContentLoggerElement() {
		LoggerElement loggerElement = _summaryLogLoggerElement.copy();

		LoggerElement stepsLoggerElement = loggerElement.loggerElement("div");

		LoggerElement majorStepsLoggerElement =
			stepsLoggerElement.loggerElement("ul");

		List<LoggerElement> majorStepLoggerElements =
			majorStepsLoggerElement.loggerElements("li");

		for (int i = 0; i < majorStepLoggerElements.size(); i++) {
			LoggerElement majorStepLoggerElement = majorStepLoggerElements.get(
				i);

			boolean lastMajorStep = (i >= (majorStepLoggerElements.size() - 1));

			if (_containsMinorStepWarning && lastMajorStep) {
				_warnStepLoggerElement(majorStepLoggerElement);
			}

			String togglerClassNameSuffix = "collapsed";

			String majorStepClassName = majorStepLoggerElement.getClassName();

			if (lastMajorStep) {
				if (majorStepClassName.contains("summary-failure") ||
					majorStepClassName.contains("summary-warning")) {

					togglerClassNameSuffix = "expanded";
				}
			}

			LoggerElement buttonLoggerElement =
				majorStepLoggerElement.loggerElement("button");

			buttonLoggerElement.addClassName(
				"toggler-header-" + togglerClassNameSuffix);

			LoggerElement minorStepsLoggerElement =
				majorStepLoggerElement.loggerElement("ul");

			minorStepsLoggerElement.addClassName(
				"toggler-content-" + togglerClassNameSuffix);
		}

		return loggerElement;
	}

	private static LoggerElement _getSummaryLogLoggerElement() {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("summary-log");
		loggerElement.setName("div");

		loggerElement.addChildLoggerElement(_getStepsLoggerElement());
		loggerElement.addChildLoggerElement(_getCauseLoggerElement());

		return loggerElement;
	}

	private static LoggerElement _getSummaryTestDescriptionLoggerElement() {
		LoggerElement loggerElement = new LoggerElement(
			"summaryTestDescription");

		String testCaseDescription = PoshiRunnerContext.getTestCaseDescription(
			PoshiRunnerContext.getTestCaseCommandName());

		if (Validator.isNull(testCaseDescription)) {
			testCaseDescription = "";
		}

		loggerElement.setName("p");
		loggerElement.setText(testCaseDescription);

		return loggerElement;
	}

	private static LoggerElement _getSummaryTestNameLoggerElement() {
		LoggerElement loggerElement = new LoggerElement("summaryTestName");

		loggerElement.setName("h3");
		loggerElement.setText(PoshiRunnerContext.getTestCaseCommandName());

		return loggerElement;
	}

	private static LoggerElement _getSummaryTitleLinkLoggerElement(
		String title) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute("href", "#");
		loggerElement.setName("a");
		loggerElement.setText(title);

		return loggerElement;
	}

	private static LoggerElement _getSummaryTitleLoggerElement(String title) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setName("li");

		loggerElement.addChildLoggerElement(
			_getSummaryTitleLinkLoggerElement(title));

		return loggerElement;
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

	private static String _readResource(String path) throws Exception {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader = SummaryLoggerHandler.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(path);

		InputStreamReader inputStreamReader = new InputStreamReader(
			inputStream);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		bufferedReader.close();

		return sb.toString();
	}

	private static void _reduceStepsLoggerElement(LoggerElement loggerElement) {
		LoggerElement majorStepsLoggerElement = loggerElement.loggerElement(
			"ul");

		List<LoggerElement> majorStepLoggerElements =
			majorStepsLoggerElement.loggerElements("li");

		Iterator<LoggerElement> iterator = majorStepLoggerElements.iterator();

		while (iterator.hasNext()) {
			LoggerElement majorStepLoggerElement = iterator.next();

			majorStepLoggerElement.removeChildLoggerElements("button");

			if (iterator.hasNext()) {
				majorStepLoggerElement.removeChildLoggerElements("ul");
			}
			else {
				if (_containsMinorStepWarning) {
					_warnStepLoggerElement(majorStepLoggerElement);
				}
				else {
					_failStepLoggerElement(majorStepLoggerElement);
				}
			}
		}
	}

	private static String _replaceExecuteVars(String token, Element element)
		throws Exception {

		Matcher matcher = _pattern.matcher(token);

		while (matcher.find() &&
			   PoshiRunnerVariablesUtil.containsKeyInExecuteMap(
				   matcher.group(1))) {

			String varName = matcher.group(1);

			String varValue = HtmlUtil.escape(
				PoshiRunnerVariablesUtil.getValueFromExecuteMap(varName));

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

	private static void _warnStepLoggerElement(
		LoggerElement stepLoggerElement) {

		stepLoggerElement.addClassName("summary-warning");

		LoggerElement lineContainerLoggerElement =
			stepLoggerElement.loggerElement("div");

		if (lineContainerLoggerElement == null) {
			return;
		}

		lineContainerLoggerElement.addChildLoggerElement(
			_getStatusLoggerElement("WARNING"));
		lineContainerLoggerElement.setName("strong");
	}

	private static LoggerElement _causeBodyLoggerElement;
	private static boolean _containsMinorStepWarning = false;
	private static Element _majorStepElement;
	private static LoggerElement _majorStepLoggerElement;
	private static LoggerElement _majorStepsLoggerElement;
	private static Element _minorStepElement;
	private static LoggerElement _minorStepLoggerElement;
	private static LoggerElement _minorStepsLoggerElement;
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");
	private static final LoggerElement _summaryContentContainerLoggerElement =
		new LoggerElement("summaryContentContainer");
	private static final LoggerElement _summaryContentWrapperLoggerElement =
		new LoggerElement("summaryContentWrapper");
	private static LoggerElement _summaryLogLoggerElement;
	private static final LoggerElement _summaryTitleContainerLoggerElement =
		new LoggerElement("summaryTitleContainer");
	private static int _warningCount = 0;

	static {
		_summaryContentContainerLoggerElement.addChildLoggerElement(
			_summaryContentWrapperLoggerElement);
		_summaryContentContainerLoggerElement.setID("summaryContentContainer");

		_summaryTitleContainerLoggerElement.addChildLoggerElement(
			_getSummaryTitleLoggerElement("SUMMARY"));
		_summaryTitleContainerLoggerElement.setName("ul");
	}

}