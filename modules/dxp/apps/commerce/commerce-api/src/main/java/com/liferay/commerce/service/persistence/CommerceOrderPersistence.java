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

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderPersistenceImpl
 * @see CommerceOrderUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderPersistence extends BasePersistence<CommerceOrder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderUtil} to access the commerce order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the commerce order in the entity cache if it is enabled.
	*
	* @param commerceOrder the commerce order
	*/
	public void cacheResult(CommerceOrder commerceOrder);

	/**
	* Caches the commerce orders in the entity cache if it is enabled.
	*
	* @param commerceOrders the commerce orders
	*/
	public void cacheResult(java.util.List<CommerceOrder> commerceOrders);

	/**
	* Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	*
	* @param commerceOrderId the primary key for the new commerce order
	* @return the new commerce order
	*/
	public CommerceOrder create(long commerceOrderId);

	/**
	* Removes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order that was removed
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public CommerceOrder remove(long commerceOrderId)
		throws NoSuchOrderException;

	public CommerceOrder updateImpl(CommerceOrder commerceOrder);

	/**
	* Returns the commerce order with the primary key or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public CommerceOrder findByPrimaryKey(long commerceOrderId)
		throws NoSuchOrderException;

	/**
	* Returns the commerce order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order, or <code>null</code> if a commerce order with the primary key could not be found
	*/
	public CommerceOrder fetchByPrimaryKey(long commerceOrderId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce orders.
	*
	* @return the commerce orders
	*/
	public java.util.List<CommerceOrder> findAll();

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
	public java.util.List<CommerceOrder> findAll(int start, int end);

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
	public java.util.List<CommerceOrder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrder> orderByComparator);

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
	public java.util.List<CommerceOrder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce orders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce orders.
	*
	* @return the number of commerce orders
	*/
	public int countAll();
}