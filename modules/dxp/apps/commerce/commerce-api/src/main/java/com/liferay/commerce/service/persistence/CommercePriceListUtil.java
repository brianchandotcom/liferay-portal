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

import com.liferay.commerce.model.CommercePriceList;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the commerce price list service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommercePriceListPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommercePriceListPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListUtil {
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
	public static void clearCache(CommercePriceList commercePriceList) {
		getPersistence().clearCache(commercePriceList);
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
	public static List<CommercePriceList> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceList> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceList update(CommercePriceList commercePriceList) {
		return getPersistence().update(commercePriceList);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceList update(
		CommercePriceList commercePriceList, ServiceContext serviceContext) {
		return getPersistence().update(commercePriceList, serviceContext);
	}

	/**
	* Returns all the commerce price lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByUuid_PrevAndNext(
		long commercePriceListId, java.lang.String uuid,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commercePriceListId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce price lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce price lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price lists
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce price list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list that was removed
	*/
	public static CommercePriceList removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce price lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price lists
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByUuid_C_PrevAndNext(
		long commercePriceListId, java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commercePriceListId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price lists where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price lists
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce price lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByGroupId_First(long groupId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByGroupId_First(long groupId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByGroupId_Last(long groupId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByGroupId_Last(long groupId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByGroupId_PrevAndNext(
		long commercePriceListId, long groupId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commercePriceListId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce price lists where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce price lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce price lists
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce price lists where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByCompanyId_First(long companyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByCompanyId_First(long companyId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByCompanyId_Last(long companyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where companyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByCompanyId_PrevAndNext(
		long commercePriceListId, long companyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commercePriceListId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the commerce price lists where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce price lists where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce price lists
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByParentCommercePriceListId(
		long parentCommercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByParentCommercePriceListId(parentCommercePriceListId);
	}

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByParentCommercePriceListId(
		long parentCommercePriceListId) {
		return getPersistence()
				   .fetchByParentCommercePriceListId(parentCommercePriceListId);
	}

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByParentCommercePriceListId(
		long parentCommercePriceListId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByParentCommercePriceListId(parentCommercePriceListId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce price list where parentCommercePriceListId = &#63; from the database.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the commerce price list that was removed
	*/
	public static CommercePriceList removeByParentCommercePriceListId(
		long parentCommercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .removeByParentCommercePriceListId(parentCommercePriceListId);
	}

	/**
	* Returns the number of commerce price lists where parentCommercePriceListId = &#63;.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the number of matching commerce price lists
	*/
	public static int countByParentCommercePriceListId(
		long parentCommercePriceListId) {
		return getPersistence()
				   .countByParentCommercePriceListId(parentCommercePriceListId);
	}

	/**
	* Returns all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId) {
		return getPersistence().findByCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	* Returns a range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end) {
		return getPersistence()
				   .findByCommerceCurrencyId(commerceCurrencyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByCommerceCurrencyId(commerceCurrencyId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCurrencyId(commerceCurrencyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByCommerceCurrencyId_First(
		long commerceCurrencyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCommerceCurrencyId_First(commerceCurrencyId,
			orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByCommerceCurrencyId_First(
		long commerceCurrencyId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCurrencyId_First(commerceCurrencyId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByCommerceCurrencyId_Last(
		long commerceCurrencyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCommerceCurrencyId_Last(commerceCurrencyId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByCommerceCurrencyId_Last(
		long commerceCurrencyId,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCurrencyId_Last(commerceCurrencyId,
			orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByCommerceCurrencyId_PrevAndNext(
		long commercePriceListId, long commerceCurrencyId,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByCommerceCurrencyId_PrevAndNext(commercePriceListId,
			commerceCurrencyId, orderByComparator);
	}

	/**
	* Removes all the commerce price lists where commerceCurrencyId = &#63; from the database.
	*
	* @param commerceCurrencyId the commerce currency ID
	*/
	public static void removeByCommerceCurrencyId(long commerceCurrencyId) {
		getPersistence().removeByCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	* Returns the number of commerce price lists where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @return the number of matching commerce price lists
	*/
	public static int countByCommerceCurrencyId(long commerceCurrencyId) {
		return getPersistence().countByCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	* Returns all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	* Returns a range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_S(long groupId, int status,
		int start, int end) {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_S(long groupId, int status,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_S(long groupId, int status,
		int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_S(groupId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByG_S_First(long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByG_S_First(long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByG_S_Last(long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByG_S_Last(long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByG_S_PrevAndNext(
		long commercePriceListId, long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_S_PrevAndNext(commercePriceListId, groupId, status,
			orderByComparator);
	}

	/**
	* Removes all the commerce price lists where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	* Returns the number of commerce price lists where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	* Returns all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_NotS(long groupId, int status) {
		return getPersistence().findByG_NotS(groupId, status);
	}

	/**
	* Returns a range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end) {
		return getPersistence().findByG_NotS(groupId, status, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByG_NotS(groupId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_NotS(groupId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByG_NotS_First(long groupId,
		int status, OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_NotS_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByG_NotS_First(long groupId,
		int status, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByG_NotS_First(groupId, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByG_NotS_Last(long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_NotS_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByG_NotS_Last(long groupId,
		int status, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByG_NotS_Last(groupId, status, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByG_NotS_PrevAndNext(
		long commercePriceListId, long groupId, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByG_NotS_PrevAndNext(commercePriceListId, groupId,
			status, orderByComparator);
	}

	/**
	* Removes all the commerce price lists where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public static void removeByG_NotS(long groupId, int status) {
		getPersistence().removeByG_NotS(groupId, status);
	}

	/**
	* Returns the number of commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public static int countByG_NotS(long groupId, int status) {
		return getPersistence().countByG_NotS(groupId, status);
	}

	/**
	* Returns all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching commerce price lists
	*/
	public static List<CommercePriceList> findByLtD_S(Date displayDate,
		int status) {
		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	* Returns a range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end) {
		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public static List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLtD_S(displayDate, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_First(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public static CommercePriceList findByLtD_S_Last(Date displayDate,
		int status, OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static CommercePriceList fetchByLtD_S_Last(Date displayDate,
		int status, OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence()
				   .fetchByLtD_S_Last(displayDate, status, orderByComparator);
	}

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList[] findByLtD_S_PrevAndNext(
		long commercePriceListId, Date displayDate, int status,
		OrderByComparator<CommercePriceList> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence()
				   .findByLtD_S_PrevAndNext(commercePriceListId, displayDate,
			status, orderByComparator);
	}

	/**
	* Removes all the commerce price lists where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	* Returns the number of commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	* Caches the commerce price list in the entity cache if it is enabled.
	*
	* @param commercePriceList the commerce price list
	*/
	public static void cacheResult(CommercePriceList commercePriceList) {
		getPersistence().cacheResult(commercePriceList);
	}

	/**
	* Caches the commerce price lists in the entity cache if it is enabled.
	*
	* @param commercePriceLists the commerce price lists
	*/
	public static void cacheResult(List<CommercePriceList> commercePriceLists) {
		getPersistence().cacheResult(commercePriceLists);
	}

	/**
	* Creates a new commerce price list with the primary key. Does not add the commerce price list to the database.
	*
	* @param commercePriceListId the primary key for the new commerce price list
	* @return the new commerce price list
	*/
	public static CommercePriceList create(long commercePriceListId) {
		return getPersistence().create(commercePriceListId);
	}

	/**
	* Removes the commerce price list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list that was removed
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList remove(long commercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().remove(commercePriceListId);
	}

	public static CommercePriceList updateImpl(
		CommercePriceList commercePriceList) {
		return getPersistence().updateImpl(commercePriceList);
	}

	/**
	* Returns the commerce price list with the primary key or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList findByPrimaryKey(long commercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListException {
		return getPersistence().findByPrimaryKey(commercePriceListId);
	}

	/**
	* Returns the commerce price list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list, or <code>null</code> if a commerce price list with the primary key could not be found
	*/
	public static CommercePriceList fetchByPrimaryKey(long commercePriceListId) {
		return getPersistence().fetchByPrimaryKey(commercePriceListId);
	}

	public static java.util.Map<java.io.Serializable, CommercePriceList> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce price lists.
	*
	* @return the commerce price lists
	*/
	public static List<CommercePriceList> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of commerce price lists
	*/
	public static List<CommercePriceList> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price lists
	*/
	public static List<CommercePriceList> findAll(int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price lists
	*/
	public static List<CommercePriceList> findAll(int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce price lists from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce price lists.
	*
	* @return the number of commerce price lists
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePriceListPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListPersistence, CommercePriceListPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListPersistence.class);

		ServiceTracker<CommercePriceListPersistence, CommercePriceListPersistence> serviceTracker =
			new ServiceTracker<CommercePriceListPersistence, CommercePriceListPersistence>(bundle.getBundleContext(),
				CommercePriceListPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}