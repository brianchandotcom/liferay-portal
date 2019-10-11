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

package com.liferay.adaptive.media.image.service.persistence;

import com.liferay.adaptive.media.image.model.AMImageEntry;
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
 * The persistence utility for the am image entry service. This utility wraps <code>com.liferay.adaptive.media.image.service.persistence.impl.AMImageEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntryPersistence
 * @generated
 */
public class AMImageEntryUtil {

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
	public static void clearCache(AMImageEntry amImageEntry) {
		getPersistence().clearCache(amImageEntry);
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
	public static Map<Serializable, AMImageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AMImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AMImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AMImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AMImageEntry update(AMImageEntry amImageEntry) {
		return getPersistence().update(amImageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AMImageEntry update(
		AMImageEntry amImageEntry, ServiceContext serviceContext) {

		return getPersistence().update(amImageEntry, serviceContext);
	}

	/**
	 * Returns all the am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByUuid_First(
			String uuid, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUuid_First(
		String uuid, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByUuid_Last(
			String uuid, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUuid_Last(
		String uuid, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByUuid_PrevAndNext(
			long amImageEntryId, String uuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			amImageEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the am image entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching am image entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the am image entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the am image entry that was removed
	 */
	public static AMImageEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of am image entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByUuid_C_PrevAndNext(
			long amImageEntryId, String uuid, long companyId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			amImageEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the am image entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByGroupId_First(
			long groupId, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByGroupId_First(
		long groupId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByGroupId_Last(
			long groupId, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByGroupId_PrevAndNext(
			long amImageEntryId, long groupId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			amImageEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the am image entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByCompanyId_First(
			long companyId, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByCompanyId_Last(
			long companyId, OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByCompanyId_PrevAndNext(
			long amImageEntryId, long companyId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			amImageEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the am image entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid) {

		return getPersistence().findByConfigurationUuid(configurationUuid);
	}

	/**
	 * Returns a range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end) {

		return getPersistence().findByConfigurationUuid(
			configurationUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByConfigurationUuid(
			configurationUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByConfigurationUuid(
			configurationUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByConfigurationUuid_First(
			String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByConfigurationUuid_First(
			configurationUuid, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByConfigurationUuid_First(
			configurationUuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByConfigurationUuid_Last(
			String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByConfigurationUuid_Last(
			configurationUuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByConfigurationUuid_Last(
			configurationUuid, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByConfigurationUuid_PrevAndNext(
			long amImageEntryId, String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByConfigurationUuid_PrevAndNext(
			amImageEntryId, configurationUuid, orderByComparator);
	}

	/**
	 * Removes all the am image entries where configurationUuid = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 */
	public static void removeByConfigurationUuid(String configurationUuid) {
		getPersistence().removeByConfigurationUuid(configurationUuid);
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	public static int countByConfigurationUuid(String configurationUuid) {
		return getPersistence().countByConfigurationUuid(configurationUuid);
	}

	/**
	 * Returns all the am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByFileVersionId(long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

	/**
	 * Returns a range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end) {

		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByFileVersionId(
			fileVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFileVersionId(
			fileVersionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByFileVersionId_First(
			long fileVersionId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByFileVersionId_First(
			fileVersionId, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByFileVersionId_First(
		long fileVersionId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByFileVersionId_First(
			fileVersionId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByFileVersionId_Last(
			long fileVersionId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByFileVersionId_Last(
			fileVersionId, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByFileVersionId_Last(
		long fileVersionId, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByFileVersionId_Last(
			fileVersionId, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByFileVersionId_PrevAndNext(
			long amImageEntryId, long fileVersionId,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByFileVersionId_PrevAndNext(
			amImageEntryId, fileVersionId, orderByComparator);
	}

	/**
	 * Removes all the am image entries where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	 * Returns the number of am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	 * Returns all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	public static List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid) {

		return getPersistence().findByC_C(companyId, configurationUuid);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	public static List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end) {

		return getPersistence().findByC_C(
			companyId, configurationUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findByC_C(
			companyId, configurationUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching am image entries
	 */
	public static List<AMImageEntry> findByC_C(
		long companyId, String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			companyId, configurationUuid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByC_C_First(
			long companyId, String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByC_C_First(
			companyId, configurationUuid, orderByComparator);
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByC_C_First(
		long companyId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			companyId, configurationUuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByC_C_Last(
			long companyId, String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByC_C_Last(
			companyId, configurationUuid, orderByComparator);
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByC_C_Last(
		long companyId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			companyId, configurationUuid, orderByComparator);
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry[] findByC_C_PrevAndNext(
			long amImageEntryId, long companyId, String configurationUuid,
			OrderByComparator<AMImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByC_C_PrevAndNext(
			amImageEntryId, companyId, configurationUuid, orderByComparator);
	}

	/**
	 * Removes all the am image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 */
	public static void removeByC_C(long companyId, String configurationUuid) {
		getPersistence().removeByC_C(companyId, configurationUuid);
	}

	/**
	 * Returns the number of am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	public static int countByC_C(long companyId, String configurationUuid) {
		return getPersistence().countByC_C(companyId, configurationUuid);
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	public static AMImageEntry findByC_F(
			String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByC_F(configurationUuid, fileVersionId);
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByC_F(
		String configurationUuid, long fileVersionId) {

		return getPersistence().fetchByC_F(configurationUuid, fileVersionId);
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	public static AMImageEntry fetchByC_F(
		String configurationUuid, long fileVersionId, boolean useFinderCache) {

		return getPersistence().fetchByC_F(
			configurationUuid, fileVersionId, useFinderCache);
	}

	/**
	 * Removes the am image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the am image entry that was removed
	 */
	public static AMImageEntry removeByC_F(
			String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().removeByC_F(configurationUuid, fileVersionId);
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	public static int countByC_F(String configurationUuid, long fileVersionId) {
		return getPersistence().countByC_F(configurationUuid, fileVersionId);
	}

	/**
	 * Caches the am image entry in the entity cache if it is enabled.
	 *
	 * @param amImageEntry the am image entry
	 */
	public static void cacheResult(AMImageEntry amImageEntry) {
		getPersistence().cacheResult(amImageEntry);
	}

	/**
	 * Caches the am image entries in the entity cache if it is enabled.
	 *
	 * @param amImageEntries the am image entries
	 */
	public static void cacheResult(List<AMImageEntry> amImageEntries) {
		getPersistence().cacheResult(amImageEntries);
	}

	/**
	 * Creates a new am image entry with the primary key. Does not add the am image entry to the database.
	 *
	 * @param amImageEntryId the primary key for the new am image entry
	 * @return the new am image entry
	 */
	public static AMImageEntry create(long amImageEntryId) {
		return getPersistence().create(amImageEntryId);
	}

	/**
	 * Removes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry that was removed
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry remove(long amImageEntryId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().remove(amImageEntryId);
	}

	public static AMImageEntry updateImpl(AMImageEntry amImageEntry) {
		return getPersistence().updateImpl(amImageEntry);
	}

	/**
	 * Returns the am image entry with the primary key or throws a <code>NoSuchAMImageEntryException</code> if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry findByPrimaryKey(long amImageEntryId)
		throws com.liferay.adaptive.media.image.exception.
			NoSuchAMImageEntryException {

		return getPersistence().findByPrimaryKey(amImageEntryId);
	}

	/**
	 * Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	 */
	public static AMImageEntry fetchByPrimaryKey(long amImageEntryId) {
		return getPersistence().fetchByPrimaryKey(amImageEntryId);
	}

	/**
	 * Returns all the am image entries.
	 *
	 * @return the am image entries
	 */
	public static List<AMImageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of am image entries
	 */
	public static List<AMImageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of am image entries
	 */
	public static List<AMImageEntry> findAll(
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AMImageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of am image entries
	 */
	public static List<AMImageEntry> findAll(
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the am image entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of am image entries.
	 *
	 * @return the number of am image entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AMImageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AMImageEntryPersistence, AMImageEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AMImageEntryPersistence.class);

		ServiceTracker<AMImageEntryPersistence, AMImageEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<AMImageEntryPersistence, AMImageEntryPersistence>(
						bundle.getBundleContext(),
						AMImageEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}