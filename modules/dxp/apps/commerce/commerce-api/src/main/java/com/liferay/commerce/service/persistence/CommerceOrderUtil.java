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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrder;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce order service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceOrderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderUtil {
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
	public static void clearCache(CommerceOrder commerceOrder) {
		getPersistence().clearCache(commerceOrder);
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
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrder update(CommerceOrder commerceOrder) {
		return getPersistence().update(commerceOrder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrder update(CommerceOrder commerceOrder,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceOrder, serviceContext);
	}

	/**
	* Caches the commerce order in the entity cache if it is enabled.
	*
	* @param commerceOrder the commerce order
	*/
	public static void cacheResult(CommerceOrder commerceOrder) {
		getPersistence().cacheResult(commerceOrder);
	}

	/**
	* Caches the commerce orders in the entity cache if it is enabled.
	*
	* @param commerceOrders the commerce orders
	*/
	public static void cacheResult(List<CommerceOrder> commerceOrders) {
		getPersistence().cacheResult(commerceOrders);
	}

	/**
	* Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	*
	* @param commerceOrderId the primary key for the new commerce order
	* @return the new commerce order
	*/
	public static CommerceOrder create(long commerceOrderId) {
		return getPersistence().create(commerceOrderId);
	}

	/**
	* Removes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order that was removed
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder remove(long commerceOrderId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().remove(commerceOrderId);
	}

	public static CommerceOrder updateImpl(CommerceOrder commerceOrder) {
		return getPersistence().updateImpl(commerceOrder);
	}

	/**
	* Returns the commerce order with the primary key or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder findByPrimaryKey(long commerceOrderId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByPrimaryKey(commerceOrderId);
	}

	/**
	* Returns the commerce order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order, or <code>null</code> if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder fetchByPrimaryKey(long commerceOrderId) {
		return getPersistence().fetchByPrimaryKey(commerceOrderId);
	}

	public static java.util.Map<java.io.Serializable, CommerceOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce orders.
	*
	* @return the commerce orders
	*/
	public static List<CommerceOrder> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce orders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce orders.
	*
	* @return the number of commerce orders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderPersistence, CommerceOrderPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderPersistence.class);
}