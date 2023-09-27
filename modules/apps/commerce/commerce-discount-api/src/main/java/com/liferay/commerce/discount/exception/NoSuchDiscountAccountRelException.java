/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchDiscountAccountRelException extends NoSuchModelException {

	public NoSuchDiscountAccountRelException() {
	}

	public NoSuchDiscountAccountRelException(String msg) {
		super(msg);
	}

	public NoSuchDiscountAccountRelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDiscountAccountRelException(Throwable throwable) {
		super(throwable);
	}

}