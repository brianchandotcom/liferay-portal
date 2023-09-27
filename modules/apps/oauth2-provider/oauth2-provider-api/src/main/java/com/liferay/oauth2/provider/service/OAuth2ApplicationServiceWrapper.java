/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuth2ApplicationService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationService
 * @generated
 */
public class OAuth2ApplicationServiceWrapper
	implements OAuth2ApplicationService,
			   ServiceWrapper<OAuth2ApplicationService> {

	public OAuth2ApplicationServiceWrapper() {
		this(null);
	}

	public OAuth2ApplicationServiceWrapper(
		OAuth2ApplicationService oAuth2ApplicationService) {

		_oAuth2ApplicationService = oAuth2ApplicationService;
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			addOAuth2Application(
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientAuthenticationMethod, long clientCredentialUserId,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String jwks,
				String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList, boolean rememberDevice,
				java.util.List<String> scopeAliasesList,
				boolean trustedApplication,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.addOAuth2Application(
			allowedGrantTypesList, clientAuthenticationMethod,
			clientCredentialUserId, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, jwks, name,
			privacyPolicyURL, redirectURIsList, rememberDevice,
			scopeAliasesList, trustedApplication, serviceContext);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			deleteOAuth2Application(long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.deleteOAuth2Application(
			oAuth2ApplicationId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			fetchOAuth2Application(long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.fetchOAuth2Application(
			companyId, clientId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.getOAuth2Application(
			oAuth2ApplicationId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.getOAuth2Application(
			companyId, clientId);
	}

	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Application>
		getOAuth2Applications(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.oauth2.provider.model.OAuth2Application>
					orderByComparator) {

		return _oAuth2ApplicationService.getOAuth2Applications(
			companyId, start, end, orderByComparator);
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
	public String getOSGiServiceIdentifier() {
		return _oAuth2ApplicationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application updateIcon(
			long oAuth2ApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.updateIcon(
			oAuth2ApplicationId, inputStream);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			updateOAuth2Application(
				long oAuth2ApplicationId, long oAuth2ApplicationScopeAliasesId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientAuthenticationMethod, long clientCredentialUserId,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String jwks,
				String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList, boolean rememberDevice,
				boolean trustedApplication)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.updateOAuth2Application(
			oAuth2ApplicationId, oAuth2ApplicationScopeAliasesId,
			allowedGrantTypesList, clientAuthenticationMethod,
			clientCredentialUserId, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, jwks, name,
			privacyPolicyURL, redirectURIsList, rememberDevice,
			trustedApplication);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Application
			updateScopeAliases(
				long oAuth2ApplicationId,
				java.util.List<String> scopeAliasesList)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationService.updateScopeAliases(
			oAuth2ApplicationId, scopeAliasesList);
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