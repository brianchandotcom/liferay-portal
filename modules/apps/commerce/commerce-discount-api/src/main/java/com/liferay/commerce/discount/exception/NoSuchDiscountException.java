/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchDiscountException extends NoSuchModelException {

	public NoSuchDiscountException() {
	}

	public NoSuchDiscountException(String msg) {
		super(msg);
	}

	public NoSuchDiscountException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDiscountException(Throwable throwable) {
		super(throwable);
	}

}