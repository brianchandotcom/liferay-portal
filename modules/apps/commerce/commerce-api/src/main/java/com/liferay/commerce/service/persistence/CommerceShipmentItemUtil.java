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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce shipment item service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceShipmentItemPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemPersistence
 * @generated
 */
public class CommerceShipmentItemUtil {

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
	public static void clearCache(CommerceShipmentItem commerceShipmentItem) {
		getPersistence().clearCache(commerceShipmentItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommerceShipmentItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceShipmentItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceShipmentItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceShipmentItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceShipmentItem update(
		CommerceShipmentItem commerceShipmentItem) {

		return getPersistence().update(commerceShipmentItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceShipmentItem update(
		CommerceShipmentItem commerceShipmentItem,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceShipmentItem, serviceContext);
	}

	/**
	 * Returns all the commerce shipment items where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem[] findByGroupId_PrevAndNext(
			long commerceShipmentItemId, long groupId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByGroupId_PrevAndNext(
			commerceShipmentItemId, groupId, orderByComparator);
	}

	/**
	 * Removes all the commerce shipment items where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce shipment items where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce shipment items
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the commerce shipment items where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @return the matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceShipmentId(
		long commerceShipmentId) {

		return getPersistence().findByCommerceShipmentId(commerceShipmentId);
	}

	/**
	 * Returns a range of all the commerce shipment items where commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceShipmentId(
		long commerceShipmentId, int start, int end) {

		return getPersistence().findByCommerceShipmentId(
			commerceShipmentId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceShipmentId(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findByCommerceShipmentId(
			commerceShipmentId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceShipmentId(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceShipmentId(
			commerceShipmentId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByCommerceShipmentId_First(
			long commerceShipmentId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceShipmentId_First(
			commerceShipmentId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByCommerceShipmentId_First(
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByCommerceShipmentId_First(
			commerceShipmentId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByCommerceShipmentId_Last(
			long commerceShipmentId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceShipmentId_Last(
			commerceShipmentId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByCommerceShipmentId_Last(
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByCommerceShipmentId_Last(
			commerceShipmentId, orderByComparator);
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem[] findByCommerceShipmentId_PrevAndNext(
			long commerceShipmentItemId, long commerceShipmentId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceShipmentId_PrevAndNext(
			commerceShipmentItemId, commerceShipmentId, orderByComparator);
	}

	/**
	 * Removes all the commerce shipment items where commerceShipmentId = &#63; from the database.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 */
	public static void removeByCommerceShipmentId(long commerceShipmentId) {
		getPersistence().removeByCommerceShipmentId(commerceShipmentId);
	}

	/**
	 * Returns the number of commerce shipment items where commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @return the number of matching commerce shipment items
	 */
	public static int countByCommerceShipmentId(long commerceShipmentId) {
		return getPersistence().countByCommerceShipmentId(commerceShipmentId);
	}

	/**
	 * Returns all the commerce shipment items where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceOrderItemId(
		long commerceOrderItemId) {

		return getPersistence().findByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns a range of all the commerce shipment items where commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceOrderItemId(
		long commerceOrderItemId, int start, int end) {

		return getPersistence().findByCommerceOrderItemId(
			commerceOrderItemId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceOrderItemId(
		long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findByCommerceOrderItemId(
			commerceOrderItemId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByCommerceOrderItemId(
		long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceOrderItemId(
			commerceOrderItemId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByCommerceOrderItemId_First(
			long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceOrderItemId_First(
			commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByCommerceOrderItemId_First(
		long commerceOrderItemId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByCommerceOrderItemId_First(
			commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByCommerceOrderItemId_Last(
			long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceOrderItemId_Last(
			commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByCommerceOrderItemId_Last(
		long commerceOrderItemId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByCommerceOrderItemId_Last(
			commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem[] findByCommerceOrderItemId_PrevAndNext(
			long commerceShipmentItemId, long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByCommerceOrderItemId_PrevAndNext(
			commerceShipmentItemId, commerceOrderItemId, orderByComparator);
	}

	/**
	 * Removes all the commerce shipment items where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 */
	public static void removeByCommerceOrderItemId(long commerceOrderItemId) {
		getPersistence().removeByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns the number of commerce shipment items where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce shipment items
	 */
	public static int countByCommerceOrderItemId(long commerceOrderItemId) {
		return getPersistence().countByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns all the commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByC_C(
		long commerceShipmentId, long commerceOrderItemId) {

		return getPersistence().findByC_C(
			commerceShipmentId, commerceOrderItemId);
	}

	/**
	 * Returns a range of all the commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByC_C(
		long commerceShipmentId, long commerceOrderItemId, int start, int end) {

		return getPersistence().findByC_C(
			commerceShipmentId, commerceOrderItemId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByC_C(
		long commerceShipmentId, long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findByC_C(
			commerceShipmentId, commerceOrderItemId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	public static List<CommerceShipmentItem> findByC_C(
		long commerceShipmentId, long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			commerceShipmentId, commerceOrderItemId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByC_C_First(
			long commerceShipmentId, long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByC_C_First(
			commerceShipmentId, commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_C_First(
		long commerceShipmentId, long commerceOrderItemId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			commerceShipmentId, commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByC_C_Last(
			long commerceShipmentId, long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByC_C_Last(
			commerceShipmentId, commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_C_Last(
		long commerceShipmentId, long commerceOrderItemId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			commerceShipmentId, commerceOrderItemId, orderByComparator);
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem[] findByC_C_PrevAndNext(
			long commerceShipmentItemId, long commerceShipmentId,
			long commerceOrderItemId,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByC_C_PrevAndNext(
			commerceShipmentItemId, commerceShipmentId, commerceOrderItemId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 */
	public static void removeByC_C(
		long commerceShipmentId, long commerceOrderItemId) {

		getPersistence().removeByC_C(commerceShipmentId, commerceOrderItemId);
	}

	/**
	 * Returns the number of commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce shipment items
	 */
	public static int countByC_C(
		long commerceShipmentId, long commerceOrderItemId) {

		return getPersistence().countByC_C(
			commerceShipmentId, commerceOrderItemId);
	}

	/**
	 * Returns the commerce shipment item where commerceShipmentId = &#63; and commerceOrderItemId = &#63; and commerceInventoryWarehouseId = &#63; or throws a <code>NoSuchShipmentItemException</code> if it could not be found.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByC_C_C(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce shipment item where commerceShipmentId = &#63; and commerceOrderItemId = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_C_C(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceInventoryWarehouseId) {

		return getPersistence().fetchByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce shipment item where commerceShipmentId = &#63; and commerceOrderItemId = &#63; and commerceInventoryWarehouseId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_C_C(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceInventoryWarehouseId, boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, useFinderCache);
	}

	/**
	 * Removes the commerce shipment item where commerceShipmentId = &#63; and commerceOrderItemId = &#63; and commerceInventoryWarehouseId = &#63; from the database.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the commerce shipment item that was removed
	 */
	public static CommerceShipmentItem removeByC_C_C(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().removeByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the number of commerce shipment items where commerceShipmentId = &#63; and commerceOrderItemId = &#63; and commerceInventoryWarehouseId = &#63;.
	 *
	 * @param commerceShipmentId the commerce shipment ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID
	 * @return the number of matching commerce shipment items
	 */
	public static int countByC_C_C(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceInventoryWarehouseId) {

		return getPersistence().countByC_C_C(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce shipment item where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchShipmentItemException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce shipment item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce shipment item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	public static CommerceShipmentItem fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce shipment item where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce shipment item that was removed
	 */
	public static CommerceShipmentItem removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce shipment items where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce shipment items
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the commerce shipment item in the entity cache if it is enabled.
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 */
	public static void cacheResult(CommerceShipmentItem commerceShipmentItem) {
		getPersistence().cacheResult(commerceShipmentItem);
	}

	/**
	 * Caches the commerce shipment items in the entity cache if it is enabled.
	 *
	 * @param commerceShipmentItems the commerce shipment items
	 */
	public static void cacheResult(
		List<CommerceShipmentItem> commerceShipmentItems) {

		getPersistence().cacheResult(commerceShipmentItems);
	}

	/**
	 * Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	 *
	 * @param commerceShipmentItemId the primary key for the new commerce shipment item
	 * @return the new commerce shipment item
	 */
	public static CommerceShipmentItem create(long commerceShipmentItemId) {
		return getPersistence().create(commerceShipmentItemId);
	}

	/**
	 * Removes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item that was removed
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem remove(long commerceShipmentItemId)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().remove(commerceShipmentItemId);
	}

	public static CommerceShipmentItem updateImpl(
		CommerceShipmentItem commerceShipmentItem) {

		return getPersistence().updateImpl(commerceShipmentItem);
	}

	/**
	 * Returns the commerce shipment item with the primary key or throws a <code>NoSuchShipmentItemException</code> if it could not be found.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem findByPrimaryKey(
			long commerceShipmentItemId)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {

		return getPersistence().findByPrimaryKey(commerceShipmentItemId);
	}

	/**
	 * Returns the commerce shipment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item, or <code>null</code> if a commerce shipment item with the primary key could not be found
	 */
	public static CommerceShipmentItem fetchByPrimaryKey(
		long commerceShipmentItemId) {

		return getPersistence().fetchByPrimaryKey(commerceShipmentItemId);
	}

	/**
	 * Returns all the commerce shipment items.
	 *
	 * @return the commerce shipment items
	 */
	public static List<CommerceShipmentItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of commerce shipment items
	 */
	public static List<CommerceShipmentItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipment items
	 */
	public static List<CommerceShipmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipment items
	 */
	public static List<CommerceShipmentItem> findAll(
		int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce shipment items from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce shipment items.
	 *
	 * @return the number of commerce shipment items
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceShipmentItemPersistence getPersistence() {
		return _persistence;
	}

	private static volatile CommerceShipmentItemPersistence _persistence;

}