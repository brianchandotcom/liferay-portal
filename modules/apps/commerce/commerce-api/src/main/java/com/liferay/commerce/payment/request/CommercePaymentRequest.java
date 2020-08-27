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

package com.liferay.commerce.payment.request;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentRequest implements Serializable {

	public CommercePaymentRequest(
		BigDecimal amount, String cancelUrl, long commerceOrderId,
		Locale locale, String returnUrl, String transactionId) {

		_amount = amount;
		_cancelUrl = cancelUrl;
		_commerceOrderId = commerceOrderId;
		_locale = locale;
		_returnUrl = returnUrl;
		_transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return _amount;
	}

	public String getCancelUrl() {
		return _cancelUrl;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getReturnUrl() {
		return _returnUrl;
	}

	public String getTransactionId() {
		return _transactionId;
	}

	private final BigDecimal _amount;
	private final String _cancelUrl;
	private final long _commerceOrderId;
	private final Locale _locale;
	private final String _returnUrl;
	private final String _transactionId;

}