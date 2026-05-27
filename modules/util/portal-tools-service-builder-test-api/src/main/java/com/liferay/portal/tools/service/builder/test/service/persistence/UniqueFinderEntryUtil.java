/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the unique finder entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.UniqueFinderEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UniqueFinderEntryPersistence
 * @generated
 */
public class UniqueFinderEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<UniqueFinderEntry> uniqueFinderEntries) {

		getPersistence().cacheResult(uniqueFinderEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(UniqueFinderEntry uniqueFinderEntry) {
		getPersistence().cacheResult(uniqueFinderEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(UniqueFinderEntry uniqueFinderEntry) {
		getPersistence().clearCache(uniqueFinderEntry);
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
	public static Map<Serializable, UniqueFinderEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UniqueFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UniqueFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UniqueFinderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UniqueFinderEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UniqueFinderEntry update(
		UniqueFinderEntry uniqueFinderEntry) {

		return getPersistence().update(uniqueFinderEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UniqueFinderEntry update(
		UniqueFinderEntry uniqueFinderEntry, ServiceContext serviceContext) {

		return getPersistence().update(uniqueFinderEntry, serviceContext);
	}

	/**
	 * Returns the unique finder entry where name = &#63; or throws a <code>NoSuchUniqueFinderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching unique finder entry
	 * @throws NoSuchUniqueFinderEntryException if a matching unique finder entry could not be found
	 */
	public static UniqueFinderEntry findByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUniqueFinderEntryException {

		return getPersistence().findByName(name);
	}

	/**
	 * Returns the unique finder entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching unique finder entry, or <code>null</code> if a matching unique finder entry could not be found
	 */
	public static UniqueFinderEntry fetchByName(
		String name, boolean useFinderCache) {

		return getPersistence().fetchByName(name, useFinderCache);
	}

	/**
	 * Removes the unique finder entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the unique finder entry that was removed
	 */
	public static UniqueFinderEntry removeByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUniqueFinderEntryException {

		return getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of unique finder entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching unique finder entries
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Creates a new unique finder entry with the primary key. Does not add the unique finder entry to the database.
	 *
	 * @param uniqueFinderEntryId the primary key for the new unique finder entry
	 * @return the new unique finder entry
	 */
	public static UniqueFinderEntry create(long uniqueFinderEntryId) {
		return getPersistence().create(uniqueFinderEntryId);
	}

	/**
	 * Removes the unique finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry that was removed
	 * @throws NoSuchUniqueFinderEntryException if a unique finder entry with the primary key could not be found
	 */
	public static UniqueFinderEntry remove(long uniqueFinderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUniqueFinderEntryException {

		return getPersistence().remove(uniqueFinderEntryId);
	}

	public static UniqueFinderEntry updateImpl(
		UniqueFinderEntry uniqueFinderEntry) {

		return getPersistence().updateImpl(uniqueFinderEntry);
	}

	/**
	 * Returns the unique finder entry with the primary key or throws a <code>NoSuchUniqueFinderEntryException</code> if it could not be found.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry
	 * @throws NoSuchUniqueFinderEntryException if a unique finder entry with the primary key could not be found
	 */
	public static UniqueFinderEntry findByPrimaryKey(long uniqueFinderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUniqueFinderEntryException {

		return getPersistence().findByPrimaryKey(uniqueFinderEntryId);
	}

	/**
	 * Returns the unique finder entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry, or <code>null</code> if a unique finder entry with the primary key could not be found
	 */
	public static UniqueFinderEntry fetchByPrimaryKey(
		long uniqueFinderEntryId) {

		return getPersistence().fetchByPrimaryKey(uniqueFinderEntryId);
	}

	/**
	 * Returns the unique finder entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching unique finder entry, or <code>null</code> if a matching unique finder entry could not be found
	 */
	public static UniqueFinderEntry fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	public static UniqueFinderEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		UniqueFinderEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile UniqueFinderEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1120010184