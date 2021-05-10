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

import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
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
 * The persistence utility for the frontend view state entry service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FrontendViewStateEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateEntryPersistence
 * @generated
 */
public class FrontendViewStateEntryUtil {

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
		FrontendViewStateEntry frontendViewStateEntry) {

		getPersistence().clearCache(frontendViewStateEntry);
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
	public static Map<Serializable, FrontendViewStateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FrontendViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FrontendViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FrontendViewStateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FrontendViewStateEntry update(
		FrontendViewStateEntry frontendViewStateEntry) {

		return getPersistence().update(frontendViewStateEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FrontendViewStateEntry update(
		FrontendViewStateEntry frontendViewStateEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(frontendViewStateEntry, serviceContext);
	}

	/**
	 * Returns all the frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry findByUuid_First(
			String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry findByUuid_Last(
			String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry[] findByUuid_PrevAndNext(
			long frontendViewStateEntryId, String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			frontendViewStateEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the frontend view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching frontend view state entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry[] findByUuid_C_PrevAndNext(
			long frontendViewStateEntryId, String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			frontendViewStateEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the frontend view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching frontend view state entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the frontend view state entry in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 */
	public static void cacheResult(
		FrontendViewStateEntry frontendViewStateEntry) {

		getPersistence().cacheResult(frontendViewStateEntry);
	}

	/**
	 * Caches the frontend view state entries in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntries the frontend view state entries
	 */
	public static void cacheResult(
		List<FrontendViewStateEntry> frontendViewStateEntries) {

		getPersistence().cacheResult(frontendViewStateEntries);
	}

	/**
	 * Creates a new frontend view state entry with the primary key. Does not add the frontend view state entry to the database.
	 *
	 * @param frontendViewStateEntryId the primary key for the new frontend view state entry
	 * @return the new frontend view state entry
	 */
	public static FrontendViewStateEntry create(long frontendViewStateEntryId) {
		return getPersistence().create(frontendViewStateEntryId);
	}

	/**
	 * Removes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry remove(long frontendViewStateEntryId)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().remove(frontendViewStateEntryId);
	}

	public static FrontendViewStateEntry updateImpl(
		FrontendViewStateEntry frontendViewStateEntry) {

		return getPersistence().updateImpl(frontendViewStateEntry);
	}

	/**
	 * Returns the frontend view state entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry findByPrimaryKey(
			long frontendViewStateEntryId)
		throws com.liferay.frontend.view.state.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry, or <code>null</code> if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry fetchByPrimaryKey(
		long frontendViewStateEntryId) {

		return getPersistence().fetchByPrimaryKey(frontendViewStateEntryId);
	}

	/**
	 * Returns all the frontend view state entries.
	 *
	 * @return the frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of frontend view state entries
	 */
	public static List<FrontendViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the frontend view state entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of frontend view state entries.
	 *
	 * @return the number of frontend view state entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FrontendViewStateEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FrontendViewStateEntryPersistence, FrontendViewStateEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FrontendViewStateEntryPersistence.class);

		ServiceTracker
			<FrontendViewStateEntryPersistence,
			 FrontendViewStateEntryPersistence> serviceTracker =
				new ServiceTracker
					<FrontendViewStateEntryPersistence,
					 FrontendViewStateEntryPersistence>(
						 bundle.getBundleContext(),
						 FrontendViewStateEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}