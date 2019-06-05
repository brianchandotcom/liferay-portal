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

package com.liferay.announcements.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.UserBagFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class AnnouncementsUtil {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getAnnouncementScopes(User)}
	 */
	@Deprecated
	public static LinkedHashMap<Long, long[]> getAnnouncementScopes(long userId)
		throws PortalException {

		return getAnnouncementScopes(UserLocalServiceUtil.getUserById(userId));
	}

	public static LinkedHashMap<Long, long[]> getAnnouncementScopes(User user)
		throws PortalException {

		long userId = user.getUserId();

		LinkedHashMap<Long, long[]> scopes = new LinkedHashMap<>();

		// General announcements

		scopes.put(0L, new long[] {0});

		// Personal announcements

		scopes.put(_USER_CLASS_NAME_ID, new long[] {userId});

		// Organization announcements

		UserBag userBag = UserBagFactoryUtil.create(userId);

		long[] organizationIds = userBag.getUserOrgIds();

		if (organizationIds.length > 0) {
			scopes.put(_ORGANIZATION_CLASS_NAME_ID, organizationIds);
		}

		// Site announcements

		long[] groupIds = userBag.getUserGroupIds();

		if (groupIds.length > 0) {
			scopes.put(_GROUP_CLASS_NAME_ID, groupIds);
		}

		// User group announcements

		List<UserGroup> userGroups =
			UserGroupLocalServiceUtil.getUserUserGroups(userId);

		if (!userGroups.isEmpty()) {
			long[] userGroupIds = ListUtil.toLongArray(
				userGroups, UserGroup.USER_GROUP_ID_ACCESSOR);

			scopes.put(_USER_GROUP_CLASS_NAME_ID, userGroupIds);
		}

		// Role announcements

		Set<Long> roleIds = SetUtil.fromArray(userBag.getRoleIds());

		if ((groupIds.length > 0) || (organizationIds.length > 0)) {
			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				roleIds.add(userGroupRole.getRoleId());
			}
		}

		if (!userGroups.isEmpty()) {
			List<UserGroupGroupRole> userGroupGroupRoles =
				UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRolesByUser(
					userId);

			for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
				roleIds.add(userGroupGroupRole.getRoleId());
			}
		}

		List<Team> teams = TeamLocalServiceUtil.getUserTeams(userId);

		long[] teamIds = ListUtil.toLongArray(teams, Team.TEAM_ID_ACCESSOR);

		long companyId = user.getCompanyId();

		if (teamIds.length > 0) {
			List<Role> teamsRoles = RoleLocalServiceUtil.getTeamsRoles(
				companyId, teamIds);

			for (Role teamRole : teamsRoles) {
				roleIds.add(teamRole.getRoleId());
			}
		}

		if (_PERMISSIONS_CHECK_GUEST_ENABLED) {
			Role guestRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.GUEST);

			roleIds.add(guestRole.getRoleId());
		}

		if (!roleIds.isEmpty()) {
			scopes.put(_ROLE_CLASS_NAME_ID, ArrayUtil.toLongArray(roleIds));
		}

		return scopes;
	}

	public static List<Group> getGroups(ThemeDisplay themeDisplay)
		throws PortalException {

		List<Group> filteredGroups = new ArrayList<>();

		List<Group> groups = GroupLocalServiceUtil.getUserGroups(
			themeDisplay.getUserId(), true);

		for (Group group : groups) {
			if (((group.isOrganization() && group.isSite()) ||
				 group.isRegularSite()) &&
				GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), group.getGroupId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	public static List<Organization> getOrganizations(ThemeDisplay themeDisplay)
		throws PortalException {

		List<Organization> filteredOrganizations = new ArrayList<>();

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				themeDisplay.getUserId());

		for (Organization organization : organizations) {
			if (OrganizationPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					organization.getOrganizationId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				filteredOrganizations.add(organization);
			}
		}

		return filteredOrganizations;
	}

	public static List<Role> getRoles(ThemeDisplay themeDisplay)
		throws PortalException {

		List<Role> filteredRoles = new ArrayList<>();

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			themeDisplay.getCompanyId());

		for (Role role : roles) {
			if (hasManageAnnouncementsPermission(
					role, themeDisplay.getPermissionChecker())) {

				filteredRoles.add(role);
			}
		}

		return filteredRoles;
	}

	public static List<UserGroup> getUserGroups(ThemeDisplay themeDisplay) {
		List<UserGroup> filteredUserGroups = new ArrayList<>();

		List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(
			themeDisplay.getCompanyId());

		for (UserGroup userGroup : userGroups) {
			if (UserGroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					userGroup.getUserGroupId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				filteredUserGroups.add(userGroup);
			}
		}

		return filteredUserGroups;
	}

	public static boolean hasManageAnnouncementsPermission(
			Role role, PermissionChecker permissionChecker)
		throws PortalException {

		if (role.isTeam()) {
			Team team = TeamLocalServiceUtil.getTeam(role.getClassPK());

			if (GroupPermissionUtil.contains(
					permissionChecker, team.getGroupId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS) &&
				RolePermissionUtil.contains(
					permissionChecker, team.getGroupId(), role.getRoleId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				return true;
			}
		}
		else if (RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(),
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

			return true;
		}

		return false;
	}

	private static final long _GROUP_CLASS_NAME_ID = PortalUtil.getClassNameId(
		Group.class.getName());

	private static final long _ORGANIZATION_CLASS_NAME_ID =
		PortalUtil.getClassNameId(Organization.class.getName());

	private static final boolean _PERMISSIONS_CHECK_GUEST_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.PERMISSIONS_CHECK_GUEST_ENABLED));

	private static final long _ROLE_CLASS_NAME_ID = PortalUtil.getClassNameId(
		Role.class.getName());

	private static final long _USER_CLASS_NAME_ID = PortalUtil.getClassNameId(
		User.class.getName());

	private static final long _USER_GROUP_CLASS_NAME_ID =
		PortalUtil.getClassNameId(UserGroup.class.getName());

}