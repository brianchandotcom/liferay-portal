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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthorizationHeader {

	public static final String AUTH_PARAMETER_NAME_NONCE = "nonce";

	public static final String AUTH_PARAMETER_NAME_PASSWORD = "password";

	public static final String AUTH_PARAMETER_NAME_REALM = "realm";

	public static final String AUTH_PARAMETER_NAME_RESPONSE = "response";

	public static final String AUTH_PARAMETER_NAME_URI = "uri";

	public static final String AUTH_PARAMETER_NAME_USERNAME = "username";

	public static final String SCHEME_BASIC = "Basic";

	public static final String SCHEME_BEARER = "Bearer";

	public static final String SCHEME_DIGEST = "Digest";

	public HttpAuthorizationHeader(String scheme) {
		_scheme = scheme;
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
		_scheme = scheme;
	}

	@Override
	public String toString() {
		if (_scheme.equals(SCHEME_BASIC) &&
			!Validator.isBlank(
				getAuthParameter(AUTH_PARAMETER_NAME_USERNAME))) {

			String userName = getAuthParameter(AUTH_PARAMETER_NAME_USERNAME);
			String password = getAuthParameter(AUTH_PARAMETER_NAME_PASSWORD);

			String userNameAndPassword = userName + StringPool.COLON + password;

			String encodedUserNameAndPassword = Base64.encode(
				userNameAndPassword.getBytes());

			return SCHEME_BASIC + StringPool.SPACE + encodedUserNameAndPassword;
		}

		StringBundler sb = new StringBundler(_authParameters.size() * 6 + 2);

		sb.append(_scheme);
		sb.append(StringPool.SPACE);

		for (Map.Entry<String, String> entry : _authParameters.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(StringPool.QUOTE);
			sb.append(entry.getValue());
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private final Map<String, String> _authParameters = new LinkedHashMap<>();
	private String _scheme;

}