/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat710.model.WhereClauseEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the where clause entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat710.service.persistence.impl.WhereClauseEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntryPersistence
 * @generated
 */
public class WhereClauseEntryUtil {

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
	public static void clearCache(WhereClauseEntry whereClauseEntry) {
		getPersistence().clearCache(whereClauseEntry);
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
	public static Map<Serializable, WhereClauseEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WhereClauseEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WhereClauseEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WhereClauseEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WhereClauseEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WhereClauseEntry update(WhereClauseEntry whereClauseEntry) {
		return getPersistence().update(whereClauseEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WhereClauseEntry update(
		WhereClauseEntry whereClauseEntry, ServiceContext serviceContext) {

		return getPersistence().update(whereClauseEntry, serviceContext);
	}

	/**
	 * Returns all the where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching where clause entries
	 */
	public static List<WhereClauseEntry> findByName_Nickname(String name) {
		return getPersistence().findByName_Nickname(name);
	}

	/**
	 * Returns a range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of matching where clause entries
	 */
	public static List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end) {

		return getPersistence().findByName_Nickname(name, start, end);
	}

	/**
	 * Returns an ordered range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching where clause entries
	 */
	public static List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		OrderByComparator<WhereClauseEntry> orderByComparator) {

		return getPersistence().findByName_Nickname(
			name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching where clause entries
	 */
	public static List<WhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		OrderByComparator<WhereClauseEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByName_Nickname(
			name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching where clause entry
	 * @throws NoSuchWhereClauseEntryException if a matching where clause entry could not be found
	 */
	public static WhereClauseEntry findByName_Nickname_First(
			String name, OrderByComparator<WhereClauseEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat710.
			exception.NoSuchWhereClauseEntryException {

		return getPersistence().findByName_Nickname_First(
			name, orderByComparator);
	}

	/**
	 * Returns the first where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching where clause entry, or <code>null</code> if a matching where clause entry could not be found
	 */
	public static WhereClauseEntry fetchByName_Nickname_First(
		String name, OrderByComparator<WhereClauseEntry> orderByComparator) {

		return getPersistence().fetchByName_Nickname_First(
			name, orderByComparator);
	}

	/**
	 * Returns the last where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching where clause entry
	 * @throws NoSuchWhereClauseEntryException if a matching where clause entry could not be found
	 */
	public static WhereClauseEntry findByName_Nickname_Last(
			String name, OrderByComparator<WhereClauseEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat710.
			exception.NoSuchWhereClauseEntryException {

		return getPersistence().findByName_Nickname_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the last where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching where clause entry, or <code>null</code> if a matching where clause entry could not be found
	 */
	public static WhereClauseEntry fetchByName_Nickname_Last(
		String name, OrderByComparator<WhereClauseEntry> orderByComparator) {

		return getPersistence().fetchByName_Nickname_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the where clause entries before and after the current where clause entry in the ordered set where name = &#63;.
	 *
	 * @param whereClauseEntryId the primary key of the current where clause entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next where clause entry
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry[] findByName_Nickname_PrevAndNext(
			long whereClauseEntryId, String name,
			OrderByComparator<WhereClauseEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat710.
			exception.NoSuchWhereClauseEntryException {

		return getPersistence().findByName_Nickname_PrevAndNext(
			whereClauseEntryId, name, orderByComparator);
	}

	/**
	 * Removes all the where clause entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public static void removeByName_Nickname(String name) {
		getPersistence().removeByName_Nickname(name);
	}

	/**
	 * Returns the number of where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching where clause entries
	 */
	public static int countByName_Nickname(String name) {
		return getPersistence().countByName_Nickname(name);
	}

	/**
	 * Caches the where clause entry in the entity cache if it is enabled.
	 *
	 * @param whereClauseEntry the where clause entry
	 */
	public static void cacheResult(WhereClauseEntry whereClauseEntry) {
		getPersistence().cacheResult(whereClauseEntry);
	}

	/**
	 * Caches the where clause entries in the entity cache if it is enabled.
	 *
	 * @param whereClauseEntries the where clause entries
	 */
	public static void cacheResult(List<WhereClauseEntry> whereClauseEntries) {
		getPersistence().cacheResult(whereClauseEntries);
	}

	/**
	 * Creates a new where clause entry with the primary key. Does not add the where clause entry to the database.
	 *
	 * @param whereClauseEntryId the primary key for the new where clause entry
	 * @return the new where clause entry
	 */
	public static WhereClauseEntry create(long whereClauseEntryId) {
		return getPersistence().create(whereClauseEntryId);
	}

	/**
	 * Removes the where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry that was removed
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry remove(long whereClauseEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat710.
			exception.NoSuchWhereClauseEntryException {

		return getPersistence().remove(whereClauseEntryId);
	}

	public static WhereClauseEntry updateImpl(
		WhereClauseEntry whereClauseEntry) {

		return getPersistence().updateImpl(whereClauseEntry);
	}

	/**
	 * Returns the where clause entry with the primary key or throws a <code>NoSuchWhereClauseEntryException</code> if it could not be found.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry
	 * @throws NoSuchWhereClauseEntryException if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry findByPrimaryKey(long whereClauseEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat710.
			exception.NoSuchWhereClauseEntryException {

		return getPersistence().findByPrimaryKey(whereClauseEntryId);
	}

	/**
	 * Returns the where clause entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry, or <code>null</code> if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry fetchByPrimaryKey(long whereClauseEntryId) {
		return getPersistence().fetchByPrimaryKey(whereClauseEntryId);
	}

	/**
	 * Returns all the where clause entries.
	 *
	 * @return the where clause entries
	 */
	public static List<WhereClauseEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of where clause entries
	 */
	public static List<WhereClauseEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of where clause entries
	 */
	public static List<WhereClauseEntry> findAll(
		int start, int end,
		OrderByComparator<WhereClauseEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of where clause entries
	 */
	public static List<WhereClauseEntry> findAll(
		int start, int end,
		OrderByComparator<WhereClauseEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the where clause entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of where clause entries.
	 *
	 * @return the number of where clause entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WhereClauseEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(WhereClauseEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile WhereClauseEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-779985741