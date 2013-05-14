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

package com.liferay.portlet.mobiledevicerules.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupInstanceServiceBaseImpl;
import com.liferay.portlet.mobiledevicerules.service.permission.MDRPermissionUtil;
import com.liferay.portlet.mobiledevicerules.service.permission.MDRRuleGroupInstancePermissionUtil;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupInstanceServiceImpl
	extends MDRRuleGroupInstanceServiceBaseImpl {

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			int priority, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.ADD_RULE_GROUP_INSTANCE);

		return mdrRuleGroupInstanceLocalService.addRuleGroupInstance(
			groupId, className, classPK, ruleGroupId, priority, serviceContext);
	}

	@Override
	public MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRPermissionUtil.check(
			getPermissionChecker(), groupId,
			ActionKeys.ADD_RULE_GROUP_INSTANCE);

		return mdrRuleGroupInstanceLocalService.addRuleGroupInstance(
			groupId, className, classPK, ruleGroupId, serviceContext);
	}

	@Override
	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);

		MDRRuleGroupInstancePermissionUtil.check(
			getPermissionChecker(), ruleGroupInstance, ActionKeys.DELETE);

		mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	@Override
	public List<MDRRuleGroupInstance> getRuleGroupInstances(
			String className, long classPK, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		long groupId = getGroupId(className, classPK);
		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getRuleGroupInstancesCount(String className, long classPK)
		throws SystemException {

		long groupId = getGroupId(className, classPK);
		long classNameId = PortalUtil.getClassNameId(className);

		return mdrRuleGroupInstancePersistence.filterCountByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public MDRRuleGroupInstance updateRuleGroupInstance(
			long ruleGroupInstanceId, int priority)
		throws PortalException, SystemException {

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);

		MDRRuleGroupInstancePermissionUtil.check(
			getPermissionChecker(), ruleGroupInstance.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		return mdrRuleGroupInstanceLocalService.updateRuleGroupInstance(
			ruleGroupInstanceId, priority);
	}

	protected long getGroupId(String className, long classPK)
		throws SystemException {

		long groupId = 0;

		if (className.equals(Layout.class.getName())) {
			Layout layout = layoutPersistence.fetchByPrimaryKey(classPK);

			if (layout != null) {
				groupId = layout.getGroupId();
			}
		}
		else if (className.equals(LayoutSet.class.getName())) {
			LayoutSet layoutSet = layoutSetPersistence.fetchByPrimaryKey(
				classPK);

			if (layoutSet != null) {
				groupId = layoutSet.getGroupId();
			}
		}

		return groupId;
	}

}