/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CryptoHashException extends PortalException {

	public CryptoHashException() {
	}

	public CryptoHashException(String msg) {
		super(msg);
	}

	public CryptoHashException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CryptoHashException(Throwable throwable) {
		super(throwable);
	}

}