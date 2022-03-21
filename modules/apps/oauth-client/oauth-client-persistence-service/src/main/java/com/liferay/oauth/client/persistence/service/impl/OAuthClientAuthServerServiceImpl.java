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

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.oauth.client.persistence.constants.OAuthClientPersistenceActionKeys;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.service.base.OAuthClientAuthServerServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = {
		"json.web.service.context.name=oauthclient",
		"json.web.service.context.path=OAuthClientAuthServer"
	},
	service = AopService.class
)
public class OAuthClientAuthServerServiceImpl
	extends OAuthClientAuthServerServiceBaseImpl {

	@Override
	public OAuthClientAuthServer addOAuthClientAuthServer(
			long companyId, long userId, String discoveryEndpoint,
			String metadataJSON, String type)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_oAuthClientAuthServerModelResourcePermission,
			getPermissionChecker(), 0, 0,
			OAuthClientPersistenceActionKeys.
				ACTION_ADD_OAUTH_CLIENT_AUTH_SERVER);

		return oAuthClientAuthServerLocalService.addOAuthClientAuthServer(
			companyId, userId, discoveryEndpoint, metadataJSON, type);
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
			long oAuthClientAuthServerId)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				oAuthClientAuthServerId);

		_oAuthClientAuthServerModelResourcePermission.check(
			getPermissionChecker(), oAuthClientAuthServer, ActionKeys.DELETE);

		return oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
			oAuthClientAuthServerId);
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.fetchOAuthClientAuthServer(
				companyId, issuer);

		if (oAuthClientAuthServer != null) {
			_oAuthClientAuthServerModelResourcePermission.check(
				getPermissionChecker(), oAuthClientAuthServer,
				ActionKeys.DELETE);
			oAuthClientAuthServer =
				oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
					companyId, issuer);
		}

		return oAuthClientAuthServer;
	}

	@Override
	public OAuthClientAuthServer fetchOAuthClientAuthServer(
		long companyId, String issuer) {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.fetchOAuthClientAuthServer(
				companyId, issuer);

		if (oAuthClientAuthServer != null) {
			try {
				_oAuthClientAuthServerModelResourcePermission.check(
					getPermissionChecker(), oAuthClientAuthServer,
					ActionKeys.VIEW);
			}
			catch (PortalException portalException) {
				_log.error(portalException);

				oAuthClientAuthServer = null;
			}
		}

		return oAuthClientAuthServer;
	}

	@Override
	public List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, int start, int end) {

		return oAuthClientAuthServerPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, long userId) {

		return oAuthClientAuthServerPersistence.filterFindByC_U(
			companyId, userId);
	}

	@Override
	public List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, String type) {

		return oAuthClientAuthServerPersistence.filterFindByC_T(
			companyId, type);
	}

	@Override
	public OAuthClientAuthServer updateOAuthClientAuthServer(
			long oAuthClientAuthServerId, String discoveryEndpoint,
			String metadataJSON, String type)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				oAuthClientAuthServerId);

		_oAuthClientAuthServerModelResourcePermission.check(
			getPermissionChecker(), oAuthClientAuthServer, ActionKeys.UPDATE);

		return oAuthClientAuthServerLocalService.updateOAuthClientAuthServer(
			oAuthClientAuthServerId, discoveryEndpoint, metadataJSON, type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientAuthServerServiceImpl.class);

	@Reference(
		target = "(model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientAuthServer)"
	)
	private ModelResourcePermission<OAuthClientAuthServer>
		_oAuthClientAuthServerModelResourcePermission;

}