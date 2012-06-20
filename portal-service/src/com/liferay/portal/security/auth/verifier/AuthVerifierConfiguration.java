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

import java.util.Properties;

/**
 * Bean for holding instance of AuthVerifier and its configuration object
 * @author Tomas Polesovsky
 */
public class AuthVerifierConfiguration {
	public AuthVerifier getAuthVerifier() {
		return _authVerifier;
	}

	public Properties getConfiguration() {
		return _configuration;
	}

	public void setAuthVerifier(AuthVerifier authVerifier) {
		this._authVerifier = authVerifier;
	}

	public void setConfiguration(Properties configuration) {
		this._configuration = configuration;
	}

	private AuthVerifier _authVerifier;
	private Properties _configuration;

}