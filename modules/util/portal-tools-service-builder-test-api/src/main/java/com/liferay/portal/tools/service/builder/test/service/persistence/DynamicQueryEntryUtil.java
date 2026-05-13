/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the dynamic query entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DynamicQueryEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntryPersistence
 * @generated
 */
public class DynamicQueryEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<DynamicQueryEntry> dynamicQueryEntries) {

		getPersistence().cacheResult(dynamicQueryEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(DynamicQueryEntry dynamicQueryEntry) {
		getPersistence().cacheResult(dynamicQueryEntry);
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
	public static void clearCache(DynamicQueryEntry dynamicQueryEntry) {
		getPersistence().clearCache(dynamicQueryEntry);
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
	public static Map<Serializable, DynamicQueryEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DynamicQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DynamicQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DynamicQueryEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DynamicQueryEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DynamicQueryEntry update(
		DynamicQueryEntry dynamicQueryEntry) {

		return getPersistence().update(dynamicQueryEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DynamicQueryEntry update(
		DynamicQueryEntry dynamicQueryEntry, ServiceContext serviceContext) {

		return getPersistence().update(dynamicQueryEntry, serviceContext);
	}

	/**
	 * Creates a new dynamic query entry with the primary key. Does not add the dynamic query entry to the database.
	 *
	 * @param dynamicQueryEntryId the primary key for the new dynamic query entry
	 * @return the new dynamic query entry
	 */
	public static DynamicQueryEntry create(long dynamicQueryEntryId) {
		return getPersistence().create(dynamicQueryEntryId);
	}

	/**
	 * Removes the dynamic query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry that was removed
	 * @throws NoSuchDynamicQueryEntryException if a dynamic query entry with the primary key could not be found
	 */
	public static DynamicQueryEntry remove(long dynamicQueryEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDynamicQueryEntryException {

		return getPersistence().remove(dynamicQueryEntryId);
	}

	public static DynamicQueryEntry updateImpl(
		DynamicQueryEntry dynamicQueryEntry) {

		return getPersistence().updateImpl(dynamicQueryEntry);
	}

	/**
	 * Returns the dynamic query entry with the primary key or throws a <code>NoSuchDynamicQueryEntryException</code> if it could not be found.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry
	 * @throws NoSuchDynamicQueryEntryException if a dynamic query entry with the primary key could not be found
	 */
	public static DynamicQueryEntry findByPrimaryKey(long dynamicQueryEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDynamicQueryEntryException {

		return getPersistence().findByPrimaryKey(dynamicQueryEntryId);
	}

	/**
	 * Returns the dynamic query entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry, or <code>null</code> if a dynamic query entry with the primary key could not be found
	 */
	public static DynamicQueryEntry fetchByPrimaryKey(
		long dynamicQueryEntryId) {

		return getPersistence().fetchByPrimaryKey(dynamicQueryEntryId);
	}

	public static DynamicQueryEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		DynamicQueryEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile DynamicQueryEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1821432994