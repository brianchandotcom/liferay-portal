/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the rename finder column entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.RenameFinderColumnEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RenameFinderColumnEntryPersistence
 * @generated
 */
public class RenameFinderColumnEntryUtil {

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
	public static void clearCache(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		getPersistence().clearCache(renameFinderColumnEntry);
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
	public static Map<Serializable, RenameFinderColumnEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RenameFinderColumnEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RenameFinderColumnEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RenameFinderColumnEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RenameFinderColumnEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RenameFinderColumnEntry update(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		return getPersistence().update(renameFinderColumnEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RenameFinderColumnEntry update(
		RenameFinderColumnEntry renameFinderColumnEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(renameFinderColumnEntry, serviceContext);
	}

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a matching rename finder column entry could not be found
	 */
	public static RenameFinderColumnEntry findByColumnToRename(
			String columnToRename)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRenameFinderColumnEntryException {

		return getPersistence().findByColumnToRename(columnToRename);
	}

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	public static RenameFinderColumnEntry fetchByColumnToRename(
		String columnToRename) {

		return getPersistence().fetchByColumnToRename(columnToRename);
	}

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	public static RenameFinderColumnEntry fetchByColumnToRename(
		String columnToRename, boolean useFinderCache) {

		return getPersistence().fetchByColumnToRename(
			columnToRename, useFinderCache);
	}

	/**
	 * Removes the rename finder column entry where columnToRename = &#63; from the database.
	 *
	 * @param columnToRename the column to rename
	 * @return the rename finder column entry that was removed
	 */
	public static RenameFinderColumnEntry removeByColumnToRename(
			String columnToRename)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRenameFinderColumnEntryException {

		return getPersistence().removeByColumnToRename(columnToRename);
	}

	/**
	 * Returns the number of rename finder column entries where columnToRename = &#63;.
	 *
	 * @param columnToRename the column to rename
	 * @return the number of matching rename finder column entries
	 */
	public static int countByColumnToRename(String columnToRename) {
		return getPersistence().countByColumnToRename(columnToRename);
	}

	/**
	 * Caches the rename finder column entry in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 */
	public static void cacheResult(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		getPersistence().cacheResult(renameFinderColumnEntry);
	}

	/**
	 * Caches the rename finder column entries in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntries the rename finder column entries
	 */
	public static void cacheResult(
		List<RenameFinderColumnEntry> renameFinderColumnEntries) {

		getPersistence().cacheResult(renameFinderColumnEntries);
	}

	/**
	 * Creates a new rename finder column entry with the primary key. Does not add the rename finder column entry to the database.
	 *
	 * @param renameFinderColumnEntryId the primary key for the new rename finder column entry
	 * @return the new rename finder column entry
	 */
	public static RenameFinderColumnEntry create(
		long renameFinderColumnEntryId) {

		return getPersistence().create(renameFinderColumnEntryId);
	}

	/**
	 * Removes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	public static RenameFinderColumnEntry remove(long renameFinderColumnEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRenameFinderColumnEntryException {

		return getPersistence().remove(renameFinderColumnEntryId);
	}

	public static RenameFinderColumnEntry updateImpl(
		RenameFinderColumnEntry renameFinderColumnEntry) {

		return getPersistence().updateImpl(renameFinderColumnEntry);
	}

	/**
	 * Returns the rename finder column entry with the primary key or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	public static RenameFinderColumnEntry findByPrimaryKey(
			long renameFinderColumnEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchRenameFinderColumnEntryException {

		return getPersistence().findByPrimaryKey(renameFinderColumnEntryId);
	}

	/**
	 * Returns the rename finder column entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry, or <code>null</code> if a rename finder column entry with the primary key could not be found
	 */
	public static RenameFinderColumnEntry fetchByPrimaryKey(
		long renameFinderColumnEntryId) {

		return getPersistence().fetchByPrimaryKey(renameFinderColumnEntryId);
	}

	/**
	 * Returns all the rename finder column entries.
	 *
	 * @return the rename finder column entries
	 */
	public static List<RenameFinderColumnEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @return the range of rename finder column entries
	 */
	public static List<RenameFinderColumnEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rename finder column entries
	 */
	public static List<RenameFinderColumnEntry> findAll(
		int start, int end,
		OrderByComparator<RenameFinderColumnEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rename finder column entries
	 */
	public static List<RenameFinderColumnEntry> findAll(
		int start, int end,
		OrderByComparator<RenameFinderColumnEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the rename finder column entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of rename finder column entries.
	 *
	 * @return the number of rename finder column entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RenameFinderColumnEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RenameFinderColumnEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RenameFinderColumnEntryPersistence _persistence;

}