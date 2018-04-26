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
 * Provides a wrapper for {@link OAuthApplicationService}.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationService
 * @generated
 */
@ProviderType
public class OAuthApplicationServiceWrapper implements OAuthApplicationService,
	ServiceWrapper<OAuthApplicationService> {
	public OAuthApplicationServiceWrapper(
		OAuthApplicationService oAuthApplicationService) {
		_oAuthApplicationService = oAuthApplicationService;
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication addOAuthApplication(
		String name, String description, int accessLevel,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.addOAuthApplication(name, description,
			accessLevel, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	@Override
	public void deleteLogo(long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_oAuthApplicationService.deleteLogo(oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication deleteOAuthApplication(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.deleteOAuthApplication(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthApplicationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateLogo(
		long oAuthApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.updateLogo(oAuthApplicationId,
			inputStream);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateOAuthApplication(
		long oAuthApplicationId, String name, String description,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.updateOAuthApplication(oAuthApplicationId,
			name, description, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	@Override
	public OAuthApplicationService getWrappedService() {
		return _oAuthApplicationService;
	}

	@Override
	public void setWrappedService(
		OAuthApplicationService oAuthApplicationService) {
		_oAuthApplicationService = oAuthApplicationService;
	}

	private OAuthApplicationService _oAuthApplicationService;
}