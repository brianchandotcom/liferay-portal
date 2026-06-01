/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.servlet;

import com.liferay.mcp.server.rest.internal.constants.MCPServerConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.rest.internal.servlet.MCPProtectedResourceMetadataServlet",
		"osgi.http.whiteboard.servlet.pattern=/.well-known/oauth-protected-resource",
		"osgi.http.whiteboard.servlet.pattern=/.well-known/oauth-protected-resource/*"
	},
	service = Servlet.class
)
public class MCPProtectedResourceMetadataServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String portalURL =
			_portal.getPortalURL(httpServletRequest) + _portal.getPathContext();

		String resource = portalURL + MCPServerConstants.MCP_PATH;

		JSONObject metadataJSONObject = JSONUtil.put(
			"authorization_servers", JSONUtil.putAll(portalURL)
		).put(
			"bearer_methods_supported", JSONUtil.putAll("header")
		).put(
			"resource", resource
		).put(
			"resource_name", "Liferay MCP Server"
		);

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setHeader(
			HttpHeaders.CACHE_CONTROL, "public, max-age=300");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(metadataJSONObject.toString());
	}

	@Reference
	private Portal _portal;

}