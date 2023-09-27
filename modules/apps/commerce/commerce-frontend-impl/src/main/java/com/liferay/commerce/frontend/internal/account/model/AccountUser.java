/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.account.model;

/**
 * @author Marco Leo
 */
public class AccountUser {

	public AccountUser(
		long userId, String name, String email, String thumbnail) {

		_userId = userId;
		_name = name;
		_email = email;
		_thumbnail = thumbnail;

		_success = true;
	}

	public AccountUser(String[] errorMessages) {
		_errorMessages = errorMessages;

		_success = false;
	}

	public String getEmail() {
		return _email;
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

	public long getUserId() {
		return _userId;
	}

	public void setErrorMessages(String[] errorMessages) {
		_errorMessages = errorMessages;
	}

	public void setSuccess(boolean success) {
		_success = success;
	}

	private String _email;
	private String[] _errorMessages;
	private String _name;
	private boolean _success;
	private String _thumbnail;
	private long _userId;

}