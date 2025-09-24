/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.mcp.server.internal.io.modelcontextprotocol.server.transport.AuthorizedHttpServletSseServerTransportProvider;
import com.liferay.mcp.server.internal.util.MCPServerHttpUtil;
import com.liferay.mcp.server.internal.util.MCPServerToolCallHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
public class MCPServerServlet extends HttpServlet {

	@Override
	public void destroy() {
		for (Map.Entry<Long, Servlet> entry : _servlets.entrySet()) {
			Servlet servlet = entry.getValue();

			servlet.destroy();
		}

		_servlets.clear();
	}

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-63311")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		Servlet servlet = _servlets.computeIfAbsent(
			companyId,
			__ -> {
				String baseURL =
					_portal.getPortalURL(httpServletRequest) +
						_portal.getPathModule();

				AuthorizedHttpServletSseServerTransportProvider
					authorizedHttpServletSseServerTransportProvider =
						new AuthorizedHttpServletSseServerTransportProvider(
							baseURL + "/mcp");

				McpSyncServer mcpSyncServer = _buildMcpSyncServer(
					baseURL, companyId,
					authorizedHttpServletSseServerTransportProvider);

				return new GenericServlet() {

					@Override
					public void destroy() {
						mcpSyncServer.closeGracefully();
					}

					@Override
					public void service(
							ServletRequest servletRequest,
							ServletResponse servletResponse)
						throws IOException, ServletException {

						authorizedHttpServletSseServerTransportProvider.service(
							servletRequest, servletResponse);
					}

				};
			});

		servlet.service(httpServletRequest, httpServletResponse);
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	private McpSyncServer _buildMcpSyncServer(
		String baseURL, long companyId,
		AuthorizedHttpServletSseServerTransportProvider
			authorizedHttpServletSseServerTransportProvider) {

		return McpServer.sync(
			authorizedHttpServletSseServerTransportProvider
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).tools(
				true
			).prompts(
				true
			).build()
		).tool(
			new McpSchema.Tool(
				"get-openapis",
				StringBundler.concat(
					"Retrieves the current available Liferay OpenAPIs. Use it ",
					"to discover the most suitable API that could fulfill the ",
					"user request. The flow should be: get-openapis, ",
					"get-openapi, call-http-endpoint. If you are not sure ",
					"what the user wants, just call this to get an idea of ",
					"what is possible."),
				JSONUtil.put(
					"properties", _jsonFactory.createJSONObject()
				).put(
					"type", "object"
				).toString()),
			MCPServerToolCallHandler.of(
				(exchange, arguments) -> MCPServerHttpUtil.callEndpoint(
					"GET", baseURL + "/openapi", null,
					authorizedHttpServletSseServerTransportProvider.
						getAuthorizationHeader(exchange)))
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
				(exchange, arguments) -> MCPServerHttpUtil.callEndpoint(
					"GET", String.valueOf(arguments.get("url")), null,
					authorizedHttpServletSseServerTransportProvider.
						getAuthorizationHeader(exchange)))
		).tool(
			new McpSchema.Tool(
				"call-http-endpoint",
				"Calls an HTTP endpoint from a Liferay OpenAPI. Never call a " +
					"batch endpoint, always use individual operations.",
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
							StringBundler.concat(
								"The full endpoint path starting with / ",
								"relative to ", baseURL,
								". Do not include the leading /o")
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
				(exchange, arguments) -> {
					String path = String.valueOf(arguments.get("path"));

					if (!path.startsWith("/")) {
						path = "/" + path;
					}

					return MCPServerHttpUtil.callEndpoint(
						String.valueOf(arguments.get("method")), baseURL + path,
						String.valueOf(arguments.get("payload")),
						authorizedHttpServletSseServerTransportProvider.
							getAuthorizationHeader(exchange));
				})
		).prompts(
			_getSyncPromptSpecifications(companyId)
		).build();
	}

	private List<McpServerFeatures.SyncPromptSpecification>
		_getSyncPromptSpecifications(long companyId) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					MCPServerConstants.MCP_SERVER_PROMPT_OBJECT_DEFINITION_ERC,
					companyId);

		if (objectDefinition == null) {
			return new ArrayList<>();
		}

		return TransformUtil.transform(
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			objectEntry -> {
				Map<String, Serializable> objectEntryValues =
					objectEntry.getValues();

				return new McpServerFeatures.SyncPromptSpecification(
					new McpSchema.Prompt(
						(String)objectEntryValues.get("name"),
						(String)objectEntryValues.get("description"),
						Collections.emptyList()),
					(exchange, request) -> new McpSchema.GetPromptResult(
						(String)objectEntryValues.get("description"),
						List.of(
							new McpSchema.PromptMessage(
								McpSchema.Role.USER,
								new McpSchema.TextContent(
									(String)objectEntryValues.get(
										"prompt"))))));
			});
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	private final Map<Long, Servlet> _servlets = new ConcurrentHashMap<>();

}