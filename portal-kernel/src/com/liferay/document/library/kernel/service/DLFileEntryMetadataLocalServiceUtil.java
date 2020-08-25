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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for DLFileEntryMetadata. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryMetadataLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataLocalService
 * @generated
 */
public class DLFileEntryMetadataLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryMetadataLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the document library file entry metadata to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLFileEntryMetadataLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 * @return the document library file entry metadata that was added
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		addDLFileEntryMetadata(
			com.liferay.document.library.kernel.model.DLFileEntryMetadata
				dlFileEntryMetadata) {

		return getService().addDLFileEntryMetadata(dlFileEntryMetadata);
	}

	/**
	 * Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	 *
	 * @param fileEntryMetadataId the primary key for the new document library file entry metadata
	 * @return the new document library file entry metadata
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		createDLFileEntryMetadata(long fileEntryMetadataId) {

		return getService().createDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	 * Deletes the document library file entry metadata from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLFileEntryMetadataLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		deleteDLFileEntryMetadata(
			com.liferay.document.library.kernel.model.DLFileEntryMetadata
				dlFileEntryMetadata) {

		return getService().deleteDLFileEntryMetadata(dlFileEntryMetadata);
	}

	/**
	 * Deletes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLFileEntryMetadataLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 * @throws PortalException if a document library file entry metadata with the primary key could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
			deleteDLFileEntryMetadata(long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDLFileEntryMetadata(fileEntryMetadataId);
	}

	public static void deleteFileEntryMetadata(
			com.liferay.document.library.kernel.model.DLFileEntryMetadata
				fileEntryMetadata)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntryMetadata(fileEntryMetadata);
	}

	public static void deleteFileEntryMetadata(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileEntryMetadata(fileEntryId);
	}

	public static void deleteFileVersionFileEntryMetadata(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFileVersionFileEntryMetadata(fileVersionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		fetchDLFileEntryMetadata(long fileEntryMetadataId) {

		return getService().fetchDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	 * Returns the document library file entry metadata with the matching UUID and company.
	 *
	 * @param uuid the document library file entry metadata's UUID
	 * @param companyId the primary key of the company
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		fetchDLFileEntryMetadataByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchDLFileEntryMetadataByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		fetchFileEntryMetadata(long fileEntryMetadataId) {

		return getService().fetchFileEntryMetadata(fileEntryMetadataId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		fetchFileEntryMetadata(long ddmStructureId, long fileVersionId) {

		return getService().fetchFileEntryMetadata(
			ddmStructureId, fileVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the document library file entry metadata with the primary key.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata
	 * @throws PortalException if a document library file entry metadata with the primary key could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
			getDLFileEntryMetadata(long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	 * Returns the document library file entry metadata with the matching UUID and company.
	 *
	 * @param uuid the document library file entry metadata's UUID
	 * @param companyId the primary key of the company
	 * @return the matching document library file entry metadata
	 * @throws PortalException if a matching document library file entry metadata could not be found
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
			getDLFileEntryMetadataByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLFileEntryMetadataByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of document library file entry metadatas
	 */
	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			getDLFileEntryMetadatas(int start, int end) {

		return getService().getDLFileEntryMetadatas(start, end);
	}

	/**
	 * Returns the number of document library file entry metadatas.
	 *
	 * @return the number of document library file entry metadatas
	 */
	public static int getDLFileEntryMetadatasCount() {
		return getService().getDLFileEntryMetadatasCount();
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
			getFileEntryMetadata(long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryMetadata(fileEntryMetadataId);
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
			getFileEntryMetadata(long ddmStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryMetadata(ddmStructureId, fileVersionId);
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			getFileVersionFileEntryMetadatas(long fileVersionId) {

		return getService().getFileVersionFileEntryMetadatas(fileVersionId);
	}

	public static long getFileVersionFileEntryMetadatasCount(
		long fileVersionId) {

		return getService().getFileVersionFileEntryMetadatasCount(
			fileVersionId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			getMismatchedCompanyIdFileEntryMetadatas() {

		return getService().getMismatchedCompanyIdFileEntryMetadatas();
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			getNoStructuresFileEntryMetadatas() {

		return getService().getNoStructuresFileEntryMetadatas();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the document library file entry metadata in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLFileEntryMetadataLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 * @return the document library file entry metadata that was updated
	 */
	public static com.liferay.document.library.kernel.model.DLFileEntryMetadata
		updateDLFileEntryMetadata(
			com.liferay.document.library.kernel.model.DLFileEntryMetadata
				dlFileEntryMetadata) {

		return getService().updateDLFileEntryMetadata(dlFileEntryMetadata);
	}

	public static void updateFileEntryMetadata(
			long companyId,
			java.util.List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
				ddmStructures,
			long fileEntryId, long fileVersionId,
			java.util.Map
				<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
					ddmFormValuesMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFileEntryMetadata(
			companyId, ddmStructures, fileEntryId, fileVersionId,
			ddmFormValuesMap, serviceContext);
	}

	public static void updateFileEntryMetadata(
			long fileEntryTypeId, long fileEntryId, long fileVersionId,
			java.util.Map
				<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
					ddmFormValuesMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFileEntryMetadata(
			fileEntryTypeId, fileEntryId, fileVersionId, ddmFormValuesMap,
			serviceContext);
	}

	public static DLFileEntryMetadataLocalService getService() {
		if (_service == null) {
			_service =
				(DLFileEntryMetadataLocalService)PortalBeanLocatorUtil.locate(
					DLFileEntryMetadataLocalService.class.getName());
		}

		return _service;
	}

	private static DLFileEntryMetadataLocalService _service;

}