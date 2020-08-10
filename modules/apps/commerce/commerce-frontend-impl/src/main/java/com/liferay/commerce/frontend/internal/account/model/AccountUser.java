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
 * @author Marco Leo
 */
public class AccountUser {

	public AccountUser(
		long userId, String name, String email, String thumbnail) {

		_userId = userId;
		_name = name;
		_email = email;
		_success = true;
		_thumbnail = thumbnail;
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