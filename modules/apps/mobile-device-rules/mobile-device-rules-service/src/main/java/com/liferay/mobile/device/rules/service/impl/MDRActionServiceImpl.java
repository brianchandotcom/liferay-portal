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

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.base.MDRActionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
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
		"json.web.service.context.path=MDRAction"
	},
	service = AopService.class
)
public class MDRActionServiceImpl extends MDRActionServiceBaseImpl {

	@Override
	public MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_mdrRuleGroupInstanceModelResourcePermission.check(
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

		_mdrRuleGroupInstanceModelResourcePermission.check(
			getPermissionChecker(), ruleGroupInstanceId, ActionKeys.UPDATE);

		return mdrActionLocalService.addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type,
			typeSettingsProperties, serviceContext);
	}

	@Override
	public void deleteAction(long actionId) throws PortalException {
		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		_mdrRuleGroupInstanceModelResourcePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		mdrActionLocalService.deleteAction(action);
	}

	@Override
	public MDRAction fetchAction(long actionId) throws PortalException {
		MDRAction action = mdrActionLocalService.fetchAction(actionId);

		if (action != null) {
			_mdrRuleGroupInstanceModelResourcePermission.check(
				getPermissionChecker(), action.getRuleGroupInstanceId(),
				ActionKeys.VIEW);
		}

		return action;
	}

	@Override
	public MDRAction getAction(long actionId) throws PortalException {
		MDRAction action = mdrActionPersistence.findByPrimaryKey(actionId);

		_mdrRuleGroupInstanceModelResourcePermission.check(
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

		_mdrRuleGroupInstanceModelResourcePermission.check(
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

		_mdrRuleGroupInstanceModelResourcePermission.check(
			getPermissionChecker(), action.getRuleGroupInstanceId(),
			ActionKeys.UPDATE);

		return mdrActionLocalService.updateAction(
			actionId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroupInstance)"
	)
	private ModelResourcePermission<MDRRuleGroupInstance>
		_mdrRuleGroupInstanceModelResourcePermission;

}