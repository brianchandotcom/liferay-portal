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

package com.liferay.oauth2.provider.service.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ApplicationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ApplicationLocalService
 * @generated
 */
@ProviderType
public class ApplicationLocalServiceWrapper implements ApplicationLocalService,
	ServiceWrapper<ApplicationLocalService> {
	public ApplicationLocalServiceWrapper(
		ApplicationLocalService applicationLocalService) {
		_applicationLocalService = applicationLocalService;
	}

	/**
	* Adds the application to the database. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was added
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application addApplication(
		com.liferay.oauth2.provider.service.model.Application application) {
		return _applicationLocalService.addApplication(application);
	}

	/**
	* Creates a new application with the primary key. Does not add the application to the database.
	*
	* @param id the primary key for the new application
	* @return the new application
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application createApplication(
		long id) {
		return _applicationLocalService.createApplication(id);
	}

	/**
	* Deletes the application from the database. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was removed
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application deleteApplication(
		com.liferay.oauth2.provider.service.model.Application application) {
		return _applicationLocalService.deleteApplication(application);
	}

	/**
	* Deletes the application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the application
	* @return the application that was removed
	* @throws PortalException if a application with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application deleteApplication(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _applicationLocalService.deleteApplication(id);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _applicationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _applicationLocalService.dynamicQuery();
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
		return _applicationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.service.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _applicationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.service.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _applicationLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _applicationLocalService.dynamicQueryCount(dynamicQuery);
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
		return _applicationLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.oauth2.provider.service.model.Application fetchApplication(
		long id) {
		return _applicationLocalService.fetchApplication(id);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _applicationLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the application with the primary key.
	*
	* @param id the primary key of the application
	* @return the application
	* @throws PortalException if a application with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application getApplication(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return _applicationLocalService.getApplication(id);
	}

	/**
	* Returns a range of all the applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.service.model.impl.ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of applications
	* @param end the upper bound of the range of applications (not inclusive)
	* @return the range of applications
	*/
	@Override
	public java.util.List<com.liferay.oauth2.provider.service.model.Application> getApplications(
		int start, int end) {
		return _applicationLocalService.getApplications(start, end);
	}

	/**
	* Returns the number of applications.
	*
	* @return the number of applications
	*/
	@Override
	public int getApplicationsCount() {
		return _applicationLocalService.getApplicationsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _applicationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _applicationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _applicationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the application in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param application the application
	* @return the application that was updated
	*/
	@Override
	public com.liferay.oauth2.provider.service.model.Application updateApplication(
		com.liferay.oauth2.provider.service.model.Application application) {
		return _applicationLocalService.updateApplication(application);
	}

	@Override
	public ApplicationLocalService getWrappedService() {
		return _applicationLocalService;
	}

	@Override
	public void setWrappedService(
		ApplicationLocalService applicationLocalService) {
		_applicationLocalService = applicationLocalService;
	}

	private ApplicationLocalService _applicationLocalService;
}