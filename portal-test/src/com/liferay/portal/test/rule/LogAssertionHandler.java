/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringBundler;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author William Newbury
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
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
			LogAssertionTestRule.caughtFailure(
				new AssertionError(
					StringBundler.concat(
						"{level=", logRecord.getLevel(), ", loggerName=",
						logRecord.getLoggerName(), ", message=",
						logRecord.getMessage()),
					logRecord.getThrown()));
		}
	}

}