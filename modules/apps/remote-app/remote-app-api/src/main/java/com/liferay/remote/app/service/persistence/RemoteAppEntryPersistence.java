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

package com.liferay.remote.app.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.RemoteAppEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the remote app entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryUtil
 * @generated
 */
@ProviderType
public interface RemoteAppEntryPersistence
	extends BasePersistence<RemoteAppEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RemoteAppEntryUtil} to access the remote app entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public RemoteAppEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public RemoteAppEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public RemoteAppEntry[] findByUuid_PrevAndNext(
			long entryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of remote app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote app entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entries
	 */
	public java.util.List<RemoteAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public RemoteAppEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public RemoteAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns the remote app entries before and after the current remote app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current remote app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public RemoteAppEntry[] findByUuid_C_PrevAndNext(
			long entryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of remote app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote app entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry
	 * @throws NoSuchEntryException if a matching remote app entry could not be found
	 */
	public RemoteAppEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByC_U(long companyId, String url);

	/**
	 * Returns the remote app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public RemoteAppEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache);

	/**
	 * Removes the remote app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote app entry that was removed
	 */
	public RemoteAppEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the number of remote app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote app entries
	 */
	public int countByC_U(long companyId, String url);

	/**
	 * Caches the remote app entry in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntry the remote app entry
	 */
	public void cacheResult(RemoteAppEntry remoteAppEntry);

	/**
	 * Caches the remote app entries in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntries the remote app entries
	 */
	public void cacheResult(java.util.List<RemoteAppEntry> remoteAppEntries);

	/**
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param entryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	public RemoteAppEntry create(long entryId);

	/**
	 * Removes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public RemoteAppEntry remove(long entryId) throws NoSuchEntryException;

	public RemoteAppEntry updateImpl(RemoteAppEntry remoteAppEntry);

	/**
	 * Returns the remote app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws NoSuchEntryException if a remote app entry with the primary key could not be found
	 */
	public RemoteAppEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	 * Returns the remote app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote app entry
	 * @return the remote app entry, or <code>null</code> if a remote app entry with the primary key could not be found
	 */
	public RemoteAppEntry fetchByPrimaryKey(long entryId);

	/**
	 * Returns all the remote app entries.
	 *
	 * @return the remote app entries
	 */
	public java.util.List<RemoteAppEntry> findAll();

	/**
	 * Returns a range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	public java.util.List<RemoteAppEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote app entries
	 */
	public java.util.List<RemoteAppEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote app entries
	 */
	public java.util.List<RemoteAppEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the remote app entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	public int countAll();

}