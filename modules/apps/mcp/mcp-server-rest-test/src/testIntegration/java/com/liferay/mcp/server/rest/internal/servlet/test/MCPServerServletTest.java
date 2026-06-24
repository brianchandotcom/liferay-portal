/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.servlet.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.test.util.BatchEngineTestUtil;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
			"com.liferay.mcp.server.rest.impl", MCPServerServletTest.class,
			new String[] {
				".com.liferay.mcp.server.rest.internal.batch.01.object." +
					"definition",
				".com.liferay.mcp.server.rest.internal.batch.02.object." +
					"definition",
				".com.liferay.mcp.server.rest.internal.batch.03.object.entry"
			});

		_accessTokenContent = RandomTestUtil.randomString();

		_addOAuth2Authorization(
			_accessTokenContent,
			new Date(System.currentTimeMillis() + Time.HOUR),
			Collections.singletonList(_getMCPURL()));
	}

	@After
	public void tearDown() throws Exception {
		for (OAuth2Authorization oAuth2Authorization : _oAuth2Authorizations) {
			_oAuth2AuthorizationLocalService.deleteOAuth2Authorization(
				oAuth2Authorization);
		}

		_oAuth2Authorizations.clear();

		_updateMCPServerConfiguration(false);
	}

	@Test
	public void testServiceWhenAccessTokenAudienceIsNotBound()
		throws Exception {

		String accessTokenContent = RandomTestUtil.randomString();

		_addOAuth2Authorization(
			accessTokenContent,
			new Date(System.currentTimeMillis() + Time.HOUR),
			Collections.singletonList("http://localhost/o/other-resource"));

		Http.Response response = _getResponse("Bearer " + accessTokenContent);

		Assert.assertEquals(401, response.getResponseCode());

		String wwwAuthenticate = response.getHeader(
			HttpHeaders.WWW_AUTHENTICATE);

		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains("error=\"invalid_token\""));
		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains(
				"error_description=\"Access token is not bound to this MCP " +
					"server\""));
	}

	@Test
	public void testServiceWhenAccessTokenIsExpired() throws Exception {
		String accessTokenContent = RandomTestUtil.randomString();

		_addOAuth2Authorization(
			accessTokenContent,
			new Date(System.currentTimeMillis() - Time.HOUR),
			Collections.singletonList(_getMCPURL()));

		Http.Response response = _getResponse("Bearer " + accessTokenContent);

		Assert.assertEquals(401, response.getResponseCode());

		String wwwAuthenticate = response.getHeader(
			HttpHeaders.WWW_AUTHENTICATE);

		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains("error=\"invalid_token\""));
		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains(
				"error_description=\"Access token has expired\""));
	}

	@Test
	public void testServiceWhenAccessTokenIsUnknown() throws Exception {
		Http.Response response = _getResponse(
			"Bearer " + RandomTestUtil.randomString());

		Assert.assertEquals(401, response.getResponseCode());

		String wwwAuthenticate = response.getHeader(
			HttpHeaders.WWW_AUTHENTICATE);

		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains("error=\"invalid_token\""));
		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains(
				"error_description=\"Access token is unknown or revoked\""));
	}

	@Test
	public void testServiceWhenAuthorizationHeaderIsMissing() throws Exception {
		Http.Response response = _getResponse(null);

		Assert.assertEquals(401, response.getResponseCode());

		String wwwAuthenticate = response.getHeader(
			HttpHeaders.WWW_AUTHENTICATE);

		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.startsWith("Bearer realm=\"mcp\""));
		Assert.assertTrue(
			wwwAuthenticate, wwwAuthenticate.contains("resource_metadata="));
		Assert.assertFalse(wwwAuthenticate, wwwAuthenticate.contains("error="));
	}

	@Test
	public void testServiceWhenAuthorizationHeaderIsNotBearer()
		throws Exception {

		Http.Response response = _getResponse("Basic dGVzdDp0ZXN0");

		Assert.assertEquals(401, response.getResponseCode());

		String wwwAuthenticate = response.getHeader(
			HttpHeaders.WWW_AUTHENTICATE);

		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains("error=\"invalid_token\""));
		Assert.assertTrue(
			wwwAuthenticate,
			wwwAuthenticate.contains(
				"error_description=\"Authorization header is not a Bearer " +
					"token\""));
	}

	@Test
	public void testServiceWhenMCPServerConfigurationIsDisabled()
		throws Exception {

		_updateMCPServerConfiguration(false);

		Assert.assertEquals(
			404, HTTPTestUtil.invokeToHttpCode(null, "mcp", Http.Method.GET));
	}

	@Test
	public void testServiceWithModifiedProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _addObjectEntry(
			name, "mcp-server-profiles getMCPServerProfilesPage",
			"mcp-server-profiles postMCPServerProfile");

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
				"name", name
			).put(
				"tools", "mcp-server-profiles getMCPServerProfilesPage"
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
	public void testServiceWithNoContentResponse() throws Exception {
		McpSyncClient mcpSyncClient = _getMcpSyncClient(null);

		mcpSyncClient.initialize();

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"postToolSetToolSetNameToolInvoke",
				HashMapBuilder.<String, Object>put(
					"body",
					HashMapBuilder.<String, Object>put(
						"mCPServerProfileId",
						String.valueOf(
							_addObjectEntry(
								RandomTestUtil.randomString(),
								"mcp-server-profiles getMCPServerProfilesPage"
							).getObjectEntryId())
					).build()
				).put(
					"toolName", "deleteMCPServerProfile"
				).put(
					"toolSetName", "mcp-server-profiles"
				).build()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent textContent = (McpSchema.TextContent)contents.get(
			0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());
		Assert.assertEquals("Status code: 204", textContent.text());

		mcpSyncClient.closeGracefully();
	}

	@Test
	public void testServiceWithoutProfile() throws Exception {
		McpSyncClient mcpSyncClient = _getMcpSyncClient(null);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 4, tools.size());

		_assertTool(tools.get(0), "getToolSetsPage", "get_tool_sets.json");
		_assertTool(
			tools.get(1), "getToolSetToolSetNameToolSummariesPage",
			"get_tool_summaries.json");
		_assertTool(tools.get(2), "getToolSetToolSetNameTool", "get_tool.json");
		_assertTool(
			tools.get(3), "postToolSetToolSetNameToolInvoke",
			"invoke_tool.json");

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getToolSetsPage", Collections.emptyMap()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent textContent = (McpSchema.TextContent)contents.get(
			0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		Assert.assertTrue(
			textContent.text(),
			JSONUtil.toStringList(
				JSONFactoryUtil.createJSONObject(
					textContent.text()
				).getJSONArray(
					"items"
				),
				"name"
			).contains(
				"mcp-server-profiles"
			));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getToolSetToolSetNameToolSummariesPage",
				HashMapBuilder.<String, Object>put(
					"toolSetName", "mcp-server-profiles"
				).build()));

		contents = callToolResult.content();

		textContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		Assert.assertTrue(
			textContent.text(),
			JSONUtil.toStringList(
				JSONFactoryUtil.createJSONObject(
					textContent.text()
				).getJSONArray(
					"items"
				),
				"name"
			).contains(
				"getMCPServerProfilesPage"
			));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getToolSetToolSetNameTool",
				HashMapBuilder.<String, Object>put(
					"toolName", "getMCPServerProfilesPage"
				).put(
					"toolSetName", "mcp-server-profiles"
				).build()));

		contents = callToolResult.content();

		textContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		JSONObject toolJSONObject = JSONFactoryUtil.createJSONObject(
			textContent.text());

		Assert.assertEquals(
			"getMCPServerProfilesPage", toolJSONObject.getString("name"));
		Assert.assertNotNull(toolJSONObject.getJSONObject("inputSchema"));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"postToolSetToolSetNameToolInvoke",
				HashMapBuilder.<String, Object>put(
					"body", Collections.<String, Object>emptyMap()
				).put(
					"toolName", "getMCPServerProfilesPage"
				).put(
					"toolSetName", "mcp-server-profiles"
				).build()));

		contents = callToolResult.content();

		textContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		Assert.assertNotNull(
			JSONFactoryUtil.createJSONObject(
				textContent.text()
			).getJSONArray(
				"items"
			));

		mcpSyncClient.closeGracefully();
	}

	@Test
	public void testServiceWithoutSession() throws Exception {
		String url =
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/mcp";

		Http.Options options = new Http.Options();

		options.addHeader("Authorization", _getAuthorization());
		options.setLocation(url);

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
		options.setLocation(url);
		options.setPost(true);

		_http.URLtoString(options);

		response = options.getResponse();

		Assert.assertNull(response.getHeader("Mcp-Session-Id"));
		Assert.assertEquals(200, response.getResponseCode());
	}

	@Test
	public void testServiceWithProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		_addObjectEntry(
			name, "mcp-server-profiles getMCPServerProfilesPage",
			"mcp-server-profiles postMCPServerProfile");

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
						"name", entryName
					).put(
						"tools", "mcp-server-profiles getMCPServerProfilesPage"
					).build()
				).build()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent textContent = (McpSchema.TextContent)contents.get(
			0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		Assert.assertEquals(
			entryName,
			JSONFactoryUtil.createJSONObject(
				textContent.text()
			).getString(
				"name"
			));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getMCPServerProfilesPage", Collections.emptyMap()));

		contents = callToolResult.content();

		textContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(textContent.text(), callToolResult.isError());

		Assert.assertTrue(
			JSONUtil.toStringList(
				JSONFactoryUtil.createJSONObject(
					textContent.text()
				).getJSONArray(
					"items"
				),
				"name"
			).contains(
				entryName
			));

		mcpSyncClient.closeGracefully();
	}

	private OAuth2Authorization _addOAuth2Authorization(
			String accessTokenContent, Date accessTokenExpirationDate,
			List<String> audiencesList)
		throws Exception {

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.addOAuth2Authorization(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				"test@liferay.com", 0, 0, accessTokenContent, new Date(),
				accessTokenExpirationDate, audiencesList, "localhost",
				"127.0.0.1", null, null, null);

		_oAuth2Authorizations.add(oAuth2Authorization);

		return oAuth2Authorization;
	}

	private ObjectEntry _addObjectEntry(String name, String... tools)
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
				"name", name
			).put(
				"tools", StringUtil.merge(tools, "\n")
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
		return "Bearer " + _accessTokenContent;
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

	private String _getMCPURL() {
		return "http://localhost:" + PortalUtil.getPortalServerPort(false) +
			"/o/mcp";
	}

	private Http.Response _getResponse(String authorization) throws Exception {
		Http.Options options = new Http.Options();

		if (authorization != null) {
			options.addHeader("Authorization", authorization);
		}

		options.setLocation(_getMCPURL());

		_http.URLtoString(options);

		return options.getResponse();
	}

	private void _updateMCPServerConfiguration(boolean enabled)
		throws Exception {

		ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.mcp.server.rest.internal.configuration." +
				"MCPServerConfiguration.scoped",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"enabled", enabled
			).build());
	}

	private String _accessTokenContent;

	@Inject
	private Http _http;

	@Inject
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	private final List<OAuth2Authorization> _oAuth2Authorizations =
		new ArrayList<>();

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}