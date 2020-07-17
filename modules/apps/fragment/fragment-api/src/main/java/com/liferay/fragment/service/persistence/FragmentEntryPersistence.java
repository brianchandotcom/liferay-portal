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

package com.liferay.fragment.service.persistence;

import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryUtil
 * @generated
 */
@ProviderType
public interface FragmentEntryPersistence
	extends BasePersistence<FragmentEntry>, CTPersistence<FragmentEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryUtil} to access the fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByUuid_PrevAndNext(
			long fragmentEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head);

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_Head_First(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_Head_Last(
			String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByUuid_Head_PrevAndNext(
			long fragmentEntryId, String uuid, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	public void removeByUuid_Head(String uuid, boolean head);

	/**
	 * Returns the number of fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByUuid_Head(String uuid, boolean head);

	/**
	 * Returns all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUUID_G_First(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUUID_G_First(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUUID_G_Last(
			String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByUUID_G_PrevAndNext(
			long fragmentEntryId, String uuid, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public void removeByUUID_G(String uuid, long groupId);

	/**
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head);

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache);

	/**
	 * Removes the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	public FragmentEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByUUID_G_Head(String uuid, long groupId, boolean head);

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByUuid_C_PrevAndNext(
			long fragmentEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head);

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByUuid_C_Head_PrevAndNext(
			long fragmentEntryId, String uuid, long companyId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByUuid_C_Head(String uuid, long companyId, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByGroupId_PrevAndNext(
			long fragmentEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByGroupId_Head_First(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByGroupId_Head_PrevAndNext(
			long fragmentEntryId, long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public void removeByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByFragmentCollectionId_First(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByFragmentCollectionId_Last(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByFragmentCollectionId_PrevAndNext(
			long fragmentEntryId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	public int countByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head);

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByFragmentCollectionId_Head_First(
			long fragmentCollectionId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByFragmentCollectionId_Head_First(
		long fragmentCollectionId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByFragmentCollectionId_Head_Last(
			long fragmentCollectionId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByFragmentCollectionId_Head_Last(
		long fragmentCollectionId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByFragmentCollectionId_Head_PrevAndNext(
			long fragmentEntryId, long fragmentCollectionId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	public void removeByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head);

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_Last(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_Last(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_Head_First(
			long groupId, long fragmentCollectionId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_Head_First(
		long groupId, long fragmentCollectionId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_Head_Last(
			long groupId, long fragmentCollectionId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_Head_Last(
		long groupId, long fragmentCollectionId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	public void removeByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FEK_Last(
			long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FEK_Last(
		long groupId, String fragmentEntryKey,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FEK_PrevAndNext(
			long fragmentEntryId, long groupId, String fragmentEntryKey,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 */
	public void removeByG_FEK(long groupId, String fragmentEntryKey);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entries
	 */
	public int countByG_FEK(long groupId, String fragmentEntryKey);

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head);

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head,
		boolean useFinderCache);

	/**
	 * Removes the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	public FragmentEntry removeByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException;

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_Last(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_Last(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_LikeN_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_Head_First(
			long groupId, long fragmentCollectionId, String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_Head_First(
		long groupId, long fragmentCollectionId, String name, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_Head_Last(
			long groupId, long fragmentCollectionId, String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_Head_Last(
		long groupId, long fragmentCollectionId, String name, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_LikeN_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 */
	public void removeByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_Last(
			long groupId, long fragmentCollectionId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_Last(
		long groupId, long fragmentCollectionId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_T_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 */
	public void removeByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_Head_First(
			long groupId, long fragmentCollectionId, int type, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_Head_First(
		long groupId, long fragmentCollectionId, int type, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_Head_Last(
			long groupId, long fragmentCollectionId, int type, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_Head_Last(
		long groupId, long fragmentCollectionId, int type, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_T_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 */
	public void removeByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_S_Last(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_S_Last(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_S_Head_First(
			long groupId, long fragmentCollectionId, int status, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_S_Head_First(
		long groupId, long fragmentCollectionId, int status, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_S_Head_Last(
			long groupId, long fragmentCollectionId, int status, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_S_Head_Last(
		long groupId, long fragmentCollectionId, int status, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int status, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 */
	public void removeByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_S_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_S_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_LikeN_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_S_Head_First(
			long groupId, long fragmentCollectionId, String name, int status,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_S_Head_First(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_LikeN_S_Head_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_LikeN_S_Head_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_LikeN_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, int status, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 */
	public void removeByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_S_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_S_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_T_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 */
	public void removeByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status);

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head);

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	public java.util.List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_S_Head_First(
			long groupId, long fragmentCollectionId, int type, int status,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_S_Head_First(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByG_FCI_T_S_Head_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByG_FCI_T_S_Head_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry[] findByG_FCI_T_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, int status, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 */
	public void removeByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head);

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	public int countByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head);

	/**
	 * Returns the fragment entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	public FragmentEntry findByHeadId(long headId) throws NoSuchEntryException;

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByHeadId(long headId);

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public FragmentEntry fetchByHeadId(long headId, boolean useFinderCache);

	/**
	 * Removes the fragment entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the fragment entry that was removed
	 */
	public FragmentEntry removeByHeadId(long headId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of fragment entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching fragment entries
	 */
	public int countByHeadId(long headId);

	/**
	 * Caches the fragment entry in the entity cache if it is enabled.
	 *
	 * @param fragmentEntry the fragment entry
	 */
	public void cacheResult(FragmentEntry fragmentEntry);

	/**
	 * Caches the fragment entries in the entity cache if it is enabled.
	 *
	 * @param fragmentEntries the fragment entries
	 */
	public void cacheResult(java.util.List<FragmentEntry> fragmentEntries);

	/**
	 * Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	 *
	 * @param fragmentEntryId the primary key for the new fragment entry
	 * @return the new fragment entry
	 */
	public FragmentEntry create(long fragmentEntryId);

	/**
	 * Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry remove(long fragmentEntryId)
		throws NoSuchEntryException;

	public FragmentEntry updateImpl(FragmentEntry fragmentEntry);

	/**
	 * Returns the fragment entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry findByPrimaryKey(long fragmentEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	 */
	public FragmentEntry fetchByPrimaryKey(long fragmentEntryId);

	/**
	 * Returns all the fragment entries.
	 *
	 * @return the fragment entries
	 */
	public java.util.List<FragmentEntry> findAll();

	/**
	 * Returns a range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of fragment entries
	 */
	public java.util.List<FragmentEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entries
	 */
	public java.util.List<FragmentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entries
	 */
	public java.util.List<FragmentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fragment entries.
	 *
	 * @return the number of fragment entries
	 */
	public int countAll();

}