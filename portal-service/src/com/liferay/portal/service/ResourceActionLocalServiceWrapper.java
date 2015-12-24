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
 * Provides a wrapper for {@link ResourceActionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceActionLocalService
 * @generated
 */
@ProviderType
public class ResourceActionLocalServiceWrapper
	implements ResourceActionLocalService,
		ServiceWrapper<ResourceActionLocalService> {
	public ResourceActionLocalServiceWrapper(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	@Override
	public com.liferay.portal.model.ResourceAction addResourceAction(
		java.lang.String name, java.lang.String actionId, long bitwiseValue) {
		return _resourceActionLocalService.addResourceAction(name, actionId,
			bitwiseValue);
	}

	/**
	* Adds the resource action to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was added
	*/
	@Override
	public com.liferay.portal.model.ResourceAction addResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceActionLocalService.addResourceAction(resourceAction);
	}

	@Override
	public void checkResourceActions() {
		_resourceActionLocalService.checkResourceActions();
	}

	@Override
	public void checkResourceActions(java.lang.String name,
		java.util.List<java.lang.String> actionIds) {
		_resourceActionLocalService.checkResourceActions(name, actionIds);
	}

	@Override
	public void checkResourceActions(java.lang.String name,
		java.util.List<java.lang.String> actionIds, boolean addDefaultActions) {
		_resourceActionLocalService.checkResourceActions(name, actionIds,
			addDefaultActions);
	}

	/**
	* Creates a new resource action with the primary key. Does not add the resource action to the database.
	*
	* @param resourceActionId the primary key for the new resource action
	* @return the new resource action
	*/
	@Override
	public com.liferay.portal.model.ResourceAction createResourceAction(
		long resourceActionId) {
		return _resourceActionLocalService.createResourceAction(resourceActionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource action from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was removed
	*/
	@Override
	public com.liferay.portal.model.ResourceAction deleteResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	/**
	* Deletes the resource action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceActionId the primary key of the resource action
	* @return the resource action that was removed
	* @throws PortalException if a resource action with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceAction deleteResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.deleteResourceAction(resourceActionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourceActionLocalService.dynamicQuery();
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
		return _resourceActionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _resourceActionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _resourceActionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.ResourceAction fetchResourceAction(
		java.lang.String name, java.lang.String actionId) {
		return _resourceActionLocalService.fetchResourceAction(name, actionId);
	}

	@Override
	public com.liferay.portal.model.ResourceAction fetchResourceAction(
		long resourceActionId) {
		return _resourceActionLocalService.fetchResourceAction(resourceActionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _resourceActionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _resourceActionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _resourceActionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.model.ResourceAction getResourceAction(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.getResourceAction(name, actionId);
	}

	/**
	* Returns the resource action with the primary key.
	*
	* @param resourceActionId the primary key of the resource action
	* @return the resource action
	* @throws PortalException if a resource action with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.ResourceAction getResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.getResourceAction(resourceActionId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		java.lang.String name) {
		return _resourceActionLocalService.getResourceActions(name);
	}

	/**
	* Returns a range of all the resource actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource actions
	* @param end the upper bound of the range of resource actions (not inclusive)
	* @return the range of resource actions
	*/
	@Override
	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		int start, int end) {
		return _resourceActionLocalService.getResourceActions(start, end);
	}

	/**
	* Returns the number of resource actions.
	*
	* @return the number of resource actions
	*/
	@Override
	public int getResourceActionsCount() {
		return _resourceActionLocalService.getResourceActionsCount();
	}

	@Override
	public int getResourceActionsCount(java.lang.String name) {
		return _resourceActionLocalService.getResourceActionsCount(name);
	}

	/**
	* Updates the resource action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was updated
	*/
	@Override
	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceActionLocalService.updateResourceAction(resourceAction);
	}

	@Override
	public ResourceActionLocalService getWrappedService() {
		return _resourceActionLocalService;
	}

	@Override
	public void setWrappedService(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	private ResourceActionLocalService _resourceActionLocalService;
}