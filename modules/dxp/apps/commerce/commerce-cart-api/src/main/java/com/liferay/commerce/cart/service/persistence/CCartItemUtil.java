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

import com.liferay.commerce.cart.model.CCartItem;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the c cart item service. This utility wraps {@link com.liferay.commerce.cart.service.persistence.impl.CCartItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CCartItemPersistence
 * @see com.liferay.commerce.cart.service.persistence.impl.CCartItemPersistenceImpl
 * @generated
 */
@ProviderType
public class CCartItemUtil {
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
	public static void clearCache(CCartItem cCartItem) {
		getPersistence().clearCache(cCartItem);
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
	public static List<CCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CCartItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CCartItem update(CCartItem cCartItem) {
		return getPersistence().update(cCartItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CCartItem update(CCartItem cCartItem,
		ServiceContext serviceContext) {
		return getPersistence().update(cCartItem, serviceContext);
	}

	/**
	* Returns all the c cart items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c cart items
	*/
	public static List<CCartItem> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public static List<CCartItem> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByUuid_First(java.lang.String uuid,
		OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public static CCartItem[] findByUuid_PrevAndNext(long CCartItemId,
		java.lang.String uuid, OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CCartItemId, uuid, orderByComparator);
	}

	/**
	* Removes all the c cart items where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of c cart items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c cart items
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCCartItemException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the c cart item where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c cart item that was removed
	*/
	public static CCartItem removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of c cart items where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c cart items
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c cart items
	*/
	public static List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public static List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public static CCartItem[] findByUuid_C_PrevAndNext(long CCartItemId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CCartItemId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the c cart items where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of c cart items where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c cart items
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the c cart items where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @return the matching c cart items
	*/
	public static List<CCartItem> findByCCartId(long CCartId) {
		return getPersistence().findByCCartId(CCartId);
	}

	/**
	* Returns a range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public static List<CCartItem> findByCCartId(long CCartId, int start, int end) {
		return getPersistence().findByCCartId(CCartId, start, end);
	}

	/**
	* Returns an ordered range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByCCartId(long CCartId, int start,
		int end, OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence()
				   .findByCCartId(CCartId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public static List<CCartItem> findByCCartId(long CCartId, int start,
		int end, OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCCartId(CCartId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByCCartId_First(long CCartId,
		OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByCCartId_First(CCartId, orderByComparator);
	}

	/**
	* Returns the first c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByCCartId_First(long CCartId,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().fetchByCCartId_First(CCartId, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public static CCartItem findByCCartId_Last(long CCartId,
		OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByCCartId_Last(CCartId, orderByComparator);
	}

	/**
	* Returns the last c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static CCartItem fetchByCCartId_Last(long CCartId,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().fetchByCCartId_Last(CCartId, orderByComparator);
	}

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public static CCartItem[] findByCCartId_PrevAndNext(long CCartItemId,
		long CCartId, OrderByComparator<CCartItem> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence()
				   .findByCCartId_PrevAndNext(CCartItemId, CCartId,
			orderByComparator);
	}

	/**
	* Removes all the c cart items where CCartId = &#63; from the database.
	*
	* @param CCartId the c cart ID
	*/
	public static void removeByCCartId(long CCartId) {
		getPersistence().removeByCCartId(CCartId);
	}

	/**
	* Returns the number of c cart items where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @return the number of matching c cart items
	*/
	public static int countByCCartId(long CCartId) {
		return getPersistence().countByCCartId(CCartId);
	}

	/**
	* Caches the c cart item in the entity cache if it is enabled.
	*
	* @param cCartItem the c cart item
	*/
	public static void cacheResult(CCartItem cCartItem) {
		getPersistence().cacheResult(cCartItem);
	}

	/**
	* Caches the c cart items in the entity cache if it is enabled.
	*
	* @param cCartItems the c cart items
	*/
	public static void cacheResult(List<CCartItem> cCartItems) {
		getPersistence().cacheResult(cCartItems);
	}

	/**
	* Creates a new c cart item with the primary key. Does not add the c cart item to the database.
	*
	* @param CCartItemId the primary key for the new c cart item
	* @return the new c cart item
	*/
	public static CCartItem create(long CCartItemId) {
		return getPersistence().create(CCartItemId);
	}

	/**
	* Removes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item that was removed
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public static CCartItem remove(long CCartItemId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().remove(CCartItemId);
	}

	public static CCartItem updateImpl(CCartItem cCartItem) {
		return getPersistence().updateImpl(cCartItem);
	}

	/**
	* Returns the c cart item with the primary key or throws a {@link NoSuchCCartItemException} if it could not be found.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public static CCartItem findByPrimaryKey(long CCartItemId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartItemException {
		return getPersistence().findByPrimaryKey(CCartItemId);
	}

	/**
	* Returns the c cart item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item, or <code>null</code> if a c cart item with the primary key could not be found
	*/
	public static CCartItem fetchByPrimaryKey(long CCartItemId) {
		return getPersistence().fetchByPrimaryKey(CCartItemId);
	}

	public static java.util.Map<java.io.Serializable, CCartItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the c cart items.
	*
	* @return the c cart items
	*/
	public static List<CCartItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of c cart items
	*/
	public static List<CCartItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c cart items
	*/
	public static List<CCartItem> findAll(int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c cart items
	*/
	public static List<CCartItem> findAll(int start, int end,
		OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the c cart items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of c cart items.
	*
	* @return the number of c cart items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CCartItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CCartItemPersistence, CCartItemPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CCartItemPersistence.class);
}