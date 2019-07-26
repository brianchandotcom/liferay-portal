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

package com.liferay.mobile.device.rules.util.test;

import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.rule.RuleGroupProcessorUtil;
import com.liferay.mobile.device.rules.rule.RuleHandler;
import com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class MDRTestUtil {

	public static MDRAction addAction(long ruleGroupInstanceId)
		throws Exception {

		Collection<ActionHandler> actionHandlers =
			ActionHandlerManagerUtil.getActionHandlers();

		Object[] actionHandlersArray = actionHandlers.toArray();

		ActionHandler actionHandler = (ActionHandler)actionHandlersArray[0];

		return addAction(
			ruleGroupInstanceId, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), actionHandler.getType(),
			null);
	}

	public static MDRAction addAction(
			long ruleGroupInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings)
		throws Exception {

		return MDRActionLocalServiceUtil.addAction(
			ruleGroupInstanceId, nameMap, descriptionMap, type, typeSettings,
			ServiceContextTestUtil.getServiceContext());
	}

	public static MDRRule addRule(long ruleGroupId) throws Exception {
		Collection<RuleHandler> ruleHandlers =
			RuleGroupProcessorUtil.getRuleHandlers();

		Object[] ruleHandlersArray = ruleHandlers.toArray();

		RuleHandler ruleHandler = (RuleHandler)ruleHandlersArray[0];

		return addRule(
			ruleGroupId, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), ruleHandler.getType(),
			null);
	}

	public static MDRRule addRule(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type,
			String typeSettings)
		throws Exception {

		return MDRRuleLocalServiceUtil.addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			ServiceContextTestUtil.getServiceContext());
	}

	public static MDRRuleGroup addRuleGroup(long groupId) throws Exception {
		return addRuleGroup(
			groupId, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

	public static MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws Exception {

		return MDRRuleGroupLocalServiceUtil.addRuleGroup(
			groupId, nameMap, descriptionMap,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, long ruleGroupId)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(groupId);

		return addRuleGroupInstance(
			groupId, Layout.class.getName(), layout.getPlid(), ruleGroupId);
	}

	public static MDRRuleGroupInstance addRuleGroupInstance(
			long groupId, String className, long classPK, long ruleGroupId)
		throws Exception {

		return MDRRuleGroupInstanceLocalServiceUtil.addRuleGroupInstance(
			groupId, className, classPK, ruleGroupId,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}