/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Base64;
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

		HttpServletSseServerTransportProvider
			httpServletSseServerTransportProvider =
				new HttpServletSseServerTransportProvider.Builder(
				).baseUrl(
					baseURL + "/mcp"
				).messageEndpoint(
					"/message"
				).build();

		McpServer.sync(
			httpServletSseServerTransportProvider
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).resources(
				false, true
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
				_callEndpoint("GET", baseURL + "/openapi", null), false)
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
				_callEndpoint(
					"GET", String.valueOf(arguments.get("url")), null),
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
				_callEndpoint(
					String.valueOf(arguments.get("method")),
					baseURL + arguments.get("path"),
					String.valueOf(arguments.get("payload"))),
				false)
		).build();

		return httpServletSseServerTransportProvider;
	}

	private String _callEndpoint(String method, String path, String payload) {
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
			connection.setRequestMethod(StringUtil.toUpperCase(method));

			if (Validator.isNotNull(payload)) {
				connection.setRequestProperty(
					"Content-Type", "application/json");

				try (OutputStream outputStream = connection.getOutputStream()) {
					outputStream.write(payload.getBytes("UTF-8"));
				}
			}

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