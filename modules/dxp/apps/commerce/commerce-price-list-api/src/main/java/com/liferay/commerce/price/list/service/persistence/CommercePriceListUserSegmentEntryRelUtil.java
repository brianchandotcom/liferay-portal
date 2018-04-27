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

package com.liferay.commerce.price.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce price list user segment entry rel service. This utility wraps {@link com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListUserSegmentEntryRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelPersistence
 * @see com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListUserSegmentEntryRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelUtil {
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
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		getPersistence().clearCache(commercePriceListUserSegmentEntryRel);
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
	public static List<CommercePriceListUserSegmentEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListUserSegmentEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListUserSegmentEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListUserSegmentEntryRel update(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return getPersistence().update(commercePriceListUserSegmentEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListUserSegmentEntryRel update(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commercePriceListUserSegmentEntryRel, serviceContext);
	}

	/**
	* Returns all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel[] findByUuid_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commercePriceListUserSegmentEntryRelId,
			uuid, orderByComparator);
	}

	/**
	* Removes all the commerce price list user segment entry rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list user segment entry rels
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByUUID_G(
		String uuid, long groupId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUUID_G(
		String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list user segment entry rel that was removed
	*/
	public static CommercePriceListUserSegmentEntryRel removeByUUID_G(
		String uuid, long groupId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commercePriceListUserSegmentEntryRelId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId) {
		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns a range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByCommercePriceListId_PrevAndNext(commercePriceListUserSegmentEntryRelId,
			commercePriceListId, orderByComparator);
	}

	/**
	* Removes all the commerce price list user segment entry rels where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByC_C(commercePriceListId, commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return getPersistence()
				   .fetchByC_C(commercePriceListId, commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(commercePriceListId, commerceUserSegmentEntryId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the commerce price list user segment entry rel that was removed
	*/
	public static CommercePriceListUserSegmentEntryRel removeByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .removeByC_C(commercePriceListId, commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public static int countByC_C(long commercePriceListId,
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByC_C(commercePriceListId, commerceUserSegmentEntryId);
	}

	/**
	* Caches the commerce price list user segment entry rel in the entity cache if it is enabled.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	*/
	public static void cacheResult(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		getPersistence().cacheResult(commercePriceListUserSegmentEntryRel);
	}

	/**
	* Caches the commerce price list user segment entry rels in the entity cache if it is enabled.
	*
	* @param commercePriceListUserSegmentEntryRels the commerce price list user segment entry rels
	*/
	public static void cacheResult(
		List<CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels) {
		getPersistence().cacheResult(commercePriceListUserSegmentEntryRels);
	}

	/**
	* Creates a new commerce price list user segment entry rel with the primary key. Does not add the commerce price list user segment entry rel to the database.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key for the new commerce price list user segment entry rel
	* @return the new commerce price list user segment entry rel
	*/
	public static CommercePriceListUserSegmentEntryRel create(
		long commercePriceListUserSegmentEntryRelId) {
		return getPersistence().create(commercePriceListUserSegmentEntryRelId);
	}

	/**
	* Removes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel remove(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence().remove(commercePriceListUserSegmentEntryRelId);
	}

	public static CommercePriceListUserSegmentEntryRel updateImpl(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return getPersistence().updateImpl(commercePriceListUserSegmentEntryRel);
	}

	/**
	* Returns the commerce price list user segment entry rel with the primary key or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel findByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException {
		return getPersistence()
				   .findByPrimaryKey(commercePriceListUserSegmentEntryRelId);
	}

	/**
	* Returns the commerce price list user segment entry rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel, or <code>null</code> if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public static CommercePriceListUserSegmentEntryRel fetchByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commercePriceListUserSegmentEntryRelId);
	}

	public static java.util.Map<java.io.Serializable, CommercePriceListUserSegmentEntryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce price list user segment entry rels.
	*
	* @return the commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list user segment entry rels
	*/
	public static List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce price list user segment entry rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce price list user segment entry rels.
	*
	* @return the number of commerce price list user segment entry rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePriceListUserSegmentEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListUserSegmentEntryRelPersistence, CommercePriceListUserSegmentEntryRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListUserSegmentEntryRelPersistence.class);

		ServiceTracker<CommercePriceListUserSegmentEntryRelPersistence, CommercePriceListUserSegmentEntryRelPersistence> serviceTracker =
			new ServiceTracker<CommercePriceListUserSegmentEntryRelPersistence, CommercePriceListUserSegmentEntryRelPersistence>(bundle.getBundleContext(),
				CommercePriceListUserSegmentEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}