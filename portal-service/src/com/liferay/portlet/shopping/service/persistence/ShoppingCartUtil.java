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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.shopping.model.ShoppingCart;

import java.util.List;

/**
 * The persistence utility for the shopping cart service. This utility wraps {@link ShoppingCartPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartPersistence
 * @see ShoppingCartPersistenceImpl
 * @generated
 */
public class ShoppingCartUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ShoppingCart shoppingCart) {
		getPersistence().clearCache(shoppingCart);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingCart update(ShoppingCart shoppingCart)
		throws SystemException {
		return getPersistence().update(shoppingCart);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingCart update(ShoppingCart shoppingCart,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(shoppingCart, serviceContext);
	}

	/**
	* Returns all the shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where groupId = &#63;.
	*
	* @param cartId the primary key of the current shopping cart
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByGroupId_PrevAndNext(
		long cartId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(cartId, groupId, orderByComparator);
	}

	/**
	* Removes all the shopping carts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of shopping carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping carts where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last shopping cart in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the shopping carts before and after the current shopping cart in the ordered set where userId = &#63;.
	*
	* @param cartId the primary key of the current shopping cart
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByUserId_PrevAndNext(
		long cartId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence()
				   .findByUserId_PrevAndNext(cartId, userId, orderByComparator);
	}

	/**
	* Removes all the shopping carts where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of shopping carts where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	/**
	* Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	/**
	* Removes the shopping cart where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the shopping cart that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart removeByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Returns the number of shopping carts where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Caches the shopping cart in the entity cache if it is enabled.
	*
	* @param shoppingCart the shopping cart
	*/
	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart) {
		getPersistence().cacheResult(shoppingCart);
	}

	/**
	* Caches the shopping carts in the entity cache if it is enabled.
	*
	* @param shoppingCarts the shopping carts
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> shoppingCarts) {
		getPersistence().cacheResult(shoppingCarts);
	}

	/**
	* Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	*
	* @param cartId the primary key for the new shopping cart
	* @return the new shopping cart
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart create(
		long cartId) {
		return getPersistence().create(cartId);
	}

	/**
	* Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart that was removed
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		long cartId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().remove(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingCart);
	}

	/**
	* Returns the shopping cart with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart
	* @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		long cartId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByPrimaryKey(cartId);
	}

	/**
	* Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByPrimaryKey(
		long cartId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(cartId);
	}

	/**
	* Returns all the shopping carts.
	*
	* @return the shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the shopping carts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping carts.
	*
	* @return the number of shopping carts
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingCartPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingCartPersistence)PortalBeanLocatorUtil.locate(ShoppingCartPersistence.class.getName());

			ReferenceRegistry.registerReference(ShoppingCartUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ShoppingCartPersistence persistence) {
	}

	private static ShoppingCartPersistence _persistence;
}