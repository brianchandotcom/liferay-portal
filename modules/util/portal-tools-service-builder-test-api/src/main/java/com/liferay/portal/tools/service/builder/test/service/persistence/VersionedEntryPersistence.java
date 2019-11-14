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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the versioned entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryUtil
 * @generated
 */
@ProviderType
public interface VersionedEntryPersistence
	extends BasePersistence<VersionedEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VersionedEntryUtil} to access the versioned entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the versioned entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the versioned entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @return the range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the versioned entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public VersionedEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public VersionedEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns the versioned entries before and after the current versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param versionedEntryId the primary key of the current versioned entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public VersionedEntry[] findByGroupId_PrevAndNext(
			long versionedEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Removes all the versioned entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of versioned entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching versioned entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head);

	/**
	 * Returns a range of all the versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @return the range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end);

	/**
	 * Returns an ordered range of all the versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching versioned entries
	 */
	public java.util.List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public VersionedEntry findByGroupId_Head_First(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public VersionedEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns the versioned entries before and after the current versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param versionedEntryId the primary key of the current versioned entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public VersionedEntry[] findByGroupId_Head_PrevAndNext(
			long versionedEntryId, long groupId, boolean head,
			com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
				orderByComparator)
		throws NoSuchVersionedEntryException;

	/**
	 * Removes all the versioned entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public void removeByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the number of versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching versioned entries
	 */
	public int countByGroupId_Head(long groupId, boolean head);

	/**
	 * Returns the versioned entry where headId = &#63; or throws a <code>NoSuchVersionedEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public VersionedEntry findByHeadId(long headId)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the versioned entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByHeadId(long headId);

	/**
	 * Returns the versioned entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public VersionedEntry fetchByHeadId(long headId, boolean useFinderCache);

	/**
	 * Removes the versioned entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the versioned entry that was removed
	 */
	public VersionedEntry removeByHeadId(long headId)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the number of versioned entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching versioned entries
	 */
	public int countByHeadId(long headId);

	/**
	 * Caches the versioned entry in the entity cache if it is enabled.
	 *
	 * @param versionedEntry the versioned entry
	 */
	public void cacheResult(VersionedEntry versionedEntry);

	/**
	 * Caches the versioned entries in the entity cache if it is enabled.
	 *
	 * @param versionedEntries the versioned entries
	 */
	public void cacheResult(java.util.List<VersionedEntry> versionedEntries);

	/**
	 * Creates a new versioned entry with the primary key. Does not add the versioned entry to the database.
	 *
	 * @param versionedEntryId the primary key for the new versioned entry
	 * @return the new versioned entry
	 */
	public VersionedEntry create(long versionedEntryId);

	/**
	 * Removes the versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry that was removed
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public VersionedEntry remove(long versionedEntryId)
		throws NoSuchVersionedEntryException;

	public VersionedEntry updateImpl(VersionedEntry versionedEntry);

	/**
	 * Returns the versioned entry with the primary key or throws a <code>NoSuchVersionedEntryException</code> if it could not be found.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public VersionedEntry findByPrimaryKey(long versionedEntryId)
		throws NoSuchVersionedEntryException;

	/**
	 * Returns the versioned entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry, or <code>null</code> if a versioned entry with the primary key could not be found
	 */
	public VersionedEntry fetchByPrimaryKey(long versionedEntryId);

	/**
	 * Returns all the versioned entries.
	 *
	 * @return the versioned entries
	 */
	public java.util.List<VersionedEntry> findAll();

	/**
	 * Returns a range of all the versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @return the range of versioned entries
	 */
	public java.util.List<VersionedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entries
	 */
	public java.util.List<VersionedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of versioned entries
	 */
	public java.util.List<VersionedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the versioned entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of versioned entries.
	 *
	 * @return the number of versioned entries
	 */
	public int countAll();

}