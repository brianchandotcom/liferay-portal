/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class LoggedExceptionInInitializerError
	extends ExceptionInInitializerError {

	public LoggedExceptionInInitializerError(Throwable throwable) {
		super(throwable);

		_log.error(throwable, throwable);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoggedExceptionInInitializerError.class);

}