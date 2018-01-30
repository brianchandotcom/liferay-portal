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

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.sharepoint.rest.oauth2.service.SharepointOAuth2TokenEntryLocalService;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = TokenStore.class)
public class SharepointRepositoryTokenStore implements TokenStore {

	@Override
	public void delete(String configurationPid, long userId)
		throws PortalException {

		_sharepointOAuth2TokenEntryLocalService.
			deleteSharepointOAuth2TokenEntry(userId, configurationPid);
	}

	@Override
	public Token get(String configurationPid, long userId)
		throws PortalException {

		try {
			Token token = SharepointRepositoryToken.newInstance(
				_sharepointOAuth2TokenEntryLocalService.
					fetchSharepointOAuth2TokenEntry(userId, configurationPid));

			if ((token == null) || !token.isExpired()) {
				return token;
			}

			SharepointRepositoryTokenBroker sharepointRepositoryTokenBroker =
				_sharepointRepositoryTokenBrokerFactory.create(
					configurationPid);

			Token freshToken =
				sharepointRepositoryTokenBroker.refreshAccessToken(token);

			save(configurationPid, userId, freshToken);

			return freshToken;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public Token getFresh(String configurationPid, long userId)
		throws PortalException {

		try {
			Token token = SharepointRepositoryToken.newInstance(
				_sharepointOAuth2TokenEntryLocalService.
					fetchSharepointOAuth2TokenEntry(userId, configurationPid));

			SharepointRepositoryTokenBroker sharepointRepositoryTokenBroker =
				_sharepointRepositoryTokenBrokerFactory.create(
					configurationPid);

			Token freshToken =
				sharepointRepositoryTokenBroker.refreshAccessToken(token);

			save(configurationPid, userId, freshToken);

			return freshToken;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void save(String configurationPid, long userId, Token token)
		throws PortalException {

		_sharepointOAuth2TokenEntryLocalService.addSharepointOAuth2TokenEntry(
			userId, configurationPid, token.getAccessToken(),
			token.getRefreshToken(), token.getExpirationDate());
	}

	@Reference
	private SharepointOAuth2TokenEntryLocalService
		_sharepointOAuth2TokenEntryLocalService;

	@Reference
	private SharepointRepositoryTokenBrokerFactory
		_sharepointRepositoryTokenBrokerFactory;

}