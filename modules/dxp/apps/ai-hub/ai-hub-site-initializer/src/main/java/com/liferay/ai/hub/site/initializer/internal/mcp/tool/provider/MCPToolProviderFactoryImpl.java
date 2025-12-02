/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.mcp.tool.provider;

import com.liferay.ai.hub.site.initializer.mcp.tool.provider.MCPToolProviderFactory;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.request.json.JsonAnyOfSchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchemaElement;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = MCPToolProviderFactory.class)
public class MCPToolProviderFactoryImpl implements MCPToolProviderFactory {

	public McpToolProvider create(
		long companyId, long groupId, Locale locale,
		List<String> mcpServerExternalReferenceCodes, long userId) {

		List<ObjectEntry> objectEntries = _getMCPServerObjectEntries(
			companyId, groupId, locale, mcpServerExternalReferenceCodes,
			userId);

		List<McpClient> mcpClients = new ArrayList<>();

		for (ObjectEntry objectEntry : objectEntries) {
			McpTransport mcpTransport = _createMcpTransport(
				objectEntry.getProperties());

			mcpClients.add(
				new DefaultMcpClient.Builder(
				).transport(
					mcpTransport
				).build());
		}

		return McpToolProvider.builder(
		).mcpClients(
			mcpClients
		).filter(
			(mcpClient, toolSpecification) -> _filterToolSpecifications(
				toolSpecification)
		).build();
	}

	private Map<String, String> _createCustomHeaders(String authArguments) {
		if (authArguments.isBlank()) {
			return Map.of();
		}

		return Map.of("Authorization", _parseBasicAuthorization(authArguments));
	}

	private McpTransport _createMcpTransport(Map<String, Object> properties) {
		String url = GetterUtil.getString(properties.get("url"));

		Map<String, String> customHeaders = _createCustomHeaders(
			GetterUtil.getString(properties.get("authenticationArguments")));

		if (url.endsWith("/sse")) {
			return new HttpMcpTransport.Builder(
			).customHeaders(
				customHeaders
			).sseUrl(
				url
			).build();
		}

		return new StreamableHttpMcpTransport.Builder(
		).customHeaders(
			customHeaders
		).url(
			url
		).build();
	}

	private boolean _filterToolSpecifications(
		ToolSpecification toolSpecification) {

		JsonObjectSchema jsonObjectSchema = toolSpecification.parameters();

		Map<String, JsonSchemaElement> properties =
			jsonObjectSchema.properties();

		for (JsonSchemaElement jsonSchemaElement : properties.values()) {
			if (jsonSchemaElement instanceof JsonAnyOfSchema) {
				return false;
			}
		}

		return true;
	}

	private List<ObjectEntry> _getMCPServerObjectEntries(
		long companyId, long groupId, Locale locale,
		List<String> mcpServerExternalReferenceCodes, long userId) {

		Group group = _groupLocalService.fetchGroup(groupId);

		try {
			Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
				companyId,
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_MCP_SERVER", companyId),
				group.getGroupKey(), null,
				new DefaultDTOConverterContext(
					false, Map.of(), _dtoConverterRegistry, null, locale, null,
					_userService.getUserById(userId)),
				StringBundler.concat(
					"externalReferenceCode in (",
					StringUtil.merge(
						TransformUtil.transform(
							mcpServerExternalReferenceCodes, StringUtil::quote),
						StringPool.COMMA_AND_SPACE),
					")"),
				null, null, null);

			return (List<ObjectEntry>)page.getItems();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _parseBasicAuthorization(String authArguments) {
		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				authArguments);

			Base64.Encoder encoder = Base64.getEncoder();

			String credentials =
				jsonObject.getString("userName") +
					jsonObject.getString("password");

			return "Basic " +
				new String(
					encoder.encode(credentials.getBytes("UTF-8")), "UTF-8");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserService _userService;

}