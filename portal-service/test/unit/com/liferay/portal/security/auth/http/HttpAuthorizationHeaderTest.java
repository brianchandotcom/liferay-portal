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

import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.util.Portal;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthorizationHeaderTest {

	@Test
	public void testGenerateBasicChallengeResponse() {
		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_BASIC);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_REALM,
			Portal.PORTAL_REALM);

		Assert.assertEquals(
			httpAuthorizationHeader.toString(),
			"Basic realm=\"" + Portal.PORTAL_REALM + "\"");
	}

	@Test
	public void testGenerateBasicRequest() {
		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_BASIC);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME,
			"test@liferay.com");

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD, "test");

		Assert.assertEquals(
			httpAuthorizationHeader.toString(),
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
	}

	@Test
	public void testGenerateDigestChallengeResponse() {
		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_DIGEST);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_REALM,
			Portal.PORTAL_REALM);

		String nonce = String.valueOf(Math.random());

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_NONCE, nonce);

		Assert.assertEquals(
			httpAuthorizationHeader.toString(),
			"Digest realm=\"" + Portal.PORTAL_REALM + "\", nonce=\"" +
				nonce + "\"");
	}

	@Test
	public void testGenerateDigestRequest() {
		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_DIGEST);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_REALM,
			Portal.PORTAL_REALM);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME,
			"test@liferay.com");

		String nonce = String.valueOf(Math.random());

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_NONCE, nonce);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_URI, "/url");

		String response = String.valueOf(Math.random());

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_RESPONSE, response);

		Assert.assertEquals(
			httpAuthorizationHeader.toString(),
			"Digest realm=\"PortalRealm\", username=\"test@liferay.com\", " +
				"nonce=\"" + nonce + "\", uri=\"/url\", response=\"" +
				response + "\"");
	}

}