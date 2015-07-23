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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ServiceAccessPolicyLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyLocalService
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyLocalServiceWrapper
	implements ServiceAccessPolicyLocalService,
		ServiceWrapper<ServiceAccessPolicyLocalService> {
	public ServiceAccessPolicyLocalServiceWrapper(
		ServiceAccessPolicyLocalService serviceAccessPolicyLocalService) {
		_serviceAccessPolicyLocalService = serviceAccessPolicyLocalService;
	}

	/**
	* Adds the service access policy to the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was added
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return _serviceAccessPolicyLocalService.addServiceAccessPolicy(serviceAccessPolicy);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		long userId, java.lang.String allowedServiceSignatures,
		boolean defaultServiceAccessPolicy, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.addServiceAccessPolicy(userId,
			allowedServiceSignatures, defaultServiceAccessPolicy, name,
			titleMap, serviceContext);
	}

	@Override
	public void checkDefaultServiceAccessPolicy(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_serviceAccessPolicyLocalService.checkDefaultServiceAccessPolicy(companyId);
	}

	/**
	* Creates a new service access policy with the primary key. Does not add the service access policy to the database.
	*
	* @param serviceAccessPolicyId the primary key for the new service access policy
	* @return the new service access policy
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy createServiceAccessPolicy(
		long serviceAccessPolicyId) {
		return _serviceAccessPolicyLocalService.createServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the service access policy from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.deleteServiceAccessPolicy(serviceAccessPolicy);
	}

	/**
	* Deletes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy that was removed
	* @throws PortalException if a service access policy with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.deleteServiceAccessPolicy(serviceAccessPolicyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _serviceAccessPolicyLocalService.dynamicQuery();
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
		return _serviceAccessPolicyLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _serviceAccessPolicyLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _serviceAccessPolicyLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _serviceAccessPolicyLocalService.dynamicQueryCount(dynamicQuery);
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
		return _serviceAccessPolicyLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchServiceAccessPolicy(
		long serviceAccessPolicyId) {
		return _serviceAccessPolicyLocalService.fetchServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Returns the service access policy with the matching UUID and company.
	*
	* @param uuid the service access policy's UUID
	* @param companyId the primary key of the company
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchServiceAccessPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _serviceAccessPolicyLocalService.fetchServiceAccessPolicyByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _serviceAccessPolicyLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _serviceAccessPolicyLocalService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end) {
		return _serviceAccessPolicyLocalService.getCompanyServiceAccessPolicies(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> obc) {
		return _serviceAccessPolicyLocalService.getCompanyServiceAccessPolicies(companyId,
			start, end, obc);
	}

	@Override
	public int getCompanyServiceAccessPoliciesCount(long companyId) {
		return _serviceAccessPolicyLocalService.getCompanyServiceAccessPoliciesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _serviceAccessPolicyLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getServiceAccessPolicies(
		int start, int end) {
		return _serviceAccessPolicyLocalService.getServiceAccessPolicies(start,
			end);
	}

	/**
	* Returns the number of service access policies.
	*
	* @return the number of service access policies
	*/
	@Override
	public int getServiceAccessPoliciesCount() {
		return _serviceAccessPolicyLocalService.getServiceAccessPoliciesCount();
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.getServiceAccessPolicy(companyId,
			name);
	}

	/**
	* Returns the service access policy with the primary key.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy
	* @throws PortalException if a service access policy with the primary key could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.getServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Returns the service access policy with the matching UUID and company.
	*
	* @param uuid the service access policy's UUID
	* @param companyId the primary key of the company
	* @return the matching service access policy
	* @throws PortalException if a matching service access policy could not be found
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.getServiceAccessPolicyByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_serviceAccessPolicyLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the service access policy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicy the service access policy
	* @return the service access policy that was updated
	*/
	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return _serviceAccessPolicyLocalService.updateServiceAccessPolicy(serviceAccessPolicy);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		long serviceAccessPolicyId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyLocalService.updateServiceAccessPolicy(serviceAccessPolicyId,
			allowedServiceSignatures, name, titleMap, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ServiceAccessPolicyLocalService getWrappedServiceAccessPolicyLocalService() {
		return _serviceAccessPolicyLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedServiceAccessPolicyLocalService(
		ServiceAccessPolicyLocalService serviceAccessPolicyLocalService) {
		_serviceAccessPolicyLocalService = serviceAccessPolicyLocalService;
	}

	@Override
	public ServiceAccessPolicyLocalService getWrappedService() {
		return _serviceAccessPolicyLocalService;
	}

	@Override
	public void setWrappedService(
		ServiceAccessPolicyLocalService serviceAccessPolicyLocalService) {
		_serviceAccessPolicyLocalService = serviceAccessPolicyLocalService;
	}

	private ServiceAccessPolicyLocalService _serviceAccessPolicyLocalService;
}