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

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class AssetEntryUsageUtil {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, retrieveFromCache);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param type the type
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByA_T(
		long assetEntryId, int type, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByA_T(
			assetEntryId, type, start, end, orderByComparator,
			retrieveFromCache);
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
	 * Returns all the asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByP_C_C(
		long plid, long containerType, String containerKey) {

		return getPersistence().findByP_C_C(plid, containerType, containerKey);
	}

	/**
	 * Returns a range of all the asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByP_C_C(
		long plid, long containerType, String containerKey, int start,
		int end) {

		return getPersistence().findByP_C_C(
			plid, containerType, containerKey, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByP_C_C(
		long plid, long containerType, String containerKey, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().findByP_C_C(
			plid, containerType, containerKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry usages
	 */
	public static List<AssetEntryUsage> findByP_C_C(
		long plid, long containerType, String containerKey, int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByP_C_C(
			plid, containerType, containerKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByP_C_C_First(
			long plid, long containerType, String containerKey,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByP_C_C_First(
			plid, containerType, containerKey, orderByComparator);
	}

	/**
	 * Returns the first asset entry usage in the ordered set where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByP_C_C_First(
		long plid, long containerType, String containerKey,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByP_C_C_First(
			plid, containerType, containerKey, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByP_C_C_Last(
			long plid, long containerType, String containerKey,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByP_C_C_Last(
			plid, containerType, containerKey, orderByComparator);
	}

	/**
	 * Returns the last asset entry usage in the ordered set where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByP_C_C_Last(
		long plid, long containerType, String containerKey,
		OrderByComparator<AssetEntryUsage> orderByComparator) {

		return getPersistence().fetchByP_C_C_Last(
			plid, containerType, containerKey, orderByComparator);
	}

	/**
	 * Returns the asset entry usages before and after the current asset entry usage in the ordered set where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param assetEntryUsageId the primary key of the current asset entry usage
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry usage
	 * @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	 */
	public static AssetEntryUsage[] findByP_C_C_PrevAndNext(
			long assetEntryUsageId, long plid, long containerType,
			String containerKey,
			OrderByComparator<AssetEntryUsage> orderByComparator)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByP_C_C_PrevAndNext(
			assetEntryUsageId, plid, containerType, containerKey,
			orderByComparator);
	}

	/**
	 * Removes all the asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 */
	public static void removeByP_C_C(
		long plid, long containerType, String containerKey) {

		getPersistence().removeByP_C_C(plid, containerType, containerKey);
	}

	/**
	 * Returns the number of asset entry usages where plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the number of matching asset entry usages
	 */
	public static int countByP_C_C(
		long plid, long containerType, String containerKey) {

		return getPersistence().countByP_C_C(plid, containerType, containerKey);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and plid = &#63; and containerType = &#63; and containerKey = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the matching asset entry usage
	 * @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage findByA_P_C_C(
			long assetEntryId, long plid, long containerType,
			String containerKey)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().findByA_P_C_C(
			assetEntryId, plid, containerType, containerKey);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and plid = &#63; and containerType = &#63; and containerKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_P_C_C(
		long assetEntryId, long plid, long containerType, String containerKey) {

		return getPersistence().fetchByA_P_C_C(
			assetEntryId, plid, containerType, containerKey);
	}

	/**
	 * Returns the asset entry usage where assetEntryId = &#63; and plid = &#63; and containerType = &#63; and containerKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static AssetEntryUsage fetchByA_P_C_C(
		long assetEntryId, long plid, long containerType, String containerKey,
		boolean retrieveFromCache) {

		return getPersistence().fetchByA_P_C_C(
			assetEntryId, plid, containerType, containerKey, retrieveFromCache);
	}

	/**
	 * Removes the asset entry usage where assetEntryId = &#63; and plid = &#63; and containerType = &#63; and containerKey = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the asset entry usage that was removed
	 */
	public static AssetEntryUsage removeByA_P_C_C(
			long assetEntryId, long plid, long containerType,
			String containerKey)
		throws com.liferay.asset.exception.NoSuchEntryUsageException {

		return getPersistence().removeByA_P_C_C(
			assetEntryId, plid, containerType, containerKey);
	}

	/**
	 * Returns the number of asset entry usages where assetEntryId = &#63; and plid = &#63; and containerType = &#63; and containerKey = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param plid the plid
	 * @param containerType the container type
	 * @param containerKey the container key
	 * @return the number of matching asset entry usages
	 */
	public static int countByA_P_C_C(
		long assetEntryId, long plid, long containerType, String containerKey) {

		return getPersistence().countByA_P_C_C(
			assetEntryId, plid, containerType, containerKey);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryUsageModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry usages
	 */
	public static List<AssetEntryUsage> findAll(
		int start, int end,
		OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
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