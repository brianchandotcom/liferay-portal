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

import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLoadFinderCacheEntryException;
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the load finder cache entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LoadFinderCacheEntryUtil
 * @generated
 */
@ProviderType
public interface LoadFinderCacheEntryPersistence
	extends BasePersistence<LoadFinderCacheEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LoadFinderCacheEntryUtil} to access the load finder cache entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	public FinderPath getFinderPathWithoutPaginationFindAll();

	public FinderPath getFinderPathCountAll();

	public FinderPath getFinderPathFetchByUniqueName();

	public FinderPath getFinderPathCountByUniqueName();

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry findByUniqueName(String uniqueName)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByUniqueName(String uniqueName);

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByUniqueName(
		String uniqueName, boolean useFinderCache);

	/**
	 * Removes the load finder cache entry where uniqueName = &#63; from the database.
	 *
	 * @param uniqueName the unique name
	 * @return the load finder cache entry that was removed
	 */
	public LoadFinderCacheEntry removeByUniqueName(String uniqueName)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the number of load finder cache entries where uniqueName = &#63;.
	 *
	 * @param uniqueName the unique name
	 * @return the number of matching load finder cache entries
	 */
	public int countByUniqueName(String uniqueName);

	public FinderPath getFinderPathWithoutPaginationFindByGroupId();

	public FinderPath getFinderPathCountByGroupId();

	/**
	 * Returns all the load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	public java.util.List<LoadFinderCacheEntry> findByGroupId(long groupId);

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
	public java.util.List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

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
	public java.util.List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

	/**
	 * Returns the load finder cache entries before and after the current load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param loadFinderCacheEntryId the primary key of the current load finder cache entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public LoadFinderCacheEntry[] findByGroupId_PrevAndNext(
			long loadFinderCacheEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Removes all the load finder cache entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	public int countByGroupId(long groupId);

	public FinderPath getFinderPathWithoutPaginationFindByC_G();

	public FinderPath getFinderPathCountByC_G();

	/**
	 * Returns all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	public java.util.List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId);

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
	public java.util.List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end);

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
	public java.util.List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

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
	public java.util.List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry findByC_G_First(
			long companyId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByC_G_First(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry findByC_G_Last(
			long companyId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	public LoadFinderCacheEntry fetchByC_G_Last(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

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
	public LoadFinderCacheEntry[] findByC_G_PrevAndNext(
			long loadFinderCacheEntryId, long companyId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Removes all the load finder cache entries where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	public void removeByC_G(long companyId, long groupId);

	/**
	 * Returns the number of load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	public int countByC_G(long companyId, long groupId);

	/**
	 * Caches the load finder cache entry in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 */
	public void cacheResult(LoadFinderCacheEntry loadFinderCacheEntry);

	/**
	 * Caches the load finder cache entries in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntries the load finder cache entries
	 */
	public void cacheResult(
		java.util.List<LoadFinderCacheEntry> loadFinderCacheEntries);

	/**
	 * Creates a new load finder cache entry with the primary key. Does not add the load finder cache entry to the database.
	 *
	 * @param loadFinderCacheEntryId the primary key for the new load finder cache entry
	 * @return the new load finder cache entry
	 */
	public LoadFinderCacheEntry create(long loadFinderCacheEntryId);

	/**
	 * Removes the load finder cache entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry that was removed
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public LoadFinderCacheEntry remove(long loadFinderCacheEntryId)
		throws NoSuchLoadFinderCacheEntryException;

	public LoadFinderCacheEntry updateImpl(
		LoadFinderCacheEntry loadFinderCacheEntry);

	/**
	 * Returns the load finder cache entry with the primary key or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	public LoadFinderCacheEntry findByPrimaryKey(long loadFinderCacheEntryId)
		throws NoSuchLoadFinderCacheEntryException;

	/**
	 * Returns the load finder cache entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry, or <code>null</code> if a load finder cache entry with the primary key could not be found
	 */
	public LoadFinderCacheEntry fetchByPrimaryKey(long loadFinderCacheEntryId);

	/**
	 * Returns all the load finder cache entries.
	 *
	 * @return the load finder cache entries
	 */
	public java.util.List<LoadFinderCacheEntry> findAll();

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
	public java.util.List<LoadFinderCacheEntry> findAll(int start, int end);

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
	public java.util.List<LoadFinderCacheEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator);

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
	public java.util.List<LoadFinderCacheEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LoadFinderCacheEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the load finder cache entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of load finder cache entries.
	 *
	 * @return the number of load finder cache entries
	 */
	public int countAll();

}