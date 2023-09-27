/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.service.access.policy;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public interface ServiceAccessPolicyManager {

	public String getDefaultApplicationServiceAccessPolicyName(long companyId);

	public String getDefaultUserServiceAccessPolicyName(long companyId);

	public List<ServiceAccessPolicy> getServiceAccessPolicies(
		long companyId, int start, int end);

	public int getServiceAccessPoliciesCount(long companyId);

	public ServiceAccessPolicy getServiceAccessPolicy(
		long companyId, String name);

}