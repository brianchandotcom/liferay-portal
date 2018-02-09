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

import com.liferay.commerce.model.CommerceCart;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce cart service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceCartPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceCartPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCartUtil {
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
	public static void clearCache(CommerceCart commerceCart) {
		getPersistence().clearCache(commerceCart);
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
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCart> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCart update(CommerceCart commerceCart) {
		return getPersistence().update(commerceCart);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCart update(CommerceCart commerceCart,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceCart, serviceContext);
	}

	/**
	* Returns all the commerce carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByUuid_PrevAndNext(long commerceCartId,
		java.lang.String uuid, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceCartId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce carts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce carts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce cart where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce cart that was removed
	*/
	public static CommerceCart removeByUUID_G(java.lang.String uuid,
		long groupId) throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce carts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce carts
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByUuid_C_PrevAndNext(long commerceCartId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceCartId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the commerce carts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce carts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByGroupId_First(long groupId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByGroupId_Last(long groupId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByGroupId_PrevAndNext(
		long commerceCartId, long groupId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceCartId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce carts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce carts
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce carts where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByBillingAddressId(
		long billingAddressId) {
		return getPersistence().findByBillingAddressId(billingAddressId);
	}

	/**
	* Returns a range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end) {
		return getPersistence()
				   .findByBillingAddressId(billingAddressId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByBillingAddressId(billingAddressId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByBillingAddressId(billingAddressId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByBillingAddressId_First(
		long billingAddressId, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByBillingAddressId_First(billingAddressId,
			orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByBillingAddressId_First(
		long billingAddressId, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByBillingAddressId_First(billingAddressId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByBillingAddressId_Last(
		long billingAddressId, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByBillingAddressId_Last(billingAddressId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByBillingAddressId_Last(
		long billingAddressId, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByBillingAddressId_Last(billingAddressId,
			orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByBillingAddressId_PrevAndNext(
		long commerceCartId, long billingAddressId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByBillingAddressId_PrevAndNext(commerceCartId,
			billingAddressId, orderByComparator);
	}

	/**
	* Removes all the commerce carts where billingAddressId = &#63; from the database.
	*
	* @param billingAddressId the billing address ID
	*/
	public static void removeByBillingAddressId(long billingAddressId) {
		getPersistence().removeByBillingAddressId(billingAddressId);
	}

	/**
	* Returns the number of commerce carts where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @return the number of matching commerce carts
	*/
	public static int countByBillingAddressId(long billingAddressId) {
		return getPersistence().countByBillingAddressId(billingAddressId);
	}

	/**
	* Returns all the commerce carts where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByShippingAddressId(
		long shippingAddressId) {
		return getPersistence().findByShippingAddressId(shippingAddressId);
	}

	/**
	* Returns a range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end) {
		return getPersistence()
				   .findByShippingAddressId(shippingAddressId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByShippingAddressId(shippingAddressId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByShippingAddressId(shippingAddressId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByShippingAddressId_First(
		long shippingAddressId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByShippingAddressId_First(shippingAddressId,
			orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByShippingAddressId_First(
		long shippingAddressId,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByShippingAddressId_First(shippingAddressId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByShippingAddressId_Last(
		long shippingAddressId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByShippingAddressId_Last(shippingAddressId,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByShippingAddressId_Last(
		long shippingAddressId,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByShippingAddressId_Last(shippingAddressId,
			orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByShippingAddressId_PrevAndNext(
		long commerceCartId, long shippingAddressId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByShippingAddressId_PrevAndNext(commerceCartId,
			shippingAddressId, orderByComparator);
	}

	/**
	* Removes all the commerce carts where shippingAddressId = &#63; from the database.
	*
	* @param shippingAddressId the shipping address ID
	*/
	public static void removeByShippingAddressId(long shippingAddressId) {
		getPersistence().removeByShippingAddressId(shippingAddressId);
	}

	/**
	* Returns the number of commerce carts where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @return the number of matching commerce carts
	*/
	public static int countByShippingAddressId(long shippingAddressId) {
		return getPersistence().countByShippingAddressId(shippingAddressId);
	}

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name) {
		return getPersistence().findByG_U_N(groupId, userId, name);
	}

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_U_N(groupId, userId, name, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByG_U_N(groupId, userId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_N(groupId, userId, name, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_U_N_First(long groupId, long userId,
		java.lang.String name, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_N_First(groupId, userId, name, orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_N_First(long groupId, long userId,
		java.lang.String name, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_N_First(groupId, userId, name, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_U_N_Last(long groupId, long userId,
		java.lang.String name, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_N_Last(groupId, userId, name, orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_N_Last(long groupId, long userId,
		java.lang.String name, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_N_Last(groupId, userId, name, orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByG_U_N_PrevAndNext(long commerceCartId,
		long groupId, long userId, java.lang.String name,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_N_PrevAndNext(commerceCartId, groupId, userId,
			name, orderByComparator);
	}

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	*/
	public static void removeByG_U_N(long groupId, long userId,
		java.lang.String name) {
		getPersistence().removeByG_U_N(groupId, userId, name);
	}

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the number of matching commerce carts
	*/
	public static int countByG_U_N(long groupId, long userId,
		java.lang.String name) {
		return getPersistence().countByG_U_N(groupId, userId, name);
	}

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @return the matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart) {
		return getPersistence().findByG_U_D(groupId, userId, defaultCart);
	}

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end) {
		return getPersistence()
				   .findByG_U_D(groupId, userId, defaultCart, start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .findByG_U_D(groupId, userId, defaultCart, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public static List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_U_D(groupId, userId, defaultCart, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_U_D_First(long groupId, long userId,
		boolean defaultCart, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_D_First(groupId, userId, defaultCart,
			orderByComparator);
	}

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_D_First(long groupId, long userId,
		boolean defaultCart, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_D_First(groupId, userId, defaultCart,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public static CommerceCart findByG_U_D_Last(long groupId, long userId,
		boolean defaultCart, OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_D_Last(groupId, userId, defaultCart,
			orderByComparator);
	}

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public static CommerceCart fetchByG_U_D_Last(long groupId, long userId,
		boolean defaultCart, OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence()
				   .fetchByG_U_D_Last(groupId, userId, defaultCart,
			orderByComparator);
	}

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart[] findByG_U_D_PrevAndNext(long commerceCartId,
		long groupId, long userId, boolean defaultCart,
		OrderByComparator<CommerceCart> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence()
				   .findByG_U_D_PrevAndNext(commerceCartId, groupId, userId,
			defaultCart, orderByComparator);
	}

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	*/
	public static void removeByG_U_D(long groupId, long userId,
		boolean defaultCart) {
		getPersistence().removeByG_U_D(groupId, userId, defaultCart);
	}

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @return the number of matching commerce carts
	*/
	public static int countByG_U_D(long groupId, long userId,
		boolean defaultCart) {
		return getPersistence().countByG_U_D(groupId, userId, defaultCart);
	}

	/**
	* Caches the commerce cart in the entity cache if it is enabled.
	*
	* @param commerceCart the commerce cart
	*/
	public static void cacheResult(CommerceCart commerceCart) {
		getPersistence().cacheResult(commerceCart);
	}

	/**
	* Caches the commerce carts in the entity cache if it is enabled.
	*
	* @param commerceCarts the commerce carts
	*/
	public static void cacheResult(List<CommerceCart> commerceCarts) {
		getPersistence().cacheResult(commerceCarts);
	}

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param commerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	public static CommerceCart create(long commerceCartId) {
		return getPersistence().create(commerceCartId);
	}

	/**
	* Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart remove(long commerceCartId)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().remove(commerceCartId);
	}

	public static CommerceCart updateImpl(CommerceCart commerceCart) {
		return getPersistence().updateImpl(commerceCart);
	}

	/**
	* Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart findByPrimaryKey(long commerceCartId)
		throws com.liferay.commerce.exception.NoSuchCartException {
		return getPersistence().findByPrimaryKey(commerceCartId);
	}

	/**
	* Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	*/
	public static CommerceCart fetchByPrimaryKey(long commerceCartId) {
		return getPersistence().fetchByPrimaryKey(commerceCartId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce carts.
	*
	* @return the commerce carts
	*/
	public static List<CommerceCart> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce carts
	*/
	public static List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce carts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce carts.
	*
	* @return the number of commerce carts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceCartPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartPersistence, CommerceCartPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartPersistence.class);
}