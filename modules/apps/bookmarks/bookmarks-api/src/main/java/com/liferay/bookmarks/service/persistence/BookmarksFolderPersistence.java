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

import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksFolder;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the bookmarks folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.bookmarks.service.persistence.impl.BookmarksFolderPersistenceImpl
 * @see BookmarksFolderUtil
 * @generated
 */
@ProviderType
public interface BookmarksFolderPersistence extends BasePersistence<BookmarksFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksFolderUtil} to access the bookmarks folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the bookmarks folders where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByResourceBlockId(
		long resourceBlockId);

	/**
	* Returns a range of all the bookmarks folders where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByResourceBlockId(
		long resourceBlockId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByResourceBlockId_First(long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByResourceBlockId_First(long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByResourceBlockId_Last(long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByResourceBlockId_Last(long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByResourceBlockId_PrevAndNext(long folderId,
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	*/
	public void removeByResourceBlockId(long resourceBlockId);

	/**
	* Returns the number of bookmarks folders where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching bookmarks folders
	*/
	public int countByResourceBlockId(long resourceBlockId);

	/**
	* Returns all the bookmarks folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the bookmarks folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByUuid_PrevAndNext(long folderId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of bookmarks folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching bookmarks folders
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFolderException;

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the bookmarks folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the bookmarks folder that was removed
	*/
	public BookmarksFolder removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFolderException;

	/**
	* Returns the number of bookmarks folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching bookmarks folders
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByUuid_C_PrevAndNext(long folderId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of bookmarks folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching bookmarks folders
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the bookmarks folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByGroupId(long groupId);

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of bookmarks folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks folders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks folders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the bookmarks folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByCompanyId(long companyId);

	/**
	* Returns a range of all the bookmarks folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of bookmarks folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching bookmarks folders
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P(long groupId,
		long parentFolderId);

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P(long groupId,
		long parentFolderId);

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] filterFindByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching bookmarks folders
	*/
	public int countByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching bookmarks folders that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentFolderId);

	/**
	* Returns all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByC_NotS(long companyId,
		int status);

	/**
	* Returns a range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByC_NotS(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByC_NotS_PrevAndNext(long folderId,
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of bookmarks folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching bookmarks folders
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_S(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_S_First(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_S_First(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_S_Last(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_S_Last(long groupId, long parentFolderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_S(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] filterFindByG_P_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching bookmarks folders
	*/
	public int countByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching bookmarks folders that the user has permission to view
	*/
	public int filterCountByG_P_S(long groupId, long parentFolderId, int status);

	/**
	* Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_NotS_First(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByG_P_NotS_Last(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByG_P_NotS_Last(long groupId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] findByG_P_NotS_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status);

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	*/
	public java.util.List<BookmarksFolder> filterFindByG_P_NotS(long groupId,
		long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder[] filterFindByG_P_NotS_PrevAndNext(long folderId,
		long groupId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching bookmarks folders
	*/
	public int countByG_P_NotS(long groupId, long parentFolderId, int status);

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching bookmarks folders that the user has permission to view
	*/
	public int filterCountByG_P_NotS(long groupId, long parentFolderId,
		int status);

	/**
	* Returns all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status);

	/**
	* Returns a range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first bookmarks folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByF_C_P_NotS_First(long folderId,
		long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first bookmarks folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByF_C_P_NotS_First(long folderId,
		long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns the last bookmarks folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws NoSuchFolderException if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder findByF_C_P_NotS_Last(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last bookmarks folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	*/
	public BookmarksFolder fetchByF_C_P_NotS_Last(long folderId,
		long companyId, long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Removes all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns the number of bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching bookmarks folders
	*/
	public int countByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Caches the bookmarks folder in the entity cache if it is enabled.
	*
	* @param bookmarksFolder the bookmarks folder
	*/
	public void cacheResult(BookmarksFolder bookmarksFolder);

	/**
	* Caches the bookmarks folders in the entity cache if it is enabled.
	*
	* @param bookmarksFolders the bookmarks folders
	*/
	public void cacheResult(java.util.List<BookmarksFolder> bookmarksFolders);

	/**
	* Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	*
	* @param folderId the primary key for the new bookmarks folder
	* @return the new bookmarks folder
	*/
	public BookmarksFolder create(long folderId);

	/**
	* Removes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder that was removed
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder remove(long folderId) throws NoSuchFolderException;

	public BookmarksFolder updateImpl(BookmarksFolder bookmarksFolder);

	/**
	* Returns the bookmarks folder with the primary key or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder
	* @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException;

	/**
	* Returns the bookmarks folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder, or <code>null</code> if a bookmarks folder with the primary key could not be found
	*/
	public BookmarksFolder fetchByPrimaryKey(long folderId);

	@Override
	public java.util.Map<java.io.Serializable, BookmarksFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the bookmarks folders.
	*
	* @return the bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findAll();

	/**
	* Returns a range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator);

	/**
	* Returns an ordered range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BookmarksFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of bookmarks folders
	*/
	public java.util.List<BookmarksFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BookmarksFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the bookmarks folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of bookmarks folders.
	*
	* @return the number of bookmarks folders
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}