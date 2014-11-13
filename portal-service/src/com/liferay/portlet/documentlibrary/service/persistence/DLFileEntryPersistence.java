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

import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * The persistence interface for the document library file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPersistenceImpl
 * @see DLFileEntryUtil
 * @generated
 */
@ProviderType
public interface DLFileEntryPersistence extends BasePersistence<DLFileEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryUtil} to access the document library file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the document library file entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the document library file entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByUuid_PrevAndNext(
		long fileEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of document library file entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the document library file entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the document library file entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file entry that was removed
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the number of document library file entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the document library file entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByUuid_C_PrevAndNext(
		long fileEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of document library file entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the document library file entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByGroupId_PrevAndNext(
		long fileEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] filterFindByGroupId_PrevAndNext(
		long fileEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of document library file entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the document library file entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the document library file entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where companyId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByCompanyId_PrevAndNext(
		long fileEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of document library file entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library file entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the document library file entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByRepositoryId(
		long repositoryId);

	/**
	* Returns a range of all the document library file entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByRepositoryId(
		long repositoryId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByRepositoryId(
		long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByRepositoryId_First(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByRepositoryId_First(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByRepositoryId_Last(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByRepositoryId_Last(
		long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where repositoryId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByRepositoryId_PrevAndNext(
		long fileEntryId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where repositoryId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	*/
	public void removeByRepositoryId(long repositoryId);

	/**
	* Returns the number of document library file entries where repositoryId = &#63;.
	*
	* @param repositoryId the repository ID
	* @return the number of matching document library file entries
	*/
	public int countByRepositoryId(long repositoryId);

	/**
	* Returns all the document library file entries where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByMimeType(
		java.lang.String mimeType);

	/**
	* Returns a range of all the document library file entries where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByMimeType(
		java.lang.String mimeType, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByMimeType(
		java.lang.String mimeType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByMimeType_First(
		java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByMimeType_First(
		java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByMimeType_Last(
		java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByMimeType_Last(
		java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where mimeType = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByMimeType_PrevAndNext(
		long fileEntryId, java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where mimeType = &#63; from the database.
	*
	* @param mimeType the mime type
	*/
	public void removeByMimeType(java.lang.String mimeType);

	/**
	* Returns the number of document library file entries where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the number of matching document library file entries
	*/
	public int countByMimeType(java.lang.String mimeType);

	/**
	* Returns all the document library file entries where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId);

	/**
	* Returns a range of all the document library file entries where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByFileEntryTypeId_First(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByFileEntryTypeId_First(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByFileEntryTypeId_Last(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByFileEntryTypeId_Last(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByFileEntryTypeId_PrevAndNext(
		long fileEntryId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where fileEntryTypeId = &#63; from the database.
	*
	* @param fileEntryTypeId the file entry type ID
	*/
	public void removeByFileEntryTypeId(long fileEntryTypeId);

	/**
	* Returns the number of document library file entries where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entries
	*/
	public int countByFileEntryTypeId(long fileEntryTypeId);

	/**
	* Returns all the document library file entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U(
		long groupId, long userId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U(
		long groupId, long userId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByG_U_PrevAndNext(
		long fileEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U(
		long groupId, long userId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U(
		long groupId, long userId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] filterFindByG_U_PrevAndNext(
		long fileEntryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching document library file entries
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_U(long groupId, long userId);

	/**
	* Returns all the document library file entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long folderId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByG_F_PrevAndNext(
		long fileEntryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long folderId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] filterFindByG_F_PrevAndNext(
		long fileEntryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long[] folderIds);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns all the document library file entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long[] folderIds);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Removes all the document library file entries where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	*/
	public void removeByG_F(long groupId, long folderId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file entries
	*/
	public int countByG_F(long groupId, long folderId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching document library file entries
	*/
	public int countByG_F(long groupId, long[] folderIds);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long folderId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long[] folderIds);

	/**
	* Returns all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByR_F(
		long repositoryId, long folderId);

	/**
	* Returns a range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByR_F(
		long repositoryId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByR_F(
		long repositoryId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByR_F_First(
		long repositoryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByR_F_First(
		long repositoryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByR_F_Last(
		long repositoryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByR_F_Last(
		long repositoryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByR_F_PrevAndNext(
		long fileEntryId, long repositoryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where repositoryId = &#63; and folderId = &#63; from the database.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	*/
	public void removeByR_F(long repositoryId, long folderId);

	/**
	* Returns the number of document library file entries where repositoryId = &#63; and folderId = &#63;.
	*
	* @param repositoryId the repository ID
	* @param folderId the folder ID
	* @return the number of matching document library file entries
	*/
	public int countByR_F(long repositoryId, long folderId);

	/**
	* Returns all the document library file entries where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByF_N(
		long folderId, java.lang.String name);

	/**
	* Returns a range of all the document library file entries where folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByF_N(
		long folderId, java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByF_N(
		long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByF_N_First(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByF_N_First(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByF_N_Last(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByF_N_Last(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByF_N_PrevAndNext(
		long fileEntryId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Removes all the document library file entries where folderId = &#63; and name = &#63; from the database.
	*
	* @param folderId the folder ID
	* @param name the name
	*/
	public void removeByF_N(long folderId, java.lang.String name);

	/**
	* Returns the number of document library file entries where folderId = &#63; and name = &#63;.
	*
	* @param folderId the folder ID
	* @param name the name
	* @return the number of matching document library file entries
	*/
	public int countByF_N(long folderId, java.lang.String name);

	/**
	* Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_U_F_First(
		long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_U_F_First(
		long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_U_F_Last(
		long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_U_F_Last(
		long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByG_U_F_PrevAndNext(
		long fileEntryId, long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] filterFindByG_U_F_PrevAndNext(
		long fileEntryId, long groupId, long userId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_U_F(
		long groupId, long userId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Removes all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	*/
	public void removeByG_U_F(long groupId, long userId, long folderId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @return the number of matching document library file entries
	*/
	public int countByG_U_F(long groupId, long userId, long folderId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @return the number of matching document library file entries
	*/
	public int countByG_U_F(long groupId, long userId, long[] folderIds);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderId the folder ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_U_F(long groupId, long userId, long folderId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param folderIds the folder IDs
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_U_F(long groupId, long userId, long[] folderIds);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_N(
		long groupId, long folderId, java.lang.String name);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_N(
		long groupId, long folderId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the document library file entry that was removed
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry removeByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the number of matching document library file entries
	*/
	public int countByG_F_N(long groupId, long folderId, java.lang.String name);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileName the file name
	* @return the matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_FN(
		long groupId, long folderId, java.lang.String fileName)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileName the file name
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_FN(
		long groupId, long folderId, java.lang.String fileName);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileName the file name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_FN(
		long groupId, long folderId, java.lang.String fileName,
		boolean retrieveFromCache);

	/**
	* Removes the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileName the file name
	* @return the document library file entry that was removed
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry removeByG_F_FN(
		long groupId, long folderId, java.lang.String fileName)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileName = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileName the file name
	* @return the number of matching document library file entries
	*/
	public int countByG_F_FN(long groupId, long folderId,
		java.lang.String fileName);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @return the matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_T(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_T(
		long groupId, long folderId, java.lang.String title);

	/**
	* Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_T(
		long groupId, long folderId, java.lang.String title,
		boolean retrieveFromCache);

	/**
	* Removes the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @return the document library file entry that was removed
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry removeByG_F_T(
		long groupId, long folderId, java.lang.String title)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and title = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @return the number of matching document library file entries
	*/
	public int countByG_F_T(long groupId, long folderId, java.lang.String title);

	/**
	* Returns all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_F_First(
		long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_F_First(
		long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByG_F_F_Last(
		long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByG_F_F_Last(
		long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] findByG_F_F_PrevAndNext(
		long fileEntryId, long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long folderId, long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param fileEntryId the primary key of the current document library file entry
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry[] filterFindByG_F_F_PrevAndNext(
		long fileEntryId, long groupId, long folderId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId);

	/**
	* Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> filterFindByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Returns all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId);

	/**
	* Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findByG_F_F(
		long groupId, long[] folderIds, long fileEntryTypeId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Removes all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	*/
	public void removeByG_F_F(long groupId, long folderId, long fileEntryTypeId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entries
	*/
	public int countByG_F_F(long groupId, long folderId, long fileEntryTypeId);

	/**
	* Returns the number of document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entries
	*/
	public int countByG_F_F(long groupId, long[] folderIds, long fileEntryTypeId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_F_F(long groupId, long folderId,
		long fileEntryTypeId);

	/**
	* Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entries that the user has permission to view
	*/
	public int filterCountByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId);

	/**
	* Caches the document library file entry in the entity cache if it is enabled.
	*
	* @param dlFileEntry the document library file entry
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry);

	/**
	* Caches the document library file entries in the entity cache if it is enabled.
	*
	* @param dlFileEntries the document library file entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> dlFileEntries);

	/**
	* Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	*
	* @param fileEntryId the primary key for the new document library file entry
	* @return the new document library file entry
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry create(
		long fileEntryId);

	/**
	* Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry remove(
		long fileEntryId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry);

	/**
	* Returns the document library file entry with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryException} if it could not be found.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry findByPrimaryKey(
		long fileEntryId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

	/**
	* Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryId the primary key of the document library file entry
	* @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntry fetchByPrimaryKey(
		long fileEntryId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portlet.documentlibrary.model.DLFileEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the document library file entries.
	*
	* @return the document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findAll();

	/**
	* Returns a range of all the document library file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @return the range of document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the document library file entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entries
	* @param end the upper bound of the range of document library file entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entries
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntry> orderByComparator);

	/**
	* Removes all the document library file entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of document library file entries.
	*
	* @return the number of document library file entries
	*/
	public int countAll();
}