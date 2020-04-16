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

package com.liferay.redirect.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.redirect.exception.NoSuchEntryException;
import com.liferay.redirect.model.RedirectEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the redirect entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntryUtil
 * @generated
 */
@ProviderType
public interface RedirectEntryPersistence
	extends BasePersistence<RedirectEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RedirectEntryUtil} to access the redirect entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the redirect entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where uuid = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] findByUuid_PrevAndNext(
			long redirectEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the redirect entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of redirect entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching redirect entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the redirect entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the redirect entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the redirect entry that was removed
	 */
	public RedirectEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of redirect entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching redirect entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] findByUuid_C_PrevAndNext(
			long redirectEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the redirect entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of redirect entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching redirect entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the redirect entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where groupId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] findByGroupId_PrevAndNext(
			long redirectEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set of redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] filterFindByGroupId_PrevAndNext(
			long redirectEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the redirect entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of redirect entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of redirect entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect entries that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByG_D(
		long groupId, String destinationURL);

	/**
	 * Returns a range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect entries
	 */
	public java.util.List<RedirectEntry> findByG_D(
		long groupId, String destinationURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByG_D_First(
			long groupId, String destinationURL,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByG_D_First(
		long groupId, String destinationURL,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByG_D_Last(
			long groupId, String destinationURL,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByG_D_Last(
		long groupId, String destinationURL,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] findByG_D_PrevAndNext(
			long redirectEntryId, long groupId, String destinationURL,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL);

	/**
	 * Returns a range of all the redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL, int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries that the user has permissions to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect entries that the user has permission to view
	 */
	public java.util.List<RedirectEntry> filterFindByG_D(
		long groupId, String destinationURL, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns the redirect entries before and after the current redirect entry in the ordered set of redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param redirectEntryId the primary key of the current redirect entry
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry[] filterFindByG_D_PrevAndNext(
			long redirectEntryId, long groupId, String destinationURL,
			com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the redirect entries where groupId = &#63; and destinationURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 */
	public void removeByG_D(long groupId, String destinationURL);

	/**
	 * Returns the number of redirect entries where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the number of matching redirect entries
	 */
	public int countByG_D(long groupId, String destinationURL);

	/**
	 * Returns the number of redirect entries that the user has permission to view where groupId = &#63; and destinationURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param destinationURL the destination url
	 * @return the number of matching redirect entries that the user has permission to view
	 */
	public int filterCountByG_D(long groupId, String destinationURL);

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the matching redirect entry
	 * @throws NoSuchEntryException if a matching redirect entry could not be found
	 */
	public RedirectEntry findByG_S(long groupId, String sourceURL)
		throws NoSuchEntryException;

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByG_S(long groupId, String sourceURL);

	/**
	 * Returns the redirect entry where groupId = &#63; and sourceURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public RedirectEntry fetchByG_S(
		long groupId, String sourceURL, boolean useFinderCache);

	/**
	 * Removes the redirect entry where groupId = &#63; and sourceURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the redirect entry that was removed
	 */
	public RedirectEntry removeByG_S(long groupId, String sourceURL)
		throws NoSuchEntryException;

	/**
	 * Returns the number of redirect entries where groupId = &#63; and sourceURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param sourceURL the source url
	 * @return the number of matching redirect entries
	 */
	public int countByG_S(long groupId, String sourceURL);

	/**
	 * Caches the redirect entry in the entity cache if it is enabled.
	 *
	 * @param redirectEntry the redirect entry
	 */
	public void cacheResult(RedirectEntry redirectEntry);

	/**
	 * Caches the redirect entries in the entity cache if it is enabled.
	 *
	 * @param redirectEntries the redirect entries
	 */
	public void cacheResult(java.util.List<RedirectEntry> redirectEntries);

	/**
	 * Creates a new redirect entry with the primary key. Does not add the redirect entry to the database.
	 *
	 * @param redirectEntryId the primary key for the new redirect entry
	 * @return the new redirect entry
	 */
	public RedirectEntry create(long redirectEntryId);

	/**
	 * Removes the redirect entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry that was removed
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry remove(long redirectEntryId)
		throws NoSuchEntryException;

	public RedirectEntry updateImpl(RedirectEntry redirectEntry);

	/**
	 * Returns the redirect entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry
	 * @throws NoSuchEntryException if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry findByPrimaryKey(long redirectEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the redirect entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry, or <code>null</code> if a redirect entry with the primary key could not be found
	 */
	public RedirectEntry fetchByPrimaryKey(long redirectEntryId);

	/**
	 * Returns all the redirect entries.
	 *
	 * @return the redirect entries
	 */
	public java.util.List<RedirectEntry> findAll();

	/**
	 * Returns a range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of redirect entries
	 */
	public java.util.List<RedirectEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redirect entries
	 */
	public java.util.List<RedirectEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redirect entries
	 */
	public java.util.List<RedirectEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RedirectEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the redirect entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of redirect entries.
	 *
	 * @return the number of redirect entries
	 */
	public int countAll();

}