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

import com.liferay.object.model.ObjectState;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object state service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectStatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStatePersistence
 * @generated
 */
public class ObjectStateUtil {

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
	public static void clearCache(ObjectState objectState) {
		getPersistence().clearCache(objectState);
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
	public static Map<Serializable, ObjectState> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectState> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectState> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectState> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectState update(ObjectState objectState) {
		return getPersistence().update(objectState);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectState update(
		ObjectState objectState, ServiceContext serviceContext) {

		return getPersistence().update(objectState, serviceContext);
	}

	/**
	 * Returns all the object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object states
	 */
	public static List<ObjectState> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public static List<ObjectState> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByUuid_First(
			String uuid, OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByUuid_First(
		String uuid, OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByUuid_Last(
			String uuid, OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState[] findByUuid_PrevAndNext(
			long objectStateId, String uuid,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_PrevAndNext(
			objectStateId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object states where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object states
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object states
	 */
	public static List<ObjectState> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public static List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState[] findByUuid_C_PrevAndNext(
			long objectStateId, String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectStateId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object states where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object states
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching object states
	 */
	public static List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId) {

		return getPersistence().findByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns a range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public static List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByListTypeEntryId_First(
			long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByListTypeEntryId_First(
		long listTypeEntryId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByListTypeEntryId_Last(
			long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState[] findByListTypeEntryId_PrevAndNext(
			long objectStateId, long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByListTypeEntryId_PrevAndNext(
			objectStateId, listTypeEntryId, orderByComparator);
	}

	/**
	 * Removes all the object states where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	public static void removeByListTypeEntryId(long listTypeEntryId) {
		getPersistence().removeByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns the number of object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching object states
	 */
	public static int countByListTypeEntryId(long listTypeEntryId) {
		return getPersistence().countByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns all the object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object states
	 */
	public static List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId) {

		return getPersistence().findByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns a range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	public static List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end);
	}

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	public static List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByObjectStateFlowId_First(
			long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByObjectStateFlowId_First(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByObjectStateFlowId_First(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByObjectStateFlowId_Last(
			long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByObjectStateFlowId_Last(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().fetchByObjectStateFlowId_Last(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState[] findByObjectStateFlowId_PrevAndNext(
			long objectStateId, long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByObjectStateFlowId_PrevAndNext(
			objectStateId, objectStateFlowId, orderByComparator);
	}

	/**
	 * Removes all the object states where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	public static void removeByObjectStateFlowId(long objectStateFlowId) {
		getPersistence().removeByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns the number of object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	public static int countByObjectStateFlowId(long objectStateFlowId) {
		return getPersistence().countByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	public static ObjectState findByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId) {

		return getPersistence().fetchByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	public static ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId, boolean useFinderCache) {

		return getPersistence().fetchByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId, useFinderCache);
	}

	/**
	 * Removes the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the object state that was removed
	 */
	public static ObjectState removeByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().removeByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Returns the number of object states where listTypeEntryId = &#63; and objectStateFlowId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	public static int countByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId) {

		return getPersistence().countByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	/**
	 * Caches the object state in the entity cache if it is enabled.
	 *
	 * @param objectState the object state
	 */
	public static void cacheResult(ObjectState objectState) {
		getPersistence().cacheResult(objectState);
	}

	/**
	 * Caches the object states in the entity cache if it is enabled.
	 *
	 * @param objectStates the object states
	 */
	public static void cacheResult(List<ObjectState> objectStates) {
		getPersistence().cacheResult(objectStates);
	}

	/**
	 * Creates a new object state with the primary key. Does not add the object state to the database.
	 *
	 * @param objectStateId the primary key for the new object state
	 * @return the new object state
	 */
	public static ObjectState create(long objectStateId) {
		return getPersistence().create(objectStateId);
	}

	/**
	 * Removes the object state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state that was removed
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState remove(long objectStateId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().remove(objectStateId);
	}

	public static ObjectState updateImpl(ObjectState objectState) {
		return getPersistence().updateImpl(objectState);
	}

	/**
	 * Returns the object state with the primary key or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	public static ObjectState findByPrimaryKey(long objectStateId)
		throws com.liferay.object.exception.NoSuchObjectStateException {

		return getPersistence().findByPrimaryKey(objectStateId);
	}

	/**
	 * Returns the object state with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state, or <code>null</code> if a object state with the primary key could not be found
	 */
	public static ObjectState fetchByPrimaryKey(long objectStateId) {
		return getPersistence().fetchByPrimaryKey(objectStateId);
	}

	/**
	 * Returns all the object states.
	 *
	 * @return the object states
	 */
	public static List<ObjectState> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of object states
	 */
	public static List<ObjectState> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object states
	 */
	public static List<ObjectState> findAll(
		int start, int end, OrderByComparator<ObjectState> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object states
	 */
	public static List<ObjectState> findAll(
		int start, int end, OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object states from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object states.
	 *
	 * @return the number of object states
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectStatePersistence getPersistence() {
		return _persistence;
	}

	private static volatile ObjectStatePersistence _persistence;

}