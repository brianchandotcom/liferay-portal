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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.TeamPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.UserGroupIdComparator;
import com.liferay.portal.service.base.UserGroupServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * user groups. Its methods include permission checks.
 *
 * @author Charles May
 */
public class UserGroupServiceImpl extends UserGroupServiceBaseImpl {

	/**
	 * Adds the user groups to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	/**
	 * Adds the user groups to the team
	 *
	 * @param teamId the primary key of the team
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.addTeamUserGroups(teamId, userGroupIds);
	}

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param  name the user group's name
	 * @param  description the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 */
	@Override
	public UserGroup addUserGroup(
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_USER_GROUP);

		User user = getUser();

		UserGroup userGroup = userGroupLocalService.addUserGroup(
			user.getUserId(), user.getCompanyId(), name, description,
			serviceContext);

		UserGroupMembershipPolicyUtil.verifyPolicy(userGroup);

		return userGroup;
	}

	/**
	 * Deletes the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 */
	@Override
	public void deleteUserGroup(long userGroupId) throws PortalException {
		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.DELETE);

		userGroupLocalService.deleteUserGroup(userGroupId);
	}

	/**
	 * Fetches the user group with the primary key.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @return the user group with the primary key
	 */
	@Override
	public UserGroup fetchUserGroup(long userGroupId) throws PortalException {
		UserGroup userGroup = userGroupLocalService.fetchUserGroup(userGroupId);

		if (userGroup != null) {
			UserGroupPermissionUtil.check(
				getPermissionChecker(), userGroupId, ActionKeys.VIEW);
		}

		return userGroup;
	}

	@Override
	public List<UserGroup> getGtUserGroups(
		long gtUserGroupId, long companyId, long parentUserGroupId, int size) {

		return userGroupPersistence.filterFindByU_C_P(
			gtUserGroupId, companyId, parentUserGroupId, 0, size,
			new UserGroupIdComparator(true));
	}

	/**
	 * Returns the user group with the primary key.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @return the user group with the primary key
	 */
	@Override
	public UserGroup getUserGroup(long userGroupId) throws PortalException {
		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.VIEW);

		return userGroupLocalService.getUserGroup(userGroupId);
	}

	/**
	 * Returns the user group with the name.
	 *
	 * @param  name the user group's name
	 * @return the user group with the name
	 */
	@Override
	public UserGroup getUserGroup(String name) throws PortalException {
		User user = getUser();

		UserGroup userGroup = userGroupLocalService.getUserGroup(
			user.getCompanyId(), name);

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroup.getUserGroupId(),
			ActionKeys.VIEW);

		return userGroup;
	}

	@Override
	public List<UserGroup> getUserGroups(long companyId)
		throws PortalException {

		return filterUserGroups(userGroupLocalService.getUserGroups(companyId));
	}

	@Override
	public List<UserGroup> getUserGroups(
		long companyId, String name, int start, int end) {

		if (Validator.isNull(name)) {
			return userGroupPersistence.filterFindByCompanyId(
				companyId, start, end);
		}

		return userGroupPersistence.filterFindByC_LikeN(
			companyId, name, start, end);
	}

	@Override
	public int getUserGroupsCount(long companyId, String name) {
		if (Validator.isNull(name)) {
			return userGroupPersistence.filterCountByCompanyId(companyId);
		}

		return userGroupPersistence.filterCountByC_LikeN(companyId, name);
	}

	/**
	 * Returns all the user groups to which the user belongs.
	 *
	 * @param  userId the primary key of the user
	 * @return the user groups to which the user belongs
	 */
	@Override
	public List<UserGroup> getUserUserGroups(long userId)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<UserGroup> userGroups = userGroupLocalService.getUserUserGroups(
			userId);

		return filterUserGroups(userGroups);
	}

	/**
	 * Removes the user groups from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	/**
	 * Removes the user groups from the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userGroupIds the primary keys of the user groups
	 */
	@Override
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userGroupLocalService.unsetTeamUserGroups(teamId, userGroupIds);
	}

	/**
	 * Updates the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  name the user group's name
	 * @param  description the the user group's description
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @return the user group
	 */
	@Override
	public UserGroup updateUserGroup(
			long userGroupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.UPDATE);

		User user = getUser();

		return userGroupLocalService.updateUserGroup(
			user.getCompanyId(), userGroupId, name, description,
			serviceContext);
	}

	protected List<UserGroup> filterUserGroups(List<UserGroup> userGroups)
		throws PortalException {

		List<UserGroup> filteredGroups = new ArrayList<>();

		for (UserGroup userGroup : userGroups) {
			if (UserGroupPermissionUtil.contains(
					getPermissionChecker(), userGroup.getUserGroupId(),
					ActionKeys.VIEW)) {

				filteredGroups.add(userGroup);
			}
		}

		return filteredGroups;
	}

}