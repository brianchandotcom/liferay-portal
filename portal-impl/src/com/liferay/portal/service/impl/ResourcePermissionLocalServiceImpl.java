/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ResourcePermissionsThreadLocal;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * Provides the local service for accessing, adding, checking, deleting,
 * granting, and revoking resource permissions.
 *
 * <p>
 * Before attempting to read any of the documentation for this class, first read
 * {@link com.liferay.portal.model.impl.ResourcePermissionImpl} for an
 * explanation of scoping.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Connor McKay
 */
public class ResourcePermissionLocalServiceImpl
	extends ResourcePermissionLocalServiceBaseImpl {

	/**
	 * @see com.liferay.portal.verify.VerifyPermission#fixOrganizationRolePermissions
	 */
	public static final String[] EMPTY_ACTION_IDS = {null};

	/**
	 * Grants the role permission at the scope to perform the action on
	 * resources of the type. Existing actions are retained.
	 *
	 * <p>
	 * This method cannot be used to grant individual scope permissions, but is
	 * only intended for adding permissions at the company, group, and
	 * group-template scopes. For example, this method could be used to grant a
	 * company scope permission to edit message board posts.
	 * </p>
	 *
	 * <p>
	 * If a company scope permission is granted to resources that the role
	 * already had group scope permissions to, the group scope permissions are
	 * deleted. Likewise, if a group scope permission is granted to resources
	 * that the role already had company scope permissions to, the company scope
	 * permissions are deleted. Be aware that this latter behavior can result in
	 * an overall reduction in permissions for the role.
	 * </p>
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope. This method only supports company, group, and
	 *         group-template scope.
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if scope was set to individual scope or if a role
	 *         with the primary key or a resource action with the name and
	 *         action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void addResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		if (scope == ResourceConstants.SCOPE_COMPANY) {

			// Remove group permission

			removeResourcePermissions(
				companyId, name, ResourceConstants.SCOPE_GROUP, roleId,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {

			// Remove company permission

			removeResourcePermissions(
				companyId, name, ResourceConstants.SCOPE_COMPANY, roleId,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			throw new NoSuchResourcePermissionException();
		}

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, 0, new String[] {actionId},
			ResourcePermissionConstants.OPERATOR_ADD);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Grants the role permissions at the scope to perform the actions on all
	 * resources of the type. Existing actions are retained.
	 *
	 * <p>
	 * This method should only be used to add default permissions to existing
	 * resources en masse during upgrades or while verifying permissions. For
	 * example, this method could be used to grant site members individual scope
	 * permissions to view all blog posts.
	 * </p>
	 *
	 * @param  resourceName the resource's name, which can be either a class
	 *         name or a portlet ID
	 * @param  roleName the role's name
	 * @param  scope the scope
	 * @param  resourceActionBitwiseValue the bitwise IDs of the actions
	 * @throws SystemException if a system exception occurred
	 */
	public void addResourcePermissions(
			String resourceName, String roleName, int scope,
			long resourceActionBitwiseValue)
		throws SystemException {

		List<Role> roles = rolePersistence.findByName(roleName);

		if (roles.isEmpty()) {
			return;
		}

		Session session = resourcePermissionPersistence.openSession();

		try {

			// Update existing resource permissions

			String sql = CustomSQLUtil.get(_UPDATE_ACTION_IDS);

			sql = StringUtil.replace(
				sql, "[$ROLE_ID$]",
				ListUtil.toString(roles, Role.ROLE_ID_ACCESSOR));

			SQLQuery sqlQuery = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(resourceActionBitwiseValue);
			qPos.add(resourceActionBitwiseValue);
			qPos.add(resourceName);
			qPos.add(scope);

			sqlQuery.executeUpdate();

			// Add missing resource permissions

			sql = CustomSQLUtil.get(_FIND_MISSING_RESOURCE_PERMISSIONS);

			sqlQuery = session.createSQLQuery(sql);

			sqlQuery.addScalar("TEMP_TABLE.companyId", Type.LONG);
			sqlQuery.addScalar("TEMP_TABLE.name", Type.STRING);
			sqlQuery.addScalar("TEMP_TABLE.scope", Type.INTEGER);
			sqlQuery.addScalar("TEMP_TABLE.primKey", Type.STRING);
			sqlQuery.addScalar("Role_.roleId", Type.LONG);

			qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(resourceName);
			qPos.add(scope);
			qPos.add(roleName);

			List<Object[]> resourcePermissionArrays = sqlQuery.list(true);

			if (resourcePermissionArrays.isEmpty()) {
				return;
			}

			for (Object[] resourcePermissionArray : resourcePermissionArrays) {
				long resourcePermissionId = counterLocalService.increment(
					ResourcePermission.class.getName());

				ResourcePermission resourcePermission =
					resourcePermissionPersistence.create(resourcePermissionId);

				resourcePermission.setCompanyId(
					(Long)resourcePermissionArray[0]);
				resourcePermission.setName((String)resourcePermissionArray[1]);
				resourcePermission.setScope(
					(Integer)resourcePermissionArray[2]);
				resourcePermission.setPrimKey(
					(String)resourcePermissionArray[3]);
				resourcePermission.setRoleId((Long)resourcePermissionArray[4]);
				resourcePermission.setActionIds(resourceActionBitwiseValue);

				session.save(resourcePermission);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			resourcePermissionPersistence.closeSession(session);

			resourcePermissionPersistence.clearCache();
		}
	}

	/**
	 * Deletes all resource permissions at the scope to resources of the type.
	 * This method should not be confused with any of the
	 * <code>removeResourcePermission</code> methods, as its purpose is very
	 * different. This method should only be used for deleting resource
	 * permissions that refer to a resource when that resource is deleted. For
	 * example this method could be used to delete all individual scope
	 * permissions to a blog post when it is deleted.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteResourcePermissions(
			long companyId, String name, int scope, long primKey)
		throws PortalException, SystemException {

		deleteResourcePermissions(
			companyId, name, scope, String.valueOf(primKey));
	}

	/**
	 * Deletes all resource permissions at the scope to resources of the type.
	 * This method should not be confused with any of the
	 * <code>removeResourcePermission</code> methods, as its purpose is very
	 * different. This method should only be used for deleting resource
	 * permissions that refer to a resource when that resource is deleted. For
	 * example this method could be used to delete all individual scope
	 * permissions to a blog post when it is deleted.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteResourcePermissions(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S_P(
				companyId, name, scope, primKey);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			deleteResourcePermission(
				resourcePermission.getResourcePermissionId());
		}
	}

	/**
	 * Returns the intersection of action IDs the role has permission at the
	 * scope to perform on resources of the type.
	 *
	 * @param  companyId he primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionIds the action IDs
	 * @return the intersection of action IDs the role has permission at the
	 *         scope to perform on resources of the type
	 * @throws PortalException if a resouce action could not be found for any
	 *         one of the actions on the resource
	 * @throws SystemException if a system exception occurred
	 */
	public List<String> getAvailableResourcePermissionActionIds(
			long companyId, String name, int scope, String primKey, long roleId,
			Collection<String> actionIds)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			return Collections.emptyList();
		}

		List<String> availableActionIds = new ArrayList<String>(
			actionIds.size());

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			if (hasActionId(resourcePermission, resourceAction)) {
				availableActionIds.add(actionId);
			}
		}

		return availableActionIds;
	}

	public Map<Long, Set<String>> getAvailableResourcePermissionActionIds(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, Collection<String> actionIds)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S_P_R(
				companyId, name, scope, primKey, roleIds);

		if (resourcePermissions.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Long, Set<String>> roleIdsToActionIds =
			new HashMap<Long, Set<String>>();

		for (ResourcePermission resourcePermission : resourcePermissions) {
			long roleId = resourcePermission.getRoleId();

			Set<String> availableActionIds = roleIdsToActionIds.get(roleId);

			if (availableActionIds != null) {
				continue;
			}

			availableActionIds = new HashSet<String>();

			roleIdsToActionIds.put(roleId, availableActionIds);

			for (String actionId : actionIds) {
				ResourceAction resourceAction =
					resourceActionLocalService.getResourceAction(
						name, actionId);

				if (hasActionId(resourcePermission, resourceAction)) {
					availableActionIds.add(actionId);
				}
			}
		}

		return roleIdsToActionIds;
	}

	/**
	 * Returns the resource permission for the role at the scope to perform the
	 * actions on resources of the type.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @return the resource permission for the role at the scope to perform the
	 *         actions on resources of the type
	 * @throws PortalException if no matching resources could be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission getResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId)
		throws PortalException, SystemException {

		return resourcePermissionPersistence.findByC_N_S_P_R(
			companyId, name, scope, primKey, roleId);
	}

	/**
	 * Returns all the resource permissions at the scope of the type.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @return the resource permissions at the scope of the type
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> getResourcePermissions(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return resourcePermissionPersistence.findByC_N_S_P(
			companyId, name, scope, primKey);
	}

	/**
	 * Returns the number of resource permissions at the scope of the type.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @return the number of resource permissions at the scope of the type
	 * @throws SystemException if a system exception occurred
	 */
	public int getResourcePermissionsCount(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return resourcePermissionPersistence.countByC_N_S_P(
			companyId, name, scope, primKey);
	}

	/**
	 * Returns the resource permissions that apply to the resource.
	 *
	 * @param  companyId the primary key of the resource's company
	 * @param  groupId the primary key of the resource's group
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @return the resource permissions associated with the resource
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> getResourceResourcePermissions(
			long companyId, long groupId, String name, String primKey)
		throws SystemException {

		return resourcePermissionFinder.findByResource(
			companyId, groupId, name, primKey);
	}

	/**
	 * Returns all the resource permissions for the role.
	 *
	 * @param  roleId the primary key of the role
	 * @return the resource permissions for the role
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> getRoleResourcePermissions(long roleId)
		throws SystemException {

		return resourcePermissionPersistence.findByRoleId(roleId);
	}

	/**
	 * Returns a range of all the resource permissions for the role at the
	 * scopes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  roleId the primary key of the role
	 * @param  scopes the scopes
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of resource permissions for the role at the scopes
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> getRoleResourcePermissions(
			long roleId, int[] scopes, int start, int end)
		throws SystemException {

		return resourcePermissionFinder.findByR_S(roleId, scopes, start, end);
	}

	/**
	 * Returns all the resource permissions where scope = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  scopes the scopes
	 * @return the resource permissions where scope = any &#63;
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> getScopeResourcePermissions(int[] scopes)
		throws SystemException {

		return resourcePermissionPersistence.findByScope(scopes);
	}

	/**
	 * Returns <code>true</code> if the resource permission grants permission to
	 * perform the resource action. Note that this method does not ensure that
	 * the resource permission refers to the same type of resource as the
	 * resource action.
	 *
	 * @param  resourcePermission the resource permission
	 * @param  resourceAction the resource action
	 * @return <code>true</code> if the resource permission grants permission to
	 *         perform the resource action
	 */
	public boolean hasActionId(
		ResourcePermission resourcePermission, ResourceAction resourceAction) {

		long actionIds = resourcePermission.getActionIds();
		long bitwiseValue = resourceAction.getBitwiseValue();

		if ((actionIds & bitwiseValue) == bitwiseValue) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the roles have permission at the scope to
	 * perform the action on the resources.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  resources the resources
	 * @param  roleIds the primary keys of the roles
	 * @param  actionId the action ID
	 * @return <code>true</code> if any one of the roles has permission to
	 *         perform the action on any one of the resources;
	 *         <code>false</code> otherwise
	 * @throws PortalException if any one of the roles with the primary keys
	 *         could not be found or if a resource action with the name and
	 *         action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasResourcePermission(
			List<Resource> resources, long[] roleIds, String actionId)
		throws PortalException, SystemException {

		// Iterate the list of resources in reverse order to test permissions
		// from company scope to individual scope because it is more likely that
		// a permission is assigned at a higher scope. Optimizing this method to
		// one SQL call may actually slow things down since most of the calls
		// will pull from the cache after the first request.

		for (int i = resources.size() - 1; i >= 0; i--) {
			Resource resource = resources.get(i);

			if (hasResourcePermission(
					resource.getCompanyId(), resource.getName(),
					resource.getScope(), resource.getPrimKey(), roleIds,
					actionId)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the role has permission at the scope to
	 * perform the action on resources of the type.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @return <code>true</code> if the role has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			return false;
		}

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		if (hasActionId(resourcePermission, resourceAction)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the roles have permission at the scope to
	 * perform the action on resources of the type.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleIds the primary keys of the roles
	 * @param  actionId the action ID
	 * @return <code>true</code> if any one of the roles has permission to
	 *         perform the action on the resource; <code>false</code> otherwise
	 * @throws PortalException if any one of the roles with the primary keys
	 *         could not be found or if a resource action with the name and
	 *         action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, String actionId)
		throws PortalException, SystemException {

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if ((roleIds.length >
				PropsValues.
					PERMISSIONS_ROLE_RESOURCE_PERMISSION_QUERY_THRESHOLD) &&
			!dbType.equals(DB.TYPE_DERBY) &&
			!dbType.equals(DB.TYPE_JDATASTORE) &&
			!dbType.equals(DB.TYPE_SAP)) {

			int count = resourcePermissionFinder.countByC_N_S_P_R_A(
				companyId, name, scope, primKey, roleIds,
				resourceAction.getBitwiseValue());

			if (count > 0) {
				return true;
			}
		}
		else {
			List<ResourcePermission> resourcePermissions =
				resourcePermissionPersistence.findByC_N_S_P_R(
					companyId, name, scope, primKey, roleIds);

			if (resourcePermissions.isEmpty()) {
				return false;
			}

			for (ResourcePermission resourcePermission : resourcePermissions) {
				if (hasActionId(resourcePermission, resourceAction)) {
					return true;
				}
			}

		}

		return false;
	}

	public boolean[] hasResourcePermissions(
			long companyId, String name, int scope, String primKey,
			long[] roleIds, String actionId)
		throws PortalException, SystemException {

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S_P_R(
				companyId, name, scope, primKey, roleIds);

		boolean[] hasResourcePermissions = new boolean[roleIds.length];

		if (resourcePermissions.isEmpty()) {
			return hasResourcePermissions;
		}

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (hasActionId(resourcePermission, resourceAction)) {
				long roleId = resourcePermission.getRoleId();

				for (int i = 0; i < roleIds.length; i++) {
					if (roleIds[i] == roleId) {
						hasResourcePermissions[i] = true;
					}
				}
			}
		}

		return hasResourcePermissions;
	}

	/**
	 * Returns <code>true</code> if the role has permission at the scope to
	 * perform the action on the resource.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @return <code>true</code> if the role has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasScopeResourcePermission(
			long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S(companyId, name, scope);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (hasResourcePermission(
					companyId, name, scope, resourcePermission.getPrimKey(),
					roleId, actionId)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Reassigns all the resource permissions from the source role to the
	 * destination role, and deletes the source role.
	 *
	 * @param  fromRoleId the primary key of the source role
	 * @param  toRoleId the primary key of the destination role
	 * @throws PortalException if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void mergePermissions(long fromRoleId, long toRoleId)
		throws PortalException, SystemException {

		Role fromRole = rolePersistence.findByPrimaryKey(fromRoleId);
		Role toRole = rolePersistence.findByPrimaryKey(toRoleId);

		if (fromRole.getType() != toRole.getType()) {
			throw new PortalException("Role types are mismatched");
		}
		else if (PortalUtil.isSystemRole(toRole.getName())) {
			throw new PortalException("Cannot move permissions to system role");
		}
		else if (PortalUtil.isSystemRole(fromRole.getName())) {
			throw new PortalException(
				"Cannot move permissions from system role");
		}

		List<ResourcePermission> resourcePermissions =
			getRoleResourcePermissions(fromRoleId);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermission.setRoleId(toRoleId);

			resourcePermissionPersistence.update(resourcePermission);
		}

		roleLocalService.deleteRole(fromRoleId);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Grants the role default permissions to all the resources of the type and
	 * at the scope stored in the resource permission, deletes the resource
	 * permission, and deletes the resource permission's role if it has no
	 * permissions remaining.
	 *
	 * @param  resourcePermissionId the primary key of the resource permission
	 * @param  toRoleId the primary key of the role
	 * @throws PortalException if a resource permission or role with the primary
	 *         key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission = getResourcePermission(
			resourcePermissionId);

		long companyId = resourcePermission.getCompanyId();
		String name = resourcePermission.getName();
		int scope = resourcePermission.getScope();
		String primKey = resourcePermission.getPrimKey();
		long fromRoleId = resourcePermission.getRoleId();

		Role toRole = roleLocalService.getRole(toRoleId);

		List<String> actionIds = null;

		if (toRole.getType() == RoleConstants.TYPE_REGULAR) {
			actionIds = ResourceActionsUtil.getModelResourceActions(name);
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceGroupDefaultActions(
				name);
		}

		setResourcePermissions(
			companyId, name, scope, primKey, toRoleId,
			actionIds.toArray(new String[actionIds.size()]));

		resourcePermissionPersistence.remove(resourcePermissionId);

		List<ResourcePermission> resourcePermissions =
			getRoleResourcePermissions(fromRoleId);

		if (resourcePermissions.isEmpty()) {
			roleLocalService.deleteRole(fromRoleId);
		}
	}

	/**
	 * Revokes permission at the scope from the role to perform the action on
	 * resources of the type. For example, this method could be used to revoke a
	 * group scope permission to edit blog posts.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void removeResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, 0, new String[] {actionId},
			ResourcePermissionConstants.OPERATOR_REMOVE);

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Revokes all permissions at the scope from the role to perform the action
	 * on resources of the type. For example, this method could be used to
	 * revoke all individual scope permissions to edit blog posts from site
	 * members.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void removeResourcePermissions(
			long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S(companyId, name, scope);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			updateResourcePermission(
				companyId, name, scope, resourcePermission.getPrimKey(), roleId,
				0, new String[] {actionId},
				ResourcePermissionConstants.OPERATOR_REMOVE);
		}

		PermissionCacheUtil.clearCache();
	}

	/**
	 * Updates the role's permissions at the scope, setting the actions that can
	 * be performed on resources of the type, also setting the owner of any
	 * newly created resource permissions. Existing actions are replaced.
	 *
	 * <p>
	 * This method can be used to set permissions at any scope, but it is
	 * generally only used at the individual scope. For example, it could be
	 * used to set the guest permissions on a blog post.
	 * </p>
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  ownerId the primary key of the owner (generally the user that
	 *         created the resource)
	 * @param  actionIds the action IDs of the actions
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setOwnerResourcePermissions(
			long companyId, String name, int scope, String primKey, long roleId,
			long ownerId, String[] actionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, ownerId, actionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	/**
	 * Updates the role's permissions at the scope, setting the actions that can
	 * be performed on resources of the type. Existing actions are replaced.
	 *
	 * <p>
	 * This method can be used to set permissions at any scope, but it is
	 * generally only used at the individual scope. For example, it could be
	 * used to set the guest permissions on a blog post.
	 * </p>
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionIds the action IDs of the actions
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setResourcePermissions(
			long companyId, String name, int scope, String primKey, long roleId,
			String[] actionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, 0, actionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	/**
	 * Updates the role's permissions at the scope, setting the actions that can
	 * be performed on resources of the type. Existing actions are replaced.
	 *
	 * <p>
	 * This method can be used to set permissions at any scope, but it is
	 * generally only used at the individual scope. For example, it could be
	 * used to set the guest permissions on a blog post.
	 * </p>
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleIdsToActionIds a map of role IDs to action IDs of the actions
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setResourcePermissions(
			long companyId, String name, int scope, String primKey,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, 0, roleIdsToActionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	protected void doUpdateResourcePermission(
			long companyId, String name, int scope, String primKey,
			long ownerId, long roleId, String[] actionIds, int operator)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission = null;

		Map<Long, ResourcePermission> resourcePermissionsMap =
			ResourcePermissionsThreadLocal.getResourcePermissions();

		if (resourcePermissionsMap != null) {
			resourcePermission = resourcePermissionsMap.get(roleId);
		}
		else {
			resourcePermission = resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);
		}

		if (resourcePermission == null) {
			if (((operator == ResourcePermissionConstants.OPERATOR_ADD) ||
				 (operator == ResourcePermissionConstants.OPERATOR_SET)) &&
				(actionIds.length == 0)) {

				return;
			}

			if (operator == ResourcePermissionConstants.OPERATOR_REMOVE) {
				return;
			}

			long resourcePermissionId = counterLocalService.increment(
				ResourcePermission.class.getName());

			resourcePermission = resourcePermissionPersistence.create(
				resourcePermissionId);

			resourcePermission.setCompanyId(companyId);
			resourcePermission.setName(name);
			resourcePermission.setScope(scope);
			resourcePermission.setPrimKey(primKey);
			resourcePermission.setRoleId(roleId);
			resourcePermission.setOwnerId(ownerId);
		}

		long actionIdsLong = resourcePermission.getActionIds();

		if (operator == ResourcePermissionConstants.OPERATOR_SET) {
			actionIdsLong = 0;
		}

		for (String actionId : actionIds) {
			if (actionId == null) {
				break;
			}

			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			if ((operator == ResourcePermissionConstants.OPERATOR_ADD) ||
				(operator == ResourcePermissionConstants.OPERATOR_SET)) {

				actionIdsLong |= resourceAction.getBitwiseValue();
			}
			else {
				actionIdsLong =
					actionIdsLong & (~resourceAction.getBitwiseValue());
			}
		}

		resourcePermission.setActionIds(actionIdsLong);

		resourcePermissionPersistence.update(resourcePermission);

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(name, primKey);
	}

	protected void doUpdateResourcePermission(
			long companyId, String name, int scope, String primKey,
			long ownerId, Map<Long, String[]> roleIdsToActionIds, int operator)
		throws PortalException, SystemException {

		boolean flushEnabled = PermissionThreadLocal.isFlushEnabled();

		PermissionThreadLocal.setIndexEnabled(false);

		try {
			for (Map.Entry<Long, String[]> entry :
					roleIdsToActionIds.entrySet()) {

				long roleId = entry.getKey();
				String[] actionIds = entry.getValue();

				doUpdateResourcePermission(
					companyId, name, scope, primKey, ownerId, roleId, actionIds,
					operator);
			}
		}
		finally {
			PermissionThreadLocal.setIndexEnabled(flushEnabled);

			PermissionCacheUtil.clearCache();

			SearchEngineUtil.updatePermissionFields(name, primKey);
		}
	}

	/**
	 * Updates the role's permissions at the scope, either adding to, removing
	 * from, or setting the actions that can be performed on resources of the
	 * type. Automatically creates a new resource permission if none exists, or
	 * deletes the existing resource permission if it no longer grants
	 * permissions to perform any action.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  ownerId the primary key of the owner
	 * @param  actionIds the action IDs of the actions
	 * @param  operator whether to add to, remove from, or set/replace the
	 *         existing actions. Possible values can be found in {@link
	 *         ResourcePermissionConstants}.
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	protected void updateResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			long ownerId, String[] actionIds, int operator)
		throws PortalException, SystemException {

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_HYPERSONIC)) {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, ownerId, roleId, actionIds,
				operator);

			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(companyId);
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(scope);
		sb.append(StringPool.POUND);
		sb.append(primKey);
		sb.append(StringPool.POUND);
		sb.append(roleId);

		Class<?> clazz = getClass();

		String groupName = clazz.getName();

		String key = sb.toString();

		Lock lock = LockRegistry.allocateLock(groupName, key);

		lock.lock();

		try {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, ownerId, roleId, actionIds,
				operator);
		}
		finally {
			lock.unlock();

			LockRegistry.freeLock(groupName, key);
		}
	}

	/**
	 * Updates the role's permissions at the scope, either adding to, removing
	 * from, or setting the actions that can be performed on resources of the
	 * type. Automatically creates a new resource permission if none exists, or
	 * deletes the existing resource permission if it no longer grants
	 * permissions to perform any action.
	 *
	 * <p>
	 * Depending on the scope, the value of <code>primKey</code> will have
	 * different meanings. For more information, see {@link
	 * com.liferay.portal.model.impl.ResourcePermissionImpl}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  ownerId the primary key of the owner
	 * @param  operator whether to add to, remove from, or set/replace the
	 *         existing actions. Possible values can be found in {@link
	 *         ResourcePermissionConstants}.
	 * @throws PortalException if a role with the primary key or a resource
	 *         action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	protected void updateResourcePermission(
			long companyId, String name, int scope, String primKey,
			long ownerId, Map<Long, String[]> roleIdsToActionIds, int operator)
		throws PortalException, SystemException {

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_HYPERSONIC)) {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, ownerId, roleIdsToActionIds,
				operator);

			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(companyId);
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(scope);
		sb.append(StringPool.POUND);
		sb.append(primKey);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.merge(roleIdsToActionIds.keySet()));

		Class<?> clazz = getClass();

		String groupName = clazz.getName();

		String key = sb.toString();

		Lock lock = LockRegistry.allocateLock(groupName, key);

		lock.lock();

		try {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, ownerId, roleIdsToActionIds,
				operator);
		}
		finally {
			lock.unlock();

			LockRegistry.freeLock(groupName, key);
		}
	}

	private static final String _FIND_MISSING_RESOURCE_PERMISSIONS =
		ResourcePermissionLocalServiceImpl.class.getName() +
			".findMissingResourcePermissions";

	private static final String _UPDATE_ACTION_IDS =
		ResourcePermissionLocalServiceImpl.class.getName() + ".updateActionIds";

}