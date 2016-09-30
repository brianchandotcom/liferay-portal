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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.test.rule.callback.TestCallback;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedMultipleLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LogAssertionAppender;
import com.liferay.portal.test.rule.LogAssertionHandler;
import com.liferay.portal.test.rule.LogAssertionUncaughtExceptionHandler;

import java.lang.Thread.UncaughtExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class LogAssertionTestCallback
	implements TestCallback<List<CaptureAppender>, List<CaptureAppender>> {

	public static final LogAssertionTestCallback INSTANCE =
		new LogAssertionTestCallback();

	public static void caughtFailure(Error error) {
		Thread currentThread = Thread.currentThread();

		if (currentThread != _thread) {
			_concurrentFailures.put(currentThread, error);
		}
		else {
			throw error;
		}
	}

	public static void endAssert(
		List<ExpectedLogs> expectedLogsList,
		List<CaptureAppender> captureAppenders) {

		for (CaptureAppender captureAppender : captureAppenders) {
			try {
				for (LoggingEvent loggingEvent :
						captureAppender.getLoggingEvents()) {

					String renderedMessage = loggingEvent.getRenderedMessage();

					if (!isExpected(expectedLogsList, renderedMessage)) {
						Assert.fail(renderedMessage);
					}
				}
			}
			finally {
				captureAppender.close();
			}
		}

		Thread.setDefaultUncaughtExceptionHandler(_uncaughtExceptionHandler);

		_thread = null;

		try {
			for (Map.Entry<Thread, Error> entry :
					_concurrentFailures.entrySet()) {

				Thread thread = entry.getKey();
				Error error = entry.getValue();

				UnsyncStringWriter unsyncStringWriter =
					new UnsyncStringWriter();

				error.printStackTrace(
					new UnsyncPrintWriter(unsyncStringWriter));

				Assert.fail(
					"Thread " + thread + " caught concurrent failure: " +
						error + "\n" + unsyncStringWriter.toString());

				throw error;
			}
		}
		finally {
			_concurrentFailures.clear();
		}
	}

	public static List<CaptureAppender> startAssert(
		List<ExpectedLogs> expectedLogsList) {

		_thread = Thread.currentThread();
		_uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(
			new LogAssertionUncaughtExceptionHandler(
				_uncaughtExceptionHandler));

		List<CaptureAppender> captureAppenders = new ArrayList<>(
			expectedLogsList.size());

		for (ExpectedLogs expectedLogs : expectedLogsList) {
			Class<?> clazz = expectedLogs.loggerClass();

			captureAppenders.add(
				Log4JLoggerTestUtil.configureLog4JLogger(
					clazz.getName(), Level.toLevel(expectedLogs.level())));
		}

		installJdk14Handler();
		installLog4jAppender();

		return captureAppenders;
	}

	@Override
	public void afterClass(
		Description description, List<CaptureAppender> captureAppenders) {

		ExpectedMultipleLogs expectedMultipleLogs = description.getAnnotation(
			ExpectedMultipleLogs.class);

		List<ExpectedLogs> expectedLogsList = new ArrayList<>();

		if (expectedMultipleLogs == null) {
			ExpectedLogs expectedLogs = description.getAnnotation(
				ExpectedLogs.class);

			if (expectedLogs != null) {
				expectedLogsList.add(expectedLogs);
			}
		}
		else {
			expectedLogsList.addAll(
				Arrays.asList(expectedMultipleLogs.expectedMultipleLogs()));
		}

		endAssert(expectedLogsList, captureAppenders);
	}

	@Override
	public void afterMethod(
		Description description, List<CaptureAppender> captureAppenders,
		Object target) {

		afterClass(description, captureAppenders);
	}

	@Override
	public List<CaptureAppender> beforeClass(Description description) {
		ExpectedMultipleLogs expectedMultipleLogs = description.getAnnotation(
			ExpectedMultipleLogs.class);

		List<ExpectedLogs> expectedLogsList = new ArrayList<>();

		if (expectedMultipleLogs == null) {
			ExpectedLogs expectedLogs = description.getAnnotation(
				ExpectedLogs.class);

			if (expectedLogs != null) {
				expectedLogsList.add(expectedLogs);
			}
		}
		else {
			expectedLogsList.addAll(
				Arrays.asList(expectedMultipleLogs.expectedMultipleLogs()));
		}

		return startAssert(expectedLogsList);
	}

	@Override
	public List<CaptureAppender> beforeMethod(
		Description description, Object target) {

		return beforeClass(description);
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
		List<ExpectedLogs> expectedLogsList, String renderedMessage) {

		for (ExpectedLogs expectedLogs : expectedLogsList) {
			for (ExpectedLog expectedLog : expectedLogs.expectedLogs()) {
				ExpectedDBType expectedDBType = expectedLog.expectedDBType();

				if (expectedDBType != ExpectedDBType.NONE) {
					DB db = DBManagerUtil.getDB();

					if (expectedDBType.getDBType() != db.getDBType()) {
						continue;
					}
				}

				ExpectedType expectedType = expectedLog.expectedType();

				if (expectedType == ExpectedType.CONTAINS) {
					if (renderedMessage.contains(expectedLog.expectedLog())) {
						return true;
					}
				}
				else if (expectedType == ExpectedType.EXACT) {
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
		}

		return false;
	}

	private LogAssertionTestCallback() {
	}

	private static final Map<Thread, Error> _concurrentFailures =
		new ConcurrentHashMap<>();
	private static volatile Thread _thread;
	private static volatile UncaughtExceptionHandler _uncaughtExceptionHandler;

}