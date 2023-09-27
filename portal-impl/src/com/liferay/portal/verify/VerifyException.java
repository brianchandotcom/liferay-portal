/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alexander Chow
 */
public class VerifyException extends PortalException {

	public VerifyException() {
	}

	public VerifyException(String msg) {
		super(msg);
	}

	public VerifyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public VerifyException(Throwable throwable) {
		super(throwable);
	}

}