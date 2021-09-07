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

import com.liferay.object.model.ObjectLayoutTab;
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
 * The persistence utility for the object layout tab service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectLayoutTabPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutTabPersistence
 * @generated
 */
public class ObjectLayoutTabUtil {

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
	public static void clearCache(ObjectLayoutTab objectLayoutTab) {
		getPersistence().clearCache(objectLayoutTab);
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
	public static Map<Serializable, ObjectLayoutTab> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectLayoutTab> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectLayoutTab> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectLayoutTab> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectLayoutTab update(ObjectLayoutTab objectLayoutTab) {
		return getPersistence().update(objectLayoutTab);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectLayoutTab update(
		ObjectLayoutTab objectLayoutTab, ServiceContext serviceContext) {

		return getPersistence().update(objectLayoutTab, serviceContext);
	}

	/**
	 * Returns all the object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab[] findByUuid_PrevAndNext(
			long objectLayoutTabId, String uuid,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_PrevAndNext(
			objectLayoutTabId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object layout tabs where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout tabs
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab[] findByUuid_C_PrevAndNext(
			long objectLayoutTabId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectLayoutTabId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object layout tabs where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout tabs
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId) {

		return getPersistence().findByObjectLayoutId(objectLayoutId);
	}

	/**
	 * Returns a range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end) {

		return getPersistence().findByObjectLayoutId(
			objectLayoutId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().findByObjectLayoutId(
			objectLayoutId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public static List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectLayoutId(
			objectLayoutId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByObjectLayoutId_First(
			long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByObjectLayoutId_First(
			objectLayoutId, orderByComparator);
	}

	/**
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByObjectLayoutId_First(
		long objectLayoutId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByObjectLayoutId_First(
			objectLayoutId, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab findByObjectLayoutId_Last(
			long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByObjectLayoutId_Last(
			objectLayoutId, orderByComparator);
	}

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public static ObjectLayoutTab fetchByObjectLayoutId_Last(
		long objectLayoutId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().fetchByObjectLayoutId_Last(
			objectLayoutId, orderByComparator);
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab[] findByObjectLayoutId_PrevAndNext(
			long objectLayoutTabId, long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByObjectLayoutId_PrevAndNext(
			objectLayoutTabId, objectLayoutId, orderByComparator);
	}

	/**
	 * Removes all the object layout tabs where objectLayoutId = &#63; from the database.
	 *
	 * @param objectLayoutId the object layout ID
	 */
	public static void removeByObjectLayoutId(long objectLayoutId) {
		getPersistence().removeByObjectLayoutId(objectLayoutId);
	}

	/**
	 * Returns the number of object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the number of matching object layout tabs
	 */
	public static int countByObjectLayoutId(long objectLayoutId) {
		return getPersistence().countByObjectLayoutId(objectLayoutId);
	}

	/**
	 * Caches the object layout tab in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTab the object layout tab
	 */
	public static void cacheResult(ObjectLayoutTab objectLayoutTab) {
		getPersistence().cacheResult(objectLayoutTab);
	}

	/**
	 * Caches the object layout tabs in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTabs the object layout tabs
	 */
	public static void cacheResult(List<ObjectLayoutTab> objectLayoutTabs) {
		getPersistence().cacheResult(objectLayoutTabs);
	}

	/**
	 * Creates a new object layout tab with the primary key. Does not add the object layout tab to the database.
	 *
	 * @param objectLayoutTabId the primary key for the new object layout tab
	 * @return the new object layout tab
	 */
	public static ObjectLayoutTab create(long objectLayoutTabId) {
		return getPersistence().create(objectLayoutTabId);
	}

	/**
	 * Removes the object layout tab with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab that was removed
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab remove(long objectLayoutTabId)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().remove(objectLayoutTabId);
	}

	public static ObjectLayoutTab updateImpl(ObjectLayoutTab objectLayoutTab) {
		return getPersistence().updateImpl(objectLayoutTab);
	}

	/**
	 * Returns the object layout tab with the primary key or throws a <code>NoSuchObjectLayoutTabException</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab findByPrimaryKey(long objectLayoutTabId)
		throws com.liferay.object.exception.NoSuchObjectLayoutTabException {

		return getPersistence().findByPrimaryKey(objectLayoutTabId);
	}

	/**
	 * Returns the object layout tab with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab, or <code>null</code> if a object layout tab with the primary key could not be found
	 */
	public static ObjectLayoutTab fetchByPrimaryKey(long objectLayoutTabId) {
		return getPersistence().fetchByPrimaryKey(objectLayoutTabId);
	}

	/**
	 * Returns all the object layout tabs.
	 *
	 * @return the object layout tabs
	 */
	public static List<ObjectLayoutTab> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of object layout tabs
	 */
	public static List<ObjectLayoutTab> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout tabs
	 */
	public static List<ObjectLayoutTab> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout tabs
	 */
	public static List<ObjectLayoutTab> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object layout tabs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object layout tabs.
	 *
	 * @return the number of object layout tabs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectLayoutTabPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ObjectLayoutTabPersistence, ObjectLayoutTabPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectLayoutTabPersistence.class);

		ServiceTracker<ObjectLayoutTabPersistence, ObjectLayoutTabPersistence>
			serviceTracker =
				new ServiceTracker
					<ObjectLayoutTabPersistence, ObjectLayoutTabPersistence>(
						bundle.getBundleContext(),
						ObjectLayoutTabPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}