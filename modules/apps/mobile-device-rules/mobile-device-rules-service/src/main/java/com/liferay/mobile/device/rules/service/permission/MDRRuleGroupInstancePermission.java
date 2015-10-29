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

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance"},
	service = BaseModelPermissionChecker.class
)
public class MDRRuleGroupInstancePermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroupInstanceId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MDRRuleGroupInstance.class.getName(),
				ruleGroupInstanceId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ruleGroupInstance, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MDRRuleGroupInstance.class.getName(),
				ruleGroupInstance.getRuleGroupInstanceId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException {

		MDRRuleGroupInstance ruleGroupInstance =
			_mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstance(
				ruleGroupInstanceId);

		return contains(permissionChecker, ruleGroupInstance, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		MDRRuleGroupInstance ruleGroupInstance, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, ruleGroupInstance.getGroupId(),
			MDRRuleGroupInstance.class.getName(),
			ruleGroupInstance.getRuleGroupInstanceId(),
			PortletKeys.MOBILE_DEVICE_RULES, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			ruleGroupInstance.getGroupId(),
			MDRRuleGroupInstance.class.getName(),
			ruleGroupInstance.getRuleGroupInstanceId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	private static MDRRuleGroupInstanceLocalService
		_mdrRuleGroupInstanceLocalService;

}