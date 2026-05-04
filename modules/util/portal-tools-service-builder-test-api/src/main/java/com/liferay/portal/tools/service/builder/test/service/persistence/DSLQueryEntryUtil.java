/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the dsl query entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DSLQueryEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryEntryPersistence
 * @generated
 */
public class DSLQueryEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<DSLQueryEntry> dslQueryEntries) {
		getPersistence().cacheResult(dslQueryEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(DSLQueryEntry dslQueryEntry) {
		getPersistence().cacheResult(dslQueryEntry);
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
	public static void clearCache(DSLQueryEntry dslQueryEntry) {
		getPersistence().clearCache(dslQueryEntry);
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
	public static Map<Serializable, DSLQueryEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DSLQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DSLQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DSLQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DSLQueryEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DSLQueryEntry update(DSLQueryEntry dslQueryEntry) {
		return getPersistence().update(dslQueryEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DSLQueryEntry update(
		DSLQueryEntry dslQueryEntry, ServiceContext serviceContext) {

		return getPersistence().update(dslQueryEntry, serviceContext);
	}

	/**
	 * Creates a new dsl query entry with the primary key. Does not add the dsl query entry to the database.
	 *
	 * @param dslQueryEntryId the primary key for the new dsl query entry
	 * @return the new dsl query entry
	 */
	public static DSLQueryEntry create(long dslQueryEntryId) {
		return getPersistence().create(dslQueryEntryId);
	}

	/**
	 * Removes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	public static DSLQueryEntry remove(long dslQueryEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDSLQueryEntryException {

		return getPersistence().remove(dslQueryEntryId);
	}

	public static DSLQueryEntry updateImpl(DSLQueryEntry dslQueryEntry) {
		return getPersistence().updateImpl(dslQueryEntry);
	}

	/**
	 * Returns the dsl query entry with the primary key or throws a <code>NoSuchDSLQueryEntryException</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	public static DSLQueryEntry findByPrimaryKey(long dslQueryEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDSLQueryEntryException {

		return getPersistence().findByPrimaryKey(dslQueryEntryId);
	}

	/**
	 * Returns the dsl query entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry, or <code>null</code> if a dsl query entry with the primary key could not be found
	 */
	public static DSLQueryEntry fetchByPrimaryKey(long dslQueryEntryId) {
		return getPersistence().fetchByPrimaryKey(dslQueryEntryId);
	}

	public static DSLQueryEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DSLQueryEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DSLQueryEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1435575532