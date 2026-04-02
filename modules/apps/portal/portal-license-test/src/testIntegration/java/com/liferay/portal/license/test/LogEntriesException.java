/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.portal.test.log.LogEntry;

import java.util.List;

/**
 * @author Tina Tian
 */
public class LogEntriesException extends Exception {

	public LogEntriesException(List<LogEntry> logEntries, String payload) {
		_logEntries = logEntries;
		_payload = payload;
	}

	public List<LogEntry> getLogEntries() {
		return _logEntries;
	}

	public String getPayload() {
		return _payload;
	}

	private final List<LogEntry> _logEntries;
	private final String _payload;

}