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

	public MDRRule addMDRRule(
			long groupId, long ruleGroupId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, String type,
            UnicodeProperties typeSettingsProperties,
            ServiceContext serviceContext)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return addMDRRule(
            groupId, ruleGroupId, nameMap, descriptionMap, type, typeSettings,
            serviceContext);
	}

	public MDRRule addMDRRule(
			long groupId, long ruleGroupId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, String type,
            String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

        User user = userLocalService.getUser(serviceContext.getUserId());
        Date now = new Date();

		long ruleId = counterLocalService.increment();

		MDRRule mdrRule = mdrRulePersistence.create(ruleId);

        mdrRule.setUuid(serviceContext.getUuid());
        mdrRule.setCompanyId(serviceContext.getCompanyId());
        mdrRule.setCreateDate(serviceContext.getCreateDate(now));
        mdrRule.setModifiedDate(serviceContext.getModifiedDate(now));
        mdrRule.setUserId(user.getUserId());
        mdrRule.setUserName(user.getFullName());
        mdrRule.setDescriptionMap(descriptionMap);
        mdrRule.setGroupId(groupId);
        mdrRule.setNameMap(nameMap);
        mdrRule.setRuleGroupId(ruleGroupId);
        mdrRule.setType(type);
        mdrRule.setTypeSettings(typeSettings);

		return updateMDRRule(mdrRule, false);
	}

	public MDRRule cloneMDRRule(
            long ruleId, long targetRuleGroupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule mdrRule = getMDRRule(ruleId);

		return cloneMDRRule(mdrRule, targetRuleGroupId, serviceContext);
	}

	public MDRRule cloneMDRRule(
            MDRRule mdrRule, long targetRuleGroupId,
            ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup mdrRuleGroup = mdrRuleGroupLocalService.getMDRRuleGroup(
            targetRuleGroupId);

		MDRRule newMdrRule = addMDRRule(
            mdrRuleGroup.getGroupId(), mdrRuleGroup.getRuleGroupId(),
            mdrRule.getNameMap(), mdrRule.getDescriptionMap(),
            mdrRule.getType(), mdrRule.getTypeSettings(), serviceContext);

        for (MDRAction mdrAction : mdrRule.getActions()) {
            serviceContext.setUuid(PortalUUIDUtil.generate());

			mdrActionLocalService.cloneMDRAction(
                mdrAction, newMdrRule.getRuleId(), serviceContext);
		}

		return newMdrRule;
	}

	@Override
	public void deleteMDRRule(MDRRule mdrRule)
        throws PortalException, SystemException {

		mdrActionLocalService.deleteMDRAction(mdrRule.getRuleId());

		super.deleteMDRRule(mdrRule);
	}

	@Override
	public void deleteMDRRule(long ruleId)
        throws PortalException, SystemException {

		MDRRule mdrRule = fetchMDRRule(ruleId);

		if (mdrRule != null) {
			deleteMDRRule(mdrRule);
		}
	}

	public void deleteMDRRules(long ruleGroupId)
        throws PortalException, SystemException {

		List<MDRRule> mdrRules = getMDRRules(ruleGroupId);

		for (MDRRule mdrRule : mdrRules) {
			deleteMDRRule(mdrRule);
		}
	}

	public MDRRule fetchMDRRule(long deviceRuleId) throws SystemException {
		return mdrRulePersistence.fetchByPrimaryKey(deviceRuleId);
	}

	public List<MDRRule> getMDRRules(long ruleGroupId) throws SystemException {
		return mdrRulePersistence.findByRuleGroupId(ruleGroupId);
	}

	public List<MDRRule> getMDRRules(long deviceRuleGroupId, int start, int end)
		throws SystemException {

		return mdrRulePersistence.findByRuleGroupId(
            deviceRuleGroupId, start, end);
	}

	public int getMDRRulesCount(long ruleGroupId) throws SystemException {
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

        MDRRule mdrRule = mdrRulePersistence.findByPrimaryKey(ruleId);

        mdrRule.setNameMap(nameMap);
        mdrRule.setDescriptionMap(descriptionMap);
        mdrRule.setType(type);
        mdrRule.setTypeSettings(typeSettings);
        mdrRule.setModifiedDate(serviceContext.getModifiedDate(null));

        mdrRulePersistence.update(mdrRule, false);

        return mdrRule;
    }
}