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

import com.liferay.asset.list.exception.NoSuchEntryAssetEntryRelException;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset list entry asset entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.list.service.persistence.impl.AssetListEntryAssetEntryRelPersistenceImpl
 * @see AssetListEntryAssetEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetListEntryAssetEntryRelPersistence extends BasePersistence<AssetListEntryAssetEntryRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryAssetEntryRelUtil} to access the asset list entry asset entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntryAssetEntryRel> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache);

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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator);

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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator);

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
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator)
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
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or throws a {@link NoSuchEntryAssetEntryRelException} if it could not be found.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the matching asset list entry asset entry rel
	* @throws NoSuchEntryAssetEntryRelException if a matching asset list entry asset entry rel could not be found
	*/
	public AssetListEntryAssetEntryRel findByA_P(long assetListEntryId,
		int position) throws NoSuchEntryAssetEntryRelException;

	/**
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public AssetListEntryAssetEntryRel fetchByA_P(long assetListEntryId,
		int position);

	/**
	* Returns the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	*/
	public AssetListEntryAssetEntryRel fetchByA_P(long assetListEntryId,
		int position, boolean retrieveFromCache);

	/**
	* Removes the asset list entry asset entry rel where assetListEntryId = &#63; and position = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the asset list entry asset entry rel that was removed
	*/
	public AssetListEntryAssetEntryRel removeByA_P(long assetListEntryId,
		int position) throws NoSuchEntryAssetEntryRelException;

	/**
	* Returns the number of asset list entry asset entry rels where assetListEntryId = &#63; and position = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param position the position
	* @return the number of matching asset list entry asset entry rels
	*/
	public int countByA_P(long assetListEntryId, int position);

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
		java.util.List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels);

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
	* Returns the asset list entry asset entry rel with the primary key or throws a {@link NoSuchEntryAssetEntryRelException} if it could not be found.
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

	@Override
	public java.util.Map<java.io.Serializable, AssetListEntryAssetEntryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryAssetEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry asset entry rels
	* @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	* @return the range of asset list entry asset entry rels
	*/
	public java.util.List<AssetListEntryAssetEntryRel> findAll(int start,
		int end);

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
	public java.util.List<AssetListEntryAssetEntryRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator);

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
	public java.util.List<AssetListEntryAssetEntryRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryAssetEntryRel> orderByComparator,
		boolean retrieveFromCache);

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