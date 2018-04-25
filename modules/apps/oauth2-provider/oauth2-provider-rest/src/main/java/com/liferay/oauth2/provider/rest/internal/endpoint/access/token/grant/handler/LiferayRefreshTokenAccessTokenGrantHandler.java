/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.grant.handler;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.refresh.RefreshTokenGrantHandler;
import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;
import org.apache.cxf.rs.security.oauth2.tokens.refresh.RefreshToken;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	service = AccessTokenGrantHandler.class
)
public class LiferayRefreshTokenAccessTokenGrantHandler
	extends BaseAccessTokenGrantHandler {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_refreshTokenGrantHandler = new RefreshTokenGrantHandler();

		_refreshTokenGrantHandler.setDataProvider(_liferayOAuthDataProvider);

		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);
	}

	@Override
	protected AccessTokenGrantHandler getAccessTokenGrantHandler() {
		return _refreshTokenGrantHandler;
	}

	@Override
	protected boolean hasPermission(
		Client client, MultivaluedMap<String, String> params) {

		String refreshTokenString = params.getFirst("refresh_token");

		if (refreshTokenString == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No refresh_token parameter was provided");
			}

			return false;
		}

		RefreshToken refreshToken = _liferayOAuthDataProvider.getRefreshToken(
			refreshTokenString);

		if (refreshToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No refresh token found for " + refreshTokenString);
			}

			return false;
		}

		if (!clientsMatch(client, refreshToken.getClient())) {

			// TODO: Inform the audit service that the user is trying to
			// refresh a token belonging to a client other than the
			// authenticated client

			_liferayOAuthDataProvider.doRevokeRefreshToken(refreshToken);

			for (String accessToken : refreshToken.getAccessTokens()) {
				ServerAccessToken serverAccessToken =
					_liferayOAuthDataProvider.getAccessToken(accessToken);

				_liferayOAuthDataProvider.doRevokeAccessToken(
					serverAccessToken);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Authenticated client does not match the refresh token's " +
						"client");
			}

			return false;
		}

		UserSubject userSubject = refreshToken.getSubject();

		OAuth2Application oAuth2Application =
			_liferayOAuthDataProvider.resolveOAuth2Application(
				refreshToken.getClient());

		return hasCreateTokenPermission(
			GetterUtil.getLong(userSubject.getId()), oAuth2Application);
	}

	@Override
	protected boolean isGrantHandlerEnabled() {
		return _oAuth2ProviderConfiguration.allowRefreshTokenGrant();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayRefreshTokenAccessTokenGrantHandler.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;
	private RefreshTokenGrantHandler _refreshTokenGrantHandler;

}