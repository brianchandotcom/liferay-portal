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

package com.liferay.commerce.wish.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce wish list item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.wish.list.service.persistence.impl.CommerceWishListItemPersistenceImpl
 * @see CommerceWishListItemUtil
 * @generated
 */
@ProviderType
public interface CommerceWishListItemPersistence extends BasePersistence<CommerceWishListItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWishListItemUtil} to access the commerce wish list item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce wish list items where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @return the matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId);

	/**
	* Returns a range of all the commerce wish list items where commerceWishListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWishListId the commerce wish list ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @return the range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWishListId the commerce wish list ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWishListId the commerce wish list ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCommerceWishListId_First(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCommerceWishListId_First(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCommerceWishListId_Last(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCommerceWishListId_Last(
		long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem[] findByCommerceWishListId_PrevAndNext(
		long commerceWishListItemId, long commerceWishListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Removes all the commerce wish list items where commerceWishListId = &#63; from the database.
	*
	* @param commerceWishListId the commerce wish list ID
	*/
	public void removeByCommerceWishListId(long commerceWishListId);

	/**
	* Returns the number of commerce wish list items where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @return the number of matching commerce wish list items
	*/
	public int countByCommerceWishListId(long commerceWishListId);

	/**
	* Returns all the commerce wish list items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId);

	/**
	* Returns a range of all the commerce wish list items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @return the range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish list items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish list items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCPDefinitionId_First(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCPDefinitionId_Last(long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem[] findByCPDefinitionId_PrevAndNext(
		long commerceWishListItemId, long CPDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Removes all the commerce wish list items where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public void removeByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns the number of commerce wish list items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching commerce wish list items
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns all the commerce wish list items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId);

	/**
	* Returns a range of all the commerce wish list items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @return the range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish list items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish list items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem findByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public CommerceWishListItem fetchByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem[] findByCPInstanceId_PrevAndNext(
		long commerceWishListItemId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException;

	/**
	* Removes all the commerce wish list items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	* Returns the number of commerce wish list items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce wish list items
	*/
	public int countByCPInstanceId(long CPInstanceId);

	/**
	* Caches the commerce wish list item in the entity cache if it is enabled.
	*
	* @param commerceWishListItem the commerce wish list item
	*/
	public void cacheResult(CommerceWishListItem commerceWishListItem);

	/**
	* Caches the commerce wish list items in the entity cache if it is enabled.
	*
	* @param commerceWishListItems the commerce wish list items
	*/
	public void cacheResult(
		java.util.List<CommerceWishListItem> commerceWishListItems);

	/**
	* Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	*
	* @param commerceWishListItemId the primary key for the new commerce wish list item
	* @return the new commerce wish list item
	*/
	public CommerceWishListItem create(long commerceWishListItemId);

	/**
	* Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item that was removed
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem remove(long commerceWishListItemId)
		throws NoSuchWishListItemException;

	public CommerceWishListItem updateImpl(
		CommerceWishListItem commerceWishListItem);

	/**
	* Returns the commerce wish list item with the primary key or throws a {@link NoSuchWishListItemException} if it could not be found.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem findByPrimaryKey(long commerceWishListItemId)
		throws NoSuchWishListItemException;

	/**
	* Returns the commerce wish list item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	*/
	public CommerceWishListItem fetchByPrimaryKey(long commerceWishListItemId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceWishListItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce wish list items.
	*
	* @return the commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findAll();

	/**
	* Returns a range of all the commerce wish list items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @return the range of commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce wish list items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish list items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce wish list items
	*/
	public java.util.List<CommerceWishListItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce wish list items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce wish list items.
	*
	* @return the number of commerce wish list items
	*/
	public int countAll();
}