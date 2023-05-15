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

package com.liferay.redirect.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.redirect.model.RedirectEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the redirect entry service. This utility wraps <code>com.liferay.redirect.service.persistence.impl.RedirectEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntryPersistence
 * @generated
 */
public class RedirectEntryUtil {

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
	public static void clearCache(RedirectEntry redirectEntry) {
		getPersistence().clearCache(redirectEntry);
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
	public static Map<Serializable, RedirectEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RedirectEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RedirectEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RedirectEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RedirectEntry update(RedirectEntry redirectEntry) {
		return getPersistence().update(redirectEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RedirectEntry update(
		RedirectEntry redirectEntry, ServiceContext serviceContext) {

		return getPersistence().update(redirectEntry, serviceContext);
	}

	/**
	 * Returns all the redirect entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByUuid_First(
			String uuid, OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUuid_First(
		String uuid, OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByUuid_Last(
			String uuid, OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] findByUuid_PrevAndNext(
			long redirectEntryId, String uuid,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			redirectEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the redirect entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of redirect entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching redirect entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the redirect entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the redirect entry that was removed
	 */
	public static RedirectEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of redirect entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching redirect entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] findByUuid_C_PrevAndNext(
			long redirectEntryId, String uuid, long companyId,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			redirectEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the redirect entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching redirect entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the redirect entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect entries
	 */
	public static List<RedirectEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public static List<RedirectEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByGroupId_First(
			long groupId, OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByGroupId_First(
		long groupId, OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByGroupId_Last(
			long groupId, OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] findByGroupId_PrevAndNext(
			long redirectEntryId, long groupId,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			redirectEntryId, groupId, orderByComparator);
	}

	/**
	 * Returns all the redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set of redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] filterFindByGroupId_PrevAndNext(
			long redirectEntryId, long groupId,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			redirectEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the redirect entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of redirect entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect entries that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the matching redirect entries
	 */
	public static List<RedirectEntry> findByG_D(
		long groupId, String destinationURL) {

		return getPersistence().findByG_D(groupId, destinationURL);
	}

	/**
	 * Returns a range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public static List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end) {

		return getPersistence().findByG_D(groupId, destinationURL, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findByG_D(
			groupId, destinationURL, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public static List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_D(
			groupId, destinationURL, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByG_D_First(
			long groupId, String destinationURL,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByG_D_First(
			groupId, destinationURL, orderByComparator);
	}

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByG_D_First(
		long groupId, String destinationURL,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByG_D_First(
			groupId, destinationURL, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByG_D_Last(
			long groupId, String destinationURL,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByG_D_Last(
			groupId, destinationURL, orderByComparator);
	}

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByG_D_Last(
		long groupId, String destinationURL,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().fetchByG_D_Last(
			groupId, destinationURL, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] findByG_D_PrevAndNext(
			long redirectEntryId, long groupId, String destinationURL,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByG_D_PrevAndNext(
			redirectEntryId, groupId, destinationURL, orderByComparator);
	}

	/**
	 * Returns all the redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL) {

		return getPersistence().filterFindByG_D(groupId, destinationURL);
	}

	/**
	 * Returns a range of all the redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL, int start, int end) {

		return getPersistence().filterFindByG_D(
			groupId, destinationURL, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries that the user has permissions to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries that the user has permission to view
	 */
	public static List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL, int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().filterFindByG_D(
			groupId, destinationURL, start, end, orderByComparator);
	}

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set of redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry[] filterFindByG_D_PrevAndNext(
			long redirectEntryId, long groupId, String destinationURL,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().filterFindByG_D_PrevAndNext(
			redirectEntryId, groupId, destinationURL, orderByComparator);
	}

	/**
	 * Removes all the redirect entries where groupId = &#63; and destinationURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 */
	public static void removeByG_D(long groupId, String destinationURL) {
		getPersistence().removeByG_D(groupId, destinationURL);
	}

	/**
	 * Returns the number of redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the number of matching redirect entries
	 */
	public static int countByG_D(long groupId, String destinationURL) {
		return getPersistence().countByG_D(groupId, destinationURL);
	}

	/**
	 * Returns the number of redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the number of matching redirect entries that the user has permission to view
	 */
	public static int filterCountByG_D(long groupId, String destinationURL) {
		return getPersistence().filterCountByG_D(groupId, destinationURL);
	}

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public static RedirectEntry findByG_S(long groupId, String sourceURL)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByG_S(groupId, sourceURL);
	}

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByG_S(long groupId, String sourceURL) {
		return getPersistence().fetchByG_S(groupId, sourceURL);
	}

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static RedirectEntry fetchByG_S(
		long groupId, String sourceURL, boolean useFinderCache) {

		return getPersistence().fetchByG_S(groupId, sourceURL, useFinderCache);
	}

	/**
	 * Removes the redirect entry where groupId = &#63; and sourceURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the redirect entry that was removed
	 */
	public static RedirectEntry removeByG_S(long groupId, String sourceURL)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().removeByG_S(groupId, sourceURL);
	}

	/**
	 * Returns the number of redirect entries where groupId = &#63; and sourceURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the number of matching redirect entries
	 */
	public static int countByG_S(long groupId, String sourceURL) {
		return getPersistence().countByG_S(groupId, sourceURL);
	}

	/**
	 * Caches the redirect entry in the entity cache if it is enabled.
	 *
	 * @param redirectEntry the redirect entry
	 */
	public static void cacheResult(RedirectEntry redirectEntry) {
		getPersistence().cacheResult(redirectEntry);
	}

	/**
	 * Caches the redirect entries in the entity cache if it is enabled.
	 *
	 * @param redirectEntries the redirect entries
	 */
	public static void cacheResult(List<RedirectEntry> redirectEntries) {
		getPersistence().cacheResult(redirectEntries);
	}

	/**
	 * Creates a new redirect entry with the primary key. Does not add the redirect entry to the database.
	 *
	 * @param redirectEntryId the primary key for the new redirect entry
	 * @return the new redirect entry
	 */
	public static RedirectEntry create(long redirectEntryId) {
		return getPersistence().create(redirectEntryId);
	}

	/**
	 * Removes the redirect entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry that was removed
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry remove(long redirectEntryId)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().remove(redirectEntryId);
	}

	public static RedirectEntry updateImpl(RedirectEntry redirectEntry) {
		return getPersistence().updateImpl(redirectEntry);
	}

	/**
	 * Returns the redirect entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry findByPrimaryKey(long redirectEntryId)
		throws com.liferay.redirect.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(redirectEntryId);
	}

	/**
	 * Returns the redirect entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry, or <code>null</code> if a redirect entry with the primary key could not be found
	 */
	public static RedirectEntry fetchByPrimaryKey(long redirectEntryId) {
		return getPersistence().fetchByPrimaryKey(redirectEntryId);
	}

	/**
	 * Returns all the redirect entries.
	 *
	 * @return the redirect entries
	 */
	public static List<RedirectEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of redirect entries
	 */
	public static List<RedirectEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redirect entries
	 */
	public static List<RedirectEntry> findAll(
		int start, int end,
		OrderByComparator<RedirectEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redirect entries
	 */
	public static List<RedirectEntry> findAll(
		int start, int end, OrderByComparator<RedirectEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the redirect entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of redirect entries.
	 *
	 * @return the number of redirect entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RedirectEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(RedirectEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile RedirectEntryPersistence _persistence;

}