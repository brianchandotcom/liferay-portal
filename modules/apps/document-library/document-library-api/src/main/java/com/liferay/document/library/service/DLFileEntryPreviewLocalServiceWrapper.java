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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileEntryPreviewLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreviewLocalService
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewLocalServiceWrapper
	implements DLFileEntryPreviewLocalService,
		ServiceWrapper<DLFileEntryPreviewLocalService> {
	public DLFileEntryPreviewLocalServiceWrapper(
		DLFileEntryPreviewLocalService dlFileEntryPreviewLocalService) {
		_dlFileEntryPreviewLocalService = dlFileEntryPreviewLocalService;
	}

	/**
	* Adds the dl file entry preview to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryPreview the dl file entry preview
	* @return the dl file entry preview that was added
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview addDLFileEntryPreview(
		com.liferay.document.library.model.DLFileEntryPreview dlFileEntryPreview) {
		return _dlFileEntryPreviewLocalService.addDLFileEntryPreview(dlFileEntryPreview);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link DLFileEntryPreviewLocalServiceUtil} to access the dl file entry preview local service.
	*/
	@Override
	public void addDLFileEntryPreview(long fileEntryId, long fileVersionId,
		int previewType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryPreviewLocalService.addDLFileEntryPreview(fileEntryId,
			fileVersionId, previewType);
	}

	/**
	* Creates a new dl file entry preview with the primary key. Does not add the dl file entry preview to the database.
	*
	* @param fileEntryPreviewId the primary key for the new dl file entry preview
	* @return the new dl file entry preview
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview createDLFileEntryPreview(
		long fileEntryPreviewId) {
		return _dlFileEntryPreviewLocalService.createDLFileEntryPreview(fileEntryPreviewId);
	}

	/**
	* Deletes the dl file entry preview from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryPreview the dl file entry preview
	* @return the dl file entry preview that was removed
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview deleteDLFileEntryPreview(
		com.liferay.document.library.model.DLFileEntryPreview dlFileEntryPreview) {
		return _dlFileEntryPreviewLocalService.deleteDLFileEntryPreview(dlFileEntryPreview);
	}

	/**
	* Deletes the dl file entry preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview that was removed
	* @throws PortalException if a dl file entry preview with the primary key could not be found
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview deleteDLFileEntryPreview(
		long fileEntryPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.deleteDLFileEntryPreview(fileEntryPreviewId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileEntryPreviewLocalService.dynamicQuery();
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
		return _dlFileEntryPreviewLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileEntryPreviewLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileEntryPreviewLocalService.dynamicQuery(dynamicQuery,
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
		return _dlFileEntryPreviewLocalService.dynamicQueryCount(dynamicQuery);
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
		return _dlFileEntryPreviewLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.document.library.model.DLFileEntryPreview fetchDLFileEntryPreview(
		long fileEntryPreviewId) {
		return _dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(fileEntryPreviewId);
	}

	@Override
	public com.liferay.document.library.model.DLFileEntryPreview fetchDLFileEntryPreview(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(fileEntryId,
			fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.DLFileEntryPreview fetchDLFileEntryPreview(
		long fileEntryId, long fileVersionId, int previewType) {
		return _dlFileEntryPreviewLocalService.fetchDLFileEntryPreview(fileEntryId,
			fileVersionId, previewType);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileEntryPreviewLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the dl file entry preview with the primary key.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview
	* @throws PortalException if a dl file entry preview with the primary key could not be found
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview getDLFileEntryPreview(
		long fileEntryPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreview(fileEntryPreviewId);
	}

	@Override
	public com.liferay.document.library.model.DLFileEntryPreview getDLFileEntryPreview(
		long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreview(fileEntryId,
			fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.DLFileEntryPreview getDLFileEntryPreview(
		long fileEntryId, long fileVersionId, int previewType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreview(fileEntryId,
			fileVersionId, previewType);
	}

	/**
	* Returns a range of all the dl file entry previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @return the range of dl file entry previews
	*/
	@Override
	public java.util.List<com.liferay.document.library.model.DLFileEntryPreview> getDLFileEntryPreviews(
		int start, int end) {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreviews(start, end);
	}

	@Override
	public java.util.List<com.liferay.document.library.model.DLFileEntryPreview> getDLFileEntryPreviews(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreviews(fileEntryId);
	}

	/**
	* Returns the number of dl file entry previews.
	*
	* @return the number of dl file entry previews
	*/
	@Override
	public int getDLFileEntryPreviewsCount() {
		return _dlFileEntryPreviewLocalService.getDLFileEntryPreviewsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _dlFileEntryPreviewLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileEntryPreviewLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryPreviewLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the dl file entry preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileEntryPreview the dl file entry preview
	* @return the dl file entry preview that was updated
	*/
	@Override
	public com.liferay.document.library.model.DLFileEntryPreview updateDLFileEntryPreview(
		com.liferay.document.library.model.DLFileEntryPreview dlFileEntryPreview) {
		return _dlFileEntryPreviewLocalService.updateDLFileEntryPreview(dlFileEntryPreview);
	}

	@Override
	public void updateDLFileEntryPreview(long fileEntryPreviewId,
		int fileEntryPreviewType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileEntryPreviewLocalService.updateDLFileEntryPreview(fileEntryPreviewId,
			fileEntryPreviewType);
	}

	@Override
	public DLFileEntryPreviewLocalService getWrappedService() {
		return _dlFileEntryPreviewLocalService;
	}

	@Override
	public void setWrappedService(
		DLFileEntryPreviewLocalService dlFileEntryPreviewLocalService) {
		_dlFileEntryPreviewLocalService = dlFileEntryPreviewLocalService;
	}

	private DLFileEntryPreviewLocalService _dlFileEntryPreviewLocalService;
}