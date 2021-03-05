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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link LoggerTestUtil}
 */
@Deprecated
public class Log4JLoggerTestUtil {

	public static final String ALL = String.valueOf(Level.ALL);

	public static final String DEBUG = String.valueOf(Level.DEBUG);

	public static final String ERROR = String.valueOf(Level.ERROR);

	public static final String FATAL = String.valueOf(Level.FATAL);

	public static final String INFO = String.valueOf(Level.INFO);

	public static final String OFF = String.valueOf(Level.OFF);

	public static final String TRACE = String.valueOf(Level.TRACE);

	public static final String WARN = String.valueOf(Level.WARN);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *            #configureLog4JLogger(String, String)}
	 */
	@Deprecated
	public static CaptureAppender configureLog4JLogger(
		String name, Level level) {

		return _configureLog4JLogger(name, level);
	}

	public static CaptureAppender configureLog4JLogger(
		String name, String priority) {

		return _configureLog4JLogger(name, Level.toLevel(priority));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static Level setLoggerLevel(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		Logger logger = null;

		try {
			logger = ReflectionTestUtil.getFieldValue(log, "_logger");
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Log " + name + " is not a Log4j logger");
		}

		Level oldLevel = logger.getLevel();

		logger.setLevel(level);

		return oldLevel;
	}

	private static CaptureAppender _configureLog4JLogger(
		String name, Level level) {

		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		Logger logger = null;

		try {
			logger = ReflectionTestUtil.getFieldValue(log, "_logger");
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Log " + name + " is not a Log4j logger");
		}

		CaptureAppender captureAppender = new CaptureAppender(logger);

		logger.addAppender(captureAppender);

		logger.setLevel(level);

		return captureAppender;
	}

}