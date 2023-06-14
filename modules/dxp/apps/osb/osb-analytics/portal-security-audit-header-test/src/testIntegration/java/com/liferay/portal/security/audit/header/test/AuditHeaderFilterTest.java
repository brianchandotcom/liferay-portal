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

package com.liferay.portal.security.audit.header.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@FeatureFlags("LPS-177196")
@RunWith(Arquillian.class)
public class AuditHeaderFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();





	@Test
	public void testProcessFilterDisabled() throws Exception {
		try (ConfigurationTemporarySwapper
				 configurationTemporarySwapper =
				 _getCompanyConfigurationTemporarySwapper(false, false, false)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection(null, null, null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request"));
			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request-Company"));
			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request-Site"));

			httpURLConnection.disconnect();
		}

	}

	@Test
	public void testProcessFilterMALU() throws Exception {

		try (ConfigurationTemporarySwapper
				 configurationTemporarySwapper =
				 _getCompanyConfigurationTemporarySwapper(true, false, false)) {

			HttpURLConnection httpURLConnection =
				_openHttpURLConnection(null, "0", null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertFalse(
				headerFields.containsKey("X-Liferay-Request"));

			httpURLConnection.disconnect();
		}
	}


	@Test
	public void testProcessFilterAPV() throws Exception {

		try (ConfigurationTemporarySwapper
				 configurationTemporarySwapper =
				 _getCompanyConfigurationTemporarySwapper(false, true, false)) {

			HttpURLConnection httpURLConnection = _openHttpURLConnection(null, "0", null);

			Map<String, List<String>> headerFields =
				httpURLConnection.getHeaderFields();

			Assert.assertTrue(
				headerFields.containsKey("X-Liferay-Request"));
			String header = httpURLConnection.getHeaderField("X-Liferay-Request");
			Assert.assertEquals(header, "anonymous");
			httpURLConnection.disconnect();
		}

	}


	private ConfigurationTemporarySwapper
	_getCompanyConfigurationTemporarySwapper(
		boolean enabledMALU, boolean enabledAPV, boolean enabledScope)
		throws Exception {

		return new ConfigurationTemporarySwapper(
			"com.liferay.portal.security.audit.header.internal." +
			"configuration.AuditHeaderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"enabledMALU", enabledMALU
			).put(
				"enabledAPV", enabledAPV
			).put(
				"enabledScope", enabledScope
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

	private HttpURLConnection _openHttpURLConnection(String companyId, String userId, String siteId) throws IOException {
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