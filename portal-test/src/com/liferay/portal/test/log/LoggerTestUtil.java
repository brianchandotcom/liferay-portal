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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.lang.reflect.Field;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Category;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author Shuyang Zhou
 */
public class LoggerTestUtil {

	public static final String ALL = String.valueOf(org.apache.log4j.Level.ALL);

	public static final String DEBUG = String.valueOf(
		org.apache.log4j.Level.DEBUG);

	public static final String ERROR = String.valueOf(
		org.apache.log4j.Level.ERROR);

	public static final String FATAL = String.valueOf(
		org.apache.log4j.Level.FATAL);

	public static final String INFO = String.valueOf(
		org.apache.log4j.Level.INFO);

	public static final String OFF = String.valueOf(org.apache.log4j.Level.OFF);

	public static final String TRACE = String.valueOf(
		org.apache.log4j.Level.TRACE);

	public static final String WARN = String.valueOf(
		org.apache.log4j.Level.WARN);

	public static LogCapture configureJDKLogger(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		if (!(log instanceof Jdk14LogImpl)) {
			throw new IllegalStateException(
				"Log " + name + " is not a JDK logger");
		}

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)log;

		Logger logger = jdk14LogImpl.getWrappedLogger();

		JDKLogCapture jdkLogCapture = new JDKLogCapture(logger, level);

		logger.addHandler(jdkLogCapture);

		return jdkLogCapture;
	}

	public static LogCapture configureLog4JLogger(
		String name, String priority) {

		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		org.apache.log4j.Logger logger = null;

		try {
			logger = ReflectionTestUtil.getFieldValue(log, "_logger");
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Log " + name + " is not a Log4j logger");
		}

		Log4JLogCapture log4JLogCapture = new Log4JLogCapture(logger);

		logger.addAppender(log4JLogCapture);

		logger.setLevel(org.apache.log4j.Level.toLevel(priority));

		return log4JLogCapture;
	}

	static {

		// See LPS-32051 and LPS-32471

		LogFactoryUtil.getLog(LoggerTestUtil.class);
	}

	private static class JDKLogCapture extends Handler implements LogCapture {

		@Override
		public void close() {
			_logEntries.clear();

			_logger.removeHandler(this);

			for (Handler handler : _handlers) {
				_logger.addHandler(handler);
			}

			_logger.setLevel(_level);
			_logger.setUseParentHandlers(_useParentHandlers);
		}

		@Override
		public void flush() {
			_logEntries.clear();
		}

		@Override
		public List<LogEntry> getLogEntries() {
			return _logEntries;
		}

		@Override
		public boolean isLoggable(LogRecord logRecord) {
			return false;
		}

		@Override
		public void publish(LogRecord logRecord) {
			_logEntries.add(
				new LogEntry(
					logRecord.getMessage(),
					String.valueOf(logRecord.getLevel()),
					logRecord.getThrown()));
		}

		@Override
		public List<LogEntry> resetPriority(String priority) {
			_logEntries.clear();

			_logger.setLevel(Level.parse(priority));

			return _logEntries;
		}

		private JDKLogCapture(Logger logger, Level level) {
			_logger = logger;

			_handlers = logger.getHandlers();
			_level = logger.getLevel();
			_useParentHandlers = logger.getUseParentHandlers();

			for (Handler handler : _handlers) {
				logger.removeHandler(handler);
			}

			logger.setLevel(level);
			logger.setUseParentHandlers(false);
		}

		private final Handler[] _handlers;
		private final Level _level;
		private final List<LogEntry> _logEntries = new CopyOnWriteArrayList<>();
		private final Logger _logger;
		private final boolean _useParentHandlers;

	}

	private static class Log4JLogCapture
		extends AppenderSkeleton implements LogCapture {

		@Override
		public void close() {
			closed = true;

			_logger.removeAppender(this);

			_logger.setLevel(_level);

			try {
				_parentField.set(_logger, _parentCategory);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public List<LogEntry> getLogEntries() {
			return _logEntries;
		}

		@Override
		public boolean requiresLayout() {
			return false;
		}

		@Override
		public List<LogEntry> resetPriority(String priority) {
			_logEntries.clear();

			_logger.setLevel(org.apache.log4j.Level.toLevel(priority));

			return _logEntries;
		}

		@Override
		protected void append(LoggingEvent loggingEvent) {
			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Throwable throwable = null;

			if (throwableInformation != null) {
				throwable = throwableInformation.getThrowable();
			}

			_logEntries.add(
				new LogEntry(
					loggingEvent.getRenderedMessage(),
					String.valueOf(loggingEvent.getLevel()), throwable));
		}

		private Log4JLogCapture(org.apache.log4j.Logger logger) {
			_logger = logger;

			_level = _logger.getLevel();

			_parentCategory = logger.getParent();

			try {
				_parentField.set(_logger, null);
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private static final Field _parentField;

		static {
			try {
				_parentField = ReflectionUtil.getDeclaredField(
					Category.class, "parent");
			}
			catch (Exception exception) {
				throw new ExceptionInInitializerError(exception);
			}
		}

		private final org.apache.log4j.Level _level;
		private final List<LogEntry> _logEntries = new CopyOnWriteArrayList<>();
		private final org.apache.log4j.Logger _logger;
		private final Category _parentCategory;

	}

}