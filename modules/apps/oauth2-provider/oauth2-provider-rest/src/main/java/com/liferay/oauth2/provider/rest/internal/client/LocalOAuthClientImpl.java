/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.client;

import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.cxf.rs.security.oauth2.common.AccessTokenRegistration;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 * @author Raymond Augé
 */
@Component(service = LocalOAuthClient.class)
public class LocalOAuthClientImpl implements LocalOAuthClient {

	@Override
	public void consumeAccessToken(
		Consumer<String> accessTokenConsumer,
		OAuth2Application oAuth2Application, long userId) {

		ServerAccessToken serverAccessToken = _getServerAccessToken(
			oAuth2Application, userId);

		accessTokenConsumer.accept(serverAccessToken.getTokenKey());
	}

	@Override
	public String requestTokens(
		OAuth2Application oAuth2Application, long userId) {

		try {
			ServerAccessToken serverAccessToken = _getServerAccessToken(
				oAuth2Application, userId);

			return JSONUtil.put(
				"access_token", serverAccessToken.getTokenKey()
			).put(
				"expires_in", serverAccessToken.getExpiresIn()
			).put(
				"refresh_token", serverAccessToken.getRefreshToken()
			).put(
				"scope",
				OAuthUtils.convertPermissionsToScope(
					serverAccessToken.getScopes())
			).put(
				"token_type", serverAccessToken.getTokenType()
			).toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private ServerAccessToken _getServerAccessToken(
		OAuth2Application oAuth2Application, long userId) {

		AccessTokenRegistration accessTokenRegistration =
			new AccessTokenRegistration();

		Client client = _liferayOAuthDataProvider.getClient(oAuth2Application);

		accessTokenRegistration.setApprovedScope(client.getRegisteredScopes());

		accessTokenRegistration.setAudiences(
			Collections.singletonList(oAuth2Application.getHomePageURL()));
		accessTokenRegistration.setClient(client);

		List<GrantType> allowedGrantTypesList =
			oAuth2Application.getAllowedGrantTypesList();

		accessTokenRegistration.setGrantType(
			String.valueOf(allowedGrantTypesList.get(0)));

		accessTokenRegistration.setRequestedScope(client.getRegisteredScopes());
		accessTokenRegistration.setSubject(
			_liferayOAuthDataProvider.getUserSubject(userId));

		return _liferayOAuthDataProvider.createAccessToken(
			accessTokenRegistration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalOAuthClientImpl.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

}