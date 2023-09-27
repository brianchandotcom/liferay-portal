/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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