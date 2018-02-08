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

package com.liferay.commerce.user.service.impl;

import com.liferay.commerce.user.service.base.CommerceUserLocalServiceBaseImpl;
import com.liferay.commerce.user.util.CommerceRoleRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceUserLocalServiceImpl
	extends CommerceUserLocalServiceBaseImpl {

	@Override
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException {

		return userLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	@Override
	public User updatePasswordReset(long userId, boolean passwordReset)
		throws PortalException {

		return userLocalService.updatePasswordReset(userId, passwordReset);
	}

	@Override
	public User updateReminderQuery(long userId, String question, String answer)
		throws PortalException {

		return userLocalService.updateReminderQuery(userId, question, answer);
	}

	@Override
	public User updateUser(
			long userId, String screenName, String emailAddress,
			boolean portrait, byte[] portraitBytes, String languageId,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		return userLocalService.updateUser(
			userId, user.getPassword(), null, null, false,
			user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
			screenName, emailAddress, user.getFacebookId(), user.getOpenId(),
			portrait, portraitBytes, languageId, user.getTimeZoneId(),
			user.getGreeting(), user.getComments(), firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, null, null, null, null, null, jobTitle,
			user.getGroupIds(), user.getOrganizationIds(), user.getRoleIds(),
			null, user.getUserGroupIds(), serviceContext);
	}

	@Override
	public void updateUserRoles(long userId, long groupId, long[] roleIds)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Set<Long> organizationRoleIds = new HashSet<>();
		Set<Long> regularRoleIds = new HashSet<>();

		List<Role> roles = commerceRoleRegistry.getRoles(user.getCompanyId());

		for (long roleId : roleIds) {
			if (roleId <= 0) {
				continue;
			}

			Role role = roleLocalService.getRole(roleId);

			if (!roles.contains(role)) {
				continue;
			}

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				organizationRoleIds.add(role.getRoleId());
			}
			else if (role.getType() == RoleConstants.TYPE_REGULAR) {
				regularRoleIds.add(role.getRoleId());
			}
		}

		updateUserRoles(userId, regularRoleIds);

		if (groupId > 0) {
			updateUserGroupRoles(userId, groupId, organizationRoleIds);
		}
	}

	protected void updateUserGroupRoles(
			long userId, long groupId, Set<Long> roleIds)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		if (!group.isOrganization()) {
			return;
		}

		List<UserGroupRole> previousUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId, groupId);

		for (UserGroupRole userGroupRole : previousUserGroupRoles) {
			if (roleIds.contains(userGroupRole.getRoleId())) {
				roleIds.remove(userGroupRole.getRoleId());
			}
			else {
				userGroupRoleLocalService.deleteUserGroupRole(userGroupRole);
			}
		}

		if (roleIds.isEmpty()) {
			return;
		}

		userGroupRoleLocalService.addUserGroupRoles(
			userId, groupId, ArrayUtil.toLongArray(roleIds));
	}

	protected void updateUserRoles(long userId, Set<Long> roleIds)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		List<Role> previousUserRoles = user.getRoles();

		for (Role userRole : previousUserRoles) {
			if (roleIds.contains(userRole.getRoleId())) {
				roleIds.remove(userRole.getRoleId());
			}
			else {
				roleLocalService.deleteUserRole(userId, userRole);
			}
		}

		if (roleIds.isEmpty()) {
			return;
		}

		roleLocalService.setUserRoles(userId, ArrayUtil.toLongArray(roleIds));
	}

	@ServiceReference(type = CommerceRoleRegistry.class)
	protected CommerceRoleRegistry commerceRoleRegistry;

}