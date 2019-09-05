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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.internal.util.ExportImportPermissionUtil;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 */
public class LayoutCache {

	public Role getNameRole(long companyId, String roleName)
		throws PortalException {

		Role role = nameRolesMap.get(roleName);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRole(companyId, roleName);

				nameRolesMap.put(roleName, role);
			}
			catch (NoSuchRoleException nsre) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsre, nsre);
				}
			}
		}

		return role;
	}

	public Role getUuidRole(long companyId, String uuid)
		throws PortalException {

		Role role = uuidRolesMap.get(uuid);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRoleByUuidAndCompanyId(
					uuid, companyId);

				uuidRolesMap.put(uuid, role);
			}
			catch (NoSuchRoleException nsre) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsre, nsre);
				}
			}
		}

		return role;
	}

	protected List<Role> getGroupRoles(long groupId, String resourceName)
		throws PortalException {

		List<Role> roles = groupRolesMap.get(groupId);

		if (roles != null) {
			return roles;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		roles = ListUtil.copy(
			ResourceActionsUtil.getRoles(
				group.getCompanyId(), group, resourceName, null));

		Map<Team, Role> teamRoleMap = RoleLocalServiceUtil.getTeamRoleMap(
			groupId);

		for (Map.Entry<Team, Role> entry : teamRoleMap.entrySet()) {
			Team team = entry.getKey();
			Role teamRole = entry.getValue();

			teamRole.setName(
				ExportImportPermissionUtil.getTeamRoleName(team.getName()));
			teamRole.setDescription(team.getDescription());

			roles.add(teamRole);
		}

		groupRolesMap.put(groupId, roles);

		return roles;
	}

	protected List<User> getGroupUsers(long groupId) {
		List<User> users = groupUsersMap.get(groupId);

		if (users == null) {
			users = UserLocalServiceUtil.getGroupUsers(groupId);

			groupUsersMap.put(groupId, users);
		}

		return users;
	}

	protected List<Role> getUserRoles(long userId) {
		List<Role> userRoles = userRolesMap.get(userId);

		if (userRoles == null) {
			userRoles = RoleLocalServiceUtil.getUserRoles(userId);

			userRolesMap.put(userId, userRoles);
		}

		return userRoles;
	}

	protected Map<Long, List<Role>> groupRolesMap = new HashMap<>();
	protected Map<Long, List<User>> groupUsersMap = new HashMap<>();
	protected Map<String, Role> nameRolesMap = new HashMap<>();
	protected Map<Long, List<Role>> userRolesMap = new HashMap<>();
	protected Map<String, Role> uuidRolesMap = new HashMap<>();

	private static final Log _log = LogFactoryUtil.getLog(LayoutCache.class);

}