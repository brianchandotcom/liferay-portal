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

package com.liferay.portal.mobile.device.rulegroup;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.RuleGroupProcessor;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
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

	@Override
	public MDRRuleGroupInstance evaluateRuleGroups(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		MDRRuleGroupInstance mdrRuleGroupInstance = evaluateRuleGroupInstances(
			Layout.class.getName(), layout.getPlid(), themeDisplay);

		if (mdrRuleGroupInstance != null) {
			return mdrRuleGroupInstance;
		}

		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		mdrRuleGroupInstance = evaluateRuleGroupInstances(
			LayoutSet.class.getName(), layoutSet.getLayoutSetId(),
			themeDisplay);

		return mdrRuleGroupInstance;
	}

	@Override
	public RuleHandler getRuleHandler(String ruleType) {
		return _ruleHandlers.get(ruleType);
	}

	@Override
	public Collection<RuleHandler> getRuleHandlers() {
		return Collections.unmodifiableCollection(_ruleHandlers.values());
	}

	@Override
	public Collection<String> getRuleHandlerTypes() {
		return _ruleHandlers.keySet();
	}

	@Override
	public void registerRuleHandler(RuleHandler ruleHandler) {
		RuleHandler oldRuleHandler = _ruleHandlers.put(
			ruleHandler.getType(), ruleHandler);

		if ((oldRuleHandler != null) && _log.isWarnEnabled()) {
			_log.warn(
				"Replacing existing rule handler type " +
					ruleHandler.getType());
		}
	}

	public void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	public void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	public void setRuleHandlers(Collection<RuleHandler> ruleHandlers) {
		for (RuleHandler ruleHandler : ruleHandlers) {
			registerRuleHandler(ruleHandler);
		}
	}

	@Override
	public RuleHandler unregisterRuleHandler(String ruleType) {
		return _ruleHandlers.remove(ruleType);
	}

	protected boolean evaluateRule(MDRRule rule, ThemeDisplay themeDisplay) {
		RuleHandler ruleHandler = _ruleHandlers.get(rule.getType());

		if (ruleHandler != null) {
			return ruleHandler.evaluateRule(rule, themeDisplay);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn("No rule handler registered for type " + rule.getType());
		}

		return false;
	}

	protected MDRRuleGroupInstance evaluateRuleGroupInstances(
		String className, long classPK, ThemeDisplay themeDisplay) {

		List<MDRRuleGroupInstance> mdrRuleGroupInstances =
			_mdrRuleGroupInstanceLocalService.getRuleGroupInstances(
				className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new RuleGroupInstancePriorityComparator());

		for (MDRRuleGroupInstance mdrRuleGroupInstance :
				mdrRuleGroupInstances) {

			MDRRuleGroup mdrRuleGroup =
				_mdrRuleGroupLocalService.fetchRuleGroup(
					mdrRuleGroupInstance.getRuleGroupId());

			if (mdrRuleGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Rule group instance " +
							mdrRuleGroupInstance.getRuleGroupInstanceId() +
								" has invalid rule group");
				}

				continue;
			}

			Collection<MDRRule> mdrRules = mdrRuleGroup.getRules();

			for (MDRRule mdrRule : mdrRules) {
				if (evaluateRule(mdrRule, themeDisplay)) {
					return mdrRuleGroupInstance;
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultRuleGroupProcessorImpl.class);

	@BeanReference(type = MDRRuleGroupInstanceLocalService.class)
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;

	@BeanReference(type = MDRRuleGroupLocalService.class)
	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;

	private final Map<String, RuleHandler> _ruleHandlers =
		new HashMap<String, RuleHandler>();

}