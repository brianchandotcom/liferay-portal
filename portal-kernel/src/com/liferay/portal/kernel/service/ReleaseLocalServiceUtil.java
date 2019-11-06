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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Release. This utility wraps
 * <code>com.liferay.portal.service.impl.ReleaseLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ReleaseLocalService
 * @generated
 */
public class ReleaseLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ReleaseLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the release to the database. Also notifies the appropriate model listeners.
	 *
	 * @param release the release
	 * @return the release that was added
	 */
	public static com.liferay.portal.kernel.model.Release addRelease(
		com.liferay.portal.kernel.model.Release release) {

		return getService().addRelease(release);
	}

	public static com.liferay.portal.kernel.model.Release addRelease(
		String servletContextName, int buildNumber) {

		return getService().addRelease(servletContextName, buildNumber);
	}

	public static com.liferay.portal.kernel.model.Release addRelease(
		String servletContextName, String schemaVersion) {

		return getService().addRelease(servletContextName, schemaVersion);
	}

	/**
	 * Creates a new release with the primary key. Does not add the release to the database.
	 *
	 * @param releaseId the primary key for the new release
	 * @return the new release
	 */
	public static com.liferay.portal.kernel.model.Release createRelease(
		long releaseId) {

		return getService().createRelease(releaseId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void createTablesAndPopulate() {
		getService().createTablesAndPopulate();
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

	/**
	 * Deletes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release that was removed
	 * @throws PortalException if a release with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Release deleteRelease(
			long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRelease(releaseId);
	}

	/**
	 * Deletes the release from the database. Also notifies the appropriate model listeners.
	 *
	 * @param release the release
	 * @return the release that was removed
	 */
	public static com.liferay.portal.kernel.model.Release deleteRelease(
		com.liferay.portal.kernel.model.Release release) {

		return getService().deleteRelease(release);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ReleaseModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ReleaseModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.Release fetchRelease(
		long releaseId) {

		return getService().fetchRelease(releaseId);
	}

	public static com.liferay.portal.kernel.model.Release fetchRelease(
		String servletContextName) {

		return getService().fetchRelease(servletContextName);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBuildNumberOrCreate();
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the release with the primary key.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release
	 * @throws PortalException if a release with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Release getRelease(
			long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRelease(releaseId);
	}

	/**
	 * Returns a range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @return the range of releases
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Release>
		getReleases(int start, int end) {

		return getService().getReleases(start, end);
	}

	/**
	 * Returns the number of releases.
	 *
	 * @return the number of releases
	 */
	public static int getReleasesCount() {
		return getService().getReleasesCount();
	}

	public static com.liferay.portal.kernel.model.Release updateRelease(
			long releaseId, String schemaVersion, int buildNumber,
			java.util.Date buildDate, boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRelease(
			releaseId, schemaVersion, buildNumber, buildDate, verified);
	}

	/**
	 * Updates the release in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param release the release
	 * @return the release that was updated
	 */
	public static com.liferay.portal.kernel.model.Release updateRelease(
		com.liferay.portal.kernel.model.Release release) {

		return getService().updateRelease(release);
	}

	public static void updateRelease(
			String servletContextName,
			java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess>
				upgradeProcesses,
			int buildNumber, int previousBuildNumber, boolean indexOnUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateRelease(
			servletContextName, upgradeProcesses, buildNumber,
			previousBuildNumber, indexOnUpgrade);
	}

	public static void updateRelease(
			String servletContextName,
			java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess>
				upgradeProcesses,
			java.util.Properties unfilteredPortalProperties)
		throws Exception {

		getService().updateRelease(
			servletContextName, upgradeProcesses, unfilteredPortalProperties);
	}

	public static void updateRelease(
		String servletContextName, String schemaVersion,
		String previousSchemaVersion) {

		getService().updateRelease(
			servletContextName, schemaVersion, previousSchemaVersion);
	}

	public static ReleaseLocalService getService() {
		if (_service == null) {
			_service = (ReleaseLocalService)PortalBeanLocatorUtil.locate(
				ReleaseLocalService.class.getName());
		}

		return _service;
	}

	private static ReleaseLocalService _service;

}