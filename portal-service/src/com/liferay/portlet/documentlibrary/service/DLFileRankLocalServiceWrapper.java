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
 * Provides a wrapper for {@link DLFileRankLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRankLocalService
 * @generated
 */
@ProviderType
public class DLFileRankLocalServiceWrapper implements DLFileRankLocalService,
	ServiceWrapper<DLFileRankLocalService> {
	public DLFileRankLocalServiceWrapper(
		DLFileRankLocalService dlFileRankLocalService) {
		_dlFileRankLocalService = dlFileRankLocalService;
	}

	/**
	* Adds the document library file rank to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileRank the document library file rank
	* @return the document library file rank that was added
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank addDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRankLocalService.addDLFileRank(dlFileRank);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank addFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _dlFileRankLocalService.addFileRank(groupId, companyId, userId,
			fileEntryId, serviceContext);
	}

	@Override
	public void checkFileRanks() {
		_dlFileRankLocalService.checkFileRanks();
	}

	/**
	* Creates a new document library file rank with the primary key. Does not add the document library file rank to the database.
	*
	* @param fileRankId the primary key for the new document library file rank
	* @return the new document library file rank
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank createDLFileRank(
		long fileRankId) {
		return _dlFileRankLocalService.createDLFileRank(fileRankId);
	}

	/**
	* Deletes the document library file rank from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileRank the document library file rank
	* @return the document library file rank that was removed
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank deleteDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRankLocalService.deleteDLFileRank(dlFileRank);
	}

	/**
	* Deletes the document library file rank with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileRankId the primary key of the document library file rank
	* @return the document library file rank that was removed
	* @throws PortalException if a document library file rank with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank deleteDLFileRank(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileRankLocalService.deleteDLFileRank(fileRankId);
	}

	@Override
	public void deleteFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		_dlFileRankLocalService.deleteFileRank(dlFileRank);
	}

	@Override
	public void deleteFileRank(long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileRankLocalService.deleteFileRank(fileRankId);
	}

	@Override
	public void deleteFileRanksByFileEntryId(long fileEntryId) {
		_dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	@Override
	public void deleteFileRanksByUserId(long userId) {
		_dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileRankLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void disableFileRanks(long fileEntryId) {
		_dlFileRankLocalService.disableFileRanks(fileEntryId);
	}

	@Override
	public void disableFileRanksByFolderId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileRankLocalService.disableFileRanksByFolderId(folderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dlFileRankLocalService.dynamicQuery();
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
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _dlFileRankLocalService.dynamicQueryCount(dynamicQuery);
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
		return _dlFileRankLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void enableFileRanks(long fileEntryId) {
		_dlFileRankLocalService.enableFileRanks(fileEntryId);
	}

	@Override
	public void enableFileRanksByFolderId(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_dlFileRankLocalService.enableFileRanksByFolderId(folderId);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank fetchDLFileRank(
		long fileRankId) {
		return _dlFileRankLocalService.fetchDLFileRank(fileRankId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileRankLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the document library file rank with the primary key.
	*
	* @param fileRankId the primary key of the document library file rank
	* @return the document library file rank
	* @throws PortalException if a document library file rank with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank getDLFileRank(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileRankLocalService.getDLFileRank(fileRankId);
	}

	/**
	* Returns a range of all the document library file ranks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file ranks
	* @param end the upper bound of the range of document library file ranks (not inclusive)
	* @return the range of document library file ranks
	*/
	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getDLFileRanks(
		int start, int end) {
		return _dlFileRankLocalService.getDLFileRanks(start, end);
	}

	/**
	* Returns the number of document library file ranks.
	*
	* @return the number of document library file ranks
	*/
	@Override
	public int getDLFileRanksCount() {
		return _dlFileRankLocalService.getDLFileRanksCount();
	}

	@Override
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId) {
		return _dlFileRankLocalService.getFileRanks(groupId, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _dlFileRankLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _dlFileRankLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileRankLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the document library file rank in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileRank the document library file rank
	* @return the document library file rank that was updated
	*/
	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRankLocalService.updateDLFileRank(dlFileRank);
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _dlFileRankLocalService.updateFileRank(groupId, companyId,
			userId, fileEntryId, serviceContext);
	}

	@Override
	public DLFileRankLocalService getWrappedService() {
		return _dlFileRankLocalService;
	}

	@Override
	public void setWrappedService(DLFileRankLocalService dlFileRankLocalService) {
		_dlFileRankLocalService = dlFileRankLocalService;
	}

	private DLFileRankLocalService _dlFileRankLocalService;
}