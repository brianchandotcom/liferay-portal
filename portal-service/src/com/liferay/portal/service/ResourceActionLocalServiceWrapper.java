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

/**
 * Provides a wrapper for {@link ResourceActionLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionLocalService
 * @generated
 */
public class ResourceActionLocalServiceWrapper
	implements ResourceActionLocalService,
		ServiceWrapper<ResourceActionLocalService> {
	public ResourceActionLocalServiceWrapper(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	/**
	* Adds the resource action to the database. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceAction addResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.addResourceAction(resourceAction);
	}

	/**
	* Creates a new resource action with the primary key. Does not add the resource action to the database.
	*
	* @param resourceActionId the primary key for the new resource action
	* @return the new resource action
	*/
	public com.liferay.portal.model.ResourceAction createResourceAction(
		long resourceActionId) {
		return _resourceActionLocalService.createResourceAction(resourceActionId);
	}

	/**
	* Deletes the resource action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceActionId the primary key of the resource action
	* @return the resource action that was removed
	* @throws PortalException if a resource action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceAction deleteResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.deleteResourceAction(resourceActionId);
	}

	/**
	* Deletes the resource action from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceAction deleteResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _resourceActionLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ResourceAction fetchResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.fetchResourceAction(resourceActionId);
	}

	/**
	* Returns the resource action with the primary key.
	*
	* @param resourceActionId the primary key of the resource action
	* @return the resource action
	* @throws PortalException if a resource action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceAction getResourceAction(
		long resourceActionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceAction(resourceActionId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActions(start, end);
	}

	/**
	* Returns the number of resource actions.
	*
	* @return the number of resource actions
	* @throws SystemException if a system exception occurred
	*/
	public int getResourceActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActionsCount();
	}

	/**
	* Updates the resource action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param resourceAction the resource action
	* @return the resource action that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.updateResourceAction(resourceAction);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _resourceActionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_resourceActionLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void checkResourceActions()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.checkResourceActions();
	}

	public void checkResourceActions(java.lang.String name,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.checkResourceActions(name, actionIds);
	}

	public void checkResourceActions(java.lang.String name,
		java.util.List<java.lang.String> actionIds, boolean addDefaultActions)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceActionLocalService.checkResourceActions(name, actionIds,
			addDefaultActions);
	}

	public com.liferay.portal.model.ResourceAction fetchResourceAction(
		java.lang.String name, java.lang.String actionId) {
		return _resourceActionLocalService.fetchResourceAction(name, actionId);
	}

	public com.liferay.portal.model.ResourceAction getResourceAction(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _resourceActionLocalService.getResourceAction(name, actionId);
	}

	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceActionLocalService.getResourceActions(name);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ResourceActionLocalService getWrappedResourceActionLocalService() {
		return _resourceActionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	public ResourceActionLocalService getWrappedService() {
		return _resourceActionLocalService;
	}

	public void setWrappedService(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	private ResourceActionLocalService _resourceActionLocalService;
}