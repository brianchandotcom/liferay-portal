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
import com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the redundant index entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.RedundantIndexEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedundantIndexEntryPersistence
 * @generated
 */
public class RedundantIndexEntryUtil {

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
	public static void clearCache(RedundantIndexEntry redundantIndexEntry) {
		getPersistence().clearCache(redundantIndexEntry);
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
	public static Map<Serializable, RedundantIndexEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RedundantIndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RedundantIndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RedundantIndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RedundantIndexEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RedundantIndexEntry update(
		RedundantIndexEntry redundantIndexEntry) {

		return getPersistence().update(redundantIndexEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RedundantIndexEntry update(
		RedundantIndexEntry redundantIndexEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(redundantIndexEntry, serviceContext);
	}

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a matching redundant index entry could not be found
	 */
	public static RedundantIndexEntry findByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRedundantIndexEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	public static RedundantIndexEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the redundant index entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redundant index entry, or <code>null</code> if a matching redundant index entry could not be found
	 */
	public static RedundantIndexEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the redundant index entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the redundant index entry that was removed
	 */
	public static RedundantIndexEntry removeByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRedundantIndexEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of redundant index entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching redundant index entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the redundant index entry in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntry the redundant index entry
	 */
	public static void cacheResult(RedundantIndexEntry redundantIndexEntry) {
		getPersistence().cacheResult(redundantIndexEntry);
	}

	/**
	 * Caches the redundant index entries in the entity cache if it is enabled.
	 *
	 * @param redundantIndexEntries the redundant index entries
	 */
	public static void cacheResult(
		List<RedundantIndexEntry> redundantIndexEntries) {

		getPersistence().cacheResult(redundantIndexEntries);
	}

	/**
	 * Creates a new redundant index entry with the primary key. Does not add the redundant index entry to the database.
	 *
	 * @param redundantIndexEntryId the primary key for the new redundant index entry
	 * @return the new redundant index entry
	 */
	public static RedundantIndexEntry create(long redundantIndexEntryId) {
		return getPersistence().create(redundantIndexEntryId);
	}

	/**
	 * Removes the redundant index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry that was removed
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	public static RedundantIndexEntry remove(long redundantIndexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRedundantIndexEntryException {

		return getPersistence().remove(redundantIndexEntryId);
	}

	public static RedundantIndexEntry updateImpl(
		RedundantIndexEntry redundantIndexEntry) {

		return getPersistence().updateImpl(redundantIndexEntry);
	}

	/**
	 * Returns the redundant index entry with the primary key or throws a <code>NoSuchRedundantIndexEntryException</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry
	 * @throws NoSuchRedundantIndexEntryException if a redundant index entry with the primary key could not be found
	 */
	public static RedundantIndexEntry findByPrimaryKey(
			long redundantIndexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRedundantIndexEntryException {

		return getPersistence().findByPrimaryKey(redundantIndexEntryId);
	}

	/**
	 * Returns the redundant index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry, or <code>null</code> if a redundant index entry with the primary key could not be found
	 */
	public static RedundantIndexEntry fetchByPrimaryKey(
		long redundantIndexEntryId) {

		return getPersistence().fetchByPrimaryKey(redundantIndexEntryId);
	}

	/**
	 * Returns all the redundant index entries.
	 *
	 * @return the redundant index entries
	 */
	public static List<RedundantIndexEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @return the range of redundant index entries
	 */
	public static List<RedundantIndexEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redundant index entries
	 */
	public static List<RedundantIndexEntry> findAll(
		int start, int end,
		OrderByComparator<RedundantIndexEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redundant index entries
	 */
	public static List<RedundantIndexEntry> findAll(
		int start, int end,
		OrderByComparator<RedundantIndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the redundant index entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of redundant index entries.
	 *
	 * @return the number of redundant index entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RedundantIndexEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RedundantIndexEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RedundantIndexEntryPersistence _persistence;

}