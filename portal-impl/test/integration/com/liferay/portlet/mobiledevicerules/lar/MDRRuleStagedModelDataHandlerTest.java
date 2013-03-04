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

package com.liferay.portlet.mobiledevicerules.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.util.MDRTestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MDRRuleStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModels(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> stagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		List<StagedModel> stagedModels = new ArrayList<StagedModel>();

		stagedModels.add(MDRTestUtil.addRuleGroup(group.getGroupId()));

		stagedModelsMap.put(MDRRuleGroup.class.getName(), stagedModels);

		return stagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String, List<StagedModel>> dependentStagedModels)
		throws Exception {

		List<StagedModel> stagedModels = dependentStagedModels.get(
			MDRRuleGroup.class.getName());

		MDRRuleGroup ruleGroup = (MDRRuleGroup)stagedModels.get(0);

		return MDRTestUtil.addRule(ruleGroup.getRuleGroupId());
	}

	@Override
	protected String getElementName() {
		return "rule";
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return MDRRuleLocalServiceUtil.getMDRRuleByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected String getStagedModelClassName() {
		return MDRRule.class.getName();
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> stagedModelsMap, Group group)
		throws Exception {

		List<StagedModel> stagedModels = stagedModelsMap.get(
			MDRRuleGroup.class.getName());

		Assert.assertEquals(1, stagedModels.size());

		MDRRuleGroup ruleGroup = (MDRRuleGroup)stagedModels.get(0);

		MDRRuleGroupLocalServiceUtil.getMDRRuleGroupByUuidAndGroupId(
			ruleGroup.getUuid(), ruleGroup.getGroupId());
	}

}