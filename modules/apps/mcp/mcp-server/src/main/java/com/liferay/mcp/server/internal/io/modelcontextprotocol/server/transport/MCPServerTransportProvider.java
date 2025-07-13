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
public class MCPServerTransportProvider
	extends HttpServletSseServerTransportProvider {

	public MCPServerTransportProvider(String baseURL) {
		super(new ObjectMapper(), baseURL, "/message", "/sse");
	}

	public String getAccessToken(McpSyncServerExchange exchange) {
		try {
			Field exchangeField = McpSyncServerExchange.class.getDeclaredField(
				"exchange");

			exchangeField.setAccessible(true);

			Field sessionField = McpAsyncServerExchange.class.getDeclaredField(
				"session");

			sessionField.setAccessible(true);

			Session session = (Session)sessionField.get(
				exchangeField.get(exchange));

			return session.getAccessToken();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setSessionFactory(McpServerSession.Factory sessionFactory) {
		super.setSessionFactory(new Session.Factory(sessionFactory));
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String accessToken = null;
		String authorization = httpServletRequest.getHeader("Authorization");

		if ((authorization != null) && authorization.startsWith("Bearer ")) {
			accessToken = authorization.substring("Bearer ".length());
		}

		try (SafeCloseable safeCloseable =
				AccessTokenThreadLocal.setAccessTokenWithSafeCloseable(
					accessToken)) {

			super.doGet(httpServletRequest, httpServletResponse);
		}
	}

	private static class AccessTokenThreadLocal {

		public static String get() {
			return _accessToken.get();
		}

		public static SafeCloseable setAccessTokenWithSafeCloseable(
			String accessToken) {

			return _accessToken.setWithSafeCloseable(accessToken);
		}

		private static final CentralizedThreadLocal<String> _accessToken =
			new CentralizedThreadLocal<>(
				AccessTokenThreadLocal.class + "._accessToken");

	}

	private static class Session extends McpServerSession {

		public Session(
			String id, Duration requestTimeout, McpServerTransport transport,
			InitRequestHandler initHandler,
			InitNotificationHandler initNotificationHandler,
			Map<String, RequestHandler<?>> requestHandlers,
			Map<String, NotificationHandler> notificationHandlers) {

			super(
				id, requestTimeout, transport, initHandler,
				initNotificationHandler, requestHandlers, notificationHandlers);

			_accessToken = AccessTokenThreadLocal.get();
		}

		public String getAccessToken() {
			return _accessToken;
		}

		public static class Factory implements McpServerSession.Factory {

			public Factory(McpServerSession.Factory factory) {
				_factory = factory;
			}

			@Override
			public McpServerSession create(McpServerTransport transport) {
				McpServerSession mcpServerSession = _factory.create(transport);

				try {
					Field requestTimeoutField =
						McpServerSession.class.getDeclaredField(
							"requestTimeout");

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
						McpServerSession.class.getDeclaredField(
							"requestHandlers");

					requestHandlersField.setAccessible(true);

					Field notificationHandlersField =
						McpServerSession.class.getDeclaredField(
							"notificationHandlers");

					notificationHandlersField.setAccessible(true);

					return new Session(
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

		private final String _accessToken;

	}

}