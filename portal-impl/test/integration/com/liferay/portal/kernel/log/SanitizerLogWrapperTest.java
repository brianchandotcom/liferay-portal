/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.CaptureAppender;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Log4JLoggerTestUtil;

import java.lang.reflect.Field;

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
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tomas Polesovsky
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SanitizerLogWrapperTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		char[] chars = new char[128];

		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char)i;
		}

		_message = new String(chars);

		String sanitizedMessageSuffix = " [Sanitized]";

		_expectedMessageChars =
			new char[chars.length + sanitizedMessageSuffix.length()];

		for (int i = 0; i < chars.length; i++) {
			if ((i == 9) || ((i >= 32) && (i != 127))) {
				_expectedMessageChars[i] = (char)i;
			}
			else {
				_expectedMessageChars[i] = CharPool.UNDERLINE;
			}
		}

		System.arraycopy(
			sanitizedMessageSuffix.toCharArray(), 0, _expectedMessageChars,
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

		Field field = ReflectionUtil.getDeclaredField(
			SanitizerLogWrapper.class, "_LOG_SANITIZER_ENABLED");

		field.set(null, true);

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

				char[] sanitizedMessageChars =
					new char[line.length() - exceptionPrefix.length()];

				line.getChars(
					exceptionPrefix.length(), line.length(),
					sanitizedMessageChars, 0);

				Assert.assertArrayEquals(
					_expectedMessageChars, sanitizedMessageChars);
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

				char[] sanitizedMessageChars = message.toCharArray();

				Assert.assertArrayEquals(
					_expectedMessageChars, sanitizedMessageChars);
			}
		}
		finally {
			_captureAppender.close();
		}
	}

	private static Log _log;

	private static char[] _expectedMessageChars;
	private static String _message;
	private static Properties _systemProperties;

	private CaptureAppender _captureAppender;

}