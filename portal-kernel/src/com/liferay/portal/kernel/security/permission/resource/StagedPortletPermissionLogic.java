/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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