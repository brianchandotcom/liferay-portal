/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.servlet.filter;

import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

		// TODO Add support for https://datatracker.ietf.org/doc/html/rfc8414

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

		// TODO Add support for https://www.rfc-editor.org/rfc/rfc7591

		if (httpServletRequest.getRequestURI(
			).endsWith(
				"/o/oauth2/register"
			)) {

			httpServletResponse.setHeader("Content-Type", "application/json");
			httpServletResponse.setHeader("Cache-Control", "no-cache");

			Set<String> redirectURIsSet = new HashSet<>(
				oAuth2Application.getRedirectURIsList());

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(httpServletRequest.getInputStream()));

			JSONArray redirectURIsJSONArray = jsonObject.getJSONArray(
				"redirect_uris");

			for (int i = 0; i < redirectURIsJSONArray.length(); i++) {
				redirectURIsSet.add(redirectURIsJSONArray.getString(i));
			}

			oAuth2Application.setRedirectURIsList(
				new ArrayList<>(redirectURIsSet));

			oAuth2Application =
				_oAuth2ApplicationLocalService.updateOAuth2Application(
					oAuth2Application);

			httpServletResponse.getWriter(
			).write(
				JSONUtil.put(
					"client_id", oAuth2Application.getClientId()
				).put(
					"client_secret", oAuth2Application.getClientSecret()
				).put(
					"client_secret_expires_at", 0
				).put(
					"redirect_uris", JSONUtil.putAll(redirectURIsSet.toArray())
				).toString()
			);

			return;
		}

		processFilter(
			MCPServerFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

}