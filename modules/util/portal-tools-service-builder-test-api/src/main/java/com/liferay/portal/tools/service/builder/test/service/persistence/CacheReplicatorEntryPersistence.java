/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheReplicatorEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cache replicator entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntryUtil
 * @generated
 */
@ProviderType
public interface CacheReplicatorEntryPersistence
	extends BasePersistence<CacheReplicatorEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CacheReplicatorEntryUtil} to access the cache replicator entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

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
	public java.util.List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheReplicatorEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cache replicator entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a matching cache replicator entry could not be found
	 */
	public CacheReplicatorEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CacheReplicatorEntry> orderByComparator)
		throws NoSuchCacheReplicatorEntryException;

	/**
	 * Returns the first cache replicator entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public CacheReplicatorEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CacheReplicatorEntry>
			orderByComparator);

	/**
	 * Removes all the cache replicator entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of cache replicator entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cache replicator entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the cache replicator entry where name = &#63; or throws a <code>NoSuchCacheReplicatorEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a matching cache replicator entry could not be found
	 */
	public CacheReplicatorEntry findByName(String name)
		throws NoSuchCacheReplicatorEntryException;

	/**
	 * Returns the cache replicator entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public CacheReplicatorEntry fetchByName(
		String name, boolean useFinderCache);

	/**
	 * Removes the cache replicator entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the cache replicator entry that was removed
	 */
	public CacheReplicatorEntry removeByName(String name)
		throws NoSuchCacheReplicatorEntryException;

	/**
	 * Returns the number of cache replicator entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching cache replicator entries
	 */
	public int countByName(String name);

	/**
	 * Creates a new cache replicator entry with the primary key. Does not add the cache replicator entry to the database.
	 *
	 * @param cacheReplicatorEntryId the primary key for the new cache replicator entry
	 * @return the new cache replicator entry
	 */
	public CacheReplicatorEntry create(long cacheReplicatorEntryId);

	/**
	 * Removes the cache replicator entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry that was removed
	 * @throws NoSuchCacheReplicatorEntryException if a cache replicator entry with the primary key could not be found
	 */
	public CacheReplicatorEntry remove(long cacheReplicatorEntryId)
		throws NoSuchCacheReplicatorEntryException;

	public CacheReplicatorEntry updateImpl(
		CacheReplicatorEntry cacheReplicatorEntry);

	/**
	 * Returns the cache replicator entry with the primary key or throws a <code>NoSuchCacheReplicatorEntryException</code> if it could not be found.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry
	 * @throws NoSuchCacheReplicatorEntryException if a cache replicator entry with the primary key could not be found
	 */
	public CacheReplicatorEntry findByPrimaryKey(long cacheReplicatorEntryId)
		throws NoSuchCacheReplicatorEntryException;

	/**
	 * Returns the cache replicator entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry, or <code>null</code> if a cache replicator entry with the primary key could not be found
	 */
	public CacheReplicatorEntry fetchByPrimaryKey(long cacheReplicatorEntryId);

	/**
	 * Returns the cache replicator entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching cache replicator entry, or <code>null</code> if a matching cache replicator entry could not be found
	 */
	public default CacheReplicatorEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns all the cache replicator entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cache replicator entries
	 */
	public default java.util.List<CacheReplicatorEntry> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
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
	public default java.util.List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
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
	public default java.util.List<CacheReplicatorEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheReplicatorEntry>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1288119525