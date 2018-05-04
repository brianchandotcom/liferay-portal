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

package com.liferay.asset.entry.rel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset entry display page rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryDisplayPageRelPersistenceImpl
 * @see AssetEntryDisplayPageRelUtil
 * @generated
 */
@ProviderType
public interface AssetEntryDisplayPageRelPersistence extends BasePersistence<AssetEntryDisplayPageRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryDisplayPageRelUtil} to access the asset entry display page rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset entry display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId);

	/**
	* Returns a range of all the asset entry display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @return the range of matching asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException;

	/**
	* Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator);

	/**
	* Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel findByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException;

	/**
	* Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator);

	/**
	* Returns the asset entry display page rels before and after the current asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryDisplayPageRelId the primary key of the current asset entry display page rel
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public AssetEntryDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryDisplayPageRelId, long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws NoSuchEntryDisplayPageRelException;

	/**
	* Removes all the asset entry display page rels where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public void removeByAssetEntryId(long assetEntryId);

	/**
	* Returns the number of asset entry display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset entry display page rels
	*/
	public int countByAssetEntryId(long assetEntryId);

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel findByA_D(long assetEntryId,
		long displayPageId) throws NoSuchEntryDisplayPageRelException;

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId);

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId, boolean retrieveFromCache);

	/**
	* Removes the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the asset entry display page rel that was removed
	*/
	public AssetEntryDisplayPageRel removeByA_D(long assetEntryId,
		long displayPageId) throws NoSuchEntryDisplayPageRelException;

	/**
	* Returns the number of asset entry display page rels where assetEntryId = &#63; and displayPageId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the number of matching asset entry display page rels
	*/
	public int countByA_D(long assetEntryId, long displayPageId);

	/**
	* Caches the asset entry display page rel in the entity cache if it is enabled.
	*
	* @param assetEntryDisplayPageRel the asset entry display page rel
	*/
	public void cacheResult(AssetEntryDisplayPageRel assetEntryDisplayPageRel);

	/**
	* Caches the asset entry display page rels in the entity cache if it is enabled.
	*
	* @param assetEntryDisplayPageRels the asset entry display page rels
	*/
	public void cacheResult(
		java.util.List<AssetEntryDisplayPageRel> assetEntryDisplayPageRels);

	/**
	* Creates a new asset entry display page rel with the primary key. Does not add the asset entry display page rel to the database.
	*
	* @param assetEntryDisplayPageRelId the primary key for the new asset entry display page rel
	* @return the new asset entry display page rel
	*/
	public AssetEntryDisplayPageRel create(long assetEntryDisplayPageRelId);

	/**
	* Removes the asset entry display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel that was removed
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public AssetEntryDisplayPageRel remove(long assetEntryDisplayPageRelId)
		throws NoSuchEntryDisplayPageRelException;

	public AssetEntryDisplayPageRel updateImpl(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel);

	/**
	* Returns the asset entry display page rel with the primary key or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public AssetEntryDisplayPageRel findByPrimaryKey(
		long assetEntryDisplayPageRelId)
		throws NoSuchEntryDisplayPageRelException;

	/**
	* Returns the asset entry display page rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel, or <code>null</code> if a asset entry display page rel with the primary key could not be found
	*/
	public AssetEntryDisplayPageRel fetchByPrimaryKey(
		long assetEntryDisplayPageRelId);

	@Override
	public java.util.Map<java.io.Serializable, AssetEntryDisplayPageRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset entry display page rels.
	*
	* @return the asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findAll();

	/**
	* Returns a range of all the asset entry display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @return the range of asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset entry display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entry display page rels
	*/
	public java.util.List<AssetEntryDisplayPageRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset entry display page rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset entry display page rels.
	*
	* @return the number of asset entry display page rels
	*/
	public int countAll();
}