/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.service.access.policy;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class ServiceAccessPolicyManagerUtil {

	public static String getDefaultApplicationServiceAccessPolicyName(
		long companyId) {

		ServiceAccessPolicyManager serviceAccessPolicyManager =
			_serviceAccessPolicyManagerSnapshot.get();

		return serviceAccessPolicyManager.
			getDefaultApplicationServiceAccessPolicyName(companyId);
	}

	public static String getDefaultUserServiceAccessPolicyName(long companyId) {
		ServiceAccessPolicyManager serviceAccessPolicyManager =
			_serviceAccessPolicyManagerSnapshot.get();

		return serviceAccessPolicyManager.getDefaultUserServiceAccessPolicyName(
			companyId);
	}

	public static List<ServiceAccessPolicy> getServiceAccessPolicies(
		long companyId, int start, int end) {

		ServiceAccessPolicyManager serviceAccessPolicyManager =
			_serviceAccessPolicyManagerSnapshot.get();

		return serviceAccessPolicyManager.getServiceAccessPolicies(
			companyId, start, end);
	}

	public static int getServiceAccessPoliciesCount(long companyId) {
		ServiceAccessPolicyManager serviceAccessPolicyManager =
			_serviceAccessPolicyManagerSnapshot.get();

		return serviceAccessPolicyManager.getServiceAccessPoliciesCount(
			companyId);
	}

	public static ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, String name) {

		ServiceAccessPolicyManager serviceAccessPolicyManager =
			_serviceAccessPolicyManagerSnapshot.get();

		return serviceAccessPolicyManager.getServiceAccessPolicy(
			companyId, name);
	}

	public static ServiceAccessPolicyManager getServiceAccessPolicyManager() {
		return _serviceAccessPolicyManagerSnapshot.get();
	}

	private ServiceAccessPolicyManagerUtil() {
	}

	private static final Snapshot<ServiceAccessPolicyManager>
		_serviceAccessPolicyManagerSnapshot = new Snapshot<>(
			ServiceAccessPolicyManagerUtil.class,
			ServiceAccessPolicyManager.class);

}