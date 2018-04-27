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

import com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition availability range service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CPDefinitionAvailabilityRangePersistenceImpl
 * @see CPDefinitionAvailabilityRangeUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionAvailabilityRangePersistence
	extends BasePersistence<CPDefinitionAvailabilityRange> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionAvailabilityRangeUtil} to access the cp definition availability range persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid(String uuid);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public CPDefinitionAvailabilityRange[] findByUuid_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Removes all the cp definition availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition availability ranges
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the cp definition availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition availability range that was removed
	*/
	public CPDefinitionAvailabilityRange removeByUUID_G(String uuid,
		long groupId) throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition availability ranges
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

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
	public java.util.List<CPDefinitionAvailabilityRange> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

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
	public CPDefinitionAvailabilityRange[] findByUuid_C_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Removes all the cp definition availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition availability ranges
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByCPDefinitionId(
		long CPDefinitionId)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId);

	/**
	* Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache);

	/**
	* Removes the cp definition availability range where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the cp definition availability range that was removed
	*/
	public CPDefinitionAvailabilityRange removeByCPDefinitionId(
		long CPDefinitionId)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the number of cp definition availability ranges where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition availability ranges
	*/
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	* Returns all the cp definition availability ranges where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId);

	/**
	* Returns a range of all the cp definition availability ranges where commerceAvailabilityRangeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition availability ranges where commerceAvailabilityRangeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition availability ranges where commerceAvailabilityRangeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition availability range in the ordered set where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByCommerceAvailabilityRangeId_First(
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the first cp definition availability range in the ordered set where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByCommerceAvailabilityRangeId_First(
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the last cp definition availability range in the ordered set where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange findByCommerceAvailabilityRangeId_Last(
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the last cp definition availability range in the ordered set where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByCommerceAvailabilityRangeId_Last(
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where commerceAvailabilityRangeId = &#63;.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public CPDefinitionAvailabilityRange[] findByCommerceAvailabilityRangeId_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Removes all the cp definition availability ranges where commerceAvailabilityRangeId = &#63; from the database.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	*/
	public void removeByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId);

	/**
	* Returns the number of cp definition availability ranges where commerceAvailabilityRangeId = &#63;.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the number of matching cp definition availability ranges
	*/
	public int countByCommerceAvailabilityRangeId(
		long commerceAvailabilityRangeId);

	/**
	* Caches the cp definition availability range in the entity cache if it is enabled.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	*/
	public void cacheResult(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange);

	/**
	* Caches the cp definition availability ranges in the entity cache if it is enabled.
	*
	* @param cpDefinitionAvailabilityRanges the cp definition availability ranges
	*/
	public void cacheResult(
		java.util.List<CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges);

	/**
	* Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	* @return the new cp definition availability range
	*/
	public CPDefinitionAvailabilityRange create(
		long CPDefinitionAvailabilityRangeId);

	/**
	* Removes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range that was removed
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public CPDefinitionAvailabilityRange remove(
		long CPDefinitionAvailabilityRangeId)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	public CPDefinitionAvailabilityRange updateImpl(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange);

	/**
	* Returns the cp definition availability range with the primary key or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range
	* @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	*/
	public CPDefinitionAvailabilityRange findByPrimaryKey(
		long CPDefinitionAvailabilityRangeId)
		throws NoSuchCPDefinitionAvailabilityRangeException;

	/**
	* Returns the cp definition availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range, or <code>null</code> if a cp definition availability range with the primary key could not be found
	*/
	public CPDefinitionAvailabilityRange fetchByPrimaryKey(
		long CPDefinitionAvailabilityRangeId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition availability ranges.
	*
	* @return the cp definition availability ranges
	*/
	public java.util.List<CPDefinitionAvailabilityRange> findAll();

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
	public java.util.List<CPDefinitionAvailabilityRange> findAll(int start,
		int end);

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
	public java.util.List<CPDefinitionAvailabilityRange> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

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
	public java.util.List<CPDefinitionAvailabilityRange> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition availability ranges from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition availability ranges.
	*
	* @return the number of cp definition availability ranges
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}