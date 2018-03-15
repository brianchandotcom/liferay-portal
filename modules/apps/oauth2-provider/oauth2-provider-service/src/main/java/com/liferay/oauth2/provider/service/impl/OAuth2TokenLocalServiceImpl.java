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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.model.OAuth2Token;
import com.liferay.oauth2.provider.service.base.OAuth2TokenLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collection;

/**
 * The implementation of the o auth2 token local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2TokenLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2TokenLocalServiceUtil
 */
public class OAuth2TokenLocalServiceImpl
	extends OAuth2TokenLocalServiceBaseImpl {

	@Override
	public OAuth2Token createOAuth2Token(String tokenContent) {
		OAuth2Token oAuth2Token = createOAuth2Token(
			counterLocalService.increment());

		oAuth2Token.setOAuth2TokenContent(tokenContent);

		return updateOAuth2Token(oAuth2Token);
	}

	@Override
	public OAuth2Token deleteOAuth2Token(long oAuth2TokenId)
		throws PortalException {

		Collection<OAuth2ScopeGrant> grants =
			oAuth2ScopeGrantLocalService.findByToken(oAuth2TokenId);

		for (OAuth2ScopeGrant grant : grants) {
			oAuth2ScopeGrantLocalService.deleteOAuth2ScopeGrant(grant);
		}

		return super.deleteOAuth2Token(oAuth2TokenId);
	}

	@Override
	public OAuth2Token fetchByContent(String oAuth2TokenContent) {
		return oAuth2TokenPersistence.fetchByContent(oAuth2TokenContent);
	}

	@Override
	public Collection<OAuth2Token> findByApplicationId(
		long applicationId, int start, int end,
		OrderByComparator<OAuth2Token> orderByComparator) {

		return oAuth2TokenPersistence.findByA(
			applicationId, start, end, orderByComparator);
	}

	@Override
	public OAuth2Token findByContent(String oAuth2TokenContent)
		throws NoSuchOAuth2TokenException {

		return oAuth2TokenPersistence.findByContent(oAuth2TokenContent);
	}

	@Override
	public Collection<OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId) {

		return oAuth2TokenPersistence.findByRefreshToken(oAuth2RefreshTokenId);
	}

}