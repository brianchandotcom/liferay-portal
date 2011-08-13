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
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.service.base.MDRActionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRActionLocalServiceImpl extends MDRActionLocalServiceBaseImpl {

	public MDRAction addMDRAction(
			long groupId, long ruleGroupId, long ruleId,
            Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
            String type, UnicodeProperties typeSettingsProperties,
            ServiceContext serviceContext)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return addMDRAction(
			groupId, ruleGroupId, ruleId, nameMap, descriptionMap, type,
            typeSettings, serviceContext);
	}

	public MDRAction addMDRAction(
			long groupId, long ruleGroupId, long ruleId,
            Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
            String type, String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(serviceContext.getUserId());
        Date now = new Date();

		long actionId = counterLocalService.increment();

        MDRAction mdrAction = mdrActionLocalService.createMDRAction(actionId);

        mdrAction.setUuid(serviceContext.getUuid());
        mdrAction.setCompanyId(serviceContext.getCompanyId());
        mdrAction.setCreateDate(serviceContext.getCreateDate(now));
        mdrAction.setModifiedDate(serviceContext.getModifiedDate(now));
        mdrAction.setUserId(serviceContext.getUserId());
        mdrAction.setUserName(user.getFullName());
        mdrAction.setRuleGroupId(ruleGroupId);
        mdrAction.setRuleId(ruleId);
        mdrAction.setGroupId(groupId);
        mdrAction.setDescriptionMap(descriptionMap);
        mdrAction.setNameMap(nameMap);
        mdrAction.setType(type);
        mdrAction.setTypeSettings(typeSettings);

		return updateMDRAction(mdrAction, false);
	}

	public MDRAction cloneMDRAction(
            long actionId, long targetRuleId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRAction mdrAction = getMDRAction(actionId);

		return cloneMDRAction(mdrAction, targetRuleId, serviceContext);
	}

	public MDRAction cloneMDRAction(
            MDRAction mdrAction, long targetRuleId,
            ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule mdrRule = mdrRuleLocalService.getMDRRule(targetRuleId);

		return addMDRAction(
            mdrRule.getGroupId(), mdrRule.getRuleGroupId(), mdrRule.getRuleId(),
            mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
            mdrAction.getType(), mdrAction.getTypeSettings(), serviceContext);
	}

	public void deleteMDRActions(long ruleId) throws SystemException {
		List<MDRAction> mdrActions = getMDRActions(ruleId);

		for (MDRAction mDRAction : mdrActions) {
			deleteMDRAction(mDRAction);
		}
	}

	public MDRAction fetchMDRAction(long actionId) throws SystemException {
		return mdrActionPersistence.fetchByPrimaryKey(actionId);
	}

	public List<MDRAction> getMDRActions(long ruleId) throws SystemException {
		return mdrActionPersistence.findByRuleId(ruleId);
	}

	public List<MDRAction> getMDRActions(long ruleId, int start, int end)
		throws SystemException {

		return mdrActionPersistence.findByRuleId(ruleId, start, end);
	}

	public int getMDRActionsCount(long ruleId) throws SystemException {
		return mdrActionPersistence.countByRuleId(ruleId);
	}

    public MDRAction updateAction(
            long actionId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, String type,
            UnicodeProperties typeSettingsProperties,
            ServiceContext serviceContext)
            throws PortalException, SystemException {

        String typeSettings = typeSettingsProperties.toString();

        return updateAction(
                actionId, nameMap, descriptionMap, type, typeSettings,
                serviceContext);
    }

    public MDRAction updateAction(
            long actionId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, String type,
            String typeSettings, ServiceContext serviceContext)
            throws PortalException, SystemException {

        MDRAction mdrAction = mdrActionPersistence.findByPrimaryKey(actionId);

        mdrAction.setNameMap(nameMap);
        mdrAction.setDescriptionMap(descriptionMap);
        mdrAction.setType(type);
        mdrAction.setTypeSettings(typeSettings);

        return mdrAction;
    }

}