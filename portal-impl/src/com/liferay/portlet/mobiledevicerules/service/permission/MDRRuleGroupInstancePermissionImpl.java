/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class MDRRuleGroupInstancePermissionImpl
	implements MDRRuleGroupInstancePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, ruleGroupInstanceId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroupInstance, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstance(
				ruleGroupInstanceId);

		return contains(permissionChecker, ruleGroupInstance, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker,
		MDRRuleGroupInstance ruleGroupInstance, String actionId) {

		return permissionChecker.hasPermission(
			ruleGroupInstance.getGroupId(),
			MDRRuleGroupInstance.class.getName(),
			ruleGroupInstance.getRuleGroupInstanceId(), actionId);
	}

}