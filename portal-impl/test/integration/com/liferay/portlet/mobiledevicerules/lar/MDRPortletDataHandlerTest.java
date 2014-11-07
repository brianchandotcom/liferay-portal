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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Layout;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.util.test.MDRTestUtil;

import java.util.Map;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
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
		Layout layout = LayoutTestUtil.addLayout(stagingGroup);

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
	protected String getPortletId() {
		return PortletKeys.MOBILE_DEVICE_SITE_ADMIN;
	}

}