/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the dsl query status entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DSLQueryStatusEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryStatusEntryPersistence
 * @generated
 */
public class DSLQueryStatusEntryUtil {

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
	public static void clearCache(DSLQueryStatusEntry dslQueryStatusEntry) {
		getPersistence().clearCache(dslQueryStatusEntry);
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
	public static Map<Serializable, DSLQueryStatusEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DSLQueryStatusEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DSLQueryStatusEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DSLQueryStatusEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DSLQueryStatusEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DSLQueryStatusEntry update(
		DSLQueryStatusEntry dslQueryStatusEntry) {

		return getPersistence().update(dslQueryStatusEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DSLQueryStatusEntry update(
		DSLQueryStatusEntry dslQueryStatusEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(dslQueryStatusEntry, serviceContext);
	}

	/**
	 * Caches the dsl query status entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 */
	public static void cacheResult(DSLQueryStatusEntry dslQueryStatusEntry) {
		getPersistence().cacheResult(dslQueryStatusEntry);
	}

	/**
	 * Caches the dsl query status entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntries the dsl query status entries
	 */
	public static void cacheResult(
		List<DSLQueryStatusEntry> dslQueryStatusEntries) {

		getPersistence().cacheResult(dslQueryStatusEntries);
	}

	/**
	 * Creates a new dsl query status entry with the primary key. Does not add the dsl query status entry to the database.
	 *
	 * @param dslQueryStatusEntryId the primary key for the new dsl query status entry
	 * @return the new dsl query status entry
	 */
	public static DSLQueryStatusEntry create(long dslQueryStatusEntryId) {
		return getPersistence().create(dslQueryStatusEntryId);
	}

	/**
	 * Removes the dsl query status entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry that was removed
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	public static DSLQueryStatusEntry remove(long dslQueryStatusEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDSLQueryStatusEntryException {

		return getPersistence().remove(dslQueryStatusEntryId);
	}

	public static DSLQueryStatusEntry updateImpl(
		DSLQueryStatusEntry dslQueryStatusEntry) {

		return getPersistence().updateImpl(dslQueryStatusEntry);
	}

	/**
	 * Returns the dsl query status entry with the primary key or throws a <code>NoSuchDSLQueryStatusEntryException</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	public static DSLQueryStatusEntry findByPrimaryKey(
			long dslQueryStatusEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDSLQueryStatusEntryException {

		return getPersistence().findByPrimaryKey(dslQueryStatusEntryId);
	}

	/**
	 * Returns the dsl query status entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry, or <code>null</code> if a dsl query status entry with the primary key could not be found
	 */
	public static DSLQueryStatusEntry fetchByPrimaryKey(
		long dslQueryStatusEntryId) {

		return getPersistence().fetchByPrimaryKey(dslQueryStatusEntryId);
	}

	public static DSLQueryStatusEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		DSLQueryStatusEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile DSLQueryStatusEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1354233513