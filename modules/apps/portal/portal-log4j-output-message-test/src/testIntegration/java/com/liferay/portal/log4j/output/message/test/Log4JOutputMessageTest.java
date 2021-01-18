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

package com.liferay.portal.log4j.output.message.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.file.Path;

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
public class Log4JOutputMessageTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Path tempLogDir = Files.createTempDirectory(
			Log4JOutputMessageTest.class.getName());

		_tempLogDir = tempLogDir.toFile();

		_tempLogDir.deleteOnExit();

		Logger logger = _getLogger();

		logger.setLevel(Level.TRACE);
		logger.setAdditivity(false);

		Logger rootLogger = Logger.getRootLogger();

		Enumeration<Appender> enumeration = rootLogger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			if (appender instanceof FileAppender) {
				RollingFileAppender rollingFileAppender =
					(RollingFileAppender)appender;

				if (Objects.equals("TEXT_FILE", appender.getName())) {
					RollingFileAppender textFileRollingFileAppender =
						new RollingFileAppender();

					textFileRollingFileAppender.setLayout(
						rollingFileAppender.getLayout());

					TimeBasedRollingPolicy timeBasedRollingPolicy =
						new TimeBasedRollingPolicy();

					timeBasedRollingPolicy.setFileNamePattern(
						StringUtil.replace(tempLogDir.toString(), '\\', '/') +
							"/liferay.%d{yyyy-MM-dd}.log");

					textFileRollingFileAppender.setRollingPolicy(
						timeBasedRollingPolicy);
				}
				else if (Objects.equals("XML_FILE", appender.getName())) {
					RollingFileAppender xmlFileRollingFileAppender =
						new RollingFileAppender();

					xmlFileRollingFileAppender.setLayout(
						rollingFileAppender.getLayout());

					TimeBasedRollingPolicy timeBasedRollingPolicy =
						new TimeBasedRollingPolicy();

					timeBasedRollingPolicy.setFileNamePattern(
						StringUtil.replace(tempLogDir.toString(), '\\', '/') +
							"/liferay.%d{yyyy-MM-dd}.xml");

					xmlFileRollingFileAppender.setRollingPolicy(
						timeBasedRollingPolicy);
				}
			}
			else if ((appender instanceof ConsoleAppender) &&
					 Objects.equals("CONSOLE", appender.getName())) {

				ConsoleAppender consoleAppender = new ConsoleAppender();

				consoleAppender.setLayout(appender.getLayout());

				consoleAppender.activateOptions();

				_unsyncStringWriter = new UnsyncStringWriter();

				consoleAppender.setWriter(_unsyncStringWriter);

				logger.addAppender(consoleAppender);
			}
		}
	}

	@AfterClass
	public static void tearDownClass() {
		Logger logger = _getLogger();

		logger.removeAllAppenders();
	}

	@Test
	public void testConsoleOutput() {
		_testConsoleOutput("TRACE", "TRACE message", null);
		_testConsoleOutput("DEBUG", "DEBUG message", null);
		_testConsoleOutput("INFO", "INFO message", null);
		_testConsoleOutput("WARN", "WARN message", null);
		_testConsoleOutput("ERROR", "ERROR message", null);
		_testConsoleOutput("FATAL", "FATAL message", null);

		_testConsoleOutput("TRACE", "TRACE message", new TestException());
		_testConsoleOutput("DEBUG", "DEBUG message", new TestException());
		_testConsoleOutput("INFO", "INFO message", new TestException());
		_testConsoleOutput("WARN", "WARN message", new TestException());
		_testConsoleOutput("ERROR", "ERROR message", new TestException());
		_testConsoleOutput("FATAL", "FATAL message", new TestException());

		_testConsoleOutput("TRACE", null, new TestException());
		_testConsoleOutput("DEBUG", null, new TestException());
		_testConsoleOutput("INFO", null, new TestException());
		_testConsoleOutput("WARN", null, new TestException());
		_testConsoleOutput("ERROR", null, new TestException());
		_testConsoleOutput("FATAL", null, new TestException());
	}

	@Test
	public void testFileOutput() throws Exception {
		_testFileOutput("TRACE", "TRACE message", null);
		_testFileOutput("DEBUG", "DEBUG message", null);
		_testFileOutput("INFO", "INFO message", null);
		_testFileOutput("WARN", "WARN message", null);
		_testFileOutput("ERROR", "ERROR message", null);
		_testFileOutput("FATAL", "FATAL message", null);

		_testFileOutput("TRACE", "TRACE message", new TestException());
		_testFileOutput("DEBUG", "DEBUG message", new TestException());
		_testFileOutput("INFO", "INFO message", new TestException());
		_testFileOutput("WARN", "WARN message", new TestException());
		_testFileOutput("ERROR", "ERROR message", new TestException());
		_testFileOutput("FATAL", "FATAL message", new TestException());

		_testFileOutput("TRACE", null, new TestException());
		_testFileOutput("DEBUG", null, new TestException());
		_testFileOutput("INFO", null, new TestException());
		_testFileOutput("WARN", null, new TestException());
		_testFileOutput("ERROR", null, new TestException());
		_testFileOutput("FATAL", null, new TestException());
	}

	private static Logger _getLogger() {
		LogWrapper logWrapper = (LogWrapper)_log;

		Log log = logWrapper.getWrappedLog();

		return ReflectionTestUtil.getFieldValue(log, "_logger");
	}

	private void _assertTextLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String actualOutput) {

		String[] outputLines = StringUtil.splitLines(actualOutput);

		Assert.assertTrue(
			"The log output should have at least 1 line",
			outputLines.length > 0);

		String messageLine = outputLines[0];

		// Date Format

		Matcher dateMatcher = _datePattern.matcher(
			messageLine.substring(0, _DATE_FORMAT.length()));

		Assert.assertTrue(
			"Output date format should be yyyy-MM-dd HH:mm:ss.SSS",
			dateMatcher.matches());

		// Level part

		messageLine = messageLine.substring(_DATE_FORMAT.length());

		String expectedLevelPart = StringBundler.concat(
			StringPool.SPACE, expectedLevel, StringPool.SPACE);

		Assert.assertEquals(
			expectedLevelPart,
			messageLine.substring(0, expectedLevelPart.length()));

		// Thread name part

		int threadNamePartBeginIndex = messageLine.indexOf(
			StringPool.OPEN_BRACKET);

		messageLine = messageLine.substring(threadNamePartBeginIndex);

		Thread currentThread = Thread.currentThread();

		String expectedThreadNamePart = StringBundler.concat(
			StringPool.OPEN_BRACKET, currentThread.getName(),
			StringPool.CLOSE_BRACKET);

		Assert.assertEquals(
			expectedThreadNamePart,
			messageLine.substring(0, expectedThreadNamePart.length()));

		// Class name

		messageLine = messageLine.substring(expectedThreadNamePart.length());

		String expectedClassNamePart = StringBundler.concat(
			StringPool.OPEN_BRACKET,
			Log4JOutputMessageTest.class.getSimpleName(), StringPool.COLON);

		Assert.assertEquals(
			expectedClassNamePart,
			messageLine.substring(0, expectedClassNamePart.length()));

		messageLine = messageLine.substring(expectedClassNamePart.length());

		// Line number

		int classNamePartEndIndex = messageLine.indexOf(
			StringPool.CLOSE_BRACKET);

		Integer.valueOf(messageLine.substring(0, classNamePartEndIndex - 1));

		// Message part

		messageLine = messageLine.substring(classNamePartEndIndex + 1);

		if (expectedMessage == null) {
			expectedMessage = "null";
		}

		Assert.assertEquals(expectedMessage, messageLine.trim());

		// Throwable part

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			Assert.assertEquals(
				expectedThrowableClass.getName(), outputLines[1]);

			String actualFirstPrefixStackTraceElement = outputLines[2].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					Log4JOutputMessageTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + Log4JOutputMessageTest.class.getName()));
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

		String log4JEvent = outputLines[0];

		String log4JEventPart = log4JEvent.substring(
			log4JEvent.indexOf(StringPool.SPACE),
			log4JEvent.indexOf(StringPool.GREATER_THAN));

		// log4j:event logger

		String expectedLog4JEventLoggerPart = StringBundler.concat(
			StringPool.SPACE, "logger=", StringPool.QUOTE,
			Log4JOutputMessageTest.class.getName(), StringPool.QUOTE,
			StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JEventLoggerPart,
			log4JEventPart.substring(0, expectedLog4JEventLoggerPart.length()));

		// log4j:event timestamp

		log4JEventPart = log4JEventPart.substring(
			expectedLog4JEventLoggerPart.length());

		String actualLog4JEventTimestamp = log4JEventPart.substring(
			log4JEventPart.indexOf(StringPool.QUOTE) + 1,
			log4JEventPart.indexOf(StringPool.SPACE) - 1);

		Long.valueOf(actualLog4JEventTimestamp);

		// log4j:event level

		log4JEventPart = log4JEventPart.substring(
			"timestamp=".length() + actualLog4JEventTimestamp.length() + 2);

		String expectedLog4JEventLevelPart = StringBundler.concat(
			StringPool.SPACE, "level=", StringPool.QUOTE, expectedLevel,
			StringPool.QUOTE, StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JEventLevelPart,
			log4JEventPart.substring(0, expectedLog4JEventLevelPart.length()));

		// log4j:event thread

		log4JEventPart = log4JEventPart.substring(
			expectedLog4JEventLevelPart.length());

		Thread currentThread = Thread.currentThread();

		String expectedLog4JEventThreadPart = StringBundler.concat(
			"thread=", StringPool.QUOTE, currentThread.getName(),
			StringPool.QUOTE);

		Assert.assertEquals(
			expectedLog4JEventThreadPart,
			log4JEventPart.substring(0, expectedLog4JEventThreadPart.length()));

		// log4j:message

		if (expectedThrowable != null) {
			if (expectedMessage == null) {
				expectedMessage = StringPool.BLANK;
			}

			String expectedLog4JMessagePart = StringBundler.concat(
				"<log4j:message>", StringPool.CDATA_OPEN, expectedMessage,
				StringPool.CDATA_CLOSE, "</log4j:message>");

			Assert.assertEquals(expectedLog4JMessagePart, outputLines[1]);
		}

		// log4j:throwable

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			String expectedLog4JThrowablePart = StringBundler.concat(
				"<log4j:throwable>", StringPool.CDATA_OPEN,
				expectedThrowableClass.getName());

			Assert.assertEquals(expectedLog4JThrowablePart, outputLines[2]);

			String actualFirstPrefixStackTraceElement = outputLines[3].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					Log4JOutputMessageTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + Log4JOutputMessageTest.class.getName()));
		}

		// log4j:locationInfo

		String log4JLocationInfo = outputLines[outputLines.length - 2];

		String log4JLocationInfoPart = log4JLocationInfo.substring(
			log4JLocationInfo.indexOf(StringPool.SPACE),
			log4JLocationInfo.indexOf(StringPool.FORWARD_SLASH));

		// log4j:locationInfo class

		String expectedLog4JLocationInfoClassPart = StringBundler.concat(
			StringPool.SPACE, "class=", StringPool.QUOTE,
			Log4JOutputMessageTest.class.getName(), StringPool.QUOTE,
			StringPool.SPACE);

		Assert.assertEquals(
			expectedLog4JLocationInfoClassPart,
			log4JLocationInfoPart.substring(
				0, expectedLog4JLocationInfoClassPart.length()));

		// log4j:locationInfo file

		log4JLocationInfoPart = log4JLocationInfoPart.substring(
			expectedLog4JLocationInfoClassPart.length());
		log4JLocationInfoPart = log4JLocationInfoPart.substring(
			log4JLocationInfoPart.indexOf("file"));

		String expectedLog4JLocationInfoFilePart = StringBundler.concat(
			"file=", StringPool.QUOTE,
			Log4JOutputMessageTest.class.getSimpleName(), ".java",
			StringPool.QUOTE);

		Assert.assertEquals(
			expectedLog4JLocationInfoFilePart,
			log4JLocationInfoPart.substring(
				0, expectedLog4JLocationInfoFilePart.length()));
	}

	private void _outputLog(String level, String message, Throwable throwable) {
		if (level.equals("TRACE")) {
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
		else if (level.equals("DEBUG")) {
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
		else {
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
	}

	private void _testConsoleOutput(
		String level, String message, Throwable throwable) {

		_outputLog(level, message, throwable);

		try {
			_assertTextLog(
				level, message, throwable, _unsyncStringWriter.toString());
		}
		finally {
			_unsyncStringWriter.reset();
		}
	}

	private void _testFileOutput(
			String level, String message, Throwable throwable)
		throws Exception {

		for (File logFile : _tempLogDir.listFiles()) {
			if (logFile.isFile()) {
				logFile.delete();
			}
		}

		_outputLog(level, message, throwable);

		try {
			for (File file : _tempLogDir.listFiles()) {
				String fileName = file.getName();

				if (fileName.endsWith(".log")) {
					Matcher matcher = _textFileNamePattern.matcher(fileName);

					Assert.assertTrue(
						"Text log file name should match the pattern liferay." +
							"yyyy-MM-dd.log, but actual name is " + fileName,
						matcher.matches());

					_assertTextLog(
						level, message, throwable,
						StreamUtil.toString(new FileInputStream(file)));
				}
				else {
					Matcher matcher = _xmlFileNamePattern.matcher(fileName);

					Assert.assertTrue(
						"XML log file name should match the pattern liferay." +
							"yyyy-MM-dd.xml, but actual name is " + fileName,
						matcher.matches());

					_assertXmlLog(
						level, message, throwable,
						StreamUtil.toString(new FileInputStream(file)));
				}
			}
		}
		finally {
			_unsyncStringWriter.reset();
		}
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final Log _log = LogFactoryUtil.getLog(
		Log4JOutputMessageTest.class);

	private static final Pattern _datePattern = Pattern.compile(
		"\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d");
	private static File _tempLogDir;
	private static final Pattern _textFileNamePattern = Pattern.compile(
		"liferay.\\d\\d\\d\\d-\\d\\d-\\d\\d.log");
	private static UnsyncStringWriter _unsyncStringWriter;
	private static final Pattern _xmlFileNamePattern = Pattern.compile(
		"liferay.\\d\\d\\d\\d-\\d\\d-\\d\\d.xml");

	private class TestException extends Exception {
	}

}