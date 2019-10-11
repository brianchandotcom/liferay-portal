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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
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
 * The persistence utility for the ddm structure layout service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLayoutPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutPersistence
 * @generated
 */
public class DDMStructureLayoutUtil {

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
	public static void clearCache(DDMStructureLayout ddmStructureLayout) {
		getPersistence().clearCache(ddmStructureLayout);
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
	public static Map<Serializable, DDMStructureLayout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMStructureLayout update(
		DDMStructureLayout ddmStructureLayout) {

		return getPersistence().update(ddmStructureLayout);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureLayout update(
		DDMStructureLayout ddmStructureLayout, ServiceContext serviceContext) {

		return getPersistence().update(ddmStructureLayout, serviceContext);
	}

	/**
	 * Returns all the ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByUuid_First(
			String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUuid_First(
		String uuid, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByUuid_Last(
			String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUuid_Last(
		String uuid, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByUuid_PrevAndNext(
			long structureLayoutId, String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_PrevAndNext(
			structureLayoutId, uuid, orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByUUID_G(String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the ddm structure layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm structure layout that was removed
	 */
	public static DDMStructureLayout removeByUUID_G(String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByUuid_C_PrevAndNext(
			long structureLayoutId, String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByUuid_C_PrevAndNext(
			structureLayoutId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByGroupId_First(
			long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByGroupId_First(
		long groupId, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByGroupId_Last(
			long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByGroupId_Last(
		long groupId, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByGroupId_PrevAndNext(
			long structureLayoutId, long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByGroupId_PrevAndNext(
			structureLayoutId, groupId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey) {

		return getPersistence().findByStructureLayoutKey(structureLayoutKey);
	}

	/**
	 * Returns a range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end) {

		return getPersistence().findByStructureLayoutKey(
			structureLayoutKey, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByStructureLayoutKey(
			structureLayoutKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureLayoutKey(
			structureLayoutKey, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByStructureLayoutKey_First(
			String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByStructureLayoutKey_First(
			structureLayoutKey, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByStructureLayoutKey_First(
		String structureLayoutKey,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByStructureLayoutKey_First(
			structureLayoutKey, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByStructureLayoutKey_Last(
			String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByStructureLayoutKey_Last(
			structureLayoutKey, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByStructureLayoutKey_Last(
		String structureLayoutKey,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByStructureLayoutKey_Last(
			structureLayoutKey, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByStructureLayoutKey_PrevAndNext(
			long structureLayoutId, String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByStructureLayoutKey_PrevAndNext(
			structureLayoutId, structureLayoutKey, orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where structureLayoutKey = &#63; from the database.
	 *
	 * @param structureLayoutKey the structure layout key
	 */
	public static void removeByStructureLayoutKey(String structureLayoutKey) {
		getPersistence().removeByStructureLayoutKey(structureLayoutKey);
	}

	/**
	 * Returns the number of ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByStructureLayoutKey(String structureLayoutKey) {
		return getPersistence().countByStructureLayoutKey(structureLayoutKey);
	}

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByStructureVersionId(
			long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId) {

		return getPersistence().fetchByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean useFinderCache) {

		return getPersistence().fetchByStructureVersionId(
			structureVersionId, useFinderCache);
	}

	/**
	 * Removes the ddm structure layout where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the ddm structure layout that was removed
	 */
	public static DDMStructureLayout removeByStructureVersionId(
			long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().removeByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the number of ddm structure layouts where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByStructureVersionId(long structureVersionId) {
		return getPersistence().countByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId) {

		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end) {

		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByG_C(
			groupId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C(
			groupId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByG_C_First(
			long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_First(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_First(
		long groupId, long classNameId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByG_C_First(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByG_C_Last(
			long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_Last(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_Last(
		long groupId, long classNameId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByG_C_Last(
			groupId, classNameId, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByG_C_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_PrevAndNext(
			structureLayoutId, groupId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_S(
			groupId, classNameId, structureLayoutKey);
	}

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey) {

		return getPersistence().fetchByG_C_S(
			groupId, classNameId, structureLayoutKey);
	}

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey,
		boolean useFinderCache) {

		return getPersistence().fetchByG_C_S(
			groupId, classNameId, structureLayoutKey, useFinderCache);
	}

	/**
	 * Removes the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the ddm structure layout that was removed
	 */
	public static DDMStructureLayout removeByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().removeByG_C_S(
			groupId, classNameId, structureLayoutKey);
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByG_C_S(
		long groupId, long classNameId, String structureLayoutKey) {

		return getPersistence().countByG_C_S(
			groupId, classNameId, structureLayoutKey);
	}

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		return getPersistence().findByG_C_SV(
			groupId, classNameId, structureVersionId);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end) {

		return getPersistence().findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public static List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end, OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByG_C_SV_First(
			long groupId, long classNameId, long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_SV_First(
			groupId, classNameId, structureVersionId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_SV_First(
		long groupId, long classNameId, long structureVersionId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByG_C_SV_First(
			groupId, classNameId, structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout findByG_C_SV_Last(
			long groupId, long classNameId, long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_SV_Last(
			groupId, classNameId, structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public static DDMStructureLayout fetchByG_C_SV_Last(
		long groupId, long classNameId, long structureVersionId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().fetchByG_C_SV_Last(
			groupId, classNameId, structureVersionId, orderByComparator);
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout[] findByG_C_SV_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByG_C_SV_PrevAndNext(
			structureLayoutId, groupId, classNameId, structureVersionId,
			orderByComparator);
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 */
	public static void removeByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		getPersistence().removeByG_C_SV(
			groupId, classNameId, structureVersionId);
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	public static int countByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		return getPersistence().countByG_C_SV(
			groupId, classNameId, structureVersionId);
	}

	/**
	 * Caches the ddm structure layout in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayout the ddm structure layout
	 */
	public static void cacheResult(DDMStructureLayout ddmStructureLayout) {
		getPersistence().cacheResult(ddmStructureLayout);
	}

	/**
	 * Caches the ddm structure layouts in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayouts the ddm structure layouts
	 */
	public static void cacheResult(
		List<DDMStructureLayout> ddmStructureLayouts) {

		getPersistence().cacheResult(ddmStructureLayouts);
	}

	/**
	 * Creates a new ddm structure layout with the primary key. Does not add the ddm structure layout to the database.
	 *
	 * @param structureLayoutId the primary key for the new ddm structure layout
	 * @return the new ddm structure layout
	 */
	public static DDMStructureLayout create(long structureLayoutId) {
		return getPersistence().create(structureLayoutId);
	}

	/**
	 * Removes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout that was removed
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout remove(long structureLayoutId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().remove(structureLayoutId);
	}

	public static DDMStructureLayout updateImpl(
		DDMStructureLayout ddmStructureLayout) {

		return getPersistence().updateImpl(ddmStructureLayout);
	}

	/**
	 * Returns the ddm structure layout with the primary key or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureLayoutException {

		return getPersistence().findByPrimaryKey(structureLayoutId);
	}

	/**
	 * Returns the ddm structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout, or <code>null</code> if a ddm structure layout with the primary key could not be found
	 */
	public static DDMStructureLayout fetchByPrimaryKey(long structureLayoutId) {
		return getPersistence().fetchByPrimaryKey(structureLayoutId);
	}

	/**
	 * Returns all the ddm structure layouts.
	 *
	 * @return the ddm structure layouts
	 */
	public static List<DDMStructureLayout> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of ddm structure layouts
	 */
	public static List<DDMStructureLayout> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure layouts
	 */
	public static List<DDMStructureLayout> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure layouts
	 */
	public static List<DDMStructureLayout> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm structure layouts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm structure layouts.
	 *
	 * @return the number of ddm structure layouts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMStructureLayoutPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStructureLayoutPersistence, DDMStructureLayoutPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMStructureLayoutPersistence.class);

		ServiceTracker
			<DDMStructureLayoutPersistence, DDMStructureLayoutPersistence>
				serviceTracker =
					new ServiceTracker
						<DDMStructureLayoutPersistence,
						 DDMStructureLayoutPersistence>(
							 bundle.getBundleContext(),
							 DDMStructureLayoutPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}