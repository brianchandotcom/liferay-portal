/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth.verifier;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class AuthVerifierResult {

	public String getPassword() {
		return _password;
	}

	public Map<String, Object> getSettings() {
		return _settings;
	}

	public State getState() {
		return _state;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isPasswordBasedAuthentication() {
		return _passwordBasedAuthentication;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setPasswordBasedAuthentication(
		boolean passwordBasedAuthentication) {

		_passwordBasedAuthentication = passwordBasedAuthentication;
	}

	public void setSettings(Map<String, Object> settings) {
		_settings = settings;
	}

	public void setState(State state) {
		_state = state;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{settings=", _settings, ", state=", _state, ", userId=", _userId,
			"}");
	}

	public enum State {

		INVALID_CREDENTIALS, NOT_APPLICABLE, SUCCESS, UNSUCCESSFUL

	}

	private String _password;
	private boolean _passwordBasedAuthentication;
	private Map<String, Object> _settings = new HashMap<>();
	private State _state = State.NOT_APPLICABLE;
	private long _userId;

}