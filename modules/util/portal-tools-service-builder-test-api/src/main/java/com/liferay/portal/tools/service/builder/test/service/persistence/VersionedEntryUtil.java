/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the versioned entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryPersistence
 * @generated
 */
public class VersionedEntryUtil {

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
	public static void clearCache(VersionedEntry versionedEntry) {
		getPersistence().clearCache(versionedEntry);
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
	public static Map<Serializable, VersionedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VersionedEntry update(VersionedEntry versionedEntry) {
		return getPersistence().update(versionedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VersionedEntry update(
		VersionedEntry versionedEntry, ServiceContext serviceContext) {

		return getPersistence().update(versionedEntry, serviceContext);
	}

	/**
	 * Returns all the versioned entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching versioned entries
	 */
	public static List<VersionedEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<VersionedEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<VersionedEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

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
	public static List<VersionedEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public static VersionedEntry findByGroupId_First(
			long groupId, OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByGroupId_First(
		long groupId, OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public static VersionedEntry findByGroupId_Last(
			long groupId, OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the versioned entries before and after the current versioned entry in the ordered set where groupId = &#63;.
	 *
	 * @param versionedEntryId the primary key of the current versioned entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public static VersionedEntry[] findByGroupId_PrevAndNext(
			long versionedEntryId, long groupId,
			OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			versionedEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the versioned entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of versioned entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching versioned entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching versioned entries
	 */
	public static List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head) {

		return getPersistence().findByGroupId_Head(groupId, head);
	}

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
	public static List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return getPersistence().findByGroupId_Head(groupId, head, start, end);
	}

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
	public static List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().findByGroupId_Head(
			groupId, head, start, end, orderByComparator);
	}

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
	public static List<VersionedEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId_Head(
			groupId, head, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public static VersionedEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_Head_First(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the first versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Head_First(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public static VersionedEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_Head_Last(
			groupId, head, orderByComparator);
	}

	/**
	 * Returns the last versioned entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);
	}

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
	public static VersionedEntry[] findByGroupId_Head_PrevAndNext(
			long versionedEntryId, long groupId, boolean head,
			OrderByComparator<VersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByGroupId_Head_PrevAndNext(
			versionedEntryId, groupId, head, orderByComparator);
	}

	/**
	 * Removes all the versioned entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	public static void removeByGroupId_Head(long groupId, boolean head) {
		getPersistence().removeByGroupId_Head(groupId, head);
	}

	/**
	 * Returns the number of versioned entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching versioned entries
	 */
	public static int countByGroupId_Head(long groupId, boolean head) {
		return getPersistence().countByGroupId_Head(groupId, head);
	}

	/**
	 * Returns the versioned entry where headId = &#63; or throws a <code>NoSuchVersionedEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry
	 * @throws NoSuchVersionedEntryException if a matching versioned entry could not be found
	 */
	public static VersionedEntry findByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByHeadId(headId);
	}

	/**
	 * Returns the versioned entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	 * Returns the versioned entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching versioned entry, or <code>null</code> if a matching versioned entry could not be found
	 */
	public static VersionedEntry fetchByHeadId(
		long headId, boolean useFinderCache) {

		return getPersistence().fetchByHeadId(headId, useFinderCache);
	}

	/**
	 * Removes the versioned entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the versioned entry that was removed
	 */
	public static VersionedEntry removeByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().removeByHeadId(headId);
	}

	/**
	 * Returns the number of versioned entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching versioned entries
	 */
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	 * Caches the versioned entry in the entity cache if it is enabled.
	 *
	 * @param versionedEntry the versioned entry
	 */
	public static void cacheResult(VersionedEntry versionedEntry) {
		getPersistence().cacheResult(versionedEntry);
	}

	/**
	 * Caches the versioned entries in the entity cache if it is enabled.
	 *
	 * @param versionedEntries the versioned entries
	 */
	public static void cacheResult(List<VersionedEntry> versionedEntries) {
		getPersistence().cacheResult(versionedEntries);
	}

	/**
	 * Creates a new versioned entry with the primary key. Does not add the versioned entry to the database.
	 *
	 * @param versionedEntryId the primary key for the new versioned entry
	 * @return the new versioned entry
	 */
	public static VersionedEntry create(long versionedEntryId) {
		return getPersistence().create(versionedEntryId);
	}

	/**
	 * Removes the versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry that was removed
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public static VersionedEntry remove(long versionedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().remove(versionedEntryId);
	}

	public static VersionedEntry updateImpl(VersionedEntry versionedEntry) {
		return getPersistence().updateImpl(versionedEntry);
	}

	/**
	 * Returns the versioned entry with the primary key or throws a <code>NoSuchVersionedEntryException</code> if it could not be found.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry
	 * @throws NoSuchVersionedEntryException if a versioned entry with the primary key could not be found
	 */
	public static VersionedEntry findByPrimaryKey(long versionedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchVersionedEntryException {

		return getPersistence().findByPrimaryKey(versionedEntryId);
	}

	/**
	 * Returns the versioned entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry, or <code>null</code> if a versioned entry with the primary key could not be found
	 */
	public static VersionedEntry fetchByPrimaryKey(long versionedEntryId) {
		return getPersistence().fetchByPrimaryKey(versionedEntryId);
	}

	/**
	 * Returns all the versioned entries.
	 *
	 * @return the versioned entries
	 */
	public static List<VersionedEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<VersionedEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<VersionedEntry> findAll(
		int start, int end,
		OrderByComparator<VersionedEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<VersionedEntry> findAll(
		int start, int end, OrderByComparator<VersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the versioned entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of versioned entries.
	 *
	 * @return the number of versioned entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VersionedEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(VersionedEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile VersionedEntryPersistence _persistence;

}