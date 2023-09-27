/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Tina Tian
 */
public class NoSuchLockException extends NoSuchModelException {

	public NoSuchLockException() {
	}

	public NoSuchLockException(String msg) {
		super(msg);
	}

	public NoSuchLockException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchLockException(Throwable throwable) {
		super(throwable);
	}

}