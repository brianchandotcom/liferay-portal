/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Christopher Kian
 */
public class CompanyKeyException extends SystemException {

	public CompanyKeyException() {
	}

	public CompanyKeyException(String msg) {
		super(msg);
	}

	public CompanyKeyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CompanyKeyException(Throwable throwable) {
		super(throwable);
	}

}