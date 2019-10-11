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

package com.liferay.document.library.content.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLContentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLContentLocalService
 * @generated
 */
public class DLContentLocalServiceWrapper
	implements DLContentLocalService, ServiceWrapper<DLContentLocalService> {

	public DLContentLocalServiceWrapper(
		DLContentLocalService dlContentLocalService) {

		_dlContentLocalService = dlContentLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLContentLocalServiceUtil} to access the document library content local service. Add custom service methods to <code>com.liferay.document.library.content.service.impl.DLContentLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		byte[] bytes) {

		return _dlContentLocalService.addContent(
			companyId, repositoryId, path, version, bytes);
	}

	@Override
	public com.liferay.document.library.content.model.DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		java.io.InputStream inputStream, long size) {

		return _dlContentLocalService.addContent(
			companyId, repositoryId, path, version, inputStream, size);
	}

	/**
	 * Adds the document library content to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlContent the document library content
	 * @return the document library content that was added
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent addDLContent(
		com.liferay.document.library.content.model.DLContent dlContent) {

		return _dlContentLocalService.addDLContent(dlContent);
	}

	/**
	 * Creates a new document library content with the primary key. Does not add the document library content to the database.
	 *
	 * @param contentId the primary key for the new document library content
	 * @return the new document library content
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent createDLContent(
		long contentId) {

		return _dlContentLocalService.createDLContent(contentId);
	}

	@Override
	public void deleteContent(
			long companyId, long repositoryId, String path, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlContentLocalService.deleteContent(
			companyId, repositoryId, path, version);
	}

	@Override
	public void deleteContents(long companyId, long repositoryId, String path) {
		_dlContentLocalService.deleteContents(companyId, repositoryId, path);
	}

	@Override
	public void deleteContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		_dlContentLocalService.deleteContentsByDirectory(
			companyId, repositoryId, dirName);
	}

	/**
	 * Deletes the document library content from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlContent the document library content
	 * @return the document library content that was removed
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent deleteDLContent(
		com.liferay.document.library.content.model.DLContent dlContent) {

		return _dlContentLocalService.deleteDLContent(dlContent);
	}

	/**
	 * Deletes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content that was removed
	 * @throws PortalException if a document library content with the primary key could not be found
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent deleteDLContent(
			long contentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlContentLocalService.deleteDLContent(contentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlContentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlContentLocalService.dynamicQuery();
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

		return _dlContentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.content.model.impl.DLContentModelImpl</code>.
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

		return _dlContentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.content.model.impl.DLContentModelImpl</code>.
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

		return _dlContentLocalService.dynamicQuery(
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

		return _dlContentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _dlContentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.document.library.content.model.DLContent fetchDLContent(
		long contentId) {

		return _dlContentLocalService.fetchDLContent(contentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dlContentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.document.library.content.model.DLContent getContent(
			long companyId, long repositoryId, String path)
		throws com.liferay.document.library.content.exception.
			NoSuchContentException {

		return _dlContentLocalService.getContent(companyId, repositoryId, path);
	}

	@Override
	public com.liferay.document.library.content.model.DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws com.liferay.document.library.content.exception.
			NoSuchContentException {

		return _dlContentLocalService.getContent(
			companyId, repositoryId, path, version);
	}

	@Override
	public java.util.List<com.liferay.document.library.content.model.DLContent>
		getContents(long companyId, long repositoryId) {

		return _dlContentLocalService.getContents(companyId, repositoryId);
	}

	@Override
	public java.util.List<com.liferay.document.library.content.model.DLContent>
		getContents(long companyId, long repositoryId, String path) {

		return _dlContentLocalService.getContents(
			companyId, repositoryId, path);
	}

	@Override
	public java.util.List<com.liferay.document.library.content.model.DLContent>
		getContentsByDirectory(
			long companyId, long repositoryId, String dirName) {

		return _dlContentLocalService.getContentsByDirectory(
			companyId, repositoryId, dirName);
	}

	@Override
	public com.liferay.document.library.content.model.DLContentDataBlobModel
		getDataBlobModel(java.io.Serializable primaryKey) {

		return _dlContentLocalService.getDataBlobModel(primaryKey);
	}

	/**
	 * Returns the document library content with the primary key.
	 *
	 * @param contentId the primary key of the document library content
	 * @return the document library content
	 * @throws PortalException if a document library content with the primary key could not be found
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent getDLContent(
			long contentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlContentLocalService.getDLContent(contentId);
	}

	/**
	 * Returns a range of all the document library contents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.content.model.impl.DLContentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library contents
	 * @param end the upper bound of the range of document library contents (not inclusive)
	 * @return the range of document library contents
	 */
	@Override
	public java.util.List<com.liferay.document.library.content.model.DLContent>
		getDLContents(int start, int end) {

		return _dlContentLocalService.getDLContents(start, end);
	}

	/**
	 * Returns the number of document library contents.
	 *
	 * @return the number of document library contents
	 */
	@Override
	public int getDLContentsCount() {
		return _dlContentLocalService.getDLContentsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlContentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlContentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlContentLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasContent(
		long companyId, long repositoryId, String path, String version) {

		return _dlContentLocalService.hasContent(
			companyId, repositoryId, path, version);
	}

	/**
	 * Updates the document library content in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlContent the document library content
	 * @return the document library content that was updated
	 */
	@Override
	public com.liferay.document.library.content.model.DLContent updateDLContent(
		com.liferay.document.library.content.model.DLContent dlContent) {

		return _dlContentLocalService.updateDLContent(dlContent);
	}

	@Override
	public void updateDLContent(
		long companyId, long oldRepositoryId, long newRepositoryId,
		String oldPath, String newPath) {

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);
	}

	@Override
	public DLContentLocalService getWrappedService() {
		return _dlContentLocalService;
	}

	@Override
	public void setWrappedService(DLContentLocalService dlContentLocalService) {
		_dlContentLocalService = dlContentLocalService;
	}

	private DLContentLocalService _dlContentLocalService;

}