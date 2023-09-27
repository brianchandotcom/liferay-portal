/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchOrderPaymentException extends NoSuchModelException {

	public NoSuchOrderPaymentException() {
	}

	public NoSuchOrderPaymentException(String msg) {
		super(msg);
	}

	public NoSuchOrderPaymentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOrderPaymentException(Throwable throwable) {
		super(throwable);
	}

}