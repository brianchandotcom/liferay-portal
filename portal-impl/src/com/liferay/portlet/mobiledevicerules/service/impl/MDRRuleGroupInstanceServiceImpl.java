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

package com.liferay.portlet.mobiledevicerules.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.permission.MDRRuleGroupInstancePermissionUtil;
import com.liferay.portlet.mobiledevicerules.permission.MobileDeviceRulesPermissionUtil;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupInstanceServiceBaseImpl;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupInstanceServiceImpl
	extends MDRRuleGroupInstanceServiceBaseImpl {

	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			int priority, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MobileDeviceRulesPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.ADD_RULE_GROUP_INSTANCE);

		return  mdrRuleGroupInstanceLocalService.addRuleGroupInstance(
			groupId, className, classPK, ruleGroupId, priority, serviceContext);
	}

	public void deleteRuleGroupInstance(MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException, SystemException {

		MDRRuleGroupInstancePermissionUtil.check(
			getPermissionChecker(), ruleGroupInstance.getGroupId(),
			ruleGroupInstance, ActionKeys.DELETE);

		mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	public MDRRuleGroupInstance updateRuleGroupInstance(
			long ruleGroupInstanceId, int priority)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstance(
				ruleGroupInstanceId);

		MDRRuleGroupInstancePermissionUtil.check(
			getPermissionChecker(), ruleGroupInstance.getGroupId(),
			ruleGroupInstance.getRuleGroupInstanceId(), ActionKeys.UPDATE);

		return mdrRuleGroupInstanceLocalService.updateRuleGroupInstance(
			ruleGroupInstanceId, priority);
	}

}