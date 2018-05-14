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

package com.liferay.commerce.cloud.client.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the commerce cloud forecast order service. This utility wraps {@link com.liferay.commerce.cloud.client.service.persistence.impl.CommerceCloudForecastOrderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrderPersistence
 * @see com.liferay.commerce.cloud.client.service.persistence.impl.CommerceCloudForecastOrderPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderUtil {
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
	public static void clearCache(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		getPersistence().clearCache(commerceCloudForecastOrder);
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
	public static List<CommerceCloudForecastOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCloudForecastOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCloudForecastOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCloudForecastOrder update(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return getPersistence().update(commerceCloudForecastOrder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCloudForecastOrder update(
		CommerceCloudForecastOrder commerceCloudForecastOrder,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceCloudForecastOrder, serviceContext);
	}

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder findByCommerceOrderId(
		long commerceOrderId)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId) {
		return getPersistence().fetchByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCommerceOrderId(commerceOrderId, retrieveFromCache);
	}

	/**
	* Removes the commerce cloud forecast order where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @return the commerce cloud forecast order that was removed
	*/
	public static CommerceCloudForecastOrder removeByCommerceOrderId(
		long commerceOrderId)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the number of commerce cloud forecast orders where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce cloud forecast orders
	*/
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns all the commerce cloud forecast orders where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the matching commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findBySyncDate(Date syncDate) {
		return getPersistence().findBySyncDate(syncDate);
	}

	/**
	* Returns a range of all the commerce cloud forecast orders where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @return the range of matching commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end) {
		return getPersistence().findBySyncDate(syncDate, start, end);
	}

	/**
	* Returns an ordered range of all the commerce cloud forecast orders where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return getPersistence()
				   .findBySyncDate(syncDate, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cloud forecast orders where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySyncDate(syncDate, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder findBySyncDate_First(
		Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().findBySyncDate_First(syncDate, orderByComparator);
	}

	/**
	* Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder fetchBySyncDate_First(
		Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return getPersistence()
				   .fetchBySyncDate_First(syncDate, orderByComparator);
	}

	/**
	* Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder findBySyncDate_Last(
		Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().findBySyncDate_Last(syncDate, orderByComparator);
	}

	/**
	* Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public static CommerceCloudForecastOrder fetchBySyncDate_Last(
		Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return getPersistence().fetchBySyncDate_Last(syncDate, orderByComparator);
	}

	/**
	* Returns the commerce cloud forecast orders before and after the current commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param commerceCloudForecastOrderId the primary key of the current commerce cloud forecast order
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public static CommerceCloudForecastOrder[] findBySyncDate_PrevAndNext(
		long commerceCloudForecastOrderId, Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence()
				   .findBySyncDate_PrevAndNext(commerceCloudForecastOrderId,
			syncDate, orderByComparator);
	}

	/**
	* Removes all the commerce cloud forecast orders where syncDate = &#63; from the database.
	*
	* @param syncDate the sync date
	*/
	public static void removeBySyncDate(Date syncDate) {
		getPersistence().removeBySyncDate(syncDate);
	}

	/**
	* Returns the number of commerce cloud forecast orders where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the number of matching commerce cloud forecast orders
	*/
	public static int countBySyncDate(Date syncDate) {
		return getPersistence().countBySyncDate(syncDate);
	}

	/**
	* Caches the commerce cloud forecast order in the entity cache if it is enabled.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	*/
	public static void cacheResult(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		getPersistence().cacheResult(commerceCloudForecastOrder);
	}

	/**
	* Caches the commerce cloud forecast orders in the entity cache if it is enabled.
	*
	* @param commerceCloudForecastOrders the commerce cloud forecast orders
	*/
	public static void cacheResult(
		List<CommerceCloudForecastOrder> commerceCloudForecastOrders) {
		getPersistence().cacheResult(commerceCloudForecastOrders);
	}

	/**
	* Creates a new commerce cloud forecast order with the primary key. Does not add the commerce cloud forecast order to the database.
	*
	* @param commerceCloudForecastOrderId the primary key for the new commerce cloud forecast order
	* @return the new commerce cloud forecast order
	*/
	public static CommerceCloudForecastOrder create(
		long commerceCloudForecastOrderId) {
		return getPersistence().create(commerceCloudForecastOrderId);
	}

	/**
	* Removes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public static CommerceCloudForecastOrder remove(
		long commerceCloudForecastOrderId)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().remove(commerceCloudForecastOrderId);
	}

	public static CommerceCloudForecastOrder updateImpl(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return getPersistence().updateImpl(commerceCloudForecastOrder);
	}

	/**
	* Returns the commerce cloud forecast order with the primary key or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public static CommerceCloudForecastOrder findByPrimaryKey(
		long commerceCloudForecastOrderId)
		throws com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException {
		return getPersistence().findByPrimaryKey(commerceCloudForecastOrderId);
	}

	/**
	* Returns the commerce cloud forecast order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order, or <code>null</code> if a commerce cloud forecast order with the primary key could not be found
	*/
	public static CommerceCloudForecastOrder fetchByPrimaryKey(
		long commerceCloudForecastOrderId) {
		return getPersistence().fetchByPrimaryKey(commerceCloudForecastOrderId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCloudForecastOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce cloud forecast orders.
	*
	* @return the commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce cloud forecast orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @return the range of commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce cloud forecast orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findAll(int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cloud forecast orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce cloud forecast orders
	*/
	public static List<CommerceCloudForecastOrder> findAll(int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce cloud forecast orders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce cloud forecast orders.
	*
	* @return the number of commerce cloud forecast orders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceCloudForecastOrderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCloudForecastOrderPersistence, CommerceCloudForecastOrderPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCloudForecastOrderPersistence.class);

		ServiceTracker<CommerceCloudForecastOrderPersistence, CommerceCloudForecastOrderPersistence> serviceTracker =
			new ServiceTracker<CommerceCloudForecastOrderPersistence, CommerceCloudForecastOrderPersistence>(bundle.getBundleContext(),
				CommerceCloudForecastOrderPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}