/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class DuplicateCommerceCurrencyException extends PortalException {

	public DuplicateCommerceCurrencyException() {
	}

	public DuplicateCommerceCurrencyException(String msg) {
		super(msg);
	}

	public DuplicateCommerceCurrencyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateCommerceCurrencyException(Throwable throwable) {
		super(throwable);
	}

}