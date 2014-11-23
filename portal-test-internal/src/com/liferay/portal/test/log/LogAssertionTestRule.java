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

package com.liferay.portal.test.log;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.log.CaptureAppender;
import com.liferay.portal.log.Log4JLoggerTestUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class LogAssertionTestRule implements TestRule {

	public static final LogAssertionTestRule INSTANCE =
		new LogAssertionTestRule();

	public static void caughtFailure(Error error) {
		Thread currentThread = Thread.currentThread();

		if (currentThread != _thread) {
			_concurrentFailures.put(currentThread, error);

			_thread.interrupt();
		}
		else {
			throw error;
		}
	}

	@Override
	public Statement apply(
		final Statement statement, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ExpectedLogs expectedLogs = description.getAnnotation(
					ExpectedLogs.class);

				CaptureAppender captureAppender = startAssert(expectedLogs);

				try {
					statement.evaluate();
				}
				finally {
					endAssert(expectedLogs, captureAppender);
				}
			}

		};
	}

	protected static void endAssert(
		ExpectedLogs expectedLogs, CaptureAppender captureAppender) {

		if (expectedLogs != null) {
			try {
				for (LoggingEvent loggingEvent :
						captureAppender.getLoggingEvents()) {

					String renderedMessage = loggingEvent.getRenderedMessage();

					if (!isExpected(expectedLogs, renderedMessage)) {
						Assert.fail(renderedMessage);
					}
				}
			}
			finally {
				captureAppender.close();
			}
		}

		_thread = null;

		try {
			for (Map.Entry<Thread, Error> entry :
					_concurrentFailures.entrySet()) {

				Thread thread = entry.getKey();
				Error error = entry.getValue();

				Assert.fail(
					"Thread " + thread + " caught concurrent failure: " +
						error);

				throw error;
			}
		}
		finally {
			_concurrentFailures.clear();
		}
	}

	protected static void installJdk14Handler() {
		Logger logger = Logger.getLogger(StringPool.BLANK);

		logger.removeHandler(LogAssertionHandler.INSTANCE);

		logger.addHandler(LogAssertionHandler.INSTANCE);
	}

	protected static void installLog4jAppender() {
		org.apache.log4j.Logger logger =
			org.apache.log4j.Logger.getRootLogger();

		logger.removeAppender(LogAssertionAppender.INSTANCE);

		logger.addAppender(LogAssertionAppender.INSTANCE);
	}

	protected static boolean isExpected(
		ExpectedLogs expectedLogs, String renderedMessage) {

		for (ExpectedLog expectedLog : expectedLogs.expectedLogs()) {
			ExpectedType expectedType = expectedLog.expectedType();

			if (expectedType == ExpectedType.EXACT) {
				if (renderedMessage.equals(expectedLog.expectedLog())) {
					return true;
				}
			}
			else if (expectedType == ExpectedType.POSTFIX) {
				if (renderedMessage.endsWith(expectedLog.expectedLog())) {
					return true;
				}
			}
			else if (expectedType == ExpectedType.PREFIX) {
				if (renderedMessage.startsWith(expectedLog.expectedLog())) {
					return true;
				}
			}
		}

		return false;
	}

	protected static CaptureAppender startAssert(ExpectedLogs expectedLogs) {
		_thread = Thread.currentThread();

		CaptureAppender captureAppender = null;

		if (expectedLogs != null) {
			Class<?> clazz = expectedLogs.loggerClass();

			captureAppender = Log4JLoggerTestUtil.configureLog4JLogger(
				clazz.getName(), Level.toLevel(expectedLogs.level()));
		}

		installJdk14Handler();
		installLog4jAppender();

		return captureAppender;
	}

	private LogAssertionTestRule() {
	}

	private static final Map<Thread, Error> _concurrentFailures =
		new ConcurrentHashMap<Thread, Error>();
	private static volatile Thread _thread;

}