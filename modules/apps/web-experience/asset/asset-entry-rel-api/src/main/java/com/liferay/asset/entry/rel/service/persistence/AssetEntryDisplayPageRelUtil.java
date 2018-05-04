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

import com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset entry display page rel service. This utility wraps {@link com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryDisplayPageRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRelPersistence
 * @see com.liferay.asset.entry.rel.service.persistence.impl.AssetEntryDisplayPageRelPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetEntryDisplayPageRelUtil {
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
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		getPersistence().clearCache(assetEntryDisplayPageRel);
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
	public static List<AssetEntryDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntryDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntryDisplayPageRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntryDisplayPageRel update(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return getPersistence().update(assetEntryDisplayPageRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntryDisplayPageRel update(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel,
		ServiceContext serviceContext) {
		return getPersistence().update(assetEntryDisplayPageRel, serviceContext);
	}

	/**
	* Returns all the asset entry display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset entry display page rels
	*/
	public static List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId) {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

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
	public static List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end) {
		return getPersistence().findByAssetEntryId(assetEntryId, start, end);
	}

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
	public static List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .findByAssetEntryId(assetEntryId, start, end,
			orderByComparator);
	}

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
	public static List<AssetEntryDisplayPageRel> findByAssetEntryId(
		long assetEntryId, int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetEntryId(assetEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel findByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_First(assetEntryId, orderByComparator);
	}

	/**
	* Returns the first asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel fetchByAssetEntryId_First(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_First(assetEntryId, orderByComparator);
	}

	/**
	* Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel findByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_Last(assetEntryId, orderByComparator);
	}

	/**
	* Returns the last asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel fetchByAssetEntryId_Last(
		long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_Last(assetEntryId, orderByComparator);
	}

	/**
	* Returns the asset entry display page rels before and after the current asset entry display page rel in the ordered set where assetEntryId = &#63;.
	*
	* @param assetEntryDisplayPageRelId the primary key of the current asset entry display page rel
	* @param assetEntryId the asset entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public static AssetEntryDisplayPageRel[] findByAssetEntryId_PrevAndNext(
		long assetEntryDisplayPageRelId, long assetEntryId,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence()
				   .findByAssetEntryId_PrevAndNext(assetEntryDisplayPageRelId,
			assetEntryId, orderByComparator);
	}

	/**
	* Removes all the asset entry display page rels where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	*/
	public static void removeByAssetEntryId(long assetEntryId) {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the number of asset entry display page rels where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset entry display page rels
	*/
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the matching asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel findByA_D(long assetEntryId,
		long displayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence().findByA_D(assetEntryId, displayPageId);
	}

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId) {
		return getPersistence().fetchByA_D(assetEntryId, displayPageId);
	}

	/**
	* Returns the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry display page rel, or <code>null</code> if a matching asset entry display page rel could not be found
	*/
	public static AssetEntryDisplayPageRel fetchByA_D(long assetEntryId,
		long displayPageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByA_D(assetEntryId, displayPageId, retrieveFromCache);
	}

	/**
	* Removes the asset entry display page rel where assetEntryId = &#63; and displayPageId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the asset entry display page rel that was removed
	*/
	public static AssetEntryDisplayPageRel removeByA_D(long assetEntryId,
		long displayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence().removeByA_D(assetEntryId, displayPageId);
	}

	/**
	* Returns the number of asset entry display page rels where assetEntryId = &#63; and displayPageId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @param displayPageId the display page ID
	* @return the number of matching asset entry display page rels
	*/
	public static int countByA_D(long assetEntryId, long displayPageId) {
		return getPersistence().countByA_D(assetEntryId, displayPageId);
	}

	/**
	* Caches the asset entry display page rel in the entity cache if it is enabled.
	*
	* @param assetEntryDisplayPageRel the asset entry display page rel
	*/
	public static void cacheResult(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		getPersistence().cacheResult(assetEntryDisplayPageRel);
	}

	/**
	* Caches the asset entry display page rels in the entity cache if it is enabled.
	*
	* @param assetEntryDisplayPageRels the asset entry display page rels
	*/
	public static void cacheResult(
		List<AssetEntryDisplayPageRel> assetEntryDisplayPageRels) {
		getPersistence().cacheResult(assetEntryDisplayPageRels);
	}

	/**
	* Creates a new asset entry display page rel with the primary key. Does not add the asset entry display page rel to the database.
	*
	* @param assetEntryDisplayPageRelId the primary key for the new asset entry display page rel
	* @return the new asset entry display page rel
	*/
	public static AssetEntryDisplayPageRel create(
		long assetEntryDisplayPageRelId) {
		return getPersistence().create(assetEntryDisplayPageRelId);
	}

	/**
	* Removes the asset entry display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel that was removed
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public static AssetEntryDisplayPageRel remove(
		long assetEntryDisplayPageRelId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence().remove(assetEntryDisplayPageRelId);
	}

	public static AssetEntryDisplayPageRel updateImpl(
		AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return getPersistence().updateImpl(assetEntryDisplayPageRel);
	}

	/**
	* Returns the asset entry display page rel with the primary key or throws a {@link NoSuchEntryDisplayPageRelException} if it could not be found.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel
	* @throws NoSuchEntryDisplayPageRelException if a asset entry display page rel with the primary key could not be found
	*/
	public static AssetEntryDisplayPageRel findByPrimaryKey(
		long assetEntryDisplayPageRelId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		return getPersistence().findByPrimaryKey(assetEntryDisplayPageRelId);
	}

	/**
	* Returns the asset entry display page rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel, or <code>null</code> if a asset entry display page rel with the primary key could not be found
	*/
	public static AssetEntryDisplayPageRel fetchByPrimaryKey(
		long assetEntryDisplayPageRelId) {
		return getPersistence().fetchByPrimaryKey(assetEntryDisplayPageRelId);
	}

	public static java.util.Map<java.io.Serializable, AssetEntryDisplayPageRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset entry display page rels.
	*
	* @return the asset entry display page rels
	*/
	public static List<AssetEntryDisplayPageRel> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<AssetEntryDisplayPageRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<AssetEntryDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetEntryDisplayPageRel> findAll(int start, int end,
		OrderByComparator<AssetEntryDisplayPageRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset entry display page rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entry display page rels.
	*
	* @return the number of asset entry display page rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetEntryDisplayPageRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetEntryDisplayPageRelPersistence, AssetEntryDisplayPageRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetEntryDisplayPageRelPersistence.class);

		ServiceTracker<AssetEntryDisplayPageRelPersistence, AssetEntryDisplayPageRelPersistence> serviceTracker =
			new ServiceTracker<AssetEntryDisplayPageRelPersistence, AssetEntryDisplayPageRelPersistence>(bundle.getBundleContext(),
				AssetEntryDisplayPageRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}