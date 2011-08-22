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

	public MDRAction addAction(
			long groupId, long ruleGroupId, long ruleId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return addAction(
			groupId, ruleGroupId, ruleId, nameMap, descriptionMap, type,
			typeSettings, serviceContext);
	}

	public MDRAction addAction(
			long groupId, long ruleGroupId, long ruleId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		long actionId = counterLocalService.increment();

		MDRAction action = mdrActionLocalService.createMDRAction(actionId);

		action.setUuid(serviceContext.getUuid());
		action.setCompanyId(serviceContext.getCompanyId());
		action.setCreateDate(serviceContext.getCreateDate(now));
		action.setModifiedDate(serviceContext.getModifiedDate(now));
		action.setUserId(serviceContext.getUserId());
		action.setUserName(user.getFullName());
		action.setRuleGroupId(ruleGroupId);
		action.setRuleId(ruleId);
		action.setGroupId(groupId);
		action.setDescriptionMap(descriptionMap);
		action.setNameMap(nameMap);
		action.setType(type);
		action.setTypeSettings(typeSettings);

		return updateMDRAction(action, false);
	}

	public MDRAction cloneAction(
			long actionId, long targetRuleId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRAction action = getMDRAction(actionId);

		return cloneAction(action, targetRuleId, serviceContext);
	}

	public MDRAction cloneAction(
			MDRAction action, long targetRuleId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRule rule = mdrRuleLocalService.getMDRRule(targetRuleId);

		return addAction(
			rule.getGroupId(), rule.getRuleGroupId(), rule.getRuleId(),
			action.getNameMap(), action.getDescriptionMap(),
			action.getType(), action.getTypeSettings(), serviceContext);
	}

	public void deleteActions(long ruleId) throws SystemException {
		List<MDRAction> actions = getActions(ruleId);

		for (MDRAction action : actions) {
			deleteMDRAction(action);
		}
	}

	public MDRAction fetchAction(long actionId) throws SystemException {
		return mdrActionPersistence.fetchByPrimaryKey(actionId);
	}

	public List<MDRAction> getActions(long ruleId) throws SystemException {
		return mdrActionPersistence.findByRuleId(ruleId);
	}

	public List<MDRAction> getActions(long ruleId, int start, int end)
		throws SystemException {

		return mdrActionPersistence.findByRuleId(ruleId, start, end);
	}

	public int getActionsCount(long ruleId) throws SystemException {
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

		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		action.setNameMap(nameMap);
		action.setDescriptionMap(descriptionMap);
		action.setType(type);
		action.setTypeSettings(typeSettings);

		mdrActionPersistence.update(action, false);

		return action;
	}

}