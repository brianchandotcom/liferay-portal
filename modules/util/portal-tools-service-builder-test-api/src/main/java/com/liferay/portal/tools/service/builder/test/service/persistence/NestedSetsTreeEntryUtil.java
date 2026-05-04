/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the nested sets tree entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.NestedSetsTreeEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntryPersistence
 * @generated
 */
public class NestedSetsTreeEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<NestedSetsTreeEntry> nestedSetsTreeEntries) {

		getPersistence().cacheResult(nestedSetsTreeEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(NestedSetsTreeEntry nestedSetsTreeEntry) {
		getPersistence().cacheResult(nestedSetsTreeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(NestedSetsTreeEntry nestedSetsTreeEntry) {
		getPersistence().clearCache(nestedSetsTreeEntry);
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
	public static Map<Serializable, NestedSetsTreeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<NestedSetsTreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<NestedSetsTreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<NestedSetsTreeEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<NestedSetsTreeEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static NestedSetsTreeEntry update(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getPersistence().update(nestedSetsTreeEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static NestedSetsTreeEntry update(
		NestedSetsTreeEntry nestedSetsTreeEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(nestedSetsTreeEntry, serviceContext);
	}

	/**
	 * Creates a new nested sets tree entry with the primary key. Does not add the nested sets tree entry to the database.
	 *
	 * @param nestedSetsTreeEntryId the primary key for the new nested sets tree entry
	 * @return the new nested sets tree entry
	 */
	public static NestedSetsTreeEntry create(long nestedSetsTreeEntryId) {
		return getPersistence().create(nestedSetsTreeEntryId);
	}

	/**
	 * Removes the nested sets tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	public static NestedSetsTreeEntry remove(long nestedSetsTreeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNestedSetsTreeEntryException {

		return getPersistence().remove(nestedSetsTreeEntryId);
	}

	public static NestedSetsTreeEntry updateImpl(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getPersistence().updateImpl(nestedSetsTreeEntry);
	}

	/**
	 * Returns the nested sets tree entry with the primary key or throws a <code>NoSuchNestedSetsTreeEntryException</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry
	 * @throws NoSuchNestedSetsTreeEntryException if a nested sets tree entry with the primary key could not be found
	 */
	public static NestedSetsTreeEntry findByPrimaryKey(
			long nestedSetsTreeEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchNestedSetsTreeEntryException {

		return getPersistence().findByPrimaryKey(nestedSetsTreeEntryId);
	}

	/**
	 * Returns the nested sets tree entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry, or <code>null</code> if a nested sets tree entry with the primary key could not be found
	 */
	public static NestedSetsTreeEntry fetchByPrimaryKey(
		long nestedSetsTreeEntryId) {

		return getPersistence().fetchByPrimaryKey(nestedSetsTreeEntryId);
	}

	public static long countAncestors(NestedSetsTreeEntry nestedSetsTreeEntry) {
		return getPersistence().countAncestors(nestedSetsTreeEntry);
	}

	public static long countDescendants(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getPersistence().countDescendants(nestedSetsTreeEntry);
	}

	public static List<NestedSetsTreeEntry> getAncestors(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getPersistence().getAncestors(nestedSetsTreeEntry);
	}

	public static List<NestedSetsTreeEntry> getDescendants(
		NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getPersistence().getDescendants(nestedSetsTreeEntry);
	}

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
	public static void rebuildTree(long groupId, boolean force) {
		getPersistence().rebuildTree(groupId, force);
	}

	public static void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		getPersistence().setRebuildTreeEnabled(rebuildTreeEnabled);
	}

	public static NestedSetsTreeEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		NestedSetsTreeEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile NestedSetsTreeEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:225365256