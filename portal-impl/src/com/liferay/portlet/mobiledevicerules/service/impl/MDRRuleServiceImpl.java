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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleServiceBaseImpl;
import com.liferay.portlet.mobiledevicerules.service.permission.MDRRuleGroupPermissionUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRRuleServiceImpl extends MDRRuleServiceBaseImpl {

	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), ruleGroupId, ActionKeys.UPDATE);

		return mdrRuleLocalService.addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), ruleGroupId, ActionKeys.UPDATE);

		return mdrRuleLocalService.addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public void deleteRule(long ruleId)
		throws PortalException, SystemException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.UPDATE);

		mdrRuleLocalService.deleteRule(rule);
	}

	@Override
	public MDRRule fetchRule(long ruleId)
		throws PortalException, SystemException {

		MDRRule rule = mdrRuleLocalService.fetchRule(ruleId);

		if (rule != null) {
			MDRRuleGroupPermissionUtil.check(
				getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.VIEW);
		}

		return rule;
	}

	@Override
	public MDRRule getRule(long ruleId)
		throws PortalException, SystemException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.VIEW);

		return rule;
	}

	@Override
	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.UPDATE);

		return mdrRuleLocalService.updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		MDRRuleGroupPermissionUtil.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.UPDATE);

		return mdrRuleLocalService.updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

}