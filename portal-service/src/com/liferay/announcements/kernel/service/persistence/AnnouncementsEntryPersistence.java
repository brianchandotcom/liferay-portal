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

package com.liferay.announcements.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.announcements.kernel.exception.NoSuchEntryException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the announcements entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.announcements.service.persistence.impl.AnnouncementsEntryPersistenceImpl
 * @see AnnouncementsEntryUtil
 * @generated
 */
@ProviderType
public interface AnnouncementsEntryPersistence extends BasePersistence<AnnouncementsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsEntryUtil} to access the announcements entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the announcements entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the announcements entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] findByUuid_PrevAndNext(long entryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the announcements entries that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] filterFindByUuid_PrevAndNext(long entryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the announcements entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of announcements entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching announcements entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of announcements entries that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching announcements entries that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] findByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] filterFindByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the announcements entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of announcements entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching announcements entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of announcements entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching announcements entries that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the announcements entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUserId(long userId);

	/**
	* Returns a range of all the announcements entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the announcements entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the last announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last announcements entry in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where userId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] findByUserId_PrevAndNext(long entryId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUserId(long userId);

	/**
	* Returns a range of all the announcements entries that the user has permission to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] filterFindByUserId_PrevAndNext(long entryId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the announcements entries where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of announcements entries where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements entries
	*/
	public int countByUserId(long userId);

	/**
	* Returns the number of announcements entries that the user has permission to view where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching announcements entries that the user has permission to view
	*/
	public int filterCountByUserId(long userId);

	/**
	* Returns all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] findByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK);

	/**
	* Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] filterFindByC_C_PrevAndNext(long entryId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the announcements entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of announcements entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching announcements entries
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching announcements entries that the user has permission to view
	*/
	public int filterCountByC_C(long classNameId, long classPK);

	/**
	* Returns all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C_A(long classNameId,
		long classPK, boolean alert);

	/**
	* Returns a range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C_A(long classNameId,
		long classPK, boolean alert, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C_A(long classNameId,
		long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findByC_C_A(long classNameId,
		long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByC_C_A_First(long classNameId, long classPK,
		boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByC_C_A_First(long classNameId,
		long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry
	* @throws NoSuchEntryException if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry findByC_C_A_Last(long classNameId, long classPK,
		boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	public AnnouncementsEntry fetchByC_C_A_Last(long classNameId, long classPK,
		boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] findByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert);

	/**
	* Returns a range of all the announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end);

	/**
	* Returns an ordered range of all the announcements entries that the user has permissions to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching announcements entries that the user has permission to view
	*/
	public java.util.List<AnnouncementsEntry> filterFindByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns the announcements entries before and after the current announcements entry in the ordered set of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param entryId the primary key of the current announcements entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry[] filterFindByC_C_A_PrevAndNext(long entryId,
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	*/
	public void removeByC_C_A(long classNameId, long classPK, boolean alert);

	/**
	* Returns the number of announcements entries where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the number of matching announcements entries
	*/
	public int countByC_C_A(long classNameId, long classPK, boolean alert);

	/**
	* Returns the number of announcements entries that the user has permission to view where classNameId = &#63; and classPK = &#63; and alert = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param alert the alert
	* @return the number of matching announcements entries that the user has permission to view
	*/
	public int filterCountByC_C_A(long classNameId, long classPK, boolean alert);

	/**
	* Caches the announcements entry in the entity cache if it is enabled.
	*
	* @param announcementsEntry the announcements entry
	*/
	public void cacheResult(AnnouncementsEntry announcementsEntry);

	/**
	* Caches the announcements entries in the entity cache if it is enabled.
	*
	* @param announcementsEntries the announcements entries
	*/
	public void cacheResult(
		java.util.List<AnnouncementsEntry> announcementsEntries);

	/**
	* Creates a new announcements entry with the primary key. Does not add the announcements entry to the database.
	*
	* @param entryId the primary key for the new announcements entry
	* @return the new announcements entry
	*/
	public AnnouncementsEntry create(long entryId);

	/**
	* Removes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry that was removed
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry remove(long entryId) throws NoSuchEntryException;

	public AnnouncementsEntry updateImpl(AnnouncementsEntry announcementsEntry);

	/**
	* Returns the announcements entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry
	* @throws NoSuchEntryException if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the announcements entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry, or <code>null</code> if a announcements entry with the primary key could not be found
	*/
	public AnnouncementsEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, AnnouncementsEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the announcements entries.
	*
	* @return the announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findAll();

	/**
	* Returns a range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of announcements entries
	*/
	public java.util.List<AnnouncementsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AnnouncementsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the announcements entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of announcements entries.
	*
	* @return the number of announcements entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}