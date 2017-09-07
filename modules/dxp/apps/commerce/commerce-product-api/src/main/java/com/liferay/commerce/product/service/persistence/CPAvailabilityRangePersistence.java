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

import com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException;
import com.liferay.commerce.product.model.CPAvailabilityRange;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp availability range service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPAvailabilityRangePersistenceImpl
 * @see CPAvailabilityRangeUtil
 * @generated
 */
@ProviderType
public interface CPAvailabilityRangePersistence extends BasePersistence<CPAvailabilityRange> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPAvailabilityRangeUtil} to access the cp availability range persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp availability ranges
	*/
	public java.util.List<CPAvailabilityRange> findByUuid(java.lang.String uuid);

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
	public java.util.List<CPAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end);

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
	public java.util.List<CPAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

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
	public java.util.List<CPAvailabilityRange> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

	/**
	* Returns the cp availability ranges before and after the current cp availability range in the ordered set where uuid = &#63;.
	*
	* @param CPAvailabilityRangeId the primary key of the current cp availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public CPAvailabilityRange[] findByUuid_PrevAndNext(
		long CPAvailabilityRangeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Removes all the cp availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp availability ranges
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the cp availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp availability range that was removed
	*/
	public CPAvailabilityRange removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the number of cp availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp availability ranges
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp availability ranges
	*/
	public java.util.List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

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
	public java.util.List<CPAvailabilityRange> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the first cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the last cp availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

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
	public CPAvailabilityRange[] findByUuid_C_PrevAndNext(
		long CPAvailabilityRangeId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Removes all the cp availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp availability ranges
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the cp availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp availability ranges
	*/
	public java.util.List<CPAvailabilityRange> findByGroupId(long groupId);

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
	public java.util.List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

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
	public java.util.List<CPAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the first cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the last cp availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp availability range, or <code>null</code> if a matching cp availability range could not be found
	*/
	public CPAvailabilityRange fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

	/**
	* Returns the cp availability ranges before and after the current cp availability range in the ordered set where groupId = &#63;.
	*
	* @param CPAvailabilityRangeId the primary key of the current cp availability range
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public CPAvailabilityRange[] findByGroupId_PrevAndNext(
		long CPAvailabilityRangeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Removes all the cp availability ranges where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of cp availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp availability ranges
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the cp availability range in the entity cache if it is enabled.
	*
	* @param cpAvailabilityRange the cp availability range
	*/
	public void cacheResult(CPAvailabilityRange cpAvailabilityRange);

	/**
	* Caches the cp availability ranges in the entity cache if it is enabled.
	*
	* @param cpAvailabilityRanges the cp availability ranges
	*/
	public void cacheResult(
		java.util.List<CPAvailabilityRange> cpAvailabilityRanges);

	/**
	* Creates a new cp availability range with the primary key. Does not add the cp availability range to the database.
	*
	* @param CPAvailabilityRangeId the primary key for the new cp availability range
	* @return the new cp availability range
	*/
	public CPAvailabilityRange create(long CPAvailabilityRangeId);

	/**
	* Removes the cp availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range that was removed
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public CPAvailabilityRange remove(long CPAvailabilityRangeId)
		throws NoSuchCPAvailabilityRangeException;

	public CPAvailabilityRange updateImpl(
		CPAvailabilityRange cpAvailabilityRange);

	/**
	* Returns the cp availability range with the primary key or throws a {@link NoSuchCPAvailabilityRangeException} if it could not be found.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range
	* @throws NoSuchCPAvailabilityRangeException if a cp availability range with the primary key could not be found
	*/
	public CPAvailabilityRange findByPrimaryKey(long CPAvailabilityRangeId)
		throws NoSuchCPAvailabilityRangeException;

	/**
	* Returns the cp availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPAvailabilityRangeId the primary key of the cp availability range
	* @return the cp availability range, or <code>null</code> if a cp availability range with the primary key could not be found
	*/
	public CPAvailabilityRange fetchByPrimaryKey(long CPAvailabilityRangeId);

	@Override
	public java.util.Map<java.io.Serializable, CPAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp availability ranges.
	*
	* @return the cp availability ranges
	*/
	public java.util.List<CPAvailabilityRange> findAll();

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
	public java.util.List<CPAvailabilityRange> findAll(int start, int end);

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
	public java.util.List<CPAvailabilityRange> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator);

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
	public java.util.List<CPAvailabilityRange> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp availability ranges from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp availability ranges.
	*
	* @return the number of cp availability ranges
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}