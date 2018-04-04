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

import com.liferay.commerce.product.model.CPMeasurementUnit;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp measurement unit service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPMeasurementUnitPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPMeasurementUnitPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPMeasurementUnitPersistenceImpl
 * @generated
 */
@ProviderType
public class CPMeasurementUnitUtil {
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
	public static void clearCache(CPMeasurementUnit cpMeasurementUnit) {
		getPersistence().clearCache(cpMeasurementUnit);
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
	public static List<CPMeasurementUnit> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPMeasurementUnit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPMeasurementUnit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPMeasurementUnit update(CPMeasurementUnit cpMeasurementUnit) {
		return getPersistence().update(cpMeasurementUnit);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPMeasurementUnit update(
		CPMeasurementUnit cpMeasurementUnit, ServiceContext serviceContext) {
		return getPersistence().update(cpMeasurementUnit, serviceContext);
	}

	/**
	* Returns all the cp measurement units where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp measurement units where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit[] findByUuid_PrevAndNext(
		long CPMeasurementUnitId, java.lang.String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPMeasurementUnitId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp measurement units where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp measurement units where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp measurement units
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp measurement unit where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp measurement unit that was removed
	*/
	public static CPMeasurementUnit removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp measurement units where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp measurement units
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit[] findByUuid_C_PrevAndNext(
		long CPMeasurementUnitId, java.lang.String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPMeasurementUnitId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp measurement units where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp measurement units
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp measurement units where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp measurement units where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByGroupId_First(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByGroupId_First(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByGroupId_Last(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByGroupId_Last(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit[] findByGroupId_PrevAndNext(
		long CPMeasurementUnitId, long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPMeasurementUnitId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the cp measurement units where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp measurement units where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp measurement units
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cp measurement units where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_T(long groupId, int type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	* Returns a range of all the cp measurement units where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end) {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByG_T_First(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_T_First(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByG_T_Last(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_T_Last(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().fetchByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit[] findByG_T_PrevAndNext(
		long CPMeasurementUnitId, long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByG_T_PrevAndNext(CPMeasurementUnitId, groupId, type,
			orderByComparator);
	}

	/**
	* Removes all the cp measurement units where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public static void removeByG_T(long groupId, int type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	* Returns the number of cp measurement units where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public static int countByG_T(long groupId, int type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByG_K_T(long groupId,
		java.lang.String key, int type)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByG_K_T(groupId, key, type);
	}

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_K_T(long groupId,
		java.lang.String key, int type) {
		return getPersistence().fetchByG_K_T(groupId, key, type);
	}

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_K_T(long groupId,
		java.lang.String key, int type, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_K_T(groupId, key, type, retrieveFromCache);
	}

	/**
	* Removes the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the cp measurement unit that was removed
	*/
	public static CPMeasurementUnit removeByG_K_T(long groupId,
		java.lang.String key, int type)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().removeByG_K_T(groupId, key, type);
	}

	/**
	* Returns the number of cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public static int countByG_K_T(long groupId, java.lang.String key, int type) {
		return getPersistence().countByG_K_T(groupId, key, type);
	}

	/**
	* Returns all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @return the matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type) {
		return getPersistence().findByG_P_T(groupId, primary, type);
	}

	/**
	* Returns a range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end) {
		return getPersistence().findByG_P_T(groupId, primary, type, start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .findByG_P_T(groupId, primary, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp measurement units
	*/
	public static List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_T(groupId, primary, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByG_P_T_First(long groupId,
		boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByG_P_T_First(groupId, primary, type, orderByComparator);
	}

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_P_T_First(long groupId,
		boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_T_First(groupId, primary, type, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit findByG_P_T_Last(long groupId,
		boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByG_P_T_Last(groupId, primary, type, orderByComparator);
	}

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public static CPMeasurementUnit fetchByG_P_T_Last(long groupId,
		boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_T_Last(groupId, primary, type, orderByComparator);
	}

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit[] findByG_P_T_PrevAndNext(
		long CPMeasurementUnitId, long groupId, boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence()
				   .findByG_P_T_PrevAndNext(CPMeasurementUnitId, groupId,
			primary, type, orderByComparator);
	}

	/**
	* Removes all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	*/
	public static void removeByG_P_T(long groupId, boolean primary, int type) {
		getPersistence().removeByG_P_T(groupId, primary, type);
	}

	/**
	* Returns the number of cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public static int countByG_P_T(long groupId, boolean primary, int type) {
		return getPersistence().countByG_P_T(groupId, primary, type);
	}

	/**
	* Caches the cp measurement unit in the entity cache if it is enabled.
	*
	* @param cpMeasurementUnit the cp measurement unit
	*/
	public static void cacheResult(CPMeasurementUnit cpMeasurementUnit) {
		getPersistence().cacheResult(cpMeasurementUnit);
	}

	/**
	* Caches the cp measurement units in the entity cache if it is enabled.
	*
	* @param cpMeasurementUnits the cp measurement units
	*/
	public static void cacheResult(List<CPMeasurementUnit> cpMeasurementUnits) {
		getPersistence().cacheResult(cpMeasurementUnits);
	}

	/**
	* Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	*
	* @param CPMeasurementUnitId the primary key for the new cp measurement unit
	* @return the new cp measurement unit
	*/
	public static CPMeasurementUnit create(long CPMeasurementUnitId) {
		return getPersistence().create(CPMeasurementUnitId);
	}

	/**
	* Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit that was removed
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit remove(long CPMeasurementUnitId)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().remove(CPMeasurementUnitId);
	}

	public static CPMeasurementUnit updateImpl(
		CPMeasurementUnit cpMeasurementUnit) {
		return getPersistence().updateImpl(cpMeasurementUnit);
	}

	/**
	* Returns the cp measurement unit with the primary key or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit findByPrimaryKey(long CPMeasurementUnitId)
		throws com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException {
		return getPersistence().findByPrimaryKey(CPMeasurementUnitId);
	}

	/**
	* Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	*/
	public static CPMeasurementUnit fetchByPrimaryKey(long CPMeasurementUnitId) {
		return getPersistence().fetchByPrimaryKey(CPMeasurementUnitId);
	}

	public static java.util.Map<java.io.Serializable, CPMeasurementUnit> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp measurement units.
	*
	* @return the cp measurement units
	*/
	public static List<CPMeasurementUnit> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp measurement units.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of cp measurement units
	*/
	public static List<CPMeasurementUnit> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp measurement units.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp measurement units
	*/
	public static List<CPMeasurementUnit> findAll(int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp measurement units.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp measurement units
	*/
	public static List<CPMeasurementUnit> findAll(int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp measurement units from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp measurement units.
	*
	* @return the number of cp measurement units
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPMeasurementUnitPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPMeasurementUnitPersistence, CPMeasurementUnitPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPMeasurementUnitPersistence.class);

		ServiceTracker<CPMeasurementUnitPersistence, CPMeasurementUnitPersistence> serviceTracker =
			new ServiceTracker<CPMeasurementUnitPersistence, CPMeasurementUnitPersistence>(bundle.getBundleContext(),
				CPMeasurementUnitPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}