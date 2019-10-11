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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileVersionPreviewLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPreviewLocalService
 * @generated
 */
public class DLFileVersionPreviewLocalServiceWrapper
	implements DLFileVersionPreviewLocalService,
			   ServiceWrapper<DLFileVersionPreviewLocalService> {

	public DLFileVersionPreviewLocalServiceWrapper(
		DLFileVersionPreviewLocalService dlFileVersionPreviewLocalService) {

		_dlFileVersionPreviewLocalService = dlFileVersionPreviewLocalService;
	}

	/**
	 * Adds the dl file version preview to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was added
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		addDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return _dlFileVersionPreviewLocalService.addDLFileVersionPreview(
			dlFileVersionPreview);
	}

	@Override
	public void addDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileVersionPreviewLocalService.addDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Creates a new dl file version preview with the primary key. Does not add the dl file version preview to the database.
	 *
	 * @param dlFileVersionPreviewId the primary key for the new dl file version preview
	 * @return the new dl file version preview
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		createDLFileVersionPreview(long dlFileVersionPreviewId) {

		return _dlFileVersionPreviewLocalService.createDLFileVersionPreview(
			dlFileVersionPreviewId);
	}

	@Override
	public void deleteDLFileEntryFileVersionPreviews(long fileEntryId) {
		_dlFileVersionPreviewLocalService.deleteDLFileEntryFileVersionPreviews(
			fileEntryId);
	}

	/**
	 * Deletes the dl file version preview from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was removed
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		deleteDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return _dlFileVersionPreviewLocalService.deleteDLFileVersionPreview(
			dlFileVersionPreview);
	}

	/**
	 * Deletes the dl file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview that was removed
	 * @throws PortalException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
			deleteDLFileVersionPreview(long dlFileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.deleteDLFileVersionPreview(
			dlFileVersionPreviewId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileVersionPreviewLocalService.dynamicQuery();
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

		return _dlFileVersionPreviewLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
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

		return _dlFileVersionPreviewLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
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

		return _dlFileVersionPreviewLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _dlFileVersionPreviewLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _dlFileVersionPreviewLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(long dlFileVersionPreviewId) {

		return _dlFileVersionPreviewLocalService.fetchDLFileVersionPreview(
			dlFileVersionPreviewId);
	}

	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(long fileEntryId, long fileVersionId) {

		return _dlFileVersionPreviewLocalService.fetchDLFileVersionPreview(
			fileEntryId, fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		fetchDLFileVersionPreview(
			long fileEntryId, long fileVersionId, int previewStatus) {

		return _dlFileVersionPreviewLocalService.fetchDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dlFileVersionPreviewLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the dl file version preview with the primary key.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview
	 * @throws PortalException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(long dlFileVersionPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.getDLFileVersionPreview(
			dlFileVersionPreviewId);
	}

	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.getDLFileVersionPreview(
			fileEntryId, fileVersionId);
	}

	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
			getDLFileVersionPreview(
				long fileEntryId, long fileVersionId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.getDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Returns a range of all the dl file version previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @return the range of dl file version previews
	 */
	@Override
	public java.util.List
		<com.liferay.document.library.model.DLFileVersionPreview>
			getDLFileVersionPreviews(int start, int end) {

		return _dlFileVersionPreviewLocalService.getDLFileVersionPreviews(
			start, end);
	}

	/**
	 * Returns the number of dl file version previews.
	 *
	 * @return the number of dl file version previews
	 */
	@Override
	public int getDLFileVersionPreviewsCount() {
		return _dlFileVersionPreviewLocalService.
			getDLFileVersionPreviewsCount();
	}

	@Override
	public java.util.List
		<com.liferay.document.library.model.DLFileVersionPreview>
			getFileEntryDLFileVersionPreviews(long fileEntryId) {

		return _dlFileVersionPreviewLocalService.
			getFileEntryDLFileVersionPreviews(fileEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlFileVersionPreviewLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileVersionPreviewLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileVersionPreviewLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public boolean hasDLFileVersionPreview(
		long fileEntryId, long fileVersionId, int previewStatus) {

		return _dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
			fileEntryId, fileVersionId, previewStatus);
	}

	/**
	 * Updates the dl file version preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 * @return the dl file version preview that was updated
	 */
	@Override
	public com.liferay.document.library.model.DLFileVersionPreview
		updateDLFileVersionPreview(
			com.liferay.document.library.model.DLFileVersionPreview
				dlFileVersionPreview) {

		return _dlFileVersionPreviewLocalService.updateDLFileVersionPreview(
			dlFileVersionPreview);
	}

	@Override
	public void updateDLFileVersionPreview(
			long dlFileVersionPreviewId, int previewStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileVersionPreviewLocalService.updateDLFileVersionPreview(
			dlFileVersionPreviewId, previewStatus);
	}

	@Override
	public DLFileVersionPreviewLocalService getWrappedService() {
		return _dlFileVersionPreviewLocalService;
	}

	@Override
	public void setWrappedService(
		DLFileVersionPreviewLocalService dlFileVersionPreviewLocalService) {

		_dlFileVersionPreviewLocalService = dlFileVersionPreviewLocalService;
	}

	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

}