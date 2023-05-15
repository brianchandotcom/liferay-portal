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

package com.liferay.redirect.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the redirect not found entry service. This utility wraps <code>com.liferay.redirect.service.persistence.impl.RedirectNotFoundEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntryPersistence
 * @generated
 */
public class RedirectNotFoundEntryUtil {

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
	public static void clearCache(RedirectNotFoundEntry redirectNotFoundEntry) {
		getPersistence().clearCache(redirectNotFoundEntry);
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
	public static Map<Serializable, RedirectNotFoundEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RedirectNotFoundEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RedirectNotFoundEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RedirectNotFoundEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RedirectNotFoundEntry update(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return getPersistence().update(redirectNotFoundEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RedirectNotFoundEntry update(
		RedirectNotFoundEntry redirectNotFoundEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(redirectNotFoundEntry, serviceContext);
	}

	/**
	 * Returns all the redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of matching redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry findByGroupId_First(
			long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the redirect not found entries before and after the current redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param redirectNotFoundEntryId the primary key of the current redirect not found entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public static RedirectNotFoundEntry[] findByGroupId_PrevAndNext(
			long redirectNotFoundEntryId, long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			redirectNotFoundEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the redirect not found entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect not found entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry findByG_U(long groupId, String url)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().findByG_U(groupId, url);
	}

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry fetchByG_U(long groupId, String url) {
		return getPersistence().fetchByG_U(groupId, url);
	}

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	public static RedirectNotFoundEntry fetchByG_U(
		long groupId, String url, boolean useFinderCache) {

		return getPersistence().fetchByG_U(groupId, url, useFinderCache);
	}

	/**
	 * Removes the redirect not found entry where groupId = &#63; and url = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the redirect not found entry that was removed
	 */
	public static RedirectNotFoundEntry removeByG_U(long groupId, String url)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().removeByG_U(groupId, url);
	}

	/**
	 * Returns the number of redirect not found entries where groupId = &#63; and url = &#63;.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the number of matching redirect not found entries
	 */
	public static int countByG_U(long groupId, String url) {
		return getPersistence().countByG_U(groupId, url);
	}

	/**
	 * Caches the redirect not found entry in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 */
	public static void cacheResult(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		getPersistence().cacheResult(redirectNotFoundEntry);
	}

	/**
	 * Caches the redirect not found entries in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntries the redirect not found entries
	 */
	public static void cacheResult(
		List<RedirectNotFoundEntry> redirectNotFoundEntries) {

		getPersistence().cacheResult(redirectNotFoundEntries);
	}

	/**
	 * Creates a new redirect not found entry with the primary key. Does not add the redirect not found entry to the database.
	 *
	 * @param redirectNotFoundEntryId the primary key for the new redirect not found entry
	 * @return the new redirect not found entry
	 */
	public static RedirectNotFoundEntry create(long redirectNotFoundEntryId) {
		return getPersistence().create(redirectNotFoundEntryId);
	}

	/**
	 * Removes the redirect not found entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry that was removed
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public static RedirectNotFoundEntry remove(long redirectNotFoundEntryId)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().remove(redirectNotFoundEntryId);
	}

	public static RedirectNotFoundEntry updateImpl(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return getPersistence().updateImpl(redirectNotFoundEntry);
	}

	/**
	 * Returns the redirect not found entry with the primary key or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	public static RedirectNotFoundEntry findByPrimaryKey(
			long redirectNotFoundEntryId)
		throws com.liferay.redirect.exception.NoSuchNotFoundEntryException {

		return getPersistence().findByPrimaryKey(redirectNotFoundEntryId);
	}

	/**
	 * Returns the redirect not found entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry, or <code>null</code> if a redirect not found entry with the primary key could not be found
	 */
	public static RedirectNotFoundEntry fetchByPrimaryKey(
		long redirectNotFoundEntryId) {

		return getPersistence().fetchByPrimaryKey(redirectNotFoundEntryId);
	}

	/**
	 * Returns all the redirect not found entries.
	 *
	 * @return the redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findAll(
		int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redirect not found entries
	 */
	public static List<RedirectNotFoundEntry> findAll(
		int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the redirect not found entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of redirect not found entries.
	 *
	 * @return the number of redirect not found entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RedirectNotFoundEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RedirectNotFoundEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RedirectNotFoundEntryPersistence _persistence;

}