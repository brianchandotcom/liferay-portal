/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.log;

import com.liferay.petra.reflect.ReflectionUtil;

import java.io.Closeable;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             LoggerTestUtil.Log4JLogCapture}
 */
@Deprecated
public class CaptureAppender extends AppenderSkeleton implements Closeable {

	public CaptureAppender(Logger logger) {
		_logger = logger;

		_level = logger.getLevel();

		_parentCategory = logger.getParent();

		try {
			_parentField.set(_logger, null);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
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
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public List<LogEvent> getLogEvents() {
		return _logEvents;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getLogEvents()}
	 */
	@Deprecated
	public List<LoggingEvent> getLoggingEvents() {
		List<LoggingEvent> loggingEvents = new ArrayList<>();

		for (LogEvent logEvent : _logEvents) {
			loggingEvents.add((LoggingEvent)logEvent.getWrappedObject());
		}

		return loggingEvents;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent loggingEvent) {
		_logEvents.add(new LogEvent(loggingEvent));
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

	private final Level _level;
	private final List<LogEvent> _logEvents = new CopyOnWriteArrayList<>();
	private final Logger _logger;
	private final Category _parentCategory;

}