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

package com.liferay.portal.mobile.device.internal.rule.group;

import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.rule.RuleGroupProcessor;
import com.liferay.mobile.device.rules.rule.RuleHandler;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupInstancePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Edward Han
 */
@Component(immediate = true, service = RuleGroupProcessor.class)
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
		addRuleHandler(ruleHandler);
	}

	@Override
	public RuleHandler unregisterRuleHandler(String ruleType) {
		return _ruleHandlers.remove(ruleType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeRuleHandler"
	)
	protected void addRuleHandler(RuleHandler ruleHandler) {
		RuleHandler oldRuleHandler = _ruleHandlers.put(
			ruleHandler.getType(), ruleHandler);

		if ((oldRuleHandler != null) && _log.isWarnEnabled()) {
			_log.warn(
				"Replacing existing rule handler type " +
					ruleHandler.getType());
		}
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

	protected void removeRuleHandler(RuleHandler ruleHandler) {
		_ruleHandlers.remove(ruleHandler.getType());
	}

	@Reference(unbind = "-")
	protected void setMdrRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setMdrRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultRuleGroupProcessorImpl.class);

	private volatile MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;
	private volatile MDRRuleGroupLocalService _mdrRuleGroupLocalService;
	private final Map<String, RuleHandler> _ruleHandlers = new HashMap<>();

}