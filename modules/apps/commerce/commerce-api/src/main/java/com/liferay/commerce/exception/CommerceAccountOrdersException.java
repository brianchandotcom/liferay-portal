/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.ModelListenerException;

/**
 * @author Alec Sloan
 */
public class CommerceAccountOrdersException extends ModelListenerException {

	public CommerceAccountOrdersException() {
	}

	public CommerceAccountOrdersException(String msg) {
		super(msg);
	}

	public CommerceAccountOrdersException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceAccountOrdersException(Throwable throwable) {
		super(throwable);
	}

}