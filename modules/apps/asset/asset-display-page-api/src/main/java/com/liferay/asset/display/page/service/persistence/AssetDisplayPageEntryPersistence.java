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

package com.liferay.asset.display.page.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset display page entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.display.page.service.persistence.impl.AssetDisplayPageEntryPersistenceImpl
 * @see AssetDisplayPageEntryUtil
 * @generated
 */
@ProviderType
public interface AssetDisplayPageEntryPersistence extends BasePersistence<AssetDisplayPageEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetDisplayPageEntryUtil} to access the asset display page entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset display page entries where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findByAssetEntryId(
		long assetEntryId);

	/**
	* Returns a range of all the asset display page entries where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @return the range of matching asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset display page entries where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset display page entries where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset display page entry in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry findByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException;

	/**
	* Returns the first asset display page entry in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry fetchByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator);

	/**
	* Returns the last asset display page entry in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry findByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException;

	/**
	* Returns the last asset display page entry in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry fetchByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator);

	/**
	* Returns the asset display page entries before and after the current asset display page entry in the ordered set where assetEntryId = &#63;.
	*
	* @param assetDisplayPageEntryId the primary key of the current asset display page entry
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display page entry
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public AssetDisplayPageEntry[] findByAssetEntryId_PrevAndNext(
		long assetDisplayPageEntryId, long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws NoSuchDisplayPageEntryException;

	/**
	* Removes all the asset display page entries where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public void removeByAssetEntryId(long assetEntryId);

	/**
	* Returns the number of asset display page entries where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset display page entries
	*/
	public int countByAssetEntryId(long assetEntryId);

	/**
	* Returns the asset display page entry where assetEntryId = &#63; and layoutId = &#63; or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry findByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageEntryException;

	/**
	* Returns the asset display page entry where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry fetchByA_L(long assetEntryId, long layoutId);

	/**
	* Returns the asset display page entry where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public AssetDisplayPageEntry fetchByA_L(long assetEntryId, long layoutId,
		boolean retrieveFromCache);

	/**
	* Removes the asset display page entry where assetEntryId = &#63; and layoutId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the asset display page entry that was removed
	*/
	public AssetDisplayPageEntry removeByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageEntryException;

	/**
	* Returns the number of asset display page entries where assetEntryId = &#63; and layoutId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the number of matching asset display page entries
	*/
	public int countByA_L(long assetEntryId, long layoutId);

	/**
	* Caches the asset display page entry in the entity cache if it is enabled.
	*
	* @param assetDisplayPageEntry the asset display page entry
	*/
	public void cacheResult(AssetDisplayPageEntry assetDisplayPageEntry);

	/**
	* Caches the asset display page entries in the entity cache if it is enabled.
	*
	* @param assetDisplayPageEntries the asset display page entries
	*/
	public void cacheResult(
		java.util.List<AssetDisplayPageEntry> assetDisplayPageEntries);

	/**
	* Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	*
	* @param assetDisplayPageEntryId the primary key for the new asset display page entry
	* @return the new asset display page entry
	*/
	public AssetDisplayPageEntry create(long assetDisplayPageEntryId);

	/**
	* Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry that was removed
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public AssetDisplayPageEntry remove(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException;

	public AssetDisplayPageEntry updateImpl(
		AssetDisplayPageEntry assetDisplayPageEntry);

	/**
	* Returns the asset display page entry with the primary key or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public AssetDisplayPageEntry findByPrimaryKey(long assetDisplayPageEntryId)
		throws NoSuchDisplayPageEntryException;

	/**
	* Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	*/
	public AssetDisplayPageEntry fetchByPrimaryKey(long assetDisplayPageEntryId);

	@Override
	public java.util.Map<java.io.Serializable, AssetDisplayPageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset display page entries.
	*
	* @return the asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findAll();

	/**
	* Returns a range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @return the range of asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset display page entries
	*/
	public java.util.List<AssetDisplayPageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset display page entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset display page entries.
	*
	* @return the number of asset display page entries
	*/
	public int countAll();
}