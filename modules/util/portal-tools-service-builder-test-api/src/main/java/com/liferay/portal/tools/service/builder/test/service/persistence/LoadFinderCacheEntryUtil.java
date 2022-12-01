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
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the load finder cache entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LoadFinderCacheEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LoadFinderCacheEntryPersistence
 * @generated
 */
public class LoadFinderCacheEntryUtil {

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
	public static void clearCache(LoadFinderCacheEntry loadFinderCacheEntry) {
		getPersistence().clearCache(loadFinderCacheEntry);
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
	public static Map<Serializable, LoadFinderCacheEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LoadFinderCacheEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LoadFinderCacheEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LoadFinderCacheEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LoadFinderCacheEntry update(
		LoadFinderCacheEntry loadFinderCacheEntry) {

		return getPersistence().update(loadFinderCacheEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LoadFinderCacheEntry update(
		LoadFinderCacheEntry loadFinderCacheEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(loadFinderCacheEntry, serviceContext);
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathWithoutPaginationFindAll() {

		return getPersistence().getFinderPathWithoutPaginationFindAll();
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathCountAll() {

		return getPersistence().getFinderPathCountAll();
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathFetchByUniqueName() {

		return getPersistence().getFinderPathFetchByUniqueName();
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathCountByUniqueName() {

		return getPersistence().getFinderPathCountByUniqueName();
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry findByUniqueName(String uniqueName)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByUniqueName(uniqueName);
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByUniqueName(String uniqueName) {
		return getPersistence().fetchByUniqueName(uniqueName);
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByUniqueName(
		String uniqueName, boolean useFinderCache) {

		return getPersistence().fetchByUniqueName(uniqueName, useFinderCache);
	}

	/**
	 * Removes the load finder cache entry where uniqueName = &#63; from the database.
	 *
	 * @param uniqueName the unique name
	 * @return the load finder cache entry that was removed
	 */
	public static LoadFinderCacheEntry removeByUniqueName(String uniqueName)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().removeByUniqueName(uniqueName);
	}

	/**
	 * Returns the number of load finder cache entries where uniqueName = &#63;.
	 *
	 * @param uniqueName the unique name
	 * @return the number of matching load finder cache entries
	 */
	public static int countByUniqueName(String uniqueName) {
		return getPersistence().countByUniqueName(uniqueName);
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathWithoutPaginationFindByGroupId() {

		return getPersistence().getFinderPathWithoutPaginationFindByGroupId();
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathCountByGroupId() {

		return getPersistence().getFinderPathCountByGroupId();
	}

	/**
	 * Returns all the load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry findByGroupId_First(
			long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the load finder cache entries before and after the current load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param loadFinderCacheEntryId the primary key of the current load finder cache entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public static LoadFinderCacheEntry[] findByGroupId_PrevAndNext(
			long loadFinderCacheEntryId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			loadFinderCacheEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the load finder cache entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathWithoutPaginationFindByC_G() {

		return getPersistence().getFinderPathWithoutPaginationFindByC_G();
	}

	public static com.liferay.portal.kernel.dao.orm.FinderPath
		getFinderPathCountByC_G() {

		return getPersistence().getFinderPathCountByC_G();
	}

	/**
	 * Returns all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId) {

		return getPersistence().findByC_G(companyId, groupId);
	}

	/**
	 * Returns a range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end) {

		return getPersistence().findByC_G(companyId, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().findByC_G(
			companyId, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_G(
			companyId, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry findByC_G_First(
			long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByC_G_First(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByC_G_First(
		long companyId, long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().fetchByC_G_First(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry findByC_G_Last(
			long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByC_G_Last(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public static LoadFinderCacheEntry fetchByC_G_Last(
		long companyId, long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().fetchByC_G_Last(
			companyId, groupId, orderByComparator);
	}

	/**
	 * Returns the load finder cache entries before and after the current load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param loadFinderCacheEntryId the primary key of the current load finder cache entry
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public static LoadFinderCacheEntry[] findByC_G_PrevAndNext(
			long loadFinderCacheEntryId, long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByC_G_PrevAndNext(
			loadFinderCacheEntryId, companyId, groupId, orderByComparator);
	}

	/**
	 * Removes all the load finder cache entries where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	public static void removeByC_G(long companyId, long groupId) {
		getPersistence().removeByC_G(companyId, groupId);
	}

	/**
	 * Returns the number of load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	public static int countByC_G(long companyId, long groupId) {
		return getPersistence().countByC_G(companyId, groupId);
	}

	/**
	 * Caches the load finder cache entry in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 */
	public static void cacheResult(LoadFinderCacheEntry loadFinderCacheEntry) {
		getPersistence().cacheResult(loadFinderCacheEntry);
	}

	/**
	 * Caches the load finder cache entries in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntries the load finder cache entries
	 */
	public static void cacheResult(
		List<LoadFinderCacheEntry> loadFinderCacheEntries) {

		getPersistence().cacheResult(loadFinderCacheEntries);
	}

	/**
	 * Creates a new load finder cache entry with the primary key. Does not add the load finder cache entry to the database.
	 *
	 * @param loadFinderCacheEntryId the primary key for the new load finder cache entry
	 * @return the new load finder cache entry
	 */
	public static LoadFinderCacheEntry create(long loadFinderCacheEntryId) {
		return getPersistence().create(loadFinderCacheEntryId);
	}

	/**
	 * Removes the load finder cache entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry that was removed
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public static LoadFinderCacheEntry remove(long loadFinderCacheEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().remove(loadFinderCacheEntryId);
	}

	public static LoadFinderCacheEntry updateImpl(
		LoadFinderCacheEntry loadFinderCacheEntry) {

		return getPersistence().updateImpl(loadFinderCacheEntry);
	}

	/**
	 * Returns the load finder cache entry with the primary key or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public static LoadFinderCacheEntry findByPrimaryKey(
			long loadFinderCacheEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLoadFinderCacheEntryException {

		return getPersistence().findByPrimaryKey(loadFinderCacheEntryId);
	}

	/**
	 * Returns the load finder cache entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry, or <code>null</code> if a load finder cache entry with the primary key could not be found
	 */
	public static LoadFinderCacheEntry fetchByPrimaryKey(
		long loadFinderCacheEntryId) {

		return getPersistence().fetchByPrimaryKey(loadFinderCacheEntryId);
	}

	/**
	 * Returns all the load finder cache entries.
	 *
	 * @return the load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findAll(
		int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of load finder cache entries
	 */
	public static List<LoadFinderCacheEntry> findAll(
		int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the load finder cache entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of load finder cache entries.
	 *
	 * @return the number of load finder cache entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LoadFinderCacheEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile LoadFinderCacheEntryPersistence _persistence;

}