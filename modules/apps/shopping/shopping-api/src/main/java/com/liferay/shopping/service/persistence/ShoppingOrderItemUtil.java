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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.shopping.model.ShoppingOrderItem;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the shopping order item service. This utility wraps {@link com.liferay.shopping.service.persistence.impl.ShoppingOrderItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderItemPersistence
 * @see com.liferay.shopping.service.persistence.impl.ShoppingOrderItemPersistenceImpl
 * @generated
 */
@ProviderType
public class ShoppingOrderItemUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ShoppingOrderItem shoppingOrderItem) {
		getPersistence().clearCache(shoppingOrderItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ShoppingOrderItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ShoppingOrderItem update(ShoppingOrderItem shoppingOrderItem) {
		return getPersistence().update(shoppingOrderItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ShoppingOrderItem update(
		ShoppingOrderItem shoppingOrderItem, ServiceContext serviceContext) {
		return getPersistence().update(shoppingOrderItem, serviceContext);
	}

	/**
	* Returns all the shopping order items where orderId = &#63;.
	*
	* @param orderId the order ID
	* @return the matching shopping order items
	*/
	public static List<ShoppingOrderItem> findByOrderId(long orderId) {
		return getPersistence().findByOrderId(orderId);
	}

	/**
	* Returns a range of all the shopping order items where orderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param orderId the order ID
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @return the range of matching shopping order items
	*/
	public static List<ShoppingOrderItem> findByOrderId(long orderId,
		int start, int end) {
		return getPersistence().findByOrderId(orderId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping order items where orderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param orderId the order ID
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping order items
	*/
	public static List<ShoppingOrderItem> findByOrderId(long orderId,
		int start, int end,
		OrderByComparator<ShoppingOrderItem> orderByComparator) {
		return getPersistence()
				   .findByOrderId(orderId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the shopping order items where orderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param orderId the order ID
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping order items
	*/
	public static List<ShoppingOrderItem> findByOrderId(long orderId,
		int start, int end,
		OrderByComparator<ShoppingOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByOrderId(orderId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first shopping order item in the ordered set where orderId = &#63;.
	*
	* @param orderId the order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order item
	* @throws NoSuchOrderItemException if a matching shopping order item could not be found
	*/
	public static ShoppingOrderItem findByOrderId_First(long orderId,
		OrderByComparator<ShoppingOrderItem> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderItemException {
		return getPersistence().findByOrderId_First(orderId, orderByComparator);
	}

	/**
	* Returns the first shopping order item in the ordered set where orderId = &#63;.
	*
	* @param orderId the order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order item, or <code>null</code> if a matching shopping order item could not be found
	*/
	public static ShoppingOrderItem fetchByOrderId_First(long orderId,
		OrderByComparator<ShoppingOrderItem> orderByComparator) {
		return getPersistence().fetchByOrderId_First(orderId, orderByComparator);
	}

	/**
	* Returns the last shopping order item in the ordered set where orderId = &#63;.
	*
	* @param orderId the order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order item
	* @throws NoSuchOrderItemException if a matching shopping order item could not be found
	*/
	public static ShoppingOrderItem findByOrderId_Last(long orderId,
		OrderByComparator<ShoppingOrderItem> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderItemException {
		return getPersistence().findByOrderId_Last(orderId, orderByComparator);
	}

	/**
	* Returns the last shopping order item in the ordered set where orderId = &#63;.
	*
	* @param orderId the order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order item, or <code>null</code> if a matching shopping order item could not be found
	*/
	public static ShoppingOrderItem fetchByOrderId_Last(long orderId,
		OrderByComparator<ShoppingOrderItem> orderByComparator) {
		return getPersistence().fetchByOrderId_Last(orderId, orderByComparator);
	}

	/**
	* Returns the shopping order items before and after the current shopping order item in the ordered set where orderId = &#63;.
	*
	* @param orderItemId the primary key of the current shopping order item
	* @param orderId the order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order item
	* @throws NoSuchOrderItemException if a shopping order item with the primary key could not be found
	*/
	public static ShoppingOrderItem[] findByOrderId_PrevAndNext(
		long orderItemId, long orderId,
		OrderByComparator<ShoppingOrderItem> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByOrderId_PrevAndNext(orderItemId, orderId,
			orderByComparator);
	}

	/**
	* Removes all the shopping order items where orderId = &#63; from the database.
	*
	* @param orderId the order ID
	*/
	public static void removeByOrderId(long orderId) {
		getPersistence().removeByOrderId(orderId);
	}

	/**
	* Returns the number of shopping order items where orderId = &#63;.
	*
	* @param orderId the order ID
	* @return the number of matching shopping order items
	*/
	public static int countByOrderId(long orderId) {
		return getPersistence().countByOrderId(orderId);
	}

	/**
	* Caches the shopping order item in the entity cache if it is enabled.
	*
	* @param shoppingOrderItem the shopping order item
	*/
	public static void cacheResult(ShoppingOrderItem shoppingOrderItem) {
		getPersistence().cacheResult(shoppingOrderItem);
	}

	/**
	* Caches the shopping order items in the entity cache if it is enabled.
	*
	* @param shoppingOrderItems the shopping order items
	*/
	public static void cacheResult(List<ShoppingOrderItem> shoppingOrderItems) {
		getPersistence().cacheResult(shoppingOrderItems);
	}

	/**
	* Creates a new shopping order item with the primary key. Does not add the shopping order item to the database.
	*
	* @param orderItemId the primary key for the new shopping order item
	* @return the new shopping order item
	*/
	public static ShoppingOrderItem create(long orderItemId) {
		return getPersistence().create(orderItemId);
	}

	/**
	* Removes the shopping order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderItemId the primary key of the shopping order item
	* @return the shopping order item that was removed
	* @throws NoSuchOrderItemException if a shopping order item with the primary key could not be found
	*/
	public static ShoppingOrderItem remove(long orderItemId)
		throws com.liferay.shopping.exception.NoSuchOrderItemException {
		return getPersistence().remove(orderItemId);
	}

	public static ShoppingOrderItem updateImpl(
		ShoppingOrderItem shoppingOrderItem) {
		return getPersistence().updateImpl(shoppingOrderItem);
	}

	/**
	* Returns the shopping order item with the primary key or throws a {@link NoSuchOrderItemException} if it could not be found.
	*
	* @param orderItemId the primary key of the shopping order item
	* @return the shopping order item
	* @throws NoSuchOrderItemException if a shopping order item with the primary key could not be found
	*/
	public static ShoppingOrderItem findByPrimaryKey(long orderItemId)
		throws com.liferay.shopping.exception.NoSuchOrderItemException {
		return getPersistence().findByPrimaryKey(orderItemId);
	}

	/**
	* Returns the shopping order item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orderItemId the primary key of the shopping order item
	* @return the shopping order item, or <code>null</code> if a shopping order item with the primary key could not be found
	*/
	public static ShoppingOrderItem fetchByPrimaryKey(long orderItemId) {
		return getPersistence().fetchByPrimaryKey(orderItemId);
	}

	public static java.util.Map<java.io.Serializable, ShoppingOrderItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shopping order items.
	*
	* @return the shopping order items
	*/
	public static List<ShoppingOrderItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @return the range of shopping order items
	*/
	public static List<ShoppingOrderItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping order items
	*/
	public static List<ShoppingOrderItem> findAll(int start, int end,
		OrderByComparator<ShoppingOrderItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the shopping order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping order items
	* @param end the upper bound of the range of shopping order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping order items
	*/
	public static List<ShoppingOrderItem> findAll(int start, int end,
		OrderByComparator<ShoppingOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the shopping order items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping order items.
	*
	* @return the number of shopping order items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ShoppingOrderItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingOrderItemPersistence, ShoppingOrderItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingOrderItemPersistence.class);
}