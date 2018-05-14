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

import com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the commerce cloud forecast order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.cloud.client.service.persistence.impl.CommerceCloudForecastOrderPersistenceImpl
 * @see CommerceCloudForecastOrderUtil
 * @generated
 */
@ProviderType
public interface CommerceCloudForecastOrderPersistence extends BasePersistence<CommerceCloudForecastOrder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCloudForecastOrderUtil} to access the commerce cloud forecast order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder findByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudForecastOrderException;

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId);

	/**
	* Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId, boolean retrieveFromCache);

	/**
	* Removes the commerce cloud forecast order where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @return the commerce cloud forecast order that was removed
	*/
	public CommerceCloudForecastOrder removeByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudForecastOrderException;

	/**
	* Returns the number of commerce cloud forecast orders where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce cloud forecast orders
	*/
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	* Returns all the commerce cloud forecast orders where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the matching commerce cloud forecast orders
	*/
	public java.util.List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate);

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
	public java.util.List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end);

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
	public java.util.List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator);

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
	public java.util.List<CommerceCloudForecastOrder> findBySyncDate(
		Date syncDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder findBySyncDate_First(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException;

	/**
	* Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder fetchBySyncDate_First(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator);

	/**
	* Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder findBySyncDate_Last(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException;

	/**
	* Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	*/
	public CommerceCloudForecastOrder fetchBySyncDate_Last(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator);

	/**
	* Returns the commerce cloud forecast orders before and after the current commerce cloud forecast order in the ordered set where syncDate = &#63;.
	*
	* @param commerceCloudForecastOrderId the primary key of the current commerce cloud forecast order
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public CommerceCloudForecastOrder[] findBySyncDate_PrevAndNext(
		long commerceCloudForecastOrderId, Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException;

	/**
	* Removes all the commerce cloud forecast orders where syncDate = &#63; from the database.
	*
	* @param syncDate the sync date
	*/
	public void removeBySyncDate(Date syncDate);

	/**
	* Returns the number of commerce cloud forecast orders where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the number of matching commerce cloud forecast orders
	*/
	public int countBySyncDate(Date syncDate);

	/**
	* Caches the commerce cloud forecast order in the entity cache if it is enabled.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	*/
	public void cacheResult(
		CommerceCloudForecastOrder commerceCloudForecastOrder);

	/**
	* Caches the commerce cloud forecast orders in the entity cache if it is enabled.
	*
	* @param commerceCloudForecastOrders the commerce cloud forecast orders
	*/
	public void cacheResult(
		java.util.List<CommerceCloudForecastOrder> commerceCloudForecastOrders);

	/**
	* Creates a new commerce cloud forecast order with the primary key. Does not add the commerce cloud forecast order to the database.
	*
	* @param commerceCloudForecastOrderId the primary key for the new commerce cloud forecast order
	* @return the new commerce cloud forecast order
	*/
	public CommerceCloudForecastOrder create(long commerceCloudForecastOrderId);

	/**
	* Removes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public CommerceCloudForecastOrder remove(long commerceCloudForecastOrderId)
		throws NoSuchCloudForecastOrderException;

	public CommerceCloudForecastOrder updateImpl(
		CommerceCloudForecastOrder commerceCloudForecastOrder);

	/**
	* Returns the commerce cloud forecast order with the primary key or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order
	* @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	*/
	public CommerceCloudForecastOrder findByPrimaryKey(
		long commerceCloudForecastOrderId)
		throws NoSuchCloudForecastOrderException;

	/**
	* Returns the commerce cloud forecast order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order, or <code>null</code> if a commerce cloud forecast order with the primary key could not be found
	*/
	public CommerceCloudForecastOrder fetchByPrimaryKey(
		long commerceCloudForecastOrderId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCloudForecastOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce cloud forecast orders.
	*
	* @return the commerce cloud forecast orders
	*/
	public java.util.List<CommerceCloudForecastOrder> findAll();

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
	public java.util.List<CommerceCloudForecastOrder> findAll(int start, int end);

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
	public java.util.List<CommerceCloudForecastOrder> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator);

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
	public java.util.List<CommerceCloudForecastOrder> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce cloud forecast orders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce cloud forecast orders.
	*
	* @return the number of commerce cloud forecast orders
	*/
	public int countAll();
}