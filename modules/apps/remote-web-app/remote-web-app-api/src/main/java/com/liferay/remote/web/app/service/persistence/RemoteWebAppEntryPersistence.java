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

package com.liferay.remote.web.app.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.remote.web.app.exception.NoSuchEntryException;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the remote web app entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebAppEntryUtil
 * @generated
 */
@ProviderType
public interface RemoteWebAppEntryPersistence
	extends BasePersistence<RemoteWebAppEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RemoteWebAppEntryUtil} to access the remote web app entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public RemoteWebAppEntry[] findByUuid_PrevAndNext(
			long entryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote web app entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of remote web app entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote web app entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns the remote web app entries before and after the current remote web app entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current remote web app entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public RemoteWebAppEntry[] findByUuid_C_PrevAndNext(
			long entryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote web app entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of remote web app entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote web app entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry
	 * @throws NoSuchEntryException if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByC_U(long companyId, String url);

	/**
	 * Returns the remote web app entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public RemoteWebAppEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache);

	/**
	 * Removes the remote web app entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote web app entry that was removed
	 */
	public RemoteWebAppEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the number of remote web app entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote web app entries
	 */
	public int countByC_U(long companyId, String url);

	/**
	 * Caches the remote web app entry in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 */
	public void cacheResult(RemoteWebAppEntry remoteWebAppEntry);

	/**
	 * Caches the remote web app entries in the entity cache if it is enabled.
	 *
	 * @param remoteWebAppEntries the remote web app entries
	 */
	public void cacheResult(
		java.util.List<RemoteWebAppEntry> remoteWebAppEntries);

	/**
	 * Creates a new remote web app entry with the primary key. Does not add the remote web app entry to the database.
	 *
	 * @param entryId the primary key for the new remote web app entry
	 * @return the new remote web app entry
	 */
	public RemoteWebAppEntry create(long entryId);

	/**
	 * Removes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public RemoteWebAppEntry remove(long entryId) throws NoSuchEntryException;

	public RemoteWebAppEntry updateImpl(RemoteWebAppEntry remoteWebAppEntry);

	/**
	 * Returns the remote web app entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws NoSuchEntryException if a remote web app entry with the primary key could not be found
	 */
	public RemoteWebAppEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	 * Returns the remote web app entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry, or <code>null</code> if a remote web app entry with the primary key could not be found
	 */
	public RemoteWebAppEntry fetchByPrimaryKey(long entryId);

	/**
	 * Returns all the remote web app entries.
	 *
	 * @return the remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findAll();

	/**
	 * Returns a range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote web app entries
	 */
	public java.util.List<RemoteWebAppEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RemoteWebAppEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the remote web app entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of remote web app entries.
	 *
	 * @return the number of remote web app entries
	 */
	public int countAll();

}