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

package com.liferay.commerce.frontend.internal.account.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Order {

	public Order(
		long id, long accountId, String accountName, String purchaseOrderNumber,
		String lastEdit, String status, String addOrderLink) {

		_id = id;
		_accountId = accountId;
		_accountName = accountName;
		_purchaseOrderNumber = purchaseOrderNumber;
		_lastEdit = lastEdit;
		_status = status;
		_addOrderLink = addOrderLink;
		_success = true;
	}

	public Order(String[] errorMessages) {
		_errorMessages = errorMessages;
		_success = false;
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAddOrderLink() {
		return _addOrderLink;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public long getId() {
		return _id;
	}

	public String getLastEdit() {
		return _lastEdit;
	}

	public String getPurchaseOrderNumber() {
		return _purchaseOrderNumber;
	}

	public String getStatus() {
		return _status;
	}

	public boolean getSuccess() {
		return _success;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private long _accountId;
	private String _accountName;
	private String _addOrderLink;
	private String[] _errorMessages;
	private long _id;
	private String _lastEdit;
	private String _purchaseOrderNumber;
	private String _status;
	private boolean _success;

}