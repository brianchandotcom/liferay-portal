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
 * Provides a wrapper for {@link AppBuilderAppLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppLocalService
 * @generated
 */
public class AppBuilderAppLocalServiceWrapper
	implements AppBuilderAppLocalService,
			   ServiceWrapper<AppBuilderAppLocalService> {

	public AppBuilderAppLocalServiceWrapper(
		AppBuilderAppLocalService appBuilderAppLocalService) {

		_appBuilderAppLocalService = appBuilderAppLocalService;
	}

	/**
	 * Adds the app builder app to the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was added
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp addAppBuilderApp(
		com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return _appBuilderAppLocalService.addAppBuilderApp(appBuilderApp);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			java.util.Map<java.util.Locale, String> nameMap, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.addAppBuilderApp(
			groupId, companyId, userId, ddmStructureId, ddmStructureLayoutId,
			deDataListViewId, nameMap, status);
	}

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp createAppBuilderApp(
		long appBuilderAppId) {

		return _appBuilderAppLocalService.createAppBuilderApp(appBuilderAppId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the app builder app from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was removed
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp deleteAppBuilderApp(
		com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return _appBuilderAppLocalService.deleteAppBuilderApp(appBuilderApp);
	}

	/**
	 * Deletes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp deleteAppBuilderApp(
			long appBuilderAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.deleteAppBuilderApp(appBuilderAppId);
	}

	@Override
	public void deleteAppBuilderApps(long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_appBuilderAppLocalService.deleteAppBuilderApps(ddmStructureId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _appBuilderAppLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _appBuilderAppLocalService.dynamicQuery();
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

		return _appBuilderAppLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
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

		return _appBuilderAppLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
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

		return _appBuilderAppLocalService.dynamicQuery(
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

		return _appBuilderAppLocalService.dynamicQueryCount(dynamicQuery);
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

		return _appBuilderAppLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderApp fetchAppBuilderApp(
		long appBuilderAppId) {

		return _appBuilderAppLocalService.fetchAppBuilderApp(appBuilderAppId);
	}

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp
		fetchAppBuilderAppByUuidAndGroupId(String uuid, long groupId) {

		return _appBuilderAppLocalService.fetchAppBuilderAppByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _appBuilderAppLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app with the primary key.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp getAppBuilderApp(
			long appBuilderAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.getAppBuilderApp(appBuilderAppId);
	}

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app
	 * @throws PortalException if a matching app builder app could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp
			getAppBuilderAppByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.getAppBuilderAppByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public java.util.List<Long> getAppBuilderAppIds(int status, String type) {
		return _appBuilderAppLocalService.getAppBuilderAppIds(status, type);
	}

	/**
	 * Returns a range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of app builder apps
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(int start, int end) {

		return _appBuilderAppLocalService.getAppBuilderApps(start, end);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(long ddmStructureId) {

		return _appBuilderAppLocalService.getAppBuilderApps(ddmStructureId);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(long companyId, int status) {

		return _appBuilderAppLocalService.getAppBuilderApps(companyId, status);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return _appBuilderAppLocalService.getAppBuilderApps(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(
			long groupId, long companyId, long ddmStructureId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return _appBuilderAppLocalService.getAppBuilderApps(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns all the app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @return the matching app builder apps, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderAppsByUuidAndCompanyId(String uuid, long companyId) {

		return _appBuilderAppLocalService.getAppBuilderAppsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching app builder apps, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderAppsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return _appBuilderAppLocalService.getAppBuilderAppsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	@Override
	public int getAppBuilderAppsCount() {
		return _appBuilderAppLocalService.getAppBuilderAppsCount();
	}

	@Override
	public int getAppBuilderAppsCount(long groupId) {
		return _appBuilderAppLocalService.getAppBuilderAppsCount(groupId);
	}

	@Override
	public int getAppBuilderAppsCount(
		long groupId, long companyId, long ddmStructureId) {

		return _appBuilderAppLocalService.getAppBuilderAppsCount(
			groupId, companyId, ddmStructureId);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getCompanyAppBuilderApps(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return _appBuilderAppLocalService.getCompanyAppBuilderApps(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyAppBuilderAppsCount(long companyId) {
		return _appBuilderAppLocalService.getCompanyAppBuilderAppsCount(
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _appBuilderAppLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _appBuilderAppLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _appBuilderAppLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the app builder app in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was updated
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderApp updateAppBuilderApp(
		com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return _appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderApp updateAppBuilderApp(
			long userId, long appBuilderAppId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			java.util.Map<java.util.Locale, String> nameMap, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppLocalService.updateAppBuilderApp(
			userId, appBuilderAppId, ddmStructureId, ddmStructureLayoutId,
			deDataListViewId, nameMap, status);
	}

	@Override
	public AppBuilderAppLocalService getWrappedService() {
		return _appBuilderAppLocalService;
	}

	@Override
	public void setWrappedService(
		AppBuilderAppLocalService appBuilderAppLocalService) {

		_appBuilderAppLocalService = appBuilderAppLocalService;
	}

	private AppBuilderAppLocalService _appBuilderAppLocalService;

}