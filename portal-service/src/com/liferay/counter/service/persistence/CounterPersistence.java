/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.counter.service.persistence;

import com.liferay.counter.model.Counter;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CounterPersistenceImpl
 * @see CounterUtil
 * @generated
 */
public interface CounterPersistence extends BasePersistence<Counter> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CounterUtil} to access the counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the counter in the entity cache if it is enabled.
	*
	* @param counter the counter
	*/
	public void cacheResult(com.liferay.counter.model.Counter counter);

	/**
	* Caches the counters in the entity cache if it is enabled.
	*
	* @param counters the counters
	*/
	public void cacheResult(
		java.util.List<com.liferay.counter.model.Counter> counters);

	/**
	* Creates a new counter with the primary key. Does not add the counter to the database.
	*
	* @param name the primary key for the new counter
	* @return the new counter
	*/
	public com.liferay.counter.model.Counter create(java.lang.String name);

	/**
	* Removes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param name the primary key of the counter
	* @return the counter that was removed
	* @throws com.liferay.counter.NoSuchCounterException if a counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter remove(java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.counter.model.Counter updateImpl(
		com.liferay.counter.model.Counter counter, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the counter with the primary key or throws a {@link com.liferay.counter.NoSuchCounterException} if it could not be found.
	*
	* @param name the primary key of the counter
	* @return the counter
	* @throws com.liferay.counter.NoSuchCounterException if a counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter findByPrimaryKey(
		java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the counter with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param name the primary key of the counter
	* @return the counter, or <code>null</code> if a counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter fetchByPrimaryKey(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the counters.
	*
	* @return the counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.counter.model.Counter> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of counters
	* @param end the upper bound of the range of counters (not inclusive)
	* @return the range of counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of counters
	* @param end the upper bound of the range of counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the counters from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of counters.
	*
	* @return the number of counters
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}