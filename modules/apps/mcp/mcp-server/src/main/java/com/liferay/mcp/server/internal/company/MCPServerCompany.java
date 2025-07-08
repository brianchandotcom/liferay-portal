/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import io.modelcontextprotocol.server.McpAsyncServerExchange;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpServerSession;
import io.modelcontextprotocol.spec.McpServerTransport;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Field;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.time.Duration;

import java.util.Map;

/**
 * @author Leandro Aguiar
 */
public class MCPServerCompany {

	public static String getAccessToken(McpSyncServerExchange exchange) {
		try {
			Field exchangeField = McpSyncServerExchange.class.getDeclaredField(
				"exchange");

			exchangeField.setAccessible(true);

			Field sessionField = McpAsyncServerExchange.class.getDeclaredField(
				"session");

			sessionField.setAccessible(true);

			MCPServerCompany.Session session =
				(MCPServerCompany.Session)sessionField.get(
					exchangeField.get(exchange));

			return session.getAccessToken();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public MCPServerCompany(String baseURL) {
		_baseURL = baseURL;

		_servlet = new TransportProvider(baseURL + "/mcp");
	}

	public String callEndpoint(
		String method, String path, String payload, String accessToken) {

		return _callEndpoint(method, _baseURL + path, payload, accessToken);
	}

	public String getAllOpenAPIs(String accessToken) {
		if (_openAPIs == null) {
			_openAPIs = _callEndpoint(
				"GET", _baseURL + "/openapi", null, accessToken);
		}

		return _openAPIs;
	}

	public String getOpenAPI(String url, String accessToken) {
		return _callEndpoint("GET", url, null, accessToken);
	}

	public HttpServletSseServerTransportProvider getServlet() {
		return _servlet;
	}

	private String _callEndpoint(
		String method, String path, String payload, String accessToken) {

		try {
			URL url = new URL(path);

			HttpURLConnection connection =
				(HttpURLConnection)url.openConnection();

			if (accessToken != null) {
				connection.setRequestProperty(
					"Authorization", "Bearer " + accessToken);
			}

			connection.setDoOutput(true);
			connection.setRequestMethod(StringUtil.toUpperCase(method));

			if (Validator.isNotNull(payload)) {
				connection.setRequestProperty(
					"Content-Type", "application/json");

				try (OutputStream outputStream = connection.getOutputStream()) {
					outputStream.write(
						payload.getBytes(StandardCharsets.UTF_8));
				}
			}

			int status = connection.getResponseCode();

			if (status >= 300) {
				String errorMessage = StringBundler.concat(
					"Request to ", path, " failed with status ", status);

				InputStream inputStream = connection.getErrorStream();

				if (inputStream != null) {
					errorMessage += StringUtil.read(inputStream);
				}

				throw new Exception(errorMessage);
			}

			return StringUtil.read(connection.getInputStream());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final String _baseURL;
	private String _openAPIs;
	private final TransportProvider _servlet;

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

		private final String _accessToken;

	}

	private class Factory implements McpServerSession.Factory {

		public Factory(McpServerSession.Factory factory) {
			_factory = factory;
		}

		@Override
		public McpServerSession create(McpServerTransport transport) {
			McpServerSession session = _factory.create(transport);

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

				return new Session(
					session.getId(), (Duration)requestTimeoutField.get(session),
					transport,
					(McpServerSession.InitRequestHandler)
						initRequestHandlerField.get(session),
					(McpServerSession.InitNotificationHandler)
						initNotificationHandlerField.get(session),
					(Map<String, McpServerSession.RequestHandler<?>>)
						requestHandlersField.get(session),
					(Map<String, McpServerSession.NotificationHandler>)
						notificationHandlersField.get(session));
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private final McpServerSession.Factory _factory;

	}

	private class TransportProvider
		extends HttpServletSseServerTransportProvider {

		public TransportProvider(String baseURL) {
			super(new ObjectMapper(), baseURL, "/message", "/sse");
		}

		@Override
		public void setSessionFactory(McpServerSession.Factory sessionFactory) {
			super.setSessionFactory(new Factory(sessionFactory));
		}

		@Override
		protected void doGet(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
			throws IOException, ServletException {

			String accessToken = _extractAccessToken(httpServletRequest);

			try (SafeCloseable safeCloseable =
					AccessTokenThreadLocal.setAccessTokenWithSafeCloseable(
						accessToken)) {

				super.doGet(httpServletRequest, httpServletResponse);
			}
		}

		private String _extractAccessToken(
			HttpServletRequest httpServletRequest) {

			String authorization = httpServletRequest.getHeader(
				"Authorization");

			if ((authorization == null) ||
				!authorization.startsWith("Bearer ")) {

				return null;
			}

			return authorization.substring("Bearer ".length());
		}

	}

}