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

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class AccountUserList {

	public AccountUserList(List<AccountUser> accounts, int count) {
		_accounts = accounts;
		_count = count;
		_success = true;
	}

	public AccountUserList(String[] errorMessages) {
		_errorMessages = errorMessages;
		_success = false;
	}

	public int getCount() {
		return _count;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public boolean getSuccess() {
		return _success;
	}

	public List<AccountUser> getUsers() {
		return _accounts;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	public void setUsers(List<AccountUser> accounts) {
		_accounts = accounts;
	}

	private List<AccountUser> _accounts;
	private int _count;
	private String[] _errorMessages;
	private boolean _success;

}