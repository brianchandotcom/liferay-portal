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
 * Provides a wrapper for {@link ResourceTypePermissionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionLocalService
 * @generated
 */
@ProviderType
public class ResourceTypePermissionLocalServiceWrapper
	implements ResourceTypePermissionLocalService,
		ServiceWrapper<ResourceTypePermissionLocalService> {
	public ResourceTypePermissionLocalServiceWrapper(
		ResourceTypePermissionLocalService resourceTypePermissionLocalService) {
		_resourceTypePermissionLocalService = resourceTypePermissionLocalService;
	}

	/**
	* Adds the resource type permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was added
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission addResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		return _resourceTypePermissionLocalService.addResourceTypePermission(resourceTypePermission);
	}

	/**
	* Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	*
	* @param resourceTypePermissionId the primary key for the new resource type permission
	* @return the new resource type permission
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission createResourceTypePermission(
		long resourceTypePermissionId) {
		return _resourceTypePermissionLocalService.createResourceTypePermission(resourceTypePermissionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource type permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was removed
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission deleteResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		return _resourceTypePermissionLocalService.deleteResourceTypePermission(resourceTypePermission);
	}

	/**
	* Deletes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission that was removed
	* @throws PortalException if a resource type permission with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission deleteResourceTypePermission(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.deleteResourceTypePermission(resourceTypePermissionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourceTypePermissionLocalService.dynamicQuery();
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
		return _resourceTypePermissionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _resourceTypePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _resourceTypePermissionLocalService.dynamicQuery(dynamicQuery,
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
		return _resourceTypePermissionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _resourceTypePermissionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.ResourceTypePermission fetchResourceTypePermission(
		long resourceTypePermissionId) {
		return _resourceTypePermissionLocalService.fetchResourceTypePermission(resourceTypePermissionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _resourceTypePermissionLocalService.getActionableDynamicQuery();
	}

	@Override
	public long getCompanyScopeActionIds(long companyId, java.lang.String name,
		long roleId) {
		return _resourceTypePermissionLocalService.getCompanyScopeActionIds(companyId,
			name, roleId);
	}

	@Override
	public long getGroupScopeActionIds(long companyId, long groupId,
		java.lang.String name, long roleId) {
		return _resourceTypePermissionLocalService.getGroupScopeActionIds(companyId,
			groupId, name, roleId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> getGroupScopeResourceTypePermissions(
		long companyId, java.lang.String name, long roleId) {
		return _resourceTypePermissionLocalService.getGroupScopeResourceTypePermissions(companyId,
			name, roleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _resourceTypePermissionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _resourceTypePermissionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.model.ResourceBlockPermissionsContainer getResourceBlockPermissionsContainer(
		long companyId, long groupId, java.lang.String name) {
		return _resourceTypePermissionLocalService.getResourceBlockPermissionsContainer(companyId,
			groupId, name);
	}

	/**
	* Returns the resource type permission with the primary key.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission
	* @throws PortalException if a resource type permission with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission getResourceTypePermission(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.getResourceTypePermission(resourceTypePermissionId);
	}

	/**
	* Returns a range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of resource type permissions
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> getResourceTypePermissions(
		int start, int end) {
		return _resourceTypePermissionLocalService.getResourceTypePermissions(start,
			end);
	}

	/**
	* Returns the number of resource type permissions.
	*
	* @return the number of resource type permissions
	*/
	@Override
	public int getResourceTypePermissionsCount() {
		return _resourceTypePermissionLocalService.getResourceTypePermissionsCount();
	}

	@Override
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> getRoleResourceTypePermissions(
		long roleId) {
		return _resourceTypePermissionLocalService.getRoleResourceTypePermissions(roleId);
	}

	@Override
	public boolean hasCompanyScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.hasCompanyScopePermission(companyId,
			name, roleId, actionId);
	}

	@Override
	public boolean hasEitherScopePermission(long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.hasEitherScopePermission(companyId,
			name, roleId, actionId);
	}

	@Override
	public boolean hasGroupScopePermission(long companyId, long groupId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceTypePermissionLocalService.hasGroupScopePermission(companyId,
			groupId, name, roleId, actionId);
	}

	@Override
	public void updateCompanyScopeResourceTypePermissions(long companyId,
		java.lang.String name, long roleId, long actionIdsLong, long operator) {
		_resourceTypePermissionLocalService.updateCompanyScopeResourceTypePermissions(companyId,
			name, roleId, actionIdsLong, operator);
	}

	@Override
	public void updateGroupScopeResourceTypePermissions(long companyId,
		long groupId, java.lang.String name, long roleId, long actionIdsLong,
		long operator) {
		_resourceTypePermissionLocalService.updateGroupScopeResourceTypePermissions(companyId,
			groupId, name, roleId, actionIdsLong, operator);
	}

	/**
	* Updates the resource type permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermission the resource type permission
	* @return the resource type permission that was updated
	*/
	@Override
	public com.liferay.portal.model.ResourceTypePermission updateResourceTypePermission(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		return _resourceTypePermissionLocalService.updateResourceTypePermission(resourceTypePermission);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ResourceTypePermissionLocalService getWrappedResourceTypePermissionLocalService() {
		return _resourceTypePermissionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedResourceTypePermissionLocalService(
		ResourceTypePermissionLocalService resourceTypePermissionLocalService) {
		_resourceTypePermissionLocalService = resourceTypePermissionLocalService;
	}

	@Override
	public ResourceTypePermissionLocalService getWrappedService() {
		return _resourceTypePermissionLocalService;
	}

	@Override
	public void setWrappedService(
		ResourceTypePermissionLocalService resourceTypePermissionLocalService) {
		_resourceTypePermissionLocalService = resourceTypePermissionLocalService;
	}

	private ResourceTypePermissionLocalService _resourceTypePermissionLocalService;
}