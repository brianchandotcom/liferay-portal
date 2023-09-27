/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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