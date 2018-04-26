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

package com.liferay.adaptive.media.image.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.exception.NoSuchAMImageEntryException;
import com.liferay.adaptive.media.image.model.AMImageEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the am image entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.adaptive.media.image.service.persistence.impl.AMImageEntryPersistenceImpl
 * @see AMImageEntryUtil
 * @generated
 */
@ProviderType
public interface AMImageEntryPersistence extends BasePersistence<AMImageEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AMImageEntryUtil} to access the am image entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the am image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the am image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the am image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByUuid_PrevAndNext(long amImageEntryId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of am image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching am image entries
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the am image entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the am image entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the am image entry that was removed
	*/
	public AMImageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the number of am image entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching am image entries
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the am image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid_C(String uuid, long companyId);

	/**
	* Returns a range of all the am image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByUuid_C_PrevAndNext(long amImageEntryId,
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of am image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching am image entries
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the am image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the am image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the am image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where groupId = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByGroupId_PrevAndNext(long amImageEntryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of am image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching am image entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the am image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the am image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the am image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByCompanyId_PrevAndNext(long amImageEntryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of am image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching am image entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the am image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid);

	/**
	* Returns a range of all the am image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end);

	/**
	* Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByConfigurationUuid_First(
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByConfigurationUuid_First(
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByConfigurationUuid_Last(String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByConfigurationUuid_Last(
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByConfigurationUuid_PrevAndNext(
		long amImageEntryId, String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where configurationUuid = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	*/
	public void removeByConfigurationUuid(String configurationUuid);

	/**
	* Returns the number of am image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the number of matching am image entries
	*/
	public int countByConfigurationUuid(String configurationUuid);

	/**
	* Returns all the am image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByFileVersionId(long fileVersionId);

	/**
	* Returns a range of all the am image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end);

	/**
	* Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByFileVersionId_PrevAndNext(long amImageEntryId,
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public void removeByFileVersionId(long fileVersionId);

	/**
	* Returns the number of am image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching am image entries
	*/
	public int countByFileVersionId(long fileVersionId);

	/**
	* Returns all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the matching am image entries
	*/
	public java.util.List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid);

	/**
	* Returns a range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end);

	/**
	* Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching am image entries
	*/
	public java.util.List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByC_C_First(long companyId,
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByC_C_First(long companyId,
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByC_C_Last(long companyId,
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByC_C_Last(long companyId,
		String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param amImageEntryId the primary key of the current am image entry
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry[] findByC_C_PrevAndNext(long amImageEntryId,
		long companyId, String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException;

	/**
	* Removes all the am image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	*/
	public void removeByC_C(long companyId, String configurationUuid);

	/**
	* Returns the number of am image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the number of matching am image entries
	*/
	public int countByC_C(long companyId, String configurationUuid);

	/**
	* Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching am image entry
	* @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	*/
	public AMImageEntry findByC_F(String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByC_F(String configurationUuid, long fileVersionId);

	/**
	* Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	*/
	public AMImageEntry fetchByC_F(String configurationUuid,
		long fileVersionId, boolean retrieveFromCache);

	/**
	* Removes the am image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the am image entry that was removed
	*/
	public AMImageEntry removeByC_F(String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the number of am image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the number of matching am image entries
	*/
	public int countByC_F(String configurationUuid, long fileVersionId);

	/**
	* Caches the am image entry in the entity cache if it is enabled.
	*
	* @param amImageEntry the am image entry
	*/
	public void cacheResult(AMImageEntry amImageEntry);

	/**
	* Caches the am image entries in the entity cache if it is enabled.
	*
	* @param amImageEntries the am image entries
	*/
	public void cacheResult(java.util.List<AMImageEntry> amImageEntries);

	/**
	* Creates a new am image entry with the primary key. Does not add the am image entry to the database.
	*
	* @param amImageEntryId the primary key for the new am image entry
	* @return the new am image entry
	*/
	public AMImageEntry create(long amImageEntryId);

	/**
	* Removes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param amImageEntryId the primary key of the am image entry
	* @return the am image entry that was removed
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry remove(long amImageEntryId)
		throws NoSuchAMImageEntryException;

	public AMImageEntry updateImpl(AMImageEntry amImageEntry);

	/**
	* Returns the am image entry with the primary key or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	*
	* @param amImageEntryId the primary key of the am image entry
	* @return the am image entry
	* @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	*/
	public AMImageEntry findByPrimaryKey(long amImageEntryId)
		throws NoSuchAMImageEntryException;

	/**
	* Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param amImageEntryId the primary key of the am image entry
	* @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	*/
	public AMImageEntry fetchByPrimaryKey(long amImageEntryId);

	@Override
	public java.util.Map<java.io.Serializable, AMImageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the am image entries.
	*
	* @return the am image entries
	*/
	public java.util.List<AMImageEntry> findAll();

	/**
	* Returns a range of all the am image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @return the range of am image entries
	*/
	public java.util.List<AMImageEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the am image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of am image entries
	*/
	public java.util.List<AMImageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator);

	/**
	* Returns an ordered range of all the am image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of am image entries
	* @param end the upper bound of the range of am image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of am image entries
	*/
	public java.util.List<AMImageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the am image entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of am image entries.
	*
	* @return the number of am image entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}