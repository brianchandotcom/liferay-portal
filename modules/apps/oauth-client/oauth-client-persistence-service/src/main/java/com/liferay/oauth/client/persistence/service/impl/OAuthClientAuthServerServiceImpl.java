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
import com.liferay.portal.kernel.model.GroupConstants;
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
			long userId, String discoveryEndpoint, String metadataJSON,
			String type)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_oAuthClientAuthServerModelResourcePermission,
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID, 0,
			OAuthClientPersistenceActionKeys.
				ACTION_ADD_OAUTH_CLIENT_AUTH_SERVER);

		return oAuthClientAuthServerLocalService.addOAuthClientAuthServer(
			userId, discoveryEndpoint, metadataJSON, type);
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
			oAuthClientAuthServer);
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				companyId, issuer);

		_oAuthClientAuthServerModelResourcePermission.check(
			getPermissionChecker(), oAuthClientAuthServer, ActionKeys.DELETE);

		return oAuthClientAuthServerLocalService.deleteOAuthClientAuthServer(
			oAuthClientAuthServer);
	}

	@Override
	public List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId) {

		return oAuthClientAuthServerPersistence.filterFindByCompanyId(
			companyId);
	}

	@Override
	public List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId, int start, int end) {

		return oAuthClientAuthServerPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public OAuthClientAuthServer getOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				companyId, issuer);

		_oAuthClientAuthServerModelResourcePermission.check(
			getPermissionChecker(), oAuthClientAuthServer, ActionKeys.VIEW);

		return oAuthClientAuthServer;
	}

	@Override
	public List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, String type) {

		return oAuthClientAuthServerPersistence.filterFindByC_T(
			companyId, type);
	}

	@Override
	public List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId) {

		return oAuthClientAuthServerPersistence.filterFindByUserId(userId);
	}

	@Override
	public List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId, int start, int end) {

		return oAuthClientAuthServerPersistence.filterFindByUserId(
			userId, start, end);
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

	@Reference(
		target = "(model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientAuthServer)"
	)
	private ModelResourcePermission<OAuthClientAuthServer>
		_oAuthClientAuthServerModelResourcePermission;

}