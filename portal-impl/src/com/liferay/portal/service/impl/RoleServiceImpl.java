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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.membershippolicy.OrganizationMembershipPolicyUtil;
import com.liferay.portal.kernel.security.membershippolicy.RoleMembershipPolicyUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.RoleServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, unassigning, checking,
 * deleting, and updating roles. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 */
public class RoleServiceImpl extends RoleServiceBaseImpl {

	/**
	 * Adds a role. The user is reindexed after role is added.
	 *
	 * @param  className the name of the class for which the role is created
	 * @param  classPK the primary key of the class for which the role is
	 *         created (optionally <code>0</code>)
	 * @param  name the role's name
	 * @param  titleMap the role's localized titles (optionally
	 *         <code>null</code>)
	 * @param  descriptionMap the role's localized descriptions (optionally
	 *         <code>null</code>)
	 * @param  type the role's type (optionally <code>0</code>)
	 * @param  subtype the role's subtype (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the expando bridge attributes for the
	 *         role.
	 * @return the role
	 */
	@Override
	public Role addRole(
			String className, long classPK, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int type, String subtype, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(getPermissionChecker(), ActionKeys.ADD_ROLE);

		User user = getUser();

		Role role = roleLocalService.addRole(
			user.getUserId(), className, classPK, name, titleMap,
			descriptionMap, type, subtype, serviceContext);

		if (type == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.verifyPolicy(role);
		}
		else if (type == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.verifyPolicy(role);
		}
		else {
			RoleMembershipPolicyUtil.verifyPolicy(role);
		}

		return role;
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	@Override
	public void addUserRoles(long userId, long[] roleIds)
		throws PortalException {

		if (roleIds.length == 0) {
			return;
		}

		checkUserRolesPermission(userId, roleIds);

		RoleMembershipPolicyUtil.checkRoles(new long[] {userId}, roleIds, null);

		roleLocalService.addUserRoles(userId, roleIds);

		RoleMembershipPolicyUtil.propagateRoles(
			new long[] {userId}, roleIds, null);
	}

	/**
	 * Deletes the role with the primary key and its associated permissions.
	 *
	 * @param roleId the primary key of the role
	 */
	@Override
	public void deleteRole(long roleId) throws PortalException {
		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.DELETE);

		roleLocalService.deleteRole(roleId);
	}

	@Override
	public Role fetchRole(long roleId) throws PortalException {
		Role role = roleLocalService.fetchRole(roleId);

		if (role != null) {
			RolePermissionUtil.check(
				getPermissionChecker(), roleId, ActionKeys.VIEW);
		}

		return role;
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the roles associated with the group
	 */
	@Override
	public List<Role> getGroupRoles(long groupId) throws PortalException {
		List<Role> roles = roleLocalService.getGroupRoles(groupId);

		return filterRoles(roles);
	}

	@Override
	public List<Role> getGroupRolesAndTeamRoles(
		long companyId, String keywords, List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId, int start,
		int end) {

		return roleFinder.filterFindByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId, start, end);
	}

	@Override
	public int getGroupRolesAndTeamRolesCount(
		long companyId, String keywords, List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId) {

		return roleFinder.filterCountByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId);
	}

	/**
	 * Returns the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @return the role with the primary key
	 */
	@Override
	public Role getRole(long roleId) throws PortalException {
		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.VIEW);

		return roleLocalService.getRole(roleId);
	}

	/**
	 * Returns the role with the name in the company.
	 *
	 * <p>
	 * The method searches the system roles map first for default roles. If a
	 * role with the name is not found, then the method will query the database.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the role's name
	 * @return the role with the name
	 */
	@Override
	public Role getRole(long companyId, String name) throws PortalException {
		Role role = roleLocalService.getRole(companyId, name);

		RolePermissionUtil.check(
			getPermissionChecker(), role.getRoleId(), ActionKeys.VIEW);

		return role;
	}

	@Override
	public List<Role> getRoles(int type, String subtype)
		throws PortalException {

		return filterRoles(roleLocalService.getRoles(type, subtype));
	}

	@Override
	public List<Role> getRoles(long companyId, int[] types)
		throws PortalException {

		return filterRoles(roleLocalService.getRoles(companyId, types));
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 */
	@Override
	public List<Role> getUserGroupGroupRoles(long userId, long groupId)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<Role> roles = roleLocalService.getUserGroupGroupRoles(
			userId, groupId);

		return filterRoles(roles);
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the user's roles within the user group
	 */
	@Override
	public List<Role> getUserGroupRoles(long userId, long groupId)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<Role> roles = roleLocalService.getUserGroupRoles(userId, groupId);

		return filterRoles(roles);
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param  userId the primary key of the user
	 * @param  groups the groups (optionally <code>null</code>)
	 * @return the union of all the user's roles within the groups
	 */
	@Override
	public List<Role> getUserRelatedRoles(long userId, List<Group> groups)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<Role> roles = roleLocalService.getUserRelatedRoles(userId, groups);

		return filterRoles(roles);
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the roles associated with the user
	 */
	@Override
	public List<Role> getUserRoles(long userId) throws PortalException {
		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		List<Role> roles = roleLocalService.getUserRoles(userId);

		return filterRoles(roles);
	}

	/**
	 * Returns <code>true</code> if the user is associated with the named
	 * regular role.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  name the name of the role
	 * @param  inherited whether to include the user's inherited roles in the
	 *         search
	 * @return <code>true</code> if the user is associated with the regular
	 *         role; <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		return roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	/**
	 * Returns <code>true</code> if the user has any one of the named regular
	 * roles.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the company
	 * @param  names the names of the roles
	 * @param  inherited whether to include the user's inherited roles in the
	 *         search
	 * @return <code>true</code> if the user has any one of the regular roles;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws PortalException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.VIEW);

		return roleLocalService.hasUserRoles(
			userId, companyId, names, inherited);
	}

	@Override
	public List<Role> search(
		long companyId, String keywords, Integer[] types,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Role> obc) {

		return roleFinder.filterFindByKeywords(
			companyId, keywords, types, params, start, end, obc);
	}

	@Override
	public int searchCount(
		long companyId, String keywords, Integer[] types,
		LinkedHashMap<String, Object> params) {

		return roleFinder.filterCountByKeywords(
			companyId, keywords, types, params);
	}

	/**
	 * Removes the matching roles associated with the user. The user is
	 * reindexed after the roles are removed.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	@Override
	public void unsetUserRoles(long userId, long[] roleIds)
		throws PortalException {

		if (roleIds.length == 0) {
			return;
		}

		checkUserRolesPermission(userId, roleIds);

		RoleMembershipPolicyUtil.checkRoles(new long[] {userId}, null, roleIds);

		roleLocalService.unsetUserRoles(userId, roleIds);

		RoleMembershipPolicyUtil.propagateRoles(
			new long[] {userId}, null, roleIds);
	}

	/**
	 * Updates the role with the primary key.
	 *
	 * @param  roleId the primary key of the role
	 * @param  name the role's new name
	 * @param  titleMap the new localized titles (optionally <code>null</code>)
	 *         to replace those existing for the role
	 * @param  descriptionMap the new localized descriptions (optionally
	 *         <code>null</code>) to replace those existing for the role
	 * @param  subtype the role's new subtype (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the expando bridge attributes for the
	 *         role.
	 * @return the role with the primary key
	 */
	@Override
	public Role updateRole(
			long roleId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String subtype,
			ServiceContext serviceContext)
		throws PortalException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		Role oldRole = rolePersistence.findByPrimaryKey(roleId);

		ExpandoBridge oldExpandoBridge = oldRole.getExpandoBridge();

		Map<String, Serializable> oldExpandoAttributes =
			oldExpandoBridge.getAttributes();

		Role role = roleLocalService.updateRole(
			roleId, name, titleMap, descriptionMap, subtype, serviceContext);

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			OrganizationMembershipPolicyUtil.verifyPolicy(
				role, oldRole, oldExpandoAttributes);
		}
		else if (role.getType() == RoleConstants.TYPE_SITE) {
			SiteMembershipPolicyUtil.verifyPolicy(
				role, oldRole, oldExpandoAttributes);
		}
		else {
			RoleMembershipPolicyUtil.verifyPolicy(
				role, oldRole, oldExpandoAttributes);
		}

		return role;
	}

	protected void checkUserRolesPermission(long userId, long[] roleIds)
		throws PortalException {

		for (long roleId : roleIds) {
			RolePermissionUtil.check(
				getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);
		}
	}

	protected List<Role> filterRoles(List<Role> roles) throws PortalException {
		List<Role> filteredRoles = new ArrayList<>();

		for (Role role : roles) {
			if (RolePermissionUtil.contains(
					getPermissionChecker(), role.getRoleId(),
					ActionKeys.VIEW)) {

				filteredRoles.add(role);
			}
		}

		return filteredRoles;
	}

}