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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceShipmentItem;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce shipment item service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceShipmentItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceShipmentItemPersistenceImpl
 * @generated
 */
@ProviderType
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
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		CommerceShipmentItem commerceShipmentItem, ServiceContext serviceContext) {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem findByGroupId_First(long groupId,
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
	public static CommerceShipmentItem fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem findByGroupId_Last(long groupId,
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
	public static CommerceShipmentItem fetchByGroupId_Last(long groupId,
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
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceShipmentItemId, groupId,
			orderByComparator);
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
	* Returns all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @return the matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId) {
		return getPersistence().findByG_C(groupId, commerceShipmentId);
	}

	/**
	* Returns a range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end) {
		return getPersistence()
				   .findByG_C(groupId, commerceShipmentId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, commerceShipmentId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce shipment items
	*/
	public static List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, commerceShipmentId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem findByG_C_First(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {
		return getPersistence()
				   .findByG_C_First(groupId, commerceShipmentId,
			orderByComparator);
	}

	/**
	* Returns the first commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem fetchByG_C_First(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, commerceShipmentId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item
	* @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem findByG_C_Last(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {
		return getPersistence()
				   .findByG_C_Last(groupId, commerceShipmentId,
			orderByComparator);
	}

	/**
	* Returns the last commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	*/
	public static CommerceShipmentItem fetchByG_C_Last(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, commerceShipmentId,
			orderByComparator);
	}

	/**
	* Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param commerceShipmentItemId the primary key of the current commerce shipment item
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce shipment item
	* @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	*/
	public static CommerceShipmentItem[] findByG_C_PrevAndNext(
		long commerceShipmentItemId, long groupId, long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchShipmentItemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(commerceShipmentItemId, groupId,
			commerceShipmentId, orderByComparator);
	}

	/**
	* Removes all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	*/
	public static void removeByG_C(long groupId, long commerceShipmentId) {
		getPersistence().removeByG_C(groupId, commerceShipmentId);
	}

	/**
	* Returns the number of commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	*
	* @param groupId the group ID
	* @param commerceShipmentId the commerce shipment ID
	* @return the number of matching commerce shipment items
	*/
	public static int countByG_C(long groupId, long commerceShipmentId) {
		return getPersistence().countByG_C(groupId, commerceShipmentId);
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
	* Returns the commerce shipment item with the primary key or throws a {@link NoSuchShipmentItemException} if it could not be found.
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

	public static java.util.Map<java.io.Serializable, CommerceShipmentItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce shipment items
	*/
	public static List<CommerceShipmentItem> findAll(int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce shipment items
	*/
	public static List<CommerceShipmentItem> findAll(int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
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
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShipmentItemPersistence, CommerceShipmentItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceShipmentItemPersistence.class);
}