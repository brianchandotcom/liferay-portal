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
 * Provides a wrapper for {@link OAuth2RefreshTokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2RefreshTokenService
 * @generated
 */
@ProviderType
public class OAuth2RefreshTokenServiceWrapper
	implements OAuth2RefreshTokenService,
		ServiceWrapper<OAuth2RefreshTokenService> {
	public OAuth2RefreshTokenServiceWrapper(
		OAuth2RefreshTokenService oAuth2RefreshTokenService) {
		_oAuth2RefreshTokenService = oAuth2RefreshTokenService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2RefreshTokenService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuth2RefreshTokenService getWrappedService() {
		return _oAuth2RefreshTokenService;
	}

	@Override
	public void setWrappedService(
		OAuth2RefreshTokenService oAuth2RefreshTokenService) {
		_oAuth2RefreshTokenService = oAuth2RefreshTokenService;
	}

	private OAuth2RefreshTokenService _oAuth2RefreshTokenService;
}