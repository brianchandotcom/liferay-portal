/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.revert.schema.version.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.revert.schema.version.model.RSVEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the rsv entry service. This utility wraps <code>com.liferay.revert.schema.version.service.persistence.impl.RSVEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntryPersistence
 * @generated
 */
public class RSVEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<RSVEntry> rsvEntries) {
		getPersistence().cacheResult(rsvEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(RSVEntry rsvEntry) {
		getPersistence().cacheResult(rsvEntry);
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
	public static void clearCache(RSVEntry rsvEntry) {
		getPersistence().clearCache(rsvEntry);
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
	public static Map<Serializable, RSVEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RSVEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RSVEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RSVEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RSVEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RSVEntry update(RSVEntry rsvEntry) {
		return getPersistence().update(rsvEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RSVEntry update(
		RSVEntry rsvEntry, ServiceContext serviceContext) {

		return getPersistence().update(rsvEntry, serviceContext);
	}

	/**
	 * Creates a new rsv entry with the primary key. Does not add the rsv entry to the database.
	 *
	 * @param rsvEntryId the primary key for the new rsv entry
	 * @return the new rsv entry
	 */
	public static RSVEntry create(long rsvEntryId) {
		return getPersistence().create(rsvEntryId);
	}

	/**
	 * Removes the rsv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry that was removed
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	public static RSVEntry remove(long rsvEntryId)
		throws com.liferay.revert.schema.version.exception.
			NoSuchEntryException {

		return getPersistence().remove(rsvEntryId);
	}

	public static RSVEntry updateImpl(RSVEntry rsvEntry) {
		return getPersistence().updateImpl(rsvEntry);
	}

	/**
	 * Returns the rsv entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry
	 * @throws NoSuchEntryException if a rsv entry with the primary key could not be found
	 */
	public static RSVEntry findByPrimaryKey(long rsvEntryId)
		throws com.liferay.revert.schema.version.exception.
			NoSuchEntryException {

		return getPersistence().findByPrimaryKey(rsvEntryId);
	}

	/**
	 * Returns the rsv entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry, or <code>null</code> if a rsv entry with the primary key could not be found
	 */
	public static RSVEntry fetchByPrimaryKey(long rsvEntryId) {
		return getPersistence().fetchByPrimaryKey(rsvEntryId);
	}

	public static RSVEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(RSVEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile RSVEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1516114485