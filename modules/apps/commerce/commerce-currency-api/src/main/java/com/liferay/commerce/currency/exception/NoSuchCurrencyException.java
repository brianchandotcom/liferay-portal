/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Andrea Di Giorgi
 */
public class NoSuchCurrencyException extends NoSuchModelException {

	public NoSuchCurrencyException() {
	}

	public NoSuchCurrencyException(String msg) {
		super(msg);
	}

	public NoSuchCurrencyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCurrencyException(Throwable throwable) {
		super(throwable);
	}

}