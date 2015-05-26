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

package com.liferay.portlet.mobiledevicerules.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRRuleLocalServiceImpl extends MDRRuleLocalServiceBaseImpl {

	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);
		Date now = new Date();

		long ruleId = counterLocalService.increment();

		MDRRule rule = mdrRulePersistence.create(ruleId);

		rule.setUuid(serviceContext.getUuid());
		rule.setGroupId(ruleGroup.getGroupId());
		rule.setCompanyId(serviceContext.getCompanyId());
		rule.setUserId(user.getUserId());
		rule.setUserName(user.getFullName());
		rule.setRuleGroupId(ruleGroupId);
		rule.setNameMap(nameMap);
		rule.setDescriptionMap(descriptionMap);
		rule.setType(type);
		rule.setTypeSettings(typeSettings);

		rule = updateMDRRule(rule);

		ruleGroup.setModifiedDate(now);

		mdrRuleGroupPersistence.update(ruleGroup);

		return rule;
	}

	@Override
	public MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return addRule(
			ruleGroupId, nameMap, descriptionMap, type,
			typeSettingsProperties.toString(), serviceContext);
	}

	@Override
	public MDRRule copyRule(
			long ruleId, long ruleGroupId, ServiceContext serviceContext)
		throws PortalException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		return copyRule(rule, ruleGroupId, serviceContext);
	}

	@Override
	public MDRRule copyRule(
			MDRRule rule, long ruleGroupId, ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		MDRRule newRule = addRule(
			ruleGroup.getRuleGroupId(), rule.getNameMap(),
			rule.getDescriptionMap(), rule.getType(), rule.getTypeSettings(),
			serviceContext);

		return newRule;
	}

	@Override
	public void deleteRule(long ruleId) {
		MDRRule rule = mdrRulePersistence.fetchByPrimaryKey(ruleId);

		if (rule != null) {
			mdrRuleLocalService.deleteRule(rule);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteRule(MDRRule rule) {
		mdrRulePersistence.remove(rule);

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.fetchByPrimaryKey(
			rule.getRuleGroupId());

		if (ruleGroup != null) {
			ruleGroup.setModifiedDate(new Date());

			mdrRuleGroupPersistence.update(ruleGroup);
		}
	}

	@Override
	public void deleteRules(long ruleGroupId) {
		List<MDRRule> rules = mdrRulePersistence.findByRuleGroupId(ruleGroupId);

		for (MDRRule rule : rules) {
			mdrRuleLocalService.deleteRule(rule);
		}
	}

	@Override
	public MDRRule fetchRule(long ruleId) {
		return mdrRulePersistence.fetchByPrimaryKey(ruleId);
	}

	@Override
	public MDRRule getRule(long ruleId) throws PortalException {
		return mdrRulePersistence.findByPrimaryKey(ruleId);
	}

	@Override
	public List<MDRRule> getRules(long ruleGroupId) {
		return mdrRulePersistence.findByRuleGroupId(ruleGroupId);
	}

	@Override
	public List<MDRRule> getRules(long ruleGroupId, int start, int end) {
		return mdrRulePersistence.findByRuleGroupId(ruleGroupId, start, end);
	}

	@Override
	public int getRulesCount(long ruleGroupId) {
		return mdrRulePersistence.countByRuleGroupId(ruleGroupId);
	}

	@Override
	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		rule.setNameMap(nameMap);
		rule.setDescriptionMap(descriptionMap);
		rule.setType(type);
		rule.setTypeSettings(typeSettings);

		mdrRulePersistence.update(rule);

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			rule.getRuleGroupId());

		ruleGroup.setModifiedDate(serviceContext.getModifiedDate(null));

		mdrRuleGroupPersistence.update(ruleGroup);

		return rule;
	}

	@Override
	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return updateRule(
			ruleId, nameMap, descriptionMap, type,
			typeSettingsProperties.toString(), serviceContext);
	}

}