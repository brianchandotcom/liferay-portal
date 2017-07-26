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

package com.liferay.commerce.cart.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.model.CommerceCartItem;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce cart item service. This utility wraps {@link com.liferay.commerce.cart.service.persistence.impl.CommerceCartItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCartItemPersistence
 * @see com.liferay.commerce.cart.service.persistence.impl.CommerceCartItemPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCartItemUtil {
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
	public static void clearCache(CommerceCartItem commerceCartItem) {
		getPersistence().clearCache(commerceCartItem);
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
	public static List<CommerceCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCartItem update(CommerceCartItem commerceCartItem) {
		return getPersistence().update(commerceCartItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCartItem update(CommerceCartItem commerceCartItem,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceCartItem, serviceContext);
	}

	/**
	* Returns all the commerce cart items where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @return the matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCommerceCartId(
		long commerceCartId) {
		return getPersistence().findByCommerceCartId(commerceCartId);
	}

	/**
	* Returns a range of all the commerce cart items where commerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCommerceCartId(
		long commerceCartId, int start, int end) {
		return getPersistence().findByCommerceCartId(commerceCartId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce cart items where commerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCommerceCartId(
		long commerceCartId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .findByCommerceCartId(commerceCartId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cart items where commerceCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCartId the commerce cart ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCommerceCartId(
		long commerceCartId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCartId(commerceCartId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart item in the ordered set where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCommerceCartId_First(
		long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCommerceCartId_First(commerceCartId, orderByComparator);
	}

	/**
	* Returns the first commerce cart item in the ordered set where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCommerceCartId_First(
		long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCartId_First(commerceCartId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCommerceCartId_Last(
		long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCommerceCartId_Last(commerceCartId, orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCommerceCartId_Last(
		long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCartId_Last(commerceCartId, orderByComparator);
	}

	/**
	* Returns the commerce cart items before and after the current commerce cart item in the ordered set where commerceCartId = &#63;.
	*
	* @param commerceCartItemId the primary key of the current commerce cart item
	* @param commerceCartId the commerce cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem[] findByCommerceCartId_PrevAndNext(
		long commerceCartItemId, long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCommerceCartId_PrevAndNext(commerceCartItemId,
			commerceCartId, orderByComparator);
	}

	/**
	* Removes all the commerce cart items where commerceCartId = &#63; from the database.
	*
	* @param commerceCartId the commerce cart ID
	*/
	public static void removeByCommerceCartId(long commerceCartId) {
		getPersistence().removeByCommerceCartId(commerceCartId);
	}

	/**
	* Returns the number of commerce cart items where commerceCartId = &#63;.
	*
	* @param commerceCartId the commerce cart ID
	* @return the number of matching commerce cart items
	*/
	public static int countByCommerceCartId(long commerceCartId) {
		return getPersistence().countByCommerceCartId(commerceCartId);
	}

	/**
	* Returns all the commerce cart items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns a range of all the commerce cart items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce cart items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cart items where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first commerce cart item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the commerce cart items before and after the current commerce cart item in the ordered set where CPDefinitionId = &#63;.
	*
	* @param commerceCartItemId the primary key of the current commerce cart item
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem[] findByCPDefinitionId_PrevAndNext(
		long commerceCartItemId, long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(commerceCartItemId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the commerce cart items where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of commerce cart items where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching commerce cart items
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns all the commerce cart items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPInstanceId(long CPInstanceId) {
		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns a range of all the commerce cart items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end) {
		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce cart items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cart items where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cart items
	*/
	public static List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCPInstanceId_First(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the first commerce cart item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCPInstanceId_First(
		long CPInstanceId, OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item
	* @throws NoSuchCartItemException if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem findByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce cart item in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	*/
	public static CommerceCartItem fetchByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the commerce cart items before and after the current commerce cart item in the ordered set where CPInstanceId = &#63;.
	*
	* @param commerceCartItemId the primary key of the current commerce cart item
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem[] findByCPInstanceId_PrevAndNext(
		long commerceCartItemId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence()
				   .findByCPInstanceId_PrevAndNext(commerceCartItemId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce cart items where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the number of commerce cart items where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce cart items
	*/
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	* Caches the commerce cart item in the entity cache if it is enabled.
	*
	* @param commerceCartItem the commerce cart item
	*/
	public static void cacheResult(CommerceCartItem commerceCartItem) {
		getPersistence().cacheResult(commerceCartItem);
	}

	/**
	* Caches the commerce cart items in the entity cache if it is enabled.
	*
	* @param commerceCartItems the commerce cart items
	*/
	public static void cacheResult(List<CommerceCartItem> commerceCartItems) {
		getPersistence().cacheResult(commerceCartItems);
	}

	/**
	* Creates a new commerce cart item with the primary key. Does not add the commerce cart item to the database.
	*
	* @param commerceCartItemId the primary key for the new commerce cart item
	* @return the new commerce cart item
	*/
	public static CommerceCartItem create(long commerceCartItemId) {
		return getPersistence().create(commerceCartItemId);
	}

	/**
	* Removes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item that was removed
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem remove(long commerceCartItemId)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence().remove(commerceCartItemId);
	}

	public static CommerceCartItem updateImpl(CommerceCartItem commerceCartItem) {
		return getPersistence().updateImpl(commerceCartItem);
	}

	/**
	* Returns the commerce cart item with the primary key or throws a {@link NoSuchCartItemException} if it could not be found.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item
	* @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem findByPrimaryKey(long commerceCartItemId)
		throws com.liferay.commerce.cart.exception.NoSuchCartItemException {
		return getPersistence().findByPrimaryKey(commerceCartItemId);
	}

	/**
	* Returns the commerce cart item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item, or <code>null</code> if a commerce cart item with the primary key could not be found
	*/
	public static CommerceCartItem fetchByPrimaryKey(long commerceCartItemId) {
		return getPersistence().fetchByPrimaryKey(commerceCartItemId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCartItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce cart items.
	*
	* @return the commerce cart items
	*/
	public static List<CommerceCartItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of commerce cart items
	*/
	public static List<CommerceCartItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce cart items
	*/
	public static List<CommerceCartItem> findAll(int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce cart items
	*/
	public static List<CommerceCartItem> findAll(int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce cart items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce cart items.
	*
	* @return the number of commerce cart items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceCartItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartItemPersistence, CommerceCartItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartItemPersistence.class);
}