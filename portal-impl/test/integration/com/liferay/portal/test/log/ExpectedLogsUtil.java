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

import com.liferay.portal.log.CaptureAppender;
import com.liferay.portal.log.Log4JLoggerTestUtil;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class ExpectedLogsUtil {

	public static void endAssert(
		Method method, CaptureAppender captureAppender) {

		ExpectedLogs expectedLogs = method.getAnnotation(ExpectedLogs.class);

		if (expectedLogs == null) {
			return;
		}

		try {
			loggingEvent:
			for (LoggingEvent loggingEvent :
					captureAppender.getLoggingEvents()) {

				String eventMessage = loggingEvent.getRenderedMessage();

				for (ExpectedLog expectedLog : expectedLogs.expectedLogs()) {
					ExpectedType expectedType = expectedLog.expectedType();

					if (expectedType == ExpectedType.EXACT) {
						if (eventMessage.equals(expectedLog.expectedLog())) {
							continue loggingEvent;
						}
					}
					else if (expectedType == ExpectedType.POSTFIX) {
						if (eventMessage.endsWith(expectedLog.expectedLog())) {
							continue loggingEvent;
						}
					}
					else if (expectedType == ExpectedType.PREFIX) {
						if (eventMessage.startsWith(
								expectedLog.expectedLog())) {

							continue loggingEvent;
						}
					}
				}

				Assert.fail(eventMessage);
			}
		}
		finally {
			captureAppender.close();
		}
	}

	public static CaptureAppender startAssert(Method method) {
		ExpectedLogs expectedLogs = method.getAnnotation(ExpectedLogs.class);

		if (expectedLogs == null) {
			return null;
		}

		Class<?> clazz = expectedLogs.loggerClass();

		return Log4JLoggerTestUtil.configureLog4JLogger(
			clazz.getName(), Level.toLevel(expectedLogs.level()));
	}

}