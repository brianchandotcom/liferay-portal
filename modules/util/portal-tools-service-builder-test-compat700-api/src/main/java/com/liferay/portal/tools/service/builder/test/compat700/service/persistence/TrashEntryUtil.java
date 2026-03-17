/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat700.model.TrashEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the trash entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat700.service.persistence.impl.TrashEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryPersistence
 * @generated
 */
public class TrashEntryUtil {

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
	public static void clearCache(TrashEntry trashEntry) {
		getPersistence().clearCache(trashEntry);
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
	public static Map<Serializable, TrashEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TrashEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TrashEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TrashEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<TrashEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static TrashEntry update(TrashEntry trashEntry) {
		return getPersistence().update(trashEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static TrashEntry update(
		TrashEntry trashEntry, ServiceContext serviceContext) {

		return getPersistence().update(trashEntry, serviceContext);
	}

	/**
	 * Caches the trash entry in the entity cache if it is enabled.
	 *
	 * @param trashEntry the trash entry
	 */
	public static void cacheResult(TrashEntry trashEntry) {
		getPersistence().cacheResult(trashEntry);
	}

	/**
	 * Caches the trash entries in the entity cache if it is enabled.
	 *
	 * @param trashEntries the trash entries
	 */
	public static void cacheResult(List<TrashEntry> trashEntries) {
		getPersistence().cacheResult(trashEntries);
	}

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param trashEntryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	public static TrashEntry create(long trashEntryId) {
		return getPersistence().create(trashEntryId);
	}

	/**
	 * Removes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public static TrashEntry remove(long trashEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchTrashEntryException {

		return getPersistence().remove(trashEntryId);
	}

	public static TrashEntry updateImpl(TrashEntry trashEntry) {
		return getPersistence().updateImpl(trashEntry);
	}

	/**
	 * Returns the trash entry with the primary key or throws a <code>NoSuchTrashEntryException</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws NoSuchTrashEntryException if a trash entry with the primary key could not be found
	 */
	public static TrashEntry findByPrimaryKey(long trashEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchTrashEntryException {

		return getPersistence().findByPrimaryKey(trashEntryId);
	}

	/**
	 * Returns the trash entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry, or <code>null</code> if a trash entry with the primary key could not be found
	 */
	public static TrashEntry fetchByPrimaryKey(long trashEntryId) {
		return getPersistence().fetchByPrimaryKey(trashEntryId);
	}

	/**
	 * Returns all the trash entries.
	 *
	 * @return the trash entries
	 */
	public static List<TrashEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 */
	public static List<TrashEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of trash entries
	 */
	public static List<TrashEntry> findAll(
		int start, int end, OrderByComparator<TrashEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of trash entries
	 */
	public static List<TrashEntry> findAll(
		int start, int end, OrderByComparator<TrashEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the trash entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static TrashEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(TrashEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile TrashEntryPersistence _persistence;

}
// SB-Hash:1134540776