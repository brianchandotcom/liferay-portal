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
import com.liferay.poshi.runner.elements.PoshiElement;
import com.liferay.poshi.runner.exception.PoshiRunnerLoggerException;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.List;

import org.dom4j.Element;

/**
 * @author Leslie Wong
 */
public class PoshiLogger {

	public PoshiLogger(String testNamespacedClassCommandName) throws Exception {
		_commandLogger = new CommandLogger();

		_syntaxLogger = _getSyntaxLogger(testNamespacedClassCommandName);
	}

	public void createPoshiReport() throws IOException {
		ClassLoader classLoader = PoshiLogger.class.getClassLoader();

		URL url = classLoader.getResource("META-INF/resources/html/index.html");

		String indexHTMLContent = FileUtil.read(url);

		indexHTMLContent = StringUtil.replace(
			indexHTMLContent,
			"<ul class=\"command-log\" data-logid=\"01\" " +
				"id=\"commandLog\"></ul>",
			_commandLogger.getCommandLogText());
		indexHTMLContent = StringUtil.replace(
			indexHTMLContent,
			"<ul class=\"syntax-log-container\" id=\"syntaxLogContainer\"" +
				"></ul>",
			_syntaxLogger.getSyntaxLogText());

		String currentDirName = FileUtil.getCanonicalPath(".");

		if (PropsValues.TEST_RUN_LOCALLY) {
			FileUtil.copyFileFromResource(
				"META-INF/resources/css/main.css",
				currentDirName + "/test-results/css/main.css");
			FileUtil.copyFileFromResource(
				"META-INF/resources/js/component.js",
				currentDirName + "/test-results/js/component.js");
			FileUtil.copyFileFromResource(
				"META-INF/resources/js/main.js",
				currentDirName + "/test-results/js/main.js");
		}
		else {
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<link href=\"../css/main.css\"",
				"<link href=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/css/main.css\"");
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<script defer src=\"../js/component.js\"",
				"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/js/component.js\"");
			indexHTMLContent = StringUtil.replace(
				indexHTMLContent, "<script defer src=\"../js/main.js\"",
				"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
					"/js/main.js\"");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(currentDirName);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(
				PoshiRunnerContext.getTestCaseNamespacedClassCommandName(), "#",
				"_"));
		sb.append("/index.html");

		FileUtil.write(sb.toString(), indexHTMLContent);
	}

	public void failCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.failCommand(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "fail");
	}

	public int getDetailsLinkId() {
		return _commandLogger.getDetailsLinkId();
	}

	public void logExternalMethodCommand(
			Element element, List<String> arguments, Object returnValue)
		throws Exception {

		_commandLogger.logExternalMethodCommand(
			element, arguments, returnValue, _syntaxLogger);
	}

	public void logMessage(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.logMessage(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pass");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void logNamespacedClassCommandName(
		String namespacedClassCommandName) {

		_commandLogger.logNamespacedClassCommandName(
			namespacedClassCommandName);
	}

	public void logSeleniumCommand(Element element, List<String> arguments)
		throws PoshiRunnerLoggerException {

		_commandLogger.logSeleniumCommand(element, arguments);
	}

	public void passCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.passCommand(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pass");
	}

	public void startCommand(Element element)
		throws PoshiRunnerLoggerException {

		_commandLogger.startCommand(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pending");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void takeScreenshotCommand(Element element)
		throws PoshiRunnerLoggerException {

		_commandLogger.takeScreenshotCommand(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "screenshot");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void updateStatus(Element element, String status) {
		_syntaxLogger.updateStatus(element, status);
	}

	public void warnCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.warnCommand(element, _syntaxLogger);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "warning");
	}

	private SyntaxLogger _getSyntaxLogger(String namespacedClassCommandName)
		throws Exception {

		String namespace =
			PoshiRunnerGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String classCommandName =
			PoshiRunnerGetterUtil.
				getClassCommandNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

		Element commandElement = PoshiRunnerContext.getTestCaseCommandElement(
			classCommandName, namespace);

		if (commandElement instanceof PoshiElement) {
			return new PoshiScriptSyntaxLogger(namespacedClassCommandName);
		}

		return new XMLSyntaxLogger(namespacedClassCommandName);
	}

	private LoggerElement _getSyntaxLoggerElement() {
		return _syntaxLogger.getSyntaxLoggerElement(
			PoshiRunnerStackTraceUtil.getSimpleStackTrace());
	}

	private void _linkLoggerElements(
		LoggerElement lineGroupLoggerElement,
		LoggerElement scriptLoggerElement) {

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

	private final CommandLogger _commandLogger;
	private int _functionLinkId;
	private final SyntaxLogger _syntaxLogger;

}