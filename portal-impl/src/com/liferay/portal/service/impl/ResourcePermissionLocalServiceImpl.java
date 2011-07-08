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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.ResourcePermissionsThreadLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ResourcePermissionLocalServiceImpl
	extends ResourcePermissionLocalServiceBaseImpl {

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

	public void addResourcePermissions(
			String resourceName, String roleName, int scope,
			long resourceActionBitwiseValue)
		throws SystemException{

		List<Role> roles = rolePersistence.findByName(roleName);

		for (Role role : roles) {
			List<String> primKeys = resourcePermissionFinder.findByC_N_S(
				role.getCompanyId(), resourceName, scope);

			for (String primKey : primKeys) {
				ResourcePermission resourcePermission =
					resourcePermissionPersistence.fetchByC_N_S_P_R(
						role.getCompanyId(), resourceName, scope, primKey,
						role.getRoleId());

				if (resourcePermission == null) {
					long resourcePermissionId = counterLocalService.increment(
						ResourcePermission.class.getName());

					resourcePermission = resourcePermissionPersistence.create(
						resourcePermissionId);

					resourcePermission.setCompanyId(role.getCompanyId());
					resourcePermission.setName(resourceName);
					resourcePermission.setScope(scope);
					resourcePermission.setPrimKey(primKey);
					resourcePermission.setRoleId(role.getRoleId());
				}

				long actionIdsLong = resourcePermission.getActionIds();

				actionIdsLong |= resourceActionBitwiseValue;

				resourcePermission.setActionIds(actionIdsLong);

				resourcePermissionPersistence.update(resourcePermission, false);
			}
		}
	}

	@Override
	public void deleteResourcePermission(long resourcePermissionId)
		throws PortalException, SystemException {

		resourcePermissionPersistence.remove(resourcePermissionId);
	}

	public void deleteResourcePermissions(
			long companyId, String name, int scope, long primKey)
		throws PortalException, SystemException {

		deleteResourcePermissions(
			companyId, name, scope, String.valueOf(primKey));
	}

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

	public List<String> getAvailableResourcePermissionActionIds(
			long companyId, String name, int scope, String primKey, long roleId,
			List<String> actionIds)
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

	public ResourcePermission getResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId)
		throws PortalException, SystemException {

		return resourcePermissionPersistence.findByC_N_S_P_R(
			companyId, name, scope, primKey, roleId);
	}

	public List<ResourcePermission> getResourcePermissions(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return resourcePermissionPersistence.findByC_N_S_P(
			companyId, name, scope, primKey);
	}

	public int getResourcePermissionsCount(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return resourcePermissionPersistence.countByC_N_S_P(
			companyId, name, scope, primKey);
	}

	public List<ResourcePermission> getRoleResourcePermissions(long roleId)
		throws SystemException {

		return resourcePermissionPersistence.findByRoleId(roleId);
	}

	public List<ResourcePermission> getRoleResourcePermissions(
			long roleId, int[] scopes, int start, int end)
		throws SystemException {

		return resourcePermissionFinder.findByR_S(roleId, scopes, start, end);
	}

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
	 * Returns <code>true</code> if the role has permission to perform the
	 * action on the resource.
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
	 * @param  scope the resource's scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @return <code>true</code> if the role has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws PortalException if a resource action with the resource name and
	 *         action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission = null;

		// It is faster to cache the results of the query without the role ID
		// and search the list manually than to use a single query.

		for (ResourcePermission curResourcePermission :
				resourcePermissionPersistence.findByC_N_S_P(
					companyId, name, scope, primKey)) {

			if (curResourcePermission.getRoleId() == roleId) {
				resourcePermission = curResourcePermission;

				break;
			}
		}

		if (resourcePermission == null) {
			return false;
		}

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		if (hasActionId(resourcePermission, resourceAction)) {
			return true;
		}
		else {
			return false;
		}
	}

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

			resourcePermissionPersistence.update(resourcePermission, false);
		}

		roleLocalService.deleteRole(fromRoleId);

		PermissionCacheUtil.clearCache();
	}

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

	public void removeResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, 0, new String[] {actionId},
			ResourcePermissionConstants.OPERATOR_REMOVE);

		PermissionCacheUtil.clearCache();
	}

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

	public void setContainerResourcePermissions(
			String name, String roleName, long actionIdsLong)
		throws SystemException {

		List<Role> roles = rolePersistence.findByName(roleName);

		for (Role role : roles) {
			List<Group> groups = groupPersistence.findByCompanyId(
				role.getCompanyId());

			for (Group group : groups) {
				setContainerResourcePermissions(
					group, role, name, actionIdsLong);
			}
		}
	}
	
	public void setOwnerResourcePermissions(
			long companyId, String name, int scope, String primKey, long roleId,
			long ownerId, String[] actionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, ownerId, actionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	public void setResourcePermissions(
			long companyId, String name, int scope, String primKey, long roleId,
			String[] actionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, 0, actionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	protected void doUpdateResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			long ownerId, String[] actionIds, int operator)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission = null;

		Map<Long, ResourcePermission> resourcePermissions =
			ResourcePermissionsThreadLocal.getResourcePermissions();

		if (resourcePermissions != null) {
			resourcePermission = resourcePermissions.get(roleId);
		}
		else {
			resourcePermission = resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);
		}

		long oldActionIds = 0;

		if (resourcePermission == null) {
			if ((operator == ResourcePermissionConstants.OPERATOR_REMOVE) ||
				(actionIds.length == 0)) {

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
		else {
			oldActionIds = resourcePermission.getActionIds();
		}

		long actionIdsLong = resourcePermission.getActionIds();

		if (operator == ResourcePermissionConstants.OPERATOR_SET) {
			actionIdsLong = 0;
		}

		for (String actionId : actionIds) {
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

		if (oldActionIds == actionIdsLong) {
			return;
		}

		if (actionIdsLong == 0) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
		else {
			resourcePermission.setActionIds(actionIdsLong);

			resourcePermissionPersistence.update(resourcePermission, false);
		}

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(name, primKey);
	}

	protected void setContainerResourcePermissions(
			Group group, Role role, String name, long actionIdsLong)
		throws SystemException {

		String primKey = String.valueOf(group.getGroupId());

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				group.getCompanyId(), name, ResourceConstants.SCOPE_INDIVIDUAL,
				primKey, role.getRoleId());

		if (resourcePermission == null) {
			long resourcePermissionId = counterLocalService.increment(
				ResourcePermission.class.getName());

			resourcePermission = resourcePermissionPersistence.create(
				resourcePermissionId);

			resourcePermission.setCompanyId(group.getCompanyId());
			resourcePermission.setName(name);
			resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
			resourcePermission.setPrimKey(primKey);
			resourcePermission.setRoleId(role.getRoleId());

			List<String> defaultActionIds = null;

			String roleName = role.getName();

			if (roleName.equals(RoleConstants.GUEST)) {
				defaultActionIds =
					ResourceActionsUtil.getModelResourceGuestDefaultActions(
						name);
			}
			else if (roleName.equals(RoleConstants.SITE_MEMBER)) {
				defaultActionIds =
					ResourceActionsUtil.getModelResourceGroupDefaultActions(
						name);
			}

			List<ResourceAction> resourceActions =
				resourceActionLocalService.getResourceActions(name);

			for (ResourceAction resourceAction : resourceActions) {
				if (defaultActionIds.contains(resourceAction.getActionId())) {
					actionIdsLong |= resourceAction.getBitwiseValue();
				}
			}
		}

		resourcePermission.setActionIds(actionIdsLong);

		resourcePermissionPersistence.update(resourcePermission, false);
	}

	protected void updateResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			long ownerId, String[] actionIds, int operator)
		throws PortalException, SystemException {

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_HYPERSONIC)) {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, roleId, ownerId, actionIds,
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

		String groupName = getClass().getName();
		String key = sb.toString();

		Lock lock = LockRegistry.allocateLock(groupName, key);

		lock.lock();

		try {
			doUpdateResourcePermission(
				companyId, name, scope, primKey, roleId, ownerId, actionIds,
				operator);
		}
		finally {
			lock.unlock();

			LockRegistry.freeLock(groupName, key);
		}
	}

}