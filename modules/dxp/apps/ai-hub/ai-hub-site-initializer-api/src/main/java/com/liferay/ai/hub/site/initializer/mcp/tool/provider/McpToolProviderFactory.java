/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.mcp.tool.provider;

import dev.langchain4j.mcp.McpToolProvider;

import java.util.List;
import java.util.Locale;

/**
 * @author João Victor Alves
 */
public interface McpToolProviderFactory {

	public McpToolProvider create(
		long companyId, long groupId, Locale locale,
		List<String> mcpServerExternalReferenceCodes, long userId);

}