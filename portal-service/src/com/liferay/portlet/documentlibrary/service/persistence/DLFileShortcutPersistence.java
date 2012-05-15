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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

/**
 * The persistence interface for the document library file shortcut service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistenceImpl
 * @see DLFileShortcutUtil
 * @generated
 */
public interface DLFileShortcutPersistence extends BasePersistence<DLFileShortcut> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileShortcutUtil} to access the document library file shortcut persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the document library file shortcut in the entity cache if it is enabled.
	*
	* @param dlFileShortcut the document library file shortcut
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut);

	/**
	* Caches the document library file shortcuts in the entity cache if it is enabled.
	*
	* @param dlFileShortcuts the document library file shortcuts
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> dlFileShortcuts);

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId);

	/**
	* Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcut with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_PrevAndNext(
		long fileShortcutId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_C_PrevAndNext(
		long fileShortcutId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByToFileEntryId_First(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByToFileEntryId_Last(
		long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_A(
		long groupId, long folderId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_A(
		long groupId, long folderId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_A(
		long groupId, long folderId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_A_First(
		long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_A_Last(
		long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_A(
		long groupId, long folderId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_A(
		long groupId, long folderId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_A(
		long groupId, long folderId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] filterFindByG_F_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @return the matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S_A(
		long groupId, long folderId, int status, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S_A(
		long groupId, long folderId, int status, boolean active, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S_A(
		long groupId, long folderId, int status, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_A_First(
		long groupId, long folderId, int status, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_A_Last(
		long groupId, long folderId, int status, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_S_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @return the matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S_A(
		long groupId, long folderId, int status, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S_A(
		long groupId, long folderId, int status, boolean active, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S_A(
		long groupId, long folderId, int status, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut[] filterFindByG_F_S_A_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts.
	*
	* @return the document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file shortcuts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library file shortcut where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file shortcut that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file shortcuts where toFileEntryId = &#63; from the database.
	*
	* @param toFileEntryId the to file entry ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByToFileEntryId(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F_A(long groupId, long folderId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F_S_A(long groupId, long folderId, int status,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file shortcuts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByToFileEntryId(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F_A(long groupId, long folderId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F_A(long groupId, long folderId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @return the number of matching document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F_S_A(long groupId, long folderId, int status,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param active the active
	* @return the number of matching document library file shortcuts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_F_S_A(long groupId, long folderId, int status,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}