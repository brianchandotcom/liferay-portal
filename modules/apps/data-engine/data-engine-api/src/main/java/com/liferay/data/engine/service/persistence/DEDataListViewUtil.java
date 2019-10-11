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

package com.liferay.data.engine.service.persistence;

import com.liferay.data.engine.model.DEDataListView;
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
 * The persistence utility for the de data list view service. This utility wraps <code>com.liferay.data.engine.service.persistence.impl.DEDataListViewPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListViewPersistence
 * @generated
 */
public class DEDataListViewUtil {

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
	public static void clearCache(DEDataListView deDataListView) {
		getPersistence().clearCache(deDataListView);
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
	public static Map<Serializable, DEDataListView> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DEDataListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DEDataListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DEDataListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DEDataListView update(DEDataListView deDataListView) {
		return getPersistence().update(deDataListView);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DEDataListView update(
		DEDataListView deDataListView, ServiceContext serviceContext) {

		return getPersistence().update(deDataListView, serviceContext);
	}

	/**
	 * Returns all the de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data list views
	 */
	public static List<DEDataListView> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByUuid_First(
			String uuid, OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUuid_First(
		String uuid, OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByUuid_Last(
			String uuid, OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUuid_Last(
		String uuid, OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public static DEDataListView[] findByUuid_PrevAndNext(
			long deDataListViewId, String uuid,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_PrevAndNext(
			deDataListViewId, uuid, orderByComparator);
	}

	/**
	 * Removes all the de data list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data list views
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByUUID_G(String uuid, long groupId)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the de data list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de data list view that was removed
	 */
	public static DEDataListView removeByUUID_G(String uuid, long groupId)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of de data list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de data list views
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de data list views
	 */
	public static List<DEDataListView> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public static DEDataListView[] findByUuid_C_PrevAndNext(
			long deDataListViewId, String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByUuid_C_PrevAndNext(
			deDataListViewId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the de data list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de data list views
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data list views
	 */
	public static List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return getPersistence().findByG_C_D(groupId, companyId, ddmStructureId);
	}

	/**
	 * Returns a range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public static List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end);
	}

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public static List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByG_C_D_First(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByG_C_D_First(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByG_C_D_First(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByG_C_D_First(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public static DEDataListView findByG_C_D_Last(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByG_C_D_Last(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public static DEDataListView fetchByG_C_D_Last(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().fetchByG_C_D_Last(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public static DEDataListView[] findByG_C_D_PrevAndNext(
			long deDataListViewId, long groupId, long companyId,
			long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByG_C_D_PrevAndNext(
			deDataListViewId, groupId, companyId, ddmStructureId,
			orderByComparator);
	}

	/**
	 * Removes all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	public static void removeByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		getPersistence().removeByG_C_D(groupId, companyId, ddmStructureId);
	}

	/**
	 * Returns the number of de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data list views
	 */
	public static int countByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return getPersistence().countByG_C_D(
			groupId, companyId, ddmStructureId);
	}

	/**
	 * Caches the de data list view in the entity cache if it is enabled.
	 *
	 * @param deDataListView the de data list view
	 */
	public static void cacheResult(DEDataListView deDataListView) {
		getPersistence().cacheResult(deDataListView);
	}

	/**
	 * Caches the de data list views in the entity cache if it is enabled.
	 *
	 * @param deDataListViews the de data list views
	 */
	public static void cacheResult(List<DEDataListView> deDataListViews) {
		getPersistence().cacheResult(deDataListViews);
	}

	/**
	 * Creates a new de data list view with the primary key. Does not add the de data list view to the database.
	 *
	 * @param deDataListViewId the primary key for the new de data list view
	 * @return the new de data list view
	 */
	public static DEDataListView create(long deDataListViewId) {
		return getPersistence().create(deDataListViewId);
	}

	/**
	 * Removes the de data list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view that was removed
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public static DEDataListView remove(long deDataListViewId)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().remove(deDataListViewId);
	}

	public static DEDataListView updateImpl(DEDataListView deDataListView) {
		return getPersistence().updateImpl(deDataListView);
	}

	/**
	 * Returns the de data list view with the primary key or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public static DEDataListView findByPrimaryKey(long deDataListViewId)
		throws com.liferay.data.engine.exception.NoSuchDataListViewException {

		return getPersistence().findByPrimaryKey(deDataListViewId);
	}

	/**
	 * Returns the de data list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view, or <code>null</code> if a de data list view with the primary key could not be found
	 */
	public static DEDataListView fetchByPrimaryKey(long deDataListViewId) {
		return getPersistence().fetchByPrimaryKey(deDataListViewId);
	}

	/**
	 * Returns all the de data list views.
	 *
	 * @return the de data list views
	 */
	public static List<DEDataListView> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of de data list views
	 */
	public static List<DEDataListView> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data list views
	 */
	public static List<DEDataListView> findAll(
		int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of de data list views
	 */
	public static List<DEDataListView> findAll(
		int start, int end, OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the de data list views from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of de data list views.
	 *
	 * @return the number of de data list views
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DEDataListViewPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DEDataListViewPersistence, DEDataListViewPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DEDataListViewPersistence.class);

		ServiceTracker<DEDataListViewPersistence, DEDataListViewPersistence>
			serviceTracker =
				new ServiceTracker
					<DEDataListViewPersistence, DEDataListViewPersistence>(
						bundle.getBundleContext(),
						DEDataListViewPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}