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

package com.liferay.mobile.device.rules.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.base.MDRRuleGroupServiceBaseImpl;
import com.liferay.mobile.device.rules.service.permission.MDRPermission;
import com.liferay.mobile.device.rules.service.permission.MDRRuleGroupPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
@ProviderType
public class MDRRuleGroupServiceImpl extends MDRRuleGroupServiceBaseImpl {

	@Override
	public MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		MDRPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_RULE_GROUP);

		return mdrRuleGroupLocalService.addRuleGroup(
			groupId, nameMap, descriptionMap, serviceContext);
	}

	@Override
	public MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		MDRRuleGroup ruleGroup = getRuleGroup(ruleGroupId);

		MDRRuleGroupPermission.check(
			permissionChecker, ruleGroup, ActionKeys.VIEW);

		MDRPermission.check(
			permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP);

		return mdrRuleGroupLocalService.copyRuleGroup(
			ruleGroup, groupId, serviceContext);
	}

	@Override
	public void deleteRuleGroup(long ruleGroupId) throws PortalException {
		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		MDRRuleGroupPermission.check(
			getPermissionChecker(), ruleGroup, ActionKeys.DELETE);

		mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	@Override
	public MDRRuleGroup fetchRuleGroup(long ruleGroupId)
		throws PortalException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.fetchByPrimaryKey(
			ruleGroupId);

		if (ruleGroup != null) {
			MDRRuleGroupPermission.check(
				getPermissionChecker(), ruleGroup, ActionKeys.VIEW);
		}

		return ruleGroup;
	}

	@Override
	public MDRRuleGroup getRuleGroup(long ruleGroupId) throws PortalException {
		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		MDRRuleGroupPermission.check(
			getPermissionChecker(), ruleGroup, ActionKeys.VIEW);

		return ruleGroup;
	}

	@Override
	public List<MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {

		return mdrRuleGroupPersistence.filterFindByGroupId(
			groupIds, start, end);
	}

	@Override
	public int getRuleGroupsCount(long[] groupIds) {
		return mdrRuleGroupPersistence.filterCountByGroupId(groupIds);
	}

	@Override
	public MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		MDRRuleGroupPermission.check(
			getPermissionChecker(), ruleGroup, ActionKeys.UPDATE);

		return mdrRuleGroupLocalService.updateRuleGroup(
			ruleGroupId, nameMap, descriptionMap, serviceContext);
	}

}