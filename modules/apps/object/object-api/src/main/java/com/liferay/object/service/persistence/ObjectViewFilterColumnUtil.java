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

import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object view filter column service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectViewFilterColumnPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewFilterColumnPersistence
 * @generated
 */
public class ObjectViewFilterColumnUtil {

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
		ObjectViewFilterColumn objectViewFilterColumn) {

		getPersistence().clearCache(objectViewFilterColumn);
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
	public static Map<Serializable, ObjectViewFilterColumn> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectViewFilterColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectViewFilterColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectViewFilterColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectViewFilterColumn update(
		ObjectViewFilterColumn objectViewFilterColumn) {

		return getPersistence().update(objectViewFilterColumn);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectViewFilterColumn update(
		ObjectViewFilterColumn objectViewFilterColumn,
		ServiceContext serviceContext) {

		return getPersistence().update(objectViewFilterColumn, serviceContext);
	}

	/**
	 * Returns all the object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn[] findByUuid_PrevAndNext(
			long objectViewFilterColumnId, String uuid,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_PrevAndNext(
			objectViewFilterColumnId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view filter columns
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn[] findByUuid_C_PrevAndNext(
			long objectViewFilterColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectViewFilterColumnId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view filter columns
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId) {

		return getPersistence().findByObjectViewId(objectViewId);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return getPersistence().findByObjectViewId(objectViewId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn[] findByObjectViewId_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByObjectViewId_PrevAndNext(
			objectViewFilterColumnId, objectViewId, orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public static void removeByObjectViewId(long objectViewId) {
		getPersistence().removeByObjectViewId(objectViewId);
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view filter columns
	 */
	public static int countByObjectViewId(long objectViewId) {
		return getPersistence().countByObjectViewId(objectViewId);
	}

	/**
	 * Returns all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return getPersistence().findByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public static ObjectViewFilterColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().fetchByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);
	}

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			String objectFieldName,
			OrderByComparator<ObjectViewFilterColumn> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByOVI_OFN_PrevAndNext(
			objectViewFilterColumnId, objectViewId, objectFieldName,
			orderByComparator);
	}

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	public static void removeByOVI_OFN(
		long objectViewId, String objectFieldName) {

		getPersistence().removeByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view filter columns
	 */
	public static int countByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return getPersistence().countByOVI_OFN(objectViewId, objectFieldName);
	}

	/**
	 * Caches the object view filter column in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumn the object view filter column
	 */
	public static void cacheResult(
		ObjectViewFilterColumn objectViewFilterColumn) {

		getPersistence().cacheResult(objectViewFilterColumn);
	}

	/**
	 * Caches the object view filter columns in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumns the object view filter columns
	 */
	public static void cacheResult(
		List<ObjectViewFilterColumn> objectViewFilterColumns) {

		getPersistence().cacheResult(objectViewFilterColumns);
	}

	/**
	 * Creates a new object view filter column with the primary key. Does not add the object view filter column to the database.
	 *
	 * @param objectViewFilterColumnId the primary key for the new object view filter column
	 * @return the new object view filter column
	 */
	public static ObjectViewFilterColumn create(long objectViewFilterColumnId) {
		return getPersistence().create(objectViewFilterColumnId);
	}

	/**
	 * Removes the object view filter column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column that was removed
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn remove(long objectViewFilterColumnId)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().remove(objectViewFilterColumnId);
	}

	public static ObjectViewFilterColumn updateImpl(
		ObjectViewFilterColumn objectViewFilterColumn) {

		return getPersistence().updateImpl(objectViewFilterColumn);
	}

	/**
	 * Returns the object view filter column with the primary key or throws a <code>NoSuchObjectViewFilterColumnException</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn findByPrimaryKey(
			long objectViewFilterColumnId)
		throws com.liferay.object.exception.
			NoSuchObjectViewFilterColumnException {

		return getPersistence().findByPrimaryKey(objectViewFilterColumnId);
	}

	/**
	 * Returns the object view filter column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column, or <code>null</code> if a object view filter column with the primary key could not be found
	 */
	public static ObjectViewFilterColumn fetchByPrimaryKey(
		long objectViewFilterColumnId) {

		return getPersistence().fetchByPrimaryKey(objectViewFilterColumnId);
	}

	/**
	 * Returns all the object view filter columns.
	 *
	 * @return the object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view filter columns
	 */
	public static List<ObjectViewFilterColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewFilterColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object view filter columns from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object view filter columns.
	 *
	 * @return the number of object view filter columns
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectViewFilterColumnPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ObjectViewFilterColumnPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ObjectViewFilterColumnPersistence _persistence;

}