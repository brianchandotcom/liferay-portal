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

package com.liferay.commerce.discount.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscount;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the commerce discount service. This utility wraps {@link com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountPersistence
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountUtil {
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
	public static void clearCache(CommerceDiscount commerceDiscount) {
		getPersistence().clearCache(commerceDiscount);
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
	public static List<CommerceDiscount> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscount> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscount update(CommerceDiscount commerceDiscount) {
		return getPersistence().update(commerceDiscount);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscount update(CommerceDiscount commerceDiscount,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceDiscount, serviceContext);
	}

	/**
	* Returns all the commerce discounts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] findByUuid_PrevAndNext(
		long commerceDiscountId, java.lang.String uuid,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceDiscountId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce discounts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce discounts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce discounts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchDiscountException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce discount where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce discount that was removed
	*/
	public static CommerceDiscount removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce discounts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce discounts
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] findByUuid_C_PrevAndNext(
		long commerceDiscountId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceDiscountId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce discounts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce discounts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce discounts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discounts
	*/
	public static List<CommerceDiscount> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByGroupId_First(long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where groupId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] findByGroupId_PrevAndNext(
		long commerceDiscountId, long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceDiscountId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discounts that the user has permission to view
	*/
	public static List<CommerceDiscount> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts that the user has permission to view
	*/
	public static List<CommerceDiscount> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts that the user has permission to view
	*/
	public static List<CommerceDiscount> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] filterFindByGroupId_PrevAndNext(
		long commerceDiscountId, long groupId,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(commerceDiscountId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce discounts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce discounts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discounts
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discounts that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status) {
		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	* Returns a range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end) {
		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByLtD_S_Last(Date displayDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByLtD_S_Last(Date displayDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] findByLtD_S_PrevAndNext(
		long commerceDiscountId, Date displayDate, int status,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtD_S_PrevAndNext(commerceDiscountId, displayDate,
			status, orderByComparator);
	}

	/**
	* Removes all the commerce discounts where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	* Returns the number of commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching commerce discounts
	*/
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	* Returns all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @return the matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status) {
		return getPersistence().findByLtE_S(expirationDate, status);
	}

	/**
	* Returns a range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end) {
		return getPersistence().findByLtE_S(expirationDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .findByLtE_S(expirationDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public static List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLtE_S(expirationDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByLtE_S_First(Date expirationDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtE_S_First(expirationDate, status, orderByComparator);
	}

	/**
	* Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByLtE_S_First(Date expirationDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByLtE_S_First(expirationDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public static CommerceDiscount findByLtE_S_Last(Date expirationDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtE_S_Last(expirationDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public static CommerceDiscount fetchByLtE_S_Last(Date expirationDate,
		int status, OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence()
				   .fetchByLtE_S_Last(expirationDate, status, orderByComparator);
	}

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount[] findByLtE_S_PrevAndNext(
		long commerceDiscountId, Date expirationDate, int status,
		OrderByComparator<CommerceDiscount> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence()
				   .findByLtE_S_PrevAndNext(commerceDiscountId, expirationDate,
			status, orderByComparator);
	}

	/**
	* Removes all the commerce discounts where expirationDate &lt; &#63; and status = &#63; from the database.
	*
	* @param expirationDate the expiration date
	* @param status the status
	*/
	public static void removeByLtE_S(Date expirationDate, int status) {
		getPersistence().removeByLtE_S(expirationDate, status);
	}

	/**
	* Returns the number of commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @return the number of matching commerce discounts
	*/
	public static int countByLtE_S(Date expirationDate, int status) {
		return getPersistence().countByLtE_S(expirationDate, status);
	}

	/**
	* Caches the commerce discount in the entity cache if it is enabled.
	*
	* @param commerceDiscount the commerce discount
	*/
	public static void cacheResult(CommerceDiscount commerceDiscount) {
		getPersistence().cacheResult(commerceDiscount);
	}

	/**
	* Caches the commerce discounts in the entity cache if it is enabled.
	*
	* @param commerceDiscounts the commerce discounts
	*/
	public static void cacheResult(List<CommerceDiscount> commerceDiscounts) {
		getPersistence().cacheResult(commerceDiscounts);
	}

	/**
	* Creates a new commerce discount with the primary key. Does not add the commerce discount to the database.
	*
	* @param commerceDiscountId the primary key for the new commerce discount
	* @return the new commerce discount
	*/
	public static CommerceDiscount create(long commerceDiscountId) {
		return getPersistence().create(commerceDiscountId);
	}

	/**
	* Removes the commerce discount with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount that was removed
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount remove(long commerceDiscountId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().remove(commerceDiscountId);
	}

	public static CommerceDiscount updateImpl(CommerceDiscount commerceDiscount) {
		return getPersistence().updateImpl(commerceDiscount);
	}

	/**
	* Returns the commerce discount with the primary key or throws a {@link NoSuchDiscountException} if it could not be found.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount findByPrimaryKey(long commerceDiscountId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountException {
		return getPersistence().findByPrimaryKey(commerceDiscountId);
	}

	/**
	* Returns the commerce discount with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount, or <code>null</code> if a commerce discount with the primary key could not be found
	*/
	public static CommerceDiscount fetchByPrimaryKey(long commerceDiscountId) {
		return getPersistence().fetchByPrimaryKey(commerceDiscountId);
	}

	public static java.util.Map<java.io.Serializable, CommerceDiscount> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce discounts.
	*
	* @return the commerce discounts
	*/
	public static List<CommerceDiscount> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of commerce discounts
	*/
	public static List<CommerceDiscount> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce discounts
	*/
	public static List<CommerceDiscount> findAll(int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce discounts
	*/
	public static List<CommerceDiscount> findAll(int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce discounts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce discounts.
	*
	* @return the number of commerce discounts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceDiscountPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountPersistence, CommerceDiscountPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountPersistence.class);

		ServiceTracker<CommerceDiscountPersistence, CommerceDiscountPersistence> serviceTracker =
			new ServiceTracker<CommerceDiscountPersistence, CommerceDiscountPersistence>(bundle.getBundleContext(),
				CommerceDiscountPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}