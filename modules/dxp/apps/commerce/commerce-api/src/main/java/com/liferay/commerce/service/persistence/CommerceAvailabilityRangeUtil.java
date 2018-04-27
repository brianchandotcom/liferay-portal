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

import com.liferay.commerce.model.CommerceAvailabilityRange;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce availability range service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceAvailabilityRangePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangePersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceAvailabilityRangePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeUtil {
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
		CommerceAvailabilityRange commerceAvailabilityRange) {
		getPersistence().clearCache(commerceAvailabilityRange);
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
	public static List<CommerceAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceAvailabilityRange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceAvailabilityRange update(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		return getPersistence().update(commerceAvailabilityRange);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceAvailabilityRange update(
		CommerceAvailabilityRange commerceAvailabilityRange,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceAvailabilityRange, serviceContext);
	}

	/**
	* Returns all the commerce availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange[] findByUuid_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceAvailabilityRangeId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce availability ranges
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUUID_G(String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce availability range that was removed
	*/
	public static CommerceAvailabilityRange removeByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce availability ranges
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange[] findByUuid_C_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceAvailabilityRangeId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce availability ranges
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange findByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public static CommerceAvailabilityRange fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange[] findByGroupId_PrevAndNext(
		long commerceAvailabilityRangeId, long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceAvailabilityRangeId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce availability ranges where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce availability ranges
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the commerce availability range in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityRange the commerce availability range
	*/
	public static void cacheResult(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		getPersistence().cacheResult(commerceAvailabilityRange);
	}

	/**
	* Caches the commerce availability ranges in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityRanges the commerce availability ranges
	*/
	public static void cacheResult(
		List<CommerceAvailabilityRange> commerceAvailabilityRanges) {
		getPersistence().cacheResult(commerceAvailabilityRanges);
	}

	/**
	* Creates a new commerce availability range with the primary key. Does not add the commerce availability range to the database.
	*
	* @param commerceAvailabilityRangeId the primary key for the new commerce availability range
	* @return the new commerce availability range
	*/
	public static CommerceAvailabilityRange create(
		long commerceAvailabilityRangeId) {
		return getPersistence().create(commerceAvailabilityRangeId);
	}

	/**
	* Removes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range that was removed
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange remove(
		long commerceAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().remove(commerceAvailabilityRangeId);
	}

	public static CommerceAvailabilityRange updateImpl(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		return getPersistence().updateImpl(commerceAvailabilityRange);
	}

	/**
	* Returns the commerce availability range with the primary key or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange findByPrimaryKey(
		long commerceAvailabilityRangeId)
		throws com.liferay.commerce.exception.NoSuchAvailabilityRangeException {
		return getPersistence().findByPrimaryKey(commerceAvailabilityRangeId);
	}

	/**
	* Returns the commerce availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range, or <code>null</code> if a commerce availability range with the primary key could not be found
	*/
	public static CommerceAvailabilityRange fetchByPrimaryKey(
		long commerceAvailabilityRangeId) {
		return getPersistence().fetchByPrimaryKey(commerceAvailabilityRangeId);
	}

	public static java.util.Map<java.io.Serializable, CommerceAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce availability ranges.
	*
	* @return the commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce availability ranges
	*/
	public static List<CommerceAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce availability ranges from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce availability ranges.
	*
	* @return the number of commerce availability ranges
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceAvailabilityRangePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAvailabilityRangePersistence, CommerceAvailabilityRangePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAvailabilityRangePersistence.class);

		ServiceTracker<CommerceAvailabilityRangePersistence, CommerceAvailabilityRangePersistence> serviceTracker =
			new ServiceTracker<CommerceAvailabilityRangePersistence, CommerceAvailabilityRangePersistence>(bundle.getBundleContext(),
				CommerceAvailabilityRangePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}