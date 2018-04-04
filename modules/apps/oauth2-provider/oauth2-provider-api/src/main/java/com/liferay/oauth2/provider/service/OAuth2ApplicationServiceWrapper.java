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
 * Provides a wrapper for {@link OAuth2ApplicationService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationService
 * @generated
 */
@ProviderType
public class OAuth2ApplicationServiceWrapper implements OAuth2ApplicationService,
	ServiceWrapper<OAuth2ApplicationService> {
	public OAuth2ApplicationServiceWrapper(
		OAuth2ApplicationService oAuth2ApplicationService) {
		_oAuth2ApplicationService = oAuth2ApplicationService;
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application addOAuth2Application(
		java.util.List<com.liferay.oauth2.provider.constants.GrantType> allowedGrantTypesList,
		java.lang.String clientId, int clientProfile,
		java.lang.String clientSecret, java.lang.String description,
		java.util.List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		java.util.List<java.lang.String> redirectURIsList,
		java.util.List<java.lang.String> scopeAliasesList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.addOAuth2Application(allowedGrantTypesList,
			clientId, clientProfile, clientSecret, description, featuresList,
			homePageURL, iconFileEntryId, name, privacyPolicyURL,
			redirectURIsList, scopeAliasesList, serviceContext);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application deleteOAuth2Application(
		long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.deleteOAuth2Application(oAuth2ApplicationId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application fetchOAuth2Application(
		long companyId, java.lang.String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.fetchOAuth2Application(companyId,
			clientId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application getOAuth2Application(
		long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.getOAuth2Application(oAuth2ApplicationId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application getOAuth2Application(
		long companyId, java.lang.String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.getOAuth2Application(companyId,
			clientId);
	}

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Application> getOAuth2Applications(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Application> orderByComparator) {
		return _oAuth2ApplicationService.getOAuth2Applications(companyId,
			start, end, orderByComparator);
	}

	@Override
	public int getOAuth2ApplicationsCount(long companyId) {
		return _oAuth2ApplicationService.getOAuth2ApplicationsCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2ApplicationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application updateIcon(
		long oAuth2ApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.updateIcon(oAuth2ApplicationId,
			inputStream);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application updateOAuth2Application(
		long oAuth2ApplicationId,
		java.util.List<com.liferay.oauth2.provider.constants.GrantType> allowedGrantTypesList,
		java.lang.String clientId, int clientProfile,
		java.lang.String clientSecret, java.lang.String description,
		java.util.List<java.lang.String> featuresList,
		java.lang.String homePageURL, long iconFileEntryId,
		java.lang.String name, java.lang.String privacyPolicyURL,
		java.util.List<java.lang.String> redirectURIsList,
		long auth2ApplicationScopeAliasesId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.updateOAuth2Application(oAuth2ApplicationId,
			allowedGrantTypesList, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, name,
			privacyPolicyURL, redirectURIsList, auth2ApplicationScopeAliasesId,
			serviceContext);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application updateScopeAliases(
		long oAuth2ApplicationId,
		java.util.List<java.lang.String> scopeAliasesList)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2ApplicationService.updateScopeAliases(oAuth2ApplicationId,
			scopeAliasesList);
	}

	@Override
	public OAuth2ApplicationService getWrappedService() {
		return _oAuth2ApplicationService;
	}

	@Override
	public void setWrappedService(
		OAuth2ApplicationService oAuth2ApplicationService) {
		_oAuth2ApplicationService = oAuth2ApplicationService;
	}

	private OAuth2ApplicationService _oAuth2ApplicationService;
}