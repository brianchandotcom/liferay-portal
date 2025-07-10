/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.liferay.mcp.server.internal.tenant.MCPServerTenant;
import com.liferay.mcp.server.internal.util.MCPServerToolCallHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leandro Aguiar
 * @author Vendel Toreki
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/mcp",
		"osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.internal.servlet.MCPServerServlet",
		"osgi.http.whiteboard.servlet.pattern=/mcp/*"
	},
	service = Servlet.class
)
public class MCPServerServlet extends GenericServlet {

	public void reset() {
		_mcpServerTenants.clear();
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		// TODO Validate token using oauth2 infra

		if ((servletRequest instanceof HttpServletRequest httpServletRequest) &&
			Validator.isNull(httpServletRequest.getHeader("Authorization")) &&
			(servletResponse instanceof
				HttpServletResponse httpServletResponse)) {

			httpServletResponse.setHeader(
				"WWW-Authenticate", "Bearer error=\"invalid_request\"");
			httpServletResponse.setStatus(401);

			return;
		}

		MCPServerTenant mcpServerTenant = _mcpServerTenants.computeIfAbsent(
			_portal.getCompanyId((HttpServletRequest)servletRequest),
			companyId -> _buildMCPServerTenant(
				_portal.getPortalURL((HttpServletRequest)servletRequest) +
					_portal.getPathModule(),
				companyId));

		Servlet servlet = mcpServerTenant.getServlet();

		servlet.service(servletRequest, servletResponse);
	}

	private MCPServerTenant _buildMCPServerTenant(
		String baseURL, long companyId) {

		MCPServerTenant mcpServerTenant = new MCPServerTenant(baseURL);

		List<McpServerFeatures.SyncPromptSpecification> prompts =
			_getSyncPromptSpecifications(companyId);

		McpServer.sync(
			mcpServerTenant.getServlet()
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).tools(
				true
			).prompts(
				!prompts.isEmpty()
			).build()
		).tool(
			new McpSchema.Tool(
				"get-openapis",
				"Retrieves the current available Liferay OpenAPIs. Use it " +
					"before interacting with Liferay upon user request to " +
						"decide which API would be the best fit.",
				JSONUtil.put(
					"properties", _jsonFactory.createJSONObject()
				).put(
					"type", "object"
				).toString()),
			MCPServerToolCallHandler.of(
				(exchange, arguments) -> mcpServerTenant.getOpenAPIs(
					MCPServerTenant.getAccessToken(exchange)))
		).tool(
			new McpSchema.Tool(
				"get-openapi", "Retrieves the OpenAPI YAML file.",
				JSONUtil.put(
					"properties",
					JSONUtil.put(
						"url",
						JSONUtil.put(
							"description", "The OpenAPI YAML URL"
						).put(
							"type", "string"
						))
				).put(
					"type", "object"
				).toString()),
			MCPServerToolCallHandler.of(
				(exchange, arguments) -> mcpServerTenant.getOpenAPI(
					String.valueOf(arguments.get("url")),
					MCPServerTenant.getAccessToken(exchange)))
		).tool(
			new McpSchema.Tool(
				"call-http-endpoint",
				"Calls an HTTP endpoint with method, path, and payload. It " +
					"must always be performed after a having retrieved a " +
						"valid Liferay OpenAPI through the get-openapi tool.",
				JSONUtil.put(
					"additionalProperties", false
				).put(
					"properties",
					JSONUtil.put(
						"method",
						JSONUtil.put(
							"description", "The HTTP method"
						).put(
							"type", "string"
						)
					).put(
						"path",
						JSONUtil.put(
							"description",
							"The full endpoint path starting with / relative " +
								"to " + baseURL
						).put(
							"type", "string"
						)
					).put(
						"payload",
						JSONUtil.put(
							"description",
							"The endpoint payload. Can be an empty string if " +
								"there is no payload."
						).put(
							"type", "string"
						)
					)
				).put(
					"required", JSONUtil.putAll("method", "path", "payload")
				).put(
					"type", "object"
				).toString()),
			MCPServerToolCallHandler.of(
				(exchange, arguments) -> mcpServerTenant.callEndpoint(
					String.valueOf(arguments.get("method")),
					String.valueOf(arguments.get("path")),
					String.valueOf(arguments.get("payload")),
					MCPServerTenant.getAccessToken(exchange)))
		).prompts(
			prompts
		).build();

		return mcpServerTenant;
	}

	private List<McpServerFeatures.SyncPromptSpecification>
		_getSyncPromptSpecifications(long companyId) {

		List<McpServerFeatures.SyncPromptSpecification>
			syncPromptSpecifications = new ArrayList<>();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"MCP_PROMPT", companyId);

		if (objectDefinition == null) {
			return syncPromptSpecifications;
		}

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Serializable> objectEntryValues =
				objectEntry.getValues();

			String argumentName = (String)objectEntryValues.get("argumentName");
			String resultText = (String)objectEntryValues.get("resultText");

			McpServerFeatures.SyncPromptSpecification syncPromptSpecification =
				new McpServerFeatures.SyncPromptSpecification(
					new McpSchema.Prompt(
						(String)objectEntryValues.get("name"),
						(String)objectEntryValues.get("description"),
						Arrays.asList(
							new McpSchema.PromptArgument(
								argumentName,
								(String)objectEntryValues.get(
									"argumentDescription"),
								true))),
					(exchange, request) -> {
						String argumentValue = (String)request.arguments(
						).get(
							argumentName
						);

						String content = StringUtil.replace(
							resultText, "${" + argumentName + "}",
							argumentValue);

						return new McpSchema.GetPromptResult(
							(String)objectEntryValues.get("resultDescription"),
							Arrays.asList(
								new McpSchema.PromptMessage(
									McpSchema.Role.USER,
									new McpSchema.TextContent(content))));
					});

			syncPromptSpecifications.add(syncPromptSpecification);
		}

		return syncPromptSpecifications;
	}

	@Reference
	private JSONFactory _jsonFactory;

	private final Map<Long, MCPServerTenant> _mcpServerTenants =
		new ConcurrentHashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

}