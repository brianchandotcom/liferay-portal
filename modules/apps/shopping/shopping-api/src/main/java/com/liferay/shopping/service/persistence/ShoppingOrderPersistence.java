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

import com.liferay.shopping.exception.NoSuchOrderException;
import com.liferay.shopping.model.ShoppingOrder;

/**
 * The persistence interface for the shopping order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingOrderPersistenceImpl
 * @see ShoppingOrderUtil
 * @generated
 */
@ProviderType
public interface ShoppingOrderPersistence extends BasePersistence<ShoppingOrder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingOrderUtil} to access the shopping order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByGroupId(long groupId);

	/**
	* Returns a range of all the shopping orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder[] findByGroupId_PrevAndNext(long orderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the shopping orders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder[] filterFindByGroupId_PrevAndNext(long orderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Removes all the shopping orders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the shopping order where number = &#63; or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param number the number
	* @return the matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByNumber(java.lang.String number)
		throws NoSuchOrderException;

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param number the number
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByNumber(java.lang.String number);

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param number the number
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByNumber(java.lang.String number,
		boolean retrieveFromCache);

	/**
	* Removes the shopping order where number = &#63; from the database.
	*
	* @param number the number
	* @return the shopping order that was removed
	*/
	public ShoppingOrder removeByNumber(java.lang.String number)
		throws NoSuchOrderException;

	/**
	* Returns the number of shopping orders where number = &#63;.
	*
	* @param number the number
	* @return the number of matching shopping orders
	*/
	public int countByNumber(java.lang.String number);

	/**
	* Returns the shopping order where ppTxnId = &#63; or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByPPTxnId(java.lang.String ppTxnId)
		throws NoSuchOrderException;

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByPPTxnId(java.lang.String ppTxnId);

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByPPTxnId(java.lang.String ppTxnId,
		boolean retrieveFromCache);

	/**
	* Removes the shopping order where ppTxnId = &#63; from the database.
	*
	* @param ppTxnId the pp txn ID
	* @return the shopping order that was removed
	*/
	public ShoppingOrder removeByPPTxnId(java.lang.String ppTxnId)
		throws NoSuchOrderException;

	/**
	* Returns the number of shopping orders where ppTxnId = &#63;.
	*
	* @param ppTxnId the pp txn ID
	* @return the number of matching shopping orders
	*/
	public int countByPPTxnId(java.lang.String ppTxnId);

	/**
	* Returns all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus);

	/**
	* Returns a range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end);

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping orders
	*/
	public java.util.List<ShoppingOrder> findByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByG_U_PPPS_First(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByG_U_PPPS_First(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws NoSuchOrderException if a matching shopping order could not be found
	*/
	public ShoppingOrder findByG_U_PPPS_Last(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public ShoppingOrder fetchByG_U_PPPS_Last(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus);

	/**
	* Returns a range of all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end);

	/**
	* Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping orders that the user has permission to view
	*/
	public java.util.List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder[] filterFindByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, java.lang.String ppPaymentStatus,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException;

	/**
	* Removes all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	*/
	public void removeByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus);

	/**
	* Returns the number of shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders
	*/
	public int countByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus);

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders that the user has permission to view
	*/
	public int filterCountByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus);

	/**
	* Caches the shopping order in the entity cache if it is enabled.
	*
	* @param shoppingOrder the shopping order
	*/
	public void cacheResult(ShoppingOrder shoppingOrder);

	/**
	* Caches the shopping orders in the entity cache if it is enabled.
	*
	* @param shoppingOrders the shopping orders
	*/
	public void cacheResult(java.util.List<ShoppingOrder> shoppingOrders);

	/**
	* Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	*
	* @param orderId the primary key for the new shopping order
	* @return the new shopping order
	*/
	public ShoppingOrder create(long orderId);

	/**
	* Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order that was removed
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder remove(long orderId) throws NoSuchOrderException;

	public ShoppingOrder updateImpl(ShoppingOrder shoppingOrder);

	/**
	* Returns the shopping order with the primary key or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order
	* @throws NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder findByPrimaryKey(long orderId)
		throws NoSuchOrderException;

	/**
	* Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	*/
	public ShoppingOrder fetchByPrimaryKey(long orderId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping orders.
	*
	* @return the shopping orders
	*/
	public java.util.List<ShoppingOrder> findAll();

	/**
	* Returns a range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of shopping orders
	*/
	public java.util.List<ShoppingOrder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping orders
	*/
	public java.util.List<ShoppingOrder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator);

	/**
	* Returns an ordered range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping orders
	*/
	public java.util.List<ShoppingOrder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping orders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping orders.
	*
	* @return the number of shopping orders
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}