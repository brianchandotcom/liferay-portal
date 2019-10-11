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

package com.liferay.asset.auto.tagger.service.persistence;

import com.liferay.asset.auto.tagger.exception.NoSuchEntryException;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset auto tagger entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryUtil
 * @generated
 */
@ProviderType
public interface AssetAutoTaggerEntryPersistence
	extends BasePersistence<AssetAutoTaggerEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetAutoTaggerEntryUtil} to access the asset auto tagger entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId);

	/**
	 * Returns a range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry findByAssetEntryId_First(
			long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry findByAssetEntryId_Last(
			long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByAssetEntryId_Last(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public AssetAutoTaggerEntry[] findByAssetEntryId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the asset auto tagger entries where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	public void removeByAssetEntryId(long assetEntryId);

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset auto tagger entries
	 */
	public int countByAssetEntryId(long assetEntryId);

	/**
	 * Returns all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId);

	/**
	 * Returns a range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end);

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry findByAssetTagId_First(
			long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByAssetTagId_First(
		long assetTagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry findByAssetTagId_Last(
			long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByAssetTagId_Last(
		long assetTagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public AssetAutoTaggerEntry[] findByAssetTagId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetAutoTaggerEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the asset auto tagger entries where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	public void removeByAssetTagId(long assetTagId);

	/**
	 * Returns the number of asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	public int countByAssetTagId(long assetTagId);

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry findByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException;

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByA_A(long assetEntryId, long assetTagId);

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public AssetAutoTaggerEntry fetchByA_A(
		long assetEntryId, long assetTagId, boolean useFinderCache);

	/**
	 * Removes the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the asset auto tagger entry that was removed
	 */
	public AssetAutoTaggerEntry removeByA_A(long assetEntryId, long assetTagId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63; and assetTagId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	public int countByA_A(long assetEntryId, long assetTagId);

	/**
	 * Caches the asset auto tagger entry in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 */
	public void cacheResult(AssetAutoTaggerEntry assetAutoTaggerEntry);

	/**
	 * Caches the asset auto tagger entries in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntries the asset auto tagger entries
	 */
	public void cacheResult(
		java.util.List<AssetAutoTaggerEntry> assetAutoTaggerEntries);

	/**
	 * Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	 *
	 * @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	 * @return the new asset auto tagger entry
	 */
	public AssetAutoTaggerEntry create(long assetAutoTaggerEntryId);

	/**
	 * Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public AssetAutoTaggerEntry remove(long assetAutoTaggerEntryId)
		throws NoSuchEntryException;

	public AssetAutoTaggerEntry updateImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry);

	/**
	 * Returns the asset auto tagger entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public AssetAutoTaggerEntry findByPrimaryKey(long assetAutoTaggerEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the asset auto tagger entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry, or <code>null</code> if a asset auto tagger entry with the primary key could not be found
	 */
	public AssetAutoTaggerEntry fetchByPrimaryKey(long assetAutoTaggerEntryId);

	/**
	 * Returns all the asset auto tagger entries.
	 *
	 * @return the asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findAll();

	/**
	 * Returns a range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset auto tagger entries
	 */
	public java.util.List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetAutoTaggerEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset auto tagger entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset auto tagger entries.
	 *
	 * @return the number of asset auto tagger entries
	 */
	public int countAll();

}