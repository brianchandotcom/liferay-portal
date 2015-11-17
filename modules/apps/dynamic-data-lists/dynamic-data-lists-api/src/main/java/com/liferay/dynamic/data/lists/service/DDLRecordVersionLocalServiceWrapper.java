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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionLocalService
 * @generated
 */
@ProviderType
public class DDLRecordVersionLocalServiceWrapper
	implements DDLRecordVersionLocalService,
		ServiceWrapper<DDLRecordVersionLocalService> {
	public DDLRecordVersionLocalServiceWrapper(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {
		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	/**
	* Adds the d d l record version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was added
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion addDDLRecordVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordVersion ddlRecordVersion) {
		return _ddlRecordVersionLocalService.addDDLRecordVersion(ddlRecordVersion);
	}

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion createDDLRecordVersion(
		long recordVersionId) {
		return _ddlRecordVersionLocalService.createDDLRecordVersion(recordVersionId);
	}

	/**
	* Deletes the d d l record version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was removed
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion deleteDDLRecordVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordVersion ddlRecordVersion) {
		return _ddlRecordVersionLocalService.deleteDDLRecordVersion(ddlRecordVersion);
	}

	/**
	* Deletes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws PortalException if a d d l record version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion deleteDDLRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.deleteDDLRecordVersion(recordVersionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddlRecordVersionLocalService.dynamicQuery();
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
		return _ddlRecordVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddlRecordVersionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddlRecordVersionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _ddlRecordVersionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddlRecordVersionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion fetchDDLRecordVersion(
		long recordVersionId) {
		return _ddlRecordVersionLocalService.fetchDDLRecordVersion(recordVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddlRecordVersionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the d d l record version with the primary key.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws PortalException if a d d l record version with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getDDLRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.getDDLRecordVersion(recordVersionId);
	}

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getDDLRecordVersions(
		int start, int end) {
		return _ddlRecordVersionLocalService.getDDLRecordVersions(start, end);
	}

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	*/
	@Override
	public int getDDLRecordVersionsCount() {
		return _ddlRecordVersionLocalService.getDDLRecordVersionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddlRecordVersionLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.getLatestRecordVersion(recordId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddlRecordVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.getRecordVersion(recordId, version);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionLocalService.getRecordVersion(recordVersionId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordVersion> orderByComparator) {
		return _ddlRecordVersionLocalService.getRecordVersions(recordId, start,
			end, orderByComparator);
	}

	@Override
	public int getRecordVersionsCount(long recordId) {
		return _ddlRecordVersionLocalService.getRecordVersionsCount(recordId);
	}

	/**
	* Updates the d d l record version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordVersion the d d l record version
	* @return the d d l record version that was updated
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion updateDDLRecordVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordVersion ddlRecordVersion) {
		return _ddlRecordVersionLocalService.updateDDLRecordVersion(ddlRecordVersion);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDLRecordVersionLocalService getWrappedDDLRecordVersionLocalService() {
		return _ddlRecordVersionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDLRecordVersionLocalService(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {
		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	@Override
	public DDLRecordVersionLocalService getWrappedService() {
		return _ddlRecordVersionLocalService;
	}

	@Override
	public void setWrappedService(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {
		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	private DDLRecordVersionLocalService _ddlRecordVersionLocalService;
}