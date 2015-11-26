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

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.shopping.model.ShoppingOrder;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the shopping order service. This utility wraps {@link com.liferay.shopping.service.persistence.impl.ShoppingOrderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderPersistence
 * @see com.liferay.shopping.service.persistence.impl.ShoppingOrderPersistenceImpl
 * @generated
 */
@ProviderType
public class ShoppingOrderUtil {
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
	public static void clearCache(ShoppingOrder shoppingOrder) {
		getPersistence().clearCache(shoppingOrder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingOrder update(ShoppingOrder shoppingOrder) {
		return getPersistence().update(shoppingOrder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingOrder update(ShoppingOrder shoppingOrder,
		ServiceContext serviceContext) {
		return getPersistence().update(shoppingOrder, serviceContext);
	}

	/**
	* Returns all the shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders
	*/
	public static List<ShoppingOrder> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static List<ShoppingOrder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByGroupId_First(long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByGroupId_First(long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByGroupId_Last(long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByGroupId_Last(long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder[] findByGroupId_PrevAndNext(long orderId,
		long groupId, OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(orderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping orders that the user has permission to view
	*/
	public static List<ShoppingOrder> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

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
	public static List<ShoppingOrder> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

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
	public static List<ShoppingOrder> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder[] filterFindByGroupId_PrevAndNext(
		long orderId, long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(orderId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the shopping orders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of shopping orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping orders that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the shopping order where number = &#63; or throws a {@link com.liferay.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param number the number
	* @return the matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByNumber(java.lang.String number)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().findByNumber(number);
	}

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param number the number
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByNumber(java.lang.String number) {
		return getPersistence().fetchByNumber(number);
	}

	/**
	* Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param number the number
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByNumber(java.lang.String number,
		boolean retrieveFromCache) {
		return getPersistence().fetchByNumber(number, retrieveFromCache);
	}

	/**
	* Removes the shopping order where number = &#63; from the database.
	*
	* @param number the number
	* @return the shopping order that was removed
	*/
	public static ShoppingOrder removeByNumber(java.lang.String number)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().removeByNumber(number);
	}

	/**
	* Returns the number of shopping orders where number = &#63;.
	*
	* @param number the number
	* @return the number of matching shopping orders
	*/
	public static int countByNumber(java.lang.String number) {
		return getPersistence().countByNumber(number);
	}

	/**
	* Returns the shopping order where ppTxnId = &#63; or throws a {@link com.liferay.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByPPTxnId(java.lang.String ppTxnId)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().findByPPTxnId(ppTxnId);
	}

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByPPTxnId(java.lang.String ppTxnId) {
		return getPersistence().fetchByPPTxnId(ppTxnId);
	}

	/**
	* Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ppTxnId the pp txn ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByPPTxnId(java.lang.String ppTxnId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByPPTxnId(ppTxnId, retrieveFromCache);
	}

	/**
	* Removes the shopping order where ppTxnId = &#63; from the database.
	*
	* @param ppTxnId the pp txn ID
	* @return the shopping order that was removed
	*/
	public static ShoppingOrder removeByPPTxnId(java.lang.String ppTxnId)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().removeByPPTxnId(ppTxnId);
	}

	/**
	* Returns the number of shopping orders where ppTxnId = &#63;.
	*
	* @param ppTxnId the pp txn ID
	* @return the number of matching shopping orders
	*/
	public static int countByPPTxnId(java.lang.String ppTxnId) {
		return getPersistence().countByPPTxnId(ppTxnId);
	}

	/**
	* Returns all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders
	*/
	public static List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus) {
		return getPersistence().findByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

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
	public static List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus, int start, int end) {
		return getPersistence()
				   .findByG_U_PPPS(groupId, userId, ppPaymentStatus, start, end);
	}

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
	public static List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .findByG_U_PPPS(groupId, userId, ppPaymentStatus, start,
			end, orderByComparator);
	}

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
	public static List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_PPPS(groupId, userId, ppPaymentStatus, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByG_U_PPPS_First(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_U_PPPS_First(groupId, userId, ppPaymentStatus,
			orderByComparator);
	}

	/**
	* Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByG_U_PPPS_First(long groupId,
		long userId, java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_PPPS_First(groupId, userId, ppPaymentStatus,
			orderByComparator);
	}

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a matching shopping order could not be found
	*/
	public static ShoppingOrder findByG_U_PPPS_Last(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_U_PPPS_Last(groupId, userId, ppPaymentStatus,
			orderByComparator);
	}

	/**
	* Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	*/
	public static ShoppingOrder fetchByG_U_PPPS_Last(long groupId, long userId,
		java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_PPPS_Last(groupId, userId, ppPaymentStatus,
			orderByComparator);
	}

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder[] findByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_U_PPPS_PrevAndNext(orderId, groupId, userId,
			ppPaymentStatus, orderByComparator);
	}

	/**
	* Returns all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the matching shopping orders that the user has permission to view
	*/
	public static List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus) {
		return getPersistence()
				   .filterFindByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

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
	public static List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end) {
		return getPersistence()
				   .filterFindByG_U_PPPS(groupId, userId, ppPaymentStatus,
			start, end);
	}

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
	public static List<ShoppingOrder> filterFindByG_U_PPPS(long groupId,
		long userId, java.lang.String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence()
				   .filterFindByG_U_PPPS(groupId, userId, ppPaymentStatus,
			start, end, orderByComparator);
	}

	/**
	* Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param orderId the primary key of the current shopping order
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder[] filterFindByG_U_PPPS_PrevAndNext(
		long orderId, long groupId, long userId,
		java.lang.String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence()
				   .filterFindByG_U_PPPS_PrevAndNext(orderId, groupId, userId,
			ppPaymentStatus, orderByComparator);
	}

	/**
	* Removes all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	*/
	public static void removeByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus) {
		getPersistence().removeByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	/**
	* Returns the number of shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders
	*/
	public static int countByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus) {
		return getPersistence().countByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	/**
	* Returns the number of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param ppPaymentStatus the pp payment status
	* @return the number of matching shopping orders that the user has permission to view
	*/
	public static int filterCountByG_U_PPPS(long groupId, long userId,
		java.lang.String ppPaymentStatus) {
		return getPersistence()
				   .filterCountByG_U_PPPS(groupId, userId, ppPaymentStatus);
	}

	/**
	* Caches the shopping order in the entity cache if it is enabled.
	*
	* @param shoppingOrder the shopping order
	*/
	public static void cacheResult(ShoppingOrder shoppingOrder) {
		getPersistence().cacheResult(shoppingOrder);
	}

	/**
	* Caches the shopping orders in the entity cache if it is enabled.
	*
	* @param shoppingOrders the shopping orders
	*/
	public static void cacheResult(List<ShoppingOrder> shoppingOrders) {
		getPersistence().cacheResult(shoppingOrders);
	}

	/**
	* Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	*
	* @param orderId the primary key for the new shopping order
	* @return the new shopping order
	*/
	public static ShoppingOrder create(long orderId) {
		return getPersistence().create(orderId);
	}

	/**
	* Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order that was removed
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder remove(long orderId)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().remove(orderId);
	}

	public static ShoppingOrder updateImpl(ShoppingOrder shoppingOrder) {
		return getPersistence().updateImpl(shoppingOrder);
	}

	/**
	* Returns the shopping order with the primary key or throws a {@link com.liferay.shopping.NoSuchOrderException} if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order
	* @throws com.liferay.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder findByPrimaryKey(long orderId)
		throws com.liferay.shopping.exception.NoSuchOrderException {
		return getPersistence().findByPrimaryKey(orderId);
	}

	/**
	* Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	*/
	public static ShoppingOrder fetchByPrimaryKey(long orderId) {
		return getPersistence().fetchByPrimaryKey(orderId);
	}

	public static java.util.Map<java.io.Serializable, ShoppingOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shopping orders.
	*
	* @return the shopping orders
	*/
	public static List<ShoppingOrder> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ShoppingOrder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ShoppingOrder> findAll(int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ShoppingOrder> findAll(int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the shopping orders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping orders.
	*
	* @return the number of shopping orders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ShoppingOrderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ShoppingOrderPersistence persistence) {
	}

	private static ServiceTracker<ShoppingOrderPersistence, ShoppingOrderPersistence> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingOrderPersistence.class);
}