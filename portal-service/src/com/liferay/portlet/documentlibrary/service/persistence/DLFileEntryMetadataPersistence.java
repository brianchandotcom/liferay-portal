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

import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

/**
 * The persistence interface for the document library file entry metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataPersistenceImpl
 * @see DLFileEntryMetadataUtil
 * @generated
 */
public interface DLFileEntryMetadataPersistence extends BasePersistence<DLFileEntryMetadata> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryMetadataUtil} to access the document library file entry metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the document library file entry metadata in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata);

	/**
	* Caches the document library file entry metadatas in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadatas the document library file entry metadatas
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> dlFileEntryMetadatas);

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata create(
		long fileEntryMetadataId);

	/**
	* Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata remove(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry metadata with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByPrimaryKey(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByPrimaryKey(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByUuid_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryTypeId_First(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryTypeId_Last(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileEntryTypeId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
		long fileEntryMetadataId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry metadata where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByF_V(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByF_V(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry metadata where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByF_V(
		long fileEntryId, long fileVersionId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry metadatas.
	*
	* @return the document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry metadatas where fileEntryTypeId = &#63; from the database.
	*
	* @param fileEntryTypeId the file entry type ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileEntryTypeId(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the document library file entry metadata that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata removeByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Removes the document library file entry metadata where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the document library file entry metadata that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata removeByF_V(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;

	/**
	* Removes all the document library file entry metadatas from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileEntryTypeId(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas where fileEntryId = &#63; and fileVersionId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countByF_V(long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}