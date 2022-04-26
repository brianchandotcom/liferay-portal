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

package com.liferay.petra.log4j.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.WriterAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Hai Yu
 */
public class Log4jConfigUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConfigureLog4J() {
		String loggerName = StringUtil.randomString();

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		Map<String, String> priorities = Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ALL));

		Assert.assertEquals(
			priorities, Collections.singletonMap(loggerName, _ALL));

		_assertPriority(logger, _ALL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _DEBUG));

		_assertPriority(logger, _DEBUG);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		_assertPriority(logger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _FATAL));

		_assertPriority(logger, _FATAL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _INFO));

		_assertPriority(logger, _INFO);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _OFF));

		_assertPriority(logger, _OFF);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _TRACE));

		_assertPriority(logger, _TRACE);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, "FAKE_LEVEL"));

		_assertPriority(logger, _ERROR);
	}

	@Test
	public void testConfigureLog4JWithAppender() {
		String loggerName = StringUtil.randomString();

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		_assertAppenders(logger);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _CONSOLE));

		_assertAppenders(logger, _CONSOLE);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _NULL));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _CONSOLE));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, _CONSOLE, _NULL));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, _CONSOLE, _NULL),
			_NULL);

		_assertAppenders(logger, _CONSOLE, _NULL);
	}

	@Test
	public void testConfigureLog4JWithCompatibility() throws Exception {
		String loggerName = StringUtil.randomString();

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(loggerName, _DEBUG));

		_assertPriority(logger, _DEBUG);

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(loggerName, _ERROR));

		_assertPriority(logger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class));

		_assertAppenders(logger, ConsoleAppender.class.getName());

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(
				loggerName, _ERROR, WriterAppender.class));

		_assertAppenders(
			logger, ConsoleAppender.class.getName(),
			WriterAppender.class.getName());

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class,
				WriterAppender.class));

		_assertAppenders(
			logger, ConsoleAppender.class.getName(),
			WriterAppender.class.getName());

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class,
				WriterAppender.class),
			ConsoleAppender.class.getName());

		_assertAppenders(
			logger, ConsoleAppender.class.getName(),
			WriterAppender.class.getName());

		Log4jConfigUtil.configureLog4J(
			_generateLog4j1XMLConfigurationContent(
				loggerName, _ERROR,
				LinkedHashMapBuilder.put(
					"org.apache.log4j.rolling.RollingFileAppender", "TEXT_FILE"
				).put(
					WriterAppender.class.getName(), "WRITER_APPENDER"
				).build()));

		_assertAppenders(
			logger, ConsoleAppender.class.getName(),
			WriterAppender.class.getName(),
			"TEXT_FILE_" + Log4jConfigUtilTest.class.getSimpleName(),
			"WRITER_APPENDER");
	}

	@Test
	public void testConfigureLog4JWithException() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				Log4jConfigUtil.class.getName(), Level.SEVERE)) {

			Log4jConfigUtil.configureLog4J(null);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertNull(logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(NullPointerException.class, throwable.getClass());

			String xmlContent = _generateXMLConfigurationContent(
				StringUtil.randomString(), _INFO);

			xmlContent = StringUtil.removeSubstring(
				xmlContent, "strict=\"true\"");

			Log4jConfigUtil.configureLog4J(xmlContent);

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"<Configuration> strict attribute requires true",
				logEntry.getMessage());
		}
	}

	@Test
	public void testGetJDKLevel() {
		Assert.assertEquals(
			"FINE", String.valueOf(Log4jConfigUtil.getJDKLevel(_DEBUG)));
		Assert.assertEquals(
			"SEVERE", String.valueOf(Log4jConfigUtil.getJDKLevel(_ERROR)));
		Assert.assertEquals(
			"INFO", String.valueOf(Log4jConfigUtil.getJDKLevel(_INFO)));
		Assert.assertEquals(
			"WARNING", String.valueOf(Log4jConfigUtil.getJDKLevel(_WARN)));
	}

	@Test
	public void testGetPriorities() {
		String loggerName = StringUtil.randomString();

		Map<String, String> priorities = Log4jConfigUtil.getPriorities();

		Assert.assertFalse(priorities.containsKey(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertEquals(
			"The priority should be WARN by configuration", _WARN,
			priorities.get(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, null));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertEquals(
			"The level should use its parent level(root logger level is Error)",
			_ERROR, priorities.get(loggerName));
	}

	@Test
	public void testMisc() {
		new Log4jConfigUtil();
	}

	@Test
	public void testSetLevel() {
		String loggerName = StringUtil.randomString();

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		_assertPriority(logger, _ERROR);

		String childLoggerName = loggerName + ".child";

		Logger childLogger = (Logger)LogManager.getLogger(childLoggerName);

		_assertPriority(childLogger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);
		_assertPriority(childLogger, _WARN);

		Log4jConfigUtil.setLevel(loggerName, _DEBUG);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _DEBUG);

		Log4jConfigUtil.setLevel(childLoggerName, _ERROR);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _ERROR);
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testShutdownLog4J() {
		PropsTestUtil.setProps(Collections.emptyMap());

		Logger logger = (Logger)LogManager.getRootLogger();

		Map<String, Appender> appenders = logger.getAppenders();

		Assert.assertTrue(
			"The root logger should include appenders", !appenders.isEmpty());

		Log4jConfigUtil.shutdownLog4J();

		Assert.assertFalse(
			"The root logger should not own appenders after shutting down",
			appenders.isEmpty());
	}

	private void _assertAppenders(Logger logger, String... appenderTypes) {
		Map<String, Appender> appenders = logger.getAppenders();

		List<String> targetAppenderNames = new ArrayList<>();

		for (String appenderName : appenders.keySet()) {
			targetAppenderNames.add(appenderName);
		}

		Assert.assertEquals(targetAppenderNames.size(), appenderTypes.length);

		for (String appenderType : appenderTypes) {
			Assert.assertTrue(
				"Missing appender " + appenderType,
				targetAppenderNames.contains(appenderType));
		}
	}

	private void _assertPriority(Logger logger, String priority) {
		if (priority.equals(_ALL)) {
			Assert.assertTrue(
				"TRACE should be enabled if logging priority is ALL",
				logger.isTraceEnabled());

			return;
		}

		if (logger.isTraceEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _TRACE);
		}
		else if (logger.isDebugEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _DEBUG);
		}
		else if (logger.isInfoEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _INFO);
		}
		else if (logger.isWarnEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _WARN);
		}
		else if (logger.isErrorEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _ERROR);
		}
		else if (logger.isFatalEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _FATAL);
		}
		else {
			Assert.assertEquals("Logging priority is wrong", priority, _OFF);
		}
	}

	private String _generateLog4j1XMLConfigurationContent(
			String loggerName, String priority, Class<?>... appenderTypes)
		throws Exception {

		Map<String, String> appenders = new LinkedHashMap<>();

		for (Class<?> appenderType : appenderTypes) {
			appenders.put(appenderType.getName(), appenderType.getName());
		}

		return _generateLog4j1XMLConfigurationContent(
			loggerName, priority, appenders);
	}

	private String _generateLog4j1XMLConfigurationContent(
			String loggerName, String priority, Map<String, String> appenders)
		throws Exception {

		StringBundler sb = new StringBundler(10 + (appenders.size() * 17));

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");

		for (Map.Entry<String, String> appenderEntry : appenders.entrySet()) {
			sb.append("<appender class=\"");
			sb.append(appenderEntry.getKey());
			sb.append("\" name=\"");
			sb.append(appenderEntry.getValue());
			sb.append("\">");

			if (Objects.equals(
					appenderEntry.getKey(),
					"org.apache.log4j.rolling.RollingFileAppender")) {

				Path tempDirPath = Files.createTempDirectory(
					Log4jConfigUtilTest.class.getName());

				File tempDir = tempDirPath.toFile();

				tempDir.mkdirs();
				tempDir.deleteOnExit();

				sb.append("<rollingPolicy class=\"");
				sb.append("org.apache.log4j.rolling.TimeBasedRollingPolicy\">");
				sb.append("<param name=\"FileNamePattern\" ");
				sb.append("value=\"");
				sb.append(tempDir);
				sb.append("/");
				sb.append(Log4jConfigUtilTest.class.getSimpleName());
				sb.append(".%d{yyyy-MM-dd}.log\" />");
				sb.append("<param name=\"paramName\" value=\"paramValue\" />");
				sb.append("\t\t</rollingPolicy>");
			}

			sb.append("</appender>");
		}

		sb.append("<category name=\"");
		sb.append(loggerName);
		sb.append("\"><priority value=\"");
		sb.append(priority);
		sb.append("\" />");

		for (String appenderName : appenders.values()) {
			sb.append("<appender-ref ref=\"");
			sb.append(appenderName);
			sb.append("\" />");
		}

		sb.append("</category></log4j:configuration>");

		return sb.toString();
	}

	private String _generateXMLConfigurationContent(
		String loggerName, String priority, String... appenderTypes) {

		int initialCapacity =
			(appenderTypes.length == 0) ? 7 : (9 + (8 * appenderTypes.length));

		StringBundler sb = new StringBundler(initialCapacity);

		sb.append("<?xml version=\"1.0\"?><Configuration strict=\"true\">");

		if (appenderTypes.length > 0) {
			sb.append("<Appenders>");

			for (String appenderType : appenderTypes) {
				sb.append("<Appender name=\"");
				sb.append(appenderType);
				sb.append("\" type=\"");
				sb.append(appenderType);
				sb.append("\"></Appender>");
			}

			sb.append("</Appenders>");
		}

		sb.append("<Loggers><Logger level= \"");
		sb.append(priority);
		sb.append("\" name=\"");
		sb.append(loggerName);
		sb.append("\">");

		for (String appenderType : appenderTypes) {
			sb.append("<AppenderRef ref=\"");
			sb.append(appenderType);
			sb.append("\" />");
		}

		sb.append("</Logger></Loggers></Configuration>");

		return sb.toString();
	}

	private static final String _ALL = "ALL";

	private static final String _CONSOLE = "CONSOLE";

	private static final String _DEBUG = "DEBUG";

	private static final String _ERROR = "ERROR";

	private static final String _FATAL = "FATAL";

	private static final String _INFO = "INFO";

	private static final String _NULL = "NULL";

	private static final String _OFF = "OFF";

	private static final String _TRACE = "TRACE";

	private static final String _WARN = "WARN";

}