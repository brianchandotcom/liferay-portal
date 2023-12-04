/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryClassPKException extends PortalException {

	public CommercePaymentEntryClassPKException() {
	}

	public CommercePaymentEntryClassPKException(String msg) {
		super(msg);
	}

	public CommercePaymentEntryClassPKException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommercePaymentEntryClassPKException(Throwable throwable) {
		super(throwable);
	}

}