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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the group scoped entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.GroupScopedEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupScopedEntryPersistence
 * @generated
 */
public class GroupScopedEntryUtil {

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
	public static void clearCache(GroupScopedEntry groupScopedEntry) {
		getPersistence().clearCache(groupScopedEntry);
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
	public static Map<Serializable, GroupScopedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<GroupScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<GroupScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<GroupScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<GroupScopedEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static GroupScopedEntry update(GroupScopedEntry groupScopedEntry) {
		return getPersistence().update(groupScopedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static GroupScopedEntry update(
		GroupScopedEntry groupScopedEntry, ServiceContext serviceContext) {

		return getPersistence().update(groupScopedEntry, serviceContext);
	}

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a matching group scoped entry could not be found
	 */
	public static GroupScopedEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchGroupScopedEntryException {

		return getPersistence().findByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	public static GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return getPersistence().fetchByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	public static GroupScopedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByG_ERC(
			groupId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the group scoped entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the group scoped entry that was removed
	 */
	public static GroupScopedEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchGroupScopedEntryException {

		return getPersistence().removeByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the number of group scoped entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching group scoped entries
	 */
	public static int countByG_ERC(long groupId, String externalReferenceCode) {
		return getPersistence().countByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Caches the group scoped entry in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntry the group scoped entry
	 */
	public static void cacheResult(GroupScopedEntry groupScopedEntry) {
		getPersistence().cacheResult(groupScopedEntry);
	}

	/**
	 * Caches the group scoped entries in the entity cache if it is enabled.
	 *
	 * @param groupScopedEntries the group scoped entries
	 */
	public static void cacheResult(List<GroupScopedEntry> groupScopedEntries) {
		getPersistence().cacheResult(groupScopedEntries);
	}

	/**
	 * Creates a new group scoped entry with the primary key. Does not add the group scoped entry to the database.
	 *
	 * @param GroupScopedEntryId the primary key for the new group scoped entry
	 * @return the new group scoped entry
	 */
	public static GroupScopedEntry create(long GroupScopedEntryId) {
		return getPersistence().create(GroupScopedEntryId);
	}

	/**
	 * Removes the group scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry that was removed
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	public static GroupScopedEntry remove(long GroupScopedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchGroupScopedEntryException {

		return getPersistence().remove(GroupScopedEntryId);
	}

	public static GroupScopedEntry updateImpl(
		GroupScopedEntry groupScopedEntry) {

		return getPersistence().updateImpl(groupScopedEntry);
	}

	/**
	 * Returns the group scoped entry with the primary key or throws a <code>NoSuchGroupScopedEntryException</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry
	 * @throws NoSuchGroupScopedEntryException if a group scoped entry with the primary key could not be found
	 */
	public static GroupScopedEntry findByPrimaryKey(long GroupScopedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchGroupScopedEntryException {

		return getPersistence().findByPrimaryKey(GroupScopedEntryId);
	}

	/**
	 * Returns the group scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry, or <code>null</code> if a group scoped entry with the primary key could not be found
	 */
	public static GroupScopedEntry fetchByPrimaryKey(long GroupScopedEntryId) {
		return getPersistence().fetchByPrimaryKey(GroupScopedEntryId);
	}

	/**
	 * Returns all the group scoped entries.
	 *
	 * @return the group scoped entries
	 */
	public static List<GroupScopedEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<GroupScopedEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<GroupScopedEntry> findAll(
		int start, int end,
		OrderByComparator<GroupScopedEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<GroupScopedEntry> findAll(
		int start, int end,
		OrderByComparator<GroupScopedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the group scoped entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of group scoped entries.
	 *
	 * @return the number of group scoped entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static GroupScopedEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<GroupScopedEntryPersistence, GroupScopedEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			GroupScopedEntryPersistence.class);

		ServiceTracker<GroupScopedEntryPersistence, GroupScopedEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<GroupScopedEntryPersistence, GroupScopedEntryPersistence>(
						bundle.getBundleContext(),
						GroupScopedEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}