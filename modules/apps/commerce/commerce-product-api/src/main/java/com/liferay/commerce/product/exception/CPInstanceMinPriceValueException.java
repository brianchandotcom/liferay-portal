/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPInstanceMinPriceValueException extends PortalException {

	public CPInstanceMinPriceValueException() {
	}

	public CPInstanceMinPriceValueException(String msg) {
		super(msg);
	}

	public CPInstanceMinPriceValueException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CPInstanceMinPriceValueException(Throwable throwable) {
		super(throwable);
	}

}