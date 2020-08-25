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

/**
 * Provides a wrapper for {@link ServiceComponentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentLocalService
 * @generated
 */
public class ServiceComponentLocalServiceWrapper
	implements ServiceComponentLocalService,
			   ServiceWrapper<ServiceComponentLocalService> {

	public ServiceComponentLocalServiceWrapper(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

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
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent addServiceComponent(
		com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return _serviceComponentLocalService.addServiceComponent(
			serviceComponent);
	}

	/**
	 * Creates a new service component with the primary key. Does not add the service component to the database.
	 *
	 * @param serviceComponentId the primary key for the new service component
	 * @return the new service component
	 */
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
		createServiceComponent(long serviceComponentId) {

		return _serviceComponentLocalService.createServiceComponent(
			serviceComponentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.deletePersistedModel(
			persistedModel);
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
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
			deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.deleteServiceComponent(
			serviceComponentId);
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
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
		deleteServiceComponent(
			com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return _serviceComponentLocalService.deleteServiceComponent(
			serviceComponent);
	}

	@Override
	public void destroyServiceComponent(
		com.liferay.portal.kernel.service.configuration.
			ServiceComponentConfiguration serviceComponentConfiguration,
		java.lang.ClassLoader classLoader) {

		_serviceComponentLocalService.destroyServiceComponent(
			serviceComponentConfiguration, classLoader);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _serviceComponentLocalService.dynamicQuery();
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

		return _serviceComponentLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _serviceComponentLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _serviceComponentLocalService.dynamicQuery(
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

		return _serviceComponentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _serviceComponentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
		fetchServiceComponent(long serviceComponentId) {

		return _serviceComponentLocalService.fetchServiceComponent(
			serviceComponentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _serviceComponentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _serviceComponentLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.ServiceComponent>
		getLatestServiceComponents() {

		return _serviceComponentLocalService.getLatestServiceComponents();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _serviceComponentLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the service component with the primary key.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component
	 * @throws PortalException if a service component with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent getServiceComponent(
			long serviceComponentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.getServiceComponent(
			serviceComponentId);
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
	@Override
	public java.util.List<com.liferay.portal.kernel.model.ServiceComponent>
		getServiceComponents(int start, int end) {

		return _serviceComponentLocalService.getServiceComponents(start, end);
	}

	/**
	 * Returns the number of service components.
	 *
	 * @return the number of service components
	 */
	@Override
	public int getServiceComponentsCount() {
		return _serviceComponentLocalService.getServiceComponentsCount();
	}

	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
			initServiceComponent(
				com.liferay.portal.kernel.service.configuration.
					ServiceComponentConfiguration serviceComponentConfiguration,
				java.lang.ClassLoader classLoader,
				java.lang.String buildNamespace, long buildNumber,
				long buildDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.initServiceComponent(
			serviceComponentConfiguration, classLoader, buildNamespace,
			buildNumber, buildDate);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #initServiceComponent(ServiceComponentConfiguration,
	 ClassLoader, String, long, long)}
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
			initServiceComponent(
				com.liferay.portal.kernel.service.configuration.
					ServiceComponentConfiguration serviceComponentConfiguration,
				java.lang.ClassLoader classLoader,
				java.lang.String buildNamespace, long buildNumber,
				long buildDate, boolean buildAutoUpgrade)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _serviceComponentLocalService.initServiceComponent(
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
	@Override
	public com.liferay.portal.kernel.model.ServiceComponent
		updateServiceComponent(
			com.liferay.portal.kernel.model.ServiceComponent serviceComponent) {

		return _serviceComponentLocalService.updateServiceComponent(
			serviceComponent);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #upgradeDB(ClassLoader, String, long, ServiceComponent,
	 String, String, String)}
	 */
	@Deprecated
	@Override
	public void upgradeDB(
			java.lang.ClassLoader classLoader, java.lang.String buildNamespace,
			long buildNumber, boolean buildAutoUpgrade,
			com.liferay.portal.kernel.model.ServiceComponent
				previousServiceComponent,
			java.lang.String tablesSQL, java.lang.String sequencesSQL,
			java.lang.String indexesSQL)
		throws java.lang.Exception {

		_serviceComponentLocalService.upgradeDB(
			classLoader, buildNamespace, buildNumber, buildAutoUpgrade,
			previousServiceComponent, tablesSQL, sequencesSQL, indexesSQL);
	}

	@Override
	public void upgradeDB(
			java.lang.ClassLoader classLoader, java.lang.String buildNamespace,
			long buildNumber,
			com.liferay.portal.kernel.model.ServiceComponent
				previousServiceComponent,
			java.lang.String tablesSQL, java.lang.String sequencesSQL,
			java.lang.String indexesSQL)
		throws java.lang.Exception {

		_serviceComponentLocalService.upgradeDB(
			classLoader, buildNamespace, buildNumber, previousServiceComponent,
			tablesSQL, sequencesSQL, indexesSQL);
	}

	@Override
	public void verifyDB() {
		_serviceComponentLocalService.verifyDB();
	}

	@Override
	public ServiceComponentLocalService getWrappedService() {
		return _serviceComponentLocalService;
	}

	@Override
	public void setWrappedService(
		ServiceComponentLocalService serviceComponentLocalService) {

		_serviceComponentLocalService = serviceComponentLocalService;
	}

	private ServiceComponentLocalService _serviceComponentLocalService;

}