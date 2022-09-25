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

import org.apache.cxf.rs.security.oauth2.common.AccessTokenRegistration;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = LocalOAuthClient.class)
public class LocalOAuthClientImpl implements LocalOAuthClient {

	@Override
	public String requestTokens(
		long userId, OAuth2Application oAuth2Application) {

		Client client = _liferayOAuthDataProvider.getClient(oAuth2Application);

		UserSubject userSubject = _liferayOAuthDataProvider.getUserSubject(
			userId);
		List<GrantType> allowedGrantTypes =
			oAuth2Application.getAllowedGrantTypesList();

		AccessTokenRegistration reg = new AccessTokenRegistration();

		reg.setClient(client);
		reg.setGrantType(String.valueOf(allowedGrantTypes.get(0)));
		reg.setSubject(userSubject);
		reg.setRequestedScope(client.getRegisteredScopes());
		reg.setApprovedScope(client.getRegisteredScopes());
		reg.setAudiences(
			Collections.singletonList(oAuth2Application.getHomePageURL()));

		try {
			ServerAccessToken serverAccessToken =
				_liferayOAuthDataProvider.createAccessToken(reg);

			return _toJSONString(serverAccessToken);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private String _toJSONString(ServerAccessToken serverAccessToken) {
		return JSONUtil.put(
			"access_token", serverAccessToken.getTokenKey()
		).put(
			"expires_in", serverAccessToken.getExpiresIn()
		).put(
			"refresh_token", serverAccessToken.getRefreshToken()
		).put(
			"scope",
			OAuthUtils.convertPermissionsToScope(serverAccessToken.getScopes())
		).put(
			"token_type", serverAccessToken.getTokenType()
		).toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalOAuthClientImpl.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

}