/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.paypal.internal;

import com.liferay.commerce.payment.request.CommercePaymentRequest;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Luca Pellizzon
 */
public class PayPalCommercePaymentRequest extends CommercePaymentRequest {

	public PayPalCommercePaymentRequest(
		BigDecimal amount, String cancelUrl, long commerceOrderId,
		Locale locale, String payerId, String returnUrl, String transactionId) {

		super(
			amount, cancelUrl, commerceOrderId, locale, returnUrl,
			transactionId);

		_payerId = payerId;
	}

	public String getPayerId() {
		return _payerId;
	}

	private final String _payerId;

}