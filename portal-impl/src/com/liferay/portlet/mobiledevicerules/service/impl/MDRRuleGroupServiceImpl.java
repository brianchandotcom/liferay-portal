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
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.permission.MDRRuleGroupPermissionUtil;
import com.liferay.portlet.mobiledevicerules.permission.MobileDeviceRulesPermissionUtil;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupServiceImpl extends MDRRuleGroupServiceBaseImpl {

	public MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MobileDeviceRulesPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_RULE_GROUP);

		return mdrRuleGroupLocalService.addRuleGroup(
			groupId, nameMap, descriptionMap, serviceContext);
	}

	public MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup deviceRuleGroup = getRuleGroup(ruleGroupId);

		return copyRuleGroup(deviceRuleGroup, groupId, serviceContext);
	}

	public MDRRuleGroup copyRuleGroup(
			MDRRuleGroup ruleGroup, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), groupId, ruleGroup, ActionKeys.VIEW);

		MobileDeviceRulesPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_RULE_GROUP);

		return mdrRuleGroupLocalService.copyRuleGroup(
			ruleGroup, groupId, serviceContext);
	}

	public void deleteRuleGroup(long ruleGroupId)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupLocalService.fetchRuleGroup(
			ruleGroupId);

		if (ruleGroup != null) {
			deleteRuleGroup(ruleGroup);
		}
	}

	public void deleteRuleGroup(MDRRuleGroup ruleGroup)
		throws PortalException, SystemException {

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), ruleGroup.getGroupId(), ruleGroup,
			ActionKeys.DELETE);

		mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	public MDRRuleGroup fetchRuleGroup(long ruleGroupId)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupLocalService.fetchRuleGroup(
			ruleGroupId);

		if (ruleGroup != null) {
			MDRRuleGroupPermissionUtil.check(
				getPermissionChecker(), ruleGroup.getGroupId(), ruleGroupId,
				ActionKeys.VIEW);
		}

		return ruleGroup;
	}

	public List<MDRRuleGroup> findByGroupId(long groupId)
		throws SystemException {

		return mdrRuleGroupPersistence.filterFindByGroupId(groupId);
	}

	public List<MDRRuleGroup> findByGroupId(long groupId, int start, int end)
		throws SystemException {

		return mdrRuleGroupPersistence.filterFindByGroupId(groupId, start, end);
	}

	public int filterCountByGroupId(long groupId) throws SystemException {
		return mdrRuleGroupPersistence.filterCountByGroupId(groupId);
	}

	public MDRRuleGroup getRuleGroup(long ruleGroupId)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupLocalService.getMDRRuleGroup(
			ruleGroupId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), ruleGroup.getGroupId(), ruleGroupId,
			ActionKeys.VIEW);

		return ruleGroup;
	}

	public MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupLocalService.getMDRRuleGroup(
			ruleGroupId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), ruleGroup.getGroupId(),
			ruleGroup.getRuleGroupId(), ActionKeys.UPDATE);

		return mdrRuleGroupLocalService.updateRuleGroup(
			ruleGroupId, nameMap, descriptionMap, serviceContext);
	}

}