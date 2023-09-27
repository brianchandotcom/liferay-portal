/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.log;

import com.liferay.petra.string.StringBundler;

/**
 * @author Dante Wang
 */
public class LogEntry {

	public LogEntry(String message, String priority, Throwable throwable) {
		_message = message;
		_priority = priority;
		_throwable = throwable;
	}

	public String getMessage() {
		return _message;
	}

	public String getPriority() {
		return _priority;
	}

	public Throwable getThrowable() {
		return _throwable;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{level=", _priority, ", message=", _message, "}");
	}

	private final String _message;
	private final String _priority;
	private final Throwable _throwable;

}