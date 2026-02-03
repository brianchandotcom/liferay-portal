/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthClientASLocalMetadataService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientASLocalMetadataService
 * @generated
 */
public class OAuthClientASLocalMetadataServiceWrapper
	implements OAuthClientASLocalMetadataService,
			   ServiceWrapper<OAuthClientASLocalMetadataService> {

	public OAuthClientASLocalMetadataServiceWrapper() {
		this(null);
	}

	public OAuthClientASLocalMetadataServiceWrapper(
		OAuthClientASLocalMetadataService oAuthClientASLocalMetadataService) {

		_oAuthClientASLocalMetadataService = oAuthClientASLocalMetadataService;
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			addOAuthClientASLocalMetadata(
				String metadataJSON, String wellKnownURISuffix)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.addOAuthClientASLocalMetadata(
			metadataJSON, wellKnownURISuffix);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			addOAuthClientASLocalMetadata(
				String authorizationEndpoint, String issuer, String jwksURI,
				boolean localWellKnownEnabled, String[] supportedGrantTypes,
				String[] supportedScopes, String[] supportedSubjectTypes,
				String tokenEndpoint, String userInfoEndpoint)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.addOAuthClientASLocalMetadata(
			authorizationEndpoint, issuer, jwksURI, localWellKnownEnabled,
			supportedGrantTypes, supportedScopes, supportedSubjectTypes,
			tokenEndpoint, userInfoEndpoint);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			deleteOAuthClientASLocalMetadata(long oAuthClientASLocalMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			deleteOAuthClientASLocalMetadata(String localWellKnownURI)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			deleteOAuthClientASLocalMetadata(localWellKnownURI);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			fetchOAuthClientASLocalMetadata(long oAuthClientASLocalMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			fetchOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			fetchOAuthClientASLocalMetadata(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			fetchOAuthClientASLocalMetadata(companyId, issuer);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata>
			getCompanyOAuthClientASLocalMetadata(long companyId) {

		return _oAuthClientASLocalMetadataService.
			getCompanyOAuthClientASLocalMetadata(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata>
			getCompanyOAuthClientASLocalMetadata(
				long companyId, int start, int end) {

		return _oAuthClientASLocalMetadataService.
			getCompanyOAuthClientASLocalMetadata(companyId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			getOAuthClientASLocalMetadata(
				long companyId, boolean localWellKnownEnabled,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth.client.persistence.model.
						OAuthClientASLocalMetadata> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.getOAuthClientASLocalMetadata(
			companyId, localWellKnownEnabled, orderByComparator);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			getOAuthClientASLocalMetadata(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.getOAuthClientASLocalMetadata(
			companyId, issuer);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			getOAuthClientASLocalMetadata(String localWellKnownURI)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.getOAuthClientASLocalMetadata(
			localWellKnownURI);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthClientASLocalMetadataService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata>
			getUserOAuthClientASLocalMetadata(long userId) {

		return _oAuthClientASLocalMetadataService.
			getUserOAuthClientASLocalMetadata(userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata>
			getUserOAuthClientASLocalMetadata(long userId, int start, int end) {

		return _oAuthClientASLocalMetadataService.
			getUserOAuthClientASLocalMetadata(userId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			updateOAuthClientASLocalMetadata(
				long oAuthClientASLocalMetadataId, String metadataJSON,
				String wellKnownURISuffix)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadataId, metadataJSON, wellKnownURISuffix);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			updateOAuthClientASLocalMetadata(
				long oAuthClientASLocalMetadataId, String authorizationEndpoint,
				String issuer, String jwksURI, boolean localWellKnownEnabled,
				String[] supportedGrantTypes, String[] supportedScopes,
				String[] supportedSubjectTypes, String tokenEndpoint,
				String userInfoEndpoint)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientASLocalMetadataService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadataId, authorizationEndpoint, issuer,
				jwksURI, localWellKnownEnabled, supportedGrantTypes,
				supportedScopes, supportedSubjectTypes, tokenEndpoint,
				userInfoEndpoint);
	}

	@Override
	public OAuthClientASLocalMetadataService getWrappedService() {
		return _oAuthClientASLocalMetadataService;
	}

	@Override
	public void setWrappedService(
		OAuthClientASLocalMetadataService oAuthClientASLocalMetadataService) {

		_oAuthClientASLocalMetadataService = oAuthClientASLocalMetadataService;
	}

	private OAuthClientASLocalMetadataService
		_oAuthClientASLocalMetadataService;

}