/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchAutoEscapeEntryException extends NoSuchModelException {

	public NoSuchAutoEscapeEntryException() {
	}

	public NoSuchAutoEscapeEntryException(String msg) {
		super(msg);
	}

	public NoSuchAutoEscapeEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAutoEscapeEntryException(Throwable throwable) {
		super(throwable);
	}

}