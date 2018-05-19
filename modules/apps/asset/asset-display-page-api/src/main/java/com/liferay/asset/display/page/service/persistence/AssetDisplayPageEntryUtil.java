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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset display page entry service. This utility wraps {@link com.liferay.asset.display.page.service.persistence.impl.AssetDisplayPageEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryPersistence
 * @see com.liferay.asset.display.page.service.persistence.impl.AssetDisplayPageEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryUtil {
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
	public static void clearCache(AssetDisplayPageEntry assetDisplayPageEntry) {
		getPersistence().clearCache(assetDisplayPageEntry);
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
	public static List<AssetDisplayPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetDisplayPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetDisplayPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetDisplayPageEntry update(
		AssetDisplayPageEntry assetDisplayPageEntry) {
		return getPersistence().update(assetDisplayPageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetDisplayPageEntry update(
		AssetDisplayPageEntry assetDisplayPageEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(assetDisplayPageEntry, serviceContext);
	}

	/**
	* Returns the asset display page entry where assetEntryId = &#63; or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry findByAssetEntryId(long assetEntryId)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the asset display page entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry fetchByAssetEntryId(long assetEntryId) {
		return getPersistence().fetchByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the asset display page entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry fetchByAssetEntryId(long assetEntryId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByAssetEntryId(assetEntryId, retrieveFromCache);
	}

	/**
	* Removes the asset display page entry where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @return the asset display page entry that was removed
	*/
	public static AssetDisplayPageEntry removeByAssetEntryId(long assetEntryId)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the number of asset display page entries where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching asset display page entries
	*/
	public static int countByAssetEntryId(long assetEntryId) {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	* Returns all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the matching asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	* Returns a range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @return the range of matching asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			start, end);
	}

	/**
	* Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display page entries where layoutPageTemplateEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId(layoutPageTemplateEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry findByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId_First(layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the first asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPageTemplateEntryId_First(layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page entry
	* @throws NoSuchDisplayPageEntryException if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry findByLayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId_Last(layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	*/
	public static AssetDisplayPageEntry fetchByLayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutPageTemplateEntryId_Last(layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	* Returns the asset display page entries before and after the current asset display page entry in the ordered set where layoutPageTemplateEntryId = &#63;.
	*
	* @param assetDisplayPageEntryId the primary key of the current asset display page entry
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset display page entry
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public static AssetDisplayPageEntry[] findByLayoutPageTemplateEntryId_PrevAndNext(
		long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence()
				   .findByLayoutPageTemplateEntryId_PrevAndNext(assetDisplayPageEntryId,
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	* Removes all the asset display page entries where layoutPageTemplateEntryId = &#63; from the database.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	*/
	public static void removeByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {
		getPersistence()
			.removeByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	* Returns the number of asset display page entries where layoutPageTemplateEntryId = &#63;.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID
	* @return the number of matching asset display page entries
	*/
	public static int countByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {
		return getPersistence()
				   .countByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	* Caches the asset display page entry in the entity cache if it is enabled.
	*
	* @param assetDisplayPageEntry the asset display page entry
	*/
	public static void cacheResult(AssetDisplayPageEntry assetDisplayPageEntry) {
		getPersistence().cacheResult(assetDisplayPageEntry);
	}

	/**
	* Caches the asset display page entries in the entity cache if it is enabled.
	*
	* @param assetDisplayPageEntries the asset display page entries
	*/
	public static void cacheResult(
		List<AssetDisplayPageEntry> assetDisplayPageEntries) {
		getPersistence().cacheResult(assetDisplayPageEntries);
	}

	/**
	* Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	*
	* @param assetDisplayPageEntryId the primary key for the new asset display page entry
	* @return the new asset display page entry
	*/
	public static AssetDisplayPageEntry create(long assetDisplayPageEntryId) {
		return getPersistence().create(assetDisplayPageEntryId);
	}

	/**
	* Removes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry that was removed
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public static AssetDisplayPageEntry remove(long assetDisplayPageEntryId)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence().remove(assetDisplayPageEntryId);
	}

	public static AssetDisplayPageEntry updateImpl(
		AssetDisplayPageEntry assetDisplayPageEntry) {
		return getPersistence().updateImpl(assetDisplayPageEntry);
	}

	/**
	* Returns the asset display page entry with the primary key or throws a {@link NoSuchDisplayPageEntryException} if it could not be found.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry
	* @throws NoSuchDisplayPageEntryException if a asset display page entry with the primary key could not be found
	*/
	public static AssetDisplayPageEntry findByPrimaryKey(
		long assetDisplayPageEntryId)
		throws com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException {
		return getPersistence().findByPrimaryKey(assetDisplayPageEntryId);
	}

	/**
	* Returns the asset display page entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry, or <code>null</code> if a asset display page entry with the primary key could not be found
	*/
	public static AssetDisplayPageEntry fetchByPrimaryKey(
		long assetDisplayPageEntryId) {
		return getPersistence().fetchByPrimaryKey(assetDisplayPageEntryId);
	}

	public static java.util.Map<java.io.Serializable, AssetDisplayPageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset display page entries.
	*
	* @return the asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @return the range of asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findAll(int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset display page entries
	*/
	public static List<AssetDisplayPageEntry> findAll(int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset display page entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset display page entries.
	*
	* @return the number of asset display page entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetDisplayPageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetDisplayPageEntryPersistence, AssetDisplayPageEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetDisplayPageEntryPersistence.class);

		ServiceTracker<AssetDisplayPageEntryPersistence, AssetDisplayPageEntryPersistence> serviceTracker =
			new ServiceTracker<AssetDisplayPageEntryPersistence, AssetDisplayPageEntryPersistence>(bundle.getBundleContext(),
				AssetDisplayPageEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}