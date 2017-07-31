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

import com.liferay.commerce.address.exception.NoSuchRegionException;
import com.liferay.commerce.address.model.CommerceRegion;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce region service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.address.service.persistence.impl.CommerceRegionPersistenceImpl
 * @see CommerceRegionUtil
 * @generated
 */
@ProviderType
public interface CommerceRegionPersistence extends BasePersistence<CommerceRegion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceRegionUtil} to access the commerce region persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce regions
	*/
	public java.util.List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId);

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
	public java.util.List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end);

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
	public java.util.List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator);

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
	public java.util.List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public CommerceRegion findByCommerceCountryId_First(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException;

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public CommerceRegion fetchByCommerceCountryId_First(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator);

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public CommerceRegion findByCommerceCountryId_Last(long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException;

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public CommerceRegion fetchByCommerceCountryId_Last(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator);

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public CommerceRegion[] findByCommerceCountryId_PrevAndNext(
		long commerceRegionId, long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException;

	/**
	* Removes all the commerce regions where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public void removeByCommerceCountryId(long commerceCountryId);

	/**
	* Returns the number of commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce regions
	*/
	public int countByCommerceCountryId(long commerceCountryId);

	/**
	* Caches the commerce region in the entity cache if it is enabled.
	*
	* @param commerceRegion the commerce region
	*/
	public void cacheResult(CommerceRegion commerceRegion);

	/**
	* Caches the commerce regions in the entity cache if it is enabled.
	*
	* @param commerceRegions the commerce regions
	*/
	public void cacheResult(java.util.List<CommerceRegion> commerceRegions);

	/**
	* Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	*
	* @param commerceRegionId the primary key for the new commerce region
	* @return the new commerce region
	*/
	public CommerceRegion create(long commerceRegionId);

	/**
	* Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region that was removed
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public CommerceRegion remove(long commerceRegionId)
		throws NoSuchRegionException;

	public CommerceRegion updateImpl(CommerceRegion commerceRegion);

	/**
	* Returns the commerce region with the primary key or throws a {@link NoSuchRegionException} if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public CommerceRegion findByPrimaryKey(long commerceRegionId)
		throws NoSuchRegionException;

	/**
	* Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	*/
	public CommerceRegion fetchByPrimaryKey(long commerceRegionId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceRegion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce regions.
	*
	* @return the commerce regions
	*/
	public java.util.List<CommerceRegion> findAll();

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
	public java.util.List<CommerceRegion> findAll(int start, int end);

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
	public java.util.List<CommerceRegion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator);

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
	public java.util.List<CommerceRegion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce regions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce regions.
	*
	* @return the number of commerce regions
	*/
	public int countAll();
}