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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

import java.util.List;

/**
 * The persistence utility for the document library file entry metadata service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileEntryMetadataPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileEntryMetadataPersistenceImpl
 * @generated
 */
@ProviderType
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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileEntryMetadata> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileEntryMetadata update(
		DLFileEntryMetadata dlFileEntryMetadata) {
		return getPersistence().update(dlFileEntryMetadata);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLFileEntryMetadata update(
		DLFileEntryMetadata dlFileEntryMetadata, ServiceContext serviceContext) {
		return getPersistence().update(dlFileEntryMetadata, serviceContext);
	}

	/**
	* Returns all the document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entry metadatas
	*/
	public static List<DLFileEntryMetadata> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<DLFileEntryMetadata> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<DLFileEntryMetadata> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<DLFileEntryMetadata> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	*
	* @param fileEntryMetadataId the primary key of the current document library file entry metadata
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata[] findByUuid_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileEntryMetadataId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entry metadatas
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file entry metadatas
	*/
	public static List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<DLFileEntryMetadata> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static DLFileEntryMetadata[] findByUuid_C_PrevAndNext(
		long fileEntryMetadataId, java.lang.String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(fileEntryMetadataId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file entry metadatas
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file entry metadatas
	*/
	public static List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId) {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

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
	public static List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end) {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

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
	public static List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

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
	public static List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByFileEntryId_Last(
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
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
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileEntryMetadataId,
			fileEntryId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public static void removeByFileEntryId(long fileEntryId) {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file entry metadatas
	*/
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns all the document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadatas
	*/
	public static List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

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
	public static List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

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
	public static List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

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
	public static List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
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
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
		long fileEntryMetadataId, long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(fileEntryMetadataId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	*/
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata findByD_F(long DDMStructureId,
		long fileVersionId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId) {
		return getPersistence().fetchByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	public static DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByD_F(DDMStructureId, fileVersionId, retrieveFromCache);
	}

	/**
	* Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the document library file entry metadata that was removed
	*/
	public static DLFileEntryMetadata removeByD_F(long DDMStructureId,
		long fileVersionId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().removeByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID
	* @param fileVersionId the file version ID
	* @return the number of matching document library file entry metadatas
	*/
	public static int countByD_F(long DDMStructureId, long fileVersionId) {
		return getPersistence().countByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Caches the document library file entry metadata in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	*/
	public static void cacheResult(DLFileEntryMetadata dlFileEntryMetadata) {
		getPersistence().cacheResult(dlFileEntryMetadata);
	}

	/**
	* Caches the document library file entry metadatas in the entity cache if it is enabled.
	*
	* @param dlFileEntryMetadatas the document library file entry metadatas
	*/
	public static void cacheResult(
		List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		getPersistence().cacheResult(dlFileEntryMetadatas);
	}

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	public static DLFileEntryMetadata create(long fileEntryMetadataId) {
		return getPersistence().create(fileEntryMetadataId);
	}

	/**
	* Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata remove(long fileEntryMetadataId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().remove(fileEntryMetadataId);
	}

	public static DLFileEntryMetadata updateImpl(
		DLFileEntryMetadata dlFileEntryMetadata) {
		return getPersistence().updateImpl(dlFileEntryMetadata);
	}

	/**
	* Returns the document library file entry metadata with the primary key or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata findByPrimaryKey(long fileEntryMetadataId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException {
		return getPersistence().findByPrimaryKey(fileEntryMetadataId);
	}

	/**
	* Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	*/
	public static DLFileEntryMetadata fetchByPrimaryKey(
		long fileEntryMetadataId) {
		return getPersistence().fetchByPrimaryKey(fileEntryMetadataId);
	}

	public static java.util.Map<java.io.Serializable, DLFileEntryMetadata> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the document library file entry metadatas.
	*
	* @return the document library file entry metadatas
	*/
	public static List<DLFileEntryMetadata> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<DLFileEntryMetadata> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<DLFileEntryMetadata> findAll(int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<DLFileEntryMetadata> findAll(int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the document library file entry metadatas from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLFileEntryMetadataPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileEntryMetadataPersistence)PortalBeanLocatorUtil.locate(DLFileEntryMetadataPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryMetadataUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLFileEntryMetadataPersistence _persistence;
}