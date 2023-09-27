/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Eduardo García
 */
public class NoSuchEntryRelException extends NoSuchModelException {

	public NoSuchEntryRelException() {
	}

	public NoSuchEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchEntryRelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEntryRelException(Throwable throwable) {
		super(throwable);
	}

}