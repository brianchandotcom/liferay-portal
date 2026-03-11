/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchConvertNullEntryException extends NoSuchModelException {

	public NoSuchConvertNullEntryException() {
	}

	public NoSuchConvertNullEntryException(String msg) {
		super(msg);
	}

	public NoSuchConvertNullEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchConvertNullEntryException(Throwable throwable) {
		super(throwable);
	}

}