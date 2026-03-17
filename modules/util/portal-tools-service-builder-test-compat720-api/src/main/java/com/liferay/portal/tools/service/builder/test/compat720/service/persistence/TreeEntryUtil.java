/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat720.model.TreeEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the tree entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat720.service.persistence.impl.TreeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntryPersistence
 * @generated
 */
public class TreeEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(TreeEntry treeEntry) {
		getPersistence().clearCache(treeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, TreeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<TreeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static TreeEntry update(TreeEntry treeEntry) {
		return getPersistence().update(treeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static TreeEntry update(
		TreeEntry treeEntry, ServiceContext serviceContext) {

		return getPersistence().update(treeEntry, serviceContext);
	}

	/**
	 * Caches the tree entry in the entity cache if it is enabled.
	 *
	 * @param treeEntry the tree entry
	 */
	public static void cacheResult(TreeEntry treeEntry) {
		getPersistence().cacheResult(treeEntry);
	}

	/**
	 * Caches the tree entries in the entity cache if it is enabled.
	 *
	 * @param treeEntries the tree entries
	 */
	public static void cacheResult(List<TreeEntry> treeEntries) {
		getPersistence().cacheResult(treeEntries);
	}

	/**
	 * Creates a new tree entry with the primary key. Does not add the tree entry to the database.
	 *
	 * @param treeEntryId the primary key for the new tree entry
	 * @return the new tree entry
	 */
	public static TreeEntry create(long treeEntryId) {
		return getPersistence().create(treeEntryId);
	}

	/**
	 * Removes the tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry that was removed
	 * @throws NoSuchTreeEntryException if a tree entry with the primary key could not be found
	 */
	public static TreeEntry remove(long treeEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat720.
			exception.NoSuchTreeEntryException {

		return getPersistence().remove(treeEntryId);
	}

	public static TreeEntry updateImpl(TreeEntry treeEntry) {
		return getPersistence().updateImpl(treeEntry);
	}

	/**
	 * Returns the tree entry with the primary key or throws a <code>NoSuchTreeEntryException</code> if it could not be found.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry
	 * @throws NoSuchTreeEntryException if a tree entry with the primary key could not be found
	 */
	public static TreeEntry findByPrimaryKey(long treeEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat720.
			exception.NoSuchTreeEntryException {

		return getPersistence().findByPrimaryKey(treeEntryId);
	}

	/**
	 * Returns the tree entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry, or <code>null</code> if a tree entry with the primary key could not be found
	 */
	public static TreeEntry fetchByPrimaryKey(long treeEntryId) {
		return getPersistence().fetchByPrimaryKey(treeEntryId);
	}

	/**
	 * Returns all the tree entries.
	 *
	 * @return the tree entries
	 */
	public static List<TreeEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<TreeEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<TreeEntry> findAll(
		int start, int end, OrderByComparator<TreeEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<TreeEntry> findAll(
		int start, int end, OrderByComparator<TreeEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the tree entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of tree entries.
	 *
	 * @return the number of tree entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static long countAncestors(TreeEntry treeEntry) {
		return getPersistence().countAncestors(treeEntry);
	}

	public static long countDescendants(TreeEntry treeEntry) {
		return getPersistence().countDescendants(treeEntry);
	}

	public static List<TreeEntry> getAncestors(TreeEntry treeEntry) {
		return getPersistence().getAncestors(treeEntry);
	}

	public static List<TreeEntry> getDescendants(TreeEntry treeEntry) {
		return getPersistence().getDescendants(treeEntry);
	}

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
	public static void rebuildTree(long groupId, boolean force) {
		getPersistence().rebuildTree(groupId, force);
	}

	public static void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		getPersistence().setRebuildTreeEnabled(rebuildTreeEnabled);
	}

	public static TreeEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(TreeEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile TreeEntryPersistence _persistence;

}
// SB-Hash:1476572579