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

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.model.FVSEntry;
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
 * The persistence utility for the fvs entry service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FVSEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSEntryPersistence
 * @generated
 */
public class FVSEntryUtil {

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
	public static void clearCache(FVSEntry fvsEntry) {
		getPersistence().clearCache(fvsEntry);
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
	public static Map<Serializable, FVSEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FVSEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FVSEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FVSEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FVSEntry update(FVSEntry fvsEntry) {
		return getPersistence().update(fvsEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FVSEntry update(
		FVSEntry fvsEntry, ServiceContext serviceContext) {

		return getPersistence().update(fvsEntry, serviceContext);
	}

	/**
	 * Returns all the fvs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs entries
	 */
	public static List<FVSEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public static FVSEntry findByUuid_First(
			String uuid, OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public static FVSEntry fetchByUuid_First(
		String uuid, OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public static FVSEntry findByUuid_Last(
			String uuid, OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public static FVSEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the fvs entries before and after the current fvs entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsEntryId the primary key of the current fvs entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public static FVSEntry[] findByUuid_PrevAndNext(
			long fvsEntryId, String uuid,
			OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			fvsEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the fvs entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of fvs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs entries
	 */
	public static List<FVSEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs entries
	 */
	public static List<FVSEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public static FVSEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public static FVSEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry
	 * @throws NoSuchEntryException if a matching fvs entry could not be found
	 */
	public static FVSEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	public static FVSEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the fvs entries before and after the current fvs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsEntryId the primary key of the current fvs entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public static FVSEntry[] findByUuid_C_PrevAndNext(
			long fvsEntryId, String uuid, long companyId,
			OrderByComparator<FVSEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			fvsEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the fvs entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of fvs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the fvs entry in the entity cache if it is enabled.
	 *
	 * @param fvsEntry the fvs entry
	 */
	public static void cacheResult(FVSEntry fvsEntry) {
		getPersistence().cacheResult(fvsEntry);
	}

	/**
	 * Caches the fvs entries in the entity cache if it is enabled.
	 *
	 * @param fvsEntries the fvs entries
	 */
	public static void cacheResult(List<FVSEntry> fvsEntries) {
		getPersistence().cacheResult(fvsEntries);
	}

	/**
	 * Creates a new fvs entry with the primary key. Does not add the fvs entry to the database.
	 *
	 * @param fvsEntryId the primary key for the new fvs entry
	 * @return the new fvs entry
	 */
	public static FVSEntry create(long fvsEntryId) {
		return getPersistence().create(fvsEntryId);
	}

	/**
	 * Removes the fvs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry that was removed
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public static FVSEntry remove(long fvsEntryId)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().remove(fvsEntryId);
	}

	public static FVSEntry updateImpl(FVSEntry fvsEntry) {
		return getPersistence().updateImpl(fvsEntry);
	}

	/**
	 * Returns the fvs entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry
	 * @throws NoSuchEntryException if a fvs entry with the primary key could not be found
	 */
	public static FVSEntry findByPrimaryKey(long fvsEntryId)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(fvsEntryId);
	}

	/**
	 * Returns the fvs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry, or <code>null</code> if a fvs entry with the primary key could not be found
	 */
	public static FVSEntry fetchByPrimaryKey(long fvsEntryId) {
		return getPersistence().fetchByPrimaryKey(fvsEntryId);
	}

	/**
	 * Returns all the fvs entries.
	 *
	 * @return the fvs entries
	 */
	public static List<FVSEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of fvs entries
	 */
	public static List<FVSEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs entries
	 */
	public static List<FVSEntry> findAll(
		int start, int end, OrderByComparator<FVSEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs entries
	 */
	public static List<FVSEntry> findAll(
		int start, int end, OrderByComparator<FVSEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fvs entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fvs entries.
	 *
	 * @return the number of fvs entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FVSEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FVSEntryPersistence, FVSEntryPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FVSEntryPersistence.class);

		ServiceTracker<FVSEntryPersistence, FVSEntryPersistence>
			serviceTracker =
				new ServiceTracker<FVSEntryPersistence, FVSEntryPersistence>(
					bundle.getBundleContext(), FVSEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}