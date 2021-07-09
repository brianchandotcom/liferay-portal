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

package com.liferay.commerce.order.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class Order {

	public Order(
		String account, String accountCode, String amount, String channel,
		String createDateString, LabelField fulfillmentWorkflow, long orderId,
		LabelField orderStatus) {

		_account = account;
		_accountCode = accountCode;
		_amount = amount;
		_channel = channel;
		_createDateString = createDateString;
		_fulfillmentWorkflow = fulfillmentWorkflow;
		_orderId = orderId;
		_orderStatus = orderStatus;
	}

	public String getAccount() {
		return _account;
	}

	public String getAccountCode() {
		return _accountCode;
	}

	public String getAmount() {
		return _amount;
	}

	public String getChannel() {
		return _channel;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public LabelField getFulfillmentWorkflow() {
		return _fulfillmentWorkflow;
	}

	public long getOrderId() {
		return _orderId;
	}

	public LabelField getOrderStatus() {
		return _orderStatus;
	}

	private final String _account;
	private final String _accountCode;
	private final String _amount;
	private final String _channel;
	private final String _createDateString;
	private final LabelField _fulfillmentWorkflow;
	private final long _orderId;
	private final LabelField _orderStatus;

}