/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.sync.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEventException extends NoSuchModelException {

	public NoSuchEventException() {
	}

	public NoSuchEventException(String msg) {
		super(msg);
	}

	public NoSuchEventException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEventException(Throwable throwable) {
		super(throwable);
	}

}