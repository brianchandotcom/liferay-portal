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

package com.liferay.layout.seo.service.persistence;

import com.liferay.layout.seo.model.LayoutSEOSiteEntry;
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
 * The persistence utility for the layout seo site entry service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.LayoutSEOSiteEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteEntryPersistence
 * @generated
 */
public class LayoutSEOSiteEntryUtil {

	/**
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
	public static void clearCache(LayoutSEOSiteEntry layoutSEOSiteEntry) {
		getPersistence().clearCache(layoutSEOSiteEntry);
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
	public static Map<Serializable, LayoutSEOSiteEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSEOSiteEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSEOSiteEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSEOSiteEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSEOSiteEntry update(
		LayoutSEOSiteEntry layoutSEOSiteEntry) {

		return getPersistence().update(layoutSEOSiteEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSEOSiteEntry update(
		LayoutSEOSiteEntry layoutSEOSiteEntry, ServiceContext serviceContext) {

		return getPersistence().update(layoutSEOSiteEntry, serviceContext);
	}

	/**
	 * Returns all the layout seo site entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByUuid_First(
			String uuid,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUuid_First(
		String uuid, OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout seo site entries before and after the current layout seo site entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the current layout seo site entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public static LayoutSEOSiteEntry[] findByUuid_PrevAndNext(
			long layoutSEOSiteEntryId, String uuid,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutSEOSiteEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout seo site entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout seo site entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo site entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo site entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout seo site entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo site entry that was removed
	 */
	public static LayoutSEOSiteEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout seo site entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo site entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout seo site entries before and after the current layout seo site entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the current layout seo site entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public static LayoutSEOSiteEntry[] findByUuid_C_PrevAndNext(
			long layoutSEOSiteEntryId, String uuid, long companyId,
			OrderByComparator<LayoutSEOSiteEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutSEOSiteEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout seo site entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout seo site entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo site entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the layout seo site entry where groupId = &#63; or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site entry
	 * @throws NoSuchSiteEntryException if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry findByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns the layout seo site entry where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByGroupId(long groupId) {
		return getPersistence().fetchByGroupId(groupId);
	}

	/**
	 * Returns the layout seo site entry where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site entry, or <code>null</code> if a matching layout seo site entry could not be found
	 */
	public static LayoutSEOSiteEntry fetchByGroupId(
		long groupId, boolean useFinderCache) {

		return getPersistence().fetchByGroupId(groupId, useFinderCache);
	}

	/**
	 * Removes the layout seo site entry where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the layout seo site entry that was removed
	 */
	public static LayoutSEOSiteEntry removeByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of layout seo site entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout seo site entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Caches the layout seo site entry in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSiteEntry the layout seo site entry
	 */
	public static void cacheResult(LayoutSEOSiteEntry layoutSEOSiteEntry) {
		getPersistence().cacheResult(layoutSEOSiteEntry);
	}

	/**
	 * Caches the layout seo site entries in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSiteEntries the layout seo site entries
	 */
	public static void cacheResult(
		List<LayoutSEOSiteEntry> layoutSEOSiteEntries) {

		getPersistence().cacheResult(layoutSEOSiteEntries);
	}

	/**
	 * Creates a new layout seo site entry with the primary key. Does not add the layout seo site entry to the database.
	 *
	 * @param layoutSEOSiteEntryId the primary key for the new layout seo site entry
	 * @return the new layout seo site entry
	 */
	public static LayoutSEOSiteEntry create(long layoutSEOSiteEntryId) {
		return getPersistence().create(layoutSEOSiteEntryId);
	}

	/**
	 * Removes the layout seo site entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry that was removed
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public static LayoutSEOSiteEntry remove(long layoutSEOSiteEntryId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().remove(layoutSEOSiteEntryId);
	}

	public static LayoutSEOSiteEntry updateImpl(
		LayoutSEOSiteEntry layoutSEOSiteEntry) {

		return getPersistence().updateImpl(layoutSEOSiteEntry);
	}

	/**
	 * Returns the layout seo site entry with the primary key or throws a <code>NoSuchSiteEntryException</code> if it could not be found.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry
	 * @throws NoSuchSiteEntryException if a layout seo site entry with the primary key could not be found
	 */
	public static LayoutSEOSiteEntry findByPrimaryKey(long layoutSEOSiteEntryId)
		throws com.liferay.layout.seo.exception.NoSuchSiteEntryException {

		return getPersistence().findByPrimaryKey(layoutSEOSiteEntryId);
	}

	/**
	 * Returns the layout seo site entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOSiteEntryId the primary key of the layout seo site entry
	 * @return the layout seo site entry, or <code>null</code> if a layout seo site entry with the primary key could not be found
	 */
	public static LayoutSEOSiteEntry fetchByPrimaryKey(
		long layoutSEOSiteEntryId) {

		return getPersistence().fetchByPrimaryKey(layoutSEOSiteEntryId);
	}

	/**
	 * Returns all the layout seo site entries.
	 *
	 * @return the layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @return the range of layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo site entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo site entries
	 * @param end the upper bound of the range of layout seo site entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo site entries
	 */
	public static List<LayoutSEOSiteEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOSiteEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout seo site entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout seo site entries.
	 *
	 * @return the number of layout seo site entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSEOSiteEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOSiteEntryPersistence, LayoutSEOSiteEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutSEOSiteEntryPersistence.class);

		ServiceTracker
			<LayoutSEOSiteEntryPersistence, LayoutSEOSiteEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<LayoutSEOSiteEntryPersistence,
						 LayoutSEOSiteEntryPersistence>(
							 bundle.getBundleContext(),
							 LayoutSEOSiteEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}