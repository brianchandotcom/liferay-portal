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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 */
public class SanitizerLogWrapperTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		char[] chars = new char[128];

		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char)i;
		}

		_message = new String(chars);

		String sanitizedMessageSuffix = " [Sanitized]";

		_messageChars =
			new char[chars.length + sanitizedMessageSuffix.length()];

		for (int i = 0; i < chars.length; i++) {
			if ((i == 9) || ((i >= 32) && (i != 127))) {
				_messageChars[i] = (char)i;
			}
			else {
				_messageChars[i] = CharPool.UNDERLINE;
			}
		}

		System.arraycopy(
			sanitizedMessageSuffix.toCharArray(), 0, _messageChars,
			chars.length, sanitizedMessageSuffix.length());

		_systemProperties = new Properties(System.getProperties());

		System.setProperty("log.sanitizer.enabled", "true");
		System.setProperty("log.sanitizer.escape.html.enabled", "false");
		System.setProperty("log.sanitizer.replacement.character", "95");

		StringBundler sb = new StringBundler(191);

		sb.append("9,");

		for (int i = 32; i <= 126; i++) {
			sb.append(i);
			sb.append(",");
		}

		System.setProperty("log.sanitizer.whitelist.characters", sb.toString());

		ReflectionTestUtil.setFieldValue(
			SanitizerLogWrapper.class, "_LOG_SANITIZER_ENABLED", true);

		SanitizerLogWrapper.init();
	}

	@AfterClass
	public static void tearDownClass() {
		System.setProperties(_systemProperties);
	}

	@Before
	public void setUp() {
		String loggerName = "test.logger";

		_captureAppender = Log4JLoggerTestUtil.configureLog4JLogger(
			loggerName, Level.ALL);

		LogFactory logFactory = LogFactoryUtil.getLogFactory();

		_log = new SanitizerLogWrapper(logFactory.getLog(loggerName));
	}

	@After
	public void tearDown() {
		_captureAppender.close();
	}

	@Test
	public void testAllowCRLF() {
		Exception exception = new NullPointerException();

		char[] expectedMessageWithCRLFChars = new char[_messageChars.length];

		System.arraycopy(
			_messageChars, 0, expectedMessageWithCRLFChars, 0,
			expectedMessageWithCRLFChars.length);

		expectedMessageWithCRLFChars[CharPool.NEW_LINE] = CharPool.NEW_LINE;
		expectedMessageWithCRLFChars[CharPool.RETURN] = CharPool.RETURN;

		Log log = SanitizerLogWrapper.allowCRLF(_log);

		try {
			log.debug(_message);
			log.debug(_message, exception);
			log.error(_message);
			log.error(_message, exception);
			log.fatal(_message);
			log.fatal(_message, exception);
			log.info(_message);
			log.info(_message, exception);
			log.trace(_message);
			log.trace(_message, exception);
			log.warn(_message);
			log.warn(_message, exception);

			List<LoggingEvent> loggingEvents =
				_captureAppender.getLoggingEvents();

			Assert.assertNotNull(loggingEvents);
			Assert.assertEquals(12, loggingEvents.size());

			for (LoggingEvent loggingEvent : loggingEvents) {
				String message = loggingEvent.getRenderedMessage();

				Assert.assertTrue(
					message.startsWith(SanitizerLogWrapper.CRLF_WARNING));

				int messageWithCRLFCharsLength =
					message.length() -
						SanitizerLogWrapper.CRLF_WARNING.length();

				char[] messageWithCRLFChars =
					new char[messageWithCRLFCharsLength];

				message.getChars(
					SanitizerLogWrapper.CRLF_WARNING.length(), message.length(),
					messageWithCRLFChars, 0);

				Assert.assertArrayEquals(
					expectedMessageWithCRLFChars, messageWithCRLFChars);
			}

			loggingEvents.clear();

			_log.debug(_message);
			_log.debug(_message, exception);
			_log.error(_message);
			_log.error(_message, exception);
			_log.fatal(_message);
			_log.fatal(_message, exception);
			_log.info(_message);
			_log.info(_message, exception);
			_log.trace(_message);
			_log.trace(_message, exception);
			_log.warn(_message);
			_log.warn(_message, exception);

			Assert.assertNotNull(loggingEvents);
			Assert.assertEquals(12, loggingEvents.size());

			for (LoggingEvent loggingEvent : loggingEvents) {
				String message = loggingEvent.getRenderedMessage();

				char[] messageChars = message.toCharArray();

				Assert.assertArrayEquals(_messageChars, messageChars);
			}
		}
		finally {
			_captureAppender.close();
		}
	}

	@Test
	public void testInvalidCharactersInExceptionMessage() {
		Exception exception = new Exception(
			new RuntimeException(new NullPointerException(_message)));

		String exceptionPrefix =
			"java.lang.Exception: java.lang.RuntimeException: " +
				"java.lang.NullPointerException: ";

		try {
			_log.debug(exception);
			_log.debug(null, exception);
			_log.error(exception);
			_log.error(null, exception);
			_log.fatal(exception);
			_log.fatal(null, exception);
			_log.info(exception);
			_log.info(null, exception);
			_log.trace(exception);
			_log.trace(null, exception);
			_log.warn(exception);
			_log.warn(null, exception);

			List<LoggingEvent> loggingEvents =
				_captureAppender.getLoggingEvents();

			Assert.assertNotNull(loggingEvents);
			Assert.assertEquals(12, loggingEvents.size());

			for (LoggingEvent loggingEvent : loggingEvents) {
				ThrowableInformation throwableInformation =
					loggingEvent.getThrowableInformation();

				String line = throwableInformation.getThrowableStrRep()[0];

				Assert.assertTrue(line.startsWith(exceptionPrefix));

				char[] messageChars =
					new char[line.length() - exceptionPrefix.length()];

				line.getChars(
					exceptionPrefix.length(), line.length(), messageChars, 0);

				Assert.assertArrayEquals(_messageChars, messageChars);
			}
		}
		finally {
			_captureAppender.close();
		}
	}

	@Test
	public void testInvalidCharactersInLogMessage() {
		Exception exception = new NullPointerException();

		try {
			_log.debug(_message);
			_log.debug(_message, exception);
			_log.error(_message);
			_log.error(_message, exception);
			_log.fatal(_message);
			_log.fatal(_message, exception);
			_log.info(_message);
			_log.info(_message, exception);
			_log.trace(_message);
			_log.trace(_message, exception);
			_log.warn(_message);
			_log.warn(_message, exception);

			List<LoggingEvent> loggingEvents =
				_captureAppender.getLoggingEvents();

			Assert.assertNotNull(loggingEvents);
			Assert.assertEquals(12, loggingEvents.size());

			for (LoggingEvent loggingEvent : loggingEvents) {
				String message = loggingEvent.getRenderedMessage();

				char[] messageChars = message.toCharArray();

				Assert.assertArrayEquals(_messageChars, messageChars);
			}
		}
		finally {
			_captureAppender.close();
		}
	}

	private static Log _log;

	private static String _message;
	private static char[] _messageChars;
	private static Properties _systemProperties;

	private CaptureAppender _captureAppender;

}