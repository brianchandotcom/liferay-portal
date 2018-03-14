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
import com.liferay.oauth2.provider.service.base.OAuth2AccessTokenServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;

import java.util.Objects;

/**
 * The implementation of the o auth2 access token remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2AccessTokenService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2AccessTokenServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2AccessTokenServiceImpl
	extends OAuth2AccessTokenServiceBaseImpl {

	@Override
	public OAuth2AccessToken deleteOAuth2Token(long oAuth2AccessTokenId)
		throws PortalException {

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AccessTokenLocalService.getOAuth2AccessToken(
				oAuth2AccessTokenId);

		if (!Objects.equals(getUserId(), oAuth2AccessToken.getUserId())) {
			OAuth2Application oAuth2Application =
				oAuth2ApplicationService.getOAuth2Application(
					oAuth2AccessToken.getOAuth2ApplicationId());

			oAuth2ApplicationService.check(
				oAuth2Application,
				OAuth2ProviderActionKeys.ACTION_REVOKE_TOKEN);
		}

		return oAuth2AccessTokenLocalService.deleteOAuth2AccessToken(
			oAuth2AccessTokenId);
	}

}