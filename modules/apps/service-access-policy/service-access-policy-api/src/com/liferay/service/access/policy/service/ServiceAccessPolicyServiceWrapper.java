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
 * Provides a wrapper for {@link ServiceAccessPolicyService}.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyService
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyServiceWrapper
	implements ServiceAccessPolicyService,
		ServiceWrapper<ServiceAccessPolicyService> {
	public ServiceAccessPolicyServiceWrapper(
		ServiceAccessPolicyService serviceAccessPolicyService) {
		_serviceAccessPolicyService = serviceAccessPolicyService;
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		java.lang.String allowedServiceSignatures, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.addServiceAccessPolicy(allowedServiceSignatures,
			name, titleMap, serviceContext);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.deleteServiceAccessPolicy(serviceAccessPolicy);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.deleteServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _serviceAccessPolicyService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end) {
		return _serviceAccessPolicyService.getCompanyServiceAccessPolicies(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> obc) {
		return _serviceAccessPolicyService.getCompanyServiceAccessPolicies(companyId,
			start, end, obc);
	}

	@Override
	public int getCompanyServiceAccessPoliciesCount(long companyId) {
		return _serviceAccessPolicyService.getCompanyServiceAccessPoliciesCount(companyId);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.getServiceAccessPolicy(companyId,
			name);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.getServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_serviceAccessPolicyService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		long serviceAccessPolicyId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _serviceAccessPolicyService.updateServiceAccessPolicy(serviceAccessPolicyId,
			allowedServiceSignatures, name, titleMap, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ServiceAccessPolicyService getWrappedServiceAccessPolicyService() {
		return _serviceAccessPolicyService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedServiceAccessPolicyService(
		ServiceAccessPolicyService serviceAccessPolicyService) {
		_serviceAccessPolicyService = serviceAccessPolicyService;
	}

	@Override
	public ServiceAccessPolicyService getWrappedService() {
		return _serviceAccessPolicyService;
	}

	@Override
	public void setWrappedService(
		ServiceAccessPolicyService serviceAccessPolicyService) {
		_serviceAccessPolicyService = serviceAccessPolicyService;
	}

	private ServiceAccessPolicyService _serviceAccessPolicyService;
}