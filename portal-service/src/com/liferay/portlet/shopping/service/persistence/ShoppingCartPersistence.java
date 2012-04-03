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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.model.ShoppingCart;

/**
 * The persistence interface for the shopping cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartPersistenceImpl
 * @see ShoppingCartUtil
 * @generated
 */
public interface ShoppingCartPersistence extends BasePersistence<ShoppingCart> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCartUtil} to access the shopping cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the shopping cart in the entity cache if it is enabled.
	*
	* @param shoppingCart the shopping cart
	*/
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart);

	/**
	* Caches the shopping carts in the entity cache if it is enabled.
	*
	* @param shoppingCarts the shopping carts
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> shoppingCarts);

	/**
	* Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	*
	* @param cartId the primary key for the new shopping cart
	* @return the new shopping cart
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart create(long cartId);

	/**
	* Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart that was removed
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart remove(long cartId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	public com.liferay.portlet.shopping.model.ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping cart with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		long cartId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart fetchByPrimaryKey(
		long cartId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first shopping cart in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the last shopping cart in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cartId the primary key of the current shopping cart
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart[] findByGroupId_PrevAndNext(
		long cartId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns all the shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first shopping cart in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the last shopping cart in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cartId the primary key of the current shopping cart
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart[] findByUserId_PrevAndNext(
		long cartId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping carts.
	*
	* @return the shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shopping carts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shopping carts where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the shopping cart where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the shopping cart that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCart removeByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException;

	/**
	* Removes all the shopping carts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping carts where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping carts.
	*
	* @return the number of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}