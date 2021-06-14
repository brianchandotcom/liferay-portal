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

import com.liferay.asset.list.exception.NoSuchEntryUsageException;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset list entry usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUsageUtil
 * @generated
 */
@ProviderType
public interface AssetListEntryUsagePersistence
	extends BasePersistence<AssetListEntryUsage>,
			CTPersistence<AssetListEntryUsage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryUsageUtil} to access the asset list entry usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset list entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByUuid_PrevAndNext(
			long assetListEntryUsageId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset list entry usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entry usages
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset list entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset list entry usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry usage that was removed
	 */
	public AssetListEntryUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the number of asset list entry usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entry usages
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByUuid_C_PrevAndNext(
			long assetListEntryUsageId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset list entry usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entry usages
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId);

	/**
	 * Returns a range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByAssetListEntryId_First(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByAssetListEntryId_Last(
			long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param assetListEntryId the asset list entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByAssetListEntryId_PrevAndNext(
			long assetListEntryUsageId, long assetListEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where assetListEntryId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 */
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns the number of asset list entry usages where assetListEntryId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @return the number of matching asset list entry usages
	 */
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	 * Returns all the asset list entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByPlid(long plid);

	/**
	 * Returns a range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByPlid(
		long plid, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where plid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByPlid_PrevAndNext(
			long assetListEntryUsageId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of asset list entry usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId);

	/**
	 * Returns a range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByA_C(
		long assetListEntryId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByA_C_First(
			long assetListEntryId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByA_C_First(
		long assetListEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByA_C_Last(
			long assetListEntryId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByA_C_Last(
		long assetListEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByA_C_PrevAndNext(
			long assetListEntryUsageId, long assetListEntryId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where assetListEntryId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 */
	public void removeByA_C(long assetListEntryId, long classNameId);

	/**
	 * Returns the number of asset list entry usages where assetListEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param assetListEntryId the asset list entry ID
	 * @param classNameId the class name ID
	 * @return the number of matching asset list entry usages
	 */
	public int countByA_C(long assetListEntryId, long classNameId);

	/**
	 * Returns all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key);

	/**
	 * Returns a range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K(
		long groupId, long classNameId, String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByG_C_K_First(
			long groupId, long classNameId, String key,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_K_First(
		long groupId, long classNameId, String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByG_C_K_Last(
			long groupId, long classNameId, String key,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_K_Last(
		long groupId, long classNameId, String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByG_C_K_PrevAndNext(
			long assetListEntryUsageId, long groupId, long classNameId,
			String key,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 */
	public void removeByG_C_K(long groupId, long classNameId, String key);

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @return the number of matching asset list entry usages
	 */
	public int countByG_C_K(long groupId, long classNameId, String key);

	/**
	 * Returns all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns a range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByCK_CT_P_PrevAndNext(
			long assetListEntryUsageId, String containerKey, long containerType,
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns the number of asset list entry usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	public int countByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @return the matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type);

	/**
	 * Returns a range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end);

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findByG_C_K_T(
		long groupId, long classNameId, String key, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByG_C_K_T_First(
			long groupId, long classNameId, String key, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the first asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_K_T_First(
		long groupId, long classNameId, String key, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByG_C_K_T_Last(
			long groupId, long classNameId, String key, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the last asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_K_T_Last(
		long groupId, long classNameId, String key, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns the asset list entry usages before and after the current asset list entry usage in the ordered set where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param assetListEntryUsageId the primary key of the current asset list entry usage
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage[] findByG_C_K_T_PrevAndNext(
			long assetListEntryUsageId, long groupId, long classNameId,
			String key, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetListEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	 * Removes all the asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 */
	public void removeByG_C_K_T(
		long groupId, long classNameId, String key, int type);

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @return the number of matching asset list entry usages
	 */
	public int countByG_C_K_T(
		long groupId, long classNameId, String key, int type);

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the matching asset list entry usage
	 * @throws NoSuchEntryUsageException if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage findByG_C_CK_CT_K_P(
			long groupId, long classNameId, String containerKey,
			long containerType, String key, long plid)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid);

	/**
	 * Returns the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public AssetListEntryUsage fetchByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid, boolean useFinderCache);

	/**
	 * Removes the asset list entry usage where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the asset list entry usage that was removed
	 */
	public AssetListEntryUsage removeByG_C_CK_CT_K_P(
			long groupId, long classNameId, String containerKey,
			long containerType, String key, long plid)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the number of asset list entry usages where groupId = &#63; and classNameId = &#63; and containerKey = &#63; and containerType = &#63; and key = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param key the key
	 * @param plid the plid
	 * @return the number of matching asset list entry usages
	 */
	public int countByG_C_CK_CT_K_P(
		long groupId, long classNameId, String containerKey, long containerType,
		String key, long plid);

	/**
	 * Caches the asset list entry usage in the entity cache if it is enabled.
	 *
	 * @param assetListEntryUsage the asset list entry usage
	 */
	public void cacheResult(AssetListEntryUsage assetListEntryUsage);

	/**
	 * Caches the asset list entry usages in the entity cache if it is enabled.
	 *
	 * @param assetListEntryUsages the asset list entry usages
	 */
	public void cacheResult(
		java.util.List<AssetListEntryUsage> assetListEntryUsages);

	/**
	 * Creates a new asset list entry usage with the primary key. Does not add the asset list entry usage to the database.
	 *
	 * @param assetListEntryUsageId the primary key for the new asset list entry usage
	 * @return the new asset list entry usage
	 */
	public AssetListEntryUsage create(long assetListEntryUsageId);

	/**
	 * Removes the asset list entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage that was removed
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage remove(long assetListEntryUsageId)
		throws NoSuchEntryUsageException;

	public AssetListEntryUsage updateImpl(
		AssetListEntryUsage assetListEntryUsage);

	/**
	 * Returns the asset list entry usage with the primary key or throws a <code>NoSuchEntryUsageException</code> if it could not be found.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage
	 * @throws NoSuchEntryUsageException if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage findByPrimaryKey(long assetListEntryUsageId)
		throws NoSuchEntryUsageException;

	/**
	 * Returns the asset list entry usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage, or <code>null</code> if a asset list entry usage with the primary key could not be found
	 */
	public AssetListEntryUsage fetchByPrimaryKey(long assetListEntryUsageId);

	/**
	 * Returns all the asset list entry usages.
	 *
	 * @return the asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findAll();

	/**
	 * Returns a range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entry usages
	 */
	public java.util.List<AssetListEntryUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entry usages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset list entry usages.
	 *
	 * @return the number of asset list entry usages
	 */
	public int countAll();

}