/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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