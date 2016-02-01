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

import com.liferay.portlet.documentlibrary.exception.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

/**
 * The persistence interface for the document library file entry metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileEntryMetadataPersistenceImpl
 * @see DLFileEntryMetadataUtil
 * @generated
 */
@ProviderType
public interface DLFileEntryMetadataPersistence extends BasePersistence<DLFileEntryMetadata> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryMetadataUtil} to access the document library file entry metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata[] findByUuid_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entry metadatas
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata[] findByUuid_C_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file entry metadatas
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId);

	/**
	* Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public void removeByFileEntryId(long fileEntryId);

	/**
	* Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file entry metadatas
	*/
	public int countByFileEntryId(long fileEntryId);

	/**
	* Returns all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId);

	/**
	* Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end);

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
		long fileEntryMetadataId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException;

	/**
	* Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public void removeByFileVersionId(long fileVersionId);

	/**
	* Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	*/
	public int countByFileVersionId(long fileVersionId);

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata findByD_F(long DDMStructureId, long fileVersionId)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId);

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId, boolean retrieveFromCache);

	/**
	* Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the document library file entry metadata that was removed
	*/
	public DLFileEntryMetadata removeByD_F(long DDMStructureId,
		long fileVersionId) throws NoSuchFileEntryMetadataException;

	/**
	* Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	*/
	public int countByD_F(long DDMStructureId, long fileVersionId);

	/**
	* Caches the document library file entry metadata in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	*/
	public void cacheResult(DLFileEntryMetadata dlFileEntryMetadata);

	/**
	* Caches the document library file entry metadatas in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadatas the document library file entry metadatas
	*/
	public void cacheResult(
		java.util.List<DLFileEntryMetadata> dlFileEntryMetadatas);

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	public DLFileEntryMetadata create(long fileEntryMetadataId);

	/**
	* Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata remove(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException;

	public DLFileEntryMetadata updateImpl(
		DLFileEntryMetadata dlFileEntryMetadata);

	/**
	* Returns the document library file entry metadata with the primary key or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata findByPrimaryKey(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException;

	/**
	* Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	*/
	public DLFileEntryMetadata fetchByPrimaryKey(long fileEntryMetadataId);

	@Override
	public java.util.Map<java.io.Serializable, DLFileEntryMetadata> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the document library file entry metadatas.
	*
	* @return the document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findAll();

	/**
	* Returns a range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findAll(int start, int end);

	/**
	* Returns an ordered range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator);

	/**
	* Returns an ordered range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file entry metadatas
	*/
	public java.util.List<DLFileEntryMetadata> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the document library file entry metadatas from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}