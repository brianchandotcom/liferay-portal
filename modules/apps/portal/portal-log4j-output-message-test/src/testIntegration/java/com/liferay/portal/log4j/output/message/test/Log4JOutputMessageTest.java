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
public class Log4JOutputMessageTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Path tempLogDir = Files.createTempDirectory(
			Log4JOutputMessageTest.class.getName());

		File tempLogFileDir = tempLogDir.toFile();

		tempLogFileDir.deleteOnExit();

		Logger logger = _getLogger();

		logger.setLevel(Level.TRACE);
		logger.setAdditivity(false);

		Logger rootLogger = Logger.getRootLogger();

		Enumeration<Appender> enumeration = rootLogger.getAllAppenders();

		while (enumeration.hasMoreElements()) {
			Appender appender = enumeration.nextElement();

			if ((appender instanceof FileAppender) &&
				(Objects.equals("TEXT_FILE", appender.getName()) ||
				 Objects.equals("XML_FILE", appender.getName()))) {

				_setLoggerRollingFileAppender(appender, logger, tempLogFileDir);
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
	public void testLogOutput() throws Exception {
		_testLogOutput("TRACE");
		_testLogOutput("DEBUG");
		_testLogOutput("INFO");
		_testLogOutput("WARN");
		_testLogOutput("ERROR");
		_testLogOutput("FATAL");
	}

	@Test
	public void testPortalLogFileName() {
		Matcher matcher = _textFileNamePattern.matcher(_portalTextLogFileName);

		Assert.assertTrue(
			"Text log file name should match the pattern liferay.yyyy-MM-dd." +
				"log, but actual name is " + _portalTextLogFileName,
			matcher.matches());

		matcher = _xmlFileNamePattern.matcher(_portalXmlLogFileName);

		Assert.assertTrue(
			"XML log file name should match the pattern liferay.yyyy-MM-dd." +
				"xml, but actual name is " + _portalXmlLogFileName,
			matcher.matches());
	}

	private static Logger _getLogger() {
		LogWrapper logWrapper = (LogWrapper)_log;

		Log log = logWrapper.getWrappedLog();

		return ReflectionTestUtil.getFieldValue(log, "_logger");
	}

	private static void _setLoggerRollingFileAppender(
		Appender appender, Logger logger, File tempLogFileDir) {

		RollingFileAppender portalRollingFileAppender =
			(RollingFileAppender)appender;

		TimeBasedRollingPolicy portalTimeBasedRollingPolicy =
			(TimeBasedRollingPolicy)
				portalRollingFileAppender.getRollingPolicy();

		String portalFileNamePattern =
			portalTimeBasedRollingPolicy.getFileNamePattern();

		TimeBasedRollingPolicy testTimeBasedRollingPolicy =
			new TimeBasedRollingPolicy();

		testTimeBasedRollingPolicy.setFileNamePattern(
			StringBundler.concat(
				StringUtil.replace(tempLogFileDir.toString(), '\\', '/'),
				StringPool.SLASH,
				StringUtil.extractLast(
					portalFileNamePattern, StringPool.SLASH)));

		RollingFileAppender testRollingFileAppender = new RollingFileAppender();

		testRollingFileAppender.setLayout(
			portalRollingFileAppender.getLayout());
		testRollingFileAppender.setRollingPolicy(testTimeBasedRollingPolicy);

		testRollingFileAppender.activateOptions();

		logger.addAppender(testRollingFileAppender);

		String portalLogFileName = portalRollingFileAppender.getFile();

		if (Objects.equals("TEXT_FILE", portalRollingFileAppender.getName())) {
			_textLogFile = new File(testRollingFileAppender.getFile());

			_portalTextLogFileName = StringUtil.extractLast(
				portalLogFileName, StringPool.SLASH);
		}
		else if (Objects.equals(
					portalRollingFileAppender.getName(), "XML_FILE")) {

			_xmlLogFile = new File(testRollingFileAppender.getFile());

			_portalXmlLogFileName = StringUtil.extractLast(
				portalLogFileName, StringPool.SLASH);
		}
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

		Assert.assertEquals(
			String.valueOf(expectedMessage), messageLine.trim());

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
				StreamUtil.toString(new FileInputStream(_textLogFile)));

			_assertXmlLog(
				level, message, throwable,
				StreamUtil.toString(new FileInputStream(_xmlLogFile)));
		}
		finally {
			_unsyncStringWriter.reset();

			Files.write(
				_textLogFile.toPath(), new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(
				_xmlLogFile.toPath(), new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
		}
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final Log _log = LogFactoryUtil.getLog(
		Log4JOutputMessageTest.class);

	private static final Pattern _datePattern = Pattern.compile(
		"\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d");
	private static String _portalTextLogFileName;
	private static String _portalXmlLogFileName;
	private static final Pattern _textFileNamePattern = Pattern.compile(
		"liferay.\\d\\d\\d\\d-\\d\\d-\\d\\d.log");
	private static File _textLogFile;
	private static UnsyncStringWriter _unsyncStringWriter;
	private static final Pattern _xmlFileNamePattern = Pattern.compile(
		"liferay.\\d\\d\\d\\d-\\d\\d-\\d\\d.xml");
	private static File _xmlLogFile;

	private class TestException extends Exception {
	}

}