/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.oauth.client.test.util.OAuthClientTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.http.internal.HttpImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.common.contenttype.ContentType;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.oauth2.sdk.util.URLUtils;

import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() {
		_httpImpl = new HttpImpl();

		ReflectionTestUtil.invoke(
			_httpImpl, "activate", new Class<?>[] {Map.class},
			Collections.emptyMap());

		Snapshot<Http> snapshot = ReflectionTestUtil.getFieldValue(
			HttpUtil.class, "_httpSnapshot");

		_originalServiceSupplier = ReflectionTestUtil.getAndSetFieldValue(
			snapshot, "_serviceSupplier", (Supplier<Http>)() -> _httpImpl);
	}

	@After
	public void tearDown() {
		Snapshot<Http> snapshot = ReflectionTestUtil.getFieldValue(
			HttpUtil.class, "_httpSnapshot");

		ReflectionTestUtil.setFieldValue(
			snapshot, "_serviceSupplier", _originalServiceSupplier);

		ReflectionTestUtil.invoke(_httpImpl, "deactivate", new Class<?>[0]);
	}

	@Test
	public void testSend() throws Exception {
		String subject = RandomTestUtil.randomString();

		HTTPResponse httpResponse = _send(
			"application/json", 200,
			JSONUtil.put(
				"sub", subject
			).toString());

		Map<String, Object> bodyMap = JSONObjectUtils.parse(
			httpResponse.getBody());

		Assert.assertEquals(subject, bodyMap.get("sub"));

		Assert.assertEquals(
			"application/json",
			String.valueOf(httpResponse.getEntityContentType()));
		Assert.assertEquals(200, httpResponse.getStatusCode());

		httpResponse = _send(null, 204, StringPool.BLANK);

		Assert.assertNull(httpResponse.getEntityContentType());
		Assert.assertEquals(204, httpResponse.getStatusCode());

		OAuthClientTestUtil.assertNoCookieWarnings(
			"/userinfo",
			serverURL -> OpenIdConnectHttpUtil.send(
				new HTTPRequest(HTTPRequest.Method.GET, new URL(serverURL))));
	}

	@Test
	public void testToHttpOptions() throws Exception {
		URL userInfoURL = new URL(
			"http://" + RandomTestUtil.randomString() + "/userinfo");

		HTTPRequest httpRequest = new HTTPRequest(
			HTTPRequest.Method.GET, userInfoURL);

		String authorization = "Bearer " + RandomTestUtil.randomString();

		httpRequest.setAuthorization(authorization);

		Http.Options httpOptions = _toHttpOptions(httpRequest);

		Assert.assertNull(httpOptions.getBody());
		Assert.assertEquals(
			Http.CookieSpec.STANDARD, httpOptions.getCookieSpec());
		Assert.assertEquals(
			authorization, httpOptions.getHeader("Authorization"));
		Assert.assertEquals(userInfoURL.toString(), httpOptions.getLocation());
		Assert.assertFalse(httpOptions.isPost());

		String tokenURL = "http://" + RandomTestUtil.randomString() + "/token";

		httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL(tokenURL));

		String code = RandomTestUtil.randomString();

		httpRequest.setBody("grant_type=authorization_code&code=" + code);

		httpRequest.setEntityContentType(ContentType.APPLICATION_URLENCODED);

		String headerName = "X-" + RandomTestUtil.randomString();
		String headerValue = RandomTestUtil.randomString();

		httpRequest.setHeader(headerName, headerValue);

		httpOptions = _toHttpOptions(httpRequest);

		Assert.assertEquals(tokenURL, httpOptions.getLocation());
		Assert.assertTrue(httpOptions.isPost());

		Http.Body body = httpOptions.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());

		Map<String, List<String>> bodyParameters = URLUtils.parseParameters(
			body.getContent());

		Assert.assertEquals(
			Collections.singletonList(code), bodyParameters.get("code"));
		Assert.assertEquals(
			Collections.singletonList("authorization_code"),
			bodyParameters.get("grant_type"));

		Assert.assertEquals(
			ContentType.APPLICATION_URLENCODED.toString(),
			body.getContentType());

		Map<String, String> headers = httpOptions.getHeaders();

		Assert.assertEquals(headerValue, headers.get(headerName));

		httpRequest = new HTTPRequest(
			HTTPRequest.Method.POST, new URL(tokenURL));

		httpRequest.setBody("a=1");

		httpOptions = _toHttpOptions(httpRequest);

		body = httpOptions.getBody();

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
			HTTPRequest.Method.GET,
			new URL("http://" + RandomTestUtil.randomString() + "/userinfo"));

		httpRequest.setConnectTimeout(connectTimeout);
		httpRequest.setReadTimeout(readTimeout);

		return httpRequest;
	}

	private HTTPResponse _send(
			String contentType, int responseCode, String responseJSON)
		throws Exception {

		try (OAuthClientTestUtil.HttpServerHandle httpServerHandle =
				OAuthClientTestUtil.startServer(
					contentType, null, "/userinfo", responseJSON,
					responseCode)) {

			return OpenIdConnectHttpUtil.send(
				new HTTPRequest(
					HTTPRequest.Method.GET,
					new URL(httpServerHandle.getURL())));
		}
	}

	private Http.Options _toHttpOptions(HTTPRequest httpRequest) {
		return ReflectionTestUtil.invoke(
			OpenIdConnectHttpUtil.class, "_toHttpOptions",
			new Class<?>[] {HTTPRequest.class}, httpRequest);
	}

	private HttpImpl _httpImpl;
	private Supplier<Http> _originalServiceSupplier;

}