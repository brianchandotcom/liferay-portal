/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.resource.v1_0;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.internal.util.ToolSetUtil;
import com.liferay.mcp.server.rest.resource.v1_0.ToolResource;

import jakarta.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/tool.properties",
	scope = ServiceScope.PROTOTYPE, service = ToolResource.class
)
public class ToolResourceImpl extends BaseToolResourceImpl {

	@Override
	public Tool getTool(String toolSetName, String toolName) {
		return ToolSetUtil.getTool(
			contextHttpServletRequest, toolName, toolSetName);
	}

	@Override
	public Response invokeToolObject(
			String toolSetName, String toolName, Object object)
		throws Exception {

		return ToolSetUtil.invokeTool(
			contextHttpServletRequest, object, toolName, toolSetName,
			contextUriInfo);
	}

}