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