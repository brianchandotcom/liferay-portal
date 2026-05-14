/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchCacheDisabledEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.CacheDisabledEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cache disabled entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheDisabledEntryUtil
 * @generated
 */
@ProviderType
public interface CacheDisabledEntryPersistence
	extends BasePersistence<CacheDisabledEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CacheDisabledEntryUtil} to access the cache disabled entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the cache disabled entry where name = &#63; or throws a <code>NoSuchCacheDisabledEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching cache disabled entry
	 * @throws NoSuchCacheDisabledEntryException if a matching cache disabled entry could not be found
	 */
	public CacheDisabledEntry findByName(String name)
		throws NoSuchCacheDisabledEntryException;

	/**
	 * Returns the cache disabled entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cache disabled entry, or <code>null</code> if a matching cache disabled entry could not be found
	 */
	public CacheDisabledEntry fetchByName(String name, boolean useFinderCache);

	/**
	 * Removes the cache disabled entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the cache disabled entry that was removed
	 */
	public CacheDisabledEntry removeByName(String name)
		throws NoSuchCacheDisabledEntryException;

	/**
	 * Returns the number of cache disabled entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching cache disabled entries
	 */
	public int countByName(String name);

	/**
	 * Creates a new cache disabled entry with the primary key. Does not add the cache disabled entry to the database.
	 *
	 * @param cacheDisabledEntryId the primary key for the new cache disabled entry
	 * @return the new cache disabled entry
	 */
	public CacheDisabledEntry create(long cacheDisabledEntryId);

	/**
	 * Removes the cache disabled entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheDisabledEntryId the primary key of the cache disabled entry
	 * @return the cache disabled entry that was removed
	 * @throws NoSuchCacheDisabledEntryException if a cache disabled entry with the primary key could not be found
	 */
	public CacheDisabledEntry remove(long cacheDisabledEntryId)
		throws NoSuchCacheDisabledEntryException;

	public CacheDisabledEntry updateImpl(CacheDisabledEntry cacheDisabledEntry);

	/**
	 * Returns the cache disabled entry with the primary key or throws a <code>NoSuchCacheDisabledEntryException</code> if it could not be found.
	 *
	 * @param cacheDisabledEntryId the primary key of the cache disabled entry
	 * @return the cache disabled entry
	 * @throws NoSuchCacheDisabledEntryException if a cache disabled entry with the primary key could not be found
	 */
	public CacheDisabledEntry findByPrimaryKey(long cacheDisabledEntryId)
		throws NoSuchCacheDisabledEntryException;

	/**
	 * Returns the cache disabled entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheDisabledEntryId the primary key of the cache disabled entry
	 * @return the cache disabled entry, or <code>null</code> if a cache disabled entry with the primary key could not be found
	 */
	public CacheDisabledEntry fetchByPrimaryKey(long cacheDisabledEntryId);

	/**
	 * Returns the cache disabled entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching cache disabled entry, or <code>null</code> if a matching cache disabled entry could not be found
	 */
	public default CacheDisabledEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1056522450