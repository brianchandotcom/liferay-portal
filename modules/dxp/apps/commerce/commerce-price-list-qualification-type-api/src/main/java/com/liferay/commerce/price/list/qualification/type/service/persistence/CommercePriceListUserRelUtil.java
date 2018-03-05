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

package com.liferay.commerce.price.list.qualification.type.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce price list user rel service. This utility wraps {@link com.liferay.commerce.price.list.qualification.type.service.persistence.impl.CommercePriceListUserRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelPersistence
 * @see com.liferay.commerce.price.list.qualification.type.service.persistence.impl.CommercePriceListUserRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelUtil {
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
	public static void clearCache(
		CommercePriceListUserRel commercePriceListUserRel) {
		getPersistence().clearCache(commercePriceListUserRel);
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
	public static List<CommercePriceListUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListUserRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListUserRel update(
		CommercePriceListUserRel commercePriceListUserRel) {
		return getPersistence().update(commercePriceListUserRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListUserRel update(
		CommercePriceListUserRel commercePriceListUserRel,
		ServiceContext serviceContext) {
		return getPersistence().update(commercePriceListUserRel, serviceContext);
	}

	/**
	* Returns all the commerce price list user rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel[] findByUuid_PrevAndNext(
		long commercePriceListUserRelId, java.lang.String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commercePriceListUserRelId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce price list user rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce price list user rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list user rels
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce price list user rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list user rel that was removed
	*/
	public static CommercePriceListUserRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce price list user rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list user rels
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserRelId, java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commercePriceListUserRelId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price list user rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce price list user rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list user rels
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId_First(commercePriceListQualificationTypeRelId,
			orderByComparator);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListQualificationTypeRelId_First(commercePriceListQualificationTypeRelId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId_Last(commercePriceListQualificationTypeRelId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListQualificationTypeRelId_Last(commercePriceListQualificationTypeRelId,
			orderByComparator);
	}

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel[] findByCommercePriceListQualificationTypeRelId_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByCommercePriceListQualificationTypeRelId_PrevAndNext(commercePriceListUserRelId,
			commercePriceListQualificationTypeRelId, orderByComparator);
	}

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	*/
	public static void removeByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		getPersistence()
			.removeByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @return the number of matching commerce price list user rels
	*/
	public static int countByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		return getPersistence()
				   .countByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId) {
		return getPersistence()
				   .findByC_C(commercePriceListQualificationTypeRelId,
			classNameId);
	}

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end) {
		return getPersistence()
				   .findByC_C(commercePriceListQualificationTypeRelId,
			classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findByC_C(commercePriceListQualificationTypeRelId,
			classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(commercePriceListQualificationTypeRelId,
			classNameId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_First(commercePriceListQualificationTypeRelId,
			classNameId, orderByComparator);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(commercePriceListQualificationTypeRelId,
			classNameId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_Last(commercePriceListQualificationTypeRelId,
			classNameId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(commercePriceListQualificationTypeRelId,
			classNameId, orderByComparator);
	}

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel[] findByC_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_PrevAndNext(commercePriceListUserRelId,
			commercePriceListQualificationTypeRelId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	*/
	public static void removeByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId) {
		getPersistence()
			.removeByC_C(commercePriceListQualificationTypeRelId, classNameId);
	}

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @return the number of matching commerce price list user rels
	*/
	public static int countByC_C(long commercePriceListQualificationTypeRelId,
		long classNameId) {
		return getPersistence()
				   .countByC_C(commercePriceListQualificationTypeRelId,
			classNameId);
	}

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK);
	}

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_C_First(commercePriceListQualificationTypeRelId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_C_First(commercePriceListQualificationTypeRelId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel findByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_C_Last(commercePriceListQualificationTypeRelId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static CommercePriceListUserRel fetchByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_C_Last(commercePriceListQualificationTypeRelId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel[] findByC_C_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence()
				   .findByC_C_C_PrevAndNext(commercePriceListUserRelId,
			commercePriceListQualificationTypeRelId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPKs the class pks
	* @return the matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long[] classPKs) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPKs);
	}

	/**
	* Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPKs the class pks
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long[] classPKs, int start, int end) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPKs, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPKs the class pks
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long[] classPKs, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPKs, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long[] classPKs, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPKs, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK) {
		getPersistence()
			.removeByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK);
	}

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce price list user rels
	*/
	public static int countByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK) {
		return getPersistence()
				   .countByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK);
	}

	/**
	* Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = any &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	* @param classNameId the class name ID
	* @param classPKs the class pks
	* @return the number of matching commerce price list user rels
	*/
	public static int countByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long[] classPKs) {
		return getPersistence()
				   .countByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPKs);
	}

	/**
	* Caches the commerce price list user rel in the entity cache if it is enabled.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	*/
	public static void cacheResult(
		CommercePriceListUserRel commercePriceListUserRel) {
		getPersistence().cacheResult(commercePriceListUserRel);
	}

	/**
	* Caches the commerce price list user rels in the entity cache if it is enabled.
	*
	* @param commercePriceListUserRels the commerce price list user rels
	*/
	public static void cacheResult(
		List<CommercePriceListUserRel> commercePriceListUserRels) {
		getPersistence().cacheResult(commercePriceListUserRels);
	}

	/**
	* Creates a new commerce price list user rel with the primary key. Does not add the commerce price list user rel to the database.
	*
	* @param commercePriceListUserRelId the primary key for the new commerce price list user rel
	* @return the new commerce price list user rel
	*/
	public static CommercePriceListUserRel create(
		long commercePriceListUserRelId) {
		return getPersistence().create(commercePriceListUserRelId);
	}

	/**
	* Removes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel that was removed
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel remove(
		long commercePriceListUserRelId)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().remove(commercePriceListUserRelId);
	}

	public static CommercePriceListUserRel updateImpl(
		CommercePriceListUserRel commercePriceListUserRel) {
		return getPersistence().updateImpl(commercePriceListUserRel);
	}

	/**
	* Returns the commerce price list user rel with the primary key or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel
	* @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel findByPrimaryKey(
		long commercePriceListUserRelId)
		throws com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException {
		return getPersistence().findByPrimaryKey(commercePriceListUserRelId);
	}

	/**
	* Returns the commerce price list user rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel, or <code>null</code> if a commerce price list user rel with the primary key could not be found
	*/
	public static CommercePriceListUserRel fetchByPrimaryKey(
		long commercePriceListUserRelId) {
		return getPersistence().fetchByPrimaryKey(commercePriceListUserRelId);
	}

	public static java.util.Map<java.io.Serializable, CommercePriceListUserRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce price list user rels.
	*
	* @return the commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findAll(int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list user rels
	*/
	public static List<CommercePriceListUserRel> findAll(int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce price list user rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce price list user rels.
	*
	* @return the number of commerce price list user rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePriceListUserRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListUserRelPersistence, CommercePriceListUserRelPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommercePriceListUserRelPersistence.class);
}