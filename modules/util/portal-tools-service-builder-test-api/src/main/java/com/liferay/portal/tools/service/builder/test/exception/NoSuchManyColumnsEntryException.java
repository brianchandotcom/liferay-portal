/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchManyColumnsEntryException extends NoSuchModelException {

	public NoSuchManyColumnsEntryException() {
	}

	public NoSuchManyColumnsEntryException(String msg) {
		super(msg);
	}

	public NoSuchManyColumnsEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchManyColumnsEntryException(Throwable throwable) {
		super(throwable);
	}

}