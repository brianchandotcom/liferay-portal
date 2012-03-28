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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.journal.model.JournalFolder;

import java.util.List;

/**
 * The persistence utility for the journal folder service. This utility wraps {@link JournalFolderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderPersistence
 * @see JournalFolderPersistenceImpl
 * @generated
 */
public class JournalFolderUtil {
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
	public static void clearCache(JournalFolder journalFolder) {
		getPersistence().clearCache(journalFolder);
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
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalFolder update(JournalFolder journalFolder,
		boolean merge) throws SystemException {
		return getPersistence().update(journalFolder, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static JournalFolder update(JournalFolder journalFolder,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(journalFolder, merge, serviceContext);
	}

	/**
	* Caches the journal folder in the entity cache if it is enabled.
	*
	* @param journalFolder the journal folder
	*/
	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalFolder journalFolder) {
		getPersistence().cacheResult(journalFolder);
	}

	/**
	* Caches the journal folders in the entity cache if it is enabled.
	*
	* @param journalFolders the journal folders
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalFolder> journalFolders) {
		getPersistence().cacheResult(journalFolders);
	}

	/**
	* Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	*
	* @param folderId the primary key for the new journal folder
	* @return the new journal folder
	*/
	public static com.liferay.portlet.journal.model.JournalFolder create(
		long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	* Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder that was removed
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder remove(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().remove(folderId);
	}

	public static com.liferay.portlet.journal.model.JournalFolder updateImpl(
		com.liferay.portlet.journal.model.JournalFolder journalFolder,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalFolder, merge);
	}

	/**
	* Returns the journal folder with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	* Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the journal folder
	* @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder fetchByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	/**
	* Returns all the journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current journal folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(folderId, uuid, orderByComparator);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current journal folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(folderId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_First(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
			orderByComparator);
	}

	/**
	* Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current journal folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder[] filterFindByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(folderId, groupId,
			parentFolderId, orderByComparator);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder
	* @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder findByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().findByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_N(groupId, parentFolderId, name,
			retrieveFromCache);
	}

	/**
	* Returns all the journal folders.
	*
	* @return the journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @return the range of journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal folders
	* @param end the upper bound of the range of journal folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the journal folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the journal folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the journal folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
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
	* Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the journal folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalFolder removeByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFolderException {
		return getPersistence().removeByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Removes all the journal folders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of journal folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of journal folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching journal folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the number of journal folders.
	*
	* @return the number of journal folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalFolderPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalFolderPersistence)PortalBeanLocatorUtil.locate(JournalFolderPersistence.class.getName());

			ReferenceRegistry.registerReference(JournalFolderUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(JournalFolderPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(JournalFolderUtil.class,
			"_persistence");
	}

	private static JournalFolderPersistence _persistence;
}