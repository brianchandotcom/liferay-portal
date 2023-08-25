/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.PoshiStackTrace;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.runner.exception.PoshiRunnerLoggerException;

import java.io.IOException;

import java.net.URL;

import java.util.List;

import org.dom4j.Element;

/**
 * @author Leslie Wong
 */
public class PoshiLogger {

	public PoshiLogger(String testNamespacedClassCommandName) throws Exception {
		_commandLogger = new CommandLogger(testNamespacedClassCommandName);

		_syntaxLogger = _getSyntaxLogger(testNamespacedClassCommandName);

		_testNamespacedClassCommandName = testNamespacedClassCommandName;

		_poshiProperties = PoshiProperties.getPoshiProperties();

		_poshiStackTrace = PoshiStackTrace.getPoshiStackTrace(
			testNamespacedClassCommandName);
	}

	public void createPoshiReport() throws IOException {
		String indexHTMLContent = null;

		String currentDirName = FileUtil.getCanonicalPath(".");

		try {
			ClassLoader classLoader = PoshiLogger.class.getClassLoader();

			URL url = classLoader.getResource(
				"META-INF/resources/logger/html/index.html");

			indexHTMLContent = FileUtil.read(url);

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

			if (_poshiProperties.testRunLocally) {
				FileUtil.copyFileFromResource(
					"META-INF/resources/logger/css/main.css",
					currentDirName + "/test-results/css/main.css");
				FileUtil.copyFileFromResource(
					"META-INF/resources/logger/js/component.js",
					currentDirName + "/test-results/js/component.js");
				FileUtil.copyFileFromResource(
					"META-INF/resources/logger/js/main.js",
					currentDirName + "/test-results/js/main.js");
				FileUtil.copyFileFromResource(
					"META-INF/resources/logger/js/update_images.js",
					currentDirName + "/test-results/js/update_images.js");
			}
			else {
				indexHTMLContent = StringUtil.replace(
					indexHTMLContent, "<link href=\"../css/main.css\"",
					"<link href=\"" + _poshiProperties.loggerResourcesURL +
						"/css/main.css\"");
				indexHTMLContent = StringUtil.replace(
					indexHTMLContent,
					"<script defer src=\"../js/component.js\"",
					"<script defer src=\"" +
						_poshiProperties.loggerResourcesURL +
							"/js/component.js\"");
				indexHTMLContent = StringUtil.replace(
					indexHTMLContent, "<script defer src=\"../js/main.js\"",
					"<script defer src=\"" +
						_poshiProperties.loggerResourcesURL + "/js/main.js\"");
				indexHTMLContent = StringUtil.replace(
					indexHTMLContent,
					"<script defer src=\"../js/update_images.js\"",
					"<script defer src=\"" +
						_poshiProperties.loggerResourcesURL +
							"/js/update_images.js\"");
			}
		}
		catch (OutOfMemoryError outOfMemoryError) {
			System.out.println(
				"Unable to create Poshi syntax logger. See POSHI-378 for " +
					"details. Use the summary.html log instead.");

			String summaryHTMLFileName = "summary.html";

			if (System.getenv("JENKINS_HOME") != null) {
				summaryHTMLFileName = summaryHTMLFileName + ".gz";
			}

			Element element = Dom4JUtil.getNewElement(
				"html", null,
				Dom4JUtil.getNewElement(
					"body", null, "Unable to create Poshi syntax logger. See ",
					Dom4JUtil.getNewAnchorElement(
						"https://issues.liferay.com/browse/POSHI-378",
						"POSHI-378"),
					" details. Use the ",
					Dom4JUtil.getNewAnchorElement(
						summaryHTMLFileName, "Summary Log"),
					" instead."));

			indexHTMLContent = Dom4JUtil.format(element);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(currentDirName);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(_testNamespacedClassCommandName, "#", "_"));
		sb.append("/index.html");

		FileUtil.write(sb.toString(), indexHTMLContent);
	}

	public void failCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.failCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "fail");
	}

	public int getDetailsLinkId() {
		return _commandLogger.getDetailsLinkId();
	}

	public String getTestNamespacedClassCommandName() {
		return _testNamespacedClassCommandName;
	}

	public void logMessage(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.logMessage(element);

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

	public void ocularCommand(Element element)
		throws PoshiRunnerLoggerException {

		_commandLogger.ocularCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "fail");
	}

	public void passCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.passCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pass");
	}

	public void startCommand(Element element)
		throws PoshiRunnerLoggerException {

		_commandLogger.startCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pending");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void startExternalMethodCommand(
			Element element, List<String> arguments, Object returnValue)
		throws Exception {

		_commandLogger.startExternalMethodCommand(
			element, arguments, returnValue);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "pending");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void takeScreenshotCommand(Element element)
		throws PoshiRunnerLoggerException {

		_commandLogger.takeScreenshotCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "screenshot");

		_linkLoggerElements(
			syntaxLoggerElement, _commandLogger.lineGroupLoggerElement);
	}

	public void updateStatus(Element element, String status) {
		_syntaxLogger.updateStatus(element, status);
	}

	public void warnCommand(Element element) throws PoshiRunnerLoggerException {
		_commandLogger.warnCommand(element);

		LoggerElement syntaxLoggerElement = _getSyntaxLoggerElement();

		syntaxLoggerElement.setAttribute("data-status01", "warning");
	}

	private SyntaxLogger _getSyntaxLogger(String namespacedClassCommandName)
		throws Exception {

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element commandElement = PoshiContext.getTestCaseCommandElement(
			classCommandName, namespace);

		if (commandElement instanceof PoshiElement) {
			PoshiElement poshiCommandElement = (PoshiElement)commandElement;

			if (!poshiCommandElement.isPoshiProse()) {
				return new PoshiScriptSyntaxLogger(namespacedClassCommandName);
			}
		}

		return new XMLSyntaxLogger(namespacedClassCommandName);
	}

	private LoggerElement _getSyntaxLoggerElement() {
		return _syntaxLogger.getSyntaxLoggerElement(
			_poshiStackTrace.getSimpleStackTraceMessage());
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
	private final PoshiProperties _poshiProperties;
	private final PoshiStackTrace _poshiStackTrace;
	private final SyntaxLogger _syntaxLogger;
	private final String _testNamespacedClassCommandName;

}