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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;

/**
 * @author William Newbury
 */
public class LogAssertionAppender extends AppenderSkeleton {

	public LogAssertionAppender(Logger logger) {
		_logger = logger;

		_level = logger.getLevel();

		logger.setLevel(Level.WARN);
	}

	@Override
	public void close() {
		_logger.setLevel(_level);

		_logger.removeAppender(this);
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent loggingEvent) {
		if (loggingEvent.getLevel().equals(Level.WARN) ||
			loggingEvent.getLevel().equals(Level.ERROR) ||
			loggingEvent.getLevel().equals(Level.FATAL)) {

			Assert.fail(
				"Method failed due to logged error or warning: " +
				loggingEvent.getMessage());
		}
	}

	private Level _level;
	private Logger _logger;

}