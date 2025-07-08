package com.liferay.mcp.server.internal.mcp.session;

import com.liferay.mcp.server.internal.mcp.company.MCPCompany;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import io.modelcontextprotocol.server.McpAsyncServerExchange;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpServerSession;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Map;

public final class MCPSessionUtil {
	private static final Log _log = LogFactoryUtil.getLog(MCPSessionUtil.class);
	private static Field _mcpSyncServerExchangeField_exchange;
	private static Field _mcpAsyncServerExchangeField_exchange;
	private static Field _mcpServerSessionField_requestTimeout;
	private static Field _mcpServerSessionField_initRequestHandler;
	private static Field _mcpServerSessionField_initNotificationHandler;
	private static Field _mcpServerSessionField_requestHandlers;
	private static Field _mcpServerSessionField_notificationHandlers;

	static {
		try {
			_mcpSyncServerExchangeField_exchange = McpSyncServerExchange.class.getDeclaredField("exchange");
			_mcpSyncServerExchangeField_exchange.setAccessible(true);

			_mcpAsyncServerExchangeField_exchange = McpAsyncServerExchange.class.getDeclaredField("session");
			_mcpAsyncServerExchangeField_exchange.setAccessible(true);

			_mcpServerSessionField_requestTimeout = McpServerSession.class.getDeclaredField("requestTimeout");
			_mcpServerSessionField_requestTimeout.setAccessible(true);

			_mcpServerSessionField_initRequestHandler = McpServerSession.class.getDeclaredField("initRequestHandler");
			_mcpServerSessionField_initRequestHandler.setAccessible(true);

			_mcpServerSessionField_initNotificationHandler = McpServerSession.class.getDeclaredField("initNotificationHandler");
			_mcpServerSessionField_initNotificationHandler.setAccessible(true);

			_mcpServerSessionField_requestHandlers = McpServerSession.class.getDeclaredField("requestHandlers");
			_mcpServerSessionField_requestHandlers.setAccessible(true);

			_mcpServerSessionField_notificationHandlers = McpServerSession.class.getDeclaredField("notificationHandlers");
			_mcpServerSessionField_notificationHandlers.setAccessible(true);
		} catch (Throwable t) {
			_log.error(t);
		}
	}

	private MCPSessionUtil() {}

	public static MCPCompany.Session getSession(McpSyncServerExchange exchange) throws IllegalAccessException {
		return getSession((McpAsyncServerExchange) _mcpSyncServerExchangeField_exchange.get(exchange));
	}

	public static MCPCompany.Session getSession(McpAsyncServerExchange exchange) throws IllegalAccessException {
		return (MCPCompany.Session) _mcpAsyncServerExchangeField_exchange.get(exchange);
	}

	public static Duration getRequestTimeout(McpServerSession session) throws IllegalAccessException {
		return (Duration) _mcpServerSessionField_requestTimeout.get(session);
	}

	public static McpServerSession.InitRequestHandler getInitHandler(McpServerSession session) throws IllegalAccessException {
		return (McpServerSession.InitRequestHandler) _mcpServerSessionField_initRequestHandler.get(session);
	}

	public static McpServerSession.InitNotificationHandler getInitNotificationHandler(McpServerSession session) throws IllegalAccessException {
		return (McpServerSession.InitNotificationHandler) _mcpServerSessionField_initNotificationHandler.get(session);
	}

	public static Map<String, McpServerSession.RequestHandler<?>> getRequestHandlers(McpServerSession session) throws IllegalAccessException {
		return (Map<String, McpServerSession.RequestHandler<?>>) _mcpServerSessionField_requestHandlers.get(session);
	}

	public static Map<String, McpServerSession.NotificationHandler> getNotificationHandlers(McpServerSession session) throws IllegalAccessException {
		return (Map<String, McpServerSession.NotificationHandler>) _mcpServerSessionField_notificationHandlers.get(session);
	}
}
