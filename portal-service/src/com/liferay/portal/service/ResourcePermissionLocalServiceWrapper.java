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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ResourcePermissionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionLocalService
 * @generated
 */
@ProviderType
public class ResourcePermissionLocalServiceWrapper
	implements ResourcePermissionLocalService,
		ServiceWrapper<ResourcePermissionLocalService> {
	public ResourcePermissionLocalServiceWrapper(
		ResourcePermissionLocalService resourcePermissionLocalService) {
		_resourcePermissionLocalService = resourcePermissionLocalService;
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
	*/
	@Override
	public void addResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.addResourcePermission(companyId, name,
			scope, primKey, roleId, actionId);
	}

	/**
	* Adds the resource permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was added
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		return _resourcePermissionLocalService.addResourcePermission(resourcePermission);
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
	* @param resourceName the resource's name, which can be either a class name
	or a portlet ID
	* @param roleName the role's name
	* @param scope the scope
	* @param resourceActionBitwiseValue the bitwise IDs of the actions
	*/
	@Override
	public void addResourcePermissions(java.lang.String resourceName,
		java.lang.String roleName, int scope, long resourceActionBitwiseValue) {
		_resourcePermissionLocalService.addResourcePermissions(resourceName,
			roleName, scope, resourceActionBitwiseValue);
	}

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return _resourcePermissionLocalService.createResourcePermission(resourcePermissionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was removed
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		return _resourcePermissionLocalService.deleteResourcePermission(resourcePermission);
	}

	/**
	* Deletes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission that was removed
	* @throws PortalException if a resource permission with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission deleteResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.deleteResourcePermission(resourcePermissionId);
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
	*/
	@Override
	public void deleteResourcePermissions(long companyId,
		java.lang.String name, int scope, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.deleteResourcePermissions(companyId,
			name, scope, primKey);
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
	*/
	@Override
	public void deleteResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.deleteResourcePermissions(companyId,
			name, scope, primKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourcePermissionLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _resourcePermissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _resourcePermissionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.ResourcePermission fetchResourcePermission(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId) {
		return _resourcePermissionLocalService.fetchResourcePermission(companyId,
			name, scope, primKey, roleId);
	}

	@Override
	public com.liferay.portal.model.ResourcePermission fetchResourcePermission(
		long resourcePermissionId) {
		return _resourcePermissionLocalService.fetchResourcePermission(resourcePermissionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _resourcePermissionLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		java.util.Collection<java.lang.String> actionIds) {
		return _resourcePermissionLocalService.getAvailableResourcePermissionActionIds(companyId,
			name, scope, primKey, actionIds);
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
	*/
	@Override
	public java.util.List<java.lang.String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId,
		java.util.Collection<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.getAvailableResourcePermissionActionIds(companyId,
			name, scope, primKey, roleId, actionIds);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getAvailableResourcePermissionActionIds(long, String, int,
	String, Collection)}
	*/
	@Deprecated
	@Override
	public java.util.Map<java.lang.Long, java.util.Set<java.lang.String>> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds,
		java.util.Collection<java.lang.String> actionIds) {
		return _resourcePermissionLocalService.getAvailableResourcePermissionActionIds(companyId,
			name, scope, primKey, roleIds, actionIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _resourcePermissionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _resourcePermissionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.getPersistedModel(primaryKeyObj);
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
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission getResourcePermission(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.getResourcePermission(companyId,
			name, scope, primKey, roleId);
	}

	/**
	* Returns the resource permission with the primary key.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission
	* @throws PortalException if a resource permission with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.getResourcePermission(resourcePermissionId);
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
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey) {
		return _resourcePermissionLocalService.getResourcePermissions(companyId,
			name, scope, primKey);
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
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end) {
		return _resourcePermissionLocalService.getResourcePermissions(start, end);
	}

	/**
	* Returns the number of resource permissions.
	*
	* @return the number of resource permissions
	*/
	@Override
	public int getResourcePermissionsCount() {
		return _resourcePermissionLocalService.getResourcePermissionsCount();
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
	*/
	@Override
	public int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey) {
		return _resourcePermissionLocalService.getResourcePermissionsCount(companyId,
			name, scope, primKey);
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
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourceResourcePermissions(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey) {
		return _resourcePermissionLocalService.getResourceResourcePermissions(companyId,
			groupId, name, primKey);
	}

	/**
	* Returns all the resource permissions for the role.
	*
	* @param roleId the primary key of the role
	* @return the resource permissions for the role
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId);
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
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end) {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId,
			scopes, start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.getRoles(companyId, name, scope,
			primKey, actionId);
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
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourcePermission> getScopeResourcePermissions(
		int[] scopes) {
		return _resourcePermissionLocalService.getScopeResourcePermissions(scopes);
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
	@Override
	public boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourcePermissionLocalService.hasActionId(resourcePermission,
			resourceAction);
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
	*/
	@Override
	public boolean hasResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.hasResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
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
	*/
	@Override
	public boolean hasResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.hasResourcePermission(companyId,
			name, scope, primKey, roleIds, actionId);
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
	*/
	@Override
	public boolean hasResourcePermission(
		java.util.List<com.liferay.portal.model.Resource> resources,
		long[] roleIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.hasResourcePermission(resources,
			roleIds, actionId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getRoles(long, String, int,
	String, String}
	*/
	@Deprecated
	@Override
	public boolean[] hasResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.hasResourcePermissions(companyId,
			name, scope, primKey, roleIds, actionId);
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
	*/
	@Override
	public boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourcePermissionLocalService.hasScopeResourcePermission(companyId,
			name, scope, roleId, actionId);
	}

	/**
	* Reassigns all the resource permissions from the source role to the
	* destination role, and deletes the source role.
	*
	* @param fromRoleId the primary key of the source role
	* @param toRoleId the primary key of the destination role
	*/
	@Override
	public void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.mergePermissions(fromRoleId, toRoleId);
	}

	/**
	* Grants the role default permissions to all the resources of the type and
	* at the scope stored in the resource permission, deletes the resource
	* permission, and deletes the resource permission's role if it has no
	* permissions remaining.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @param toRoleId the primary key of the role
	*/
	@Override
	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.reassignPermissions(resourcePermissionId,
			toRoleId);
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
	*/
	@Override
	public void removeResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.removeResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
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
	*/
	@Override
	public void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.removeResourcePermissions(companyId,
			name, scope, roleId, actionId);
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
	*/
	@Override
	public void setOwnerResourcePermissions(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long ownerId, java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.setOwnerResourcePermissions(companyId,
			name, scope, primKey, roleId, ownerId, actionIds);
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
	*/
	@Override
	public void setResourcePermissions(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.setResourcePermissions(companyId, name,
			scope, primKey, roleId, actionIds);
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
	*/
	@Override
	public void setResourcePermissions(long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_resourcePermissionLocalService.setResourcePermissions(companyId, name,
			scope, primKey, roleIdsToActionIds);
	}

	/**
	* Updates the resource permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission
	* @return the resource permission that was updated
	*/
	@Override
	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		return _resourcePermissionLocalService.updateResourcePermission(resourcePermission);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ResourcePermissionLocalService getWrappedResourcePermissionLocalService() {
		return _resourcePermissionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	public ResourcePermissionLocalService getWrappedService() {
		return _resourcePermissionLocalService;
	}

	@Override
	public void setWrappedService(
		ResourcePermissionLocalService resourcePermissionLocalService) {
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	private ResourcePermissionLocalService _resourcePermissionLocalService;
}