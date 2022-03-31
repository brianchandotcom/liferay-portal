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

import java.util.Properties;

/**
 * @author Tomas Polesovsky
 */
public class AuthVerifierConfiguration {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no replacement
	 */
	@Deprecated
	public AuthVerifier getAuthVerifier() {
		return null;
	}

	public String getAuthVerifierClassName() {
		return _authVerifierClassName;
	}

	public Properties getProperties() {
		return _properties;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no replacement
	 */
	@Deprecated
	public void setAuthVerifier(AuthVerifier authVerifier) {
	}

	public void setAuthVerifierClassName(String authVerifierClassName) {
		int lastDotIndex = authVerifierClassName.lastIndexOf('.');

		int simpleClassNameIndex = lastDotIndex + 1;

		int lastDollarIndex = authVerifierClassName.lastIndexOf('$');

		if (lastDotIndex < lastDollarIndex) {
			simpleClassNameIndex = lastDollarIndex + 1;
		}

		_authVerifierClassName = authVerifierClassName.substring(
			simpleClassNameIndex);
	}

	public void setProperties(Properties properties) {
		_properties = properties;
	}

	private String _authVerifierClassName;
	private Properties _properties;

}