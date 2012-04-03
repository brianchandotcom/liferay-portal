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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.util.List;

/**
 * The persistence utility for the bookmarks folder service. This utility wraps {@link BookmarksFolderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderPersistence
 * @see BookmarksFolderPersistenceImpl
 * @generated
 */
public class BookmarksFolderUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(BookmarksFolder bookmarksFolder) {
		getPersistence().clearCache(bookmarksFolder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static BookmarksFolder update(BookmarksFolder bookmarksFolder,
		boolean merge) throws SystemException {
		return getPersistence().update(bookmarksFolder, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static BookmarksFolder update(BookmarksFolder bookmarksFolder,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(bookmarksFolder, merge, serviceContext);
	}

	/**
	* Caches the bookmarks folder in the entity cache if it is enabled.
	*
	* @param bookmarksFolder the bookmarks folder
	*/
	public static void cacheResult(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder) {
		getPersistence().cacheResult(bookmarksFolder);
	}

	/**
	* Caches the bookmarks folders in the entity cache if it is enabled.
	*
	* @param bookmarksFolders the bookmarks folders
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> bookmarksFolders) {
		getPersistence().cacheResult(bookmarksFolders);
	}

	/**
	* Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	*
	* @param folderId the primary key for the new bookmarks folder
	* @return the new bookmarks folder
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder create(
		long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	* Removes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder that was removed
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder remove(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().remove(folderId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksFolder updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(bookmarksFolder, merge);
	}

	/**
	* Returns the bookmarks folder with the primary key or throws a {@link com.liferay.portlet.bookmarks.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	* Returns the bookmarks folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the bookmarks folder
	* @return the bookmarks folder, or <code>null</code> if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder fetchByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	/**
	* Returns all the bookmarks folders where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByResourceBlockId(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns a range of all the bookmarks folders where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByResourceBlockId(
		long resourceBlockId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByResourceBlockId_First(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the last bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByResourceBlockId_Last(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] findByResourceBlockId_PrevAndNext(
		long folderId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByResourceBlockId_PrevAndNext(folderId,
			resourceBlockId, orderByComparator);
	}

	/**
	* Returns all the bookmarks folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the bookmarks folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(folderId, uuid, orderByComparator);
	}

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.bookmarks.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the bookmarks folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the bookmarks folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the bookmarks folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(folderId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_First(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
			orderByComparator);
	}

	/**
	* Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current bookmarks folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next bookmarks folder
	* @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder[] filterFindByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(folderId, groupId,
			parentFolderId, orderByComparator);
	}

	/**
	* Returns all the bookmarks folders.
	*
	* @return the bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @return the range of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the bookmarks folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks folders
	* @param end the upper bound of the range of bookmarks folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the bookmarks folders where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByResourceBlockId(resourceBlockId);
	}

	/**
	* Removes all the bookmarks folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the bookmarks folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the bookmarks folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksFolder removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchFolderException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the bookmarks folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the bookmarks folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentFolderId);
	}

	/**
	* Removes all the bookmarks folders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of bookmarks folders where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the number of bookmarks folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of bookmarks folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of bookmarks folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of bookmarks folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching bookmarks folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of bookmarks folders.
	*
	* @return the number of bookmarks folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static BookmarksFolderPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BookmarksFolderPersistence)PortalBeanLocatorUtil.locate(BookmarksFolderPersistence.class.getName());

			ReferenceRegistry.registerReference(BookmarksFolderUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(BookmarksFolderPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(BookmarksFolderUtil.class,
			"_persistence");
	}

	private static BookmarksFolderPersistence _persistence;
}