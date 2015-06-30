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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
	}

	protected void generateDigestChallenge(
		HttpServletRequest request, HttpServletResponse response,
		HttpAuthorizationHeader authorizationHeader) {
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

		return null;
	}

	protected HttpAuthorizationHeader parseDigest(
		HttpServletRequest request, String authorizationHeader,
		String[] authorizationHeaderParts) {

		return null;
	}

}