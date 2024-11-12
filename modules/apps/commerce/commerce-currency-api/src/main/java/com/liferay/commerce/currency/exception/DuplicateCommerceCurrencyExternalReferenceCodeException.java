/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Andrea Di Giorgi
 */
public class DuplicateCommerceCurrencyExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceCurrencyExternalReferenceCodeException() {
	}

	public DuplicateCommerceCurrencyExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCommerceCurrencyExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceCurrencyExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}