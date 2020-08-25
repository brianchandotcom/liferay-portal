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

/**
 * Provides a wrapper for {@link PortletItemLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemLocalService
 * @generated
 */
public class PortletItemLocalServiceWrapper
	implements PortletItemLocalService,
			   ServiceWrapper<PortletItemLocalService> {

	public PortletItemLocalServiceWrapper(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.PortletItem addPortletItem(
			long userId, long groupId, java.lang.String name,
			java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.addPortletItem(
			userId, groupId, name, portletId, className);
	}

	/**
	 * Adds the portlet item to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortletItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portletItem the portlet item
	 * @return the portlet item that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem addPortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return _portletItemLocalService.addPortletItem(portletItem);
	}

	/**
	 * Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	 *
	 * @param portletItemId the primary key for the new portlet item
	 * @return the new portlet item
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem createPortletItem(
		long portletItemId) {

		return _portletItemLocalService.createPortletItem(portletItemId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortletItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item that was removed
	 * @throws PortalException if a portlet item with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem deletePortletItem(
			long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.deletePortletItem(portletItemId);
	}

	/**
	 * Deletes the portlet item from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortletItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portletItem the portlet item
	 * @return the portlet item that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem deletePortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return _portletItemLocalService.deletePortletItem(portletItem);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portletItemLocalService.dynamicQuery();
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

		return _portletItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortletItemModelImpl</code>.
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

		return _portletItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortletItemModelImpl</code>.
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

		return _portletItemLocalService.dynamicQuery(
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

		return _portletItemLocalService.dynamicQueryCount(dynamicQuery);
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

		return _portletItemLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.PortletItem fetchPortletItem(
		long portletItemId) {

		return _portletItemLocalService.fetchPortletItem(portletItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _portletItemLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _portletItemLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _portletItemLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the portlet item with the primary key.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item
	 * @throws PortalException if a portlet item with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem getPortletItem(
			long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.getPortletItem(portletItemId);
	}

	@Override
	public com.liferay.portal.kernel.model.PortletItem getPortletItem(
			long groupId, java.lang.String name, java.lang.String portletId,
			java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.getPortletItem(
			groupId, name, portletId, className);
	}

	/**
	 * Returns a range of all the portlet items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortletItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of portlet items
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(int start, int end) {

		return _portletItemLocalService.getPortletItems(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(long groupId, java.lang.String className) {

		return _portletItemLocalService.getPortletItems(groupId, className);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(
			long groupId, java.lang.String portletId,
			java.lang.String className) {

		return _portletItemLocalService.getPortletItems(
			groupId, portletId, className);
	}

	/**
	 * Returns the number of portlet items.
	 *
	 * @return the number of portlet items
	 */
	@Override
	public int getPortletItemsCount() {
		return _portletItemLocalService.getPortletItemsCount();
	}

	@Override
	public com.liferay.portal.kernel.model.PortletItem updatePortletItem(
			long userId, long groupId, java.lang.String name,
			java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portletItemLocalService.updatePortletItem(
			userId, groupId, name, portletId, className);
	}

	/**
	 * Updates the portlet item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortletItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portletItem the portlet item
	 * @return the portlet item that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.PortletItem updatePortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return _portletItemLocalService.updatePortletItem(portletItem);
	}

	@Override
	public PortletItemLocalService getWrappedService() {
		return _portletItemLocalService;
	}

	@Override
	public void setWrappedService(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	private PortletItemLocalService _portletItemLocalService;

}