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

package com.liferay.portal.security.auth;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.security.auth.http.HttpAuthManagerImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import jodd.util.StringUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class HttpAuthManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLPS88011() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode("test@liferay.com:te:st".getBytes()));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			"te:st",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));
	}

	@Test
	public void testParseBasic() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			HttpAuthorizationHeader.SCHEME_BASIC,
			httpAuthorizationHeader.getScheme());

		Map<String, String> authParameters =
			httpAuthorizationHeader.getAuthParameters();

		Assert.assertEquals(
			authParameters.toString(), 2, authParameters.size());

		Assert.assertEquals(
			"test@liferay.com",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));

		Assert.assertEquals(
			"test",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));
	}

	@Test
	public void testParseBasicNoCredentials() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode("test@liferay.com".getBytes()));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			HttpAuthorizationHeader.SCHEME_BASIC,
			httpAuthorizationHeader.getScheme());

		Map<String, String> authParameters =
			httpAuthorizationHeader.getAuthParameters();

		Assert.assertEquals(
			authParameters.toString(), 2, authParameters.size());

		Assert.assertEquals(
			"test@liferay.com",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));

		Assert.assertEquals(
			null,
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));

		mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode("test@liferay.com:".getBytes()));

		httpAuthorizationHeader = _httpAuthManagerImpl.parse(
			mockHttpServletRequest);

		Assert.assertEquals(
			StringPool.BLANK,
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));

		mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode(":".getBytes()));

		httpAuthorizationHeader = _httpAuthManagerImpl.parse(
			mockHttpServletRequest);

		Assert.assertEquals(
			StringPool.BLANK,
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));

		Assert.assertEquals(
			StringPool.BLANK,
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));
	}

	@Test
	public void testParseBasicTrimValues() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " + Base64.encode(" test@liferay.com : test ".getBytes()));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			HttpAuthorizationHeader.SCHEME_BASIC,
			httpAuthorizationHeader.getScheme());

		Map<String, String> authParameters =
			httpAuthorizationHeader.getAuthParameters();

		Assert.assertEquals(
			authParameters.toString(), 2, authParameters.size());

		Assert.assertEquals(
			"test@liferay.com",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));

		Assert.assertEquals(
			"test",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));
	}

	@Test
	public void testParseBasicURLDecode() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"Basic " +
				Base64.encode("test%40liferay%253ecom:test%40".getBytes()));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			HttpAuthorizationHeader.SCHEME_BASIC,
			httpAuthorizationHeader.getScheme());

		Map<String, String> authParameters =
			httpAuthorizationHeader.getAuthParameters();

		Assert.assertEquals(
			authParameters.toString(), 2, authParameters.size());

		Assert.assertEquals(
			"test@liferay%3ecom",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));

		Assert.assertEquals(
			"test%40",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD));
	}

	@Test
	public void testParseDigest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String[] digestParams = {
			"cnonce=\"0a4f113b\"", "nc=00000001",
			"nonce=\"dcd98b7102dd2f0e8b11d0f600bfb0c093\"",
			"opaque=\"5ccc069c403ebaf9f0171e9517f40e41", "qop=auth",
			"realm=\"testrealm@host.com\"",
			"response=\"6629fae49393a05397450978507c4ef1\"",
			"uri=\"/dir/index.html\"", "username=\"Mufasa\""
		};

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION,
			"DIGEST " + StringUtil.join(digestParams, ",\n"));

		HttpAuthorizationHeader httpAuthorizationHeader =
			_httpAuthManagerImpl.parse(mockHttpServletRequest);

		Assert.assertEquals(
			HttpAuthorizationHeader.SCHEME_DIGEST,
			httpAuthorizationHeader.getScheme());

		Map<String, String> authParameters =
			httpAuthorizationHeader.getAuthParameters();

		Assert.assertEquals(
			authParameters.toString(), digestParams.length,
			authParameters.size());

		Assert.assertEquals(
			"dcd98b7102dd2f0e8b11d0f600bfb0c093",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_NONCE));

		Assert.assertEquals(
			"testrealm@host.com",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_REALM));

		Assert.assertEquals(
			"/dir/index.html",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_URI));

		Assert.assertEquals(
			"Mufasa",
			httpAuthorizationHeader.getAuthParameter(
				HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME));
	}

	@Test
	public void testParseNullAuthorization() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Assert.assertEquals(
			null, _httpAuthManagerImpl.parse(mockHttpServletRequest));

		mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, StringPool.BLANK);

		Assert.assertEquals(
			null, _httpAuthManagerImpl.parse(mockHttpServletRequest));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testParseUnsupportedAuthorizationHeader() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.AUTHORIZATION, "Unsupported");

		_httpAuthManagerImpl.parse(mockHttpServletRequest);
	}

	private final HttpAuthManagerImpl _httpAuthManagerImpl =
		new HttpAuthManagerImpl();

}