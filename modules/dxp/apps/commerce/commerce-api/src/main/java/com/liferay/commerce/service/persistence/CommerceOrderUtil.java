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

import com.liferay.commerce.model.CommerceOrder;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce order service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceOrderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderUtil {
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
	public static void clearCache(CommerceOrder commerceOrder) {
		getPersistence().clearCache(commerceOrder);
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
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrder update(CommerceOrder commerceOrder) {
		return getPersistence().update(commerceOrder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrder update(CommerceOrder commerceOrder,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceOrder, serviceContext);
	}

	/**
	* Returns all the commerce orders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce orders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce order in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce order in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce orders before and after the current commerce order in the ordered set where uuid = &#63;.
	*
	* @param commerceOrderId the primary key of the current commerce order
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder[] findByUuid_PrevAndNext(long commerceOrderId,
		java.lang.String uuid,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceOrderId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce orders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce orders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce orders
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce order where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce order where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce order where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce order where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce order that was removed
	*/
	public static CommerceOrder removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce orders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce orders
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce orders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce orders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce order in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce orders before and after the current commerce order in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceOrderId the primary key of the current commerce order
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder[] findByUuid_C_PrevAndNext(
		long commerceOrderId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceOrderId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the commerce orders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce orders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce orders
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce orders
	*/
	public static List<CommerceOrder> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of matching commerce orders
	*/
	public static List<CommerceOrder> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByGroupId_First(long groupId,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByGroupId_Last(long groupId,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce orders before and after the current commerce order in the ordered set where groupId = &#63;.
	*
	* @param commerceOrderId the primary key of the current commerce order
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder[] findByGroupId_PrevAndNext(
		long commerceOrderId, long groupId,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceOrderId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce orders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce orders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce orders
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce orders where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @return the matching commerce orders
	*/
	public static List<CommerceOrder> findByG_O(long groupId, int orderStatus) {
		return getPersistence().findByG_O(groupId, orderStatus);
	}

	/**
	* Returns a range of all the commerce orders where groupId = &#63; and orderStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of matching commerce orders
	*/
	public static List<CommerceOrder> findByG_O(long groupId, int orderStatus,
		int start, int end) {
		return getPersistence().findByG_O(groupId, orderStatus, start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders where groupId = &#63; and orderStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByG_O(long groupId, int orderStatus,
		int start, int end, OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .findByG_O(groupId, orderStatus, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders where groupId = &#63; and orderStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce orders
	*/
	public static List<CommerceOrder> findByG_O(long groupId, int orderStatus,
		int start, int end, OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_O(groupId, orderStatus, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order in the ordered set where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByG_O_First(long groupId, int orderStatus,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_O_First(groupId, orderStatus, orderByComparator);
	}

	/**
	* Returns the first commerce order in the ordered set where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByG_O_First(long groupId, int orderStatus,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .fetchByG_O_First(groupId, orderStatus, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order
	* @throws NoSuchOrderException if a matching commerce order could not be found
	*/
	public static CommerceOrder findByG_O_Last(long groupId, int orderStatus,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_O_Last(groupId, orderStatus, orderByComparator);
	}

	/**
	* Returns the last commerce order in the ordered set where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	public static CommerceOrder fetchByG_O_Last(long groupId, int orderStatus,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence()
				   .fetchByG_O_Last(groupId, orderStatus, orderByComparator);
	}

	/**
	* Returns the commerce orders before and after the current commerce order in the ordered set where groupId = &#63; and orderStatus = &#63;.
	*
	* @param commerceOrderId the primary key of the current commerce order
	* @param groupId the group ID
	* @param orderStatus the order status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder[] findByG_O_PrevAndNext(long commerceOrderId,
		long groupId, int orderStatus,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence()
				   .findByG_O_PrevAndNext(commerceOrderId, groupId,
			orderStatus, orderByComparator);
	}

	/**
	* Removes all the commerce orders where groupId = &#63; and orderStatus = &#63; from the database.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	*/
	public static void removeByG_O(long groupId, int orderStatus) {
		getPersistence().removeByG_O(groupId, orderStatus);
	}

	/**
	* Returns the number of commerce orders where groupId = &#63; and orderStatus = &#63;.
	*
	* @param groupId the group ID
	* @param orderStatus the order status
	* @return the number of matching commerce orders
	*/
	public static int countByG_O(long groupId, int orderStatus) {
		return getPersistence().countByG_O(groupId, orderStatus);
	}

	/**
	* Caches the commerce order in the entity cache if it is enabled.
	*
	* @param commerceOrder the commerce order
	*/
	public static void cacheResult(CommerceOrder commerceOrder) {
		getPersistence().cacheResult(commerceOrder);
	}

	/**
	* Caches the commerce orders in the entity cache if it is enabled.
	*
	* @param commerceOrders the commerce orders
	*/
	public static void cacheResult(List<CommerceOrder> commerceOrders) {
		getPersistence().cacheResult(commerceOrders);
	}

	/**
	* Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	*
	* @param commerceOrderId the primary key for the new commerce order
	* @return the new commerce order
	*/
	public static CommerceOrder create(long commerceOrderId) {
		return getPersistence().create(commerceOrderId);
	}

	/**
	* Removes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order that was removed
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder remove(long commerceOrderId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().remove(commerceOrderId);
	}

	public static CommerceOrder updateImpl(CommerceOrder commerceOrder) {
		return getPersistence().updateImpl(commerceOrder);
	}

	/**
	* Returns the commerce order with the primary key or throws a {@link NoSuchOrderException} if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order
	* @throws NoSuchOrderException if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder findByPrimaryKey(long commerceOrderId)
		throws com.liferay.commerce.exception.NoSuchOrderException {
		return getPersistence().findByPrimaryKey(commerceOrderId);
	}

	/**
	* Returns the commerce order with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order, or <code>null</code> if a commerce order with the primary key could not be found
	*/
	public static CommerceOrder fetchByPrimaryKey(long commerceOrderId) {
		return getPersistence().fetchByPrimaryKey(commerceOrderId);
	}

	public static java.util.Map<java.io.Serializable, CommerceOrder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce orders.
	*
	* @return the commerce orders
	*/
	public static List<CommerceOrder> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce orders
	*/
	public static List<CommerceOrder> findAll(int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce orders from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce orders.
	*
	* @return the number of commerce orders
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceOrderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderPersistence, CommerceOrderPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderPersistence.class);
}