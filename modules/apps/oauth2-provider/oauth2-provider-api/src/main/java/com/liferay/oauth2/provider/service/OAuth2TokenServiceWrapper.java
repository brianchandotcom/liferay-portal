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

package com.liferay.oauth2.provider.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuth2TokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenService
 * @generated
 */
@ProviderType
public class OAuth2TokenServiceWrapper implements OAuth2TokenService,
	ServiceWrapper<OAuth2TokenService> {
	public OAuth2TokenServiceWrapper(OAuth2TokenService oAuth2TokenService) {
		_oAuth2TokenService = oAuth2TokenService;
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link OAuth2TokenServiceUtil} to access the o auth2 token remote service.
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2TokenService.deleteOAuth2Token(oAuth2TokenId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2TokenService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuth2TokenService getWrappedService() {
		return _oAuth2TokenService;
	}

	@Override
	public void setWrappedService(OAuth2TokenService oAuth2TokenService) {
		_oAuth2TokenService = oAuth2TokenService;
	}

	private OAuth2TokenService _oAuth2TokenService;
}