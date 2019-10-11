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

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
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
 * The persistence utility for the asset entry asset category rel service. This utility wraps <code>com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetCategoryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelPersistence
 * @generated
 */
public class AssetEntryAssetCategoryRelUtil {

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
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AssetEntryAssetCategoryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return getPersistence().update(
			assetEntryAssetCategoryRel, serviceContext);
	}

	/**
	 * Returns all the asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching asset entry asset category rels
	 */
	public static List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId) {

		return getPersistence().findByAssetEntryId(assetEntryId);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {

		return getPersistence().findByAssetEntryId(assetEntryId, start, end);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetEntryId(
			assetEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel findByAssetEntryId_First(
			long assetEntryId,
			OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_First(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel findByAssetEntryId_Last(
			long assetEntryId,
			OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().fetchByAssetEntryId_Last(
			assetEntryId, orderByComparator);
	}

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetEntryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetEntryId the asset entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public static AssetEntryAssetCategoryRel[] findByAssetEntryId_PrevAndNext(
			long assetEntryAssetCategoryRelId, long assetEntryId,
			OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetEntryId_PrevAndNext(
			assetEntryAssetCategoryRelId, assetEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset entry asset category rels where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 */
	public static void removeByAssetEntryId(long assetEntryId) {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns the number of asset entry asset category rels where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching asset entry asset category rels
	 */
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	 * Returns all the asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rels
	 */
	public static List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId) {

		return getPersistence().findByAssetCategoryId(assetCategoryId);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator);
	}

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
	public static List<AssetEntryAssetCategoryRel> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel findByAssetCategoryId_First(
			long assetCategoryId,
			OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetCategoryId_First(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the first asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().fetchByAssetCategoryId_First(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel findByAssetCategoryId_Last(
			long assetCategoryId,
			OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetCategoryId_Last(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the last asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().fetchByAssetCategoryId_Last(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the asset entry asset category rels before and after the current asset entry asset category rel in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the current asset entry asset category rel
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public static AssetEntryAssetCategoryRel[]
			findByAssetCategoryId_PrevAndNext(
				long assetEntryAssetCategoryRelId, long assetCategoryId,
				OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByAssetCategoryId_PrevAndNext(
			assetEntryAssetCategoryRelId, assetCategoryId, orderByComparator);
	}

	/**
	 * Removes all the asset entry asset category rels where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	public static void removeByAssetCategoryId(long assetCategoryId) {
		getPersistence().removeByAssetCategoryId(assetCategoryId);
	}

	/**
	 * Returns the number of asset entry asset category rels where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching asset entry asset category rels
	 */
	public static int countByAssetCategoryId(long assetCategoryId) {
		return getPersistence().countByAssetCategoryId(assetCategoryId);
	}

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or throws a <code>NoSuchEntryAssetCategoryRelException</code> if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel findByA_A(
			long assetEntryId, long assetCategoryId)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().findByA_A(assetEntryId, assetCategoryId);
	}

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByA_A(
		long assetEntryId, long assetCategoryId) {

		return getPersistence().fetchByA_A(assetEntryId, assetCategoryId);
	}

	/**
	 * Returns the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry asset category rel, or <code>null</code> if a matching asset entry asset category rel could not be found
	 */
	public static AssetEntryAssetCategoryRel fetchByA_A(
		long assetEntryId, long assetCategoryId, boolean useFinderCache) {

		return getPersistence().fetchByA_A(
			assetEntryId, assetCategoryId, useFinderCache);
	}

	/**
	 * Removes the asset entry asset category rel where assetEntryId = &#63; and assetCategoryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the asset entry asset category rel that was removed
	 */
	public static AssetEntryAssetCategoryRel removeByA_A(
			long assetEntryId, long assetCategoryId)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().removeByA_A(assetEntryId, assetCategoryId);
	}

	/**
	 * Returns the number of asset entry asset category rels where assetEntryId = &#63; and assetCategoryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching asset entry asset category rels
	 */
	public static int countByA_A(long assetEntryId, long assetCategoryId) {
		return getPersistence().countByA_A(assetEntryId, assetCategoryId);
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
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

		return getPersistence().remove(assetEntryAssetCategoryRelId);
	}

	public static AssetEntryAssetCategoryRel updateImpl(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

		return getPersistence().updateImpl(assetEntryAssetCategoryRel);
	}

	/**
	 * Returns the asset entry asset category rel with the primary key or throws a <code>NoSuchEntryAssetCategoryRelException</code> if it could not be found.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel
	 * @throws NoSuchEntryAssetCategoryRelException if a asset entry asset category rel with the primary key could not be found
	 */
	public static AssetEntryAssetCategoryRel findByPrimaryKey(
			long assetEntryAssetCategoryRelId)
		throws com.liferay.asset.entry.rel.exception.
			NoSuchEntryAssetCategoryRelException {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry asset category rels
	 */
	public static List<AssetEntryAssetCategoryRel> findAll(
		int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetEntryAssetCategoryRel> findAll(
		int start, int end,
		OrderByComparator<AssetEntryAssetCategoryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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

	private static ServiceTracker
		<AssetEntryAssetCategoryRelPersistence,
		 AssetEntryAssetCategoryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryAssetCategoryRelPersistence.class);

		ServiceTracker
			<AssetEntryAssetCategoryRelPersistence,
			 AssetEntryAssetCategoryRelPersistence> serviceTracker =
				new ServiceTracker
					<AssetEntryAssetCategoryRelPersistence,
					 AssetEntryAssetCategoryRelPersistence>(
						 bundle.getBundleContext(),
						 AssetEntryAssetCategoryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}