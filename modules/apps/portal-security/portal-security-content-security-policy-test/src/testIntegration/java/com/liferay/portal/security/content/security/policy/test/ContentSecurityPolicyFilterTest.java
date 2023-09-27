/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
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
	public void testProcessFilter() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper =
					_getCompanyConfigurationTemporarySwapper(false, null, "")) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));

			httpURLConnection.disconnect();
		}

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper =
					_getCompanyConfigurationTemporarySwapper(true, null, "")) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));

			String content = _getContent(httpURLConnection);

			Assert.assertFalse(content.contains("nonce="));

			httpURLConnection.disconnect();
		}

		String policy = "default-src 'self';";

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper =
					_getCompanyConfigurationTemporarySwapper(
						true, null, policy)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(
				headerFields.containsKey("Content-Security-Policy"));

			Assert.assertEquals(
				httpURLConnection.getHeaderField("Content-Security-Policy"),
				policy);

			httpURLConnection.disconnect();
		}

		policy =
			"default-src 'self'; script-src 'self' '[$NONCE$]'; style-src " +
				"'self' '[$NONCE$]'";

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper =
					_getCompanyConfigurationTemporarySwapper(
						true, null, policy)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(
				headerFields.containsKey("Content-Security-Policy"));

			String value = httpURLConnection.getHeaderField(
				"Content-Security-Policy");

			Assert.assertNotNull(value);

			int index = value.indexOf("nonce-") + "nonce-".length();

			String nonce = value.substring(index, index + 24);

			Assert.assertEquals(
				value,
				StringUtil.replace(policy, "[$NONCE$]", "nonce-" + nonce));

			String content = _getContent(httpURLConnection);

			Assert.assertTrue(
				content.contains("<link nonce=\"" + nonce + "\""));
			Assert.assertTrue(
				content.contains("<script nonce=\"" + nonce + "\""));
			Assert.assertTrue(
				content.contains("<style nonce=\"" + nonce + "\""));

			httpURLConnection.disconnect();
		}

		try (CompanyConfigurationTemporarySwapper
				configurationTemporarySwapper =
					_getCompanyConfigurationTemporarySwapper(
						true, new String[] {"/c/portal/layout", "/web/guest"},
						policy)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection();

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("Content-Security-Policy"));

			String content = _getContent(httpURLConnection);

			Assert.assertFalse(content.contains("nonce="));
		}
	}

	private CompanyConfigurationTemporarySwapper
			_getCompanyConfigurationTemporarySwapper(
				boolean enabled, String[] excludedPaths, String policy)
		throws Exception {

		return new CompanyConfigurationTemporarySwapper(
			TestPropsValues.getCompanyId(),
			"com.liferay.portal.security.content.security.policy.internal." +
				"configuration.ContentSecurityPolicyConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).put(
				"excludedPaths", excludedPaths
			).put(
				"policy", policy
			).build());
	}

	private String _getContent(HttpURLConnection httpURLConnection)
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

	private HttpURLConnection _openHttpURLConnection() throws IOException {
		URL url = new URL("http://localhost:8080/web/guest");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestMethod("GET");

		return httpURLConnection;
	}

}