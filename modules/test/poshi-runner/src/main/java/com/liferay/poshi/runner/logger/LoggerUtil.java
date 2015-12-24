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
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Michael Hashimoto
 */
public final class LoggerUtil {

	public static void addChildLoggerElement(
		LoggerElement parentLoggerElement, LoggerElement childLoggerElement) {

		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder parentSB = new StringBuilder();

		parentSB.append("{");
		parentSB.append("cssClass: '");
		parentSB.append(
			StringEscapeUtils.escapeEcmaScript(
				childLoggerElement.getClassName()));
		parentSB.append("', ");
		parentSB.append("id: '");
		parentSB.append(
			StringEscapeUtils.escapeEcmaScript(childLoggerElement.getID()));
		parentSB.append("', ");
		parentSB.append("innerHTML: '");
		parentSB.append(
			StringEscapeUtils.escapeEcmaScript(childLoggerElement.getText()));
		parentSB.append("', ");
		parentSB.append("name: '");
		parentSB.append(
			StringEscapeUtils.escapeEcmaScript(childLoggerElement.getName()));
		parentSB.append("', ");
		parentSB.append("parentId: '");
		parentSB.append(
			StringEscapeUtils.escapeEcmaScript(parentLoggerElement.getID()));
		parentSB.append("'");
		parentSB.append("}");

		StringBuilder childSB = new StringBuilder();

		childSB.append("{");

		List<String> attributeNames = childLoggerElement.getAttributeNames();

		Iterator<String> iterator = attributeNames.iterator();

		while (iterator.hasNext()) {
			String attributeName = iterator.next();

			String escapedAttributeName = StringEscapeUtils.escapeEcmaScript(
				attributeName);
			String escapedAttributeValue = StringEscapeUtils.escapeEcmaScript(
				childLoggerElement.getAttributeValue(attributeName));

			childSB.append(
				"'" + escapedAttributeName + "': '" + escapedAttributeValue +
					"'");

			if (iterator.hasNext()) {
				childSB.append(", ");
			}
		}

		childSB.append("}");

		_javascriptExecutor.executeScript(
			"addChildLoggerElement(" + parentSB + ", " + childSB + ");");
	}

	public static void createSummary() throws Exception {
		FileUtil.write(
			_getSummaryLogFilePath(), SummaryLoggerHandler.getSummary());
	}

	public static void executeJavaScript(String script) {
		if (!isLoggerStarted()) {
			return;
		}

		_javascriptExecutor.executeScript(script);
	}

	public static String getClassName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		return (String)_javascriptExecutor.executeScript(
			"getClassName('" + loggerElement.getID() + "');");
	}

	public static String getName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		return (String)_javascriptExecutor.executeScript(
			"getName('" + loggerElement.getID() + "');");
	}

	public static String getText(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		return (String)_javascriptExecutor.executeScript(
			"getText('" + loggerElement.getID() + "');");
	}

	public static boolean isLoggerStarted() {
		if (_webDriver != null) {
			return true;
		}

		return false;
	}

	public static boolean isWrittenToLogger(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return false;
		}

		try {
			return (boolean)_javascriptExecutor.executeScript(
				"isWrittenToLogger('" + loggerElement.getID() + "');");
		}
		catch (Exception e) {
			return false;
		}
	}

	public static void pauseLoggerCheck() throws Exception {
		if (!isLoggerStarted()) {
			return;
		}

		WebElement webElement = _webDriver.findElement(By.id("commandLog"));

		String classAttribute = webElement.getAttribute("class");

		while (classAttribute.contains("paused")) {
			webElement = _webDriver.findElement(By.id("commandLog"));

			classAttribute = webElement.getAttribute("class");

			Thread.sleep(1000);
		}
	}

	public static void setAttribute(
		LoggerElement loggerElement, String attributeName,
		String attributeValue) {

		if (!isLoggerStarted()) {
			return;
		}

		String escapedAttributeName = StringEscapeUtils.escapeEcmaScript(
			attributeName);
		String escapedAttributeValue = StringEscapeUtils.escapeEcmaScript(
			attributeValue);

		_javascriptExecutor.executeScript(
			"setAttribute('" + loggerElement.getID() + "', '" +
				escapedAttributeName + "', '" + escapedAttributeValue + "');");
	}

	public static void setClassName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		String className = StringEscapeUtils.escapeEcmaScript(
			loggerElement.getClassName());

		_javascriptExecutor.executeScript(
			"setClassName('" + loggerElement.getID() + "', '" + className +
				"');");
	}

	public static void setID(String oldID, String newID) {
		if (!isLoggerStarted()) {
			return;
		}

		String escapedOldID = StringEscapeUtils.escapeEcmaScript(oldID);
		String escapedNewID = StringEscapeUtils.escapeEcmaScript(newID);

		_javascriptExecutor.executeScript(
			"setID(" + escapedOldID + ", '" + escapedNewID + "');");
	}

	public static void setName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		String name = StringEscapeUtils.escapeEcmaScript(
			loggerElement.getName());

		_javascriptExecutor.executeScript(
			"setName('" + loggerElement.getID() + "', '" + name + "');");
	}

	public static void setText(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		String text = StringEscapeUtils.escapeEcmaScript(
			loggerElement.getText());

		_javascriptExecutor.executeScript(
			"setText('" + loggerElement.getID() + "', '" + text + "');");
	}

	public static void startLogger() throws Exception {
		if (isLoggerStarted() || !PropsValues.SELENIUM_LOGGER_ENABLED) {
			return;
		}

		_webDriver = new FirefoxDriver();

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1050, 45));
		window.setSize(new Dimension(850, 950));

		_javascriptExecutor = (JavascriptExecutor)_webDriver;

		String mainCSSContent = _readResource(
			"META-INF/resources/css/main.css");

		FileUtil.write(
			_CURRENT_DIR_NAME + "/test-results/css/main.css", mainCSSContent);

		String indexHTMLContent = _readResource(
			"META-INF/resources/html/index.html");

		indexHTMLContent = indexHTMLContent.replace(
			"<ul class=\"command-log\" data-logid=\"01\" id=\"commandLog\">" +
				"</ul>",
			CommandLoggerHandler.getCommandLogText());
		indexHTMLContent = indexHTMLContent.replace(
			"<ul class=\"xml-log-container\" id=\"xmlLogContainer\"></ul>",
			XMLLoggerHandler.getXMLLogText());

		FileUtil.write(_getHtmlFilePath(), indexHTMLContent);

		String componentJSContent = _readResource(
			"META-INF/resources/js/component.js");

		FileUtil.write(
			_CURRENT_DIR_NAME + "/test-results/js/component.js",
			componentJSContent);

		String mainJSContent = _readResource("META-INF/resources/js/main.js");

		FileUtil.write(
			_CURRENT_DIR_NAME + "/test-results/js/main.js", mainJSContent);

		_webDriver.get("file://" + _getHtmlFilePath());
	}

	public static void stopLogger() throws Exception {
		if (!PropsValues.SELENIUM_LOGGER_ENABLED) {
			String mainCSSContent = _readResource(
				"META-INF/resources/css/main.css");

			FileUtil.write(
				_CURRENT_DIR_NAME + "/test-results/css/main.css",
				mainCSSContent);

			String componentJSContent = _readResource(
				"META-INF/resources/js/component.js");

			FileUtil.write(
				_CURRENT_DIR_NAME + "/test-results/js/component.js",
				componentJSContent);

			String mainJSContent = _readResource(
				"META-INF/resources/js/main.js");

			FileUtil.write(
				_CURRENT_DIR_NAME + "/test-results/js/main.js", mainJSContent);
		}

		String indexHTMLContent = _readResource(
			"META-INF/resources/html/index.html");

		indexHTMLContent = indexHTMLContent.replace(
			"<ul class=\"command-log\" data-logid=\"01\" id=\"commandLog\">" +
				"</ul>",
			CommandLoggerHandler.getCommandLogText());
		indexHTMLContent = indexHTMLContent.replace(
			"<ul class=\"xml-log-container\" id=\"xmlLogContainer\"></ul>",
			XMLLoggerHandler.getXMLLogText());

		if (!PropsValues.TEST_RUN_LOCALLY) {
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<link href=\"../css/main.css\"",
				"<link href=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/css/.sass-cache/main.css\"");
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<script defer src=\"../js/component.js\"",
				"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/js/component.js\"");
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<script defer src=\"../js/main.js\"",
				"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/js/main.js\"");
		}

		FileUtil.write(_getHtmlFilePath(), indexHTMLContent);

		if (isLoggerStarted()) {
			_webDriver.quit();

			_webDriver = null;
		}
	}

	private static String _getHtmlFilePath() {
		StringBuilder sb = new StringBuilder();

		sb.append(_CURRENT_DIR_NAME);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(
				PoshiRunnerContext.getTestCaseCommandName(), "#", "_"));
		sb.append("/index.html");

		return sb.toString();
	}

	private static String _getSummaryLogFilePath() {
		StringBuilder sb = new StringBuilder();

		sb.append(_CURRENT_DIR_NAME);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(
				PoshiRunnerContext.getTestCaseCommandName(), "#", "_"));
		sb.append("/summary.html");

		return sb.toString();
	}

	private static String _readResource(String path) throws Exception {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader = LoggerUtil.class.getClassLoader();

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

	private static final String _CURRENT_DIR_NAME =
		PoshiRunnerGetterUtil.getCanonicalPath(".");

	private static JavascriptExecutor _javascriptExecutor;
	private static WebDriver _webDriver;

}