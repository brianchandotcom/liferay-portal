/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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