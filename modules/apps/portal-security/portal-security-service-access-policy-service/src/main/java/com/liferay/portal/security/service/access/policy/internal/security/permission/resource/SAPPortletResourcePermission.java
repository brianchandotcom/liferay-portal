/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.security.permission.resource;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	property = "resource.name=" + SAPConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class SAPPortletResourcePermission implements PortletResourcePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, null, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPConstants.RESOURCE_NAME,
				SAPConstants.RESOURCE_NAME, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, null, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPConstants.RESOURCE_NAME,
				SAPConstants.RESOURCE_NAME, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId) {

		return permissionChecker.hasPermission(
			null, SAPConstants.RESOURCE_NAME, SAPConstants.RESOURCE_NAME,
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return permissionChecker.hasPermission(
			null, SAPConstants.RESOURCE_NAME, SAPConstants.RESOURCE_NAME,
			actionId);
	}

	@Override
	public String getResourceName() {
		return SAPConstants.RESOURCE_NAME;
	}

}