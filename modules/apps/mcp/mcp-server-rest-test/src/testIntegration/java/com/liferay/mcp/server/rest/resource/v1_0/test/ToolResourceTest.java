/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mcp.server.rest.client.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.client.http.HttpInvoker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;

import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class ToolResourceTest extends BaseToolResourceTestCase {

	@Override
	@Test
	public void testGetToolSetToolSetNameTool() throws Exception {
		Tool tool = toolResource.getToolSetToolSetNameTool(
			"mcp-server-v1.0", "getToolSetsPage");

		Assert.assertEquals("getToolSetsPage", tool.getName());
		Assert.assertNotNull(tool.getInputSchema());
	}

	@Override
	@Test
	public void testPostToolSetToolSetNameToolInvoke() throws Exception {
		byte[] bytes = RandomTestUtil.randomBytes();
		Base64.Encoder encoder = Base64.getEncoder();
		String fileName =
			"mcp-upload-" + RandomTestUtil.randomString() + ".txt";

		HttpInvoker.HttpResponse httpResponse =
			toolResource.postToolSetToolSetNameToolInvokeHttpResponse(
				"headless-delivery-v1.0", "postSiteDocument",
				JSONUtil.put(
					"file",
					JSONUtil.put(
						"contentType", "text/plain"
					).put(
						"data", encoder.encodeToString(bytes)
					).put(
						"filename", fileName
					)
				).put(
					"siteId", testGroup.getGroupId()
				).toString());

		Assert.assertEquals(
			httpResponse.getContent(), 200, httpResponse.getStatusCode());

		JSONObject documentJSONObject = JSONFactoryUtil.createJSONObject(
			httpResponse.getContent());

		Assert.assertTrue(documentJSONObject.getLong("id") > 0);
		Assert.assertEquals(
			bytes.length, documentJSONObject.getInt("sizeInBytes"));
		Assert.assertEquals(fileName, documentJSONObject.getString("title"));

		httpResponse =
			toolResource.postToolSetToolSetNameToolInvokeHttpResponse(
				"mcp-server-v1.0", "getToolSetToolSetNameToolSummariesPage",
				JSONUtil.put(
					"toolSetName", "mcp-server-v1.0"
				).toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			httpResponse.getContent());

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertFalse(
			jsonArray.getJSONObject(
				0
			).has(
				"xClassName"
			));

		httpResponse =
			toolResource.postToolSetToolSetNameToolInvokeHttpResponse(
				"headless-delivery-v1.0", "getSiteDocumentsPage",
				JSONUtil.put(
					"fields", "id"
				).put(
					"siteId", testGroup.getGroupId()
				).toString());

		Assert.assertTrue(
			JSONFactoryUtil.createJSONObject(
				httpResponse.getContent()
			).getJSONArray(
				"items"
			).getJSONObject(
				0
			).has(
				"id"
			));
		Assert.assertFalse(
			JSONFactoryUtil.createJSONObject(
				httpResponse.getContent()
			).getJSONArray(
				"items"
			).getJSONObject(
				0
			).has(
				"title"
			));
	}

}