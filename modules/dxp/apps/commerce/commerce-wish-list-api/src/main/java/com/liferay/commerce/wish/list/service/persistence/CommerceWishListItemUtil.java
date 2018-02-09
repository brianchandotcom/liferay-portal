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

import com.liferay.commerce.wish.list.model.CommerceWishListItem;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce wish list item service. This utility wraps {@link com.liferay.commerce.wish.list.service.persistence.impl.CommerceWishListItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemPersistence
 * @see com.liferay.commerce.wish.list.service.persistence.impl.CommerceWishListItemPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceWishListItemUtil {
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
	public static void clearCache(CommerceWishListItem commerceWishListItem) {
		getPersistence().clearCache(commerceWishListItem);
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
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceWishListItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceWishListItem update(
		CommerceWishListItem commerceWishListItem) {
		return getPersistence().update(commerceWishListItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceWishListItem update(
		CommerceWishListItem commerceWishListItem, ServiceContext serviceContext) {
		return getPersistence().update(commerceWishListItem, serviceContext);
	}

	/**
	* Returns all the commerce wish list items where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @return the matching commerce wish list items
	*/
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId) {
		return getPersistence().findByCommerceWishListId(commerceWishListId);
	}

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
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end) {
		return getPersistence()
				   .findByCommerceWishListId(commerceWishListId, start, end);
	}

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
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .findByCommerceWishListId(commerceWishListId, start, end,
			orderByComparator);
	}

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
	public static List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceWishListId(commerceWishListId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCommerceWishListId_First(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCommerceWishListId_First(commerceWishListId,
			orderByComparator);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCommerceWishListId_First(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceWishListId_First(commerceWishListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCommerceWishListId_Last(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCommerceWishListId_Last(commerceWishListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCommerceWishListId_Last(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceWishListId_Last(commerceWishListId,
			orderByComparator);
	}

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param commerceWishListId the commerce wish list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem[] findByCommerceWishListId_PrevAndNext(
		long commerceWishListItemId, long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCommerceWishListId_PrevAndNext(commerceWishListItemId,
			commerceWishListId, orderByComparator);
	}

	/**
	* Removes all the commerce wish list items where commerceWishListId = &#63; from the database.
	*
	* @param commerceWishListId the commerce wish list ID
	*/
	public static void removeByCommerceWishListId(long commerceWishListId) {
		getPersistence().removeByCommerceWishListId(commerceWishListId);
	}

	/**
	* Returns the number of commerce wish list items where commerceWishListId = &#63;.
	*
	* @param commerceWishListId the commerce wish list ID
	* @return the number of matching commerce wish list items
	*/
	public static int countByCommerceWishListId(long commerceWishListId) {
		return getPersistence().countByCommerceWishListId(commerceWishListId);
	}

	/**
	* Returns all the commerce wish list items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce wish list items
	*/
	public static List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

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
	public static List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

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
	public static List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

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
	public static List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem[] findByCPDefinitionId_PrevAndNext(
		long commerceWishListItemId, long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(commerceWishListItemId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the commerce wish list items where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of commerce wish list items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching commerce wish list items
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns all the commerce wish list items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce wish list items
	*/
	public static List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId) {
		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

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
	public static List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end) {
		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

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
	public static List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator);
	}

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
	public static List<CommerceWishListItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item
	* @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem findByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	*/
	public static CommerceWishListItem fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceWishListItemId the primary key of the current commerce wish list item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem[] findByCPInstanceId_PrevAndNext(
		long commerceWishListItemId, long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence()
				   .findByCPInstanceId_PrevAndNext(commerceWishListItemId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce wish list items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the number of commerce wish list items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce wish list items
	*/
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	* Caches the commerce wish list item in the entity cache if it is enabled.
	*
	* @param commerceWishListItem the commerce wish list item
	*/
	public static void cacheResult(CommerceWishListItem commerceWishListItem) {
		getPersistence().cacheResult(commerceWishListItem);
	}

	/**
	* Caches the commerce wish list items in the entity cache if it is enabled.
	*
	* @param commerceWishListItems the commerce wish list items
	*/
	public static void cacheResult(
		List<CommerceWishListItem> commerceWishListItems) {
		getPersistence().cacheResult(commerceWishListItems);
	}

	/**
	* Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	*
	* @param commerceWishListItemId the primary key for the new commerce wish list item
	* @return the new commerce wish list item
	*/
	public static CommerceWishListItem create(long commerceWishListItemId) {
		return getPersistence().create(commerceWishListItemId);
	}

	/**
	* Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item that was removed
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem remove(long commerceWishListItemId)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence().remove(commerceWishListItemId);
	}

	public static CommerceWishListItem updateImpl(
		CommerceWishListItem commerceWishListItem) {
		return getPersistence().updateImpl(commerceWishListItem);
	}

	/**
	* Returns the commerce wish list item with the primary key or throws a {@link NoSuchWishListItemException} if it could not be found.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item
	* @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem findByPrimaryKey(
		long commerceWishListItemId)
		throws com.liferay.commerce.wish.list.exception.NoSuchWishListItemException {
		return getPersistence().findByPrimaryKey(commerceWishListItemId);
	}

	/**
	* Returns the commerce wish list item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	*/
	public static CommerceWishListItem fetchByPrimaryKey(
		long commerceWishListItemId) {
		return getPersistence().fetchByPrimaryKey(commerceWishListItemId);
	}

	public static java.util.Map<java.io.Serializable, CommerceWishListItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce wish list items.
	*
	* @return the commerce wish list items
	*/
	public static List<CommerceWishListItem> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CommerceWishListItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CommerceWishListItem> findAll(int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CommerceWishListItem> findAll(int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce wish list items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce wish list items.
	*
	* @return the number of commerce wish list items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceWishListItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWishListItemPersistence, CommerceWishListItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceWishListItemPersistence.class);
}