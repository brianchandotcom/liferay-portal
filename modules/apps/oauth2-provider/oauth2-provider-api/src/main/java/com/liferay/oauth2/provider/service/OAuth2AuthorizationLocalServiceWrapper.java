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

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getOAuth2Authorizations(
		long companyId, long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator) {
		return _oAuth2AuthorizationLocalService.getOAuth2Authorizations(companyId,
			applicationId, start, end, orderByComparator);
	}

	@Override
	public int getOAuth2AuthorizationsCount(long companyId, long applicationId) {
		return _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount(companyId,
			applicationId);
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
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getUserOAuth2Authorizations(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator) {
		return _oAuth2AuthorizationLocalService.getUserOAuth2Authorizations(companyId,
			userId, start, end, orderByComparator);
	}

	@Override
	public int getUserOAuth2AuthorizationsCount(long companyId, long userId) {
		return _oAuth2AuthorizationLocalService.getUserOAuth2AuthorizationsCount(companyId,
			userId);
	}

	@Override
	public boolean revokeOAuth2Authorization(long oAuth2AccessTokenId,
		long oAuth2RefreshTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2AuthorizationLocalService.revokeOAuth2Authorization(oAuth2AccessTokenId,
			oAuth2RefreshTokenId);
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