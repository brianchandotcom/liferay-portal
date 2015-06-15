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

package com.liferay.portlet.mobiledevicerules.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class MDRRuleGroupPermissionImpl implements MDRRuleGroupPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroupId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroup, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException {

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.getMDRRuleGroup(
			ruleGroupId);

		return contains(permissionChecker, ruleGroup, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
		String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, ruleGroup.getGroupId(),
			MDRRuleGroup.class.getName(), ruleGroup.getRuleGroupId(),
			PortletKeys.MOBILE_DEVICE_SITE_ADMIN, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			ruleGroup.getGroupId(), MDRRuleGroup.class.getName(),
			ruleGroup.getRuleGroupId(), actionId);
	}

}