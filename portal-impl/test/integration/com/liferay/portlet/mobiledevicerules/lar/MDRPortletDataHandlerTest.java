/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.util.MDRTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MDRPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, MDRPortletDataHandler.NAMESPACE, "actions", true);
		addBooleanParameter(
			parameterMap, MDRPortletDataHandler.NAMESPACE, "rules", true);
	}

	@Override
	protected void addStagedModels() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString());

		MDRRuleGroup ruleGroup = MDRTestUtil.addRuleGroup(
			stagingGroup.getGroupId());

		MDRRuleGroupInstance ruleGroupInstance =
			MDRTestUtil.addRuleGroupInstance(
				stagingGroup.getGroupId(), Layout.class.getName(),
				layout.getPlid(), ruleGroup.getRuleGroupId());

		MDRTestUtil.addRule(ruleGroup.getRuleGroupId());

		MDRTestUtil.addAction(ruleGroupInstance.getRuleGroupInstanceId());

		ruleGroupInstance = MDRTestUtil.addRuleGroupInstance(
			stagingGroup.getGroupId(), ruleGroup.getRuleGroupId());

		MDRTestUtil.addAction(ruleGroupInstance.getRuleGroupInstanceId());
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new MDRPortletDataHandler();
	}

	@Override
	protected void deleteStagedModels() throws Exception {
		List<MDRAction> actions = MDRActionLocalServiceUtil.getMDRActions(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MDRAction action : actions) {
			MDRActionLocalServiceUtil.deleteAction(action);
		}

		List<MDRRule> rules = MDRRuleLocalServiceUtil.getMDRRules(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MDRRule rule : rules) {
			MDRRuleLocalServiceUtil.deleteRule(rule);
		}

		List<MDRRuleGroupInstance> ruleGroupInstances =
			MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstances(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MDRRuleGroupInstance ruleGroupInstance : ruleGroupInstances) {
			MDRRuleGroupInstanceLocalServiceUtil.deleteRuleGroupInstance(
				ruleGroupInstance);
		}

		MDRRuleGroupLocalServiceUtil.deleteRuleGroups(
			portletDataContext.getGroupId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.MOBILE_DEVICE_SITE_ADMIN;
	}

}