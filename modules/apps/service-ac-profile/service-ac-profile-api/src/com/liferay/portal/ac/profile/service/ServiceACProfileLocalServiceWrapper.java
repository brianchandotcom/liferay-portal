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

package com.liferay.portal.ac.profile.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ServiceACProfileLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfileLocalService
 * @generated
 */
@ProviderType
public class ServiceACProfileLocalServiceWrapper
	implements ServiceACProfileLocalService,
		ServiceWrapper<ServiceACProfileLocalService> {
	public ServiceACProfileLocalServiceWrapper(
		ServiceACProfileLocalService serviceACProfileLocalService) {
		_serviceACProfileLocalService = serviceACProfileLocalService;
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		long companyId, long userId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.addServiceACProfile(companyId,
			userId, allowedServices, name, titleMap, serviceContext);
	}

	/**
	* Adds the service a c profile to the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was added
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return _serviceACProfileLocalService.addServiceACProfile(serviceACProfile);
	}

	@Override
	public void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_serviceACProfileLocalService.addServiceACProfileResources(serviceACProfile,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_serviceACProfileLocalService.addServiceACProfileResources(serviceACProfile,
			groupPermissions, guestPermissions);
	}

	/**
	* Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	*
	* @param serviceACProfileId the primary key for the new service a c profile
	* @return the new service a c profile
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile createServiceACProfile(
		long serviceACProfileId) {
		return _serviceACProfileLocalService.createServiceACProfile(serviceACProfileId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the service a c profile from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.deleteServiceACProfile(serviceACProfile);
	}

	/**
	* Deletes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.deleteServiceACProfile(serviceACProfileId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _serviceACProfileLocalService.dynamicQuery();
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
		return _serviceACProfileLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _serviceACProfileLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _serviceACProfileLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _serviceACProfileLocalService.dynamicQueryCount(dynamicQuery);
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
		return _serviceACProfileLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfile(
		long serviceACProfileId) {
		return _serviceACProfileLocalService.fetchServiceACProfile(serviceACProfileId);
	}

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _serviceACProfileLocalService.fetchServiceACProfileByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _serviceACProfileLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _serviceACProfileLocalService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end) {
		return _serviceACProfileLocalService.getCompanyServiceACProfiles(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> obc) {
		return _serviceACProfileLocalService.getCompanyServiceACProfiles(companyId,
			start, end, obc);
	}

	@Override
	public int getCompanyServiceACProfilesCount(long companyId) {
		return _serviceACProfileLocalService.getCompanyServiceACProfilesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return _serviceACProfileLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.getServiceACProfile(companyId, name);
	}

	/**
	* Returns the service a c profile with the primary key.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.getServiceACProfile(serviceACProfileId);
	}

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile
	* @throws PortalException if a matching service a c profile could not be found
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.getServiceACProfileByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of service a c profiles
	*/
	@Override
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getServiceACProfiles(
		int start, int end) {
		return _serviceACProfileLocalService.getServiceACProfiles(start, end);
	}

	/**
	* Returns the number of service a c profiles.
	*
	* @return the number of service a c profiles
	*/
	@Override
	public int getServiceACProfilesCount() {
		return _serviceACProfileLocalService.getServiceACProfilesCount();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_serviceACProfileLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the service a c profile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was updated
	*/
	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return _serviceACProfileLocalService.updateServiceACProfile(serviceACProfile);
	}

	@Override
	public com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		long serviceACProfileId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceACProfileLocalService.updateServiceACProfile(serviceACProfileId,
			allowedServices, name, titleMap, serviceContext);
	}

	@Override
	public void updateServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_serviceACProfileLocalService.updateServiceACProfileResources(serviceACProfile,
			groupPermissions, guestPermissions);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ServiceACProfileLocalService getWrappedServiceACProfileLocalService() {
		return _serviceACProfileLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedServiceACProfileLocalService(
		ServiceACProfileLocalService serviceACProfileLocalService) {
		_serviceACProfileLocalService = serviceACProfileLocalService;
	}

	@Override
	public ServiceACProfileLocalService getWrappedService() {
		return _serviceACProfileLocalService;
	}

	@Override
	public void setWrappedService(
		ServiceACProfileLocalService serviceACProfileLocalService) {
		_serviceACProfileLocalService = serviceACProfileLocalService;
	}

	private ServiceACProfileLocalService _serviceACProfileLocalService;
}