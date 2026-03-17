/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.portal.tools.service.builder.test.compat700.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchArrayableEntryException extends NoSuchModelException {

	public NoSuchArrayableEntryException() {
	}

	public NoSuchArrayableEntryException(String msg) {
		super(msg);
	}

	public NoSuchArrayableEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchArrayableEntryException(Throwable throwable) {
		super(throwable);
	}

}