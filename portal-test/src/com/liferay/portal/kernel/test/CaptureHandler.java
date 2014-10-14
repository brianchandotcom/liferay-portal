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

package com.liferay.portal.kernel.test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Shuyang Zhou
 */
public class CaptureHandler extends Handler {

	public CaptureHandler(Logger logger, Level level) {
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

	@Override
	public void close() {
		_logRecords.clear();

		_logger.removeHandler(this);

		for (Handler handler : _handlers) {
			_logger.addHandler(handler);
		}

		_logger.setLevel(_level);
		_logger.setUseParentHandlers(_useParentHandlers);
	}

	@Override
	public void flush() {
		_logRecords.clear();
	}

	public List<LogRecord> getLogRecords() {
		return _logRecords;
	}

	@Override
	public boolean isLoggable(LogRecord logRecord) {
		return false;
	}

	@Override
	public void publish(LogRecord logRecord) {
		_logRecords.add(logRecord);
	}

	public List<LogRecord> resetLogLevel(Level level) {
		_logRecords.clear();

		_logger.setLevel(level);

		return _logRecords;
	}

	private final Handler[] _handlers;
	private final Level _level;
	private final Logger _logger;
	private final List<LogRecord> _logRecords =
		new CopyOnWriteArrayList<LogRecord>();
	private final boolean _useParentHandlers;

}