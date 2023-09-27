/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.StringPool;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ShutdownUtil {

	public static void cancel() {
		_date = null;
		_message = null;
	}

	public static long getInProcess() {
		long milliseconds = 0;

		if (_date != null) {
			milliseconds = _date.getTime() - System.currentTimeMillis();
		}

		return milliseconds;
	}

	public static String getMessage() {
		return _message;
	}

	public static boolean isInProcess() {
		if (_date == null) {
			return false;
		}

		if (_date.after(new Date())) {
			return true;
		}

		return false;
	}

	public static boolean isShutdown() {
		if (_date == null) {
			return false;
		}

		if (_date.before(new Date())) {
			return true;
		}

		return false;
	}

	public static void shutdown(long milliseconds) {
		shutdown(milliseconds, StringPool.BLANK);
	}

	public static void shutdown(long milliseconds, String message) {
		_date = new Date(System.currentTimeMillis() + milliseconds);
		_message = message;
	}

	private static Date _date;
	private static String _message;

}