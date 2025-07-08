/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.liferay.mcp.server.internal.company.MCPServerCompany;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leandro Aguiar
 * @author Vendel Toreki
 * @author Alejandro Tardín
 * <p>
 * This servlet operates with the following considerations:
 *
 * 1. No Reactivity:
 *    The server is initialized on the first request with the resources and
 *    tools available at that time. Any changes in Liferay after initialization
 *    will not be reflected unless the server is restarted. The simplest way to
 *    improve this would be to cache servlets for a fixed amount of time and
 *    rebuild them after.
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

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if ((servletRequest instanceof HttpServletRequest httpServletRequest) &&
			Validator.isNull(httpServletRequest.getHeader("Authorization")) &&
			(servletResponse instanceof
				HttpServletResponse httpServletResponse)) {

			httpServletResponse.setHeader(
				"WWW-Authenticate", "Bearer error=\"invalid_request\"");
			httpServletResponse.setStatus(401);

			return;
		}

		MCPServerCompany mcpServerCompany = _mcpServerCompanies.computeIfAbsent(
			_portal.getCompanyId((HttpServletRequest)servletRequest),
			companyId -> _buildMCPCompany(
				_portal.getPortalURL((HttpServletRequest)servletRequest) +
					_portal.getPathModule()));

		Servlet servlet = mcpServerCompany.getServlet();

		servlet.service(servletRequest, servletResponse);
	}

	private MCPServerCompany _buildMCPCompany(String baseURL) {
		MCPServerCompany mcpServerCompany = new MCPServerCompany(baseURL);

		McpServer.sync(
			mcpServerCompany.getServlet()
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).tools(
				true
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
			(exchange, arguments) -> new McpSchema.CallToolResult(
				mcpServerCompany.getAllOpenAPIs(
					MCPServerCompany.getAccessToken(exchange)),
				false)
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
			(exchange, arguments) -> new McpSchema.CallToolResult(
				mcpServerCompany.getOpenAPI(
					String.valueOf(arguments.get("url")),
					MCPServerCompany.getAccessToken(exchange)),
				false)
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
			(exchange, arguments) -> new McpSchema.CallToolResult(
				mcpServerCompany.callEndpoint(
					String.valueOf(arguments.get("method")),
					String.valueOf(arguments.get("path")),
					String.valueOf(arguments.get("payload")),
					MCPServerCompany.getAccessToken(exchange)),
				false)
		).build();

		return mcpServerCompany;
	}

	@Reference
	private JSONFactory _jsonFactory;

	private final Map<Long, MCPServerCompany> _mcpServerCompanies =
		new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

}