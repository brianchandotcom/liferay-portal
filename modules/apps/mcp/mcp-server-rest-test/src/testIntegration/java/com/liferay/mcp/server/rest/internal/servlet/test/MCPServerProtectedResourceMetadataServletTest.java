/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@FeatureFlag("LPD-63415")
@RunWith(Arquillian.class)
public class MCPServerProtectedResourceMetadataServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testServiceWithGet() throws Exception {
		Http.Options options = new Http.Options();

		options.setLocation(_getURL());

		String body = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		Assert.assertEquals(200, response.getResponseCode());

		Assert.assertTrue(
			response.getContentType(),
			response.getContentType(
			).contains(
				"application/json"
			));
		Assert.assertEquals(
			"public, max-age=300",
			response.getHeader(HttpHeaders.CACHE_CONTROL));
		Assert.assertEquals(
			"*", response.getHeader("Access-Control-Allow-Origin"));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(body);

		String portalURL =
			"http://localhost:" + PortalUtil.getPortalServerPort(false);

		JSONArray authorizationServersJSONArray = jsonObject.getJSONArray(
			"authorization_servers");

		Assert.assertEquals(
			portalURL, authorizationServersJSONArray.getString(0));

		JSONArray bearerMethodsSupportedJSONArray = jsonObject.getJSONArray(
			"bearer_methods_supported");

		Assert.assertEquals(
			"header", bearerMethodsSupportedJSONArray.getString(0));

		Assert.assertEquals(
			portalURL + "/o/mcp", jsonObject.getString("resource"));
		Assert.assertEquals(
			"Liferay MCP Server", jsonObject.getString("resource_name"));
	}

	@Test
	public void testServiceWithPost() throws Exception {
		Http.Options options = new Http.Options();

		options.setLocation(_getURL());
		options.setPost(true);

		_http.URLtoString(options);

		Http.Response response = options.getResponse();

		Assert.assertEquals(405, response.getResponseCode());

		Assert.assertEquals("GET, HEAD, OPTIONS", response.getHeader("Allow"));
	}

	private String _getURL() {
		return "http://localhost:" + PortalUtil.getPortalServerPort(false) +
			"/o/.well-known/oauth-protected-resource";
	}

	@Inject
	private Http _http;

}