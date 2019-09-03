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

import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the layout seo canonical url service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.LayoutSEOCanonicalURLPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOCanonicalURLPersistence
 * @generated
 */
@ProviderType
public class LayoutSEOCanonicalURLUtil {

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
	public static void clearCache(LayoutSEOCanonicalURL layoutSEOCanonicalURL) {
		getPersistence().clearCache(layoutSEOCanonicalURL);
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
	public static Map<Serializable, LayoutSEOCanonicalURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSEOCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSEOCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSEOCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSEOCanonicalURL update(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

		return getPersistence().update(layoutSEOCanonicalURL);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSEOCanonicalURL update(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL,
		ServiceContext serviceContext) {

		return getPersistence().update(layoutSEOCanonicalURL, serviceContext);
	}

	/**
	 * Returns all the layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public static LayoutSEOCanonicalURL[] findByUuid_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutSEOCanonicalURLId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo canonical urls
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout seo canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout seo canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo canonical url that was removed
	 */
	public static LayoutSEOCanonicalURL removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo canonical urls
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout seo canonical urls before and after the current layout seo canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the current layout seo canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public static LayoutSEOCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutSEOCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutSEOCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutSEOCanonicalURLId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout seo canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout seo canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo canonical urls
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		return getPersistence().fetchByG_P_L(
			groupId, privateLayout, layoutId, useFinderCache);
	}

	/**
	 * Removes the layout seo canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo canonical url that was removed
	 */
	public static LayoutSEOCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the number of layout seo canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo canonical urls
	 */
	public static int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Caches the layout seo canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 */
	public static void cacheResult(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

		getPersistence().cacheResult(layoutSEOCanonicalURL);
	}

	/**
	 * Caches the layout seo canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutSEOCanonicalURLs the layout seo canonical urls
	 */
	public static void cacheResult(
		List<LayoutSEOCanonicalURL> layoutSEOCanonicalURLs) {

		getPersistence().cacheResult(layoutSEOCanonicalURLs);
	}

	/**
	 * Creates a new layout seo canonical url with the primary key. Does not add the layout seo canonical url to the database.
	 *
	 * @param layoutSEOCanonicalURLId the primary key for the new layout seo canonical url
	 * @return the new layout seo canonical url
	 */
	public static LayoutSEOCanonicalURL create(long layoutSEOCanonicalURLId) {
		return getPersistence().create(layoutSEOCanonicalURLId);
	}

	/**
	 * Removes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public static LayoutSEOCanonicalURL remove(long layoutSEOCanonicalURLId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().remove(layoutSEOCanonicalURLId);
	}

	public static LayoutSEOCanonicalURL updateImpl(
		LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

		return getPersistence().updateImpl(layoutSEOCanonicalURL);
	}

	/**
	 * Returns the layout seo canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws NoSuchCanonicalURLException if a layout seo canonical url with the primary key could not be found
	 */
	public static LayoutSEOCanonicalURL findByPrimaryKey(
			long layoutSEOCanonicalURLId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByPrimaryKey(layoutSEOCanonicalURLId);
	}

	/**
	 * Returns the layout seo canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url, or <code>null</code> if a layout seo canonical url with the primary key could not be found
	 */
	public static LayoutSEOCanonicalURL fetchByPrimaryKey(
		long layoutSEOCanonicalURLId) {

		return getPersistence().fetchByPrimaryKey(layoutSEOCanonicalURLId);
	}

	/**
	 * Returns all the layout seo canonical urls.
	 *
	 * @return the layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo canonical urls
	 */
	public static List<LayoutSEOCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout seo canonical urls from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout seo canonical urls.
	 *
	 * @return the number of layout seo canonical urls
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSEOCanonicalURLPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOCanonicalURLPersistence, LayoutSEOCanonicalURLPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutSEOCanonicalURLPersistence.class);

		ServiceTracker
			<LayoutSEOCanonicalURLPersistence, LayoutSEOCanonicalURLPersistence>
				serviceTracker =
					new ServiceTracker
						<LayoutSEOCanonicalURLPersistence,
						 LayoutSEOCanonicalURLPersistence>(
							 bundle.getBundleContext(),
							 LayoutSEOCanonicalURLPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}