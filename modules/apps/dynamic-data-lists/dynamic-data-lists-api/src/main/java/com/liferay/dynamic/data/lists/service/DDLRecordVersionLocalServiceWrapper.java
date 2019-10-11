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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionLocalService
 * @generated
 */
public class DDLRecordVersionLocalServiceWrapper
	implements DDLRecordVersionLocalService,
			   ServiceWrapper<DDLRecordVersionLocalService> {

	public DDLRecordVersionLocalServiceWrapper(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {

		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	/**
	 * Adds the ddl record version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the ddl record version
	 * @return the ddl record version that was added
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		addDDLRecordVersion(
			com.liferay.dynamic.data.lists.model.DDLRecordVersion
				ddlRecordVersion) {

		return _ddlRecordVersionLocalService.addDDLRecordVersion(
			ddlRecordVersion);
	}

	/**
	 * Creates a new ddl record version with the primary key. Does not add the ddl record version to the database.
	 *
	 * @param recordVersionId the primary key for the new ddl record version
	 * @return the new ddl record version
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		createDDLRecordVersion(long recordVersionId) {

		return _ddlRecordVersionLocalService.createDDLRecordVersion(
			recordVersionId);
	}

	/**
	 * Deletes the ddl record version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the ddl record version
	 * @return the ddl record version that was removed
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		deleteDDLRecordVersion(
			com.liferay.dynamic.data.lists.model.DDLRecordVersion
				ddlRecordVersion) {

		return _ddlRecordVersionLocalService.deleteDDLRecordVersion(
			ddlRecordVersion);
	}

	/**
	 * Deletes the ddl record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordVersionId the primary key of the ddl record version
	 * @return the ddl record version that was removed
	 * @throws PortalException if a ddl record version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			deleteDDLRecordVersion(long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.deleteDDLRecordVersion(
			recordVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.deletePersistedModel(
			persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl</code>.
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

		return _ddlRecordVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl</code>.
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

		return _ddlRecordVersionLocalService.dynamicQuery(
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

		return _ddlRecordVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		fetchDDLRecordVersion(long recordVersionId) {

		return _ddlRecordVersionLocalService.fetchDDLRecordVersion(
			recordVersionId);
	}

	/**
	 * Returns the latest record version matching the user, the record set, the
	 * record set version and workflow status.
	 *
	 * @param userId the primary key of the user
	 * @param recordSetId the primary key of the record set
	 * @param recordSetVersion the version of the record set
	 * @param status the workflow status
	 * @return the latest matching record version or <code>null</code>
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		fetchLatestRecordVersion(
			long userId, long recordSetId, String recordSetVersion,
			int status) {

		return _ddlRecordVersionLocalService.fetchLatestRecordVersion(
			userId, recordSetId, recordSetVersion, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddlRecordVersionLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ddl record version with the primary key.
	 *
	 * @param recordVersionId the primary key of the ddl record version
	 * @return the ddl record version
	 * @throws PortalException if a ddl record version with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getDDLRecordVersion(long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.getDDLRecordVersion(
			recordVersionId);
	}

	/**
	 * Returns a range of all the ddl record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of ddl record versions
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
		getDDLRecordVersions(int start, int end) {

		return _ddlRecordVersionLocalService.getDDLRecordVersions(start, end);
	}

	/**
	 * Returns the number of ddl record versions.
	 *
	 * @return the number of ddl record versions
	 */
	@Override
	public int getDDLRecordVersionsCount() {
		return _ddlRecordVersionLocalService.getDDLRecordVersionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddlRecordVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the record's latest record version.
	 *
	 * @param recordId the primary key of the record
	 * @return the latest record version for the given record
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getLatestRecordVersion(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.getLatestRecordVersion(recordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddlRecordVersionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the record version by its ID.
	 *
	 * @param recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if a matching record set could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.getRecordVersion(recordVersionId);
	}

	/**
	 * Returns the record version matching the record and version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the record version
	 * @return the record version matching the record primary key and version
	 * @throws PortalException if a matching record set could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionLocalService.getRecordVersion(
			recordId, version);
	}

	/**
	 * Returns an ordered range of record versions matching the record's ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param recordId the primary key of the record
	 * @param start the lower bound of the range of record versions to return
	 * @param end the upper bound of the range of record versions to return
	 (not inclusive)
	 * @param orderByComparator the comparator used to order the record
	 versions
	 * @return the range of matching record versions ordered by the comparator
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
		getRecordVersions(
			long recordId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
					orderByComparator) {

		return _ddlRecordVersionLocalService.getRecordVersions(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the number of matching record versions
	 */
	@Override
	public int getRecordVersionsCount(long recordId) {
		return _ddlRecordVersionLocalService.getRecordVersionsCount(recordId);
	}

	/**
	 * Updates the ddl record version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordVersion the ddl record version
	 * @return the ddl record version that was updated
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
		updateDDLRecordVersion(
			com.liferay.dynamic.data.lists.model.DDLRecordVersion
				ddlRecordVersion) {

		return _ddlRecordVersionLocalService.updateDDLRecordVersion(
			ddlRecordVersion);
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