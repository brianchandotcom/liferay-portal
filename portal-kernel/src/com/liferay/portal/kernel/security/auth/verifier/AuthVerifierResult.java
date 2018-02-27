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

package com.liferay.portal.kernel.security.auth.verifier;

import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler(7);

		sb.append("{settings=");
		sb.append(_settings);
		sb.append(", state=");
		sb.append(_state);
		sb.append(", userId=");
		sb.append(_userId);
		sb.append("}");

		return sb.toString();
	}

	public enum State {

		INVALID_CREDENTIALS, NOT_APPLICABLE, SUCCESS

	}

	private String _password;
	private boolean _passwordBasedAuthentication;
	private Map<String, Object> _settings = new HashMap<>();
	private State _state = State.NOT_APPLICABLE;
	private long _userId;

}