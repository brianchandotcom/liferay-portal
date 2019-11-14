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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException;
import com.liferay.portal.tools.service.builder.test.model.LVEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the lv entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryUtil
 * @generated
 */
@ProviderType
public interface LVEntryPersistence extends BasePersistence<LVEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LVEntryUtil} to access the lv entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the lv entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid(String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByUuid_PrevAndNext(
			long lvEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of lv entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lv entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_Head(String uuid, boolean head);

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_Head_First(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_Head_Last(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByUuid_Head_PrevAndNext(
			long lvEntryId, String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	public void removeByUuid_Head(String uuid, boolean head);

	/**
	 * Returns the number of lv entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByUuid_Head(String uuid, boolean head);

	/**
	 * Returns all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByUUID_G(String uuid, long groupId);

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUUID_G_First(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUUID_G_First(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUUID_G_Last(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByUUID_G_PrevAndNext(
			long lvEntryId, String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public void removeByUUID_G(String uuid, long groupId);

	/**
	 * Returns the number of lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lv entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUUID_G_Head(String uuid, long groupId, boolean head)
		throws NoSuchLVEntryException;

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUUID_G_Head(String uuid, long groupId, boolean head);

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache);

	/**
	 * Removes the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the lv entry that was removed
	 */
	public LVEntry removeByUUID_G_Head(String uuid, long groupId, boolean head)
		throws NoSuchLVEntryException;

	/**
	 * Returns the number of lv entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByUUID_G_Head(String uuid, long groupId, boolean head);

	/**
	 * Returns all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByUuid_C_PrevAndNext(
			long lvEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching lv entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head);

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByUuid_C_Head_PrevAndNext(
			long lvEntryId, String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns the number of lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns all the lv entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByGroupId_PrevAndNext(
			long lvEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lv entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of lv entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching lv entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of lv entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching lv entries
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long groupId, boolean head);

	/**
	 * Returns a range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByGroupId_Head_First(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByGroupId_Head_PrevAndNext(
			long lvEntryId, long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head);

	/**
	 * Returns a range of all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lv entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public void removeByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the number of lv entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the number of lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByGroupId_Head(long[] groupIds, boolean head);

	/**
	 * Returns all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the matching lv entries
	 */
	public java.util.List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey);

	/**
	 * Returns a range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	public java.util.List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	public java.util.List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByG_UGK_First(
			long groupId, String uniqueGroupKey,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByG_UGK_First(
		long groupId, String uniqueGroupKey,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByG_UGK_Last(
			long groupId, String uniqueGroupKey,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByG_UGK_Last(
		long groupId, String uniqueGroupKey,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry[] findByG_UGK_PrevAndNext(
			long lvEntryId, long groupId, String uniqueGroupKey,
			com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
				orderByComparator)
		throws NoSuchLVEntryException;

	/**
	 * Removes all the lv entries where groupId = &#63; and uniqueGroupKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 */
	public void removeByG_UGK(long groupId, String uniqueGroupKey);

	/**
	 * Returns the number of lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the number of matching lv entries
	 */
	public int countByG_UGK(long groupId, String uniqueGroupKey);

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByG_UGK_Head(
			long groupId, String uniqueGroupKey, boolean head)
		throws NoSuchLVEntryException;

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head);

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head,
		boolean useFinderCache);

	/**
	 * Removes the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the lv entry that was removed
	 */
	public LVEntry removeByG_UGK_Head(
			long groupId, String uniqueGroupKey, boolean head)
		throws NoSuchLVEntryException;

	/**
	 * Returns the number of lv entries where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	public int countByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head);

	/**
	 * Returns the lv entry where headId = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	public LVEntry findByHeadId(long headId) throws NoSuchLVEntryException;

	/**
	 * Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByHeadId(long headId);

	/**
	 * Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	public LVEntry fetchByHeadId(long headId, boolean useFinderCache);

	/**
	 * Removes the lv entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the lv entry that was removed
	 */
	public LVEntry removeByHeadId(long headId) throws NoSuchLVEntryException;

	/**
	 * Returns the number of lv entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching lv entries
	 */
	public int countByHeadId(long headId);

	/**
	 * Caches the lv entry in the entity cache if it is enabled.
	 *
	 * @param lvEntry the lv entry
	 */
	public void cacheResult(LVEntry lvEntry);

	/**
	 * Caches the lv entries in the entity cache if it is enabled.
	 *
	 * @param lvEntries the lv entries
	 */
	public void cacheResult(java.util.List<LVEntry> lvEntries);

	/**
	 * Creates a new lv entry with the primary key. Does not add the lv entry to the database.
	 *
	 * @param lvEntryId the primary key for the new lv entry
	 * @return the new lv entry
	 */
	public LVEntry create(long lvEntryId);

	/**
	 * Removes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry that was removed
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry remove(long lvEntryId) throws NoSuchLVEntryException;

	public LVEntry updateImpl(LVEntry lvEntry);

	/**
	 * Returns the lv entry with the primary key or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	public LVEntry findByPrimaryKey(long lvEntryId)
		throws NoSuchLVEntryException;

	/**
	 * Returns the lv entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry, or <code>null</code> if a lv entry with the primary key could not be found
	 */
	public LVEntry fetchByPrimaryKey(long lvEntryId);

	/**
	 * Returns all the lv entries.
	 *
	 * @return the lv entries
	 */
	public java.util.List<LVEntry> findAll();

	/**
	 * Returns a range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of lv entries
	 */
	public java.util.List<LVEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entries
	 */
	public java.util.List<LVEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entries
	 */
	public java.util.List<LVEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lv entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of lv entries.
	 *
	 * @return the number of lv entries
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return long[] of the primaryKeys of big decimal entries associated with the lv entry
	 */
	public long[] getBigDecimalEntryPrimaryKeys(long pk);

	/**
	 * Returns all the big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return the big decimal entries associated with the lv entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk);

	/**
	 * Returns a range of all the big decimal entries associated with the lv entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of big decimal entries associated with the lv entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk, int start, int end);

	/**
	 * Returns an ordered range of all the big decimal entries associated with the lv entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries associated with the lv entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(
				long pk, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						BigDecimalEntry> orderByComparator);

	/**
	 * Returns the number of big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return the number of big decimal entries associated with the lv entry
	 */
	public int getBigDecimalEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the big decimal entry is associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 * @return <code>true</code> if the big decimal entry is associated with the lv entry; <code>false</code> otherwise
	 */
	public boolean containsBigDecimalEntry(long pk, long bigDecimalEntryPK);

	/**
	 * Returns <code>true</code> if the lv entry has any big decimal entries associated with it.
	 *
	 * @param pk the primary key of the lv entry to check for associations with big decimal entries
	 * @return <code>true</code> if the lv entry has any big decimal entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsBigDecimalEntries(long pk);

	/**
	 * Adds an association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	public void addBigDecimalEntry(long pk, long bigDecimalEntryPK);

	/**
	 * Adds an association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntry the big decimal entry
	 */
	public void addBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry);

	/**
	 * Adds an association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	public void addBigDecimalEntries(long pk, long[] bigDecimalEntryPKs);

	/**
	 * Adds an association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries
	 */
	public void addBigDecimalEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries);

	/**
	 * Clears all associations between the lv entry and its big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry to clear the associated big decimal entries from
	 */
	public void clearBigDecimalEntries(long pk);

	/**
	 * Removes the association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	public void removeBigDecimalEntry(long pk, long bigDecimalEntryPK);

	/**
	 * Removes the association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntry the big decimal entry
	 */
	public void removeBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry);

	/**
	 * Removes the association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	public void removeBigDecimalEntries(long pk, long[] bigDecimalEntryPKs);

	/**
	 * Removes the association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries
	 */
	public void removeBigDecimalEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries);

	/**
	 * Sets the big decimal entries associated with the lv entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries to be associated with the lv entry
	 */
	public void setBigDecimalEntries(long pk, long[] bigDecimalEntryPKs);

	/**
	 * Sets the big decimal entries associated with the lv entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries to be associated with the lv entry
	 */
	public void setBigDecimalEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries);

}