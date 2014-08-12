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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ShutdownUtil {

	public static void cancel() {
		_instance._cancel();
	}

	public static long getInProcess() {
		return _instance._getInProcess();
	}

	public static String getMessage() {
		return _instance._getMessage();
	}

	public static boolean isInProcess() {
		return _instance._isInProcess();
	}

	public static boolean isShutdown() {
		return _instance._isShutdown();
	}

	public static void shutdown(long milliseconds) {
		shutdown(milliseconds, StringPool.BLANK);
	}

	public static void shutdown(long milliseconds, String message) {
		_instance._shutdown(milliseconds, message);
	}

	private ShutdownUtil() {
	}

	private void _cancel() {
		_date = null;
		_message = null;
	}

	private long _getInProcess() {
		long milliseconds = 0;

		if (_date != null) {
			milliseconds = _date.getTime() - System.currentTimeMillis();
		}

		return milliseconds;
	}

	private String _getMessage() {
		return _message;
	}

	private boolean _isInProcess() {
		if (_date == null) {
			return false;
		}

		if (_date.after(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isShutdown() {
		if (_date == null) {
			return false;
		}

		if (_date.before(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _shutdown(long milliseconds, String message) {
		_date = new Date(System.currentTimeMillis() + milliseconds);
		_message = message;
	}

	private static final ShutdownUtil _instance = new ShutdownUtil();

	private Date _date;
	private String _message;

}