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

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Closeable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author Shuyang Zhou
 */
public class CaptureAppender extends AppenderSkeleton implements Closeable {

	public CaptureAppender(Logger logger) {
		_logger = logger;

		_level = _logger.getLevel();

		_parentCategory = logger.getParent();

		try {
			_parentField.set(_logger, null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		closed = true;

		_logger.removeAppender(this);

		_logger.setLevel(_level);

		try {
			_parentField.set(_logger, _parentCategory);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<LoggingEvent> getLoggingEvents() {
		return _loggingEvents;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent loggingEvent) {
		_loggingEvents.add(
			new PrintableLoggingEvent(
				loggingEvent.getFQNOfLoggerClass(), loggingEvent.getLogger(),
				loggingEvent.getTimeStamp(), loggingEvent.getLevel(),
				loggingEvent.getMessage(), loggingEvent.getThreadName(),
				loggingEvent.getThrowableInformation(), loggingEvent.getNDC(),
				loggingEvent.getLocationInformation(),
				loggingEvent.getProperties()));
	}

	private static final Field _parentField;

	static {
		try {
			_parentField = ReflectionUtil.getDeclaredField(
				Category.class, "parent");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final Level _level;
	private final Logger _logger;
	private final List<LoggingEvent> _loggingEvents =
		new CopyOnWriteArrayList<>();
	private final Category _parentCategory;

	private static class PrintableLoggingEvent extends LoggingEvent {

		@Override
		public String toString() {
			StringBundler sb = new StringBundler();

			sb.append('{');

			Level level = getLevel();

			if (level == null) {
				sb.append("No Level Found");
			}
			else {
				sb.append(level.toString());
			}

			sb.append(": ");

			String message = (String)getMessage();

			if (message == null) {
				sb.append("No Message Found");
			}
			else {
				sb.append(message);
			}

			sb.append('}');

			return sb.toString();
		}

		private PrintableLoggingEvent(
			String fqnOfCategoryClass, Category logger, long timeStamp,
			Level level, Object message, String threadName,
			ThrowableInformation throwable, String ndc, LocationInfo info,
			Map properties) {

			super(
				fqnOfCategoryClass, logger, timeStamp, level, message,
				threadName, throwable, ndc, info, properties);
		}

	}

}