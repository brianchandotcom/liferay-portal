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

package com.liferay.portal.security.content.security.policy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Olivér Kecskeméty
 */
@FeatureFlags("LPS-134060")
@RunWith(Arquillian.class)
public class ContentSecurityPolicyFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testWhenConfigDisabledFilterShouldBeDisabled()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper = _configureContentSecurityPolicy(
					false, "")) {

			HttpURLConnection httpURLConnection = _createHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));
		}
	}

	@Test
	public void testWhenConfigEnabledAndPolicyEmptyCspHeaderShouldNotBeAdded()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper = _configureContentSecurityPolicy(
					true, "")) {

			HttpURLConnection httpURLConnection = _createHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));
		}
	}

	@Test
	public void testWhenConfigEnabledAndPolicyEmptyNonceShouldNotBeAddedToTags()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper = _configureContentSecurityPolicy(
					true, "")) {

			HttpURLConnection httpURLConnection = _createHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));

			String responseBody = _getResponseBody(httpURLConnection);

			Assert.assertFalse(responseBody.contains("nonce="));
		}
	}

	@Test
	public void testWhenConfiguredCorrectlyCspHeaderShouldBeAdded()
		throws Exception {

		String cspPolicy = "default-src 'self';";

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper = _configureContentSecurityPolicy(
					true, cspPolicy)) {

			HttpURLConnection httpURLConnection = _createHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(
				headerFields.containsKey("Content-Security-Policy"));

			Assert.assertEquals(
				httpURLConnection.getHeaderField("Content-Security-Policy"),
				cspPolicy);
		}
	}

	@Test
	public void testWhenConfiguredCorrectlyNonceShouldBeGeneratedToBodyAndHeader()
		throws Exception {

		String cspPolicy =
			"default-src 'self'; script-src 'self' '[$NONCE_TOKEN$]'; " +
				"style-src 'self' '[$NONCE_TOKEN$]'";

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper = _configureContentSecurityPolicy(
					true, cspPolicy)) {

			HttpURLConnection httpURLConnection = _createHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(
				headerFields.containsKey("Content-Security-Policy"));

			String contentSecurityPolicyHeaderValue =
				httpURLConnection.getHeaderField("Content-Security-Policy");

			Assert.assertNotNull(contentSecurityPolicyHeaderValue);

			int nonceStartPos = contentSecurityPolicyHeaderValue.indexOf(
				"nonce-");

			int noncePrefixLength = "nonce-".length();

			int nonceRandomStartPos = nonceStartPos + noncePrefixLength;

			String nonce = contentSecurityPolicyHeaderValue.substring(
				nonceRandomStartPos, nonceRandomStartPos + 24);

			String substitutedCspPolicy = StringUtil.replace(
				cspPolicy, "[$NONCE_TOKEN$]", "nonce-" + nonce);

			Assert.assertEquals(
				contentSecurityPolicyHeaderValue, substitutedCspPolicy);

			String responseBody = _getResponseBody(httpURLConnection);

			Assert.assertTrue(
				responseBody.contains("<link nonce=\"" + nonce + "\""));

			Assert.assertTrue(
				responseBody.contains("<script nonce=\"" + nonce + "\""));

			Assert.assertTrue(
				responseBody.contains("<style nonce=\"" + nonce + "\""));
		}
	}

	private CompanyConfigurationTemporarySwapper
			_configureContentSecurityPolicy(boolean enabled, String policy)
		throws Exception {

		return new CompanyConfigurationTemporarySwapper(
			TestPropsValues.getCompanyId(),
			"com.liferay.portal.security.content.security.policy.internal." +
				"configuration.ContentSecurityPolicyConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).put(
				"policy", policy
			).build(),
			SettingsFactoryUtil.getSettingsFactory());
	}

	private HttpURLConnection _createHttpURLConnection() throws IOException {
		URL url = new URL("http://localhost:8080/web/guest");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestMethod("GET");

		return httpURLConnection;
	}

	private String _getResponseBody(HttpURLConnection httpURLConnection)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpURLConnection.getInputStream()))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		}

		return sb.toString();
	}

}