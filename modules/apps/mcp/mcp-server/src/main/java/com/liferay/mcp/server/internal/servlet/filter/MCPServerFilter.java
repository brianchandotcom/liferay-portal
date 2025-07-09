/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet.filter;

import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=MCP Server Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class MCPServerFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.getOAuth2Application(
				_portal.getCompanyId(httpServletRequest),
				MCPServerConstants.MCP_SERVER_OAUTH2_CLIENT_ID);

		if (httpServletRequest.getRequestURI(
			).endsWith(
				"/.well-known/oauth-authorization-server"
			)) {

			httpServletResponse.getWriter(
			).write(
				JSONUtil.put(
					"authorization_endpoint",
					_portal.getAbsoluteURL(
						httpServletRequest,
						"/o/oauth2/authorize?response_type=code&client_id=" +
							oAuth2Application.getClientId())
				).put(
					"code_challenge_methods_supported",
					JSONUtil.putAll("plain", "S256")
				).put(
					"grant_types_supported",
					JSONUtil.putAll("authorization_code")
				).put(
					"issuer", _portal.getPortalURL(httpServletRequest)
				).put(
					"registration_endpoint",
					_portal.getAbsoluteURL(
						httpServletRequest, "/o/oauth2/register")
				).put(
					"response_modes_supported", JSONUtil.putAll("query")
				).put(
					"response_types_supported", JSONUtil.putAll("code")
				).put(
					"revocation_endpoint",
					_portal.getAbsoluteURL(
						httpServletRequest, "/o/oauth2/token")
				).put(
					"token_endpoint",
					StringBundler.concat(
						_portal.getAbsoluteURL(httpServletRequest, "/o/oauth2"),
						"/token?client_id=", oAuth2Application.getClientId(),
						"&client_secret=", oAuth2Application.getClientSecret())
				).put(
					"token_endpoint_auth_methods_supported",
					JSONUtil.putAll(
						"client_secret_basic", "client_secret_post", "none")
				).toString()
			);

			return;
		}

		if (httpServletRequest.getRequestURI(
			).endsWith(
				"/o/oauth2/register"
			)) {

			httpServletResponse.setHeader("Content-Type", "application/json");
			httpServletResponse.setHeader("Cache-Control", "no-cache");

			List<String> redirectURIsList =
				oAuth2Application.getRedirectURIsList();

			httpServletResponse.getWriter(
			).write(
				JSONUtil.put(
					"client_id", oAuth2Application.getClientId()
				).put(
					"client_secret", oAuth2Application.getClientSecret()
				).put(
					"client_secret_expires_at", 0
				).put(
					"redirect_uris", JSONUtil.putAll(redirectURIsList.toArray())
				).toString()
			);

			return;
		}

		processFilter(
			MCPServerFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

}