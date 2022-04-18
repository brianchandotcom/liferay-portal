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
 * Provides a wrapper for {@link OAuthClientAuthServerService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerService
 * @generated
 */
public class OAuthClientAuthServerServiceWrapper
	implements OAuthClientAuthServerService,
			   ServiceWrapper<OAuthClientAuthServerService> {

	public OAuthClientAuthServerServiceWrapper() {
		this(null);
	}

	public OAuthClientAuthServerServiceWrapper(
		OAuthClientAuthServerService oAuthClientAuthServerService) {

		_oAuthClientAuthServerService = oAuthClientAuthServerService;
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			addOAuthClientAuthServer(
				long companyId, long userId, String discoveryEndpoint,
				String metadataJSON, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.addOAuthClientAuthServer(
			companyId, userId, discoveryEndpoint, metadataJSON, type);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			deleteOAuthClientAuthServer(long oAuthClientAuthServerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.deleteOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			deleteOAuthClientAuthServer(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.deleteOAuthClientAuthServer(
			companyId, issuer);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
		fetchOAuthClientAuthServer(long companyId, String issuer) {

		return _oAuthClientAuthServerService.fetchOAuthClientAuthServer(
			companyId, issuer);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getOAuthClientAuthServers(long companyId, int start, int end) {

		return _oAuthClientAuthServerService.getOAuthClientAuthServers(
			companyId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getOAuthClientAuthServers(long companyId, long userId) {

		return _oAuthClientAuthServerService.getOAuthClientAuthServers(
			companyId, userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getOAuthClientAuthServers(long companyId, String type) {

		return _oAuthClientAuthServerService.getOAuthClientAuthServers(
			companyId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthClientAuthServerService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			updateOAuthClientAuthServer(
				long oAuthClientAuthServerId, String discoveryEndpoint,
				String metadataJSON, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.updateOAuthClientAuthServer(
			oAuthClientAuthServerId, discoveryEndpoint, metadataJSON, type);
	}

	@Override
	public OAuthClientAuthServerService getWrappedService() {
		return _oAuthClientAuthServerService;
	}

	@Override
	public void setWrappedService(
		OAuthClientAuthServerService oAuthClientAuthServerService) {

		_oAuthClientAuthServerService = oAuthClientAuthServerService;
	}

	private OAuthClientAuthServerService _oAuthClientAuthServerService;

}