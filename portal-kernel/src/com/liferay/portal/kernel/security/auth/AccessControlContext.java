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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AccessControlContext {

	public AuthVerifierResult getAuthVerifierResult() {
		return _authVerifierResult;
	}

	public HttpServletRequest getRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getResponse() {
		return _httpServletResponse;
	}

	public Map<String, Object> getSettings() {
		return _settings;
	}

	public void setAuthVerifierResult(AuthVerifierResult authVerifierResult) {
		_authVerifierResult = authVerifierResult;
	}

	public void setRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setResponse(HttpServletResponse httpServletResponse) {
		_httpServletResponse = httpServletResponse;
	}

	public static enum Settings {

		SERVICE_DEPTH

	}

	private AuthVerifierResult _authVerifierResult;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private final Map<String, Object> _settings = new HashMap<>();

}