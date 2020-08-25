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
 * Provides the local service utility for ServiceComponent. This utility wraps
 * <code>com.liferay.portal.service.impl.ServiceComponentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentLocalService
 * @generated
 */
public class ServiceComponentLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ServiceComponentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the service component to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ServiceComponentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param serviceComponent the service component
	 * @return the service component that was added
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
		addServiceComponent(
			com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return getService().addServiceComponent(serviceComponent);
	}

	/**
	 * Creates a new service component with the primary key. Does not add the service component to the database.
	 *
	 * @param serviceComponentId the primary key for the new service component
	 * @return the new service component
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
		createServiceComponent(long serviceComponentId) {

		return getService().createServiceComponent(serviceComponentId);
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
	 * Deletes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ServiceComponentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component that was removed
	 * @throws PortalException if a service component with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
			deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteServiceComponent(serviceComponentId);
	}

	/**
	 * Deletes the service component from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ServiceComponentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param serviceComponent the service component
	 * @return the service component that was removed
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
		deleteServiceComponent(
			com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return getService().deleteServiceComponent(serviceComponent);
	}

	public static void destroyServiceComponent(
		com.liferay.portal.kernel.service.configuration.
			ServiceComponentConfiguration serviceComponentConfiguration,
		ClassLoader classLoader) {

		getService().destroyServiceComponent(
			serviceComponentConfiguration, classLoader);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ServiceComponentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ServiceComponentModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.ServiceComponent
		fetchServiceComponent(long serviceComponentId) {

		return getService().fetchServiceComponent(serviceComponentId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.ServiceComponent>
			getLatestServiceComponents() {

		return getService().getLatestServiceComponents();
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
	 * Returns the service component with the primary key.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component
	 * @throws PortalException if a service component with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
			getServiceComponent(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getServiceComponent(serviceComponentId);
	}

	/**
	 * Returns a range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @return the range of service components
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.ServiceComponent> getServiceComponents(
			int start, int end) {

		return getService().getServiceComponents(start, end);
	}

	/**
	 * Returns the number of service components.
	 *
	 * @return the number of service components
	 */
	public static int getServiceComponentsCount() {
		return getService().getServiceComponentsCount();
	}

	public static com.liferay.portal.kernel.model.ServiceComponent
			initServiceComponent(
				com.liferay.portal.kernel.service.configuration.
					ServiceComponentConfiguration serviceComponentConfiguration,
				ClassLoader classLoader, String buildNamespace,
				long buildNumber, long buildDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().initServiceComponent(
			serviceComponentConfiguration, classLoader, buildNamespace,
			buildNumber, buildDate);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #initServiceComponent(ServiceComponentConfiguration,
	 ClassLoader, String, long, long)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.ServiceComponent
			initServiceComponent(
				com.liferay.portal.kernel.service.configuration.
					ServiceComponentConfiguration serviceComponentConfiguration,
				ClassLoader classLoader, String buildNamespace,
				long buildNumber, long buildDate, boolean buildAutoUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().initServiceComponent(
			serviceComponentConfiguration, classLoader, buildNamespace,
			buildNumber, buildDate, buildAutoUpgrade);
	}

	/**
	 * Updates the service component in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ServiceComponentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param serviceComponent the service component
	 * @return the service component that was updated
	 */
	public static com.liferay.portal.kernel.model.ServiceComponent
		updateServiceComponent(
			com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return getService().updateServiceComponent(serviceComponent);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #upgradeDB(ClassLoader, String, long, ServiceComponent,
	 String, String, String)}
	 */
	@Deprecated
	public static void upgradeDB(
			ClassLoader classLoader, String buildNamespace, long buildNumber,
			boolean buildAutoUpgrade,
			com.liferay.portal.kernel.model.ServiceComponent
				previousServiceComponent,
			String tablesSQL, String sequencesSQL, String indexesSQL)
		throws Exception {

		getService().upgradeDB(
			classLoader, buildNamespace, buildNumber, buildAutoUpgrade,
			previousServiceComponent, tablesSQL, sequencesSQL, indexesSQL);
	}

	public static void upgradeDB(
			ClassLoader classLoader, String buildNamespace, long buildNumber,
			com.liferay.portal.kernel.model.ServiceComponent
				previousServiceComponent,
			String tablesSQL, String sequencesSQL, String indexesSQL)
		throws Exception {

		getService().upgradeDB(
			classLoader, buildNamespace, buildNumber, previousServiceComponent,
			tablesSQL, sequencesSQL, indexesSQL);
	}

	public static void verifyDB() {
		getService().verifyDB();
	}

	public static ServiceComponentLocalService getService() {
		if (_service == null) {
			_service =
				(ServiceComponentLocalService)PortalBeanLocatorUtil.locate(
					ServiceComponentLocalService.class.getName());
		}

		return _service;
	}

	private static ServiceComponentLocalService _service;

}