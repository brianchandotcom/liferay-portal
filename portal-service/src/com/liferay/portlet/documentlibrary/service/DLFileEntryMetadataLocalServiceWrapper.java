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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileEntryMetadataLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataLocalService
 * @generated
 */
@ProviderType
public class DLFileEntryMetadataLocalServiceWrapper
	implements DLFileEntryMetadataLocalService,
		ServiceWrapper<DLFileEntryMetadataLocalService> {
	public DLFileEntryMetadataLocalServiceWrapper(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	/**
	* Adds the document library file entry metadata to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was added
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata addDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata) {
		return _dlFileEntryMetadataLocalService.addDLFileEntryMetadata(dlFileEntryMetadata);
	}

	/**
	* Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	*
	* @param fileEntryMetadataId the primary key for the new document library file entry metadata
	* @return the new document library file entry metadata
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata createDLFileEntryMetadata(
		long fileEntryMetadataId) {
		return _dlFileEntryMetadataLocalService.createDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Deletes the document library file entry metadata from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was removed
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata deleteDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata) {
		return _dlFileEntryMetadataLocalService.deleteDLFileEntryMetadata(dlFileEntryMetadata);
	}

	/**
	* Deletes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata that was removed
	* @throws PortalException if a document library file entry metadata with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata deleteDLFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.deleteDLFileEntryMetadata(fileEntryMetadataId);
	}

	@Override
	public void deleteFileEntryMetadata(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryMetadataLocalService.deleteFileEntryMetadata(fileEntryId);
	}

	@Override
	public void deleteFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fileEntryMetadata)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryMetadataLocalService.deleteFileEntryMetadata(fileEntryMetadata);
	}

	@Override
	public void deleteFileVersionFileEntryMetadata(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryMetadataLocalService.deleteFileVersionFileEntryMetadata(fileVersionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileEntryMetadataLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileEntryMetadataLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _dlFileEntryMetadataLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _dlFileEntryMetadataLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _dlFileEntryMetadataLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _dlFileEntryMetadataLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchDLFileEntryMetadata(
		long fileEntryMetadataId) {
		return _dlFileEntryMetadataLocalService.fetchDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Returns the document library file entry metadata with the matching UUID and company.
	*
	* @param uuid the document library file entry metadata's UUID
	* @param companyId the primary key of the company
	* @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchDLFileEntryMetadataByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _dlFileEntryMetadataLocalService.fetchDLFileEntryMetadataByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchFileEntryMetadata(
		long ddmStructureId, long fileVersionId) {
		return _dlFileEntryMetadataLocalService.fetchFileEntryMetadata(ddmStructureId,
			fileVersionId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata fetchFileEntryMetadata(
		long fileEntryMetadataId) {
		return _dlFileEntryMetadataLocalService.fetchFileEntryMetadata(fileEntryMetadataId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileEntryMetadataLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the document library file entry metadata with the primary key.
	*
	* @param fileEntryMetadataId the primary key of the document library file entry metadata
	* @return the document library file entry metadata
	* @throws PortalException if a document library file entry metadata with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getDLFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.getDLFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* Returns the document library file entry metadata with the matching UUID and company.
	*
	* @param uuid the document library file entry metadata's UUID
	* @param companyId the primary key of the company
	* @return the matching document library file entry metadata
	* @throws PortalException if a matching document library file entry metadata could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getDLFileEntryMetadataByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.getDLFileEntryMetadataByUuidAndCompanyId(uuid,
			companyId);
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
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getDLFileEntryMetadatas(
		int start, int end) {
		return _dlFileEntryMetadataLocalService.getDLFileEntryMetadatas(start,
			end);
	}

	/**
	* Returns the number of document library file entry metadatas.
	*
	* @return the number of document library file entry metadatas
	*/
	@Override
	public int getDLFileEntryMetadatasCount() {
		return _dlFileEntryMetadataLocalService.getDLFileEntryMetadatasCount();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getFileEntryMetadata(
		long ddmStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.getFileEntryMetadata(ddmStructureId,
			fileVersionId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata getFileEntryMetadata(
		long fileEntryMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.getFileEntryMetadata(fileEntryMetadataId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getFileVersionFileEntryMetadatasCount(long)}
	*/
	@Deprecated
	@Override
	public long getFileEntryMetadataCount(long fileEntryId, long fileVersionId) {
		return _dlFileEntryMetadataLocalService.getFileEntryMetadataCount(fileEntryId,
			fileVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getFileVersionFileEntryMetadatas(
		long fileVersionId) {
		return _dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(fileVersionId);
	}

	@Override
	public long getFileVersionFileEntryMetadatasCount(long fileVersionId) {
		return _dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatasCount(fileVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _dlFileEntryMetadataLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getMismatchedCompanyIdFileEntryMetadatas() {
		return _dlFileEntryMetadataLocalService.getMismatchedCompanyIdFileEntryMetadatas();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata> getNoStructuresFileEntryMetadatas() {
		return _dlFileEntryMetadataLocalService.getNoStructuresFileEntryMetadatas();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlFileEntryMetadataLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryMetadataLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the document library file entry metadata in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryMetadata the document library file entry metadata
	* @return the document library file entry metadata that was updated
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata updateDLFileEntryMetadata(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata) {
		return _dlFileEntryMetadataLocalService.updateDLFileEntryMetadata(dlFileEntryMetadata);
	}

	@Override
	public void updateFileEntryMetadata(long companyId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.DDMStructure> ddmStructures,
		long fileEntryId, long fileVersionId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> ddmFormValuesMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryMetadataLocalService.updateFileEntryMetadata(companyId,
			ddmStructures, fileEntryId, fileVersionId, ddmFormValuesMap,
			serviceContext);
	}

	@Override
	public void updateFileEntryMetadata(long fileEntryTypeId, long fileEntryId,
		long fileVersionId,
		java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> ddmFormValuesMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryMetadataLocalService.updateFileEntryMetadata(fileEntryTypeId,
			fileEntryId, fileVersionId, ddmFormValuesMap, serviceContext);
	}

	@Override
	public DLFileEntryMetadataLocalService getWrappedService() {
		return _dlFileEntryMetadataLocalService;
	}

	@Override
	public void setWrappedService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
}