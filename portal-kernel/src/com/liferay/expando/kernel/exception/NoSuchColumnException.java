/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchColumnException extends NoSuchModelException {

	public NoSuchColumnException() {
	}

	public NoSuchColumnException(String msg) {
		super(msg);
	}

	public NoSuchColumnException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchColumnException(Throwable throwable) {
		super(throwable);
	}

}