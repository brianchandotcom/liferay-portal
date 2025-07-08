package com.liferay.mcp.server.internal.mcp.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.mcp.server.internal.mcp.session.MCPSessionUtil;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpServerSession;
import io.modelcontextprotocol.spec.McpServerTransport;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

public class MCPCompany {

	public MCPCompany(String baseURL, long companyId) {
		_baseURL = baseURL;
		_companyId = companyId;
		_servlet = new TransportProvider(baseURL + "/mcp");
	}

	public TransportProvider getServlet() {
		return _servlet;
	}

	public String callEndpoint(String method, String path, String payload, String accessToken) {
		return _callEndpoint(method, _baseURL + path, payload, accessToken);
	}

	public String getAllOpenAPIs() {
		if (_openAPIs == null) {
			refreshAllOpenAPIs();
		}

		return _openAPIs;
	}

	public String getOpenAPI(String url) {
		return _callEndpoint("GET", url, null, null);
	}

	public void refreshAllOpenAPIs() {
		_openAPIs = _callEndpoint("GET", _baseURL + "/openapi", null, null);
	}

	private final String _baseURL;

	private final long _companyId;

	private final TransportProvider _servlet;

	private String _openAPIs;

	protected static String _callEndpoint(String method, String path, String payload, String accessToken) {
		try {
			URL url = new URL(path);

			HttpURLConnection connection =
				(HttpURLConnection) url.openConnection();

			connection.setRequestProperty(
				"Authorization",
				"Bearer " + accessToken);

			connection.setDoOutput(true);
			connection.setRequestMethod(StringUtil.toUpperCase(method));

			if (Validator.isNotNull(payload)) {
				connection.setRequestProperty(
					"Content-Type", "application/json");

				try (OutputStream outputStream = connection.getOutputStream()) {
					outputStream.write(payload.getBytes(StandardCharsets.UTF_8));
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

	public class TransportProvider extends HttpServletSseServerTransportProvider {
		TransportProvider(String baseURL) {
			super(new ObjectMapper(), baseURL, "/message", "/sse");
		}

		@Override
		public void setSessionFactory(McpServerSession.Factory sessionFactory) {
			super.setSessionFactory(new Factory(sessionFactory));
		}

		@Override
		protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
			var accessToken = extractAccessToken(request);

			try (var scope = AccessTokenThreadLocal.set(accessToken)) {
				super.doGet(request, response);
			}
		}

		static String extractAccessToken(HttpServletRequest request) {
			var authorization = request.getHeader("Authorization");

			if (authorization == null || !authorization.startsWith("Bearer ")) {
				return null;
			}

			return authorization.substring("Bearer ".length());
		}
	}

	class Factory implements McpServerSession.Factory {
		private final McpServerSession.Factory _factory;

		Factory(McpServerSession.Factory factory) {
			_factory = factory;
		}

		@Override
		public McpServerSession create(McpServerTransport transport) {
			var session = _factory.create(transport);

			try {
				return new Session(
					session.getId(),
					MCPSessionUtil.getRequestTimeout(session),
					transport,
					MCPSessionUtil.getInitHandler(session),
					MCPSessionUtil.getInitNotificationHandler(session),
					MCPSessionUtil.getRequestHandlers(session),
					MCPSessionUtil.getNotificationHandlers(session)
				);
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public class Session extends McpServerSession {
		private final String _accessToken;

		public Session(
			String id, Duration requestTimeout, McpServerTransport transport,
			InitRequestHandler initHandler,
			InitNotificationHandler initNotificationHandler,
			Map<String, RequestHandler<?>> requestHandlers,
			Map<String, NotificationHandler> notificationHandlers) {
			super(
				id, requestTimeout, transport, initHandler,
				initNotificationHandler,
				requestHandlers, notificationHandlers);

			_accessToken = AccessTokenThreadLocal.get();
		}

		long getCompanyId() {
			return _companyId;
		}

		public String getAccessToken() {
			return _accessToken;
		}
	}

	static final class AccessTokenThreadLocal {
		private static final CentralizedThreadLocal<String> _accessToken = new CentralizedThreadLocal<>(AccessTokenThreadLocal.class + "._accessToken");

		private AccessTokenThreadLocal() {}

		public static String get() {
			return _accessToken.get();
		}

		public static SafeCloseable set(String accessToken) {
			return _accessToken.setWithSafeCloseable(accessToken);
		}
	}
}
