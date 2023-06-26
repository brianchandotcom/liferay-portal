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

package com.liferay.analytics.audit.header.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.OutputStream;

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
 * @author Alvaro Saugar
 */
@FeatureFlags("LPS-177196")
@RunWith(Arquillian.class)
public class AnalyticsAuditHeaderFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessFilterAPV() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getCompanyConfigurationTemporarySwapper(false, true, false)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection(
				null, "0", null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(headerFields.containsKey("X-Liferay-Request"));

			String header = httpURLConnection.getHeaderField(
				"X-Liferay-Request");

			Assert.assertEquals("anonymous", header);

			httpURLConnection.disconnect();
		}
	}

	@Test
	public void testProcessFilterDisabled() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getCompanyConfigurationTemporarySwapper(false, false, false)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection(
				null, null, null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(headerFields.containsKey("X-Liferay-Request"));
			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request-Company"));
			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request-Site"));

			httpURLConnection.disconnect();
		}
	}

	@Test
	public void testProcessFilterMALU() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				_getCompanyConfigurationTemporarySwapper(true, false, false)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection(
				null, "0", null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(headerFields.containsKey("X-Liferay-Request"));

			httpURLConnection.disconnect();
		}
	}

	private ConfigurationTemporarySwapper
			_getCompanyConfigurationTemporarySwapper(
				boolean enabledMALU, boolean enabledAPV, boolean enabledScope)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.analytics.audit.header.internal.configuration." +
				"AnalyticsAuditHeaderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"enabledAPV", enabledAPV
			).put(
				"enabledMALU", enabledMALU
			).put(
				"enabledScope", enabledScope
			).build());
	}

	private HttpURLConnection _openHttpURLConnection(
			String companyId, String userId, String siteId)
		throws IOException {

		URL url = new URL("http://localhost:8080/web/guesturlString");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");

		StringBuilder sb = new StringBuilder();

		if (companyId != null) {
			sb.append(WebKeys.COMPANY_ID);
			sb.append(StringPool.EQUAL);
			sb.append(companyId);
		}

		if (userId != null) {
			sb.append(WebKeys.USER_ID);
			sb.append(StringPool.EQUAL);
			sb.append(userId);
		}

		if (siteId != null) {
			sb.append(WebKeys.LAYOUT);
			sb.append(StringPool.EQUAL);
			sb.append(siteId);
		}

		String e = sb.toString();

		try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
			outputStream.write(e.getBytes());
			outputStream.flush();
		}

		return httpURLConnection;
	}

}