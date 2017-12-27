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

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset entry asset category rel service. This utility wraps {@link com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetCategoryRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetCategoryRelPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetEntryAssetCategoryRelUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		getPersistence().clearCache(assetEntryAssetCategoryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetEntryAssetCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntryAssetCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntryAssetCategoryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntryAssetCategoryRel update(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		return getPersistence().update(assetEntryAssetCategoryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntryAssetCategoryRel update(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(assetEntryAssetCategoryRel, serviceContext);
	}

	/**
	* Returns all the asset entry asset category rels where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByEntryId(long entryId) {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Returns a range of all the asset entry asset category rels where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @return the range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByEntryId(long entryId,
		int start, int end) {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry asset category rel in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel findByEntryId_First(long entryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first asset entry asset category rel in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel fetchByEntryId_First(
		long entryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence().fetchByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset category rel in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel findByEntryId_Last(long entryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset category rel in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel fetchByEntryId_Last(long entryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence().fetchByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where entryId = &#63;.
	*
	* @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	*/
	public static AssetEntryAssetCategoryRel[] findByEntryId_PrevAndNext(
		long assetEntryAssetCategoryRelId, long entryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(assetEntryAssetCategoryRelId,
			entryId, orderByComparator);
	}

	/**
	* Removes all the asset entry asset category rels where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public static void removeByEntryId(long entryId) {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Returns the number of asset entry asset category rels where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching asset entry asset category rels
	*/
	public static int countByEntryId(long entryId) {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Returns all the asset entry asset category rels where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByCategoryId(
		long categoryId) {
		return getPersistence().findByCategoryId(categoryId);
	}

	/**
	* Returns a range of all the asset entry asset category rels where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @return the range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByCategoryId(
		long categoryId, int start, int end) {
		return getPersistence().findByCategoryId(categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence()
				   .findByCategoryId(categoryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCategoryId(categoryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry asset category rel in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel findByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence()
				   .findByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the first asset entry asset category rel in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel fetchByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset category rel in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel findByCategoryId_Last(
		long categoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence()
				   .findByCategoryId_Last(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset category rel in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	*/
	public static AssetEntryAssetCategoryRel fetchByCategoryId_Last(
		long categoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCategoryId_Last(categoryId, orderByComparator);
	}

	/**
	* Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where categoryId = &#63;.
	*
	* @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	*/
	public static AssetEntryAssetCategoryRel[] findByCategoryId_PrevAndNext(
		long assetEntryAssetCategoryRelId, long categoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence()
				   .findByCategoryId_PrevAndNext(assetEntryAssetCategoryRelId,
			categoryId, orderByComparator);
	}

	/**
	* Removes all the asset entry asset category rels where categoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	*/
	public static void removeByCategoryId(long categoryId) {
		getPersistence().removeByCategoryId(categoryId);
	}

	/**
	* Returns the number of asset entry asset category rels where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the number of matching asset entry asset category rels
	*/
	public static int countByCategoryId(long categoryId) {
		return getPersistence().countByCategoryId(categoryId);
	}

	/**
	* Caches the asset entry asset category rel in the entity cache if it is enabled.
	*
	* @param assetEntryAssetCategoryRel the asset entry asset category rel
	*/
	public static void cacheResult(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		getPersistence().cacheResult(assetEntryAssetCategoryRel);
	}

	/**
	* Caches the asset entry asset category rels in the entity cache if it is enabled.
	*
	* @param assetEntryAssetCategoryRels the asset entry asset category rels
	*/
	public static void cacheResult(
		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels) {
		getPersistence().cacheResult(assetEntryAssetCategoryRels);
	}

	/**
	* Creates a new asset entry asset category rel with the primary key. Does not add the asset entry asset category rel to the database.
	*
	* @param assetEntryAssetCategoryRelId the primary key for the new asset entry asset category rel
	* @return the new asset entry asset category rel
	*/
	public static AssetEntryAssetCategoryRel create(
		long assetEntryAssetCategoryRelId) {
		return getPersistence().create(assetEntryAssetCategoryRelId);
	}

	/**
	* Removes the asset entry asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	* @return the asset entry asset category rel that was removed
	* @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	*/
	public static AssetEntryAssetCategoryRel remove(
		long assetEntryAssetCategoryRelId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence().remove(assetEntryAssetCategoryRelId);
	}

	public static AssetEntryAssetCategoryRel updateImpl(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {
		return getPersistence().updateImpl(assetEntryAssetCategoryRel);
	}

	/**
	* Returns the asset entry asset category rel with the primary key or throws a {@link NoSuchEntryAssetCategoryRelException} if it could not be found.
	*
	* @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	* @return the asset entry asset category rel
	* @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	*/
	public static AssetEntryAssetCategoryRel findByPrimaryKey(
		long assetEntryAssetCategoryRelId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetCategoryRelException {
		return getPersistence().findByPrimaryKey(assetEntryAssetCategoryRelId);
	}

	/**
	* Returns the asset entry asset category rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	* @return the asset entry asset category rel, or <code>null</code> if a asset entry asset category rel with the primary key could not be found
	*/
	public static AssetEntryAssetCategoryRel fetchByPrimaryKey(
		long assetEntryAssetCategoryRelId) {
		return getPersistence().fetchByPrimaryKey(assetEntryAssetCategoryRelId);
	}

	public static java.util.Map<java.io.Serializable, AssetEntryAssetCategoryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset entry asset category rels.
	*
	* @return the asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset entry asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @return the range of asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry asset category rels
	* @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entry asset category rels
	*/
	public static List<AssetEntryAssetCategoryRel> findAll(int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset entry asset category rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entry asset category rels.
	*
	* @return the number of asset entry asset category rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetEntryAssetCategoryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetEntryAssetCategoryRelPersistence, AssetEntryAssetCategoryRelPersistence> _serviceTracker =
		ServiceTrackerFactory.open(AssetEntryAssetCategoryRelPersistence.class);
}