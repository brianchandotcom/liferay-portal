/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.portal.tools.service.builder.test.compat740.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchCTEntryException extends NoSuchModelException {

	public NoSuchCTEntryException() {
	}

	public NoSuchCTEntryException(String msg) {
		super(msg);
	}

	public NoSuchCTEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCTEntryException(Throwable throwable) {
		super(throwable);
	}

}