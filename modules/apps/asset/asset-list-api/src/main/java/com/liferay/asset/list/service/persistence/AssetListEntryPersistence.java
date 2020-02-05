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

import com.liferay.asset.list.exception.NoSuchEntryException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset list entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUtil
 * @generated
 */
@ProviderType
public interface AssetListEntryPersistence
	extends BasePersistence<AssetListEntry>, CTPersistence<AssetListEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryUtil} to access the asset list entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] findByUuid_PrevAndNext(
			long assetListEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the asset list entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the asset list entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry that was removed
	 */
	public AssetListEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of asset list entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] findByUuid_C_PrevAndNext(
			long assetListEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the asset list entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset list entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] findByGroupId_PrevAndNext(
			long assetListEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] filterFindByGroupId_PrevAndNext(
			long assetListEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of asset list entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset list entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of asset list entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset list entries
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_ALEK(long groupId, String assetListEntryKey)
		throws NoSuchEntryException;

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_ALEK(long groupId, String assetListEntryKey);

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_ALEK(
		long groupId, String assetListEntryKey, boolean useFinderCache);

	/**
	 * Removes the asset list entry where groupId = &#63; and assetListEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the asset list entry that was removed
	 */
	public AssetListEntry removeByG_ALEK(long groupId, String assetListEntryKey)
		throws NoSuchEntryException;

	/**
	 * Returns the number of asset list entries where groupId = &#63; and assetListEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the number of matching asset list entries
	 */
	public int countByG_ALEK(long groupId, String assetListEntryKey);

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_T(long groupId, String title)
		throws NoSuchEntryException;

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_T(long groupId, String title);

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_T(
		long groupId, String title, boolean useFinderCache);

	/**
	 * Removes the asset list entry where groupId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the asset list entry that was removed
	 */
	public AssetListEntry removeByG_T(long groupId, String title)
		throws NoSuchEntryException;

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	public int countByG_T(long groupId, String title);

	/**
	 * Returns all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long groupId, String title);

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_LikeT_First(
			long groupId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_LikeT_First(
		long groupId, String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_LikeT_Last(
			long groupId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_LikeT_Last(
		long groupId, String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] findByG_LikeT_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title);

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] filterFindByG_LikeT_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title);

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title);

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entries where groupId = &#63; and title LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 */
	public void removeByG_LikeT(long groupId, String title);

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	public int countByG_LikeT(long groupId, String title);

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	public int countByG_LikeT(long[] groupIds, String title);

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	public int filterCountByG_LikeT(long groupId, String title);

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	public int filterCountByG_LikeT(long[] groupIds, String title);

	/**
	 * Returns all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_TY(long groupId, int type);

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	public java.util.List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_TY_First(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_TY_First(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	public AssetListEntry findByG_TY_Last(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public AssetListEntry fetchByG_TY_Last(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] findByG_TY_PrevAndNext(
			long assetListEntryId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_TY(
		long groupId, int type);

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_TY(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	public java.util.List<AssetListEntry> filterFindByG_TY(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry[] filterFindByG_TY_PrevAndNext(
			long assetListEntryId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the asset list entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_TY(long groupId, int type);

	/**
	 * Returns the number of asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset list entries
	 */
	public int countByG_TY(long groupId, int type);

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	public int filterCountByG_TY(long groupId, int type);

	/**
	 * Caches the asset list entry in the entity cache if it is enabled.
	 *
	 * @param assetListEntry the asset list entry
	 */
	public void cacheResult(AssetListEntry assetListEntry);

	/**
	 * Caches the asset list entries in the entity cache if it is enabled.
	 *
	 * @param assetListEntries the asset list entries
	 */
	public void cacheResult(java.util.List<AssetListEntry> assetListEntries);

	/**
	 * Creates a new asset list entry with the primary key. Does not add the asset list entry to the database.
	 *
	 * @param assetListEntryId the primary key for the new asset list entry
	 * @return the new asset list entry
	 */
	public AssetListEntry create(long assetListEntryId);

	/**
	 * Removes the asset list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry that was removed
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry remove(long assetListEntryId)
		throws NoSuchEntryException;

	public AssetListEntry updateImpl(AssetListEntry assetListEntry);

	/**
	 * Returns the asset list entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry findByPrimaryKey(long assetListEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the asset list entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry, or <code>null</code> if a asset list entry with the primary key could not be found
	 */
	public AssetListEntry fetchByPrimaryKey(long assetListEntryId);

	/**
	 * Returns all the asset list entries.
	 *
	 * @return the asset list entries
	 */
	public java.util.List<AssetListEntry> findAll();

	/**
	 * Returns a range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of asset list entries
	 */
	public java.util.List<AssetListEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entries
	 */
	public java.util.List<AssetListEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entries
	 */
	public java.util.List<AssetListEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset list entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset list entries.
	 *
	 * @return the number of asset list entries
	 */
	public int countAll();

}