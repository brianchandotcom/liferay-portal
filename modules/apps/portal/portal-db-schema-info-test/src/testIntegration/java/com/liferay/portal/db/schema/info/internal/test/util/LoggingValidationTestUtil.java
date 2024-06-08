/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.test.util;

import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.List;

import jodd.util.StringUtil;

import org.junit.Assert;

/**
 * @author Mariano Álvaro Sáiz
 */
public class LoggingValidationTestUtil {

	public static void assertStartEndIsLogged(LogCapture logCapture)
		throws Exception {

		_assertLogCount(logCapture, 2);

		_assertLog(logCapture, "Starting schema generation");
		_assertLog(logCapture, "Schema generation finished");
	}

	public static LogCapture getLogCapture() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.db.schema.info.internal.DBSchemaDump",
			LoggerTestUtil.INFO);
	}

	private static void _assertLog(
		LogCapture logCapture, String expectedMessage) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		for (LogEntry logEntry : logEntries) {
			if (StringUtil.equals(expectedMessage, logEntry.getMessage())) {
				return;
			}
		}

		Assert.fail(expectedMessage + " not found in logs");
	}

	private static void _assertLogCount(LogCapture logCapture, int total) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), total, logEntries.size());
	}

}