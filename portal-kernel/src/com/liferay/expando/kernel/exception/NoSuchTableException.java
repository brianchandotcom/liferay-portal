/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchTableException extends NoSuchModelException {

	public NoSuchTableException() {
	}

	public NoSuchTableException(String msg) {
		super(msg);
	}

	public NoSuchTableException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchTableException(Throwable throwable) {
		super(throwable);
	}

}