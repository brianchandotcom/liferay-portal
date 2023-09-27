/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jiaxu Wei
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Group",
	service = ModelResourcePermission.class
)
public class GroupModelResourcePermission
	implements ModelResourcePermission<Group> {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException {

		GroupPermissionUtil.check(permissionChecker, group, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		GroupPermissionUtil.check(permissionChecker, groupId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException {

		return GroupPermissionUtil.contains(permissionChecker, group, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		return GroupPermissionUtil.contains(
			permissionChecker, groupId, actionId);
	}

	@Override
	public String getModelName() {
		return Group.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

}