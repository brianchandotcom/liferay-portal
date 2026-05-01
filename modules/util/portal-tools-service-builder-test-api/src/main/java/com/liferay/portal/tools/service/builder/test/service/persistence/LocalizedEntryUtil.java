/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the localized entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LocalizedEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryPersistence
 * @generated
 */
public class LocalizedEntryUtil {

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
	public static void clearCache(LocalizedEntry localizedEntry) {
		getPersistence().clearCache(localizedEntry);
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
	public static Map<Serializable, LocalizedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LocalizedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LocalizedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LocalizedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LocalizedEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LocalizedEntry update(LocalizedEntry localizedEntry) {
		return getPersistence().update(localizedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LocalizedEntry update(
		LocalizedEntry localizedEntry, ServiceContext serviceContext) {

		return getPersistence().update(localizedEntry, serviceContext);
	}

	/**
	 * Caches the localized entry in the entity cache if it is enabled.
	 *
	 * @param localizedEntry the localized entry
	 */
	public static void cacheResult(LocalizedEntry localizedEntry) {
		getPersistence().cacheResult(localizedEntry);
	}

	/**
	 * Caches the localized entries in the entity cache if it is enabled.
	 *
	 * @param localizedEntries the localized entries
	 */
	public static void cacheResult(List<LocalizedEntry> localizedEntries) {
		getPersistence().cacheResult(localizedEntries);
	}

	/**
	 * Creates a new localized entry with the primary key. Does not add the localized entry to the database.
	 *
	 * @param localizedEntryId the primary key for the new localized entry
	 * @return the new localized entry
	 */
	public static LocalizedEntry create(long localizedEntryId) {
		return getPersistence().create(localizedEntryId);
	}

	/**
	 * Removes the localized entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry that was removed
	 * @throws NoSuchLocalizedEntryException if a localized entry with the primary key could not be found
	 */
	public static LocalizedEntry remove(long localizedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLocalizedEntryException {

		return getPersistence().remove(localizedEntryId);
	}

	public static LocalizedEntry updateImpl(LocalizedEntry localizedEntry) {
		return getPersistence().updateImpl(localizedEntry);
	}

	/**
	 * Returns the localized entry with the primary key or throws a <code>NoSuchLocalizedEntryException</code> if it could not be found.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry
	 * @throws NoSuchLocalizedEntryException if a localized entry with the primary key could not be found
	 */
	public static LocalizedEntry findByPrimaryKey(long localizedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLocalizedEntryException {

		return getPersistence().findByPrimaryKey(localizedEntryId);
	}

	/**
	 * Returns the localized entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry, or <code>null</code> if a localized entry with the primary key could not be found
	 */
	public static LocalizedEntry fetchByPrimaryKey(long localizedEntryId) {
		return getPersistence().fetchByPrimaryKey(localizedEntryId);
	}

	public static LocalizedEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(LocalizedEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile LocalizedEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1376789137