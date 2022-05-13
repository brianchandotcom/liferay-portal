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
				long userId, String discoveryEndpoint, String metadataJSON,
				String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.addOAuthClientAuthServer(
			userId, discoveryEndpoint, metadataJSON, type);
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
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(long companyId) {

		return _oAuthClientAuthServerService.getCompanyOAuthClientAuthServers(
			companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(
				long companyId, int start, int end) {

		return _oAuthClientAuthServerService.getCompanyOAuthClientAuthServers(
			companyId, start, end);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
			getOAuthClientAuthServer(long companyId, String issuer)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientAuthServerService.getOAuthClientAuthServer(
			companyId, issuer);
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
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(long userId) {

		return _oAuthClientAuthServerService.getUserOAuthClientAuthServers(
			userId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(long userId, int start, int end) {

		return _oAuthClientAuthServerService.getUserOAuthClientAuthServers(
			userId, start, end);
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