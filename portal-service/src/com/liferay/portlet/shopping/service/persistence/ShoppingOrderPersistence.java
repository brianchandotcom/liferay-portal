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

import com.liferay.portlet.shopping.model.ShoppingOrder;

/**
 * The persistence interface for the shopping order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderPersistenceImpl
 * @see ShoppingOrderUtil
 * @generated
 */
public interface ShoppingOrderPersistence extends BasePersistence<ShoppingOrder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingOrderUtil} to access the shopping order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the shopping order in the entity cache if it is enabled.
	*
	* @param shoppingOrder the shopping order
	*/
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder);

	/**
	* Caches the shopping orders in the entity cache if it is enabled.
	*
	* @param shoppingOrders the shopping orders
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> shoppingOrders);

	/**
	* Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	*
	* @param orderId the primary key for the new shopping order
	* @return the new shopping order
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder create(long orderId);

	/**
	* Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order that was removed
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder remove(long orderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	public com.liferay.portlet.shopping.model.ShoppingOrder updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping order with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByPrimaryKey(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPrimaryKey(
		long orderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder[] findByGroupId_PrevAndNext(
		long orderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping orders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder[] filterFindByGroupId_PrevAndNext(
		long orderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping order where number = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param number the number
	* @return the matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByNumber(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param number the number
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByNumber(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param number the number
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByNumber(
		java.lang.String number, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping order where ppTxnId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByPPTxnId(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPPTxnId(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder fetchByPPTxnId(
		java.lang.String ppTxnId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_First(
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder findByG_U_PPPS_Last(
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder[] findByG_U_PPPS_PrevAndNext(
		long orderId, long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, java.lang.String ppPaymentStatus, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder[] filterFindByG_U_PPPS_PrevAndNext(
		long orderId, long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Returns all the shopping orders.
	*
	* @return the shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingOrder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shopping orders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the shopping order where number = &#63; from the database.
	*
	* @param number the number
	* @return the shopping order that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder removeByNumber(
		java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Removes the shopping order where ppTxnId = &#63; from the database.
	*
	* @param ppTxnId the pp txn ID
	* @return the shopping order that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingOrder removeByPPTxnId(
		java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchOrderException;

	/**
	* Removes all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shopping orders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders where number = &#63;.
	*
	* @param number the number
	* @return the number of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public int countByNumber(java.lang.String number)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders where ppTxnId = &#63;.
	*
	* @param ppTxnId the pp txn ID
	* @return the number of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public int countByPPTxnId(java.lang.String ppTxnId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping orders.
	*
	* @return the number of shopping orders
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}