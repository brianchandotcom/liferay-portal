/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.oauth2.provider.scope;

import com.liferay.mcp.server.internal.constants.MCPServerConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = ScopeChecker.class
)
public class MCPServerScopeChecker implements ScopeChecker {

	@Override
	public boolean checkScope(String scope) {

		// TODO Review the production readiness of this mechanism

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(
					_threadLocalScopeContext.getAccessToken());

		if (oAuth2Authorization != null) {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.fetchOAuth2Application(
					oAuth2Authorization.getOAuth2ApplicationId());

			if ((oAuth2Application != null) &&
				Objects.equals(
					oAuth2Application.getClientId(),
					MCPServerConstants.MCP_SERVER_OAUTH2_CLIENT_ID)) {

				return true;
			}
		}

		return _scopeChecker.checkScope(scope);
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference(
		target = "(!(component.name=com.liferay.mcp.server.internal.oauth2.provider.scope.MCPServerScopeChecker))"
	)
	private ScopeChecker _scopeChecker;

	@Reference(
		target = "(component.name=com.liferay.oauth2.provider.scope.internal.liferay.ThreadLocalScopeContext)"
	)
	private ScopeContext _threadLocalScopeContext;

}