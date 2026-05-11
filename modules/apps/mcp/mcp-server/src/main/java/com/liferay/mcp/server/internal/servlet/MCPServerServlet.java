/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mcp.server.internal.configuration.MCPServerConfiguration;
import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpStatelessServerFeatures;
import io.modelcontextprotocol.server.McpStatelessSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStatelessServerTransport;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
		"osgi.http.whiteboard.servlet.pattern=/mcp",
		"osgi.http.whiteboard.servlet.pattern=/mcp/*"
	},
	service = Servlet.class
)
public class MCPServerServlet extends HttpServlet {

	@Override
	public void destroy() {
		synchronized (this) {
			for (Servlet servlet : _servlets.values()) {
				servlet.destroy();
			}

			_servlets.clear();
		}
	}

	public void invalidate(long companyId, String mcpServerProfileName) {
		synchronized (this) {
			_destroy(_getServletKey(companyId, mcpServerProfileName));
		}
	}

	public void invalidateAll(long companyId) {
		synchronized (this) {
			String companyIdString = String.valueOf(companyId);

			for (String servletKey : new ArrayList<>(_servlets.keySet())) {
				if (servletKey.equals(companyIdString) ||
					servletKey.startsWith(
						companyIdString + StringPool.UNDERLINE)) {

					_destroy(servletKey);
				}
			}
		}
	}

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!_isEnabled(companyId)) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		ObjectEntry mcpServerProfileObjectEntry =
			_getMCPServerProfileObjectEntry(companyId, httpServletRequest);

		if (mcpServerProfileObjectEntry == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		Servlet servlet = _getServlet(
			_portal.getPortalURL(httpServletRequest) + _portal.getPathModule(),
			companyId,
			_getHeaders(
				httpServletRequest.getHeader("Authorization"),
				httpServletRequest.getHeader(
					"Liferay-AI-Hub-Cell-On-Behalf-Of")),
			mcpServerProfileObjectEntry);

		servlet.service(httpServletRequest, httpServletResponse);
	}

	private Servlet _buildServlet(
		String baseURL, long companyId, Map<String, String> headers,
		ObjectEntry mcpServerProfileObjectEntry) {

		Map<String, Serializable> values =
			mcpServerProfileObjectEntry.getValues();

		String mcpServerProfileName = (String)values.get("name");

		HttpServletStatelessServerTransport
			httpServletStatelessServerTransport =
				HttpServletStatelessServerTransport.builder(
				).contextExtractor(
					request -> McpTransportContext.create(
						HashMapBuilder.<String, Object>put(
							"authorization", request.getHeader("Authorization")
						).put(
							"liferayAIHubCellOnBehalfOf",
							request.getHeader(
								"Liferay-AI-Hub-Cell-On-Behalf-Of")
						).build())
				).messageEndpoint(
					(!Objects.equals(mcpServerProfileName, "default")) ?
						"/mcp/" + mcpServerProfileName : "/mcp"
				).build();

		List<McpStatelessServerFeatures.SyncToolSpecification>
			syncToolSpecifications = TransformUtil.transformToList(
				StringUtil.splitLines((String)values.get("tools")),
				tool -> {
					String[] tokens = StringUtil.split(tool, CharPool.SPACE);

					if (tokens.length != 2) {
						throw new IllegalArgumentException(
							"Profile tool must be in \"<toolSetName> " +
								"<toolName>\" format: " + tool);
					}

					String toolName = tokens[1];

					String toolURL = StringBundler.concat(
						baseURL, "/mcp-server/v1.0/tool-sets/", tokens[0],
						"/tools/", toolName);

					return new McpStatelessServerFeatures.SyncToolSpecification(
						_getTool(headers, toolName, toolURL),
						(mcpTransportContext, callToolRequest) -> _call(
							_jsonFactory.createJSONObject(
								callToolRequest.arguments()
							).toString(),
							ContentTypes.APPLICATION_JSON, toolURL + "/invoke",
							mcpTransportContext, "POST"));
				});

		McpStatelessSyncServer mcpStatelessSyncServer = McpServer.sync(
			httpServletStatelessServerTransport
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).prompts(
				true
			).tools(
				true
			).build()
		).prompts(
			_getSyncPromptSpecifications(companyId)
		).tools(
			syncToolSpecifications
		).build();

		return new GenericServlet() {

			@Override
			public void destroy() {
				mcpStatelessSyncServer.closeGracefully();
			}

			@Override
			public void service(
					ServletRequest servletRequest,
					ServletResponse servletResponse)
				throws IOException, ServletException {

				HttpServletRequest httpServletRequest =
					(HttpServletRequest)servletRequest;

				if (Objects.equals(httpServletRequest.getMethod(), "GET")) {
					HttpServletResponse httpServletResponse =
						(HttpServletResponse)servletResponse;

					httpServletResponse.setContentType("text/event-stream");
					httpServletResponse.setHeader("Cache-Control", "no-cache");
					httpServletResponse.setStatus(HttpServletResponse.SC_OK);

					httpServletResponse.flushBuffer();

					return;
				}

				httpServletStatelessServerTransport.service(
					servletRequest, servletResponse);
			}

		};
	}

	private McpSchema.CallToolResult _call(
		String body, String contentType, String location,
		McpTransportContext mcpTransportContext, String method) {

		Http.Options options = new Http.Options();

		String resolvedContentType =
			(contentType != null) ? contentType : ContentTypes.APPLICATION_JSON;

		if (Validator.isNotNull(body)) {
			options.setBody(body, resolvedContentType, StringPool.UTF8);
		}

		Map<String, String> headers = _getHeaders(
			(String)mcpTransportContext.get("authorization"),
			(String)mcpTransportContext.get("liferayAIHubCellOnBehalfOf"));

		if (Validator.isNotNull(body)) {
			headers.put("Content-Type", resolvedContentType);
		}

		options.setHeaders(headers);

		options.setLocation(location);
		options.setMethod(Http.Method.valueOf(StringUtil.toUpperCase(method)));
		options.setTimeout(60000);

		try {
			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if (responseCode < 300) {
				return McpSchema.CallToolResult.builder(
				).addTextContent(
					content
				).isError(
					false
				).build();
			}

			return McpSchema.CallToolResult.builder(
			).addTextContent(
				StringBundler.concat(
					"Status code: ", responseCode, ", Content:\n", content)
			).isError(
				true
			).build();
		}
		catch (IOException ioException) {
			_log.error(ioException);

			return McpSchema.CallToolResult.builder(
			).addTextContent(
				ioException.getMessage()
			).isError(
				true
			).build();
		}
	}

	private void _destroy(String servletKey) {
		Servlet servlet = _servlets.remove(servletKey);

		if (servlet != null) {
			servlet.destroy();
		}
	}

	private Map<String, String> _getHeaders(
		String authorization, String liferayAIHubCellOnBehalfOf) {

		if (liferayAIHubCellOnBehalfOf != null) {
			return HashMapBuilder.put(
				"Liferay-AI-Hub-Cell-On-Behalf-Of", liferayAIHubCellOnBehalfOf
			).build();
		}

		if (authorization != null) {
			return HashMapBuilder.put(
				"Authorization", authorization
			).build();
		}

		return new HashMap<>();
	}

	private String _getMCPServerProfileName(
		HttpServletRequest httpServletRequest) {

		String pathInfo = httpServletRequest.getPathInfo();

		if (Validator.isNull(pathInfo) || pathInfo.equals("/")) {
			return null;
		}

		String mcpServerProfileName = pathInfo.substring(1);

		int index = mcpServerProfileName.indexOf('/');

		if (index > 0) {
			mcpServerProfileName = mcpServerProfileName.substring(0, index);
		}

		if (mcpServerProfileName.isEmpty()) {
			return null;
		}

		return mcpServerProfileName;
	}

	private ObjectEntry _getMCPServerProfileObjectEntry(
		long companyId, HttpServletRequest httpServletRequest) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					MCPServerConstants.
						EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE,
					companyId);

		if (objectDefinition == null) {
			return null;
		}

		String mcpServerProfileName = GetterUtil.getString(
			_getMCPServerProfileName(httpServletRequest), "default");

		for (ObjectEntry objectEntry :
				_objectEntryLocalService.getObjectEntries(
					0, objectDefinition.getObjectDefinitionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			Map<String, Serializable> values = objectEntry.getValues();

			if (mcpServerProfileName.equals(values.get("name"))) {
				return objectEntry;
			}
		}

		return null;
	}

	private Servlet _getServlet(
		String baseURL, long companyId, Map<String, String> headers,
		ObjectEntry mcpServerProfileObjectEntry) {

		String mcpServerProfileName =
			(String)mcpServerProfileObjectEntry.getValues(
			).get(
				"name"
			);

		synchronized (this) {
			return _servlets.computeIfAbsent(
				_getServletKey(companyId, mcpServerProfileName),
				servletKey -> _buildServlet(
					baseURL, companyId, headers, mcpServerProfileObjectEntry));
		}
	}

	private String _getServletKey(long companyId, String mcpServerProfileName) {
		return companyId + StringPool.UNDERLINE + mcpServerProfileName;
	}

	private List<McpStatelessServerFeatures.SyncPromptSpecification>
		_getSyncPromptSpecifications(long companyId) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					MCPServerConstants.
						EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROMPT,
					companyId);

		if (objectDefinition == null) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			objectEntry -> {
				Map<String, Serializable> values = objectEntry.getValues();

				return new McpStatelessServerFeatures.SyncPromptSpecification(
					new McpSchema.Prompt(
						(String)values.get("name"),
						(String)values.get("description"),
						Collections.emptyList()),
					(mcpTransportContext, request) ->
						new McpSchema.GetPromptResult(
							(String)values.get("description"),
							List.of(
								new McpSchema.PromptMessage(
									McpSchema.Role.USER,
									new McpSchema.TextContent(
										(String)values.get("prompt"))))));
			});
	}

	private McpSchema.Tool _getTool(
		Map<String, String> headers, String toolName, String url) {

		Http.Options options = new Http.Options();

		options.setHeaders(headers);
		options.setLocation(url);

		try {
			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if (responseCode >= 300) {
				throw new RuntimeException(
					StringBundler.concat(
						"Unable to describe tool \"", toolName, "\": HTTP ",
						responseCode, " ", content));
			}

			JSONObject toolJSONObject = _jsonFactory.createJSONObject(content);

			return McpSchema.Tool.builder(
			).description(
				toolJSONObject.getString("description")
			).inputSchema(
				_objectMapper.readValue(
					toolJSONObject.getJSONObject(
						"inputSchema"
					).toString(),
					McpSchema.JsonSchema.class)
			).name(
				toolName
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private boolean _isEnabled(long companyId) {
		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-63311")) {
			return false;
		}

		try {
			MCPServerConfiguration mcpServerConfiguration =
				_configurationProvider.getCompanyConfiguration(
					MCPServerConfiguration.class, companyId);

			return mcpServerConfiguration.enabled();
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MCPServerServlet.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@Reference
	private ConfigurationProvider _configurationProvider;

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

	private final Map<String, Servlet> _servlets = new ConcurrentHashMap<>();

}