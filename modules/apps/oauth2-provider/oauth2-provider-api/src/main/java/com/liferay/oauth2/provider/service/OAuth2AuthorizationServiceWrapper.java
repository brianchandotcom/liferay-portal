/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuth2AuthorizationService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationService
 * @generated
 */
public class OAuth2AuthorizationServiceWrapper
	implements OAuth2AuthorizationService,
			   ServiceWrapper<OAuth2AuthorizationService> {

	public OAuth2AuthorizationServiceWrapper() {
		this(null);
	}

	public OAuth2AuthorizationServiceWrapper(
		OAuth2AuthorizationService oAuth2AuthorizationService) {

		_oAuth2AuthorizationService = oAuth2AuthorizationService;
	}

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization>
			getApplicationOAuth2Authorizations(
				long oAuth2ApplicationId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2Authorization>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2AuthorizationService.getApplicationOAuth2Authorizations(
			oAuth2ApplicationId, start, end, orderByComparator);
	}

	@Override
	public int getApplicationOAuth2AuthorizationsCount(long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2AuthorizationService.
			getApplicationOAuth2AuthorizationsCount(oAuth2ApplicationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuth2AuthorizationService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization>
			getUserOAuth2Authorizations(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2Authorization>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2AuthorizationService.getUserOAuth2Authorizations(
			start, end, orderByComparator);
	}

	@Override
	public int getUserOAuth2AuthorizationsCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2AuthorizationService.getUserOAuth2AuthorizationsCount();
	}

	@Override
	public void revokeAllOAuth2Authorizations(long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_oAuth2AuthorizationService.revokeAllOAuth2Authorizations(
			oAuth2ApplicationId);
	}

	@Override
	public void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_oAuth2AuthorizationService.revokeOAuth2Authorization(
			oAuth2AuthorizationId);
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