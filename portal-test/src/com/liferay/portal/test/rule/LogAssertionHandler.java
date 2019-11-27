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

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author William Newbury
 */
public class LogAssertionHandler extends Handler {

	public static final LogAssertionHandler INSTANCE =
		new LogAssertionHandler();

	@Override
	public void close() throws SecurityException {
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(LogRecord logRecord) {
		Level level = logRecord.getLevel();

		if (level.equals(Level.SEVERE)) {
			StringBundler sb = new StringBundler(6);

			sb.append("{level=");
			sb.append(logRecord.getLevel());
			sb.append(", loggerName=");
			sb.append(logRecord.getLoggerName());
			sb.append(", message=");
			sb.append(logRecord.getMessage());

			LogAssertionTestRule.caughtFailure(
				new AssertionError(sb.toString(), logRecord.getThrown()));
		}
	}

}