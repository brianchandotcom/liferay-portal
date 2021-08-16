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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheMissEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cache miss entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntryUtil
 * @generated
 */
@ProviderType
public interface CacheMissEntryPersistence
	extends BasePersistence<CacheMissEntry>, CTPersistence<CacheMissEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CacheMissEntryUtil} to access the cache miss entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the cache miss entry in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntry the cache miss entry
	 */
	public void cacheResult(CacheMissEntry cacheMissEntry);

	/**
	 * Caches the cache miss entries in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntries the cache miss entries
	 */
	public void cacheResult(java.util.List<CacheMissEntry> cacheMissEntries);

	/**
	 * Creates a new cache miss entry with the primary key. Does not add the cache miss entry to the database.
	 *
	 * @param cacheMissEntryId the primary key for the new cache miss entry
	 * @return the new cache miss entry
	 */
	public CacheMissEntry create(long cacheMissEntryId);

	/**
	 * Removes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry remove(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException;

	public CacheMissEntry updateImpl(CacheMissEntry cacheMissEntry);

	/**
	 * Returns the cache miss entry with the primary key or throws a <code>NoSuchCacheMissEntryException</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry findByPrimaryKey(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException;

	/**
	 * Returns the cache miss entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry, or <code>null</code> if a cache miss entry with the primary key could not be found
	 */
	public CacheMissEntry fetchByPrimaryKey(long cacheMissEntryId);

	/**
	 * Returns all the cache miss entries.
	 *
	 * @return the cache miss entries
	 */
	public java.util.List<CacheMissEntry> findAll();

	/**
	 * Returns a range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @return the range of cache miss entries
	 */
	public java.util.List<CacheMissEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache miss entries
	 */
	public java.util.List<CacheMissEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheMissEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache miss entries
	 */
	public java.util.List<CacheMissEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CacheMissEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cache miss entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cache miss entries.
	 *
	 * @return the number of cache miss entries
	 */
	public int countAll();

}