/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.shopping.model.ShoppingItemPrice;

import java.util.List;

/**
 * The persistence utility for the shopping item price service. This utility wraps {@link ShoppingItemPricePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPricePersistence
 * @see ShoppingItemPricePersistenceImpl
 * @generated
 */
public class ShoppingItemPriceUtil {
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
	public static void clearCache(ShoppingItemPrice shoppingItemPrice) {
		getPersistence().clearCache(shoppingItemPrice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingItemPrice> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingItemPrice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingItemPrice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingItemPrice update(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		return getPersistence().update(shoppingItemPrice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingItemPrice update(
		ShoppingItemPrice shoppingItemPrice, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(shoppingItemPrice, serviceContext);
	}

	/**
	* Returns all the shopping item prices where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the matching shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId);
	}

	/**
	* Returns a range of all the shopping item prices where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @return the range of matching shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByItemId(itemId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping item prices where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findByItemId(
		long itemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByItemId(itemId, start, end, orderByComparator);
	}

	/**
	* Returns the first shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item price
	* @throws com.liferay.portlet.shopping.NoSuchItemPriceException if a matching shopping item price could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_First(
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByItemId_First(itemId, orderByComparator);
	}

	/**
	* Returns the first shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByItemId_First(
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByItemId_First(itemId, orderByComparator);
	}

	/**
	* Returns the last shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item price
	* @throws com.liferay.portlet.shopping.NoSuchItemPriceException if a matching shopping item price could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByItemId_Last(
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByItemId_Last(itemId, orderByComparator);
	}

	/**
	* Returns the last shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByItemId_Last(
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByItemId_Last(itemId, orderByComparator);
	}

	/**
	* Returns the shopping item prices before and after the current shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemPriceId the primary key of the current shopping item price
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item price
	* @throws com.liferay.portlet.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice[] findByItemId_PrevAndNext(
		long itemPriceId, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence()
				   .findByItemId_PrevAndNext(itemPriceId, itemId,
			orderByComparator);
	}

	/**
	* Removes all the shopping item prices where itemId = &#63; from the database.
	*
	* @param itemId the item ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByItemId(itemId);
	}

	/**
	* Returns the number of shopping item prices where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the number of matching shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByItemId(long itemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByItemId(itemId);
	}

	/**
	* Caches the shopping item price in the entity cache if it is enabled.
	*
	* @param shoppingItemPrice the shopping item price
	*/
	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		getPersistence().cacheResult(shoppingItemPrice);
	}

	/**
	* Caches the shopping item prices in the entity cache if it is enabled.
	*
	* @param shoppingItemPrices the shopping item prices
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> shoppingItemPrices) {
		getPersistence().cacheResult(shoppingItemPrices);
	}

	/**
	* Creates a new shopping item price with the primary key. Does not add the shopping item price to the database.
	*
	* @param itemPriceId the primary key for the new shopping item price
	* @return the new shopping item price
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice create(
		long itemPriceId) {
		return getPersistence().create(itemPriceId);
	}

	/**
	* Removes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price that was removed
	* @throws com.liferay.portlet.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice remove(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().remove(itemPriceId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemPrice updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemPrice shoppingItemPrice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingItemPrice);
	}

	/**
	* Returns the shopping item price with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchItemPriceException} if it could not be found.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price
	* @throws com.liferay.portlet.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice findByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemPriceException {
		return getPersistence().findByPrimaryKey(itemPriceId);
	}

	/**
	* Returns the shopping item price with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price, or <code>null</code> if a shopping item price with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItemPrice fetchByPrimaryKey(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(itemPriceId);
	}

	/**
	* Returns all the shopping item prices.
	*
	* @return the shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @return the range of shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the shopping item prices from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping item prices.
	*
	* @return the number of shopping item prices
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingItemPricePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingItemPricePersistence)PortalBeanLocatorUtil.locate(ShoppingItemPricePersistence.class.getName());

			ReferenceRegistry.registerReference(ShoppingItemPriceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ShoppingItemPricePersistence persistence) {
	}

	private static ShoppingItemPricePersistence _persistence;
}