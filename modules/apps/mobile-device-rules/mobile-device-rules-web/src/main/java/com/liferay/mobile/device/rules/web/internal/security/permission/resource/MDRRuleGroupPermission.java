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

package com.liferay.mobile.device.rules.web.internal.security.permission.resource;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class MDRRuleGroupPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long ruleGroupId,
			String actionId)
		throws PortalException {

		return _mdrRuleGroupModelResourcePermission.contains(
			permissionChecker, ruleGroupId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MDRRuleGroup ruleGroup,
			String actionId)
		throws PortalException {

		return _mdrRuleGroupModelResourcePermission.contains(
			permissionChecker, ruleGroup, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroup)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<MDRRuleGroup> modelResourcePermission) {

		_mdrRuleGroupModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<MDRRuleGroup>
		_mdrRuleGroupModelResourcePermission;

}