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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.PermissionServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.PermissionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.PermissionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Permission}, that is translated to a
 * {@link com.liferay.portal.model.PermissionSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionServiceHttp
 * @see       com.liferay.portal.model.PermissionSoap
 * @see       com.liferay.portal.service.PermissionServiceUtil
 * @generated
 */
public class PermissionServiceSoap {
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
	public static void checkPermission(long groupId, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void checkPermission(long groupId, java.lang.String name,
		long primKey) throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, name, primKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void checkPermission(long groupId, java.lang.String name,
		java.lang.String primKey) throws RemoteException {
		try {
			PermissionServiceUtil.checkPermission(groupId, name, primKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId) throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasGroupPermission(groupId,
					actionId, resourceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId) throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasUserPermission(userId,
					actionId, resourceId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasUserPermissions(long userId, long groupId,
		com.liferay.portal.model.ResourceSoap[] resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws RemoteException {
		try {
			boolean returnValue = PermissionServiceUtil.hasUserPermissions(userId,
					groupId,
					com.liferay.portal.model.impl.ResourceModelImpl.toModels(
						resources), actionId, permissionCheckerBag);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(groupId, actionIds,
				resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId) throws RemoteException {
		try {
			PermissionServiceUtil.setGroupPermissions(className, classPK,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setOrgGroupPermissions(organizationId,
				groupId, actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId) throws RemoteException {
		try {
			PermissionServiceUtil.setRolePermission(roleId, groupId, name,
				scope, primKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setRolePermissions(long roleId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setRolePermissions(roleId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void setUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.setUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void unsetRolePermission(long roleId, long groupId,
		long permissionId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermission(roleId, groupId,
				permissionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void unsetRolePermission(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId) throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermission(roleId, groupId, name,
				scope, primKey, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void unsetRolePermissions(long roleId, long groupId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws RemoteException {
		try {
			PermissionServiceUtil.unsetRolePermissions(roleId, groupId, name,
				scope, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void unsetUserPermissions(long userId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws RemoteException {
		try {
			PermissionServiceUtil.unsetUserPermissions(userId, groupId,
				actionIds, resourceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionServiceSoap.class);
}