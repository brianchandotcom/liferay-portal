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
 * Provides a wrapper for {@link OAuth2AuthorizationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationLocalService
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationLocalServiceWrapper
	implements OAuth2AuthorizationLocalService,
		ServiceWrapper<OAuth2AuthorizationLocalService> {
	public OAuth2AuthorizationLocalServiceWrapper(
		OAuth2AuthorizationLocalService oAuth2AuthorizationLocalService) {
		_oAuth2AuthorizationLocalService = oAuth2AuthorizationLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2AuthorizationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuth2AuthorizationLocalService getWrappedService() {
		return _oAuth2AuthorizationLocalService;
	}

	@Override
	public void setWrappedService(
		OAuth2AuthorizationLocalService oAuth2AuthorizationLocalService) {
		_oAuth2AuthorizationLocalService = oAuth2AuthorizationLocalService;
	}

	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;
}