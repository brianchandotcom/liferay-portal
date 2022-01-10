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

import com.liferay.object.model.ObjectViewColumn;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object view column service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectViewColumnPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewColumnPersistence
 * @generated
 */
public class ObjectViewColumnUtil {

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
	public static void clearCache(ObjectViewColumn objectViewColumn) {
		getPersistence().clearCache(objectViewColumn);
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
	public static Map<Serializable, ObjectViewColumn> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectViewColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectViewColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectViewColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectViewColumn update(ObjectViewColumn objectViewColumn) {
		return getPersistence().update(objectViewColumn);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectViewColumn update(
		ObjectViewColumn objectViewColumn, ServiceContext serviceContext) {

		return getPersistence().update(objectViewColumn, serviceContext);
	}

	/**
	 * Returns all the object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByUuid_First(
			String uuid, OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByUuid_First(
		String uuid, OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByUuid_Last(
			String uuid, OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn[] findByUuid_PrevAndNext(
			long objectViewColumnId, String uuid,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_PrevAndNext(
			objectViewColumnId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object view columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view columns
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn[] findByUuid_C_PrevAndNext(
			long objectViewColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectViewColumnId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object view columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view columns
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view columns
	 */
	public static List<ObjectViewColumn> findByObjectViewId(long objectViewId) {
		return getPersistence().findByObjectViewId(objectViewId);
	}

	/**
	 * Returns a range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return getPersistence().findByObjectViewId(objectViewId, start, end);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	public static List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectViewId(
			objectViewId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_First(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	public static ObjectViewColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	public static ObjectViewColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().fetchByObjectViewId_Last(
			objectViewId, orderByComparator);
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn[] findByObjectViewId_PrevAndNext(
			long objectViewColumnId, long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByObjectViewId_PrevAndNext(
			objectViewColumnId, objectViewId, orderByComparator);
	}

	/**
	 * Removes all the object view columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public static void removeByObjectViewId(long objectViewId) {
		getPersistence().removeByObjectViewId(objectViewId);
	}

	/**
	 * Returns the number of object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view columns
	 */
	public static int countByObjectViewId(long objectViewId) {
		return getPersistence().countByObjectViewId(objectViewId);
	}

	/**
	 * Caches the object view column in the entity cache if it is enabled.
	 *
	 * @param objectViewColumn the object view column
	 */
	public static void cacheResult(ObjectViewColumn objectViewColumn) {
		getPersistence().cacheResult(objectViewColumn);
	}

	/**
	 * Caches the object view columns in the entity cache if it is enabled.
	 *
	 * @param objectViewColumns the object view columns
	 */
	public static void cacheResult(List<ObjectViewColumn> objectViewColumns) {
		getPersistence().cacheResult(objectViewColumns);
	}

	/**
	 * Creates a new object view column with the primary key. Does not add the object view column to the database.
	 *
	 * @param objectViewColumnId the primary key for the new object view column
	 * @return the new object view column
	 */
	public static ObjectViewColumn create(long objectViewColumnId) {
		return getPersistence().create(objectViewColumnId);
	}

	/**
	 * Removes the object view column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column that was removed
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn remove(long objectViewColumnId)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().remove(objectViewColumnId);
	}

	public static ObjectViewColumn updateImpl(
		ObjectViewColumn objectViewColumn) {

		return getPersistence().updateImpl(objectViewColumn);
	}

	/**
	 * Returns the object view column with the primary key or throws a <code>NoSuchObjectViewColumnException</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn findByPrimaryKey(long objectViewColumnId)
		throws com.liferay.object.exception.NoSuchObjectViewColumnException {

		return getPersistence().findByPrimaryKey(objectViewColumnId);
	}

	/**
	 * Returns the object view column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column, or <code>null</code> if a object view column with the primary key could not be found
	 */
	public static ObjectViewColumn fetchByPrimaryKey(long objectViewColumnId) {
		return getPersistence().fetchByPrimaryKey(objectViewColumnId);
	}

	/**
	 * Returns all the object view columns.
	 *
	 * @return the object view columns
	 */
	public static List<ObjectViewColumn> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of object view columns
	 */
	public static List<ObjectViewColumn> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view columns
	 */
	public static List<ObjectViewColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view columns
	 */
	public static List<ObjectViewColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object view columns from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object view columns.
	 *
	 * @return the number of object view columns
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectViewColumnPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ObjectViewColumnPersistence _persistence;

}