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

import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.OrgGroupPermissionPK;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.PermissionComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionLocalServiceImpl extends PermissionLocalServiceBaseImpl {

	public Permission addPermission(
			long companyId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		if (permission == null) {
			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			permissionPersistence.update(permission, false);
		}

		return permission;
	}

	public List<Permission> addPermissions(
			long companyId, List<String> actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resourceId);

		permissions = ListUtil.copy(permissions);

		Set<String> actionIdsSet = new HashSet<String>();

		for (Permission permission : permissions) {
			actionIdsSet.add(permission.getActionId());
		}

		for (String actionId : actionIds) {
			if (actionIdsSet.contains(actionId)) {
				continue;
			}

			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			Permission permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			try {
				permissionPersistence.update(permission, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {actionId=" + actionId +
							", resourceId=" + resourceId + "}");
				}

				permission = permissionPersistence.fetchByA_R(
					actionId, resourceId, false);

				if (permission == null) {
					throw se;
				}
			}

			permissions.add(permission);
		}

		return permissions;
	}

	public List<Permission> addPermissions(
			long companyId, String name, long resourceId,
			boolean portletActions)
		throws SystemException {

		List<String> actionIds = null;

		if (portletActions) {
			actionIds = ResourceActionsUtil.getPortletResourceActions(name);
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceActions(name);
		}

		return addPermissions(companyId, actionIds, resourceId);
	}

	public void addUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		List<Permission> permissions = permissionFinder.findByU_R(
			userId, resourceId);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		userPersistence.addPermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public void checkPermissions(String name, List<String> actionIds)
		throws PortalException, SystemException {

		List<Resource> resources = resourceFinder.findByN_S(
			name, ResourceConstants.SCOPE_INDIVIDUAL);

		for (Resource resource : resources) {
			for (String actionId : actionIds) {
				Permission permission = permissionPersistence.fetchByA_R(
					actionId, resource.getResourceId());

				if (permission != null) {
					continue;
				}

				checkPermission(resource, actionId);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public List<String> getActions(List<Permission> permissions) {
		List<String> actionIds = new ArrayList<String>();

		Iterator<Permission> itr = permissions.iterator();

		while (itr.hasNext()) {
			Permission permission = itr.next();

			actionIds.add(permission.getActionId());
		}

		return actionIds;
	}

	public List<Permission> getGroupPermissions(long groupId, long resourceId)
		throws SystemException {

		return permissionFinder.findByG_R(groupId, resourceId);
	}

	public List<Permission> getGroupPermissions(
			long groupId, long companyId, String name, int scope,
			String primKey)
		throws SystemException {

		return permissionFinder.findByG_C_N_S_P(
			groupId, companyId, name, scope, primKey);
	}

	public long getLatestPermissionId() throws SystemException {
		List<Permission> permissions = permissionPersistence.findAll(
			0, 1, new PermissionComparator());

		if (permissions.size() == 0) {
			return 0;
		}
		else {
			Permission permission = permissions.get(0);

			return permission.getPermissionId();
		}
	}

	public List<Permission> getOrgGroupPermissions(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		return permissionFinder.findByO_G_R(
			organizationId, groupId, resourceId);
	}

	public List<Permission> getPermissions(
			long companyId, String[] actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = new ArrayList<Permission>();

		for (int i = 0; i < actionIds.length; i++) {
			Permission permission = addPermission(
				companyId, actionIds[i], resourceId);

			permissions.add(permission);
		}

		return permissions;
	}

	public List<Permission> getRolePermissions(long roleId)
		throws SystemException {

		return rolePersistence.getPermissions(roleId);
	}

	public List<Permission> getRolePermissions(long roleId, long resourceId)
		throws SystemException {

		return permissionFinder.findByR_R(roleId, resourceId);
	}

	public List<Permission> getUserPermissions(long userId, long resourceId)
		throws SystemException {

		return permissionFinder.findByU_R(userId, resourceId);
	}

	public List<Permission> getUserPermissions(
			long userId, long companyId, String name, int scope, String primKey)
		throws SystemException {

		return permissionFinder.findByU_C_N_S_P(
			userId, companyId, name, scope, primKey);
	}

	public boolean hasGroupPermission(
			long groupId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		// Return false if there is no permission based on the given action
		// id and resource id

		if (permission == null) {
			return false;
		}

		return groupPersistence.containsPermission(
			groupId, permission.getPermissionId());
	}

	public boolean hasRolePermission(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		if (permissionFinder.countByR_A_C(
				roleId, actionId, resourceCode.getCodeId()) > 0) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		Resource resource = resourcePersistence.fetchByC_P(
			resourceCode.getCodeId(), primKey);

		if (resource == null) {
			return false;
		}

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resource.getResourceId());

		if (permission == null) {
			return false;
		}

		return rolePersistence.containsPermission(
			roleId, permission.getPermissionId());
	}

	public boolean hasUserPermission(
			long userId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		// Return false if there is no permission based on the given action
		// id and resource id

		if (permission == null) {
			return false;
		}

		return userPersistence.containsPermission(
			userId, permission.getPermissionId());
	}

	/**
	 * Returns <code>true</code> if the user has permission to perform the
	 * action on the resource.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group containing the resource
	 * @param  resources representations of the resource at each scope level
	 *         returned by {@link
	 *         com.liferay.portal.security.permission.AdvancedPermissionChecker#getResources(
	 *         long, long, String, String, String)}
	 * @param  actionId the actionID
	 * @param  permissionCheckerBag the permission checker bag
	 * @return <code>true</code> if the user has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws PortalException if a resource action with the resource name and
	 *         action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasUserPermissions(
			long userId, long groupId, List<Resource> resources,
			String actionId, PermissionCheckerBag permissionCheckerBag)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		int block = 1;

		// Return false if there are no resources

		if (Validator.isNull(actionId) || resources.isEmpty()) {
			return false;
		}

		long[] resourceIds = null;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			resourceIds = new long[resources.size()];

			for (int i = 0; i < resources.size(); i++) {
				Resource resource = resources.get(i);

				resourceIds[i] = resource.getResourceId();
			}
		}

		List<Permission> permissions = null;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			permissions = permissionFinder.findByA_R(actionId, resourceIds);

			// Return false if there are no permissions

			if (permissions.size() == 0) {
				return false;
			}
		}

		// Record logs with the first resource id

		long resourceId = 0;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			resourceId = resourceIds[0];
		}
		else {
			resourceId = resources.get(0).getResourceId();
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		//List<Group> userGroups = permissionCheckerBag.getUserGroups();
		//List<Organization> userOrgs = permissionCheckerBag.getUserOrgs();
		//List<Group> userOrgGroups = permissionCheckerBag.getUserOrgGroups();
		//List<Group> userUserGroupGroups =
		//	permissionCheckerBag.getUserUserGroupGroups();
		List<Group> groups = permissionCheckerBag.getGroups();
		List<Role> roles = permissionCheckerBag.getRoles();

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Check the organization and group intersection table. Break out of
		// this method if the user has one of the permissions set at the
		// intersection because that takes priority.

		//if (checkOrgGroupPermission(userOrgs, userGroups, permissions)) {
		//	return true;
		//}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 1) {
			return hasUserPermissions_1(
				userId, resourceId, actionId, permissions, groups, groupId,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 2) {
			return hasUserPermissions_2(
				userId, resourceId, actionId, permissions, groups, groupId,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 3) {
			return hasUserPermissions_3(
				userId, resourceId, actionId, permissions, groups, roles,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 4) {
			return hasUserPermissions_4(
				userId, resourceId, actionId, permissions, groups, roles,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			return hasUserPermissions_5(
				userId, resourceId, actionId, permissions, roles, stopWatch,
				block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return hasUserPermissions_6(
				userId, resourceId, resources, actionId, roles, stopWatch,
				block);
		}

		return false;
	}

	public void setContainerResourcePermissions(
			String name, String roleName, String actionId)
		throws PortalException, SystemException {

		List<Company> companies = companyPersistence.findAll();

		for (Company company : companies) {
			setContainerResourcePermissions(
				company.getCompanyId(), name, roleName, actionId);
		}
	}

	public void setGroupPermissions(
			long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		List<Permission> permissions = permissionFinder.findByG_R(
			groupId, resourceId);

		for (Permission permission : permissions) {
			groupPersistence.removePermission(groupId, permission);
		}

		permissions = getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		groupPersistence.addPermissions(groupId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public void setGroupPermissions(
			String className, String classPK, long groupId,
			String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		long associatedGroupId = 0;

		if (className.equals(Organization.class.getName())) {
			long organizationId = GetterUtil.getLong(classPK);

			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationId);

			orgGroupPermissionFinder.removeByO_G_R(
				organizationId, groupId, resourceId);

			associatedGroupId = organization.getGroup().getGroupId();
		}
		else if (className.equals(UserGroup.class.getName())) {
			long userGroupId = GetterUtil.getLong(classPK);

			UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
				userGroupId);

			associatedGroupId = userGroup.getGroup().getGroupId();
		}

		setGroupPermissions(associatedGroupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			long organizationId, long groupId, String[] actionIds,
			long resourceId)
		throws PortalException, SystemException {

		Organization organization =
			organizationPersistence.findByPrimaryKey(organizationId);

		long orgGroupId = organization.getGroup().getGroupId();

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resourceId);

		for (Permission permission : permissions) {
			groupPersistence.removePermission(orgGroupId, permission);
		}

		permissions = getPermissions(
			organization.getCompanyId(), actionIds, resourceId);

		orgGroupPermissionFinder.removeByO_G_R(
			organizationId, groupId, resourceId);

		for (Permission permission : permissions) {
			OrgGroupPermissionPK pk = new OrgGroupPermissionPK(
				organizationId, groupId, permission.getPermissionId());

			OrgGroupPermission orgGroupPermission =
				orgGroupPermissionPersistence.create(pk);

			orgGroupPermissionPersistence.update(orgGroupPermission, false);
		}

		PermissionCacheUtil.clearCache();
	}

	public void setRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		if (scope == ResourceConstants.SCOPE_COMPANY) {

			// Remove group permission

			unsetRolePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_GROUP,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {

			// Remove company permission

			unsetRolePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_COMPANY,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			throw new NoSuchPermissionException();
		}

		Resource resource = resourceLocalService.addResource(
			companyId, name, scope, primKey);

		long resourceId = resource.getResourceId();

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		if (permission == null) {
			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			permissionPersistence.update(permission, false);
		}

		rolePersistence.addPermission(roleId, permission);

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(resourceId);
	}

	public void setRolePermissions(
			long roleId, long companyId, String name, int scope, String primKey,
			String[] actionIds)
		throws PortalException, SystemException {

		for (int i = 0; i < actionIds.length; i++) {
			String actionId = actionIds[i];

			setRolePermission(
				roleId, companyId, name, scope, primKey, actionId);
		}
	}

	public void setRolePermissions(
			long roleId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		List<Permission> permissions = permissionFinder.findByR_R(
			roleId, resourceId);

		rolePersistence.removePermissions(roleId, permissions);

		permissions = getPermissions(
			role.getCompanyId(), actionIds, resourceId);

		rolePersistence.addPermissions(roleId, permissions);

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(resourceId);
	}

	public void setUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		List<Permission> permissions = permissionFinder.findByU_R(
			userId, resourceId);

		userPersistence.removePermissions(userId, permissions);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		userPersistence.addPermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermission(long roleId, long permissionId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByPrimaryKey(
			permissionId);

		if (permission != null) {
			rolePersistence.removePermission(roleId, permission);
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode =
			resourceCodeLocalService.getResourceCode(
				companyId, name, scope);

		Resource resource = resourcePersistence.fetchByC_P(
			resourceCode.getCodeId(), primKey);

		if (resource != null) {
			Permission permission = permissionPersistence.fetchByA_R(
				actionId, resource.getResourceId());

			if (permission != null) {
				rolePersistence.removePermission(roleId, permission);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermissions(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		List<Permission> permissions = permissionFinder.findByA_C(
			actionId, resourceCode.getCodeId());

		for (Permission permission : permissions) {
			rolePersistence.removePermission(roleId, permission);
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = permissionFinder.findByU_A_R(
			userId, actionIds, resourceId);

		userPersistence.removePermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	protected void addRolePermissions(String roleName, Permission permission)
		throws SystemException {

		List<Role> roles = rolePersistence.findByName(roleName);

		for (Role role : roles) {
			rolePersistence.addPermission(role.getRoleId(), permission);
		}
	}

	protected boolean checkOrgGroupPermission(
			List<Organization> organizations, List<Group> groups,
			List<Permission> permissions)
		throws PortalException, SystemException {

		for (Permission permission : permissions) {
			if (checkOrgGroupPermission(organizations, groups, permission)) {
				return true;
			}
		}

		return false;
	}

	protected boolean checkOrgGroupPermission(
			List<Organization> organizations, List<Group> groups,
			Permission permission)
		throws PortalException, SystemException {

		// Do not check for an OrgGroupPermission intersection unless there is
		// at least one organization and one group to check

		if ((organizations.size() == 0) || (groups.size() == 0)) {
			return false;
		}

		// Do not check unless the OrgGroupPermission intersection contains at
		// least one permission

		List<OrgGroupPermission> orgGroupPermissions =
			orgGroupPermissionPersistence.findByPermissionId(
				permission.getPermissionId());

		if (orgGroupPermissions.size() == 0) {
			return false;
		}

		for (OrgGroupPermission orgGroupPermission : orgGroupPermissions) {
			if (orgGroupPermission.containsOrganization(organizations) &&
				orgGroupPermission.containsGroup(groups)) {

				return true;
			}
		}

		// Throw an exception so that we do not continue checking permissions.
		// The user has a specific permission given in the OrgGroupPermission
		// intersection that prohibits him from going further.

		throw new NoSuchPermissionException(
			"User has a permission in OrgGroupPermission that does not match");
	}

	protected void checkPermission(Resource resource, String actionId)
		throws PortalException, SystemException {

		long permissionId = counterLocalService.increment(
			Permission.class.getName());

		Permission permission = permissionPersistence.create(permissionId);

		permission.setCompanyId(resource.getCompanyId());
		permission.setActionId(actionId);
		permission.setResourceId(resource.getResourceId());

		permissionPersistence.update(permission, false);

		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(
				resource.getName());

		if (groupDefaultActions.contains(actionId)) {
			addRolePermissions(RoleConstants.SITE_MEMBER, permission);
		}

		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(
				resource.getName());

		if (guestDefaultActions.contains(actionId)) {
			addRolePermissions(RoleConstants.GUEST, permission);
		}

		addRolePermissions(RoleConstants.OWNER, permission);

		SearchEngineUtil.updatePermissionFields(
			resource.getResourceId());
	}

	protected boolean hasUserPermissions_1(
			long userId, long resourceId, String actionId,
			List<Permission> permissions, List<Group> groups, long groupId,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Is the user connected to one of the permissions via group or
		// organization roles?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsRoles(permissions, groups) > 0) {
				return true;
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user associated with groups or organizations that are directly
		// connected to one of the permissions?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsPermissions(
					permissions, groups) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user connected to one of the permissions via user roles?

		if (permissionFinder.countByUsersRoles(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user connected to one of the permissions via user group roles?

		if (permissionFinder.countByUserGroupRole(
				permissions, userId, groupId) > 0) {

			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user directly connected to one of the permissions?

		if (permissionFinder.countByUsersPermissions(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_2(
			long userId, long resourceId, String actionId,
			List<Permission> permissions, List<Group> groups, long groupId,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Call countByGroupsRoles, countByGroupsPermissions, countByUsersRoles,
		// countByUserGroupRole, and countByUsersPermissions in one method

		if (permissionFinder.containsPermissions_2(
				permissions, userId, groups, groupId)) {

			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_3(
			long userId, long resourceId, String actionId,
			List<Permission> permissions, List<Group> groups, List<Role> roles,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Is the user associated with groups or organizations that are directly
		// connected to one of the permissions?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsPermissions(
					permissions, groups) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user associated with a role that is directly connected to one
		// of the permissions?

		if (roles.size() > 0) {
			if (permissionFinder.countByRolesPermissions(
					permissions, roles) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		// Is the user directly connected to one of the permissions?

		if (permissionFinder.countByUsersPermissions(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_4(
			long userId, long resourceId, String actionId,
			List<Permission> permissions, List<Group> groups, List<Role> roles,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Call countByGroupsPermissions, countByRolesPermissions, and
		// countByUsersPermissions in one method

		if (permissionFinder.containsPermissions_4(
				permissions, userId, groups, roles)) {

			return true;
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_5(
			long userId, long resourceId, String actionId,
			List<Permission> permissions, List<Role> roles, StopWatch stopWatch,
			int block)
		throws SystemException {

		if (roles.size() > 0) {
			if (permissionFinder.countByRolesPermissions(
					permissions, roles) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_6(
			long userId, long resourceId, List<Resource> resources,
			String actionId, List<Role> roles, StopWatch stopWatch,
			int block)
		throws PortalException, SystemException {

		// Iterate the list of resources in reverse order to test permissions
		// from company scope to individual scope because it is more likely that
		// a permission is assigned at a higher scope. Optimizing this method
		// to one SQL call may actually slow things down since most of the calls
		// will pull from the cache after the first request.

		for (int i = resources.size() - 1; i >= 0; i--) {
			Resource resource = resources.get(i);

			for (Role role : roles) {
				if (resourcePermissionLocalService.hasResourcePermission(
						resource.getCompanyId(), resource.getName(),
						resource.getScope(), resource.getPrimKey(),
						role.getRoleId(), actionId)) {

					return true;
				}
			}
		}

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return false;
	}

	protected void logHasUserPermissions(
		long userId, long resourceId, String actionId, StopWatch stopWatch,
		int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Checking user permissions block " + block + " for " + userId +
				" " + resourceId + " " + actionId + " takes " +
					stopWatch.getTime() + " ms");
	}

	protected void setContainerResourcePermissions(
			long companyId, String name, String roleName, String actionId)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodePersistence.fetchByC_N_S(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL);

		if (resourceCode == null) {
			long codeId = counterLocalService.increment(
				ResourceCode.class.getName());

			resourceCode = resourceCodePersistence.create(codeId);

			resourceCode.setCompanyId(companyId);
			resourceCode.setName(name);
			resourceCode.setScope(ResourceConstants.SCOPE_INDIVIDUAL);

			resourceCodePersistence.update(resourceCode, false);
		}

		List<Group> groups = groupPersistence.findByCompanyId(companyId);

		for (Group group : groups) {
			String primKey = Long.toString(group.getGroupId());

			Resource resource = resourcePersistence.fetchByC_P(
				resourceCode.getCodeId(), primKey);

			if (resource == null) {
				long resourceId = counterLocalService.increment(
					Resource.class.getName());

				resource = resourcePersistence.create(resourceId);

				resource.setCodeId(resourceCode.getCodeId());
				resource.setPrimKey(primKey);

				resourcePersistence.update(resource, false);
			}

			Permission permission = permissionPersistence.fetchByA_R(
				actionId, resource.getResourceId());

			if (permission == null) {
				long permissionId = counterLocalService.increment(
					Permission.class.getName());

				permission = permissionPersistence.create(permissionId);

				permission.setCompanyId(companyId);
				permission.setActionId(actionId);
				permission.setResourceId(resource.getResourceId());

				permissionPersistence.update(permission, false);
			}

			if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) ||
				roleName.equals(RoleConstants.ORGANIZATION_USER) ||
				roleName.equals(RoleConstants.SITE_MEMBER)) {

				Role role = rolePersistence.findByC_N(companyId, roleName);

				permissionPersistence.addRole(
					permission.getPermissionId(), role);
			}
			else {
				long defaultUserId = userLocalService.getDefaultUserId(
					companyId);

				permissionPersistence.addUser(
					permission.getPermissionId(), defaultUserId);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PermissionLocalServiceImpl.class);

}