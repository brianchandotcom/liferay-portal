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