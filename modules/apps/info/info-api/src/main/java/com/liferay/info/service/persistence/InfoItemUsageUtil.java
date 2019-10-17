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

package com.liferay.info.service.persistence;

import com.liferay.info.model.InfoItemUsage;
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
 * The persistence utility for the info item usage service. This utility wraps <code>com.liferay.info.service.persistence.impl.InfoItemUsagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see InfoItemUsagePersistence
 * @generated
 */
public class InfoItemUsageUtil {

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
	public static void clearCache(InfoItemUsage infoItemUsage) {
		getPersistence().clearCache(infoItemUsage);
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
	public static Map<Serializable, InfoItemUsage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<InfoItemUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<InfoItemUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<InfoItemUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static InfoItemUsage update(InfoItemUsage infoItemUsage) {
		return getPersistence().update(infoItemUsage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static InfoItemUsage update(
		InfoItemUsage infoItemUsage, ServiceContext serviceContext) {

		return getPersistence().update(infoItemUsage, serviceContext);
	}

	/**
	 * Returns all the info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching info item usages
	 */
	public static List<InfoItemUsage> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public static List<InfoItemUsage> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByUuid_First(
			String uuid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByUuid_First(
		String uuid, OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByUuid_Last(
			String uuid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByUuid_Last(
		String uuid, OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage[] findByUuid_PrevAndNext(
			long infoItemUsageId, String uuid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByUuid_PrevAndNext(
			infoItemUsageId, uuid, orderByComparator);
	}

	/**
	 * Removes all the info item usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching info item usages
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByUUID_G(String uuid, long groupId)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the info item usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the info item usage that was removed
	 */
	public static InfoItemUsage removeByUUID_G(String uuid, long groupId)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of info item usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching info item usages
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	public static List<InfoItemUsage> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public static List<InfoItemUsage> findByPlid(
		long plid, int start, int end) {

		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByPlid_First(
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByPlid_First(
		long plid, OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByPlid_Last(
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByPlid_Last(
		long plid, OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage[] findByPlid_PrevAndNext(
			long infoItemUsageId, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByPlid_PrevAndNext(
			infoItemUsageId, plid, orderByComparator);
	}

	/**
	 * Removes all the info item usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage[] findByC_C_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_PrevAndNext(
			infoItemUsageId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching info item usages
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type) {

		return getPersistence().findByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByC_C_T_First(
			long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_T_First(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByC_C_T_Last(
			long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage[] findByC_C_T_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK, int type,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_T_PrevAndNext(
			infoItemUsageId, classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public static void removeByC_C_T(long classNameId, long classPK, int type) {
		getPersistence().removeByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching info item usages
	 */
	public static int countByC_C_T(long classNameId, long classPK, int type) {
		return getPersistence().countByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	public static List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid) {

		return getPersistence().findByCK_CT_P(
			containerKey, containerType, plid);
	}

	/**
	 * Returns a range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public static List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start,
		int end) {

		return getPersistence().findByCK_CT_P(
			containerKey, containerType, plid, start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findByCK_CT_P(
			containerKey, containerType, plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public static List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCK_CT_P(
			containerKey, containerType, plid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByCK_CT_P_First(
			containerKey, containerType, plid, orderByComparator);
	}

	/**
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByCK_CT_P_First(
			containerKey, containerType, plid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByCK_CT_P_Last(
			containerKey, containerType, plid, orderByComparator);
	}

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().fetchByCK_CT_P_Last(
			containerKey, containerType, plid, orderByComparator);
	}

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage[] findByCK_CT_P_PrevAndNext(
			long infoItemUsageId, String containerKey, long containerType,
			long plid, OrderByComparator<InfoItemUsage> orderByComparator)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByCK_CT_P_PrevAndNext(
			infoItemUsageId, containerKey, containerType, plid,
			orderByComparator);
	}

	/**
	 * Removes all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	public static void removeByCK_CT_P(
		String containerKey, long containerType, long plid) {

		getPersistence().removeByCK_CT_P(containerKey, containerType, plid);
	}

	/**
	 * Returns the number of info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public static int countByCK_CT_P(
		String containerKey, long containerType, long plid) {

		return getPersistence().countByCK_CT_P(
			containerKey, containerType, plid);
	}

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public static InfoItemUsage findByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		return getPersistence().fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public static InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid, boolean useFinderCache) {

		return getPersistence().fetchByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid,
			useFinderCache);
	}

	/**
	 * Removes the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the info item usage that was removed
	 */
	public static InfoItemUsage removeByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().removeByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public static int countByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid) {

		return getPersistence().countByC_C_CK_CT_P(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Caches the info item usage in the entity cache if it is enabled.
	 *
	 * @param infoItemUsage the info item usage
	 */
	public static void cacheResult(InfoItemUsage infoItemUsage) {
		getPersistence().cacheResult(infoItemUsage);
	}

	/**
	 * Caches the info item usages in the entity cache if it is enabled.
	 *
	 * @param infoItemUsages the info item usages
	 */
	public static void cacheResult(List<InfoItemUsage> infoItemUsages) {
		getPersistence().cacheResult(infoItemUsages);
	}

	/**
	 * Creates a new info item usage with the primary key. Does not add the info item usage to the database.
	 *
	 * @param infoItemUsageId the primary key for the new info item usage
	 * @return the new info item usage
	 */
	public static InfoItemUsage create(long infoItemUsageId) {
		return getPersistence().create(infoItemUsageId);
	}

	/**
	 * Removes the info item usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage that was removed
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage remove(long infoItemUsageId)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().remove(infoItemUsageId);
	}

	public static InfoItemUsage updateImpl(InfoItemUsage infoItemUsage) {
		return getPersistence().updateImpl(infoItemUsage);
	}

	/**
	 * Returns the info item usage with the primary key or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage findByPrimaryKey(long infoItemUsageId)
		throws com.liferay.info.exception.NoSuchItemUsageException {

		return getPersistence().findByPrimaryKey(infoItemUsageId);
	}

	/**
	 * Returns the info item usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage, or <code>null</code> if a info item usage with the primary key could not be found
	 */
	public static InfoItemUsage fetchByPrimaryKey(long infoItemUsageId) {
		return getPersistence().fetchByPrimaryKey(infoItemUsageId);
	}

	/**
	 * Returns all the info item usages.
	 *
	 * @return the info item usages
	 */
	public static List<InfoItemUsage> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of info item usages
	 */
	public static List<InfoItemUsage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of info item usages
	 */
	public static List<InfoItemUsage> findAll(
		int start, int end,
		OrderByComparator<InfoItemUsage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of info item usages
	 */
	public static List<InfoItemUsage> findAll(
		int start, int end, OrderByComparator<InfoItemUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the info item usages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of info item usages.
	 *
	 * @return the number of info item usages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static InfoItemUsagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<InfoItemUsagePersistence, InfoItemUsagePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(InfoItemUsagePersistence.class);

		ServiceTracker<InfoItemUsagePersistence, InfoItemUsagePersistence>
			serviceTracker =
				new ServiceTracker
					<InfoItemUsagePersistence, InfoItemUsagePersistence>(
						bundle.getBundleContext(),
						InfoItemUsagePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}