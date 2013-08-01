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
import com.liferay.portal.test.CaptureAppender;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Log4JLoggerTestUtil;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tomas Polesovsky
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SanitizingLogWrapperTest {

	@BeforeClass
	public static void setUpClass() {
		char[] _messageChars = new char[128];

		for (int i = 0; i < _messageChars.length; i++) {
			_messageChars[i] = (char)i;
		}

		_message = new String(_messageChars);

		String sanitized = " [Sanitized]";

		_expectedChars = new char[_messageChars.length + sanitized.length()];

		for (int i = 0; i < _messageChars.length; i++) {
			if ((i == 9) || ((i >= 32) && (i != 127))) {
				_expectedChars[i] = (char)i;
			}
			else {
				_expectedChars[i] = CharPool.UNDERLINE;
			}
		}

		System.arraycopy(
			sanitized.toCharArray(), 0, _expectedChars, _messageChars.length,
			sanitized.length());
	}

	@Before
	public void setUp() {
		String loggerName = "test.logger";

		_captureAppender = Log4JLoggerTestUtil.configureLog4JLogger(
			loggerName, Level.ALL);

		_log = LogFactoryUtil.getLog(loggerName);
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

			List<LoggingEvent> events = _captureAppender.getLoggingEvents();

			Assert.assertNotNull(events);
			Assert.assertEquals(12, events.size());

			for (LoggingEvent event : events) {
				String stackTraceFirstLine =
					event.getThrowableInformation().getThrowableStrRep()[0];

				Assert.assertTrue(
					stackTraceFirstLine.startsWith(exceptionPrefix));

				char[] sanitizedMessageChars =
					new char[stackTraceFirstLine.length() -
						exceptionPrefix.length()];

				stackTraceFirstLine.getChars(
					exceptionPrefix.length(), stackTraceFirstLine.length(),
					sanitizedMessageChars, 0);

				Assert.assertArrayEquals(_expectedChars, sanitizedMessageChars);
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

			List<LoggingEvent> events = _captureAppender.getLoggingEvents();

			Assert.assertNotNull(events);
			Assert.assertEquals(12, events.size());

			for (LoggingEvent event : events) {
				char[] sanitizedMessageChars =
					event.getRenderedMessage().toString().toCharArray();

				Assert.assertArrayEquals(_expectedChars, sanitizedMessageChars);
			}
		}
		finally {
			_captureAppender.close();
		}
	}

	private static Log _log;

	private static char[] _expectedChars;
	private static String _message;

	private CaptureAppender _captureAppender;

}