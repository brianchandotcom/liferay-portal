/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.UserGroupServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;

import java.util.List;

/**
 * @author Charles May
 */
public class UserGroupServiceImpl extends UserGroupServiceBaseImpl {

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addTeamUserGroups(teamId, userGroupIds);
	}

	public UserGroup addUserGroup(
			String name, String description, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId)
		throws PortalException, SystemException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_USER_GROUP);

		User user = getUser();

		return userGroupLocalService.addUserGroup(
			user.getUserId(), user.getCompanyId(), name, description,
			publicLayoutSetPrototypeId, privateLayoutSetPrototypeId);
	}

	public void deleteUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.DELETE);

		userGroupLocalService.deleteUserGroup(userGroupId);
	}

	public UserGroup getUserGroup(long userGroupId)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroupLocalService.getUserGroup(userGroupId);
	}

	public UserGroup getUserGroup(String name)
		throws PortalException, SystemException {

		User user = getUser();

		UserGroup userGroup = userGroupLocalService.getUserGroup(
			user.getCompanyId(), name);

		long userGroupId = userGroup.getUserGroupId();

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroup;
	}

	public List<UserGroup> getUserUserGroups(long userId)
		throws SystemException {

		return userGroupLocalService.getUserUserGroups(userId);
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetTeamUserGroups(teamId, userGroupIds);
	}

	public UserGroup updateUserGroup(
			long userGroupId, String name, String description,
			long publicLayoutSetPrototypeId, long privateLayoutSetPrototypeId)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		User user = getUser();

		return userGroupLocalService.updateUserGroup(
			user.getCompanyId(), userGroupId, name, description,
			publicLayoutSetPrototypeId, privateLayoutSetPrototypeId);
	}

}