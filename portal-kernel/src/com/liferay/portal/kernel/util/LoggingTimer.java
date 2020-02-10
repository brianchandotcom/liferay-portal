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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Closeable;

/**
 * @author Shuyang Zhou
 */
public class LoggingTimer implements Closeable {

	public LoggingTimer() {
		this(_getInvokerName(null, null), System.currentTimeMillis());
	}

	public LoggingTimer(Class<?> clazz, String name) {
		this(_getInvokerName(clazz, name), System.currentTimeMillis());
	}

	public LoggingTimer(String name) {
		this(_getInvokerName(null, name), System.currentTimeMillis());
	}

	@Override
	public void close() {
		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Completed ", _name, " in ",
					System.currentTimeMillis() - _startTime, " ms"));
		}
	}

	private static String _getInvokerName(Class<?> clazz, String name) {
		Thread thread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = thread.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[3];

		StringBundler sb = new StringBundler(5);

		if (clazz == null) {
			sb.append(stackTraceElement.getClassName());
		}
		else {
			sb.append(clazz.getName());
		}

		sb.append(StringPool.POUND);
		sb.append(stackTraceElement.getMethodName());

		if (name != null) {
			sb.append(StringPool.POUND);
			sb.append(name);
		}

		return sb.toString();
	}

	private LoggingTimer(String name, long startTime) {
		_name = name;
		_startTime = startTime;

		if (_log.isInfoEnabled()) {
			_log.info("Starting " + name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LoggingTimer.class);

	private final String _name;
	private final long _startTime;

}