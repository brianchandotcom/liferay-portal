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

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getApplicationOAuth2Authorizations(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2AuthorizationService.getApplicationOAuth2Authorizations(oAuth2ApplicationId,
			start, end, orderByComparator);
	}

	@Override
	public int getApplicationOAuth2AuthorizationsCount(long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2AuthorizationService.getApplicationOAuth2AuthorizationsCount(oAuth2ApplicationId);
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
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getUserOAuth2Authorizations(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2AuthorizationService.getUserOAuth2Authorizations(start,
			end, orderByComparator);
	}

	@Override
	public int getUserOAuth2AuthorizationsCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2AuthorizationService.getUserOAuth2AuthorizationsCount();
	}

	@Override
	public void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_oAuth2AuthorizationService.revokeOAuth2Authorization(oAuth2AuthorizationId);
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