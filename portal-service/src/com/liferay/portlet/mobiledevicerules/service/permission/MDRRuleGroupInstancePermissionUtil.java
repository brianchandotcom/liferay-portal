/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

/**
 * @author Michael C. Han
 */
public class MDRRuleGroupInstancePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException, SystemException {

		getMDRRuleGroupInstancePermission().check(
			permissionChecker, ruleGroupInstanceId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker,
			MDRRuleGroupInstance ruleGroupInstance, String actionId)
		throws PortalException {

		getMDRRuleGroupInstancePermission().check(
			permissionChecker, ruleGroupInstance, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupInstanceId,
			String actionId)
		throws PortalException, SystemException {

		return getMDRRuleGroupInstancePermission().contains(
			permissionChecker, ruleGroupInstanceId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		MDRRuleGroupInstance ruleGroupInstance, String actionId) {

		return getMDRRuleGroupInstancePermission().contains(
			permissionChecker, ruleGroupInstance, actionId);
	}

	public static MDRRuleGroupInstancePermission
		getMDRRuleGroupInstancePermission() {

		return _mdrRuleGroupInstancePermission;
	}

	public void setMDRRuleGroupInstancePermission(
		MDRRuleGroupInstancePermission mdrRuleGroupInstancePermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_mdrRuleGroupInstancePermission = mdrRuleGroupInstancePermission;
	}

	private static MDRRuleGroupInstancePermission
		_mdrRuleGroupInstancePermission;

}