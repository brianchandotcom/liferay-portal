/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Order {

	public Order(
		String externalReferenceCode, long orderId, String accountName,
		String date, String author, String orderStatus, String orderType,
		String purchaseOrderNumber, String status, String amount) {

		_externalReferenceCode = externalReferenceCode;
		_orderId = orderId;
		_accountName = accountName;
		_date = date;
		_author = author;
		_orderStatus = orderStatus;
		_orderType = orderType;
		_purchaseOrderNumber = purchaseOrderNumber;
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

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getOrderId() {
		return _orderId;
	}

	public String getOrderStatus() {
		return _orderStatus;
	}

	public String getOrderType() {
		return _orderType;
	}

	public String getPurchaseOrderNumber() {
		return _purchaseOrderNumber;
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
	private final String _externalReferenceCode;
	private final long _orderId;
	private final String _orderStatus;
	private final String _orderType;
	private final String _purchaseOrderNumber;
	private final String _status;
	private final String _title;

}