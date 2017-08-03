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

package com.liferay.sharepoint.repository.internal;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.sharepoint.oauth2.service.SharepointOAuth2TokenEntryLocalService;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = TokenStore.class)
public class SharepointOAuth2TokenStore implements TokenStore {

	@Override
	public void delete(String configurationId, long userId)
		throws PortalException {

		_sharepointOAuth2TokenEntryLocalService.
			deleteSharepointOAuth2TokenEntry(userId, configurationId);
	}

	@Override
	public Token get(String configurationId, long userId)
		throws PortalException {

		try {
			Token token = SharepointOAuth2Token.fromModel(
				_sharepointOAuth2TokenEntryLocalService.
					fetchSharepointOAuth2TokenEntry(userId, configurationId));

			if ((token == null) || !token.isExpired()) {
				return token;
			}

			SharepointOAuth2AuthorizationServer
				sharepointOAuth2AuthorizationServer =
					_sharepointOAuth2AuthorizationServerFactory.create(
						configurationId);

			Token refreshedToken =
				sharepointOAuth2AuthorizationServer.refreshAccessToken(token);

			save(configurationId, userId, refreshedToken);

			return refreshedToken;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public Token getFresh(String configurationId, long userId)
		throws PortalException {

		try {
			Token token = SharepointOAuth2Token.fromModel(
				_sharepointOAuth2TokenEntryLocalService.
					fetchSharepointOAuth2TokenEntry(userId, configurationId));

			SharepointOAuth2AuthorizationServer
				sharepointOAuth2AuthorizationServer =
					_sharepointOAuth2AuthorizationServerFactory.create(
						configurationId);

			Token refreshedToken =
				sharepointOAuth2AuthorizationServer.refreshAccessToken(token);

			save(configurationId, userId, refreshedToken);

			return refreshedToken;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void save(String configurationId, long userId, Token token)
		throws PortalException {

		_sharepointOAuth2TokenEntryLocalService.addSharepointOAuth2TokenEntry(
			userId, configurationId, token.getAccessToken(),
			token.getRefreshToken(), token.getExpirationDate());
	}

	@Reference
	private SharepointOAuth2AuthorizationServerFactory
		_sharepointOAuth2AuthorizationServerFactory;

	@Reference
	private SharepointOAuth2TokenEntryLocalService
		_sharepointOAuth2TokenEntryLocalService;

}