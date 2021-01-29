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
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Hai Yu
 */
public class Log4JConfiguratorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConfigureLog4JXmlAppender() {

		// Assert no appender exist

		String loggerName = StringUtil.randomString();

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		Logger logger = Logger.getLogger(loggerName);

		_assertAppenders(logger);

		// Assert one appender exist

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class));

		_assertAppenders(logger, ConsoleAppender.class);

		// Assert override the previous appender

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, FileAppender.class));

		_assertAppenders(logger, FileAppender.class);

		// Assert two appenders

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, ConsoleAppender.class, FileAppender.class));

		_assertAppenders(logger, ConsoleAppender.class, FileAppender.class);
	}

	@Test
	public void testConfigureLog4JXmlLogLevel() {
		String loggerName = StringUtil.randomString();

		Logger logger = Logger.getLogger(loggerName);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _ALL));

		_assertLog4JLevel(logger, _ALL);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _DEBUG));

		_assertLog4JLevel(logger, _DEBUG);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		_assertLog4JLevel(logger, _ERROR);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _FATAL));

		_assertLog4JLevel(logger, _FATAL);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _INFO));

		_assertLog4JLevel(logger, _INFO);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _OFF));

		_assertLog4JLevel(logger, _OFF);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _TRACE));

		_assertLog4JLevel(logger, _TRACE);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertLog4JLevel(logger, _WARN);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, "FAKE_LEVEL"));

		_assertLog4JLevel(logger, _DEBUG);
	}

	@Test
	public void testConstructor() {
		new Log4JConfigurator();
	}

	@Test
	public void testGetOriginalLevel() {
		String loggerName = StringUtil.randomString();

		Assert.assertEquals(
			"The original level should be ALL for Logger not configured or " +
				"created",
			_ALL, Log4JConfigurator.getOriginalPriority(loggerName));

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		Assert.assertEquals(
			"The original level should be WARN by configuration", _ERROR,
			Log4JConfigurator.getOriginalPriority(loggerName));
	}

	@Test
	public void testSetLevel() {
		String loggerName = StringUtil.randomString();

		String childLoggerName = loggerName + ".child";

		Logger logger = Logger.getLogger(loggerName);

		Logger childLogger = Logger.getLogger(childLoggerName);

		_assertLog4JLevel(logger, _INFO);

		_assertLog4JLevel(childLogger, _INFO);

		Log4JConfigurator.configureLog4JXml(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertLog4JLevel(logger, _WARN);

		_assertLog4JLevel(childLogger, _WARN);

		Log4JConfigurator.setLevel(loggerName, _DEBUG);

		_assertLog4JLevel(logger, _DEBUG);

		_assertLog4JLevel(childLogger, _DEBUG);
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testShutdownLog4J() {
		Logger logger = Logger.getRootLogger();

		Enumeration<Appender> appendersEnumeration = logger.getAllAppenders();

		Assert.assertTrue(
			"The root logger should include appenders",
			appendersEnumeration.hasMoreElements());

		Log4JConfigurator.shutdownLog4J();

		Assert.assertFalse(
			"The root logger should not own appenders after shutting down",
			appendersEnumeration.hasMoreElements());
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	private void _assertAppenders(Logger logger, Class<?>... appenderTypes) {
		Enumeration<Appender> enumeration = logger.getAllAppenders();

		List<String> targetAppenderNames = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			targetAppenderNames.add(appender.getName());
		}

		Assert.assertEquals(targetAppenderNames.size(), appenderTypes.length);

		for (Class<?> appenderType : appenderTypes) {
			Assert.assertTrue(
				"Missing appender " + appenderType.getName(),
				targetAppenderNames.contains(appenderType.getName()));
		}
	}

	private void _assertLog4JLevel(Logger logger, String expectedLevel) {
		if (expectedLevel.equals("ALL")) {
			Assert.assertTrue(
				"TRACE should be enabled if logging level is ALL",
				logger.isTraceEnabled());

			return;
		}

		String actualLevel = null;

		if (logger.isTraceEnabled()) {
			actualLevel = "TRACE";
		}
		else if (logger.isDebugEnabled()) {
			actualLevel = "DEBUG";
		}
		else if (logger.isInfoEnabled()) {
			actualLevel = "INFO";
		}
		else if (logger.isEnabledFor(Level.WARN)) {
			actualLevel = "WARN";
		}
		else if (logger.isEnabledFor(Level.ERROR)) {
			actualLevel = "ERROR";
		}
		else if (logger.isEnabledFor(Level.FATAL)) {
			actualLevel = "FATAL";
		}
		else {
			actualLevel = "OFF";
		}

		Assert.assertEquals(
			"Logging level is wrong", expectedLevel, actualLevel);
	}

	private String _generateXMLConfigurationContent(
		String loggerName, String priority, Class<?>... appenderTypes) {

		StringBundler sb = new StringBundler(10 + (8 * appenderTypes.length));

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");

		for (Class<?> appenderType : appenderTypes) {
			sb.append("<appender class=\"");
			sb.append(appenderType.getName());
			sb.append("\" name=\"");
			sb.append(appenderType.getName());
			sb.append("\"></appender>");
		}

		sb.append("<category name=\"");
		sb.append(loggerName);
		sb.append("\"><priority value=\"");
		sb.append(priority);
		sb.append("\" />");

		for (Class<?> appenderType : appenderTypes) {
			sb.append("<appender-ref ref=\"");
			sb.append(appenderType.getName());
			sb.append("\" />");
		}

		sb.append("</category></log4j:configuration>");

		return sb.toString();
	}

	private static final String _ALL = "ALL";

	private static final String _DEBUG = "DEBUG";

	private static final String _ERROR = "ERROR";

	private static final String _FATAL = "FATAL";

	private static final String _INFO = "INFO";

	private static final String _OFF = "OFF";

	private static final String _TRACE = "TRACE";

	private static final String _WARN = "WARN";

}