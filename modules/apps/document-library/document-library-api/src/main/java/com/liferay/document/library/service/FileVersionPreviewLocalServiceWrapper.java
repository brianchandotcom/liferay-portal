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

package com.liferay.document.library.service;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FileVersionPreviewLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FileVersionPreviewLocalService
 * @generated
 */
public class FileVersionPreviewLocalServiceWrapper
	implements FileVersionPreviewLocalService,
		ServiceWrapper<FileVersionPreviewLocalService> {
	public FileVersionPreviewLocalServiceWrapper(
		FileVersionPreviewLocalService fileVersionPreviewLocalService) {
		_fileVersionPreviewLocalService = fileVersionPreviewLocalService;
	}

	/**
	* Adds the file version preview to the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was added
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview addFileVersionPreview(
		com.liferay.document.library.model.FileVersionPreview fileVersionPreview) {
		return _fileVersionPreviewLocalService.addFileVersionPreview(fileVersionPreview);
	}

	@Override
	public void addFileVersionPreview(long fileEntryId, long fileVersionId,
		int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		_fileVersionPreviewLocalService.addFileVersionPreview(fileEntryId,
			fileVersionId, previewStatus);
	}

	/**
	* Creates a new file version preview with the primary key. Does not add the file version preview to the database.
	*
	* @param fileVersionPreviewId the primary key for the new file version preview
	* @return the new file version preview
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview createFileVersionPreview(
		long fileVersionPreviewId) {
		return _fileVersionPreviewLocalService.createFileVersionPreview(fileVersionPreviewId);
	}

	@Override
	public void deleteFileEntryFileVersionPreviews(long fileEntryId) {
		_fileVersionPreviewLocalService.deleteFileEntryFileVersionPreviews(fileEntryId);
	}

	/**
	* Deletes the file version preview from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was removed
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview deleteFileVersionPreview(
		com.liferay.document.library.model.FileVersionPreview fileVersionPreview) {
		return _fileVersionPreviewLocalService.deleteFileVersionPreview(fileVersionPreview);
	}

	/**
	* Deletes the file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview that was removed
	* @throws PortalException if a file version preview with the primary key could not be found
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview deleteFileVersionPreview(
		long fileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.deleteFileVersionPreview(fileVersionPreviewId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fileVersionPreviewLocalService.dynamicQuery();
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
		return _fileVersionPreviewLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fileVersionPreviewLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fileVersionPreviewLocalService.dynamicQuery(dynamicQuery,
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
		return _fileVersionPreviewLocalService.dynamicQueryCount(dynamicQuery);
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
		return _fileVersionPreviewLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.document.library.model.FileVersionPreview fetchFileVersionPreview(
		long fileVersionPreviewId) {
		return _fileVersionPreviewLocalService.fetchFileVersionPreview(fileVersionPreviewId);
	}

	@Override
	public com.liferay.document.library.model.FileVersionPreview fetchFileVersionPreview(
		long fileEntryId, long fileVersionId) {
		return _fileVersionPreviewLocalService.fetchFileVersionPreview(fileEntryId,
			fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.FileVersionPreview fetchFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {
		return _fileVersionPreviewLocalService.fetchFileVersionPreview(fileEntryId,
			fileVersionId, previewStatus);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _fileVersionPreviewLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.document.library.model.FileVersionPreview> getFileEntryFileVersionPreviews(
		long fileEntryId) {
		return _fileVersionPreviewLocalService.getFileEntryFileVersionPreviews(fileEntryId);
	}

	/**
	* Returns the file version preview with the primary key.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview
	* @throws PortalException if a file version preview with the primary key could not be found
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview getFileVersionPreview(
		long fileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.getFileVersionPreview(fileVersionPreviewId);
	}

	@Override
	public com.liferay.document.library.model.FileVersionPreview getFileVersionPreview(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.getFileVersionPreview(fileEntryId,
			fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.FileVersionPreview getFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.getFileVersionPreview(fileEntryId,
			fileVersionId, previewStatus);
	}

	/**
	* Returns a range of all the file version previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @return the range of file version previews
	*/
	@Override
	public java.util.List<com.liferay.document.library.model.FileVersionPreview> getFileVersionPreviews(
		int start, int end) {
		return _fileVersionPreviewLocalService.getFileVersionPreviews(start, end);
	}

	/**
	* Returns the number of file version previews.
	*
	* @return the number of file version previews
	*/
	@Override
	public int getFileVersionPreviewsCount() {
		return _fileVersionPreviewLocalService.getFileVersionPreviewsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _fileVersionPreviewLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _fileVersionPreviewLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fileVersionPreviewLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasFileVersionPreview(long fileEntryId, long fileVersionId,
		int previewStatus) {
		return _fileVersionPreviewLocalService.hasFileVersionPreview(fileEntryId,
			fileVersionId, previewStatus);
	}

	/**
	* Updates the file version preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was updated
	*/
	@Override
	public com.liferay.document.library.model.FileVersionPreview updateFileVersionPreview(
		com.liferay.document.library.model.FileVersionPreview fileVersionPreview) {
		return _fileVersionPreviewLocalService.updateFileVersionPreview(fileVersionPreview);
	}

	@Override
	public void updateFileVersionPreview(long fileVersionPreviewId,
		int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		_fileVersionPreviewLocalService.updateFileVersionPreview(fileVersionPreviewId,
			previewStatus);
	}

	@Override
	public FileVersionPreviewLocalService getWrappedService() {
		return _fileVersionPreviewLocalService;
	}

	@Override
	public void setWrappedService(
		FileVersionPreviewLocalService fileVersionPreviewLocalService) {
		_fileVersionPreviewLocalService = fileVersionPreviewLocalService;
	}

	private FileVersionPreviewLocalService _fileVersionPreviewLocalService;
}