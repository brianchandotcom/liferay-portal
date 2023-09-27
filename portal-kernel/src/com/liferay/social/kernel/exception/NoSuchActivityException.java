/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchActivityException extends NoSuchModelException {

	public NoSuchActivityException() {
	}

	public NoSuchActivityException(String msg) {
		super(msg);
	}

	public NoSuchActivityException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchActivityException(Throwable throwable) {
		super(throwable);
	}

}