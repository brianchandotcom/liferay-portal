/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.Map;

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
public class OAuth2WellKnownProtectedResourceMetadataFilterTest {

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
	public void testProcessFilter() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String urlString = StringBundler.concat(
			Http.HTTP_WITH_SLASH, company.getVirtualHostname(), ":",
			PortalUtil.getPortalServerPort(false),
			"/.well-known/oauth-protected-resource");

		HttpResponse<String> httpResponse = _send(urlString, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, httpResponse.statusCode());

		String authorizationServer =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString();
		String bearerMethodSupported = RandomTestUtil.randomString();
		String resourceName = RandomTestUtil.randomString();
		String scopeSupported = RandomTestUtil.randomString();

		String protectedResourceURI =
			authorizationServer + StringPool.SLASH +
				RandomTestUtil.randomString();

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				addOAuthClientPRLocalMetadata(
					null, TestPropsValues.getUserId(),
					new String[] {authorizationServer},
					new String[] {bearerMethodSupported}, false,
					protectedResourceURI, resourceName,
					new String[] {scopeSupported});

		httpResponse = _send(urlString, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, httpResponse.statusCode());

		_oAuthClientPRLocalMetadataLocalService.
			updateOAuthClientPRLocalMetadata(
				oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId(),
				new String[] {authorizationServer},
				new String[] {bearerMethodSupported}, true,
				protectedResourceURI, resourceName,
				new String[] {scopeSupported});

		httpResponse = _send(urlString, "GET");

		_assertHeader(
			StringPool.STAR, "Access-Control-Allow-Origin",
			httpResponse.headers());
		_assertHeader(
			"public, max-age=300", "Cache-Control", httpResponse.headers());

		Assert.assertEquals(
			HttpServletResponse.SC_OK, httpResponse.statusCode());

		OAuthClientPRLocalMetadata existingOAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				getOAuthClientPRLocalMetadata(
					oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId());

		Assert.assertEquals(
			existingOAuthClientPRLocalMetadata.getMetadataJSON(),
			httpResponse.body());

		httpResponse = _send(urlString, "HEAD");

		_assertHeader(
			"public, max-age=300", "Cache-Control", httpResponse.headers());

		Assert.assertEquals(
			HttpServletResponse.SC_OK, httpResponse.statusCode());
		Assert.assertEquals(StringPool.BLANK, httpResponse.body());

		httpResponse = _send(urlString, "OPTIONS");

		_assertHeader(
			"Authorization, Content-Type, MCP-Protocol-Version",
			"Access-Control-Allow-Headers", httpResponse.headers());
		_assertHeader(
			"GET, HEAD, OPTIONS", "Access-Control-Allow-Methods",
			httpResponse.headers());
		_assertHeader(
			StringPool.STAR, "Access-Control-Allow-Origin",
			httpResponse.headers());
		_assertHeader("300", "Access-Control-Max-Age", httpResponse.headers());

		Assert.assertEquals(
			HttpServletResponse.SC_NO_CONTENT, httpResponse.statusCode());

		httpResponse = _send(urlString, "POST");

		_assertHeader("GET, HEAD, OPTIONS", "Allow", httpResponse.headers());

		Assert.assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			httpResponse.statusCode());
	}

	private void _assertHeader(
		String expectedHeaderValue, String headerName,
		HttpHeaders httpHeaders) {

		Map<String, List<String>> map = httpHeaders.map();

		List<String> headerValues = map.get(headerName);

		Assert.assertEquals(expectedHeaderValue, headerValues.get(0));
	}

	private HttpResponse<String> _send(String urlString, String method)
		throws Exception {

		return _httpClient.send(
			HttpRequest.newBuilder(
			).uri(
				URI.create(urlString)
			).method(
				method, HttpRequest.BodyPublishers.noBody()
			).build(),
			HttpResponse.BodyHandlers.ofString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private final HttpClient _httpClient = HttpClient.newBuilder(
	).followRedirects(
		HttpClient.Redirect.NEVER
	).build();

	@Inject
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}