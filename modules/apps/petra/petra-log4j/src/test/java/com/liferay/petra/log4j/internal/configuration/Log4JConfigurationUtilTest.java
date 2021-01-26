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

package com.liferay.petra.log4j.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.util.PropsImpl;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Hai Yu
 */
public class Log4JConfigurationUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws Exception {
		PropsUtil.setProps(new PropsImpl());

		LogFactoryUtil.setLogFactory(new Log4jLogFactoryImpl());
	}

	@Test
	public void testConfigureLog4JXmlAppenderExist() {

		// Assert no appender exist

		String loggerName = StringUtil.randomString();

		String xml = _getLoggerConfigurationWithAppender(loggerName, _ERROR);

		xml = StringUtil.removeSubstring(xml, _APPENDER_REF_PLACEHOLDER);

		Log4JConfigurationUtil.configureLog4JXml(xml);

		Logger logger = Logger.getLogger(loggerName);

		Enumeration<Appender> enumeration = logger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Assert.fail("The logger should not own any Appenders");
		}

		// Assert one appender exist

		xml = _getLoggerConfigurationWithAppender(loggerName, _ERROR);

		xml = StringUtil.replace(
			xml, _APPENDER_REF_PLACEHOLDER,
			"<appender-ref ref=\"" + _CONSOLE_APPENDER_NAME + "\" />");

		Log4JConfigurationUtil.configureLog4JXml(xml);

		enumeration = logger.getAllAppenders();

		int count = 0;

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			Assert.assertTrue(appender instanceof ConsoleAppender);

			ConsoleAppender consoleAppender = (ConsoleAppender)appender;

			Assert.assertEquals(
				"Expected console appender name is " + _CONSOLE_APPENDER_NAME,
				_CONSOLE_APPENDER_NAME, consoleAppender.getName());

			count++;
		}

		Assert.assertTrue(count == 1);

		// Assert override the previous appender

		count = 0;

		xml = _getLoggerConfigurationWithAppender(loggerName, _ERROR);

		xml = StringUtil.replace(
			xml, _APPENDER_REF_PLACEHOLDER,
			"<appender-ref ref=\"" + _FILE_APPENDER_NAME + "\" />");

		Log4JConfigurationUtil.configureLog4JXml(xml);

		enumeration = logger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			Assert.assertTrue(appender instanceof FileAppender);

			FileAppender fileAppender = (FileAppender)appender;

			Assert.assertEquals(
				"Expected file appender name is " + _FILE_APPENDER_NAME,
				_FILE_APPENDER_NAME, fileAppender.getName());

			count++;
		}

		Assert.assertTrue(count == 1);

		count = 0;

		// Assert two appenders

		xml = _getLoggerConfigurationWithAppender(loggerName, _WARN);

		xml = StringUtil.replace(
			xml, _APPENDER_REF_PLACEHOLDER,
			"<appender-ref ref=\"" + _CONSOLE_APPENDER_NAME + "\" />" +
				"<appender-ref ref=\"" + _FILE_APPENDER_NAME + "\" />");

		Log4JConfigurationUtil.configureLog4JXml(xml);

		enumeration = logger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			if (appender instanceof FileAppender) {
				FileAppender fileAppender = (FileAppender)appender;

				Assert.assertEquals(
					"Expected console appender name is " + _FILE_APPENDER_NAME,
					_FILE_APPENDER_NAME, fileAppender.getName());
			}
			else if (appender instanceof ConsoleAppender) {
				ConsoleAppender consoleAppender = (ConsoleAppender)appender;

				Assert.assertEquals(
					"Expected console appender name is " +
						_CONSOLE_APPENDER_NAME,
					_CONSOLE_APPENDER_NAME, consoleAppender.getName());
			}
			else {
				Assert.fail("The logger attached other type Appender");
			}

			count++;
		}

		Assert.assertTrue(count == 2);
	}

	@Test
	public void testConfigureLog4JXmlLogEnable() {

		// Aseert log level

		String loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _ALL));

		_assertLog4JLevel(_ALL, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _OFF));

		_assertLog4JLevel(_OFF, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _FATAL));

		_assertLog4JLevel(_FATAL, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _ERROR));

		_assertLog4JLevel(_ERROR, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _WARN));

		_assertLog4JLevel(_WARN, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _INFO));

		_assertLog4JLevel(_INFO, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _DEBUG));

		_assertLog4JLevel(_DEBUG, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _TRACE));

		_assertLog4JLevel(_TRACE, loggerName);

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, "FAKE_LEVEL"));

		_assertLog4JLevel(_DEBUG, loggerName);

		// Assert override log level

		loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _WARN));

		_assertLog4JLevel(_WARN, loggerName);

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _INFO));

		_assertLog4JLevel(_INFO, loggerName);
	}

	@Test
	public void testConstructor() {
		new Log4JConfigurationUtil();
	}

	@Test
	public void testGetOriginalLevel() {
		String loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _ERROR));

		Assert.assertEquals(
			"The original level should be WARN by configuration", "ERROR",
			Log4JConfigurationUtil.getOriginalLevel(loggerName));

		Assert.assertEquals(
			"The original level should be ALL for Logger not configured or " +
				"created",
			"ALL",
			Log4JConfigurationUtil.getOriginalLevel(StringUtil.randomString()));
	}

	@Test
	public void testSetLevel() {
		String loggerName = StringUtil.randomString();

		Log4JConfigurationUtil.configureLog4JXml(
			_getLoggerConfiguration(loggerName, _WARN));

		_assertLog4JLevel(_WARN, loggerName);

		Log4JConfigurationUtil.setLevel(loggerName, _DEBUG);

		_assertLog4JLevel(_DEBUG, loggerName);

		String childLoggerName = "com.test.parent.child";

		_assertLog4JLevel(_INFO, childLoggerName);

		Log4JConfigurationUtil.setLevel("com.test.parent", _DEBUG);

		_assertLog4JLevel(_DEBUG, childLoggerName);
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testShutdownLog4J() {
		Logger logger = Logger.getRootLogger();

		Enumeration<Appender> appendersEnumeration = logger.getAllAppenders();

		Assert.assertTrue(
			"The root logger should include appenders",
			appendersEnumeration.hasMoreElements());

		Log4JConfigurationUtil.shutdownLog4J();

		Assert.assertFalse(
			"The root logger should not own appenders after shutting down",
			appendersEnumeration.hasMoreElements());
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	private void _assertLog4JLevel(String expectedLevel, String loggerName) {
		Log log = LogFactoryUtil.getLog(loggerName);

		if (expectedLevel.equals("ALL")) {
			Assert.assertTrue(
				"TRACE should be enabled if logging level is ALL",
				log.isTraceEnabled());

			return;
		}

		String actualLevel = null;

		if (log.isTraceEnabled()) {
			actualLevel = "TRACE";
		}
		else if (log.isDebugEnabled()) {
			actualLevel = "DEBUG";
		}
		else if (log.isInfoEnabled()) {
			actualLevel = "INFO";
		}
		else if (log.isWarnEnabled()) {
			actualLevel = "WARN";
		}
		else if (log.isErrorEnabled()) {
			actualLevel = "ERROR";
		}
		else if (log.isFatalEnabled()) {
			actualLevel = "FATAL";
		}
		else {
			actualLevel = "OFF";
		}

		Assert.assertEquals(
			"Logging level is wrong", expectedLevel, actualLevel);
	}

	private String _getLoggerConfiguration(String name, String level) {
		StringBundler sb = new StringBundler(9);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");
		sb.append("<category name=\"");
		sb.append(name);
		sb.append("\"><priority value=\"");
		sb.append(level);
		sb.append("\" /></category></log4j:configuration>");

		return sb.toString();
	}

	private String _getLoggerConfigurationWithAppender(
		String name, String level) {

		StringBundler sb = new StringBundler(21);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");
		sb.append("<appender class=\"");
		sb.append(ConsoleAppender.class.getName());
		sb.append("\" name=\"");
		sb.append(_CONSOLE_APPENDER_NAME);
		sb.append("\"></appender>");
		sb.append("<appender class=\"");
		sb.append(FileAppender.class.getName());
		sb.append("\" name=\"");
		sb.append(_FILE_APPENDER_NAME);
		sb.append("\"></appender>");
		sb.append("<category name=\"");
		sb.append(name);
		sb.append("\"><priority value=\"");
		sb.append(level);
		sb.append("\" />");
		sb.append(_APPENDER_REF_PLACEHOLDER);
		sb.append("</category></log4j:configuration>");

		return sb.toString();
	}

	private static final String _ALL = "ALL";

	private static final String _APPENDER_REF_PLACEHOLDER = "%appender-ref%";

	private static final String _CONSOLE_APPENDER_NAME = "TEST-CONSOLE";

	private static final String _DEBUG = "DEBUG";

	private static final String _ERROR = "ERROR";

	private static final String _FATAL = "FATAL";

	private static final String _FILE_APPENDER_NAME = "TEST-FILE";

	private static final String _INFO = "INFO";

	private static final String _OFF = "OFF";

	private static final String _TRACE = "TRACE";

	private static final String _WARN = "WARN";

}