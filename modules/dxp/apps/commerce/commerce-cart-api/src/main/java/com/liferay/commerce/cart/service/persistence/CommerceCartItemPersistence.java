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

package com.liferay.commerce.cart.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.exception.NoSuchCartItemException;
import com.liferay.commerce.cart.model.CommerceCartItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce cart item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.cart.service.persistence.impl.CommerceCartItemPersistenceImpl
 * @see CommerceCartItemUtil
 * @generated
 */
@ProviderType
public interface CommerceCartItemPersistence extends BasePersistence<CommerceCartItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCartItemUtil} to access the commerce cart item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce cart items where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @return the matching commerce cart items
	*/
	public java.util.List<CommerceCartItem> findByCommerceCartId(
		long CommerceCartId);

	/**
	* Returns a range of all the commerce cart items where CommerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CommerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of matching commerce cart items
	*/
	public java.util.List<CommerceCartItem> findByCommerceCartId(
		long CommerceCartId, int start, int end);

	/**
	* Returns an ordered range of all the commerce cart items where CommerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CommerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cart items
	*/
	public java.util.List<CommerceCartItem> findByCommerceCartId(
		long CommerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce cart items where CommerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CommerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cart items
	*/
	public java.util.List<CommerceCartItem> findByCommerceCartId(
		long CommerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart item in the ordered set where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public CommerceCartItem findByCommerceCartId_First(long CommerceCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException;

	/**
	* Returns the first commerce cart item in the ordered set where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public CommerceCartItem fetchByCommerceCartId_First(long CommerceCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator);

	/**
	* Returns the last commerce cart item in the ordered set where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public CommerceCartItem findByCommerceCartId_Last(long CommerceCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException;

	/**
	* Returns the last commerce cart item in the ordered set where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public CommerceCartItem fetchByCommerceCartId_Last(long CommerceCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator);

	/**
	* Returns the commerce cart items before and after the current commerce cart item in the ordered set where CommerceCartId = &#63;.
	*
	* @param CommerceCartItemId the primary key of the current commerce cart item
	* @param CommerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public CommerceCartItem[] findByCommerceCartId_PrevAndNext(
		long CommerceCartItemId, long CommerceCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException;

	/**
	* Removes all the commerce cart items where CommerceCartId = &#63; from the database.
	*
	* @param CommerceCartId the commerce cart ID
	*/
	public void removeByCommerceCartId(long CommerceCartId);

	/**
	* Returns the number of commerce cart items where CommerceCartId = &#63;.
	*
	* @param CommerceCartId the commerce cart ID
	* @return the number of matching commerce cart items
	*/
	public int countByCommerceCartId(long CommerceCartId);

	/**
	* Caches the commerce cart item in the entity cache if it is enabled.
	*
	* @param commerceCartItem the commerce cart item
	*/
	public void cacheResult(CommerceCartItem commerceCartItem);

	/**
	* Caches the commerce cart items in the entity cache if it is enabled.
	*
	* @param commerceCartItems the commerce cart items
	*/
	public void cacheResult(java.util.List<CommerceCartItem> commerceCartItems);

	/**
	* Creates a new commerce cart item with the primary key. Does not add the commerce cart item to the database.
	*
	* @param CommerceCartItemId the primary key for the new commerce cart item
	* @return the new commerce cart item
	*/
	public CommerceCartItem create(long CommerceCartItemId);

	/**
	* Removes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item that was removed
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public CommerceCartItem remove(long CommerceCartItemId)
		throws NoSuchCartItemException;

	public CommerceCartItem updateImpl(CommerceCartItem commerceCartItem);

	/**
	* Returns the commerce cart item with the primary key or throws a {@link NoSuchCartItemException} if it could not be found.
	*
	* @param CommerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public CommerceCartItem findByPrimaryKey(long CommerceCartItemId)
		throws NoSuchCartItemException;

	/**
	* Returns the commerce cart item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CommerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item, or <code>null</code> if a commerce cart item with the primary key could not be found
	*/
	public CommerceCartItem fetchByPrimaryKey(long CommerceCartItemId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCartItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce cart items.
	*
	* @return the commerce cart items
	*/
	public java.util.List<CommerceCartItem> findAll();

	/**
	* Returns a range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of commerce cart items
	*/
	public java.util.List<CommerceCartItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce cart items
	*/
	public java.util.List<CommerceCartItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce cart items
	*/
	public java.util.List<CommerceCartItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce cart items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce cart items.
	*
	* @return the number of commerce cart items
	*/
	public int countAll();
}