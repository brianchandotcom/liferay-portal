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

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the asset auto tagger entry service. This utility wraps <code>com.liferay.asset.auto.tagger.service.persistence.impl.AssetAutoTaggerEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryPersistence
 * @generated
 */
public class AssetAutoTaggerEntryUtil {

	/**
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
	public static void clearCache(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		getPersistence().clearCache(assetAutoTaggerEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AssetAutoTaggerEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetAutoTaggerEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetAutoTaggerEntry update(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		return getPersistence().update(assetAutoTaggerEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetAutoTaggerEntry update(
		AssetAutoTaggerEntry assetAutoTaggerEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(assetAutoTaggerEntry, serviceContext);
	}

	/**
	 * Returns all the asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset auto tagger entries
	 */
	public static List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId) {

		return getPersistence().findByAssetEntryId(assetEntryId);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end) {

		return getPersistence().findByAssetEntryId(assetEntryId, start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry findByAssetEntryId_First(
			long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry findByAssetEntryId_Last(
			long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public static AssetAutoTaggerEntry[] findByAssetEntryId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetEntryId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetEntryId_PrevAndNext(
			assetAutoTaggerEntryId, assetEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset auto tagger entries where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	public static void removeByAssetEntryId(long assetEntryId) {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset auto tagger entries
	 */
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns all the asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entries
	 */
	public static List<AssetAutoTaggerEntry> findByAssetTagId(long assetTagId) {
		return getPersistence().findByAssetTagId(assetTagId);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end) {

		return getPersistence().findByAssetTagId(assetTagId, start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().findByAssetTagId(
			assetTagId, start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetTagId(
			assetTagId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry findByAssetTagId_First(
			long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetTagId_First(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the first asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByAssetTagId_First(
		long assetTagId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().fetchByAssetTagId_First(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry findByAssetTagId_Last(
			long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetTagId_Last(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the last asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByAssetTagId_Last(
		long assetTagId,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().fetchByAssetTagId_Last(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the asset auto tagger entries before and after the current asset auto tagger entry in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the current asset auto tagger entry
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public static AssetAutoTaggerEntry[] findByAssetTagId_PrevAndNext(
			long assetAutoTaggerEntryId, long assetTagId,
			OrderByComparator<AssetAutoTaggerEntry> orderByComparator)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByAssetTagId_PrevAndNext(
			assetAutoTaggerEntryId, assetTagId, orderByComparator);
	}

	/**
	 * Removes all the asset auto tagger entries where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	public static void removeByAssetTagId(long assetTagId) {
		getPersistence().removeByAssetTagId(assetTagId);
	}

	/**
	 * Returns the number of asset auto tagger entries where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	public static int countByAssetTagId(long assetTagId) {
		return getPersistence().countByAssetTagId(assetTagId);
	}

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry
	 * @throws NoSuchEntryException if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry findByA_A(
			long assetEntryId, long assetTagId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByA_A(assetEntryId, assetTagId);
	}

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByA_A(
		long assetEntryId, long assetTagId) {

		return getPersistence().fetchByA_A(assetEntryId, assetTagId);
	}

	/**
	 * Returns the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset auto tagger entry, or <code>null</code> if a matching asset auto tagger entry could not be found
	 */
	public static AssetAutoTaggerEntry fetchByA_A(
		long assetEntryId, long assetTagId, boolean useFinderCache) {

		return getPersistence().fetchByA_A(
			assetEntryId, assetTagId, useFinderCache);
	}

	/**
	 * Removes the asset auto tagger entry where assetEntryId = &#63; and assetTagId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the asset auto tagger entry that was removed
	 */
	public static AssetAutoTaggerEntry removeByA_A(
			long assetEntryId, long assetTagId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().removeByA_A(assetEntryId, assetTagId);
	}

	/**
	 * Returns the number of asset auto tagger entries where assetEntryId = &#63; and assetTagId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset auto tagger entries
	 */
	public static int countByA_A(long assetEntryId, long assetTagId) {
		return getPersistence().countByA_A(assetEntryId, assetTagId);
	}

	/**
	 * Caches the asset auto tagger entry in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 */
	public static void cacheResult(AssetAutoTaggerEntry assetAutoTaggerEntry) {
		getPersistence().cacheResult(assetAutoTaggerEntry);
	}

	/**
	 * Caches the asset auto tagger entries in the entity cache if it is enabled.
	 *
	 * @param assetAutoTaggerEntries the asset auto tagger entries
	 */
	public static void cacheResult(
		List<AssetAutoTaggerEntry> assetAutoTaggerEntries) {

		getPersistence().cacheResult(assetAutoTaggerEntries);
	}

	/**
	 * Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	 *
	 * @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	 * @return the new asset auto tagger entry
	 */
	public static AssetAutoTaggerEntry create(long assetAutoTaggerEntryId) {
		return getPersistence().create(assetAutoTaggerEntryId);
	}

	/**
	 * Removes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public static AssetAutoTaggerEntry remove(long assetAutoTaggerEntryId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().remove(assetAutoTaggerEntryId);
	}

	public static AssetAutoTaggerEntry updateImpl(
		AssetAutoTaggerEntry assetAutoTaggerEntry) {

		return getPersistence().updateImpl(assetAutoTaggerEntry);
	}

	/**
	 * Returns the asset auto tagger entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry
	 * @throws NoSuchEntryException if a asset auto tagger entry with the primary key could not be found
	 */
	public static AssetAutoTaggerEntry findByPrimaryKey(
			long assetAutoTaggerEntryId)
		throws com.liferay.asset.auto.tagger.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(assetAutoTaggerEntryId);
	}

	/**
	 * Returns the asset auto tagger entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry, or <code>null</code> if a asset auto tagger entry with the primary key could not be found
	 */
	public static AssetAutoTaggerEntry fetchByPrimaryKey(
		long assetAutoTaggerEntryId) {

		return getPersistence().fetchByPrimaryKey(assetAutoTaggerEntryId);
	}

	/**
	 * Returns all the asset auto tagger entries.
	 *
	 * @return the asset auto tagger entries
	 */
	public static List<AssetAutoTaggerEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<AssetAutoTaggerEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetAutoTaggerEntry> findAll(
		int start, int end,
		OrderByComparator<AssetAutoTaggerEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset auto tagger entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset auto tagger entries.
	 *
	 * @return the number of asset auto tagger entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetAutoTaggerEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetAutoTaggerEntryPersistence, AssetAutoTaggerEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetAutoTaggerEntryPersistence.class);

		ServiceTracker
			<AssetAutoTaggerEntryPersistence, AssetAutoTaggerEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<AssetAutoTaggerEntryPersistence,
						 AssetAutoTaggerEntryPersistence>(
							 bundle.getBundleContext(),
							 AssetAutoTaggerEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}