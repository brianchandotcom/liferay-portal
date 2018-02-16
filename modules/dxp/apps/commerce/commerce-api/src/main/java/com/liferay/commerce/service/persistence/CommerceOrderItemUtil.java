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

import com.liferay.commerce.model.CommerceOrderItem;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce order item service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceOrderItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderItemPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderItemUtil {
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
	public static void clearCache(CommerceOrderItem commerceOrderItem) {
		getPersistence().clearCache(commerceOrderItem);
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
	public static List<CommerceOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderItem update(CommerceOrderItem commerceOrderItem) {
		return getPersistence().update(commerceOrderItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderItem update(
		CommerceOrderItem commerceOrderItem, ServiceContext serviceContext) {
		return getPersistence().update(commerceOrderItem, serviceContext);
	}

	/**
	* Returns all the commerce order items where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId) {
		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns a range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderItemId, long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCommerceOrderId_PrevAndNext(commerceOrderItemId,
			commerceOrderId, orderByComparator);
	}

	/**
	* Removes all the commerce order items where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public static void removeByCommerceOrderId(long commerceOrderId) {
		getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the number of commerce order items where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order items
	*/
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns all the commerce order items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCPInstanceId(long CPInstanceId) {
		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns a range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end) {
		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem[] findByCPInstanceId_PrevAndNext(
		long commerceOrderItemId, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByCPInstanceId_PrevAndNext(commerceOrderItemId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce order items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the number of commerce order items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce order items
	*/
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce order items
	*/
	public static List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId) {
		return getPersistence().findByC_I(commerceOrderId, CPInstanceId);
	}

	/**
	* Returns a range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end) {
		return getPersistence()
				   .findByC_I(commerceOrderId, CPInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .findByC_I(commerceOrderId, CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order items
	*/
	public static List<CommerceOrderItem> findByC_I(long commerceOrderId,
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_I(commerceOrderId, CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByC_I_First(long commerceOrderId,
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByC_I_First(commerceOrderId, CPInstanceId,
			orderByComparator);
	}

	/**
	* Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByC_I_First(long commerceOrderId,
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByC_I_First(commerceOrderId, CPInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item
	* @throws NoSuchOrderItemException if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem findByC_I_Last(long commerceOrderId,
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByC_I_Last(commerceOrderId, CPInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	*/
	public static CommerceOrderItem fetchByC_I_Last(long commerceOrderId,
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence()
				   .fetchByC_I_Last(commerceOrderId, CPInstanceId,
			orderByComparator);
	}

	/**
	* Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderItemId the primary key of the current commerce order item
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem[] findByC_I_PrevAndNext(
		long commerceOrderItemId, long commerceOrderId, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence()
				   .findByC_I_PrevAndNext(commerceOrderItemId, commerceOrderId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByC_I(long commerceOrderId, long CPInstanceId) {
		getPersistence().removeByC_I(commerceOrderId, CPInstanceId);
	}

	/**
	* Returns the number of commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce order items
	*/
	public static int countByC_I(long commerceOrderId, long CPInstanceId) {
		return getPersistence().countByC_I(commerceOrderId, CPInstanceId);
	}

	/**
	* Caches the commerce order item in the entity cache if it is enabled.
	*
	* @param commerceOrderItem the commerce order item
	*/
	public static void cacheResult(CommerceOrderItem commerceOrderItem) {
		getPersistence().cacheResult(commerceOrderItem);
	}

	/**
	* Caches the commerce order items in the entity cache if it is enabled.
	*
	* @param commerceOrderItems the commerce order items
	*/
	public static void cacheResult(List<CommerceOrderItem> commerceOrderItems) {
		getPersistence().cacheResult(commerceOrderItems);
	}

	/**
	* Creates a new commerce order item with the primary key. Does not add the commerce order item to the database.
	*
	* @param commerceOrderItemId the primary key for the new commerce order item
	* @return the new commerce order item
	*/
	public static CommerceOrderItem create(long commerceOrderItemId) {
		return getPersistence().create(commerceOrderItemId);
	}

	/**
	* Removes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item that was removed
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem remove(long commerceOrderItemId)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence().remove(commerceOrderItemId);
	}

	public static CommerceOrderItem updateImpl(
		CommerceOrderItem commerceOrderItem) {
		return getPersistence().updateImpl(commerceOrderItem);
	}

	/**
	* Returns the commerce order item with the primary key or throws a {@link NoSuchOrderItemException} if it could not be found.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item
	* @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem findByPrimaryKey(long commerceOrderItemId)
		throws com.liferay.commerce.exception.NoSuchOrderItemException {
		return getPersistence().findByPrimaryKey(commerceOrderItemId);
	}

	/**
	* Returns the commerce order item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item, or <code>null</code> if a commerce order item with the primary key could not be found
	*/
	public static CommerceOrderItem fetchByPrimaryKey(long commerceOrderItemId) {
		return getPersistence().fetchByPrimaryKey(commerceOrderItemId);
	}

	public static java.util.Map<java.io.Serializable, CommerceOrderItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce order items.
	*
	* @return the commerce order items
	*/
	public static List<CommerceOrderItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of commerce order items
	*/
	public static List<CommerceOrderItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce order items
	*/
	public static List<CommerceOrderItem> findAll(int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce order items
	*/
	public static List<CommerceOrderItem> findAll(int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce order items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce order items.
	*
	* @return the number of commerce order items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderItemPersistence, CommerceOrderItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderItemPersistence.class);
}