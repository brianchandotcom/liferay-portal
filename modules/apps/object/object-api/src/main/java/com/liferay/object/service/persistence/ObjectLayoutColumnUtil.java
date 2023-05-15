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

import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object layout column service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectLayoutColumnPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutColumnPersistence
 * @generated
 */
public class ObjectLayoutColumnUtil {

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
	public static void clearCache(ObjectLayoutColumn objectLayoutColumn) {
		getPersistence().clearCache(objectLayoutColumn);
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
	public static Map<Serializable, ObjectLayoutColumn> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectLayoutColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectLayoutColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectLayoutColumn> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectLayoutColumn update(
		ObjectLayoutColumn objectLayoutColumn) {

		return getPersistence().update(objectLayoutColumn);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectLayoutColumn update(
		ObjectLayoutColumn objectLayoutColumn, ServiceContext serviceContext) {

		return getPersistence().update(objectLayoutColumn, serviceContext);
	}

	/**
	 * Returns all the object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn[] findByUuid_PrevAndNext(
			long objectLayoutColumnId, String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_PrevAndNext(
			objectLayoutColumnId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object layout columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout columns
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn[] findByUuid_C_PrevAndNext(
			long objectLayoutColumnId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectLayoutColumnId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object layout columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout columns
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object layout columns where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId) {

		return getPersistence().findByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns a range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end) {

		return getPersistence().findByObjectFieldId(objectFieldId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByObjectFieldId_First(
			long objectFieldId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the first object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByObjectFieldId_First(
		long objectFieldId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByObjectFieldId_Last(
			long objectFieldId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByObjectFieldId_Last(
		long objectFieldId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn[] findByObjectFieldId_PrevAndNext(
			long objectLayoutColumnId, long objectFieldId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectFieldId_PrevAndNext(
			objectLayoutColumnId, objectFieldId, orderByComparator);
	}

	/**
	 * Removes all the object layout columns where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	public static void removeByObjectFieldId(long objectFieldId) {
		getPersistence().removeByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the number of object layout columns where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object layout columns
	 */
	public static int countByObjectFieldId(long objectFieldId) {
		return getPersistence().countByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId) {

		return getPersistence().findByObjectLayoutRowId(objectLayoutRowId);
	}

	/**
	 * Returns a range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end) {

		return getPersistence().findByObjectLayoutRowId(
			objectLayoutRowId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findByObjectLayoutRowId(
			objectLayoutRowId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public static List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectLayoutRowId(
			objectLayoutRowId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByObjectLayoutRowId_First(
			long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectLayoutRowId_First(
			objectLayoutRowId, orderByComparator);
	}

	/**
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByObjectLayoutRowId_First(
		long objectLayoutRowId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByObjectLayoutRowId_First(
			objectLayoutRowId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn findByObjectLayoutRowId_Last(
			long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectLayoutRowId_Last(
			objectLayoutRowId, orderByComparator);
	}

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public static ObjectLayoutColumn fetchByObjectLayoutRowId_Last(
		long objectLayoutRowId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().fetchByObjectLayoutRowId_Last(
			objectLayoutRowId, orderByComparator);
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn[] findByObjectLayoutRowId_PrevAndNext(
			long objectLayoutColumnId, long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByObjectLayoutRowId_PrevAndNext(
			objectLayoutColumnId, objectLayoutRowId, orderByComparator);
	}

	/**
	 * Removes all the object layout columns where objectLayoutRowId = &#63; from the database.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 */
	public static void removeByObjectLayoutRowId(long objectLayoutRowId) {
		getPersistence().removeByObjectLayoutRowId(objectLayoutRowId);
	}

	/**
	 * Returns the number of object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the number of matching object layout columns
	 */
	public static int countByObjectLayoutRowId(long objectLayoutRowId) {
		return getPersistence().countByObjectLayoutRowId(objectLayoutRowId);
	}

	/**
	 * Caches the object layout column in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumn the object layout column
	 */
	public static void cacheResult(ObjectLayoutColumn objectLayoutColumn) {
		getPersistence().cacheResult(objectLayoutColumn);
	}

	/**
	 * Caches the object layout columns in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumns the object layout columns
	 */
	public static void cacheResult(
		List<ObjectLayoutColumn> objectLayoutColumns) {

		getPersistence().cacheResult(objectLayoutColumns);
	}

	/**
	 * Creates a new object layout column with the primary key. Does not add the object layout column to the database.
	 *
	 * @param objectLayoutColumnId the primary key for the new object layout column
	 * @return the new object layout column
	 */
	public static ObjectLayoutColumn create(long objectLayoutColumnId) {
		return getPersistence().create(objectLayoutColumnId);
	}

	/**
	 * Removes the object layout column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column that was removed
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn remove(long objectLayoutColumnId)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().remove(objectLayoutColumnId);
	}

	public static ObjectLayoutColumn updateImpl(
		ObjectLayoutColumn objectLayoutColumn) {

		return getPersistence().updateImpl(objectLayoutColumn);
	}

	/**
	 * Returns the object layout column with the primary key or throws a <code>NoSuchObjectLayoutColumnException</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn findByPrimaryKey(long objectLayoutColumnId)
		throws com.liferay.object.exception.NoSuchObjectLayoutColumnException {

		return getPersistence().findByPrimaryKey(objectLayoutColumnId);
	}

	/**
	 * Returns the object layout column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column, or <code>null</code> if a object layout column with the primary key could not be found
	 */
	public static ObjectLayoutColumn fetchByPrimaryKey(
		long objectLayoutColumnId) {

		return getPersistence().fetchByPrimaryKey(objectLayoutColumnId);
	}

	/**
	 * Returns all the object layout columns.
	 *
	 * @return the object layout columns
	 */
	public static List<ObjectLayoutColumn> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of object layout columns
	 */
	public static List<ObjectLayoutColumn> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout columns
	 */
	public static List<ObjectLayoutColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout columns
	 */
	public static List<ObjectLayoutColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object layout columns from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object layout columns.
	 *
	 * @return the number of object layout columns
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectLayoutColumnPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ObjectLayoutColumnPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ObjectLayoutColumnPersistence _persistence;

}