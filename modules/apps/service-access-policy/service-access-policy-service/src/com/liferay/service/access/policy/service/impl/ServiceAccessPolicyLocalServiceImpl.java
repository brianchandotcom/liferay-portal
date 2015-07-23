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
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.service.access.policy.configuration.ServiceAccessPolicyConfiguration;
import com.liferay.service.access.policy.constants.ServiceAccessPolicyConstants;
import com.liferay.service.access.policy.exception.DuplicateServiceAccessPolicyNameException;
import com.liferay.service.access.policy.exception.RequiredServiceAccessPolicyException;
import com.liferay.service.access.policy.exception.ServiceAccessPolicyNameException;
import com.liferay.service.access.policy.exception.ServiceAccessPolicyTitleException;
import com.liferay.service.access.policy.model.ServiceAccessPolicy;
import com.liferay.service.access.policy.service.base.ServiceAccessPolicyLocalServiceBaseImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class ServiceAccessPolicyLocalServiceImpl
	extends ServiceAccessPolicyLocalServiceBaseImpl {

	@Override
	public ServiceAccessPolicy addServiceAccessPolicy(
			long userId, String allowedServiceSignatures,
			boolean defaultServiceAccessPolicy, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		// Service access policy

		User user = userPersistence.findByPrimaryKey(userId);
		name = StringUtil.trim(name);

		validate(name, titleMap);

		if (serviceAccessPolicyPersistence.fetchByC_N(
				user.getCompanyId(), name) != null) {

			throw new DuplicateServiceAccessPolicyNameException();
		}

		long serviceAccessPolicyId = counterLocalService.increment();

		ServiceAccessPolicy serviceAccessPolicy =
			serviceAccessPolicyPersistence.create(serviceAccessPolicyId);

		serviceAccessPolicy.setUuid(serviceContext.getUuid());
		serviceAccessPolicy.setCompanyId(user.getCompanyId());
		serviceAccessPolicy.setUserId(userId);
		serviceAccessPolicy.setUserName(user.getFullName());
		serviceAccessPolicy.setAllowedServiceSignatures(
			allowedServiceSignatures);
		serviceAccessPolicy.setDefaultServiceAccessPolicy(
			defaultServiceAccessPolicy);
		serviceAccessPolicy.setName(name);
		serviceAccessPolicy.setTitleMap(titleMap);

		serviceAccessPolicyPersistence.update(
			serviceAccessPolicy, serviceContext);

		// Resources

		resourceLocalService.addResources(
			serviceAccessPolicy.getCompanyId(), 0, userId,
			ServiceAccessPolicy.class.getName(),
			serviceAccessPolicy.getServiceAccessPolicyId(), false, false,
			false);

		return serviceAccessPolicy;
	}

	public void checkDefaultServiceAccessPolicy(long companyId)
		throws PortalException {

		ServiceAccessPolicyConfiguration serviceAccessPolicyConfiguration =
			_settingsFactory.getSettings(
				ServiceAccessPolicyConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, ServiceAccessPolicyConstants.SERVICE_NAME));

		ServiceAccessPolicy applicationServiceAccessPolicy =
			serviceAccessPolicyPersistence.fetchByC_N(
				companyId,
				serviceAccessPolicyConfiguration.
					defaultApplicationServiceAccessPolicyName());
		ServiceAccessPolicy userServiceAccessPolicy =
			serviceAccessPolicyPersistence.fetchByC_N(
				companyId,
				serviceAccessPolicyConfiguration.
					defaultUserServiceAccessPolicyName());

		if ((applicationServiceAccessPolicy != null) &&
			(userServiceAccessPolicy != null)) {

			return;
		}

		long defaultUserId = userLocalService.getDefaultUserId(companyId);
		Role guestRole = roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		if (applicationServiceAccessPolicy == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				serviceAccessPolicyConfiguration.
					defaultApplicationServiceAccessPolicyDescription());

			applicationServiceAccessPolicy = addServiceAccessPolicy(
				defaultUserId,
				serviceAccessPolicyConfiguration.
					defaultApplicationServiceAccessPolicyServiceSignatures(),
				true,
				serviceAccessPolicyConfiguration.
					defaultApplicationServiceAccessPolicyName(),
				titleMap, new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				applicationServiceAccessPolicy.getCompanyId(),
				ServiceAccessPolicy.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					applicationServiceAccessPolicy.getServiceAccessPolicyId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}

		if (userServiceAccessPolicy == null) {
			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(),
				serviceAccessPolicyConfiguration.
					defaultUserServiceAccessPolicyDescription());

			userServiceAccessPolicy = addServiceAccessPolicy(
				defaultUserId,
				serviceAccessPolicyConfiguration.
					defaultUserServiceAccessPolicyServiceSignatures(),
				true,
				serviceAccessPolicyConfiguration.
					defaultUserServiceAccessPolicyName(),
				titleMap, new ServiceContext());

			resourcePermissionLocalService.setResourcePermissions(
				userServiceAccessPolicy.getCompanyId(),
				ServiceAccessPolicy.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					userServiceAccessPolicy.getServiceAccessPolicyId()),
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}
	}

	@Override
	public ServiceAccessPolicy deleteServiceAccessPolicy(
			long serviceAccessPolicyId)
		throws PortalException {

		ServiceAccessPolicy serviceAccessPolicy =
			serviceAccessPolicyPersistence.findByPrimaryKey(
				serviceAccessPolicyId);

		return deleteServiceAccessPolicy(serviceAccessPolicy);
	}

	@Override
	public ServiceAccessPolicy deleteServiceAccessPolicy(
			ServiceAccessPolicy serviceAccessPolicy)
		throws PortalException {

		if (serviceAccessPolicy.isDefaultServiceAccessPolicy() &&
			!CompanyThreadLocal.isDeleteInProcess()) {

			throw new RequiredServiceAccessPolicyException();
		}

		serviceAccessPolicy = serviceAccessPolicyPersistence.remove(
			serviceAccessPolicy);

		resourceLocalService.deleteResource(
			serviceAccessPolicy.getCompanyId(),
			ServiceAccessPolicy.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			serviceAccessPolicy.getServiceAccessPolicyId());

		return serviceAccessPolicy;
	}

	@Override
	public List<ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end) {

		return serviceAccessPolicyPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<ServiceAccessPolicy> getCompanyServiceAccessPolicies(
		long companyId, int start, int end,
		OrderByComparator<ServiceAccessPolicy> obc) {

		return serviceAccessPolicyPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	@Override
	public int getCompanyServiceAccessPoliciesCount(long companyId) {
		return serviceAccessPolicyPersistence.countByCompanyId(companyId);
	}

	@Override
	public ServiceAccessPolicy getServiceAccessPolicy(
			long companyId, String name)
		throws PortalException {

		return serviceAccessPolicyPersistence.findByC_N(companyId, name);
	}

	@Override
	public ServiceAccessPolicy updateServiceAccessPolicy(
			long serviceAccessPolicyId, String allowedServiceSignatures,
			String name, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		// Service access policy

		ServiceAccessPolicy serviceAccessPolicy =
			serviceAccessPolicyPersistence.findByPrimaryKey(
				serviceAccessPolicyId);

		ServiceAccessPolicy existingServiceAccessPolicy =
			serviceAccessPolicyPersistence.fetchByC_N(
				serviceAccessPolicy.getCompanyId(), name);

		if ((existingServiceAccessPolicy != null) &&
			(existingServiceAccessPolicy.getServiceAccessPolicyId() !=
				serviceAccessPolicyId)) {

			throw new DuplicateServiceAccessPolicyNameException();
		}

		if (serviceAccessPolicy.isDefaultServiceAccessPolicy()) {
			name = serviceAccessPolicy.getName();
		}

		name = StringUtil.trim(name);

		validate(name, titleMap);

		serviceAccessPolicy.setAllowedServiceSignatures(
			allowedServiceSignatures);
		serviceAccessPolicy.setName(name);
		serviceAccessPolicy.setTitleMap(titleMap);

		serviceAccessPolicy = serviceAccessPolicyPersistence.update(
			serviceAccessPolicy, serviceContext);

		return serviceAccessPolicy;
	}

	protected void validate(String name, Map<Locale, String> titleMap)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ServiceAccessPolicyNameException();
		}

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			int index =
				ServiceAccessPolicyConstants.NAME_ALLOWED_CHARACTERS.indexOf(c);

			if (index < 0) {
				throw new ServiceAccessPolicyNameException(
					"Invalid character " + c);
			}
		}

		boolean titleExists = false;

		if (titleMap != null) {
			Locale defaultLocale = LocaleUtil.getDefault();
			String defaultTitle = titleMap.get(defaultLocale);

			if (Validator.isNotNull(defaultTitle)) {
				titleExists = true;
			}
		}

		if (!titleExists) {
			throw new ServiceAccessPolicyTitleException();
		}
	}

	@ServiceReference(type = SettingsFactory.class)
	protected SettingsFactory _settingsFactory;

}