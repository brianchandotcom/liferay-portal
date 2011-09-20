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

package com.liferay.portal.kernel.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
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

		return _ruleGroupProcessor.evaluateRuleGroups(themeDisplay);
	}

	public static RuleHandler getRuleHandler(String ruleType) {
		return _ruleGroupProcessor.getRuleHandler(ruleType);
	}

	public static Collection<RuleHandler> getRuleHandlers() {
		return _ruleGroupProcessor.getRuleHandlers();
	}

	public static Collection<String> getRuleHandlerTypes() {
		return _ruleGroupProcessor.getRuleHandlerTypes();
	}

	public static void registerRuleHandler(RuleHandler ruleHandler) {
		_ruleGroupProcessor.registerRuleHandler(ruleHandler);
	}

	public void setRuleGroupProcessor(RuleGroupProcessor ruleGroupProcessor) {
		_ruleGroupProcessor = ruleGroupProcessor;
	}

	public static RuleHandler unregisterRuleHandler(String ruleType) {
		return _ruleGroupProcessor.unregisterRuleHandler(ruleType);
	}

	private static RuleGroupProcessor _ruleGroupProcessor;
}