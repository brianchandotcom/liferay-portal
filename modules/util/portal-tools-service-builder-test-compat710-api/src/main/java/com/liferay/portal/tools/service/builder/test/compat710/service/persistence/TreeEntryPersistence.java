/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat710.exception.NoSuchTreeEntryException;
import com.liferay.portal.tools.service.builder.test.compat710.model.TreeEntry;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the tree entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntryUtil
 * @generated
 */
@ProviderType
public interface TreeEntryPersistence extends BasePersistence<TreeEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TreeEntryUtil} to access the tree entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, TreeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	 * Caches the tree entry in the entity cache if it is enabled.
	 *
	 * @param treeEntry the tree entry
	 */
	public void cacheResult(TreeEntry treeEntry);

	/**
	 * Caches the tree entries in the entity cache if it is enabled.
	 *
	 * @param treeEntries the tree entries
	 */
	public void cacheResult(java.util.List<TreeEntry> treeEntries);

	/**
	 * Creates a new tree entry with the primary key. Does not add the tree entry to the database.
	 *
	 * @param treeEntryId the primary key for the new tree entry
	 * @return the new tree entry
	 */
	public TreeEntry create(long treeEntryId);

	/**
	 * Removes the tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry that was removed
	 * @throws NoSuchTreeEntryException if a tree entry with the primary key could not be found
	 */
	public TreeEntry remove(long treeEntryId) throws NoSuchTreeEntryException;

	public TreeEntry updateImpl(TreeEntry treeEntry);

	/**
	 * Returns the tree entry with the primary key or throws a <code>NoSuchTreeEntryException</code> if it could not be found.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry
	 * @throws NoSuchTreeEntryException if a tree entry with the primary key could not be found
	 */
	public TreeEntry findByPrimaryKey(long treeEntryId)
		throws NoSuchTreeEntryException;

	/**
	 * Returns the tree entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry, or <code>null</code> if a tree entry with the primary key could not be found
	 */
	public TreeEntry fetchByPrimaryKey(long treeEntryId);

	/**
	 * Returns all the tree entries.
	 *
	 * @return the tree entries
	 */
	public java.util.List<TreeEntry> findAll();

	/**
	 * Returns a range of all the tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of tree entries
	 * @param end the upper bound of the range of tree entries (not inclusive)
	 * @return the range of tree entries
	 */
	public java.util.List<TreeEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of tree entries
	 * @param end the upper bound of the range of tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of tree entries
	 */
	public java.util.List<TreeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TreeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of tree entries
	 * @param end the upper bound of the range of tree entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of tree entries
	 */
	public java.util.List<TreeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TreeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the tree entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of tree entries.
	 *
	 * @return the number of tree entries
	 */
	public int countAll();

	public long countAncestors(TreeEntry treeEntry);

	public long countDescendants(TreeEntry treeEntry);

	public java.util.List<TreeEntry> getAncestors(TreeEntry treeEntry);

	public java.util.List<TreeEntry> getDescendants(TreeEntry treeEntry);

	/**
	 * Rebuilds the tree entries tree for the scope using the modified pre-order tree traversal algorithm.
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
// LIFERAY-SERVICE-BUILDER-HASH:-463831308