/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth.verifier;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * Result of the verification. {@link AuthVerifier}s should set at
 * least:<ul>
 *     <li>{@link #setState(VerificationResult.State)} indicating the
 *     result state</li>
 *     <li>{@link #setUserId(long)} when user is authenticated - state is
 *     {@link State#SUCCESS}</li>
 * </ul>
 * Optionally can return other authentication specific settings using
 * {@link #setAuthenticationSettings(java.util.Map)}, these settings will be
 * then merged with {@link AuthVerifierConfiguration#getConfiguration()}
 * into
 * {@link com.liferay.portal.security.auth.AccessControlContext#getSettings()}
 *
 * @author Tomas Polesovsky
 */
public class AuthVerifierResult {

	public enum State {
		/** Authentication has been successful */
		SUCCESS,
		/** Authentication token is invalid */
		INVALID_CREDENTIALS,
		/** Authentication cannot be applied */
		NOT_APPLICABLE
	}

	public Map<String, Object> getAuthenticationSettings() {
		return _authenticationSettings;
	}

	public String getPassword() {
		return _password;
	}

	public State getState() {
		return _state;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAuthenticationSettings(Map<String, Object> settings) {
		_authenticationSettings = settings;
	}

	public void setPassword(String password) {
		_password = password;
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

		sb.append("{authenticationSettings=");
		sb.append(_authenticationSettings);
		sb.append(", state=");
		sb.append(_state);
		sb.append(", userId=");
		sb.append(_userId);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, Object> _authenticationSettings =
		new HashMap<String, Object>();
	private String _password;
	private State _state = State.NOT_APPLICABLE;
	private long _userId = 0;

}