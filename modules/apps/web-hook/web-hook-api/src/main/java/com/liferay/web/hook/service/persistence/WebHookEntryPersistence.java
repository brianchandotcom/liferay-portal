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

package com.liferay.web.hook.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.web.hook.exception.NoSuchEntryException;
import com.liferay.web.hook.model.WebHookEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the web hook entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebHookEntryUtil
 * @generated
 */
@ProviderType
public interface WebHookEntryPersistence extends BasePersistence<WebHookEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebHookEntryUtil} to access the web hook entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public WebHookEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public WebHookEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public WebHookEntry[] findByUuid_PrevAndNext(
			long webHookEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the web hook entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching web hook entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	public java.util.List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public WebHookEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public WebHookEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public WebHookEntry[] findByUuid_C_PrevAndNext(
			long webHookEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the web hook entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching web hook entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	public WebHookEntry findByC_D_U(
			long companyId, String destination, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url);

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url, boolean useFinderCache);

	/**
	 * Removes the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the web hook entry that was removed
	 */
	public WebHookEntry removeByC_D_U(
			long companyId, String destination, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the number of web hook entries where companyId = &#63; and destination = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the number of matching web hook entries
	 */
	public int countByC_D_U(long companyId, String destination, String url);

	/**
	 * Caches the web hook entry in the entity cache if it is enabled.
	 *
	 * @param webHookEntry the web hook entry
	 */
	public void cacheResult(WebHookEntry webHookEntry);

	/**
	 * Caches the web hook entries in the entity cache if it is enabled.
	 *
	 * @param webHookEntries the web hook entries
	 */
	public void cacheResult(java.util.List<WebHookEntry> webHookEntries);

	/**
	 * Creates a new web hook entry with the primary key. Does not add the web hook entry to the database.
	 *
	 * @param webHookEntryId the primary key for the new web hook entry
	 * @return the new web hook entry
	 */
	public WebHookEntry create(long webHookEntryId);

	/**
	 * Removes the web hook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry that was removed
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public WebHookEntry remove(long webHookEntryId) throws NoSuchEntryException;

	public WebHookEntry updateImpl(WebHookEntry webHookEntry);

	/**
	 * Returns the web hook entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	public WebHookEntry findByPrimaryKey(long webHookEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the web hook entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry, or <code>null</code> if a web hook entry with the primary key could not be found
	 */
	public WebHookEntry fetchByPrimaryKey(long webHookEntryId);

	/**
	 * Returns all the web hook entries.
	 *
	 * @return the web hook entries
	 */
	public java.util.List<WebHookEntry> findAll();

	/**
	 * Returns a range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of web hook entries
	 */
	public java.util.List<WebHookEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web hook entries
	 */
	public java.util.List<WebHookEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of web hook entries
	 */
	public java.util.List<WebHookEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<WebHookEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the web hook entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of web hook entries.
	 *
	 * @return the number of web hook entries
	 */
	public int countAll();

}