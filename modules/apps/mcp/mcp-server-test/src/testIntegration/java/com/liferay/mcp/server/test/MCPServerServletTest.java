/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Tardín
 * @author Beni Herrero
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class MCPServerServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_updateMCPServerConfiguration(true);

		BatchEngineTestUtil.processBatchEngineUnits(
			"com.liferay.mcp.server", MCPServerServletTest.class,
			new String[] {
				".com.liferay.mcp.server.internal.batch.01.object.definition",
				".com.liferay.mcp.server.internal.batch.02.object.definition"
			});
	}

	@After
	public void tearDown() throws Exception {
		_updateMCPServerConfiguration(false);
	}

	@Test
	public void testServiceWhenMCPServerConfigurationIsDisabled()
		throws Exception {

		_updateMCPServerConfiguration(false);

		HttpResponse<Void> httpResponse = HttpClient.newHttpClient(
		).send(
			HttpRequest.newBuilder(
			).header(
				"Authorization", _getAuthorization()
			).uri(
				URI.create(
					"http://localhost:" +
						PortalUtil.getPortalServerPort(false) + "/o/mcp")
			).build(),
			HttpResponse.BodyHandlers.discarding()
		);

		Assert.assertEquals(404, httpResponse.statusCode());
	}

	@FeatureFlag("LPD-86164")
	@Test
	public void testServiceWithModifiedProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _addObjectEntry(
			name, "GET /mcp/server-profiles", "POST /mcp/server-profiles");

		McpSyncClient mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 2, tools.size());

		_assertTool(
			tools.get(0), "getMCPServerProfilesPage",
			"get_mcp_server_profiles.json");
		_assertTool(
			tools.get(1), "postMCPServerProfile",
			"post_mcp_server_profiles.json");

		mcpSyncClient.closeGracefully();

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"endpoints", "GET /mcp/server-profiles"
			).put(
				"name", name
			).build(),
			ServiceContextTestUtil.getServiceContext());

		mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		listToolsResult = mcpSyncClient.listTools();

		tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 1, tools.size());

		_assertTool(
			tools.get(0), "getMCPServerProfilesPage",
			"get_mcp_server_profiles.json");

		mcpSyncClient.closeGracefully();
	}

	@Test
	public void testServiceWithoutProfile() throws Exception {
		McpSyncClient mcpSyncClient = _getMcpSyncClient(null);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 3, tools.size());

		McpSchema.Tool tool1 = tools.get(0);

		Assert.assertEquals("call-http-endpoint", tool1.name());

		McpSchema.Tool tool2 = tools.get(1);

		Assert.assertEquals("get-openapi", tool2.name());

		McpSchema.Tool tool3 = tools.get(2);

		Assert.assertEquals("get-openapis", tool3.name());

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"get-openapis", Collections.emptyMap()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent content = (McpSchema.TextContent)contents.get(0);

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"get-openapi",
				HashMapBuilder.<String, Object>put(
					"url",
					() -> {
						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject(content.text());

						JSONArray jsonArray = jsonObject.getJSONArray(
							"/object-admin");

						return jsonArray.getString(0);
					}
				).build()));

		contents = callToolResult.content();

		McpSchema.TextContent newContent = (McpSchema.TextContent)contents.get(
			0);

		Assert.assertThat(
			newContent.text(), CoreMatchers.containsString("/object-admin"));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"call-http-endpoint",
				HashMapBuilder.<String, Object>put(
					"method", "GET"
				).put(
					"path", "/object-admin/v1.0/object-definitions"
				).build()));

		contents = callToolResult.content();

		newContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertNotNull(
			JSONFactoryUtil.createJSONObject(
				newContent.text()
			).getJSONArray(
				"items"
			));

		mcpSyncClient.closeGracefully();
	}

	@Test
	public void testServiceWithoutSession() throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader("Authorization", _getAuthorization());
		options.setLocation(
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/mcp");

		_http.URLtoString(options);

		Http.Response response = options.getResponse();

		Assert.assertEquals("text/event-stream", response.getContentType());
		Assert.assertEquals(200, response.getResponseCode());

		options = new Http.Options();

		options.addHeader("Accept", "application/json, text/event-stream");
		options.addHeader("Authorization", _getAuthorization());
		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setBody(
			JSONUtil.put(
				"id", 1
			).put(
				"jsonrpc", "2.0"
			).put(
				"method", "initialize"
			).put(
				"params",
				JSONUtil.put(
					"capabilities", JSONFactoryUtil.createJSONObject()
				).put(
					"clientInfo",
					JSONUtil.put(
						"name", "test-client"
					).put(
						"version", "1.0.0"
					)
				).put(
					"protocolVersion", "2025-11-25"
				)
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/mcp");
		options.setPost(true);

		_http.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(200, response.getResponseCode());
		Assert.assertNull(response.getHeader("Mcp-Session-Id"));
	}

	@FeatureFlag("LPD-86164")
	@Test
	public void testServiceWithProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		_addObjectEntry(
			name, "GET /mcp/server-profiles", "POST /mcp/server-profiles");

		McpSyncClient mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 2, tools.size());

		_assertTool(
			tools.get(0), "getMCPServerProfilesPage",
			"get_mcp_server_profiles.json");
		_assertTool(
			tools.get(1), "postMCPServerProfile",
			"post_mcp_server_profiles.json");

		String entryName = RandomTestUtil.randomString();

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"postMCPServerProfile",
				HashMapBuilder.<String, Object>put(
					"body",
					HashMapBuilder.<String, Object>put(
						"description", RandomTestUtil.randomString()
					).put(
						"endpoints", RandomTestUtil.randomString()
					).put(
						"name", entryName
					).build()
				).build()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent content = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(content.text(), callToolResult.isError());

		Assert.assertEquals(
			entryName,
			JSONFactoryUtil.createJSONObject(
				content.text()
			).getString(
				"name"
			));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getMCPServerProfilesPage", Collections.emptyMap()));

		contents = callToolResult.content();

		content = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(content.text(), callToolResult.isError());

		Assert.assertTrue(
			JSONUtil.toStringList(
				JSONFactoryUtil.createJSONObject(
					content.text()
				).getJSONArray(
					"items"
				),
				"name"
			).contains(
				entryName
			));

		mcpSyncClient.closeGracefully();
	}

	private ObjectEntry _addObjectEntry(String name, String... endpoints)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_MCP_SERVER_PROFILE", TestPropsValues.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"endpoints", StringUtil.merge(endpoints, "\n")
			).put(
				"name", name
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertTool(
			McpSchema.Tool tool, String expectedName,
			String expectedSchemaFileName)
		throws Exception {

		Assert.assertEquals(expectedName, tool.name());

		JSONAssert.assertEquals(
			StringUtil.read(
				MCPServerServletTest.class.getResourceAsStream(
					"dependencies/" + expectedSchemaFileName)),
			new ObjectMapper(
			).writeValueAsString(
				tool.inputSchema()
			),
			false);
	}

	private String _getAuthorization() {
		try {
			Base64.Encoder encoder = Base64.getEncoder();

			String userNameAndPassword =
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

			return "Basic " +
				new String(
					encoder.encode(userNameAndPassword.getBytes("UTF-8")),
					"UTF-8");
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private McpSyncClient _getMcpSyncClient(String profileName) {
		return McpClient.sync(
			HttpClientStreamableHttpTransport.builder(
				"http://localhost:" + PortalUtil.getPortalServerPort(false) +
					"/o/"
			).customizeRequest(
				builder -> builder.header("Authorization", _getAuthorization())
			).endpoint(
				(profileName != null) ? "mcp/" + profileName : "mcp"
			).build()
		).capabilities(
			McpSchema.ClientCapabilities.builder(
			).elicitation(
				true, true
			).build()
		).build();
	}

	private void _updateMCPServerConfiguration(boolean enabled)
		throws Exception {

		ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.mcp.server.internal.configuration." +
				"MCPServerConfiguration.scoped",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"enabled", enabled
			).build());
	}

	@Inject
	private Http _http;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}