/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.account.model;

/**
 * @author Alessio Antonio Rendina
 */
public class AccountOrganization {

	public AccountOrganization(
		long id, String name, String email, String thumbnail) {

		_id = id;
		_name = name;
		_email = email;
		_thumbnail = thumbnail;

		_success = true;
	}

	public AccountOrganization(String[] errorMessages) {
		_errorMessages = errorMessages;

		_success = false;
	}

	public String getEmail() {
		return _email;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public long getId() {
		return _id;
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

	private String _email;
	private String[] _errorMessages;
	private long _id;
	private String _name;
	private boolean _success;
	private String _thumbnail;

}