/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCurrencyRateException extends PortalException {

	public CommerceCurrencyRateException() {
	}

	public CommerceCurrencyRateException(String msg) {
		super(msg);
	}

	public CommerceCurrencyRateException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceCurrencyRateException(Throwable throwable) {
		super(throwable);
	}

}