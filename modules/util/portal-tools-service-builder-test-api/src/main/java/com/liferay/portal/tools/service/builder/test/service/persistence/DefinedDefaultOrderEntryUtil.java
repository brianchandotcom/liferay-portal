/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the defined default order entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DefinedDefaultOrderEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntryPersistence
 * @generated
 */
public class DefinedDefaultOrderEntryUtil {

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
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		getPersistence().clearCache(definedDefaultOrderEntry);
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
	public static Map<Serializable, DefinedDefaultOrderEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DefinedDefaultOrderEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DefinedDefaultOrderEntry update(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return getPersistence().update(definedDefaultOrderEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DefinedDefaultOrderEntry update(
		DefinedDefaultOrderEntry definedDefaultOrderEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			definedDefaultOrderEntry, serviceContext);
	}

	/**
	 * Returns the defined default order entry where name = &#63; or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry findByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().findByName(name);
	}

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry fetchByName(
		String name, boolean useFinderCache) {

		return getPersistence().fetchByName(name, useFinderCache);
	}

	/**
	 * Removes the defined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the defined default order entry that was removed
	 */
	public static DefinedDefaultOrderEntry removeByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Returns all the defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findByName_Collection(
		String name) {

		return getPersistence().findByName_Collection(name);
	}

	/**
	 * Returns a range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of matching defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end) {

		return getPersistence().findByName_Collection(name, start, end);
	}

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findByName_Collection(
			name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByName_Collection(
			name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_First(
			name, orderByComparator);
	}

	/**
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().fetchByName_Collection_First(
			name, orderByComparator);
	}

	/**
	 * Returns the last defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry findByName_Collection_Last(
			String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the last defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public static DefinedDefaultOrderEntry fetchByName_Collection_Last(
		String name,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().fetchByName_Collection_Last(
			name, orderByComparator);
	}

	/**
	 * Returns the defined default order entries before and after the current defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the current defined default order entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry[] findByName_Collection_PrevAndNext(
			long definedDefaultOrderEntryId, String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().findByName_Collection_PrevAndNext(
			definedDefaultOrderEntryId, name, orderByComparator);
	}

	/**
	 * Removes all the defined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public static void removeByName_Collection(String name) {
		getPersistence().removeByName_Collection(name);
	}

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	public static int countByName_Collection(String name) {
		return getPersistence().countByName_Collection(name);
	}

	/**
	 * Caches the defined default order entry in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 */
	public static void cacheResult(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		getPersistence().cacheResult(definedDefaultOrderEntry);
	}

	/**
	 * Caches the defined default order entries in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntries the defined default order entries
	 */
	public static void cacheResult(
		List<DefinedDefaultOrderEntry> definedDefaultOrderEntries) {

		getPersistence().cacheResult(definedDefaultOrderEntries);
	}

	/**
	 * Creates a new defined default order entry with the primary key. Does not add the defined default order entry to the database.
	 *
	 * @param definedDefaultOrderEntryId the primary key for the new defined default order entry
	 * @return the new defined default order entry
	 */
	public static DefinedDefaultOrderEntry create(
		long definedDefaultOrderEntryId) {

		return getPersistence().create(definedDefaultOrderEntryId);
	}

	/**
	 * Removes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry remove(
			long definedDefaultOrderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().remove(definedDefaultOrderEntryId);
	}

	public static DefinedDefaultOrderEntry updateImpl(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return getPersistence().updateImpl(definedDefaultOrderEntry);
	}

	/**
	 * Returns the defined default order entry with the primary key or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry findByPrimaryKey(
			long definedDefaultOrderEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefinedDefaultOrderEntryException {

		return getPersistence().findByPrimaryKey(definedDefaultOrderEntryId);
	}

	/**
	 * Returns the defined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry, or <code>null</code> if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry fetchByPrimaryKey(
		long definedDefaultOrderEntryId) {

		return getPersistence().fetchByPrimaryKey(definedDefaultOrderEntryId);
	}

	/**
	 * Returns all the defined default order entries.
	 *
	 * @return the defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the defined default order entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of defined default order entries.
	 *
	 * @return the number of defined default order entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DefinedDefaultOrderEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		DefinedDefaultOrderEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile DefinedDefaultOrderEntryPersistence _persistence;

}