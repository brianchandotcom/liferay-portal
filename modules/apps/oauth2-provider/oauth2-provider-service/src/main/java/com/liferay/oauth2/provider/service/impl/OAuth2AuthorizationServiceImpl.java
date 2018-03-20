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

import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.OAuth2RefreshToken;
import com.liferay.oauth2.provider.service.base.OAuth2AuthorizationServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2AuthorizationServiceImpl
	extends OAuth2AuthorizationServiceBaseImpl {

	public List<OAuth2Authorization> getUserOAuth2Authorizations(
			int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		User user = getUser();

		return oAuth2AuthorizationLocalService.getUserOAuth2Authorizations(
			user.getCompanyId(), user.getUserId(), start, end,
			orderByComparator);
	}

	public int getUserOAuth2AuthorizationsCount() throws PortalException {
		User user = getUser();

		return oAuth2AuthorizationLocalService.getUserOAuth2AuthorizationsCount(
			user.getCompanyId(), user.getUserId());
	}

	@Override
	public boolean revokeOAuth2Authorization(
			long oAuth2AccessTokenId, long oAuth2RefreshTokenId)
		throws PortalException {

		long oAuth2ApplicationId = 0;

		boolean owner = true;

		if (oAuth2RefreshTokenId != 0) {
			OAuth2RefreshToken oAuth2RefreshToken =
				oAuth2RefreshTokenLocalService.getOAuth2RefreshToken(
					oAuth2RefreshTokenId);

			oAuth2ApplicationId = oAuth2RefreshToken.getOAuth2ApplicationId();

			if (!Objects.equals(getUserId(), oAuth2RefreshToken.getUserId())) {
				owner = false;
			}
		}
		else if (oAuth2AccessTokenId != 0) {
			OAuth2AccessToken oAuth2AccessToken =
				oAuth2AccessTokenLocalService.getOAuth2AccessToken(
					oAuth2AccessTokenId);

			oAuth2ApplicationId = oAuth2AccessToken.getOAuth2ApplicationId();

			if (!Objects.equals(getUserId(), oAuth2AccessToken.getUserId())) {
				owner = false;
			}
		}
		else {
			return false;
		}

		if (!owner) {
			OAuth2Application oAuth2Application =
				oAuth2ApplicationService.getOAuth2Application(
					oAuth2ApplicationId);

			oAuth2ApplicationService.check(
				oAuth2Application,
				OAuth2ProviderActionKeys.ACTION_REVOKE_TOKEN);
		}

		return oAuth2AuthorizationLocalService.revokeOAuth2Authorization(
			oAuth2AccessTokenId, oAuth2RefreshTokenId);
	}

}