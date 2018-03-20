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

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.base.OAuth2AuthorizationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2AuthorizationLocalServiceImpl
	extends OAuth2AuthorizationLocalServiceBaseImpl {

	@Override
	public List<OAuth2Authorization> getOAuth2Authorizations(
		long companyId, long applicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return oAuth2AuthorizationFinder.findByApplicationId(
			companyId, applicationId, start, end, orderByComparator);
	}

	@Override
	public int getOAuth2AuthorizationsCount(
		long companyId, long applicationId) {

		return oAuth2AuthorizationFinder.countByApplicationId(
			companyId, applicationId);
	}

	@Override
	public List<OAuth2Authorization> getUserOAuth2Authorizations(
		long companyId, long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return oAuth2AuthorizationFinder.findByUserId(
			companyId, userId, start, end, orderByComparator);
	}

	@Override
	public int getUserOAuth2AuthorizationsCount(long companyId, long userId) {
		return oAuth2AuthorizationFinder.countByUserId(companyId, userId);
	}

	@Override
	public boolean revokeOAuth2Authorization(
			long oAuth2AccessTokenId, long oAuth2RefreshTokenId)
		throws PortalException {

		if (oAuth2AccessTokenId > 0) {
			oAuth2AccessTokenLocalService.deleteOAuth2AccessToken(
				oAuth2AccessTokenId);
		}

		if (oAuth2RefreshTokenId > 0) {
			oAuth2RefreshTokenLocalService.deleteOAuth2RefreshToken(
				oAuth2RefreshTokenId);
		}

		return true;
	}

}