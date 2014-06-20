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

import org.junit.Assert;

/**
 * @author William Newbury
 */
public class LogAssertionHandler extends Handler {

	public LogAssertionHandler(Logger logger) {
		_logger = logger;

		_level = logger.getLevel();

		logger.setLevel(Level.WARNING);
	}

	@Override
	public void close() throws SecurityException {
		_logRecords.clear();

		_logger.setLevel(_level);

		_logger.removeHandler(this);
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
		if (logRecord.getLevel().equals(Level.WARNING) ||
			logRecord.getLevel().equals(Level.SEVERE)) {

			Assert.fail(
				"Method failed due to logged error or warning: " +
				logRecord.getMessage());
		}
	}

	private Level _level;
	private Logger _logger;
	private List<LogRecord> _logRecords = new CopyOnWriteArrayList<LogRecord>();

}