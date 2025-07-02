/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchParentException extends NoSuchModelException {

	public NoSuchParentException() {
	}

	public NoSuchParentException(String msg) {
		super(msg);
	}

	public NoSuchParentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchParentException(Throwable throwable) {
		super(throwable);
	}

}