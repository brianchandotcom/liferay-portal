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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cache miss entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.CacheMissEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntryPersistence
 * @generated
 */
public class CacheMissEntryUtil {

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
	public static void clearCache(CacheMissEntry cacheMissEntry) {
		getPersistence().clearCache(cacheMissEntry);
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
	public static Map<Serializable, CacheMissEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CacheMissEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CacheMissEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CacheMissEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CacheMissEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CacheMissEntry update(CacheMissEntry cacheMissEntry) {
		return getPersistence().update(cacheMissEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CacheMissEntry update(
		CacheMissEntry cacheMissEntry, ServiceContext serviceContext) {

		return getPersistence().update(cacheMissEntry, serviceContext);
	}

	/**
	 * Caches the cache miss entry in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntry the cache miss entry
	 */
	public static void cacheResult(CacheMissEntry cacheMissEntry) {
		getPersistence().cacheResult(cacheMissEntry);
	}

	/**
	 * Caches the cache miss entries in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntries the cache miss entries
	 */
	public static void cacheResult(List<CacheMissEntry> cacheMissEntries) {
		getPersistence().cacheResult(cacheMissEntries);
	}

	/**
	 * Creates a new cache miss entry with the primary key. Does not add the cache miss entry to the database.
	 *
	 * @param cacheMissEntryId the primary key for the new cache miss entry
	 * @return the new cache miss entry
	 */
	public static CacheMissEntry create(long cacheMissEntryId) {
		return getPersistence().create(cacheMissEntryId);
	}

	/**
	 * Removes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public static CacheMissEntry remove(long cacheMissEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheMissEntryException {

		return getPersistence().remove(cacheMissEntryId);
	}

	public static CacheMissEntry updateImpl(CacheMissEntry cacheMissEntry) {
		return getPersistence().updateImpl(cacheMissEntry);
	}

	/**
	 * Returns the cache miss entry with the primary key or throws a <code>NoSuchCacheMissEntryException</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public static CacheMissEntry findByPrimaryKey(long cacheMissEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheMissEntryException {

		return getPersistence().findByPrimaryKey(cacheMissEntryId);
	}

	/**
	 * Returns the cache miss entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry, or <code>null</code> if a cache miss entry with the primary key could not be found
	 */
	public static CacheMissEntry fetchByPrimaryKey(long cacheMissEntryId) {
		return getPersistence().fetchByPrimaryKey(cacheMissEntryId);
	}

	/**
	 * Returns all the cache miss entries.
	 *
	 * @return the cache miss entries
	 */
	public static List<CacheMissEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @return the range of cache miss entries
	 */
	public static List<CacheMissEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache miss entries
	 */
	public static List<CacheMissEntry> findAll(
		int start, int end,
		OrderByComparator<CacheMissEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache miss entries
	 */
	public static List<CacheMissEntry> findAll(
		int start, int end, OrderByComparator<CacheMissEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cache miss entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cache miss entries.
	 *
	 * @return the number of cache miss entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CacheMissEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CacheMissEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CacheMissEntryPersistence _persistence;

}