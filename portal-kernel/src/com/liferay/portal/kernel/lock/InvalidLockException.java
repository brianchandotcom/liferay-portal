/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Tina Tian
 */
public class InvalidLockException extends PortalException {

	public InvalidLockException() {
	}

	public InvalidLockException(String msg) {
		super(msg);
	}

	public InvalidLockException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidLockException(Throwable throwable) {
		super(throwable);
	}

}