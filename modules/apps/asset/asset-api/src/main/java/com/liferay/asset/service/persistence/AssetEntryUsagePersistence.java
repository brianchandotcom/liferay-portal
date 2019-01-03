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

import com.liferay.asset.exception.NoSuchEntryUsageException;
import com.liferay.asset.model.AssetEntryUsage;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the asset entry usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.service.persistence.impl.AssetEntryUsagePersistenceImpl
 * @see AssetEntryUsageUtil
 * @generated
 */
@ProviderType
public interface AssetEntryUsagePersistence extends BasePersistence<AssetEntryUsage> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryUsageUtil} to access the asset entry usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, AssetEntryUsage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the asset entry usages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid(String uuid);

	/**
	* Returns a range of all the asset entry usages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the asset entry usages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry usage in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the first asset entry usage in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the last asset entry usage in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the last asset entry usage in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the asset entry usages before and after the current asset entry usage in the ordered set where uuid = &#63;.
	*
	* @param assetEntryUsageId the primary key of the current asset entry usage
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage[] findByUuid_PrevAndNext(long assetEntryUsageId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Removes all the asset entry usages where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of asset entry usages where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset entry usages
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the asset entry usage where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryUsageException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException;

	/**
	* Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the asset entry usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the asset entry usage where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset entry usage that was removed
	*/
	public AssetEntryUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryUsageException;

	/**
	* Returns the number of asset entry usages where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset entry usages
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the asset entry usages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the first asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the last asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the last asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the asset entry usages before and after the current asset entry usage in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param assetEntryUsageId the primary key of the current asset entry usage
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage[] findByUuid_C_PrevAndNext(long assetEntryUsageId,
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Removes all the asset entry usages where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of asset entry usages where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset entry usages
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the asset entry usages where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByAssetEntryId(long assetEntryId);

	/**
	* Returns a range of all the asset entry usages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the first asset entry usage in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the last asset entry usage in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryUsageId the primary key of the current asset entry usage
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage[] findByAssetEntryId_PrevAndNext(
		long assetEntryUsageId, long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Removes all the asset entry usages where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public void removeByAssetEntryId(long assetEntryId);

	/**
	* Returns the number of asset entry usages where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset entry usages
	*/
	public int countByAssetEntryId(long assetEntryId);

	/**
	* Returns all the asset entry usages where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @return the matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByA_C(long assetEntryId,
		long classNameId);

	/**
	* Returns a range of all the asset entry usages where assetEntryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByA_C(long assetEntryId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByA_C(long assetEntryId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages where assetEntryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByA_C(long assetEntryId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByA_C_First(long assetEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the first asset entry usage in the ordered set where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByA_C_First(long assetEntryId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByA_C_Last(long assetEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the last asset entry usage in the ordered set where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByA_C_Last(long assetEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the asset entry usages before and after the current asset entry usage in the ordered set where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryUsageId the primary key of the current asset entry usage
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage[] findByA_C_PrevAndNext(long assetEntryUsageId,
		long assetEntryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Removes all the asset entry usages where assetEntryId = &#63; and classNameId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	*/
	public void removeByA_C(long assetEntryId, long classNameId);

	/**
	* Returns the number of asset entry usages where assetEntryId = &#63; and classNameId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @return the number of matching asset entry usages
	*/
	public int countByA_C(long assetEntryId, long classNameId);

	/**
	* Returns all the asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByC_C_P(long classNameId,
		long classPK, String portletId);

	/**
	* Returns a range of all the asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByC_C_P(long classNameId,
		long classPK, String portletId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByC_C_P(long classNameId,
		long classPK, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findByC_C_P(long classNameId,
		long classPK, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry usage in the ordered set where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByC_C_P_First(long classNameId, long classPK,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the first asset entry usage in the ordered set where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByC_C_P_First(long classNameId, long classPK,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the last asset entry usage in the ordered set where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByC_C_P_Last(long classNameId, long classPK,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Returns the last asset entry usage in the ordered set where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByC_C_P_Last(long classNameId, long classPK,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns the asset entry usages before and after the current asset entry usage in the ordered set where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param assetEntryUsageId the primary key of the current asset entry usage
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage[] findByC_C_P_PrevAndNext(long assetEntryUsageId,
		long classNameId, long classPK, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator)
		throws NoSuchEntryUsageException;

	/**
	* Removes all the asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	*/
	public void removeByC_C_P(long classNameId, long classPK, String portletId);

	/**
	* Returns the number of asset entry usages where classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the number of matching asset entry usages
	*/
	public int countByC_C_P(long classNameId, long classPK, String portletId);

	/**
	* Returns the asset entry usage where assetEntryId = &#63; and classNameId = &#63; and classPK = &#63; and portletId = &#63; or throws a {@link NoSuchEntryUsageException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the matching asset entry usage
	* @throws NoSuchEntryUsageException if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage findByA_C_C_P(long assetEntryId, long classNameId,
		long classPK, String portletId) throws NoSuchEntryUsageException;

	/**
	* Returns the asset entry usage where assetEntryId = &#63; and classNameId = &#63; and classPK = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByA_C_C_P(long assetEntryId, long classNameId,
		long classPK, String portletId);

	/**
	* Returns the asset entry usage where assetEntryId = &#63; and classNameId = &#63; and classPK = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public AssetEntryUsage fetchByA_C_C_P(long assetEntryId, long classNameId,
		long classPK, String portletId, boolean retrieveFromCache);

	/**
	* Removes the asset entry usage where assetEntryId = &#63; and classNameId = &#63; and classPK = &#63; and portletId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the asset entry usage that was removed
	*/
	public AssetEntryUsage removeByA_C_C_P(long assetEntryId, long classNameId,
		long classPK, String portletId) throws NoSuchEntryUsageException;

	/**
	* Returns the number of asset entry usages where assetEntryId = &#63; and classNameId = &#63; and classPK = &#63; and portletId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param portletId the portlet ID
	* @return the number of matching asset entry usages
	*/
	public int countByA_C_C_P(long assetEntryId, long classNameId,
		long classPK, String portletId);

	/**
	* Caches the asset entry usage in the entity cache if it is enabled.
	*
	* @param assetEntryUsage the asset entry usage
	*/
	public void cacheResult(AssetEntryUsage assetEntryUsage);

	/**
	* Caches the asset entry usages in the entity cache if it is enabled.
	*
	* @param assetEntryUsages the asset entry usages
	*/
	public void cacheResult(java.util.List<AssetEntryUsage> assetEntryUsages);

	/**
	* Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	*
	* @param assetEntryUsageId the primary key for the new asset entry usage
	* @return the new asset entry usage
	*/
	public AssetEntryUsage create(long assetEntryUsageId);

	/**
	* Removes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryUsageId the primary key of the asset entry usage
	* @return the asset entry usage that was removed
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage remove(long assetEntryUsageId)
		throws NoSuchEntryUsageException;

	public AssetEntryUsage updateImpl(AssetEntryUsage assetEntryUsage);

	/**
	* Returns the asset entry usage with the primary key or throws a {@link NoSuchEntryUsageException} if it could not be found.
	*
	* @param assetEntryUsageId the primary key of the asset entry usage
	* @return the asset entry usage
	* @throws NoSuchEntryUsageException if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage findByPrimaryKey(long assetEntryUsageId)
		throws NoSuchEntryUsageException;

	/**
	* Returns the asset entry usage with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryUsageId the primary key of the asset entry usage
	* @return the asset entry usage, or <code>null</code> if a asset entry usage with the primary key could not be found
	*/
	public AssetEntryUsage fetchByPrimaryKey(long assetEntryUsageId);

	/**
	* Returns all the asset entry usages.
	*
	* @return the asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findAll();

	/**
	* Returns a range of all the asset entry usages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset entry usages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry usages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entry usages
	*/
	public java.util.List<AssetEntryUsage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryUsage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset entry usages from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset entry usages.
	*
	* @return the number of asset entry usages
	*/
	public int countAll();

	@Override
	public Set<String> getBadColumnNames();
}