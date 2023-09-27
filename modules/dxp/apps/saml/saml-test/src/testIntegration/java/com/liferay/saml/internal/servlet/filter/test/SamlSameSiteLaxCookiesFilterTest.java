/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class SamlSameSiteLaxCookiesFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_paramsMap = HashMapBuilder.put(
			"RelayState", "TEST_RELAYSTATE"
		).put(
			"SAMLRequest", "TEST_SAMLREQUEST"
		).put(
			"SAMLResponse", "TEST_SAMLRESPONSE"
		).build();

		StringBundler sb = new StringBundler(4 * _paramsMap.size());

		for (Map.Entry<String, String> entry : _paramsMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}

		sb.setIndex(sb.index() - 1);

		_postBody = sb.toString();
	}

	@Test
	public void testACSSameSiteLaxCookiesSupport() throws Exception {
		_execute(new URL("http://localhost:8080/c/portal/saml/acs"));
	}

	@Test
	public void testSLOSameSiteLaxCookies() throws Exception {
		_execute(new URL("http://localhost:8080/c/portal/saml/slo"));
	}

	@Test
	public void testSSOSameSiteLaxCookies() throws Exception {
		_execute(new URL("http://localhost:8080/c/portal/saml/sso"));
	}

	private void _execute(URL url) throws Exception {
		CookieManager cookieManager = new CookieManager();

		CookieHandler.setDefault(cookieManager);

		HttpURLConnection httpClient = (HttpURLConnection)url.openConnection();

		httpClient.setDoOutput(true);
		httpClient.setRequestMethod("POST");

		try (DataOutputStream dataOutputStream = new DataOutputStream(
				httpClient.getOutputStream())) {

			dataOutputStream.writeBytes(_postBody);
			dataOutputStream.flush();
		}

		String contentType = httpClient.getHeaderField("Content-Type");

		Assert.assertTrue(
			"Response content type is not text/html",
			Validator.isNotNull(contentType) &&
			contentType.startsWith("text/html"));

		CookieStore cookieStore = cookieManager.getCookieStore();

		List<HttpCookie> cookies = cookieStore.getCookies();

		cookies.forEach(
			httpCookie -> Assert.assertFalse(
				"New JSESSIONID cookie received, so session was undesirably " +
					"invalidated",
				Objects.equals(httpCookie.getName(), "JSESSIONID")));

		Map<String, String> paramsMap = new HashMap<>(_paramsMap);

		Collection<String> paramValues = paramsMap.values();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpClient.getInputStream()))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				paramValues.removeIf(line::contains);
			}
		}

		Assert.assertTrue(
			"Missing reflected fields in response content: " +
				paramsMap.keySet(),
			paramValues.isEmpty());
	}

	private static Map<String, String> _paramsMap;
	private static String _postBody;

}