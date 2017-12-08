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

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class StagedPortletPermissionLogic
	implements PortletResourcePermissionLogic {

	public StagedPortletPermissionLogic(
		StagingPermission stagingPermission, String portletId) {

		_stagingPermission = Objects.requireNonNull(stagingPermission);
		_portletId = Objects.requireNonNull(portletId);
	}

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, Group group,
		String actionId) {

		long groupId = 0;

		if (group != null) {
			groupId = group.getGroupId();
		}

		return _stagingPermission.hasPermission(
			permissionChecker, group, name, groupId, _portletId, actionId);
	}

	private final String _portletId;
	private final StagingPermission _stagingPermission;

}