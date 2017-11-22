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

package com.liferay.reading.time.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.reading.time.exception.NoSuchReadingTimeEntryException;
import com.liferay.reading.time.model.ReadingTimeEntry;

/**
 * The persistence interface for the reading time entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.reading.time.service.persistence.impl.ReadingTimeEntryPersistenceImpl
 * @see ReadingTimeEntryUtil
 * @generated
 */
@ProviderType
public interface ReadingTimeEntryPersistence extends BasePersistence<ReadingTimeEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReadingTimeEntryUtil} to access the reading time entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the reading time entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the reading time entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @return the range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the reading time entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the reading time entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first reading time entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the first reading time entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns the last reading time entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the last reading time entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns the reading time entries before and after the current reading time entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current reading time entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next reading time entry
	* @throws NoSuchReadingTimeEntryException if a reading time entry with the primary key could not be found
	*/
	public ReadingTimeEntry[] findByUuid_PrevAndNext(long entryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Removes all the reading time entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of reading time entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching reading time entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the reading time entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchReadingTimeEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the reading time entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the reading time entry that was removed
	*/
	public ReadingTimeEntry removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the number of reading time entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching reading time entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the reading time entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @return the range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns the last reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the last reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns the reading time entries before and after the current reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current reading time entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next reading time entry
	* @throws NoSuchReadingTimeEntryException if a reading time entry with the primary key could not be found
	*/
	public ReadingTimeEntry[] findByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator)
		throws NoSuchReadingTimeEntryException;

	/**
	* Removes all the reading time entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of reading time entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching reading time entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchReadingTimeEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching reading time entry
	* @throws NoSuchReadingTimeEntryException if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchReadingTimeEntryException;

	/**
	* Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK);

	/**
	* Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	public ReadingTimeEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the reading time entry that was removed
	*/
	public ReadingTimeEntry removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchReadingTimeEntryException;

	/**
	* Returns the number of reading time entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching reading time entries
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Caches the reading time entry in the entity cache if it is enabled.
	*
	* @param readingTimeEntry the reading time entry
	*/
	public void cacheResult(ReadingTimeEntry readingTimeEntry);

	/**
	* Caches the reading time entries in the entity cache if it is enabled.
	*
	* @param readingTimeEntries the reading time entries
	*/
	public void cacheResult(java.util.List<ReadingTimeEntry> readingTimeEntries);

	/**
	* Creates a new reading time entry with the primary key. Does not add the reading time entry to the database.
	*
	* @param entryId the primary key for the new reading time entry
	* @return the new reading time entry
	*/
	public ReadingTimeEntry create(long entryId);

	/**
	* Removes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the reading time entry
	* @return the reading time entry that was removed
	* @throws NoSuchReadingTimeEntryException if a reading time entry with the primary key could not be found
	*/
	public ReadingTimeEntry remove(long entryId)
		throws NoSuchReadingTimeEntryException;

	public ReadingTimeEntry updateImpl(ReadingTimeEntry readingTimeEntry);

	/**
	* Returns the reading time entry with the primary key or throws a {@link NoSuchReadingTimeEntryException} if it could not be found.
	*
	* @param entryId the primary key of the reading time entry
	* @return the reading time entry
	* @throws NoSuchReadingTimeEntryException if a reading time entry with the primary key could not be found
	*/
	public ReadingTimeEntry findByPrimaryKey(long entryId)
		throws NoSuchReadingTimeEntryException;

	/**
	* Returns the reading time entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the reading time entry
	* @return the reading time entry, or <code>null</code> if a reading time entry with the primary key could not be found
	*/
	public ReadingTimeEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, ReadingTimeEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the reading time entries.
	*
	* @return the reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findAll();

	/**
	* Returns a range of all the reading time entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @return the range of reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the reading time entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator);

	/**
	* Returns an ordered range of all the reading time entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of reading time entries
	*/
	public java.util.List<ReadingTimeEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the reading time entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of reading time entries.
	*
	* @return the number of reading time entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}