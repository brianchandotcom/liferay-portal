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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for ServiceAccessPolicy. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyServiceUtil
 * @see com.liferay.service.access.policy.service.base.ServiceAccessPolicyServiceBaseImpl
 * @see com.liferay.service.access.policy.service.impl.ServiceAccessPolicyServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=sap", "json.web.service.context.path=ServiceAccessPolicy"}, service = ServiceAccessPolicyService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ServiceAccessPolicyService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ServiceAccessPolicyServiceUtil} to access the service access policy remote service. Add custom service methods to {@link com.liferay.service.access.policy.service.impl.ServiceAccessPolicyServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.service.access.policy.model.ServiceAccessPolicy addServiceAccessPolicy(
		java.lang.String allowedServiceSignatures, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.service.access.policy.model.ServiceAccessPolicy deleteServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyServiceAccessPoliciesCount(long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.service.access.policy.model.ServiceAccessPolicy getServiceAccessPolicy(
		long serviceAccessPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public com.liferay.service.access.policy.model.ServiceAccessPolicy updateServiceAccessPolicy(
		long serviceAccessPolicyId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;
}