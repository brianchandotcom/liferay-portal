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

package com.liferay.portal.security.auth.http;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManager;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.secure.NonceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthManagerImpl implements HttpAuthManager {

	@Override
	public void generateChallenge(
		HttpServletRequest request, HttpServletResponse response,
		HttpAuthorizationHeader authorizationHeader) {

		if (request == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		if (response == null) {
			throw new IllegalArgumentException(
				"HttpServletResponse is mandatory");
		}

		if ((authorizationHeader == null) ||
			Validator.isBlank(authorizationHeader.getScheme())) {

			throw new IllegalArgumentException(
				"AuthorizationHeader scheme is mandatory");
		}

		String realm = authorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTHPARAM_REALM);

		if (Validator.isBlank(realm)) {
			authorizationHeader.setAuthParameter(
				HttpAuthorizationHeader.AUTHPARAM_REALM, Portal.PORTAL_REALM);
		}

		String authorizationScheme = authorizationHeader.getScheme();

		if (StringUtil.equalsIgnoreCase(
				authorizationScheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			generateBasicChallenge(request, response, authorizationHeader);
		}
		else if (StringUtil.equalsIgnoreCase(
					authorizationScheme,
					HttpAuthorizationHeader.SCHEME_DIGEST)) {

			generateDigestChallenge(request, response, authorizationHeader);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + authorizationScheme +
					" is not supported");
		}
	}

	@Override
	public long getUserId(
			HttpServletRequest request,
			HttpAuthorizationHeader authorizationHeader)
		throws PortalException {

		if (request == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		if ((authorizationHeader == null) ||
			Validator.isBlank(authorizationHeader.getScheme())) {

			throw new IllegalArgumentException(
				"AuthorizationHeader scheme is mandatory");
		}

		String authorizationScheme = authorizationHeader.getScheme();

		if (StringUtil.equalsIgnoreCase(
				authorizationScheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return getBasicUserId(request, authorizationHeader);
		}
		else if (StringUtil.equalsIgnoreCase(
					authorizationScheme,
					HttpAuthorizationHeader.SCHEME_DIGEST)) {

			return getDigestUserId(request, authorizationHeader);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + authorizationScheme +
					" is not supported");
		}
	}

	@Override
	public HttpAuthorizationHeader parse(HttpServletRequest request) {
		if (request == null) {
			throw new IllegalArgumentException(
				"HttpServletRequest is mandatory");
		}

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isBlank(authorizationHeader)) {
			return null;
		}

		String[] authorizationHeaderParts = authorizationHeader.split("\\s");

		String scheme = authorizationHeaderParts[0];

		if (StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			return parseBasic(
				request, authorizationHeader, authorizationHeaderParts);
		}
		else if (StringUtil.equalsIgnoreCase(
					scheme, HttpAuthorizationHeader.SCHEME_DIGEST)) {

			return parseDigest(
				request, authorizationHeader, authorizationHeaderParts);
		}
		else {
			throw new UnsupportedOperationException(
				"Authorization scheme " + scheme + " is not supported");
		}
	}

	protected void generateBasicChallenge(
		HttpServletRequest request, HttpServletResponse response,
		HttpAuthorizationHeader authorizationHeader) {

		response.setHeader(
			HttpHeaders.WWW_AUTHENTICATE, authorizationHeader.toString());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	protected void generateDigestChallenge(
		HttpServletRequest request, HttpServletResponse response,
		HttpAuthorizationHeader authorizationHeader) {

		// Must generate a new nonce for each 401 (RFC2617, 3.2.1)

		long companyId = PortalInstances.getCompanyId(request);

		String remoteAddress = request.getRemoteAddr();

		String nonce = NonceUtil.generate(companyId, remoteAddress);

		authorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTHPARAM_NONCE, nonce);

		response.setHeader(
			HttpHeaders.WWW_AUTHENTICATE, authorizationHeader.toString());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	protected long getBasicUserId(
			HttpServletRequest request,
			HttpAuthorizationHeader authorizationHeader)
		throws PortalException {

		return 0;
	}

	protected long getDigestUserId(
			HttpServletRequest request,
			HttpAuthorizationHeader authorizationHeader)
		throws PortalException {

		return 0;
	}

	protected HttpAuthorizationHeader parseBasic(
		HttpServletRequest request, String authorizationHeader,
		String[] authorizationHeaderParts) {

		String credentials = new String(
			Base64.decode(authorizationHeaderParts[1]));

		String[] loginAndPassword = StringUtil.split(
			credentials, CharPool.COLON);

		String login = HttpUtil.decodeURL(loginAndPassword[0].trim());

		String password = null;

		if (loginAndPassword.length > 1) {
			password = loginAndPassword[1].trim();
		}

		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_BASIC);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTHPARAM_USERID, login);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTHPARAM_PASSWORD, password);

		return httpAuthorizationHeader;
	}

	protected HttpAuthorizationHeader parseDigest(
		HttpServletRequest request, String authorizationHeader,
		String[] authorizationHeaderParts) {

		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_DIGEST);

		authorizationHeader = authorizationHeader.substring(
			HttpAuthorizationHeader.SCHEME_DIGEST.length() + 1);

		authorizationHeader = StringUtil.replace(
			authorizationHeader, CharPool.COMMA, CharPool.NEW_LINE);

		UnicodeProperties authorizationProperties = new UnicodeProperties();

		authorizationProperties.fastLoad(authorizationHeader);

		for (Map.Entry<String, String> authorizationProperty :
				authorizationProperties.entrySet()) {

			String key = authorizationProperty.getKey();
			String value = StringUtil.unquote(
				authorizationProperties.getProperty(key));

			httpAuthorizationHeader.setAuthParameter(key, value);
		}

		return httpAuthorizationHeader;
	}

}