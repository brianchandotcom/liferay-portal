/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 * @author Jorge García Jiménez
 */
@FeatureFlag("LPD-63415")
@RunWith(Arquillian.class)
public class OAuthAuthorizationServerMetadataWellKnownFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		for (OAuthClientASLocalMetadata oAuthClientASLocalMetadata :
				_oAuthClientASLocalMetadataLocalService.
					getCompanyOAuthClientASLocalMetadata(
						TestPropsValues.getCompanyId())) {

			_oAuthClientASLocalMetadataLocalService.
				deleteOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata.
						getOAuthClientASLocalMetadataId());
		}
	}

	@Test
	public void testProcessFilter() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String hostRootURL = StringBundler.concat(
			Http.HTTP_WITH_SLASH, company.getVirtualHostname(), ":",
			PortalUtil.getPortalServerPort(false),
			"/.well-known/oauth-authorization-server");

		HttpURLConnection httpURLConnection = _openConnection(
			hostRootURL, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();

		String issuer =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";

		String tokenEndpoint = issuer + "/o/oauth2/token";

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					null, TestPropsValues.getUserId(), issuer, issuer, issuer,
					false, issuer,
					new String[] {"authorization_code", "client_credentials"},
					new String[] {"openid"}, new String[] {"public"},
					tokenEndpoint, issuer);

		httpURLConnection = _openConnection(hostRootURL, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();

		_oAuthClientASLocalMetadataLocalService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
				issuer, issuer, issuer, true, issuer,
				new String[] {"authorization_code", "client_credentials"},
				new String[] {"openid"}, new String[] {"public"}, tokenEndpoint,
				issuer);

		httpURLConnection = _openConnection(hostRootURL, "GET");

		Assert.assertEquals(
			StringPool.STAR,
			httpURLConnection.getHeaderField("Access-Control-Allow-Origin"));
		Assert.assertEquals(
			"public, max-age=300",
			httpURLConnection.getHeaderField("Cache-Control"));
		Assert.assertEquals(
			HttpServletResponse.SC_OK, httpURLConnection.getResponseCode());
		Assert.assertEquals(
			oAuthClientASLocalMetadata.getOAuthASMetadataJSON(),
			StringUtil.read(httpURLConnection.getInputStream()));

		httpURLConnection.disconnect();

		httpURLConnection = _openConnection(hostRootURL, "OPTIONS");

		Assert.assertEquals(
			"Authorization, Content-Type",
			httpURLConnection.getHeaderField("Access-Control-Allow-Headers"));
		Assert.assertEquals(
			"GET, OPTIONS",
			httpURLConnection.getHeaderField("Access-Control-Allow-Methods"));
		Assert.assertEquals(
			StringPool.STAR,
			httpURLConnection.getHeaderField("Access-Control-Allow-Origin"));
		Assert.assertEquals(
			"300", httpURLConnection.getHeaderField("Access-Control-Max-Age"));
		Assert.assertEquals(
			HttpServletResponse.SC_NO_CONTENT,
			httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();

		httpURLConnection = _openConnection(hostRootURL, "POST");

		Assert.assertEquals(
			"GET, OPTIONS", httpURLConnection.getHeaderField("Allow"));
		Assert.assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			httpURLConnection.getResponseCode());

		httpURLConnection.disconnect();
	}

	private HttpURLConnection _openConnection(String urlString, String method)
		throws Exception {

		URI uri = URI.create(urlString);

		URL url = uri.toURL();

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.setRequestMethod(method);

		return httpURLConnection;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}