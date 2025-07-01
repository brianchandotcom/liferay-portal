/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import jakarta.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leandro Aguiar
 * @author Vendel Toreki
 * @author Alejandro Tardín
 */
@Component(service = {})
public class LiferayMCPServletRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		HttpServletSseServerTransportProvider
			httpServletSseServerTransportProvider =
				new HttpServletSseServerTransportProvider.Builder(
				).baseUrl(
					"http://localhost:8080/o/mcp"
				).messageEndpoint(
					"/message"
				).build();

		_serviceRegistration = bundleContext.registerService(
			Servlet.class, httpServletSseServerTransportProvider,
			HashMapDictionaryBuilder.put(
				"osgi.http.whiteboard.context.path", "/mcp"
			).put(
				"osgi.http.whiteboard.servlet.name",
				"com.liferay.mcp.server.LiferayMcpServerHttpServlet"
			).put(
				"osgi.http.whiteboard.servlet.pattern", "/mcp/*"
			).build());

		_mcpSyncServer = McpServer.sync(
			httpServletSseServerTransportProvider
		).capabilities(
			McpSchema.ServerCapabilities.builder(
			).tools(
				true
			).build()
		).build();

		_mcpSyncServer.addTool(
			new McpServerFeatures.SyncToolSpecification(
				new McpSchema.Tool(
					"hello-world", "returns the \"hello world\" message",
					JSONUtil.put(
						"type", "object"
					).toString()),
				(exchange, arguments) -> new McpSchema.CallToolResult(
					"Hello World", false)));
	}

	@Deactivate
	protected void deactivate() {
		_mcpSyncServer.close();
		_serviceRegistration.unregister();
	}

	private McpSyncServer _mcpSyncServer;
	private ServiceRegistration<Servlet> _serviceRegistration;

}