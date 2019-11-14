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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchNestedSetsTreeEntryException;
import com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the nested sets tree entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntryUtil
 * @generated
 */
@ProviderType
public interface NestedSetsTreeEntryPersistence
	extends BasePersistence<NestedSetsTreeEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NestedSetsTreeEntryUtil} to access the nested sets tree entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the nested sets tree entry in the entity cache if it is enabled.
	 *
	 * @param nestedSetsTreeEntry the nested sets tree entry
	 */
	public void cacheResult(NestedSetsTreeEntry nestedSetsTreeEntry);

	/**
	 * Caches the nested sets tree entries in the entity cache if it is enabled.
	 *
	 * @param nestedSetsTreeEntries the nested sets tree entries
	 */
	public void cacheResult(
		java.util.List<NestedSetsTreeEntry> nestedSetsTreeEntries);

	/**
	 * Creates a new nested sets tree entry with the primary key. Does not add the nested sets tree entry to the database.
	 *
	 * @param nestedSetsTreeEntryId the primary key for the new nested sets tree entry
	 * @return the new nested sets tree entry
	 */
	public NestedSetsTreeEntry create(long nestedSetsTreeEntryId);

	/**
	 * Removes the nested sets tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	public NestedSetsTreeEntry remove(long nestedSetsTreeEntryId)
		throws NoSuchNestedSetsTreeEntryException;

	public NestedSetsTreeEntry updateImpl(
		NestedSetsTreeEntry nestedSetsTreeEntry);

	/**
	 * Returns the nested sets tree entry with the primary key or throws a <code>NoSuchNestedSetsTreeEntryException</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	public NestedSetsTreeEntry findByPrimaryKey(long nestedSetsTreeEntryId)
		throws NoSuchNestedSetsTreeEntryException;

	/**
	 * Returns the nested sets tree entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry, or <code>null</code> if a nested sets tree entry with the primary key could not be found
	 */
	public NestedSetsTreeEntry fetchByPrimaryKey(long nestedSetsTreeEntryId);

	/**
	 * Returns all the nested sets tree entries.
	 *
	 * @return the nested sets tree entries
	 */
	public java.util.List<NestedSetsTreeEntry> findAll();

	/**
	 * Returns a range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @return the range of nested sets tree entries
	 */
	public java.util.List<NestedSetsTreeEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of nested sets tree entries
	 */
	public java.util.List<NestedSetsTreeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NestedSetsTreeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of nested sets tree entries
	 */
	public java.util.List<NestedSetsTreeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<NestedSetsTreeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the nested sets tree entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of nested sets tree entries.
	 *
	 * @return the number of nested sets tree entries
	 */
	public int countAll();

	public long countAncestors(NestedSetsTreeEntry nestedSetsTreeEntry);

	public long countDescendants(NestedSetsTreeEntry nestedSetsTreeEntry);

	public java.util.List<NestedSetsTreeEntry> getAncestors(
		NestedSetsTreeEntry nestedSetsTreeEntry);

	public java.util.List<NestedSetsTreeEntry> getDescendants(
		NestedSetsTreeEntry nestedSetsTreeEntry);

	/**
	 * Rebuilds the nested sets tree entries tree for the scope using the modified pre-order tree traversal algorithm.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	 * </p>
	 *
	 * @param groupId the ID of the scope
	 * @param force whether to force the rebuild even if the tree is not stale
	 */
	public void rebuildTree(long groupId, boolean force);

	public void setRebuildTreeEnabled(boolean rebuildTreeEnabled);

}