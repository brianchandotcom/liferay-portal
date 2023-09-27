/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheDisabledEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry;

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
	 * Returns the cache disabled entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching cache disabled entry, or <code>null</code> if a matching cache disabled entry could not be found
	 */
	public CacheDisabledEntry fetchByName(String name);

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
	 * Caches the cache disabled entry in the entity cache if it is enabled.
	 *
	 * @param cacheDisabledEntry the cache disabled entry
	 */
	public void cacheResult(CacheDisabledEntry cacheDisabledEntry);

	/**
	 * Caches the cache disabled entries in the entity cache if it is enabled.
	 *
	 * @param cacheDisabledEntries the cache disabled entries
	 */
	public void cacheResult(
		java.util.List<CacheDisabledEntry> cacheDisabledEntries);

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
	 * Returns all the cache disabled entries.
	 *
	 * @return the cache disabled entries
	 */
	public java.util.List<CacheDisabledEntry> findAll();

	/**
	 * Returns a range of all the cache disabled entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheDisabledEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache disabled entries
	 * @param end the upper bound of the range of cache disabled entries (not inclusive)
	 * @return the range of cache disabled entries
	 */
	public java.util.List<CacheDisabledEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cache disabled entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheDisabledEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache disabled entries
	 * @param end the upper bound of the range of cache disabled entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache disabled entries
	 */
	public java.util.List<CacheDisabledEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheDisabledEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cache disabled entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheDisabledEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache disabled entries
	 * @param end the upper bound of the range of cache disabled entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache disabled entries
	 */
	public java.util.List<CacheDisabledEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheDisabledEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cache disabled entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cache disabled entries.
	 *
	 * @return the number of cache disabled entries
	 */
	public int countAll();

}