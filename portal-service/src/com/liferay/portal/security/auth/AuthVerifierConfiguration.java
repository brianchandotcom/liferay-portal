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

package com.liferay.portal.security.auth;

import java.util.Properties;

/**
 * @author Tomas Polesovsky
 */
public class AuthVerifierConfiguration {

	public AuthVerifier getAuthVerifier() {
		return _authVerifier;
	}

	public String getAuthVerifierClassName() {
		return _authVerifierClassName;
	}

	public Properties getProperties() {
		return _properties;
	}

	public void setAuthVerifier(AuthVerifier authVerifier) {
		_authVerifier = authVerifier;
	}

	public void setAuthVerifierClassName(String authVerifierClassName) {
		this._authVerifierClassName = authVerifierClassName;
	}

	public void setProperties(Properties properties) {
		_properties = properties;
	}

	private AuthVerifier _authVerifier;
	private String _authVerifierClassName;
	private Properties _properties;

}