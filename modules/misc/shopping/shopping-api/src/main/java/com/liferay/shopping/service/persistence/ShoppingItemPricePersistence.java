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

import com.liferay.shopping.exception.NoSuchItemPriceException;
import com.liferay.shopping.model.ShoppingItemPrice;

/**
 * The persistence interface for the shopping item price service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingItemPricePersistenceImpl
 * @see ShoppingItemPriceUtil
 * @generated
 */
@ProviderType
public interface ShoppingItemPricePersistence extends BasePersistence<ShoppingItemPrice> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingItemPriceUtil} to access the shopping item price persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping item prices where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the matching shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findByItemId(long itemId);

	/**
	* Returns a range of all the shopping item prices where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @return the range of matching shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findByItemId(long itemId,
		int start, int end);

	/**
	* Returns an ordered range of all the shopping item prices where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findByItemId(long itemId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator);

	/**
	* Returns an ordered range of all the shopping item prices where itemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param itemId the item ID
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findByItemId(long itemId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item price
	* @throws NoSuchItemPriceException if a matching shopping item price could not be found
	*/
	public ShoppingItemPrice findByItemId_First(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException;

	/**
	* Returns the first shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	*/
	public ShoppingItemPrice fetchByItemId_First(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator);

	/**
	* Returns the last shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item price
	* @throws NoSuchItemPriceException if a matching shopping item price could not be found
	*/
	public ShoppingItemPrice findByItemId_Last(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException;

	/**
	* Returns the last shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	*/
	public ShoppingItemPrice fetchByItemId_Last(long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator);

	/**
	* Returns the shopping item prices before and after the current shopping item price in the ordered set where itemId = &#63;.
	*
	* @param itemPriceId the primary key of the current shopping item price
	* @param itemId the item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item price
	* @throws NoSuchItemPriceException if a shopping item price with the primary key could not be found
	*/
	public ShoppingItemPrice[] findByItemId_PrevAndNext(long itemPriceId,
		long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException;

	/**
	* Removes all the shopping item prices where itemId = &#63; from the database.
	*
	* @param itemId the item ID
	*/
	public void removeByItemId(long itemId);

	/**
	* Returns the number of shopping item prices where itemId = &#63;.
	*
	* @param itemId the item ID
	* @return the number of matching shopping item prices
	*/
	public int countByItemId(long itemId);

	/**
	* Caches the shopping item price in the entity cache if it is enabled.
	*
	* @param shoppingItemPrice the shopping item price
	*/
	public void cacheResult(ShoppingItemPrice shoppingItemPrice);

	/**
	* Caches the shopping item prices in the entity cache if it is enabled.
	*
	* @param shoppingItemPrices the shopping item prices
	*/
	public void cacheResult(
		java.util.List<ShoppingItemPrice> shoppingItemPrices);

	/**
	* Creates a new shopping item price with the primary key. Does not add the shopping item price to the database.
	*
	* @param itemPriceId the primary key for the new shopping item price
	* @return the new shopping item price
	*/
	public ShoppingItemPrice create(long itemPriceId);

	/**
	* Removes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price that was removed
	* @throws NoSuchItemPriceException if a shopping item price with the primary key could not be found
	*/
	public ShoppingItemPrice remove(long itemPriceId)
		throws NoSuchItemPriceException;

	public ShoppingItemPrice updateImpl(ShoppingItemPrice shoppingItemPrice);

	/**
	* Returns the shopping item price with the primary key or throws a {@link NoSuchItemPriceException} if it could not be found.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price
	* @throws NoSuchItemPriceException if a shopping item price with the primary key could not be found
	*/
	public ShoppingItemPrice findByPrimaryKey(long itemPriceId)
		throws NoSuchItemPriceException;

	/**
	* Returns the shopping item price with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price, or <code>null</code> if a shopping item price with the primary key could not be found
	*/
	public ShoppingItemPrice fetchByPrimaryKey(long itemPriceId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingItemPrice> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping item prices.
	*
	* @return the shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findAll();

	/**
	* Returns a range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @return the range of shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator);

	/**
	* Returns an ordered range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping item prices
	*/
	public java.util.List<ShoppingItemPrice> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItemPrice> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping item prices from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping item prices.
	*
	* @return the number of shopping item prices
	*/
	public int countAll();
}