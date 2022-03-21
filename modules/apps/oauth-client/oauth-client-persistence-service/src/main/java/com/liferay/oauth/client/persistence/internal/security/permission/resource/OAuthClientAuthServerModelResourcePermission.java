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

package com.liferay.oauth.client.persistence.internal.security.permission.resource;

import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.service.OAuthClientAuthServerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientAuthServer",
	service = ModelResourcePermission.class
)
public class OAuthClientAuthServerModelResourcePermission
	implements ModelResourcePermission<OAuthClientAuthServer> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long oAuthClientAuthServerId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthClientAuthServerId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientAuthServer.class.getName(),
				oAuthClientAuthServerId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			OAuthClientAuthServer oAuthClientAuthServer, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthClientAuthServer, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientAuthServer.class.getName(),
				oAuthClientAuthServer.getOAuthClientAuthServerId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long oAuthClientAuthServerId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				oAuthClientAuthServerId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			OAuthClientAuthServer oAuthClientAuthServer, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				oAuthClientAuthServer.getCompanyId(),
				OAuthClientAuthServer.class.getName(),
				oAuthClientAuthServer.getOAuthClientAuthServerId(),
				oAuthClientAuthServer.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuthClientAuthServer.class.getName(),
			oAuthClientAuthServer.getOAuthClientAuthServerId(), actionId);
	}

	@Override
	public String getModelName() {
		return OAuthClientAuthServer.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private OAuthClientAuthServerLocalService
		_oAuthClientAuthServerLocalService;

	@Reference(target = "(resource.name=com.liferay.oauth.client.persistence)")
	private PortletResourcePermission _portletResourcePermission;

}