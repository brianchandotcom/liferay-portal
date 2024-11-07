/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class RequiredCPOptionException extends PortalException {

	public RequiredCPOptionException() {
	}

	public RequiredCPOptionException(String msg) {
		super(msg);
	}

	public RequiredCPOptionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredCPOptionException(Throwable throwable) {
		super(throwable);
	}

}