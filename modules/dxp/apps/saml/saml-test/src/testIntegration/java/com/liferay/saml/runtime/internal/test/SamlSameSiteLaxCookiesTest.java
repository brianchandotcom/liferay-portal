/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.runtime.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class SamlSameSiteLaxCookiesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	public SamlSameSiteLaxCookiesTest() {
		_paramsMap = HashMapBuilder.put(
			"RelayState", "TEST_RELAYSTATE"
		).put(
			"SAMLRequest", "TEST_SAMLREQUEST"
		).put(
			"SAMLResponse", "TEST_SAMLRESPONSE"
		).build();

		Set<Map.Entry<String, String>> entrySet = _paramsMap.entrySet();

		Stream<Map.Entry<String, String>> stream = entrySet.stream();

		_postBody = stream.map(
			entry -> StringBundler.concat(entry.getKey(), "=", entry.getValue())
		).collect(
			Collectors.joining("&")
		);
	}

	public void execute(URL url) throws Exception {
		CookieManager cookieManager = new CookieManager();

		CookieHandler.setDefault(cookieManager);

		HttpURLConnection httpClient = (HttpURLConnection)url.openConnection();

		httpClient.setRequestMethod("POST");

		httpClient.setDoOutput(true);

		try (DataOutputStream wr = new DataOutputStream(
				httpClient.getOutputStream())) {

			wr.writeBytes(_postBody);
			wr.flush();
		}

		String contentType = httpClient.getHeaderField("Content-Type");

		Assert.assertTrue(
			"Response is not text/html content type",
			Validator.isNotNull(contentType) &&
			contentType.startsWith("text/html"));

		CookieStore cookieStore = cookieManager.getCookieStore();

		List<HttpCookie> cookies = cookieStore.getCookies();

		cookies.forEach(
			httpCookie -> Assert.assertFalse(
				"New JSESSIONID cookie received, so session was undesirably " +
					"invalidated",
				Objects.equals("JSESSIONID", httpCookie.getName())));

		Map<String, String> paramsMap = new HashMap<>(_paramsMap);

		Collection<String> testValues = paramsMap.values();

		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(httpClient.getInputStream()))) {

			String line;

			while ((line = in.readLine()) != null) {
				testValues.removeIf(line::contains);
			}
		}

		Assert.assertTrue(
			"Missing reflected fields in response content: " +
				paramsMap.keySet(),
			testValues.isEmpty());
	}

	@Test
	public void testACSSameSiteLaxCookiesSupport() throws Exception {
		execute(new URL("http://localhost:8080/c/portal/saml/acs"));
	}

	@Test
	public void testSLOSameSiteLaxCookies() throws Exception {
		execute(new URL("http://localhost:8080/c/portal/saml/slo"));
	}

	@Test
	public void testSSOSameSiteLaxCookies() throws Exception {
		execute(new URL("http://localhost:8080/c/portal/saml/sso"));
	}

	private final Map<String, String> _paramsMap;
	private final String _postBody;

}