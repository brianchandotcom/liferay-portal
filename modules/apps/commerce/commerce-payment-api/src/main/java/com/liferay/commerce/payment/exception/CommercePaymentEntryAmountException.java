/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryAmountException extends PortalException {

	public CommercePaymentEntryAmountException() {
	}

	public CommercePaymentEntryAmountException(String msg) {
		super(msg);
	}

	public CommercePaymentEntryAmountException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommercePaymentEntryAmountException(Throwable throwable) {
		super(throwable);
	}

}