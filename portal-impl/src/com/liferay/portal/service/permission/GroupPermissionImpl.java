/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class GroupPermissionImpl implements GroupPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, group, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException, SystemException {

		long groupId = group.getGroupId();

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isUser()) {

			// An individual user would never reach this block because he would
			// be an administrator of his own layouts. However, a user who
			// manages a set of organizations may be modifying pages of a user
			// he manages.

			User user = UserLocalServiceUtil.getUserById(group.getClassPK());

			if ((permissionChecker.getUserId() != user.getUserId()) &&
				UserPermissionUtil.contains(
					permissionChecker, user.getUserId(),
					user.getOrganizationIds(), ActionKeys.UPDATE)) {

				return true;
			}
		}

		if (actionId.equals(ActionKeys.ADD_COMMUNITY) &&
			permissionChecker.hasPermission(
				groupId, Group.class.getName(), groupId,
				ActionKeys.MANAGE_SUBGROUPS) ||
			PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_COMMUNITY)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.ADD_LAYOUT) &&
				 permissionChecker.hasPermission(
					groupId, Group.class.getName(), groupId,
					ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}
		else if ((actionId.equals(ActionKeys.EXPORT_IMPORT_LAYOUTS) ||
				  actionId.equals(ActionKeys.EXPORT_IMPORT_PORTLET_INFO)) &&
				 permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.PUBLISH_STAGING)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW) &&
				 (permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.ASSIGN_USER_ROLES) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.MANAGE_LAYOUTS))) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW_STAGING) &&
				 (permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.MANAGE_LAYOUTS) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.MANAGE_STAGING) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.PUBLISH_STAGING) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.UPDATE))) {

			return true;
		}

		// Group id must be set so that users can modify their personal pages

		return permissionChecker.hasPermission(
			groupId, Group.class.getName(), groupId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		if (groupId > 0) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return contains(permissionChecker, group, actionId);
		}
		else {
			return false;
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return permissionChecker.hasPermission(
			0, Group.class.getName(), 0, actionId);
	}

}