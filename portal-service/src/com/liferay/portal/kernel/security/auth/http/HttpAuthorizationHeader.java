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

package com.liferay.portal.kernel.security.auth.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthorizationHeader {

	public static final String AUTHPARAM_PASSWORD = "password";

	public static final String AUTHPARAM_USERID = "userid";

	public static final String SCHEME_BASIC = "Basic";

	public static final String SCHEME_DIGEST = "Digest";

	public HttpAuthorizationHeader() {
	}

	public HttpAuthorizationHeader(String scheme) {
		this._scheme = scheme;
	}

	public String getAuthParameter(String name) {
		return _authParameters.get(name);
	}

	public Map<String, String> getAuthParameters() {
		return _authParameters;
	}

	public String getScheme() {
		return _scheme;
	}

	public void setAuthParameter(String name, String value) {
		_authParameters.put(name, value);
	}

	public void setScheme(String scheme) {
		this._scheme = scheme;
	}

	private final Map<String, String> _authParameters = new HashMap<>();
	private String _scheme;

}