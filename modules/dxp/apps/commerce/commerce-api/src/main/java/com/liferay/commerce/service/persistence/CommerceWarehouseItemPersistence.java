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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchWarehouseItemException;
import com.liferay.commerce.model.CommerceWarehouseItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce warehouse item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceWarehouseItemPersistenceImpl
 * @see CommerceWarehouseItemUtil
 * @generated
 */
@ProviderType
public interface CommerceWarehouseItemPersistence extends BasePersistence<CommerceWarehouseItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWarehouseItemUtil} to access the commerce warehouse item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @return the matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId);

	/**
	* Returns a range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end);

	/**
	* Returns an ordered range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem findByCommerceWarehouseId_First(
		long commerceWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Returns the first commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByCommerceWarehouseId_First(
		long commerceWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns the last commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem findByCommerceWarehouseId_Last(
		long commerceWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Returns the last commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByCommerceWarehouseId_Last(
		long commerceWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns the commerce warehouse items before and after the current commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseItemId the primary key of the current commerce warehouse item
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public CommerceWarehouseItem[] findByCommerceWarehouseId_PrevAndNext(
		long commerceWarehouseItemId, long commerceWarehouseId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Removes all the commerce warehouse items where commerceWarehouseId = &#63; from the database.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	*/
	public void removeByCommerceWarehouseId(long commerceWarehouseId);

	/**
	* Returns the number of commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @return the number of matching commerce warehouse items
	*/
	public int countByCommerceWarehouseId(long commerceWarehouseId);

	/**
	* Returns all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId);

	/**
	* Returns a range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem findByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Returns the first commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns the last commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem findByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Returns the last commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns the commerce warehouse items before and after the current commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceWarehouseItemId the primary key of the current commerce warehouse item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public CommerceWarehouseItem[] findByCPInstanceId_PrevAndNext(
		long commerceWarehouseItemId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws NoSuchWarehouseItemException;

	/**
	* Removes all the commerce warehouse items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	* Returns the number of commerce warehouse items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce warehouse items
	*/
	public int countByCPInstanceId(long CPInstanceId);

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or throws a {@link NoSuchWarehouseItemException} if it could not be found.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem findByC_C(long commerceWarehouseId,
		long CPInstanceId) throws NoSuchWarehouseItemException;

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByC_C(long commerceWarehouseId,
		long CPInstanceId);

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public CommerceWarehouseItem fetchByC_C(long commerceWarehouseId,
		long CPInstanceId, boolean retrieveFromCache);

	/**
	* Removes the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; from the database.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the commerce warehouse item that was removed
	*/
	public CommerceWarehouseItem removeByC_C(long commerceWarehouseId,
		long CPInstanceId) throws NoSuchWarehouseItemException;

	/**
	* Returns the number of commerce warehouse items where commerceWarehouseId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce warehouse items
	*/
	public int countByC_C(long commerceWarehouseId, long CPInstanceId);

	/**
	* Caches the commerce warehouse item in the entity cache if it is enabled.
	*
	* @param commerceWarehouseItem the commerce warehouse item
	*/
	public void cacheResult(CommerceWarehouseItem commerceWarehouseItem);

	/**
	* Caches the commerce warehouse items in the entity cache if it is enabled.
	*
	* @param commerceWarehouseItems the commerce warehouse items
	*/
	public void cacheResult(
		java.util.List<CommerceWarehouseItem> commerceWarehouseItems);

	/**
	* Creates a new commerce warehouse item with the primary key. Does not add the commerce warehouse item to the database.
	*
	* @param commerceWarehouseItemId the primary key for the new commerce warehouse item
	* @return the new commerce warehouse item
	*/
	public CommerceWarehouseItem create(long commerceWarehouseItemId);

	/**
	* Removes the commerce warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item that was removed
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public CommerceWarehouseItem remove(long commerceWarehouseItemId)
		throws NoSuchWarehouseItemException;

	public CommerceWarehouseItem updateImpl(
		CommerceWarehouseItem commerceWarehouseItem);

	/**
	* Returns the commerce warehouse item with the primary key or throws a {@link NoSuchWarehouseItemException} if it could not be found.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public CommerceWarehouseItem findByPrimaryKey(long commerceWarehouseItemId)
		throws NoSuchWarehouseItemException;

	/**
	* Returns the commerce warehouse item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item, or <code>null</code> if a commerce warehouse item with the primary key could not be found
	*/
	public CommerceWarehouseItem fetchByPrimaryKey(long commerceWarehouseItemId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceWarehouseItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce warehouse items.
	*
	* @return the commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findAll();

	/**
	* Returns a range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce warehouse items
	*/
	public java.util.List<CommerceWarehouseItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce warehouse items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce warehouse items.
	*
	* @return the number of commerce warehouse items
	*/
	public int countAll();
}