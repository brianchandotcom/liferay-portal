/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URI;

import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@FeatureFlag("LPD-63415")
@RunWith(Arquillian.class)
public class OAuth2WellKnownProtectedResourceServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		for (OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata :
				_oAuthClientPRLocalMetadataLocalService.
					getCompanyOAuthClientPRLocalMetadata(
						TestPropsValues.getCompanyId())) {

			_oAuthClientPRLocalMetadataLocalService.
				deleteOAuthClientPRLocalMetadata(
					oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId());
		}
	}

	@Test
	public void testDoGetHostRootWellKnown() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String hostRootURL = StringBundler.concat(
			Http.HTTP_WITH_SLASH, company.getVirtualHostname(),
			":8080/.well-known/oauth-protected-resource");

		HttpURLConnection notFoundHttpURLConnection = _openConnection(
			hostRootURL, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			notFoundHttpURLConnection.getResponseCode());

		notFoundHttpURLConnection.disconnect();

		String authorizationServer =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		String resource = authorizationServer + "/o/mcp";

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				addOAuthClientPRLocalMetadata(
					null, TestPropsValues.getUserId(),
					new String[] {authorizationServer}, new String[] {"header"},
					false, resource, "MCP Server", new String[] {"mcp.read"});

		HttpURLConnection disabledHttpURLConnection = _openConnection(
			hostRootURL, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			disabledHttpURLConnection.getResponseCode());

		disabledHttpURLConnection.disconnect();

		_oAuthClientPRLocalMetadataLocalService.
			updateOAuthClientPRLocalMetadata(
				oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId(),
				new String[] {authorizationServer}, new String[] {"header"},
				true, resource, "MCP Server", new String[] {"mcp.read"});

		HttpURLConnection getHttpURLConnection = _openConnection(
			hostRootURL, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_OK, getHttpURLConnection.getResponseCode());
		Assert.assertEquals(
			"*",
			getHttpURLConnection.getHeaderField("Access-Control-Allow-Origin"));
		Assert.assertEquals(
			"public, max-age=300",
			getHttpURLConnection.getHeaderField("Cache-Control"));

		String getResponseBody = _readBody(getHttpURLConnection);

		getHttpURLConnection.disconnect();

		Assert.assertEquals(
			_oAuthClientPRLocalMetadataLocalService.
				getOAuthClientPRLocalMetadata(
					oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId()
				).getMetadataJSON(),
			getResponseBody);

		HttpURLConnection optionsHttpURLConnection = _openConnection(
			hostRootURL, "OPTIONS");

		Assert.assertEquals(
			HttpServletResponse.SC_NO_CONTENT,
			optionsHttpURLConnection.getResponseCode());
		Assert.assertEquals(
			"*",
			optionsHttpURLConnection.getHeaderField(
				"Access-Control-Allow-Origin"));
		Assert.assertEquals(
			"GET, OPTIONS",
			optionsHttpURLConnection.getHeaderField(
				"Access-Control-Allow-Methods"));

		optionsHttpURLConnection.disconnect();

		HttpURLConnection postHttpURLConnection = _openConnection(
			hostRootURL, "POST");

		Assert.assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			postHttpURLConnection.getResponseCode());
		Assert.assertEquals(
			"GET, OPTIONS", postHttpURLConnection.getHeaderField("Allow"));

		postHttpURLConnection.disconnect();
	}

	private HttpURLConnection _openConnection(String urlString, String method)
		throws Exception {

		HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(
			urlString
		).toURL(
		).openConnection();

		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.setRequestMethod(method);

		return httpURLConnection;
	}

	private String _readBody(HttpURLConnection httpURLConnection)
		throws Exception {

		InputStream inputStream = httpURLConnection.getInputStream();

		if (inputStream == null) {
			return null;
		}

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			StringBuilder stringBuilder = new StringBuilder();
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}

			return stringBuilder.toString();
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}