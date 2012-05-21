/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

import java.util.Collection;

/**
 * @author Edward Han
 */
public class RuleGroupProcessorUtil {

	public static MDRRuleGroupInstance evaluateRuleGroups(
			ThemeDisplay themeDisplay)
		throws SystemException {

		return getRuleGroupProcessor().evaluateRuleGroups(themeDisplay);
	}

	public static RuleGroupProcessor getRuleGroupProcessor() {
		PortalRuntimePermission.checkGetBeanProperty(
			RuleGroupProcessorUtil.class);

		return _ruleGroupProcessor;
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

	public void setRuleGroupProcessor(RuleGroupProcessor ruleGroupProcessor) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ruleGroupProcessor = ruleGroupProcessor;
	}

	private static RuleGroupProcessor _ruleGroupProcessor;

}