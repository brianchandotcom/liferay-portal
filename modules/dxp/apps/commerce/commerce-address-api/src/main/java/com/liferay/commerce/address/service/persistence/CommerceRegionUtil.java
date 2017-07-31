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

package com.liferay.commerce.address.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.model.CommerceRegion;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce region service. This utility wraps {@link com.liferay.commerce.address.service.persistence.impl.CommerceRegionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionPersistence
 * @see com.liferay.commerce.address.service.persistence.impl.CommerceRegionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceRegionUtil {
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
	public static void clearCache(CommerceRegion commerceRegion) {
		getPersistence().clearCache(commerceRegion);
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
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceRegion update(CommerceRegion commerceRegion) {
		return getPersistence().update(commerceRegion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceRegion update(CommerceRegion commerceRegion,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceRegion, serviceContext);
	}

	/**
	* Returns all the commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId) {
		return getPersistence().findByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns a range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.address.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.address.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion[] findByCommerceCountryId_PrevAndNext(
		long commerceRegionId, long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.address.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_PrevAndNext(commerceRegionId,
			commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce regions where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByCommerceCountryId(long commerceCountryId) {
		getPersistence().removeByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns the number of commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce regions
	*/
	public static int countByCommerceCountryId(long commerceCountryId) {
		return getPersistence().countByCommerceCountryId(commerceCountryId);
	}

	/**
	* Caches the commerce region in the entity cache if it is enabled.
	*
	* @param commerceRegion the commerce region
	*/
	public static void cacheResult(CommerceRegion commerceRegion) {
		getPersistence().cacheResult(commerceRegion);
	}

	/**
	* Caches the commerce regions in the entity cache if it is enabled.
	*
	* @param commerceRegions the commerce regions
	*/
	public static void cacheResult(List<CommerceRegion> commerceRegions) {
		getPersistence().cacheResult(commerceRegions);
	}

	/**
	* Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	*
	* @param commerceRegionId the primary key for the new commerce region
	* @return the new commerce region
	*/
	public static CommerceRegion create(long commerceRegionId) {
		return getPersistence().create(commerceRegionId);
	}

	/**
	* Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region that was removed
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion remove(long commerceRegionId)
		throws com.liferay.commerce.address.exception.NoSuchRegionException {
		return getPersistence().remove(commerceRegionId);
	}

	public static CommerceRegion updateImpl(CommerceRegion commerceRegion) {
		return getPersistence().updateImpl(commerceRegion);
	}

	/**
	* Returns the commerce region with the primary key or throws a {@link NoSuchRegionException} if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion findByPrimaryKey(long commerceRegionId)
		throws com.liferay.commerce.address.exception.NoSuchRegionException {
		return getPersistence().findByPrimaryKey(commerceRegionId);
	}

	/**
	* Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion fetchByPrimaryKey(long commerceRegionId) {
		return getPersistence().fetchByPrimaryKey(commerceRegionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceRegion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce regions.
	*
	* @return the commerce regions
	*/
	public static List<CommerceRegion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce regions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce regions.
	*
	* @return the number of commerce regions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceRegionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceRegionPersistence, CommerceRegionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceRegionPersistence.class);
}