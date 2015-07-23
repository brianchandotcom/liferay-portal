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

package com.liferay.service.access.policy.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.service.access.policy.model.ServiceAccessPolicy;
import com.liferay.service.access.policy.service.ServiceAccessPolicyLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"model.class.name=com.liferay.service.access.policy.model.ServiceAccessPolicy"
	}
)
public class ServiceAccessPolicyPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long serviceAccessPolicyId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, serviceAccessPolicyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ServiceAccessPolicy.class.getName(),
				serviceAccessPolicyId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			ServiceAccessPolicy serviceAccessPolicy, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, serviceAccessPolicy, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ServiceAccessPolicy.class.getName(),
				serviceAccessPolicy.getServiceAccessPolicyId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		ServiceAccessPolicy serviceAccessPolicy =
			ServiceAccessPolicyLocalServiceUtil.getServiceAccessPolicy(classPK);

		return contains(permissionChecker, serviceAccessPolicy, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		ServiceAccessPolicy serviceAccessPolicy, String actionId) {

		return permissionChecker.hasPermission(
			0, ServiceAccessPolicy.class.getName(),
			serviceAccessPolicy.getServiceAccessPolicyId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}