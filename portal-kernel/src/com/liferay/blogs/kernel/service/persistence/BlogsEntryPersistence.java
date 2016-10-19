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

package com.liferay.blogs.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the blogs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.blogs.service.persistence.impl.BlogsEntryPersistenceImpl
 * @see BlogsEntryUtil
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.blogs.model.impl.BlogsEntryImpl}
 * @generated
 */
@Deprecated
@ProviderType
public interface BlogsEntryPersistence extends BasePersistence<BlogsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsEntryUtil} to access the blogs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the blogs entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByUuid_PrevAndNext(long entryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of blogs entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching blogs entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the blogs entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the blogs entry that was removed
	*/
	public BlogsEntry removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	* Returns the number of blogs entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching blogs entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByUuid_C_PrevAndNext(long entryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of blogs entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching blogs entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the blogs entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByGroupId_PrevAndNext(long entryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of blogs entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching blogs entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the blogs entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of blogs entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching blogs entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_UT(long groupId, java.lang.String urlTitle)
		throws NoSuchEntryException;

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_UT(long groupId, java.lang.String urlTitle);

	/**
	* Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_UT(long groupId, java.lang.String urlTitle,
		boolean retrieveFromCache);

	/**
	* Removes the blogs entry where groupId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the blogs entry that was removed
	*/
	public BlogsEntry removeByG_UT(long groupId, java.lang.String urlTitle)
		throws NoSuchEntryException;

	/**
	* Returns the number of blogs entries where groupId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @return the number of matching blogs entries
	*/
	public int countByG_UT(long groupId, java.lang.String urlTitle);

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD(long groupId, Date displayDate);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD(long groupId,
		Date displayDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD(long groupId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD(long groupId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_First(long groupId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_First(long groupId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_Last(long groupId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_Last(long groupId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_LtD_PrevAndNext(long entryId, long groupId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD(long groupId,
		Date displayDate);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD(long groupId,
		Date displayDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD(long groupId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_LtD_PrevAndNext(long entryId,
		long groupId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	*/
	public void removeByG_LtD(long groupId, Date displayDate);

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public int countByG_LtD(long groupId, Date displayDate);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_LtD(long groupId, Date displayDate);

	/**
	* Returns all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_S(long groupId, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_S(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_S_PrevAndNext(long entryId, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_S(long groupId, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_S_PrevAndNext(long entryId, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_S(long groupId, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_NotS(long groupId, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_NotS(long groupId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_NotS_PrevAndNext(long entryId, long groupId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_NotS(long groupId,
		int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_NotS(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_NotS(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_NotS_PrevAndNext(long entryId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_NotS(long groupId, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_NotS(long groupId, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_NotS(long groupId, int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U(long companyId, long userId);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U(long companyId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_First(long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_First(long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_Last(long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_Last(long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_U_PrevAndNext(long entryId, long companyId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	*/
	public void removeByC_U(long companyId, long userId);

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the number of matching blogs entries
	*/
	public int countByC_U(long companyId, long userId);

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD(long companyId,
		Date displayDate);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD(long companyId,
		Date displayDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD(long companyId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD(long companyId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_First(long companyId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_First(long companyId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_Last(long companyId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_Last(long companyId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_LtD_PrevAndNext(long entryId, long companyId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	*/
	public void removeByC_LtD(long companyId, Date displayDate);

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public int countByC_LtD(long companyId, Date displayDate);

	/**
	* Returns all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_S(long companyId, int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_S(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_S_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_S_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_S_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_S_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_S_PrevAndNext(long entryId, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_S(long companyId, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_S(long companyId, int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_NotS(long companyId, int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_NotS(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_NotS_PrevAndNext(long entryId, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByLtD_S(Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByLtD_S(Date displayDate, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByLtD_S(Date displayDate, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByLtD_S(Date displayDate, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByLtD_S_PrevAndNext(long entryId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of blogs entries where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_First(long groupId, long userId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_First(long groupId, long userId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_Last(long groupId, long userId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_Last(long groupId, long userId,
		Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_U_LtD_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, Date displayDate);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, Date displayDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD(long groupId,
		long userId, Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_U_LtD_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	*/
	public void removeByG_U_LtD(long groupId, long userId, Date displayDate);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the number of matching blogs entries
	*/
	public int countByG_U_LtD(long groupId, long userId, Date displayDate);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_LtD(long groupId, long userId, Date displayDate);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_S_First(long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_S_First(long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_S_Last(long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_S_Last(long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_U_S_PrevAndNext(long entryId, long groupId,
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_U_S_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int[] statuses);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_S(long groupId,
		long userId, int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int[] statuses);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int[] statuses, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_S(long groupId, long userId,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByG_U_S(long groupId, long userId, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_U_S(long groupId, long userId, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @return the number of matching blogs entries
	*/
	public int countByG_U_S(long groupId, long userId, int[] statuses);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_S(long groupId, long userId, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param statuses the statuses
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_S(long groupId, long userId, int[] statuses);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_NotS_First(long groupId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_NotS_First(long groupId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_NotS_Last(long groupId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_NotS_Last(long groupId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_U_NotS_PrevAndNext(long entryId, long groupId,
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_NotS(long groupId,
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_U_NotS_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_D_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_D_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_D_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_D_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_D_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_D_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_D_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_D_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_D_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_D_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_D_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_D_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_D_S_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_D_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_D_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_D_S(long groupId, Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_GtD_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_GtD_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_GtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_GtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_GtD_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_GtD_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_GtD_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_GtD_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_GtD_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_GtD_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_GtD_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_GtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_GtD_S_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_GtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_GtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &gt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_GtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_S_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_S_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_LtD_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_S(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_LtD_S_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_LtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_LtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_LtD_S(long groupId, Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_NotS(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_NotS_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_NotS_First(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_LtD_NotS_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_LtD_NotS_Last(long groupId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_LtD_NotS_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_LtD_NotS_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_LtD_NotS(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_LtD_NotS(long groupId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_LtD_NotS(long groupId, Date displayDate,
		int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_S_First(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_S_First(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_S_Last(long companyId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_S_Last(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_U_S_PrevAndNext(long entryId, long companyId,
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByC_U_S(long companyId, long userId, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_U_S(long companyId, long userId, int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_NotS(long companyId,
		long userId, int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_NotS(long companyId,
		long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_NotS(long companyId,
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_U_NotS(long companyId,
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_NotS_First(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_NotS_First(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_U_NotS_Last(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_U_NotS_Last(long companyId, long userId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_U_NotS_PrevAndNext(long entryId,
		long companyId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByC_U_NotS(long companyId, long userId, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_U_NotS(long companyId, long userId, int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_S(long companyId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_S(long companyId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_S(long companyId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_S(long companyId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_S_First(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_S_First(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_S_Last(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_S_Last(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_LtD_S_PrevAndNext(long entryId, long companyId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByC_LtD_S(long companyId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_LtD_S(long companyId, Date displayDate, int status);

	/**
	* Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_NotS(long companyId,
		Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_NotS(long companyId,
		Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_NotS(long companyId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByC_LtD_NotS(long companyId,
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_NotS_First(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_NotS_First(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByC_LtD_NotS_Last(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByC_LtD_NotS_Last(long companyId, Date displayDate,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByC_LtD_NotS_PrevAndNext(long entryId,
		long companyId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByC_LtD_NotS(long companyId, Date displayDate, int status);

	/**
	* Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByC_LtD_NotS(long companyId, Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_S_First(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_S_First(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_S_Last(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_S_Last(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_U_LtD_S_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_S(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_U_LtD_S_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status);

	/**
	* Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs entries
	*/
	public java.util.List<BlogsEntry> findByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_NotS_First(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_NotS_First(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry
	* @throws NoSuchEntryException if a matching blogs entry could not be found
	*/
	public BlogsEntry findByG_U_LtD_NotS_Last(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	public BlogsEntry fetchByG_U_LtD_NotS_Last(long groupId, long userId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] findByG_U_LtD_NotS_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status);

	/**
	* Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status, int start, int end);

	/**
	* Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs entries that the user has permission to view
	*/
	public java.util.List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId,
		long userId, Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current blogs entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry[] filterFindByG_U_LtD_NotS_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status);

	/**
	* Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries
	*/
	public int countByG_U_LtD_NotS(long groupId, long userId, Date displayDate,
		int status);

	/**
	* Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching blogs entries that the user has permission to view
	*/
	public int filterCountByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status);

	/**
	* Caches the blogs entry in the entity cache if it is enabled.
	*
	* @param blogsEntry the blogs entry
	*/
	public void cacheResult(BlogsEntry blogsEntry);

	/**
	* Caches the blogs entries in the entity cache if it is enabled.
	*
	* @param blogsEntries the blogs entries
	*/
	public void cacheResult(java.util.List<BlogsEntry> blogsEntries);

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	public BlogsEntry create(long entryId);

	/**
	* Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry that was removed
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry remove(long entryId) throws NoSuchEntryException;

	public BlogsEntry updateImpl(BlogsEntry blogsEntry);

	/**
	* Returns the blogs entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry
	* @throws NoSuchEntryException if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	*/
	public BlogsEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, BlogsEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the blogs entries.
	*
	* @return the blogs entries
	*/
	public java.util.List<BlogsEntry> findAll();

	/**
	* Returns a range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of blogs entries
	*/
	public java.util.List<BlogsEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of blogs entries
	*/
	public java.util.List<BlogsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator);

	/**
	* Returns an ordered range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of blogs entries
	*/
	public java.util.List<BlogsEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the blogs entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of blogs entries.
	*
	* @return the number of blogs entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}