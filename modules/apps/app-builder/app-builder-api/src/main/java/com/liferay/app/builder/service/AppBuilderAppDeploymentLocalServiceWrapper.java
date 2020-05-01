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
 * Provides a wrapper for {@link AppBuilderAppDeploymentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentLocalService
 * @generated
 */
public class AppBuilderAppDeploymentLocalServiceWrapper
	implements AppBuilderAppDeploymentLocalService,
			   ServiceWrapper<AppBuilderAppDeploymentLocalService> {

	public AppBuilderAppDeploymentLocalServiceWrapper(
		AppBuilderAppDeploymentLocalService
			appBuilderAppDeploymentLocalService) {

		_appBuilderAppDeploymentLocalService =
			appBuilderAppDeploymentLocalService;
	}

	/**
	 * Adds the app builder app deployment to the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was added
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		addAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return _appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderAppDeployment);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		addAppBuilderAppDeployment(
			long appBuilderAppId, String settings, String type) {

		return _appBuilderAppDeploymentLocalService.addAppBuilderAppDeployment(
			appBuilderAppId, settings, type);
	}

	/**
	 * Creates a new app builder app deployment with the primary key. Does not add the app builder app deployment to the database.
	 *
	 * @param appBuilderAppDeploymentId the primary key for the new app builder app deployment
	 * @return the new app builder app deployment
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		createAppBuilderAppDeployment(long appBuilderAppDeploymentId) {

		return _appBuilderAppDeploymentLocalService.
			createAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDeploymentLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the app builder app deployment from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was removed
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		deleteAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return _appBuilderAppDeploymentLocalService.
			deleteAppBuilderAppDeployment(appBuilderAppDeployment);
	}

	/**
	 * Deletes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws PortalException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
			deleteAppBuilderAppDeployment(long appBuilderAppDeploymentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDeploymentLocalService.
			deleteAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDeploymentLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _appBuilderAppDeploymentLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _appBuilderAppDeploymentLocalService.dynamicQuery();
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

		return _appBuilderAppDeploymentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl</code>.
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

		return _appBuilderAppDeploymentLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl</code>.
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

		return _appBuilderAppDeploymentLocalService.dynamicQuery(
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

		return _appBuilderAppDeploymentLocalService.dynamicQueryCount(
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

		return _appBuilderAppDeploymentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		fetchAppBuilderAppDeployment(long appBuilderAppDeploymentId) {

		return _appBuilderAppDeploymentLocalService.
			fetchAppBuilderAppDeployment(appBuilderAppDeploymentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _appBuilderAppDeploymentLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app deployment with the primary key.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws PortalException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
			getAppBuilderAppDeployment(long appBuilderAppDeploymentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDeploymentLocalService.getAppBuilderAppDeployment(
			appBuilderAppDeploymentId);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
			getAppBuilderAppDeployment(long appBuilderAppId, String type)
		throws Exception {

		return _appBuilderAppDeploymentLocalService.getAppBuilderAppDeployment(
			appBuilderAppId, type);
	}

	/**
	 * Returns a range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of app builder app deployments
	 */
	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderAppDeployment>
		getAppBuilderAppDeployments(int start, int end) {

		return _appBuilderAppDeploymentLocalService.getAppBuilderAppDeployments(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.app.builder.model.AppBuilderAppDeployment>
		getAppBuilderAppDeployments(long appBuilderAppId) {

		return _appBuilderAppDeploymentLocalService.getAppBuilderAppDeployments(
			appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app deployments.
	 *
	 * @return the number of app builder app deployments
	 */
	@Override
	public int getAppBuilderAppDeploymentsCount() {
		return _appBuilderAppDeploymentLocalService.
			getAppBuilderAppDeploymentsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _appBuilderAppDeploymentLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _appBuilderAppDeploymentLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDeploymentLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the app builder app deployment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was updated
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDeployment
		updateAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return _appBuilderAppDeploymentLocalService.
			updateAppBuilderAppDeployment(appBuilderAppDeployment);
	}

	@Override
	public AppBuilderAppDeploymentLocalService getWrappedService() {
		return _appBuilderAppDeploymentLocalService;
	}

	@Override
	public void setWrappedService(
		AppBuilderAppDeploymentLocalService
			appBuilderAppDeploymentLocalService) {

		_appBuilderAppDeploymentLocalService =
			appBuilderAppDeploymentLocalService;
	}

	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

}