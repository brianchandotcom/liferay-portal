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
				long companyId, long userId, String authServerIssuer,
				String infoJSON, String requestParamsJSON)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.addOAuthClientEntry(
			companyId, userId, authServerIssuer, infoJSON, requestParamsJSON);
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
				long companyId, String authServerIssuer, String clientID)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.deleteOAuthClientEntry(
			companyId, authServerIssuer, clientID);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
		fetchOAuthClientEntry(
			long companyId, String authServerIssuer, String clientID) {

		return _oAuthClientEntryService.fetchOAuthClientEntry(
			companyId, authServerIssuer, clientID);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getOAuthClientEntries(long companyId) {

		return _oAuthClientEntryService.getOAuthClientEntries(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getOAuthClientEntries(long companyId, long userId) {

		return _oAuthClientEntryService.getOAuthClientEntries(
			companyId, userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getOAuthClientEntries(long companyId, String authServerIssuer) {

		return _oAuthClientEntryService.getOAuthClientEntries(
			companyId, authServerIssuer);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientEntry>
			getOAuthClientEntriesByAuthServerType(
				long companyId, String authServerType) {

		return _oAuthClientEntryService.getOAuthClientEntriesByAuthServerType(
			companyId, authServerType);
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
	public com.liferay.oauth.client.persistence.model.OAuthClientEntry
			updateOAuthClientEntry(
				long oAuthClientEntryId, String authServerIssuer,
				String infoJSON, String requestParamsJSON)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientEntryService.updateOAuthClientEntry(
			oAuthClientEntryId, authServerIssuer, infoJSON, requestParamsJSON);
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