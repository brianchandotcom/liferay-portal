/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
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

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Christian Moura
 */
public class OpenIdConnectHttpUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSend() throws Exception {
		HTTPResponse httpResponse = _send(
			"application/json", 200, "{\"sub\":\"subject\"}");

		Assert.assertEquals("{\"sub\":\"subject\"}", httpResponse.getBody());
		Assert.assertEquals(
			"application/json",
			String.valueOf(httpResponse.getEntityContentType()));
		Assert.assertEquals(200, httpResponse.getStatusCode());

		httpResponse = _send(null, 204, StringPool.BLANK);

		Assert.assertEquals(204, httpResponse.getStatusCode());
		Assert.assertNull(httpResponse.getEntityContentType());
	}

	@Test
	public void testToHttpOptions() throws Exception {
		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		httpRequest.setAuthorization("Bearer token");

		Http.Options httpOptions = _toHttpOptions(httpRequest);

		Assert.assertNull(httpOptions.getBody());
		Assert.assertEquals(
			"Bearer token", httpOptions.getHeader("Authorization"));
		Assert.assertEquals(
			"http://localhost:63636/userinfo", httpOptions.getLocation());
		Assert.assertFalse(httpOptions.isPost());

		httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL("http://localhost:63636/token"));

		httpRequest.setBody("grant_type=authorization_code&code=xyz");
		httpRequest.setEntityContentType(ContentType.APPLICATION_URLENCODED);
		httpRequest.setHeader("X-Custom", "value");

		httpOptions = _toHttpOptions(httpRequest);

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

		httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL("http://localhost:63636/token"));

		httpRequest.setBody("a=1");

		httpOptions = _toHttpOptions(httpRequest);

		body = httpOptions.getBody();

		Assert.assertNotNull(body);
		Assert.assertEquals(
			"application/x-www-form-urlencoded", body.getContentType());

		httpOptions = _toHttpOptions(_createHTTPRequest(0, 0));

		Assert.assertEquals(0, httpOptions.getTimeout());

		httpOptions = _toHttpOptions(_createHTTPRequest(1000, 5000));

		Assert.assertEquals(5000, httpOptions.getTimeout());
	}

	private HTTPRequest _createHTTPRequest(int connectTimeout, int readTimeout)
		throws Exception {

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, new URL("http://localhost:63636/userinfo"));

		httpRequest.setConnectTimeout(connectTimeout);
		httpRequest.setReadTimeout(readTimeout);

		return httpRequest;
	}

	private HTTPResponse _send(
			String contentType, int responseCode, String responseJSON)
		throws Exception {

		try (MockedStatic<HttpUtil> httpUtilMockedStatic = Mockito.mockStatic(
				HttpUtil.class)) {

			httpUtilMockedStatic.when(
				() -> HttpUtil.URLtoString(Mockito.any(Http.Options.class))
			).thenAnswer(
				invocation -> {
					Http.Options httpOptions = invocation.getArgument(0);

					Http.Response httpResponse = new Http.Response();

					httpResponse.setContentType(contentType);
					httpResponse.setResponseCode(responseCode);

					httpOptions.setResponse(httpResponse);

					return responseJSON;
				}
			);

			HTTPRequest httpRequest = new HTTPRequest(
				HTTPRequest.Method.GET,
				new URL("http://localhost:63636/userinfo"));

			return OpenIdConnectHttpUtil.send(httpRequest);
		}
	}

	private Http.Options _toHttpOptions(HTTPRequest httpRequest) {
		return ReflectionTestUtil.invoke(
			OpenIdConnectHttpUtil.class, "_toHttpOptions",
			new Class<?>[] {HTTPRequest.class}, httpRequest);
	}

}