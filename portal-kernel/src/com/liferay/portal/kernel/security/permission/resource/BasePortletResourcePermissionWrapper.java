/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Shuyang Zhou
 */
public abstract class BasePortletResourcePermissionWrapper
	implements PortletResourcePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionDCLSingleton.getSingleton(
				this::doGetPortletResourcePermission);

		portletResourcePermission.check(permissionChecker, group, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionDCLSingleton.getSingleton(
				this::doGetPortletResourcePermission);

		portletResourcePermission.check(permissionChecker, groupId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionDCLSingleton.getSingleton(
				this::doGetPortletResourcePermission);

		return portletResourcePermission.contains(
			permissionChecker, group, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionDCLSingleton.getSingleton(
				this::doGetPortletResourcePermission);

		return portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Override
	public String getResourceName() {
		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionDCLSingleton.getSingleton(
				this::doGetPortletResourcePermission);

		return portletResourcePermission.getResourceName();
	}

	protected abstract PortletResourcePermission
		doGetPortletResourcePermission();

	private final DCLSingleton<PortletResourcePermission>
		_portletResourcePermissionDCLSingleton = new DCLSingleton<>();

}