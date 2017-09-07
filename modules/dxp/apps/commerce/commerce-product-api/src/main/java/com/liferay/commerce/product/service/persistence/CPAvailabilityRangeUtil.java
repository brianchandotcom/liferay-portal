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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPAvailabilityRange;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp availability range service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPAvailabilityRangePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPAvailabilityRangePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPAvailabilityRangePersistenceImpl
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeUtil {
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
	public static void clearCache(CPAvailabilityRange cpAvailabilityRange) {
		getPersistence().clearCache(cpAvailabilityRange);
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
	public static List<CPAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPAvailabilityRange update(
		CPAvailabilityRange cpAvailabilityRange) {
		return getPersistence().update(cpAvailabilityRange);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPAvailabilityRange update(
		CPAvailabilityRange cpAvailabilityRange, ServiceContext serviceContext) {
		return getPersistence().update(cpAvailabilityRange, serviceContext);
	}

	/**
	* Returns all the cp availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @return the range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp availability ranges before and after the current cp availability range in the ordered set where uuid = &#63;.
	*
	* @param CPAvailabilityRangeId the primary key of the current cp availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange[] findByUuid_PrevAndNext(
		long CPAvailabilityRangeId, java.lang.String uuid,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPAvailabilityRangeId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp availability ranges
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp availability range that was removed
	*/
	public static CPAvailabilityRange removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp availability ranges
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @return the range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp availability ranges before and after the current cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPAvailabilityRangeId the primary key of the current cp availability range
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange[] findByUuid_C_PrevAndNext(
		long CPAvailabilityRangeId, java.lang.String uuid, long companyId,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPAvailabilityRangeId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp availability ranges
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @return the range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp availability ranges
	*/
	public static List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByGroupId_First(long groupId,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByGroupId_First(long groupId,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange findByGroupId_Last(long groupId,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public static CPAvailabilityRange fetchByGroupId_Last(long groupId,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp availability ranges before and after the current cp availability range in the ordered set where groupId = &#63;.
	*
	* @param CPAvailabilityRangeId the primary key of the current cp availability range
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange[] findByGroupId_PrevAndNext(
		long CPAvailabilityRangeId, long groupId,
		OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPAvailabilityRangeId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp availability ranges where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp availability ranges
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the cp availability range in the entity cache if it is enabled.
	*
	* @param cpAvailabilityRange the cp availability range
	*/
	public static void cacheResult(CPAvailabilityRange cpAvailabilityRange) {
		getPersistence().cacheResult(cpAvailabilityRange);
	}

	/**
	* Caches the cp availability ranges in the entity cache if it is enabled.
	*
	* @param cpAvailabilityRanges the cp availability ranges
	*/
	public static void cacheResult(
		List<CPAvailabilityRange> cpAvailabilityRanges) {
		getPersistence().cacheResult(cpAvailabilityRanges);
	}

	/**
	* Creates a new cp availability range with the primary key. Does not add the cp availability range to the database.
	*
	* @param CPAvailabilityRangeId the primary key for the new cp availability range
	* @return the new cp availability range
	*/
	public static CPAvailabilityRange create(long CPAvailabilityRangeId) {
		return getPersistence().create(CPAvailabilityRangeId);
	}

	/**
	* Removes the cp availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range that was removed
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange remove(long CPAvailabilityRangeId)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().remove(CPAvailabilityRangeId);
	}

	public static CPAvailabilityRange updateImpl(
		CPAvailabilityRange cpAvailabilityRange) {
		return getPersistence().updateImpl(cpAvailabilityRange);
	}

	/**
	* Returns the cp availability range with the primary key or throws a {@link NoSuchCPAvailabilityRangeException} if it could not be found.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange findByPrimaryKey(
		long CPAvailabilityRangeId)
		throws com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException {
		return getPersistence().findByPrimaryKey(CPAvailabilityRangeId);
	}

	/**
	* Returns the cp availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range, or <code>null</code> if a cp availability range with the primary key could not be found
	*/
	public static CPAvailabilityRange fetchByPrimaryKey(
		long CPAvailabilityRangeId) {
		return getPersistence().fetchByPrimaryKey(CPAvailabilityRangeId);
	}

	public static java.util.Map<java.io.Serializable, CPAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp availability ranges.
	*
	* @return the cp availability ranges
	*/
	public static List<CPAvailabilityRange> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @return the range of cp availability ranges
	*/
	public static List<CPAvailabilityRange> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp availability ranges
	*/
	public static List<CPAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp availability ranges
	* @param end the upper bound of the range of cp availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp availability ranges
	*/
	public static List<CPAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp availability ranges from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp availability ranges.
	*
	* @return the number of cp availability ranges
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPAvailabilityRangePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPAvailabilityRangePersistence, CPAvailabilityRangePersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPAvailabilityRangePersistence.class);
}