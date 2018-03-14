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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2AccessTokenException;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.service.base.OAuth2AccessTokenLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collection;

/**
 * The implementation of the o auth2 access token local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2AccessTokenLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2AccessTokenLocalServiceUtil
 */
public class OAuth2AccessTokenLocalServiceImpl
	extends OAuth2AccessTokenLocalServiceBaseImpl {

	@Override
	public OAuth2AccessToken createOAuth2AccessToken(String tokenContent) {
		OAuth2AccessToken oAuth2AccessToken = createOAuth2AccessToken(
			counterLocalService.increment());

		oAuth2AccessToken.setTokenContent(tokenContent);

		return updateOAuth2AccessToken(oAuth2AccessToken);
	}

	@Override
	public OAuth2AccessToken deleteOAuth2AccessToken(long oAuth2AccessTokenId)
		throws PortalException {

		Collection<OAuth2ScopeGrant> grants =
			oAuth2ScopeGrantLocalService.findByToken(oAuth2AccessTokenId);

		for (OAuth2ScopeGrant grant : grants) {
			oAuth2ScopeGrantLocalService.deleteOAuth2ScopeGrant(grant);
		}

		return super.deleteOAuth2AccessToken(oAuth2AccessTokenId);
	}

	@Override
	public OAuth2AccessToken fetchByContent(String tokenContent) {
		return oAuth2AccessTokenPersistence.fetchByTokenContent(tokenContent);
	}

	@Override
	public Collection<OAuth2AccessToken> findByApplicationId(
		long applicationId, int start, int end,
		OrderByComparator<OAuth2AccessToken> orderByComparator) {

		return oAuth2AccessTokenPersistence.findByA(
			applicationId, start, end, orderByComparator);
	}

	@Override
	public OAuth2AccessToken findByContent(String tokenContent)
		throws NoSuchOAuth2AccessTokenException {

		return oAuth2AccessTokenPersistence.findByTokenContent(tokenContent);
	}

	@Override
	public Collection<OAuth2AccessToken> findByRefreshToken(
		long oAuth2RefreshTokenId) {

		return oAuth2AccessTokenPersistence.findByRefreshToken(
			oAuth2RefreshTokenId);
	}

}