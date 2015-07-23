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

package com.liferay.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ServiceAccessPolicy. This utility wraps
 * {@link com.liferay.service.access.policy.service.impl.ServiceAccessPolicyLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyLocalService
 * @see com.liferay.service.access.policy.service.base.ServiceAccessPolicyLocalServiceBaseImpl
 * @see com.liferay.service.access.policy.service.impl.ServiceAccessPolicyLocalServiceImpl
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.policy.service.impl.ServiceAccessPolicyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the service access policy to the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was added
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return getService().addServiceAccessPolicy(serviceAccessPolicy);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		long userId, java.lang.String allowedServiceSignatures,
		boolean defaultServiceAccessPolicy, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addServiceAccessPolicy(userId, allowedServiceSignatures,
			defaultServiceAccessPolicy, name, titleMap, serviceContext);
	}

	public static void checkDefaultServiceAccessPolicy(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkDefaultServiceAccessPolicy(companyId);
	}

	/**
	* Creates a new service access policy with the primary key. Does not add the service access policy to the database.
	*
	* @param serviceAccessPolicyId the primary key for the new service access policy
	* @return the new service access policy
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy createServiceAccessPolicy(
		long serviceAccessPolicyId) {
		return getService().createServiceAccessPolicy(serviceAccessPolicyId);
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
	* Deletes the service access policy from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was removed
	* @throws PortalException
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceAccessPolicy(serviceAccessPolicy);
	}

	/**
	* Deletes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy that was removed
	* @throws PortalException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceAccessPolicy(serviceAccessPolicyId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchServiceAccessPolicy(
		long serviceAccessPolicyId) {
		return getService().fetchServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Returns the service access policy with the matching UUID and company.
	*
	* @param uuid the service access policy's UUID
	* @param companyId the primary key of the company
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchServiceAccessPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .fetchServiceAccessPolicyByUuidAndCompanyId(uuid, companyId);
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

	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end) {
		return getService()
				   .getCompanyServiceAccessPolicies(companyId, start, end);
	}

	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> obc) {
		return getService()
				   .getCompanyServiceAccessPolicies(companyId, start, end, obc);
	}

	public static int getCompanyServiceAccessPoliciesCount(long companyId) {
		return getService().getCompanyServiceAccessPoliciesCount(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the service access policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getServiceAccessPolicies(
		int start, int end) {
		return getService().getServiceAccessPolicies(start, end);
	}

	/**
	* Returns the number of service access policies.
	*
	* @return the number of service access policies
	*/
	public static int getServiceAccessPoliciesCount() {
		return getService().getServiceAccessPoliciesCount();
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceAccessPolicy(companyId, name);
	}

	/**
	* Returns the service access policy with the primary key.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy
	* @throws PortalException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Returns the service access policy with the matching UUID and company.
	*
	* @param uuid the service access policy's UUID
	* @param companyId the primary key of the company
	* @return the matching service access policy
	* @throws PortalException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getServiceAccessPolicyByUuidAndCompanyId(uuid, companyId);
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
	* Updates the service access policy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was updated
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return getService().updateServiceAccessPolicy(serviceAccessPolicy);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		long serviceAccessPolicyId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateServiceAccessPolicy(serviceAccessPolicyId,
			allowedServiceSignatures, name, titleMap, serviceContext);
	}

	public static ServiceAccessPolicyLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ServiceAccessPolicyLocalService service) {
	}

	private static ServiceTracker<ServiceAccessPolicyLocalService, ServiceAccessPolicyLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServiceAccessPolicyLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<ServiceAccessPolicyLocalService, ServiceAccessPolicyLocalService>(bundle.getBundleContext(),
				ServiceAccessPolicyLocalService.class, null);

		_serviceTracker.open();
	}
}