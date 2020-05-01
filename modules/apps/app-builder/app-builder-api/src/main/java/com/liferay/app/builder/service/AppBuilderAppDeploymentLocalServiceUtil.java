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
 * Provides the local service utility for AppBuilderAppDeployment. This utility wraps
 * <code>com.liferay.app.builder.service.impl.AppBuilderAppDeploymentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentLocalService
 * @generated
 */
public class AppBuilderAppDeploymentLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppDeploymentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the app builder app deployment to the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was added
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		addAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return getService().addAppBuilderAppDeployment(appBuilderAppDeployment);
	}

	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		addAppBuilderAppDeployment(
			long appBuilderAppId, String settings, String type) {

		return getService().addAppBuilderAppDeployment(
			appBuilderAppId, settings, type);
	}

	/**
	 * Creates a new app builder app deployment with the primary key. Does not add the app builder app deployment to the database.
	 *
	 * @param appBuilderAppDeploymentId the primary key for the new app builder app deployment
	 * @return the new app builder app deployment
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		createAppBuilderAppDeployment(long appBuilderAppDeploymentId) {

		return getService().createAppBuilderAppDeployment(
			appBuilderAppDeploymentId);
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
	 * Deletes the app builder app deployment from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was removed
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		deleteAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return getService().deleteAppBuilderAppDeployment(
			appBuilderAppDeployment);
	}

	/**
	 * Deletes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws PortalException if a app builder app deployment with the primary key could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
			deleteAppBuilderAppDeployment(long appBuilderAppDeploymentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAppBuilderAppDeployment(
			appBuilderAppDeploymentId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl</code>.
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

	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		fetchAppBuilderAppDeployment(long appBuilderAppDeploymentId) {

		return getService().fetchAppBuilderAppDeployment(
			appBuilderAppDeploymentId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app deployment with the primary key.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws PortalException if a app builder app deployment with the primary key could not be found
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
			getAppBuilderAppDeployment(long appBuilderAppDeploymentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAppBuilderAppDeployment(
			appBuilderAppDeploymentId);
	}

	public static com.liferay.app.builder.model.AppBuilderAppDeployment
			getAppBuilderAppDeployment(long appBuilderAppId, String type)
		throws Exception {

		return getService().getAppBuilderAppDeployment(appBuilderAppId, type);
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
	public static java.util.List
		<com.liferay.app.builder.model.AppBuilderAppDeployment>
			getAppBuilderAppDeployments(int start, int end) {

		return getService().getAppBuilderAppDeployments(start, end);
	}

	public static java.util.List
		<com.liferay.app.builder.model.AppBuilderAppDeployment>
			getAppBuilderAppDeployments(long appBuilderAppId) {

		return getService().getAppBuilderAppDeployments(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app deployments.
	 *
	 * @return the number of app builder app deployments
	 */
	public static int getAppBuilderAppDeploymentsCount() {
		return getService().getAppBuilderAppDeploymentsCount();
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
	 * Updates the app builder app deployment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 * @return the app builder app deployment that was updated
	 */
	public static com.liferay.app.builder.model.AppBuilderAppDeployment
		updateAppBuilderAppDeployment(
			com.liferay.app.builder.model.AppBuilderAppDeployment
				appBuilderAppDeployment) {

		return getService().updateAppBuilderAppDeployment(
			appBuilderAppDeployment);
	}

	public static AppBuilderAppDeploymentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppDeploymentLocalService,
		 AppBuilderAppDeploymentLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderAppDeploymentLocalService.class);

		ServiceTracker
			<AppBuilderAppDeploymentLocalService,
			 AppBuilderAppDeploymentLocalService> serviceTracker =
				new ServiceTracker
					<AppBuilderAppDeploymentLocalService,
					 AppBuilderAppDeploymentLocalService>(
						 bundle.getBundleContext(),
						 AppBuilderAppDeploymentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}