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

import com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException;
import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset entry asset display page rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetDisplayPageRelPersistenceImpl
 * @see AssetEntryAssetDisplayPageRelUtil
 * @generated
 */
@ProviderType
public interface AssetEntryAssetDisplayPageRelPersistence
	extends BasePersistence<AssetEntryAssetDisplayPageRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryAssetDisplayPageRelUtil} to access the asset entry asset display page rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset entry asset display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId);

	/**
	* Returns a range of all the asset entry asset display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @return the range of matching asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset entry asset display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry asset display page rels where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator);

	/**
	* Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel findByAssetEntryId_Last(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator);

	/**
	* Returns the asset entry asset display page rels before and after the current asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the current asset entry asset display page rel
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public AssetEntryAssetDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryAssetDisplayPageId, long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Removes all the asset entry asset display page rels where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public void removeByAssetEntryId(long assetEntryId);

	/**
	* Returns the number of asset entry asset display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset entry asset display page rels
	*/
	public int countByAssetEntryId(long assetEntryId);

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel findByA_A(long assetEntryId,
		long assetDisplayPageId) throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId);

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId, boolean retrieveFromCache);

	/**
	* Removes the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the asset entry asset display page rel that was removed
	*/
	public AssetEntryAssetDisplayPageRel removeByA_A(long assetEntryId,
		long assetDisplayPageId) throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Returns the number of asset entry asset display page rels where assetEntryId = &#63; and assetDisplayPageId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the number of matching asset entry asset display page rels
	*/
	public int countByA_A(long assetEntryId, long assetDisplayPageId);

	/**
	* Caches the asset entry asset display page rel in the entity cache if it is enabled.
	*
	* @param assetEntryAssetDisplayPageRel the asset entry asset display page rel
	*/
	public void cacheResult(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel);

	/**
	* Caches the asset entry asset display page rels in the entity cache if it is enabled.
	*
	* @param assetEntryAssetDisplayPageRels the asset entry asset display page rels
	*/
	public void cacheResult(
		java.util.List<AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels);

	/**
	* Creates a new asset entry asset display page rel with the primary key. Does not add the asset entry asset display page rel to the database.
	*
	* @param assetEntryAssetDisplayPageId the primary key for the new asset entry asset display page rel
	* @return the new asset entry asset display page rel
	*/
	public AssetEntryAssetDisplayPageRel create(
		long assetEntryAssetDisplayPageId);

	/**
	* Removes the asset entry asset display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel that was removed
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public AssetEntryAssetDisplayPageRel remove(
		long assetEntryAssetDisplayPageId)
		throws NoSuchEntryAssetDisplayPageRelException;

	public AssetEntryAssetDisplayPageRel updateImpl(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel);

	/**
	* Returns the asset entry asset display page rel with the primary key or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public AssetEntryAssetDisplayPageRel findByPrimaryKey(
		long assetEntryAssetDisplayPageId)
		throws NoSuchEntryAssetDisplayPageRelException;

	/**
	* Returns the asset entry asset display page rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel, or <code>null</code> if a asset entry asset display page rel with the primary key could not be found
	*/
	public AssetEntryAssetDisplayPageRel fetchByPrimaryKey(
		long assetEntryAssetDisplayPageId);

	@Override
	public java.util.Map<java.io.Serializable, AssetEntryAssetDisplayPageRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset entry asset display page rels.
	*
	* @return the asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findAll();

	/**
	* Returns a range of all the asset entry asset display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @return the range of asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the asset entry asset display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator);

	/**
	* Returns an ordered range of all the asset entry asset display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset display page rels
	* @param end the upper bound of the range of asset entry asset display page rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entry asset display page rels
	*/
	public java.util.List<AssetEntryAssetDisplayPageRel> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset entry asset display page rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset entry asset display page rels.
	*
	* @return the number of asset entry asset display page rels
	*/
	public int countAll();
}