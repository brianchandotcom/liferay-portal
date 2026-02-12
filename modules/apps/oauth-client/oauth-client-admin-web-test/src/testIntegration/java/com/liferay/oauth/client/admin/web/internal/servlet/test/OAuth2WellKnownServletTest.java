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
@RunWith(Arquillian.class)
public class OAuth2WellKnownServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testNoMetadataEntriesReturns404() throws Exception {

		// I make the request without any entries created. It returns 404.

		String urlString =
			"http://localhost:8080/o/.well-known/oauth-authorization-server";

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

		// An entry has been created but is not enabled. Returns 404.

		String issuer1 = RandomTestUtil.randomString() + ".com";

		String okURL1 = Http.HTTPS_WITH_SLASH + issuer1;

		String supported1 = RandomTestUtil.randomString();

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					TestPropsValues.getUserId(), okURL1, okURL1, okURL1, false,
					new String[] {supported1}, new String[] {supported1},
					new String[] {"public"}, okURL1, okURL1);

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

		// The existing entry is enabled. It returns 200 and
		// the JSON is the same as in the database.

		oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata1.
						getOAuthClientASLocalMetadataId(),
					okURL1, okURL1, okURL1, true, new String[] {supported1},
					new String[] {supported1}, new String[] {"public"}, okURL1,
					okURL1);

		options.setLocation(urlString);
		options.setFollowRedirects(false);

		String responseBody = HttpUtil.URLtoString(options);

		response = options.getResponse();

		HttpUtil.URLtoString(options);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseBody, oAuthClientASLocalMetadata1.getOAuthASMetadataJSON());

		// Another entry is created and enabled. Returns 200 and the json
		// corresponds to the first entry created.

		String issuer2 = RandomTestUtil.randomString() + ".com";

		String okURL2 = Http.HTTPS_WITH_SLASH + issuer2;

		String supported2 = RandomTestUtil.randomString();

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata2 =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					TestPropsValues.getUserId(), okURL2, okURL2, okURL2, true,
					new String[] {supported2}, new String[] {supported2},
					new String[] {"public"}, okURL2, okURL2);

		responseBody = HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseBody, oAuthClientASLocalMetadata1.getOAuthASMetadataJSON());

		// The first entry is disabled. Returns the second entry and the same
		// json as in the database.

		_oAuthClientASLocalMetadataLocalService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadata1.getOAuthClientASLocalMetadataId(),
				okURL1, okURL1, okURL1, false, new String[] {supported1},
				new String[] {supported1}, new String[] {"public"}, okURL1,
				okURL1);

		responseBody = HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseBody, oAuthClientASLocalMetadata2.getOAuthASMetadataJSON());

		// Attempts to access with a non-existent issuer. Returns 404.

		String urlStringR = RandomTestUtil.randomString();

		urlString =
			"http://localhost:8080/o/.well-known/oauth-authorization-server/" +
				urlStringR;

		options.setLocation(urlString);

		options.setFollowRedirects(false);

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		// An attempt is made to access with the issuer deactivated and 404.

		urlString =
			"http://localhost:8080/o/.well-known/oauth-authorization-server/" +
				issuer1;

		options.setLocation(urlString);

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		// Attempt to access with the issuer activated and 200.

		String issuerSegment = URLEncoder.encode(
			issuer2.trim(), StandardCharsets.UTF_8);

		urlString =
			"http://localhost:8080/o/.well-known/oauth-authorization-server/" +
				issuerSegment;

		options.setLocation(urlString);

		responseBody = HttpUtil.URLtoString(options);

		response = options.getResponse();

		HttpUtil.URLtoString(options);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseBody, oAuthClientASLocalMetadata2.getOAuthASMetadataJSON());
	}

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}