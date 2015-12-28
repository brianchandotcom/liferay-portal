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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ShoppingOrderItem. This utility wraps
 * {@link com.liferay.shopping.service.impl.ShoppingOrderItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItemLocalService
 * @see com.liferay.shopping.service.base.ShoppingOrderItemLocalServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingOrderItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class ShoppingOrderItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingOrderItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the shopping order item to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrderItem the shopping order item
	* @return the shopping order item that was added
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem addShoppingOrderItem(
		com.liferay.shopping.model.ShoppingOrderItem shoppingOrderItem) {
		return getService().addShoppingOrderItem(shoppingOrderItem);
	}

	/**
	* Creates a new shopping order item with the primary key. Does not add the shopping order item to the database.
	*
	* @param orderItemId the primary key for the new shopping order item
	* @return the new shopping order item
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem createShoppingOrderItem(
		long orderItemId) {
		return getService().createShoppingOrderItem(orderItemId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderItemId the primary key of the shopping order item
	* @return the shopping order item that was removed
	* @throws PortalException if a shopping order item with the primary key could not be found
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem deleteShoppingOrderItem(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteShoppingOrderItem(orderItemId);
	}

	/**
	* Deletes the shopping order item from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrderItem the shopping order item
	* @return the shopping order item that was removed
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem deleteShoppingOrderItem(
		com.liferay.shopping.model.ShoppingOrderItem shoppingOrderItem) {
		return getService().deleteShoppingOrderItem(shoppingOrderItem);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.shopping.model.ShoppingOrderItem fetchShoppingOrderItem(
		long orderItemId) {
		return getService().fetchShoppingOrderItem(orderItemId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingOrderItem> getOrderItems(
		long orderId) {
		return getService().getOrderItems(orderId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the shopping order item with the primary key.
	*
	* @param orderItemId the primary key of the shopping order item
	* @return the shopping order item
	* @throws PortalException if a shopping order item with the primary key could not be found
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem getShoppingOrderItem(
		long orderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getShoppingOrderItem(orderItemId);
	}

	/**
	* Returns a range of all the shopping order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @return the range of shopping order items
	*/
	public static java.util.List<com.liferay.shopping.model.ShoppingOrderItem> getShoppingOrderItems(
		int start, int end) {
		return getService().getShoppingOrderItems(start, end);
	}

	/**
	* Returns the number of shopping order items.
	*
	* @return the number of shopping order items
	*/
	public static int getShoppingOrderItemsCount() {
		return getService().getShoppingOrderItemsCount();
	}

	/**
	* Updates the shopping order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrderItem the shopping order item
	* @return the shopping order item that was updated
	*/
	public static com.liferay.shopping.model.ShoppingOrderItem updateShoppingOrderItem(
		com.liferay.shopping.model.ShoppingOrderItem shoppingOrderItem) {
		return getService().updateShoppingOrderItem(shoppingOrderItem);
	}

	public static ShoppingOrderItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingOrderItemLocalService, ShoppingOrderItemLocalService> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingOrderItemLocalService.class);
}