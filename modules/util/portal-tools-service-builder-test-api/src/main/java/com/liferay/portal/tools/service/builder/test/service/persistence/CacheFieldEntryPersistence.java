/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheFieldEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cache field entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntryUtil
 * @generated
 */
@ProviderType
public interface CacheFieldEntryPersistence
	extends BasePersistence<CacheFieldEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CacheFieldEntryUtil} to access the cache field entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cache field entries
	 */
	public java.util.List<CacheFieldEntry> findByGroupId(long groupId);

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
	public java.util.List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator);

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
	public java.util.List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	public CacheFieldEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
				orderByComparator)
		throws NoSuchCacheFieldEntryException;

	/**
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	public CacheFieldEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator);

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	public CacheFieldEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
				orderByComparator)
		throws NoSuchCacheFieldEntryException;

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	public CacheFieldEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator);

	/**
	 * Returns the cache field entries before and after the current cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param cacheFieldEntryId the primary key of the current cache field entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public CacheFieldEntry[] findByGroupId_PrevAndNext(
			long cacheFieldEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
				orderByComparator)
		throws NoSuchCacheFieldEntryException;

	/**
	 * Removes all the cache field entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cache field entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the cache field entry in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntry the cache field entry
	 */
	public void cacheResult(CacheFieldEntry cacheFieldEntry);

	/**
	 * Caches the cache field entries in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntries the cache field entries
	 */
	public void cacheResult(java.util.List<CacheFieldEntry> cacheFieldEntries);

	/**
	 * Creates a new cache field entry with the primary key. Does not add the cache field entry to the database.
	 *
	 * @param cacheFieldEntryId the primary key for the new cache field entry
	 * @return the new cache field entry
	 */
	public CacheFieldEntry create(long cacheFieldEntryId);

	/**
	 * Removes the cache field entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry that was removed
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public CacheFieldEntry remove(long cacheFieldEntryId)
		throws NoSuchCacheFieldEntryException;

	public CacheFieldEntry updateImpl(CacheFieldEntry cacheFieldEntry);

	/**
	 * Returns the cache field entry with the primary key or throws a <code>NoSuchCacheFieldEntryException</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	public CacheFieldEntry findByPrimaryKey(long cacheFieldEntryId)
		throws NoSuchCacheFieldEntryException;

	/**
	 * Returns the cache field entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry, or <code>null</code> if a cache field entry with the primary key could not be found
	 */
	public CacheFieldEntry fetchByPrimaryKey(long cacheFieldEntryId);

	/**
	 * Returns all the cache field entries.
	 *
	 * @return the cache field entries
	 */
	public java.util.List<CacheFieldEntry> findAll();

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
	public java.util.List<CacheFieldEntry> findAll(int start, int end);

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
	public java.util.List<CacheFieldEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator);

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
	public java.util.List<CacheFieldEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheFieldEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cache field entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cache field entries.
	 *
	 * @return the number of cache field entries
	 */
	public int countAll();

}