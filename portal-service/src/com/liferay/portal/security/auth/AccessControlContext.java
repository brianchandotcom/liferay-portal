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

import com.liferay.portal.security.auth.verifier.AuthVerifierResult;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthenticationContext holds all information bound with user authentication.
 * It's saved in PortalAAManager as a ThreadLocal.
 *
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AccessControlContext {

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public Map<String, Object> getSettings() {
		return _settings;
	}

	public AuthVerifierResult getVerificationResult() {
		return _verificationResult;
	}

	public void setRequest(HttpServletRequest request) {
		_httpServletRequest = request;
	}

	public void setResponse(HttpServletResponse response) {
		_httpServletResponse = response;
	}

	public void setVerificationResult(AuthVerifierResult verificationResult) {
		_verificationResult = verificationResult;
	}

	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Map<String, Object> _settings = new HashMap<String, Object>();
	private AuthVerifierResult _verificationResult;

}