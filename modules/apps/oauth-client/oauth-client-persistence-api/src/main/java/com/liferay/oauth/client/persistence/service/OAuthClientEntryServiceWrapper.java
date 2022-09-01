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

package com.liferay.oauth.client.persistence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthClientEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntryService
 * @generated
 */
public class OAuthClientEntryServiceWrapper
	implements OAuthClientEntryService,
			   ServiceWrapper<OAuthClientEntryService> {

	public OAuthClientEntryServiceWrapper() {
		this(null);
	}

	public OAuthClientEntryServiceWrapper(
		OAuthClientEntryService oAuthClientEntryService) {

		_oAuthClientEntryService = oAuthClientEntryService;
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			addOAuthClientEntry(
				long userId, String authRequestParametersJSON,
				String authServerWellKnownURI, String infoJSON,
				String oidcUserInfoMapperJSON,
				String tokenRequestParametersJSON)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.addOAuthClientEntry(
			userId, authRequestParametersJSON, authServerWellKnownURI, infoJSON,
			oidcUserInfoMapperJSON, tokenRequestParametersJSON);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			deleteOAuthClientEntry(long oAuthClientEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.deleteOAuthClientEntry(
			oAuthClientEntryId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			deleteOAuthClientEntry(
				long companyId, String authServerWellKnownURI, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.deleteOAuthClientEntry(
			companyId, authServerWellKnownURI, clientId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
				getAuthServerWellKnownURISuffixOAuthClientEntries(
					long companyId, String authServerWellKnownURISuffix)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.
			getAuthServerWellKnownURISuffixOAuthClientEntries(
				companyId, authServerWellKnownURISuffix);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getCompanyOAuthClientEntries(long companyId) {

		return _oAuthClientEntryService.getCompanyOAuthClientEntries(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getCompanyOAuthClientEntries(long companyId, int start, int end) {

		return _oAuthClientEntryService.getCompanyOAuthClientEntries(
			companyId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			getOAuthClientEntry(
				long companyId, String authServerWellKnownURI, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.getOAuthClientEntry(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthClientEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getUserOAuthClientEntries(long userId) {

		return _oAuthClientEntryService.getUserOAuthClientEntries(userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getUserOAuthClientEntries(long userId, int start, int end) {

		return _oAuthClientEntryService.getUserOAuthClientEntries(
			userId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			updateOAuthClientEntry(
				long oAuthClientEntryId, String authRequestParametersJSON,
				String authServerWellKnownURI, String infoJSON,
				String oidcUserInfoMapperJSON,
				String tokenRequestParametersJSON)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.updateOAuthClientEntry(
			oAuthClientEntryId, authRequestParametersJSON,
			authServerWellKnownURI, infoJSON, oidcUserInfoMapperJSON,
			tokenRequestParametersJSON);
	}

	@Override
	public OAuthClientEntryService getWrappedService() {
		return _oAuthClientEntryService;
	}

	@Override
	public void setWrappedService(
		OAuthClientEntryService oAuthClientEntryService) {

		_oAuthClientEntryService = oAuthClientEntryService;
	}

	private OAuthClientEntryService _oAuthClientEntryService;

}