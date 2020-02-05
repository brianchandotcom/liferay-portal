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

import com.liferay.asset.list.exception.NoSuchEntrySegmentsEntryRelException;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset list entry segments entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetListEntrySegmentsEntryRelPersistence
	extends BasePersistence<AssetListEntrySegmentsEntryRel>,
			CTPersistence<AssetListEntrySegmentsEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntrySegmentsEntryRelUtil} to access the asset list entry segments entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry segments entry rels
	 */
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel[] findByUuid_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByUUID_G(
			String uuid, long groupId)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset list entry segments entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	public AssetListEntrySegmentsEntryRel removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry segments entry rels
	 */
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public AssetListEntrySegmentsEntryRel[] findByUuid_C_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Removes all the asset list entry segments entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset list entry segments entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	public java.util.List<AssetListEntrySegmentsEntryRel>
		findByAssetListEntryId(long assetListEntryId);

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
	public java.util.List<AssetListEntrySegmentsEntryRel>
		findByAssetListEntryId(long assetListEntryId, int start, int end);

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
	public java.util.List<AssetListEntrySegmentsEntryRel>
		findByAssetListEntryId(
			long assetListEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntrySegmentsEntryRel>
		findByAssetListEntryId(
			long assetListEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Removes all the asset list entry segments entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns all the asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rels
	 */
	public java.util.List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the first asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the last asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry segments entry rels before and after the current asset list entry segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the current asset list entry segments entry rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel[] findBySegmentsEntryId_PrevAndNext(
			long assetListEntrySegmentsEntryRelId, long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntrySegmentsEntryRel> orderByComparator)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Removes all the asset list entry segments entry rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public void removeBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the number of asset list entry segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countBySegmentsEntryId(long segmentsEntryId);

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId);

	/**
	 * Returns the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByA_S(
		long assetListEntryId, long segmentsEntryId, boolean useFinderCache);

	/**
	 * Removes the asset list entry segments entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the asset list entry segments entry rel that was removed
	 */
	public AssetListEntrySegmentsEntryRel removeByA_S(
			long assetListEntryId, long segmentsEntryId)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the number of asset list entry segments entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry segments entry rels
	 */
	public int countByA_S(long assetListEntryId, long segmentsEntryId);

	/**
	 * Caches the asset list entry segments entry rel in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 */
	public void cacheResult(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel);

	/**
	 * Caches the asset list entry segments entry rels in the entity cache if it is enabled.
	 *
	 * @param assetListEntrySegmentsEntryRels the asset list entry segments entry rels
	 */
	public void cacheResult(
		java.util.List<AssetListEntrySegmentsEntryRel>
			assetListEntrySegmentsEntryRels);

	/**
	 * Creates a new asset list entry segments entry rel with the primary key. Does not add the asset list entry segments entry rel to the database.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key for the new asset list entry segments entry rel
	 * @return the new asset list entry segments entry rel
	 */
	public AssetListEntrySegmentsEntryRel create(
		long assetListEntrySegmentsEntryRelId);

	/**
	 * Removes the asset list entry segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel remove(
			long assetListEntrySegmentsEntryRelId)
		throws NoSuchEntrySegmentsEntryRelException;

	public AssetListEntrySegmentsEntryRel updateImpl(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel);

	/**
	 * Returns the asset list entry segments entry rel with the primary key or throws a <code>NoSuchEntrySegmentsEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel
	 * @throws NoSuchEntrySegmentsEntryRelException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel findByPrimaryKey(
			long assetListEntrySegmentsEntryRelId)
		throws NoSuchEntrySegmentsEntryRelException;

	/**
	 * Returns the asset list entry segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel, or <code>null</code> if a asset list entry segments entry rel with the primary key could not be found
	 */
	public AssetListEntrySegmentsEntryRel fetchByPrimaryKey(
		long assetListEntrySegmentsEntryRelId);

	/**
	 * Returns all the asset list entry segments entry rels.
	 *
	 * @return the asset list entry segments entry rels
	 */
	public java.util.List<AssetListEntrySegmentsEntryRel> findAll();

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntrySegmentsEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntrySegmentsEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entry segments entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset list entry segments entry rels.
	 *
	 * @return the number of asset list entry segments entry rels
	 */
	public int countAll();

}