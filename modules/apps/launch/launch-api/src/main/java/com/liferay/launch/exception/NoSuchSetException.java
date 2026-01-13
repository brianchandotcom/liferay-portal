/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchSetException extends NoSuchModelException {

	public NoSuchSetException() {
	}

	public NoSuchSetException(String msg) {
		super(msg);
	}

	public NoSuchSetException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSetException(Throwable throwable) {
		super(throwable);
	}

}