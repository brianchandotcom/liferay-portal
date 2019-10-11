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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.reading.time.exception.NoSuchEntryException;
import com.liferay.reading.time.model.ReadingTimeEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the reading time entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntryUtil
 * @generated
 */
@ProviderType
public interface ReadingTimeEntryPersistence
	extends BasePersistence<ReadingTimeEntry> {

	/**
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
	public java.util.List<ReadingTimeEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns the last reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns the reading time entries before and after the current reading time entry in the ordered set where uuid = &#63;.
	 *
	 * @param readingTimeEntryId the primary key of the current reading time entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next reading time entry
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	public ReadingTimeEntry[] findByUuid_PrevAndNext(
			long readingTimeEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the reading time entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of reading time entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching reading time entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the reading time entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the reading time entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the reading time entry that was removed
	 */
	public ReadingTimeEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of reading time entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching reading time entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
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
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns the last reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns the reading time entries before and after the current reading time entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param readingTimeEntryId the primary key of the current reading time entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next reading time entry
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	public ReadingTimeEntry[] findByUuid_C_PrevAndNext(
			long readingTimeEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the reading time entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of reading time entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching reading time entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching reading time entry
	 * @throws NoSuchEntryException if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public ReadingTimeEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the reading time entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the reading time entry that was removed
	 */
	public ReadingTimeEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchEntryException;

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
	public void cacheResult(
		java.util.List<ReadingTimeEntry> readingTimeEntries);

	/**
	 * Creates a new reading time entry with the primary key. Does not add the reading time entry to the database.
	 *
	 * @param readingTimeEntryId the primary key for the new reading time entry
	 * @return the new reading time entry
	 */
	public ReadingTimeEntry create(long readingTimeEntryId);

	/**
	 * Removes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry that was removed
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	public ReadingTimeEntry remove(long readingTimeEntryId)
		throws NoSuchEntryException;

	public ReadingTimeEntry updateImpl(ReadingTimeEntry readingTimeEntry);

	/**
	 * Returns the reading time entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry
	 * @throws NoSuchEntryException if a reading time entry with the primary key could not be found
	 */
	public ReadingTimeEntry findByPrimaryKey(long readingTimeEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the reading time entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry, or <code>null</code> if a reading time entry with the primary key could not be found
	 */
	public ReadingTimeEntry fetchByPrimaryKey(long readingTimeEntryId);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the reading time entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of reading time entries
	 */
	public java.util.List<ReadingTimeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReadingTimeEntry>
			orderByComparator,
		boolean useFinderCache);

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

}