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

import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object view sort column service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectViewSortColumnPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewSortColumnPersistence
 * @generated
 */
public class ObjectViewSortColumnUtil {

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
	public static void clearCache(ObjectViewSortColumn objectViewSortColumn) {
		getPersistence().clearCache(objectViewSortColumn);
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
	public static Map<Serializable, ObjectViewSortColumn> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectViewSortColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectViewSortColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectViewSortColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectViewSortColumn update(
		ObjectViewSortColumn objectViewSortColumn) {

		return getPersistence().update(objectViewSortColumn);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectViewSortColumn update(
		ObjectViewSortColumn objectViewSortColumn,
		ServiceContext serviceContext) {

		return getPersistence().update(objectViewSortColumn, serviceContext);
	}

	/**
	 * Returns all the object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn[] findByUuid_PrevAndNext(
			long objectViewSortColumnId, String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_PrevAndNext(
			objectViewSortColumnId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object view sort columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view sort columns
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn[] findByUuid_C_PrevAndNext(
			long objectViewSortColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectViewSortColumnId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object view sort columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view sort columns
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId) {

		return getPersistence().findByObjectViewId(objectViewId);
	}

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return getPersistence().findByObjectViewId(objectViewId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn[] findByObjectViewId_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByObjectViewId_PrevAndNext(
			objectViewSortColumnId, objectViewId, orderByComparator);
	}

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public static void removeByObjectViewId(long objectViewId) {
		getPersistence().removeByObjectViewId(objectViewId);
	}

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view sort columns
	 */
	public static int countByObjectViewId(long objectViewId) {
		return getPersistence().countByObjectViewId(objectViewId);
	}

	/**
	 * Returns all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return getPersistence().findByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	public static List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	public static ObjectViewSortColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().fetchByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByOVI_OFN_PrevAndNext(
			objectViewSortColumnId, objectViewId, objectFieldName,
			orderByComparator);
	}

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	public static void removeByOVI_OFN(
		long objectViewId, String objectFieldName) {

		getPersistence().removeByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view sort columns
	 */
	public static int countByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return getPersistence().countByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Caches the object view sort column in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumn the object view sort column
	 */
	public static void cacheResult(ObjectViewSortColumn objectViewSortColumn) {
		getPersistence().cacheResult(objectViewSortColumn);
	}

	/**
	 * Caches the object view sort columns in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumns the object view sort columns
	 */
	public static void cacheResult(
		List<ObjectViewSortColumn> objectViewSortColumns) {

		getPersistence().cacheResult(objectViewSortColumns);
	}

	/**
	 * Creates a new object view sort column with the primary key. Does not add the object view sort column to the database.
	 *
	 * @param objectViewSortColumnId the primary key for the new object view sort column
	 * @return the new object view sort column
	 */
	public static ObjectViewSortColumn create(long objectViewSortColumnId) {
		return getPersistence().create(objectViewSortColumnId);
	}

	/**
	 * Removes the object view sort column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column that was removed
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn remove(long objectViewSortColumnId)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().remove(objectViewSortColumnId);
	}

	public static ObjectViewSortColumn updateImpl(
		ObjectViewSortColumn objectViewSortColumn) {

		return getPersistence().updateImpl(objectViewSortColumn);
	}

	/**
	 * Returns the object view sort column with the primary key or throws a <code>NoSuchObjectViewSortColumnException</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn findByPrimaryKey(
			long objectViewSortColumnId)
		throws com.liferay.object.exception.
			NoSuchObjectViewSortColumnException {

		return getPersistence().findByPrimaryKey(objectViewSortColumnId);
	}

	/**
	 * Returns the object view sort column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column, or <code>null</code> if a object view sort column with the primary key could not be found
	 */
	public static ObjectViewSortColumn fetchByPrimaryKey(
		long objectViewSortColumnId) {

		return getPersistence().fetchByPrimaryKey(objectViewSortColumnId);
	}

	/**
	 * Returns all the object view sort columns.
	 *
	 * @return the object view sort columns
	 */
	public static List<ObjectViewSortColumn> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of object view sort columns
	 */
	public static List<ObjectViewSortColumn> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view sort columns
	 */
	public static List<ObjectViewSortColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view sort columns
	 */
	public static List<ObjectViewSortColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object view sort columns from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object view sort columns.
	 *
	 * @return the number of object view sort columns
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectViewSortColumnPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ObjectViewSortColumnPersistence _persistence;

}