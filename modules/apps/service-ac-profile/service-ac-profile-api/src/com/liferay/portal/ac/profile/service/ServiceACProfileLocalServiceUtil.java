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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ServiceACProfile. This utility wraps
 * {@link com.liferay.portal.ac.profile.service.impl.ServiceACProfileLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfileLocalService
 * @see com.liferay.portal.ac.profile.service.base.ServiceACProfileLocalServiceBaseImpl
 * @see com.liferay.portal.ac.profile.service.impl.ServiceACProfileLocalServiceImpl
 * @generated
 */
@ProviderType
public class ServiceACProfileLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.ac.profile.service.impl.ServiceACProfileLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		long companyId, long userId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addServiceACProfile(companyId, userId, allowedServices,
			name, titleMap, serviceContext);
	}

	/**
	* Adds the service a c profile to the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was added
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile addServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return getService().addServiceACProfile(serviceACProfile);
	}

	public static void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addServiceACProfileResources(serviceACProfile,
			addGroupPermissions, addGuestPermissions);
	}

	public static void addServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addServiceACProfileResources(serviceACProfile, groupPermissions,
			guestPermissions);
	}

	/**
	* Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	*
	* @param serviceACProfileId the primary key for the new service a c profile
	* @return the new service a c profile
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile createServiceACProfile(
		long serviceACProfileId) {
		return getService().createServiceACProfile(serviceACProfileId);
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
	* Deletes the service a c profile from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceACProfile(serviceACProfile);
	}

	/**
	* Deletes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile that was removed
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile deleteServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceACProfile(serviceACProfileId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfile(
		long serviceACProfileId) {
		return getService().fetchServiceACProfile(serviceACProfileId);
	}

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .fetchServiceACProfileByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end) {
		return getService().getCompanyServiceACProfiles(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> obc) {
		return getService()
				   .getCompanyServiceACProfiles(companyId, start, end, obc);
	}

	public static int getCompanyServiceACProfilesCount(long companyId) {
		return getService().getCompanyServiceACProfilesCount(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceACProfile(companyId, name);
	}

	/**
	* Returns the service a c profile with the primary key.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile
	* @throws PortalException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfile(
		long serviceACProfileId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceACProfile(serviceACProfileId);
	}

	/**
	* Returns the service a c profile with the matching UUID and company.
	*
	* @param uuid the service a c profile's UUID
	* @param companyId the primary key of the company
	* @return the matching service a c profile
	* @throws PortalException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile getServiceACProfileByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getServiceACProfileByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> getServiceACProfiles(
		int start, int end) {
		return getService().getServiceACProfiles(start, end);
	}

	/**
	* Returns the number of service a c profiles.
	*
	* @return the number of service a c profiles
	*/
	public static int getServiceACProfilesCount() {
		return getService().getServiceACProfilesCount();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the service a c profile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfile the service a c profile
	* @return the service a c profile that was updated
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return getService().updateServiceACProfile(serviceACProfile);
	}

	public static com.liferay.portal.ac.profile.model.ServiceACProfile updateServiceACProfile(
		long serviceACProfileId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateServiceACProfile(serviceACProfileId, allowedServices,
			name, titleMap, serviceContext);
	}

	public static void updateServiceACProfileResources(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateServiceACProfileResources(serviceACProfile,
			groupPermissions, guestPermissions);
	}

	public static ServiceACProfileLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ServiceACProfileLocalService service) {
	}

	private static ServiceTracker<ServiceACProfileLocalService, ServiceACProfileLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServiceACProfileLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<ServiceACProfileLocalService, ServiceACProfileLocalService>(bundle.getBundleContext(),
				ServiceACProfileLocalService.class, null);

		_serviceTracker.open();
	}
}