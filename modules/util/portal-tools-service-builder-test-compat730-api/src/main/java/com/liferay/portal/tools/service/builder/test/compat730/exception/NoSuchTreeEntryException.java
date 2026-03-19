/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchTreeEntryException extends NoSuchModelException {

	public NoSuchTreeEntryException() {
	}

	public NoSuchTreeEntryException(String msg) {
		super(msg);
	}

	public NoSuchTreeEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchTreeEntryException(Throwable throwable) {
		super(throwable);
	}

}