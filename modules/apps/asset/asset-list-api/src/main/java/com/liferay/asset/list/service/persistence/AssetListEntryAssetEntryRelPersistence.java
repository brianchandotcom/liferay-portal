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

import com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset list entry asset entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetListEntryAssetEntryRelPersistence
	extends BasePersistence<AssetListEntryAssetEntryRel>,
			CTPersistence<AssetListEntryAssetEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryAssetEntryRelUtil} to access the asset list entry asset entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel[] findByUuid_PrevAndNext(
			long assetListEntryAssetEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Removes all the asset list entry asset entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset list entry asset entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry asset entry rel that was removed
	 */
	public AssetListEntryAssetEntryRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

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
	public AssetListEntryAssetEntryRel[] findByUuid_C_PrevAndNext(
			long assetListEntryAssetEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Removes all the asset list entry asset entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset list entry asset entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId);

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByAssetListEntryId_First(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByAssetListEntryId_Last(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel[] findByAssetListEntryId_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId);

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S(
		long assetListEntryId, long segmentsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByA_S_First(
			long assetListEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_First(
		long assetListEntryId, long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByA_S_Last(
			long assetListEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_Last(
		long assetListEntryId, long segmentsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel[] findByA_S_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			long segmentsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 */
	public void removeByA_S(long assetListEntryId, long segmentsEntryId);

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByA_S(long assetListEntryId, long segmentsEntryId);

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByA_S_P(
			long assetListEntryId, long segmentsEntryId, int position)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position);

	/**
	 * Returns the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position,
		boolean useFinderCache);

	/**
	 * Removes the asset list entry asset entry rel where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the asset list entry asset entry rel that was removed
	 */
	public AssetListEntryAssetEntryRel removeByA_S_P(
			long assetListEntryId, long segmentsEntryId, int position)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByA_S_P(
		long assetListEntryId, long segmentsEntryId, int position);

	/**
	 * Returns all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position);

	/**
	 * Returns a range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByA_S_GtP_First(
			long assetListEntryId, long segmentsEntryId, int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the first asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_GtP_First(
		long assetListEntryId, long segmentsEntryId, int position,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel findByA_S_GtP_Last(
			long assetListEntryId, long segmentsEntryId, int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the last asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByA_S_GtP_Last(
		long assetListEntryId, long segmentsEntryId, int position,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns the asset list entry asset entry rels before and after the current asset list entry asset entry rel in the ordered set where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the current asset list entry asset entry rel
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel[] findByA_S_GtP_PrevAndNext(
			long assetListEntryAssetEntryRelId, long assetListEntryId,
			long segmentsEntryId, int position,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryAssetEntryRel> orderByComparator)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Removes all the asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 */
	public void removeByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position);

	/**
	 * Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and segmentsEntryId = &#63; and position &gt; &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param segmentsEntryId the segments entry ID
	 * @param position the position
	 * @return the number of matching asset list entry asset entry rels
	 */
	public int countByA_S_GtP(
		long assetListEntryId, long segmentsEntryId, int position);

	/**
	 * Caches the asset list entry asset entry rel in the entity cache if it is enabled.
	 *
	 * @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	 */
	public void cacheResult(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel);

	/**
	 * Caches the asset list entry asset entry rels in the entity cache if it is enabled.
	 *
	 * @param assetListEntryAssetEntryRels the asset list entry asset entry rels
	 */
	public void cacheResult(
		java.util.List<AssetListEntryAssetEntryRel>
			assetListEntryAssetEntryRels);

	/**
	 * Creates a new asset list entry asset entry rel with the primary key. Does not add the asset list entry asset entry rel to the database.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key for the new asset list entry asset entry rel
	 * @return the new asset list entry asset entry rel
	 */
	public AssetListEntryAssetEntryRel create(
		long assetListEntryAssetEntryRelId);

	/**
	 * Removes the asset list entry asset entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was removed
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel remove(
			long assetListEntryAssetEntryRelId)
		throws NoSuchEntryAssetEntryRelException;

	public AssetListEntryAssetEntryRel updateImpl(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel);

	/**
	 * Returns the asset list entry asset entry rel with the primary key or throws a <code>NoSuchEntryAssetEntryRelException</code> if it could not be found.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel
	 * @throws NoSuchEntryAssetEntryRelException if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel findByPrimaryKey(
			long assetListEntryAssetEntryRelId)
		throws NoSuchEntryAssetEntryRelException;

	/**
	 * Returns the asset list entry asset entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel, or <code>null</code> if a asset list entry asset entry rel with the primary key could not be found
	 */
	public AssetListEntryAssetEntryRel fetchByPrimaryKey(
		long assetListEntryAssetEntryRelId);

	/**
	 * Returns all the asset list entry asset entry rels.
	 *
	 * @return the asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findAll();

	/**
	 * Returns a range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry asset entry rels
	 */
	public java.util.List<AssetListEntryAssetEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetListEntryAssetEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entry asset entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset list entry asset entry rels.
	 *
	 * @return the number of asset list entry asset entry rels
	 */
	public int countAll();

}