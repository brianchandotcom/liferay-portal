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

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.ResourceActionsException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ResourceLocalServiceBaseImpl;
import com.liferay.portal.service.permission.ModelPermissions;
import com.liferay.portal.service.permission.ModelPermissionsFactory;
import com.liferay.portal.util.ResourcePermissionsThreadLocal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

/**
 * Provides the local service for accessing, adding, and updating resources.
 *
 * <p>
 * Permissions in Liferay are defined for resource/action pairs. Some resources,
 * known as portlet resources, define actions that the end-user can perform with
 * respect to a portlet window. Other resources, known as model resources,
 * define actions that the end-user can perform with respect to the
 * service/persistence layer.
 * </p>
 *
 * <p>
 * On creating an entity instance, you should create resources for it. The
 * following example demonstrates adding resources for an instance of a model
 * entity named <code>SomeWidget</code>. The IDs of the actions permitted for
 * the group and guests are passed in from the service context.
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 * resourceLocalService.addModelResources(
 * 		SomeWidget.getCompanyId(), SomeWidget.getGroupId(), userId,
 * 		SomeWidget.class.getName(), SomeWidget.getPrimaryKey(),
 * 		serviceContext.getGroupPermissions, serviceContext.getGuestPermissions);
 * </code>
 * </pre>
 * </p>
 *
 * <p>
 * Just prior to deleting an entity instance, you should delete its resource at
 * the individual scope. The following example demonstrates deleting a resource
 * associated with the <code>SomeWidget</code> model entity at the scope
 * individual scope.
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 * resourceLocalService.deleteResource(
 * 		SomeWidget.getCompanyId(), SomeWidget.class.getName(),
 * 		ResourceConstants.SCOPE_INDIVIDUAL, SomeWidget.getPrimaryKey());
 * </code>
 * </pre>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Julio Camarero
 * @author Connor McKay
 */
public class ResourceLocalServiceImpl extends ResourceLocalServiceBaseImpl {

	/**
	 * Adds resources for the model, always creating a resource at the
	 * individual scope and only creating resources at the group, group
	 * template, and company scope if such resources don't already exist.
	 *
	 * <ol>
	 * <li>
	 * If the service context specifies that default group or default guest
	 * permissions are to be added, then only default permissions are added. See
	 * {@link ServiceContext#setAddGroupPermissions(
	 * boolean)} and {@link
	 * ServiceContext#setAddGuestPermissions(
	 * boolean)}.
	 * </li>
	 * <li>
	 * Else ...
	 * <ol>
	 * <li>
	 * If the service context specifies to derive default permissions, then
	 * default group and guest permissions are derived from the model and
	 * added. See {@link
	 * ServiceContext#setDeriveDefaultPermissions(
	 * boolean)}.
	 * </li>
	 * <li>
	 * Lastly group and guest permissions from the service
	 * context are applied. See {@link
	 * ServiceContext#setGroupPermissions(String[])}
	 * and {@link
	 * ServiceContext#setGuestPermissions(String[])}.
	 * </li>
	 * </ol>
	 *
	 * </li>
	 * </ol>
	 *
	 * @param auditedModel the model to associate with the resources
	 * @param serviceContext the service context to apply. Can set whether to
	 *        add the model's default group and guest permissions, set whether
	 *        to derive default group and guest permissions from the model, set
	 *        group permissions to apply, and set guest permissions to apply.
	 */
	@Override
	public void addModelResources(
			AuditedModel auditedModel, ServiceContext serviceContext)
		throws PortalException {

		ModelPermissions modelPermissions =
			serviceContext.getModelPermissions();

		if ((modelPermissions != null) && !modelPermissions.isEmpty()) {
			addModelResources(
				auditedModel.getCompanyId(), getGroupId(auditedModel),
				auditedModel.getUserId(), auditedModel.getModelClassName(),
				String.valueOf(auditedModel.getPrimaryKeyObj()),
				modelPermissions, getPermissionedModel(auditedModel));
		}
		else if (serviceContext.isAddGroupPermissions() ||
				 serviceContext.isAddGuestPermissions()) {

			addResources(
				auditedModel.getCompanyId(), getGroupId(auditedModel),
				auditedModel.getUserId(), auditedModel.getModelClassName(),
				String.valueOf(auditedModel.getPrimaryKeyObj()), false,
				serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions(),
				getPermissionedModel(auditedModel));
		}
		else {
			if (serviceContext.isDeriveDefaultPermissions()) {
				serviceContext.deriveDefaultPermissions(
					getGroupId(auditedModel), auditedModel.getModelClassName());
			}

			addModelResources(
				auditedModel.getCompanyId(), getGroupId(auditedModel),
				auditedModel.getUserId(), auditedModel.getModelClassName(),
				String.valueOf(auditedModel.getPrimaryKeyObj()),
				serviceContext.getModelPermissions(),
				getPermissionedModel(auditedModel));
		}
	}

	@Override
	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			long primKey, ModelPermissions modelPermissions)
		throws PortalException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			modelPermissions, null);
	}

	/**
	 * Adds resources for the model with the name and primary key, always
	 * creating a resource at the individual scope and only creating resources
	 * at the group, group template, and company scope if such resources don't
	 * already exist.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user adding the resources
	 * @param name a name for the resource, typically the model's class name
	 * @param primKey the primary key of the model instance, optionally
	 *        <code>0</code> if no instance exists
	 * @param groupPermissions the group permissions to be applied
	 * @param guestPermissions the guest permissions to be applied
	 */
	@Override
	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			long primKey, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		addModelResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			groupPermissions, guestPermissions, null);
	}

	@Override
	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, ModelPermissions modelPermissions)
		throws PortalException {

		addModelResources(
			companyId, groupId, userId, name, primKey, modelPermissions, null);
	}

	/**
	 * Adds resources for the model with the name and primary key string, always
	 * creating a resource at the individual scope and only creating resources
	 * at the group, group template, and company scope if such resources don't
	 * already exist.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user adding the resources
	 * @param name a name for the resource, typically the model's class name
	 * @param primKey the primary key string of the model instance, optionally
	 *        an empty string if no instance exists
	 * @param groupPermissions the group permissions to be applied
	 * @param guestPermissions the guest permissions to be applied
	 */
	@Override
	public void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		addModelResources(
			companyId, groupId, userId, name, primKey, groupPermissions,
			guestPermissions, null);
	}

	/**
	 * Adds resources for the entity with the name and primary key, always
	 * creating a resource at the individual scope and only creating resources
	 * at the group, group template, and company scope if such resources don't
	 * already exist.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user adding the resources
	 * @param name a name for the resource, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key of the resource instance, optionally
	 *        <code>0</code> if no instance exists
	 * @param portletActions whether to associate portlet actions with the
	 *        resource
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 */
	@Override
	public void addResources(
			long companyId, long groupId, long userId, String name,
			long primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		addResources(
			companyId, groupId, userId, name, String.valueOf(primKey),
			portletActions, addGroupPermissions, addGuestPermissions, null);
	}

	/**
	 * Adds resources for the entity with the name and primary key string,
	 * always creating a resource at the individual scope and only creating
	 * resources at the group, group template, and company scope if such
	 * resources don't already exist.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user adding the resources
	 * @param name a name for the resource, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key string of the resource instance,
	 *        optionally an empty string if no instance exists
	 * @param portletActions whether to associate portlet actions with the
	 *        resource
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 */
	@Override
	public void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		addResources(
			companyId, groupId, userId, name, primKey, portletActions,
			addGroupPermissions, addGuestPermissions, null);
	}

	/**
	 * Adds resources for the entity with the name. Use this method if the user
	 * is unknown or irrelevant and there is no current entity instance.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param name a name for the resource, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param portletActions whether to associate portlet actions with the
	 *        resource
	 */
	@Override
	public void addResources(
			long companyId, long groupId, String name, boolean portletActions)
		throws PortalException {

		addResources(
			companyId, groupId, 0, name, null, portletActions, false, false);
	}

	/**
	 * Deletes the resource associated with the model at the scope.
	 *
	 * @param auditedModel the model associated with the resource
	 * @param scope the scope of the resource. For more information see {@link
	 *        ResourceConstants}.
	 */
	@Override
	public void deleteResource(AuditedModel auditedModel, int scope)
		throws PortalException {

		deleteResource(
			auditedModel.getCompanyId(), auditedModel.getModelClassName(),
			scope, String.valueOf(auditedModel.getPrimaryKeyObj()),
			getPermissionedModel(auditedModel));
	}

	/**
	 * Deletes the resource matching the primary key at the scope.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param scope the scope of the resource. For more information see {@link
	 *        ResourceConstants}.
	 * @param primKey the primary key of the resource instance
	 */
	@Override
	public void deleteResource(
			long companyId, String name, int scope, long primKey)
		throws PortalException {

		deleteResource(companyId, name, scope, String.valueOf(primKey), null);
	}

	/**
	 * Deletes the resource matching the primary key at the scope.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param scope the scope of the resource. For more information see {@link
	 *        ResourceConstants}.
	 * @param primKey the primary key string of the resource instance
	 */
	@Override
	public void deleteResource(
			long companyId, String name, int scope, String primKey)
		throws PortalException {

		deleteResource(companyId, name, scope, primKey, null);
	}

	/**
	 * Returns a new resource with the name and primary key at the scope.
	 *
	 * @param  companyId the primary key of the portal instance
	 * @param  name a name for the resource, which should be a portlet ID if the
	 *         resource is a portlet or the resource's class name otherwise
	 * @param  scope the scope of the resource. For more information see {@link
	 *         ResourceConstants}.
	 * @param  primKey the primary key string of the resource
	 * @return the new resource
	 */
	@Override
	public Resource getResource(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	/**
	 * Returns <code>true</code> if the roles have permission to perform the
	 * action on the resources.
	 *
	 * @param  userId the primary key of the user performing the permission
	 *         check
	 * @param  resourceId the primary key of the resource, typically the scope
	 *         group ID representing the scope in which the permission check is
	 *         being performed
	 * @param  resources the resources for which permissions are to be checked
	 * @param  actionId the primary key of the action to be performed on the
	 *         resources
	 * @param  roleIds the primary keys of the roles
	 * @return <code>true</code> if the roles have permission to perform the
	 *         action on the resources;<code>false</code> otherwise
	 */
	@Override
	public boolean hasUserPermissions(
			long userId, long resourceId, List<Resource> resources,
			String actionId, long[] roleIds)
		throws PortalException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		int block = 1;

		boolean hasUserPermissions =
			resourcePermissionLocalService.hasResourcePermission(
				resources, roleIds, actionId);

		logHasUserPermissions(userId, resourceId, actionId, stopWatch, block++);

		return hasUserPermissions;
	}

	/**
	 * Updates the resources for the model, replacing their group and guest
	 * permissions with new ones from the service context.
	 *
	 * @param auditedModel the model associated with the resources
	 * @param serviceContext the service context to be applied. Can set group
	 *        and guest permissions.
	 */
	@Override
	public void updateModelResources(
			AuditedModel auditedModel, ServiceContext serviceContext)
		throws PortalException {

		updateResources(
			auditedModel.getCompanyId(), getGroupId(auditedModel),
			auditedModel.getModelClassName(),
			String.valueOf(auditedModel.getPrimaryKeyObj()),
			serviceContext.getModelPermissions(),
			getPermissionedModel(auditedModel));
	}

	/**
	 * Updates resources matching the group, name, and primary key at the
	 * individual scope, setting new permissions.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key of the resource instance
	 * @param modelPermissions the model permissions to be applied
	 */
	@Override
	public void updateResources(
			long companyId, long groupId, String name, long primKey,
			ModelPermissions modelPermissions)
		throws PortalException {

		updateResources(
			companyId, groupId, name, String.valueOf(primKey), modelPermissions,
			null);
	}

	/**
	 * Updates resources matching the group, name, and primary key at the
	 * individual scope, setting new group and guest permissions.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key of the resource instance
	 * @param groupPermissions the group permissions to be applied
	 * @param guestPermissions the guest permissions to be applied
	 */
	@Override
	public void updateResources(
			long companyId, long groupId, String name, long primKey,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		updateResources(
			companyId, groupId, name, String.valueOf(primKey), groupPermissions,
			guestPermissions, null);
	}

	/**
	 * Updates resources matching the group, name, and primary key string at the
	 * individual scope, setting new permissions.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key string of the resource instance
	 * @param modelPermissions the model permissions to be applied
	 */
	@Override
	public void updateResources(
			long companyId, long groupId, String name, String primKey,
			ModelPermissions modelPermissions)
		throws PortalException {

		updateResources(
			companyId, groupId, name, primKey, modelPermissions, null);
	}

	/**
	 * Updates resources matching the group, name, and primary key string at the
	 * individual scope, setting new group and guest permissions.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param groupId the primary key of the group
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param primKey the primary key string of the resource instance
	 * @param groupPermissions the group permissions to be applied
	 * @param guestPermissions the guest permissions to be applied
	 */
	@Override
	public void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		updateResources(
			companyId, groupId, name, primKey, modelPermissions, null);
	}

	/**
	 * Updates resources matching the name, primary key string and scope,
	 * replacing the primary key of their resource permissions with the new
	 * primary key.
	 *
	 * @param companyId the primary key of the portal instance
	 * @param name the resource's name, which should be a portlet ID if the
	 *        resource is a portlet or the resource's class name otherwise
	 * @param scope the scope of the resource. For more information see {@link
	 *        ResourceConstants}.
	 * @param primKey the primary key string of the resource instance
	 * @param newPrimKey the new primary key string of the resource
	 */
	@Override
	public void updateResources(
		long companyId, String name, int scope, String primKey,
		String newPrimKey) {

		if (resourceBlockLocalService.isSupported(name)) {

			// Assuming that this method is used when the primary key of an
			// existing record is changed, then nothing needs to happen here, as
			// it should still have its resource block ID.

		}
		else {
			updateResourcePermissions(
				companyId, name, scope, primKey, newPrimKey);
		}
	}

	protected void addGroupPermissions(
			long companyId, long groupId, long userId, String name,
			Resource resource, boolean portletActions,
			PermissionedModel permissionedModel)
		throws PortalException {

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

		if (resourceBlockLocalService.isSupported(name)) {
			addGroupPermissionsBlocks(
				groupId, resource, actions, permissionedModel);
		}
		else {
			addGroupPermissions(groupId, resource, actionIds);
		}
	}

	protected void addGroupPermissions(
			long groupId, Resource resource, String[] actionIds)
		throws PortalException {

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), actionIds);
	}

	protected void addGroupPermissionsBlocks(
			long groupId, Resource resource, List<String> actionIds,
			PermissionedModel permissionedModel)
		throws PortalException {

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
		throws PortalException {

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

		if (resourceBlockLocalService.isSupported(name)) {
			addGuestPermissionsBlocks(
				companyId, groupId, resource, actions, permissionedModel);
		}
		else {
			addGuestPermissions(companyId, resource, actionIds);
		}
	}

	protected void addGuestPermissions(
			long companyId, Resource resource, String[] actionIds)
		throws PortalException {

		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), guestRole.getRoleId(), actionIds);
	}

	protected void addGuestPermissionsBlocks(
			long companyId, long groupId, Resource resource,
			List<String> actionIds, PermissionedModel permissionedModel)
		throws PortalException {

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
			long companyId, long groupId, long userId, Resource resource,
			ModelPermissions modelPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		// Owner permissions

		Role ownerRole = roleLocalService.getRole(
			companyId, RoleConstants.OWNER);

		List<String> ownerActionIds =
			ResourceActionsUtil.getModelResourceActions(resource.getName());

		ownerActionIds = ListUtil.copy(ownerActionIds);

		filterOwnerActions(resource.getName(), ownerActionIds);

		String[] ownerPermissions = ownerActionIds.toArray(
			new String[ownerActionIds.size()]);

		if (resourceBlockLocalService.isSupported(resource.getName())) {
			if (permissionedModel == null) {
				throw new IllegalArgumentException(
					"Permissioned model is null");
			}

			// Scope is assumed to always be individual

			resourceBlockLocalService.setIndividualScopePermissions(
				resource.getCompanyId(), groupId, resource.getName(),
				permissionedModel, ownerRole.getRoleId(), ownerActionIds);

			if (modelPermissions != null) {
				for (String roleName : modelPermissions.getRoleNames()) {
					Role role = getRole(
						resource.getCompanyId(), groupId, roleName);

					resourceBlockLocalService.setIndividualScopePermissions(
						resource.getCompanyId(), groupId, resource.getName(),
						permissionedModel, role.getRoleId(),
						modelPermissions.getActionIdsList(roleName));
				}
			}
		}
		else {
			resourcePermissionLocalService.setOwnerResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(),
				ownerRole.getRoleId(), userId, ownerPermissions);

			if (modelPermissions != null) {
				for (String roleName : modelPermissions.getRoleNames()) {
					Role role = getRole(
						resource.getCompanyId(), groupId, roleName);

					resourcePermissionLocalService.setResourcePermissions(
						resource.getCompanyId(), resource.getName(),
						resource.getScope(), resource.getPrimKey(),
						role.getRoleId(),
						modelPermissions.getActionIds(roleName));
				}
			}
		}
	}

	protected void addModelResources(
			long companyId, long groupId, long userId, Resource resource,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		addModelResources(
			companyId, groupId, userId, resource, modelPermissions,
			permissionedModel);
	}

	protected void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, ModelPermissions modelPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, false);

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		boolean flushResourceBlockEnabled =
			PermissionThreadLocal.isFlushResourceBlockEnabled(
				companyId, groupId, name);
		boolean flushResourcePermissionEnabled =
			PermissionThreadLocal.isFlushResourcePermissionEnabled(
				name, primKey);

		PermissionThreadLocal.setFlushResourceBlockEnabled(
			companyId, groupId, name, false);
		PermissionThreadLocal.setFlushResourcePermissionEnabled(
			name, primKey, false);

		try {
			addModelResources(
				companyId, groupId, userId, resource, modelPermissions,
				permissionedModel);
		}
		finally {
			PermissionThreadLocal.setFlushResourceBlockEnabled(
				companyId, groupId, name, flushResourceBlockEnabled);
			PermissionThreadLocal.setFlushResourcePermissionEnabled(
				name, primKey, flushResourcePermissionEnabled);

			PermissionCacheUtil.clearResourceBlockCache(
				companyId, groupId, name);
			PermissionCacheUtil.clearResourcePermissionCache(
				ResourceConstants.SCOPE_INDIVIDUAL, name, primKey);

			IndexWriterHelperUtil.updatePermissionFields(name, primKey);
		}
	}

	protected void addModelResources(
			long companyId, long groupId, long userId, String name,
			String primKey, String[] groupPermissions,
			String[] guestPermissions, PermissionedModel permissionedModel)
		throws PortalException {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		addModelResources(
			companyId, groupId, userId, name, primKey, modelPermissions,
			permissionedModel);
	}

	protected void addResources(
			long companyId, long groupId, long userId, Resource resource,
			boolean portletActions, PermissionedModel permissionedModel)
		throws PortalException {

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

	protected void addResources(
			long companyId, long groupId, long userId, String name,
			String primKey, boolean portletActions, boolean addGroupPermissions,
			boolean addGuestPermissions, PermissionedModel permissionedModel)
		throws PortalException {

		if (!PermissionThreadLocal.isAddResource()) {
			return;
		}

		validate(name, portletActions);

		if (primKey == null) {
			return;
		}

		// Individual

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		// Permissions

		boolean flushResourceBlockEnabled =
			PermissionThreadLocal.isFlushResourceBlockEnabled(
				companyId, groupId, name);
		boolean flushResourcePermissionEnabled =
			PermissionThreadLocal.isFlushResourcePermissionEnabled(
				name, primKey);

		PermissionThreadLocal.setFlushResourceBlockEnabled(
			companyId, groupId, name, false);
		PermissionThreadLocal.setFlushResourcePermissionEnabled(
			name, primKey, false);

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S_P(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		ResourcePermissionsThreadLocal.setResourcePermissions(
			resourcePermissions);

		try {
			addResources(
				companyId, groupId, userId, resource, portletActions,
				permissionedModel);

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

			PermissionThreadLocal.setFlushResourceBlockEnabled(
				companyId, groupId, name, flushResourceBlockEnabled);
			PermissionThreadLocal.setFlushResourcePermissionEnabled(
				name, primKey, flushResourcePermissionEnabled);

			PermissionCacheUtil.clearResourceBlockCache(
				companyId, groupId, name);
			PermissionCacheUtil.clearResourcePermissionCache(
				ResourceConstants.SCOPE_INDIVIDUAL, name, primKey);

			IndexWriterHelperUtil.updatePermissionFields(name, primKey);
		}
	}

	protected void deleteResource(
			long companyId, String name, int scope, String primKey,
			PermissionedModel permissionedModel)
		throws PortalException {

		if (resourceBlockLocalService.isSupported(name)) {
			if (permissionedModel == null) {
				throw new IllegalArgumentException(
					"Permissioned model is null");
			}

			resourceBlockLocalService.releasePermissionedModelResourceBlock(
				permissionedModel);

			return;
		}

		resourcePermissionLocalService.deleteResourcePermissions(
			companyId, name, scope, primKey);
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

	protected long getGroupId(AuditedModel auditedModel) {
		long groupId = 0;

		if (auditedModel instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)auditedModel;

			groupId = BeanPropertiesUtil.getLongSilent(
				groupedModel, "resourceGroupId", groupedModel.getGroupId());
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

	protected Role getRole(long companyId, long groupId, String roleName)
		throws PortalException {

		if (roleName.equals(RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE)) {
			if (groupId == 0) {
				throw new NoSuchRoleException(
					"Specify a group ID other than 0 for role name " +
						RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE);
			}

			return roleLocalService.getDefaultGroupRole(groupId);
		}

		return roleLocalService.getRole(companyId, roleName);
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

	protected void updateResourceBlocks(
			long companyId, long groupId, Resource resource,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

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

	protected void updateResourceBlocks(
			long groupId, Resource resource, ModelPermissions modelPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		if (permissionedModel == null) {
			throw new IllegalArgumentException("Permissioned model is null");
		}

		// Scope is assumed to always be individual

		for (String roleName : modelPermissions.getRoleNames()) {
			Role role = getRole(resource.getCompanyId(), groupId, roleName);

			resourceBlockLocalService.setIndividualScopePermissions(
				role.getCompanyId(), groupId, resource.getName(),
				permissionedModel, role.getRoleId(),
				modelPermissions.getActionIdsList(roleName));
		}
	}

	protected void updateResourcePermissions(
			long companyId, long groupId, Resource resource,
			String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		Role role = roleLocalService.getDefaultGroupRole(groupId);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), groupPermissions);

		role = roleLocalService.getRole(companyId, RoleConstants.GUEST);

		resourcePermissionLocalService.setResourcePermissions(
			resource.getCompanyId(), resource.getName(), resource.getScope(),
			resource.getPrimKey(), role.getRoleId(), guestPermissions);
	}

	protected void updateResourcePermissions(
			long groupId, Resource resource, ModelPermissions modelPermissions)
		throws PortalException {

		for (String roleName : modelPermissions.getRoleNames()) {
			Role role = getRole(resource.getCompanyId(), groupId, roleName);

			List<String> actionIds = modelPermissions.getActionIdsList(
				roleName);

			resourcePermissionLocalService.setResourcePermissions(
				resource.getCompanyId(), resource.getName(),
				resource.getScope(), resource.getPrimKey(), role.getRoleId(),
				actionIds.toArray(new String[actionIds.size()]));
		}
	}

	protected void updateResourcePermissions(
		long companyId, String name, int scope, String primKey,
		String newPrimKey) {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionLocalService.getResourcePermissions(
				companyId, name, scope, primKey);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermission.setPrimKey(newPrimKey);
			resourcePermission.setPrimKeyId(GetterUtil.getLong(newPrimKey));

			resourcePermissionPersistence.update(resourcePermission);
		}
	}

	protected void updateResources(
			long companyId, long groupId, String name, String primKey,
			ModelPermissions modelPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		if (resourceBlockLocalService.isSupported(name)) {
			updateResourceBlocks(
				groupId, resource, modelPermissions, permissionedModel);
		}
		else {
			updateResourcePermissions(groupId, resource, modelPermissions);
		}
	}

	protected void updateResources(
			long companyId, long groupId, String name, String primKey,
			String[] groupPermissions, String[] guestPermissions,
			PermissionedModel permissionedModel)
		throws PortalException {

		Resource resource = getResource(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey);

		if (groupPermissions == null) {
			groupPermissions = new String[0];
		}

		if (guestPermissions == null) {
			guestPermissions = new String[0];
		}

		if (resourceBlockLocalService.isSupported(name)) {
			updateResourceBlocks(
				companyId, groupId, resource, groupPermissions,
				guestPermissions, permissionedModel);
		}
		else {
			updateResourcePermissions(
				companyId, groupId, resource, groupPermissions,
				guestPermissions);
		}
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

		if (actions.isEmpty()) {
			throw new ResourceActionsException(
				"There are no actions associated with the resource " + name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceLocalServiceImpl.class);

}