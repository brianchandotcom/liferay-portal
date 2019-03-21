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

import com.liferay.asset.list.exception.NoSuchAssetEntryAssetListEntryRelException;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset entry asset list entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetListEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetEntryAssetListEntryRelPersistence
	extends BasePersistence<AssetEntryAssetListEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryAssetListEntryRelUtil} to access the asset entry asset list entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel[] findByUuid_PrevAndNext(
			long assetEntryAssetListEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Removes all the asset entry asset list entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the asset entry asset list entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset entry asset list entry rel that was removed
	 */
	public AssetEntryAssetListEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel[] findByUuid_C_PrevAndNext(
			long assetEntryAssetListEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Removes all the asset entry asset list entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset entry asset list entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId);

	/**
	 * Returns a range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetEntryAssetListEntryRelId, long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Removes all the asset entry asset list entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByA_P(
			long assetListEntryId, int position)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByA_P(
		long assetListEntryId, int position);

	/**
	 * Returns the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByA_P(
		long assetListEntryId, int position, boolean retrieveFromCache);

	/**
	 * Removes the asset entry asset list entry rel where assetListEntryId = &#63; and position = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the asset entry asset list entry rel that was removed
	 */
	public AssetEntryAssetListEntryRel removeByA_P(
			long assetListEntryId, int position)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63; and position = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByA_P(long assetListEntryId, int position);

	/**
	 * Returns all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position);

	/**
	 * Returns a range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findByA_GtP(
		long assetListEntryId, int position, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByA_GtP_First(
			long assetListEntryId, int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the first asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByA_GtP_First(
		long assetListEntryId, int position,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel findByA_GtP_Last(
			long assetListEntryId, int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the last asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByA_GtP_Last(
		long assetListEntryId, int position,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns the asset entry asset list entry rels before and after the current asset entry asset list entry rel in the ordered set where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the current asset entry asset list entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel[] findByA_GtP_PrevAndNext(
			long assetEntryAssetListEntryRelId, long assetListEntryId,
			int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetListEntryRel> orderByComparator)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Removes all the asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 */
	public void removeByA_GtP(long assetListEntryId, int position);

	/**
	 * Returns the number of asset entry asset list entry rels where assetListEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param position the position
	 * @return the number of matching asset entry asset list entry rels
	 */
	public int countByA_GtP(long assetListEntryId, int position);

	/**
	 * Caches the asset entry asset list entry rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 */
	public void cacheResult(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel);

	/**
	 * Caches the asset entry asset list entry rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetListEntryRels the asset entry asset list entry rels
	 */
	public void cacheResult(
		java.util.List<AssetEntryAssetListEntryRel>
			assetEntryAssetListEntryRels);

	/**
	 * Creates a new asset entry asset list entry rel with the primary key. Does not add the asset entry asset list entry rel to the database.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key for the new asset entry asset list entry rel
	 * @return the new asset entry asset list entry rel
	 */
	public AssetEntryAssetListEntryRel create(
		long assetEntryAssetListEntryRelId);

	/**
	 * Removes the asset entry asset list entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel remove(
			long assetEntryAssetListEntryRelId)
		throws NoSuchAssetEntryAssetListEntryRelException;

	public AssetEntryAssetListEntryRel updateImpl(
		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel);

	/**
	 * Returns the asset entry asset list entry rel with the primary key or throws a <code>NoSuchAssetEntryAssetListEntryRelException</code> if it could not be found.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel
	 * @throws NoSuchAssetEntryAssetListEntryRelException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel findByPrimaryKey(
			long assetEntryAssetListEntryRelId)
		throws NoSuchAssetEntryAssetListEntryRelException;

	/**
	 * Returns the asset entry asset list entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel, or <code>null</code> if a asset entry asset list entry rel with the primary key could not be found
	 */
	public AssetEntryAssetListEntryRel fetchByPrimaryKey(
		long assetEntryAssetListEntryRelId);

	/**
	 * Returns all the asset entry asset list entry rels.
	 *
	 * @return the asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findAll();

	/**
	 * Returns a range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry asset list entry rels
	 */
	public java.util.List<AssetEntryAssetListEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetListEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the asset entry asset list entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset entry asset list entry rels.
	 *
	 * @return the number of asset entry asset list entry rels
	 */
	public int countAll();

}