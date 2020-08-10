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

package com.liferay.commerce.order.content.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Order {

	public Order(
		long orderId, String accountName, String date, String author,
		String orderStatus, String status, String amount) {

		_orderId = orderId;
		_accountName = accountName;
		_date = date;
		_author = author;
		_orderStatus = orderStatus;
		_status = status;
		_amount = amount;
		_title = String.valueOf(orderId);
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAmount() {
		return _amount;
	}

	public String getAuthor() {
		return _author;
	}

	public String getDate() {
		return _date;
	}

	public long getOrderId() {
		return _orderId;
	}

	public String getOrderStatus() {
		return _orderStatus;
	}

	public String getStatus() {
		return _status;
	}

	public String getTitle() {
		return _title;
	}

	private final String _accountName;
	private final String _amount;
	private final String _author;
	private final String _date;
	private final long _orderId;
	private final String _orderStatus;
	private final String _status;
	private final String _title;

}