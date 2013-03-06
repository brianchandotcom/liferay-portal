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

import com.liferay.portlet.shopping.model.ShoppingItem;

import java.util.List;

/**
 * The persistence utility for the shopping item service. This utility wraps {@link ShoppingItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPersistence
 * @see ShoppingItemPersistenceImpl
 * @generated
 */
public class ShoppingItemUtil {
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
	public static void clearCache(ShoppingItem shoppingItem) {
		getPersistence().clearCache(shoppingItem);
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
	public static List<ShoppingItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingItem update(ShoppingItem shoppingItem)
		throws SystemException {
		return getPersistence().update(shoppingItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingItem update(ShoppingItem shoppingItem,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(shoppingItem, serviceContext);
	}

	/**
	* Returns the shopping item where smallImageId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchItemException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	* Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	/**
	* Returns the shopping item where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	/**
	* Removes the shopping item where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the shopping item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem removeBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	* Returns the number of shopping items where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	* Returns the shopping item where mediumImageId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchItemException} if it could not be found.
	*
	* @param mediumImageId the medium image ID
	* @return the matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByMediumImageId(mediumImageId);
	}

	/**
	* Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mediumImageId the medium image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByMediumImageId(mediumImageId);
	}

	/**
	* Returns the shopping item where mediumImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mediumImageId the medium image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByMediumImageId(
		long mediumImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByMediumImageId(mediumImageId, retrieveFromCache);
	}

	/**
	* Removes the shopping item where mediumImageId = &#63; from the database.
	*
	* @param mediumImageId the medium image ID
	* @return the shopping item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem removeByMediumImageId(
		long mediumImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().removeByMediumImageId(mediumImageId);
	}

	/**
	* Returns the number of shopping items where mediumImageId = &#63;.
	*
	* @param mediumImageId the medium image ID
	* @return the number of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByMediumImageId(long mediumImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByMediumImageId(mediumImageId);
	}

	/**
	* Returns the shopping item where largeImageId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchItemException} if it could not be found.
	*
	* @param largeImageId the large image ID
	* @return the matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByLargeImageId(largeImageId);
	}

	/**
	* Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param largeImageId the large image ID
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByLargeImageId(largeImageId);
	}

	/**
	* Returns the shopping item where largeImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param largeImageId the large image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByLargeImageId(
		long largeImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByLargeImageId(largeImageId, retrieveFromCache);
	}

	/**
	* Removes the shopping item where largeImageId = &#63; from the database.
	*
	* @param largeImageId the large image ID
	* @return the shopping item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem removeByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().removeByLargeImageId(largeImageId);
	}

	/**
	* Returns the number of shopping items where largeImageId = &#63;.
	*
	* @param largeImageId the large image ID
	* @return the number of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLargeImageId(largeImageId);
	}

	/**
	* Returns all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, categoryId, start, end, orderByComparator);
	}

	/**
	* Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .findByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the first shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_First(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .findByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the last shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_Last(groupId, categoryId, orderByComparator);
	}

	/**
	* Returns the shopping items before and after the current shopping item in the ordered set where groupId = &#63; and categoryId = &#63;.
	*
	* @param itemId the primary key of the current shopping item
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a shopping item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem[] findByG_C_PrevAndNext(
		long itemId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(itemId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Returns all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching shopping items that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> filterFindByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryId);
	}

	/**
	* Returns a range of all the shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of matching shopping items that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> filterFindByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C(groupId, categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping items that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping items that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> filterFindByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C(groupId, categoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the shopping items before and after the current shopping item in the ordered set of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param itemId the primary key of the current shopping item
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a shopping item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem[] filterFindByG_C_PrevAndNext(
		long itemId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(itemId, groupId, categoryId,
			orderByComparator);
	}

	/**
	* Removes all the shopping items where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of shopping items where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	/**
	* Returns the number of shopping items that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching shopping items that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C(groupId, categoryId);
	}

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchItemException} if it could not be found.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the matching shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByC_S(companyId, sku);
	}

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_S(companyId, sku);
	}

	/**
	* Returns the shopping item where companyId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping item, or <code>null</code> if a matching shopping item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByC_S(
		long companyId, java.lang.String sku, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_S(companyId, sku, retrieveFromCache);
	}

	/**
	* Removes the shopping item where companyId = &#63; and sku = &#63; from the database.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the shopping item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem removeByC_S(
		long companyId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().removeByC_S(companyId, sku);
	}

	/**
	* Returns the number of shopping items where companyId = &#63; and sku = &#63;.
	*
	* @param companyId the company ID
	* @param sku the sku
	* @return the number of matching shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_S(long companyId, java.lang.String sku)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_S(companyId, sku);
	}

	/**
	* Caches the shopping item in the entity cache if it is enabled.
	*
	* @param shoppingItem the shopping item
	*/
	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem) {
		getPersistence().cacheResult(shoppingItem);
	}

	/**
	* Caches the shopping items in the entity cache if it is enabled.
	*
	* @param shoppingItems the shopping items
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> shoppingItems) {
		getPersistence().cacheResult(shoppingItems);
	}

	/**
	* Creates a new shopping item with the primary key. Does not add the shopping item to the database.
	*
	* @param itemId the primary key for the new shopping item
	* @return the new shopping item
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem create(
		long itemId) {
		return getPersistence().create(itemId);
	}

	/**
	* Removes the shopping item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item that was removed
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a shopping item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem remove(
		long itemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().remove(itemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItem shoppingItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingItem);
	}

	/**
	* Returns the shopping item with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchItemException} if it could not be found.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item
	* @throws com.liferay.portlet.shopping.NoSuchItemException if a shopping item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem findByPrimaryKey(
		long itemId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchItemException {
		return getPersistence().findByPrimaryKey(itemId);
	}

	/**
	* Returns the shopping item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemId the primary key of the shopping item
	* @return the shopping item, or <code>null</code> if a shopping item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingItem fetchByPrimaryKey(
		long itemId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(itemId);
	}

	/**
	* Returns all the shopping items.
	*
	* @return the shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @return the range of shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping items
	* @param end the upper bound of the range of shopping items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the shopping items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping items.
	*
	* @return the number of shopping items
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingItemPersistence)PortalBeanLocatorUtil.locate(ShoppingItemPersistence.class.getName());

			ReferenceRegistry.registerReference(ShoppingItemUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ShoppingItemPersistence persistence) {
	}

	private static ShoppingItemPersistence _persistence;
}