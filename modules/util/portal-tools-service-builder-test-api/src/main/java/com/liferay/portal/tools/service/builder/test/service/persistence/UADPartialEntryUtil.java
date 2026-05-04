/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.UADPartialEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the uad partial entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.UADPartialEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntryPersistence
 * @generated
 */
public class UADPartialEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<UADPartialEntry> uadPartialEntries) {
		getPersistence().cacheResult(uadPartialEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(UADPartialEntry uadPartialEntry) {
		getPersistence().cacheResult(uadPartialEntry);
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
	public static void clearCache(UADPartialEntry uadPartialEntry) {
		getPersistence().clearCache(uadPartialEntry);
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
	public static Map<Serializable, UADPartialEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UADPartialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UADPartialEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UADPartialEntry update(UADPartialEntry uadPartialEntry) {
		return getPersistence().update(uadPartialEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UADPartialEntry update(
		UADPartialEntry uadPartialEntry, ServiceContext serviceContext) {

		return getPersistence().update(uadPartialEntry, serviceContext);
	}

	/**
	 * Creates a new uad partial entry with the primary key. Does not add the uad partial entry to the database.
	 *
	 * @param uadPartialEntryId the primary key for the new uad partial entry
	 * @return the new uad partial entry
	 */
	public static UADPartialEntry create(long uadPartialEntryId) {
		return getPersistence().create(uadPartialEntryId);
	}

	/**
	 * Removes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry that was removed
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	public static UADPartialEntry remove(long uadPartialEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUADPartialEntryException {

		return getPersistence().remove(uadPartialEntryId);
	}

	public static UADPartialEntry updateImpl(UADPartialEntry uadPartialEntry) {
		return getPersistence().updateImpl(uadPartialEntry);
	}

	/**
	 * Returns the uad partial entry with the primary key or throws a <code>NoSuchUADPartialEntryException</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry
	 * @throws NoSuchUADPartialEntryException if a uad partial entry with the primary key could not be found
	 */
	public static UADPartialEntry findByPrimaryKey(long uadPartialEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUADPartialEntryException {

		return getPersistence().findByPrimaryKey(uadPartialEntryId);
	}

	/**
	 * Returns the uad partial entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry, or <code>null</code> if a uad partial entry with the primary key could not be found
	 */
	public static UADPartialEntry fetchByPrimaryKey(long uadPartialEntryId) {
		return getPersistence().fetchByPrimaryKey(uadPartialEntryId);
	}

	public static UADPartialEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(UADPartialEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile UADPartialEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1051122448