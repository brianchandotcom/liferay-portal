/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the auto escape entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.AutoEscapeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntryPersistence
 * @generated
 */
public class AutoEscapeEntryUtil {

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
	public static void clearCache(AutoEscapeEntry autoEscapeEntry) {
		getPersistence().clearCache(autoEscapeEntry);
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
	public static Map<Serializable, AutoEscapeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AutoEscapeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AutoEscapeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AutoEscapeEntry update(AutoEscapeEntry autoEscapeEntry) {
		return getPersistence().update(autoEscapeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AutoEscapeEntry update(
		AutoEscapeEntry autoEscapeEntry, ServiceContext serviceContext) {

		return getPersistence().update(autoEscapeEntry, serviceContext);
	}

	/**
	 * Caches the auto escape entry in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntry the auto escape entry
	 */
	public static void cacheResult(AutoEscapeEntry autoEscapeEntry) {
		getPersistence().cacheResult(autoEscapeEntry);
	}

	/**
	 * Caches the auto escape entries in the entity cache if it is enabled.
	 *
	 * @param autoEscapeEntries the auto escape entries
	 */
	public static void cacheResult(List<AutoEscapeEntry> autoEscapeEntries) {
		getPersistence().cacheResult(autoEscapeEntries);
	}

	/**
	 * Creates a new auto escape entry with the primary key. Does not add the auto escape entry to the database.
	 *
	 * @param autoEscapeEntryId the primary key for the new auto escape entry
	 * @return the new auto escape entry
	 */
	public static AutoEscapeEntry create(long autoEscapeEntryId) {
		return getPersistence().create(autoEscapeEntryId);
	}

	/**
	 * Removes the auto escape entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry that was removed
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry remove(long autoEscapeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchAutoEscapeEntryException {

		return getPersistence().remove(autoEscapeEntryId);
	}

	public static AutoEscapeEntry updateImpl(AutoEscapeEntry autoEscapeEntry) {
		return getPersistence().updateImpl(autoEscapeEntry);
	}

	/**
	 * Returns the auto escape entry with the primary key or throws a <code>NoSuchAutoEscapeEntryException</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry
	 * @throws NoSuchAutoEscapeEntryException if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry findByPrimaryKey(long autoEscapeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchAutoEscapeEntryException {

		return getPersistence().findByPrimaryKey(autoEscapeEntryId);
	}

	/**
	 * Returns the auto escape entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param autoEscapeEntryId the primary key of the auto escape entry
	 * @return the auto escape entry, or <code>null</code> if a auto escape entry with the primary key could not be found
	 */
	public static AutoEscapeEntry fetchByPrimaryKey(long autoEscapeEntryId) {
		return getPersistence().fetchByPrimaryKey(autoEscapeEntryId);
	}

	public static AutoEscapeEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AutoEscapeEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AutoEscapeEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1889852466