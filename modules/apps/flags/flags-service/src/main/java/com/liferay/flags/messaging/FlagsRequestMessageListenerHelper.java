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

package com.liferay.flags.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Peter Fellwock
 */
public class FlagsRequestMessageListenerHelper {

	public static Company getCompany(
		CompanyLocalService companyLocalService, long companyId) {

		try {
			if (companyLocalService != null) {
				return companyLocalService.getCompany(companyId);
			}
			else {
				return CompanyLocalServiceUtil.getCompany(companyId);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	public static Group getGroup(
		GroupLocalService groupLocalService, long groupId) {

		try {
			if (groupLocalService != null) {
				return groupLocalService.getGroup(groupId);
			}
			else {
				return GroupLocalServiceUtil.getGroup(groupId);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	public static Layout getLayout(
		LayoutLocalService layoutLocalService, long plid) {

		try {
			if (layoutLocalService != null) {
				return layoutLocalService.getLayout(plid);
			}
			else {
				return LayoutLocalServiceUtil.getLayout(plid);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	public static Role getRole(
		RoleLocalService roleLocalService, long companyId, String roleName) {

		try {
			if (roleLocalService != null) {
				return roleLocalService.getRole(companyId, roleName);
			}
			else {
				return RoleLocalServiceUtil.getRole(companyId, roleName);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	public static List<User> getRoleUsers(
		UserLocalService userLocalService, long roleId) {

		if (userLocalService != null) {
			return userLocalService.getRoleUsers(roleId);
		}
		else {
			return UserLocalServiceUtil.getRoleUsers(roleId);
		}
	}

	public static User getUserById(
		UserLocalService userLocalService, long userId) {

		try {
			if (userLocalService != null) {
				return userLocalService.getUserById(userId);
			}
			else {
				return UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	public static List<UserGroupRole> getUserGroupRolesByGroupAndRole(
		UserGroupRoleLocalService userGroupRoleLocalService, long groupId,
		long roleId) {

		if (userGroupRoleLocalService != null) {
			return userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, roleId);
		}
		else {
			return UserGroupRoleLocalServiceUtil.
				getUserGroupRolesByGroupAndRole(groupId, roleId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FlagsRequestMessageListenerHelper.class);

}