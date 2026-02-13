/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.List;

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
public class OAuth2WellKnownAuthorizationServerServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testNoMetadataEntriesReturns404() throws Exception {
		String urlString =
			TestPropsValues.PORTAL_URL +
				"/o/.well-known/oauth-authorization-server";

		Http.Options options = new Http.Options();

		options.setFollowRedirects(false);
		options.setLocation(urlString);

		HttpUtil.URLtoString(options);

		List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
			_oAuthClientASLocalMetadataLocalService.
				getCompanyOAuthClientASLocalMetadata(
					TestPropsValues.getCompanyId());

		Assert.assertTrue(oAuthClientASLocalMetadatas.isEmpty());

		Http.Response response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		String issuer1 = RandomTestUtil.randomString() + ".com";

		String url1 = Http.HTTPS_WITH_SLASH + issuer1;

		String supported1 = RandomTestUtil.randomString();

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					TestPropsValues.getUserId(), url1, url1, url1, false,
					new String[] {supported1}, new String[] {supported1},
					new String[] {"public"}, url1, url1);

		oAuthClientASLocalMetadatas =
			_oAuthClientASLocalMetadataLocalService.
				getCompanyOAuthClientASLocalMetadata(
					TestPropsValues.getCompanyId());

		Assert.assertFalse(oAuthClientASLocalMetadatas.isEmpty());

		options.setFollowRedirects(false);
		options.setLocation(urlString);

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata1.
						getOAuthClientASLocalMetadataId(),
					url1, url1, url1, true, new String[] {supported1},
					new String[] {supported1}, new String[] {"public"}, url1,
					url1);

		options.setFollowRedirects(false);
		options.setLocation(urlString);

		String responseJSON = HttpUtil.URLtoString(options);

		response = options.getResponse();

		HttpUtil.URLtoString(options);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());
		Assert.assertEquals(
			responseJSON, oAuthClientASLocalMetadata1.getOAuthASMetadataJSON());

		String issuer2 = RandomTestUtil.randomString() + ".com";

		String url2 = Http.HTTPS_WITH_SLASH + issuer2;

		String supported2 = RandomTestUtil.randomString();

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata2 =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					TestPropsValues.getUserId(), url2, url2, url2, true,
					new String[] {supported2}, new String[] {supported2},
					new String[] {"public"}, url2, url2);

		response = options.getResponse();
		responseJSON = HttpUtil.URLtoString(options);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());
		Assert.assertEquals(
			responseJSON, oAuthClientASLocalMetadata1.getOAuthASMetadataJSON());
		Assert.assertNotEquals(
			responseJSON, oAuthClientASLocalMetadata2.getOAuthASMetadataJSON());

		_oAuthClientASLocalMetadataLocalService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadata1.getOAuthClientASLocalMetadataId(),
				url1, url1, url1, false, new String[] {supported1},
				new String[] {supported1}, new String[] {"public"}, url1, url1);

		responseJSON = HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseJSON, oAuthClientASLocalMetadata2.getOAuthASMetadataJSON());
		Assert.assertNotEquals(
			responseJSON, oAuthClientASLocalMetadata1.getOAuthASMetadataJSON());

		options.setFollowRedirects(false);
		options.setLocation(
			TestPropsValues.PORTAL_URL +
				"/o/.well-known/oauth-authorization-server/" +
					RandomTestUtil.randomString());

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		options.setLocation(
			TestPropsValues.PORTAL_URL +
				"/o/.well-known/oauth-authorization-server/" + issuer1);

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		String issuerSegment = URLEncoder.encode(
			issuer2.trim(), StandardCharsets.UTF_8);

		options.setLocation(
			TestPropsValues.PORTAL_URL +
				"/o/.well-known/oauth-authorization-server/" + issuerSegment);

		responseJSON = HttpUtil.URLtoString(options);

		response = options.getResponse();

		HttpUtil.URLtoString(options);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());
		Assert.assertEquals(
			responseJSON, oAuthClientASLocalMetadata2.getOAuthASMetadataJSON());
	}

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}