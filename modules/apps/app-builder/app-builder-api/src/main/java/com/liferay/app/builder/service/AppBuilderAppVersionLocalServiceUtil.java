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

import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AppBuilderAppVersion. This utility wraps
 * <code>com.liferay.app.builder.service.impl.AppBuilderAppVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersionLocalService
 * @generated
 */
public class AppBuilderAppVersionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static AppBuilderAppVersion addAppBuilderAppVersion(
		AppBuilderAppVersion appBuilderAppVersion) {

		return getService().addAppBuilderAppVersion(appBuilderAppVersion);
	}

	public static AppBuilderAppVersion addAppBuilderAppVersion(
			long groupId, long companyId, long userId, long appBuilderAppId,
			long ddlRecordSetId, long ddmStructureId, long ddmStructureLayoutId)
		throws PortalException {

		return getService().addAppBuilderAppVersion(
			groupId, companyId, userId, appBuilderAppId, ddlRecordSetId,
			ddmStructureId, ddmStructureLayoutId);
	}

	/**
	 * Creates a new app builder app version with the primary key. Does not add the app builder app version to the database.
	 *
	 * @param appBuilderAppVersionId the primary key for the new app builder app version
	 * @return the new app builder app version
	 */
	public static AppBuilderAppVersion createAppBuilderAppVersion(
		long appBuilderAppVersionId) {

		return getService().createAppBuilderAppVersion(appBuilderAppVersionId);
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
	 * Deletes the app builder app version from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersion the app builder app version
	 * @return the app builder app version that was removed
	 */
	public static AppBuilderAppVersion deleteAppBuilderAppVersion(
		AppBuilderAppVersion appBuilderAppVersion) {

		return getService().deleteAppBuilderAppVersion(appBuilderAppVersion);
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
	public static AppBuilderAppVersion deleteAppBuilderAppVersion(
			long appBuilderAppVersionId)
		throws PortalException {

		return getService().deleteAppBuilderAppVersion(appBuilderAppVersionId);
	}

	public static void deleteAppBuilderAppVersions(long appBuilderAppId) {
		getService().deleteAppBuilderAppVersions(appBuilderAppId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppVersionModelImpl</code>.
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

	public static AppBuilderAppVersion fetchAppBuilderAppVersion(
		long appBuilderAppVersionId) {

		return getService().fetchAppBuilderAppVersion(appBuilderAppVersionId);
	}

	/**
	 * Returns the app builder app version matching the UUID and group.
	 *
	 * @param uuid the app builder app version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion
		fetchAppBuilderAppVersionByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAppBuilderAppVersionByUuidAndGroupId(
			uuid, groupId);
	}

	public static AppBuilderAppVersion fetchLatestAppBuilderAppVersion(
		long appBuilderAppId) {

		return getService().fetchLatestAppBuilderAppVersion(appBuilderAppId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app version with the primary key.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws PortalException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion getAppBuilderAppVersion(
			long appBuilderAppVersionId)
		throws PortalException {

		return getService().getAppBuilderAppVersion(appBuilderAppVersionId);
	}

	public static AppBuilderAppVersion getAppBuilderAppVersion(
			long appBuilderAppId, String version)
		throws PortalException {

		return getService().getAppBuilderAppVersion(appBuilderAppId, version);
	}

	/**
	 * Returns the app builder app version matching the UUID and group.
	 *
	 * @param uuid the app builder app version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching app builder app version
	 * @throws PortalException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion getAppBuilderAppVersionByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getAppBuilderAppVersionByUuidAndGroupId(
			uuid, groupId);
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
	public static List<AppBuilderAppVersion> getAppBuilderAppVersions(
		int start, int end) {

		return getService().getAppBuilderAppVersions(start, end);
	}

	/**
	 * Returns all the app builder app versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the app builder app versions
	 * @param companyId the primary key of the company
	 * @return the matching app builder app versions, or an empty list if no matches were found
	 */
	public static List<AppBuilderAppVersion>
		getAppBuilderAppVersionsByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getAppBuilderAppVersionsByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<AppBuilderAppVersion>
		getAppBuilderAppVersionsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getService().getAppBuilderAppVersionsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of app builder app versions.
	 *
	 * @return the number of app builder app versions
	 */
	public static int getAppBuilderAppVersionsCount() {
		return getService().getAppBuilderAppVersionsCount();
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

	public static AppBuilderAppVersion getLatestAppBuilderAppVersion(
			long appBuilderAppId)
		throws PortalException {

		return getService().getLatestAppBuilderAppVersion(appBuilderAppId);
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
	 * Updates the app builder app version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppVersion the app builder app version
	 * @return the app builder app version that was updated
	 */
	public static AppBuilderAppVersion updateAppBuilderAppVersion(
		AppBuilderAppVersion appBuilderAppVersion) {

		return getService().updateAppBuilderAppVersion(appBuilderAppVersion);
	}

	public static AppBuilderAppVersionLocalService getService() {
		return _service;
	}

	private static volatile AppBuilderAppVersionLocalService _service;

}