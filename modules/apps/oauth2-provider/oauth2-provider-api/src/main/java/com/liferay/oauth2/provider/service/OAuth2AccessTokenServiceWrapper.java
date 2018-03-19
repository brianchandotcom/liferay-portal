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
 * Provides a wrapper for {@link OAuth2AccessTokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenService
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenServiceWrapper implements OAuth2AccessTokenService,
	ServiceWrapper<OAuth2AccessTokenService> {
	public OAuth2AccessTokenServiceWrapper(
		OAuth2AccessTokenService oAuth2AccessTokenService) {
		_oAuth2AccessTokenService = oAuth2AccessTokenService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2AccessTokenService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuth2AccessTokenService getWrappedService() {
		return _oAuth2AccessTokenService;
	}

	@Override
	public void setWrappedService(
		OAuth2AccessTokenService oAuth2AccessTokenService) {
		_oAuth2AccessTokenService = oAuth2AccessTokenService;
	}

	private OAuth2AccessTokenService _oAuth2AccessTokenService;
}