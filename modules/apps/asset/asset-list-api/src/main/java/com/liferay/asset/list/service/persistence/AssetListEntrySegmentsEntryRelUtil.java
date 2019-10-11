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

package com.liferay.asset.list.service.persistence;

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
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
 * The persistence utility for the asset list entry segments entry rel service. This utility wraps <code>com.liferay.asset.list.service.persistence.impl.AssetListEntrySegmentsEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRelPersistence
 * @generated
 */
public class AssetListEntrySegmentsEntryRelUtil {

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
	public static void clearCache(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		getPersistence().clearCache(assetListEntrySegmentsEntryRel);
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
	public static Map<Serializable, AssetListEntrySegmentsEntryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetListEntrySegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetListEntrySegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetListEntrySegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetListEntrySegmentsEntryRel update(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		return getPersistence().update(assetListEntrySegmentsEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetListEntrySegmentsEntryRel update(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			assetListEntrySegmentsEntryRel, serviceContext);
	}

	/**
	 * Returns all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel[] findByUuid_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_PrevAndNext(
			assetListEntrySegmentsEntryRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	public static AssetListEntrySegmentsEntryRel removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel[] findByUuid_C_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			assetListEntrySegmentsEntryRelId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId) {

		return getPersistence().findByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByAssetListEntryId_First(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetListEntryId_First(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel[]
			findByAssetListEntryId_PrevAndNext(
				long assetListEntrySegmentsEntryRelId, long assetListEntryId,
				OrderByComparator<AssetListEntrySegmentsEntryRel>
					orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByAssetListEntryId_PrevAndNext(
			assetListEntrySegmentsEntryRelId, assetListEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the asset list entry segments entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public static void removeByAssetListEntryId(long assetListEntryId) {
		getPersistence().removeByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countByAssetListEntryId(long assetListEntryId) {
		return getPersistence().countByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId) {

		return getPersistence().findBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel[]
			findBySegmentsEntryId_PrevAndNext(
				long assetListEntrySegmentsEntryRelId, long segmentsEntryId,
				OrderByComparator<AssetListEntrySegmentsEntryRel>
					orderByComparator)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findBySegmentsEntryId_PrevAndNext(
			assetListEntrySegmentsEntryRelId, segmentsEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the asset list entry segments entry rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public static void removeBySegmentsEntryId(long segmentsEntryId) {
		getPersistence().removeBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countBySegmentsEntryId(long segmentsEntryId) {
		return getPersistence().countBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId) {

		return getPersistence().fetchByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId, boolean useFinderCache) {

		return getPersistence().fetchByA_S(
			assetListEntryId, segmentsEntryId, useFinderCache);
	}

	/**
	 * Removes the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	public static AssetListEntrySegmentsEntryRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().removeByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public static int countByA_S(long assetListEntryId, long segmentsEntryId) {
		return getPersistence().countByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Caches the asset list entry segments entry rel in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 */
	public static void cacheResult(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		getPersistence().cacheResult(assetListEntrySegmentsEntryRel);
	}

	/**
	 * Caches the asset list entry segments entry rels in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRels the asset list entry segments entry rels
	 */
	public static void cacheResult(
		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels) {

		getPersistence().cacheResult(assetListEntrySegmentsEntryRels);
	}

	/**
	 * Creates a new asset list entry segments entry rel with the primary key. Does not add the asset list entry segments entry rel to the database.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key for the new asset list entry segments entry rel
	 * @return the new asset list entry segments entry rel
	 */
	public static AssetListEntrySegmentsEntryRel create(
		long assetListEntrySegmentsEntryRelId) {

		return getPersistence().create(assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Removes the asset list entry segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel remove(
			long assetListEntrySegmentsEntryRelId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().remove(assetListEntrySegmentsEntryRelId);
	}

	public static AssetListEntrySegmentsEntryRel updateImpl(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel) {

		return getPersistence().updateImpl(assetListEntrySegmentsEntryRel);
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel findByPrimaryKey(
			long assetListEntrySegmentsEntryRelId)
		throws com.liferay.asset.list.exception.
			NoSuchEntrySegmentsEntryRelException {

		return getPersistence().findByPrimaryKey(
			assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel, or <code>null</code> if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static AssetListEntrySegmentsEntryRel fetchByPrimaryKey(
		long assetListEntrySegmentsEntryRelId) {

		return getPersistence().fetchByPrimaryKey(
			assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Returns all the asset list entry segments entry rels.
	 *
	 * @return the asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry segments entry rels
	 */
	public static List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset list entry segments entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset list entry segments entry rels.
	 *
	 * @return the number of asset list entry segments entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetListEntrySegmentsEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetListEntrySegmentsEntryRelPersistence,
		 AssetListEntrySegmentsEntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetListEntrySegmentsEntryRelPersistence.class);

		ServiceTracker
			<AssetListEntrySegmentsEntryRelPersistence,
			 AssetListEntrySegmentsEntryRelPersistence> serviceTracker =
				new ServiceTracker
					<AssetListEntrySegmentsEntryRelPersistence,
					 AssetListEntrySegmentsEntryRelPersistence>(
						 bundle.getBundleContext(),
						 AssetListEntrySegmentsEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}