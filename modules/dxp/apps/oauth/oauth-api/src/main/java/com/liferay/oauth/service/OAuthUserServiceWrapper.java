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

package com.liferay.oauth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthUserService}.
 *
 * @author Ivica Cardic
 * @see OAuthUserService
 * @generated
 */
@ProviderType
public class OAuthUserServiceWrapper implements OAuthUserService,
	ServiceWrapper<OAuthUserService> {
	public OAuthUserServiceWrapper(OAuthUserService oAuthUserService) {
		_oAuthUserService = oAuthUserService;
	}

	@Override
	public com.liferay.oauth.model.OAuthUser addOAuthUser(String consumerKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserService.addOAuthUser(consumerKey, serviceContext);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserService.deleteOAuthUser(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthUserService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuthUserService getWrappedService() {
		return _oAuthUserService;
	}

	@Override
	public void setWrappedService(OAuthUserService oAuthUserService) {
		_oAuthUserService = oAuthUserService;
	}

	private OAuthUserService _oAuthUserService;
}