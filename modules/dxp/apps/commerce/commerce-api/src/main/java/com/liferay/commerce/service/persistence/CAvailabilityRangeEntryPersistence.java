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

import com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException;
import com.liferay.commerce.model.CAvailabilityRangeEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the c availability range entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CAvailabilityRangeEntryPersistenceImpl
 * @see CAvailabilityRangeEntryUtil
 * @generated
 */
@ProviderType
public interface CAvailabilityRangeEntryPersistence extends BasePersistence<CAvailabilityRangeEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CAvailabilityRangeEntryUtil} to access the c availability range entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the c availability range entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry[] findByUuid_PrevAndNext(
		long CAvailabilityRangeEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Removes all the c availability range entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of c availability range entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c availability range entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the c availability range entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c availability range entry that was removed
	*/
	public CAvailabilityRangeEntry removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the number of c availability range entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c availability range entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry[] findByUuid_C_PrevAndNext(
		long CAvailabilityRangeEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Removes all the c availability range entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of c availability range entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c availability range entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the c availability range entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the c availability range entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the first c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the last c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the last c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the c availability range entries before and after the current c availability range entry in the ordered set where groupId = &#63;.
	*
	* @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry[] findByGroupId_PrevAndNext(
		long CAvailabilityRangeEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Removes all the c availability range entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of c availability range entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching c availability range entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByG_C(long groupId, long CPDefinitionId)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByG_C(long groupId, long CPDefinitionId);

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByG_C(long groupId,
		long CPDefinitionId, boolean retrieveFromCache);

	/**
	* Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the c availability range entry that was removed
	*/
	public CAvailabilityRangeEntry removeByG_C(long groupId, long CPDefinitionId)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63;.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching c availability range entries
	*/
	public int countByG_C(long groupId, long CPDefinitionId);

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the matching c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry findByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId);

	/**
	* Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	public CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId,
		boolean retrieveFromCache);

	/**
	* Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the c availability range entry that was removed
	*/
	public CAvailabilityRangeEntry removeByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63;.
	*
	* @param groupId the group ID
	* @param CPDefinitionId the cp definition ID
	* @param commerceAvailabilityRangeId the commerce availability range ID
	* @return the number of matching c availability range entries
	*/
	public int countByG_C_C(long groupId, long CPDefinitionId,
		long commerceAvailabilityRangeId);

	/**
	* Caches the c availability range entry in the entity cache if it is enabled.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	*/
	public void cacheResult(CAvailabilityRangeEntry cAvailabilityRangeEntry);

	/**
	* Caches the c availability range entries in the entity cache if it is enabled.
	*
	* @param cAvailabilityRangeEntries the c availability range entries
	*/
	public void cacheResult(
		java.util.List<CAvailabilityRangeEntry> cAvailabilityRangeEntries);

	/**
	* Creates a new c availability range entry with the primary key. Does not add the c availability range entry to the database.
	*
	* @param CAvailabilityRangeEntryId the primary key for the new c availability range entry
	* @return the new c availability range entry
	*/
	public CAvailabilityRangeEntry create(long CAvailabilityRangeEntryId);

	/**
	* Removes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry that was removed
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry remove(long CAvailabilityRangeEntryId)
		throws NoSuchCAvailabilityRangeEntryException;

	public CAvailabilityRangeEntry updateImpl(
		CAvailabilityRangeEntry cAvailabilityRangeEntry);

	/**
	* Returns the c availability range entry with the primary key or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry
	* @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry findByPrimaryKey(
		long CAvailabilityRangeEntryId)
		throws NoSuchCAvailabilityRangeEntryException;

	/**
	* Returns the c availability range entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry, or <code>null</code> if a c availability range entry with the primary key could not be found
	*/
	public CAvailabilityRangeEntry fetchByPrimaryKey(
		long CAvailabilityRangeEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CAvailabilityRangeEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the c availability range entries.
	*
	* @return the c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findAll();

	/**
	* Returns a range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c availability range entries
	*/
	public java.util.List<CAvailabilityRangeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the c availability range entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of c availability range entries.
	*
	* @return the number of c availability range entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}