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

import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.base.MDRRuleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 */
@Component(
	property = {
		"json.web.service.context.name=mdr",
		"json.web.service.context.path=MDRRule"
	},
	service = AopService.class
)
public class MDRRuleServiceImpl extends MDRRuleServiceBaseImpl {

	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_mdrRuleGroupModelResourcePermission.check(
			getPermissionChecker(), ruleGroupId, ActionKeys.UPDATE);

		return mdrRuleLocalService.addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_mdrRuleGroupModelResourcePermission.check(
			getPermissionChecker(), ruleGroupId, ActionKeys.UPDATE);

		return mdrRuleLocalService.addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public void deleteRule(long ruleId) throws PortalException {
		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		_mdrRuleGroupModelResourcePermission.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.UPDATE);

		mdrRuleLocalService.deleteRule(rule);
	}

	@Override
	public MDRRule fetchRule(long ruleId) throws PortalException {
		MDRRule rule = mdrRuleLocalService.fetchRule(ruleId);

		if (rule != null) {
			_mdrRuleGroupModelResourcePermission.check(
				getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.VIEW);
		}

		return rule;
	}

	@Override
	public MDRRule getRule(long ruleId) throws PortalException {
		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		_mdrRuleGroupModelResourcePermission.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.VIEW);

		return rule;
	}

	@Override
	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		_mdrRuleGroupModelResourcePermission.check(
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
		throws PortalException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		_mdrRuleGroupModelResourcePermission.check(
			getPermissionChecker(), rule.getRuleGroupId(), ActionKeys.UPDATE);

		return mdrRuleLocalService.updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroup)"
	)
	private ModelResourcePermission<MDRRuleGroup>
		_mdrRuleGroupModelResourcePermission;

}