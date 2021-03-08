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
		StringBundler sb = new StringBundler(5);

		sb.append("{level=");
		sb.append(_priority);
		sb.append(", message=");
		sb.append(_message);
		sb.append("}");

		return sb.toString();
	}

	private final String _message;
	private final String _priority;
	private final Throwable _throwable;

}