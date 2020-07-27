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

package com.liferay.app.builder.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AppBuilderAppVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersionLocalService
 * @generated
 */
public class AppBuilderAppVersionLocalServiceWrapper
	implements AppBuilderAppVersionLocalService,
			   ServiceWrapper<AppBuilderAppVersionLocalService> {

	public AppBuilderAppVersionLocalServiceWrapper(
		AppBuilderAppVersionLocalService appBuilderAppVersionLocalService) {

		_appBuilderAppVersionLocalService = appBuilderAppVersionLocalService;
	}

	/**
	 * Adds the app builder app version to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersion the app builder app version
	 * @return the app builder app version that was added
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		addAppBuilderAppVersion(
			com.liferay.app.builder.model.AppBuilderAppVersion
				appBuilderAppVersion) {

		return _appBuilderAppVersionLocalService.addAppBuilderAppVersion(
			appBuilderAppVersion);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			addAppBuilderAppVersion(
				long groupId, long companyId, long userId, long appBuilderAppId,
				long ddlRecordSetId, long ddmStructureId,
				long ddmStructureLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.addAppBuilderAppVersion(
			groupId, companyId, userId, appBuilderAppId, ddlRecordSetId,
			ddmStructureId, ddmStructureLayoutId);
	}

	/**
	 * Creates a new app builder app version with the primary key. Does not add the app builder app version to the database.
	 *
	 * @param appBuilderAppVersionId the primary key for the new app builder app version
	 * @return the new app builder app version
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		createAppBuilderAppVersion(long appBuilderAppVersionId) {

		return _appBuilderAppVersionLocalService.createAppBuilderAppVersion(
			appBuilderAppVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the app builder app version from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersion the app builder app version
	 * @return the app builder app version that was removed
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		deleteAppBuilderAppVersion(
			com.liferay.app.builder.model.AppBuilderAppVersion
				appBuilderAppVersion) {

		return _appBuilderAppVersionLocalService.deleteAppBuilderAppVersion(
			appBuilderAppVersion);
	}

	/**
	 * Deletes the app builder app version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version that was removed
	 * @throws PortalException if a app builder app version with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			deleteAppBuilderAppVersion(long appBuilderAppVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.deleteAppBuilderAppVersion(
			appBuilderAppVersionId);
	}

	@Override
	public void deleteAppBuilderAppVersions(long appBuilderAppId) {
		_appBuilderAppVersionLocalService.deleteAppBuilderAppVersions(
			appBuilderAppId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _appBuilderAppVersionLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _appBuilderAppVersionLocalService.dynamicQuery();
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

		return _appBuilderAppVersionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl</code>.
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

		return _appBuilderAppVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl</code>.
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

		return _appBuilderAppVersionLocalService.dynamicQuery(
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

		return _appBuilderAppVersionLocalService.dynamicQueryCount(
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

		return _appBuilderAppVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		fetchAppBuilderAppVersion(long appBuilderAppVersionId) {

		return _appBuilderAppVersionLocalService.fetchAppBuilderAppVersion(
			appBuilderAppVersionId);
	}

	/**
	 * Returns the app builder app version matching the UUID and group.
	 *
	 * @param uuid the app builder app version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		fetchAppBuilderAppVersionByUuidAndGroupId(String uuid, long groupId) {

		return _appBuilderAppVersionLocalService.
			fetchAppBuilderAppVersionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		fetchLatestAppBuilderAppVersion(long appBuilderAppId) {

		return _appBuilderAppVersionLocalService.
			fetchLatestAppBuilderAppVersion(appBuilderAppId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _appBuilderAppVersionLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app version with the primary key.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws PortalException if a app builder app version with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			getAppBuilderAppVersion(long appBuilderAppVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.getAppBuilderAppVersion(
			appBuilderAppVersionId);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			getAppBuilderAppVersion(long appBuilderAppId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.getAppBuilderAppVersion(
			appBuilderAppId, version);
	}

	/**
	 * Returns the app builder app version matching the UUID and group.
	 *
	 * @param uuid the app builder app version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app version
	 * @throws PortalException if a matching app builder app version could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			getAppBuilderAppVersionByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.
			getAppBuilderAppVersionByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of app builder app versions
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderAppVersion>
		getAppBuilderAppVersions(int start, int end) {

		return _appBuilderAppVersionLocalService.getAppBuilderAppVersions(
			start, end);
	}

	/**
	 * Returns all the app builder app versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder app versions
	 * @param companyId the primary key of the company
	 * @return the matching app builder app versions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderAppVersion>
		getAppBuilderAppVersionsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _appBuilderAppVersionLocalService.
			getAppBuilderAppVersionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of app builder app versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder app versions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching app builder app versions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderAppVersion>
		getAppBuilderAppVersionsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderAppVersion>
					orderByComparator) {

		return _appBuilderAppVersionLocalService.
			getAppBuilderAppVersionsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of app builder app versions.
	 *
	 * @return the number of app builder app versions
	 */
	@Override
	public int getAppBuilderAppVersionsCount() {
		return _appBuilderAppVersionLocalService.
			getAppBuilderAppVersionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _appBuilderAppVersionLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _appBuilderAppVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
			getLatestAppBuilderAppVersion(long appBuilderAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
			appBuilderAppId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _appBuilderAppVersionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppVersionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the app builder app version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersion the app builder app version
	 * @return the app builder app version that was updated
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppVersion
		updateAppBuilderAppVersion(
			com.liferay.app.builder.model.AppBuilderAppVersion
				appBuilderAppVersion) {

		return _appBuilderAppVersionLocalService.updateAppBuilderAppVersion(
			appBuilderAppVersion);
	}

	@Override
	public AppBuilderAppVersionLocalService getWrappedService() {
		return _appBuilderAppVersionLocalService;
	}

	@Override
	public void setWrappedService(
		AppBuilderAppVersionLocalService appBuilderAppVersionLocalService) {

		_appBuilderAppVersionLocalService = appBuilderAppVersionLocalService;
	}

	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

}