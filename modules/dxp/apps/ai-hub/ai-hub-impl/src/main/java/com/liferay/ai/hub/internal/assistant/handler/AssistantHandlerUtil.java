/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.assistant.handler;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class AssistantHandlerUtil {

	public static void handle(
		AssistantHandlerContext assistantHandlerContext, String key) {

		AssistantHandler assistantHandler = _assistantHandlers.get(key);

		assistantHandler.handle(assistantHandlerContext);
	}

	private static final Map<String, AssistantHandler> _assistantHandlers =
		Map.of(
			ChatAssistantHandler.KEY, new ChatAssistantHandler(),
			DefaultAssistantHandler.KEY, new DefaultAssistantHandler());

}