/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchFlagException extends NoSuchModelException {

	public NoSuchFlagException() {
	}

	public NoSuchFlagException(String msg) {
		super(msg);
	}

	public NoSuchFlagException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFlagException(Throwable throwable) {
		super(throwable);
	}

}