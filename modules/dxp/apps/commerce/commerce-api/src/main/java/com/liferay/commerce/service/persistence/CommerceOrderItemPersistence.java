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

import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.model.CommerceOrderItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce order item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderItemPersistenceImpl
 * @see CommerceOrderItemUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderItemPersistence extends BasePersistence<CommerceOrderItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderItemUtil} to access the commerce order item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce order items where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId);

	/**
	* Returns a range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end);

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByCommerceOrderId_First(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByCommerceOrderId_First(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByCommerceOrderId_Last(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByCommerceOrderId_Last(long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderItemId, long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Removes all the commerce order items where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public void removeByCommerceOrderId(long commerceOrderId);

	/**
	* Returns the number of commerce order items where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order items
	*/
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	* Returns all the commerce order items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId);

	/**
	* Returns a range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem[] findByCPInstanceId_PrevAndNext(
		long commerceOrderItemId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Removes all the commerce order items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	* Returns the number of commerce order items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce order items
	*/
	public int countByCPInstanceId(long CPInstanceId);

	/**
	* Returns all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId);

	/**
	* Returns a range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public java.util.List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByC_I_First(long commerceOrderId,
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByC_I_First(long commerceOrderId,
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public CommerceOrderItem findByC_I_Last(long commerceOrderId,
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public CommerceOrderItem fetchByC_I_Last(long commerceOrderId,
		long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem[] findByC_I_PrevAndNext(long commerceOrderItemId,
		long commerceOrderId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException;

	/**
	* Removes all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	*/
	public void removeByC_I(long commerceOrderId, long CPInstanceId);

	/**
	* Returns the number of commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce order items
	*/
	public int countByC_I(long commerceOrderId, long CPInstanceId);

	/**
	* Caches the commerce order item in the entity cache if it is enabled.
	*
	* @param commerceOrderItem the commerce order item
	*/
	public void cacheResult(CommerceOrderItem commerceOrderItem);

	/**
	* Caches the commerce order items in the entity cache if it is enabled.
	*
	* @param commerceOrderItems the commerce order items
	*/
	public void cacheResult(
		java.util.List<CommerceOrderItem> commerceOrderItems);

	/**
	* Creates a new commerce order item with the primary key. Does not add the commerce order item to the database.
	*
	* @param commerceOrderItemId the primary key for the new commerce order item
	* @return the new commerce order item
	*/
	public CommerceOrderItem create(long commerceOrderItemId);

	/**
	* Removes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item that was removed
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem remove(long commerceOrderItemId)
		throws NoSuchOrderItemException;

	public CommerceOrderItem updateImpl(CommerceOrderItem commerceOrderItem);

	/**
	* Returns the commerce order item with the primary key or throws a {@link NoSuchOrderItemException} if it could not be found.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem findByPrimaryKey(long commerceOrderItemId)
		throws NoSuchOrderItemException;

	/**
	* Returns the commerce order item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item, or <code>null</code> if a commerce order item with the primary key could not be found
	*/
	public CommerceOrderItem fetchByPrimaryKey(long commerceOrderItemId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceOrderItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce order items.
	*
	* @return the commerce order items
	*/
	public java.util.List<CommerceOrderItem> findAll();

	/**
	* Returns a range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of commerce order items
	*/
	public java.util.List<CommerceOrderItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce order items
	*/
	public java.util.List<CommerceOrderItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce order items
	*/
	public java.util.List<CommerceOrderItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce order items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce order items.
	*
	* @return the number of commerce order items
	*/
	public int countAll();
}