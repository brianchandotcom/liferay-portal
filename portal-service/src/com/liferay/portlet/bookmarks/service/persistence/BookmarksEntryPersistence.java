/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;

/**
 * The persistence interface for the bookmarks entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryPersistenceImpl
 * @see BookmarksEntryUtil
 * @generated
 */
public interface BookmarksEntryPersistence extends BasePersistence<BookmarksEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksEntryUtil} to access the bookmarks entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the bookmarks entry in the entity cache if it is enabled.
	*
	* @param bookmarksEntry the bookmarks entry
	*/
	public void cacheResult(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry);

	/**
	* Caches the bookmarks entries in the entity cache if it is enabled.
	*
	* @param bookmarksEntries the bookmarks entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> bookmarksEntries);

	/**
	* Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	*
	* @param entryId the primary key for the new bookmarks entry
	* @return the new bookmarks entry
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry create(
		long entryId);

	/**
	* Removes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry that was removed
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookmarks entry with the primary key or throws a {@link com.liferay.portlet.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry, or <code>null</code> if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookmarks entries where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByResourceBlockId_PrevAndNext(
		long entryId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] filterFindByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] filterFindByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByG_F_PrevAndNext(
		long entryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] filterFindByG_F_PrevAndNext(
		long entryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the bookmarks entries.
	*
	* @return the bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookmarks entries where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookmarks entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the bookmarks entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the bookmarks entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.bookmarks.model.BookmarksEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the bookmarks entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of bookmarks entries.
	*
	* @return the number of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}