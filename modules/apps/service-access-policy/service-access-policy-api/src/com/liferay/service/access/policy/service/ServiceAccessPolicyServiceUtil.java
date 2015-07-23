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
 * Provides the remote service utility for ServiceAccessPolicy. This utility wraps
 * {@link com.liferay.service.access.policy.service.impl.ServiceAccessPolicyServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyService
 * @see com.liferay.service.access.policy.service.base.ServiceAccessPolicyServiceBaseImpl
 * @see com.liferay.service.access.policy.service.impl.ServiceAccessPolicyServiceImpl
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.policy.service.impl.ServiceAccessPolicyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		java.lang.String allowedServiceSignatures, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addServiceAccessPolicy(allowedServiceSignatures, name,
			titleMap, serviceContext);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceAccessPolicy(serviceAccessPolicy);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteServiceAccessPolicy(serviceAccessPolicyId);
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

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceAccessPolicy(companyId, name);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getServiceAccessPolicy(serviceAccessPolicyId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
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

	public static ServiceAccessPolicyService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ServiceAccessPolicyService service) {
	}

	private static ServiceTracker<ServiceAccessPolicyService, ServiceAccessPolicyService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServiceAccessPolicyServiceUtil.class);

		_serviceTracker = new ServiceTracker<ServiceAccessPolicyService, ServiceAccessPolicyService>(bundle.getBundleContext(),
				ServiceAccessPolicyService.class, null);

		_serviceTracker.open();
	}
}