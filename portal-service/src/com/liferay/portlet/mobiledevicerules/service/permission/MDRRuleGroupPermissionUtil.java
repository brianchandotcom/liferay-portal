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
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;

/**
 * @author Michael C. Han
 */
public class MDRRuleGroupPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException, SystemException {

		getMDRRuleGroupPermission().check(
			permissionChecker, ruleGroupId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
			String actionId)
		throws PortalException {

		getMDRRuleGroupPermission().check(
			permissionChecker, ruleGroup, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException, SystemException {

		return getMDRRuleGroupPermission().contains(
			permissionChecker, ruleGroupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
		String actionId) {

		return getMDRRuleGroupPermission().contains(
			permissionChecker, ruleGroup, actionId);
	}

	public static MDRRuleGroupPermission getMDRRuleGroupPermission() {
		return _mdrRuleGroupPermission;
	}

	public void setMDRRuleGroupPermission(
		MDRRuleGroupPermission mdrRuleGroupPermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_mdrRuleGroupPermission = mdrRuleGroupPermission;
	}

	private static MDRRuleGroupPermission _mdrRuleGroupPermission;

}