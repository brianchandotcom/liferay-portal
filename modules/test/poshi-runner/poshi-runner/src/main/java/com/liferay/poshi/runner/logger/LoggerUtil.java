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
import com.liferay.poshi.runner.exception.PoshiRunnerLoggerException;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Michael Hashimoto
 */
public final class LoggerUtil {

	public static void createSummary() throws PoshiRunnerLoggerException {
		try {
			FileUtil.write(
				_getSummaryLogFilePath(), SummaryLoggerHandler.getSummary());
		}
		catch (Throwable t) {
			throw new PoshiRunnerLoggerException(t.getMessage(), t);
		}
	}

	public static void startLogger() throws Exception {
		CommandLoggerHandler.startRunning();

		SummaryLoggerHandler.startRunning();
	}

	public static void stopLogger() throws PoshiRunnerLoggerException {
		try {
			CommandLoggerHandler.stopRunning();

			SummaryLoggerHandler.stopRunning();

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
					_CURRENT_DIR_NAME + "/test-results/js/main.js",
					mainJSContent);
			}

			String indexHTMLContent = _readResource(
				"META-INF/resources/html/index.html");

			indexHTMLContent = indexHTMLContent.replace(
				"<ul class=\"command-log\" data-logid=\"01\" " +
					"id=\"commandLog\"></ul>",
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
					indexHTMLContent,
					"<script defer src=\"../js/component.js\"",
					"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
						"/js/component.js\"");
				indexHTMLContent = StringUtil.replace(
					indexHTMLContent, "<script defer src=\"../js/main.js\"",
					"<script defer src=\"" + PropsValues.LOGGER_RESOURCES_URL +
						"/js/main.js\"");
			}

			FileUtil.write(_getHtmlFilePath(), indexHTMLContent);
		}
		catch (Throwable t) {
			throw new PoshiRunnerLoggerException(t.getMessage(), t);
		}
	}

	private static String _getHtmlFilePath() {
		StringBuilder sb = new StringBuilder();

		sb.append(_CURRENT_DIR_NAME);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(
				PoshiRunnerContext.getTestCaseNamespacedClassCommandName(), "#",
				"_"));
		sb.append("/index.html");

		return sb.toString();
	}

	private static String _getSummaryLogFilePath() {
		StringBuilder sb = new StringBuilder();

		sb.append(_CURRENT_DIR_NAME);
		sb.append("/test-results/");
		sb.append(
			StringUtil.replace(
				PoshiRunnerContext.getTestCaseNamespacedClassCommandName(), "#",
				"_"));
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

}