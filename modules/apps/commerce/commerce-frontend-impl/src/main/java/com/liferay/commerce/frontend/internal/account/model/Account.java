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
public class Account {

	public Account(String accountId, String name, String thumbnail) {
		_accountId = accountId;
		_name = name;
		_success = true;
		_thumbnail = thumbnail;
	}

	public Account(String[] errorMessages) {
		_errorMessages = errorMessages;
		_success = false;
	}

	public String getAccountId() {
		return _accountId;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public String getName() {
		return _name;
	}

	public boolean getSuccess() {
		return _success;
	}

	public String getThumbnail() {
		return _thumbnail;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private String _accountId;
	private String[] _errorMessages;
	private String _name;
	private boolean _success;
	private String _thumbnail;

}