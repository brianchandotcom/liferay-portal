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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.model.AssetListSegmentRel;
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
 * The persistence utility for the asset list segment rel service. This utility wraps <code>com.liferay.asset.list.service.persistence.impl.AssetListSegmentRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListSegmentRelPersistence
 * @generated
 */
@ProviderType
public class AssetListSegmentRelUtil {

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
	public static void clearCache(AssetListSegmentRel assetListSegmentRel) {
		getPersistence().clearCache(assetListSegmentRel);
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
	public static Map<Serializable, AssetListSegmentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetListSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetListSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetListSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetListSegmentRel update(
		AssetListSegmentRel assetListSegmentRel) {

		return getPersistence().update(assetListSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetListSegmentRel update(
		AssetListSegmentRel assetListSegmentRel,
		ServiceContext serviceContext) {

		return getPersistence().update(assetListSegmentRel, serviceContext);
	}

	/**
	 * Returns all the asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUuid_First(
		String uuid, OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUuid_Last(
		String uuid, OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel[] findByUuid_PrevAndNext(
			long assetListSegmentRelId, String uuid,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_PrevAndNext(
			assetListSegmentRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset list segment rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list segment rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	 * Removes the asset list segment rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list segment rel that was removed
	 */
	public static AssetListSegmentRel removeByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list segment rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel[] findByUuid_C_PrevAndNext(
			long assetListSegmentRelId, String uuid, long companyId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			assetListSegmentRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the asset list segment rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list segment rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId) {

		return getPersistence().findByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns a range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByAssetListEntryId(
			assetListEntryId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByAssetListEntryId_First(
			long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByAssetListEntryId_First(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByAssetListEntryId_First(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByAssetListEntryId_Last(
			long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchByAssetListEntryId_Last(
			assetListEntryId, orderByComparator);
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel[] findByAssetListEntryId_PrevAndNext(
			long assetListSegmentRelId, long assetListEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByAssetListEntryId_PrevAndNext(
			assetListSegmentRelId, assetListEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset list segment rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public static void removeByAssetListEntryId(long assetListEntryId) {
		getPersistence().removeByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list segment rels
	 */
	public static int countByAssetListEntryId(long assetListEntryId) {
		return getPersistence().countByAssetListEntryId(assetListEntryId);
	}

	/**
	 * Returns all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId) {

		return getPersistence().findBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns a range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset list segment rels
	 */
	public static List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel[] findBySegmentsEntryId_PrevAndNext(
			long assetListSegmentRelId, long segmentsEntryId,
			OrderByComparator<AssetListSegmentRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findBySegmentsEntryId_PrevAndNext(
			assetListSegmentRelId, segmentsEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset list segment rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public static void removeBySegmentsEntryId(long segmentsEntryId) {
		getPersistence().removeBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the number of asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	public static int countBySegmentsEntryId(long segmentsEntryId) {
		return getPersistence().countBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId) {

		return getPersistence().fetchByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public static AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId,
		boolean retrieveFromCache) {

		return getPersistence().fetchByA_S(
			assetListEntryId, segmentsEntryId, retrieveFromCache);
	}

	/**
	 * Removes the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list segment rel that was removed
	 */
	public static AssetListSegmentRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().removeByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	public static int countByA_S(long assetListEntryId, long segmentsEntryId) {
		return getPersistence().countByA_S(assetListEntryId, segmentsEntryId);
	}

	/**
	 * Caches the asset list segment rel in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 */
	public static void cacheResult(AssetListSegmentRel assetListSegmentRel) {
		getPersistence().cacheResult(assetListSegmentRel);
	}

	/**
	 * Caches the asset list segment rels in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRels the asset list segment rels
	 */
	public static void cacheResult(
		List<AssetListSegmentRel> assetListSegmentRels) {

		getPersistence().cacheResult(assetListSegmentRels);
	}

	/**
	 * Creates a new asset list segment rel with the primary key. Does not add the asset list segment rel to the database.
	 *
	 * @param assetListSegmentRelId the primary key for the new asset list segment rel
	 * @return the new asset list segment rel
	 */
	public static AssetListSegmentRel create(long assetListSegmentRelId) {
		return getPersistence().create(assetListSegmentRelId);
	}

	/**
	 * Removes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel remove(long assetListSegmentRelId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().remove(assetListSegmentRelId);
	}

	public static AssetListSegmentRel updateImpl(
		AssetListSegmentRel assetListSegmentRel) {

		return getPersistence().updateImpl(assetListSegmentRel);
	}

	/**
	 * Returns the asset list segment rel with the primary key or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel findByPrimaryKey(
			long assetListSegmentRelId)
		throws com.liferay.asset.list.exception.NoSuchSegmentRelException {

		return getPersistence().findByPrimaryKey(assetListSegmentRelId);
	}

	/**
	 * Returns the asset list segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel, or <code>null</code> if a asset list segment rel with the primary key could not be found
	 */
	public static AssetListSegmentRel fetchByPrimaryKey(
		long assetListSegmentRelId) {

		return getPersistence().fetchByPrimaryKey(assetListSegmentRelId);
	}

	/**
	 * Returns all the asset list segment rels.
	 *
	 * @return the asset list segment rels
	 */
	public static List<AssetListSegmentRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of asset list segment rels
	 */
	public static List<AssetListSegmentRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list segment rels
	 */
	public static List<AssetListSegmentRel> findAll(
		int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset list segment rels
	 */
	public static List<AssetListSegmentRel> findAll(
		int start, int end,
		OrderByComparator<AssetListSegmentRel> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the asset list segment rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset list segment rels.
	 *
	 * @return the number of asset list segment rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetListSegmentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetListSegmentRelPersistence, AssetListSegmentRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetListSegmentRelPersistence.class);

		ServiceTracker
			<AssetListSegmentRelPersistence, AssetListSegmentRelPersistence>
				serviceTracker =
					new ServiceTracker
						<AssetListSegmentRelPersistence,
						 AssetListSegmentRelPersistence>(
							 bundle.getBundleContext(),
							 AssetListSegmentRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}