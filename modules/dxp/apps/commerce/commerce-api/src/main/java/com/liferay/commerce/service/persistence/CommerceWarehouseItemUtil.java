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

import com.liferay.commerce.model.CommerceWarehouseItem;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce warehouse item service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceWarehouseItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceWarehouseItemPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemUtil {
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
	public static void clearCache(CommerceWarehouseItem commerceWarehouseItem) {
		getPersistence().clearCache(commerceWarehouseItem);
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
	public static List<CommerceWarehouseItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceWarehouseItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceWarehouseItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceWarehouseItem update(
		CommerceWarehouseItem commerceWarehouseItem) {
		return getPersistence().update(commerceWarehouseItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceWarehouseItem update(
		CommerceWarehouseItem commerceWarehouseItem,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceWarehouseItem, serviceContext);
	}

	/**
	* Returns all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @return the matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId) {
		return getPersistence().findByCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Returns a range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end) {
		return getPersistence()
				   .findByCommerceWarehouseId(commerceWarehouseId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .findByCommerceWarehouseId(commerceWarehouseId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCommerceWarehouseId(
		long commerceWarehouseId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceWarehouseId(commerceWarehouseId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem findByCommerceWarehouseId_First(
		long commerceWarehouseId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCommerceWarehouseId_First(commerceWarehouseId,
			orderByComparator);
	}

	/**
	* Returns the first commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByCommerceWarehouseId_First(
		long commerceWarehouseId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceWarehouseId_First(commerceWarehouseId,
			orderByComparator);
	}

	/**
	* Returns the last commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem findByCommerceWarehouseId_Last(
		long commerceWarehouseId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCommerceWarehouseId_Last(commerceWarehouseId,
			orderByComparator);
	}

	/**
	* Returns the last commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByCommerceWarehouseId_Last(
		long commerceWarehouseId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceWarehouseId_Last(commerceWarehouseId,
			orderByComparator);
	}

	/**
	* Returns the commerce warehouse items before and after the current commerce warehouse item in the ordered set where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseItemId the primary key of the current commerce warehouse item
	* @param commerceWarehouseId the commerce warehouse ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public static CommerceWarehouseItem[] findByCommerceWarehouseId_PrevAndNext(
		long commerceWarehouseItemId, long commerceWarehouseId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCommerceWarehouseId_PrevAndNext(commerceWarehouseItemId,
			commerceWarehouseId, orderByComparator);
	}

	/**
	* Removes all the commerce warehouse items where commerceWarehouseId = &#63; from the database.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	*/
	public static void removeByCommerceWarehouseId(long commerceWarehouseId) {
		getPersistence().removeByCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Returns the number of commerce warehouse items where commerceWarehouseId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @return the number of matching commerce warehouse items
	*/
	public static int countByCommerceWarehouseId(long commerceWarehouseId) {
		return getPersistence().countByCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Returns all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId) {
		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns a range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end) {
		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem findByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the first commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem findByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the commerce warehouse items before and after the current commerce warehouse item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceWarehouseItemId the primary key of the current commerce warehouse item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public static CommerceWarehouseItem[] findByCPInstanceId_PrevAndNext(
		long commerceWarehouseItemId, long CPInstanceId,
		OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence()
				   .findByCPInstanceId_PrevAndNext(commerceWarehouseItemId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce warehouse items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the number of commerce warehouse items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce warehouse items
	*/
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or throws a {@link NoSuchWarehouseItemException} if it could not be found.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse item
	* @throws NoSuchWarehouseItemException if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem findByC_C(long commerceWarehouseId,
		long CPInstanceId)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence().findByC_C(commerceWarehouseId, CPInstanceId);
	}

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByC_C(long commerceWarehouseId,
		long CPInstanceId) {
		return getPersistence().fetchByC_C(commerceWarehouseId, CPInstanceId);
	}

	/**
	* Returns the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce warehouse item, or <code>null</code> if a matching commerce warehouse item could not be found
	*/
	public static CommerceWarehouseItem fetchByC_C(long commerceWarehouseId,
		long CPInstanceId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(commerceWarehouseId, CPInstanceId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce warehouse item where commerceWarehouseId = &#63; and CPInstanceId = &#63; from the database.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the commerce warehouse item that was removed
	*/
	public static CommerceWarehouseItem removeByC_C(long commerceWarehouseId,
		long CPInstanceId)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence().removeByC_C(commerceWarehouseId, CPInstanceId);
	}

	/**
	* Returns the number of commerce warehouse items where commerceWarehouseId = &#63; and CPInstanceId = &#63;.
	*
	* @param commerceWarehouseId the commerce warehouse ID
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce warehouse items
	*/
	public static int countByC_C(long commerceWarehouseId, long CPInstanceId) {
		return getPersistence().countByC_C(commerceWarehouseId, CPInstanceId);
	}

	/**
	* Caches the commerce warehouse item in the entity cache if it is enabled.
	*
	* @param commerceWarehouseItem the commerce warehouse item
	*/
	public static void cacheResult(CommerceWarehouseItem commerceWarehouseItem) {
		getPersistence().cacheResult(commerceWarehouseItem);
	}

	/**
	* Caches the commerce warehouse items in the entity cache if it is enabled.
	*
	* @param commerceWarehouseItems the commerce warehouse items
	*/
	public static void cacheResult(
		List<CommerceWarehouseItem> commerceWarehouseItems) {
		getPersistence().cacheResult(commerceWarehouseItems);
	}

	/**
	* Creates a new commerce warehouse item with the primary key. Does not add the commerce warehouse item to the database.
	*
	* @param commerceWarehouseItemId the primary key for the new commerce warehouse item
	* @return the new commerce warehouse item
	*/
	public static CommerceWarehouseItem create(long commerceWarehouseItemId) {
		return getPersistence().create(commerceWarehouseItemId);
	}

	/**
	* Removes the commerce warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item that was removed
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public static CommerceWarehouseItem remove(long commerceWarehouseItemId)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence().remove(commerceWarehouseItemId);
	}

	public static CommerceWarehouseItem updateImpl(
		CommerceWarehouseItem commerceWarehouseItem) {
		return getPersistence().updateImpl(commerceWarehouseItem);
	}

	/**
	* Returns the commerce warehouse item with the primary key or throws a {@link NoSuchWarehouseItemException} if it could not be found.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item
	* @throws NoSuchWarehouseItemException if a commerce warehouse item with the primary key could not be found
	*/
	public static CommerceWarehouseItem findByPrimaryKey(
		long commerceWarehouseItemId)
		throws com.liferay.commerce.exception.NoSuchWarehouseItemException {
		return getPersistence().findByPrimaryKey(commerceWarehouseItemId);
	}

	/**
	* Returns the commerce warehouse item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item, or <code>null</code> if a commerce warehouse item with the primary key could not be found
	*/
	public static CommerceWarehouseItem fetchByPrimaryKey(
		long commerceWarehouseItemId) {
		return getPersistence().fetchByPrimaryKey(commerceWarehouseItemId);
	}

	public static java.util.Map<java.io.Serializable, CommerceWarehouseItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce warehouse items.
	*
	* @return the commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findAll(int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce warehouse items
	*/
	public static List<CommerceWarehouseItem> findAll(int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce warehouse items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce warehouse items.
	*
	* @return the number of commerce warehouse items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceWarehouseItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseItemPersistence, CommerceWarehouseItemPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWarehouseItemPersistence.class);

		ServiceTracker<CommerceWarehouseItemPersistence, CommerceWarehouseItemPersistence> serviceTracker =
			new ServiceTracker<CommerceWarehouseItemPersistence, CommerceWarehouseItemPersistence>(bundle.getBundleContext(),
				CommerceWarehouseItemPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}