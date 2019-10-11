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
 * Provides a wrapper for {@link DDMFormInstanceRecordVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersionLocalService
 * @generated
 */
public class DDMFormInstanceRecordVersionLocalServiceWrapper
	implements DDMFormInstanceRecordVersionLocalService,
			   ServiceWrapper<DDMFormInstanceRecordVersionLocalService> {

	public DDMFormInstanceRecordVersionLocalServiceWrapper(
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService) {

		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
	}

	/**
	 * Adds the ddm form instance record version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecordVersion the ddm form instance record version
	 * @return the ddm form instance record version that was added
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		addDDMFormInstanceRecordVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				ddmFormInstanceRecordVersion) {

		return _ddmFormInstanceRecordVersionLocalService.
			addDDMFormInstanceRecordVersion(ddmFormInstanceRecordVersion);
	}

	/**
	 * Creates a new ddm form instance record version with the primary key. Does not add the ddm form instance record version to the database.
	 *
	 * @param formInstanceRecordVersionId the primary key for the new ddm form instance record version
	 * @return the new ddm form instance record version
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		createDDMFormInstanceRecordVersion(long formInstanceRecordVersionId) {

		return _ddmFormInstanceRecordVersionLocalService.
			createDDMFormInstanceRecordVersion(formInstanceRecordVersionId);
	}

	/**
	 * Deletes the ddm form instance record version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecordVersion the ddm form instance record version
	 * @return the ddm form instance record version that was removed
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		deleteDDMFormInstanceRecordVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				ddmFormInstanceRecordVersion) {

		return _ddmFormInstanceRecordVersionLocalService.
			deleteDDMFormInstanceRecordVersion(ddmFormInstanceRecordVersion);
	}

	/**
	 * Deletes the ddm form instance record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version that was removed
	 * @throws PortalException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
			deleteDDMFormInstanceRecordVersion(long formInstanceRecordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.
			deleteDDMFormInstanceRecordVersion(formInstanceRecordVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmFormInstanceRecordVersionLocalService.dynamicQuery();
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

		return _ddmFormInstanceRecordVersionLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl</code>.
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

		return _ddmFormInstanceRecordVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl</code>.
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

		return _ddmFormInstanceRecordVersionLocalService.dynamicQuery(
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

		return _ddmFormInstanceRecordVersionLocalService.dynamicQueryCount(
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

		return _ddmFormInstanceRecordVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		fetchDDMFormInstanceRecordVersion(long formInstanceRecordVersionId) {

		return _ddmFormInstanceRecordVersionLocalService.
			fetchDDMFormInstanceRecordVersion(formInstanceRecordVersionId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		fetchLatestFormInstanceRecordVersion(
			long userId, long formInstanceId, String formInstanceVersion,
			int status) {

		return _ddmFormInstanceRecordVersionLocalService.
			fetchLatestFormInstanceRecordVersion(
				userId, formInstanceId, formInstanceVersion, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmFormInstanceRecordVersionLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance record version with the primary key.
	 *
	 * @param formInstanceRecordVersionId the primary key of the ddm form instance record version
	 * @return the ddm form instance record version
	 * @throws PortalException if a ddm form instance record version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
			getDDMFormInstanceRecordVersion(long formInstanceRecordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.
			getDDMFormInstanceRecordVersion(formInstanceRecordVersionId);
	}

	/**
	 * Returns a range of all the ddm form instance record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance record versions
	 * @param end the upper bound of the range of ddm form instance record versions (not inclusive)
	 * @return the range of ddm form instance record versions
	 */
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion>
			getDDMFormInstanceRecordVersions(int start, int end) {

		return _ddmFormInstanceRecordVersionLocalService.
			getDDMFormInstanceRecordVersions(start, end);
	}

	/**
	 * Returns the number of ddm form instance record versions.
	 *
	 * @return the number of ddm form instance record versions
	 */
	@Override
	public int getDDMFormInstanceRecordVersionsCount() {
		return _ddmFormInstanceRecordVersionLocalService.
			getDDMFormInstanceRecordVersionsCount();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
			getFormInstanceRecordVersion(long ddmFormInstanceRecordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.
			getFormInstanceRecordVersion(ddmFormInstanceRecordVersionId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
			getFormInstanceRecordVersion(long ddmFormInstanceId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.
			getFormInstanceRecordVersion(ddmFormInstanceId, version);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion>
			getFormInstanceRecordVersions(
				long ddmFormInstanceRecordId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMFormInstanceRecordVersion> orderByComparator) {

		return _ddmFormInstanceRecordVersionLocalService.
			getFormInstanceRecordVersions(
				ddmFormInstanceRecordId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceRecordVersionsCount(
		long ddmFormInstanceRecordId) {

		return _ddmFormInstanceRecordVersionLocalService.
			getFormInstanceRecordVersionsCount(ddmFormInstanceRecordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmFormInstanceRecordVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
			getLatestFormInstanceRecordVersion(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.
			getLatestFormInstanceRecordVersion(ddmFormInstanceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmFormInstanceRecordVersionLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceRecordVersionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the ddm form instance record version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceRecordVersion the ddm form instance record version
	 * @return the ddm form instance record version that was updated
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
		updateDDMFormInstanceRecordVersion(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				ddmFormInstanceRecordVersion) {

		return _ddmFormInstanceRecordVersionLocalService.
			updateDDMFormInstanceRecordVersion(ddmFormInstanceRecordVersion);
	}

	@Override
	public DDMFormInstanceRecordVersionLocalService getWrappedService() {
		return _ddmFormInstanceRecordVersionLocalService;
	}

	@Override
	public void setWrappedService(
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService) {

		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
	}

	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

}