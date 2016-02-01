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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.exception.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * The persistence interface for the document library folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFolderPersistenceImpl
 * @see DLFolderUtil
 * @generated
 */
@ProviderType
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
	*/
	public java.util.List<DLFolder> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the document library folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByUuid_PrevAndNext(long folderId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of document library folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library folders
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFolderException;

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the document library folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the document library folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library folder that was removed
	*/
	public DLFolder removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFolderException;

	/**
	* Returns the number of document library folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library folders
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByUuid_C_PrevAndNext(long folderId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of document library folders where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library folders
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the document library folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByGroupId(long groupId);

	/**
	* Returns a range of all the document library folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByGroupId_PrevAndNext(long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of document library folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library folders
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the document library folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByCompanyId(long companyId);

	/**
	* Returns a range of all the document library folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of document library folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library folders
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the document library folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByRepositoryId(long repositoryId);

	/**
	* Returns a range of all the document library folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByRepositoryId(long repositoryId,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByRepositoryId(long repositoryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByRepositoryId_First(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByRepositoryId_Last(long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where repositoryId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByRepositoryId_PrevAndNext(long folderId,
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public void removeByRepositoryId(long repositoryId);

	/**
	* Returns the number of document library folders where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching document library folders
	*/
	public int countByRepositoryId(long repositoryId);

	/**
	* Returns all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P(long groupId, long parentFolderId);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_First(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_Last(long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_P(long groupId,
		long parentFolderId);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders
	*/
	public int countByG_P(long groupId, long parentFolderId);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentFolderId);

	/**
	* Returns all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByC_NotS(long companyId, int status);

	/**
	* Returns a range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByC_NotS(long companyId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByC_NotS(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByC_NotS(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByC_NotS_PrevAndNext(long folderId, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of document library folders where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library folders
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param repositoryId the repository ID
	* @param mountPoint the mount point
	* @return the matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByR_M(long repositoryId, boolean mountPoint)
		throws NoSuchFolderException;

	/**
	* Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mountPoint the mount point
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByR_M(long repositoryId, boolean mountPoint);

	/**
	* Returns the document library folder where repositoryId = &#63; and mountPoint = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param repositoryId the repository ID
	* @param mountPoint the mount point
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByR_M(long repositoryId, boolean mountPoint,
		boolean retrieveFromCache);

	/**
	* Removes the document library folder where repositoryId = &#63; and mountPoint = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param mountPoint the mount point
	* @return the document library folder that was removed
	*/
	public DLFolder removeByR_M(long repositoryId, boolean mountPoint)
		throws NoSuchFolderException;

	/**
	* Returns the number of document library folders where repositoryId = &#63; and mountPoint = &#63;.
	*
	* @param repositoryId the repository ID
	* @param mountPoint the mount point
	* @return the number of matching document library folders
	*/
	public int countByR_M(long repositoryId, boolean mountPoint);

	/**
	* Returns all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByR_P(long repositoryId,
		long parentFolderId);

	/**
	* Returns a range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByR_P(long repositoryId,
		long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByR_P(long repositoryId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByR_P(long repositoryId,
		long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByR_P_First(long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByR_P_First(long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByR_P_Last(long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByR_P_Last(long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByR_P_PrevAndNext(long folderId, long repositoryId,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where repositoryId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	*/
	public void removeByR_P(long repositoryId, long parentFolderId);

	/**
	* Returns the number of document library folders where repositoryId = &#63; and parentFolderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders
	*/
	public int countByR_P(long repositoryId, long parentFolderId);

	/**
	* Returns all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByP_N(long parentFolderId,
		java.lang.String name);

	/**
	* Returns a range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByP_N(long parentFolderId,
		java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByP_N(long parentFolderId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByP_N(long parentFolderId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByP_N_First(long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByP_N_First(long parentFolderId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByP_N_Last(long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByP_N_Last(long parentFolderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where parentFolderId = &#63; and name = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByP_N_PrevAndNext(long folderId, long parentFolderId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	*/
	public void removeByP_N(long parentFolderId, java.lang.String name);

	/**
	* Returns the number of document library folders where parentFolderId = &#63; and name = &#63;.
	*
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching document library folders
	*/
	public int countByP_N(long parentFolderId, java.lang.String name);

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_First(long groupId, boolean mountPoint,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_P_First(long groupId, boolean mountPoint,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_Last(long groupId, boolean mountPoint,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_P_Last(long groupId, boolean mountPoint,
		long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_M_P_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId, int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P(long groupId,
		boolean mountPoint, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_M_P_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	*/
	public void removeByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId);

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders
	*/
	public int countByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_M_P(long groupId, boolean mountPoint,
		long parentFolderId);

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_P_N(long groupId, long parentFolderId,
		java.lang.String name) throws NoSuchFolderException;

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_N(long groupId, long parentFolderId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the document library folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the document library folder that was removed
	*/
	public DLFolder removeByG_P_N(long groupId, long parentFolderId,
		java.lang.String name) throws NoSuchFolderException;

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching document library folders
	*/
	public int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name);

	/**
	* Returns all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status);

	/**
	* Returns a range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByF_C_P_NotS(long folderId,
		long companyId, long parentFolderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByF_C_P_NotS_First(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByF_C_P_NotS_First(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByF_C_P_NotS_Last(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByF_C_P_NotS_Last(long folderId, long companyId,
		long parentFolderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Removes all the document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63; from the database.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	*/
	public void removeByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns the number of document library folders where folderId &gt; &#63; and companyId = &#63; and parentFolderId = &#63; and status &ne; &#63;.
	*
	* @param folderId the folder ID
	* @param companyId the company ID
	* @param parentFolderId the parent folder ID
	* @param status the status
	* @return the number of matching document library folders
	*/
	public int countByF_C_P_NotS(long folderId, long companyId,
		long parentFolderId, int status);

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int start,
		int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> findByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_H_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_P_H_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_H_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_P_H_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_M_P_H_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int start,
		int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_M_P_H_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	*/
	public void removeByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden);

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the number of matching document library folders
	*/
	public int countByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_M_P_H(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden);

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_T_H_First(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_T_H_First(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_T_H_Last(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_M_T_H_Last(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_M_T_H_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_T_H(long groupId,
		boolean mountPoint, java.lang.String treePath, boolean hidden,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the document library folders before and after the current document library folder in the ordered set of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param folderId the primary key of the current document library folder
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_M_T_H_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, java.lang.String treePath,
		boolean hidden,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	*/
	public void removeByG_M_T_H(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden);

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @return the number of matching document library folders
	*/
	public int countByG_M_T_H(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and treePath LIKE &#63; and hidden = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param treePath the tree path
	* @param hidden the hidden
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_M_T_H(long groupId, boolean mountPoint,
		java.lang.String treePath, boolean hidden);

	/**
	* Returns all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> findByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_P_H_S_First(long groupId, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the first document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_H_S_First(long groupId, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_P_H_S_Last(long groupId, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns the last document library folder in the ordered set where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library folder, or <code>null</code> if a matching document library folder could not be found
	*/
	public DLFolder fetchByG_P_H_S_Last(long groupId, long parentFolderId,
		boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_P_H_S_PrevAndNext(long folderId, long groupId,
		long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> filterFindByG_P_H_S(long groupId,
		long parentFolderId, boolean hidden, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_P_H_S_PrevAndNext(long folderId,
		long groupId, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	*/
	public void removeByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status);

	/**
	* Returns the number of document library folders where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders
	*/
	public int countByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_P_H_S(long groupId, long parentFolderId,
		boolean hidden, int status);

	/**
	* Returns all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status);

	/**
	* Returns a range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> findByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> findByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library folders
	*/
	public java.util.List<DLFolder> findByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

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
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_H_S_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

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
	*/
	public DLFolder fetchByG_M_P_H_S_First(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a matching document library folder could not be found
	*/
	public DLFolder findByG_M_P_H_S_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

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
	*/
	public DLFolder fetchByG_M_P_H_S_Last(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] findByG_M_P_H_S_PrevAndNext(long folderId, long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Returns all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the matching document library folders that the user has permission to view
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status);

	/**
	* Returns a range of all the document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the document library folders that the user has permissions to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<DLFolder> filterFindByG_M_P_H_S(long groupId,
		boolean mountPoint, long parentFolderId, boolean hidden, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

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
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder[] filterFindByG_M_P_H_S_PrevAndNext(long folderId,
		long groupId, boolean mountPoint, long parentFolderId, boolean hidden,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator)
		throws NoSuchFolderException;

	/**
	* Removes all the document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	*/
	public void removeByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status);

	/**
	* Returns the number of document library folders where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders
	*/
	public int countByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status);

	/**
	* Returns the number of document library folders that the user has permission to view where groupId = &#63; and mountPoint = &#63; and parentFolderId = &#63; and hidden = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param mountPoint the mount point
	* @param parentFolderId the parent folder ID
	* @param hidden the hidden
	* @param status the status
	* @return the number of matching document library folders that the user has permission to view
	*/
	public int filterCountByG_M_P_H_S(long groupId, boolean mountPoint,
		long parentFolderId, boolean hidden, int status);

	/**
	* Caches the document library folder in the entity cache if it is enabled.
	*
	* @param dlFolder the document library folder
	*/
	public void cacheResult(DLFolder dlFolder);

	/**
	* Caches the document library folders in the entity cache if it is enabled.
	*
	* @param dlFolders the document library folders
	*/
	public void cacheResult(java.util.List<DLFolder> dlFolders);

	/**
	* Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	*
	* @param folderId the primary key for the new document library folder
	* @return the new document library folder
	*/
	public DLFolder create(long folderId);

	/**
	* Removes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder that was removed
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder remove(long folderId) throws NoSuchFolderException;

	public DLFolder updateImpl(DLFolder dlFolder);

	/**
	* Returns the document library folder with the primary key or throws a {@link NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder
	* @throws NoSuchFolderException if a document library folder with the primary key could not be found
	*/
	public DLFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException;

	/**
	* Returns the document library folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the document library folder
	* @return the document library folder, or <code>null</code> if a document library folder with the primary key could not be found
	*/
	public DLFolder fetchByPrimaryKey(long folderId);

	@Override
	public java.util.Map<java.io.Serializable, DLFolder> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the document library folders.
	*
	* @return the document library folders
	*/
	public java.util.List<DLFolder> findAll();

	/**
	* Returns a range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of document library folders
	*/
	public java.util.List<DLFolder> findAll(int start, int end);

	/**
	* Returns an ordered range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library folders
	*/
	public java.util.List<DLFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator);

	/**
	* Returns an ordered range of all the document library folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library folders
	*/
	public java.util.List<DLFolder> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFolder> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the document library folders from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of document library folders.
	*
	* @return the number of document library folders
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of document library file entry types associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @return long[] of the primaryKeys of document library file entry types associated with the document library folder
	*/
	public long[] getDLFileEntryTypePrimaryKeys(long pk);

	/**
	* Returns all the document library file entry types associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @return the document library file entry types associated with the document library folder
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk);

	/**
	* Returns a range of all the document library file entry types associated with the document library folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library folder
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @return the range of document library file entry types associated with the document library folder
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the document library file entry types associated with the document library folder.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library folder
	* @param start the lower bound of the range of document library folders
	* @param end the upper bound of the range of document library folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry types associated with the document library folder
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator);

	/**
	* Returns the number of document library file entry types associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @return the number of document library file entry types associated with the document library folder
	*/
	public int getDLFileEntryTypesSize(long pk);

	/**
	* Returns <code>true</code> if the document library file entry type is associated with the document library folder.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	* @return <code>true</code> if the document library file entry type is associated with the document library folder; <code>false</code> otherwise
	*/
	public boolean containsDLFileEntryType(long pk, long dlFileEntryTypePK);

	/**
	* Returns <code>true</code> if the document library folder has any document library file entry types associated with it.
	*
	* @param pk the primary key of the document library folder to check for associations with document library file entry types
	* @return <code>true</code> if the document library folder has any document library file entry types associated with it; <code>false</code> otherwise
	*/
	public boolean containsDLFileEntryTypes(long pk);

	/**
	* Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	*/
	public void addDLFileEntryType(long pk, long dlFileEntryTypePK);

	/**
	* Adds an association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryType the document library file entry type
	*/
	public void addDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType);

	/**
	* Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	*/
	public void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs);

	/**
	* Adds an association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types
	*/
	public void addDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes);

	/**
	* Clears all associations between the document library folder and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder to clear the associated document library file entry types from
	*/
	public void clearDLFileEntryTypes(long pk);

	/**
	* Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	*/
	public void removeDLFileEntryType(long pk, long dlFileEntryTypePK);

	/**
	* Removes the association between the document library folder and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryType the document library file entry type
	*/
	public void removeDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType);

	/**
	* Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	*/
	public void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs);

	/**
	* Removes the association between the document library folder and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types
	*/
	public void removeDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes);

	/**
	* Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the document library folder
	*/
	public void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs);

	/**
	* Sets the document library file entry types associated with the document library folder, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library folder
	* @param dlFileEntryTypes the document library file entry types to be associated with the document library folder
	*/
	public void setDLFileEntryTypes(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes);

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}