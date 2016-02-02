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

package com.liferay.counter.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.counter.exception.NoSuchCounterException;
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
 * @see com.liferay.counter.service.persistence.impl.CounterPersistenceImpl
 * @see CounterUtil
 * @generated
 */
@ProviderType
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
	public void cacheResult(Counter counter);

	/**
	* Caches the counters in the entity cache if it is enabled.
	*
	* @param counters the counters
	*/
	public void cacheResult(java.util.List<Counter> counters);

	/**
	* Creates a new counter with the primary key. Does not add the counter to the database.
	*
	* @param name the primary key for the new counter
	* @return the new counter
	*/
	public Counter create(java.lang.String name);

	/**
	* Removes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param name the primary key of the counter
	* @return the counter that was removed
	* @throws NoSuchCounterException if a counter with the primary key could not be found
	*/
	public Counter remove(java.lang.String name) throws NoSuchCounterException;

	public Counter updateImpl(Counter counter);

	/**
	* Returns the counter with the primary key or throws a {@link NoSuchCounterException} if it could not be found.
	*
	* @param name the primary key of the counter
	* @return the counter
	* @throws NoSuchCounterException if a counter with the primary key could not be found
	*/
	public Counter findByPrimaryKey(java.lang.String name)
		throws NoSuchCounterException;

	/**
	* Returns the counter with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param name the primary key of the counter
	* @return the counter, or <code>null</code> if a counter with the primary key could not be found
	*/
	public Counter fetchByPrimaryKey(java.lang.String name);

	@Override
	public java.util.Map<java.io.Serializable, Counter> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the counters.
	*
	* @return the counters
	*/
	public java.util.List<Counter> findAll();

	/**
	* Returns a range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of counters
	* @param end the upper bound of the range of counters (not inclusive)
	* @return the range of counters
	*/
	public java.util.List<Counter> findAll(int start, int end);

	/**
	* Returns an ordered range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of counters
	* @param end the upper bound of the range of counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of counters
	*/
	public java.util.List<Counter> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Counter> orderByComparator);

	/**
	* Returns an ordered range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of counters
	* @param end the upper bound of the range of counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of counters
	*/
	public java.util.List<Counter> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Counter> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the counters from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of counters.
	*
	* @return the number of counters
	*/
	public int countAll();
}