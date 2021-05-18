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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was added
	 */
	public static AppBuilderApp addAppBuilderApp(AppBuilderApp appBuilderApp) {
		return getService().addAppBuilderApp(appBuilderApp);
	}

	public static AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, boolean active,
			long ddlRecordSetId, long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<java.util.Locale, String> nameMap,
			String scope)
		throws PortalException {

		return getService().addAppBuilderApp(
			groupId, companyId, userId, active, ddlRecordSetId, ddmStructureId,
			ddmStructureLayoutId, deDataListViewId, nameMap, scope);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addAppBuilderApp(long, long, long, boolean, long, long,
	 long, Map, String)}
	 */
	@Deprecated
	public static AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<java.util.Locale, String> nameMap)
		throws PortalException {

		return getService().addAppBuilderApp(
			groupId, companyId, userId, active, ddmStructureId,
			ddmStructureLayoutId, deDataListViewId, nameMap);
	}

	public static AppBuilderApp addAppBuilderApp(
			long groupId, long companyId, long userId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<java.util.Locale, String> nameMap,
			String scope)
		throws PortalException {

		return getService().addAppBuilderApp(
			groupId, companyId, userId, active, ddmStructureId,
			ddmStructureLayoutId, deDataListViewId, nameMap, scope);
	}

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	public static AppBuilderApp createAppBuilderApp(long appBuilderAppId) {
		return getService().createAppBuilderApp(appBuilderAppId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the app builder app from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was removed
	 */
	public static AppBuilderApp deleteAppBuilderApp(
		AppBuilderApp appBuilderApp) {

		return getService().deleteAppBuilderApp(appBuilderApp);
	}

	/**
	 * Deletes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws PortalException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp deleteAppBuilderApp(long appBuilderAppId)
		throws PortalException {

		return getService().deleteAppBuilderApp(appBuilderAppId);
	}

	public static void deleteAppBuilderApps(long ddmStructureId)
		throws PortalException {

		getService().deleteAppBuilderApps(ddmStructureId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static AppBuilderApp fetchAppBuilderApp(long appBuilderAppId) {
		return getService().fetchAppBuilderApp(appBuilderAppId);
	}

	/**
	 * Returns the app builder app matching the UUID and group.
	 *
	 * @param uuid the app builder app's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchAppBuilderAppByUuidAndGroupId(
		String uuid, long groupId) {

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
	public static AppBuilderApp getAppBuilderApp(long appBuilderAppId)
		throws PortalException {

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
	public static AppBuilderApp getAppBuilderAppByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getAppBuilderAppByUuidAndGroupId(uuid, groupId);
	}

	public static List<Long> getAppBuilderAppIds(boolean active, String type) {
		return getService().getAppBuilderAppIds(active, type);
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
	public static List<AppBuilderApp> getAppBuilderApps(int start, int end) {
		return getService().getAppBuilderApps(start, end);
	}

	public static List<AppBuilderApp> getAppBuilderApps(long ddmStructureId) {
		return getService().getAppBuilderApps(ddmStructureId);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long companyId, boolean active) {

		return getService().getAppBuilderApps(companyId, active);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long companyId, boolean active, String scope) {

		return getService().getAppBuilderApps(companyId, active, scope);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, start, end, orderByComparator);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long groupId, long companyId, long ddmStructureId, String scope,
		int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, companyId, ddmStructureId, scope, start, end,
			orderByComparator);
	}

	public static List<AppBuilderApp> getAppBuilderApps(
		long groupId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getAppBuilderApps(
			groupId, scope, start, end, orderByComparator);
	}

	/**
	 * Returns all the app builder apps matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder apps
	 * @param companyId the primary key of the company
	 * @return the matching app builder apps, or an empty list if no matches were found
	 */
	public static List<AppBuilderApp> getAppBuilderAppsByUuidAndCompanyId(
		String uuid, long companyId) {

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
	public static List<AppBuilderApp> getAppBuilderAppsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

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

	public static int getAppBuilderAppsCount(
		long groupId, long companyId, long ddmStructureId, String scope) {

		return getService().getAppBuilderAppsCount(
			groupId, companyId, ddmStructureId, scope);
	}

	public static int getAppBuilderAppsCount(long groupId, String scope) {
		return getService().getAppBuilderAppsCount(groupId, scope);
	}

	public static List<AppBuilderApp> getCompanyAppBuilderApps(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getCompanyAppBuilderApps(
			companyId, start, end, orderByComparator);
	}

	public static List<AppBuilderApp> getCompanyAppBuilderApps(
		long companyId, String scope, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getService().getCompanyAppBuilderApps(
			companyId, scope, start, end, orderByComparator);
	}

	public static int getCompanyAppBuilderAppsCount(long companyId) {
		return getService().getCompanyAppBuilderAppsCount(companyId);
	}

	public static int getCompanyAppBuilderAppsCount(
		long companyId, String scope) {

		return getService().getCompanyAppBuilderAppsCount(companyId, scope);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the app builder app in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderApp the app builder app
	 * @return the app builder app that was updated
	 */
	public static AppBuilderApp updateAppBuilderApp(
		AppBuilderApp appBuilderApp) {

		return getService().updateAppBuilderApp(appBuilderApp);
	}

	public static AppBuilderApp updateAppBuilderApp(
			long userId, long appBuilderAppId, boolean active,
			long ddmStructureId, long ddmStructureLayoutId,
			long deDataListViewId, Map<java.util.Locale, String> nameMap)
		throws PortalException {

		return getService().updateAppBuilderApp(
			userId, appBuilderAppId, active, ddmStructureId,
			ddmStructureLayoutId, deDataListViewId, nameMap);
	}

	public static AppBuilderAppLocalService getService() {
		return _service;
	}

	private static volatile AppBuilderAppLocalService _service;

}