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

import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp measurement unit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPMeasurementUnitPersistenceImpl
 * @see CPMeasurementUnitUtil
 * @generated
 */
@ProviderType
public interface CPMeasurementUnitPersistence extends BasePersistence<CPMeasurementUnit> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMeasurementUnitUtil} to access the cp measurement unit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp measurement units where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findByUuid(java.lang.String uuid);

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
	public java.util.List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end);

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
	public java.util.List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where uuid = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public CPMeasurementUnit[] findByUuid_PrevAndNext(
		long CPMeasurementUnitId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Removes all the cp measurement units where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp measurement units where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp measurement units
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp measurement unit where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp measurement unit that was removed
	*/
	public CPMeasurementUnit removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the number of cp measurement units where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp measurement units
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<CPMeasurementUnit> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<CPMeasurementUnit> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public CPMeasurementUnit[] findByUuid_C_PrevAndNext(
		long CPMeasurementUnitId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Removes all the cp measurement units where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp measurement units where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp measurement units
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp measurement units where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findByGroupId(long groupId);

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
	public java.util.List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63;.
	*
	* @param CPMeasurementUnitId the primary key of the current cp measurement unit
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public CPMeasurementUnit[] findByGroupId_PrevAndNext(
		long CPMeasurementUnitId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Removes all the cp measurement units where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp measurement units where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp measurement units
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the cp measurement units where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findByG_T(long groupId, int type);

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
	public java.util.List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end);

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
	public java.util.List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public CPMeasurementUnit[] findByG_T_PrevAndNext(long CPMeasurementUnitId,
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Removes all the cp measurement units where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, int type);

	/**
	* Returns the number of cp measurement units where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public int countByG_T(long groupId, int type);

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the matching cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit findByG_K_T(long groupId, java.lang.String key,
		int type) throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_K_T(long groupId, java.lang.String key,
		int type);

	/**
	* Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_K_T(long groupId, java.lang.String key,
		int type, boolean retrieveFromCache);

	/**
	* Removes the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the cp measurement unit that was removed
	*/
	public CPMeasurementUnit removeByG_K_T(long groupId, java.lang.String key,
		int type) throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the number of cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public int countByG_K_T(long groupId, java.lang.String key, int type);

	/**
	* Returns all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @return the matching cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type);

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
	public java.util.List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end);

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
	public java.util.List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findByG_P_T(long groupId,
		boolean primary, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

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
	public CPMeasurementUnit findByG_P_T_First(long groupId, boolean primary,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the first cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_P_T_First(long groupId, boolean primary,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public CPMeasurementUnit findByG_P_T_Last(long groupId, boolean primary,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the last cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	public CPMeasurementUnit fetchByG_P_T_Last(long groupId, boolean primary,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public CPMeasurementUnit[] findByG_P_T_PrevAndNext(
		long CPMeasurementUnitId, long groupId, boolean primary, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Removes all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	*/
	public void removeByG_P_T(long groupId, boolean primary, int type);

	/**
	* Returns the number of cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param type the type
	* @return the number of matching cp measurement units
	*/
	public int countByG_P_T(long groupId, boolean primary, int type);

	/**
	* Caches the cp measurement unit in the entity cache if it is enabled.
	*
	* @param cpMeasurementUnit the cp measurement unit
	*/
	public void cacheResult(CPMeasurementUnit cpMeasurementUnit);

	/**
	* Caches the cp measurement units in the entity cache if it is enabled.
	*
	* @param cpMeasurementUnits the cp measurement units
	*/
	public void cacheResult(
		java.util.List<CPMeasurementUnit> cpMeasurementUnits);

	/**
	* Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	*
	* @param CPMeasurementUnitId the primary key for the new cp measurement unit
	* @return the new cp measurement unit
	*/
	public CPMeasurementUnit create(long CPMeasurementUnitId);

	/**
	* Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit that was removed
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public CPMeasurementUnit remove(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException;

	public CPMeasurementUnit updateImpl(CPMeasurementUnit cpMeasurementUnit);

	/**
	* Returns the cp measurement unit with the primary key or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit
	* @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	*/
	public CPMeasurementUnit findByPrimaryKey(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException;

	/**
	* Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	*/
	public CPMeasurementUnit fetchByPrimaryKey(long CPMeasurementUnitId);

	@Override
	public java.util.Map<java.io.Serializable, CPMeasurementUnit> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp measurement units.
	*
	* @return the cp measurement units
	*/
	public java.util.List<CPMeasurementUnit> findAll();

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
	public java.util.List<CPMeasurementUnit> findAll(int start, int end);

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
	public java.util.List<CPMeasurementUnit> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator);

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
	public java.util.List<CPMeasurementUnit> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp measurement units from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp measurement units.
	*
	* @return the number of cp measurement units
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}