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

import com.liferay.layout.seo.model.SiteSEOEntry;
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
 * The persistence utility for the site seo entry service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.SiteSEOEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryPersistence
 * @generated
 */
public class SiteSEOEntryUtil {

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
	public static void clearCache(SiteSEOEntry siteSEOEntry) {
		getPersistence().clearCache(siteSEOEntry);
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
	public static Map<Serializable, SiteSEOEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SiteSEOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SiteSEOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SiteSEOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SiteSEOEntry update(SiteSEOEntry siteSEOEntry) {
		return getPersistence().update(siteSEOEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SiteSEOEntry update(
		SiteSEOEntry siteSEOEntry, ServiceContext serviceContext) {

		return getPersistence().update(siteSEOEntry, serviceContext);
	}

	/**
	 * Returns all the site seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByUuid_First(
			String uuid, OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUuid_First(
		String uuid, OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByUuid_Last(
			String uuid, OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the site seo entries before and after the current site seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param siteSEOEntryId the primary key of the current site seo entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public static SiteSEOEntry[] findByUuid_PrevAndNext(
			long siteSEOEntryId, String uuid,
			OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			siteSEOEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the site seo entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of site seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site seo entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the site seo entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site seo entry that was removed
	 */
	public static SiteSEOEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of site seo entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site seo entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site seo entries
	 */
	public static List<SiteSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteSEOEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the site seo entries before and after the current site seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteSEOEntryId the primary key of the current site seo entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public static SiteSEOEntry[] findByUuid_C_PrevAndNext(
			long siteSEOEntryId, String uuid, long companyId,
			OrderByComparator<SiteSEOEntry> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			siteSEOEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the site seo entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of site seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site seo entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the site seo entry where groupId = &#63; or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @return the matching site seo entry
	 * @throws NoSuchSiteSEOEntryException if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry findByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns the site seo entry where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByGroupId(long groupId) {
		return getPersistence().fetchByGroupId(groupId);
	}

	/**
	 * Returns the site seo entry where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static SiteSEOEntry fetchByGroupId(
		long groupId, boolean useFinderCache) {

		return getPersistence().fetchByGroupId(groupId, useFinderCache);
	}

	/**
	 * Removes the site seo entry where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @return the site seo entry that was removed
	 */
	public static SiteSEOEntry removeByGroupId(long groupId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of site seo entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site seo entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Caches the site seo entry in the entity cache if it is enabled.
	 *
	 * @param siteSEOEntry the site seo entry
	 */
	public static void cacheResult(SiteSEOEntry siteSEOEntry) {
		getPersistence().cacheResult(siteSEOEntry);
	}

	/**
	 * Caches the site seo entries in the entity cache if it is enabled.
	 *
	 * @param siteSEOEntries the site seo entries
	 */
	public static void cacheResult(List<SiteSEOEntry> siteSEOEntries) {
		getPersistence().cacheResult(siteSEOEntries);
	}

	/**
	 * Creates a new site seo entry with the primary key. Does not add the site seo entry to the database.
	 *
	 * @param siteSEOEntryId the primary key for the new site seo entry
	 * @return the new site seo entry
	 */
	public static SiteSEOEntry create(long siteSEOEntryId) {
		return getPersistence().create(siteSEOEntryId);
	}

	/**
	 * Removes the site seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry that was removed
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public static SiteSEOEntry remove(long siteSEOEntryId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().remove(siteSEOEntryId);
	}

	public static SiteSEOEntry updateImpl(SiteSEOEntry siteSEOEntry) {
		return getPersistence().updateImpl(siteSEOEntry);
	}

	/**
	 * Returns the site seo entry with the primary key or throws a <code>NoSuchSiteSEOEntryException</code> if it could not be found.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry
	 * @throws NoSuchSiteSEOEntryException if a site seo entry with the primary key could not be found
	 */
	public static SiteSEOEntry findByPrimaryKey(long siteSEOEntryId)
		throws com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException {

		return getPersistence().findByPrimaryKey(siteSEOEntryId);
	}

	/**
	 * Returns the site seo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry, or <code>null</code> if a site seo entry with the primary key could not be found
	 */
	public static SiteSEOEntry fetchByPrimaryKey(long siteSEOEntryId) {
		return getPersistence().fetchByPrimaryKey(siteSEOEntryId);
	}

	/**
	 * Returns all the site seo entries.
	 *
	 * @return the site seo entries
	 */
	public static List<SiteSEOEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of site seo entries
	 */
	public static List<SiteSEOEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site seo entries
	 */
	public static List<SiteSEOEntry> findAll(
		int start, int end, OrderByComparator<SiteSEOEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site seo entries
	 */
	public static List<SiteSEOEntry> findAll(
		int start, int end, OrderByComparator<SiteSEOEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the site seo entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of site seo entries.
	 *
	 * @return the number of site seo entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SiteSEOEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SiteSEOEntryPersistence, SiteSEOEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteSEOEntryPersistence.class);

		ServiceTracker<SiteSEOEntryPersistence, SiteSEOEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<SiteSEOEntryPersistence, SiteSEOEntryPersistence>(
						bundle.getBundleContext(),
						SiteSEOEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}