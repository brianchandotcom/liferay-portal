/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Leandro Aguiar
 */
public class MCPServerToolCallHandler {

	public static <E extends Exception>
		BiFunction
			<McpSyncServerExchange, Map<String, Object>,
			 McpSchema.CallToolResult> of(
				UnsafeBiFunction
					<McpSyncServerExchange, Map<String, Object>, String, E>
						unsafeBiFunction) {

		return (exchange, arguments) -> {
			try {
				return new McpSchema.CallToolResult(
					unsafeBiFunction.apply(exchange, arguments), false);
			}
			catch (Exception exception) {
				_log.error(exception);

				return new McpSchema.CallToolResult(
					exception.getMessage(), true);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MCPServerToolCallHandler.class);

}