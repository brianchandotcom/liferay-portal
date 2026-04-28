/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.common.contenttype.ContentType;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import java.net.URL;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christian Moura
 */
public class OpenIdConnectHttpUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToHttpOptionsGetRequestWithoutBody() throws Exception {
		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		httpRequest.setAuthorization("Bearer token");

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Assert.assertNull(httpOptions.getBody());
		Assert.assertEquals(
			"Bearer token", httpOptions.getHeader("Authorization"));
		Assert.assertEquals(
			"http://localhost:63636/userinfo", httpOptions.getLocation());
		Assert.assertFalse(httpOptions.isPost());
	}

	@Test
	public void testToHttpOptionsPostRequestWithBodyAndHeaders()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL("http://localhost:63636/token"));

		httpRequest.setBody("grant_type=authorization_code&code=xyz");
		httpRequest.setEntityContentType(ContentType.APPLICATION_URLENCODED);
		httpRequest.setHeader("X-Custom", "value");

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Assert.assertEquals(
			"http://localhost:63636/token", httpOptions.getLocation());
		Assert.assertTrue(httpOptions.isPost());

		Http.Body body = httpOptions.getBody();

		Assert.assertNotNull(body);
		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			"grant_type=authorization_code&code=xyz", body.getContent());
		Assert.assertEquals(
			ContentType.APPLICATION_URLENCODED.toString(),
			body.getContentType());

		Map<String, String> headers = httpOptions.getHeaders();

		Assert.assertEquals("value", headers.get("X-Custom"));
	}

	@Test
	public void testToHttpOptionsPostRequestWithoutEntityContentTypeDefaultsToFormUrlEncoded()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL("http://localhost:63636/token"));

		httpRequest.setBody("a=1");

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Http.Body body = httpOptions.getBody();

		Assert.assertNotNull(body);
		Assert.assertEquals(
			"application/x-www-form-urlencoded", body.getContentType());
	}

	@Test
	public void testToHttpOptionsTimeoutIsNotSetWhenBothTimeoutsAreZero()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Assert.assertEquals(0, httpOptions.getTimeout());
	}

	@Test
	public void testToHttpOptionsTimeoutUsesMaxOfConnectAndReadTimeouts()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		httpRequest.setConnectTimeout(1000);
		httpRequest.setReadTimeout(5000);

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Assert.assertEquals(5000, httpOptions.getTimeout());
	}

	@Test
	public void testToHttpOptionsTimeoutUsesReadTimeoutWhenConnectTimeoutIsZero()
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		httpRequest.setReadTimeout(3000);

		Http.Options httpOptions = OpenIdConnectHttpUtil.toHttpOptions(
			httpRequest);

		Assert.assertEquals(3000, httpOptions.getTimeout());
	}

	@Test
	public void testToHTTPResponseCopiesResponseCodeBodyAndContentType()
		throws Exception {

		Http.Options httpOptions = new Http.Options();

		Http.Response liferayHttpResponse = new Http.Response();

		liferayHttpResponse.setContentType("application/json");
		liferayHttpResponse.setResponseCode(200);

		httpOptions.setResponse(liferayHttpResponse);

		HTTPResponse httpResponse = OpenIdConnectHttpUtil.toHTTPResponse(
			httpOptions, "{\"sub\":\"subject\"}");

		Assert.assertEquals("{\"sub\":\"subject\"}", httpResponse.getBody());
		Assert.assertEquals(
			"application/json",
			String.valueOf(httpResponse.getEntityContentType()));
		Assert.assertEquals(200, httpResponse.getStatusCode());
	}

	@Test
	public void testToHTTPResponseWithoutContentType() throws Exception {
		Http.Options httpOptions = new Http.Options();

		Http.Response liferayHttpResponse = new Http.Response();

		liferayHttpResponse.setContentType(null);
		liferayHttpResponse.setResponseCode(204);

		httpOptions.setResponse(liferayHttpResponse);

		HTTPResponse httpResponse = OpenIdConnectHttpUtil.toHTTPResponse(
			httpOptions, StringPool.BLANK);

		Assert.assertEquals(204, httpResponse.getStatusCode());
		Assert.assertNull(httpResponse.getEntityContentType());
	}

}