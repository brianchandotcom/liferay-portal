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

package com.liferay.dataset.view.service.persistence;

import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the dataset view state entry service. This utility wraps <code>com.liferay.dataset.view.service.persistence.impl.DatasetViewStateEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntryPersistence
 * @generated
 */
public class DatasetViewStateEntryUtil {

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
	public static void clearCache(DatasetViewStateEntry datasetViewStateEntry) {
		getPersistence().clearCache(datasetViewStateEntry);
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
	public static Map<Serializable, DatasetViewStateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DatasetViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DatasetViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DatasetViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DatasetViewStateEntry update(
		DatasetViewStateEntry datasetViewStateEntry) {

		return getPersistence().update(datasetViewStateEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DatasetViewStateEntry update(
		DatasetViewStateEntry datasetViewStateEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(datasetViewStateEntry, serviceContext);
	}

	/**
	 * Returns all the dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry findByUuid_First(
			String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry findByUuid_Last(
			String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry[] findByUuid_PrevAndNext(
			long datasetViewStateEntryId, String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			datasetViewStateEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the dataset view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset view state entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	public static DatasetViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry[] findByUuid_C_PrevAndNext(
			long datasetViewStateEntryId, String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			datasetViewStateEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the dataset view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset view state entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the dataset view state entry in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 */
	public static void cacheResult(
		DatasetViewStateEntry datasetViewStateEntry) {

		getPersistence().cacheResult(datasetViewStateEntry);
	}

	/**
	 * Caches the dataset view state entries in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntries the dataset view state entries
	 */
	public static void cacheResult(
		List<DatasetViewStateEntry> datasetViewStateEntries) {

		getPersistence().cacheResult(datasetViewStateEntries);
	}

	/**
	 * Creates a new dataset view state entry with the primary key. Does not add the dataset view state entry to the database.
	 *
	 * @param datasetViewStateEntryId the primary key for the new dataset view state entry
	 * @return the new dataset view state entry
	 */
	public static DatasetViewStateEntry create(long datasetViewStateEntryId) {
		return getPersistence().create(datasetViewStateEntryId);
	}

	/**
	 * Removes the dataset view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry that was removed
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry remove(long datasetViewStateEntryId)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().remove(datasetViewStateEntryId);
	}

	public static DatasetViewStateEntry updateImpl(
		DatasetViewStateEntry datasetViewStateEntry) {

		return getPersistence().updateImpl(datasetViewStateEntry);
	}

	/**
	 * Returns the dataset view state entry with the primary key or throws a <code>NoSuchStateEntryException</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry findByPrimaryKey(
			long datasetViewStateEntryId)
		throws com.liferay.dataset.view.exception.NoSuchStateEntryException {

		return getPersistence().findByPrimaryKey(datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry, or <code>null</code> if a dataset view state entry with the primary key could not be found
	 */
	public static DatasetViewStateEntry fetchByPrimaryKey(
		long datasetViewStateEntryId) {

		return getPersistence().fetchByPrimaryKey(datasetViewStateEntryId);
	}

	/**
	 * Returns all the dataset view state entries.
	 *
	 * @return the dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dataset view state entries
	 */
	public static List<DatasetViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dataset view state entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dataset view state entries.
	 *
	 * @return the number of dataset view state entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DatasetViewStateEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DatasetViewStateEntryPersistence, DatasetViewStateEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DatasetViewStateEntryPersistence.class);

		ServiceTracker
			<DatasetViewStateEntryPersistence, DatasetViewStateEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<DatasetViewStateEntryPersistence,
						 DatasetViewStateEntryPersistence>(
							 bundle.getBundleContext(),
							 DatasetViewStateEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}