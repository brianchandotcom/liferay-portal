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
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.base.MDRActionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRActionLocalServiceImpl extends MDRActionLocalServiceBaseImpl {

	@Override
	public MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				ruleGroupInstanceId);
		Date now = new Date();

		long actionId = counterLocalService.increment();

		MDRAction action = mdrActionLocalService.createMDRAction(actionId);

		action.setUuid(serviceContext.getUuid());
		action.setGroupId(ruleGroupInstance.getGroupId());
		action.setCompanyId(serviceContext.getCompanyId());
		action.setCreateDate(serviceContext.getCreateDate(now));
		action.setModifiedDate(serviceContext.getModifiedDate(now));
		action.setUserId(serviceContext.getUserId());
		action.setUserName(user.getFullName());
		action.setClassNameId(ruleGroupInstance.getClassNameId());
		action.setClassPK(ruleGroupInstance.getClassPK());
		action.setRuleGroupInstanceId(ruleGroupInstanceId);
		action.setNameMap(nameMap);
		action.setDescriptionMap(descriptionMap);
		action.setType(type);
		action.setTypeSettings(typeSettings);

		action = updateMDRAction(action);

		ruleGroupInstance.setModifiedDate(now);

		mdrRuleGroupInstancePersistence.update(ruleGroupInstance);

		return action;
	}

	@Override
	public MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type,
			typeSettingsProperties.toString(), serviceContext);
	}

	@Override
	public void deleteAction(long actionId)
		throws PortalException, SystemException {

		MDRAction action = mdrActionPersistence.fetchByPrimaryKey(actionId);

		if (action != null) {
			deleteAction(action);
		}
	}

	@Override
	public void deleteAction(MDRAction action)
		throws PortalException, SystemException {

		mdrActionPersistence.remove(action);

		systemEventLocalService.addSystemEvent(
			0, action.getGroupId(), MDRAction.class.getName(),
			action.getActionId(), action.getUuid(), null,
			SystemEventConstants.TYPE_DELETE, null);

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.fetchByPrimaryKey(
				action.getRuleGroupInstanceId());

		if (ruleGroupInstance != null) {
			ruleGroupInstance.setModifiedDate(new Date());

			mdrRuleGroupInstancePersistence.update(ruleGroupInstance);
		}
	}

	@Override
	public void deleteActions(long ruleGroupInstanceId)
		throws PortalException, SystemException {

		List<MDRAction> actions =
			mdrActionPersistence.findByRuleGroupInstanceId(ruleGroupInstanceId);

		for (MDRAction action : actions) {
			deleteAction(action);
		}
	}

	@Override
	public MDRAction fetchAction(long actionId) throws SystemException {
		return mdrActionPersistence.fetchByPrimaryKey(actionId);
	}

	@Override
	public MDRAction getAction(long actionId)
		throws PortalException, SystemException {

		return mdrActionPersistence.findByPrimaryKey(actionId);
	}

	@Override
	public List<MDRAction> getActions(long ruleGroupInstanceId)
		throws SystemException {

		return mdrActionPersistence.findByRuleGroupInstanceId(
			ruleGroupInstanceId);
	}

	@Override
	public List<MDRAction> getActions(
			long ruleGroupInstanceId, int start, int end)
		throws SystemException {

		return mdrActionPersistence.findByRuleGroupInstanceId(
			ruleGroupInstanceId, start, end);
	}

	@Override
	public int getActionsCount(long ruleGroupInstanceId)
		throws SystemException {

		return mdrActionPersistence.countByRuleGroupInstanceId(
			ruleGroupInstanceId);
	}

	@Override
	public MDRAction updateAction(
			long actionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		action.setModifiedDate(serviceContext.getModifiedDate(null));
		action.setNameMap(nameMap);
		action.setDescriptionMap(descriptionMap);
		action.setType(type);
		action.setTypeSettings(typeSettings);

		mdrActionPersistence.update(action);

		MDRRuleGroupInstance ruleGroupInstance =
			mdrRuleGroupInstancePersistence.findByPrimaryKey(
				action.getRuleGroupInstanceId());

		ruleGroupInstance.setModifiedDate(serviceContext.getModifiedDate(null));

		mdrRuleGroupInstancePersistence.update(ruleGroupInstance);

		return action;
	}

	@Override
	public MDRAction updateAction(
			long actionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateAction(
			actionId, nameMap, descriptionMap, type,
			typeSettingsProperties.toString(), serviceContext);
	}

}