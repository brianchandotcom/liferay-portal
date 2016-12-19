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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionLocalService
 * @generated
 */
@ProviderType
public class DDMStructureVersionLocalServiceWrapper
	implements DDMStructureVersionLocalService,
		ServiceWrapper<DDMStructureVersionLocalService> {
	public DDMStructureVersionLocalServiceWrapper(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMForm getStructureVersionDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion structureVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getStructureVersionDDMForm(structureVersion);
	}

	/**
	* Adds the ddm structure version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion addDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return _ddmStructureVersionLocalService.addDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Creates a new ddm structure version with the primary key. Does not add the ddm structure version to the database.
	*
	* @param structureVersionId the primary key for the new ddm structure version
	* @return the new ddm structure version
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion createDDMStructureVersion(
		long structureVersionId) {
		return _ddmStructureVersionLocalService.createDDMStructureVersion(structureVersionId);
	}

	/**
	* Deletes the ddm structure version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion deleteDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return _ddmStructureVersionLocalService.deleteDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Deletes the ddm structure version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version that was removed
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion deleteDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.deleteDDMStructureVersion(structureVersionId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion fetchDDMStructureVersion(
		long structureVersionId) {
		return _ddmStructureVersionLocalService.fetchDDMStructureVersion(structureVersionId);
	}

	/**
	* Returns the ddm structure version with the primary key.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getDDMStructureVersion(structureVersionId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getLatestStructureVersion(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getLatestStructureVersion(structureId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getStructureVersion(
		long structureId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getStructureVersion(structureId,
			version);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getStructureVersion(structureVersionId);
	}

	/**
	* Updates the ddm structure version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion updateDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return _ddmStructureVersionLocalService.updateDDMStructureVersion(ddmStructureVersion);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmStructureVersionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmStructureVersionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmStructureVersionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of ddm structure versions.
	*
	* @return the number of ddm structure versions
	*/
	@Override
	public int getDDMStructureVersionsCount() {
		return _ddmStructureVersionLocalService.getDDMStructureVersionsCount();
	}

	@Override
	public int getStructureVersionsCount(long structureId) {
		return _ddmStructureVersionLocalService.getStructureVersionsCount(structureId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmStructureVersionLocalService.getOSGiServiceIdentifier();
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
		return _ddmStructureVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStructureVersionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStructureVersionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the ddm structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm structure versions
	* @param end the upper bound of the range of ddm structure versions (not inclusive)
	* @return the range of ddm structure versions
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getDDMStructureVersions(
		int start, int end) {
		return _ddmStructureVersionLocalService.getDDMStructureVersions(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getStructureVersions(
		long structureId) {
		return _ddmStructureVersionLocalService.getStructureVersions(structureId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> orderByComparator) {
		return _ddmStructureVersionLocalService.getStructureVersions(structureId,
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
		return _ddmStructureVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmStructureVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public DDMStructureVersionLocalService getWrappedService() {
		return _ddmStructureVersionLocalService;
	}

	@Override
	public void setWrappedService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;
}