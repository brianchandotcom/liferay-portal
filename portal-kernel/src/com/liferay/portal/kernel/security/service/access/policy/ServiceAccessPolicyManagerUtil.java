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

package com.liferay.portal.kernel.security.service.access.policy;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessPolicyManagerUtil {

	public static String getDefaultApplicationServiceAccessPolicyName(
		long companyId) {

		return _serviceAccessPolicyManager.
			getDefaultApplicationServiceAccessPolicyName(companyId);
	}

	public static String getDefaultUserServiceAccessPolicyName(long companyId) {
		return _serviceAccessPolicyManager.
			getDefaultUserServiceAccessPolicyName(companyId);
	}

	public static List<ServiceAccessPolicy> getServiceAccessPolicies(
		long companyId, int start, int end) {

		return _serviceAccessPolicyManager.getServiceAccessPolicies(
			companyId, start, end);
	}

	public static int getServiceAccessPoliciesCount(long companyId) {
		return _serviceAccessPolicyManager.getServiceAccessPoliciesCount(
			companyId);
	}

	public static ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, String name) {

		return _serviceAccessPolicyManager.getServiceAccessPolicy(
			companyId, name);
	}

	public static ServiceAccessPolicyManager getServiceAccessPolicyManager() {
		return _serviceAccessPolicyManager;
	}

	private ServiceAccessPolicyManagerUtil() {
	}

	private static volatile ServiceAccessPolicyManager
		_serviceAccessPolicyManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				ServiceAccessPolicyManager.class,
				ServiceAccessPolicyManagerUtil.class,
				"_serviceAccessPolicyManager", false, true);

}