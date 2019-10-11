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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMFormInstanceVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionLocalService
 * @generated
 */
public class DDMFormInstanceVersionLocalServiceWrapper
	implements DDMFormInstanceVersionLocalService,
			   ServiceWrapper<DDMFormInstanceVersionLocalService> {

	public DDMFormInstanceVersionLocalServiceWrapper(
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService) {

		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
	}

	/**
	 * Adds the ddm form instance version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 * @return the ddm form instance version that was added
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
		addDDMFormInstanceVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
				ddmFormInstanceVersion) {

		return _ddmFormInstanceVersionLocalService.addDDMFormInstanceVersion(
			ddmFormInstanceVersion);
	}

	/**
	 * Creates a new ddm form instance version with the primary key. Does not add the ddm form instance version to the database.
	 *
	 * @param formInstanceVersionId the primary key for the new ddm form instance version
	 * @return the new ddm form instance version
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
		createDDMFormInstanceVersion(long formInstanceVersionId) {

		return _ddmFormInstanceVersionLocalService.createDDMFormInstanceVersion(
			formInstanceVersionId);
	}

	@Override
	public void deleteByFormInstanceId(long ddmFormInstanceId) {
		_ddmFormInstanceVersionLocalService.deleteByFormInstanceId(
			ddmFormInstanceId);
	}

	/**
	 * Deletes the ddm form instance version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 * @return the ddm form instance version that was removed
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
		deleteDDMFormInstanceVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
				ddmFormInstanceVersion) {

		return _ddmFormInstanceVersionLocalService.deleteDDMFormInstanceVersion(
			ddmFormInstanceVersion);
	}

	/**
	 * Deletes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws PortalException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			deleteDDMFormInstanceVersion(long formInstanceVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.deleteDDMFormInstanceVersion(
			formInstanceVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmFormInstanceVersionLocalService.dynamicQuery();
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

		return _ddmFormInstanceVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl</code>.
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

		return _ddmFormInstanceVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl</code>.
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

		return _ddmFormInstanceVersionLocalService.dynamicQuery(
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

		return _ddmFormInstanceVersionLocalService.dynamicQueryCount(
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

		return _ddmFormInstanceVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
		fetchDDMFormInstanceVersion(long formInstanceVersionId) {

		return _ddmFormInstanceVersionLocalService.fetchDDMFormInstanceVersion(
			formInstanceVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmFormInstanceVersionLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance version with the primary key.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws PortalException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getDDMFormInstanceVersion(long formInstanceVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getDDMFormInstanceVersion(
			formInstanceVersionId);
	}

	/**
	 * Returns a range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of ddm form instance versions
	 */
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion>
			getDDMFormInstanceVersions(int start, int end) {

		return _ddmFormInstanceVersionLocalService.getDDMFormInstanceVersions(
			start, end);
	}

	/**
	 * Returns the number of ddm form instance versions.
	 *
	 * @return the number of ddm form instance versions
	 */
	@Override
	public int getDDMFormInstanceVersionsCount() {
		return _ddmFormInstanceVersionLocalService.
			getDDMFormInstanceVersionsCount();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getFormInstanceVersion(long ddmFormInstanceVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getFormInstanceVersion(
			ddmFormInstanceVersionId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getFormInstanceVersion(long ddmFormInstanceId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getFormInstanceVersion(
			ddmFormInstanceId, version);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion>
			getFormInstanceVersions(long ddmFormInstanceId) {

		return _ddmFormInstanceVersionLocalService.getFormInstanceVersions(
			ddmFormInstanceId);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion>
			getFormInstanceVersions(
				long ddmFormInstanceId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMFormInstanceVersion> orderByComparator) {

		return _ddmFormInstanceVersionLocalService.getFormInstanceVersions(
			ddmFormInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceVersionsCount(long ddmFormInstanceId) {
		return _ddmFormInstanceVersionLocalService.getFormInstanceVersionsCount(
			ddmFormInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmFormInstanceVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getLatestFormInstanceVersion(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
			ddmFormInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getLatestFormInstanceVersion(long formInstanceId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
			formInstanceId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmFormInstanceVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceVersionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the ddm form instance version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 * @return the ddm form instance version that was updated
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
		updateDDMFormInstanceVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
				ddmFormInstanceVersion) {

		return _ddmFormInstanceVersionLocalService.updateDDMFormInstanceVersion(
			ddmFormInstanceVersion);
	}

	@Override
	public DDMFormInstanceVersionLocalService getWrappedService() {
		return _ddmFormInstanceVersionLocalService;
	}

	@Override
	public void setWrappedService(
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService) {

		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
	}

	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

}