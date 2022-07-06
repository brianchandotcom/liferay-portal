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

package com.liferay.list.type.service.persistence;

import com.liferay.list.type.exception.NoSuchListTypeEntryException;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the list type entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see ListTypeEntryUtil
 * @generated
 */
@ProviderType
public interface ListTypeEntryPersistence
	extends BasePersistence<ListTypeEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ListTypeEntryUtil} to access the list type entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry[] findByUuid_PrevAndNext(
			long listTypeEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Removes all the list type entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry[] findByUuid_C_PrevAndNext(
			long listTypeEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Removes all the list type entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId);

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByListTypeEntryId_First(
			long listTypeEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByListTypeEntryId_First(
		long listTypeEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByListTypeEntryId_Last(
			long listTypeEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds);

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end);

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the list type entries where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	public void removeByListTypeEntryId(long listTypeEntryId);

	/**
	 * Returns the number of list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching list type entries
	 */
	public int countByListTypeEntryId(long listTypeEntryId);

	/**
	 * Returns the number of list type entries where listTypeEntryId = any &#63;.
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the number of matching list type entries
	 */
	public int countByListTypeEntryId(long[] listTypeEntryIds);

	/**
	 * Returns all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId);

	/**
	 * Returns a range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	public java.util.List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry[] findByListTypeDefinitionId_PrevAndNext(
			long listTypeEntryId, long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
				orderByComparator)
		throws NoSuchListTypeEntryException;

	/**
	 * Removes all the list type entries where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public void removeByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching list type entries
	 */
	public int countByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	public ListTypeEntry findByLTDI_K(long listTypeDefinitionId, String key)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByLTDI_K(long listTypeDefinitionId, String key);

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	public ListTypeEntry fetchByLTDI_K(
		long listTypeDefinitionId, String key, boolean useFinderCache);

	/**
	 * Removes the list type entry where listTypeDefinitionId = &#63; and key = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the list type entry that was removed
	 */
	public ListTypeEntry removeByLTDI_K(long listTypeDefinitionId, String key)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63; and key = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the number of matching list type entries
	 */
	public int countByLTDI_K(long listTypeDefinitionId, String key);

	/**
	 * Caches the list type entry in the entity cache if it is enabled.
	 *
	 * @param listTypeEntry the list type entry
	 */
	public void cacheResult(ListTypeEntry listTypeEntry);

	/**
	 * Caches the list type entries in the entity cache if it is enabled.
	 *
	 * @param listTypeEntries the list type entries
	 */
	public void cacheResult(java.util.List<ListTypeEntry> listTypeEntries);

	/**
	 * Creates a new list type entry with the primary key. Does not add the list type entry to the database.
	 *
	 * @param listTypeEntryId the primary key for the new list type entry
	 * @return the new list type entry
	 */
	public ListTypeEntry create(long listTypeEntryId);

	/**
	 * Removes the list type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry that was removed
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry remove(long listTypeEntryId)
		throws NoSuchListTypeEntryException;

	public ListTypeEntry updateImpl(ListTypeEntry listTypeEntry);

	/**
	 * Returns the list type entry with the primary key or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry findByPrimaryKey(long listTypeEntryId)
		throws NoSuchListTypeEntryException;

	/**
	 * Returns the list type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry, or <code>null</code> if a list type entry with the primary key could not be found
	 */
	public ListTypeEntry fetchByPrimaryKey(long listTypeEntryId);

	/**
	 * Returns all the list type entries.
	 *
	 * @return the list type entries
	 */
	public java.util.List<ListTypeEntry> findAll();

	/**
	 * Returns a range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of list type entries
	 */
	public java.util.List<ListTypeEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list type entries
	 */
	public java.util.List<ListTypeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list type entries
	 */
	public java.util.List<ListTypeEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ListTypeEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the list type entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of list type entries.
	 *
	 * @return the number of list type entries
	 */
	public int countAll();

}