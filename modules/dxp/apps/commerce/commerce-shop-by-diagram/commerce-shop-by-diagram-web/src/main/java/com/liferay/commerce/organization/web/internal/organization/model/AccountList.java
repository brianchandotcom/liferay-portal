/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.web.internal.organization.model;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class AccountList {

	public AccountList(List<Account> accounts, int total) {
		_accounts = accounts;
		_total = total;
	}

	public AccountList(String[] errorMessages) {
		_errorMessages = errorMessages;

		_success = false;
	}

	public List<Account> getAccounts() {
		return _accounts;
	}

	public int getTotal() {
		return _total;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private List<Account> _accounts;
	private String[] _errorMessages;
	private boolean _success;
	private int _total;

}