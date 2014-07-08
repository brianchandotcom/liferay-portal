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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public interface RegionPersistence extends BasePersistence<Region> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegionUtil} to access the region persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId);

	/**
	* Returns a range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end);

	/**
	* Returns an ordered range of all the regions where countryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByCountryId_First(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the first region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByCountryId_First(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByCountryId_Last(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the last region in the ordered set where countryId = &#63;.
	*
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByCountryId_Last(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	*/
	public com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		long regionId, long countryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Removes all the regions where countryId = &#63; from the database.
	*
	* @param countryId the country ID
	*/
	public void removeByCountryId(long countryId);

	/**
	* Returns the number of regions where countryId = &#63;.
	*
	* @param countryId the country ID
	* @return the number of matching regions
	*/
	public int countByCountryId(long countryId);

	/**
	* Returns all the regions where active = &#63;.
	*
	* @param active the active
	* @return the matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active);

	/**
	* Returns a range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the regions where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the first region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the last region in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the regions before and after the current region in the ordered set where active = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	*/
	public com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		long regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Removes all the regions where active = &#63; from the database.
	*
	* @param active the active
	*/
	public void removeByActive(boolean active);

	/**
	* Returns the number of regions where active = &#63;.
	*
	* @param active the active
	* @return the number of matching regions
	*/
	public int countByActive(boolean active);

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or throws a {@link com.liferay.portal.NoSuchRegionException} if it could not be found.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByC_R(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByC_R(long countryId,
		java.lang.String regionCode);

	/**
	* Returns the region where countryId = &#63; and regionCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByC_R(long countryId,
		java.lang.String regionCode, boolean retrieveFromCache);

	/**
	* Removes the region where countryId = &#63; and regionCode = &#63; from the database.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the region that was removed
	*/
	public com.liferay.portal.model.Region removeByC_R(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the number of regions where countryId = &#63; and regionCode = &#63;.
	*
	* @param countryId the country ID
	* @param regionCode the region code
	* @return the number of matching regions
	*/
	public int countByC_R(long countryId, java.lang.String regionCode);

	/**
	* Returns all the regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active);

	/**
	* Returns a range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the regions where countryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param countryId the country ID
	* @param active the active
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByC_A_First(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the first region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByC_A_First(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region
	* @throws com.liferay.portal.NoSuchRegionException if a matching region could not be found
	*/
	public com.liferay.portal.model.Region findByC_A_Last(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the last region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching region, or <code>null</code> if a matching region could not be found
	*/
	public com.liferay.portal.model.Region fetchByC_A_Last(long countryId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the regions before and after the current region in the ordered set where countryId = &#63; and active = &#63;.
	*
	* @param regionId the primary key of the current region
	* @param countryId the country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	*/
	public com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		long regionId, long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Removes all the regions where countryId = &#63; and active = &#63; from the database.
	*
	* @param countryId the country ID
	* @param active the active
	*/
	public void removeByC_A(long countryId, boolean active);

	/**
	* Returns the number of regions where countryId = &#63; and active = &#63;.
	*
	* @param countryId the country ID
	* @param active the active
	* @return the number of matching regions
	*/
	public int countByC_A(long countryId, boolean active);

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
	*/
	public com.liferay.portal.model.Region remove(long regionId)
		throws com.liferay.portal.NoSuchRegionException;

	public com.liferay.portal.model.Region updateImpl(
		com.liferay.portal.model.Region region);

	/**
	* Returns the region with the primary key or throws a {@link com.liferay.portal.NoSuchRegionException} if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region
	* @throws com.liferay.portal.NoSuchRegionException if a region with the primary key could not be found
	*/
	public com.liferay.portal.model.Region findByPrimaryKey(long regionId)
		throws com.liferay.portal.NoSuchRegionException;

	/**
	* Returns the region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param regionId the primary key of the region
	* @return the region, or <code>null</code> if a region with the primary key could not be found
	*/
	public com.liferay.portal.model.Region fetchByPrimaryKey(long regionId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.Region> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the regions.
	*
	* @return the regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll();

	/**
	* Returns a range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @return the range of regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of regions
	* @param end the upper bound of the range of regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of regions
	*/
	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the regions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of regions.
	*
	* @return the number of regions
	*/
	public int countAll();
}