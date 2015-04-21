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

import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.Validator;

import java.net.URL;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
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

		StringBuilder sb = new StringBuilder();

		sb.append("var parentNode = document.getElementById('");
		sb.append(parentLoggerElement.getID());
		sb.append("');");

		sb.append("var childNode = document.createElement('");
		sb.append(childLoggerElement.getName());
		sb.append("');");

		if (Validator.isNotNull(childLoggerElement.getClassName())) {
			sb.append("childNode.setAttribute('class', '");
			sb.append(
				StringEscapeUtils.escapeEcmaScript(
					childLoggerElement.getClassName()));
			sb.append("');");
		}

		if (Validator.isNotNull(childLoggerElement.getText())) {
			sb.append("childNode.innerHTML = '");
			sb.append(
				StringEscapeUtils.escapeEcmaScript(
					childLoggerElement.getText()));
			sb.append("';");
		}

		List<String> attributeNames = childLoggerElement.getAttributeNames();

		if (!attributeNames.isEmpty()) {
			for (String attributeName : attributeNames) {
				sb.append("childNode.setAttribute('");
				sb.append(StringEscapeUtils.escapeEcmaScript(attributeName));
				sb.append("', '");
				sb.append(
					StringEscapeUtils.escapeEcmaScript(
						childLoggerElement.getAttributeValue(attributeName)));
				sb.append("');");
			}
		}

		sb.append("childNode.setAttribute('id', '");
		sb.append(
			StringEscapeUtils.escapeEcmaScript(childLoggerElement.getID()));
		sb.append("');");

		sb.append("parentNode.appendChild(childNode);");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void executeJavascript(String script) {
		_javascriptExecutor.executeScript(
			StringEscapeUtils.escapeEcmaScript(script));
	}

	public static String getClassName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("return node.getAttribute('class');");

		return (String)_javascriptExecutor.executeScript(sb.toString());
	}

	public static String getName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("return node.nodeName;");

		return (String)_javascriptExecutor.executeScript(sb.toString());
	}

	public static String getText(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("return node.innerHTML;");

		return (String)_javascriptExecutor.executeScript(sb.toString());
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

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("if (node == null) {");
		sb.append("return false;");
		sb.append("}");

		sb.append("return true;");

		return (boolean)_javascriptExecutor.executeScript(sb.toString());
	}

	public static void setAttribute(
		LoggerElement loggerElement, String attributeName,
		String attributeValue) {

		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("node.setAttribute('");
		sb.append(StringEscapeUtils.escapeEcmaScript(attributeName));
		sb.append("', '");
		sb.append(StringEscapeUtils.escapeEcmaScript(attributeValue));
		sb.append("');");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void setClassName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("node.setAttribute('class', '");
		sb.append(
			StringEscapeUtils.escapeEcmaScript(loggerElement.getClassName()));
		sb.append("');");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void setID(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("node.setAttribute('id', '");
		sb.append(StringEscapeUtils.escapeEcmaScript(loggerElement.getID()));
		sb.append("');");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void setName(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var oldNode = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("var newNode = document.createElement('");
		sb.append(StringEscapeUtils.escapeEcmaScript(loggerElement.getName()));
		sb.append("');");

		sb.append("newNode.innerHTML = oldNode.innerHTML;");
		sb.append(
			"newNode.setAttribute('class', oldNode.getAttribute('class'));");
		sb.append("newNode.setAttribute('id', oldNode.getAttribute('id'));");

		sb.append(
			"oldNode.parentNode.insertBefore(newNode, oldNode.nextSibling);");

		sb.append("var parentNode = oldNode.parentNode;");

		sb.append("parentNode.removeChild(oldNode);");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void setText(LoggerElement loggerElement) {
		if (!isLoggerStarted()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var node = document.getElementById('");
		sb.append(loggerElement.getID());
		sb.append("');");

		sb.append("node.innerHTML = '");
		sb.append(StringEscapeUtils.escapeEcmaScript(loggerElement.getText()));
		sb.append("';");

		_javascriptExecutor.executeScript(sb.toString());
	}

	public static void startLogger() throws Exception {
		if (isLoggerStarted()) {
			return;
		}

		_webDriver = new FirefoxDriver();

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1050, 45));
		window.setSize(new Dimension(850, 950));

		_javascriptExecutor = (JavascriptExecutor)_webDriver;

		_webDriver.get("file://" + _getResourcesDir() + "html/index.html");
	}

	public static void stopLogger() throws Exception {
		FileUtil.copyDirectory(
			_getResourcesDir() + "css", _CURRENT_DIR + "/test-results/css");

		String content = (String)_javascriptExecutor.executeScript(
			"return document.getElementsByTagName('html')[0].outerHTML;");

		FileUtil.write(_CURRENT_DIR + "/test-results/html/index.html", content);

		if (isLoggerStarted()) {
			_webDriver.quit();

			_webDriver = null;
		}
	}

	private static String _getResourcesDir() {
		LoggerUtil loggerUtil = new LoggerUtil();

		Class<?> clazz = loggerUtil.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource("META-INF/resources/");

		return url.getPath();
	}

	private static final String _CURRENT_DIR =
		PoshiRunnerGetterUtil.getCanonicalPath(".");

	private static final LoggerUtil _instance = new LoggerUtil();

	private static JavascriptExecutor _javascriptExecutor;
	private static WebDriver _webDriver;

}