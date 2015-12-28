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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Release. This utility wraps
 * {@link com.liferay.portal.service.impl.ReleaseLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ReleaseLocalService
 * @see com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ReleaseLocalServiceImpl
 * @generated
 */
@ProviderType
public class ReleaseLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ReleaseLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the release to the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was added
	*/
	public static com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release) {
		return getService().addRelease(release);
	}

	public static com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber) {
		return getService().addRelease(servletContextName, buildNumber);
	}

	public static com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, java.lang.String schemaVersion) {
		return getService().addRelease(servletContextName, schemaVersion);
	}

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	public static com.liferay.portal.model.Release createRelease(long releaseId) {
		return getService().createRelease(releaseId);
	}

	public static void createTablesAndPopulate() {
		getService().createTablesAndPopulate();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the release from the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was removed
	*/
	public static com.liferay.portal.model.Release deleteRelease(
		com.liferay.portal.model.Release release) {
		return getService().deleteRelease(release);
	}

	/**
	* Deletes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release
	* @return the release that was removed
	* @throws PortalException if a release with the primary key could not be found
	*/
	public static com.liferay.portal.model.Release deleteRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRelease(releaseId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ReleaseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ReleaseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portal.model.Release fetchRelease(long releaseId) {
		return getService().fetchRelease(releaseId);
	}

	public static com.liferay.portal.model.Release fetchRelease(
		java.lang.String servletContextName) {
		return getService().fetchRelease(servletContextName);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBuildNumberOrCreate();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
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
	public static com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRelease(releaseId);
	}

	/**
	* Returns a range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ReleaseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @return the range of releases
	*/
	public static java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end) {
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

	/**
	* Updates the release in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was updated
	*/
	public static com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release) {
		return getService().updateRelease(release);
	}

	public static com.liferay.portal.model.Release updateRelease(
		long releaseId, int buildNumber, java.util.Date buildDate,
		boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRelease(releaseId, buildNumber, buildDate, verified);
	}

	public static void updateRelease(java.lang.String servletContextName,
		java.lang.String schemaVersion, java.lang.String previousSchemaVersion) {
		getService()
			.updateRelease(servletContextName, schemaVersion,
			previousSchemaVersion);
	}

	public static void updateRelease(java.lang.String servletContextName,
		java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess> upgradeProcesses,
		int buildNumber, int previousBuildNumber, boolean indexOnUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateRelease(servletContextName, upgradeProcesses, buildNumber,
			previousBuildNumber, indexOnUpgrade);
	}

	public static void updateRelease(java.lang.String servletContextName,
		java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess> upgradeProcesses,
		java.util.Properties unfilteredPortalProperties)
		throws java.lang.Exception {
		getService()
			.updateRelease(servletContextName, upgradeProcesses,
			unfilteredPortalProperties);
	}

	public static ReleaseLocalService getService() {
		if (_service == null) {
			_service = (ReleaseLocalService)PortalBeanLocatorUtil.locate(ReleaseLocalService.class.getName());

			ReferenceRegistry.registerReference(ReleaseLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ReleaseLocalService _service;
}