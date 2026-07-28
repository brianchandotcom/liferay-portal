/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the timestamp type entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.TimestampTypeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntryPersistence
 * @generated
 */
public class TimestampTypeEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<TimestampTypeEntry> timestampTypeEntries) {

		getPersistence().cacheResult(timestampTypeEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(TimestampTypeEntry timestampTypeEntry) {
		getPersistence().cacheResult(timestampTypeEntry);
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
	public static void clearCache(TimestampTypeEntry timestampTypeEntry) {
		getPersistence().clearCache(timestampTypeEntry);
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
	public static Map<Serializable, TimestampTypeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TimestampTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TimestampTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TimestampTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<TimestampTypeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static TimestampTypeEntry update(
		TimestampTypeEntry timestampTypeEntry) {

		return getPersistence().update(timestampTypeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static TimestampTypeEntry update(
		TimestampTypeEntry timestampTypeEntry, ServiceContext serviceContext) {

		return getPersistence().update(timestampTypeEntry, serviceContext);
	}

	/**
	 * Creates a new timestamp type entry with the primary key. Does not add the timestamp type entry to the database.
	 *
	 * @param timestampTypeEntryId the primary key for the new timestamp type entry
	 * @return the new timestamp type entry
	 */
	public static TimestampTypeEntry create(long timestampTypeEntryId) {
		return getPersistence().create(timestampTypeEntryId);
	}

	/**
	 * Removes the timestamp type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry that was removed
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	public static TimestampTypeEntry remove(long timestampTypeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchTimestampTypeEntryException {

		return getPersistence().remove(timestampTypeEntryId);
	}

	public static TimestampTypeEntry updateImpl(
		TimestampTypeEntry timestampTypeEntry) {

		return getPersistence().updateImpl(timestampTypeEntry);
	}

	/**
	 * Returns the timestamp type entry with the primary key or throws a <code>NoSuchTimestampTypeEntryException</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	public static TimestampTypeEntry findByPrimaryKey(long timestampTypeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchTimestampTypeEntryException {

		return getPersistence().findByPrimaryKey(timestampTypeEntryId);
	}

	/**
	 * Returns the timestamp type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry, or <code>null</code> if a timestamp type entry with the primary key could not be found
	 */
	public static TimestampTypeEntry fetchByPrimaryKey(
		long timestampTypeEntryId) {

		return getPersistence().fetchByPrimaryKey(timestampTypeEntryId);
	}

	public static TimestampTypeEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		TimestampTypeEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile TimestampTypeEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-845643037