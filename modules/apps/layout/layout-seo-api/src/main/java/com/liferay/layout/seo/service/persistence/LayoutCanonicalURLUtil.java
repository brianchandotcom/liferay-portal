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

import com.liferay.layout.seo.model.LayoutCanonicalURL;
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
 * The persistence utility for the layout canonical url service. This utility wraps <code>com.liferay.layout.seo.service.persistence.impl.LayoutCanonicalURLPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCanonicalURLPersistence
 * @generated
 */
@ProviderType
public class LayoutCanonicalURLUtil {

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
	public static void clearCache(LayoutCanonicalURL layoutCanonicalURL) {
		getPersistence().clearCache(layoutCanonicalURL);
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
	public static Map<Serializable, LayoutCanonicalURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutCanonicalURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutCanonicalURL update(
		LayoutCanonicalURL layoutCanonicalURL) {

		return getPersistence().update(layoutCanonicalURL);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutCanonicalURL update(
		LayoutCanonicalURL layoutCanonicalURL, ServiceContext serviceContext) {

		return getPersistence().update(layoutCanonicalURL, serviceContext);
	}

	/**
	 * Returns all the layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid(String, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	public static List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByUuid_First(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByUuid_First(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public static LayoutCanonicalURL[] findByUuid_PrevAndNext(
			long layoutCanonicalURLId, String uuid,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutCanonicalURLId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout canonical urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout canonical urls
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByUUID_G(String,long)}
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	public static LayoutCanonicalURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Returns the layout canonical url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Removes the layout canonical url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout canonical url that was removed
	 */
	public static LayoutCanonicalURL removeByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout canonical urls
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findByUuid_C(String,long, int, int, OrderByComparator)}
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout canonical urls
	 */
	@Deprecated
	public static List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout canonical urls before and after the current layout canonical url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutCanonicalURLId the primary key of the current layout canonical url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public static LayoutCanonicalURL[] findByUuid_C_PrevAndNext(
			long layoutCanonicalURLId, String uuid, long companyId,
			OrderByComparator<LayoutCanonicalURL> orderByComparator)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutCanonicalURLId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout canonical urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout canonical urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout canonical urls
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout canonical url
	 * @throws NoSuchCanonicalURLException if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #fetchByG_P_L(long,boolean,long)}
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Deprecated
	public static LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		return getPersistence().fetchByG_P_L(
			groupId, privateLayout, layoutId, useFinderCache);
	}

	/**
	 * Returns the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static LayoutCanonicalURL fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Removes the layout canonical url where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout canonical url that was removed
	 */
	public static LayoutCanonicalURL removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the number of layout canonical urls where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout canonical urls
	 */
	public static int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	 * Caches the layout canonical url in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 */
	public static void cacheResult(LayoutCanonicalURL layoutCanonicalURL) {
		getPersistence().cacheResult(layoutCanonicalURL);
	}

	/**
	 * Caches the layout canonical urls in the entity cache if it is enabled.
	 *
	 * @param layoutCanonicalURLs the layout canonical urls
	 */
	public static void cacheResult(
		List<LayoutCanonicalURL> layoutCanonicalURLs) {

		getPersistence().cacheResult(layoutCanonicalURLs);
	}

	/**
	 * Creates a new layout canonical url with the primary key. Does not add the layout canonical url to the database.
	 *
	 * @param layoutCanonicalURLId the primary key for the new layout canonical url
	 * @return the new layout canonical url
	 */
	public static LayoutCanonicalURL create(long layoutCanonicalURLId) {
		return getPersistence().create(layoutCanonicalURLId);
	}

	/**
	 * Removes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public static LayoutCanonicalURL remove(long layoutCanonicalURLId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().remove(layoutCanonicalURLId);
	}

	public static LayoutCanonicalURL updateImpl(
		LayoutCanonicalURL layoutCanonicalURL) {

		return getPersistence().updateImpl(layoutCanonicalURL);
	}

	/**
	 * Returns the layout canonical url with the primary key or throws a <code>NoSuchCanonicalURLException</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws NoSuchCanonicalURLException if a layout canonical url with the primary key could not be found
	 */
	public static LayoutCanonicalURL findByPrimaryKey(long layoutCanonicalURLId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		return getPersistence().findByPrimaryKey(layoutCanonicalURLId);
	}

	/**
	 * Returns the layout canonical url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url, or <code>null</code> if a layout canonical url with the primary key could not be found
	 */
	public static LayoutCanonicalURL fetchByPrimaryKey(
		long layoutCanonicalURLId) {

		return getPersistence().fetchByPrimaryKey(layoutCanonicalURLId);
	}

	/**
	 * Returns all the layout canonical urls.
	 *
	 * @return the layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #findAll(int, int, OrderByComparator)}
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout canonical urls
	 */
	@Deprecated
	public static List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns an ordered range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout canonical urls
	 */
	public static List<LayoutCanonicalURL> findAll(
		int start, int end,
		OrderByComparator<LayoutCanonicalURL> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Removes all the layout canonical urls from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout canonical urls.
	 *
	 * @return the number of layout canonical urls
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutCanonicalURLPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutCanonicalURLPersistence, LayoutCanonicalURLPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutCanonicalURLPersistence.class);

		ServiceTracker
			<LayoutCanonicalURLPersistence, LayoutCanonicalURLPersistence>
				serviceTracker =
					new ServiceTracker
						<LayoutCanonicalURLPersistence,
						 LayoutCanonicalURLPersistence>(
							 bundle.getBundleContext(),
							 LayoutCanonicalURLPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}