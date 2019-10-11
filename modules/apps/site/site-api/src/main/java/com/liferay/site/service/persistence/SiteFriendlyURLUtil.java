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

package com.liferay.site.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.model.SiteFriendlyURL;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the site friendly url service. This utility wraps <code>com.liferay.site.service.persistence.impl.SiteFriendlyURLPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLPersistence
 * @generated
 */
public class SiteFriendlyURLUtil {

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
	public static void clearCache(SiteFriendlyURL siteFriendlyURL) {
		getPersistence().clearCache(siteFriendlyURL);
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
	public static Map<Serializable, SiteFriendlyURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SiteFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SiteFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SiteFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SiteFriendlyURL update(SiteFriendlyURL siteFriendlyURL) {
		return getPersistence().update(siteFriendlyURL);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SiteFriendlyURL update(
		SiteFriendlyURL siteFriendlyURL, ServiceContext serviceContext) {

		return getPersistence().update(siteFriendlyURL, serviceContext);
	}

	/**
	 * Returns all the site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByUuid_First(
			String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUuid_First(
		String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByUuid_Last(
			String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUuid_Last(
		String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL[] findByUuid_PrevAndNext(
			long siteFriendlyURLId, String uuid,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_PrevAndNext(
			siteFriendlyURLId, uuid, orderByComparator);
	}

	/**
	 * Removes all the site friendly urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site friendly urls
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByUUID_G(String uuid, long groupId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the site friendly url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site friendly url that was removed
	 */
	public static SiteFriendlyURL removeByUUID_G(String uuid, long groupId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site friendly urls
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL[] findByUuid_C_PrevAndNext(
			long siteFriendlyURLId, String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByUuid_C_PrevAndNext(
			siteFriendlyURLId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the site friendly urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site friendly urls
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByC_G(
		long companyId, long groupId) {

		return getPersistence().findByC_G(companyId, groupId);
	}

	/**
	 * Returns a range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByC_G(
		long companyId, long groupId, int start, int end) {

		return getPersistence().findByC_G(companyId, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().findByC_G(
			companyId, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	public static List<SiteFriendlyURL> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_G(
			companyId, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByC_G_First(
			long companyId, long groupId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_G_First(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the first site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_G_First(
		long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByC_G_First(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByC_G_Last(
			long companyId, long groupId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_G_Last(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the last site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_G_Last(
		long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().fetchByC_G_Last(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL[] findByC_G_PrevAndNext(
			long siteFriendlyURLId, long companyId, long groupId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_G_PrevAndNext(
			siteFriendlyURLId, companyId, groupId, orderByComparator);
	}

	/**
	 * Removes all the site friendly urls where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	public static void removeByC_G(long companyId, long groupId) {
		getPersistence().removeByC_G(companyId, groupId);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching site friendly urls
	 */
	public static int countByC_G(long companyId, long groupId) {
		return getPersistence().countByC_G(companyId, groupId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByC_F(long companyId, String friendlyURL)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_F(
		long companyId, String friendlyURL) {

		return getPersistence().fetchByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_F(
		long companyId, String friendlyURL, boolean useFinderCache) {

		return getPersistence().fetchByC_F(
			companyId, friendlyURL, useFinderCache);
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the site friendly url that was removed
	 */
	public static SiteFriendlyURL removeByC_F(
			long companyId, String friendlyURL)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().removeByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the number of matching site friendly urls
	 */
	public static int countByC_F(long companyId, String friendlyURL) {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByC_G_L(
			long companyId, long groupId, String languageId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_G_L(companyId, groupId, languageId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_G_L(
		long companyId, long groupId, String languageId) {

		return getPersistence().fetchByC_G_L(companyId, groupId, languageId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_G_L(
		long companyId, long groupId, String languageId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_G_L(
			companyId, groupId, languageId, useFinderCache);
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	public static SiteFriendlyURL removeByC_G_L(
			long companyId, long groupId, String languageId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().removeByC_G_L(companyId, groupId, languageId);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and groupId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	public static int countByC_G_L(
		long companyId, long groupId, String languageId) {

		return getPersistence().countByC_G_L(companyId, groupId, languageId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL findByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByC_F_L(companyId, friendlyURL, languageId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId) {

		return getPersistence().fetchByC_F_L(
			companyId, friendlyURL, languageId);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	public static SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_F_L(
			companyId, friendlyURL, languageId, useFinderCache);
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	public static SiteFriendlyURL removeByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().removeByC_F_L(
			companyId, friendlyURL, languageId);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	public static int countByC_F_L(
		long companyId, String friendlyURL, String languageId) {

		return getPersistence().countByC_F_L(
			companyId, friendlyURL, languageId);
	}

	/**
	 * Caches the site friendly url in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURL the site friendly url
	 */
	public static void cacheResult(SiteFriendlyURL siteFriendlyURL) {
		getPersistence().cacheResult(siteFriendlyURL);
	}

	/**
	 * Caches the site friendly urls in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURLs the site friendly urls
	 */
	public static void cacheResult(List<SiteFriendlyURL> siteFriendlyURLs) {
		getPersistence().cacheResult(siteFriendlyURLs);
	}

	/**
	 * Creates a new site friendly url with the primary key. Does not add the site friendly url to the database.
	 *
	 * @param siteFriendlyURLId the primary key for the new site friendly url
	 * @return the new site friendly url
	 */
	public static SiteFriendlyURL create(long siteFriendlyURLId) {
		return getPersistence().create(siteFriendlyURLId);
	}

	/**
	 * Removes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL remove(long siteFriendlyURLId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().remove(siteFriendlyURLId);
	}

	public static SiteFriendlyURL updateImpl(SiteFriendlyURL siteFriendlyURL) {
		return getPersistence().updateImpl(siteFriendlyURL);
	}

	/**
	 * Returns the site friendly url with the primary key or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL findByPrimaryKey(long siteFriendlyURLId)
		throws com.liferay.site.exception.NoSuchFriendlyURLException {

		return getPersistence().findByPrimaryKey(siteFriendlyURLId);
	}

	/**
	 * Returns the site friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url, or <code>null</code> if a site friendly url with the primary key could not be found
	 */
	public static SiteFriendlyURL fetchByPrimaryKey(long siteFriendlyURLId) {
		return getPersistence().fetchByPrimaryKey(siteFriendlyURLId);
	}

	/**
	 * Returns all the site friendly urls.
	 *
	 * @return the site friendly urls
	 */
	public static List<SiteFriendlyURL> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of site friendly urls
	 */
	public static List<SiteFriendlyURL> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site friendly urls
	 */
	public static List<SiteFriendlyURL> findAll(
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site friendly urls
	 */
	public static List<SiteFriendlyURL> findAll(
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the site friendly urls from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of site friendly urls.
	 *
	 * @return the number of site friendly urls
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SiteFriendlyURLPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SiteFriendlyURLPersistence, SiteFriendlyURLPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SiteFriendlyURLPersistence.class);

		ServiceTracker<SiteFriendlyURLPersistence, SiteFriendlyURLPersistence>
			serviceTracker =
				new ServiceTracker
					<SiteFriendlyURLPersistence, SiteFriendlyURLPersistence>(
						bundle.getBundleContext(),
						SiteFriendlyURLPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}