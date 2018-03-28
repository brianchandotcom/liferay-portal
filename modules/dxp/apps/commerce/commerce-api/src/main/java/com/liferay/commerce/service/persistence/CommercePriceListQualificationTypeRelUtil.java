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

import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce price list qualification type rel service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommercePriceListQualificationTypeRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommercePriceListQualificationTypeRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelUtil {
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
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		getPersistence().clearCache(commercePriceListQualificationTypeRel);
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
	public static List<CommercePriceListQualificationTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListQualificationTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListQualificationTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListQualificationTypeRel update(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return getPersistence().update(commercePriceListQualificationTypeRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListQualificationTypeRel update(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commercePriceListQualificationTypeRel, serviceContext);
	}

	/**
	* Returns all the commerce price list qualification type rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel[] findByUuid_PrevAndNext(
		long commercePriceListQualificationTypeRelId, java.lang.String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commercePriceListQualificationTypeRelId,
			uuid, orderByComparator);
	}

	/**
	* Removes all the commerce price list qualification type rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list qualification type rels
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list qualification type rel that was removed
	*/
	public static CommercePriceListQualificationTypeRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel[] findByUuid_C_PrevAndNext(
		long commercePriceListQualificationTypeRelId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commercePriceListQualificationTypeRelId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId) {
		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns a range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListQualificationTypeRelId, long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByCommercePriceListId_PrevAndNext(commercePriceListQualificationTypeRelId,
			commercePriceListId, orderByComparator);
	}

	/**
	* Removes all the commerce price list qualification type rels where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the number of commerce price list qualification type rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByC_C(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return getPersistence()
				   .fetchByC_C(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(commercePriceListQualificationType,
			commercePriceListId, retrieveFromCache);
	}

	/**
	* Removes the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the commerce price list qualification type rel that was removed
	*/
	public static CommercePriceListQualificationTypeRel removeByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .removeByC_C(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Returns the number of commerce price list qualification type rels where commercePriceListQualificationType = &#63; and commercePriceListId = &#63;.
	*
	* @param commercePriceListQualificationType the commerce price list qualification type
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list qualification type rels
	*/
	public static int countByC_C(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return getPersistence()
				   .countByC_C(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Caches the commerce price list qualification type rel in the entity cache if it is enabled.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	*/
	public static void cacheResult(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		getPersistence().cacheResult(commercePriceListQualificationTypeRel);
	}

	/**
	* Caches the commerce price list qualification type rels in the entity cache if it is enabled.
	*
	* @param commercePriceListQualificationTypeRels the commerce price list qualification type rels
	*/
	public static void cacheResult(
		List<CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels) {
		getPersistence().cacheResult(commercePriceListQualificationTypeRels);
	}

	/**
	* Creates a new commerce price list qualification type rel with the primary key. Does not add the commerce price list qualification type rel to the database.
	*
	* @param commercePriceListQualificationTypeRelId the primary key for the new commerce price list qualification type rel
	* @return the new commerce price list qualification type rel
	*/
	public static CommercePriceListQualificationTypeRel create(
		long commercePriceListQualificationTypeRelId) {
		return getPersistence().create(commercePriceListQualificationTypeRelId);
	}

	/**
	* Removes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel remove(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence().remove(commercePriceListQualificationTypeRelId);
	}

	public static CommercePriceListQualificationTypeRel updateImpl(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return getPersistence().updateImpl(commercePriceListQualificationTypeRel);
	}

	/**
	* Returns the commerce price list qualification type rel with the primary key or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel
	* @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel findByPrimaryKey(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException {
		return getPersistence()
				   .findByPrimaryKey(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns the commerce price list qualification type rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel, or <code>null</code> if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static CommercePriceListQualificationTypeRel fetchByPrimaryKey(
		long commercePriceListQualificationTypeRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commercePriceListQualificationTypeRelId);
	}

	public static java.util.Map<java.io.Serializable, CommercePriceListQualificationTypeRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce price list qualification type rels.
	*
	* @return the commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list qualification type rels
	*/
	public static List<CommercePriceListQualificationTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce price list qualification type rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce price list qualification type rels.
	*
	* @return the number of commerce price list qualification type rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePriceListQualificationTypeRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListQualificationTypeRelPersistence, CommercePriceListQualificationTypeRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListQualificationTypeRelPersistence.class);

		ServiceTracker<CommercePriceListQualificationTypeRelPersistence, CommercePriceListQualificationTypeRelPersistence> serviceTracker =
			new ServiceTracker<CommercePriceListQualificationTypeRelPersistence, CommercePriceListQualificationTypeRelPersistence>(bundle.getBundleContext(),
				CommercePriceListQualificationTypeRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}