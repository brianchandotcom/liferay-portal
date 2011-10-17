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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link PermissionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionService
 * @generated
 */
public class PermissionServiceWrapper implements PermissionService,
	ServiceWrapper<PermissionService> {
	public PermissionServiceWrapper(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	/**
	* Checks to see if the group has permission to the resource.
	*
	* @param groupId the primary key of the group
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource, or if a group or resource with the primary key could
	not be found or was invalid
	* @throws SystemException if a system exception occurred
	*/
	public void checkPermission(long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, resourceId);
	}

	/**
	* Checks to see if the group has permission to the service.
	*
	* @param groupId the primary key of the group
	* @param name the service name
	* @param primKey the primary key of the service
	* @throws PortalException if the group did not have permission to the
	service, if a group with the primary key could not be found or
	if the permission information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public void checkPermission(long groupId, java.lang.String name,
		long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	/**
	* Checks to see if the group has permission to the service.
	*
	* @param groupId the primary key of the group
	* @param name the service name
	* @param primKey the primary key of the service
	* @throws PortalException if the group did not have permission to the
	service, if a group with the primary key could not be found or
	if the permission information was invalid
	* @throws SystemException if a system exception occurred
	*/
	public void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.checkPermission(groupId, name, primKey);
	}

	/**
	* Returns <code>true</code> if the group has permission to perform the
	* action on the resource.
	*
	* @param groupId the primary key of the group
	* @param actionId the action's ID
	* @param resourceId the primary key of the resource
	* @return <code>true</code> if the group has permission to perform the
	action on the resource; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	/**
	* Returns <code>true</code> if the user has permission to perform the
	* action on the resource.
	*
	* @param userId the primary key of the user
	* @param actionId the action's ID
	* @param resourceId the primary key of the resource
	* @return <code>true</code> if the user has permission to perform the
	action on the resource; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermission(userId, actionId, resourceId);
	}

	/**
	* Returns <code>true</code> if the user has permission to perform the
	* action on the resources.
	*
	* <p>
	* This method does not support resources managed by the resource block
	* system.
	* </p>
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group containing the resource
	* @param resources representations of the resource at each scope level
	returned by {@link
	com.liferay.portal.security.permission.AdvancedPermissionChecker#getResources(
	long, long, String, String, String)}
	* @param actionId the action's ID
	* @param permissionCheckerBag the permission checker bag
	* @return <code>true</code> if the user has permission to perform the
	action on the resources; <code>false</code> otherwise
	* @throws PortalException if a resource action based on any one of the
	resources and the action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionService.hasUserPermissions(userId, groupId,
			resources, actionId, permissionCheckerBag);
	}

	/**
	* Sets the group's permissions to perform the actions on the resource,
	* replacing the group's existing permissions on the resource.
	*
	* @param groupId the primary key of the group
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if a group with the primary key could not be
	found or if the group did not have permission to the resource
	* @throws SystemException if a system exception occurred
	*/
	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(groupId, actionIds, resourceId);
	}

	/**
	* Sets the entity's group permissions to perform the actions on the
	* resource, replacing the entity's existing group permissions on the
	* resource. Only {@link com.liferay.portal.model.Organization} and {@link
	* com.liferay.portal.model.UserGroup} class entities are supported.
	*
	* @param className the class name of an organization or user group
	* @param classPK the primary key of the class
	* @param groupId the primary key of the group
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource, if an entity with the class name and primary key could
	not be found, or if the entity's associated group could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	/**
	* Sets the permissions of each role to perform respective actions on the
	* resource, replacing the existing permissions of each role on the
	* resource.
	*
	* @param groupId the primary key of the group
	* @param companyId the primary key of the company
	* @param roleIdsToActionIds the map of roles to their new actions on the
	resource
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource
	* @throws SystemException if a system exception occurred
	*/
	public void setIndividualPermissions(long groupId, long companyId,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setIndividualPermissions(groupId, companyId,
			roleIdsToActionIds, resourceId);
	}

	/**
	* Sets the organization permission to perform the actions on the resource
	* for a particular group, replacing the organization's existing
	* permissions on the resource.
	*
	* @param organizationId the primary key of the organization
	* @param groupId the primary key of the group in which to scope the
	permissions
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource or if an organization with the primary key could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	/**
	* Sets the role's permissions to perform the action on the named resource,
	* replacing the role's existing permissions on the resource.
	*
	* @param roleId the primary key of the role
	* @param groupId the primary key of the group
	* @param name the resource name
	* @param scope the resource scope
	* @param primKey the resource primKey
	* @param actionId the action's ID
	* @throws PortalException if the group did not have permission to the role
	or if the scope was {@link
	com.liferay.portal.model.ResourceConstants#SCOPE_INDIVIDUAL}
	* @throws SystemException if a system exception occurred
	*/
	public void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	/**
	* Sets the role's permissions to perform the actions on the resource,
	* replacing the role's existing permissions on the resource.
	*
	* @param roleId the primary key of the role
	* @param groupId the primary key of the group
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource or if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setRolePermissions(roleId, groupId, actionIds,
			resourceId);
	}

	/**
	* Sets the user's permissions to perform the actions on the resource,
	* replacing the user's existing permissions on the resource.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource or if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.setUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	/**
	* Removes the permission from the role.
	*
	* @param roleId the primary key of the role
	* @param groupId the primary key of the group
	* @param permissionId the primary key of the permission
	* @throws PortalException if the group did not have permission to the role
	* @throws SystemException if a system exception occurred
	*/
	public void unsetRolePermission(long roleId, long groupId, long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, permissionId);
	}

	/**
	* Removes the role's permissions to perform the action on the named
	* resource with the scope and primKey.
	*
	* @param roleId the primary key of the role
	* @param groupId the primary key of the group
	* @param name the resource name
	* @param scope the resource scope
	* @param primKey the resource primKey
	* @param actionId the action's ID
	* @throws PortalException if the group did not have permission to the role
	* @throws SystemException if a system exception occurred
	*/
	public void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermission(roleId, groupId, name, scope,
			primKey, actionId);
	}

	/**
	* Removes the role's permissions to perform the action on the named
	* resource.
	*
	* @param roleId the primary key of the role
	* @param groupId the primary key of the group
	* @param name the resource name
	* @param scope the resource scope
	* @param actionId the action's ID
	* @throws PortalException if the group did not have permission to the role
	* @throws SystemException if a system exception occurred
	*/
	public void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetRolePermissions(roleId, groupId, name, scope,
			actionId);
	}

	/**
	* Removes the user's permissions to perform the actions on the resource.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @param actionIds the primary keys of the actions
	* @param resourceId the primary key of the resource
	* @throws PortalException if the group did not have permission to the
	resource
	* @throws SystemException if a system exception occurred
	*/
	public void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionService.unsetUserPermissions(userId, groupId, actionIds,
			resourceId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public PermissionService getWrappedPermissionService() {
		return _permissionService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedPermissionService(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	public PermissionService getWrappedService() {
		return _permissionService;
	}

	public void setWrappedService(PermissionService permissionService) {
		_permissionService = permissionService;
	}

	private PermissionService _permissionService;
}