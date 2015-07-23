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

package com.liferay.service.access.policy.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.service.access.policy.constants.ServiceAccessPolicyActionKeys;
import com.liferay.service.access.policy.constants.ServiceAccessPolicyConstants;
import com.liferay.service.access.policy.model.ServiceAccessPolicy;
import com.liferay.service.access.policy.service.base.ServiceAccessPolicyServiceBaseImpl;
import com.liferay.service.access.policy.service.permission.ServiceAccessPolicyPermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class ServiceAccessPolicyServiceImpl
	extends ServiceAccessPolicyServiceBaseImpl {

	@Override
	public ServiceAccessPolicy addServiceAccessPolicy(
			String allowedServiceSignatures, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), ServiceAccessPolicyConstants.SERVICE_NAME,
			ServiceAccessPolicyActionKeys.ACTION_ADD_SERVICE_ACCESS_POLICY);

		return serviceAccessPolicyLocalService.addServiceAccessPolicy(
			getUserId(), allowedServiceSignatures, false, name, titleMap,
			serviceContext);
	}

	@Override
	public ServiceAccessPolicy deleteServiceAccessPolicy(
			long serviceAccessPolicyId)
		throws PortalException {

		ServiceAccessPolicyPermission.check(
			getPermissionChecker(), serviceAccessPolicyId, ActionKeys.DELETE);

		return serviceAccessPolicyLocalService.deleteServiceAccessPolicy(
			serviceAccessPolicyId);
	}

	@Override
	public ServiceAccessPolicy deleteServiceAccessPolicy(
			ServiceAccessPolicy serviceAccessPolicy)
		throws PortalException {

		ServiceAccessPolicyPermission.check(
			getPermissionChecker(), serviceAccessPolicy, ActionKeys.DELETE);

		return serviceAccessPolicyLocalService.deleteServiceAccessPolicy(
			serviceAccessPolicy);
	}

	@Override
	public List<ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end) {

		return serviceAccessPolicyPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		OrderByComparator<ServiceAccessPolicy> obc) {

		return serviceAccessPolicyPersistence.filterFindByCompanyId(
			companyId, start, end, obc);
	}

	@Override
	public int getCompanyServiceAccessPoliciesCount(long companyId) {
		return serviceAccessPolicyPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public ServiceAccessPolicy getServiceAccessPolicy(
			long serviceAccessPolicyId)
		throws PortalException {

		ServiceAccessPolicyPermission.check(
			getPermissionChecker(), serviceAccessPolicyId, ActionKeys.VIEW);

		return serviceAccessPolicyPersistence.findByPrimaryKey(
			serviceAccessPolicyId);
	}

	@Override
	public ServiceAccessPolicy getServiceAccessPolicy(
			long companyId, String name)
		throws PortalException {

		ServiceAccessPolicy serviceAccessPolicy =
			serviceAccessPolicyPersistence.findByC_N(companyId, name);

		ServiceAccessPolicyPermission.check(
			getPermissionChecker(), serviceAccessPolicy, ActionKeys.VIEW);

		return serviceAccessPolicy;
	}

	@Override
	public ServiceAccessPolicy updateServiceAccessPolicy(
			long serviceAccessPolicyId, String allowedServiceSignatures,
			String name, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		ServiceAccessPolicyPermission.check(
			getPermissionChecker(), serviceAccessPolicyId, ActionKeys.UPDATE);

		return serviceAccessPolicyLocalService.updateServiceAccessPolicy(
			serviceAccessPolicyId, allowedServiceSignatures, name, titleMap,
			serviceContext);
	}

}