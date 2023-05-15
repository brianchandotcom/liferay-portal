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

import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object layout row service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectLayoutRowPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutRowPersistence
 * @generated
 */
public class ObjectLayoutRowUtil {

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
	public static void clearCache(ObjectLayoutRow objectLayoutRow) {
		getPersistence().clearCache(objectLayoutRow);
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
	public static Map<Serializable, ObjectLayoutRow> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectLayoutRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectLayoutRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectLayoutRow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectLayoutRow update(ObjectLayoutRow objectLayoutRow) {
		return getPersistence().update(objectLayoutRow);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectLayoutRow update(
		ObjectLayoutRow objectLayoutRow, ServiceContext serviceContext) {

		return getPersistence().update(objectLayoutRow, serviceContext);
	}

	/**
	 * Returns all the object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow[] findByUuid_PrevAndNext(
			long objectLayoutRowId, String uuid,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_PrevAndNext(
			objectLayoutRowId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object layout rows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout rows
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow[] findByUuid_C_PrevAndNext(
			long objectLayoutRowId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectLayoutRowId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object layout rows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout rows
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId) {

		return getPersistence().findByObjectLayoutBoxId(objectLayoutBoxId);
	}

	/**
	 * Returns a range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end) {

		return getPersistence().findByObjectLayoutBoxId(
			objectLayoutBoxId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().findByObjectLayoutBoxId(
			objectLayoutBoxId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	public static List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectLayoutBoxId(
			objectLayoutBoxId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByObjectLayoutBoxId_First(
			long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByObjectLayoutBoxId_First(
			objectLayoutBoxId, orderByComparator);
	}

	/**
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByObjectLayoutBoxId_First(
		long objectLayoutBoxId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByObjectLayoutBoxId_First(
			objectLayoutBoxId, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow findByObjectLayoutBoxId_Last(
			long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByObjectLayoutBoxId_Last(
			objectLayoutBoxId, orderByComparator);
	}

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	public static ObjectLayoutRow fetchByObjectLayoutBoxId_Last(
		long objectLayoutBoxId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().fetchByObjectLayoutBoxId_Last(
			objectLayoutBoxId, orderByComparator);
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow[] findByObjectLayoutBoxId_PrevAndNext(
			long objectLayoutRowId, long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByObjectLayoutBoxId_PrevAndNext(
			objectLayoutRowId, objectLayoutBoxId, orderByComparator);
	}

	/**
	 * Removes all the object layout rows where objectLayoutBoxId = &#63; from the database.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 */
	public static void removeByObjectLayoutBoxId(long objectLayoutBoxId) {
		getPersistence().removeByObjectLayoutBoxId(objectLayoutBoxId);
	}

	/**
	 * Returns the number of object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the number of matching object layout rows
	 */
	public static int countByObjectLayoutBoxId(long objectLayoutBoxId) {
		return getPersistence().countByObjectLayoutBoxId(objectLayoutBoxId);
	}

	/**
	 * Caches the object layout row in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRow the object layout row
	 */
	public static void cacheResult(ObjectLayoutRow objectLayoutRow) {
		getPersistence().cacheResult(objectLayoutRow);
	}

	/**
	 * Caches the object layout rows in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRows the object layout rows
	 */
	public static void cacheResult(List<ObjectLayoutRow> objectLayoutRows) {
		getPersistence().cacheResult(objectLayoutRows);
	}

	/**
	 * Creates a new object layout row with the primary key. Does not add the object layout row to the database.
	 *
	 * @param objectLayoutRowId the primary key for the new object layout row
	 * @return the new object layout row
	 */
	public static ObjectLayoutRow create(long objectLayoutRowId) {
		return getPersistence().create(objectLayoutRowId);
	}

	/**
	 * Removes the object layout row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row that was removed
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow remove(long objectLayoutRowId)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().remove(objectLayoutRowId);
	}

	public static ObjectLayoutRow updateImpl(ObjectLayoutRow objectLayoutRow) {
		return getPersistence().updateImpl(objectLayoutRow);
	}

	/**
	 * Returns the object layout row with the primary key or throws a <code>NoSuchObjectLayoutRowException</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow findByPrimaryKey(long objectLayoutRowId)
		throws com.liferay.object.exception.NoSuchObjectLayoutRowException {

		return getPersistence().findByPrimaryKey(objectLayoutRowId);
	}

	/**
	 * Returns the object layout row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row, or <code>null</code> if a object layout row with the primary key could not be found
	 */
	public static ObjectLayoutRow fetchByPrimaryKey(long objectLayoutRowId) {
		return getPersistence().fetchByPrimaryKey(objectLayoutRowId);
	}

	/**
	 * Returns all the object layout rows.
	 *
	 * @return the object layout rows
	 */
	public static List<ObjectLayoutRow> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of object layout rows
	 */
	public static List<ObjectLayoutRow> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout rows
	 */
	public static List<ObjectLayoutRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout rows
	 */
	public static List<ObjectLayoutRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object layout rows from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object layout rows.
	 *
	 * @return the number of object layout rows
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectLayoutRowPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ObjectLayoutRowPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ObjectLayoutRowPersistence _persistence;

}