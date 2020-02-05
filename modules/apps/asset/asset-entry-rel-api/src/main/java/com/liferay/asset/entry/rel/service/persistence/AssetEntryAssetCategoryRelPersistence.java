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

import com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset entry asset category rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelUtil
 * @generated
 */
@ProviderType
public interface AssetEntryAssetCategoryRelPersistence
	extends BasePersistence<AssetEntryAssetCategoryRel>,
			CTPersistence<AssetEntryAssetCategoryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryAssetCategoryRelUtil} to access the asset entry asset category rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId);

	/**
	 * Returns a range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetEntryId the asset entry ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel findByAssetEntryId_First(
			long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByAssetEntryId_First(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel findByAssetEntryId_Last(
			long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByAssetEntryId_Last(
		long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public AssetEntryAssetCategoryRel[] findByAssetEntryId_PrevAndNext(
			long assetEntryAssetCategoryRelId, long assetEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Removes all the asset entry asset category rels where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	public void removeByAssetEntryId(long assetEntryId);

	/**
	 * Returns the number of asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry asset category rels
	 */
	public int countByAssetEntryId(long assetEntryId);

	/**
	 * Returns all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId);

	/**
	 * Returns a range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel findByAssetCategoryId_First(
			long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByAssetCategoryId_First(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel findByAssetCategoryId_Last(
			long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByAssetCategoryId_Last(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public AssetEntryAssetCategoryRel[] findByAssetCategoryId_PrevAndNext(
			long assetEntryAssetCategoryRelId, long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetEntryAssetCategoryRel> orderByComparator)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Removes all the asset entry asset category rels where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	public void removeByAssetCategoryId(long assetCategoryId);

	/**
	 * Returns the number of asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching asset entry asset category rels
	 */
	public int countByAssetCategoryId(long assetCategoryId);

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or throws a <code>NoSuchEntryAssetCategoryRelException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel findByA_A(
			long assetEntryId, long assetCategoryId)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByA_A(
		long assetEntryId, long assetCategoryId);

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByA_A(
		long assetEntryId, long assetCategoryId, boolean useFinderCache);

	/**
	 * Removes the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the asset entry asset category rel that was removed
	 */
	public AssetEntryAssetCategoryRel removeByA_A(
			long assetEntryId, long assetCategoryId)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the number of asset entry asset category rels where assetEntryId = &#63; and assetCategoryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching asset entry asset category rels
	 */
	public int countByA_A(long assetEntryId, long assetCategoryId);

	/**
	 * Caches the asset entry asset category rel in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetCategoryRel the asset entry asset category rel
	 */
	public void cacheResult(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel);

	/**
	 * Caches the asset entry asset category rels in the entity cache if it is enabled.
	 *
	 * @param assetEntryAssetCategoryRels the asset entry asset category rels
	 */
	public void cacheResult(
		java.util.List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels);

	/**
	 * Creates a new asset entry asset category rel with the primary key. Does not add the asset entry asset category rel to the database.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key for the new asset entry asset category rel
	 * @return the new asset entry asset category rel
	 */
	public AssetEntryAssetCategoryRel create(long assetEntryAssetCategoryRelId);

	/**
	 * Removes the asset entry asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel that was removed
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public AssetEntryAssetCategoryRel remove(long assetEntryAssetCategoryRelId)
		throws NoSuchEntryAssetCategoryRelException;

	public AssetEntryAssetCategoryRel updateImpl(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel);

	/**
	 * Returns the asset entry asset category rel with the primary key or throws a <code>NoSuchEntryAssetCategoryRelException</code> if it could not be found.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public AssetEntryAssetCategoryRel findByPrimaryKey(
			long assetEntryAssetCategoryRelId)
		throws NoSuchEntryAssetCategoryRelException;

	/**
	 * Returns the asset entry asset category rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel, or <code>null</code> if a asset entry asset category rel with the primary key could not be found
	 */
	public AssetEntryAssetCategoryRel fetchByPrimaryKey(
		long assetEntryAssetCategoryRelId);

	/**
	 * Returns all the asset entry asset category rels.
	 *
	 * @return the asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findAll();

	/**
	 * Returns a range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator);

	/**
	 * Returns an ordered range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entry asset category rels
	 */
	public java.util.List<AssetEntryAssetCategoryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset entry asset category rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset entry asset category rels.
	 *
	 * @return the number of asset entry asset category rels
	 */
	public int countAll();

}