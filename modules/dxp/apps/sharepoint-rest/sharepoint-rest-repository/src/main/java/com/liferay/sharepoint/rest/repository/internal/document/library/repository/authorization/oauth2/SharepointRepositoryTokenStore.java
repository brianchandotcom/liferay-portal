/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (IOException ioException) {
			throw new SystemException(ioException);
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
		catch (IOException ioException) {
			throw new SystemException(ioException);
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