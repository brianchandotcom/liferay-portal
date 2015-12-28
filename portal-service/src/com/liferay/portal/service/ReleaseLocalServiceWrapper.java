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

/**
 * Provides a wrapper for {@link ReleaseLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ReleaseLocalService
 * @generated
 */
@ProviderType
public class ReleaseLocalServiceWrapper implements ReleaseLocalService,
	ServiceWrapper<ReleaseLocalService> {
	public ReleaseLocalServiceWrapper(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	/**
	* Adds the release to the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was added
	*/
	@Override
	public com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release) {
		return _releaseLocalService.addRelease(release);
	}

	@Override
	public com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber) {
		return _releaseLocalService.addRelease(servletContextName, buildNumber);
	}

	@Override
	public com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, java.lang.String schemaVersion) {
		return _releaseLocalService.addRelease(servletContextName, schemaVersion);
	}

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	@Override
	public com.liferay.portal.model.Release createRelease(long releaseId) {
		return _releaseLocalService.createRelease(releaseId);
	}

	@Override
	public void createTablesAndPopulate() {
		_releaseLocalService.createTablesAndPopulate();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the release from the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was removed
	*/
	@Override
	public com.liferay.portal.model.Release deleteRelease(
		com.liferay.portal.model.Release release) {
		return _releaseLocalService.deleteRelease(release);
	}

	/**
	* Deletes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release
	* @return the release that was removed
	* @throws PortalException if a release with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Release deleteRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.deleteRelease(releaseId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _releaseLocalService.dynamicQuery();
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
		return _releaseLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _releaseLocalService.dynamicQueryCount(dynamicQuery);
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
		return _releaseLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Release fetchRelease(long releaseId) {
		return _releaseLocalService.fetchRelease(releaseId);
	}

	@Override
	public com.liferay.portal.model.Release fetchRelease(
		java.lang.String servletContextName) {
		return _releaseLocalService.fetchRelease(servletContextName);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _releaseLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.getBuildNumberOrCreate();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _releaseLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _releaseLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the release with the primary key.
	*
	* @param releaseId the primary key of the release
	* @return the release
	* @throws PortalException if a release with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.getRelease(releaseId);
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
	@Override
	public java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end) {
		return _releaseLocalService.getReleases(start, end);
	}

	/**
	* Returns the number of releases.
	*
	* @return the number of releases
	*/
	@Override
	public int getReleasesCount() {
		return _releaseLocalService.getReleasesCount();
	}

	/**
	* Updates the release in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was updated
	*/
	@Override
	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release) {
		return _releaseLocalService.updateRelease(release);
	}

	@Override
	public com.liferay.portal.model.Release updateRelease(long releaseId,
		int buildNumber, java.util.Date buildDate, boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _releaseLocalService.updateRelease(releaseId, buildNumber,
			buildDate, verified);
	}

	@Override
	public void updateRelease(java.lang.String servletContextName,
		java.lang.String schemaVersion, java.lang.String previousSchemaVersion) {
		_releaseLocalService.updateRelease(servletContextName, schemaVersion,
			previousSchemaVersion);
	}

	@Override
	public void updateRelease(java.lang.String servletContextName,
		java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess> upgradeProcesses,
		int buildNumber, int previousBuildNumber, boolean indexOnUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException {
		_releaseLocalService.updateRelease(servletContextName,
			upgradeProcesses, buildNumber, previousBuildNumber, indexOnUpgrade);
	}

	@Override
	public void updateRelease(java.lang.String servletContextName,
		java.util.List<com.liferay.portal.kernel.upgrade.UpgradeProcess> upgradeProcesses,
		java.util.Properties unfilteredPortalProperties)
		throws java.lang.Exception {
		_releaseLocalService.updateRelease(servletContextName,
			upgradeProcesses, unfilteredPortalProperties);
	}

	@Override
	public ReleaseLocalService getWrappedService() {
		return _releaseLocalService;
	}

	@Override
	public void setWrappedService(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	private ReleaseLocalService _releaseLocalService;
}