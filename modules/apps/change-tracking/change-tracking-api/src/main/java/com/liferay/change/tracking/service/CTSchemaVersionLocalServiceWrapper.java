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

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTSchemaVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersionLocalService
 * @generated
 */
public class CTSchemaVersionLocalServiceWrapper
	implements CTSchemaVersionLocalService,
			   ServiceWrapper<CTSchemaVersionLocalService> {

	public CTSchemaVersionLocalServiceWrapper(
		CTSchemaVersionLocalService ctSchemaVersionLocalService) {

		_ctSchemaVersionLocalService = ctSchemaVersionLocalService;
	}

	/**
	 * Adds the ct schema version to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was added
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion addCTSchemaVersion(
		com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return _ctSchemaVersionLocalService.addCTSchemaVersion(ctSchemaVersion);
	}

	/**
	 * Creates a new ct schema version with the primary key. Does not add the ct schema version to the database.
	 *
	 * @param schemaVersionId the primary key for the new ct schema version
	 * @return the new ct schema version
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		createCTSchemaVersion(long schemaVersionId) {

		return _ctSchemaVersionLocalService.createCTSchemaVersion(
			schemaVersionId);
	}

	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		createLatestSchemaVersion(long companyId) {

		return _ctSchemaVersionLocalService.createLatestSchemaVersion(
			companyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctSchemaVersionLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the ct schema version from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was removed
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		deleteCTSchemaVersion(
			com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return _ctSchemaVersionLocalService.deleteCTSchemaVersion(
			ctSchemaVersion);
	}

	/**
	 * Deletes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws PortalException if a ct schema version with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
			deleteCTSchemaVersion(long schemaVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctSchemaVersionLocalService.deleteCTSchemaVersion(
			schemaVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctSchemaVersionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ctSchemaVersionLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctSchemaVersionLocalService.dynamicQuery();
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

		return _ctSchemaVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
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

		return _ctSchemaVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
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

		return _ctSchemaVersionLocalService.dynamicQuery(
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

		return _ctSchemaVersionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ctSchemaVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		fetchCTSchemaVersion(long schemaVersionId) {

		return _ctSchemaVersionLocalService.fetchCTSchemaVersion(
			schemaVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ctSchemaVersionLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ct schema version with the primary key.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws PortalException if a ct schema version with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion getCTSchemaVersion(
			long schemaVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctSchemaVersionLocalService.getCTSchemaVersion(schemaVersionId);
	}

	/**
	 * Returns a range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of ct schema versions
	 */
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTSchemaVersion>
		getCTSchemaVersions(int start, int end) {

		return _ctSchemaVersionLocalService.getCTSchemaVersions(start, end);
	}

	/**
	 * Returns the number of ct schema versions.
	 *
	 * @return the number of ct schema versions
	 */
	@Override
	public int getCTSchemaVersionsCount() {
		return _ctSchemaVersionLocalService.getCTSchemaVersionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ctSchemaVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		getLatestSchemaVersion(long companyId) {

		return _ctSchemaVersionLocalService.getLatestSchemaVersion(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctSchemaVersionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctSchemaVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean isLatestSchemaVersion(
		com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion,
		boolean strict) {

		return _ctSchemaVersionLocalService.isLatestSchemaVersion(
			ctSchemaVersion, strict);
	}

	@Override
	public boolean isLatestSchemaVersion(long ctSchemaVersionId) {
		return _ctSchemaVersionLocalService.isLatestSchemaVersion(
			ctSchemaVersionId);
	}

	/**
	 * Updates the ct schema version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was updated
	 */
	@Override
	public com.liferay.change.tracking.model.CTSchemaVersion
		updateCTSchemaVersion(
			com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return _ctSchemaVersionLocalService.updateCTSchemaVersion(
			ctSchemaVersion);
	}

	@Override
	public CTSchemaVersionLocalService getWrappedService() {
		return _ctSchemaVersionLocalService;
	}

	@Override
	public void setWrappedService(
		CTSchemaVersionLocalService ctSchemaVersionLocalService) {

		_ctSchemaVersionLocalService = ctSchemaVersionLocalService;
	}

	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

}