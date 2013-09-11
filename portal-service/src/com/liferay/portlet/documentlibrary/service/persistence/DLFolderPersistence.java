/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * The persistence interface for the document library folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderPersistenceImpl
 * @see DLFolderUtil
 * @generated
 */
public interface DLFolderPersistence extends BasePersistence<DLFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFolderUtil} to access the document library folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the document library folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the number of document library folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByUuid_C_PrevAndNext(
		long folderId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where repositoryId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	*
	* @param repositoryId the repository ID
	* @return the matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByRepositoryId(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the document library folder where repositoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repositoryId the repository ID
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByRepositoryId(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where repositoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repositoryId the repository ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByRepositoryId(
		long repositoryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library folder where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @return the document library folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder removeByRepositoryId(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the number of document library folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByRepositoryId(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] filterFindByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByC_NotS(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByC_NotS(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByC_NotS(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByC_NotS_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByC_NotS_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByC_NotS_PrevAndNext(
		long folderId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_NotS(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_NotS(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByP_N(
		long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByP_N(
		long parentFolderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByP_N(
		long parentFolderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByP_N_First(
		long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByP_N_First(
		long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByP_N_Last(
		long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByP_N_Last(
		long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByP_N_PrevAndNext(
		long folderId, long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_N(long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByP_N(long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the document library folder that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder removeByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_M_P_H_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_M_P_H_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_M_P_H_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_M_P_H_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_M_P_H_PrevAndNext(
		long folderId, long groupId, boolean mountPoint, long parentFolderId,
		boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] filterFindByG_M_P_H_PrevAndNext(
		long folderId, long groupId, boolean mountPoint, long parentFolderId,
		boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the number of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_H_S_First(
		long groupId, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_H_S_First(
		long groupId, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_P_H_S_Last(
		long groupId, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_P_H_S_Last(
		long groupId, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_P_H_S_PrevAndNext(
		long folderId, long groupId, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_P_H_S(
		long groupId, long parentFolderId, boolean hidden, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] filterFindByG_P_H_S_PrevAndNext(
		long folderId, long groupId, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_M_P_H_S_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_M_P_H_S_First(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByG_M_P_H_S_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByG_M_P_H_S_Last(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] findByG_M_P_H_S_PrevAndNext(
		long folderId, long groupId, boolean mountPoint, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> filterFindByG_M_P_H_S(
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder[] filterFindByG_M_P_H_S_PrevAndNext(
		long folderId, long groupId, boolean mountPoint, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the document library folder in the entity cache if it is enabled.
	*
	* @param dlFolder the document library folder
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder);

	/**
	* Caches the document library folders in the entity cache if it is enabled.
	*
	* @param dlFolders the document library folders
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders);

	/**
	* Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	*
	* @param folderId the primary key for the new document library folder
	* @return the new document library folder
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder create(
		long folderId);

	/**
	* Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder remove(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	public com.liferay.portlet.documentlibrary.model.DLFolder updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library folder with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder
	* @throws com.liferay.portlet.documentlibrary.NoSuchFolderException if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFolderException;

	/**
	* Returns the document library folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder, or <code>null</code> if a document library folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFolder fetchByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders.
	*
	* @return the document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library folders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders.
	*
	* @return the number of document library folders
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry types associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @return the document library file entry types associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types associated with the document library folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library folder
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of document library file entry types associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types associated with the document library folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library folder
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry types associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @return the number of document library file entry types associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileEntryTypesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the document library file entry type is associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	* @return <code>true</code> if the document library file entry type is associated with the document library folder; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the document library folder has any document library file entry types associated with it.
	*
	* @param pk the primary key of the document library folder to check for associations with document library file entry types
	* @return <code>true</code> if the document library folder has any document library file entry types associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDLFileEntryTypes(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryType the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the document library folder and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder to clear the associated document library file entry types from
	* @throws SystemException if a system exception occurred
	*/
	public void clearDLFileEntryTypes(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFileEntryType(long pk, long dlFileEntryTypePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryType the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types to be associated with the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void setDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes)
		throws com.liferay.portal.kernel.exception.SystemException;
}