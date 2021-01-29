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

package com.liferay.portal.log4j.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.rolling.RollingFileAppender;
import org.apache.log4j.rolling.TimeBasedRollingPolicy;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hai Yu
 */
@RunWith(Arquillian.class)
public class PortalLog4jTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_tempLogFileDirPath = Files.createTempDirectory(
			PortalLog4jTest.class.getName());

		Logger logger = Logger.getLogger(PortalLog4jTest.class);

		logger.setAdditivity(false);
		logger.setLevel(Level.TRACE);

		Logger rootLogger = Logger.getRootLogger();

		Enumeration<Appender> enumeration = rootLogger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			if ((appender instanceof ConsoleAppender) &&
				Objects.equals("CONSOLE", appender.getName())) {

				ConsoleAppender consoleAppender = new ConsoleAppender();

				consoleAppender.setLayout(appender.getLayout());

				consoleAppender.activateOptions();

				_unsyncStringWriter = new UnsyncStringWriter();

				consoleAppender.setWriter(_unsyncStringWriter);

				logger.addAppender(consoleAppender);
			}
			else if (appender instanceof FileAppender) {
				if (Objects.equals("TEXT_FILE", appender.getName())) {
					_textLogFilePath = _initFileAppender(
						logger, appender, _tempLogFileDirPath.toString());
				}
				else if (Objects.equals("XML_FILE", appender.getName())) {
					_xmlLogFilePath = _initFileAppender(
						logger, appender, _tempLogFileDirPath.toString());
				}
			}
		}
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		Logger logger = Logger.getLogger(PortalLog4jTest.class);

		logger.removeAllAppenders();

		Files.deleteIfExists(_textLogFilePath);
		Files.deleteIfExists(_xmlLogFilePath);

		Files.deleteIfExists(_tempLogFileDirPath);
	}

	@Test
	public void testDefaultLevel() {
		Logger logger = Logger.getLogger("test.logger");

		Assert.assertFalse(logger.isDebugEnabled());
		Assert.assertTrue(logger.isInfoEnabled());
	}

	@Test
	public void testLogOutput() throws Exception {
		_testLogOutput("DEBUG");
		_testLogOutput("ERROR");
		_testLogOutput("FATAL");
		_testLogOutput("INFO");
		_testLogOutput("TRACE");
		_testLogOutput("WARN");
	}

	private static Path _initFileAppender(
		Logger logger, Appender appender, String tempLogDir) {

		RollingFileAppender portalRollingFileAppender =
			(RollingFileAppender)appender;

		TimeBasedRollingPolicy portalTimeBasedRollingPolicy =
			(TimeBasedRollingPolicy)
				portalRollingFileAppender.getRollingPolicy();

		TimeBasedRollingPolicy testTimeBasedRollingPolicy =
			new TimeBasedRollingPolicy();

		testTimeBasedRollingPolicy.setFileNamePattern(
			StringBundler.concat(
				StringUtil.replace(tempLogDir, '\\', '/'), StringPool.SLASH,
				StringUtil.extractLast(
					portalTimeBasedRollingPolicy.getFileNamePattern(),
					StringPool.SLASH)));

		RollingFileAppender testRollingFileAppender = new RollingFileAppender();

		testRollingFileAppender.setLayout(
			portalRollingFileAppender.getLayout());
		testRollingFileAppender.setRollingPolicy(testTimeBasedRollingPolicy);

		testRollingFileAppender.activateOptions();

		logger.addAppender(testRollingFileAppender);

		return Paths.get(testRollingFileAppender.getFile());
	}

	private void _assertTextLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String actualOutput) {

		String[] outputLines = StringUtil.splitLines(actualOutput);

		Assert.assertTrue(
			"The log output should have at least 1 line",
			outputLines.length > 0);

		String messageLine = outputLines[0];

		// Date format

		Matcher dateMatcher = _datePattern.matcher(
			messageLine.substring(0, _DATE_FORMAT.length()));

		Assert.assertTrue(
			"Output date format should be yyyy-MM-dd HH:mm:ss.SSS",
			dateMatcher.matches());

		// Level

		messageLine = messageLine.substring(_DATE_FORMAT.length());

		Assert.assertEquals(
			StringBundler.concat(
				StringPool.SPACE, expectedLevel, StringPool.SPACE),
			messageLine.substring(0, expectedLevel.length() + 2));

		// Thread name

		messageLine = messageLine.substring(
			messageLine.indexOf(StringPool.OPEN_BRACKET));

		Thread currentThread = Thread.currentThread();

		String expectedThreadName = StringBundler.concat(
			StringPool.OPEN_BRACKET, currentThread.getName(),
			StringPool.CLOSE_BRACKET);

		Assert.assertEquals(
			expectedThreadName,
			messageLine.substring(0, expectedThreadName.length()));

		// Class name

		messageLine = messageLine.substring(expectedThreadName.length());

		String expectedClassName = StringBundler.concat(
			StringPool.OPEN_BRACKET, PortalLog4jTest.class.getSimpleName(),
			StringPool.COLON);

		Assert.assertEquals(
			expectedClassName,
			messageLine.substring(0, expectedClassName.length()));

		// Line number

		messageLine = messageLine.substring(expectedClassName.length());

		int classNameEndIndex = messageLine.indexOf(StringPool.CLOSE_BRACKET);

		Integer.valueOf(messageLine.substring(0, classNameEndIndex - 1));

		// Message

		messageLine = messageLine.substring(classNameEndIndex + 1);

		Assert.assertEquals(
			String.valueOf(expectedMessage), messageLine.trim());

		// Throwable

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			Assert.assertEquals(
				expectedThrowableClass.getName(), outputLines[1]);

			String actualFirstPrefixStackTraceElement = outputLines[2].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					PortalLog4jTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + PortalLog4jTest.class.getName()));
		}
	}

	private void _assertXmlLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String actualOutput) {

		String[] outputLines = StringUtil.splitLines(actualOutput);

		Assert.assertTrue(
			"The log output should have at least 1 line",
			outputLines.length > 0);

		// log4j:event

		String log4JEventLine = outputLines[0];

		String log4JEvent = log4JEventLine.substring(
			log4JEventLine.indexOf(StringPool.SPACE),
			log4JEventLine.indexOf(StringPool.GREATER_THAN));

		// log4j:event logger

		String expectedLog4JEventLogger = StringBundler.concat(
			StringPool.SPACE, "logger=", StringPool.QUOTE,
			PortalLog4jTest.class.getName(), StringPool.QUOTE,
			StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JEventLogger,
			log4JEvent.substring(0, expectedLog4JEventLogger.length()));

		// log4j:event timestamp

		log4JEvent = log4JEvent.substring(expectedLog4JEventLogger.length());

		String actualLog4JEventTimestamp = log4JEvent.substring(
			log4JEvent.indexOf(StringPool.QUOTE) + 1,
			log4JEvent.indexOf(StringPool.SPACE) - 1);

		Long.valueOf(actualLog4JEventTimestamp);

		// log4j:event level

		log4JEvent = log4JEvent.substring(
			"timestamp=".length() + actualLog4JEventTimestamp.length() + 2);

		String expectedLog4JEventLevel = StringBundler.concat(
			StringPool.SPACE, "level=", StringPool.QUOTE, expectedLevel,
			StringPool.QUOTE, StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JEventLevel,
			log4JEvent.substring(0, expectedLog4JEventLevel.length()));

		// log4j:event thread

		log4JEvent = log4JEvent.substring(expectedLog4JEventLevel.length());

		Thread currentThread = Thread.currentThread();

		String expectedLog4JEventThread = StringBundler.concat(
			"thread=", StringPool.QUOTE, currentThread.getName(),
			StringPool.QUOTE);

		Assert.assertEquals(
			expectedLog4JEventThread,
			log4JEvent.substring(0, expectedLog4JEventThread.length()));

		// log4j:message

		if (expectedThrowable != null) {
			if (expectedMessage == null) {
				expectedMessage = StringPool.BLANK;
			}

			Assert.assertEquals(
				StringBundler.concat(
					"<log4j:message>", StringPool.CDATA_OPEN, expectedMessage,
					StringPool.CDATA_CLOSE, "</log4j:message>"),
				outputLines[1]);
		}

		// log4j:throwable

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			Assert.assertEquals(
				StringBundler.concat(
					"<log4j:throwable>", StringPool.CDATA_OPEN,
					expectedThrowableClass.getName()),
				outputLines[2]);

			String actualFirstPrefixStackTraceElement = outputLines[3].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					PortalLog4jTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + PortalLog4jTest.class.getName()));
		}

		// log4j:locationInfo

		String log4JLocationInfoLine = outputLines[outputLines.length - 2];

		String log4JLocationInfo = log4JLocationInfoLine.substring(
			log4JLocationInfoLine.indexOf(StringPool.SPACE),
			log4JLocationInfoLine.indexOf(StringPool.FORWARD_SLASH));

		// log4j:locationInfo class

		String expectedLog4JLocationInfoClass = StringBundler.concat(
			StringPool.SPACE, "class=", StringPool.QUOTE,
			PortalLog4jTest.class.getName(), StringPool.QUOTE,
			StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JLocationInfoClass,
			log4JLocationInfo.substring(
				0, expectedLog4JLocationInfoClass.length()));

		// log4j:locationInfo file

		log4JLocationInfo = log4JLocationInfo.substring(
			expectedLog4JLocationInfoClass.length());
		log4JLocationInfo = log4JLocationInfo.substring(
			log4JLocationInfo.indexOf("file"));

		String expectedLog4JLocationInfoFile = StringBundler.concat(
			"file=", StringPool.QUOTE, PortalLog4jTest.class.getSimpleName(),
			".java", StringPool.QUOTE);

		Assert.assertEquals(
			expectedLog4JLocationInfoFile,
			log4JLocationInfo.substring(
				0, expectedLog4JLocationInfoFile.length()));
	}

	private void _outputLog(String level, String message, Throwable throwable) {
		if (level.equals("DEBUG")) {
			if ((message == null) && (throwable != null)) {
				_log.debug(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.debug(message);
			}
			else {
				_log.debug(message, throwable);
			}
		}
		else if (level.equals("ERROR")) {
			if ((message == null) && (throwable != null)) {
				_log.error(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.error(message);
			}
			else {
				_log.error(message, throwable);
			}
		}
		else if (level.equals("FATAL")) {
			if ((message == null) && (throwable != null)) {
				_log.fatal(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.fatal(message);
			}
			else {
				_log.fatal(message, throwable);
			}
		}
		else if (level.equals("INFO")) {
			if ((message == null) && (throwable != null)) {
				_log.info(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.info(message);
			}
			else {
				_log.info(message, throwable);
			}
		}
		else if (level.equals("TRACE")) {
			if ((message == null) && (throwable != null)) {
				_log.trace(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.trace(message);
			}
			else {
				_log.trace(message, throwable);
			}
		}
		else if (level.equals("WARN")) {
			if ((message == null) && (throwable != null)) {
				_log.warn(throwable);
			}
			else if ((message != null) && (throwable == null)) {
				_log.warn(message);
			}
			else {
				_log.warn(message, throwable);
			}
		}
	}

	private void _testLogOutput(String level) throws Exception {
		String testMessage = level + " message";

		_testLogOutput(level, testMessage, null);

		TestException testException = new TestException();

		_testLogOutput(level, testMessage, testException);

		_testLogOutput(level, null, testException);
	}

	private void _testLogOutput(
			String level, String message, Throwable throwable)
		throws Exception {

		_outputLog(level, message, throwable);

		try {
			_assertTextLog(
				level, message, throwable, _unsyncStringWriter.toString());

			_assertTextLog(
				level, message, throwable,
				new String(Files.readAllBytes(_textLogFilePath)));

			_assertXmlLog(
				level, message, throwable,
				new String(Files.readAllBytes(_xmlLogFilePath)));
		}
		finally {
			_unsyncStringWriter.reset();

			Files.write(
				_textLogFilePath, new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(
				_xmlLogFilePath, new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
		}
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalLog4jTest.class);

	private static final Pattern _datePattern = Pattern.compile(
		"\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d");
	private static Path _tempLogFileDirPath;
	private static Path _textLogFilePath;
	private static UnsyncStringWriter _unsyncStringWriter;
	private static Path _xmlLogFilePath;

	private class TestException extends Exception {
	}

}