/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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