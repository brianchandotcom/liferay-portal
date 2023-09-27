/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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