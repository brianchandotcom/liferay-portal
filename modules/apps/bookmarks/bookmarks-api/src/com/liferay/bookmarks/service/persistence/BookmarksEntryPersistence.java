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

package com.liferay.bookmarks.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.bookmarks.model.BookmarksEntry;

import com.liferay.portal.service.persistence.BasePersistence;

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
@ProviderType
public interface BookmarksEntryPersistence extends BasePersistence<BookmarksEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksEntryUtil} to access the bookmarks entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the bookmarks entries where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId);

	/**
	* Returns a range of all the bookmarks entries where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where resourceBlockId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByResourceBlockId_PrevAndNext(
		long entryId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	*/
	public void removeByResourceBlockId(long resourceBlockId);

	/**
	* Returns the number of bookmarks entries where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching bookmarks entries
	*/
	public int countByResourceBlockId(long resourceBlockId);

	/**
	* Returns all the bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where uuid = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching bookmarks entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the bookmarks entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the bookmarks entry that was removed
	*/
	public com.liferay.bookmarks.model.BookmarksEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the number of bookmarks entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching bookmarks entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByUuid_C_PrevAndNext(
		long entryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of bookmarks entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching bookmarks entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the bookmarks entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the bookmarks entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of bookmarks entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching bookmarks entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_F_PrevAndNext(
		long entryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_F_PrevAndNext(
		long entryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	*/
	public void removeByG_F(long groupId, long folderId);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F(long groupId, long folderId);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F(long groupId, long[] folderIds);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long folderId);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long[] folderIds);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_S(
		long groupId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_S(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_S_PrevAndNext(
		long entryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_S(
		long groupId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_S(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_S_PrevAndNext(
		long entryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_S(long groupId, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_NotS(
		long groupId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_NotS(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_NotS(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_NotS_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_NotS_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_NotS_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_NotS_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_NotS_PrevAndNext(
		long entryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_NotS(
		long groupId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_NotS(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_NotS(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_NotS_PrevAndNext(
		long entryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_NotS(long groupId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_NotS(long groupId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_NotS(long groupId, int status);

	/**
	* Returns all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByC_NotS(
		long companyId, int status);

	/**
	* Returns a range of all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByC_NotS(
		long companyId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByC_NotS(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByC_NotS_PrevAndNext(
		long entryId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of bookmarks entries where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_U_S_PrevAndNext(
		long entryId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_U_S_PrevAndNext(
		long entryId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByG_U_S(long groupId, long userId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_U_S(long groupId, long userId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_U_S(long groupId, long userId, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_NotS(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_NotS_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_NotS_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_NotS_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_NotS_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_U_NotS_PrevAndNext(
		long entryId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_NotS(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_U_NotS_PrevAndNext(
		long entryId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	*/
	public void removeByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_U_NotS(long groupId, long userId, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_F_S_PrevAndNext(
		long entryId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_F_S_PrevAndNext(
		long entryId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_S(
		long groupId, long[] folderIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public void removeByG_F_S(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F_S(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F_S(long groupId, long[] folderIds, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F_S(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F_S(long groupId, long[] folderIds, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_NotS_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_NotS_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_F_NotS_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_F_NotS_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_F_NotS_PrevAndNext(
		long entryId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_F_NotS_PrevAndNext(
		long entryId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_F_NotS(
		long groupId, long[] folderIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public void removeByG_F_NotS(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F_NotS(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_F_NotS(long groupId, long[] folderIds, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F_NotS(long groupId, long folderId, int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_F_NotS(long groupId, long[] folderIds, int status);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_F_S_First(
		long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_F_S_First(
		long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByG_U_F_S_Last(
		long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByG_U_F_S_Last(
		long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] findByG_U_F_S_PrevAndNext(
		long entryId, long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long folderId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns the bookmarks entries before and after the current bookmarks entry in the ordered set of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry[] filterFindByG_U_F_S_PrevAndNext(
		long entryId, long groupId, long userId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end);

	/**
	* Returns an ordered range of all the bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> filterFindByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Returns all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status);

	/**
	* Returns a range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end);

	/**
	* Returns an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findByG_U_F_S(
		long groupId, long userId, long[] folderIds, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Removes all the bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public void removeByG_U_F_S(long groupId, long userId, long folderId,
		int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_U_F_S(long groupId, long userId, long folderId,
		int status);

	/**
	* Returns the number of bookmarks entries where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries
	*/
	public int countByG_U_F_S(long groupId, long userId, long[] folderIds,
		int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_U_F_S(long groupId, long userId, long folderId,
		int status);

	/**
	* Returns the number of bookmarks entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param status the status
	* @return the number of matching bookmarks entries that the user has permission to view
	*/
	public int filterCountByG_U_F_S(long groupId, long userId,
		long[] folderIds, int status);

	/**
	* Caches the bookmarks entry in the entity cache if it is enabled.
	*
	* @param bookmarksEntry the bookmarks entry
	*/
	public void cacheResult(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry);

	/**
	* Caches the bookmarks entries in the entity cache if it is enabled.
	*
	* @param bookmarksEntries the bookmarks entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.bookmarks.model.BookmarksEntry> bookmarksEntries);

	/**
	* Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	*
	* @param entryId the primary key for the new bookmarks entry
	* @return the new bookmarks entry
	*/
	public com.liferay.bookmarks.model.BookmarksEntry create(long entryId);

	/**
	* Removes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry that was removed
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry remove(long entryId)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	public com.liferay.bookmarks.model.BookmarksEntry updateImpl(
		com.liferay.bookmarks.model.BookmarksEntry bookmarksEntry);

	/**
	* Returns the bookmarks entry with the primary key or throws a {@link com.liferay.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry
	* @throws com.liferay.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.bookmarks.exception.NoSuchEntryException;

	/**
	* Returns the bookmarks entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry
	* @return the bookmarks entry, or <code>null</code> if a bookmarks entry with the primary key could not be found
	*/
	public com.liferay.bookmarks.model.BookmarksEntry fetchByPrimaryKey(
		long entryId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.bookmarks.model.BookmarksEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the bookmarks entries.
	*
	* @return the bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findAll();

	/**
	* Returns a range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @return the range of bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries
	* @param end the upper bound of the range of bookmarks entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of bookmarks entries
	*/
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	/**
	* Removes all the bookmarks entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of bookmarks entries.
	*
	* @return the number of bookmarks entries
	*/
	public int countAll();
}