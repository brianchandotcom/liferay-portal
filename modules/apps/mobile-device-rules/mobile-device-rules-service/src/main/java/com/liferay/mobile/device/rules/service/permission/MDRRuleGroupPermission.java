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

package com.liferay.mobile.device.rules.service.permission;

import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroup"},
	service = BaseModelPermissionChecker.class
)
public class MDRRuleGroupPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MDRRuleGroup.class.getName(), ruleGroupId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroup, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MDRRuleGroup.class.getName(),
				ruleGroup.getRuleGroupId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException {

		MDRRuleGroup ruleGroup = _mdrRuleGroupLocalService.getMDRRuleGroup(
			ruleGroupId);

		return contains(permissionChecker, ruleGroup, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
		String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, ruleGroup.getGroupId(),
			MDRRuleGroup.class.getName(), ruleGroup.getRuleGroupId(),
			MDRPortletKeys.MOBILE_DEVICE_RULES, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			ruleGroup.getGroupId(), MDRRuleGroup.class.getName(),
			ruleGroup.getRuleGroupId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private static MDRRuleGroupLocalService _mdrRuleGroupLocalService;

}