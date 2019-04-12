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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ResourceBlockPermissionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionLocalService
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public class ResourceBlockPermissionLocalServiceWrapper
	implements ResourceBlockPermissionLocalService,
			   ServiceWrapper<ResourceBlockPermissionLocalService> {

	public ResourceBlockPermissionLocalServiceWrapper(
		ResourceBlockPermissionLocalService
			resourceBlockPermissionLocalService) {

		_resourceBlockPermissionLocalService =
			resourceBlockPermissionLocalService;
	}

	/**
	 * Adds the resource block permission to the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
		addResourceBlockPermission(
			com.liferay.portal.kernel.model.ResourceBlockPermission
				resourceBlockPermission) {

		return _resourceBlockPermissionLocalService.addResourceBlockPermission(
			resourceBlockPermission);
	}

	@Override
	public void addResourceBlockPermissions(
		long resourceBlockId,
		com.liferay.portal.kernel.model.ResourceBlockPermissionsContainer
			resourceBlockPermissionsContainer) {

		_resourceBlockPermissionLocalService.addResourceBlockPermissions(
			resourceBlockId, resourceBlockPermissionsContainer);
	}

	/**
	 * Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	 *
	 * @param resourceBlockPermissionId the primary key for the new resource block permission
	 * @return the new resource block permission
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
		createResourceBlockPermission(long resourceBlockPermissionId) {

		return _resourceBlockPermissionLocalService.
			createResourceBlockPermission(resourceBlockPermissionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _resourceBlockPermissionLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission that was removed
	 * @throws PortalException if a resource block permission with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
			deleteResourceBlockPermission(long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _resourceBlockPermissionLocalService.
			deleteResourceBlockPermission(resourceBlockPermissionId);
	}

	/**
	 * Deletes the resource block permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
		deleteResourceBlockPermission(
			com.liferay.portal.kernel.model.ResourceBlockPermission
				resourceBlockPermission) {

		return _resourceBlockPermissionLocalService.
			deleteResourceBlockPermission(resourceBlockPermission);
	}

	@Override
	public void deleteResourceBlockPermissions(long resourceBlockId) {
		_resourceBlockPermissionLocalService.deleteResourceBlockPermissions(
			resourceBlockId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourceBlockPermissionLocalService.dynamicQuery();
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

		return _resourceBlockPermissionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _resourceBlockPermissionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _resourceBlockPermissionLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _resourceBlockPermissionLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _resourceBlockPermissionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
		fetchResourceBlockPermission(long resourceBlockPermissionId) {

		return _resourceBlockPermissionLocalService.
			fetchResourceBlockPermission(resourceBlockPermissionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _resourceBlockPermissionLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.Map<Long, java.util.Set<String>>
			getAvailableResourceBlockPermissionActionIds(
				String name, long primKey, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _resourceBlockPermissionLocalService.
			getAvailableResourceBlockPermissionActionIds(
				name, primKey, actionIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _resourceBlockPermissionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _resourceBlockPermissionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _resourceBlockPermissionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the resource block permission with the primary key.
	 *
	 * @param resourceBlockPermissionId the primary key of the resource block permission
	 * @return the resource block permission
	 * @throws PortalException if a resource block permission with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
			getResourceBlockPermission(long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _resourceBlockPermissionLocalService.getResourceBlockPermission(
			resourceBlockPermissionId);
	}

	/**
	 * Returns a range of all the resource block permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource block permissions
	 * @param end the upper bound of the range of resource block permissions (not inclusive)
	 * @return the range of resource block permissions
	 */
	@Override
	public java.util.List
		<com.liferay.portal.kernel.model.ResourceBlockPermission>
			getResourceBlockPermissions(int start, int end) {

		return _resourceBlockPermissionLocalService.getResourceBlockPermissions(
			start, end);
	}

	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermissionsContainer
		getResourceBlockPermissionsContainer(long resourceBlockId) {

		return _resourceBlockPermissionLocalService.
			getResourceBlockPermissionsContainer(resourceBlockId);
	}

	/**
	 * Returns the number of resource block permissions.
	 *
	 * @return the number of resource block permissions
	 */
	@Override
	public int getResourceBlockPermissionsCount() {
		return _resourceBlockPermissionLocalService.
			getResourceBlockPermissionsCount();
	}

	@Override
	public int getResourceBlockPermissionsCount(
		long resourceBlockId, long roleId) {

		return _resourceBlockPermissionLocalService.
			getResourceBlockPermissionsCount(resourceBlockId, roleId);
	}

	@Override
	public void updateResourceBlockPermission(
		long resourceBlockId, long roleId, long actionIdsLong, int operator) {

		_resourceBlockPermissionLocalService.updateResourceBlockPermission(
			resourceBlockId, roleId, actionIdsLong, operator);
	}

	/**
	 * Updates the resource block permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param resourceBlockPermission the resource block permission
	 * @return the resource block permission that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.ResourceBlockPermission
		updateResourceBlockPermission(
			com.liferay.portal.kernel.model.ResourceBlockPermission
				resourceBlockPermission) {

		return _resourceBlockPermissionLocalService.
			updateResourceBlockPermission(resourceBlockPermission);
	}

	@Override
	public ResourceBlockPermissionLocalService getWrappedService() {
		return _resourceBlockPermissionLocalService;
	}

	@Override
	public void setWrappedService(
		ResourceBlockPermissionLocalService
			resourceBlockPermissionLocalService) {

		_resourceBlockPermissionLocalService =
			resourceBlockPermissionLocalService;
	}

	private ResourceBlockPermissionLocalService
		_resourceBlockPermissionLocalService;

}