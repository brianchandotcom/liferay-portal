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
 * Provides a wrapper for {@link OAuth2AuthorizationService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationService
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationServiceWrapper
	implements OAuth2AuthorizationService,
		ServiceWrapper<OAuth2AuthorizationService> {
	public OAuth2AuthorizationServiceWrapper(
		OAuth2AuthorizationService oAuth2AuthorizationService) {
		_oAuth2AuthorizationService = oAuth2AuthorizationService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2AuthorizationService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuth2AuthorizationService getWrappedService() {
		return _oAuth2AuthorizationService;
	}

	@Override
	public void setWrappedService(
		OAuth2AuthorizationService oAuth2AuthorizationService) {
		_oAuth2AuthorizationService = oAuth2AuthorizationService;
	}

	private OAuth2AuthorizationService _oAuth2AuthorizationService;
}