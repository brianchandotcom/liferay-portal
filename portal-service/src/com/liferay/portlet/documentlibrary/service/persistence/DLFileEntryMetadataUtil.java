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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

import java.util.List;

/**
 * The persistence utility for the document library file entry metadata service. This utility wraps {@link DLFileEntryMetadataPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataPersistence
 * @see DLFileEntryMetadataPersistenceImpl
 * @generated
 */
public class DLFileEntryMetadataUtil {
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
	public static void clearCache(DLFileEntryMetadata dlFileEntryMetadata) {
		getPersistence().clearCache(dlFileEntryMetadata);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileEntryMetadata update(
		DLFileEntryMetadata dlFileEntryMetadata) throws SystemException {
		return getPersistence().update(dlFileEntryMetadata);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLFileEntryMetadata update(
		DLFileEntryMetadata dlFileEntryMetadata, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(dlFileEntryMetadata, serviceContext);
	}

	/**
	* Returns all the document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByUuid_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileEntryMetadataId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns a range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileEntryTypeId(fileEntryTypeId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryTypeId the file entry type ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileEntryTypeId(fileEntryTypeId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryTypeId_First(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryTypeId_First(fileEntryTypeId,
			orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileEntryTypeId_First(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileEntryTypeId_First(fileEntryTypeId,
			orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryTypeId_Last(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryTypeId_Last(fileEntryTypeId,
			orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileEntryTypeId_Last(
		long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileEntryTypeId_Last(fileEntryTypeId,
			orderByComparator);
	}

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileEntryTypeId the file entry type ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileEntryTypeId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryTypeId_PrevAndNext(fileEntryMetadataId,
			fileEntryTypeId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where fileEntryTypeId = &#63; from the database.
	*
	* @param fileEntryTypeId the file entry type ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFileEntryTypeId(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns the number of document library file entry metadatas where fileEntryTypeId = &#63;.
	*
	* @param fileEntryTypeId the file entry type ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileEntryTypeId(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

	/**
	* Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileEntryMetadataId,
			fileEntryId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

	/**
	* Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
		long fileEntryMetadataId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(fileEntryMetadataId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByD_F(DDMStructureId, fileVersionId, retrieveFromCache);
	}

	/**
	* Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the document library file entry metadata that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata removeByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().removeByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Caches the document library file entry metadata in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata) {
		getPersistence().cacheResult(dlFileEntryMetadata);
	}

	/**
	* Caches the document library file entry metadatas in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadatas the document library file entry metadatas
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> dlFileEntryMetadatas) {
		getPersistence().cacheResult(dlFileEntryMetadatas);
	}

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata create(
		long fileEntryMetadataId) {
		return getPersistence().create(fileEntryMetadataId);
	}

	/**
	* Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata remove(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().remove(fileEntryMetadataId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileEntryMetadata);
	}

	/**
	* Returns the document library file entry metadata with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata findByPrimaryKey(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByPrimaryKey(fileEntryMetadataId);
	}

	/**
	* Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchByPrimaryKey(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileEntryMetadataId);
	}

	/**
	* Returns all the document library file entry metadatas.
	*
	* @return the document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @return the range of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry metadatas.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry metadatas
	* @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileEntryMetadataPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileEntryMetadataPersistence)PortalBeanLocatorUtil.locate(DLFileEntryMetadataPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryMetadataUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(DLFileEntryMetadataPersistence persistence) {
	}

	private static DLFileEntryMetadataPersistence _persistence;
}