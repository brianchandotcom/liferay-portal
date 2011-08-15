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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
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

	public MDRRule addRule(
			long groupId, long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return addRule(
			groupId, ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public MDRRule addRule(
			long groupId, long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		long ruleId = counterLocalService.increment();

		MDRRule rule = mdrRulePersistence.create(ruleId);

		rule.setUuid(serviceContext.getUuid());
		rule.setCompanyId(serviceContext.getCompanyId());
		rule.setCreateDate(serviceContext.getCreateDate(now));
		rule.setModifiedDate(serviceContext.getModifiedDate(now));
		rule.setUserId(user.getUserId());
		rule.setUserName(user.getFullName());
		rule.setDescriptionMap(descriptionMap);
		rule.setGroupId(groupId);
		rule.setNameMap(nameMap);
		rule.setRuleGroupId(ruleGroupId);
		rule.setType(type);
		rule.setTypeSettings(typeSettings);

		return updateMDRRule(rule, false);
	}

	public MDRRule cloneRule(
			long ruleId, long targetRuleGroupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule rule = getMDRRule(ruleId);

		return cloneRule(rule, targetRuleGroupId, serviceContext);
	}

	public MDRRule cloneRule(
			MDRRule rule, long targetRuleGroupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupLocalService.getMDRRuleGroup(
			targetRuleGroupId);

		MDRRule newRule = addRule(
			ruleGroup.getGroupId(), ruleGroup.getRuleGroupId(),
			rule.getNameMap(), rule.getDescriptionMap(), rule.getType(),
			rule.getTypeSettings(), serviceContext);

		for (MDRAction action : rule.getActions()) {
			serviceContext.setUuid(PortalUUIDUtil.generate());

			mdrActionLocalService.cloneAction(
				action, newRule.getRuleId(), serviceContext);
		}

		return newRule;
	}

	public void deleteRule(MDRRule rule)
		throws PortalException, SystemException {

		mdrActionLocalService.deleteActions(rule.getRuleId());

		deleteMDRRule(rule);
	}

	public void deleteRule(long ruleId)
		throws PortalException, SystemException {

		MDRRule rule = fetchRule(ruleId);

		if (rule != null) {
			deleteRule(rule);
		}
	}

	public void deleteRules(long ruleGroupId)
		throws PortalException, SystemException {

		List<MDRRule> rules = getRules(ruleGroupId);

		for (MDRRule mdrRule : rules) {
			deleteRule(mdrRule);
		}
	}

	public MDRRule fetchRule(long deviceRuleId) throws SystemException {
		return mdrRulePersistence.fetchByPrimaryKey(deviceRuleId);
	}

	public List<MDRRule> getRules(long ruleGroupId) throws SystemException {
		return mdrRulePersistence.findByRuleGroupId(ruleGroupId);
	}

	public List<MDRRule> getRules(long deviceRuleGroupId, int start, int end)
		throws SystemException {

		return mdrRulePersistence.findByRuleGroupId(
			deviceRuleGroupId, start, end);
	}

	public int getRulesCount(long ruleGroupId) throws SystemException {
		return mdrRulePersistence.countByRuleGroupId(ruleGroupId);
	}

	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public MDRRule updateRule(
			long ruleId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule rule = mdrRulePersistence.findByPrimaryKey(ruleId);

		rule.setNameMap(nameMap);
		rule.setDescriptionMap(descriptionMap);
		rule.setType(type);
		rule.setTypeSettings(typeSettings);
		rule.setModifiedDate(serviceContext.getModifiedDate(null));

		mdrRulePersistence.update(rule, false);

		return rule;
	}

}