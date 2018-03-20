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

package com.liferay.oauth2.provider.service.impl;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2RefreshTokenException;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;
import com.liferay.oauth2.provider.model.OAuth2RefreshToken;
import com.liferay.oauth2.provider.service.base.OAuth2RefreshTokenLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collection;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2RefreshTokenLocalServiceImpl
	extends OAuth2RefreshTokenLocalServiceBaseImpl {

	@Override
	public OAuth2RefreshToken createOAuth2RefreshToken(String tokenContent) {
		OAuth2RefreshToken oAuth2RefreshToken = createOAuth2RefreshToken(
			counterLocalService.increment());

		oAuth2RefreshToken.setTokenContent(tokenContent);

		return updateOAuth2RefreshToken(oAuth2RefreshToken);
	}

	@Override
	public OAuth2RefreshToken deleteOAuth2RefreshToken(
			long oAuth2RefreshTokenId)
		throws PortalException {

		Collection<OAuth2AccessToken> oAuth2AccessTokens =
			oAuth2AccessTokenLocalService.getOAuth2AccessTokens(
				oAuth2RefreshTokenId);

		for (OAuth2AccessToken auth2AccessToken : oAuth2AccessTokens) {
			auth2AccessToken.setOAuth2RefreshTokenId(0);

			oAuth2AccessTokenLocalService.updateOAuth2AccessToken(
				auth2AccessToken);
		}

		return super.deleteOAuth2RefreshToken(oAuth2RefreshTokenId);
	}

	public OAuth2RefreshToken fetchByContent(String tokenContent) {
		return oAuth2RefreshTokenPersistence.fetchByTokenContent(tokenContent);
	}

	@Override
	public Collection<OAuth2RefreshToken> findByApplication(
		long applicationId, int start, int end,
		OrderByComparator<OAuth2RefreshToken> orderByComparator) {

		return oAuth2RefreshTokenPersistence.findByOAuth2ApplicationId(
			applicationId, start, end, orderByComparator);
	}

	@Override
	public OAuth2RefreshToken findByContent(String tokenContent)
		throws NoSuchOAuth2RefreshTokenException {

		return oAuth2RefreshTokenPersistence.findByTokenContent(tokenContent);
	}

}