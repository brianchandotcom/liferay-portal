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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.shopping.exception.NoSuchCartException;
import com.liferay.shopping.model.ShoppingCart;

/**
 * The persistence interface for the shopping cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingCartPersistenceImpl
 * @see ShoppingCartUtil
 * @generated
 */
@ProviderType
public interface ShoppingCartPersistence extends BasePersistence<ShoppingCart> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCartUtil} to access the shopping cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByGroupId(long groupId);

	/**
	* Returns a range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns an ordered range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws NoSuchCartException if a matching shopping cart could not be found
	*/
	public ShoppingCart findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns the last shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws NoSuchCartException if a matching shopping cart could not be found
	*/
	public ShoppingCart findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where groupId = &#63;.
	*
	* @param cartId the primary key of the current shopping cart
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws NoSuchCartException if a shopping cart with the primary key could not be found
	*/
	public ShoppingCart[] findByGroupId_PrevAndNext(long cartId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the shopping carts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping carts
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByUserId(long userId);

	/**
	* Returns a range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByUserId(long userId, int start,
		int end);

	/**
	* Returns an ordered range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns an ordered range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping carts
	*/
	public java.util.List<ShoppingCart> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws NoSuchCartException if a matching shopping cart could not be found
	*/
	public ShoppingCart findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns the last shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws NoSuchCartException if a matching shopping cart could not be found
	*/
	public ShoppingCart findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where userId = &#63;.
	*
	* @param cartId the primary key of the current shopping cart
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws NoSuchCartException if a shopping cart with the primary key could not be found
	*/
	public ShoppingCart[] findByUserId_PrevAndNext(long cartId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the shopping carts where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching shopping carts
	*/
	public int countByUserId(long userId);

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart
	* @throws NoSuchCartException if a matching shopping cart could not be found
	*/
	public ShoppingCart findByG_U(long groupId, long userId)
		throws NoSuchCartException;

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByG_U(long groupId, long userId);

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	*/
	public ShoppingCart fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache);

	/**
	* Removes the shopping cart where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the shopping cart that was removed
	*/
	public ShoppingCart removeByG_U(long groupId, long userId)
		throws NoSuchCartException;

	/**
	* Returns the number of shopping carts where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching shopping carts
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Caches the shopping cart in the entity cache if it is enabled.
	*
	* @param shoppingCart the shopping cart
	*/
	public void cacheResult(ShoppingCart shoppingCart);

	/**
	* Caches the shopping carts in the entity cache if it is enabled.
	*
	* @param shoppingCarts the shopping carts
	*/
	public void cacheResult(java.util.List<ShoppingCart> shoppingCarts);

	/**
	* Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	*
	* @param cartId the primary key for the new shopping cart
	* @return the new shopping cart
	*/
	public ShoppingCart create(long cartId);

	/**
	* Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart that was removed
	* @throws NoSuchCartException if a shopping cart with the primary key could not be found
	*/
	public ShoppingCart remove(long cartId) throws NoSuchCartException;

	public ShoppingCart updateImpl(ShoppingCart shoppingCart);

	/**
	* Returns the shopping cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart
	* @throws NoSuchCartException if a shopping cart with the primary key could not be found
	*/
	public ShoppingCart findByPrimaryKey(long cartId)
		throws NoSuchCartException;

	/**
	* Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	*/
	public ShoppingCart fetchByPrimaryKey(long cartId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping carts.
	*
	* @return the shopping carts
	*/
	public java.util.List<ShoppingCart> findAll();

	/**
	* Returns a range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of shopping carts
	*/
	public java.util.List<ShoppingCart> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping carts
	*/
	public java.util.List<ShoppingCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator);

	/**
	* Returns an ordered range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping carts
	*/
	public java.util.List<ShoppingCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping carts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping carts.
	*
	* @return the number of shopping carts
	*/
	public int countAll();
}