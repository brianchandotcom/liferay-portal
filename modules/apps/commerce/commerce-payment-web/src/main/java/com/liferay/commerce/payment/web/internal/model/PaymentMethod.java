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

package com.liferay.commerce.payment.web.internal.model;

/**
 * @author Crescenzo Rega
 */
public class PaymentMethod {

	public PaymentMethod(
		long accountEntryId, String active, String channelName,
		long commerceChannelId, String paymentMethodName) {

		_accountEntryId = accountEntryId;
		_active = active;
		_channelName = channelName;
		_commerceChannelId = commerceChannelId;
		_paymentMethodName = paymentMethodName;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getActive() {
		return _active;
	}

	public String getChannelName() {
		return _channelName;
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public String getPaymentMethodName() {
		return _paymentMethodName;
	}

	private final long _accountEntryId;
	private final String _active;
	private final String _channelName;
	private final long _commerceChannelId;
	private final String _paymentMethodName;

}