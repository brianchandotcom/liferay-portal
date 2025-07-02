/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leandro Aguiar
 * @author Vendel Toreki
 * @author Alejandro Tardín
 *
 * This servlet operates with the following considerations:
 *
 * 1. No Authorization Support:
 *    All actions are performed using the admin user. Authorization must be
 *    implemented using OAuth, as described in the spec:
 *    https://modelcontextprotocol.io/specification/2025-06-18/basic/authorization
 *    For now let's leave this out of the scope of the exchange program.
 *
 * 2. No Reactivity:
 *    The server is initialized on the first request with the resources and
 *    tools available at that time. Any changes in Liferay after initialization
 *    will not be reflected unless the server is restarted. The simplest way to
 *    improve this would be to cache servlets for a fixed amount of time and
 *    rebuild them after.
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/mcp",
		"osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.MCPServlet",
		"osgi.http.whiteboard.servlet.pattern=/mcp/*"
	},
	service = Servlet.class
)
public class MCPServlet extends GenericServlet {

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		_servlets.computeIfAbsent(
			_portal.getCompanyId((HttpServletRequest)servletRequest),
			companyId -> {
				try {
					return _buildMCPServlet(companyId);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		).service(
			servletRequest, servletResponse
		);
	}

	private HttpServletSseServerTransportProvider _buildMCPServlet(
			long companyId)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		String baseURL = company.getPortalURL(0) + _portal.getPathModule();

		JSONObject openAPIJSONObject = _jsonFactory.createJSONObject(
			_get(baseURL + "/openapi"));

		HttpServletSseServerTransportProvider
			httpServletSseServerTransportProvider =
				new HttpServletSseServerTransportProvider.Builder(
				).baseUrl(
					baseURL + "/mcp"
				).messageEndpoint(
					"/message"
				).build();

		McpSyncServer mcpSyncServer = McpServer.sync(
			httpServletSseServerTransportProvider
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).resources(
				false, true
			).tools(
				true
			).build()
		).build();

		for (String key : openAPIJSONObject.keySet()) {
			mcpSyncServer.addResource(
				new McpServerFeatures.SyncResourceSpecification(
					new McpSchema.Resource(
						openAPIJSONObject.getJSONArray(
							key
						).getString(
							0
						),
						key, "OpenAPI YAML file for " + key, "application/yaml",
						null),
					(exchange, arguments) -> new McpSchema.ReadResourceResult(
						List.of(
							new McpSchema.TextResourceContents(
								arguments.uri(), "application/yaml",
								_get(arguments.uri()))))));
		}

		mcpSyncServer.addTool(
			new McpServerFeatures.SyncToolSpecification(
				new McpSchema.Tool(
					"hello-world", "returns the \"hello world\" message",
					JSONUtil.put(
						"type", "object"
					).toString()),
				(exchange, arguments) -> new McpSchema.CallToolResult(
					"Hello World", false)));

		return httpServletSseServerTransportProvider;
	}

	private String _get(String path) {
		try {
			URL url = new URL(path);

			HttpURLConnection connection =
				(HttpURLConnection)url.openConnection();

			String credentials =
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

			Base64.Encoder encoder = Base64.getEncoder();

			connection.setRequestProperty(
				"Authorization",
				"Basic " + encoder.encodeToString(credentials.getBytes()));

			connection.setDoOutput(true);
			connection.setRequestMethod(StringUtil.toUpperCase("GET"));

			if (connection.getResponseCode() >= 300) {
				throw new Exception(
					StringUtil.read(connection.getErrorStream()));
			}

			return StringUtil.read(connection.getInputStream());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	private final Map<Long, Servlet> _servlets = new ConcurrentHashMap<>();

}