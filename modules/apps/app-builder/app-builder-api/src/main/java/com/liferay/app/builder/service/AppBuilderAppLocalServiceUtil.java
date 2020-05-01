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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AppBuilderApp. This utility wraps
 * <code>com.liferay.app.builder.service.impl.AppBuilderAppLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppLocalService
 * @generated
 */
public class AppBuilderAppLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the app builder app to the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was added
	 */
	public static com.liferay.app.builder.model.AppBuilderApp addAppBuilderApp(
		com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return getService().addAppBuilderApp(appBuilderApp);
	}

	public static com.liferay.app.builder.model.AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, long ddmStructureId,
			long ddmStructureLayoutId, long deDataListViewId,
			java.util.Map<java.util.Locale, String> nameMap, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAppBuilderApp(
			groupId, companyId, userId, ddmStructureId, ddmStructureLayoutId,
			deDataListViewId, nameMap, status);
	}

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
		createAppBuilderApp(long appBuilderAppId) {

		return getService().createAppBuilderApp(appBuilderAppId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the app builder app from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was removed
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
		deleteAppBuilderApp(
			com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return getService().deleteAppBuilderApp(appBuilderApp);
	}

	/**
	 * Deletes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
			deleteAppBuilderApp(long appBuilderAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAppBuilderApp(appBuilderAppId);
	}

	public static void deleteAppBuilderApps(long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAppBuilderApps(ddmStructureId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.app.builder.model.AppBuilderApp
		fetchAppBuilderApp(long appBuilderAppId) {

		return getService().fetchAppBuilderApp(appBuilderAppId);
	}

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
		fetchAppBuilderAppByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAppBuilderAppByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app with the primary key.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderApp getAppBuilderApp(
			long appBuilderAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAppBuilderApp(appBuilderAppId);
	}

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app
	 * @throws PortalException if a matching app builder app could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
			getAppBuilderAppByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAppBuilderAppByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<Long> getAppBuilderAppIds(
		int status, String type) {

		return getService().getAppBuilderAppIds(status, type);
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
	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(int start, int end) {

		return getService().getAppBuilderApps(start, end);
	}

	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(long ddmStructureId) {

		return getService().getAppBuilderApps(ddmStructureId);
	}

	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(long companyId, int status) {

		return getService().getAppBuilderApps(companyId, status);
	}

	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderApps(
			long groupId, long companyId, long ddmStructureId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns all the app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @return the matching app builder apps, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderAppsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getAppBuilderAppsByUuidAndCompanyId(
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
	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getAppBuilderAppsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return getService().getAppBuilderAppsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	public static int getAppBuilderAppsCount() {
		return getService().getAppBuilderAppsCount();
	}

	public static int getAppBuilderAppsCount(long groupId) {
		return getService().getAppBuilderAppsCount(groupId);
	}

	public static int getAppBuilderAppsCount(
		long groupId, long companyId, long ddmStructureId) {

		return getService().getAppBuilderAppsCount(
			groupId, companyId, ddmStructureId);
	}

	public static java.util.List<com.liferay.app.builder.model.AppBuilderApp>
		getCompanyAppBuilderApps(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.app.builder.model.AppBuilderApp>
					orderByComparator) {

		return getService().getCompanyAppBuilderApps(
			companyId, start, end, orderByComparator);
	}

	public static int getCompanyAppBuilderAppsCount(long companyId) {
		return getService().getCompanyAppBuilderAppsCount(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the app builder app in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was updated
	 */
	public static com.liferay.app.builder.model.AppBuilderApp
		updateAppBuilderApp(
			com.liferay.app.builder.model.AppBuilderApp appBuilderApp) {

		return getService().updateAppBuilderApp(appBuilderApp);
	}

	public static com.liferay.app.builder.model.AppBuilderApp
			updateAppBuilderApp(
				long userId, long appBuilderAppId, long ddmStructureId,
				long ddmStructureLayoutId, long deDataListViewId,
				java.util.Map<java.util.Locale, String> nameMap, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAppBuilderApp(
			userId, appBuilderAppId, ddmStructureId, ddmStructureLayoutId,
			deDataListViewId, nameMap, status);
	}

	public static AppBuilderAppLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppLocalService, AppBuilderAppLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderAppLocalService.class);

		ServiceTracker<AppBuilderAppLocalService, AppBuilderAppLocalService>
			serviceTracker =
				new ServiceTracker
					<AppBuilderAppLocalService, AppBuilderAppLocalService>(
						bundle.getBundleContext(),
						AppBuilderAppLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}