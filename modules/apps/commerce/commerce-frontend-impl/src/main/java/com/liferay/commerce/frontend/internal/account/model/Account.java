/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.account.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Account {

	public Account(String accountId, String name, String thumbnail) {
		_accountId = accountId;
		_name = name;
		_thumbnail = thumbnail;

		_success = true;
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