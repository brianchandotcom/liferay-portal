/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the undefined default order entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.UndefinedDefaultOrderEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntryPersistence
 * @generated
 */
public class UndefinedDefaultOrderEntryUtil {

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
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		getPersistence().clearCache(undefinedDefaultOrderEntry);
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
	public static Map<Serializable, UndefinedDefaultOrderEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UndefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UndefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UndefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UndefinedDefaultOrderEntry update(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return getPersistence().update(undefinedDefaultOrderEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UndefinedDefaultOrderEntry update(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			undefinedDefaultOrderEntry, serviceContext);
	}

	/**
	 * Returns the undefined default order entry where name = &#63; or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry findByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().findByName(name);
	}

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry fetchByName(
		String name, boolean useFinderCache) {

		return getPersistence().fetchByName(name, useFinderCache);
	}

	/**
	 * Removes the undefined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the undefined default order entry that was removed
	 */
	public static UndefinedDefaultOrderEntry removeByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Returns all the undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name) {

		return getPersistence().findByName_Collection(name);
	}

	/**
	 * Returns a range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of matching undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end) {

		return getPersistence().findByName_Collection(name, start, end);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findByName_Collection(
			name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByName_Collection(
			name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_First(
			name, orderByComparator);
	}

	/**
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().fetchByName_Collection_First(
			name, orderByComparator);
	}

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry findByName_Collection_Last(
			String name,
			OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	public static UndefinedDefaultOrderEntry fetchByName_Collection_Last(
		String name,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().fetchByName_Collection_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the undefined default order entries before and after the current undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the current undefined default order entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public static UndefinedDefaultOrderEntry[]
			findByName_Collection_PrevAndNext(
				long undefinedDefaultOrderEntryId, String name,
				OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_PrevAndNext(
			undefinedDefaultOrderEntryId, name, orderByComparator);
	}

	/**
	 * Removes all the undefined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public static void removeByName_Collection(String name) {
		getPersistence().removeByName_Collection(name);
	}

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	public static int countByName_Collection(String name) {
		return getPersistence().countByName_Collection(name);
	}

	/**
	 * Caches the undefined default order entry in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 */
	public static void cacheResult(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		getPersistence().cacheResult(undefinedDefaultOrderEntry);
	}

	/**
	 * Caches the undefined default order entries in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntries the undefined default order entries
	 */
	public static void cacheResult(
		List<UndefinedDefaultOrderEntry> undefinedDefaultOrderEntries) {

		getPersistence().cacheResult(undefinedDefaultOrderEntries);
	}

	/**
	 * Creates a new undefined default order entry with the primary key. Does not add the undefined default order entry to the database.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key for the new undefined default order entry
	 * @return the new undefined default order entry
	 */
	public static UndefinedDefaultOrderEntry create(
		long undefinedDefaultOrderEntryId) {

		return getPersistence().create(undefinedDefaultOrderEntryId);
	}

	/**
	 * Removes the undefined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry that was removed
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public static UndefinedDefaultOrderEntry remove(
			long undefinedDefaultOrderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().remove(undefinedDefaultOrderEntryId);
	}

	public static UndefinedDefaultOrderEntry updateImpl(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return getPersistence().updateImpl(undefinedDefaultOrderEntry);
	}

	/**
	 * Returns the undefined default order entry with the primary key or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	public static UndefinedDefaultOrderEntry findByPrimaryKey(
			long undefinedDefaultOrderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchUndefinedDefaultOrderEntryException {

		return getPersistence().findByPrimaryKey(undefinedDefaultOrderEntryId);
	}

	/**
	 * Returns the undefined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry, or <code>null</code> if a undefined default order entry with the primary key could not be found
	 */
	public static UndefinedDefaultOrderEntry fetchByPrimaryKey(
		long undefinedDefaultOrderEntryId) {

		return getPersistence().fetchByPrimaryKey(undefinedDefaultOrderEntryId);
	}

	/**
	 * Returns all the undefined default order entries.
	 *
	 * @return the undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of undefined default order entries
	 */
	public static List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the undefined default order entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of undefined default order entries.
	 *
	 * @return the number of undefined default order entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UndefinedDefaultOrderEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		UndefinedDefaultOrderEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile UndefinedDefaultOrderEntryPersistence _persistence;

}