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
 * The implementation of the o auth2 authorization remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2AuthorizationService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2AuthorizationServiceImpl
	extends OAuth2AuthorizationServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil} to access the o auth2 authorization remote service.
	 */
	public int countByUserId() throws PortalException {
		User user = getUser();

		return oAuth2AuthorizationLocalService.countByUserId(
			user.getCompanyId(), user.getUserId());
	}

	public List<OAuth2Authorization> findByUserId(
			int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		User user = getUser();

		return oAuth2AuthorizationLocalService.findByUserId(
			user.getCompanyId(), user.getUserId(), start, end,
			orderByComparator);
	}

	@Override
	public boolean revokeAuthorization(
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

		return oAuth2AuthorizationLocalService.revokeAuthorization(
			oAuth2AccessTokenId, oAuth2RefreshTokenId);
	}

}