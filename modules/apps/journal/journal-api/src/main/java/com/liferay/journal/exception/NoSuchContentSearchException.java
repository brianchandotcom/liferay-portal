/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchContentSearchException extends NoSuchModelException {

	public NoSuchContentSearchException() {
	}

	public NoSuchContentSearchException(String msg) {
		super(msg);
	}

	public NoSuchContentSearchException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchContentSearchException(Throwable throwable) {
		super(throwable);
	}

}