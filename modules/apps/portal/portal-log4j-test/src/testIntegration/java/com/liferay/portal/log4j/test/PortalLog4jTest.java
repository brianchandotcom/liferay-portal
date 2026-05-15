/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log4j.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Hai Yu
 */
@RunWith(Arquillian.class)
public class PortalLog4jTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_tempLogFileDirPath = Files.createTempDirectory(
			PortalLog4jTest.class.getName());

		Logger logger = (Logger)LogManager.getLogger(PortalLog4jTest.class);

		logger.setAdditive(false);
		logger.setLevel(Level.TRACE);

		Logger rootLogger = (Logger)LogManager.getRootLogger();

		Map<String, Appender> appenders = rootLogger.getAppenders();

		for (Appender appender : appenders.values()) {
			if ((appender instanceof ConsoleAppender) &&
				Objects.equals(appender.getName(), "CONSOLE")) {

				ConsoleAppender consoleAppender =
					ConsoleAppender.createDefaultAppenderForLayout(
						appender.getLayout());

				OutputStreamManager outputStreamManager =
					consoleAppender.getManager();

				_testOutputStream = new TestOutputStream(
					ReflectionTestUtil.getFieldValue(
						outputStreamManager, "outputStream"));

				ReflectionTestUtil.getAndSetFieldValue(
					outputStreamManager, "outputStream", _testOutputStream);

				consoleAppender.start();

				logger.addAppender(consoleAppender);
			}
			else if (appender instanceof RollingFileAppender) {
				if (Objects.equals(appender.getName(), "TEXT_FILE")) {
					_textLogFilePath = _initFileAppender(
						logger, appender, _tempLogFileDirPath.toString());
				}
				else if (Objects.equals(appender.getName(), "XML_FILE")) {
					_xmlLogFilePath = _initFileAppender(
						logger, appender, _tempLogFileDirPath.toString());
				}
			}
		}
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		Logger logger = (Logger)LogManager.getLogger(PortalLog4jTest.class);

		Map<String, Appender> appenders = logger.getAppenders();

		for (Appender appender : appenders.values()) {
			logger.removeAppender(appender);

			appender.stop();
		}

		Files.deleteIfExists(_textLogFilePath);
		Files.deleteIfExists(_xmlLogFilePath);

		Files.deleteIfExists(_tempLogFileDirPath);
	}

	@Test
	public void testDefaultLevel() {
		Logger logger = (Logger)LogManager.getLogger("test.logger");

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

	@Test
	public void testLogOutputWithLogContext() throws Exception {
		String key1 = "test.key.1";
		String key2 = "test.key.2";
		String value1 = "test.value.1";
		String value2 = "test.value.2";

		String logContextName = "TestLogContext";

		_testLogOutputWithLogContext(
			HashMapBuilder.put(
				key1, value1
			).put(
				key2, value2
			).build(),
			StringBundler.concat(
				StringPool.OPEN_CURLY_BRACE, logContextName, StringPool.PERIOD,
				key1, StringPool.EQUAL, value1, ", ", logContextName,
				StringPool.PERIOD, key2, StringPool.EQUAL, value2,
				StringPool.CLOSE_CURLY_BRACE),
			logContextName);
	}

	@Test
	public void testLogOutputWithLogContextAndExternalContext()
		throws Exception {

		String key1 = "test.key.1";
		String key2 = "test.key.2";
		String value1 = "test.value.1";
		String value2 = "test.value.2";

		String logContextName = "TestLogContext";

		try {
			ThreadContext.put(key2, value2);

			_testLogOutputWithLogContext(
				HashMapBuilder.put(
					key1, value1
				).build(),
				StringBundler.concat(
					StringPool.OPEN_CURLY_BRACE, logContextName,
					StringPool.PERIOD, key1, StringPool.EQUAL, value1, ", ",
					key2, StringPool.EQUAL, value2,
					StringPool.CLOSE_CURLY_BRACE),
				logContextName);

			Map<String, String> context = ThreadContext.getContext();

			Assert.assertEquals(context.toString(), 1, context.size());
			Assert.assertEquals(value2, context.get(key2));
		}
		finally {
			ThreadContext.remove(key2);
		}
	}

	@Test
	public void testLogOutputWithLogContextWithEmptyContextName()
		throws Exception {

		String key1 = "test.key.1";
		String key2 = "test.key.2";
		String value1 = "test.value.1";
		String value2 = "test.value.2";

		_testLogOutputWithLogContext(
			HashMapBuilder.put(
				key1, value1
			).put(
				key2, value2
			).build(),
			StringBundler.concat(
				StringPool.OPEN_CURLY_BRACE, key1, StringPool.EQUAL, value1,
				", ", key2, StringPool.EQUAL, value2,
				StringPool.CLOSE_CURLY_BRACE),
			StringPool.BLANK);
	}

	@Test
	public void testLogOutputWithLogContextWithEmptyLogContext()
		throws Exception {

		_testLogOutputWithLogContext(
			Collections.emptyMap(),
			StringPool.OPEN_CURLY_BRACE + StringPool.CLOSE_CURLY_BRACE,
			"TestLogContext");
	}

	@Test
	public void testXMLLogOutputWithCDATAClosingSequence() throws Exception {
		_testLogOutput(
			"INFO", "before]]>after", null, "before]]>]]&gt;<![CDATA[after",
			null);
	}

	@Test
	public void testXMLLogOutputWithForbiddenXML10Chars() throws Exception {
		String forbiddenCharString = String.valueOf((char)1);

		String originalThreadNameString = Thread.currentThread(
		).getName();

		Thread.currentThread(
		).setName(
			"thread:" + forbiddenCharString
		);

		ThreadContext.push("ndc:" + forbiddenCharString);

		String key = "key:" + forbiddenCharString;
		String logContextName = "ForbiddenCharsLogContext";
		String value = "value:" + forbiddenCharString;

		try {
			_testLogOutputWithLogContext(
				HashMapBuilder.put(
					key, value
				).build(),
				StringBundler.concat(
					StringPool.OPEN_CURLY_BRACE, logContextName,
					StringPool.PERIOD, key, StringPool.EQUAL, value,
					StringPool.CLOSE_CURLY_BRACE),
				"{ForbiddenCharsLogContext.key: =value: }", "ndc:", "thread: ",
				logContextName);
		}
		finally {
			Thread.currentThread(
			).setName(
				originalThreadNameString
			);

			ThreadContext.pop();
		}
	}

	@Test
	public void testXMLLogOutputWithHTMLSpecialChars() throws Exception {
		String originalThreadNameString = Thread.currentThread(
		).getName();

		Thread.currentThread(
		).setName(
			"thread:<>&\""
		);

		try {
			_testLogOutput(
				"INFO", "message", null, null, "thread:&lt;&gt;&amp;&quot;");
		}
		finally {
			Thread.currentThread(
			).setName(
				originalThreadNameString
			);
		}
	}

	private static Path _initFileAppender(
		Logger logger, Appender appender, String tempLogDir) {

		RollingFileAppender portalRollingFileAppender =
			(RollingFileAppender)appender;

		String testFilePattern = StringBundler.concat(
			StringUtil.replace(tempLogDir, '\\', '/'), StringPool.SLASH,
			StringUtil.extractLast(
				portalRollingFileAppender.getFilePattern(), StringPool.SLASH));

		LoggerContext loggerContext = (LoggerContext)LogManager.getContext();

		RollingFileAppender testRollingFileAppender =
			RollingFileAppender.createAppender(
				null, testFilePattern, Boolean.TRUE.toString(),
				portalRollingFileAppender.getName(), Boolean.TRUE.toString(),
				String.valueOf(_BUFFER_SIZE), Boolean.TRUE.toString(),
				portalRollingFileAppender.getTriggeringPolicy(), null,
				portalRollingFileAppender.getLayout(), null,
				Boolean.FALSE.toString(), null, null,
				loggerContext.getConfiguration());

		testRollingFileAppender.start();

		logger.addAppender(testRollingFileAppender);

		RollingFileManager testRollingFileManager =
			testRollingFileAppender.getManager();

		return Paths.get(testRollingFileManager.getFileName());
	}

	private void _assertTextLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String actualOutput) {

		_assertTextLog(
			expectedLevel, expectedMessage, expectedThrowable, null,
			actualOutput);
	}

	private void _assertTextLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String expectedLogContextMessage,
		String actualOutput) {

		String[] outputLines = StringUtil.splitLines(actualOutput);

		Assert.assertTrue(
			"The log output should have at least 1 line",
			outputLines.length > 0);

		String messageLine = outputLines[0];

		// Timestamp

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

		// [ThreadName]

		messageLine = messageLine.substring(
			messageLine.indexOf(StringPool.OPEN_BRACKET));

		Thread currentThread = Thread.currentThread();

		String expectedThreadName = StringBundler.concat(
			StringPool.OPEN_BRACKET, currentThread.getName(),
			StringPool.CLOSE_BRACKET);

		Assert.assertEquals(
			expectedThreadName,
			messageLine.substring(0, expectedThreadName.length()));

		// [ClassName:LineNumber]

		messageLine = messageLine.substring(expectedThreadName.length());

		String expectedClassName = StringBundler.concat(
			StringPool.OPEN_BRACKET, PortalLog4jTest.class.getSimpleName(),
			StringPool.COLON);

		Assert.assertEquals(
			expectedClassName,
			messageLine.substring(0, expectedClassName.length()));

		messageLine = messageLine.substring(expectedClassName.length());

		int classNameEndIndex = messageLine.indexOf(StringPool.CLOSE_BRACKET);

		Integer.valueOf(messageLine.substring(0, classNameEndIndex - 1));

		// Message

		messageLine = messageLine.substring(classNameEndIndex + 1);

		Assert.assertEquals(
			String.valueOf(expectedMessage), messageLine.trim());

		int outputLineIndex = 1;

		// Log context

		if (expectedLogContextMessage != null) {
			Assert.assertEquals(
				expectedLogContextMessage, outputLines[outputLineIndex].trim());

			outputLineIndex++;
		}

		// Throwable

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			Assert.assertEquals(
				expectedThrowableClass.getName() + ": " +
					expectedThrowable.getMessage(),
				outputLines[outputLineIndex]);

			String actualFirstPrefixStackTraceElement =
				outputLines[outputLineIndex + 1].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					PortalLog4jTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + PortalLog4jTest.class.getName()));
		}
	}

	private void _assertXmlLog(
		String expectedLevel, String expectedMessage,
		Throwable expectedThrowable, String expectedLogContextMessage,
		String expectedXmlMessage, String expectedXmlNdc,
		String expectedXmlThread, String actualOutput) {

		String[] outputLines = StringUtil.splitLines(actualOutput);

		Assert.assertTrue(
			"The log output should have at least 1 line",
			outputLines.length > 0);

		// <log4j:event />

		String log4JEventLine = outputLines[0];

		String log4JEvent = log4JEventLine.substring(
			log4JEventLine.indexOf(StringPool.SPACE),
			log4JEventLine.indexOf(StringPool.GREATER_THAN));

		// <log4j:event logger="..." />

		String expectedLog4JEventLogger = StringBundler.concat(
			" logger=\"", PortalLog4jTest.class.getName(), "\" ");

		Assert.assertEquals(
			expectedLog4JEventLogger,
			log4JEvent.substring(0, expectedLog4JEventLogger.length()));

		// <log4j:event timestamp="..." />

		log4JEvent = log4JEvent.substring(expectedLog4JEventLogger.length());

		String actualLog4JEventTimestamp = log4JEvent.substring(
			log4JEvent.indexOf(StringPool.QUOTE) + 1,
			log4JEvent.indexOf(StringPool.SPACE) - 1);

		Long.valueOf(actualLog4JEventTimestamp);

		// <log4j:event level="..." />

		log4JEvent = log4JEvent.substring(
			"timestamp=".length() + actualLog4JEventTimestamp.length() + 2);

		String expectedLog4JEventLevel = StringBundler.concat(
			" level=\"", expectedLevel, "\" ");

		Assert.assertEquals(
			expectedLog4JEventLevel,
			log4JEvent.substring(0, expectedLog4JEventLevel.length()));

		// <log4j:event thread="..." />

		log4JEvent = log4JEvent.substring(expectedLog4JEventLevel.length());

		Thread currentThread = Thread.currentThread();

		String expectedLog4JEventThread = StringBundler.concat(
			"thread=\"",
			(expectedXmlThread != null) ? expectedXmlThread :
				currentThread.getName(),
			StringPool.QUOTE);

		Assert.assertEquals(
			expectedLog4JEventThread,
			log4JEvent.substring(0, expectedLog4JEventThread.length()));

		// <log4j:message>...</log4j:message>

		Assert.assertEquals(
			StringBundler.concat(
				"<log4j:message><![CDATA[",
				(expectedXmlMessage != null) ? expectedXmlMessage :
					expectedMessage,
				"]]></log4j:message>"),
			outputLines[1]);

		// <log4j:NDC>...</log4j:NDC>

		if (expectedXmlNdc != null) {
			String expectedNdcLine = StringBundler.concat(
				"<log4j:NDC><![CDATA[", expectedXmlNdc, "]]></log4j:NDC>");

			boolean found = false;

			for (String outputLine : outputLines) {
				if (Objects.equals(outputLine, expectedNdcLine)) {
					found = true;

					break;
				}
			}

			Assert.assertTrue(
				StringBundler.concat(
					"<log4j:NDC><![CDATA[", expectedXmlNdc,
					"]]></log4j:NDC> not found in XML output"),
				found);
		}

		// <log4j:throwable>...</log4j:throwable>

		if (expectedThrowable != null) {
			Class<?> expectedThrowableClass = expectedThrowable.getClass();

			Assert.assertTrue(
				outputLines[2].startsWith(
					"<log4j:throwable><![CDATA[" +
						expectedThrowableClass.getName()));

			String actualFirstPrefixStackTraceElement = outputLines[3].trim();

			Assert.assertTrue(
				"A throwable should be logged and the first stack should be " +
					PortalLog4jTest.class.getName(),
				actualFirstPrefixStackTraceElement.startsWith(
					"at " + PortalLog4jTest.class.getName()));
		}

		int locationInfoLineIndex = outputLines.length - 2;

		// <log4j:properties>...</log4j:properties>

		if ((expectedLogContextMessage != null) &&
			!Objects.equals(expectedLogContextMessage, "{}")) {

			int propertiesCloseLineIndex = outputLines.length - 2;

			Assert.assertEquals(
				"</log4j:properties>", outputLines[propertiesCloseLineIndex]);

			int propertiesOpenLineIndex = -1;

			for (int i = propertiesCloseLineIndex - 1; i >= 0; i--) {
				if (Objects.equals(outputLines[i], "<log4j:properties>")) {
					propertiesOpenLineIndex = i;

					break;
				}
			}

			Assert.assertTrue(
				"<log4j:properties> should be present",
				propertiesOpenLineIndex >= 0);

			StringBundler sb = new StringBundler();

			sb.append(StringPool.OPEN_CURLY_BRACE);

			for (int i = propertiesOpenLineIndex + 1;
				 i < propertiesCloseLineIndex; i++) {

				String dataLine = outputLines[i];

				int nameStart =
					dataLine.indexOf("name=\"") + "name=\"".length();

				int nameEnd = dataLine.indexOf(StringPool.QUOTE, nameStart);

				int valueStart =
					dataLine.indexOf("value=\"", nameEnd) + "value=\"".length();

				int valueEnd = dataLine.indexOf(StringPool.QUOTE, valueStart);

				if (i > (propertiesOpenLineIndex + 1)) {
					sb.append(", ");
				}

				sb.append(dataLine.substring(nameStart, nameEnd));
				sb.append(StringPool.EQUAL);
				sb.append(dataLine.substring(valueStart, valueEnd));
			}

			sb.append(StringPool.CLOSE_CURLY_BRACE);

			Assert.assertEquals(expectedLogContextMessage, sb.toString());

			locationInfoLineIndex = propertiesOpenLineIndex - 1;
		}

		// <log4j:locationInfo />

		String log4JLocationInfoLine = outputLines[locationInfoLineIndex];

		String log4JLocationInfo = log4JLocationInfoLine.substring(
			log4JLocationInfoLine.indexOf(StringPool.SPACE),
			log4JLocationInfoLine.indexOf(StringPool.FORWARD_SLASH));

		// <log4j:locationInfo class="..." />

		String expectedLog4JLocationInfoClassName = StringBundler.concat(
			" class=\"", PortalLog4jTest.class.getName(), "\" ");

		Assert.assertEquals(
			expectedLog4JLocationInfoClassName,
			log4JLocationInfo.substring(
				0, expectedLog4JLocationInfoClassName.length()));

		// <log4j:locationInfo file="..." />

		log4JLocationInfo = log4JLocationInfo.substring(
			expectedLog4JLocationInfoClassName.length());
		log4JLocationInfo = log4JLocationInfo.substring(
			log4JLocationInfo.indexOf("file"));

		String expectedLog4JLocationInfoFile = StringBundler.concat(
			"file=\"", PortalLog4jTest.class.getSimpleName(), ".java\"");

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

		_testLogOutput(level, message, throwable, null, null);
	}

	private void _testLogOutput(
			String level, String message, Throwable throwable,
			String expectedXmlMessage, String expectedXmlThread)
		throws Exception {

		_outputLog(level, message, throwable);

		try {
			_assertTextLog(
				level, message, throwable, _unsyncStringWriter.toString());

			_assertTextLog(
				level, message, throwable,
				new String(Files.readAllBytes(_textLogFilePath)));

			_assertXmlLog(
				level, message, throwable, null, expectedXmlMessage, null,
				expectedXmlThread,
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

	private void _testLogOutputWithLogContext(
			Map<String, String> contexts, String logContextMessage,
			String logContextName)
		throws Exception {

		_testLogOutputWithLogContext(
			contexts, logContextMessage, logContextMessage, null, null,
			logContextName);
	}

	private void _testLogOutputWithLogContext(
			Map<String, String> contexts, String logContextMessage,
			String expectedXmlLogContextMessage, String expectedXmlNdc,
			String expectedXmlThread, String logContextName)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(PortalLog4jTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<LogContext> serviceRegistration =
			bundleContext.registerService(
				LogContext.class,
				new LogContext() {

					@Override
					public Map<String, String> getContext(String logName) {
						return contexts;
					}

					@Override
					public String getName() {
						return logContextName;
					}

				},
				new HashMapDictionary());

		PatternLayout.Builder patternLayoutBuilder = PatternLayout.newBuilder();

		patternLayoutBuilder.withPattern(
			"%d{yyyy-MM-dd HH:mm:ss.SSS} %-5p [%t][%c{1}:%L] %m%n %X");

		UnsyncStringWriter textUnsyncStringWriter = new UnsyncStringWriter();

		Appender textLogContextWriterAppender = WriterAppender.createAppender(
			patternLayoutBuilder.build(), null, textUnsyncStringWriter,
			"textLogContextWriterAppender", false, false);

		textLogContextWriterAppender.start();

		Logger rootLogger = (Logger)LogManager.getRootLogger();

		Map<String, Appender> rootAppenders = rootLogger.getAppenders();

		Appender xmlFileAppender = rootAppenders.get("XML_FILE");

		Class<?> liferayXmlLayoutClass = xmlFileAppender.getLayout(
		).getClass();

		Object liferayXmlLayoutBuilder = ReflectionTestUtil.invoke(
			liferayXmlLayoutClass, "newBuilder", new Class<?>[0]);

		ReflectionTestUtil.setFieldValue(
			liferayXmlLayoutBuilder, "_locationInfo", true);
		ReflectionTestUtil.setFieldValue(
			liferayXmlLayoutBuilder, "_properties", true);

		StringLayout liferayXmlLayout = ReflectionTestUtil.invoke(
			liferayXmlLayoutBuilder, "build", new Class<?>[0]);

		UnsyncStringWriter xmlUnsyncStringWriter = new UnsyncStringWriter();

		Appender xmlLogContextWriterAppender = WriterAppender.createAppender(
			liferayXmlLayout, null, xmlUnsyncStringWriter,
			"xmlLogContextWriterAppender", false, false);

		xmlLogContextWriterAppender.start();

		Logger logger = (Logger)LogManager.getLogger(PortalLog4jTest.class);

		logger.addAppender(textLogContextWriterAppender);
		logger.addAppender(xmlLogContextWriterAppender);

		try {
			_testLogOutputWithLogContext(
				"DEBUG", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
			_testLogOutputWithLogContext(
				"ERROR", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
			_testLogOutputWithLogContext(
				"FATAL", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
			_testLogOutputWithLogContext(
				"INFO", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
			_testLogOutputWithLogContext(
				"TRACE", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
			_testLogOutputWithLogContext(
				"WARN", logContextMessage, expectedXmlLogContextMessage,
				expectedXmlNdc, expectedXmlThread, textUnsyncStringWriter,
				xmlUnsyncStringWriter);
		}
		finally {
			serviceRegistration.unregister();

			logger.removeAppender(textLogContextWriterAppender);
			logger.removeAppender(xmlLogContextWriterAppender);

			_unsyncStringWriter.reset();

			Files.write(
				_textLogFilePath, new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(
				_xmlLogFilePath, new byte[0],
				StandardOpenOption.TRUNCATE_EXISTING);
		}
	}

	private void _testLogOutputWithLogContext(
		String level, String logContextMessage,
		String expectedXmlLogContextMessage, String expectedXmlNdc,
		String expectedXmlThread, UnsyncStringWriter textUnsyncStringWriter,
		UnsyncStringWriter xmlUnsyncStringWriter) {

		String message = level + " message";

		_outputLog(level, message, null);

		_assertTextLog(
			level, message, null, logContextMessage,
			textUnsyncStringWriter.toString());
		_assertXmlLog(
			level, message, null, expectedXmlLogContextMessage, null,
			expectedXmlNdc, expectedXmlThread,
			xmlUnsyncStringWriter.toString());

		textUnsyncStringWriter.reset();
		xmlUnsyncStringWriter.reset();
	}

	private static final int _BUFFER_SIZE = 8192;

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalLog4jTest.class);

	private static final Pattern _datePattern = Pattern.compile(
		"\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d");
	private static Path _tempLogFileDirPath;
	private static TestOutputStream _testOutputStream;
	private static Path _textLogFilePath;
	private static final UnsyncStringWriter _unsyncStringWriter =
		new UnsyncStringWriter();
	private static Path _xmlLogFilePath;

	private static class TestOutputStream extends CloseShieldOutputStream {

		public TestOutputStream(OutputStream originalOutputStream) {
			super(originalOutputStream);
		}

		@Override
		public void write(byte[] bytes) throws IOException {
			_unsyncStringWriter.write(new String(bytes));
		}

		@Override
		public void write(byte[] bytes, int offset, int length)
			throws IOException {

			_unsyncStringWriter.write(new String(bytes), offset, length);
		}

		@Override
		public void write(int b) throws IOException {
			_unsyncStringWriter.write(b);
		}

	}

	private class TestException extends Exception {
	}

}