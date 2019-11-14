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

import com.liferay.layout.seo.model.LayoutSEOSite;
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
 * The persistence utility for the layout seo site service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.LayoutSEOSitePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSitePersistence
 * @generated
 */
public class LayoutSEOSiteUtil {

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
	public static void clearCache(LayoutSEOSite layoutSEOSite) {
		getPersistence().clearCache(layoutSEOSite);
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
	public static Map<Serializable, LayoutSEOSite> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSEOSite> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSEOSite> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSEOSite> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSEOSite update(LayoutSEOSite layoutSEOSite) {
		return getPersistence().update(layoutSEOSite);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSEOSite update(
		LayoutSEOSite layoutSEOSite, ServiceContext serviceContext) {

		return getPersistence().update(layoutSEOSite, serviceContext);
	}

	/**
	 * Returns all the layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByUuid_First(
			String uuid, OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUuid_First(
		String uuid, OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByUuid_Last(
			String uuid, OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public static LayoutSEOSite[] findByUuid_PrevAndNext(
			long layoutSEOSiteId, String uuid,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutSEOSiteId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout seo sites where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo sites
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo site where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout seo site where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	public static LayoutSEOSite removeByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo sites
	 */
	public static List<LayoutSEOSite> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout seo sites before and after the current layout seo site in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOSiteId the primary key of the current layout seo site
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public static LayoutSEOSite[] findByUuid_C_PrevAndNext(
			long layoutSEOSiteId, String uuid, long companyId,
			OrderByComparator<LayoutSEOSite> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutSEOSiteId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout seo sites where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout seo sites where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo sites
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the layout seo site where groupId = &#63; or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site
	 * @throws NoSuchSiteException if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite findByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByGroupId(long groupId) {
		return getPersistence().fetchByGroupId(groupId);
	}

	/**
	 * Returns the layout seo site where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static LayoutSEOSite fetchByGroupId(
		long groupId, boolean useFinderCache) {

		return getPersistence().fetchByGroupId(groupId, useFinderCache);
	}

	/**
	 * Removes the layout seo site where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the layout seo site that was removed
	 */
	public static LayoutSEOSite removeByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of layout seo sites where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout seo sites
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Caches the layout seo site in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSite the layout seo site
	 */
	public static void cacheResult(LayoutSEOSite layoutSEOSite) {
		getPersistence().cacheResult(layoutSEOSite);
	}

	/**
	 * Caches the layout seo sites in the entity cache if it is enabled.
	 *
	 * @param layoutSEOSites the layout seo sites
	 */
	public static void cacheResult(List<LayoutSEOSite> layoutSEOSites) {
		getPersistence().cacheResult(layoutSEOSites);
	}

	/**
	 * Creates a new layout seo site with the primary key. Does not add the layout seo site to the database.
	 *
	 * @param layoutSEOSiteId the primary key for the new layout seo site
	 * @return the new layout seo site
	 */
	public static LayoutSEOSite create(long layoutSEOSiteId) {
		return getPersistence().create(layoutSEOSiteId);
	}

	/**
	 * Removes the layout seo site with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site that was removed
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public static LayoutSEOSite remove(long layoutSEOSiteId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().remove(layoutSEOSiteId);
	}

	public static LayoutSEOSite updateImpl(LayoutSEOSite layoutSEOSite) {
		return getPersistence().updateImpl(layoutSEOSite);
	}

	/**
	 * Returns the layout seo site with the primary key or throws a <code>NoSuchSiteException</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site
	 * @throws NoSuchSiteException if a layout seo site with the primary key could not be found
	 */
	public static LayoutSEOSite findByPrimaryKey(long layoutSEOSiteId)
		throws com.liferay.layout.seo.exception.NoSuchSiteException {

		return getPersistence().findByPrimaryKey(layoutSEOSiteId);
	}

	/**
	 * Returns the layout seo site with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site, or <code>null</code> if a layout seo site with the primary key could not be found
	 */
	public static LayoutSEOSite fetchByPrimaryKey(long layoutSEOSiteId) {
		return getPersistence().fetchByPrimaryKey(layoutSEOSiteId);
	}

	/**
	 * Returns all the layout seo sites.
	 *
	 * @return the layout seo sites
	 */
	public static List<LayoutSEOSite> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of layout seo sites
	 */
	public static List<LayoutSEOSite> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo sites
	 */
	public static List<LayoutSEOSite> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOSite> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo sites
	 */
	public static List<LayoutSEOSite> findAll(
		int start, int end, OrderByComparator<LayoutSEOSite> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout seo sites from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout seo sites.
	 *
	 * @return the number of layout seo sites
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSEOSitePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOSitePersistence, LayoutSEOSitePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LayoutSEOSitePersistence.class);

		ServiceTracker<LayoutSEOSitePersistence, LayoutSEOSitePersistence>
			serviceTracker =
				new ServiceTracker
					<LayoutSEOSitePersistence, LayoutSEOSitePersistence>(
						bundle.getBundleContext(),
						LayoutSEOSitePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}