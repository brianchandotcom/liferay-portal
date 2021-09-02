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

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectLayoutBoxRow;
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
 * The persistence utility for the object layout box row service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectLayoutBoxRowPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRowPersistence
 * @generated
 */
public class ObjectLayoutBoxRowUtil {

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
	public static void clearCache(ObjectLayoutBoxRow objectLayoutBoxRow) {
		getPersistence().clearCache(objectLayoutBoxRow);
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
	public static Map<Serializable, ObjectLayoutBoxRow> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectLayoutBoxRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectLayoutBoxRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectLayoutBoxRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectLayoutBoxRow update(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return getPersistence().update(objectLayoutBoxRow);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectLayoutBoxRow update(
		ObjectLayoutBoxRow objectLayoutBoxRow, ServiceContext serviceContext) {

		return getPersistence().update(objectLayoutBoxRow, serviceContext);
	}

	/**
	 * Returns all the object layout box rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow findByUuid_First(
			String uuid,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object layout box rows before and after the current object layout box row in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxRowId the primary key of the current object layout box row
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow[] findByUuid_PrevAndNext(
			long objectLayoutBoxRowId, String uuid,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_PrevAndNext(
			objectLayoutBoxRowId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object layout box rows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object layout box rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout box rows
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box row, or <code>null</code> if a matching object layout box row could not be found
	 */
	public static ObjectLayoutBoxRow fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object layout box rows before and after the current object layout box row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the primary key of the current object layout box row
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxRowId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectLayoutBoxRowId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object layout box rows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object layout box rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout box rows
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the object layout box row in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxRow the object layout box row
	 */
	public static void cacheResult(ObjectLayoutBoxRow objectLayoutBoxRow) {
		getPersistence().cacheResult(objectLayoutBoxRow);
	}

	/**
	 * Caches the object layout box rows in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxRows the object layout box rows
	 */
	public static void cacheResult(
		List<ObjectLayoutBoxRow> objectLayoutBoxRows) {

		getPersistence().cacheResult(objectLayoutBoxRows);
	}

	/**
	 * Creates a new object layout box row with the primary key. Does not add the object layout box row to the database.
	 *
	 * @param objectLayoutBoxRowId the primary key for the new object layout box row
	 * @return the new object layout box row
	 */
	public static ObjectLayoutBoxRow create(long objectLayoutBoxRowId) {
		return getPersistence().create(objectLayoutBoxRowId);
	}

	/**
	 * Removes the object layout box row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row that was removed
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow remove(long objectLayoutBoxRowId)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().remove(objectLayoutBoxRowId);
	}

	public static ObjectLayoutBoxRow updateImpl(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return getPersistence().updateImpl(objectLayoutBoxRow);
	}

	/**
	 * Returns the object layout box row with the primary key or throws a <code>NoSuchObjectLayoutBoxRowException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row
	 * @throws NoSuchObjectLayoutBoxRowException if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow findByPrimaryKey(long objectLayoutBoxRowId)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxRowException {

		return getPersistence().findByPrimaryKey(objectLayoutBoxRowId);
	}

	/**
	 * Returns the object layout box row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxRowId the primary key of the object layout box row
	 * @return the object layout box row, or <code>null</code> if a object layout box row with the primary key could not be found
	 */
	public static ObjectLayoutBoxRow fetchByPrimaryKey(
		long objectLayoutBoxRowId) {

		return getPersistence().fetchByPrimaryKey(objectLayoutBoxRowId);
	}

	/**
	 * Returns all the object layout box rows.
	 *
	 * @return the object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @return the range of object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout box rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box rows
	 * @param end the upper bound of the range of object layout box rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout box rows
	 */
	public static List<ObjectLayoutBoxRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBoxRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object layout box rows from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object layout box rows.
	 *
	 * @return the number of object layout box rows
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectLayoutBoxRowPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ObjectLayoutBoxRowPersistence, ObjectLayoutBoxRowPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectLayoutBoxRowPersistence.class);

		ServiceTracker
			<ObjectLayoutBoxRowPersistence, ObjectLayoutBoxRowPersistence>
				serviceTracker =
					new ServiceTracker
						<ObjectLayoutBoxRowPersistence,
						 ObjectLayoutBoxRowPersistence>(
							 bundle.getBundleContext(),
							 ObjectLayoutBoxRowPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}