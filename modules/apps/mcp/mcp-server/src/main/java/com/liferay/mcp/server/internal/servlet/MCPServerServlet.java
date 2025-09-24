/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.mcp.server.internal.io.modelcontextprotocol.server.transport.AuthorizedHttpServletSseServerTransportProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
		throws ServletException {

		try {
			Servlet servlet = _getServlet(httpServletRequest);

			if (servlet == null) {
				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			else {
				servlet.service(httpServletRequest, httpServletResponse);
			}
		}
		catch (Exception exception) {
			throw new ServletException(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	private McpSyncServer _buildMcpSyncServer(
			String baseURL, long companyId,
			AuthorizedHttpServletSseServerTransportProvider
				authorizedHttpServletSseServerTransportProvider)
		throws Exception {

		JSONObject toolsJSONObject = _jsonFactory.createJSONObject(
			StringUtil.replace(
				StringUtil.read(MCPServerServlet.class, "/tools.json"),
				"$BASE_URL", baseURL));

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
			_getTool("get-openapis", toolsJSONObject),
			(exchange, arguments) -> _callEndpoint(
				authorizedHttpServletSseServerTransportProvider.
					getAuthorizationHeader(exchange),
				"GET", baseURL + "/openapi", null)
		).tool(
			_getTool("get-openapi", toolsJSONObject),
			(exchange, arguments) -> _callEndpoint(
				authorizedHttpServletSseServerTransportProvider.
					getAuthorizationHeader(exchange),
				"GET", String.valueOf(arguments.get("url")), null)
		).tool(
			_getTool("call-http-endpoint", toolsJSONObject),
			(exchange, arguments) -> {
				String path = String.valueOf(arguments.get("path"));

				if (!path.startsWith("/")) {
					path = "/" + path;
				}

				return _callEndpoint(
					authorizedHttpServletSseServerTransportProvider.
						getAuthorizationHeader(exchange),
					String.valueOf(arguments.get("method")), baseURL + path,
					String.valueOf(arguments.get("payload")));
			}
		).prompts(
			_getSyncPromptSpecifications(companyId)
		).build();
	}

	private McpSchema.CallToolResult _callEndpoint(
		String authorizationHeader, String method, String path,
		String payload) {

		Http.Options options = new Http.Options();

		if (Validator.isNotNull(payload)) {
			options.setBody(
				payload, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		}

		options.setHeaders(
			HashMapBuilder.put(
				"Authorization", () -> authorizationHeader
			).build());

		options.setLocation(path);
		options.setMethod(Http.Method.valueOf(StringUtil.toUpperCase(method)));

		try {
			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if (responseCode < 300) {
				return new McpSchema.CallToolResult(content, false);
			}

			return new McpSchema.CallToolResult(
				StringBundler.concat(
					"Status code: ", responseCode, ", Content:\n", content),
				true);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			return new McpSchema.CallToolResult(ioException.getMessage(), true);
		}
	}

	private Servlet _getServlet(HttpServletRequest httpServletRequest)
		throws Exception {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-63311")) {
			return null;
		}

		Servlet servlet = _servlets.get(companyId);

		if (servlet != null) {
			return servlet;
		}

		String baseURL =
			_portal.getPortalURL(httpServletRequest) + _portal.getPathModule();

		AuthorizedHttpServletSseServerTransportProvider
			authorizedHttpServletSseServerTransportProvider =
				new AuthorizedHttpServletSseServerTransportProvider(
					baseURL + "/mcp");

		McpSyncServer mcpSyncServer = _buildMcpSyncServer(
			baseURL, companyId,
			authorizedHttpServletSseServerTransportProvider);

		servlet = new GenericServlet() {

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

		_servlets.put(companyId, servlet);

		return servlet;
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

	private McpSchema.Tool _getTool(String name, JSONObject toolsJSONObject) {
		JSONObject toolJSONObject = toolsJSONObject.getJSONObject(name);

		return new McpSchema.Tool(
			name, toolJSONObject.getString("description"),
			toolJSONObject.getJSONObject(
				"schema"
			).toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MCPServerServlet.class);

	@Reference
	private Http _http;

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