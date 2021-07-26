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
import com.liferay.remote.app.exception.NoSuchRemoteCustomElementEntryException;
import com.liferay.remote.app.model.RemoteCustomElementEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the remote custom element entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteCustomElementEntryUtil
 * @generated
 */
@ProviderType
public interface RemoteCustomElementEntryPersistence
	extends BasePersistence<RemoteCustomElementEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RemoteCustomElementEntryUtil} to access the remote custom element entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the remote custom element entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns the remote custom element entries before and after the current remote custom element entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteCustomElementEntryId the primary key of the current remote custom element entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public RemoteCustomElementEntry[] findByUuid_PrevAndNext(
			long remoteCustomElementEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Removes all the remote custom element entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of remote custom element entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote custom element entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the first remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the last remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns the remote custom element entries before and after the current remote custom element entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param remoteCustomElementEntryId the primary key of the current remote custom element entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public RemoteCustomElementEntry[] findByUuid_C_PrevAndNext(
			long remoteCustomElementEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteCustomElementEntry> orderByComparator)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Removes all the remote custom element entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of remote custom element entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote custom element entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchRemoteCustomElementEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry findByC_U(long companyId, String url)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByC_U(long companyId, String url);

	/**
	 * Returns the remote custom element entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	public RemoteCustomElementEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache);

	/**
	 * Removes the remote custom element entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote custom element entry that was removed
	 */
	public RemoteCustomElementEntry removeByC_U(long companyId, String url)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the number of remote custom element entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote custom element entries
	 */
	public int countByC_U(long companyId, String url);

	/**
	 * Caches the remote custom element entry in the entity cache if it is enabled.
	 *
	 * @param remoteCustomElementEntry the remote custom element entry
	 */
	public void cacheResult(RemoteCustomElementEntry remoteCustomElementEntry);

	/**
	 * Caches the remote custom element entries in the entity cache if it is enabled.
	 *
	 * @param remoteCustomElementEntries the remote custom element entries
	 */
	public void cacheResult(
		java.util.List<RemoteCustomElementEntry> remoteCustomElementEntries);

	/**
	 * Creates a new remote custom element entry with the primary key. Does not add the remote custom element entry to the database.
	 *
	 * @param remoteCustomElementEntryId the primary key for the new remote custom element entry
	 * @return the new remote custom element entry
	 */
	public RemoteCustomElementEntry create(long remoteCustomElementEntryId);

	/**
	 * Removes the remote custom element entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry that was removed
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public RemoteCustomElementEntry remove(long remoteCustomElementEntryId)
		throws NoSuchRemoteCustomElementEntryException;

	public RemoteCustomElementEntry updateImpl(
		RemoteCustomElementEntry remoteCustomElementEntry);

	/**
	 * Returns the remote custom element entry with the primary key or throws a <code>NoSuchRemoteCustomElementEntryException</code> if it could not be found.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry
	 * @throws NoSuchRemoteCustomElementEntryException if a remote custom element entry with the primary key could not be found
	 */
	public RemoteCustomElementEntry findByPrimaryKey(
			long remoteCustomElementEntryId)
		throws NoSuchRemoteCustomElementEntryException;

	/**
	 * Returns the remote custom element entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry, or <code>null</code> if a remote custom element entry with the primary key could not be found
	 */
	public RemoteCustomElementEntry fetchByPrimaryKey(
		long remoteCustomElementEntryId);

	/**
	 * Returns all the remote custom element entries.
	 *
	 * @return the remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findAll();

	/**
	 * Returns a range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote custom element entries
	 */
	public java.util.List<RemoteCustomElementEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteCustomElementEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the remote custom element entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of remote custom element entries.
	 *
	 * @return the number of remote custom element entries
	 */
	public int countAll();

}