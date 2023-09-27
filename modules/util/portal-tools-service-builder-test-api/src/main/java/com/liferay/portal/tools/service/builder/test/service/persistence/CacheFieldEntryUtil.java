/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cache field entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.CacheFieldEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntryPersistence
 * @generated
 */
public class CacheFieldEntryUtil {

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
	public static void clearCache(CacheFieldEntry cacheFieldEntry) {
		getPersistence().clearCache(cacheFieldEntry);
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
	public static Map<Serializable, CacheFieldEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CacheFieldEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CacheFieldEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CacheFieldEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CacheFieldEntry update(CacheFieldEntry cacheFieldEntry) {
		return getPersistence().update(cacheFieldEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CacheFieldEntry update(
		CacheFieldEntry cacheFieldEntry, ServiceContext serviceContext) {

		return getPersistence().update(cacheFieldEntry, serviceContext);
	}

	/**
	 * Returns all the cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cache field entries
	 */
	public static List<CacheFieldEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @return the range of matching cache field entries
	 */
	public static List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cache field entries
	 */
	public static List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cache field entries
	 */
	public static List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	public static CacheFieldEntry findByGroupId_First(
			long groupId, OrderByComparator<CacheFieldEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheFieldEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	public static CacheFieldEntry fetchByGroupId_First(
		long groupId, OrderByComparator<CacheFieldEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	public static CacheFieldEntry findByGroupId_Last(
			long groupId, OrderByComparator<CacheFieldEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheFieldEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	public static CacheFieldEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<CacheFieldEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the cache field entries before and after the current cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param cacheFieldEntryId the primary key of the current cache field entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public static CacheFieldEntry[] findByGroupId_PrevAndNext(
			long cacheFieldEntryId, long groupId,
			OrderByComparator<CacheFieldEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheFieldEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			cacheFieldEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the cache field entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cache field entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Caches the cache field entry in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntry the cache field entry
	 */
	public static void cacheResult(CacheFieldEntry cacheFieldEntry) {
		getPersistence().cacheResult(cacheFieldEntry);
	}

	/**
	 * Caches the cache field entries in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntries the cache field entries
	 */
	public static void cacheResult(List<CacheFieldEntry> cacheFieldEntries) {
		getPersistence().cacheResult(cacheFieldEntries);
	}

	/**
	 * Creates a new cache field entry with the primary key. Does not add the cache field entry to the database.
	 *
	 * @param cacheFieldEntryId the primary key for the new cache field entry
	 * @return the new cache field entry
	 */
	public static CacheFieldEntry create(long cacheFieldEntryId) {
		return getPersistence().create(cacheFieldEntryId);
	}

	/**
	 * Removes the cache field entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry that was removed
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public static CacheFieldEntry remove(long cacheFieldEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheFieldEntryException {

		return getPersistence().remove(cacheFieldEntryId);
	}

	public static CacheFieldEntry updateImpl(CacheFieldEntry cacheFieldEntry) {
		return getPersistence().updateImpl(cacheFieldEntry);
	}

	/**
	 * Returns the cache field entry with the primary key or throws a <code>NoSuchCacheFieldEntryException</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public static CacheFieldEntry findByPrimaryKey(long cacheFieldEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheFieldEntryException {

		return getPersistence().findByPrimaryKey(cacheFieldEntryId);
	}

	/**
	 * Returns the cache field entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry, or <code>null</code> if a cache field entry with the primary key could not be found
	 */
	public static CacheFieldEntry fetchByPrimaryKey(long cacheFieldEntryId) {
		return getPersistence().fetchByPrimaryKey(cacheFieldEntryId);
	}

	/**
	 * Returns all the cache field entries.
	 *
	 * @return the cache field entries
	 */
	public static List<CacheFieldEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @return the range of cache field entries
	 */
	public static List<CacheFieldEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache field entries
	 */
	public static List<CacheFieldEntry> findAll(
		int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache field entries
	 */
	public static List<CacheFieldEntry> findAll(
		int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cache field entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cache field entries.
	 *
	 * @return the number of cache field entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CacheFieldEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CacheFieldEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CacheFieldEntryPersistence _persistence;

}