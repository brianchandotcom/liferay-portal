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

package com.liferay.asset.service.persistence;

import com.liferay.asset.model.AssetEntryUsage;
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
 * The persistence utility for the asset entry usage service. This utility wraps <code>com.liferay.asset.service.persistence.impl.AssetEntryUsagePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsagePersistence
 * @generated
 */
public class AssetEntryUsageUtil {

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
	public static void clearCache(AssetEntryUsage assetEntryUsage) {
		getPersistence().clearCache(assetEntryUsage);
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
	public static Map<Serializable, AssetEntryUsage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetEntryUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntryUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntryUsage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntryUsage update(AssetEntryUsage assetEntryUsage) {
		return getPersistence().update(assetEntryUsage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntryUsage update(
		AssetEntryUsage assetEntryUsage, ServiceContext serviceContext) {

		return getPersistence().update(assetEntryUsage, serviceContext);
	}

	/**
	 * Returns all the asset entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByUuid_First(
			String uuid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByUuid_First(
		String uuid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByUuid_Last(
			String uuid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByUuid_Last(
		String uuid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByUuid_PrevAndNext(
			long assetEntryUsageId, String uuid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByUuid_PrevAndNext(
			assetEntryUsageId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset entry usages
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the asset entry usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset entry usage that was removed
	 */
	public static AssetEntryUsage removeByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of asset entry usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset entry usages
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the asset entry usages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByAssetEntryId(long assetEntryId) {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns a range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end) {

		return getPersistence().findByAssetEntryId(assetEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByAssetEntryId_First(
			long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByAssetEntryId_Last(
			long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByAssetEntryId_PrevAndNext(
			long assetEntryUsageId, long assetEntryId,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByAssetEntryId_PrevAndNext(
			assetEntryUsageId, assetEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	public static void removeByAssetEntryId(long assetEntryId) {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry usages
	 */
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns all the asset entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByPlid(
		long plid, int start, int end) {

		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByPlid(
		long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByPlid_First(
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByPlid_First(
		long plid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByPlid_Last(
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByPlid_Last(
		long plid, OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where plid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByPlid_PrevAndNext(
			long assetEntryUsageId, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByPlid_PrevAndNext(
			assetEntryUsageId, plid, orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of asset entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByA_T(long assetEntryId, int type) {
		return getPersistence().findByA_T(assetEntryId, type);
	}

	/**
	 * Returns a range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end) {

		return getPersistence().findByA_T(assetEntryId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByA_T(
			assetEntryId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByA_T(
			assetEntryId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByA_T_First(
			long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByA_T_First(
			assetEntryId, type, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_T_First(
		long assetEntryId, int type,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByA_T_First(
			assetEntryId, type, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByA_T_Last(
			long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByA_T_Last(
			assetEntryId, type, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_T_Last(
		long assetEntryId, int type,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByA_T_Last(
			assetEntryId, type, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByA_T_PrevAndNext(
			long assetEntryUsageId, long assetEntryId, int type,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByA_T_PrevAndNext(
			assetEntryUsageId, assetEntryId, type, orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where assetEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 */
	public static void removeByA_T(long assetEntryId, int type) {
		getPersistence().removeByA_T(assetEntryId, type);
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63; and type = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @return the number of matching asset entry usages
	 */
	public static int countByA_T(long assetEntryId, int type) {
		return getPersistence().countByA_T(assetEntryId, type);
	}

	/**
	 * Returns all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid) {

		return getPersistence().findByC_C_P(containerType, containerKey, plid);
	}

	/**
	 * Returns a range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start,
		int end) {

		return getPersistence().findByC_C_P(
			containerType, containerKey, plid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByC_C_P(
			containerType, containerKey, plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByC_C_P(
		long containerType, String containerKey, long plid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_P(
			containerType, containerKey, plid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByC_C_P_First(
			long containerType, String containerKey, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByC_C_P_First(
			containerType, containerKey, plid, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByC_C_P_First(
		long containerType, String containerKey, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByC_C_P_First(
			containerType, containerKey, plid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByC_C_P_Last(
			long containerType, String containerKey, long plid,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByC_C_P_Last(
			containerType, containerKey, plid, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByC_C_P_Last(
		long containerType, String containerKey, long plid,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByC_C_P_Last(
			containerType, containerKey, plid, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByC_C_P_PrevAndNext(
			long assetEntryUsageId, long containerType, String containerKey,
			long plid, OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByC_C_P_PrevAndNext(
			assetEntryUsageId, containerType, containerKey, plid,
			orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63; from the database.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 */
	public static void removeByC_C_P(
		long containerType, String containerKey, long plid) {

		getPersistence().removeByC_C_P(containerType, containerKey, plid);
	}

	/**
	 * Returns the number of asset entry usages where containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	public static int countByC_C_P(
		long containerType, String containerKey, long plid) {

		return getPersistence().countByC_C_P(containerType, containerKey, plid);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByA_C_C_P(
			long assetEntryId, long containerType, String containerKey,
			long plid)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return getPersistence().fetchByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid,
		boolean useFinderCache) {

		return getPersistence().fetchByA_C_C_P(
			assetEntryId, containerType, containerKey, plid, useFinderCache);
	}

	/**
	 * Removes the asset entry usage where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the asset entry usage that was removed
	 */
	public static AssetEntryUsage removeByA_C_C_P(
			long assetEntryId, long containerType, String containerKey,
			long plid)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().removeByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63; and containerType = &#63; and containerKey = &#63; and plid = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param plid the plid
	 * @return the number of matching asset entry usages
	 */
	public static int countByA_C_C_P(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return getPersistence().countByA_C_C_P(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Caches the asset entry usage in the entity cache if it is enabled.
	 *
	 * @param assetEntryUsage the asset entry usage
	 */
	public static void cacheResult(AssetEntryUsage assetEntryUsage) {
		getPersistence().cacheResult(assetEntryUsage);
	}

	/**
	 * Caches the asset entry usages in the entity cache if it is enabled.
	 *
	 * @param assetEntryUsages the asset entry usages
	 */
	public static void cacheResult(List<AssetEntryUsage> assetEntryUsages) {
		getPersistence().cacheResult(assetEntryUsages);
	}

	/**
	 * Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	 *
	 * @param assetEntryUsageId the primary key for the new asset entry usage
	 * @return the new asset entry usage
	 */
	public static AssetEntryUsage create(long assetEntryUsageId) {
		return getPersistence().create(assetEntryUsageId);
	}

	/**
	 * Removes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage remove(long assetEntryUsageId)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().remove(assetEntryUsageId);
	}

	public static AssetEntryUsage updateImpl(AssetEntryUsage assetEntryUsage) {
		return getPersistence().updateImpl(assetEntryUsage);
	}

	/**
	 * Returns the asset entry usage with the primary key or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage findByPrimaryKey(long assetEntryUsageId)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByPrimaryKey(assetEntryUsageId);
	}

	/**
	 * Returns the asset entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage, or <code>null</code> if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage fetchByPrimaryKey(long assetEntryUsageId) {
		return getPersistence().fetchByPrimaryKey(assetEntryUsageId);
	}

	/**
	 * Returns all the asset entry usages.
	 *
	 * @return the asset entry usages
	 */
	public static List<AssetEntryUsage> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of asset entry usages
	 */
	public static List<AssetEntryUsage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry usages
	 */
	public static List<AssetEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entry usages
	 */
	public static List<AssetEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset entry usages from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset entry usages.
	 *
	 * @return the number of asset entry usages
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetEntryUsagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetEntryUsagePersistence, AssetEntryUsagePersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryUsagePersistence.class);

		ServiceTracker<AssetEntryUsagePersistence, AssetEntryUsagePersistence>
			serviceTracker =
				new ServiceTracker
					<AssetEntryUsagePersistence, AssetEntryUsagePersistence>(
						bundle.getBundleContext(),
						AssetEntryUsagePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}