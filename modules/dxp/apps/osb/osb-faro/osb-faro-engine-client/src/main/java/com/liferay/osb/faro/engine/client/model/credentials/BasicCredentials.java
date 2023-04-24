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