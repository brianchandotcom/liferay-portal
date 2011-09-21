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

package com.liferay.portal.mobile.device.rulegroup;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.RuleGroupProcessor;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService;
import com.liferay.portlet.mobiledevicerules.util.RuleGroupInstancePriorityComparator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Edward Han
 */
public class DefaultRuleGroupProcessorImpl implements RuleGroupProcessor {

	public MDRRuleGroupInstance evaluateRuleGroups(ThemeDisplay themeDisplay)
		throws SystemException {

		long plid = themeDisplay.getLayout().getPlid();
		long layoutSetId = themeDisplay.getLayoutSet().getLayoutSetId();

		//	Layout
		MDRRuleGroupInstance ruleGroupInstance = evaluateRuleGroupInstances(
			Layout.class.getName(), plid, themeDisplay);

		//	LayoutSet
		if (ruleGroupInstance == null) {
			ruleGroupInstance = evaluateRuleGroupInstances(
				LayoutSet.class.getName(), layoutSetId, themeDisplay);
		}

		return ruleGroupInstance;
	}

	public RuleHandler getRuleHandler(String ruleType) {
		return _ruleHandlers.get(ruleType);
	}

	public Collection<RuleHandler> getRuleHandlers() {
		return Collections.unmodifiableCollection(_ruleHandlers.values());
	}

	public Collection<String> getRuleHandlerTypes() {
		return _ruleHandlers.keySet();
	}

	public void registerRuleHandler(RuleHandler ruleHandler) {
		RuleHandler oldRuleHandler = _ruleHandlers.put(
			ruleHandler.getType(), ruleHandler);

		if (oldRuleHandler != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"RuleHandler with key: " + ruleHandler.getType() +
						" has already been registered. Replacing with " +
						ruleHandler);
			}
		}
	}

	public void setMdrRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	public void setMdrRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	public void setRuleHandlers(Collection<RuleHandler> ruleHandlers) {
		for (RuleHandler ruleHandler : ruleHandlers) {
			registerRuleHandler(ruleHandler);
		}
	}

	public RuleHandler unregisterRuleHandler(String ruleType) {
		return _ruleHandlers.remove(ruleType);
	}

	protected MDRRuleGroupInstance evaluateRuleGroupInstances(
			String className, long classPK, ThemeDisplay themeDisplay)
		throws SystemException {

		OrderByComparator orderByComparator =
			new RuleGroupInstancePriorityComparator();

		List<MDRRuleGroupInstance> ruleGroupInstances =
			_mdrRuleGroupInstanceLocalService.getRuleGroupInstances(
				className, classPK, 0, Integer.MAX_VALUE, orderByComparator);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			long ruleGroupId = ruleGroupInstance.getRuleGroupId();

			MDRRuleGroup ruleGroup = _mdrRuleGroupLocalService.fetchRuleGroup(
				ruleGroupId);

			if (ruleGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"RuleGroupInstance " +
							ruleGroupInstance.getRuleGroupInstanceId() +
							" with unknown RuleGroup, skipping");
				}
			}
			else {
				Collection<MDRRule> rules = ruleGroup.getRules();

				for (MDRRule rule : rules) {
					boolean result = evaluateRule(rule, themeDisplay);

					if (result) {
						return ruleGroupInstance;
					}
				}
			}
		}

		return null;
	}

	protected boolean evaluateRule(MDRRule rule, ThemeDisplay themeDisplay) {
		RuleHandler ruleHandler = _ruleHandlers.get(rule.getType());

		if (ruleHandler != null) {
			return ruleHandler.evaluateRule(rule, themeDisplay);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No RuleHandler registered for type:" +  rule.getType());
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultRuleGroupProcessorImpl.class);

	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;
	private Map<String, RuleHandler> _ruleHandlers =
		new HashMap<String, RuleHandler>();

}