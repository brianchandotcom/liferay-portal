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

package com.liferay.mobile.device.rules.rule;

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Collection;

/**
 * @author Edward Han
 */
public class RuleGroupProcessorUtil {

	public static MDRRuleGroupInstance evaluateRuleGroups(
		ThemeDisplay themeDisplay) {

		return getRuleGroupProcessor().evaluateRuleGroups(themeDisplay);
	}

	public static RuleGroupProcessor getRuleGroupProcessor() {
		PortalRuntimePermission.checkGetBeanProperty(
			RuleGroupProcessorUtil.class);

		return _instance._serviceTracker.getService();
	}

	public static RuleHandler getRuleHandler(String ruleType) {
		return getRuleGroupProcessor().getRuleHandler(ruleType);
	}

	public static Collection<RuleHandler> getRuleHandlers() {
		return getRuleGroupProcessor().getRuleHandlers();
	}

	public static Collection<String> getRuleHandlerTypes() {
		return getRuleGroupProcessor().getRuleHandlerTypes();
	}

	public static void registerRuleHandler(RuleHandler ruleHandler) {
		getRuleGroupProcessor().registerRuleHandler(ruleHandler);
	}

	public static RuleHandler unregisterRuleHandler(String ruleType) {
		return getRuleGroupProcessor().unregisterRuleHandler(ruleType);
	}

	public RuleGroupProcessorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(RuleGroupProcessor.class);

		_serviceTracker.open();
	}

	private static final RuleGroupProcessorUtil _instance =
		new RuleGroupProcessorUtil();

	private final ServiceTracker<RuleGroupProcessor, RuleGroupProcessor>
		_serviceTracker;

}