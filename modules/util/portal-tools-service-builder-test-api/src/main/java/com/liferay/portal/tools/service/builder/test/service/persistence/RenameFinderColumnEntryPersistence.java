/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchRenameFinderColumnEntryException;
import com.liferay.portal.tools.service.builder.test.model.RenameFinderColumnEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the rename finder column entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RenameFinderColumnEntryUtil
 * @generated
 */
@ProviderType
public interface RenameFinderColumnEntryPersistence
	extends BasePersistence<RenameFinderColumnEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RenameFinderColumnEntryUtil} to access the rename finder column entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a matching rename finder column entry could not be found
	 */
	public RenameFinderColumnEntry findByColumnToRename(String columnToRename)
		throws NoSuchRenameFinderColumnEntryException;

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	public RenameFinderColumnEntry fetchByColumnToRename(String columnToRename);

	/**
	 * Returns the rename finder column entry where columnToRename = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param columnToRename the column to rename
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching rename finder column entry, or <code>null</code> if a matching rename finder column entry could not be found
	 */
	public RenameFinderColumnEntry fetchByColumnToRename(
		String columnToRename, boolean useFinderCache);

	/**
	 * Removes the rename finder column entry where columnToRename = &#63; from the database.
	 *
	 * @param columnToRename the column to rename
	 * @return the rename finder column entry that was removed
	 */
	public RenameFinderColumnEntry removeByColumnToRename(String columnToRename)
		throws NoSuchRenameFinderColumnEntryException;

	/**
	 * Returns the number of rename finder column entries where columnToRename = &#63;.
	 *
	 * @param columnToRename the column to rename
	 * @return the number of matching rename finder column entries
	 */
	public int countByColumnToRename(String columnToRename);

	/**
	 * Caches the rename finder column entry in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 */
	public void cacheResult(RenameFinderColumnEntry renameFinderColumnEntry);

	/**
	 * Caches the rename finder column entries in the entity cache if it is enabled.
	 *
	 * @param renameFinderColumnEntries the rename finder column entries
	 */
	public void cacheResult(
		java.util.List<RenameFinderColumnEntry> renameFinderColumnEntries);

	/**
	 * Creates a new rename finder column entry with the primary key. Does not add the rename finder column entry to the database.
	 *
	 * @param renameFinderColumnEntryId the primary key for the new rename finder column entry
	 * @return the new rename finder column entry
	 */
	public RenameFinderColumnEntry create(long renameFinderColumnEntryId);

	/**
	 * Removes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	public RenameFinderColumnEntry remove(long renameFinderColumnEntryId)
		throws NoSuchRenameFinderColumnEntryException;

	public RenameFinderColumnEntry updateImpl(
		RenameFinderColumnEntry renameFinderColumnEntry);

	/**
	 * Returns the rename finder column entry with the primary key or throws a <code>NoSuchRenameFinderColumnEntryException</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws NoSuchRenameFinderColumnEntryException if a rename finder column entry with the primary key could not be found
	 */
	public RenameFinderColumnEntry findByPrimaryKey(
			long renameFinderColumnEntryId)
		throws NoSuchRenameFinderColumnEntryException;

	/**
	 * Returns the rename finder column entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry, or <code>null</code> if a rename finder column entry with the primary key could not be found
	 */
	public RenameFinderColumnEntry fetchByPrimaryKey(
		long renameFinderColumnEntryId);

	/**
	 * Returns all the rename finder column entries.
	 *
	 * @return the rename finder column entries
	 */
	public java.util.List<RenameFinderColumnEntry> findAll();

	/**
	 * Returns a range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @return the range of rename finder column entries
	 */
	public java.util.List<RenameFinderColumnEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rename finder column entries
	 */
	public java.util.List<RenameFinderColumnEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RenameFinderColumnEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rename finder column entries
	 */
	public java.util.List<RenameFinderColumnEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RenameFinderColumnEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the rename finder column entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of rename finder column entries.
	 *
	 * @return the number of rename finder column entries
	 */
	public int countAll();

}