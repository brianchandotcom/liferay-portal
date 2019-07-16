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

import com.liferay.data.engine.model.DEListView;
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
 * The persistence utility for the de list view service. This utility wraps <code>com.liferay.data.engine.service.persistence.impl.DEListViewPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEListViewPersistence
 * @generated
 */
@ProviderType
public class DEListViewUtil {

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
	public static void clearCache(DEListView deListView) {
		getPersistence().clearCache(deListView);
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
	public static Map<Serializable, DEListView> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DEListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DEListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DEListView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DEListView update(DEListView deListView) {
		return getPersistence().update(deListView);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DEListView update(
		DEListView deListView, ServiceContext serviceContext) {

		return getPersistence().update(deListView, serviceContext);
	}

	/**
	 * Returns all the de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de list views
	 */
	public static List<DEListView> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public static List<DEListView> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByUuid_First(
			String uuid, OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUuid_First(
		String uuid, OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByUuid_Last(
			String uuid, OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUuid_Last(
		String uuid, OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public static DEListView[] findByUuid_PrevAndNext(
			long deListViewId, String uuid,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_PrevAndNext(
			deListViewId, uuid, orderByComparator);
	}

	/**
	 * Removes all the de list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de list views
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByUUID_G(String uuid, long groupId)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	 * Removes the de list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de list view that was removed
	 */
	public static DEListView removeByUUID_G(String uuid, long groupId)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of de list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de list views
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de list views
	 */
	public static List<DEListView> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public static List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public static DEListView[] findByUuid_C_PrevAndNext(
			long deListViewId, String uuid, long companyId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByUuid_C_PrevAndNext(
			deListViewId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the de list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de list views
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching de list views
	 */
	public static List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId) {

		return getPersistence().findByG_C_D(groupId, companyId, DDMStructureId);
	}

	/**
	 * Returns a range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public static List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end) {

		return getPersistence().findByG_C_D(
			groupId, companyId, DDMStructureId, start, end);
	}

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().findByG_C_D(
			groupId, companyId, DDMStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public static List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByG_C_D(
			groupId, companyId, DDMStructureId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByG_C_D_First(
			long groupId, long companyId, long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByG_C_D_First(
			groupId, companyId, DDMStructureId, orderByComparator);
	}

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByG_C_D_First(
		long groupId, long companyId, long DDMStructureId,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByG_C_D_First(
			groupId, companyId, DDMStructureId, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public static DEListView findByG_C_D_Last(
			long groupId, long companyId, long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByG_C_D_Last(
			groupId, companyId, DDMStructureId, orderByComparator);
	}

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public static DEListView fetchByG_C_D_Last(
		long groupId, long companyId, long DDMStructureId,
		OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().fetchByG_C_D_Last(
			groupId, companyId, DDMStructureId, orderByComparator);
	}

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public static DEListView[] findByG_C_D_PrevAndNext(
			long deListViewId, long groupId, long companyId,
			long DDMStructureId,
			OrderByComparator<DEListView> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByG_C_D_PrevAndNext(
			deListViewId, groupId, companyId, DDMStructureId,
			orderByComparator);
	}

	/**
	 * Removes all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 */
	public static void removeByG_C_D(
		long groupId, long companyId, long DDMStructureId) {

		getPersistence().removeByG_C_D(groupId, companyId, DDMStructureId);
	}

	/**
	 * Returns the number of de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching de list views
	 */
	public static int countByG_C_D(
		long groupId, long companyId, long DDMStructureId) {

		return getPersistence().countByG_C_D(
			groupId, companyId, DDMStructureId);
	}

	/**
	 * Caches the de list view in the entity cache if it is enabled.
	 *
	 * @param deListView the de list view
	 */
	public static void cacheResult(DEListView deListView) {
		getPersistence().cacheResult(deListView);
	}

	/**
	 * Caches the de list views in the entity cache if it is enabled.
	 *
	 * @param deListViews the de list views
	 */
	public static void cacheResult(List<DEListView> deListViews) {
		getPersistence().cacheResult(deListViews);
	}

	/**
	 * Creates a new de list view with the primary key. Does not add the de list view to the database.
	 *
	 * @param deListViewId the primary key for the new de list view
	 * @return the new de list view
	 */
	public static DEListView create(long deListViewId) {
		return getPersistence().create(deListViewId);
	}

	/**
	 * Removes the de list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view that was removed
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public static DEListView remove(long deListViewId)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().remove(deListViewId);
	}

	public static DEListView updateImpl(DEListView deListView) {
		return getPersistence().updateImpl(deListView);
	}

	/**
	 * Returns the de list view with the primary key or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public static DEListView findByPrimaryKey(long deListViewId)
		throws com.liferay.data.engine.exception.NoSuchListViewException {

		return getPersistence().findByPrimaryKey(deListViewId);
	}

	/**
	 * Returns the de list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view, or <code>null</code> if a de list view with the primary key could not be found
	 */
	public static DEListView fetchByPrimaryKey(long deListViewId) {
		return getPersistence().fetchByPrimaryKey(deListViewId);
	}

	/**
	 * Returns all the de list views.
	 *
	 * @return the de list views
	 */
	public static List<DEListView> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of de list views
	 */
	public static List<DEListView> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de list views
	 */
	public static List<DEListView> findAll(
		int start, int end, OrderByComparator<DEListView> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de list views
	 */
	public static List<DEListView> findAll(
		int start, int end, OrderByComparator<DEListView> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the de list views from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of de list views.
	 *
	 * @return the number of de list views
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DEListViewPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DEListViewPersistence, DEListViewPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DEListViewPersistence.class);

		ServiceTracker<DEListViewPersistence, DEListViewPersistence>
			serviceTracker =
				new ServiceTracker
					<DEListViewPersistence, DEListViewPersistence>(
						bundle.getBundleContext(), DEListViewPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}