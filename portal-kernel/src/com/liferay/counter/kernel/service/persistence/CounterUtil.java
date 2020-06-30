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

package com.liferay.counter.kernel.service.persistence;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the counter service. This utility wraps <code>com.liferay.counter.service.persistence.impl.CounterPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CounterPersistence
 * @generated
 */
public class CounterUtil {

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
	public static void clearCache(Counter counter) {
		getPersistence().clearCache(counter);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Counter> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Counter> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Counter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Counter> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Counter> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Counter update(Counter counter) {
		return getPersistence().update(counter);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Counter update(
		Counter counter, ServiceContext serviceContext) {

		return getPersistence().update(counter, serviceContext);
	}

	/**
	 * Caches the counter in the entity cache if it is enabled.
	 *
	 * @param counter the counter
	 */
	public static void cacheResult(Counter counter) {
		getPersistence().cacheResult(counter);
	}

	/**
	 * Caches the counters in the entity cache if it is enabled.
	 *
	 * @param counters the counters
	 */
	public static void cacheResult(List<Counter> counters) {
		getPersistence().cacheResult(counters);
	}

	/**
	 * Creates a new counter with the primary key. Does not add the counter to the database.
	 *
	 * @param name the primary key for the new counter
	 * @return the new counter
	 */
	public static Counter create(String name) {
		return getPersistence().create(name);
	}

	/**
	 * Removes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param name the primary key of the counter
	 * @return the counter that was removed
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	public static Counter remove(String name)
		throws com.liferay.counter.kernel.exception.NoSuchCounterException {

		return getPersistence().remove(name);
	}

	public static Counter updateImpl(Counter counter) {
		return getPersistence().updateImpl(counter);
	}

	/**
	 * Returns the counter with the primary key or throws a <code>NoSuchCounterException</code> if it could not be found.
	 *
	 * @param name the primary key of the counter
	 * @return the counter
	 * @throws NoSuchCounterException if a counter with the primary key could not be found
	 */
	public static Counter findByPrimaryKey(String name)
		throws com.liferay.counter.kernel.exception.NoSuchCounterException {

		return getPersistence().findByPrimaryKey(name);
	}

	/**
	 * Returns the counter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param name the primary key of the counter
	 * @return the counter, or <code>null</code> if a counter with the primary key could not be found
	 */
	public static Counter fetchByPrimaryKey(String name) {
		return getPersistence().fetchByPrimaryKey(name);
	}

	/**
	 * Returns all the counters.
	 *
	 * @return the counters
	 */
	public static List<Counter> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @return the range of counters
	 */
	public static List<Counter> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of counters
	 */
	public static List<Counter> findAll(
		int start, int end, OrderByComparator<Counter> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of counters
	 * @param end the upper bound of the range of counters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of counters
	 */
	public static List<Counter> findAll(
		int start, int end, OrderByComparator<Counter> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the counters from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of counters.
	 *
	 * @return the number of counters
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CounterPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CounterPersistence)PortalBeanLocatorUtil.locate(
				CounterPersistence.class.getName());
		}

		return _persistence;
	}

	private static CounterPersistence _persistence;

}