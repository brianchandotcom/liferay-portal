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

package com.liferay.lcs.exception;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ivica Cardic
 */
public class LCSExceptionHandler {

	public static void debug(String message, Throwable t) {
		if (_log.isDebugEnabled()) {
			_log.debug(message, t);
		}
	}

	public static void debug(Throwable t) {
		if (_log.isDebugEnabled()) {
			_log.debug(t);
		}
	}

	public static void error(String message, Throwable t) {
		_log.error(message, t);
	}

	public static void error(Throwable t) {
		_log.error(t);
	}

	public static Throwable getRootCause(Throwable throwable) {
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}

		return throwable;
	}

	public static void info(String message, Throwable t) {
		if (_log.isInfoEnabled()) {
			_log.info(message, t);
		}
	}

	public static void info(Throwable t) {
		if (_log.isInfoEnabled()) {
			_log.info(t);
		}
	}

	public static void warn(String message, Throwable t) {
		if (_log.isWarnEnabled()) {
			_log.warn(message, t);
		}
	}

	public static void warn(Throwable t) {
		if (_log.isWarnEnabled()) {
			_log.warn(t);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSExceptionHandler.class);

}