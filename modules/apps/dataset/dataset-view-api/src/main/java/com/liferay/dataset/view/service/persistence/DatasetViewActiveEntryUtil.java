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

import com.liferay.dataset.view.model.DatasetViewActiveEntry;
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
 * The persistence utility for the dataset view active entry service. This utility wraps <code>com.liferay.dataset.view.service.persistence.impl.DatasetViewActiveEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewActiveEntryPersistence
 * @generated
 */
public class DatasetViewActiveEntryUtil {

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
		DatasetViewActiveEntry datasetViewActiveEntry) {

		getPersistence().clearCache(datasetViewActiveEntry);
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
	public static Map<Serializable, DatasetViewActiveEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DatasetViewActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DatasetViewActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DatasetViewActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DatasetViewActiveEntry update(
		DatasetViewActiveEntry datasetViewActiveEntry) {

		return getPersistence().update(datasetViewActiveEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DatasetViewActiveEntry update(
		DatasetViewActiveEntry datasetViewActiveEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(datasetViewActiveEntry, serviceContext);
	}

	/**
	 * Returns all the dataset view active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the dataset view active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @return the range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dataset view active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view active entry
	 * @throws NoSuchActiveEntryException if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry findByUuid_First(
			String uuid,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first dataset view active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset view active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view active entry
	 * @throws NoSuchActiveEntryException if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry findByUuid_Last(
			String uuid,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset view active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the dataset view active entries before and after the current dataset view active entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetViewActiveEntryId the primary key of the current dataset view active entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view active entry
	 * @throws NoSuchActiveEntryException if a dataset view active entry with the primary key could not be found
	 */
	public static DatasetViewActiveEntry[] findByUuid_PrevAndNext(
			long datasetViewActiveEntryId, String uuid,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			datasetViewActiveEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the dataset view active entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of dataset view active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset view active entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the dataset view active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the dataset view active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @return the range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dataset view active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view active entry
	 * @throws NoSuchActiveEntryException if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first dataset view active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last dataset view active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view active entry
	 * @throws NoSuchActiveEntryException if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last dataset view active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the dataset view active entries before and after the current dataset view active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param datasetViewActiveEntryId the primary key of the current dataset view active entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view active entry
	 * @throws NoSuchActiveEntryException if a dataset view active entry with the primary key could not be found
	 */
	public static DatasetViewActiveEntry[] findByUuid_C_PrevAndNext(
			long datasetViewActiveEntryId, String uuid, long companyId,
			OrderByComparator<DatasetViewActiveEntry> orderByComparator)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			datasetViewActiveEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the dataset view active entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of dataset view active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset view active entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the dataset view active entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching dataset view active entry
	 * @throws NoSuchActiveEntryException if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry findByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Returns the dataset view active entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return getPersistence().fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Returns the dataset view active entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dataset view active entry, or <code>null</code> if a matching dataset view active entry could not be found
	 */
	public static DatasetViewActiveEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId,
		boolean useFinderCache) {

		return getPersistence().fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId, useFinderCache);
	}

	/**
	 * Removes the dataset view active entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the dataset view active entry that was removed
	 */
	public static DatasetViewActiveEntry removeByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().removeByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Returns the number of dataset view active entries where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching dataset view active entries
	 */
	public static int countByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return getPersistence().countByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Caches the dataset view active entry in the entity cache if it is enabled.
	 *
	 * @param datasetViewActiveEntry the dataset view active entry
	 */
	public static void cacheResult(
		DatasetViewActiveEntry datasetViewActiveEntry) {

		getPersistence().cacheResult(datasetViewActiveEntry);
	}

	/**
	 * Caches the dataset view active entries in the entity cache if it is enabled.
	 *
	 * @param datasetViewActiveEntries the dataset view active entries
	 */
	public static void cacheResult(
		List<DatasetViewActiveEntry> datasetViewActiveEntries) {

		getPersistence().cacheResult(datasetViewActiveEntries);
	}

	/**
	 * Creates a new dataset view active entry with the primary key. Does not add the dataset view active entry to the database.
	 *
	 * @param datasetViewActiveEntryId the primary key for the new dataset view active entry
	 * @return the new dataset view active entry
	 */
	public static DatasetViewActiveEntry create(long datasetViewActiveEntryId) {
		return getPersistence().create(datasetViewActiveEntryId);
	}

	/**
	 * Removes the dataset view active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetViewActiveEntryId the primary key of the dataset view active entry
	 * @return the dataset view active entry that was removed
	 * @throws NoSuchActiveEntryException if a dataset view active entry with the primary key could not be found
	 */
	public static DatasetViewActiveEntry remove(long datasetViewActiveEntryId)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().remove(datasetViewActiveEntryId);
	}

	public static DatasetViewActiveEntry updateImpl(
		DatasetViewActiveEntry datasetViewActiveEntry) {

		return getPersistence().updateImpl(datasetViewActiveEntry);
	}

	/**
	 * Returns the dataset view active entry with the primary key or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param datasetViewActiveEntryId the primary key of the dataset view active entry
	 * @return the dataset view active entry
	 * @throws NoSuchActiveEntryException if a dataset view active entry with the primary key could not be found
	 */
	public static DatasetViewActiveEntry findByPrimaryKey(
			long datasetViewActiveEntryId)
		throws com.liferay.dataset.view.exception.NoSuchActiveEntryException {

		return getPersistence().findByPrimaryKey(datasetViewActiveEntryId);
	}

	/**
	 * Returns the dataset view active entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetViewActiveEntryId the primary key of the dataset view active entry
	 * @return the dataset view active entry, or <code>null</code> if a dataset view active entry with the primary key could not be found
	 */
	public static DatasetViewActiveEntry fetchByPrimaryKey(
		long datasetViewActiveEntryId) {

		return getPersistence().fetchByPrimaryKey(datasetViewActiveEntryId);
	}

	/**
	 * Returns all the dataset view active entries.
	 *
	 * @return the dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dataset view active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @return the range of dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dataset view active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view active entries
	 * @param end the upper bound of the range of dataset view active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dataset view active entries
	 */
	public static List<DatasetViewActiveEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dataset view active entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dataset view active entries.
	 *
	 * @return the number of dataset view active entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DatasetViewActiveEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DatasetViewActiveEntryPersistence, DatasetViewActiveEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DatasetViewActiveEntryPersistence.class);

		ServiceTracker
			<DatasetViewActiveEntryPersistence,
			 DatasetViewActiveEntryPersistence> serviceTracker =
				new ServiceTracker
					<DatasetViewActiveEntryPersistence,
					 DatasetViewActiveEntryPersistence>(
						 bundle.getBundleContext(),
						 DatasetViewActiveEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}