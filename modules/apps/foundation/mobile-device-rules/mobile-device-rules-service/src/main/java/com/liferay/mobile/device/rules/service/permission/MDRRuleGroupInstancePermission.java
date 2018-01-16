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
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @deprecated As of 1.2.0, with no direct replacement
 */
@Component(
	property = {"model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance"},
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class MDRRuleGroupInstancePermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException {

		_mdrRuleGroupInstanceModelResourcePermission.check(
			permissionChecker, ruleGroupInstanceId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		_mdrRuleGroupInstanceModelResourcePermission.check(
			permissionChecker, ruleGroupInstance, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException {

		return _mdrRuleGroupInstanceModelResourcePermission.contains(
			permissionChecker, ruleGroupInstanceId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		return _mdrRuleGroupInstanceModelResourcePermission.contains(
			permissionChecker, ruleGroupInstance, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_mdrRuleGroupInstanceModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	protected void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<MDRRuleGroupInstance> modelResourcePermission) {

		_mdrRuleGroupInstanceModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<MDRRuleGroupInstance>
		_mdrRuleGroupInstanceModelResourcePermission;

}