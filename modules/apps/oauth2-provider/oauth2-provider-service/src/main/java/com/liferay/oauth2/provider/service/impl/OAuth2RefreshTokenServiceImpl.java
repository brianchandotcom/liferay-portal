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
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2RefreshToken;
import com.liferay.oauth2.provider.service.base.OAuth2RefreshTokenServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;

import java.util.Objects;

/**
 * The implementation of the o auth2 refresh token remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2RefreshTokenService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2RefreshTokenServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2RefreshTokenServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2RefreshTokenServiceImpl
	extends OAuth2RefreshTokenServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.oauth2.provider.service.OAuth2RefreshTokenServiceUtil} to access the o auth2 refresh token remote service.
	 */
	@Override
	public OAuth2RefreshToken deleteOAuth2RefreshToken(
			long oAuth2RefreshTokenId)
		throws PortalException {

		OAuth2RefreshToken oAuth2RefreshToken =
			oAuth2RefreshTokenLocalService.getOAuth2RefreshToken(
				oAuth2RefreshTokenId);

		if (!Objects.equals(getUserId(), oAuth2RefreshToken.getUserId())) {
			OAuth2Application oAuth2Application =
				oAuth2ApplicationService.getOAuth2Application(
					oAuth2RefreshToken.getOAuth2ApplicationId());

			oAuth2ApplicationService.check(
				oAuth2Application,
				OAuth2ProviderActionKeys.ACTION_REVOKE_TOKEN);
		}

		return oAuth2RefreshTokenLocalService.deleteOAuth2RefreshToken(
			oAuth2RefreshTokenId);
	}

}