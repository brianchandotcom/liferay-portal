/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ReassociateEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the reassociate entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ReassociateEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReassociateEntryPersistence
 * @generated
 */
public class ReassociateEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(List<ReassociateEntry> reassociateEntries) {
		getPersistence().cacheResult(reassociateEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(ReassociateEntry reassociateEntry) {
		getPersistence().cacheResult(reassociateEntry);
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
	public static void clearCache(ReassociateEntry reassociateEntry) {
		getPersistence().clearCache(reassociateEntry);
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
	public static Map<Serializable, ReassociateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ReassociateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ReassociateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ReassociateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ReassociateEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ReassociateEntry update(ReassociateEntry reassociateEntry) {
		return getPersistence().update(reassociateEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ReassociateEntry update(
		ReassociateEntry reassociateEntry, ServiceContext serviceContext) {

		return getPersistence().update(reassociateEntry, serviceContext);
	}

	/**
	 * Creates a new reassociate entry with the primary key. Does not add the reassociate entry to the database.
	 *
	 * @param reassociateEntryId the primary key for the new reassociate entry
	 * @return the new reassociate entry
	 */
	public static ReassociateEntry create(long reassociateEntryId) {
		return getPersistence().create(reassociateEntryId);
	}

	/**
	 * Removes the reassociate entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry that was removed
	 * @throws NoSuchReassociateEntryException if a reassociate entry with the primary key could not be found
	 */
	public static ReassociateEntry remove(long reassociateEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchReassociateEntryException {

		return getPersistence().remove(reassociateEntryId);
	}

	public static ReassociateEntry updateImpl(
		ReassociateEntry reassociateEntry) {

		return getPersistence().updateImpl(reassociateEntry);
	}

	/**
	 * Returns the reassociate entry with the primary key or throws a <code>NoSuchReassociateEntryException</code> if it could not be found.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry
	 * @throws NoSuchReassociateEntryException if a reassociate entry with the primary key could not be found
	 */
	public static ReassociateEntry findByPrimaryKey(long reassociateEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchReassociateEntryException {

		return getPersistence().findByPrimaryKey(reassociateEntryId);
	}

	/**
	 * Returns the reassociate entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param reassociateEntryId the primary key of the reassociate entry
	 * @return the reassociate entry, or <code>null</code> if a reassociate entry with the primary key could not be found
	 */
	public static ReassociateEntry fetchByPrimaryKey(long reassociateEntryId) {
		return getPersistence().fetchByPrimaryKey(reassociateEntryId);
	}

	public static ReassociateEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ReassociateEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ReassociateEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:504402812