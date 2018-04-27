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

import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CommerceAvailabilityRange;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce availability range service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceAvailabilityRangePersistenceImpl
 * @see CommerceAvailabilityRangeUtil
 * @generated
 */
@ProviderType
public interface CommerceAvailabilityRangePersistence extends BasePersistence<CommerceAvailabilityRange> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAvailabilityRangeUtil} to access the commerce availability range persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce availability ranges
	*/
	public java.util.List<CommerceAvailabilityRange> findByUuid(String uuid);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

	/**
	* Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where uuid = &#63;.
	*
	* @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public CommerceAvailabilityRange[] findByUuid_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Removes all the commerce availability ranges where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of commerce availability ranges where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce availability ranges
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce availability range where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce availability range that was removed
	*/
	public CommerceAvailabilityRange removeByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the number of commerce availability ranges where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce availability ranges
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce availability ranges
	*/
	public java.util.List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

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
	public java.util.List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

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
	public CommerceAvailabilityRange[] findByUuid_C_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Removes all the commerce availability ranges where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of commerce availability ranges where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce availability ranges
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the commerce availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce availability ranges
	*/
	public java.util.List<CommerceAvailabilityRange> findByGroupId(long groupId);

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
	public java.util.List<CommerceAvailabilityRange> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<CommerceAvailabilityRange> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

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
	public java.util.List<CommerceAvailabilityRange> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the first commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

	/**
	* Returns the last commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range
	* @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the last commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	public CommerceAvailabilityRange fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

	/**
	* Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where groupId = &#63;.
	*
	* @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public CommerceAvailabilityRange[] findByGroupId_PrevAndNext(
		long commerceAvailabilityRangeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException;

	/**
	* Removes all the commerce availability ranges where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce availability ranges where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce availability ranges
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the commerce availability range in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityRange the commerce availability range
	*/
	public void cacheResult(CommerceAvailabilityRange commerceAvailabilityRange);

	/**
	* Caches the commerce availability ranges in the entity cache if it is enabled.
	*
	* @param commerceAvailabilityRanges the commerce availability ranges
	*/
	public void cacheResult(
		java.util.List<CommerceAvailabilityRange> commerceAvailabilityRanges);

	/**
	* Creates a new commerce availability range with the primary key. Does not add the commerce availability range to the database.
	*
	* @param commerceAvailabilityRangeId the primary key for the new commerce availability range
	* @return the new commerce availability range
	*/
	public CommerceAvailabilityRange create(long commerceAvailabilityRangeId);

	/**
	* Removes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range that was removed
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public CommerceAvailabilityRange remove(long commerceAvailabilityRangeId)
		throws NoSuchAvailabilityRangeException;

	public CommerceAvailabilityRange updateImpl(
		CommerceAvailabilityRange commerceAvailabilityRange);

	/**
	* Returns the commerce availability range with the primary key or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range
	* @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	*/
	public CommerceAvailabilityRange findByPrimaryKey(
		long commerceAvailabilityRangeId)
		throws NoSuchAvailabilityRangeException;

	/**
	* Returns the commerce availability range with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range, or <code>null</code> if a commerce availability range with the primary key could not be found
	*/
	public CommerceAvailabilityRange fetchByPrimaryKey(
		long commerceAvailabilityRangeId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceAvailabilityRange> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce availability ranges.
	*
	* @return the commerce availability ranges
	*/
	public java.util.List<CommerceAvailabilityRange> findAll();

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
	public java.util.List<CommerceAvailabilityRange> findAll(int start, int end);

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
	public java.util.List<CommerceAvailabilityRange> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator);

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
	public java.util.List<CommerceAvailabilityRange> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAvailabilityRange> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce availability ranges from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce availability ranges.
	*
	* @return the number of commerce availability ranges
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}