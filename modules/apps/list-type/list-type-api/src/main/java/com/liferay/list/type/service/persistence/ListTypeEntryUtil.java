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

package com.liferay.list.type.service.persistence;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the list type entry service. This utility wraps <code>com.liferay.list.type.service.persistence.impl.ListTypeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see ListTypeEntryPersistence
 * @generated
 */
public class ListTypeEntryUtil {

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
	public static void clearCache(ListTypeEntry listTypeEntry) {
		getPersistence().clearCache(listTypeEntry);
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
	public static Map<Serializable, ListTypeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ListTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ListTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ListTypeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ListTypeEntry update(ListTypeEntry listTypeEntry) {
		return getPersistence().update(listTypeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ListTypeEntry update(
		ListTypeEntry listTypeEntry, ServiceContext serviceContext) {

		return getPersistence().update(listTypeEntry, serviceContext);
	}

	/**
	 * Returns all the list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByUuid_First(
			String uuid, OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByUuid_First(
		String uuid, OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByUuid_Last(
			String uuid, OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry[] findByUuid_PrevAndNext(
			long listTypeEntryId, String uuid,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			listTypeEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the list type entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry[] findByUuid_C_PrevAndNext(
			long listTypeEntryId, String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			listTypeEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the list type entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId) {

		return getPersistence().findByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByListTypeEntryId_First(
			long listTypeEntryId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByListTypeEntryId_First(
		long listTypeEntryId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByListTypeEntryId_Last(
			long listTypeEntryId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);
	}

	/**
	 * Returns all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds) {

		return getPersistence().findByListTypeEntryId(listTypeEntryIds);
	}

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryIds, start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryIds, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByListTypeEntryId(
			listTypeEntryIds, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the list type entries where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	public static void removeByListTypeEntryId(long listTypeEntryId) {
		getPersistence().removeByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns the number of list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching list type entries
	 */
	public static int countByListTypeEntryId(long listTypeEntryId) {
		return getPersistence().countByListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Returns the number of list type entries where listTypeEntryId = any &#63;.
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the number of matching list type entries
	 */
	public static int countByListTypeEntryId(long[] listTypeEntryIds) {
		return getPersistence().countByListTypeEntryId(listTypeEntryIds);
	}

	/**
	 * Returns all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns a range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public static List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().fetchByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry[] findByListTypeDefinitionId_PrevAndNext(
			long listTypeEntryId, long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByListTypeDefinitionId_PrevAndNext(
			listTypeEntryId, listTypeDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the list type entries where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public static void removeByListTypeDefinitionId(long listTypeDefinitionId) {
		getPersistence().removeByListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching list type entries
	 */
	public static int countByListTypeDefinitionId(long listTypeDefinitionId) {
		return getPersistence().countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public static ListTypeEntry findByLTDI_K(
			long listTypeDefinitionId, String key)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByLTDI_K(listTypeDefinitionId, key);
	}

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByLTDI_K(
		long listTypeDefinitionId, String key) {

		return getPersistence().fetchByLTDI_K(listTypeDefinitionId, key);
	}

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public static ListTypeEntry fetchByLTDI_K(
		long listTypeDefinitionId, String key, boolean useFinderCache) {

		return getPersistence().fetchByLTDI_K(
			listTypeDefinitionId, key, useFinderCache);
	}

	/**
	 * Removes the list type entry where listTypeDefinitionId = &#63; and key = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the list type entry that was removed
	 */
	public static ListTypeEntry removeByLTDI_K(
			long listTypeDefinitionId, String key)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().removeByLTDI_K(listTypeDefinitionId, key);
	}

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63; and key = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the number of matching list type entries
	 */
	public static int countByLTDI_K(long listTypeDefinitionId, String key) {
		return getPersistence().countByLTDI_K(listTypeDefinitionId, key);
	}

	/**
	 * Caches the list type entry in the entity cache if it is enabled.
	 *
	 * @param listTypeEntry the list type entry
	 */
	public static void cacheResult(ListTypeEntry listTypeEntry) {
		getPersistence().cacheResult(listTypeEntry);
	}

	/**
	 * Caches the list type entries in the entity cache if it is enabled.
	 *
	 * @param listTypeEntries the list type entries
	 */
	public static void cacheResult(List<ListTypeEntry> listTypeEntries) {
		getPersistence().cacheResult(listTypeEntries);
	}

	/**
	 * Creates a new list type entry with the primary key. Does not add the list type entry to the database.
	 *
	 * @param listTypeEntryId the primary key for the new list type entry
	 * @return the new list type entry
	 */
	public static ListTypeEntry create(long listTypeEntryId) {
		return getPersistence().create(listTypeEntryId);
	}

	/**
	 * Removes the list type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry that was removed
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry remove(long listTypeEntryId)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().remove(listTypeEntryId);
	}

	public static ListTypeEntry updateImpl(ListTypeEntry listTypeEntry) {
		return getPersistence().updateImpl(listTypeEntry);
	}

	/**
	 * Returns the list type entry with the primary key or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry findByPrimaryKey(long listTypeEntryId)
		throws com.liferay.list.type.exception.NoSuchListTypeEntryException {

		return getPersistence().findByPrimaryKey(listTypeEntryId);
	}

	/**
	 * Returns the list type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry, or <code>null</code> if a list type entry with the primary key could not be found
	 */
	public static ListTypeEntry fetchByPrimaryKey(long listTypeEntryId) {
		return getPersistence().fetchByPrimaryKey(listTypeEntryId);
	}

	/**
	 * Returns all the list type entries.
	 *
	 * @return the list type entries
	 */
	public static List<ListTypeEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of list type entries
	 */
	public static List<ListTypeEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list type entries
	 */
	public static List<ListTypeEntry> findAll(
		int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list type entries
	 */
	public static List<ListTypeEntry> findAll(
		int start, int end, OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the list type entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of list type entries.
	 *
	 * @return the number of list type entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ListTypeEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ListTypeEntryPersistence _persistence;

}