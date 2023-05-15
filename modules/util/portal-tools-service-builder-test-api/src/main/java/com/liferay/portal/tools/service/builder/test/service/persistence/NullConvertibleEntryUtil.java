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
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the null convertible entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.NullConvertibleEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntryPersistence
 * @generated
 */
public class NullConvertibleEntryUtil {

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
	public static void clearCache(NullConvertibleEntry nullConvertibleEntry) {
		getPersistence().clearCache(nullConvertibleEntry);
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
	public static Map<Serializable, NullConvertibleEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NullConvertibleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NullConvertibleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NullConvertibleEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NullConvertibleEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NullConvertibleEntry update(
		NullConvertibleEntry nullConvertibleEntry) {

		return getPersistence().update(nullConvertibleEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NullConvertibleEntry update(
		NullConvertibleEntry nullConvertibleEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(nullConvertibleEntry, serviceContext);
	}

	/**
	 * Returns the null convertible entry where name = &#63; or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a matching null convertible entry could not be found
	 */
	public static NullConvertibleEntry findByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNullConvertibleEntryException {

		return getPersistence().findByName(name);
	}

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	public static NullConvertibleEntry fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	 * Returns the null convertible entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching null convertible entry, or <code>null</code> if a matching null convertible entry could not be found
	 */
	public static NullConvertibleEntry fetchByName(
		String name, boolean useFinderCache) {

		return getPersistence().fetchByName(name, useFinderCache);
	}

	/**
	 * Removes the null convertible entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the null convertible entry that was removed
	 */
	public static NullConvertibleEntry removeByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNullConvertibleEntryException {

		return getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of null convertible entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching null convertible entries
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Caches the null convertible entry in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 */
	public static void cacheResult(NullConvertibleEntry nullConvertibleEntry) {
		getPersistence().cacheResult(nullConvertibleEntry);
	}

	/**
	 * Caches the null convertible entries in the entity cache if it is enabled.
	 *
	 * @param nullConvertibleEntries the null convertible entries
	 */
	public static void cacheResult(
		List<NullConvertibleEntry> nullConvertibleEntries) {

		getPersistence().cacheResult(nullConvertibleEntries);
	}

	/**
	 * Creates a new null convertible entry with the primary key. Does not add the null convertible entry to the database.
	 *
	 * @param nullConvertibleEntryId the primary key for the new null convertible entry
	 * @return the new null convertible entry
	 */
	public static NullConvertibleEntry create(long nullConvertibleEntryId) {
		return getPersistence().create(nullConvertibleEntryId);
	}

	/**
	 * Removes the null convertible entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry that was removed
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	public static NullConvertibleEntry remove(long nullConvertibleEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNullConvertibleEntryException {

		return getPersistence().remove(nullConvertibleEntryId);
	}

	public static NullConvertibleEntry updateImpl(
		NullConvertibleEntry nullConvertibleEntry) {

		return getPersistence().updateImpl(nullConvertibleEntry);
	}

	/**
	 * Returns the null convertible entry with the primary key or throws a <code>NoSuchNullConvertibleEntryException</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry
	 * @throws NoSuchNullConvertibleEntryException if a null convertible entry with the primary key could not be found
	 */
	public static NullConvertibleEntry findByPrimaryKey(
			long nullConvertibleEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNullConvertibleEntryException {

		return getPersistence().findByPrimaryKey(nullConvertibleEntryId);
	}

	/**
	 * Returns the null convertible entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry, or <code>null</code> if a null convertible entry with the primary key could not be found
	 */
	public static NullConvertibleEntry fetchByPrimaryKey(
		long nullConvertibleEntryId) {

		return getPersistence().fetchByPrimaryKey(nullConvertibleEntryId);
	}

	/**
	 * Returns all the null convertible entries.
	 *
	 * @return the null convertible entries
	 */
	public static List<NullConvertibleEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @return the range of null convertible entries
	 */
	public static List<NullConvertibleEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of null convertible entries
	 */
	public static List<NullConvertibleEntry> findAll(
		int start, int end,
		OrderByComparator<NullConvertibleEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of null convertible entries
	 */
	public static List<NullConvertibleEntry> findAll(
		int start, int end,
		OrderByComparator<NullConvertibleEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the null convertible entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of null convertible entries.
	 *
	 * @return the number of null convertible entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NullConvertibleEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NullConvertibleEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NullConvertibleEntryPersistence _persistence;

}