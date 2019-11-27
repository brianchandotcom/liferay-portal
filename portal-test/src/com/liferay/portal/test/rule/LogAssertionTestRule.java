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

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import java.util.ArrayList;
import java.util.Collections;
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
public class LogAssertionTestRule
	extends AbstractTestRule<List<CaptureAppender>, List<CaptureAppender>> {

	public static final LogAssertionTestRule INSTANCE =
		new LogAssertionTestRule();

	public static void caughtFailure(Error error) {
		Thread currentThread = Thread.currentThread();

		if (currentThread == _thread) {
			throw error;
		}

		Error previousError = _concurrentFailures.put(currentThread, error);

		if (previousError != null) {
			error.addSuppressed(previousError);
		}
	}

	public static void endAssert(
		List<ExpectedLogs> expectedLogsList,
		List<CaptureAppender> captureAppenders) {

		StringBundler sb = new StringBundler();

		for (CaptureAppender captureAppender : captureAppenders) {
			try {
				for (LoggingEvent loggingEvent :
						captureAppender.getLoggingEvents()) {

					String renderedMessage = loggingEvent.getRenderedMessage();

					if (!isExpected(expectedLogsList, renderedMessage)) {
						sb.append(renderedMessage);
						sb.append("\n\n");
					}
				}
			}
			finally {
				captureAppender.close();
			}
		}

		if (sb.index() != 0) {
			sb.setIndex(sb.index() - 1);

			Assert.fail(sb.toString());
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

				sb.append("Thread ");
				sb.append(thread);
				sb.append(" caught concurrent failure: ");
				sb.append(error);
				sb.append("\n");
				sb.append(unsyncStringWriter.toString());
				sb.append("\n\n");
			}

			if (sb.index() != 0) {
				sb.setIndex(sb.index() - 1);

				Assert.fail(sb.toString());
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
			Collections.addAll(
				expectedLogsList, expectedMultipleLogs.expectedMultipleLogs());
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
			Collections.addAll(
				expectedLogsList, expectedMultipleLogs.expectedMultipleLogs());
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

	private LogAssertionTestRule() {
	}

	private static final Map<Thread, Error> _concurrentFailures =
		new ConcurrentHashMap<>();
	private static volatile Thread _thread;
	private static volatile Thread.UncaughtExceptionHandler
		_uncaughtExceptionHandler;

}