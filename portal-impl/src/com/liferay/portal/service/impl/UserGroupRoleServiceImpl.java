/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.MembershipPolicyException;
import com.liferay.portal.security.auth.MembershipPolicyUtil;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleServiceImpl extends UserGroupRoleServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}

		checkAddUserGroupRolesMembershipPolicy(
			new long[]{userId}, groupId, roleIds);

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		checkAddUserGroupRolesMembershipPolicy(
			userIds, groupId, new long[]{roleId});

		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}

		checkDeleteUserGroupRolesMembershipPolicy(
			new long[]{userId}, groupId, roleIds);

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, roleIds);
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		checkDeleteUserGroupRolesMembershipPolicy(
			userIds, groupId, new long[]{roleId});

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);
	}

	protected void checkAddUserGroupRolesMembershipPolicy(
			long[] userIds, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		MembershipPolicyException membershipPolicyException = null;

		Group group = groupLocalService.getGroup(groupId);

		for (long roleId : roleIds) {
			Role role = rolePersistence.findByPrimaryKey(roleId);

			for (long userId : userIds) {
				User user = userPersistence.findByPrimaryKey(userId);

				if (!MembershipPolicyUtil.isMembershipAllowed(
						role, user, group)) {

					if (membershipPolicyException == null) {
						membershipPolicyException =
							new MembershipPolicyException(
								MembershipPolicyException.
									ROLE_MEMBERSHIP_NOT_ALLOWED);
					}

					if (!membershipPolicyException.getUsers().contains(user)) {
						membershipPolicyException.addUser(user);
					}

					if (!membershipPolicyException.getRoles().contains(role)) {
						membershipPolicyException.addRole(role);
					}
				}
			}
		}

		if (membershipPolicyException!= null) {
			throw membershipPolicyException;
		}
	}

	protected void checkDeleteUserGroupRolesMembershipPolicy(
			long[] userIds, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		MembershipPolicyException membershipPolicyException = null;

		Group group = groupLocalService.getGroup(groupId);

		for (long roleId : roleIds) {
			Role role = rolePersistence.findByPrimaryKey(roleId);

			for (long userId : userIds) {
				User user = userPersistence.findByPrimaryKey(userId);

				Set<Role> mandatoryRoles =
					MembershipPolicyUtil.getMandatoryRoles(group, user);

				if (mandatoryRoles.contains(role)) {
					if (membershipPolicyException == null) {
						membershipPolicyException =
							new MembershipPolicyException(
								MembershipPolicyException.
									ROLE_MEMBERSHIP_REQUIRED);
					}

					if (!membershipPolicyException.getUsers().contains(user)) {
						membershipPolicyException.addUser(user);
					}

					if (!membershipPolicyException.getRoles().contains(role)) {
						membershipPolicyException.addRole(role);
					}
				}
			}
		}

		if (membershipPolicyException!= null) {
			throw membershipPolicyException;
		}
	}

}