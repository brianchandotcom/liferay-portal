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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ResourcePermission. This utility wraps
 * {@link com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionLocalService
 * @see com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl
 * @generated
 */
public class ResourcePermissionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the resource permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addResourcePermission(resourcePermission);
	}

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public static com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return getService().createResourcePermission(resourcePermissionId);
	}

	/**
	* Deletes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission that was removed
	* @throws PortalException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission deleteResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResourcePermission(resourcePermissionId);
	}

	/**
	* Deletes the resource permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteResourcePermission(resourcePermission);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.ResourcePermission fetchResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchResourcePermission(resourcePermissionId);
	}

	/**
	* Returns the resource permission with the primary key.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission
	* @throws PortalException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermission(resourcePermissionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermissions(start, end);
	}

	/**
	* Returns the number of resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourcePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourcePermissionsCount();
	}

	/**
	* Updates the resource permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateResourcePermission(resourcePermission);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope. This method only supports company, group, and
	group-template scope.
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if scope was set to individual scope or if a role
	with the primary key or a resource action with the name and
	action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void addResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourcePermission(companyId, name, scope, primKey, roleId,
			actionId);
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
	* @param resourceName the resource's name, which can be either a class
	name or a portlet ID
	* @param roleName the role's name
	* @param scope the scope
	* @param resourceActionBitwiseValue the bitwise IDs of the actions
	* @throws SystemException if a system exception occurred
	*/
	public static void addResourcePermissions(java.lang.String resourceName,
		java.lang.String roleName, int scope, long resourceActionBitwiseValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addResourcePermissions(resourceName, roleName, scope,
			resourceActionBitwiseValue);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourcePermissions(long companyId,
		java.lang.String name, int scope, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourcePermissions(companyId, name, scope, primKey);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteResourcePermissions(companyId, name, scope, primKey);
	}

	/**
	* Returns the intersection of action IDs the role has permission at the
	* scope to perform on resources of the type.
	*
	* @param companyId he primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionIds the action IDs
	* @return the intersection of action IDs the role has permission at the
	scope to perform on resources of the type
	* @throws PortalException if a resouce action could not be found for any
	one of the actions on the resource
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<java.lang.String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId,
		java.util.Collection<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAvailableResourcePermissionActionIds(companyId, name,
			scope, primKey, roleId, actionIds);
	}

	public static java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds,
		java.util.Collection<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getAvailableResourcePermissionActionIds(companyId, name,
			scope, primKey, roleIds, actionIds);
	}

	/**
	* Returns the resource permission for the role at the scope to perform the
	* actions on resources of the type.
	*
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @return the resource permission for the role at the scope to perform the
	actions on resources of the type
	* @throws PortalException if no matching resources could be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission getResourcePermission(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourcePermission(companyId, name, scope, primKey,
			roleId);
	}

	/**
	* Returns all the resource permissions at the scope of the type.
	*
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @return the resource permissions at the scope of the type
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourcePermissions(companyId, name, scope, primKey);
	}

	/**
	* Returns the number of resource permissions at the scope of the type.
	*
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @return the number of resource permissions at the scope of the type
	* @throws SystemException if a system exception occurred
	*/
	public static int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourcePermissionsCount(companyId, name, scope, primKey);
	}

	/**
	* Returns the resource permissions that apply to the resource.
	*
	* @param companyId the primary key of the resource's company
	* @param groupId the primary key of the resource's group
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param primKey the primary key of the resource
	* @return the resource permissions associated with the resource
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getResourceResourcePermissions(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getResourceResourcePermissions(companyId, groupId, name,
			primKey);
	}

	/**
	* Returns all the resource permissions for the role.
	*
	* @param roleId the primary key of the role
	* @return the resource permissions for the role
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleResourcePermissions(roleId);
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
	* @param roleId the primary key of the role
	* @param scopes the scopes
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of resource permissions for the role at the scopes
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRoleResourcePermissions(roleId, scopes, start, end);
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
	* @param scopes the scopes
	* @return the resource permissions where scope = any &#63;
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> getScopeResourcePermissions(
		int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getScopeResourcePermissions(scopes);
	}

	/**
	* Returns <code>true</code> if the resource permission grants permission to
	* perform the resource action. Note that this method does not ensure that
	* the resource permission refers to the same type of resource as the
	* resource action.
	*
	* @param resourcePermission the resource permission
	* @param resourceAction the resource action
	* @return <code>true</code> if the resource permission grants permission to
	perform the resource action
	*/
	public static boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return getService().hasActionId(resourcePermission, resourceAction);
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
	* @param resources the resources
	* @param roleIds the primary keys of the roles
	* @param actionId the action ID
	* @return <code>true</code> if any one of the roles has permission to
	perform the action on any one of the resources;
	<code>false</code> otherwise
	* @throws PortalException if any one of the roles with the primary keys
	could not be found or if a resource action with the name and
	action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasResourcePermission(
		java.util.List<com.liferay.portal.model.Resource> resources,
		long[] roleIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasResourcePermission(resources, roleIds, actionId);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @return <code>true</code> if the role has permission to perform the
	action on the resource; <code>false</code> otherwise
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasResourcePermission(companyId, name, scope, primKey,
			roleId, actionId);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleIds the primary keys of the roles
	* @param actionId the action ID
	* @return <code>true</code> if any one of the roles has permission to
	perform the action on the resource; <code>false</code> otherwise
	* @throws PortalException if any one of the roles with the primary keys
	could not be found or if a resource action with the name and
	action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasResourcePermission(companyId, name, scope, primKey,
			roleIds, actionId);
	}

	public static boolean[] hasResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasResourcePermissions(companyId, name, scope, primKey,
			roleIds, actionId);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @return <code>true</code> if the role has permission to perform the
	action on the resource; <code>false</code> otherwise
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasScopeResourcePermission(companyId, name, scope, roleId,
			actionId);
	}

	/**
	* Reassigns all the resource permissions from the source role to the
	* destination role, and deletes the source role.
	*
	* @param fromRoleId the primary key of the source role
	* @param toRoleId the primary key of the destination role
	* @throws PortalException if a role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().mergePermissions(fromRoleId, toRoleId);
	}

	/**
	* Grants the role default permissions to all the resources of the type and
	* at the scope stored in the resource permission, deletes the resource
	* permission, and deletes the resource permission's role if it has no
	* permissions remaining.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @param toRoleId the primary key of the role
	* @throws PortalException if a resource permission or role with the primary
	key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void reassignPermissions(long resourcePermissionId,
		long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().reassignPermissions(resourcePermissionId, toRoleId);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void removeResourcePermission(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermission(companyId, name, scope, primKey, roleId,
			actionId);
	}

	/**
	* Revokes all permissions at the scope from the role to perform the action
	* on resources of the type. For example, this method could be used to
	* revoke all individual scope permissions to edit blog posts from site
	* members.
	*
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param roleId the primary key of the role
	* @param actionId the action ID
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.removeResourcePermissions(companyId, name, scope, roleId, actionId);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param ownerId the primary key of the owner (generally the user that
	created the resource)
	* @param actionIds the action IDs of the actions
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void setOwnerResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long ownerId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setOwnerResourcePermissions(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleId the primary key of the role
	* @param actionIds the action IDs of the actions
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void setResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setResourcePermissions(companyId, name, scope, primKey, roleId,
			actionIds);
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
	* @param companyId the primary key of the company
	* @param name the resource's name, which can be either a class name or a
	portlet ID
	* @param scope the scope
	* @param primKey the primary key
	* @param roleIdsToActionIds a map of role IDs to action IDs of the actions
	* @throws PortalException if a role with the primary key or a resource
	action with the name and action ID could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void setResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setResourcePermissions(companyId, name, scope, primKey,
			roleIdsToActionIds);
	}

	public static ResourcePermissionLocalService getService() {
		if (_service == null) {
			_service = (ResourcePermissionLocalService)PortalBeanLocatorUtil.locate(ResourcePermissionLocalService.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ResourcePermissionLocalService service) {
	}

	private static ResourcePermissionLocalService _service;
}