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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchGroupScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the group scoped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupScopedEntryUtil
 * @generated
 */
@ProviderType
public interface GroupScopedEntryPersistence
	extends BasePersistence<GroupScopedEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GroupScopedEntryUtil} to access the group scoped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a matching group scoped entry could not be found
	 */
	public GroupScopedEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchGroupScopedEntryException;

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	public GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode);

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	public GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the group scoped entry that was removed
	 */
	public GroupScopedEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchGroupScopedEntryException;

	/**
	 * Returns the number of group scoped entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching group scoped entries
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the group scoped entry in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntry the group scoped entry
	 */
	public void cacheResult(GroupScopedEntry groupScopedEntry);

	/**
	 * Caches the group scoped entries in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntries the group scoped entries
	 */
	public void cacheResult(
		java.util.List<GroupScopedEntry> groupScopedEntries);

	/**
	 * Creates a new group scoped entry with the primary key. Does not add the group scoped entry to the database.
	 *
	 * @param GroupScopedEntryId the primary key for the new group scoped entry
	 * @return the new group scoped entry
	 */
	public GroupScopedEntry create(long GroupScopedEntryId);

	/**
	 * Removes the group scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry that was removed
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	public GroupScopedEntry remove(long GroupScopedEntryId)
		throws NoSuchGroupScopedEntryException;

	public GroupScopedEntry updateImpl(GroupScopedEntry groupScopedEntry);

	/**
	 * Returns the group scoped entry with the primary key or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	public GroupScopedEntry findByPrimaryKey(long GroupScopedEntryId)
		throws NoSuchGroupScopedEntryException;

	/**
	 * Returns the group scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry, or <code>null</code> if a group scoped entry with the primary key could not be found
	 */
	public GroupScopedEntry fetchByPrimaryKey(long GroupScopedEntryId);

	/**
	 * Returns all the group scoped entries.
	 *
	 * @return the group scoped entries
	 */
	public java.util.List<GroupScopedEntry> findAll();

	/**
	 * Returns a range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @return the range of group scoped entries
	 */
	public java.util.List<GroupScopedEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of group scoped entries
	 */
	public java.util.List<GroupScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupScopedEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of group scoped entries
	 */
	public java.util.List<GroupScopedEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupScopedEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the group scoped entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of group scoped entries.
	 *
	 * @return the number of group scoped entries
	 */
	public int countAll();

}