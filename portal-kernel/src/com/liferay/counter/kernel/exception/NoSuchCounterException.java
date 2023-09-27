/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchCounterException extends NoSuchModelException {

	public NoSuchCounterException() {
	}

	public NoSuchCounterException(String msg) {
		super(msg);
	}

	public NoSuchCounterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCounterException(Throwable throwable) {
		super(throwable);
	}

}