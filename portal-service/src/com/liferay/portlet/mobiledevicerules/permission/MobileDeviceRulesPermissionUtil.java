/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

/**
 * @author Edward Han
 */
public class MobileDeviceRulesPermissionUtil {
	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		_mobileDeviceRulesPermission.check(
			permissionChecker, groupId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			MDRRuleGroup ruleGroup, String actionId)
		throws PortalException {

		_mobileDeviceRulesPermission.check(
			permissionChecker, groupId, ruleGroup, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		_mobileDeviceRulesPermission.check(
			permissionChecker, groupId, ruleGroupInstance, actionId);
	}

	public static void checkRuleGroup(
			PermissionChecker permissionChecker, long groupId,
			long ruleGroupId, String actionId)
		throws PortalException, SystemException {

		_mobileDeviceRulesPermission.checkRuleGroup(
			permissionChecker, groupId, ruleGroupId, actionId);
	}

	public static void checkRuleGroupInstance(
			PermissionChecker permissionChecker, long groupId,
			long ruleGroupInstanceId, String actionId)
		throws PortalException, SystemException {

		_mobileDeviceRulesPermission.checkRuleGroupInstance(
			permissionChecker, groupId, ruleGroupInstanceId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return _mobileDeviceRulesPermission.contains(
			permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId,
		MDRRuleGroup ruleGroup, String actionId) {

		return _mobileDeviceRulesPermission.contains(
			permissionChecker, groupId, ruleGroup, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId,
		MDRRuleGroupInstance ruleGroupInstance, String actionId) {

		return _mobileDeviceRulesPermission.contains(
			permissionChecker, groupId, ruleGroupInstance, actionId);
	}

	public static boolean containsRuleGroup(
			PermissionChecker permissionChecker, long groupId,
			long ruleGroupId, String actionId)
		throws PortalException, SystemException {

		return _mobileDeviceRulesPermission.containsRuleGroup(
			permissionChecker, groupId, ruleGroupId, actionId);
	}

	public static boolean containsRuleGroupInstance(
			PermissionChecker permissionChecker, long groupId,
			long ruleGroupInstanceId, String actionId)
		throws PortalException, SystemException {

		return _mobileDeviceRulesPermission.containsRuleGroupInstance(
			permissionChecker, groupId, ruleGroupInstanceId, actionId);
	}

	public void setRuleGroupPermission(
		MobileDeviceRulesPermission ruleGroupPermission) {

		_mobileDeviceRulesPermission = ruleGroupPermission;
	}

	private static MobileDeviceRulesPermission _mobileDeviceRulesPermission;

}