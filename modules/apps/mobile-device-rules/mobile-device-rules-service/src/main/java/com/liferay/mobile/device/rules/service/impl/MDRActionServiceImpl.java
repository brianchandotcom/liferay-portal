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

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.service.base.MDRActionServiceBaseImpl;
import com.liferay.mobile.device.rules.service.permission.MDRRuleGroupInstancePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
@ProviderType
public class MDRActionServiceImpl extends MDRActionServiceBaseImpl {

	@Override
	public MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), ruleGroupInstanceId, ActionKeys.UPDATE);

		return mdrActionLocalService.addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), ruleGroupInstanceId, ActionKeys.UPDATE);

		return mdrActionLocalService.addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type,
			typeSettingsProperties, serviceContext);
	}

	@Override
	public void deleteAction(long actionId) throws PortalException {
		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		mdrActionLocalService.deleteAction(action);
	}

	@Override
	public MDRAction fetchAction(long actionId) throws PortalException {
		MDRAction action = mdrActionLocalService.fetchAction(actionId);

		if (action != null) {
			MDRRuleGroupInstancePermission.check(
				getPermissionChecker(), action.getRuleGroupInstanceId(),
				ActionKeys.VIEW);
		}

		return action;
	}

	@Override
	public MDRAction getAction(long actionId) throws PortalException {
		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.VIEW);

		return action;
	}

	@Override
	public MDRAction updateAction(
			long actionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		return mdrActionLocalService.updateAction(
			actionId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	@Override
	public MDRAction updateAction(
			long actionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		MDRRuleGroupInstancePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		return mdrActionLocalService.updateAction(
			actionId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

}