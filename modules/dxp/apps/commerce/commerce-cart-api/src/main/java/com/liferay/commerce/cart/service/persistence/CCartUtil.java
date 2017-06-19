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

import com.liferay.commerce.cart.model.CCart;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the c cart service. This utility wraps {@link com.liferay.commerce.cart.service.persistence.impl.CCartPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CCartPersistence
 * @see com.liferay.commerce.cart.service.persistence.impl.CCartPersistenceImpl
 * @generated
 */
@ProviderType
public class CCartUtil {
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
	public static void clearCache(CCart cCart) {
		getPersistence().clearCache(cCart);
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
	public static List<CCart> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CCart> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CCart> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<CCart> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CCart update(CCart cCart) {
		return getPersistence().update(cCart);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CCart update(CCart cCart, ServiceContext serviceContext) {
		return getPersistence().update(cCart, serviceContext);
	}

	/**
	* Returns all the c carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c carts
	*/
	public static List<CCart> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public static List<CCart> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CCart> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CCart> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByUuid_First(java.lang.String uuid,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the c carts before and after the current c cart in the ordered set where uuid = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public static CCart[] findByUuid_PrevAndNext(long CCartId,
		java.lang.String uuid, OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CCartId, uuid, orderByComparator);
	}

	/**
	* Removes all the c carts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of c carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c carts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCCartException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the c cart where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c cart that was removed
	*/
	public static CCart removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of c carts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c carts
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c carts
	*/
	public static List<CCart> findByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public static List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CCart> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CCart> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CCart> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the c carts before and after the current c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public static CCart[] findByUuid_C_PrevAndNext(long CCartId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CCartId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the c carts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of c carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c carts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the c carts where type = &#63;.
	*
	* @param type the type
	* @return the matching c carts
	*/
	public static List<CCart> findByType(int type) {
		return getPersistence().findByType(type);
	}

	/**
	* Returns a range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public static List<CCart> findByType(int type, int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByType(int type, int start, int end,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public static List<CCart> findByType(int type, int start, int end,
		OrderByComparator<CCart> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByType_First(int type,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByType_First(int type,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public static CCart findByType_Last(int type,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public static CCart fetchByType_Last(int type,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the c carts before and after the current c cart in the ordered set where type = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public static CCart[] findByType_PrevAndNext(long CCartId, int type,
		OrderByComparator<CCart> orderByComparator)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence()
				   .findByType_PrevAndNext(CCartId, type, orderByComparator);
	}

	/**
	* Removes all the c carts where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(int type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of c carts where type = &#63;.
	*
	* @param type the type
	* @return the number of matching c carts
	*/
	public static int countByType(int type) {
		return getPersistence().countByType(type);
	}

	/**
	* Caches the c cart in the entity cache if it is enabled.
	*
	* @param cCart the c cart
	*/
	public static void cacheResult(CCart cCart) {
		getPersistence().cacheResult(cCart);
	}

	/**
	* Caches the c carts in the entity cache if it is enabled.
	*
	* @param cCarts the c carts
	*/
	public static void cacheResult(List<CCart> cCarts) {
		getPersistence().cacheResult(cCarts);
	}

	/**
	* Creates a new c cart with the primary key. Does not add the c cart to the database.
	*
	* @param CCartId the primary key for the new c cart
	* @return the new c cart
	*/
	public static CCart create(long CCartId) {
		return getPersistence().create(CCartId);
	}

	/**
	* Removes the c cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart that was removed
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public static CCart remove(long CCartId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().remove(CCartId);
	}

	public static CCart updateImpl(CCart cCart) {
		return getPersistence().updateImpl(cCart);
	}

	/**
	* Returns the c cart with the primary key or throws a {@link NoSuchCCartException} if it could not be found.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public static CCart findByPrimaryKey(long CCartId)
		throws com.liferay.commerce.cart.exception.NoSuchCCartException {
		return getPersistence().findByPrimaryKey(CCartId);
	}

	/**
	* Returns the c cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart, or <code>null</code> if a c cart with the primary key could not be found
	*/
	public static CCart fetchByPrimaryKey(long CCartId) {
		return getPersistence().fetchByPrimaryKey(CCartId);
	}

	public static java.util.Map<java.io.Serializable, CCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the c carts.
	*
	* @return the c carts
	*/
	public static List<CCart> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of c carts
	*/
	public static List<CCart> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c carts
	*/
	public static List<CCart> findAll(int start, int end,
		OrderByComparator<CCart> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c carts
	*/
	public static List<CCart> findAll(int start, int end,
		OrderByComparator<CCart> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the c carts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of c carts.
	*
	* @return the number of c carts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CCartPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CCartPersistence, CCartPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CCartPersistence.class);
}