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

import com.liferay.asset.display.page.exception.NoSuchDisplayPageException;
import com.liferay.asset.display.page.model.AssetDisplayPage;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset display page service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.display.page.service.persistence.impl.AssetDisplayPagePersistenceImpl
 * @see AssetDisplayPageUtil
 * @generated
 */
@ProviderType
public interface AssetDisplayPagePersistence extends BasePersistence<AssetDisplayPage> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetDisplayPageUtil} to access the asset display page persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset display pages where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset display pages
	*/
	public java.util.List<AssetDisplayPage> findByAssetEntryId(
		long assetEntryId);

	/**
	* Returns a range of all the asset display pages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @return the range of matching asset display pages
	*/
	public java.util.List<AssetDisplayPage> findByAssetEntryId(
		long assetEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset display pages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display pages
	*/
	public java.util.List<AssetDisplayPage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator);

	/**
	* Returns an ordered range of all the asset display pages where assetEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetEntryId the asset entry ID
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display pages
	*/
	public java.util.List<AssetDisplayPage> findByAssetEntryId(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset display page in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page
	* @throws NoSuchDisplayPageException if a matching asset display page could not be found
	*/
	public AssetDisplayPage findByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException;

	/**
	* Returns the first asset display page in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page, or <code>null</code> if a matching asset display page could not be found
	*/
	public AssetDisplayPage fetchByAssetEntryId_First(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator);

	/**
	* Returns the last asset display page in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page
	* @throws NoSuchDisplayPageException if a matching asset display page could not be found
	*/
	public AssetDisplayPage findByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException;

	/**
	* Returns the last asset display page in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page, or <code>null</code> if a matching asset display page could not be found
	*/
	public AssetDisplayPage fetchByAssetEntryId_Last(long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator);

	/**
	* Returns the asset display pages before and after the current asset display page in the ordered set where assetEntryId = &#63;.
	*
	* @param assetDisplayPageId the primary key of the current asset display page
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display page
	* @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	*/
	public AssetDisplayPage[] findByAssetEntryId_PrevAndNext(
		long assetDisplayPageId, long assetEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator)
		throws NoSuchDisplayPageException;

	/**
	* Removes all the asset display pages where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public void removeByAssetEntryId(long assetEntryId);

	/**
	* Returns the number of asset display pages where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset display pages
	*/
	public int countByAssetEntryId(long assetEntryId);

	/**
	* Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or throws a {@link NoSuchDisplayPageException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the matching asset display page
	* @throws NoSuchDisplayPageException if a matching asset display page could not be found
	*/
	public AssetDisplayPage findByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageException;

	/**
	* Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the matching asset display page, or <code>null</code> if a matching asset display page could not be found
	*/
	public AssetDisplayPage fetchByA_L(long assetEntryId, long layoutId);

	/**
	* Returns the asset display page where assetEntryId = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset display page, or <code>null</code> if a matching asset display page could not be found
	*/
	public AssetDisplayPage fetchByA_L(long assetEntryId, long layoutId,
		boolean retrieveFromCache);

	/**
	* Removes the asset display page where assetEntryId = &#63; and layoutId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the asset display page that was removed
	*/
	public AssetDisplayPage removeByA_L(long assetEntryId, long layoutId)
		throws NoSuchDisplayPageException;

	/**
	* Returns the number of asset display pages where assetEntryId = &#63; and layoutId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param layoutId the layout ID
	* @return the number of matching asset display pages
	*/
	public int countByA_L(long assetEntryId, long layoutId);

	/**
	* Caches the asset display page in the entity cache if it is enabled.
	*
	* @param assetDisplayPage the asset display page
	*/
	public void cacheResult(AssetDisplayPage assetDisplayPage);

	/**
	* Caches the asset display pages in the entity cache if it is enabled.
	*
	* @param assetDisplayPages the asset display pages
	*/
	public void cacheResult(java.util.List<AssetDisplayPage> assetDisplayPages);

	/**
	* Creates a new asset display page with the primary key. Does not add the asset display page to the database.
	*
	* @param assetDisplayPageId the primary key for the new asset display page
	* @return the new asset display page
	*/
	public AssetDisplayPage create(long assetDisplayPageId);

	/**
	* Removes the asset display page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageId the primary key of the asset display page
	* @return the asset display page that was removed
	* @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	*/
	public AssetDisplayPage remove(long assetDisplayPageId)
		throws NoSuchDisplayPageException;

	public AssetDisplayPage updateImpl(AssetDisplayPage assetDisplayPage);

	/**
	* Returns the asset display page with the primary key or throws a {@link NoSuchDisplayPageException} if it could not be found.
	*
	* @param assetDisplayPageId the primary key of the asset display page
	* @return the asset display page
	* @throws NoSuchDisplayPageException if a asset display page with the primary key could not be found
	*/
	public AssetDisplayPage findByPrimaryKey(long assetDisplayPageId)
		throws NoSuchDisplayPageException;

	/**
	* Returns the asset display page with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetDisplayPageId the primary key of the asset display page
	* @return the asset display page, or <code>null</code> if a asset display page with the primary key could not be found
	*/
	public AssetDisplayPage fetchByPrimaryKey(long assetDisplayPageId);

	@Override
	public java.util.Map<java.io.Serializable, AssetDisplayPage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset display pages.
	*
	* @return the asset display pages
	*/
	public java.util.List<AssetDisplayPage> findAll();

	/**
	* Returns a range of all the asset display pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @return the range of asset display pages
	*/
	public java.util.List<AssetDisplayPage> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset display pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset display pages
	*/
	public java.util.List<AssetDisplayPage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator);

	/**
	* Returns an ordered range of all the asset display pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset display pages
	*/
	public java.util.List<AssetDisplayPage> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetDisplayPage> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset display pages from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset display pages.
	*
	* @return the number of asset display pages
	*/
	public int countAll();
}