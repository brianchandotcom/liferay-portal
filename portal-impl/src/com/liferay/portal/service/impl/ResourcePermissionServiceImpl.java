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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.base.ResourcePermissionServiceBaseImpl;

import java.util.Map;

/**
 * Provides the remote service for adding, granting, and revoking resource
 * permissions. Its methods include permission checks.
 *
 * <p>
 * Before attempting to read any of the documentation for this class, first read
 * {@link com.liferay.portal.model.impl.ResourcePermissionImpl} for an
 * explanation of scoping.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourcePermissionServiceImpl
	extends ResourcePermissionServiceBaseImpl {

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
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope. This method only supports company, group, and
	 *         group-template scope.
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if the user did not have permission to add
	 *         resource permissions, or if scope was set to individual scope or
	 *         if a role with the primary key or a resource action with the name
	 *         and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void addResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.addResourcePermission(
			companyId, name, scope, primKey, roleId, actionId);
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
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if the user did not have permission to remove
	 *         resource permissions, or if a role with the primary key or a
	 *         resource action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void removeResourcePermission(
			long groupId, long companyId, String name, int scope,
			String primKey, long roleId, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.removeResourcePermission(
			companyId, name, scope, primKey, roleId, actionId);
	}

	/**
	 * Revokes all permissions at the scope from the role to perform the action
	 * on resources of the type. For example, this method could be used to
	 * revoke all individual scope permissions to edit blog posts from site
	 * members.
	 *
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  scope the scope
	 * @param  roleId the primary key of the role
	 * @param  actionId the action ID
	 * @throws PortalException if the user did not have permission to remove
	 *         resource permissions, or if a role with the primary key or a
	 *         resource action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void removeResourcePermissions(
			long groupId, long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.removeResourcePermissions(
			companyId, name, scope, roleId, actionId);
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
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key
	 * @param  roleId the primary key of the role
	 * @param  actionIds the action IDs of the actions
	 * @throws PortalException if the user did not have permission to set
	 *         resource permissions, or if a role with the primary key or a
	 *         resource action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setIndividualResourcePermissions(
			long groupId, long companyId, String name, String primKey,
			long roleId, String[] actionIds)
		throws PortalException, SystemException {

		permissionService.checkPermission(groupId, name, primKey);

		resourcePermissionLocalService.setResourcePermissions(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
			roleId, actionIds);
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
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key
	 * @param  roleIdsToActionIds a map of role IDs to action IDs of the actions
	 * @throws PortalException if the user did not have permission to set
	 *         resource permissions, or if a role with the primary key or a
	 *         resource action with the name and action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setIndividualResourcePermissions(
			long groupId, long companyId, String name, String primKey,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException, SystemException {

		permissionService.checkPermission(groupId, name, primKey);

		resourcePermissionLocalService.setResourcePermissions(
			companyId, name, ResourceConstants.SCOPE_INDIVIDUAL, primKey,
			roleIdsToActionIds);
	}

}