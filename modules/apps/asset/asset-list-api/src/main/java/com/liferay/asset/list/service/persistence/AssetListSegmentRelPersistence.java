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

import com.liferay.asset.list.exception.NoSuchSegmentRelException;
import com.liferay.asset.list.model.AssetListSegmentRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset list segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListSegmentRelUtil
 * @generated
 */
@ProviderType
public interface AssetListSegmentRelPersistence
	extends BasePersistence<AssetListSegmentRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListSegmentRelUtil} to access the asset list segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list segment rels
	 */
	public java.util.List<AssetListSegmentRel> findByUuid(String uuid);

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
	public java.util.List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public java.util.List<AssetListSegmentRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel[] findByUuid_PrevAndNext(
			long assetListSegmentRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Removes all the asset list segment rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset list segment rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list segment rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByUUID_G(String uuid, long groupId)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset list segment rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the asset list segment rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list segment rel that was removed
	 */
	public AssetListSegmentRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list segment rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list segment rels
	 */
	public java.util.List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public java.util.List<AssetListSegmentRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the first asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the last asset list segment rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public AssetListSegmentRel[] findByUuid_C_PrevAndNext(
			long assetListSegmentRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Removes all the asset list segment rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset list segment rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list segment rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list segment rels
	 */
	public java.util.List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId);

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
	public java.util.List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end);

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
	public java.util.List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public java.util.List<AssetListSegmentRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByAssetListEntryId_First(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the first asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByAssetListEntryId_Last(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the last asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel[] findByAssetListEntryId_PrevAndNext(
			long assetListSegmentRelId, long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Removes all the asset list segment rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list segment rels
	 */
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns all the asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rels
	 */
	public java.util.List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId);

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
	public java.util.List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end);

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
	public java.util.List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public java.util.List<AssetListSegmentRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the first asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the last asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

	/**
	 * Returns the asset list segment rels before and after the current asset list segment rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListSegmentRelId the primary key of the current asset list segment rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel[] findBySegmentsEntryId_PrevAndNext(
			long assetListSegmentRelId, long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	 * Removes all the asset list segment rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public void removeBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the number of asset list segment rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	public int countBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel
	 * @throws NoSuchSegmentRelException if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId);

	/**
	 * Returns the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	public AssetListSegmentRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId, boolean retrieveFromCache);

	/**
	 * Removes the asset list segment rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list segment rel that was removed
	 */
	public AssetListSegmentRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the number of asset list segment rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list segment rels
	 */
	public int countByA_S(long assetListEntryId, long segmentsEntryId);

	/**
	 * Caches the asset list segment rel in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 */
	public void cacheResult(AssetListSegmentRel assetListSegmentRel);

	/**
	 * Caches the asset list segment rels in the entity cache if it is enabled.
	 *
	 * @param assetListSegmentRels the asset list segment rels
	 */
	public void cacheResult(
		java.util.List<AssetListSegmentRel> assetListSegmentRels);

	/**
	 * Creates a new asset list segment rel with the primary key. Does not add the asset list segment rel to the database.
	 *
	 * @param assetListSegmentRelId the primary key for the new asset list segment rel
	 * @return the new asset list segment rel
	 */
	public AssetListSegmentRel create(long assetListSegmentRelId);

	/**
	 * Removes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel remove(long assetListSegmentRelId)
		throws NoSuchSegmentRelException;

	public AssetListSegmentRel updateImpl(
		AssetListSegmentRel assetListSegmentRel);

	/**
	 * Returns the asset list segment rel with the primary key or throws a <code>NoSuchSegmentRelException</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws NoSuchSegmentRelException if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel findByPrimaryKey(long assetListSegmentRelId)
		throws NoSuchSegmentRelException;

	/**
	 * Returns the asset list segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel, or <code>null</code> if a asset list segment rel with the primary key could not be found
	 */
	public AssetListSegmentRel fetchByPrimaryKey(long assetListSegmentRelId);

	/**
	 * Returns all the asset list segment rels.
	 *
	 * @return the asset list segment rels
	 */
	public java.util.List<AssetListSegmentRel> findAll();

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
	public java.util.List<AssetListSegmentRel> findAll(int start, int end);

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
	public java.util.List<AssetListSegmentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator);

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
	public java.util.List<AssetListSegmentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListSegmentRel>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the asset list segment rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset list segment rels.
	 *
	 * @return the number of asset list segment rels
	 */
	public int countAll();

}