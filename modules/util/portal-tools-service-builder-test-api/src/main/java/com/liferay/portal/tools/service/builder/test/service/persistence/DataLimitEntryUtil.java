/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DataLimitEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the data limit entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DataLimitEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DataLimitEntryPersistence
 * @generated
 */
public class DataLimitEntryUtil {

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
	public static void clearCache(DataLimitEntry dataLimitEntry) {
		getPersistence().clearCache(dataLimitEntry);
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
	public static Map<Serializable, DataLimitEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DataLimitEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DataLimitEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DataLimitEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DataLimitEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DataLimitEntry update(DataLimitEntry dataLimitEntry) {
		return getPersistence().update(dataLimitEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DataLimitEntry update(
		DataLimitEntry dataLimitEntry, ServiceContext serviceContext) {

		return getPersistence().update(dataLimitEntry, serviceContext);
	}

	/**
	 * Caches the data limit entry in the entity cache if it is enabled.
	 *
	 * @param dataLimitEntry the data limit entry
	 */
	public static void cacheResult(DataLimitEntry dataLimitEntry) {
		getPersistence().cacheResult(dataLimitEntry);
	}

	/**
	 * Caches the data limit entries in the entity cache if it is enabled.
	 *
	 * @param dataLimitEntries the data limit entries
	 */
	public static void cacheResult(List<DataLimitEntry> dataLimitEntries) {
		getPersistence().cacheResult(dataLimitEntries);
	}

	/**
	 * Creates a new data limit entry with the primary key. Does not add the data limit entry to the database.
	 *
	 * @param dataLimitEntryId the primary key for the new data limit entry
	 * @return the new data limit entry
	 */
	public static DataLimitEntry create(long dataLimitEntryId) {
		return getPersistence().create(dataLimitEntryId);
	}

	/**
	 * Removes the data limit entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry that was removed
	 * @throws NoSuchDataLimitEntryException if a data limit entry with the primary key could not be found
	 */
	public static DataLimitEntry remove(long dataLimitEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDataLimitEntryException {

		return getPersistence().remove(dataLimitEntryId);
	}

	public static DataLimitEntry updateImpl(DataLimitEntry dataLimitEntry) {
		return getPersistence().updateImpl(dataLimitEntry);
	}

	/**
	 * Returns the data limit entry with the primary key or throws a <code>NoSuchDataLimitEntryException</code> if it could not be found.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry
	 * @throws NoSuchDataLimitEntryException if a data limit entry with the primary key could not be found
	 */
	public static DataLimitEntry findByPrimaryKey(long dataLimitEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDataLimitEntryException {

		return getPersistence().findByPrimaryKey(dataLimitEntryId);
	}

	/**
	 * Returns the data limit entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataLimitEntryId the primary key of the data limit entry
	 * @return the data limit entry, or <code>null</code> if a data limit entry with the primary key could not be found
	 */
	public static DataLimitEntry fetchByPrimaryKey(long dataLimitEntryId) {
		return getPersistence().fetchByPrimaryKey(dataLimitEntryId);
	}

	/**
	 * Returns all the data limit entries.
	 *
	 * @return the data limit entries
	 */
	public static List<DataLimitEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @return the range of data limit entries
	 */
	public static List<DataLimitEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of data limit entries
	 */
	public static List<DataLimitEntry> findAll(
		int start, int end,
		OrderByComparator<DataLimitEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the data limit entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DataLimitEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of data limit entries
	 * @param end the upper bound of the range of data limit entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of data limit entries
	 */
	public static List<DataLimitEntry> findAll(
		int start, int end, OrderByComparator<DataLimitEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the data limit entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of data limit entries.
	 *
	 * @return the number of data limit entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DataLimitEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DataLimitEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DataLimitEntryPersistence _persistence;

}