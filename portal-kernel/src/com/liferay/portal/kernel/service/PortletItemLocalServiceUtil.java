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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for PortletItem. This utility wraps
 * <code>com.liferay.portal.service.impl.PortletItemLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemLocalService
 * @generated
 */
public class PortletItemLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PortletItemLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.PortletItem addPortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPortletItem(
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
	public static com.liferay.portal.kernel.model.PortletItem addPortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return getService().addPortletItem(portletItem);
	}

	/**
	 * Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	 *
	 * @param portletItemId the primary key for the new portlet item
	 * @return the new portlet item
	 */
	public static com.liferay.portal.kernel.model.PortletItem createPortletItem(
		long portletItemId) {

		return getService().createPortletItem(portletItemId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static com.liferay.portal.kernel.model.PortletItem deletePortletItem(
			long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePortletItem(portletItemId);
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
	public static com.liferay.portal.kernel.model.PortletItem deletePortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return getService().deletePortletItem(portletItem);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.kernel.model.PortletItem fetchPortletItem(
		long portletItemId) {

		return getService().fetchPortletItem(portletItemId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the portlet item with the primary key.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item
	 * @throws PortalException if a portlet item with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.PortletItem getPortletItem(
			long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPortletItem(portletItemId);
	}

	public static com.liferay.portal.kernel.model.PortletItem getPortletItem(
			long groupId, String name, String portletId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPortletItem(groupId, name, portletId, className);
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
	public static java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(int start, int end) {

		return getService().getPortletItems(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(long groupId, String className) {

		return getService().getPortletItems(groupId, className);
	}

	public static java.util.List<com.liferay.portal.kernel.model.PortletItem>
		getPortletItems(long groupId, String portletId, String className) {

		return getService().getPortletItems(groupId, portletId, className);
	}

	/**
	 * Returns the number of portlet items.
	 *
	 * @return the number of portlet items
	 */
	public static int getPortletItemsCount() {
		return getService().getPortletItemsCount();
	}

	public static com.liferay.portal.kernel.model.PortletItem updatePortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePortletItem(
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
	public static com.liferay.portal.kernel.model.PortletItem updatePortletItem(
		com.liferay.portal.kernel.model.PortletItem portletItem) {

		return getService().updatePortletItem(portletItem);
	}

	public static PortletItemLocalService getService() {
		if (_service == null) {
			_service = (PortletItemLocalService)PortalBeanLocatorUtil.locate(
				PortletItemLocalService.class.getName());
		}

		return _service;
	}

	private static PortletItemLocalService _service;

}