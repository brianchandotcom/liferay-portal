/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	 * Caches the dsl query entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntry the dsl query entry
	 */
	public static void cacheResult(DSLQueryEntry dslQueryEntry) {
		getPersistence().cacheResult(dslQueryEntry);
	}

	/**
	 * Caches the dsl query entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntries the dsl query entries
	 */
	public static void cacheResult(List<DSLQueryEntry> dslQueryEntries) {
		getPersistence().cacheResult(dslQueryEntries);
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

	/**
	 * Returns all the dsl query entries.
	 *
	 * @return the dsl query entries
	 */
	public static List<DSLQueryEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @return the range of dsl query entries
	 */
	public static List<DSLQueryEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dsl query entries
	 */
	public static List<DSLQueryEntry> findAll(
		int start, int end,
		OrderByComparator<DSLQueryEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dsl query entries
	 */
	public static List<DSLQueryEntry> findAll(
		int start, int end, OrderByComparator<DSLQueryEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dsl query entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dsl query entries.
	 *
	 * @return the number of dsl query entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DSLQueryEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DSLQueryEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DSLQueryEntryPersistence _persistence;

}