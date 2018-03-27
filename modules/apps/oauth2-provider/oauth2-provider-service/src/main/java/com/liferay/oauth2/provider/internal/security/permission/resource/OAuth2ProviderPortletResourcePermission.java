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

package com.liferay.oauth2.provider.internal.security.permission.resource;

import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = "resource.name=" + OAuth2ProviderConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class OAuth2ProviderPortletResourcePermission
	implements PortletResourcePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, null, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2ProviderConstants.RESOURCE_NAME,
				OAuth2ProviderConstants.RESOURCE_NAME, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, null, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2ProviderConstants.RESOURCE_NAME,
				OAuth2ProviderConstants.RESOURCE_NAME, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId) {

		return permissionChecker.hasPermission(
			null, OAuth2ProviderConstants.RESOURCE_NAME,
			OAuth2ProviderConstants.RESOURCE_NAME, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return permissionChecker.hasPermission(
			null, OAuth2ProviderConstants.RESOURCE_NAME,
			OAuth2ProviderConstants.RESOURCE_NAME, actionId);
	}

	@Override
	public String getResourceName() {
		return OAuth2ProviderConstants.RESOURCE_NAME;
	}

}