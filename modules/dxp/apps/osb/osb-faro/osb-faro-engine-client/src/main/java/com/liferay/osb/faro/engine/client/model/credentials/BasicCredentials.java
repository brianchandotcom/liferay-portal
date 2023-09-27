/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.credentials;

import com.liferay.osb.faro.engine.client.model.Credentials;

/**
 * @author Matthew Kong
 */
public class BasicCredentials implements Credentials {

	public static final String TYPE = "Basic Authentication";

	@Override
	public void clearPasswords() {
		_password = null;
	}

	public String getLogin() {
		return _login;
	}

	public String getPassword() {
		return _password;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public void setLogin(String login) {
		_login = login;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private String _login;
	private String _password;

}