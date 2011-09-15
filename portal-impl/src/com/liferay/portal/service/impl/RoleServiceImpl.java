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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.RoleServiceBaseImpl;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the role remote service.
 *
 * @author Brian Wing Shun Chan
 */
public class RoleServiceImpl extends RoleServiceBaseImpl {

	/**
	 * Adds a role. The user is reindexed after the role is added.
	 *
	 * @param  name the role's name
	 * @param  titleMap the role's title map (optionally <code>null</code>)
	 * @param  descriptionMap the role's description map (optionally
	 *         <code>null</code>)
	 * @param  type the role's type (optionally <code>0</code>)
	 * @return the role
	 * @throws PortalException if the company or the role's name were invalid
	 *         or if the user did not have permission to add roles
	 * @throws SystemException if a system exception occurred
	 */
	public Role addRole(
			String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int type)
		throws PortalException, SystemException {

		PortalPermissionUtil.check(getPermissionChecker(), ActionKeys.ADD_ROLE);

		User user = getUser();

		return roleLocalService.addRole(
			user.getUserId(), user.getCompanyId(), name, titleMap,
			descriptionMap, type);
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleIds the primary keys of the roles
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the user did not have permission to assign members
	 *         to one of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		checkUserRolesPermission(userId, roleIds);

		roleLocalService.addUserRoles(userId, roleIds);
	}

	/**
	 * Deletes the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @throws PortalException if a role with the primary key could not be
	 *         found or if the user did not have permission to delete the role
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRole(long roleId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.DELETE);

		roleLocalService.deleteRole(roleId);
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getGroupRoles(long groupId) throws SystemException {
		return roleLocalService.getGroupRoles(groupId);
	}

	/**
	 * Returns the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @return the role with the primary key
	 * @throws PortalException if a role with the primary key could not be
	 *         found or if the user did not have permission to view the role
	 * @throws SystemException if a system exception occurred
	 */
	public Role getRole(long roleId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.VIEW);

		return roleLocalService.getRole(roleId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return the role with the name
	 * @throws PortalException if a role with the name could not be found in
	 *         the company or if the user did not have permission to view the
	 *         role
	 * @throws SystemException if a system exception occurred
	 */
	public Role getRole(long companyId, String name)
		throws PortalException, SystemException {

		Role role = roleLocalService.getRole(companyId, name);

		RolePermissionUtil.check(
			getPermissionChecker(), role.getRoleId(), ActionKeys.VIEW);

		return role;
	}

	/**
	 * Returns all the roles for the user who is part of a user group in the
	 * group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the roles for the user in a user group in the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getUserGroupGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleLocalService.getUserGroupGroupRoles(userId, groupId);
	}

	/**
	 * Returns all the roles for the user in the group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the roles for the user in the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return roleLocalService.getUserGroupRoles(userId, groupId);
	}

	/**
	 * Returns all the roles for the user and the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groups the groups (optionally <code>null</code>)
	 * @return the roles for the user and groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getUserRelatedRoles(long userId, List<Group> groups)
		throws SystemException {

		return roleLocalService.getUserRelatedRoles(userId, groups);
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<Role> getUserRoles(long userId) throws SystemException {
		return roleLocalService.getUserRoles(userId);
	}

	/**
	 * Returns <code>true</code> if the user is associated with the regular
	 * role.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  name the name of the role
	 * @param  inherited whether the role is inherited
	 * @return <code>true</code> if the user is associated with the regular
	 *         role; <code>false</code> otherwise
	 * @throws PortalException if a role with the name could not be found in
	 *         the company or if the default user for the company could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException, SystemException {

		return roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	/**
	 * Returns <code>true</code> if the user has any one of the specified
	 * regular roles.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  names the names of the roles
	 * @param  inherited whether the roles are inherited
	 * @return <code>true</code> if the user has any one of the regular roles;
	 *         <code>false</code> otherwise
	 * @throws PortalException if one of the roles could not be found in the
	 *         company or if the default user for the company could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException, SystemException {

		return roleLocalService.hasUserRoles(
			userId, companyId, names, inherited);
	}

	/**
	 * Removes roles associated with the user. The user is reindexed after the
	 * roles are removed.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleIds the primary keys of the roles
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if a role with one of the primary keys could not be
	 *         found or if the user did not have permission to remove members
	 *         from a role
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException, SystemException {

		checkUserRolesPermission(userId, roleIds);

		roleLocalService.unsetUserRoles(userId, roleIds);
	}

	/**
	 * Updates the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @param  name the role's name
	 * @param  titleMap the role's title map (optionally <code>null</code>)
	 * @param  descriptionMap the role's description map (optionally
	 *         <code>null</code>)
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @return the role with the primary key
	 * @throws PortalException if a role with the primary key could not be
	 *         found or if the role's name was invalid or if the user did not
	 *         have permission to update the role
	 * @throws SystemException if a system exception occurred
	 */
	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		return roleLocalService.updateRole(
			roleId, name, titleMap, descriptionMap, subtype);
	}

	protected void checkUserRolesPermission(long userId, long[] roleIds)
		throws PortalException {

		for (int i = 0; i < roleIds.length; i++) {
			RolePermissionUtil.check(
				getPermissionChecker(), roleIds[i], ActionKeys.ASSIGN_MEMBERS);
		}
	}

}