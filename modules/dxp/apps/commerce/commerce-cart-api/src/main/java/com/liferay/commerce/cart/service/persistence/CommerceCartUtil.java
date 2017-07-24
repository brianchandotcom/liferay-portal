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

import com.liferay.commerce.cart.model.CommerceCart;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce cart service. This utility wraps {@link com.liferay.commerce.cart.service.persistence.impl.CommerceCartPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCartPersistence
 * @see com.liferay.commerce.cart.service.persistence.impl.CommerceCartPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCartUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CommerceCart commerceCart) {
		getPersistence().clearCache(commerceCart);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCart update(CommerceCart commerceCart) {
		return getPersistence().update(commerceCart);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCart update(CommerceCart commerceCart,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceCart, serviceContext);
	}

	/**
	* Returns all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByG_T(long groupId, int type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end) {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_T(long groupId, int type,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_T_First(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().findByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_T_First(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_T_Last(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().findByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_T_Last(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().fetchByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByG_T_PrevAndNext(long commerceCartId,
		long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_T_PrevAndNext(commerceCartId, groupId, type,
			orderByComparator);
	}

	/**
	* Removes all the commerce carts where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public static void removeByG_T(long groupId, int type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	* Returns the number of commerce carts where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching commerce carts
	*/
	public static int countByG_T(long groupId, int type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	* Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; and name = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param name the name
	* @return the matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_U_T_N(long groupId, long userId,
		int type, java.lang.String name)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().findByG_U_T_N(groupId, userId, type, name);
	}

	/**
	* Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param name the name
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_T_N(long groupId, long userId,
		int type, java.lang.String name) {
		return getPersistence().fetchByG_U_T_N(groupId, userId, type, name);
	}

	/**
	* Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_T_N(long groupId, long userId,
		int type, java.lang.String name, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_U_T_N(groupId, userId, type, name,
			retrieveFromCache);
	}

	/**
	* Removes the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param name the name
	* @return the commerce cart that was removed
	*/
	public static CommerceCart removeByG_U_T_N(long groupId, long userId,
		int type, java.lang.String name)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().removeByG_U_T_N(groupId, userId, type, name);
	}

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and type = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param type the type
	* @param name the name
	* @return the number of matching commerce carts
	*/
	public static int countByG_U_T_N(long groupId, long userId, int type,
		java.lang.String name) {
		return getPersistence().countByG_U_T_N(groupId, userId, type, name);
	}

	/**
	* Caches the commerce cart in the entity cache if it is enabled.
	*
	* @param commerceCart the commerce cart
	*/
	public static void cacheResult(CommerceCart commerceCart) {
		getPersistence().cacheResult(commerceCart);
	}

	/**
	* Caches the commerce carts in the entity cache if it is enabled.
	*
	* @param commerceCarts the commerce carts
	*/
	public static void cacheResult(List<CommerceCart> commerceCarts) {
		getPersistence().cacheResult(commerceCarts);
	}

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param commerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	public static CommerceCart create(long commerceCartId) {
		return getPersistence().create(commerceCartId);
	}

	/**
	* Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart remove(long commerceCartId)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().remove(commerceCartId);
	}

	public static CommerceCart updateImpl(CommerceCart commerceCart) {
		return getPersistence().updateImpl(commerceCart);
	}

	/**
	* Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart findByPrimaryKey(long commerceCartId)
		throws com.liferay.commerce.cart.exception.NoSuchCartException {
		return getPersistence().findByPrimaryKey(commerceCartId);
	}

	/**
	* Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart fetchByPrimaryKey(long commerceCartId) {
		return getPersistence().fetchByPrimaryKey(commerceCartId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce carts.
	*
	* @return the commerce carts
	*/
	public static List<CommerceCart> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce carts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce carts.
	*
	* @return the number of commerce carts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceCartPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartPersistence, CommerceCartPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartPersistence.class);
}