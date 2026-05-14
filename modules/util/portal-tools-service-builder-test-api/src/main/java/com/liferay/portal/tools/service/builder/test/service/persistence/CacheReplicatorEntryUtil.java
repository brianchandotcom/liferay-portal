/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cache replicator entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.CacheReplicatorEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntryPersistence
 * @generated
 */
public class CacheReplicatorEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<CacheReplicatorEntry> cacheReplicatorEntries) {

		getPersistence().cacheResult(cacheReplicatorEntries);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(CacheReplicatorEntry cacheReplicatorEntry) {
		getPersistence().cacheResult(cacheReplicatorEntry);
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
	public static void clearCache(CacheReplicatorEntry cacheReplicatorEntry) {
		getPersistence().clearCache(cacheReplicatorEntry);
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
	public static Map<Serializable, CacheReplicatorEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CacheReplicatorEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CacheReplicatorEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CacheReplicatorEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CacheReplicatorEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CacheReplicatorEntry update(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return getPersistence().update(cacheReplicatorEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CacheReplicatorEntry update(
		CacheReplicatorEntry cacheReplicatorEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(cacheReplicatorEntry, serviceContext);
	}

	/**
	 * Returns an ordered range of all the cache replicator entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cache replicator entries
	 * @param end the upper bound of the range of cache replicator entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cache replicator entries
	 */
	public static List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CacheReplicatorEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cache replicator entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a matching cache replicator entry could not be found
	 */
	public static CacheReplicatorEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CacheReplicatorEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheReplicatorEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first cache replicator entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public static CacheReplicatorEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CacheReplicatorEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Removes all the cache replicator entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of cache replicator entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cache replicator entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the cache replicator entry where name = &#63; or throws a <code>NoSuchCacheReplicatorEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a matching cache replicator entry could not be found
	 */
	public static CacheReplicatorEntry findByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheReplicatorEntryException {

		return getPersistence().findByName(name);
	}

	/**
	 * Returns the cache replicator entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public static CacheReplicatorEntry fetchByName(
		String name, boolean useFinderCache) {

		return getPersistence().fetchByName(name, useFinderCache);
	}

	/**
	 * Removes the cache replicator entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the cache replicator entry that was removed
	 */
	public static CacheReplicatorEntry removeByName(String name)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheReplicatorEntryException {

		return getPersistence().removeByName(name);
	}

	/**
	 * Returns the number of cache replicator entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching cache replicator entries
	 */
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	 * Creates a new cache replicator entry with the primary key. Does not add the cache replicator entry to the database.
	 *
	 * @param cacheReplicatorEntryId the primary key for the new cache replicator entry
	 * @return the new cache replicator entry
	 */
	public static CacheReplicatorEntry create(long cacheReplicatorEntryId) {
		return getPersistence().create(cacheReplicatorEntryId);
	}

	/**
	 * Removes the cache replicator entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry that was removed
	 * @throws NoSuchCacheReplicatorEntryException if a cache replicator entry with the primary key could not be found
	 */
	public static CacheReplicatorEntry remove(long cacheReplicatorEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheReplicatorEntryException {

		return getPersistence().remove(cacheReplicatorEntryId);
	}

	public static CacheReplicatorEntry updateImpl(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return getPersistence().updateImpl(cacheReplicatorEntry);
	}

	/**
	 * Returns the cache replicator entry with the primary key or throws a <code>NoSuchCacheReplicatorEntryException</code> if it could not be found.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a cache replicator entry with the primary key could not be found
	 */
	public static CacheReplicatorEntry findByPrimaryKey(
			long cacheReplicatorEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchCacheReplicatorEntryException {

		return getPersistence().findByPrimaryKey(cacheReplicatorEntryId);
	}

	/**
	 * Returns the cache replicator entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry, or <code>null</code> if a cache replicator entry with the primary key could not be found
	 */
	public static CacheReplicatorEntry fetchByPrimaryKey(
		long cacheReplicatorEntryId) {

		return getPersistence().fetchByPrimaryKey(cacheReplicatorEntryId);
	}

	/**
	 * Returns the cache replicator entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public static CacheReplicatorEntry fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	 * Returns all the cache replicator entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cache replicator entries
	 */
	public static List<CacheReplicatorEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the cache replicator entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cache replicator entries
	 * @param end the upper bound of the range of cache replicator entries (not inclusive)
	 * @return the range of matching cache replicator entries
	 */
	public static List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cache replicator entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cache replicator entries
	 * @param end the upper bound of the range of cache replicator entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cache replicator entries
	 */
	public static List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CacheReplicatorEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	public static CacheReplicatorEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CacheReplicatorEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CacheReplicatorEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1758280925