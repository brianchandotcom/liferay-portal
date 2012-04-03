/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Region;

/**
 * The persistence interface for the region service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegionPersistenceImpl
 * @see RegionUtil
 * @generated
 */
public interface RegionPersistence extends BasePersistence<Region> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegionUtil} to access the region persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the region in the entity cache if it is enabled.
	*
	* @param region the region
	*/
	public void cacheResult(com.liferay.portal.model.Region region);

	/**
	* Caches the regions in the entity cache if it is enabled.
	*
	* @param regions the regions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Region> regions);

	/**
	* Creates a new region with the primary key. Does not add the region to the database.
	*
	* @param regionId the primary key for the new region
	* @return the new region
	*/
	public com.liferay.portal.model.Region create(long regionId);

	/**
	* Removes the region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param regionId the primary key of the region
	* @return the region that was removed
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region remove(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region updateImpl(
		com.liferay.portal.model.Region region, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the region with the primary key or throws a {@link com.liferay.portal.NoSuchRegionException} if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByPrimaryKey(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region, or <code>null</code> if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region fetchByPrimaryKey(long regionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first region in the ordered set where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByCountryId_First(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last region in the ordered set where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByCountryId_Last(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		long regionId, long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the regions where active = &#63;.
	*
	* @param active the active
	* @return the matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first region in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last region in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the regions before and after the current region in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param regionId the primary key of the current region
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		long regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or throws a {@link com.liferay.portal.NoSuchRegionException} if it could not be found.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByC_R(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region, or <code>null</code> if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region fetchByC_R(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching region, or <code>null</code> if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region fetchByC_R(long countryId,
		java.lang.String regionCode, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByC_A_First(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region findByC_A_Last(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		long regionId, long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the regions.
	*
	* @return the regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of regions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the regions where countryId = &#63; from the database.
	*
	* @param countryId the country ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCountryId(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the regions where active = &#63; from the database.
	*
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the region where countryId = &#63; and regionCode = &#63; from the database.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the region that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Region removeByC_R(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the regions where countryId = &#63; and active = &#63; from the database.
	*
	* @param countryId the country ID
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_A(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the regions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the number of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public int countByCountryId(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of regions where active = &#63;.
	*
	* @param active the active
	* @return the number of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of regions where countryId = &#63; and regionCode = &#63;.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the number of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R(long countryId, java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the number of matching regions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_A(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of regions.
	*
	* @return the number of regions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}