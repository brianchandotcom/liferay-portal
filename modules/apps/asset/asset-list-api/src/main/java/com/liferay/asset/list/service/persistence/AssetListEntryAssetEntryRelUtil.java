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

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset list entry asset entry rel service. This utility wraps {@link com.liferay.asset.list.service.persistence.impl.AssetListEntryAssetEntryRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRelPersistence
 * @see com.liferay.asset.list.service.persistence.impl.AssetListEntryAssetEntryRelPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetListEntryAssetEntryRelUtil {
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
	public static void clearCache(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		getPersistence().clearCache(assetListEntryAssetEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetListEntryAssetEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetListEntryAssetEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetListEntryAssetEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetListEntryAssetEntryRel update(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		return getPersistence().update(assetListEntryAssetEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetListEntryAssetEntryRel update(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(assetListEntryAssetEntryRel, serviceContext);
	}

	/**
	* Returns all the asset list entry asset entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the asset list entry asset entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByUuid_First(String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUuid_First(String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByUuid_Last(String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUuid_Last(String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where uuid = &#63;.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel[] findByUuid_PrevAndNext(
		long assetListEntryAssetEntryRelId, String uuid,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(assetListEntryAssetEntryRelId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the asset list entry asset entry rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of asset list entry asset entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryAssetEntryRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByUUID_G(String uuid,
		long groupId)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUUID_G(String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset list entry asset entry rel that was removed
	*/
	public static AssetListEntryAssetEntryRel removeByUUID_G(String uuid,
		long groupId)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of asset list entry asset entry rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel[] findByUuid_C_PrevAndNext(
		long assetListEntryAssetEntryRelId, String uuid, long companyId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(assetListEntryAssetEntryRelId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the asset list entry asset entry rels where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId) {
		return getPersistence().findByAssetListEntryId(assetListEntryId);
	}

	/**
	* Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByAssetListEntryId_First(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetListEntryId_First(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByAssetListEntryId_Last(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetListEntryId_Last(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel[] findByAssetListEntryId_PrevAndNext(
		long assetListEntryAssetEntryRelId, long assetListEntryId,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByAssetListEntryId_PrevAndNext(assetListEntryAssetEntryRelId,
			assetListEntryId, orderByComparator);
	}

	/**
	* Removes all the asset list entry asset entry rels where assetListEntryId = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	*/
	public static void removeByAssetListEntryId(long assetListEntryId) {
		getPersistence().removeByAssetListEntryId(assetListEntryId);
	}

	/**
	* Returns the number of asset list entry asset entry rels where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByAssetListEntryId(long assetListEntryId) {
		return getPersistence().countByAssetListEntryId(assetListEntryId);
	}

	/**
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or throws a {@link NoSuchEntryAssetEntryRelException} if it could not be found.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByA_P(long assetListEntryId,
		int position)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().findByA_P(assetListEntryId, position);
	}

	/**
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByA_P(
		long assetListEntryId, int position) {
		return getPersistence().fetchByA_P(assetListEntryId, position);
	}

	/**
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByA_P(
		long assetListEntryId, int position, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByA_P(assetListEntryId, position, retrieveFromCache);
	}

	/**
	* Removes the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the asset list entry asset entry rel that was removed
	*/
	public static AssetListEntryAssetEntryRel removeByA_P(
		long assetListEntryId, int position)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().removeByA_P(assetListEntryId, position);
	}

	/**
	* Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and position = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByA_P(long assetListEntryId, int position) {
		return getPersistence().countByA_P(assetListEntryId, position);
	}

	/**
	* Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByA_GtP(
		long assetListEntryId, int position) {
		return getPersistence().findByA_GtP(assetListEntryId, position);
	}

	/**
	* Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end) {
		return getPersistence()
				   .findByA_GtP(assetListEntryId, position, start, end);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .findByA_GtP(assetListEntryId, position, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByA_GtP(assetListEntryId, position, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByA_GtP_First(
		long assetListEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByA_GtP_First(assetListEntryId, position,
			orderByComparator);
	}

	/**
	* Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByA_GtP_First(
		long assetListEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByA_GtP_First(assetListEntryId, position,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel findByA_GtP_Last(
		long assetListEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByA_GtP_Last(assetListEntryId, position,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByA_GtP_Last(
		long assetListEntryId, int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByA_GtP_Last(assetListEntryId, position,
			orderByComparator);
	}

	/**
	* Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel[] findByA_GtP_PrevAndNext(
		long assetListEntryAssetEntryRelId, long assetListEntryId,
		int position,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence()
				   .findByA_GtP_PrevAndNext(assetListEntryAssetEntryRelId,
			assetListEntryId, position, orderByComparator);
	}

	/**
	* Removes all the asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	*/
	public static void removeByA_GtP(long assetListEntryId, int position) {
		getPersistence().removeByA_GtP(assetListEntryId, position);
	}

	/**
	* Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the number of matching asset list entry asset entry rels
	*/
	public static int countByA_GtP(long assetListEntryId, int position) {
		return getPersistence().countByA_GtP(assetListEntryId, position);
	}

	/**
	* Caches the asset list entry asset entry rel in the entity cache if it is enabled.
	*
	* @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	*/
	public static void cacheResult(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		getPersistence().cacheResult(assetListEntryAssetEntryRel);
	}

	/**
	* Caches the asset list entry asset entry rels in the entity cache if it is enabled.
	*
	* @param assetListEntryAssetEntryRels the asset list entry asset entry rels
	*/
	public static void cacheResult(
		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels) {
		getPersistence().cacheResult(assetListEntryAssetEntryRels);
	}

	/**
	* Creates a new asset list entry asset entry rel with the primary key. Does not add the asset list entry asset entry rel to the database.
	*
	* @param assetListEntryAssetEntryRelId the primary key for the new asset list entry asset entry rel
	* @return the new asset list entry asset entry rel
	*/
	public static AssetListEntryAssetEntryRel create(
		long assetListEntryAssetEntryRelId) {
		return getPersistence().create(assetListEntryAssetEntryRelId);
	}

	/**
	* Removes the asset list entry asset entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	* @return the asset list entry asset entry rel that was removed
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel remove(
		long assetListEntryAssetEntryRelId)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().remove(assetListEntryAssetEntryRelId);
	}

	public static AssetListEntryAssetEntryRel updateImpl(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {
		return getPersistence().updateImpl(assetListEntryAssetEntryRel);
	}

	/**
	* Returns the asset list entry asset entry rel with the primary key or throws a {@link NoSuchEntryAssetEntryRelException} if it could not be found.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	* @return the asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel findByPrimaryKey(
		long assetListEntryAssetEntryRelId)
		throws com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException {
		return getPersistence().findByPrimaryKey(assetListEntryAssetEntryRelId);
	}

	/**
	* Returns the asset list entry asset entry rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	* @return the asset list entry asset entry rel, or <code>null</code> if a asset list entry asset entry rel with the primary key could not be found
	*/
	public static AssetListEntryAssetEntryRel fetchByPrimaryKey(
		long assetListEntryAssetEntryRelId) {
		return getPersistence().fetchByPrimaryKey(assetListEntryAssetEntryRelId);
	}

	public static java.util.Map<java.io.Serializable, AssetListEntryAssetEntryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset list entry asset entry rels.
	*
	* @return the asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset list entry asset entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findAll(int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset list entry asset entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset list entry asset entry rels
	*/
	public static List<AssetListEntryAssetEntryRel> findAll(int start, int end,
		OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset list entry asset entry rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset list entry asset entry rels.
	*
	* @return the number of asset list entry asset entry rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AssetListEntryAssetEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetListEntryAssetEntryRelPersistence, AssetListEntryAssetEntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetListEntryAssetEntryRelPersistence.class);

		ServiceTracker<AssetListEntryAssetEntryRelPersistence, AssetListEntryAssetEntryRelPersistence> serviceTracker =
			new ServiceTracker<AssetListEntryAssetEntryRelPersistence, AssetListEntryAssetEntryRelPersistence>(bundle.getBundleContext(),
				AssetListEntryAssetEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}