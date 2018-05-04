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

import com.liferay.asset.entry.rel.model.AssetEntryAssetDisplayPageRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset entry asset display page rel service. This utility wraps {@link com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetDisplayPageRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetDisplayPageRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryAssetDisplayPageRelPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetEntryAssetDisplayPageRelUtil {
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
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		getPersistence().clearCache(assetEntryAssetDisplayPageRel);
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
	public static List<AssetEntryAssetDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntryAssetDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntryAssetDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntryAssetDisplayPageRel update(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		return getPersistence().update(assetEntryAssetDisplayPageRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntryAssetDisplayPageRel update(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(assetEntryAssetDisplayPageRel, serviceContext);
	}

	/**
	* Returns all the asset entry asset display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset entry asset display page rels
	*/
	public static List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId) {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {
		return getPersistence().findByAssetEntryId(assetEntryId, start, end);
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .findByAssetEntryId(assetEntryId, start, end,
			orderByComparator);
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetEntryId(assetEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_First(assetEntryId, orderByComparator);
	}

	/**
	* Returns the first asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_First(assetEntryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel findByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_Last(assetEntryId, orderByComparator);
	}

	/**
	* Returns the last asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_Last(assetEntryId, orderByComparator);
	}

	/**
	* Returns the asset entry asset display page rels before and after the current asset entry asset display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the current asset entry asset display page rel
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public static AssetEntryAssetDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryAssetDisplayPageId, long assetEntryId,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_PrevAndNext(assetEntryAssetDisplayPageId,
			assetEntryId, orderByComparator);
	}

	/**
	* Removes all the asset entry asset display page rels where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public static void removeByAssetEntryId(long assetEntryId) {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the number of asset entry asset display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset entry asset display page rels
	*/
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the matching asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel findByA_A(long assetEntryId,
		long assetDisplayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence().findByA_A(assetEntryId, assetDisplayPageId);
	}

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId) {
		return getPersistence().fetchByA_A(assetEntryId, assetDisplayPageId);
	}

	/**
	* Returns the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry asset display page rel, or <code>null</code> if a matching asset entry asset display page rel could not be found
	*/
	public static AssetEntryAssetDisplayPageRel fetchByA_A(long assetEntryId,
		long assetDisplayPageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByA_A(assetEntryId, assetDisplayPageId,
			retrieveFromCache);
	}

	/**
	* Removes the asset entry asset display page rel where assetEntryId = &#63; and assetDisplayPageId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the asset entry asset display page rel that was removed
	*/
	public static AssetEntryAssetDisplayPageRel removeByA_A(long assetEntryId,
		long assetDisplayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence().removeByA_A(assetEntryId, assetDisplayPageId);
	}

	/**
	* Returns the number of asset entry asset display page rels where assetEntryId = &#63; and assetDisplayPageId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param assetDisplayPageId the asset display page ID
	* @return the number of matching asset entry asset display page rels
	*/
	public static int countByA_A(long assetEntryId, long assetDisplayPageId) {
		return getPersistence().countByA_A(assetEntryId, assetDisplayPageId);
	}

	/**
	* Caches the asset entry asset display page rel in the entity cache if it is enabled.
	*
	* @param assetEntryAssetDisplayPageRel the asset entry asset display page rel
	*/
	public static void cacheResult(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		getPersistence().cacheResult(assetEntryAssetDisplayPageRel);
	}

	/**
	* Caches the asset entry asset display page rels in the entity cache if it is enabled.
	*
	* @param assetEntryAssetDisplayPageRels the asset entry asset display page rels
	*/
	public static void cacheResult(
		List<AssetEntryAssetDisplayPageRel> assetEntryAssetDisplayPageRels) {
		getPersistence().cacheResult(assetEntryAssetDisplayPageRels);
	}

	/**
	* Creates a new asset entry asset display page rel with the primary key. Does not add the asset entry asset display page rel to the database.
	*
	* @param assetEntryAssetDisplayPageId the primary key for the new asset entry asset display page rel
	* @return the new asset entry asset display page rel
	*/
	public static AssetEntryAssetDisplayPageRel create(
		long assetEntryAssetDisplayPageId) {
		return getPersistence().create(assetEntryAssetDisplayPageId);
	}

	/**
	* Removes the asset entry asset display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel that was removed
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public static AssetEntryAssetDisplayPageRel remove(
		long assetEntryAssetDisplayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence().remove(assetEntryAssetDisplayPageId);
	}

	public static AssetEntryAssetDisplayPageRel updateImpl(
		AssetEntryAssetDisplayPageRel assetEntryAssetDisplayPageRel) {
		return getPersistence().updateImpl(assetEntryAssetDisplayPageRel);
	}

	/**
	* Returns the asset entry asset display page rel with the primary key or throws a {@link NoSuchEntryAssetDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel
	* @throws NoSuchEntryAssetDisplayPageRelException if a asset entry asset display page rel with the primary key could not be found
	*/
	public static AssetEntryAssetDisplayPageRel findByPrimaryKey(
		long assetEntryAssetDisplayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryAssetDisplayPageRelException {
		return getPersistence().findByPrimaryKey(assetEntryAssetDisplayPageId);
	}

	/**
	* Returns the asset entry asset display page rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryAssetDisplayPageId the primary key of the asset entry asset display page rel
	* @return the asset entry asset display page rel, or <code>null</code> if a asset entry asset display page rel with the primary key could not be found
	*/
	public static AssetEntryAssetDisplayPageRel fetchByPrimaryKey(
		long assetEntryAssetDisplayPageId) {
		return getPersistence().fetchByPrimaryKey(assetEntryAssetDisplayPageId);
	}

	public static java.util.Map<java.io.Serializable, AssetEntryAssetDisplayPageRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset entry asset display page rels.
	*
	* @return the asset entry asset display page rels
	*/
	public static List<AssetEntryAssetDisplayPageRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findAll(int start,
		int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetEntryAssetDisplayPageRel> findAll(int start,
		int end,
		OrderByComparator<AssetEntryAssetDisplayPageRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset entry asset display page rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entry asset display page rels.
	*
	* @return the number of asset entry asset display page rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetEntryAssetDisplayPageRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetEntryAssetDisplayPageRelPersistence, AssetEntryAssetDisplayPageRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetEntryAssetDisplayPageRelPersistence.class);

		ServiceTracker<AssetEntryAssetDisplayPageRelPersistence, AssetEntryAssetDisplayPageRelPersistence> serviceTracker =
			new ServiceTracker<AssetEntryAssetDisplayPageRelPersistence, AssetEntryAssetDisplayPageRelPersistence>(bundle.getBundleContext(),
				AssetEntryAssetDisplayPageRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}