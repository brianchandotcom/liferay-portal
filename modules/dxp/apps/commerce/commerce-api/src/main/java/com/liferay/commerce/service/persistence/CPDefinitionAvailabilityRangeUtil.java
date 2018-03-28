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

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition availability range service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CPDefinitionAvailabilityRangePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangePersistence
 * @see com.liferay.commerce.service.persistence.impl.CPDefinitionAvailabilityRangePersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeUtil {
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
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		getPersistence().clearCache(cpDefinitionAvailabilityRange);
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
	public static List<CPDefinitionAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionAvailabilityRange update(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return getPersistence().update(cpDefinitionAvailabilityRange);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionAvailabilityRange update(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(cpDefinitionAvailabilityRange, serviceContext);
	}

	/**
	* Returns all the cp definition availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange[] findByUuid_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, java.lang.String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionAvailabilityRangeId,
			uuid, orderByComparator);
	}

	/**
	* Removes all the cp definition availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition availability ranges
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition availability range that was removed
	*/
	public static CPDefinitionAvailabilityRange removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition availability ranges
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange[] findByUuid_C_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionAvailabilityRangeId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition availability ranges
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByGroupId(
		long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp definition availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByGroupId(
		long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp definition availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByGroupId_Last(
		long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange[] findByGroupId_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPDefinitionAvailabilityRangeId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the cp definition availability ranges where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp definition availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definition availability ranges
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange findByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId(CPDefinitionId, retrieveFromCache);
	}

	/**
	* Removes the cp definition availability range where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition availability range that was removed
	*/
	public static CPDefinitionAvailabilityRange removeByCPDefinitionId(
		long CPDefinitionId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition availability ranges where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition availability ranges
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Caches the cp definition availability range in the entity cache if it is enabled.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	*/
	public static void cacheResult(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		getPersistence().cacheResult(cpDefinitionAvailabilityRange);
	}

	/**
	* Caches the cp definition availability ranges in the entity cache if it is enabled.
	*
	* @param cpDefinitionAvailabilityRanges the cp definition availability ranges
	*/
	public static void cacheResult(
		List<CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges) {
		getPersistence().cacheResult(cpDefinitionAvailabilityRanges);
	}

	/**
	* Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	* @return the new cp definition availability range
	*/
	public static CPDefinitionAvailabilityRange create(
		long CPDefinitionAvailabilityRangeId) {
		return getPersistence().create(CPDefinitionAvailabilityRangeId);
	}

	/**
	* Removes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range that was removed
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange remove(
		long CPDefinitionAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().remove(CPDefinitionAvailabilityRangeId);
	}

	public static CPDefinitionAvailabilityRange updateImpl(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return getPersistence().updateImpl(cpDefinitionAvailabilityRange);
	}

	/**
	* Returns the cp definition availability range with the primary key or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange findByPrimaryKey(
		long CPDefinitionAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException {
		return getPersistence().findByPrimaryKey(CPDefinitionAvailabilityRangeId);
	}

	/**
	* Returns the cp definition availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range, or <code>null</code> if a cp definition availability range with the primary key could not be found
	*/
	public static CPDefinitionAvailabilityRange fetchByPrimaryKey(
		long CPDefinitionAvailabilityRangeId) {
		return getPersistence()
				   .fetchByPrimaryKey(CPDefinitionAvailabilityRangeId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition availability ranges.
	*
	* @return the cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findAll(int start,
		int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition availability ranges
	*/
	public static List<CPDefinitionAvailabilityRange> findAll(int start,
		int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition availability ranges from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition availability ranges.
	*
	* @return the number of cp definition availability ranges
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionAvailabilityRangePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionAvailabilityRangePersistence, CPDefinitionAvailabilityRangePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionAvailabilityRangePersistence.class);

		ServiceTracker<CPDefinitionAvailabilityRangePersistence, CPDefinitionAvailabilityRangePersistence> serviceTracker =
			new ServiceTracker<CPDefinitionAvailabilityRangePersistence, CPDefinitionAvailabilityRangePersistence>(bundle.getBundleContext(),
				CPDefinitionAvailabilityRangePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}