/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentMethodGroupRelNameException
	extends PortalException {

	public CommercePaymentMethodGroupRelNameException() {
	}

	public CommercePaymentMethodGroupRelNameException(String msg) {
		super(msg);
	}

	public CommercePaymentMethodGroupRelNameException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommercePaymentMethodGroupRelNameException(Throwable throwable) {
		super(throwable);
	}

}