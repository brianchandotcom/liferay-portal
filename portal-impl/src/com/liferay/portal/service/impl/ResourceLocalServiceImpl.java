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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.PermissionsListFilter;
import com.liferay.portal.security.permission.PermissionsListFilterFactory;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ResourceLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ResourcePermissionsThreadLocal;
import com.liferay.portal.util.comparator.ResourceComparator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Julio Camarero
 * @author Connor McKay
 */
public class ResourceLocalServiceImpl extends ResourceLocalServiceBaseImpl {

	public void addModelResources(
			AuditedModel auditedModel, ServiceContext serviceContext)
		throws PortalException, SystemException {

		ClassedModel classedModel = (ClassedModel)auditedModel;

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addResources(
				auditedModel.getCompanyId(), getGroupId(auditedModel),
				auditedModel.getUserId(), classedModel.getModelClassName(),
				String.valueOf(classedModel.getPrimaryKeyObj()), false,
				serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions(),
				getPermissionedModel(auditedModel));
		}
		else {
			if (serviceContext.isDeriveDefaultPermissions()) {
				serviceContext.deriveDefaultPermissions(
					getGroupId(auditedModel), classedModel.getModelClassName());
			}

			addModelResources(
				auditedModel.getCompanyId(), getGroupId(auditedModel),
				auditedModel.getUserId(), classedModel.getModelClassName(),
				String.valueOf(classedModel.getPrimaryKeyObj()),
				serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions(),
				getPermissionedModel(auditedModel));
		}
	}

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			long primKey, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			groupPermissions, guestPermissions, null);
	}

	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		addModelResources(
			companyId, groupId, userId, name, primKey, groupPermissions,
			guestPermissions, null);
	}

	public Resource addResource(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return null;
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return addResource_6(companyId, name, scope, primKey);
		}
		else {
			return addResource_1to5(companyId, name, scope, primKey);
		}
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			long primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			portletActions, addGroupPermissions, addGuestPermissions, null);
	}

	public void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, userId, name, primKey, portletActions,
			addGroupPermissions, addGuestPermissions, null);
	}

	public void addResources(
			long companyId, long groupId, String name, boolean portletActions)
		throws PortalException, SystemException {

		addResources(
			companyId, groupId, 0, name, null, portletActions, false, false);
	}

	public void deleteResource(AuditedModel auditedModel, int scope)
		throws PortalException, SystemException {

		ClassedModel classedModel = (ClassedModel)auditedModel;

		deleteResource(
			auditedModel.getCompanyId(), classedModel.getModelClassName(),
			scope, String.valueOf(classedModel.getPrimaryKeyObj()),
			getPermissionedModel(auditedModel));
	}

	@Override
	public void deleteResource(long resourceId) throws SystemException {
		try {
			Resource resource = resourcePersistence.findByPrimaryKey(
				resourceId);

			deleteResource(resource);
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	public void deleteResource(
			long companyId, String name, int scope, long primKey)
		throws PortalException, SystemException {

		deleteResource(companyId, name, scope, String.valueOf(primKey), null);
	}

	public void deleteResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		deleteResource(companyId, name, scope, primKey, null);
	}

	@Override
	public void deleteResource(Resource resource) throws SystemException {

		// Permissions

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resource.getResourceId());

		for (Permission permission : permissions) {
			orgGroupPermissionPersistence.removeByPermissionId(
				permission.getPermissionId());
		}

		permissionPersistence.removeByResourceId(resource.getResourceId());

		// Resource

		resourcePersistence.remove(resource);
	}

	public void deleteResources(String name) throws SystemException {
		List<Resource> resources = resourceFinder.findByName(name);

		for (Resource resource : resources) {
			deleteResource(resource);
		}
	}

	public Resource fetchResource(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return getResource_6(companyId, name, scope, primKey);
		}
		else {
			return fetchResource_1to5(companyId, name, scope, primKey);
		}
	}

	public long getLatestResourceId() throws SystemException {
		List<Resource> resources = resourcePersistence.findAll(
			0, 1, new ResourceComparator());

		if (resources.size() == 0) {
			return 0;
		}
		else {
			Resource resource = resources.get(0);

			return resource.getResourceId();
		}
	}

	@Override
	public Resource getResource(long resourceId)
		throws PortalException, SystemException {

		return resourcePersistence.findByPrimaryKey(resourceId);
	}

	public Resource getResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return getResource_6(companyId, name, scope, primKey);
		}
		else {
			return getResource_1to5(companyId, name, scope, primKey);
		}
	}

	public List<Resource> getResources() throws SystemException {
		return resourcePersistence.findAll();
	}

	public void updateModelResources(
			AuditedModel auditedModel, ServiceContext serviceContext)
		throws PortalException, SystemException {

		ClassedModel classedModel = (ClassedModel)auditedModel;

		updateResources(
			auditedModel.getCompanyId(), getGroupId(auditedModel),
			classedModel.getModelClassName(),
			String.valueOf(classedModel.getPrimaryKeyObj()),
			serviceContext.getGroupPermissions(),
			serviceContext.getGuestPermissions(),
			getPermissionedModel(auditedModel));
	}

	public void updateResources(
			long companyId, long groupId, String name, long primKey,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		updateResources(
			companyId, groupId, name, String.valueOf(primKey), groupPermissions,
			guestPermissions, null);
	}

	public void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		updateResources(
			companyId, groupId, name, primKey, groupPermissions,
			guestPermissions, null);
	}

	public void updateResources(
			long companyId, String name, int scope, String primKey,
			String newPrimKey)
		throws PortalException, SystemException {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {

				// Assuming that this method is used when the primary key of an
				// existing record is changed, then nothing needs to happen
				// here, as it should still have its resource block ID.

			}
			else {
				updateResources_6(companyId, name, scope, primKey, newPrimKey);
			}
		}
		else {
			updateResources_1to5(companyId, name, scope, primKey, newPrimKey);
		}
	}

	protected void addGroupPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceGroupDefaultActions(
				name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceGroupDefaultActions(
				name);
		}

		String[] actionIds = actions.toArray(new String[actions.size()]);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {
				addGroupPermissions_6Blocks(
					groupId, resource, actions, permissionedModel);
			}
			else {
				addGroupPermissions_6(groupId, resource, actionIds);
			}
		}
		else {
			addGroupPermissions_1to5(
				companyId, groupId, userId, name, resource, portletActions,
				actionIds);
		}
	}

	protected void addGroupPermissions_1to5(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions, String[] actionIds)
		throws PortalException, SystemException {

		long resourceId = resource.getResourceId();
		String primKey = resource.getPrimKey();

		List<Permission> groupPermissionsList =
			permissionLocalService.getPermissions(
				companyId, actionIds, resourceId);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		groupPermissionsList = permissionsListFilter.filterGroupPermissions(
			companyId, groupId, userId, name, primKey, portletActions,
			groupPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role role = roleLocalService.getDefaultGroupRole(groupId);

			rolePersistence.addPermissions(
				role.getRoleId(), groupPermissionsList);
		}
		else {
			groupPersistence.addPermissions(groupId, groupPermissionsList);
		}
	}

	protected void addGroupPermissions_6(
			long groupId, Resource resource, String[] actionIds)
		throws PortalException, SystemException {

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), actionIds);
	}

	protected void addGroupPermissions_6Blocks(
			long groupId, Resource resource, List<String> actionIds,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		if (permissionedModel == null) {
			throw new IllegalArgumentException("Permissioned model is null");
		}

		// Scope is assumed to always be individual

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourceBlockLocalService.setIndividualScopePermissions(
			resource.getCompanyId(), groupId, resource.getName(),
			permissionedModel, role.getRoleId(), actionIds);
	}

	protected void addGuestPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceGuestDefaultActions(
				name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceGuestDefaultActions(
				name);
		}

		String[] actionIds = actions.toArray(new String[actions.size()]);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {
				addGuestPermissions_6Blocks(
					companyId, groupId, resource, actions, permissionedModel);
			}
			else {
				addGuestPermissions_6(companyId, resource, actionIds);
			}
		}
		else {
			addGuestPermissions_1to5(
				companyId, groupId, userId, name, resource, portletActions,
				actionIds);
		}
	}

	protected void addGuestPermissions_1to5(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions, String[] actionIds)
		throws PortalException, SystemException {

		List<Permission> guestPermissionsList =
			permissionLocalService.getPermissions(
				companyId, actionIds, resource.getResourceId());

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		guestPermissionsList = permissionsListFilter.filterGuestPermissions(
			companyId, groupId, userId, name, resource.getPrimKey(),
			portletActions, guestPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role guestRole = roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			rolePersistence.addPermissions(
				guestRole.getRoleId(), guestPermissionsList);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			userPersistence.addPermissions(defaultUserId, guestPermissionsList);
		}
	}

	protected void addGuestPermissions_6(
			long companyId, Resource resource, String[] actionIds)
		throws PortalException, SystemException {

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), guestRole.getRoleId(), actionIds);
	}

	protected void addGuestPermissions_6Blocks(
			long companyId, long groupId, Resource resource,
			List<String> actionIds, PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		if (permissionedModel == null) {
			throw new IllegalArgumentException("Permissioned model is null");
		}

		// Scope is assumed to always be individual

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		resourceBlockLocalService.setIndividualScopePermissions(
			resource.getCompanyId(), groupId, resource.getName(),
			permissionedModel, guestRole.getRoleId(), actionIds);
	}

	protected void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] groupPermissions,
			String[] guestPermissions, PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, false);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		// Guest

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		addResource(
			companyId, name, ResourceConstants.SCOPE_GROUP,
			String.valueOf(guestGroup.getGroupId()));

		// Group

		if ((groupId > 0) && (guestGroup.getGroupId() != groupId)) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = addResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		boolean flushEnabled = PermissionThreadLocal.isFlushEnabled();

		PermissionThreadLocal.setIndexEnabled(false);

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				addModelResources_6(
					companyId, groupId, userId, resource, groupPermissions,
					guestPermissions, permissionedModel);
			}
			else {
				addModelResources_1to5(
					companyId, groupId, userId, resource, groupPermissions,
					guestPermissions);
			}
		}
		finally {
			PermissionThreadLocal.setIndexEnabled(flushEnabled);

			PermissionCacheUtil.clearCache();

			SearchEngineUtil.updatePermissionFields(name, primKey);
		}
	}

	protected void addModelResources_1to5(
			long companyId, long groupId, long userId, Resource resource,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		List<Permission> permissionsList =
			permissionLocalService.addPermissions(
				companyId, resource.getName(), resource.getResourceId(), false);

		List<Permission> userPermissionsList =
			permissionsListFilter.filterUserPermissions(
				companyId, groupId, userId, resource.getName(),
				resource.getPrimKey(), false, permissionsList);

		filterOwnerPermissions(resource.getName(), userPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

			// Owner permissions

			Role ownerRole = roleLocalService.getRole(
				companyId, RoleConstants.OWNER);

			rolePersistence.addPermissions(
				ownerRole.getRoleId(), userPermissionsList);
		}
		else {

			// User permissions

			if ((userId > 0) && (userId != defaultUserId)) {
				userPersistence.addPermissions(userId, userPermissionsList);
			}
		}

		// Group permissions

		if (groupId > 0) {
			if (groupPermissions == null) {
				groupPermissions = new String[0];
			}

			List<Permission> groupPermissionsList =
				permissionLocalService.getPermissions(
					companyId, groupPermissions, resource.getResourceId());

			groupPermissionsList = permissionsListFilter.filterGroupPermissions(
				companyId, groupId, userId, resource.getName(),
				resource.getPrimKey(), false, groupPermissionsList);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				Role role = roleLocalService.getDefaultGroupRole(groupId);

				rolePersistence.addPermissions(
					role.getRoleId(), groupPermissionsList);
			}
			else {
				groupPersistence.addPermissions(groupId, groupPermissionsList);
			}
		}

		// Guest permissions

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		List<Permission> guestPermissionsList =
			permissionLocalService.getPermissions(
				companyId, guestPermissions, resource.getResourceId());

		guestPermissionsList = permissionsListFilter.filterGuestPermissions(
			companyId, groupId, userId, resource.getName(),
			resource.getPrimKey(), false, guestPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			Role guestRole = roleLocalService.getRole(
				companyId, RoleConstants.GUEST);

			rolePersistence.addPermissions(
				guestRole.getRoleId(), guestPermissionsList);
		}
		else {
			userPersistence.addPermissions(defaultUserId, guestPermissionsList);
		}
	}

	protected void addModelResources_6(
			long companyId, long groupId, long userId, Resource resource,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		// Owner permissions

		Role ownerRole = roleLocalService.getRole(
			companyId, RoleConstants.OWNER);

		List<String> ownerActionIds =
			ResourceActionsUtil.getModelResourceActions(resource.getName());

		ownerActionIds = ListUtil.copy(ownerActionIds);

		filterOwnerActions(resource.getName(), ownerActionIds);

		String[] ownerPermissions = ownerActionIds.toArray(
			new String[ownerActionIds.size()]);

		// Group permissions

		Role defaultGroupRole = null;

		if (groupId > 0) {
			defaultGroupRole = roleLocalService.getDefaultGroupRole(groupId);

			if (groupPermissions == null) {
				groupPermissions = new String[0];
			}
		}

		// Guest permissions

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		if (resourceBlockLocalService.isSupported(resource.getName())) {

			if (permissionedModel == null) {
				throw new IllegalArgumentException(
					"Permissioned model is null");
			}

			// Scope is assumed to always be individual

			resourceBlockLocalService.setIndividualScopePermissions(
				resource.getCompanyId(), groupId, resource.getName(),
				permissionedModel, ownerRole.getRoleId(), ownerActionIds);

			if (groupId > 0) {
				resourceBlockLocalService.setIndividualScopePermissions(
					resource.getCompanyId(), groupId, resource.getName(),
					permissionedModel, defaultGroupRole.getRoleId(),
					Arrays.asList(groupPermissions));
			}

			resourceBlockLocalService.setIndividualScopePermissions(
				resource.getCompanyId(), groupId, resource.getName(),
				permissionedModel, guestRole.getRoleId(),
				Arrays.asList(guestPermissions));
		}
		else {
			resourcePermissionLocalService.setOwnerResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(),
				ownerRole.getRoleId(), userId, ownerPermissions);

			if (groupId > 0) {
				resourcePermissionLocalService.setResourcePermissions(
					resource.getCompanyId(), resource.getName(),
					resource.getScope(), resource.getPrimKey(),
					defaultGroupRole.getRoleId(), groupPermissions);
			}

			resourcePermissionLocalService.setResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(),
				guestRole.getRoleId(), guestPermissions);
		}
	}

	protected Resource addResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		long codeId = resourceCode.getCodeId();

		Resource resource = resourcePersistence.fetchByC_P(codeId, primKey);

		if (resource == null) {
			long resourceId = counterLocalService.increment(
				Resource.class.getName());

			resource = resourcePersistence.create(resourceId);

			resource.setCodeId(codeId);
			resource.setPrimKey(primKey);

			try {
				resourcePersistence.update(resource, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {codeId=" + codeId + ", primKey=" +
							primKey + "}");
				}

				resource = resourcePersistence.fetchByC_P(
					codeId, primKey, false);

				if (resource == null) {
					throw se;
				}
			}
		}

		return resource;
	}

	protected Resource addResource_6(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	protected void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions, PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, portletActions);

		// Company

		addResource(
			companyId, name, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(companyId));

		if (groupId > 0) {
			addResource(
				companyId, name, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId));
		}

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = addResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		boolean flushEnabled = PermissionThreadLocal.isFlushEnabled();

		PermissionThreadLocal.setIndexEnabled(false);

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S_P(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		ResourcePermissionsThreadLocal.setResourcePermissions(
			resourcePermissions);

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				addResources_6(
					companyId, groupId, userId, resource, portletActions,
					permissionedModel);
			}
			else {
				addResources_1to5(
					companyId, groupId, userId, resource, portletActions);
			}

			// Group permissions

			if ((groupId > 0) && addGroupPermissions) {
				addGroupPermissions(
					companyId, groupId, userId, name, resource, portletActions,
					permissionedModel);
			}

			// Guest permissions

			if (addGuestPermissions) {

				// Don't add guest permissions when you've already added group
				// permissions and the given group is the guest group.

				addGuestPermissions(
					companyId, groupId, userId, name, resource, portletActions,
					permissionedModel);
			}
		}
		finally {
			ResourcePermissionsThreadLocal.setResourcePermissions(null);

			PermissionThreadLocal.setIndexEnabled(flushEnabled);

			PermissionCacheUtil.clearCache();

			SearchEngineUtil.updatePermissionFields(name, primKey);
		}
	}

	protected void addResources_1to5(
			long companyId, long groupId, long userId, Resource resource,
			boolean portletActions)
		throws PortalException, SystemException {

		List<Permission> permissionsList =
			permissionLocalService.addPermissions(
				companyId, resource.getName(), resource.getResourceId(),
				portletActions);

		PermissionsListFilter permissionsListFilter =
			PermissionsListFilterFactory.getInstance();

		List<Permission> userPermissionsList =
			permissionsListFilter.filterUserPermissions(
				companyId, groupId, userId, resource.getName(),
				resource.getPrimKey(), portletActions, permissionsList);

		filterOwnerPermissions(resource.getName(), userPermissionsList);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {

			// Owner permissions

			Role ownerRole = roleLocalService.getRole(
				companyId, RoleConstants.OWNER);

			rolePersistence.addPermissions(
				ownerRole.getRoleId(), userPermissionsList);
		}
		else {

			// User permissions

			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			if ((userId > 0) && (userId != defaultUserId)) {
				userPersistence.addPermissions(userId, userPermissionsList);
			}
		}
	}

	protected void addResources_6(
			long companyId, long groupId, long userId, Resource resource,
			boolean portletActions, PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		List<String> actionIds = null;

		if (portletActions) {
			actionIds = ResourceActionsUtil.getPortletResourceActions(
				resource.getName());
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceActions(
				resource.getName());

			actionIds = ListUtil.copy(actionIds);

			filterOwnerActions(resource.getName(), actionIds);
		}

		Role role = roleLocalService.getRole(companyId, RoleConstants.OWNER);

		if (resourceBlockLocalService.isSupported(resource.getName())) {
			if (permissionedModel == null) {
				throw new IllegalArgumentException(
					"Permissioned model is null");
			}

			// Scope is assumed to always be individual

			resourceBlockLocalService.setIndividualScopePermissions(
				resource.getCompanyId(), groupId, resource.getName(),
				permissionedModel, role.getRoleId(), actionIds);
		}
		else {
			resourcePermissionLocalService.setOwnerResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(), role.getRoleId(),
				userId, actionIds.toArray(new String[actionIds.size()]));
		}
	}

	protected void deleteResource(
			long companyId, String name, int scope, String primKey,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException{

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {
				if (permissionedModel == null) {
					throw new IllegalArgumentException(
						"Permissioned model is null");
				}

				resourceBlockLocalService.releasePermissionedModelResourceBlock(
					permissionedModel);

				return;
			}
			else {
				resourcePermissionLocalService.deleteResourcePermissions(
					companyId, name, scope, primKey);

				return;
			}
		}

		try {
			Resource resource = getResource(companyId, name, scope, primKey);

			deleteResource(resource.getResourceId());
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}
	}

	protected Resource fetchResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		return resourcePersistence.fetchByC_P(
			resourceCode.getCodeId(), primKey);
	}

	protected void filterOwnerActions(String name, List<String> actionIds) {
		List<String> defaultOwnerActions =
			ResourceActionsUtil.getModelResourceOwnerDefaultActions(name);

		if (defaultOwnerActions.isEmpty()) {
			return;
		}

		Iterator<String> itr = actionIds.iterator();

		while (itr.hasNext()) {
			String actionId = itr.next();

			if (!defaultOwnerActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected void filterOwnerPermissions(
		String name, List<Permission> permissions) {

		List<String> defaultOwnerActions =
			ResourceActionsUtil.getModelResourceOwnerDefaultActions(name);

		if (defaultOwnerActions.isEmpty()) {
			return;
		}

		Iterator<Permission> itr = permissions.iterator();

		while (itr.hasNext()) {
			Permission permission = itr.next();

			String actionId = permission.getActionId();

			if (!defaultOwnerActions.contains(actionId)) {
				itr.remove();
			}
		}
	}

	protected long getGroupId(AuditedModel auditedModel) {
		long groupId = 0;

		if (auditedModel instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)auditedModel;

			groupId = groupedModel.getGroupId();
		}

		return groupId;
	}

	protected PermissionedModel getPermissionedModel(
		AuditedModel auditedModel) {

		PermissionedModel permissionedModel = null;

		if (auditedModel instanceof PermissionedModel) {
			permissionedModel = (PermissionedModel)auditedModel;
		}

		return permissionedModel;
	}

	protected Resource getResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws PortalException, SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		return resourcePersistence.findByC_P(resourceCode.getCodeId(), primKey);
	}

	protected Resource getResource_6(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	protected void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		if (groupPermissions == null) {
			groupPermissions = new String[0];
		}

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			if (resourceBlockLocalService.isSupported(name)) {
				updateResources_6Blocks(
					companyId, groupId, resource, groupPermissions,
					guestPermissions, permissionedModel);
			}
			else {
				updateResources_6(
					companyId, groupId, resource, groupPermissions,
					guestPermissions);
			}
		}
		else {
			updateResources_1to5(
				companyId, groupId, resource, groupPermissions,
				guestPermissions);
		}
	}

	protected void updateResources_1to5(
			long companyId, long groupId, Resource resource,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		permissionLocalService.setRolePermissions(
			role.getRoleId(), groupPermissions, resource.getResourceId());

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		permissionLocalService.setRolePermissions(
			role.getRoleId(), guestPermissions, resource.getResourceId());
	}

	protected void updateResources_1to5(
			long companyId, String name, int scope, String primKey,
			String newPrimKey)
		throws PortalException, SystemException {

		Resource resource = getResource(companyId, name, scope, primKey);

		resource.setPrimKey(newPrimKey);

		resourcePersistence.update(resource, false);
	}

	protected void updateResources_6(
			long companyId, long groupId, Resource resource,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), groupPermissions);

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), guestPermissions);
	}

	protected void updateResources_6(
			long companyId, String name, int scope, String primKey,
			String newPrimKey)
		throws SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourcePermissions(
				companyId, name, scope, primKey);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermission.setPrimKey(newPrimKey);

			resourcePermissionPersistence.update(resourcePermission, false);
		}
	}

	protected void updateResources_6Blocks(
			long companyId, long groupId, Resource resource,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException, SystemException {

		if (permissionedModel == null) {
			throw new IllegalArgumentException("Permissioned model is null");
		}

		// Scope is assumed to always be individual

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourceBlockLocalService.setIndividualScopePermissions(
			companyId, groupId, resource.getName(), permissionedModel,
			role.getRoleId(), Arrays.asList(groupPermissions));

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		resourceBlockLocalService.setIndividualScopePermissions(
			companyId, groupId, resource.getName(), permissionedModel,
			role.getRoleId(), Arrays.asList(guestPermissions));
	}

	protected void validate(String name, boolean portletActions)
		throws PortalException {

		List<String> actions = null;

		if (portletActions) {
			actions = ResourceActionsUtil.getPortletResourceActions(name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceActions(name);
		}

		if (actions.size() == 0) {
			throw new ResourceActionsException(
				"There are no actions associated with the resource " + name);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourceLocalServiceImpl.class);

}