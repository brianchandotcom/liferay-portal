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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the region service. This utility wraps <code>com.liferay.portal.service.persistence.impl.RegionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegionPersistence
 * @generated
 */
@ProviderType
public class RegionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Region region) {
		getPersistence().clearCache(region);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Region> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Region> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Region> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Region> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Region update(Region region) {
		return getPersistence().update(region);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Region update(Region region, ServiceContext serviceContext) {
		return getPersistence().update(region, serviceContext);
	}

	/**
	* Returns all the regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the matching regions
	*/
	public static List<Region> findByCountryId(long countryId) {
		return getPersistence().findByCountryId(countryId);
	}

	/**
	* Returns a range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public static List<Region> findByCountryId(long countryId, int start,
		int end) {
		return getPersistence().findByCountryId(countryId, start, end);
	}

	/**
	* Returns an ordered range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByCountryId(long countryId, int start,
		int end, OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .findByCountryId(countryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByCountryId(long countryId, int start,
		int end, OrderByComparator<Region> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCountryId(countryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByCountryId_First(long countryId,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCountryId_First(countryId, orderByComparator);
	}

	/**
	* Returns the first region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByCountryId_First(long countryId,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .fetchByCountryId_First(countryId, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByCountryId_Last(long countryId,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCountryId_Last(countryId, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByCountryId_Last(long countryId,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .fetchByCountryId_Last(countryId, orderByComparator);
	}

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws NoSuchRegionException if a region with the primary key could not be found
	*/
	public static Region[] findByCountryId_PrevAndNext(long regionId,
		long countryId, OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCountryId_PrevAndNext(regionId, countryId,
			orderByComparator);
	}

	/**
	* Removes all the regions where countryId = &#63; from the database.
	*
	* @param countryId the country ID
	*/
	public static void removeByCountryId(long countryId) {
		getPersistence().removeByCountryId(countryId);
	}

	/**
	* Returns the number of regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the number of matching regions
	*/
	public static int countByCountryId(long countryId) {
		return getPersistence().countByCountryId(countryId);
	}

	/**
	* Returns all the regions where active = &#63;.
	*
	* @param active the active
	* @return the matching regions
	*/
	public static List<Region> findByActive(boolean active) {
		return getPersistence().findByActive(active);
	}

	/**
	* Returns a range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public static List<Region> findByActive(boolean active, int start, int end) {
		return getPersistence().findByActive(active, start, end);
	}

	/**
	* Returns an ordered range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByActive(boolean active, int start, int end,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByActive(boolean active, int start, int end,
		OrderByComparator<Region> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByActive_First(boolean active,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().findByActive_First(active, orderByComparator);
	}

	/**
	* Returns the first region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByActive_First(boolean active,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence().fetchByActive_First(active, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByActive_Last(boolean active,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByActive_Last(boolean active,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence().fetchByActive_Last(active, orderByComparator);
	}

	/**
	* Returns the regions before and after the current region in the ordered set where active = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws NoSuchRegionException if a region with the primary key could not be found
	*/
	public static Region[] findByActive_PrevAndNext(long regionId,
		boolean active, OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByActive_PrevAndNext(regionId, active, orderByComparator);
	}

	/**
	* Removes all the regions where active = &#63; from the database.
	*
	* @param active the active
	*/
	public static void removeByActive(boolean active) {
		getPersistence().removeByActive(active);
	}

	/**
	* Returns the number of regions where active = &#63;.
	*
	* @param active the active
	* @return the number of matching regions
	*/
	public static int countByActive(boolean active) {
		return getPersistence().countByActive(active);
	}

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or throws a <code>NoSuchRegionException</code> if it could not be found.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByC_R(long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().findByC_R(countryId, regionCode);
	}

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByC_R(long countryId, String regionCode) {
		return getPersistence().fetchByC_R(countryId, regionCode);
	}

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByC_R(long countryId, String regionCode,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_R(countryId, regionCode, retrieveFromCache);
	}

	/**
	* Removes the region where countryId = &#63; and regionCode = &#63; from the database.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the region that was removed
	*/
	public static Region removeByC_R(long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().removeByC_R(countryId, regionCode);
	}

	/**
	* Returns the number of regions where countryId = &#63; and regionCode = &#63;.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the number of matching regions
	*/
	public static int countByC_R(long countryId, String regionCode) {
		return getPersistence().countByC_R(countryId, regionCode);
	}

	/**
	* Returns all the regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the matching regions
	*/
	public static List<Region> findByC_A(long countryId, boolean active) {
		return getPersistence().findByC_A(countryId, active);
	}

	/**
	* Returns a range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public static List<Region> findByC_A(long countryId, boolean active,
		int start, int end) {
		return getPersistence().findByC_A(countryId, active, start, end);
	}

	/**
	* Returns an ordered range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByC_A(long countryId, boolean active,
		int start, int end, OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .findByC_A(countryId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching regions
	*/
	public static List<Region> findByC_A(long countryId, boolean active,
		int start, int end, OrderByComparator<Region> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_A(countryId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByC_A_First(long countryId, boolean active,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_First(countryId, active, orderByComparator);
	}

	/**
	* Returns the first region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByC_A_First(long countryId, boolean active,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_First(countryId, active, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws NoSuchRegionException if a matching region could not be found
	*/
	public static Region findByC_A_Last(long countryId, boolean active,
		OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_Last(countryId, active, orderByComparator);
	}

	/**
	* Returns the last region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public static Region fetchByC_A_Last(long countryId, boolean active,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_Last(countryId, active, orderByComparator);
	}

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws NoSuchRegionException if a region with the primary key could not be found
	*/
	public static Region[] findByC_A_PrevAndNext(long regionId, long countryId,
		boolean active, OrderByComparator<Region> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_PrevAndNext(regionId, countryId, active,
			orderByComparator);
	}

	/**
	* Removes all the regions where countryId = &#63; and active = &#63; from the database.
	*
	* @param countryId the country ID
	* @param active the active
	*/
	public static void removeByC_A(long countryId, boolean active) {
		getPersistence().removeByC_A(countryId, active);
	}

	/**
	* Returns the number of regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the number of matching regions
	*/
	public static int countByC_A(long countryId, boolean active) {
		return getPersistence().countByC_A(countryId, active);
	}

	/**
	* Caches the region in the entity cache if it is enabled.
	*
	* @param region the region
	*/
	public static void cacheResult(Region region) {
		getPersistence().cacheResult(region);
	}

	/**
	* Caches the regions in the entity cache if it is enabled.
	*
	* @param regions the regions
	*/
	public static void cacheResult(List<Region> regions) {
		getPersistence().cacheResult(regions);
	}

	/**
	* Creates a new region with the primary key. Does not add the region to the database.
	*
	* @param regionId the primary key for the new region
	* @return the new region
	*/
	public static Region create(long regionId) {
		return getPersistence().create(regionId);
	}

	/**
	* Removes the region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param regionId the primary key of the region
	* @return the region that was removed
	* @throws NoSuchRegionException if a region with the primary key could not be found
	*/
	public static Region remove(long regionId)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().remove(regionId);
	}

	public static Region updateImpl(Region region) {
		return getPersistence().updateImpl(region);
	}

	/**
	* Returns the region with the primary key or throws a <code>NoSuchRegionException</code> if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region
	* @throws NoSuchRegionException if a region with the primary key could not be found
	*/
	public static Region findByPrimaryKey(long regionId)
		throws com.liferay.portal.kernel.exception.NoSuchRegionException {
		return getPersistence().findByPrimaryKey(regionId);
	}

	/**
	* Returns the region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region, or <code>null</code> if a region with the primary key could not be found
	*/
	public static Region fetchByPrimaryKey(long regionId) {
		return getPersistence().fetchByPrimaryKey(regionId);
	}

	/**
	* Returns all the regions.
	*
	* @return the regions
	*/
	public static List<Region> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of regions
	*/
	public static List<Region> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of regions
	*/
	public static List<Region> findAll(int start, int end,
		OrderByComparator<Region> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of regions
	*/
	public static List<Region> findAll(int start, int end,
		OrderByComparator<Region> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the regions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of regions.
	*
	* @return the number of regions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RegionPersistence)PortalBeanLocatorUtil.locate(RegionPersistence.class.getName());

			ReferenceRegistry.registerReference(RegionUtil.class, "_persistence");
		}

		return _persistence;
	}

	private static RegionPersistence _persistence;
}