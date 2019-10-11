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

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the bookmarks folder service. This utility wraps <code>com.liferay.bookmarks.service.persistence.impl.BookmarksFolderPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderPersistence
 * @generated
 */
public class BookmarksFolderUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(BookmarksFolder bookmarksFolder) {
		getPersistence().clearCache(bookmarksFolder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, BookmarksFolder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BookmarksFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BookmarksFolder update(BookmarksFolder bookmarksFolder) {
		return getPersistence().update(bookmarksFolder);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BookmarksFolder update(
		BookmarksFolder bookmarksFolder, ServiceContext serviceContext) {

		return getPersistence().update(bookmarksFolder, serviceContext);
	}

	/**
	 * Returns all the bookmarks folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the bookmarks folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByUuid_First(
			String uuid, OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUuid_First(
		String uuid, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByUuid_Last(
			String uuid, OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUuid_Last(
		String uuid, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder[] findByUuid_PrevAndNext(
			long folderId, String uuid,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_PrevAndNext(
			folderId, uuid, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of bookmarks folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching bookmarks folders
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByUUID_G(String uuid, long groupId)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the bookmarks folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the bookmarks folder that was removed
	 */
	public static BookmarksFolder removeByUUID_G(String uuid, long groupId)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of bookmarks folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static BookmarksFolder[] findByUuid_C_PrevAndNext(
			long folderId, String uuid, long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByUuid_C_PrevAndNext(
			folderId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of bookmarks folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching bookmarks folders
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByGroupId_First(
			long groupId, OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByGroupId_First(
		long groupId, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByGroupId_Last(
			long groupId, OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByGroupId_Last(
		long groupId, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder[] findByGroupId_PrevAndNext(
			long folderId, long groupId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByGroupId_PrevAndNext(
			folderId, groupId, orderByComparator);
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder[] filterFindByGroupId_PrevAndNext(
			long folderId, long groupId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			folderId, groupId, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of bookmarks folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the bookmarks folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the bookmarks folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByCompanyId_First(
			long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByCompanyId_First(
		long companyId, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByCompanyId_Last(
			long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByCompanyId_Last(
		long companyId, OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder[] findByCompanyId_PrevAndNext(
			long folderId, long companyId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByCompanyId_PrevAndNext(
			folderId, companyId, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of bookmarks folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching bookmarks folders
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P(
		long groupId, long parentFolderId) {

		return getPersistence().findByG_P(groupId, parentFolderId);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return getPersistence().findByG_P(groupId, parentFolderId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByG_P(
			groupId, parentFolderId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_P(
			groupId, parentFolderId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByG_P_First(
			long groupId, long parentFolderId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_First(
			groupId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_First(
		long groupId, long parentFolderId,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_First(
			groupId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByG_P_Last(
			long groupId, long parentFolderId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_Last(
			groupId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_Last(
		long groupId, long parentFolderId,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_Last(
			groupId, parentFolderId, orderByComparator);
	}

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
	public static BookmarksFolder[] findByG_P_PrevAndNext(
			long folderId, long groupId, long parentFolderId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_PrevAndNext(
			folderId, groupId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId) {

		return getPersistence().filterFindByG_P(groupId, parentFolderId);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end) {

		return getPersistence().filterFindByG_P(
			groupId, parentFolderId, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().filterFindByG_P(
			groupId, parentFolderId, start, end, orderByComparator);
	}

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
	public static BookmarksFolder[] filterFindByG_P_PrevAndNext(
			long folderId, long groupId, long parentFolderId,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().filterFindByG_P_PrevAndNext(
			folderId, groupId, parentFolderId, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 */
	public static void removeByG_P(long groupId, long parentFolderId) {
		getPersistence().removeByG_P(groupId, parentFolderId);
	}

	/**
	 * Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching bookmarks folders
	 */
	public static int countByG_P(long groupId, long parentFolderId) {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	/**
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching bookmarks folders that the user has permission to view
	 */
	public static int filterCountByG_P(long groupId, long parentFolderId) {
		return getPersistence().filterCountByG_P(groupId, parentFolderId);
	}

	/**
	 * Returns all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByC_NotS(
		long companyId, int status) {

		return getPersistence().findByC_NotS(companyId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByC_NotS(
		long companyId, int status, int start, int end) {

		return getPersistence().findByC_NotS(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByC_NotS(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_NotS(
			companyId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByC_NotS_First(
			long companyId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByC_NotS_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByC_NotS_First(
		long companyId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByC_NotS_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws NoSuchFolderException if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder findByC_NotS_Last(
			long companyId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByC_NotS_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByC_NotS_Last(
		long companyId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByC_NotS_Last(
			companyId, status, orderByComparator);
	}

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
	public static BookmarksFolder[] findByC_NotS_PrevAndNext(
			long folderId, long companyId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByC_NotS_PrevAndNext(
			folderId, companyId, status, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByC_NotS(long companyId, int status) {
		getPersistence().removeByC_NotS(companyId, status);
	}

	/**
	 * Returns the number of bookmarks folders where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching bookmarks folders
	 */
	public static int countByC_NotS(long companyId, int status) {
		return getPersistence().countByC_NotS(companyId, status);
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_S(
		long groupId, long parentFolderId, int status) {

		return getPersistence().findByG_P_S(groupId, parentFolderId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getPersistence().findByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByG_P_S(
			groupId, parentFolderId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_P_S(
			groupId, parentFolderId, status, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static BookmarksFolder findByG_P_S_First(
			long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_S_First(
			groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_S_First(
		long groupId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_S_First(
			groupId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder findByG_P_S_Last(
			long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_S_Last(
			groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_S_Last(
		long groupId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_S_Last(
			groupId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder[] findByG_P_S_PrevAndNext(
			long folderId, long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_S_PrevAndNext(
			folderId, groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status) {

		return getPersistence().filterFindByG_P_S(
			groupId, parentFolderId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getPersistence().filterFindByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> filterFindByG_P_S(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().filterFindByG_P_S(
			groupId, parentFolderId, status, start, end, orderByComparator);
	}

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
	public static BookmarksFolder[] filterFindByG_P_S_PrevAndNext(
			long folderId, long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().filterFindByG_P_S_PrevAndNext(
			folderId, groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	public static void removeByG_P_S(
		long groupId, long parentFolderId, int status) {

		getPersistence().removeByG_P_S(groupId, parentFolderId, status);
	}

	/**
	 * Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching bookmarks folders
	 */
	public static int countByG_P_S(
		long groupId, long parentFolderId, int status) {

		return getPersistence().countByG_P_S(groupId, parentFolderId, status);
	}

	/**
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching bookmarks folders that the user has permission to view
	 */
	public static int filterCountByG_P_S(
		long groupId, long parentFolderId, int status) {

		return getPersistence().filterCountByG_P_S(
			groupId, parentFolderId, status);
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return getPersistence().findByG_P_NotS(groupId, parentFolderId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getPersistence().findByG_P_NotS(
			groupId, parentFolderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByG_P_NotS(
			groupId, parentFolderId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_P_NotS(
			groupId, parentFolderId, status, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static BookmarksFolder findByG_P_NotS_First(
			long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_NotS_First(
			groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_NotS_First(
		long groupId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_NotS_First(
			groupId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder findByG_P_NotS_Last(
			long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_NotS_Last(
			groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 */
	public static BookmarksFolder fetchByG_P_NotS_Last(
		long groupId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByG_P_NotS_Last(
			groupId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder[] findByG_P_NotS_PrevAndNext(
			long folderId, long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByG_P_NotS_PrevAndNext(
			folderId, groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return getPersistence().filterFindByG_P_NotS(
			groupId, parentFolderId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 */
	public static List<BookmarksFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getPersistence().filterFindByG_P_NotS(
			groupId, parentFolderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> filterFindByG_P_NotS(
		long groupId, long parentFolderId, int status, int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().filterFindByG_P_NotS(
			groupId, parentFolderId, status, start, end, orderByComparator);
	}

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
	public static BookmarksFolder[] filterFindByG_P_NotS_PrevAndNext(
			long folderId, long groupId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().filterFindByG_P_NotS_PrevAndNext(
			folderId, groupId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	public static void removeByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		getPersistence().removeByG_P_NotS(groupId, parentFolderId, status);
	}

	/**
	 * Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching bookmarks folders
	 */
	public static int countByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return getPersistence().countByG_P_NotS(
			groupId, parentFolderId, status);
	}

	/**
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching bookmarks folders that the user has permission to view
	 */
	public static int filterCountByG_P_NotS(
		long groupId, long parentFolderId, int status) {

		return getPersistence().filterCountByG_P_NotS(
			groupId, parentFolderId, status);
	}

	/**
	 * Returns all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		return getPersistence().findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status);
	}

	/**
	 * Returns a range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end) {

		return getPersistence().findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
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
	public static List<BookmarksFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching bookmarks folders
	 */
	public static List<BookmarksFolder> findByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status,
		int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByF_C_P_NotS(
			folderId, companyId, parentFolderId, status, start, end,
			orderByComparator, useFinderCache);
	}

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
	public static BookmarksFolder findByF_C_P_NotS_First(
			long folderId, long companyId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByF_C_P_NotS_First(
			folderId, companyId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder fetchByF_C_P_NotS_First(
		long folderId, long companyId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByF_C_P_NotS_First(
			folderId, companyId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder findByF_C_P_NotS_Last(
			long folderId, long companyId, long parentFolderId, int status,
			OrderByComparator<BookmarksFolder> orderByComparator)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByF_C_P_NotS_Last(
			folderId, companyId, parentFolderId, status, orderByComparator);
	}

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
	public static BookmarksFolder fetchByF_C_P_NotS_Last(
		long folderId, long companyId, long parentFolderId, int status,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().fetchByF_C_P_NotS_Last(
			folderId, companyId, parentFolderId, status, orderByComparator);
	}

	/**
	 * Removes all the bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 */
	public static void removeByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		getPersistence().removeByF_C_P_NotS(
			folderId, companyId, parentFolderId, status);
	}

	/**
	 * Returns the number of bookmarks folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	 *
	 * @param folderId the folder ID
	 * @param companyId the company ID
	 * @param parentFolderId the parent folder ID
	 * @param status the status
	 * @return the number of matching bookmarks folders
	 */
	public static int countByF_C_P_NotS(
		long folderId, long companyId, long parentFolderId, int status) {

		return getPersistence().countByF_C_P_NotS(
			folderId, companyId, parentFolderId, status);
	}

	/**
	 * Caches the bookmarks folder in the entity cache if it is enabled.
	 *
	 * @param bookmarksFolder the bookmarks folder
	 */
	public static void cacheResult(BookmarksFolder bookmarksFolder) {
		getPersistence().cacheResult(bookmarksFolder);
	}

	/**
	 * Caches the bookmarks folders in the entity cache if it is enabled.
	 *
	 * @param bookmarksFolders the bookmarks folders
	 */
	public static void cacheResult(List<BookmarksFolder> bookmarksFolders) {
		getPersistence().cacheResult(bookmarksFolders);
	}

	/**
	 * Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	 *
	 * @param folderId the primary key for the new bookmarks folder
	 * @return the new bookmarks folder
	 */
	public static BookmarksFolder create(long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	 * Removes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder that was removed
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder remove(long folderId)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().remove(folderId);
	}

	public static BookmarksFolder updateImpl(BookmarksFolder bookmarksFolder) {
		return getPersistence().updateImpl(bookmarksFolder);
	}

	/**
	 * Returns the bookmarks folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder
	 * @throws NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder findByPrimaryKey(long folderId)
		throws com.liferay.bookmarks.exception.NoSuchFolderException {

		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	 * Returns the bookmarks folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder, or <code>null</code> if a bookmarks folder with the primary key could not be found
	 */
	public static BookmarksFolder fetchByPrimaryKey(long folderId) {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	/**
	 * Returns all the bookmarks folders.
	 *
	 * @return the bookmarks folders
	 */
	public static List<BookmarksFolder> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the bookmarks folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of bookmarks folders
	 */
	public static List<BookmarksFolder> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of bookmarks folders
	 */
	public static List<BookmarksFolder> findAll(
		int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BookmarksFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of bookmarks folders
	 */
	public static List<BookmarksFolder> findAll(
		int start, int end,
		OrderByComparator<BookmarksFolder> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the bookmarks folders from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of bookmarks folders.
	 *
	 * @return the number of bookmarks folders
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BookmarksFolderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BookmarksFolderPersistence, BookmarksFolderPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BookmarksFolderPersistence.class);

		ServiceTracker<BookmarksFolderPersistence, BookmarksFolderPersistence>
			serviceTracker =
				new ServiceTracker
					<BookmarksFolderPersistence, BookmarksFolderPersistence>(
						bundle.getBundleContext(),
						BookmarksFolderPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}