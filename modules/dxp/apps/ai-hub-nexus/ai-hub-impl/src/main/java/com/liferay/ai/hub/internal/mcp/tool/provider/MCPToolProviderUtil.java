/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.mcp.tool.provider;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.request.json.JsonAnyOfSchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchemaElement;

import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author João Victor Alves
 */
public class MCPToolProviderUtil {

	public static McpToolProvider create(
		long companyId, DTOConverterRegistry dtoConverterRegistry, long groupId,
		Locale locale, List<String> mcpServerExternalReferenceCodes,
		ObjectEntryManager objectEntryManager, long userId) {

		if (ListUtil.isEmpty(mcpServerExternalReferenceCodes)) {
			return null;
		}

		return McpToolProvider.builder(
		).mcpClients(
			TransformUtil.transform(
				_getMCPServerObjectEntries(
					companyId, dtoConverterRegistry, groupId, locale,
					mcpServerExternalReferenceCodes, objectEntryManager,
					userId),
				objectEntry -> {
					McpTransport mcpTransport = _createMcpTransport(
						objectEntry.getProperties());

					return new DefaultMcpClient.Builder(
					).transport(
						mcpTransport
					).build();
				})
		).filter(
			(mcpClient, toolSpecification) -> _filterToolSpecifications(
				toolSpecification)
		).build();
	}

	private static Map<String, String> _createCustomHeaders(
		String authArguments) {

		if (authArguments.isBlank()) {
			return Map.of();
		}

		return Map.of("Authorization", _parseBasicAuthorization(authArguments));
	}

	private static McpTransport _createMcpTransport(
		Map<String, Object> properties) {

		String url = GetterUtil.getString(properties.get("url"));

		Map<String, String> customHeaders = _createCustomHeaders(
			GetterUtil.getString(properties.get("authArguments")));

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

	private static boolean _filterToolSpecifications(
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

	private static List<ObjectEntry> _getMCPServerObjectEntries(
		long companyId, DTOConverterRegistry dtoConverterRegistry, long groupId,
		Locale locale, List<String> mcpServerExternalReferenceCodes,
		ObjectEntryManager objectEntryManager, long userId) {

		try {
			String groupKey = GroupConstants.GUEST;

			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if (group != null) {
				groupKey = group.getGroupKey();
			}

			Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
				companyId,
				ObjectDefinitionLocalServiceUtil.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_MCP_SERVER", companyId),
				groupKey, null,
				new DefaultDTOConverterContext(
					false, Map.of(), dtoConverterRegistry, null, locale, null,
					UserServiceUtil.getUserById(userId)),
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

	private static String _parseBasicAuthorization(String authArguments) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				authArguments);

			Base64.Encoder encoder = Base64.getEncoder();

			String credentials =
				jsonObject.getString("userName") + ":" +
					jsonObject.getString("password");

			return "Basic " +
				new String(
					encoder.encode(credentials.getBytes("UTF-8")), "UTF-8");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}