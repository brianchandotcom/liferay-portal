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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 */
public class LayoutCache {

	protected long getEntityGroupId(
			long companyId, String entityName, String name)
		throws PortalException, SystemException {

		long entityGroupId = 0;

		Long entityGroupIdObj = entityGroupIdMap.get(entityName);

		if (entityGroupIdObj == null) {
			if (entityName.equals("user-group")) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(
					companyId, null, null, 0, 1, (OrderByComparator)null);

				if (userGroups.size() > 0) {
					UserGroup userGroup = userGroups.get(0);

					Group group = userGroup.getGroup();

					entityGroupId = group.getGroupId();
				}
			}
			else if (entityName.equals("organization")) {
				List<Organization> organizations =
					OrganizationLocalServiceUtil.search(
						companyId,
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, name,
						null, null, null, null, null, null, null, true, 0, 1);

				if (organizations.size() > 0) {
					Organization organization = organizations.get(0);

					Group group = organization.getGroup();

					entityGroupId = group.getGroupId();
				}
			}

			entityGroupIdMap.put(entityName, entityGroupId);
		}
		else {
			entityGroupId = entityGroupIdObj.longValue();
		}

		return entityGroupId;
	}

	protected Map<String, Long> getEntityMap(long companyId, String entityName)
		throws PortalException, SystemException {

		Map<String, Long> entityMap = entityMapMap.get(entityName);

		if (entityMap == null) {
			entityMap = new HashMap<String, Long>();

			if (entityName.equals("user-group")) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(
					companyId, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					(OrderByComparator)null);

				for (int i = 0; i < userGroups.size(); i++) {
					UserGroup userGroup = userGroups.get(i);

					Group group = userGroup.getGroup();

					entityMap.put(userGroup.getName(), group.getGroupId());
				}
			}
			else if (entityName.equals("organization")) {
				List<Organization> organizations =
					OrganizationLocalServiceUtil.search(
						companyId,
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
						OrganizationConstants.TYPE_REGULAR_ORGANIZATION, null,
						null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (int i = 0; i < organizations.size(); i++) {
					Organization organization = organizations.get(i);

					Group group = organization.getGroup();

					entityMap.put(organization.getName(), group.getGroupId());
				}
			}

			entityMapMap.put(entityName, entityMap);
		}

		return entityMap;
	}

	protected List<Role> getGroupRoles_1to4(long groupId)
		throws SystemException {

		List<Role> roles = groupRolesMap.get(groupId);

		if (roles == null) {
			roles = RoleLocalServiceUtil.getGroupRoles(groupId);

			groupRolesMap.put(groupId, roles);
		}

		return roles;
	}

	protected List<Role> getGroupRoles_5(long groupId, String resourceName)
		throws PortalException, SystemException {

		List<Role> roles = groupRolesMap.get(groupId);

		if (roles == null) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			roles = ResourceActionsUtil.getRoles(
				group.getCompanyId(), group, resourceName, null);

			List<Team> teams = TeamLocalServiceUtil.getGroupTeams(groupId);

			for (Team team : teams) {
				Role teamRole = RoleLocalServiceUtil.getTeamRole(
					group.getCompanyId(), team.getTeamId());

				teamRole.setName(
					PermissionExporter.ROLE_TEAM_PREFIX + team.getName());
				teamRole.setDescription(team.getDescription());

				roles.add(teamRole);
			}

			groupRolesMap.put(groupId, roles);
		}

		return roles;
	}

	protected List<User> getGroupUsers(long groupId) throws SystemException {
		List<User> users = groupUsersMap.get(groupId);

		if (users == null) {
			users = UserLocalServiceUtil.getGroupUsers(groupId);

			groupUsersMap.put(groupId, users);
		}

		return users;
	}

	protected Role getRole(long companyId, String roleName)
		throws PortalException, SystemException {

		Role role = rolesMap.get(roleName);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRole(companyId, roleName);

				rolesMap.put(roleName, role);
			}
			catch (NoSuchRoleException nsre) {
			}
		}

		return role;
	}

	protected List<Role> getUserRoles(long userId) throws SystemException {
		List<Role> userRoles = userRolesMap.get(userId);

		if (userRoles == null) {
			userRoles = RoleLocalServiceUtil.getUserRoles(userId);

			userRolesMap.put(userId, userRoles);
		}

		return userRoles;
	}

	protected Map<String, Long> entityGroupIdMap = new HashMap<String, Long>();
	protected Map<String, Map<String, Long>> entityMapMap =
		new HashMap<String, Map<String, Long>>();
	protected Map<Long, List<Role>> groupRolesMap =
		new HashMap<Long, List<Role>>();
	protected Map<Long, List<User>> groupUsersMap =
		new HashMap<Long, List<User>>();
	protected Map<String, Role> rolesMap = new HashMap<String, Role>();
	protected Map<Long, List<Role>> userRolesMap =
		new HashMap<Long, List<Role>>();

}