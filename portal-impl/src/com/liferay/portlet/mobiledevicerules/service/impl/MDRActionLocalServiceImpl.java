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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
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
			long groupId, String className, long classPK, long ruleGroupId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String typeSettings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		long classNameId = PortalUtil.getClassNameId(className);
		long actionId = counterLocalService.increment();

		MDRAction action = mdrActionLocalService.createMDRAction(actionId);

		action.setUuid(serviceContext.getUuid());
		action.setGroupId(groupId);
		action.setCompanyId(serviceContext.getCompanyId());
		action.setCreateDate(serviceContext.getCreateDate(now));
		action.setModifiedDate(serviceContext.getModifiedDate(now));
		action.setUserId(serviceContext.getUserId());
		action.setUserName(user.getFullName());
		action.setClassNameId(classNameId);
		action.setClassPK(classPK);
		action.setRuleGroupId(ruleGroupId);
		action.setNameMap(nameMap);
		action.setDescriptionMap(descriptionMap);
		action.setType(type);
		action.setTypeSettings(typeSettings);

		return updateMDRAction(action, false);
	}

	public MDRAction addAction(
			long groupId, String className, long classPK, long ruleGroupId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addAction(
			groupId, className, classPK, ruleGroupId, nameMap, descriptionMap,
			type, typeSettingsProperties.toString(), serviceContext);
	}

	public void deleteAction(long actionId) throws SystemException {
		MDRAction action = mdrActionPersistence.fetchByPrimaryKey(actionId);

		if (action != null) {
			deleteMDRAction(action);
		}
	}

	public void deleteActions(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<MDRAction> actions = mdrActionPersistence.findByC_C(
			classNameId, classPK);

		for (MDRAction action : actions) {
			deleteMDRAction(action);
		}
	}

	public MDRAction fetchAction(long actionId) throws SystemException {
		return mdrActionPersistence.fetchByPrimaryKey(actionId);
	}

	public List<MDRAction> getActions(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrActionPersistence.findByC_C(classNameId, classPK);
	}

	public List<MDRAction> getActions(
			String className, long classPK, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mdrActionPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	public int getActionsCount(String className, long classPK) throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		return mdrActionPersistence.countByC_C(classNameId, classPK);
	}

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

		mdrActionPersistence.update(action, false);

		return action;
	}

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