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

package com.liferay.trash.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TrashVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersionLocalService
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.trash.service.impl.TrashVersionLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class TrashVersionLocalServiceWrapper implements TrashVersionLocalService,
	ServiceWrapper<TrashVersionLocalService> {
	public TrashVersionLocalServiceWrapper(
		TrashVersionLocalService trashVersionLocalService) {
		_trashVersionLocalService = trashVersionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _trashVersionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _trashVersionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _trashVersionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _trashVersionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _trashVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the trash version to the database. Also notifies the appropriate model listeners.
	*
	* @param trashVersion the trash version
	* @return the trash version that was added
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion addTrashVersion(
		com.liferay.trash.kernel.model.TrashVersion trashVersion) {
		return _trashVersionLocalService.addTrashVersion(trashVersion);
	}

	@Override
	public com.liferay.trash.kernel.model.TrashVersion addTrashVersion(
		long trashEntryId, java.lang.String className, long classPK,
		int status,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		return _trashVersionLocalService.addTrashVersion(trashEntryId,
			className, classPK, status, typeSettingsProperties);
	}

	/**
	* Creates a new trash version with the primary key. Does not add the trash version to the database.
	*
	* @param versionId the primary key for the new trash version
	* @return the new trash version
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion createTrashVersion(
		long versionId) {
		return _trashVersionLocalService.createTrashVersion(versionId);
	}

	/**
	* Deletes the trash version from the database. Also notifies the appropriate model listeners.
	*
	* @param trashVersion the trash version
	* @return the trash version that was removed
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion deleteTrashVersion(
		com.liferay.trash.kernel.model.TrashVersion trashVersion) {
		return _trashVersionLocalService.deleteTrashVersion(trashVersion);
	}

	@Override
	public com.liferay.trash.kernel.model.TrashVersion deleteTrashVersion(
		java.lang.String className, long classPK) {
		return _trashVersionLocalService.deleteTrashVersion(className, classPK);
	}

	/**
	* Deletes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version that was removed
	* @throws PortalException if a trash version with the primary key could not be found
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion deleteTrashVersion(
		long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _trashVersionLocalService.deleteTrashVersion(versionId);
	}

	@Override
	public com.liferay.trash.kernel.model.TrashVersion fetchTrashVersion(
		long versionId) {
		return _trashVersionLocalService.fetchTrashVersion(versionId);
	}

	@Override
	public com.liferay.trash.kernel.model.TrashVersion fetchVersion(
		java.lang.String className, long classPK) {
		return _trashVersionLocalService.fetchVersion(className, classPK);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #fetchVersion(String, long)}
	*/
	@Deprecated
	@Override
	public com.liferay.trash.kernel.model.TrashVersion fetchVersion(
		long entryId, java.lang.String className, long classPK) {
		return _trashVersionLocalService.fetchVersion(entryId, className,
			classPK);
	}

	/**
	* Returns the trash version with the primary key.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version
	* @throws PortalException if a trash version with the primary key could not be found
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion getTrashVersion(
		long versionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _trashVersionLocalService.getTrashVersion(versionId);
	}

	/**
	* Updates the trash version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param trashVersion the trash version
	* @return the trash version that was updated
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashVersion updateTrashVersion(
		com.liferay.trash.kernel.model.TrashVersion trashVersion) {
		return _trashVersionLocalService.updateTrashVersion(trashVersion);
	}

	/**
	* Returns the number of trash versions.
	*
	* @return the number of trash versions
	*/
	@Override
	public int getTrashVersionsCount() {
		return _trashVersionLocalService.getTrashVersionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _trashVersionLocalService.getOSGiServiceIdentifier();
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
		return _trashVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _trashVersionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _trashVersionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of trash versions
	*/
	@Override
	public java.util.List<com.liferay.trash.kernel.model.TrashVersion> getTrashVersions(
		int start, int end) {
		return _trashVersionLocalService.getTrashVersions(start, end);
	}

	@Override
	public java.util.List<com.liferay.trash.kernel.model.TrashVersion> getVersions(
		long entryId) {
		return _trashVersionLocalService.getVersions(entryId);
	}

	@Override
	public java.util.List<com.liferay.trash.kernel.model.TrashVersion> getVersions(
		long entryId, java.lang.String className) {
		return _trashVersionLocalService.getVersions(entryId, className);
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
		return _trashVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _trashVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public TrashVersionLocalService getWrappedService() {
		return _trashVersionLocalService;
	}

	@Override
	public void setWrappedService(
		TrashVersionLocalService trashVersionLocalService) {
		_trashVersionLocalService = trashVersionLocalService;
	}

	private TrashVersionLocalService _trashVersionLocalService;
}