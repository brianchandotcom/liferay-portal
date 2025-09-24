/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.io.modelcontextprotocol.server.transport;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import io.modelcontextprotocol.server.McpAsyncServerExchange;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpServerSession;
import io.modelcontextprotocol.spec.McpServerTransport;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.lang.reflect.Field;

import java.time.Duration;

import java.util.Map;

/**
 * @author Leandro Aguiar
 */
public class AuthorizedHttpServletSseServerTransportProvider
	extends HttpServletSseServerTransportProvider {

	public AuthorizedHttpServletSseServerTransportProvider(String baseURL) {
		super(new ObjectMapper(), baseURL, "/message", "/sse");
	}

	public String getAuthorizationHeader(McpSyncServerExchange exchange) {
		try {
			Field exchangeField = McpSyncServerExchange.class.getDeclaredField(
				"exchange");

			exchangeField.setAccessible(true);

			Field sessionField = McpAsyncServerExchange.class.getDeclaredField(
				"session");

			sessionField.setAccessible(true);

			AuthorizedMcpServerSession authorizedMcpServerSession =
				(AuthorizedMcpServerSession)sessionField.get(
					exchangeField.get(exchange));

			return authorizedMcpServerSession.getAuthorizationHeader();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setSessionFactory(McpServerSession.Factory sessionFactory) {
		super.setSessionFactory(
			new AuthorizedMcpServerSessionFactory(sessionFactory));
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try (SafeCloseable safeCloseable =
				AuthorizationHeaderThreadLocal.
					setAuthorizationHeaderWithSafeCloseable(
						httpServletRequest.getHeader("Authorization"))) {

			super.doGet(httpServletRequest, httpServletResponse);
		}
	}

	private static class AuthorizationHeaderThreadLocal {

		public static String get() {
			return _authorizationHeader.get();
		}

		public static SafeCloseable setAuthorizationHeaderWithSafeCloseable(
			String authorizationHeader) {

			return _authorizationHeader.setWithSafeCloseable(
				authorizationHeader);
		}

		private static final CentralizedThreadLocal<String>
			_authorizationHeader = new CentralizedThreadLocal<>(
				AuthorizationHeaderThreadLocal.class + "._authorizationHeader");

	}

	private static class AuthorizedMcpServerSession extends McpServerSession {

		public AuthorizedMcpServerSession(
			String id, Duration requestTimeout, McpServerTransport transport,
			InitRequestHandler initHandler,
			InitNotificationHandler initNotificationHandler,
			Map<String, RequestHandler<?>> requestHandlers,
			Map<String, NotificationHandler> notificationHandlers) {

			super(
				id, requestTimeout, transport, initHandler,
				initNotificationHandler, requestHandlers, notificationHandlers);

			_authorizationHeader = AuthorizationHeaderThreadLocal.get();
		}

		public String getAuthorizationHeader() {
			return _authorizationHeader;
		}

		private final String _authorizationHeader;

	}

	private static class AuthorizedMcpServerSessionFactory
		implements McpServerSession.Factory {

		public AuthorizedMcpServerSessionFactory(
			McpServerSession.Factory factory) {

			_factory = factory;
		}

		@Override
		public McpServerSession create(McpServerTransport transport) {
			McpServerSession mcpServerSession = _factory.create(transport);

			try {
				Field requestTimeoutField =
					McpServerSession.class.getDeclaredField("requestTimeout");

				requestTimeoutField.setAccessible(true);

				Field initRequestHandlerField =
					McpServerSession.class.getDeclaredField(
						"initRequestHandler");

				initRequestHandlerField.setAccessible(true);

				Field initNotificationHandlerField =
					McpServerSession.class.getDeclaredField(
						"initNotificationHandler");

				initNotificationHandlerField.setAccessible(true);

				Field requestHandlersField =
					McpServerSession.class.getDeclaredField("requestHandlers");

				requestHandlersField.setAccessible(true);

				Field notificationHandlersField =
					McpServerSession.class.getDeclaredField(
						"notificationHandlers");

				notificationHandlersField.setAccessible(true);

				return new AuthorizedMcpServerSession(
					mcpServerSession.getId(),
					(Duration)requestTimeoutField.get(mcpServerSession),
					transport,
					(McpServerSession.InitRequestHandler)
						initRequestHandlerField.get(mcpServerSession),
					(McpServerSession.InitNotificationHandler)
						initNotificationHandlerField.get(mcpServerSession),
					(Map<String, McpServerSession.RequestHandler<?>>)
						requestHandlersField.get(mcpServerSession),
					(Map<String, McpServerSession.NotificationHandler>)
						notificationHandlersField.get(mcpServerSession));
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final McpServerSession.Factory _factory;

	}

}