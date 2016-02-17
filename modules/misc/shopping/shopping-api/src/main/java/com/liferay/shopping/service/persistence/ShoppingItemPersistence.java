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

import com.liferay.shopping.exception.NoSuchItemException;
import com.liferay.shopping.model.ShoppingItem;

/**
 * The persistence interface for the shopping item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingItemPersistenceImpl
 * @see ShoppingItemUtil
 * @generated
 */
@ProviderType
public interface ShoppingItemPersistence extends BasePersistence<ShoppingItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingItemUtil} to access the shopping item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the shopping item where smallImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findBySmallImageId(long smallImageId)
		throws NoSuchItemException;

	/**
	* Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchBySmallImageId(long smallImageId);

	/**
	* Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache);

	/**
	* Removes the shopping item where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the shopping item that was removed
	*/
	public ShoppingItem removeBySmallImageId(long smallImageId)
		throws NoSuchItemException;

	/**
	* Returns the number of shopping items where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching shopping items
	*/
	public int countBySmallImageId(long smallImageId);

	/**
	* Returns the shopping item where mediumImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	*
	* @param mediumImageId the medium image ID
	* @return the matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findByMediumImageId(long mediumImageId)
		throws NoSuchItemException;

	/**
	* Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mediumImageId the medium image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByMediumImageId(long mediumImageId);

	/**
	* Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mediumImageId the medium image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByMediumImageId(long mediumImageId,
		boolean retrieveFromCache);

	/**
	* Removes the shopping item where mediumImageId = &#63; from the database.
	*
	* @param mediumImageId the medium image ID
	* @return the shopping item that was removed
	*/
	public ShoppingItem removeByMediumImageId(long mediumImageId)
		throws NoSuchItemException;

	/**
	* Returns the number of shopping items where mediumImageId = &#63;.
	*
	* @param mediumImageId the medium image ID
	* @return the number of matching shopping items
	*/
	public int countByMediumImageId(long mediumImageId);

	/**
	* Returns the shopping item where largeImageId = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	*
	* @param largeImageId the large image ID
	* @return the matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findByLargeImageId(long largeImageId)
		throws NoSuchItemException;

	/**
	* Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param largeImageId the large image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByLargeImageId(long largeImageId);

	/**
	* Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param largeImageId the large image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByLargeImageId(long largeImageId,
		boolean retrieveFromCache);

	/**
	* Removes the shopping item where largeImageId = &#63; from the database.
	*
	* @param largeImageId the large image ID
	* @return the shopping item that was removed
	*/
	public ShoppingItem removeByLargeImageId(long largeImageId)
		throws NoSuchItemException;

	/**
	* Returns the number of shopping items where largeImageId = &#63;.
	*
	* @param largeImageId the large image ID
	* @return the number of matching shopping items
	*/
	public int countByLargeImageId(long largeImageId);

	/**
	* Returns all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching shopping items
	*/
	public java.util.List<ShoppingItem> findByG_C(long groupId, long categoryId);

	/**
	* Returns a range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of matching shopping items
	*/
	public java.util.List<ShoppingItem> findByG_C(long groupId,
		long categoryId, int start, int end);

	/**
	* Returns an ordered range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping items
	*/
	public java.util.List<ShoppingItem> findByG_C(long groupId,
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator);

	/**
	* Returns an ordered range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping items
	*/
	public java.util.List<ShoppingItem> findByG_C(long groupId,
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findByG_C_First(long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException;

	/**
	* Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByG_C_First(long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator);

	/**
	* Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findByG_C_Last(long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException;

	/**
	* Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByG_C_Last(long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator);

	/**
	* Returns the shopping items before and after the current shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param itemId the primary key of the current shopping item
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item
	* @throws NoSuchItemException if a shopping item with the primary key could not be found
	*/
	public ShoppingItem[] findByG_C_PrevAndNext(long itemId, long groupId,
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException;

	/**
	* Returns all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching shopping items that the user has permission to view
	*/
	public java.util.List<ShoppingItem> filterFindByG_C(long groupId,
		long categoryId);

	/**
	* Returns a range of all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of matching shopping items that the user has permission to view
	*/
	public java.util.List<ShoppingItem> filterFindByG_C(long groupId,
		long categoryId, int start, int end);

	/**
	* Returns an ordered range of all the shopping items that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping items that the user has permission to view
	*/
	public java.util.List<ShoppingItem> filterFindByG_C(long groupId,
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator);

	/**
	* Returns the shopping items before and after the current shopping item in the ordered set of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param itemId the primary key of the current shopping item
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item
	* @throws NoSuchItemException if a shopping item with the primary key could not be found
	*/
	public ShoppingItem[] filterFindByG_C_PrevAndNext(long itemId,
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator)
		throws NoSuchItemException;

	/**
	* Removes all the shopping items where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	*/
	public void removeByG_C(long groupId, long categoryId);

	/**
	* Returns the number of shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching shopping items
	*/
	public int countByG_C(long groupId, long categoryId);

	/**
	* Returns the number of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching shopping items that the user has permission to view
	*/
	public int filterCountByG_C(long groupId, long categoryId);

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or throws a {@link NoSuchItemException} if it could not be found.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the matching shopping item
	* @throws NoSuchItemException if a matching shopping item could not be found
	*/
	public ShoppingItem findByC_S(long companyId, java.lang.String sku)
		throws NoSuchItemException;

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByC_S(long companyId, java.lang.String sku);

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	*/
	public ShoppingItem fetchByC_S(long companyId, java.lang.String sku,
		boolean retrieveFromCache);

	/**
	* Removes the shopping item where companyId = &#63; and sku = &#63; from the database.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the shopping item that was removed
	*/
	public ShoppingItem removeByC_S(long companyId, java.lang.String sku)
		throws NoSuchItemException;

	/**
	* Returns the number of shopping items where companyId = &#63; and sku = &#63;.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the number of matching shopping items
	*/
	public int countByC_S(long companyId, java.lang.String sku);

	/**
	* Caches the shopping item in the entity cache if it is enabled.
	*
	* @param shoppingItem the shopping item
	*/
	public void cacheResult(ShoppingItem shoppingItem);

	/**
	* Caches the shopping items in the entity cache if it is enabled.
	*
	* @param shoppingItems the shopping items
	*/
	public void cacheResult(java.util.List<ShoppingItem> shoppingItems);

	/**
	* Creates a new shopping item with the primary key. Does not add the shopping item to the database.
	*
	* @param itemId the primary key for the new shopping item
	* @return the new shopping item
	*/
	public ShoppingItem create(long itemId);

	/**
	* Removes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item that was removed
	* @throws NoSuchItemException if a shopping item with the primary key could not be found
	*/
	public ShoppingItem remove(long itemId) throws NoSuchItemException;

	public ShoppingItem updateImpl(ShoppingItem shoppingItem);

	/**
	* Returns the shopping item with the primary key or throws a {@link NoSuchItemException} if it could not be found.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item
	* @throws NoSuchItemException if a shopping item with the primary key could not be found
	*/
	public ShoppingItem findByPrimaryKey(long itemId)
		throws NoSuchItemException;

	/**
	* Returns the shopping item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item, or <code>null</code> if a shopping item with the primary key could not be found
	*/
	public ShoppingItem fetchByPrimaryKey(long itemId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping items.
	*
	* @return the shopping items
	*/
	public java.util.List<ShoppingItem> findAll();

	/**
	* Returns a range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of shopping items
	*/
	public java.util.List<ShoppingItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping items
	*/
	public java.util.List<ShoppingItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator);

	/**
	* Returns an ordered range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping items
	*/
	public java.util.List<ShoppingItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping items.
	*
	* @return the number of shopping items
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}